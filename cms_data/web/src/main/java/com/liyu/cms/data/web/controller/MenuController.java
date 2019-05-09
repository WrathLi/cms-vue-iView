package com.liyu.cms.data.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.MenuPage;
import com.liyu.cms.data.core.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liyu on 2018/3/16.
 */
@RestController
@RequestMapping(value = "")
public class MenuController {

//    private final Log logger = Log.get("MenuController", "1.0", MenuController.class);

    @Autowired
    private IMenuService menuService;

    @RequestMapping(value = "/menu/one_list", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String getMenuOne(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            List<MenuOne> dataList = this.menuService.getMenuOneList();
            JSONArray menuOneList = new JSONArray();
            for (int i = 0, maxI = dataList.size(); i < maxI; i++) {
                MenuOne dataItem = dataList.get(i);
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("id",dataItem.getId());
                jsonItem.put("routeName",dataItem.getRouteName());
                jsonItem.put("title",dataItem.getTitle());
                jsonItem.put("icon",dataItem.getIcon());
                jsonItem.put("sortIndex",dataItem.getSortIndex());
                menuOneList.add(jsonItem);
            }
            resultJson.put("code",0);
            resultJson.put("data",menuOneList);
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("getMenuOne","获取目录列表错误", ex.getMessage());
            resultJson.put("code",-1);
            resultJson.put("msg","获取失败");
            resultJson.put("data",new JSONArray());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/menu/add_one", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String addMenuOne(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String routeName = body.getString("routeName").trim();
            String title = body.getString("title").trim();
            String icon = body.getString("icon").trim();
            Integer sortIndex = body.getInteger("sortIndex");
            if (StringUtils.isEmpty(icon)) {
                icon = "ios-folder-outline";
            }
            if (null == sortIndex) {
                sortIndex = 0;
            }
            if (StringUtils.isEmpty(routeName) || StringUtils.isEmpty(title)) {
                resultJson.put("code",-1);
                resultJson.put("msg","参数不能为空");
            } else {
                MenuOne oneItem = new MenuOne();
                oneItem.setRouteName(routeName);
                oneItem.setTitle(title);
                oneItem.setIcon(icon);
                oneItem.setSortIndex(sortIndex);
                Integer ret = this.menuService.addMenuOne(oneItem);
                if (ret >= 0) {
                    resultJson.put("code", 0);
                    resultJson.put("msg", "添加成功");
                } else if (ret == -2) {
                    resultJson.put("code", -2);
                    resultJson.put("msg", "该路径已经存在");
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg", "添加一级菜单失败");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("addMenuOne","添加目录错误", ex.getMessage());

            resultJson.put("code",-1);
            resultJson.put("msg","添加一级菜单失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/menu/edit_one", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String editMenuOne(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            String routeName = body.getString("routeName").trim();
            String title = body.getString("title").trim();
            String icon = body.getString("icon").trim();
            Integer sortIndex = body.getInteger("sortIndex");
            if (StringUtils.isEmpty(icon)) {
                icon = "ios-folder-outline";
            }
            if (null == sortIndex) {
                sortIndex = 0;
            }
            if (StringUtils.isEmpty(routeName) || StringUtils.isEmpty(title)) {
                resultJson.put("code",-1);
                resultJson.put("msg","参数不能为空");
            } else {
                MenuOne oneItem = new MenuOne();
                oneItem.setId(id);
                oneItem.setRouteName(routeName);
                oneItem.setTitle(title);
                oneItem.setIcon(icon);
                oneItem.setSortIndex(sortIndex);
                Integer ret = this.menuService.editMenuOne(oneItem);
                if (ret >= 0) {
                    resultJson.put("code", 0);
                    resultJson.put("msg", "修改成功");
                } else if (ret == -2) {
                    resultJson.put("code", -2);
                    resultJson.put("msg", "该路径已经存在");
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg", "修改一级菜单失败");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("editMenuOne","编辑目录错误", ex.getMessage());

            resultJson.put("code",-1);
            resultJson.put("msg","修改一级菜单失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/menu/delete_one", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String deleteMenuOne(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            Integer ret = this.menuService.deleteMenuOne(id);
            if (ret >= 0) {
                resultJson.put("code", 0);
                resultJson.put("msg","删除一级菜单成功");
            } else {
                resultJson.put("code",-1);
                resultJson.put("msg","删除一级菜单失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("deleteMenuOne","删除目录错误", ex.getMessage());

            resultJson.put("code",-1);
            resultJson.put("msg","删除一级菜单失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/menu/page_list", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String getMenuPage(final HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            String tmpOneId = request.getParameter("oneId");
            if (StringUtils.isEmpty(tmpOneId)) {
                resultJson.put("code",-1);
                resultJson.put("msg","一级菜单ID为空");
                resultJson.put("data",new JSONArray());
            } else {
                Integer oneId = Integer.parseInt(tmpOneId);
                List<MenuPage> dataList = this.menuService.getMenuPageList(oneId);
                JSONArray menuPageList = new JSONArray();
                for (int i = 0, maxI = dataList.size(); i < maxI; i++) {
                    MenuPage dataItem = dataList.get(i);
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put("id", dataItem.getId());
                    jsonItem.put("oneId", dataItem.getOneId());
                    jsonItem.put("routeName", dataItem.getRouteName());
                    jsonItem.put("title", dataItem.getTitle());
                    jsonItem.put("icon", dataItem.getIcon());
                    jsonItem.put("sortIndex", dataItem.getSortIndex());
                    jsonItem.put("hidePage", dataItem.getHidePage());
                    menuPageList.add(jsonItem);
                }
                resultJson.put("code",0);
                resultJson.put("data",menuPageList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("getMenuPage","获取明细菜单列表错误", ex.getMessage());
            resultJson.put("code",-1);
            resultJson.put("msg","获取失败");
            resultJson.put("data",new JSONArray());
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/menu/add_page", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String addMenuPage(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer oneId = body.getInteger("oneId");
            String routeName = body.getString("routeName").trim();
            String title = body.getString("title").trim();
            String icon = body.getString("icon").trim();
            Integer sortIndex = body.getInteger("sortIndex");
            Boolean hidePage = body.getBoolean("hidePage");
            if (StringUtils.isEmpty(icon)) {
                icon = "document";
            }
            if (null == sortIndex) {
                sortIndex = 0;
            }

            if (StringUtils.isEmpty(routeName) || StringUtils.isEmpty(title)) {
                resultJson.put("code",-1);
                resultJson.put("msg","参数不能为空");
            } else {
                MenuPage pageItem = new MenuPage();
                pageItem.setOneId(oneId);
                pageItem.setRouteName(routeName);
                pageItem.setTitle(title);
                pageItem.setIcon(icon);
                pageItem.setSortIndex(sortIndex);
                pageItem.setHidePage(hidePage);
                Integer ret = this.menuService.addMenuPage(pageItem);
                if (ret >= 0) {
                    resultJson.put("code", 0);
                    resultJson.put("msg", "添加明细菜单成功");
                } else if (ret == -2) {
                    resultJson.put("code", -2);
                    resultJson.put("msg", "该路径已经存在");
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg", "添加明细菜单失败");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("addMenuPage","添加明细菜单错误", ex.getMessage());

            resultJson.put("code", -1);
            resultJson.put("msg", "添加明细菜单失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/menu/edit_page", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String editMenuPage(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            Integer oneId = body.getInteger("oneId");
            String routeName = body.getString("routeName").trim();
            String title = body.getString("title").trim();
            String icon = body.getString("icon").trim();
            Integer sortIndex = body.getInteger("sortIndex");
            Boolean hidePage = body.getBoolean("hidePage");
            if (StringUtils.isEmpty(icon)) {
                icon = "document";
            }
            if (null == sortIndex) {
                sortIndex = 0;
            }

            if (StringUtils.isEmpty(routeName) || StringUtils.isEmpty(title)) {
                resultJson.put("code",-1);
                resultJson.put("msg","参数不能为空");
            } else {
                MenuPage pageItem = new MenuPage();
                pageItem.setId(id);
                pageItem.setOneId(oneId);
                pageItem.setRouteName(routeName);
                pageItem.setTitle(title);
                pageItem.setIcon(icon);
                pageItem.setSortIndex(sortIndex);
                pageItem.setHidePage(hidePage);
                Integer ret = this.menuService.editMenuPage(pageItem);
                if (ret >= 0) {
                    resultJson.put("code", 0);
                    resultJson.put("msg", "修改明细菜单成功");
                } else if (ret == -2) {
                    resultJson.put("code", -2);
                    resultJson.put("msg", "该路径已经存在");
                } else {
                    resultJson.put("code", -1);
                    resultJson.put("msg", "修改明细菜单失败");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("editMenuPage","编辑明细菜单错误", ex.getMessage());

            resultJson.put("code", -1);
            resultJson.put("msg", "修改明细菜单失败");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/menu/delete_page", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String deleteMenuPage(final HttpServletRequest request, @RequestBody JSONObject body) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            Integer id = body.getInteger("id");
            Integer ret = this.menuService.deleteMenuPage(id);
            if (ret >= 0) {
                resultJson.put("code", 0);
                resultJson.put("msg","删除明细菜单成功");
            } else {
                resultJson.put("code",-1);
                resultJson.put("msg","删除明细菜单失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("deleteMenuPage","删除明细菜单错误", ex.getMessage());

            resultJson.put("code",-1);
            resultJson.put("msg","删除明细菜单失败");
        }
        return resultJson.toJSONString();
    }
}
