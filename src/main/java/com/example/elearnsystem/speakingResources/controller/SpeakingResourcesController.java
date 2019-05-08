package com.example.elearnsystem.speakingResources.controller;


import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.common.spider.pageProcessor.EPageProcessor;

import com.example.elearnsystem.common.spider.pipeline.MySQLPipelineSpeaking;

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


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/managerSpeakingResource")
public class SpeakingResourcesController {
//    private final static Logger logger = LoggerFactory.getLogger(SpeakingResourcesController.class);

    @Autowired
    private SpeakingResourcesService speakingResourcesService;

    @PostMapping("/search")
    public int searchResources(String resourcesCategory) {
        int sum = 0;
        MySQLPipelineSpeaking mySQLPipeline = new MySQLPipelineSpeaking();
        //1、调用爬虫去抓
        Spider spider = Spider.create(new EPageProcessor());
        //重写Downloader，解决用phantomJS渲染页面重复下载的BUG
//        SeleniumDownloader seleniumDownloader = new SeleniumDownloader();
        spider.setDownloader(new HttpClientDownloader());
        spider.setScheduler(new FileCacheQueueScheduler("./src/main/resources/static/cacheQueueFileofSpeaking"));
        spider.addPipeline(new ConsolePipeline()).addPipeline(mySQLPipeline).addPipeline(new ResultItemsCollectorPipeline());
        spider.addUrl("http://xiu.kekenet.com/index.php/main/column.html?tag_id=" + resourcesCategory).thread(5).run();
        List<SpeakingResource> list = mySQLPipeline.getCollected();
        for (SpeakingResource entity : list) {
            entity.setResourcesCategory(resourcesCategory);
            ++sum;
        }
        return saveAll(list, sum);
    }

    @PostMapping("/saveAll")
    public int saveAll(List<SpeakingResource> list, int updateSum) {
        speakingResourcesService.saveAll(list);
        return updateSum;
    }

    @GetMapping("/find")
    public List<SpeakingResourceDTO> find(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String resourcesCategory, Boolean inSystem, HttpSession session) {
        return utilsFind(page, limit, resourcesCategory, inSystem);
    }

    @GetMapping("/userFind")
    public List<SpeakingResourceDTO> userFind(@RequestParam(name = "page") int page, String resourcesCategory) {
        return utilsFind(page, 5, resourcesCategory, true);
    }

    @GetMapping("/findOne")
    public SpeakingResourceDTO findOne(@RequestParam(value = "id") Long id) {
        return speakingResourcesService.findOne(id);
    }

    @PostMapping("/update")
    public List<SpeakingResourceDTO> update(@RequestBody SpeakingResourceUpdateDTO entity, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String resourcesCategory, Boolean inSystem) {
        try {
            speakingResourcesService.update(entity);
            return utilsFind(page, limit, resourcesCategory, inSystem);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/publishAll")
    public List<SpeakingResourceDTO> publishAll(@RequestParam(value = "ids[]") Long[] ids, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String resourcesCategory, Boolean inSystem) {
        try {
            speakingResourcesService.publishAll(ids);
            return utilsFind(page, limit, resourcesCategory, inSystem);
        } catch (Exception e) {
            return null;
        }
    }


    @DeleteMapping
    public List<SpeakingResourceDTO> delete(@RequestParam(value = "ids[]") Long[] ids, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, String resourcesCategory, Boolean inSystem) {
        speakingResourcesService.deleteAll(ids);
        return utilsFind(page, limit, resourcesCategory, inSystem);
    }

    /**
     * 封装查询代码
     */
    public List<SpeakingResourceDTO> utilsFind(int page, int limit, String resourcesCategory, Boolean inSystem) {
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("id");
        pageReq.setDir("ASC");
        if (resourcesCategory != null && resourcesCategory.equals("all")) {
            resourcesCategory = null;
        }
        return speakingResourcesService.findAll(pageReq.getPageable(), resourcesCategory, inSystem);
    }
}
