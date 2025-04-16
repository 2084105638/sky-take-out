package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
public interface CategoryService {
    /**
     * 新增分类
     * @param
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分页查询
     * @param
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 通过id删除分类
     * @param
     */
    void deleteCategoryById(Long id);

    /**
     * 修改分类
     * @param
     */
    void changeCategory(CategoryDTO categoryDTO);

    /**
     * 启用，禁用分类
     * @param
     */
    void changeStatus(Integer status,Long id);

    /**
     * 根据类型查询分类
     * @param
     */
    List<Category> list(Integer type);

}
