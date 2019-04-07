package com.example.elearnsystem.authority.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_authority")
public class Authority {
    private Long id;
    private String accountNumber; // 账号
    private String password;
    private Boolean download; // 下载权限

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getDownload() {
        return download;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDownload(Boolean download) {
        this.download = download;
    }
}
