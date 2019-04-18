package com.example.elearnsystem.newsResources.service;

import com.example.elearnsystem.common.util.BeanUtils;
import com.example.elearnsystem.newsResources.domain.NewsResource;
import com.example.elearnsystem.newsResources.domain.dto.NewsResourceDTO;
import com.example.elearnsystem.newsResources.domain.dto.NewsResourceUpdateDTO;
import com.example.elearnsystem.newsResources.repository.NewsResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NewsResourcesService implements INewsResourcesService{
    @Autowired
    private NewsResourcesRepository newsResourcesRepository;

    @Override
    public void saveOne(NewsResource newsResource) {
        NewsResource entity = new NewsResource();
        BeanUtils.copyProperties(newsResource, entity);
        newsResourcesRepository.save(entity);
    }

    @Override
    public void saveAll(List<NewsResource> newsResource) {
        List<NewsResource> lists = new ArrayList<>();
        for (NewsResource List: newsResource) {
            NewsResource entity = new NewsResource();
            BeanUtils.copyProperties(List,entity);
            lists.add(entity);
        }
        newsResourcesRepository.saveAll(lists);
    }

    @Override
    public NewsResourceDTO findOne(Long id) {
        NewsResourceDTO temp = new NewsResourceDTO();
        NewsResource entity = newsResourcesRepository.findById(id).get();
        BeanUtils.copyProperties(entity,temp);
        return temp;
    }

    @Override
    public List<NewsResourceDTO> findAll(Pageable pageable, String resourcesCategory, Boolean inSystem) {
        List<NewsResourceDTO> DTOList = new ArrayList<>();
        Page<NewsResource> res;
        List<NewsResource> list;
        long sum = 0;
        if (!(resourcesCategory == null) || !(inSystem == null)){
            if (!(inSystem == null) && !(resourcesCategory == null)){
                res = newsResourcesRepository.findAllByResourcesCategoryAndInSystem(resourcesCategory,inSystem,pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }else if(!(inSystem != null) && !(resourcesCategory == null)){
                res = newsResourcesRepository.findAllByResourcesCategory(resourcesCategory,pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }else{
                res = newsResourcesRepository.findAllByInSystem(inSystem,pageable);
                list = res.getContent();
                sum = res.getTotalElements();
            }
        }else {
            res =  newsResourcesRepository.findAll(pageable);
            list = res.getContent();
            sum = res.getTotalElements();
        }
        for (NewsResource s: list
        ) {
            NewsResourceDTO temp = new NewsResourceDTO();
            BeanUtils.copyProperties(s,temp);
            temp.setSum(sum);
            DTOList.add(temp);
        }
        return DTOList;
    }


    @Override
    public void update(NewsResourceUpdateDTO entity) {
        NewsResource newsResource = newsResourcesRepository.findById(entity.getId()).get();
        BeanUtils.copyProperties(entity,newsResource);
        newsResourcesRepository.save(newsResource);
    }

    @Override
    public void publishAll(Long[] ids) {
        newsResourcesRepository.publishAll(ids);
    }

    @Override
    public void deleteById(Long id) {
        newsResourcesRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Long[] ids) {
        newsResourcesRepository.deleteAll(ids);
    }
}
