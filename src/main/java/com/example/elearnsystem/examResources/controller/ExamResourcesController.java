package com.example.elearnsystem.examResources.controller;

import com.example.elearnsystem.common.spider.pageProcessor.ExamResourcesPageProcess;
import com.example.elearnsystem.common.spider.pipeline.MySQLPipelineExam;
import com.example.elearnsystem.common.spider.pipeline.MySQLPipelineSpeaking;
import com.example.elearnsystem.examResources.domain.ExamResources;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import java.util.List;

@RestController
@RequestMapping("/managerExamResource")
public class ExamResourcesController {
    @PostMapping("/search")
    public int searchResources(String resourcesCategory) {
        int sum = 0;
        String firstUrl;
        if (resourcesCategory.equals("cet4")){
            firstUrl = "http://www.kekenet.com/cet4/201703/496738.shtml";
        }else if (resourcesCategory.equals("cet6")){
            firstUrl = "http://www.kekenet.com/cet6/201612/479908.shtml";
        }else {
            firstUrl = "http://www.kekenet.com/menu/201702/484166.shtml";
        }
        MySQLPipelineExam mySQLPipeline = new MySQLPipelineExam();
        //1、调用爬虫去抓
        Spider spider = Spider.create(new ExamResourcesPageProcess());
        //重写Downloader，解决用phantomJS渲染页面重复下载的BUG
//        SeleniumDownloader seleniumDownloader = new SeleniumDownloader();
        spider.setDownloader(new HttpClientDownloader());
//        spider.setScheduler(new FileCacheQueueScheduler("./src/main/resources/static/cacheQueueFileofExam"));
        spider.addPipeline(new ConsolePipeline()).addPipeline(mySQLPipeline).addPipeline(new ResultItemsCollectorPipeline());
        spider.addUrl(firstUrl).thread(5).run();
        List<ExamResources> list = mySQLPipeline.getCollected();
        for (ExamResources entity : list) {
            entity.setResourcesCategory(resourcesCategory);
            ++sum;
        }
        return 0;
    }
}
