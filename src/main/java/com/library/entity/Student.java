package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer studentId;
    private String studentNumber;  // 学号
    private String name;  // 姓名
    private String className;  // 班级
    private String gender;  // 性别
    private String phone;  // 电话
    private String email;  // 邮箱
    private String password;  // 密码
    private Integer status;  // 状态 (0:正常, 1:禁用)
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间
}