package com.example.elearnsystem.authority.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_manager")
public class Manager {
    private Long id;
    private String userName; // 账号
    private String password;
    private String role; // 用户角色
    private String name; // 昵称

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }
}
