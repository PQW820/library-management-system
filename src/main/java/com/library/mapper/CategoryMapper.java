package com.library.mapper;

import com.library.entity.Category;
import java.util.List;

/**
 * 分类Mapper接口
 */
public interface CategoryMapper {
    /**
     * 查询所有分类
     */
    List<Category> selectAll();

    /**
     * 根据ID查询分类
     */
    Category selectById(Integer categoryId);

    /**
     * 插入分类
     */
    int insert(Category category);

    /**
     * 更新分类
     */
    int update(Category category);

    /**
     * 删除分类
     */
    int delete(Integer categoryId);
}