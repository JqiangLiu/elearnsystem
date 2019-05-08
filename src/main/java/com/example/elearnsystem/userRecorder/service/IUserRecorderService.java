package com.example.elearnsystem.userRecorder.service;

import com.example.elearnsystem.userRecorder.domain.UserRecorder;
import com.example.elearnsystem.userRecorder.domain.dto.UserRecorderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface IUserRecorderService {
    public void saveOne(UserRecorder userRecorder);

    public Double uploadRecorder(MultipartFile file, Long id, String extension, HttpSession session);

    public Optional<UserRecorder> findOne(String userName, Long speakingId);

    public List<UserRecorderDTO> findByResourcesCategory(String resourcesCategory,Pageable pageable,String userName);

    public List<UserRecorderDTO> findAll(Specification<UserRecorder> specification, Pageable pageable);

    public void delete(Long[] id);
}
