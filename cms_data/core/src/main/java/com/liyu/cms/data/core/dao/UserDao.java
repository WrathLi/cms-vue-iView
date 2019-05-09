package com.liyu.cms.data.core.dao;

import com.liyu.cms.data.core.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyu on 2018/3/15.
 */
@Mapper
public interface UserDao {

    Integer getUserTotalCount(@Param("searchKey")String searchKey,@Param("status")Integer status);

    List<UserInfo> getUserPage(@Param("searchKey")String searchKey,@Param("status")Integer status,@Param("dataIndex")Integer dataIndex,@Param("pageSize")Integer pageSize);

    List<UserInfo> getUserByUserId(@Param("userId")String userId);

    Integer userExist(@Param("userId")String userId);

    void addUser(UserInfo userInfo);

    void updateUser(UserInfo userInfo);

    void deleteUser(@Param("id")Integer id);

    void changePassword(@Param("id")Integer id,@Param("password")String password);

    void updateInfo(UserInfo userInfo);

}
