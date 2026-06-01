package com.library.service;

import com.library.entity.Category;
import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {
    /**
     * 查询所有分类
     */
    List<Category> getAllCategories();

    /**
     * 根据ID查询分类
     */
    Category getCategoryById(Integer categoryId);

    /**
     * 添加分类
     */
    void addCategory(Category category);

    /**
     * 更新分类
     */
    void updateCategory(Category category);

    /**
     * 删除分类
     */
    void deleteCategory(Integer categoryId);
}