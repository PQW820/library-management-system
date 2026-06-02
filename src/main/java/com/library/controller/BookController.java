package com.library.controller;

import com.library.dto.ResponseResult;
import com.library.entity.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 图书管理控制器
 */
@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 获取所有图书
     */
    @GetMapping
    public ResponseResult<List<Book>> listBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseResult.success("查询成功", books);
    }

    /**
     * 根据ID获取图书
     */
    @GetMapping("/{id}")
    public ResponseResult<Book> getBook(@PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseResult.error(404, "图书不存在");
        }
        return ResponseResult.success(book);
    }

    /**
     * 根据分类查询图书
     */
    @GetMapping("/category/{categoryId}")
    public ResponseResult<List<Book>> getBooksByCategory(@PathVariable Integer categoryId) {
        List<Book> books = bookService.getBooksByCategory(categoryId);
        return ResponseResult.success(books);
    }

    /**
     * 根据ISBN查询图书（用于自动识别）
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseResult<Book> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book == null) {
            return ResponseResult.error(404, "未找到该ISBN对应的图书");
        }
        return ResponseResult.success(book);
    }

    /**
     * 搜索图书（支持多字段组合搜索）
     */
    @GetMapping("/search")
    public ResponseResult<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String press,
            @RequestParam(required = false) Integer categoryId) {
        List<Book> books = bookService.searchBooks(title, isbn, author, press, categoryId);
        return ResponseResult.success(books);
    }

    /**
     * 添加图书
     */
    @PostMapping
    public ResponseResult<String> addBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return ResponseResult.success("图书添加成功");
        } catch (Exception e) {
            return ResponseResult.error("图书添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新图书
     */
    @PutMapping("/{id}")
    public ResponseResult<String> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        try {
            book.setBookId(id);
            bookService.updateBook(book);
            return ResponseResult.success("图书更新成功");
        } catch (Exception e) {
            return ResponseResult.error("图书更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除图书
     */
    @DeleteMapping("/{id}")
    public ResponseResult<String> deleteBook(@PathVariable Integer id) {
        try {
            bookService.deleteBook(id);
            return ResponseResult.success("图书删除成功");
        } catch (Exception e) {
            return ResponseResult.error("图书删除失败: " + e.getMessage());
        }
    }
}