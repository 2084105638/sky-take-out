package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("select * from sky_take_out.setmeal where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
