package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐数量
     * @param categoryId
     * @return Integer
     */
    @Select("select count(setmeal.id) from sky_take_out.setmeal where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /**
     * 通过菜品id查询套餐id
     * @param DishId
     */
    @Select("select sky_take_out.setmeal_dish.setmeal_id from sky_take_out.setmeal_dish where dish_id=#{DishId}")
    List<Long> countByDishId(Long DishId);
}
