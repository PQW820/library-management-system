package com.library.service.impl;

import com.library.entity.Book;
import com.library.service.LocalBookDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地图书数据服务实现类
 * 提供一些常见的中文图书数据作为外部API的后备方案
 */
@Service
public class LocalBookDataServiceImpl implements LocalBookDataService {
    
    private static final Logger logger = LoggerFactory.getLogger(LocalBookDataServiceImpl.class);
    
    private static final Map<String, BookData> bookDatabase = new HashMap<>();
    
    static {
        bookDatabase.put("9787532779780", new BookData("百年孤独", "加西亚·马尔克斯", "南海出版公司", "2011-06-01", 1, "魔幻现实主义文学代表作"));
        bookDatabase.put("9787530217746", new BookData("平凡的世界", "路遥", "北京十月文艺出版社", "2017-01-01", 1, "中国当代文学经典"));
        bookDatabase.put("9787020132774", new BookData("红楼梦", "曹雪芹", "人民文学出版社", "2018-02-01", 1, "中国古典四大名著之一"));
        bookDatabase.put("9787020042404", new BookData("三国演义", "罗贯中", "人民文学出版社", "2008-01-01", 3, "中国古典四大名著之一"));
        bookDatabase.put("9787020042503", new BookData("水浒传", "施耐庵", "人民文学出版社", "2008-01-01", 3, "中国古典四大名著之一"));
        bookDatabase.put("9787020056739", new BookData("西游记", "吴承恩", "人民文学出版社", "2008-01-01", 1, "中国古典四大名著之一"));
        bookDatabase.put("9787532769694", new BookData("围城", "钱钟书", "上海文艺出版社", "2018-08-01", 1, "中国现代文学经典"));
        bookDatabase.put("9787532754364", new BookData("子夜", "茅盾", "上海文艺出版社", "2018-08-01", 1, "中国现代文学经典"));
        bookDatabase.put("9787532744148", new BookData("家", "巴金", "上海文艺出版社", "2018-08-01", 1, "激流三部曲之一"));
        bookDatabase.put("9787020002207", new BookData("骆驼祥子", "老舍", "人民文学出版社", "1999-05-01", 1, "中国现代文学经典"));
        bookDatabase.put("9787544244909", new BookData("活着", "余华", "南海出版公司", "2012-08-01", 1, "当代文学经典"));
        bookDatabase.put("9787536692930", new BookData("三体", "刘慈欣", "重庆出版社", "2008-01-01", 4, "中国科幻文学里程碑"));
        bookDatabase.put("9787536699999", new BookData("三体Ⅱ：黑暗森林", "刘慈欣", "重庆出版社", "2008-05-01", 4, "科幻经典"));
        bookDatabase.put("9787536683990", new BookData("三体Ⅲ：死神永生", "刘慈欣", "重庆出版社", "2010-11-01", 4, "科幻经典"));
        bookDatabase.put("9787544282607", new BookData("解忧杂货店", "东野圭吾", "南海出版公司", "2014-05-01", 1, "治愈系小说"));
        bookDatabase.put("9787544261662", new BookData("白夜行", "东野圭吾", "南海出版公司", "2013-01-01", 1, "推理小说经典"));
        bookDatabase.put("9787532747367", new BookData("1984", "乔治·奥威尔", "上海文艺出版社", "2018-08-01", 1, "反乌托邦经典"));
        bookDatabase.put("9787532761239", new BookData("动物农场", "乔治·奥威尔", "上海文艺出版社", "2018-08-01", 1, "政治寓言"));
        bookDatabase.put("9787544777189", new BookData("小王子", "安托万·德·圣-埃克苏佩里", "译林出版社", "2018-06-01", 1, "童话经典"));
        bookDatabase.put("9787532769830", new BookData("了不起的盖茨比", "F·斯科特·菲茨杰拉德", "上海文艺出版社", "2018-08-01", 1, "美国文学经典"));
    }
    
    @Override
    public Book searchBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return null;
        }
        
        String cleanIsbn = isbn.trim().replaceAll("-", "");
        BookData bookData = bookDatabase.get(cleanIsbn);
        
        if (bookData != null) {
            logger.info("Local Book Data: Found book for ISBN: {}", cleanIsbn);
            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(bookData.title);
            book.setAuthor(bookData.author);
            book.setPress(bookData.publisher);
            book.setPublishDate(bookData.publishDate);
            book.setCategoryId(bookData.categoryId);
            book.setDescription(bookData.description);
            book.setTotalNumber(1);
            book.setAvailableNumber(1);
            return book;
        }
        
        logger.info("Local Book Data: No book found for ISBN: {}", cleanIsbn);
        return null;
    }
    
    private static class BookData {
        String title;
        String author;
        String publisher;
        String publishDate;
        Integer categoryId;
        String description;
        
        BookData(String title, String author, String publisher, String publishDate, Integer categoryId, String description) {
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.publishDate = publishDate;
            this.categoryId = categoryId;
            this.description = description;
        }
    }
}
