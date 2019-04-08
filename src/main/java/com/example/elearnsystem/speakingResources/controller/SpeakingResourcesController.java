package com.example.elearnsystem.speakingResources.controller;

import com.example.elearnsystem.common.dtw.DynamicTimeWrapping2D;
import com.example.elearnsystem.common.mfcc.MFCC;
import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.common.spider.pageProcessor.EPageProcessor;

import com.example.elearnsystem.common.spider.pipeline.MySQLPipeline;
import com.example.elearnsystem.common.util.MP3ToWav;
import com.example.elearnsystem.speakingResources.domain.SpeakingResource;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceUpdateDTO;
import com.example.elearnsystem.speakingResources.service.SpeakingResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
//import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
//import us.codecraft.webmagic.pipeline.JsonFilePipeline;

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

    @GetMapping("/findOne")
    public SpeakingResourceDTO findOne(@RequestParam(value = "id")Long id){
        return speakingResourcesService.findOne(id);
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
    public List<SpeakingResourceDTO> publishAll(@RequestParam(value = "ids[]")Long[] ids, @RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        try {
            speakingResourcesService.publishAll(ids);
            return utilsFind(page,limit,resourcesCategory,inSystem);
        }catch (Exception e){
            return null;
        }
    }

    /*
    *  用户录音以mp3形式保存
    *  将mp3转成wav格式，提取mfcc特征
    *  提取样本文件的mfcc特征
    *  计算距离并得出相似度返回
    */
    @PostMapping("/uploadRecorder")
    public Double uploadRecorder(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id, @RequestParam("extension") String extension){
        if (!file.isEmpty()){
            String recorderPath = "./src/main/resources/static/user_recorder/"+"userid"+"_"+id+extension;
//            String recorderTempPath = "./src/main/resources/static/user_recorder_temporary/"+"userid"+"_"+id+"."+extension;
            String wavTemp = "./src/main/resources/static/user_recorder_temporary/"+"userid_temp"+"_"+id+".wav";
            String recorderTempPathWav = "./src/main/resources/static/user_recorder_temporary/"+"userid"+"_"+id+".wav";
            String recorderTempPath = "./src/main/resources/static/user_recorder_temporary/"+file.getName();
            String resourcesMFCCPath = "./src/main/resources/static/speaking_resources_mfcc/"+id+".wav";
            String resourcesMp3Path = "./src/main/resources/static/speaking_resources_mp3/"+id+".mp3";
            File f = new File(recorderPath);
            switch (extension){
                case "mp3" :{
                    try {   // 首先统一保存进临时文件夹，转换格式，提取特征
                            File recorderTemp = new File(recorderTempPath);
                            File resourcesMFCC = new File(resourcesMFCCPath);
                            if (recorderTemp.exists())
                                recorderTemp.delete();
                            file.transferTo(recorderTemp);
                            MP3ToWav.toWav(recorderTempPath,recorderTempPathWav); // 转换结束后，文件已生成
                            if (!resourcesMFCC.exists()){
                                MP3ToWav.toWav(resourcesMp3Path,resourcesMFCCPath);
                            }
                            MFCC mfcc = new MFCC();
                            double[][] result1 = mfcc.getMfcc(resourcesMFCCPath); // 样本音频
                            double[][] result2 = mfcc.getMfcc(recorderTempPathWav); // 用户录音
                            DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(result1,result2);
                            double distance = dtw.calDistance();
//                            if (){ // 取历史最高分进行比较，新纪录就保存新音频和记录新分数，否则不作修改
//                                    if (f.exists()){
//                                        f.delete();
//                                        recorderTemp.renameTo(f); //将临时文件夹的音频移动到用户文件夹，原文件自动删除
//                                    }
//                            }
                            int i = (new Double(distance)).intValue();
                            System.out.println(i);
                            return distance;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "wav" :{
                    /*
                    * 接收了直接放进temp文件夹
                    * 进行运算评分
                    * 刷新分数则转换成mp3存进用户文件夹*/
                try {
                    Double d = 2.17388371106151E303;
                    BigDecimal b = new BigDecimal(d);
                    System.out.println(b.toPlainString());
                    File recorderTemp = new File(wavTemp);
                    File resourcesMFCC = new File(resourcesMFCCPath);
                    if (recorderTemp.exists())
                        recorderTemp.delete();
                    file.transferTo(recorderTemp);
                    MP3ToWav.toWav(wavTemp, recorderTempPathWav); // 转换结束后，文件已生成
                    recorderTemp.delete();
                    if (!resourcesMFCC.exists()) {
                        MP3ToWav.toWav(resourcesMp3Path, resourcesMFCCPath);
                    }
                    MFCC mfcc = new MFCC();
                    double[][] result1 = mfcc.getMfcc(resourcesMFCCPath); // 样本音频
                    double[][] result2 = mfcc.getMfcc(recorderTempPathWav); // 用户录音
                    DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(result1,  result2);
                    double distance = dtw.calDistance();
                    //                       if (){ // 取历史最高分进行比较，新纪录就保存新音频和记录新分数，否则不作修改
//                                    if (f.exists()){
//                                        f.delete();
//                                            file.transferTo(f); // 保存为mp3格式，并移动到用户文件夹
//                                    }
//                            }
                    return distance;
                }catch (IOException e) {
                    e.printStackTrace();
                }
                    break;
                }
            }
        }
        return null;
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
