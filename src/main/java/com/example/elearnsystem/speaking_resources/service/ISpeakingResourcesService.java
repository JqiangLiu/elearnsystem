package com.example.elearnsystem.speaking_resources.service;

import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.domain.TemporaryResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ISpeakingResourcesService {
    //保存
    public void saveOne(SpeakingResource speakingResource);
    public void saveAll(Iterable<SpeakingResource> speakingResources);

    //查询
    /*根据分类查询所有*/
    public Page<SpeakingResource> findAll(String resourcesCategory, Pageable pageable);
    public Page<TemporaryResource> findAllTemporary(String resourcesCategory, Pageable pageable);
    //修改
    public void update(Long id, SpeakingResource speakingResource);
    public void updateTemporary(Long id, TemporaryResource temporaryResource);

    //删除
    public void deleteById(Long id);
    public void deleteAll(Long[] ids);
}
