package com.example.elearnsystem.user.domain;

import com.example.elearnsystem.authority.domain.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
public class User {
    private Long id;
    private String name; // 用户名称
    private String photo; // 头像
    private String occupation; // 职业
    private String phoneNumber; // 手机号
    private String email;
    private String motto; // 学习格言
    private Authority authority;
//    @ManyToMany(targetEntity = SpeakingResource.class)
    // 使用JoinTabl来描述中间表，并描述中间表中外键与User,SpeakingResource的映射关系
    // joinColumns它是用来描述user与中间表中的映射关系
    // inverseJoinColums它是用来描述SpeakingResource与中间表中的映射关系
//    @JoinTable(name = "user_speakingResources",
//    joinColumns = {@JoinColumn(name = "")})

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMotto() {
        return motto;
    }

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "userMsg",optional = false)
    @JsonIgnore(value= true)
    public Authority getAuthority() {
        return authority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
