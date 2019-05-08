package com.example.elearnsystem.authority.domain;


public class MyResult {
    private Authority userInfo;
    private String token;
    private String message;

    public Authority getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Authority userInfo) {
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

    public MyResult(Authority userInfo, String token, String message) {
        this.userInfo = userInfo;
        this.token = token;
        this.message = message;
    }
}
