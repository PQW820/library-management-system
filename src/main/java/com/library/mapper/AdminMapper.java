package com.library.mapper;

import com.library.entity.Admin;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员Mapper接口
 */
public interface AdminMapper {
    /**
     * 根据用户名查询管理员
     */
    Admin selectByUsername(@Param("username") String username);

    /**
     * 根据ID查询管理员
     */
    Admin selectById(@Param("adminId") Integer adminId);

    /**
     * 插入管理员记录
     */
    int insert(Admin admin);

    /**
     * 更新管理员信息
     */
    int update(Admin admin);

    /**
     * 删除管理员
     */
    int delete(@Param("adminId") Integer adminId);

    /**
     * 检查用户名是否存在
     */
    int countByUsername(@Param("username") String username);
}
