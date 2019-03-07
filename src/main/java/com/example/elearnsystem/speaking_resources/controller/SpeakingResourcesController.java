package com.example.elearnsystem.speaking_resources.controller;

import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.common.spider.EPageProcessor;

import com.example.elearnsystem.common.spider.downloader.SeleniumDownloader;
import com.example.elearnsystem.common.spider.pipeline.MySQLPipeline;
import com.example.elearnsystem.common.spider.scheduler.LevelLimitScheduler;
import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speaking_resources.service.SpeakingResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
//import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
//import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.util.List;

@RestController
@RequestMapping("/managerSpeakingResource")
public class SpeakingResourcesController {
//    private final static Logger logger = LoggerFactory.getLogger(SpeakingResourcesController.class);

    @Autowired
    private SpeakingResourcesService speakingResourcesService;

    @PostMapping("/search")
    public List<SpeakingResourceDTO> searchResources(String id){
        MySQLPipeline mySQLPipeline = new MySQLPipeline();
        //1、调用爬虫去抓
        Spider spider = Spider.create(new EPageProcessor());
        //重写Downloader，解决用phantomJS渲染页面重复下载的BUG
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader();
        spider.setDownloader(seleniumDownloader);
        spider.setScheduler(new FileCacheQueueScheduler("C:\\Users\\Mr.Liu\\IdeaProjects\\elearnsystem\\src\\main\\resources\\static"));
        spider.addPipeline(new ConsolePipeline()).addPipeline(mySQLPipeline);
        spider.addUrl("http://xiu.kekenet.com/index.php/main/column.html?tag_id="+id).thread(5).run();
//        System.out.println("抓完了！！！！");
        List<SpeakingResource> list = mySQLPipeline.getCollected();
        return saveAll(list);
    }

    @PostMapping("/saveAll")
    public List<SpeakingResourceDTO> saveAll(List<SpeakingResource> list){
        String resourcesCategory = null;
        Boolean inSystem = null;
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(1);
        pageReq.setLimit(15);
        pageReq.setSort("inSystem");
        pageReq.setDir("ASC");
        speakingResourcesService.saveAll(list);
        return speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }

    @PostMapping("/find")
    public List<SpeakingResourceDTO> find(@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("inSystem");
        pageReq.setDir("ASC");
        return speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody SpeakingResource entity){
        try {
            speakingResourcesService.update(entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

//    @PutMapping("/updateAll")
//    public List<SpeakingResourceDTO> allinSystem(){
//        MyPageRequest pageReq = new MyPageRequest();
//        pageReq.setPage(1);
//        pageReq.setLimit(15);
//        pageReq.setSort("inSystem");
//        pageReq.setDir("ASC");
//        return speakingResourcesService.findAll(pageReq.getPageable());
//    }

    @PostMapping("/delete")
    public List<SpeakingResourceDTO> delete(Long[] ids,@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
//        Long[] mids = {43L,44L,46L};
        speakingResourcesService.deleteAll(ids);
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(1);
        pageReq.setLimit(limit);
        pageReq.setSort("inSystem");
        pageReq.setDir("ASC");
        return speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }
}
