package com.liyu.cms.data.core.domain;

import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
public class MenuOne {

    private Integer id;

    private String routeName;

    private String title;

    private String icon;

    private Integer sortIndex;

    private List<MenuPage> pageList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<MenuPage> getPageList() {
        return pageList;
    }

    public void setPageList(List<MenuPage> pageList) {
        this.pageList = pageList;
    }
}
