package com.sky.service;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    /**
     * 新增菜品和对应的口味数据
     * @param dish
     */
    void saveWithFlavor(DishDTO dish);

    /**
     * 分页获取菜品
     * @param
     */
    PageResult getDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 通过id删除菜品
     * @param id
     */
    public void deleteById(Integer id);

}
