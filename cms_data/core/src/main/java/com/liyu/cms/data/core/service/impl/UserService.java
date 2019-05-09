package com.liyu.cms.data.core.service.impl;

import com.liyu.cms.data.core.dao.UserDao;
import com.liyu.cms.data.core.domain.UserInfo;
import com.liyu.cms.data.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liyu on 2018/3/22.
 */
@Service
public class UserService implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public Integer getUserTotalCount(final String searchKey, final Integer status) {
        Integer total = 0;
        try {
            String tmpSearchKey = searchKey;
            if (StringUtils.isEmpty(tmpSearchKey)) {
                tmpSearchKey = null;
            }
            total = this.userDao.getUserTotalCount(tmpSearchKey, status);
        } catch (Exception ex) {
            ex.printStackTrace();
            total = 0;
        }
        return total;
    }

    @Override
    public List<UserInfo> getUserPage(final String searchKey, final Integer status, final Integer pageIndex, final Integer pageSize) {
        List<UserInfo> list = null;
        try {
            String tmpSearchKey = searchKey;
            if (StringUtils.isEmpty(tmpSearchKey)) {
                tmpSearchKey = null;
            }
            Integer dataIndex = pageIndex * pageSize;
            list = this.userDao.getUserPage(tmpSearchKey,status,dataIndex,pageSize);
        } catch (Exception ex) {
            ex.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public Integer addUser(UserInfo item) {
        if (null == item) {
            return -1;
        }
        Integer ret = 0;
        try {
            String userId = item.getUserId();
            Integer exist = this.userDao.userExist(userId);
            if (exist > 0) {
                ret = -2;
            } else {
                item.setPassword("123456");
                item.setCreateTime(new Date());
                this.userDao.addUser(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer editUser(UserInfo item) {
        if (null == item) {
            return -1;
        }
        Integer ret = 0;
        try {
            this.userDao.updateUser(item);
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    @Override
    public Integer deleteUser(Integer id) {
        Integer ret = 0;
        try {
            this.userDao.deleteUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = -1;
        }
        return ret;
    }
}
