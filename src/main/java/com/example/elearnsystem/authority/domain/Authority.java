package com.example.elearnsystem.authority.domain;

import com.example.elearnsystem.user.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "t_authority")
public class Authority {
    private Long id;
    private String userName; // 账号
    private String password;
    private Boolean download; // 下载权限
    private String role; // 用户角色
    private User userMsg; // 用户信息表

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getDownload() {
        return download;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDownload(Boolean download) {
        this.download = download;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToOne(cascade = CascadeType.ALL,optional = false)
    @PrimaryKeyJoinColumn
    public User getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(User userMsg) {
        this.userMsg = userMsg;
    }
}
