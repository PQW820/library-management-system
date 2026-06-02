package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 借阅记录详情DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordDTO {
    private Integer recordId;
    private Integer studentId;
    private String studentName;
    private String studentNumber;
    private Integer bookId;
    private String bookTitle;
    private String bookIsbn;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;
    private String status;
    private String remark;
}