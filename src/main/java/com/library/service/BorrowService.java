package com.library.service;

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
     * 根据ID查询借阅记录
     */
    BorrowRecord getRecordById(Integer recordId);

    /**
     * 根据学生ID查询借阅记录
     */
    List<BorrowRecord> getRecordsByStudentId(Integer studentId);

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
}