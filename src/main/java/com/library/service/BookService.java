package com.library.service;

import com.library.entity.Book;
import java.util.List;

/**
 * 图书服务接口
 */
public interface BookService {
    /**
     * 查询所有图书
     */
    List<Book> getAllBooks();

    /**
     * 根据ID查询图书
     */
    Book getBookById(Integer bookId);

    /**
     * 根据分类查询图书
     */
    List<Book> getBooksByCategory(Integer categoryId);

    /**
     * 根据书名查询图书
     */
    List<Book> searchBooks(String title);

    /**
     * 根据书名和分类组合查询图书
     */
    List<Book> searchBooks(String title, Integer categoryId);

    /**
     * 多字段组合查询图书
     */
    List<Book> searchBooks(String title, String isbn, String author, String press, Integer categoryId);

    /**
     * 添加图书
     */
    void addBook(Book book);

    /**
     * 更新图书
     */
    void updateBook(Book book);

    /**
     * 删除图书
     */
    void deleteBook(Integer bookId);
}