package com.example.elearnsystem.userRecorder.domain.dto;

import com.example.elearnsystem.userRecorder.domain.UserRecorder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRecorderDTO {
    private Long id;
    private String recorderPath;
    private String resourcesTitle;
    private Double grade;
    private String updateTime;
    private String resourcesCategory;
    private String userName;
    private Long speakingId;
    private Double recorderSize;
    private Double allSize;
    private Long sum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecorderPath() {
        return recorderPath;
    }

    public void setRecorderPath(String recorderPath) {
        this.recorderPath = recorderPath;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getSpeakingId() {
        return speakingId;
    }

    public void setSpeakingId(Long speakingId) {
        this.speakingId = speakingId;
    }

    public Double getRecorderSize() {
        return recorderSize;
    }

    public void setRecorderSize(Double recorderSize) {
        this.recorderSize = recorderSize;
    }

    public Double getAllSize() {
        return allSize;
    }

    public void setAllSize(Double allSize) {
        this.allSize = allSize;
    }

    public String getResourcesCategory() {
        return resourcesCategory;
    }

    public void setResourcesCategory(String resourcesCategory) {
        this.resourcesCategory = resourcesCategory;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getResourcesTitle() {
        return resourcesTitle;
    }

    public void setResourcesTitle(String resourcesTitle) {
        this.resourcesTitle = resourcesTitle;
    }

    public static Specification<UserRecorder> findSearch(SearchTerm searchTerm) {
        return new Specification<UserRecorder>() {
            @Override
            public Predicate toPredicate(Root<UserRecorder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                if (null != searchTerm.getResourcesCategory() && !searchTerm.getResourcesCategory().equals("")) {
                    predicate.add(criteriaBuilder.equal(root.get("resourcesCategory").as(String.class),
                            searchTerm.getResourcesCategory()));
                }

                if (null != searchTerm.getUserName() && !searchTerm.getUserName().equals("")) {
                    predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("userName").as(String.class),
                            searchTerm.getUserName()));
                }
                if (null != searchTerm.getUpdateTime() && !searchTerm.getUpdateTime().equals("")) {
                    predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("updateTime").as(String.class),
                            searchTerm.getUpdateTime()));
                }
                if (null != searchTerm.getMinGrade() && !searchTerm.getMinGrade().equals("")) {
                    predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("minGrade").as(Double.class),
                            searchTerm.getMinGrade()));
                }
                if (null != searchTerm.getMaxGrade() && !searchTerm.getMaxGrade().equals("")) {
                    predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxGrade").as(Double.class),
                            searchTerm.getMaxGrade()));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
