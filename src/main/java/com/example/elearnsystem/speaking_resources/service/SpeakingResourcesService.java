package com.example.elearnsystem.speaking_resources.service;

import com.example.elearnsystem.common.util.BeanUtils;
import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speaking_resources.repository.SpeakingResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


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
    public List<SpeakingResourceDTO> findAll(Pageable pageable, String resourcesCategory, Boolean inSystem) {
        List<SpeakingResourceDTO> DTOList = new ArrayList<>();
        List<SpeakingResource> list;
        if (!(resourcesCategory == null) || !(inSystem == null)){
            if (!(inSystem == null) && !(resourcesCategory == null)){
                list = speakingResourcesRepository.findAllByResourcesCategoryAndInSystem(resourcesCategory,inSystem,pageable).getContent();
            }else if(!(inSystem != null) && !(resourcesCategory == null)){
                list = speakingResourcesRepository.findAllByResourcesCategory(resourcesCategory,pageable).getContent();
            }else{
                list = speakingResourcesRepository.findAllByInSystem(inSystem,pageable).getContent();
            }
        }else {
            list =  speakingResourcesRepository.findAll(pageable).getContent();
        }
        for (SpeakingResource s: list
             ) {
            SpeakingResourceDTO temp = new SpeakingResourceDTO();
            BeanUtils.copyProperties(s,temp);
            DTOList.add(temp);
        }
       return DTOList;
    }

    @Override
    public List<SpeakingResourceDTO> findAll(Specification<SpeakingResource> spec, Pageable pageable) {
//            return speakingResourcesRepository.findAll(spec,pageable);
            return null;
    }

    @Override
    public void update(SpeakingResource speakingResource) {
//        SpeakingResource entity = speakingResourcesRepository.findById(id).get();
//        BeanUtils.copyProperties(speakingResource,entity);
        speakingResourcesRepository.save(speakingResource);
    }

    @Override
    public void deleteById(Long id) {
        speakingResourcesRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Long[] ids) {
            speakingResourcesRepository.deleteAll(ids);
    }
}
