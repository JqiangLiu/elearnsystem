package com.example.elearnsystem.speaking_resources.repository;

import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.awt.print.Pageable;

public interface SpeakingResourcesRepository extends JpaRepository<SpeakingResource,Long>, JpaSpecificationExecutor<SpeakingResource> {

}
