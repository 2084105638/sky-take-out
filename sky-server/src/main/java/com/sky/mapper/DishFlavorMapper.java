package com.sky.mapper;

import com.sky.entity.DishFlavor;
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
//    @AutoFill(OperationType.INSERT)
    void insertFlavor(List<DishFlavor> flavors, long id);
}
