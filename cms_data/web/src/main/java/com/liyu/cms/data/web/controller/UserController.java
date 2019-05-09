package com.liyu.cms.data.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.core.domain.Role;
import com.liyu.cms.data.core.domain.UserInfo;
import com.liyu.cms.data.core.service.IRoleService;
import com.liyu.cms.data.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2018/3/22.
 */
@RestController
@RequestMapping(value = "")
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/user/filter_role", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String filterRole(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            List<Role> roleList = this.roleService.getRoleAll();

            resultJson.put("code", 0);
            resultJson.put("data", roleList);
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("filterRole","获取角色选择器错误", ex.getMessage());
            resultJson.put("code", -1);
            resultJson.put("msg","获取角色选择器失败");
            resultJson.put("data",new ArrayList<>());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/user/user_list", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String getUserPage(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String searchKey = request.getParameter("searchKey").trim();
            searchKey = searchKey.replace(",","|");
            String tmpPageIndex = request.getParameter("pageIndex");
            String tmpPageSize = request.getParameter("pageSize");
            String tmpStatus = request.getParameter("status");
            Integer status = Integer.parseInt(tmpStatus);

            Integer pageIndex = Integer.parseInt(tmpPageIndex);
            Integer pageSize = Integer.parseInt(tmpPageSize);

            Integer total = this.userService.getUserTotalCount(searchKey, status);
            List<UserInfo> list = this.userService.getUserPage(searchKey, status, pageIndex, pageSize);

            resultJson.put("total",total);
            resultJson.put("rows",list);

        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("getUserPage","获取用户列表错误", ex.getMessage());
            resultJson.put("total",0);
            resultJson.put("rows",new ArrayList<>());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/user/add_user", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String addUser(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String userId = body.getString("userId").trim();
            String nickname = body.getString("nickname").trim();
            String phoneNumber = body.getString("phoneNumber");
            String email = body.getString("email");
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
            Integer roleId = body.getInteger("roleId");
            Boolean freezed = body.getBoolean("freezed");
            UserInfo user = new UserInfo();
            user.setUserId(userId);
            user.setNickname(nickname);
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            user.setRoleId(roleId);
            user.setFreezed(freezed);

            Integer ret = this.userService.addUser(user);
            if (ret >= 0) {
                resultJson.put("code",0);
                resultJson.put("msg","新增用户成功");
            } else if (ret == -2) {
                resultJson.put("code",-2);
                resultJson.put("msg","该用户已经存在");
            } else {
                resultJson.put("code",-1);
                resultJson.put("msg","新增用户失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("addUser","新增用户错误", ex.getMessage());
            resultJson.put("code",-1);
            resultJson.put("msg","新增用户失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/user/edit_user", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String editUser(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            String nickname = body.getString("nickname").trim();
            String phoneNumber = body.getString("phoneNumber");
            String email = body.getString("email");
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
            Integer roleId = body.getInteger("roleId");
            Boolean freezed = body.getBoolean("freezed");
            UserInfo user = new UserInfo();
            user.setId(id);
            user.setNickname(nickname);
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            user.setRoleId(roleId);
            user.setFreezed(freezed);

            Integer ret = this.userService.editUser(user);
            if (ret >= 0) {
                resultJson.put("code",0);
                resultJson.put("msg","编辑用户成功");
            } else {
                resultJson.put("code",-1);
                resultJson.put("msg","编辑用户失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("editUser","编辑用户错误", ex.getMessage());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/user/delete_user", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String deleteRole(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            Integer ret = this.userService.deleteUser(id);
            if (ret >= 0) {
                resultJson.put("code",0);
                resultJson.put("msg","删除用户成功");
            } else {
                resultJson.put("code",0);
                resultJson.put("msg","删除用户失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("deleteRole","删除用户错误", ex.getMessage());
        }
        return resultJson.toJSONString();
    }

}
