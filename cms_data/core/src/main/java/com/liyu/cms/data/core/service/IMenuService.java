package com.liyu.cms.data.core.service;

import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.MenuPage;

import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
public interface IMenuService {

    List<MenuOne> getMenuOneList();

    List<MenuPage> getMenuPageList(Integer oneId);

    Integer addMenuOne(MenuOne item);

    Integer editMenuOne(MenuOne item);

    Integer deleteMenuOne(Integer oneId);

    Integer addMenuPage(MenuPage item);

    Integer editMenuPage(MenuPage item);

    Integer deleteMenuPage(Integer pageId);

}
