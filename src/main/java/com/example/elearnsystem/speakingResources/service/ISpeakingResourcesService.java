package com.example.elearnsystem.speakingResources.service;

import com.example.elearnsystem.speakingResources.domain.SpeakingResource;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speakingResources.domain.dto.SpeakingResourceUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface ISpeakingResourcesService {
    //保存
    public void saveOne(SpeakingResource speakingResource);
    public void saveAll(List<SpeakingResource> speakingResource);

    //查询
    /*根据分类查询所有*/
    public List<SpeakingResourceDTO> findAll(Pageable pageable, String category, Boolean inSystem);
    //修改
    public void update(SpeakingResourceUpdateDTO entity);
    public void publishAll(Long[] ids);
    //删除
    public void deleteById(Long id);
    public void deleteAll(Long[] ids);
}
