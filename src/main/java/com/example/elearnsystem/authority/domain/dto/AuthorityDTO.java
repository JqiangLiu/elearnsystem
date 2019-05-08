package com.example.elearnsystem.authority.domain.dto;

import com.example.elearnsystem.user.domain.User;

public class AuthorityDTO {
    private Long id;
    private String userName; // 账号
    private Boolean download; // 下载权限
    private String role; // 用户角色
    private User userMsg; // 用户信息表
    private long sum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getDownload() {
        return download;
    }

    public void setDownload(Boolean download) {
        this.download = download;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(User userMsg) {
        this.userMsg = userMsg;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
