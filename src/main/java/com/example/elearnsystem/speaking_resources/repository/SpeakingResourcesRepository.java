package com.example.elearnsystem.speaking_resources.repository;

import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SpeakingResourcesRepository extends JpaRepository<SpeakingResource,Long>, JpaSpecificationExecutor<SpeakingResource> {

    @Modifying
    @Transactional
    @Query("delete from SpeakingResource r where r.id in (?1)")
    void deleteAll(Long[] ids);

    Page<SpeakingResource> findAllByResourcesCategory(String category,Pageable pageable);
    Page<SpeakingResource> findAllByInSystem(Boolean inSystem, Pageable pageable);
    Page<SpeakingResource> findAllByResourcesCategoryAndInSystem(String category, Boolean inSystem, Pageable pageable);
}
