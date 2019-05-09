package com.liyu.cms.data.core.service;

import com.liyu.cms.data.core.domain.UserInfo;

import java.util.List;

/**
 * Created by liyu on 2018/3/22.
 */
public interface IUserService {

    Integer getUserTotalCount(String searchKey,Integer status);

    List<UserInfo> getUserPage(String searchKey,Integer status,Integer pageIndex,Integer pageSize);

    Integer addUser(UserInfo item);

    Integer editUser(UserInfo item);

    Integer deleteUser(Integer id);
}
