package com.example.elearnsystem.common.spider.pipeline;

import com.example.elearnsystem.examResources.domain.ExamResources;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.CollectorPipeline;

import java.util.ArrayList;
import java.util.List;

public class MySQLPipelineExam implements CollectorPipeline<ExamResources> {
    private List<ExamResources> collector = new ArrayList<>();

    @Override
    public List<ExamResources> getCollected() {
        return collector;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        resultItems.getRequest().getUrl();
        if (resultItems.get("resourcesTitle") != null) {
            ExamResources entity = new ExamResources();
            entity.setResourcesTitle(resultItems.get("resourcesTitle"));
            entity.setResourcesText(resultItems.get("resourcesText").toString());
            entity.setResourcesTranslation_text(resultItems.get("resourcesTranslation_text").toString());
            entity.setResourcesParentUrl(resultItems.get("resourcesParentUrl"));
            entity.setResourcesDate(resultItems.get("resourcesDate"));
            entity.setResourcesCite(resultItems.get("resourcesCite"));
            entity.setResourcesNetworkUrl(resultItems.get("resourcesNetworkUrl"));
            entity.setInSystem(false);
            collector.add(entity);
        }
    }
}
