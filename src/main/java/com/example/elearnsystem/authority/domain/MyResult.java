package com.example.elearnsystem.authority.domain;

import com.example.elearnsystem.user.domain.User;

import java.util.Map;

public class MyResult {
    private User userInfo;
    private String token;
    private String message;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyResult(User userInfo, String token, String message) {
        this.userInfo = userInfo;
        this.token = token;
        this.message = message;
    }
}
