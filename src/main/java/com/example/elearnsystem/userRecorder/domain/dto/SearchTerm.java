package com.example.elearnsystem.userRecorder.domain.dto;

import java.util.Date;

public class SearchTerm {
    private Double minGrade;
    private Double maxGrade;
    private String updateTime;
    private String resourcesCategory;
    private String userName;


    public Double getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(Double minGrade) {
        this.minGrade = minGrade;
    }

    public Double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getResourcesCategory() {
        return resourcesCategory;
    }

    public void setResourcesCategory(String resourcesCategory) {
        this.resourcesCategory = resourcesCategory;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
