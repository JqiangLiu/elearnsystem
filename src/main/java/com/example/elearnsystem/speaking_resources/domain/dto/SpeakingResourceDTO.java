package com.example.elearnsystem.speaking_resources.domain.dto;

import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpeakingResourceDTO {
    private  Long id;
    private String resourcesTitle;  //资源标题
    private String resourcesCategory; //资源分类
    private String resourcesText; // 资源正文
    private String resourcesTranslation_text; // 资源译文
    private String resourcesNetworkUrl; // 音频网络URL链接
    private String resourcesLocalUrl; // 音频本地URL链接
    private Boolean inSystem; // 资源存库标志


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

    public String getResourcesLocalUrl() {
        return resourcesLocalUrl;
    }

    public Boolean getInSystem() {
        return inSystem;
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

    public void setResourcesLocalUrl(String resourcesLocalUrl) {
        this.resourcesLocalUrl = resourcesLocalUrl;
    }

    public void setInSystem(Boolean inSystem) {
        this.inSystem = inSystem;
    }

}
