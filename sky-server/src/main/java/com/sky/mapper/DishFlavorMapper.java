package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@Mapper
public interface DishFlavorMapper {
    /**
     * 添加菜品口味
     * @param
     * @return Integer
     */
    void insertFlavor(List<DishFlavor> flavors, long id);

    /**
     * 通过菜品id删除对应口味
     * @param dishId
     */
    @Delete("delete from sky_take_out.dish_flavor where dish_id=#{dishId}")
    void deleteByDishId(Long dishId);
}
