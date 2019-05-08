package com.example.elearnsystem.userRecorder.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_userRecorder")
public class UserRecorder {
    private Long id;
    private String recorderPath;
    private Double grade;
    private String updateTime;
    private String resourcesCategory;
    private String userName;
    private Long speakingId;
    private String resourcesTitle;
    private Double recorderSize;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getRecorderPath() {
        return recorderPath;
    }

    public Double getGrade() {
        return grade;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public Long getSpeakingId() {
        return speakingId;
    }

    public Double getRecorderSize() {
        return recorderSize;
    }

    public String getResourcesCategory() {
        return resourcesCategory;
    }

    public String getResourcesTitle() {
        return resourcesTitle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecorderPath(String recorderPath) {
        this.recorderPath = recorderPath;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSpeakingId(Long speakingId) {
        this.speakingId = speakingId;
    }

    public void setRecorderSize(Double recorderSize) {
        this.recorderSize = recorderSize;
    }

    public void setResourcesCategory(String resourcesCategory) {
        this.resourcesCategory = resourcesCategory;
    }

    public void setResourcesTitle(String resourcesTitle) {
        this.resourcesTitle = resourcesTitle;
    }
}
