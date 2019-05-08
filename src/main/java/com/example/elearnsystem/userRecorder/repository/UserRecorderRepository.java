package com.example.elearnsystem.userRecorder.repository;

import com.example.elearnsystem.userRecorder.domain.UserRecorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface UserRecorderRepository extends JpaRepository<UserRecorder, Long>, JpaSpecificationExecutor<UserRecorder> {
    public Optional<UserRecorder> findAllByUserNameAndSpeakingId(String userName, Long speakingId);
    public Page<UserRecorder> findAllByResourcesCategory(String resourcesCategory, Pageable pageable);
    public void deleteAllByUserName(List<String> userName);

    @Modifying
    @Transactional
    @Query("delete from UserRecorder r where r.id in (?1)")
    public void deleteAll(Long[] ids);

    @Modifying
    @Transactional
    @Query("select recorderPath from UserRecorder r where r.id in (?1)")
    public List<String> findAllByIds(Long[] ids);

    @Transactional
    @Query("select sum(recorderSize) from UserRecorder r where r.userName = ?1")
    public Double getSumRecorderSize(String userName);

    @Transactional
    @Query("select recorderPath from UserRecorder r where r.userName in (?1)")
    public List<String> getAllUserName(List<String> userName);
}
