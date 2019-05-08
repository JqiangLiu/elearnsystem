package com.example.elearnsystem.userRecorder.controller;

import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.userRecorder.domain.dto.SearchTerm;
import com.example.elearnsystem.userRecorder.domain.dto.UserRecorderDTO;
import com.example.elearnsystem.userRecorder.service.UserRecorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/recorder")
public class UserRecorderController {

    @Autowired
    UserRecorderService userRecorderService;

    @PostMapping("/uploadRecorder")
    public Double uploadRecorder(@RequestParam("file") MultipartFile file, @RequestParam("id") Long speakingId, @RequestParam("extension") String extension, HttpSession session) {
        return userRecorderService.uploadRecorder(file, speakingId, extension, session);
    }

    @PostMapping("/findAllRecorder")
    public List<UserRecorderDTO> findAll(@RequestBody SearchTerm searchTerm, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit) {
        return utilsFind(page, limit, searchTerm);
    }

    @GetMapping("/userRecorder")
    public List<UserRecorderDTO> findUserRecorder(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit,String resourcesCategory,HttpSession session){
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("id");
        pageReq.setDir("ASC");
        if (resourcesCategory != null && resourcesCategory.equals("all")) {
            resourcesCategory = null;
        }
        return userRecorderService.findByResourcesCategory(resourcesCategory,pageReq.getPageable(),String.valueOf(session.getAttribute("userName")));
    }

    @DeleteMapping
    public List<UserRecorderDTO> deleteAll(@RequestParam(value = "ids[]") Long[] ids, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, @RequestBody SearchTerm searchTerm) {
        userRecorderService.delete(ids);
        return utilsFind(page, limit, searchTerm);
    }

    /**
     * 封装查询代码
     */
    public List<UserRecorderDTO> utilsFind(int page, int limit, SearchTerm searchTerm) {
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("id");
        pageReq.setDir("ASC");
        return userRecorderService.findAll(UserRecorderDTO.findSearch(searchTerm), pageReq.getPageable());
    }
}
