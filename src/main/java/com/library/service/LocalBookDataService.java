package com.library.service;

import com.library.entity.Book;

/**
 * 本地图书数据服务接口
 */
public interface LocalBookDataService {
    
    /**
     * 通过ISBN查询本地图书数据
     * @param isbn ISBN编号
     * @return Book对象，如果没有找到返回null
     */
    Book searchBookByIsbn(String isbn);
}
