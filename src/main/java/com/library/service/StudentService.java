package com.library.service;

import com.library.entity.Student;
import java.util.List;

/**
 * 学生服务接口
 */
public interface StudentService {
    /**
     * 查询所有学生
     */
    List<Student> getAllStudents();

    /**
     * 根据ID查询学生
     */
    Student getStudentById(Integer studentId);

    /**
     * 根据学号查询学生
     */
    Student getStudentByNumber(String studentNumber);

    /**
     * 根据班级查询学生
     */
    List<Student> getStudentsByClass(String className);

    /**
     * 添加学生
     */
    void addStudent(Student student);

    /**
     * 更新学生
     */
    void updateStudent(Student student);

    /**
     * 删除学生
     */
    void deleteStudent(Integer studentId);
}
