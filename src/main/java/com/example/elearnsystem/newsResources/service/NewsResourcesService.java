//package com.example.elearnsystem.newsResources.service;
//
//import com.example.elearnsystem.common.util.BeanUtils;
//import com.example.elearnsystem.newsResources.domain.NewsResource;
//import com.example.elearnsystem.newsResources.domain.dto.NewsResourceDTO;
//import com.example.elearnsystem.newsResources.repository.NewsResourcesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NewsResourcesService implements INewsResourcesService{
//    @Autowired
//    private NewsResourcesRepository newsResourcesRepository;
//    @Override
//    public void saveOne(NewsResource newsResource) {
//        NewsResource entity = new NewsResource();
//        BeanUtils.copyProperties(newsResource, entity);
//        newsResourcesRepository.save(entity);
//    }
//
//    @Override
//    public void saveAll(List<NewsResource> newsResource) {
//        List<NewsResource> lists = new ArrayList<>();
//        for (NewsResource List: newsResource) {
//            NewsResource entity = new NewsResource();
//            BeanUtils.copyProperties(List,entity);
//            lists.add(entity);
//        }
//        newsResourcesRepository.saveAll(lists);
//    }
//
//    @Override
//    public List<NewsResourceDTO> findAll(Pageable pageable, String resourcesCategory, Boolean inSystem) {
//        List<NewsResourceDTO> DTOList = new ArrayList<>();
//        List<NewsResource> list;
//        if (!(resourcesCategory == null) || !(inSystem == null)){
//            if (!(inSystem == null) && !(resourcesCategory == null)){
//                list = newsResourcesRepository.findAllByResourcesCategoryAndInSystem(resourcesCategory,inSystem,pageable).getContent();
//            }else if(!(inSystem != null) && !(resourcesCategory == null)){
//                list = newsResourcesRepository.findAllByResourcesCategory(resourcesCategory,pageable).getContent();
//            }else{
//                list = newsResourcesRepository.findAllByInSystem(inSystem,pageable).getContent();
//            }
//        }else {
//            list =  newsResourcesRepository.findAll(pageable).getContent();
//        }
//        for (NewsResource s: list
//        ) {
//            NewsResourceDTO temp = new NewsResourceDTO();
//            BeanUtils.copyProperties(s,temp);
//            DTOList.add(temp);
//        }
//        return DTOList;
//    }
//
//    @Override
//    public void update(NewsResource newsResource) {
//        newsResourcesRepository.save(newsResource);
//    }
//
//    @Override
//    public void deleteAll(Long[] ids) {
//        newsResourcesRepository.deleteAll(ids);
//    }
//}
