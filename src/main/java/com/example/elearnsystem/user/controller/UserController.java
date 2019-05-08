package com.example.elearnsystem.user.controller;

import com.example.elearnsystem.common.util.Base64ToImg;
import com.example.elearnsystem.common.util.SessionUtils;
import com.example.elearnsystem.user.domain.User;
import com.example.elearnsystem.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;

@RestController
@RequestMapping("/userMsg")
public class UserController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/find")
    public User find(HttpSession session) {
        Long id = SessionUtils.getUser(session).getId();
        return iUserService.find(id);
    }

    @PostMapping("/updateInfo")
    public User update(@RequestBody User user, HttpSession session) {
        Long id = Long.valueOf(String.valueOf(session.getAttribute("id")));
        user.setId(id);
        String oldAvator = "E://eSystemResources" + iUserService.findAvator(id);
        File avator = new File(oldAvator);
        if (avator.exists()) {
            avator.delete();
        }
        double random = Math.random();
        String avatorImgPath = "E://eSystemResources/headImg/" + session.getAttribute("useName") + "Avator"+random+".jpg";
        String avatorImgUrl = "/headImg/"+session.getAttribute("useName") + "Avator"+random+".jpg";
        String imgStr = user.getAvatorImgPath().replace("data:image/png;base64,", "");
        if (Base64ToImg.base64ToImg(imgStr, avatorImgPath) != null) {
            user.setAvatorImgPath(avatorImgUrl);
        }
        return iUserService.update(user);
    }
}
