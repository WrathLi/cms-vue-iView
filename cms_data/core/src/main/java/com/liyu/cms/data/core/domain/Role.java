package com.liyu.cms.data.core.domain;

import java.util.Date;

/**
 * Created by liyu on 2018/3/15.
 */
public class Role {

    private Integer id;

    private String name;

    private Boolean beSuper;

    private String description;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBeSuper() {
        return beSuper;
    }

    public void setBeSuper(Boolean beSuper) {
        this.beSuper = beSuper;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
