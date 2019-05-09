package com.liyu.cms.data.core.service.impl;

import com.liyu.cms.data.core.dao.MenuDao;
import com.liyu.cms.data.core.dao.RoleDao;
import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.Role;
import com.liyu.cms.data.core.domain.RolePrivilege;
import com.liyu.cms.data.core.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
@Service
public class RoleService implements IRoleService{


    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    public Integer getRoleTotalCount(final String searchKey) {
        Integer count = 0;

        try {
            String tmpSearchKey = searchKey;
            if (StringUtils.isEmpty(tmpSearchKey)) {
                tmpSearchKey = null;
            }
            count = this.roleDao.getRoleTotalCount(tmpSearchKey);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return count;
    }

    @Override
    public List<Role> getRolePage(String searchKey, Integer pageIndex, Integer pageSize) {
        List<Role> list = null;
        try {
            String tmpSearchKey = searchKey;
            if (StringUtils.isEmpty(tmpSearchKey)) {
                tmpSearchKey = null;
            }
            Integer dataIndex = pageIndex * pageSize;
            list = this.roleDao.getRolePage(tmpSearchKey, dataIndex, pageSize);
        } catch (Exception ex) {
            ex.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<Role> getRoleAll() {
        List<Role> list = null;
        try {
            list = this.roleDao.getRoleAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public Integer addRole(Role item) {
        if (null == item) {
            return -1;
        }
        Integer ret = 0;
        try {
            String name = item.getName();
            Integer exist = this.roleDao.roleExist(name, 0);
            if (exist > 0) {
                ret = -2;
            } else {
                item.setBeSuper(false);
                item.setCreateTime(new Date());
                this.roleDao.addRole(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer editRole(Role item) {
        if (null == item) {
            return -1;
        }
        Integer ret = 0;
        try {
            String name = item.getName();
            Integer exist = this.roleDao.roleExist(name, item.getId());
            if (exist > 0) {
                ret = -2;
            } else {
                this.roleDao.updateRole(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer deleteRole(Integer id) {
        Integer ret = 0;
        try {
            this.roleDao.deletePrivilegeRole(id);
            this.roleDao.deleteRole(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public List<MenuOne> getMenuTree() {

        List<MenuOne> list = null;
        try {
            list = this.menuDao.getMenuTree();
        } catch (Exception ex) {
            ex.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<Integer> getRolePrivilege(Integer roleId) {
        List<Integer> list = null;
        try {
            list = this.roleDao.getRolePrivilege(roleId);
        } catch (Exception ex) {
            ex.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public Integer editPrivilege(Integer roleId, List<Integer> pageList) {
        if (null == roleId || roleId <= 0 || null == pageList) {
            return -1;
        }
        Integer ret = 0;
        try {
            ArrayList<Integer> oriPageList = (ArrayList<Integer>)this.roleDao.getRolePrivilege(roleId);
            ArrayList<Integer> oriPageListCopy = (ArrayList<Integer>)oriPageList.clone();
            ArrayList<Integer> pageListCopy1 = (ArrayList<Integer>)((ArrayList<Integer>)pageList).clone();
            ArrayList<Integer> pageListCopy2 = (ArrayList<Integer>)((ArrayList<Integer>)pageList).clone();
            oriPageListCopy.removeAll(pageListCopy1);
            pageListCopy2.removeAll(oriPageList);
            List<RolePrivilege> pList = new ArrayList<>();
            for (Integer pageId : pageListCopy2) {
                RolePrivilege pItem = new RolePrivilege();
                pItem.setRoleId(roleId);
                pItem.setPageId(pageId);
                pList.add(pItem);
            }
            if (oriPageListCopy.size() > 0) {
                this.roleDao.deleteRolePrivilege(roleId, oriPageListCopy);
            }
            if (pList.size() > 0) {
                this.roleDao.addRolePrivilege(pList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }
}
