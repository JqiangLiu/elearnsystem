package com.example.elearnsystem.user.controller;

import com.example.elearnsystem.common.util.SessionUtils;
import com.example.elearnsystem.user.domain.User;
import com.example.elearnsystem.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userMsg")
public class UserController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/find")
    public User find(HttpSession session){
        Long id = SessionUtils.getUser(session).getId();
        return iUserService.find(id);
    }

    @PostMapping
    public User update(@RequestBody User user,HttpSession session){
        Long id = SessionUtils.getUser(session).getId();
        user.setId(id);
        return iUserService.update(user);
    }
}
