package com.library.mapper;

import com.library.entity.Student;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 学生Mapper接口
 */
public interface StudentMapper {
    /**
     * 查询所有学生
     */
    List<Student> selectAll();

    /**
     * 根据ID查询学生
     */
    Student selectById(Integer studentId);

    /**
     * 根据学号查询学生
     */
    Student selectByStudentNumber(@Param("studentNumber") String studentNumber);

    /**
     * 根据班级查询学生
     */
    List<Student> selectByClassName(@Param("className") String className);

    /**
     * 多字段组合查询学生
     */
    List<Student> searchByMultipleFields(@Param("studentNumber") String studentNumber, 
                                         @Param("name") String name, 
                                         @Param("className") String className);

    /**
     * 插入学生
     */
    int insert(Student student);

    /**
     * 更新学生
     */
    int update(Student student);

    /**
     * 删除学生
     */
    int delete(Integer studentId);
}