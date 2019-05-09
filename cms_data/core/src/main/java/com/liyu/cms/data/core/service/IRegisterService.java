package com.liyu.cms.data.core.service;

import com.liyu.cms.data.core.domain.UserInfo;

/**
 * Created by liyu on 2018/3/15.
 */
public interface IRegisterService {

    Integer registerUser(UserInfo userInfo);

    Integer editPersonInfo(UserInfo userInfo);

    Integer changePassword(Integer id, String pwd);

    UserInfo getUserByUserId(String userId);

}
