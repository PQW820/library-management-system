package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 借阅记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {
    private Integer recordId;  // 借阅记录ID
    private Integer studentId;  // 学生ID
    private Integer bookId;  // 图书ID
    private LocalDate borrowDate;  // 借阅日期
    private LocalDate returnDate;  // 应还日期
    private LocalDate actualReturnDate;  // 实际还书日期
    private String status;  // 状态 (0:已借出, 1:已归还, 2:超期)
    private String remark;  // 备注
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间
}