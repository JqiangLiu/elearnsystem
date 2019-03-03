package com.example.elearnsystem.speaking_resources.service;

import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.domain.dto.SpeakingResourceDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;
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
    public List<SpeakingResourceDTO> findAll(Specification<SpeakingResource> spec, Pageable pageable);
    //修改
    public void update(SpeakingResource speakingResource);

    //删除
    public void deleteById(Long id);
    public void deleteAll(Long[] ids);
}
