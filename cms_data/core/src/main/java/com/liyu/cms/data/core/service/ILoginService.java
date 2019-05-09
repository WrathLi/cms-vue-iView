package com.liyu.cms.data.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.core.domain.UserInfo;

/**
 * Created by liyu on 2018/3/15.
 */
public interface ILoginService {

    //获取用户信息
    UserInfo getUserInfo(String userId);

    JSONArray getMenuByUser(UserInfo userInfo);


}
