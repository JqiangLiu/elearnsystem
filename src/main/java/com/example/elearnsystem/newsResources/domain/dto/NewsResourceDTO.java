package com.example.elearnsystem.newsResources.domain.dto;

public class NewsResourceDTO {
    private Long id;
    private String resourcesTitle;
    private String resourcesText;
    private String resourcesTranslation_text;
    private String resourcesCategory;
    private String resourcesImg;
    private String resourcesParentUrl; // 父级URL，用于去重
    private String resourcesDate; // 新闻日期
    private String resourcesCite; // 资源来源
    private Boolean inSystem; // 发布标识
    private Long sum; //总记录数
    private int updateSum; // 新抓取的总记录数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourcesTitle() {
        return resourcesTitle;
    }

    public void setResourcesTitle(String resourcesTitle) {
        this.resourcesTitle = resourcesTitle;
    }

    public String getResourcesText() {
        return resourcesText;
    }

    public void setResourcesText(String resourcesText) {
        this.resourcesText = resourcesText;
    }

    public String getResourcesTranslation_text() {
        return resourcesTranslation_text;
    }

    public void setResourcesTranslation_text(String resourcesTranslation_text) {
        this.resourcesTranslation_text = resourcesTranslation_text;
    }

    public String getResourcesCategory() {
        return resourcesCategory;
    }

    public void setResourcesCategory(String resourcesCategory) {
        this.resourcesCategory = resourcesCategory;
    }

    public String getResourcesImg() {
        return resourcesImg;
    }

    public void setResourcesImg(String resourcesImg) {
        this.resourcesImg = resourcesImg;
    }

    public String getResourcesParentUrl() {
        return resourcesParentUrl;
    }

    public void setResourcesParentUrl(String resourcesParentUrl) {
        this.resourcesParentUrl = resourcesParentUrl;
    }

    public String getResourcesDate() {
        return resourcesDate;
    }

    public void setResourcesDate(String resourcesDate) {
        this.resourcesDate = resourcesDate;
    }

    public String getResourcesCite() {
        return resourcesCite;
    }

    public void setResourcesCite(String resourcesCite) {
        this.resourcesCite = resourcesCite;
    }

    public Boolean getInSystem() {
        return inSystem;
    }

    public void setInSystem(Boolean inSystem) {
        this.inSystem = inSystem;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public int getUpdateSum() {
        return updateSum;
    }

    public void setUpdateSum(int updateSum) {
        this.updateSum = updateSum;
    }
}
