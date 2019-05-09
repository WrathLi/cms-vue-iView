package com.liyu.cms.data.core.service.impl;

import com.liyu.cms.data.core.dao.UserDao;
import com.liyu.cms.data.core.domain.UserInfo;
import com.liyu.cms.data.core.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
@Service
public class RegisterService implements IRegisterService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer registerUser(UserInfo userInfo) {
        if (null == userInfo) {
            return -1;
        }
        Integer ret = 0;
        try {
            String userId = userInfo.getUserId();
            Integer exist = this.userDao.userExist(userId);
            if (exist > 0) {
                ret = -2;
            } else {
                userInfo.setPhoneNumber("");
                userInfo.setEmail("");
                userInfo.setCreateTime(new Date());
                //游客
                userInfo.setRoleId(3);
                userInfo.setFreezed(false);
                this.userDao.addUser(userInfo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer editPersonInfo(UserInfo userInfo) {
        if (null == userInfo) {
            return -1;
        }
        Integer ret = 0;
        try {
            this.userDao.updateInfo(userInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer changePassword(Integer id, String pwd) {
        if (StringUtils.isEmpty(pwd)) {
            return -1;
        }
        Integer ret = 0;
        try {
            this.userDao.changePassword(id, pwd);
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public UserInfo getUserByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        UserInfo user = null;
        try {
            List<UserInfo> list = this.userDao.getUserByUserId(userId);
            if (list.size() > 0) {
                user = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }
}
