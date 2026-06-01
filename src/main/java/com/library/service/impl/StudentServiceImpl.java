package com.library.service.impl;

import com.library.entity.Student;
import com.library.mapper.StudentMapper;
import com.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 学生服务实现类
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.selectAll();
    }

    @Override
    public Student getStudentById(Integer studentId) {
        return studentMapper.selectById(studentId);
    }

    @Override
    public Student getStudentByNumber(String studentNumber) {
        return studentMapper.selectByStudentNumber(studentNumber);
    }

    @Override
    public List<Student> getStudentsByClass(String className) {
        return studentMapper.selectByClassName(className);
    }

    @Override
    public List<Student> searchStudents(String studentNumber, String name, String className) {
        return studentMapper.searchByMultipleFields(studentNumber, name, className);
    }

    @Override
    public void addStudent(Student student) {
        student.setStatus(0);  // 默认正常状态
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        studentMapper.insert(student);
    }

    @Override
    public void updateStudent(Student student) {
        student.setUpdatedAt(LocalDateTime.now());
        studentMapper.update(student);
    }

    @Override
    public void deleteStudent(Integer studentId) {
        studentMapper.delete(studentId);
    }
}