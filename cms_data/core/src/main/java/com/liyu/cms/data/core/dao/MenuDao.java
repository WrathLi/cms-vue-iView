package com.liyu.cms.data.core.dao;

import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.MenuPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
@Mapper
public interface MenuDao {

    //获取全部menu菜单
    List<MenuOne> getAllMenu();

    //根据角色ID获取menu菜单
    List<MenuOne> getRoleMenu(@Param("roleId")Integer roleId);

    List<MenuOne> getMenuTree();

    List<MenuOne> getMenuOneList();

    List<MenuPage> getMenuPageList(@Param("oneId")Integer oneId);

    Integer menuOneExist(@Param("routeName")String routeName,@Param("oneId")Integer oneId);

    void addMenuOne(MenuOne item);

    void updateMenuOne(MenuOne item);

    void deleteMenuOne(@Param("oneId")Integer oneId);

    void deleteMenuPageInOne(@Param("oneId")Integer oneId);

    Integer menuPageExist(@Param("routeName")String routeName,@Param("pageId")Integer pageId);

    void addMenuPage(MenuPage item);

    void updateMenuPage(MenuPage item);

    void deleteMenuPage(@Param("pageId")Integer pageId);

}
