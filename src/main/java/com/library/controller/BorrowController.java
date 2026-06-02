package com.library.controller;

import com.library.dto.BorrowRecordDTO;
import com.library.dto.BorrowRecordGroupDTO;
import com.library.dto.ResponseResult;
import com.library.entity.BorrowRecord;
import com.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借阅管理控制器
 */
@RestController
@RequestMapping("/borrows")
@CrossOrigin
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    /**
     * 获取所有借阅记录（含详情）
     */
    @GetMapping
    public ResponseResult<List<BorrowRecordDTO>> listRecords() {
        List<BorrowRecordDTO> records = borrowService.getAllRecordsWithDetails();
        return ResponseResult.success(records);
    }

    /**
     * 分页查询借阅记录（含详情）
     */
    @GetMapping("/page")
    public ResponseResult<Map<String, Object>> listRecordsByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer studentId) {
        List<BorrowRecordGroupDTO> groupedRecords = studentId != null ? 
            borrowService.getRecordsGroupedByStudentId(studentId) : 
            borrowService.getAllRecordsGrouped();
        
        int total = groupedRecords.size();
        int start = (page - 1) * size;
        int end = Math.min(start + size, total);
        List<BorrowRecordGroupDTO> pageRecords = start < total ? groupedRecords.subList(start, end) : new java.util.ArrayList<>();
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", pageRecords);
        result.put("total", total);
        return ResponseResult.success(result);
    }

    /**
     * 根据学生ID查询借阅记录（含详情）
     */
    @GetMapping("/student/{studentId}")
    public ResponseResult<List<BorrowRecordDTO>> getRecordsByStudent(@PathVariable Integer studentId) {
        List<BorrowRecordDTO> records = borrowService.getRecordsByStudentIdWithDetails(studentId);
        return ResponseResult.success(records);
    }

    /**
     * 获取学生未归还的图书
     */
    @GetMapping("/unreturned/{studentId}")
    public ResponseResult<List<BorrowRecord>> getUnreturnedBooks(@PathVariable Integer studentId) {
        List<BorrowRecord> records = borrowService.getUnreturnedBooks(studentId);
        return ResponseResult.success(records);
    }

    /**
     * 获取所有超期未归还的记录（含详情）
     */
    @GetMapping("/overdue")
    public ResponseResult<List<BorrowRecordDTO>> getOverdueRecords() {
        List<BorrowRecordDTO> records = borrowService.getOverdueRecordsWithDetails();
        return ResponseResult.success(records);
    }

    /**
     * 获取所有借阅记录（归并后）
     */
    @GetMapping("/grouped")
    public ResponseResult<List<BorrowRecordGroupDTO>> listRecordsGrouped() {
        List<BorrowRecordGroupDTO> records = borrowService.getAllRecordsGrouped();
        return ResponseResult.success(records);
    }

    /**
     * 根据学生ID查询借阅记录（归并后）
     */
    @GetMapping("/grouped/student/{studentId}")
    public ResponseResult<List<BorrowRecordGroupDTO>> getRecordsGroupedByStudent(@PathVariable Integer studentId) {
        List<BorrowRecordGroupDTO> records = borrowService.getRecordsGroupedByStudentId(studentId);
        return ResponseResult.success(records);
    }

    /**
     * 借书
     */
    @PostMapping("/borrow")
    public ResponseResult<String> borrowBook(@RequestParam Integer studentId, @RequestParam Integer bookId) {
        try {
            boolean success = borrowService.borrowBook(studentId, bookId);
            if (success) {
                return ResponseResult.success("借书成功");
            } else {
                return ResponseResult.error("借书失败");
            }
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 还书
     */
    @PostMapping("/return/{recordId}")
    public ResponseResult<String> returnBook(@PathVariable Integer recordId) {
        try {
            boolean success = borrowService.returnBook(recordId);
            if (success) {
                return ResponseResult.success("还书成功");
            } else {
                return ResponseResult.error("还书失败");
            }
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
    }
}
