package com.liyu.cms.data.core.domain;

/**
 * Created by liyu on 2018/3/15.
 */
public class RolePrivilege {

    private Integer id;

    private Integer roleId;

    private Integer pageId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }
}
