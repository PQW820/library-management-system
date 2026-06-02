package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 借阅记录归并DTO - 同一学生借同一本书的记录归并显示
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordGroupDTO {
    private Integer studentId;
    private String studentName;
    private String studentNumber;
    private Integer bookId;
    private String bookTitle;
    private String bookIsbn;
    private Integer borrowCount;  // 借阅数量
    private String status;  // 状态（只要有一本未归还就是未归还）
    private LocalDate firstBorrowDate;  // 最早借阅日期
    private LocalDate lastBorrowDate;   // 最近借阅日期
    private List<BorrowRecordDTO> records;  // 包含的详细记录
    
    public BorrowRecordGroupDTO(BorrowRecordDTO record) {
        this.studentId = record.getStudentId();
        this.studentName = record.getStudentName();
        this.studentNumber = record.getStudentNumber();
        this.bookId = record.getBookId();
        this.bookTitle = record.getBookTitle();
        this.bookIsbn = record.getBookIsbn();
        this.borrowCount = 1;
        this.status = record.getStatus();
        this.firstBorrowDate = record.getBorrowDate();
        this.lastBorrowDate = record.getBorrowDate();
        this.records = new ArrayList<>();
        this.records.add(record);
    }
    
    public void merge(BorrowRecordDTO record) {
        this.borrowCount++;
        this.records.add(record);
        // 更新状态：只要有一本未归还就是未归还
        if ("0".equals(record.getStatus())) {
            this.status = "0";
        }
        // 更新最早和最晚借阅日期
        if (record.getBorrowDate().isBefore(this.firstBorrowDate)) {
            this.firstBorrowDate = record.getBorrowDate();
        }
        if (record.getBorrowDate().isAfter(this.lastBorrowDate)) {
            this.lastBorrowDate = record.getBorrowDate();
        }
    }
}
