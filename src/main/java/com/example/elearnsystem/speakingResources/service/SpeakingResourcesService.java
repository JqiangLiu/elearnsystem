package com.example.elearnsystem.speakingResources.service;

import com.example.elearnsystem.common.util.BeanUtils;
import com.example.elearnsystem.common.util.DownLoadFile;
import com.example.elearnsystem.speakingResources.domain.SpeakingResource;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceUpdateDTO;
import com.example.elearnsystem.speakingResources.repository.SpeakingResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class SpeakingResourcesService implements ISpeakingResourcesService{

    @Autowired
    private SpeakingResourcesRepository speakingResourcesRepository;

    @Override
    public void saveOne(SpeakingResource speakingResource) {
            SpeakingResource entity = new SpeakingResource();
            BeanUtils.copyProperties(speakingResource, entity);
            speakingResourcesRepository.save(entity);
    }

    @Override
    public void saveAll(List<SpeakingResource> speakingResource) {
            List<SpeakingResource> lists = new ArrayList<>();
            for (SpeakingResource List: speakingResource) {
                SpeakingResource entity = new SpeakingResource();
                BeanUtils.copyProperties(List,entity);
                lists.add(entity);
            }
            speakingResourcesRepository.saveAll(lists);
    }

    @Override
    public SpeakingResourceDTO findOne(Long id) {
        SpeakingResourceDTO temp = new SpeakingResourceDTO();
        SpeakingResource entity = speakingResourcesRepository.findById(id).get();
        BeanUtils.copyProperties(entity,temp);
        return temp;
    }

    @Override
    public List<SpeakingResourceDTO> findAll(Pageable pageable, String resourcesCategory, Boolean inSystem) {
        List<SpeakingResourceDTO> DTOList = new ArrayList<>();
        Page<SpeakingResource> res;
        List<SpeakingResource> list;
        long sum = 0;
        if (!(resourcesCategory == null) || !(inSystem == null)){
            if (!(inSystem == null) && !(resourcesCategory == null)){
                res = speakingResourcesRepository.findAllByResourcesCategoryAndInSystem(resourcesCategory,inSystem,pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }else if(!(inSystem != null) && !(resourcesCategory == null)){
                res = speakingResourcesRepository.findAllByResourcesCategory(resourcesCategory,pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }else{
                res = speakingResourcesRepository.findAllByInSystem(inSystem,pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }
        }else {
            res =  speakingResourcesRepository.findAll(pageable);
            list = res.getContent();
            sum = res.getTotalElements();
        }
        for (SpeakingResource s: list
             ) {
            SpeakingResourceDTO temp = new SpeakingResourceDTO();
            BeanUtils.copyProperties(s,temp);
            temp.setSum(sum);
            DTOList.add(temp);
        }
       return DTOList;
    }


    @Override
    public void update(SpeakingResourceUpdateDTO entity) {
        SpeakingResource speakingResource = speakingResourcesRepository.findById(entity.getId()).get();
        BeanUtils.copyProperties(entity,speakingResource);
//        SpeakingResource entity = speakingResourcesRepository.findById(id).get();
//        BeanUtils.copyProperties(speakingResource,entity);
        speakingResourcesRepository.save(speakingResource);
    }

    @Override
    public void publishAll(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        List<SpeakingResource> lists = speakingResourcesRepository.findAllById(longs);
        for (SpeakingResource list: lists) {
            String networkUrl = list.getResourcesNetworkUrl();
            String fileName = list.getId().toString() + ".mp3";
            try {
                DownLoadFile.downLoadFromUrl(networkUrl,fileName,"E://eSystemResources/speaking_resources_mp3");
                list.setInSystem(true);
                list.setResourcesLocalUrl("E://eSystemResources/speaking_resources_mp3/"+fileName);
            }catch (Exception e){
                System.out.println(e);
            }
        }
        speakingResourcesRepository.saveAll(lists);
    }

    @Override
    public void deleteById(Long id) {
        speakingResourcesRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Long[] ids) {
            for (Long id:ids) {
                File file = new File("E://eSystemResources/speaking_resources_mp3/" + id + ".mp3");
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
            speakingResourcesRepository.deleteAll(ids);
    }
}
