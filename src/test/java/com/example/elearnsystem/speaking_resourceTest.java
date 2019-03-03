package com.example.elearnsystem;

import com.example.elearnsystem.common.util.BeanUtils;
import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speaking_resources.service.ISpeakingResourcesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class speaking_resourceTest {
    @Autowired
    private ISpeakingResourcesService speakingResourcesService;

    @Test
    public void saveTest(){
        List<SpeakingResource> lists = new ArrayList<>();
        for(int i = 0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(true);
            entity.setResourcesCategory("exam");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
        for (int i=0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(true);
            entity.setResourcesCategory("trip");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
        for(int i = 0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(false);
            entity.setResourcesCategory("exam");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
        for (int i=0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(false);
            entity.setResourcesCategory("trip");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
    }
}
