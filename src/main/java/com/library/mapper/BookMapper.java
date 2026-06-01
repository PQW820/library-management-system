package com.library.mapper;

import com.library.entity.Book;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 图书Mapper接口
 */
public interface BookMapper {
    /**
     * 查询所有图书
     */
    List<Book> selectAll();

    /**
     * 根据ID查询图书
     */
    Book selectById(Integer bookId);

    /**
     * 根据分类ID查询图书
     */
    List<Book> selectByCategoryId(Integer categoryId);

    /**
     * 根据书名查询图书
     */
    List<Book> selectByTitle(@Param("title") String title);

    /**
     * 根据书名和分类组合查询图书
     */
    List<Book> selectByTitleAndCategory(@Param("title") String title, @Param("categoryId") Integer categoryId);

    /**
     * 多字段组合查询图书
     */
    List<Book> searchByMultipleFields(@Param("title") String title, @Param("isbn") String isbn, 
                                      @Param("author") String author, @Param("press") String press, 
                                      @Param("categoryId") Integer categoryId);

    /**
     * 插入图书
     */
    int insert(Book book);

    /**
     * 更新图书
     */
    int update(Book book);

    /**
     * 删除图书
     */
    int delete(Integer bookId);

    /**
     * 更新可借数量
     */
    int updateAvailableNumber(@Param("bookId") Integer bookId, @Param("count") Integer count);
}
