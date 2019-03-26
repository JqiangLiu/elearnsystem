package com.example.elearnsystem;

import com.example.elearnsystem.common.dtw.DynamicTimeWrapping2D;
import com.example.elearnsystem.common.mfcc.MFCC;
import com.example.elearnsystem.speakingResources.domain.SpeakingResource;
import com.example.elearnsystem.speakingResources.service.ISpeakingResourcesService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.elearnsystem.common.util.DownLoadFile.downLoadFromUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class speaking_resourceTest {
    @Autowired
    private ISpeakingResourcesService speakingResourcesService;
    @Autowired
    private static ChromeDriverService service;

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

    @Test
    public void downLoadTest(){
        try{
            downLoadFromUrl("http://k6.kekenet.com/Sound/2016/12/cgly161222_3125113YM3.mp3",
                    "test.mp3","./src/main/resources/static/speaking_resources_mp3");
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
    @Test
    public void MP3ToWav(){
        String sourcePath = "e:\\cgly170607_2126871Weg.mp3";
        String targetPath = "e:\\test2.wav";
        String webroot = "e:\\javaSoft\\ffmpeg-20190312-d227ed5-win64-static\\bin";
        Runtime run = null;
        try {
            String path = new File(webroot).getAbsolutePath();
            System.out.println(path);
            run = Runtime.getRuntime();
            Process p = run.exec(path+"\\ffmpeg -y -i "+sourcePath+" -acodec pcm_s16le -ac 1 "+targetPath);
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            run.freeMemory();
        }

    }

    @Test
    public void mfcc(){
        MFCC mfcc = new MFCC();
        double[][] result1 = mfcc.getMfcc("e:\\test2.wav");
        double[][] result2 = mfcc.getMfcc("e:\\test2.wav");
        DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(result1,result2);
        double distance = dtw.calDistance();
        System.out.println(distance);
    }
}
