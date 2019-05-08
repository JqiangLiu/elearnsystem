package com.example.elearnsystem.examResources.repository;

import com.example.elearnsystem.examResources.domain.ExamResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface ExamResourcesRepository {
    @Repository
    public interface SpeakingResourcesRepository extends JpaRepository<ExamResources, Long>, JpaSpecificationExecutor<ExamResources> {

    }
}
