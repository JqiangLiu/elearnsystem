package com.example.elearnsystem.common.spider.pipeline;

import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.CollectorPipeline;

import java.util.ArrayList;
import java.util.List;


public class MySQLPipeline implements CollectorPipeline<SpeakingResource> {
    private List<SpeakingResource> collector = new ArrayList<SpeakingResource>();
    @Override
    public void process(ResultItems resultItems, Task task) {
        SpeakingResource entity = new SpeakingResource();
        entity.setResourcesTitle(resultItems.get("resourcesTitle"));
        entity.setResourcesText(resultItems.get("resourcesText"));
        entity.setResourcesTranslation_text(resultItems.get("resourcesTranslation_text"));
        entity.setResourcesParentUrl(resultItems.get("resourcesParentUrl"));
        entity.setResourcesNetworkUrl(resultItems.get("resourcesNetworkUrl"));
        entity.setInSystem(false);
        collector.add(entity);
    }

    @Override
    public List<SpeakingResource> getCollected() {
        return collector;
    }
}
