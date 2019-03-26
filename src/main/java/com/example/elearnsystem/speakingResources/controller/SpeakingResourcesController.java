package com.example.elearnsystem.speakingResources.controller;

import com.example.elearnsystem.common.dtw.DynamicTimeWrapping2D;
import com.example.elearnsystem.common.mfcc.MFCC;
import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.common.spider.pageProcessor.EPageProcessor;

import com.example.elearnsystem.common.spider.pipeline.MySQLPipeline;
import com.example.elearnsystem.speakingResources.domain.SpeakingResource;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceUpdateDTO;
import com.example.elearnsystem.speakingResources.service.SpeakingResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
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
    public int searchResources(String resourcesCategory){
        int sum = 0;
        MySQLPipeline mySQLPipeline = new MySQLPipeline();
        //1、调用爬虫去抓
        Spider spider = Spider.create(new EPageProcessor());
        //重写Downloader，解决用phantomJS渲染页面重复下载的BUG
//        SeleniumDownloader seleniumDownloader = new SeleniumDownloader();
        spider.setDownloader(new HttpClientDownloader());
        spider.setScheduler(new FileCacheQueueScheduler("./src/main/resources/static/cacheQueueFile"));
        spider.addPipeline(new ConsolePipeline()).addPipeline(mySQLPipeline).addPipeline(new ResultItemsCollectorPipeline());
        spider.addUrl("http://xiu.kekenet.com/index.php/main/column.html?tag_id="+resourcesCategory).thread(5).run();
//        System.out.println("抓完了！！！！");
        List<SpeakingResource> list = mySQLPipeline.getCollected();
        for (SpeakingResource entity: list) {
            entity.setResourcesCategory(resourcesCategory);
            ++sum;
        }
        return saveAll(list,sum);
    }

    @PostMapping("/saveAll")
    public int saveAll(List<SpeakingResource> list,int updateSum){
//        String resourcesCategory = null;
//        Boolean inSystem = null;
//        MyPageRequest pageReq = new MyPageRequest();
//        pageReq.setPage(1);
//        pageReq.setLimit(15);
//        pageReq.setSort("id");
//        pageReq.setDir("ASC");
        speakingResourcesService.saveAll(list);
//        List<SpeakingResourceDTO> dto = speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
//        dto.get(0).setUpdateSum(updateSum);
        return updateSum;
    }

    @GetMapping("/find")
    public List<SpeakingResourceDTO> find(@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        return utilsFind(page,limit,resourcesCategory,inSystem);
    }

    @GetMapping("/userFind")
    public List<SpeakingResourceDTO> userFind(@RequestParam(name="page") int page, String resourcesCategory){
        return utilsFind(page,5,resourcesCategory,true);
    }

    @PostMapping("/update")
    public List<SpeakingResourceDTO> update(@RequestBody SpeakingResourceUpdateDTO entity,@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        try {
            speakingResourcesService.update(entity);
            return utilsFind(page,limit,resourcesCategory,inSystem);
        }catch (Exception e){
            return null;
        }
    }

    @PostMapping("/publishAll")
    public List<SpeakingResourceDTO> publishAll(Long[] ids, @RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        try {
            speakingResourcesService.publishAll(ids);
            return utilsFind(page,limit,resourcesCategory,inSystem);
        }catch (Exception e){
            return null;
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

    @DeleteMapping
    public List<SpeakingResourceDTO> delete(@RequestParam(value = "ids[]")Long[] ids,@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
//        Long[] mids = {43L,44L,46L};
        speakingResourcesService.deleteAll(ids);
        return utilsFind(page,limit,resourcesCategory,inSystem);
    }

    /*封装查询代码*/
    public List<SpeakingResourceDTO> utilsFind(int page,int limit,String resourcesCategory, Boolean inSystem){
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("id");
        pageReq.setDir("ASC");
        if (resourcesCategory != null && resourcesCategory.equals("all")){
            resourcesCategory = null;
        }
        return speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }
}
