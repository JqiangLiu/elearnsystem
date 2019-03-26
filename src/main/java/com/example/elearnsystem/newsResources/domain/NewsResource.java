package com.example.elearnsystem.newsResources.domain;

import javax.persistence.*;

@Entity
@Table(name="t_NewsResource")
public class NewsResource {
    private Long id;
    private String resourcesTitle;
    private String resourcesText;
    private String resourcesTransation_text;
    private String resourcesCategory;
    private String resourcesImg;
    private String resourcesParentUrl; // 父级URL，用于去重
    private String resourcesDate; // 新闻日期
    private String resourcesCite; // 资源来源
    private String inSystem; // 发布标识

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String getResourcesTitle() {
        return resourcesTitle;
    }

    public String getResourcesText() {
        return resourcesText;
    }

    public String getResourcesTransation_text() {
        return resourcesTransation_text;
    }

    public String getResourcesCategory() {
        return resourcesCategory;
    }

    public String getResourcesImg() {
        return resourcesImg;
    }

    public String getResourcesParentUrl() {
        return resourcesParentUrl;
    }

    public String getResourcesDate() {
        return resourcesDate;
    }

    public String getResourcesCite() {
        return resourcesCite;
    }

    public String getInSystem() {
        return inSystem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setResourcesTitle(String resourcesTitle) {
        this.resourcesTitle = resourcesTitle;
    }

    public void setResourcesText(String resourcesText) {
        this.resourcesText = resourcesText;
    }

    public void setResourcesTransation_text(String resourcesTransation_text) {
        this.resourcesTransation_text = resourcesTransation_text;
    }

    public void setResourcesCategory(String resourcesCategory) {
        this.resourcesCategory = resourcesCategory;
    }

    public void setResourcesImg(String resourcesImg) {
        this.resourcesImg = resourcesImg;
    }

    public void setResourcesParentUrl(String resourcesParentUrl) {
        this.resourcesParentUrl = resourcesParentUrl;
    }

    public void setResourcesDate(String resourcesDate) {
        this.resourcesDate = resourcesDate;
    }

    public void setResourcesCite(String resourcesCite) {
        this.resourcesCite = resourcesCite;
    }

    public void setInSystem(String inSystem) {
        this.inSystem = inSystem;
    }
}
