package com.liyu.cms.data.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.core.dao.MenuDao;
import com.liyu.cms.data.core.dao.UserDao;
import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.MenuPage;
import com.liyu.cms.data.core.domain.UserInfo;
import com.liyu.cms.data.core.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
@Service
public class LoginService implements ILoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    public UserInfo getUserInfo(String userId) {
        if (null == userId || userId.length() == 0) {
            return null;
        }
        UserInfo user = null;
        try {
            List<UserInfo> list = this.userDao.getUserByUserId(userId);
            if (null != list && list.size() > 0) {
                user = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public JSONArray getMenuByUser(UserInfo userInfo) {
        if (null == userInfo) {
            return null;
        }
        JSONArray menuList = new JSONArray();
        try {
            Boolean beSuper = userInfo.getBeSuper();
            List<MenuOne> dataList = null;
            if (beSuper.equals(true)) {
                //超管
                dataList = this.menuDao.getAllMenu();
            } else {
                //普通用户
                Integer roleId = userInfo.getRoleId();
                dataList = this.menuDao.getRoleMenu(roleId);
            }
            //组装menu
            for (int i = 0, maxI = dataList.size(); i < maxI; i++) {
                MenuOne oneItem = dataList.get(i);
                List<MenuPage> pageDataList = oneItem.getPageList();
                if (null != pageDataList && pageDataList.size() > 0) {
                    JSONObject menuJson = new JSONObject();
                    JSONArray pageList = new JSONArray();
                    for (int j = 0, maxJ = pageDataList.size(); j < maxJ; j++) {
                        MenuPage pageItem = pageDataList.get(j);
                        JSONObject pageJson = new JSONObject();
                        pageJson.put("name",pageItem.getRouteName());
                        pageJson.put("title",pageItem.getTitle());
                        pageJson.put("icon",pageItem.getIcon());
                        pageList.add(pageJson);
                    }
                    menuJson.put("children",pageList);
                    menuJson.put("name",oneItem.getRouteName());
                    menuJson.put("title",oneItem.getTitle());
                    menuJson.put("icon",oneItem.getIcon());
                    menuList.add(menuJson);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return menuList;
    }
}
