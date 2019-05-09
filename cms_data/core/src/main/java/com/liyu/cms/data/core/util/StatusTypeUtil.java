package com.liyu.cms.data.core.util;

/**
 * Created by liyu on 2018/3/15.
 */
public class StatusTypeUtil {

    //登录
    public static final Integer LOGIN_SUCCEED_CODE = 1;
    public static final String LOGIN_SUCCEED_MSG = "正常登录";

    public static final Integer ERROR_LOGIN_UNKNOW_CODE = -1;
    public static final String ERROR_LOGIN_UNKNOW_MSG = "未知错误";

    public static final Integer ERROR_LOGIN_NOT_USER_CODE = -2;
    public static final String ERROR_LOGIN_NOT_USER_MSG = "用户名不存在";

    public static final Integer ERROR_LOGIN_WRONG_PWD_CODE = -3;
    public static final String ERROR_LOGIN_WRONG_PWD_MSG = "密码错误";

    public static final Integer ERROR_LOGIN_USER_FREEZED_CODE = -4;
    public static final String ERROR_LOGIN_USER_FREEZED_MSG = "用户被冻结";


    //注册
    public static final Integer NORMAL_REGISTER_CODE = 1;
    public static final String NORMAL_REGISTER_MSG = "正常注册";

}
