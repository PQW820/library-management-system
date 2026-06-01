package com.library.service.impl;

import com.library.dto.BorrowRecordDTO;
import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.Student;
import com.library.mapper.BookMapper;
import com.library.mapper.BorrowRecordMapper;
import com.library.mapper.StudentMapper;
import com.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 借阅服务实现类
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<BorrowRecord> getAllRecords() {
        return borrowRecordMapper.selectAll();
    }

    private BorrowRecordDTO convertToDTO(BorrowRecord record) {
        BorrowRecordDTO dto = new BorrowRecordDTO();
        dto.setRecordId(record.getRecordId());
        dto.setStudentId(record.getStudentId());
        dto.setBookId(record.getBookId());
        dto.setBorrowDate(record.getBorrowDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setActualReturnDate(record.getActualReturnDate());
        dto.setStatus(record.getStatus());
        dto.setRemark(record.getRemark());
        
        // 查询学生信息
        Student student = studentMapper.selectById(record.getStudentId());
        if (student != null) {
            dto.setStudentName(student.getName());
            dto.setStudentNumber(student.getStudentNumber());
        }
        
        // 查询图书信息
        Book book = bookMapper.selectById(record.getBookId());
        if (book != null) {
            dto.setBookTitle(book.getTitle());
            dto.setBookIsbn(book.getIsbn());
        }
        
        return dto;
    }

    @Override
    public List<BorrowRecordDTO> getAllRecordsWithDetails() {
        List<BorrowRecord> records = borrowRecordMapper.selectAll();
        List<BorrowRecordDTO> dtos = new ArrayList<>();
        for (BorrowRecord record : records) {
            dtos.add(convertToDTO(record));
        }
        return dtos;
    }

    @Override
    public BorrowRecord getRecordById(Integer recordId) {
        return borrowRecordMapper.selectById(recordId);
    }

    @Override
    public List<BorrowRecord> getRecordsByStudentId(Integer studentId) {
        return borrowRecordMapper.selectByStudentId(studentId);
    }

    @Override
    public List<BorrowRecordDTO> getRecordsByStudentIdWithDetails(Integer studentId) {
        List<BorrowRecord> records = borrowRecordMapper.selectByStudentId(studentId);
        List<BorrowRecordDTO> dtos = new ArrayList<>();
        for (BorrowRecord record : records) {
            dtos.add(convertToDTO(record));
        }
        return dtos;
    }

    @Override
    public List<BorrowRecord> getUnreturnedBooks(Integer studentId) {
        return borrowRecordMapper.selectByStudentIdAndStatus(studentId, "0");
    }

    @Override
    @Transactional
    public boolean borrowBook(Integer studentId, Integer bookId) {
        // 查询图书
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            throw new RuntimeException("图书不存在");
        }

        if (book.getAvailableNumber() <= 0) {
            throw new RuntimeException("图书暂时没有可借的");
        }

        // 创建借阅记录
        BorrowRecord record = new BorrowRecord();
        record.setStudentId(studentId);
        record.setBookId(bookId);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(LocalDate.now().plusDays(30));  // 30天后归还
        record.setStatus("0");  // 0:已借出
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());

        borrowRecordMapper.insert(record);

        // 更新图书可借数量
        bookMapper.updateAvailableNumber(bookId, -1);

        return true;
    }

    @Override
    @Transactional
    public boolean returnBook(Integer recordId) {
        BorrowRecord record = borrowRecordMapper.selectById(recordId);
        if (record == null) {
            throw new RuntimeException("借阅记录不存在");
        }

        if (!"0".equals(record.getStatus())) {
            throw new RuntimeException("该图书已归还");
        }

        // 检查是否超期
        LocalDate now = LocalDate.now();
        if (now.isAfter(record.getReturnDate())) {
            record.setStatus("2");  // 超期状态
        } else {
            record.setStatus("1");  // 1:已归还
        }

        record.setActualReturnDate(now);
        record.setUpdatedAt(LocalDateTime.now());
        borrowRecordMapper.update(record);

        // 更新图书可借数量
        bookMapper.updateAvailableNumber(record.getBookId(), 1);

        return true;
    }

    @Override
    public List<BorrowRecord> getOverdueRecords() {
        List<BorrowRecord> records = borrowRecordMapper.selectUnreturned();
        LocalDate now = LocalDate.now();
        records.removeIf(record -> !now.isAfter(record.getReturnDate()));
        return records;
    }

    @Override
    public List<BorrowRecordDTO> getOverdueRecordsWithDetails() {
        List<BorrowRecord> records = getOverdueRecords();
        List<BorrowRecordDTO> dtos = new ArrayList<>();
        for (BorrowRecord record : records) {
            dtos.add(convertToDTO(record));
        }
        return dtos;
    }
}