package com.library.service.impl;

import com.library.entity.Book;
import com.library.mapper.BookMapper;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图书服务实现类
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectAll();
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookMapper.selectById(bookId);
    }

    @Override
    public List<Book> getBooksByCategory(Integer categoryId) {
        return bookMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<Book> searchBooks(String title) {
        return bookMapper.selectByTitle(title);
    }

    @Override
    public void addBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        if (book.getAvailableNumber() == null) {
            book.setAvailableNumber(book.getTotalNumber());
        }
        bookMapper.insert(book);
    }

    @Override
    public void updateBook(Book book) {
        book.setUpdatedAt(LocalDateTime.now());
        bookMapper.update(book);
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookMapper.delete(bookId);
    }
}