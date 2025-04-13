package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return Integer
     */
    @Select("select * from sky_take_out.dish where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
