package com.library.mapper;

import com.library.dto.BorrowRecordDTO;
import com.library.entity.BorrowRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 借阅记录Mapper接口
 */
public interface BorrowRecordMapper {
    /**
     * 查询所有借阅记录
     */
    List<BorrowRecord> selectAll();

    /**
     * 查询所有借阅记录（含详情）
     */
    List<BorrowRecordDTO> selectAllWithDetails();

    /**
     * 根据ID查询借阅记录
     */
    BorrowRecord selectById(Integer recordId);

    /**
     * 根据学生ID查询借阅记录
     */
    List<BorrowRecord> selectByStudentId(@Param("studentId") Integer studentId);

    /**
     * 根据学生ID查询借阅记录（含详情）
     */
    List<BorrowRecordDTO> selectByStudentIdWithDetails(@Param("studentId") Integer studentId);

    /**
     * 根据学生ID和状态查询借阅记录
     */
    List<BorrowRecord> selectByStudentIdAndStatus(@Param("studentId") Integer studentId, @Param("status") String status);

    /**
     * 根据图书ID查询借阅记录
     */
    List<BorrowRecord> selectByBookId(@Param("bookId") Integer bookId);

    /**
     * 查询未归还的记录
     */
    List<BorrowRecord> selectUnreturned();

    /**
     * 查询超期记录（含详情）
     */
    List<BorrowRecordDTO> selectOverdueWithDetails();

    /**
     * 插入借阅记录
     */
    int insert(BorrowRecord record);

    /**
     * 更新借阅记录
     */
    int update(BorrowRecord record);

    /**
     * 删除借阅记录
     */
    int delete(Integer recordId);
}