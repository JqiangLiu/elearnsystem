package com.example.elearnsystem.userRecorder.service;

import com.example.elearnsystem.common.dtw.DynamicTimeWrapping2D;
import com.example.elearnsystem.common.mfcc.MFCC;
import com.example.elearnsystem.common.util.BeanUtils;
import com.example.elearnsystem.common.util.MP3ToWav;
import com.example.elearnsystem.speakingResources.domain.SpeakingResource;
import com.example.elearnsystem.speakingResources.repository.SpeakingResourcesRepository;
import com.example.elearnsystem.userRecorder.domain.UserRecorder;
import com.example.elearnsystem.userRecorder.domain.dto.UserRecorderDTO;
import com.example.elearnsystem.userRecorder.repository.UserRecorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserRecorderService implements IUserRecorderService {

    @Autowired
    UserRecorderRepository userRecorderRepository;
    @Autowired
    SpeakingResourcesRepository speakingResourcesRepository;

    @Override
    public void saveOne(UserRecorder userRecorder) {
        userRecorderRepository.save(userRecorder);
    }

    @Override
    public Optional<UserRecorder> findOne(String userName, Long speakingId) {
        return userRecorderRepository.findAllByUserNameAndSpeakingId(userName, speakingId);
    }

    @Override
    public List<UserRecorderDTO> findByResourcesCategory(String resourcesCategory,Pageable pageable,String userName) {
        List<UserRecorderDTO> DTOList = new ArrayList<>();
        Page<UserRecorder> res;
        List<UserRecorder> list;
        long sum = 0;
        Double sumRecorderSize = userRecorderRepository.getSumRecorderSize(userName);
        if (resourcesCategory != null) {
            res = userRecorderRepository.findAllByResourcesCategory(resourcesCategory, pageable);
            list = res.getContent();
            sum = res.getTotalElements();
        }else{
            res = userRecorderRepository.findAll(pageable);
            list = res.getContent();
            sum = res.getTotalElements();
        }
        for (UserRecorder s: list
        ) {
            UserRecorderDTO temp = new UserRecorderDTO();
            BeanUtils.copyProperties(s,temp);
            temp.setSum(sum);
            temp.setAllSize(sumRecorderSize);
            DTOList.add(temp);
        }
        return DTOList;
    }

    @Override
    public List<UserRecorderDTO> findAll(Specification<UserRecorder> specification, Pageable pageable) {
        List<UserRecorderDTO> DTOList = new ArrayList<>();
        Page<UserRecorder> res;
        List<UserRecorder> list;
        long sum = 0;
        res = userRecorderRepository.findAll(specification,pageable);
        list = res.getContent();
        sum = res.getTotalElements();
        for (UserRecorder s: list
        ) {
            UserRecorderDTO temp = new UserRecorderDTO();
            BeanUtils.copyProperties(s,temp);
            temp.setSum(sum);
            DTOList.add(temp);
        }
        return DTOList;

    }

    /**
     * 用户录音以mp3形式保存
     * 将mp3转成wav格式，提取mfcc特征
     * 提取样本文件的mfcc特征
     * 计算距离并得出相似度返回
     */
    @Override
    public Double uploadRecorder(MultipartFile file, Long speakingId, String extension, HttpSession session) {
        if (!file.isEmpty()) {
            String userName = String.valueOf(session.getAttribute("userName"));
            String recorderPath = "E://eSystemResources/user_recorder/" + userName + "_" + speakingId +"."+ extension;
//            String recorderTempPath = "./src/main/resources/static/user_recorder_temporary/"+"userid"+"_"+id+"."+extension;
            String wavTemp = "E://eSystemResources/user_recorder_temporary/" + userName + "_temp_" + speakingId + ".wav";
            String recorderTempPathWav = "E://eSystemResources/user_recorder_temporary/" + userName + "_" + speakingId + ".wav";
            String recorderTempPath = "E://eSystemResources/user_recorder_temporary/" + file.getName();
            String resourcesMFCCPath = "E://eSystemResources/speaking_resources_mfcc/" + speakingId + ".wav";
            String resourcesMp3Path = "E://eSystemResources/speaking_resources_mp3/" + speakingId + ".mp3";
            File f = new File(recorderPath);
            switch (extension) {
                case "mp3": {
                    try {   // 首先统一保存进临时文件夹，转换格式，提取特征
                        File recorderTemp = new File(recorderTempPath);
                        File recorderTempWav = new File(recorderTempPathWav);
                        File resourcesMFCC = new File(resourcesMFCCPath);
                        if (recorderTempWav.exists()) {
                            recorderTempWav.delete();
                        }
                        file.transferTo(recorderTemp);
                        /**转换结束后，文件已生成*/
                        MP3ToWav.toWav(recorderTempPath, recorderTempPathWav);
                        if (!resourcesMFCC.exists()) {
                            MP3ToWav.toWav(resourcesMp3Path, resourcesMFCCPath);
                        }
                        MFCC mfcc = new MFCC();
                        /**样本音频*/
                        double[][] result1 = mfcc.getMfcc(resourcesMFCCPath);
                        /**用户录音*/
                        double[][] result2 = mfcc.getMfcc(recorderTempPathWav);
                        DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(result1, result2);
                        double distance = dtw.calDistance();
                        Optional<UserRecorder> entity = findOne(userName, speakingId);
                        UserRecorder userRecorder = new UserRecorder();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DecimalFormat df = new DecimalFormat("#.00");
                        SpeakingResource speakingResource = speakingResourcesRepository.findById(speakingId).get();
                        if (entity.isPresent() == true && entity.get().getGrade() < distance) { // 取历史最高分进行比较，新纪录就保存新音频和记录新分数，否则不作修改
                            if (f.exists()) {
                                f.delete();
                                recorderTemp.renameTo(f); //将临时文件夹的音频移动到用户文件夹，原文件自动删除
                                BeanUtils.copyProperties(entity.get(),userRecorder);
                                userRecorder.setUpdateTime(dateFormat.format(new Date()));
                                userRecorder.setGrade(distance);
                                userRecorder.setRecorderSize(Double.valueOf(df.format((double) f.length() / 1048576)));
                                saveOne(userRecorder);
                            }
                        } else {
                            recorderTemp.renameTo(f);
                            userRecorder.setUpdateTime(dateFormat.format(new Date()));
                            userRecorder.setGrade(distance);
                            userRecorder.setRecorderPath("/user_recorder/" + userName + "_" + speakingId +"."+ extension);
                            userRecorder.setUserName(userName);
                            userRecorder.setSpeakingId(speakingId);
                            userRecorder.setResourcesCategory(speakingResource.getResourcesCategory());
                            userRecorder.setResourcesTitle(speakingResource.getResourcesTitle());
                            userRecorder.setRecorderSize(Double.valueOf(df.format((double) f.length() / 1048576)));
                            System.out.println(f.length());
                            recorderTemp.delete();
                            saveOne(userRecorder);
                        }
                        return distance;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    /*
                     * 接收了直接放进temp文件夹
                     * 进行运算评分
                     * 刷新分数则转换成mp3存进用户文件夹*/
                    try {
                        File recorderTemp = new File(wavTemp);
                        File recorderTempWav = new File(recorderTempPathWav);
                        File resourcesMFCC = new File(resourcesMFCCPath);
                        if (recorderTempWav.exists())
                            recorderTempWav.delete();
                        file.transferTo(recorderTemp);
                        MP3ToWav.toWav(wavTemp, recorderTempPathWav); // 转换结束后，文件已生成
                        if (!resourcesMFCC.exists()) {
                            MP3ToWav.toWav(resourcesMp3Path, resourcesMFCCPath);
                        }
                        MFCC mfcc = new MFCC();
                        double[][] result1 = mfcc.getMfcc(resourcesMFCCPath); // 样本音频
                        double[][] result2 = mfcc.getMfcc(recorderTempPathWav); // 用户录音
                        DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(result1, result2);
                        double distance = dtw.calDistance();
                        Optional<UserRecorder> entity = findOne(userName, speakingId);
                        UserRecorder userRecorder = new UserRecorder();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DecimalFormat df = new DecimalFormat("#.00");
                        SpeakingResource speakingResource = speakingResourcesRepository.findById(speakingId).get();
                        if (entity.isPresent() == true && entity.get().getGrade() < distance) { // 取历史最高分进行比较，新纪录就保存新音频和记录新分数，否则不作修改
                            if (f.exists()) {
                                f.delete();
                                file.transferTo(f);
                                BeanUtils.copyProperties(entity.get(),userRecorder);
                                userRecorder.setUpdateTime(dateFormat.format(new Date()));
                                userRecorder.setGrade(distance);
                                userRecorder.setRecorderSize(Double.valueOf(df.format((double) f.length() / 1048576)));
                                saveOne(userRecorder);
                            }
                        }else{
                            recorderTemp.renameTo(f);
                            userRecorder.setUpdateTime(dateFormat.format(new Date()));
                            userRecorder.setGrade(distance);
                            userRecorder.setRecorderPath("/user_recorder/" + userName + "_" + speakingId +"."+ extension);
                            userRecorder.setUserName(userName);
                            userRecorder.setSpeakingId(speakingId);
                            userRecorder.setResourcesCategory(speakingResource.getResourcesCategory());
                            userRecorder.setResourcesTitle(speakingResource.getResourcesTitle());
                            userRecorder.setRecorderSize(Double.valueOf(df.format((double) f.length() / 1048576)));
                            recorderTemp.delete();
                            saveOne(userRecorder);
                        }
                        return distance;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Long[] id) {
        List<String> recorderPathList = userRecorderRepository.findAllByIds(id);
        for (String s:recorderPathList) {
            File file = new File("E://eSystemResources"+s);
            file.delete();
        }
        userRecorderRepository.deleteAll(id);
    }
}
