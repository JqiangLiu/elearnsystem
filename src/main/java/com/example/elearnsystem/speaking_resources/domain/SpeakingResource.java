package com.example.elearnsystem.speaking_resources.domain;

import javax.persistence.*;

@Entity
@Table(name="t_speakingResources")
public class SpeakingResource {
    private  Long id;
    private String resourcesTitle;  //资源标题
    private String resourcesCategory; //资源分类
    private String resourcesText; // 资源正文
    private String resourcesTranslation_text; // 资源译文
    private String resourcesNetworkUrl; // 音频网络URL链接
    private String resourcesLocalUrl; // 音频本地URL链接
    private String resourcesNetworkPic; // 音频图片
    private String resourcesLocalPic; // 音频本地图片
    private boolean inSystenm; // 资源存库标志

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    public String getResourcesNetworkPic() {
        return resourcesNetworkPic;
    }

    public String getResourcesLocalPic() {
        return resourcesLocalPic;
    }

    public boolean isInSystenm() {
        return inSystenm;
    }
    /*------------------------------------set函数----------------------------*/

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

    public void setResourcesNetworkPic(String resourcesNetworkPic) {
        this.resourcesNetworkPic = resourcesNetworkPic;
    }

    public void setResourcesLocalPic(String resourcesLocalPic) {
        this.resourcesLocalPic = resourcesLocalPic;
    }

    public void setInSystenm(boolean inSystenm) {
        this.inSystenm = inSystenm;
    }
}
