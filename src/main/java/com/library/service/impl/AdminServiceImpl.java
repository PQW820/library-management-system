package com.library.service.impl;

import com.library.dto.AdminLoginRequest;
import com.library.dto.AdminRegisterRequest;
import com.library.entity.Admin;
import com.library.mapper.AdminMapper;
import com.library.service.AdminService;
import com.library.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 管理员服务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(AdminLoginRequest request) {
        // 验证验证码
        if (!validateCaptcha(request.getCaptcha())) {
            throw new RuntimeException("验证码错误");
        }

        // 根据用户名查询管理员
        Admin admin = adminMapper.selectByUsername(request.getUsername());
        if (admin == null) {
            throw new RuntimeException("用户名不存在");
        }

        // 验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());
        if (!encryptedPassword.equals(admin.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 验证状态
        if (admin.getStatus() != 1) {
            throw new RuntimeException("账号已被禁用");
        }

        return admin;
    }

    @Override
    public boolean register(AdminRegisterRequest request) {
        // 验证验证码
        if (!validateCaptcha(request.getCaptcha())) {
            throw new RuntimeException("验证码错误");
        }

        // 验证密码是否一致
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 检查用户名是否已存在
        if (existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建管理员
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
        admin.setEmail(request.getEmail());
        admin.setPhone(request.getPhone());
        admin.setStatus(1);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());

        adminMapper.insert(admin);
        return true;
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectById(adminId);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminMapper.countByUsername(username) > 0;
    }

    /**
     * 验证验证码
     */
    private boolean validateCaptcha(String captcha) {
        if (captcha == null || captcha.isEmpty()) {
            return false;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return false;
        }

        HttpSession session = attributes.getRequest().getSession();
        String storedCaptcha = (String) session.getAttribute(CaptchaUtil.CAPTCHA_SESSION_KEY);

        if (storedCaptcha == null) {
            return false;
        }

        return storedCaptcha.equalsIgnoreCase(captcha);
    }
}
