package com.liyu.cms.data.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.core.domain.UserInfo;
import com.liyu.cms.data.core.service.IRegisterService;
import com.liyu.cms.data.core.util.StatusTypeUtil;
import com.liyu.cms.data.web.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liyu on 2018/3/16.
 */
@RestController
@RequestMapping(value = "")
public class RegisterController {

//    private final Log logger = Log.get("RegisterController", "1.0", RegisterController.class);

    @Autowired
    private IRegisterService registerService;

    @RequestMapping(value = "/register", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String register(final HttpServletRequest request, @RequestBody JSONObject param) throws Exception {

        JSONObject resultJson = new JSONObject();
        try {
            String userIdParam = param.getString("userId");
            String nickname = param.getString("nickname");
            String passwordParam = param.getString("password");
            if (null == userIdParam || null == nickname || null == passwordParam) {
                resultJson.put("code", -1);
                resultJson.put("msg","注册失败");
            } else {
                userIdParam = userIdParam.trim();
                nickname = nickname.trim();
                passwordParam = passwordParam.trim();

                String userId = RSAUtil.decryptBase64(userIdParam);
                String password = RSAUtil.decryptBase64(passwordParam);
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(userId);
                userInfo.setPassword(password);
                userInfo.setNickname(nickname);
                Integer ret = this.registerService.registerUser(userInfo);
                if (ret >= 0) {
                    resultJson.put("code", 0);
                    resultJson.put("msg","注册成功");
                } else if (ret == -2) {
                    resultJson.put("code", -2);
                    resultJson.put("msg","用户名已经存在");
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg","注册失败");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("register","注册错误", ex.getMessage());

            resultJson.put("code", -1);
            resultJson.put("msg","注册错误");
        }

        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/person/user_info", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String personInfo(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String userId = request.getParameter("userId");
            if (StringUtils.isEmpty(userId)) {
                resultJson.put("code", -1);
                resultJson.put("msg", "用户名不能为空");
            } else {
                userId = userId.trim();
                UserInfo userInfo = this.registerService.getUserByUserId(userId);
                if (null != userInfo) {
                    JSONObject data = new JSONObject();
                    data.put("id",userInfo.getId());
                    data.put("userId",userInfo.getUserId());
                    data.put("nickname",userInfo.getNickname());
                    data.put("phoneNumber",userInfo.getPhoneNumber());
                    data.put("email",userInfo.getEmail());
                    data.put("roleName",userInfo.getRoleName());
                    resultJson.put("code", 0);
                    resultJson.put("data", data);
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg", "获取用户信息失败");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultJson.put("code", -1);
            resultJson.put("msg", "获取用户信息失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/person/edit_info", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String editPerson(final HttpServletRequest request,@RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            String nickname = body.getString("nickname");
            String phoneNumber = body.getString("phoneNumber");
            String email = body.getString("email");
            if (StringUtils.isEmpty(nickname)) {
                resultJson.put("code", -1);
                resultJson.put("msg", "昵称不能为空");
            } else {
                nickname = nickname.trim();
                if (null == phoneNumber) {
                    phoneNumber = "";
                } else {
                    phoneNumber = phoneNumber.trim();
                }
                if (null == email) {
                    email = "";
                } else {
                    email = email.trim();
                }
                UserInfo userInfo = new UserInfo();
                userInfo.setId(id);
                userInfo.setNickname(nickname);
                userInfo.setPhoneNumber(phoneNumber);
                userInfo.setEmail(email);
                Integer ret = this.registerService.editPersonInfo(userInfo);
                if (ret >= 0) {
                    resultJson.put("code", 0);
                    resultJson.put("msg", "修改个人信息成功");
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg", "修改个人信息失败");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultJson.put("code", -1);
            resultJson.put("msg", "修改个人信息失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/person/change_pwd", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String changePwd(final HttpServletRequest request,@RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            String tmpPwd = body.getString("password");
            if (StringUtils.isEmpty(tmpPwd)) {
                resultJson.put("code", -1);
                resultJson.put("msg", "密码不能为空");
            } else {
                tmpPwd = tmpPwd.trim();
                String password = RSAUtil.decryptBase64(tmpPwd);
                Integer ret = this.registerService.changePassword(id, password);
                if (ret >= 0) {
                    resultJson.put("code", 0);
                    resultJson.put("msg", "修改密码成功");
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg", "修改密码失败");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultJson.put("code", -1);
            resultJson.put("msg", "修改密码失败");
        }
        return resultJson.toJSONString();
    }
}
