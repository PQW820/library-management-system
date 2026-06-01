package com.library.controller;

import com.library.dto.ResponseResult;
import com.library.entity.Category;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 分类管理控制器
 */
@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类
     */
    @GetMapping
    public ResponseResult<List<Category>> listCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseResult.success(categories);
    }

    /**
     * 根据ID获取分类
     */
    @GetMapping("/{id}")
    public ResponseResult<Category> getCategory(@PathVariable Integer id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseResult.error(404, "分类不存在");
        }
        return ResponseResult.success(category);
    }

    /**
     * 添加分类
     */
    @PostMapping
    public ResponseResult<String> addCategory(@RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            return ResponseResult.success("分类添加成功");
        } catch (Exception e) {
            return ResponseResult.error("分类添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新分类
     */
    @PutMapping("/{id}")
    public ResponseResult<String> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        try {
            category.setCategoryId(id);
            categoryService.updateCategory(category);
            return ResponseResult.success("分类更新成功");
        } catch (Exception e) {
            return ResponseResult.error("分类更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public ResponseResult<String> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseResult.success("分类删除成功");
        } catch (Exception e) {
            return ResponseResult.error("分类删除失败: " + e.getMessage());
        }
    }
}