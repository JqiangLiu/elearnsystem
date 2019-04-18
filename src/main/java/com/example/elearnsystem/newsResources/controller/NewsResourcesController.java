package com.example.elearnsystem.newsResources.controller;

import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.common.spider.pageProcessor.NewsPageProcessor;
import com.example.elearnsystem.common.spider.pipeline.MySQLPipelineNews;
import com.example.elearnsystem.newsResources.domain.NewsResource;
import com.example.elearnsystem.newsResources.domain.dto.NewsResourceDTO;
import com.example.elearnsystem.newsResources.domain.dto.NewsResourceUpdateDTO;
import com.example.elearnsystem.newsResources.service.NewsResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import java.util.List;

@RestController
@RequestMapping("/managerNewsResource")
public class NewsResourcesController {

    @Autowired
    private NewsResourcesService newsResourcesService;

    @PostMapping("/search")
    public int searchResources(String resourcesCategory){
        int sum = 0;
        MySQLPipelineNews mySQLPipeline = new MySQLPipelineNews();
        //1、调用爬虫去抓
        Spider spider = Spider.create(new NewsPageProcessor());
//        spider.setDownloader(new HttpClientDownloader());
        spider.setScheduler(new FileCacheQueueScheduler("./src/main/resources/static/cacheQueueFileofNews"));
        spider.addPipeline(new ConsolePipeline()).addPipeline(mySQLPipeline).addPipeline(new ResultItemsCollectorPipeline());
        spider.addUrl("http://www.kekenet.com/read/news/"+resourcesCategory).thread(5).run();
        List<NewsResource> list = mySQLPipeline.getCollected();
        for (NewsResource entity: list) {
            entity.setResourcesCategory(resourcesCategory);
            ++sum;
        }
        return saveAll(list,sum);
    }

    @PostMapping("/saveAll")
    public int saveAll(List<NewsResource> list, int updateSum){
        newsResourcesService.saveAll(list);
        return updateSum;
    }

    @GetMapping("/find")
    public List<NewsResourceDTO> find(@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        return utilsFind(page,limit,resourcesCategory,inSystem);
    }

    @GetMapping("/userFind")
    public List<NewsResourceDTO> userFind(@RequestParam(name="page") int page, String resourcesCategory){
        return utilsFind(page,5,resourcesCategory,true);
    }

    @GetMapping("/findOne")
    public NewsResourceDTO findOne(@RequestParam(value = "id")Long id){
        return newsResourcesService.findOne(id);
    }

    @PostMapping("/update")
    public List<NewsResourceDTO> update(@RequestBody NewsResourceUpdateDTO entity, @RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        try {
            newsResourcesService.update(entity);
            return utilsFind(page,limit,resourcesCategory,inSystem);
        }catch (Exception e){
            return null;
        }
    }

    @PostMapping("/publishAll")
    public List<NewsResourceDTO> publishAll(@RequestParam(value = "ids[]")Long[] ids, @RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        try {
            newsResourcesService.publishAll(ids);
            return utilsFind(page,limit,resourcesCategory,inSystem);
        }catch (Exception e){
            return null;
        }
    }

    @DeleteMapping
    public List<NewsResourceDTO> delete(@RequestParam(value = "ids[]")Long[] ids,@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        newsResourcesService.deleteAll(ids);
        return utilsFind(page,limit,resourcesCategory,inSystem);
    }

    /*封装查询代码*/
    public List<NewsResourceDTO> utilsFind(int page, int limit, String resourcesCategory, Boolean inSystem){
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("resourcesDate");
        pageReq.setDir("DESC");
        if (resourcesCategory != null && resourcesCategory.equals("all")){
            resourcesCategory = null;
        }
        return newsResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }

}
