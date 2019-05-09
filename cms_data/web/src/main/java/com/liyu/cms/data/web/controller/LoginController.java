package com.liyu.cms.data.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.core.domain.UserInfo;
import com.liyu.cms.data.core.service.ILoginService;
import com.liyu.cms.data.core.util.StatusTypeUtil;
import com.liyu.cms.data.web.util.JWT;
import com.liyu.cms.data.web.util.LoginInfo;
import com.liyu.cms.data.web.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liyu on 2018/3/16.
 */
@RestController
@RequestMapping(value = "")
public class LoginController {

//    private final Log logger = Log.get("LoginController", "1.0", LoginController.class);

    @Autowired
    private ILoginService loginService;

    @RequestMapping(value = "/public_key", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String publicKey(final HttpServletRequest request) throws Exception{
        String ret = "";
        JSONObject resultObj = new JSONObject();
        try {
            String key = RSAUtil.generateBase64PublicKey();
            resultObj.put("publicKey",key);
            resultObj.put("code",1);
        } catch (Exception ex) {
            resultObj.put("code",-1);
//            logger.error("publicKey","获取公钥错误", ex.getMessage());

        }

        ret = resultObj.toJSONString();
        return ret;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String login(final HttpServletRequest request, @RequestBody JSONObject param) throws Exception {

        String ret = "";
        JSONObject resultJson = new JSONObject();
        try {
            String userNameParam = param.getString("username").trim();
            String passwordParam = param.getString("password").trim();
            String userName = RSAUtil.decryptBase64(userNameParam);
            String password = RSAUtil.decryptBase64(passwordParam);
            UserInfo userInfo = this.loginService.getUserInfo(userName);
            if (null == userInfo) {
                //账号不存在
                resultJson.put("code", StatusTypeUtil.ERROR_LOGIN_NOT_USER_CODE);
                resultJson.put("msg",StatusTypeUtil.ERROR_LOGIN_NOT_USER_MSG);
                resultJson.put("access",0);
            } else if (userInfo.getFreezed() == true) {
                //账号被冻结
                resultJson.put("code", StatusTypeUtil.ERROR_LOGIN_USER_FREEZED_CODE);
                resultJson.put("msg",StatusTypeUtil.ERROR_LOGIN_USER_FREEZED_MSG);
                resultJson.put("access",0);
            } else {
                String oriPwd = userInfo.getPassword();
                if (!oriPwd.equals(password)) {
                    //密码不匹配
                    resultJson.put("code", StatusTypeUtil.ERROR_LOGIN_WRONG_PWD_CODE);
                    resultJson.put("msg",StatusTypeUtil.ERROR_LOGIN_WRONG_PWD_MSG);
                    resultJson.put("access",0);
                } else {
                    //正常登录，获取menu列表下发
                    Integer loginId = userInfo.getId() + 1000;
                    LoginInfo loginInfo = new LoginInfo();
                    loginInfo.setLoginId(loginId);
                    loginInfo.setUsername(userInfo.getUserId());
                    String token = JWT.sign(loginInfo, JWT.expTime);

                    JSONArray menu = this.loginService.getMenuByUser(userInfo);
                    resultJson.put("code", StatusTypeUtil.LOGIN_SUCCEED_CODE);
                    resultJson.put("msg",StatusTypeUtil.LOGIN_SUCCEED_MSG);
                    resultJson.put("access",1);
                    resultJson.put("loginId", loginId);
                    resultJson.put("token",token);

                    JSONObject userJson = new JSONObject();
                    userJson.put("userName",userInfo.getUserId());
                    userJson.put("nickname",userInfo.getNickname());
                    resultJson.put("userInfo",userJson);
                    resultJson.put("menu",menu);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("login","登录错误", ex.getMessage());

            resultJson.put("access",0);
            resultJson.put("code", StatusTypeUtil.ERROR_LOGIN_UNKNOW_CODE);
            resultJson.put("msg",StatusTypeUtil.ERROR_LOGIN_UNKNOW_MSG);
        }
        ret = resultJson.toJSONString();
        return ret;
    }

}
