package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 图书实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer bookId;
    private String isbn;  // ISBN号
    private String title;  // 书名
    private String author;  // 作者
    private String press;  // 出版社
    private String publishDate;  // 出版日期
    private Integer categoryId;  // 分类ID
    private Integer totalNumber;  // 总数量
    private Integer availableNumber;  // 可借数量
    private String description;  // 描述
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间
}