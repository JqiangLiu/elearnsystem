package com.example.elearnsystem.common.spider.pipeline;

import com.example.elearnsystem.newsResources.domain.NewsResource;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.CollectorPipeline;

import java.util.ArrayList;
import java.util.List;

public class MySQLPipelineNews implements CollectorPipeline<NewsResource> {
    private List<NewsResource> collector = new ArrayList<>();
    @Override
    public void process(ResultItems resultItems, Task task) {
        resultItems.getRequest().getUrl();
        if (resultItems.get("resourcesTitle") != null){
            NewsResource entity = new NewsResource();
            entity.setResourcesTitle(resultItems.get("resourcesTitle"));
            entity.setResourcesText(resultItems.get("resourcesText").toString());
            entity.setResourcesTranslation_text(resultItems.get("resourcesTranslation_text").toString());
            entity.setResourcesParentUrl(resultItems.get("resourcesParentUrl"));
            entity.setResourcesDate(resultItems.get("resourcesDate"));
            entity.setResourcesCite(resultItems.get("resourcesCite"));
            entity.setResourcesImg(resultItems.get("resourceImg"));
            entity.setInSystem(false);
            collector.add(entity);
        }
    }

    @Override
    public List<NewsResource> getCollected() {
        return collector;
    }
}
