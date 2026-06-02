package com.library.service;

import com.library.entity.Book;

/**
 * 豆瓣图书API服务接口
 */
public interface DoubanBookService {
    
    /**
     * 通过ISBN查询豆瓣图书信息
     * @param isbn ISBN编号
     * @return Book对象，如果查询失败返回null
     */
    Book searchBookByIsbn(String isbn);
}
