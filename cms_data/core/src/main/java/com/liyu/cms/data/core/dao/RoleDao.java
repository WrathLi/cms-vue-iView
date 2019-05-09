package com.liyu.cms.data.core.dao;

import com.liyu.cms.data.core.domain.Role;
import com.liyu.cms.data.core.domain.RolePrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
@Mapper
public interface RoleDao {

    void deletePrivilegeMenuOne(@Param("oneId")Integer oneId);

    void deletePrivilegeMenuPage(@Param("pageId")Integer pageId);

    Integer getRoleTotalCount(@Param("searchKey")String searchKey);

    List<Role> getRolePage(@Param("searchKey")String searchKey,@Param("dataIndex")Integer dataIndex,@Param("pageSize")Integer pageSize);

    List<Role> getRoleAll();

    Integer roleExist(@Param("name")String name,@Param("id")Integer id);

    void addRole(Role item);

    void updateRole(Role item);

    void deleteRole(@Param("id")Integer id);

    void deletePrivilegeRole(@Param("roleId")Integer roleId);

    List<Integer> getRolePrivilege(@Param("roleId")Integer roleId);

    void deleteRolePrivilege(@Param("roleId")Integer roleId,@Param("pageList")List<Integer> pageList);

    void addRolePrivilege(List<RolePrivilege> pageList);
}
