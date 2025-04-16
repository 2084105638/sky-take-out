package com.sky.service;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.ArrayList;
import java.util.List;

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
    void deleteById(List<Long> id);

    /**
     * 通过id查询菜品
     * @param id
     * @return
     */
    DishVO getDishById(Long id);

    /**
     * 修改菜品
     * @param dish
     */
    void changeDish(DishDTO dish);

}
