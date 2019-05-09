package com.liyu.cms.data.core.domain;

/**
 * Created by liyu on 2018/3/15.
 */
public class MenuPage {

    private Integer id;

    private Integer oneId;

    private String routeName;

    private String title;

    private String icon;

    private Integer sortIndex;

    private Boolean hidePage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOneId() {
        return oneId;
    }

    public void setOneId(Integer oneId) {
        this.oneId = oneId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public Boolean getHidePage() {
        return hidePage;
    }

    public void setHidePage(Boolean hidePage) {
        this.hidePage = hidePage;
    }
}
