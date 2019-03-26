//package com.example.elearnsystem.newsResources.repository;
//
//import com.example.elearnsystem.newsResources.domain.NewsResource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.transaction.annotation.Transactional;
//
//public interface NewsResourcesRepository extends JpaRepository<NewsResource,Long>, JpaSpecificationExecutor<NewsResource> {
//    @Modifying
//    @Transactional
////    @Query("delete from NewsResource r where r.id in (?1)")
//    void deleteAll(Long[] ids);
//
//    Page<NewsResource> findAllByResourcesCategory(String category, Pageable pageable);
//    Page<NewsResource> findAllByInSystem(Boolean inSystem, Pageable pageable);
//    Page<NewsResource> findAllByResourcesCategoryAndInSystem(String category, Boolean inSystem, Pageable pageable);
//}
