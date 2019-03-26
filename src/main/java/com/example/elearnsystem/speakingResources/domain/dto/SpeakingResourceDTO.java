package com.example.elearnsystem.speakingResources.domain.dto;

public class SpeakingResourceDTO {
    private  Long id;
    private String resourcesTitle;  //资源标题
    private String resourcesCategory; //资源分类
    private String resourcesText; // 资源正文
    private String resourcesTranslation_text; // 资源译文
    private String resourcesNetworkUrl; // 音频网络URL链接
    private Boolean inSystem; // 资源存库标志
    private Long sum; //总记录数
    private int updateSum; // 新抓取的总记录数


    public Long getId() {
        return id;
    }

    public String getResourcesTitle() {
        return resourcesTitle;
    }

    public String getResourcesCategory() {
        return resourcesCategory;
    }

    public String getResourcesText() {
        return resourcesText;
    }

    public String getResourcesTranslation_text() {
        return resourcesTranslation_text;
    }

    public String getResourcesNetworkUrl() {
        return resourcesNetworkUrl;
    }

    public Boolean getInSystem() {
        return inSystem;
    }

    public Long getSum() {
        return sum;
    }

    public int getUpdateSum() {
        return updateSum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setResourcesTitle(String resourcesTitle) {
        this.resourcesTitle = resourcesTitle;
    }

    public void setResourcesCategory(String resourcesCategory) {
        this.resourcesCategory = resourcesCategory;
    }

    public void setResourcesText(String resourcesText) {
        this.resourcesText = resourcesText;
    }

    public void setResourcesTranslation_text(String resourcesTranslation_text) {
        this.resourcesTranslation_text = resourcesTranslation_text;
    }

    public void setResourcesNetworkUrl(String resourcesNetworkUrl) {
        this.resourcesNetworkUrl = resourcesNetworkUrl;
    }

    public void setInSystem(Boolean inSystem) {
        this.inSystem = inSystem;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public void setUpdateSum(int updateSum) {
        this.updateSum = updateSum;
    }
}
