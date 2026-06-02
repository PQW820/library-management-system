package com.library.controller;

import com.library.dto.AdminLoginRequest;
import com.library.dto.AdminRegisterRequest;
import com.library.dto.ResponseResult;
import com.library.entity.Admin;
import com.library.service.AdminService;
import com.library.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) throws Exception {
        CaptchaUtil.generateCaptcha(session, response);
    }

    /**
     * 管理员注册
     */
    @PostMapping("/register")
    public ResponseResult<String> register(@RequestBody AdminRegisterRequest request) {
        try {
            boolean success = adminService.register(request);
            if (success) {
                return ResponseResult.success("注册成功，请登录");
            } else {
                return ResponseResult.error("注册失败");
            }
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public ResponseResult<Admin> login(@RequestBody AdminLoginRequest request) {
        try {
            Admin admin = adminService.login(request);
            return ResponseResult.success(admin);
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/check-username")
    public ResponseResult<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = adminService.existsByUsername(username);
        return ResponseResult.success(exists);
    }
}
