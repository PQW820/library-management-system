package com.library.service;

import com.library.dto.BorrowRecordDTO;
import com.library.dto.BorrowRecordGroupDTO;
import com.library.entity.BorrowRecord;
import java.util.List;

/**
 * 借阅服务接口
 */
public interface BorrowService {
    /**
     * 查询所有借阅记录
     */
    List<BorrowRecord> getAllRecords();

    /**
     * 查询所有借阅记录（含详情）
     */
    List<BorrowRecordDTO> getAllRecordsWithDetails();

    /**
     * 根据ID查询借阅记录
     */
    BorrowRecord getRecordById(Integer recordId);

    /**
     * 根据学生ID查询借阅记录
     */
    List<BorrowRecord> getRecordsByStudentId(Integer studentId);

    /**
     * 根据学生ID查询借阅记录（含详情）
     */
    List<BorrowRecordDTO> getRecordsByStudentIdWithDetails(Integer studentId);

    /**
     * 获取学生未归还的图书
     */
    List<BorrowRecord> getUnreturnedBooks(Integer studentId);

    /**
     * 借书
     */
    boolean borrowBook(Integer studentId, Integer bookId);

    /**
     * 还书
     */
    boolean returnBook(Integer recordId);

    /**
     * 获取所有超期未归还的记录
     */
    List<BorrowRecord> getOverdueRecords();

    /**
     * 获取所有超期未归还的记录（含详情）
     */
    List<BorrowRecordDTO> getOverdueRecordsWithDetails();

    /**
     * 获取所有借阅记录（归并后，同一学生借同一本书的记录合并显示）
     */
    List<BorrowRecordGroupDTO> getAllRecordsGrouped();

    /**
     * 根据学生ID获取借阅记录（归并后）
     */
    List<BorrowRecordGroupDTO> getRecordsGroupedByStudentId(Integer studentId);

    /**
     * 分页查询借阅记录（含详情）
     */
    List<BorrowRecordDTO> getRecordsWithDetailsByPage(Integer studentId, Integer page, Integer size);

    /**
     * 查询借阅记录总数
     */
    int getRecordsCount(Integer studentId);
}
