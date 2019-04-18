package com.example.elearnsystem.authority.controller;

import com.example.elearnsystem.authority.domain.Authority;
import com.example.elearnsystem.authority.domain.MyResult;
import com.example.elearnsystem.authority.service.IAuthorityService;
import com.example.elearnsystem.common.util.MD5Utils;
import com.example.elearnsystem.common.util.SessionUtils;
import com.example.elearnsystem.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class AuthorityController {
    @Autowired
    private IAuthorityService iAuthorityService;

    @PostMapping("/register")
    public String register(@RequestBody Authority authority){
        try{
            String password = MD5Utils.MD5Encode(authority.getPassword(),"");
            authority.setPassword(password);
            User user = new User();
            authority.setDownload(true);
            authority.setRole("user");
            authority.setUserMsg(user);
            iAuthorityService.saveOne(authority);
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }

    @GetMapping("/getUserName")
    public int getUserName(@RequestParam(name="userName") String userName){
        if (iAuthorityService.getUserName(userName) == null){
            return 1;
        }else {
            return 0;
        }
    }

    @PostMapping("/login")
    public MyResult login(@RequestBody Authority authority, HttpSession session){
        String token = UUID.randomUUID().toString().replace("-", "");
        String userName = authority.getUserName();
        String password = MD5Utils.MD5Encode(authority.getPassword(),"");
        Authority entity = iAuthorityService.login(userName);
        MyResult myResult = null;
        if (entity != null && entity.getPassword().equals(password)){
            SessionUtils.setUser(authority,session);
             myResult = new MyResult(entity.getUserMsg(),token,"success");
        }else{
            myResult = new MyResult(entity.getUserMsg(),"","false");
        }
        return myResult;
    }
}
