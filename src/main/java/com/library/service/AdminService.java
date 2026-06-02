package com.library.service;

import com.library.dto.AdminLoginRequest;
import com.library.dto.AdminRegisterRequest;
import com.library.entity.Admin;

/**
 * 管理员服务接口
 */
public interface AdminService {
    /**
     * 管理员登录
     */
    Admin login(AdminLoginRequest request);

    /**
     * 管理员注册
     */
    boolean register(AdminRegisterRequest request);

    /**
     * 根据ID获取管理员信息
     */
    Admin getAdminById(Integer adminId);

    /**
     * 根据用户名获取管理员信息
     */
    Admin getAdminByUsername(String username);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);
}
