package com.example.elearnsystem.newsResources.service;

import com.example.elearnsystem.newsResources.domain.NewsResource;
import com.example.elearnsystem.newsResources.domain.dto.NewsResourceDTO;
import com.example.elearnsystem.newsResources.domain.dto.NewsResourceUpdateDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewsResourcesService {
    //保存
    public void saveOne(NewsResource newsResource);
    public void saveAll(List<NewsResource> newsResource);

    //查询
    /*根据分类查询所有*/
    public NewsResourceDTO findOne(Long id);
    public List<NewsResourceDTO> findAll(Pageable pageable, String category, Boolean inSystem);
    //修改
    public void update(NewsResourceUpdateDTO entity);
    public void publishAll(Long[] ids);
    //删除
    public void deleteById(Long id);
    public void deleteAll(Long[] ids);
}
