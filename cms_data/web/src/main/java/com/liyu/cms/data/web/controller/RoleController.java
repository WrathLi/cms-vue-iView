package com.liyu.cms.data.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.MenuPage;
import com.liyu.cms.data.core.domain.Role;
import com.liyu.cms.data.core.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2018/3/16.
 */
@RestController
@RequestMapping(value = "")
public class RoleController {

//    private final Log logger = Log.get("RoleController", "1.0", RoleController.class);

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/role/privilege_tree", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String privilegeTree(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            List<MenuOne> menuTree = this.roleService.getMenuTree();
            JSONArray privilegeTree = new JSONArray();
            for (int i = 0,maxI = menuTree.size(); i < maxI; i++) {
                MenuOne oneItem = menuTree.get(i);
                JSONObject privilegeItem = new JSONObject();
                privilegeItem.put("title", oneItem.getTitle());
                privilegeItem.put("expand", true);
                List<MenuPage> pageList = oneItem.getPageList();
                JSONArray leafList = new JSONArray();
                for (int j = 0,maxJ = pageList.size(); j < maxJ; j++) {
                    MenuPage pageItem = pageList.get(j);
                    JSONObject leafItem = new JSONObject();
                    leafItem.put("title",pageItem.getTitle());
                    leafItem.put("pageId",pageItem.getId());
                    leafItem.put("checked",false);
                    leafList.add(leafItem);
                }
                privilegeItem.put("children",leafList);
                privilegeTree.add(privilegeItem);
            }
            resultJson.put("code", 0);
            resultJson.put("data", privilegeTree);
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("privilegeTree","获取权限树错误", ex.getMessage());
            resultJson.put("code", -1);
            resultJson.put("msg","获取权限树失败");
            resultJson.put("data",new JSONArray());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/role/role_privilege", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String rolePrivilege(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String tmpRoleId = request.getParameter("roleId");
            Integer roleId = Integer.parseInt(tmpRoleId);
            List<Integer> pageList = this.roleService.getRolePrivilege(roleId);
            JSONObject pageJson = new JSONObject();
            for (Integer item : pageList) {
                String key = "" + item;
                pageJson.put(key,item);
            }
            resultJson.put("code", 0);
            resultJson.put("data", pageJson);
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("rolePrivilege","获取角色权限错误", ex.getMessage());
            resultJson.put("code", -1);
            resultJson.put("msg","获取权限失败");
            resultJson.put("data",new ArrayList<>());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/role/edit_privilege", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String editPrivilege(final HttpServletRequest request,@RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer roleId = body.getInteger("roleId");
            JSONArray tmpPageList = body.getJSONArray("pageList");
            List<Integer> pageList = new ArrayList<>();
            for (int i = 0, maxI = tmpPageList.size(); i < maxI; i++) {
                Integer pageId = tmpPageList.getInteger(i);
                pageList.add(pageId);
            }
            this.roleService.editPrivilege(roleId,pageList);
            resultJson.put("code", 0);
            resultJson.put("msg","修改权限成功");
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("editPrivilege","修改角色权限错误", ex.getMessage());
            resultJson.put("code", -1);
            resultJson.put("msg","修改权限失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/role/role_list", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String getRolePage(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String searchKey = request.getParameter("searchKey").trim();
            searchKey = searchKey.replace(",","|");
            String tmpPageIndex = request.getParameter("pageIndex");
            String tmpPageSize = request.getParameter("pageSize");
            Integer pageIndex = Integer.parseInt(tmpPageIndex);
            Integer pageSize = Integer.parseInt(tmpPageSize);
            Integer total = this.roleService.getRoleTotalCount(searchKey);
            List<Role> list = this.roleService.getRolePage(searchKey,pageIndex,pageSize);
            resultJson.put("total",total);
            resultJson.put("rows",list);

        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("getRolePage","获取角色列表错误", ex.getMessage());
            resultJson.put("total",0);
            resultJson.put("rows",new ArrayList<>());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/role/add_role", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String addRole(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String name = body.getString("name").trim();
            String description = body.getString("description").trim();
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
            Integer ret = this.roleService.addRole(role);
            if (ret >= 0) {
                resultJson.put("code",0);
                resultJson.put("msg","新增角色成功");
            } else if (ret == -2) {
                resultJson.put("code",-2);
                resultJson.put("msg","该角色已经存在");
            } else {
                resultJson.put("code",-1);
                resultJson.put("msg","新增角色失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("addRole","新增角色错误", ex.getMessage());
            resultJson.put("code",-1);
            resultJson.put("msg","新增角色失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/role/edit_role", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String editRole(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            String name = body.getString("name").trim();
            String description = body.getString("description").trim();
            Role role = new Role();
            role.setId(id);
            role.setName(name);
            role.setDescription(description);
            Integer ret = this.roleService.editRole(role);
            if (ret >= 0) {
                resultJson.put("code",0);
                resultJson.put("msg","编辑角色成功");
            } else if (ret == -2) {
                resultJson.put("code",-2);
                resultJson.put("msg","该角色名已经存在");
            } else {
                resultJson.put("code",-1);
                resultJson.put("msg","编辑角色失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("editRole","编辑角色错误", ex.getMessage());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/role/delete_role", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String deleteRole(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            Integer ret = this.roleService.deleteRole(id);
            if (ret >= 0) {
                resultJson.put("code",0);
                resultJson.put("msg","删除角色成功");
            } else {
                resultJson.put("code",0);
                resultJson.put("msg","删除角色失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("deleteRole","删除角色错误", ex.getMessage());
        }
        return resultJson.toJSONString();
    }


}
