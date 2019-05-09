package com.liyu.cms.data.core.service.impl;

import com.liyu.cms.data.core.dao.MenuDao;
import com.liyu.cms.data.core.dao.RoleDao;
import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.MenuPage;
import com.liyu.cms.data.core.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
@Service
public class MenuService implements IMenuService{

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<MenuOne> getMenuOneList() {
        List<MenuOne> list = null;
        try {
            list = this.menuDao.getMenuOneList();
        } catch (Exception ex) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<MenuPage> getMenuPageList(Integer oneId) {
        List<MenuPage> list = null;
        try {
            list = this.menuDao.getMenuPageList(oneId);
        } catch (Exception ex) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public Integer addMenuOne(MenuOne item) {
        if (null == item || StringUtils.isEmpty(item.getRouteName())) {
            return -1;
        }
        Integer ret = 0;
        try {
            Integer exist = this.menuDao.menuOneExist(item.getRouteName(),0);
            if (null != exist && exist > 0) {
                ret = -2;
            } else {
                this.menuDao.addMenuOne(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer editMenuOne(MenuOne item) {
        if (null == item || StringUtils.isEmpty(item.getRouteName())) {
            return -1;
        }
        Integer ret = 0;
        try {
            Integer exist = this.menuDao.menuOneExist(item.getRouteName(),item.getId());
            if (null != exist && exist > 0) {
                ret = -2;
            } else {
                this.menuDao.updateMenuOne(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer deleteMenuOne(Integer oneId) {
        Integer ret = 0;
        try {
            this.roleDao.deletePrivilegeMenuOne(oneId);
            this.menuDao.deleteMenuPageInOne(oneId);
            this.menuDao.deleteMenuOne(oneId);
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer addMenuPage(MenuPage item) {
        if (null == item || StringUtils.isEmpty(item.getRouteName())) {
            return -1;
        }
        Integer ret = 0;
        try {
            Integer exist = this.menuDao.menuPageExist(item.getRouteName(),0);
            if (null != exist && exist > 0) {
                ret = -2;
            } else {
                this.menuDao.addMenuPage(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer editMenuPage(MenuPage item) {
        if (null == item || StringUtils.isEmpty(item.getRouteName())) {
            return -1;
        }
        Integer ret = 0;
        try {
            Integer exist = this.menuDao.menuPageExist(item.getRouteName(),item.getId());
            if (null != exist && exist > 0) {
                ret = -2;
            } else {
                this.menuDao.updateMenuPage(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer deleteMenuPage(Integer pageId) {
        Integer ret = 0;
        try {
            this.roleDao.deletePrivilegeMenuPage(pageId);
            this.menuDao.deleteMenuPage(pageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }
}
