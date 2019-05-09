package com.liyu.cms.data.core.service;

import com.liyu.cms.data.core.domain.MenuOne;
import com.liyu.cms.data.core.domain.Role;

import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
public interface IRoleService {

    Integer getRoleTotalCount(String searchKey);

    List<Role> getRolePage(String searchKey,Integer pageIndex,Integer pageSize);

    List<Role> getRoleAll();

    Integer addRole(Role item);

    Integer editRole(Role item);

    Integer deleteRole(Integer id);

    List<MenuOne> getMenuTree();

    List<Integer> getRolePrivilege(Integer roleId);

    Integer editPrivilege(Integer roleId,List<Integer> pageList);

}
