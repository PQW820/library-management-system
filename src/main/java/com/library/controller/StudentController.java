package com.library.controller;

import com.library.dto.ResponseResult;
import com.library.entity.Student;
import com.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 学生管理控制器
 */
@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 获取所有学生
     */
    @GetMapping
    public ResponseResult<List<Student>> listStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseResult.success(students);
    }

    /**
     * 根据ID获取学生
     */
    @GetMapping("/{id}")
    public ResponseResult<Student> getStudent(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseResult.error(404, "学生不存在");
        }
        return ResponseResult.success(student);
    }

    /**
     * 根据班级查询学生
     */
    @GetMapping("/class/{className}")
    public ResponseResult<List<Student>> getStudentsByClass(@PathVariable String className) {
        List<Student> students = studentService.getStudentsByClass(className);
        return ResponseResult.success(students);
    }

    /**
     * 多字段组合查询学生
     */
    @GetMapping("/search")
    public ResponseResult<List<Student>> searchStudents(
            @RequestParam(required = false) String studentNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className) {
        List<Student> students = studentService.searchStudents(studentNumber, name, className);
        return ResponseResult.success(students);
    }

    /**
     * 添加学生
     */
    @PostMapping
    public ResponseResult<String> addStudent(@RequestBody Student student) {
        try {
            studentService.addStudent(student);
            return ResponseResult.success("学生添加成功");
        } catch (Exception e) {
            return ResponseResult.error("学生添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新学生
     */
    @PutMapping("/{id}")
    public ResponseResult<String> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        try {
            student.setStudentId(id);
            studentService.updateStudent(student);
            return ResponseResult.success("学生更新成功");
        } catch (Exception e) {
            return ResponseResult.error("学生更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/{id}")
    public ResponseResult<String> deleteStudent(@PathVariable Integer id) {
        try {
            studentService.deleteStudent(id);
            return ResponseResult.success("学生删除成功");
        } catch (Exception e) {
            return ResponseResult.error("学生删除失败: " + e.getMessage());
        }
    }
}