package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@Mapper
public interface CategoryMapper {
    /**
     * 插入套餐数据
     * @param category
     * @return Integer
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into sky_take_out.category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "VALUES" +
            "(#{type},#{name}#{sort}#{status}#{createTime}#{updateTime}#{createUser}#{updateUser}#{type}#{type}#{type}#{type}#{type}#{type}#{type})")
    void insert(Category category);

    /**
     * 分页查询套餐数据
     * @param categoryPageQueryDTO
     * @return Integer
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 删除套餐数据
     * @param id
     * @return Integer
     */

    @Delete("delete from sky_take_out.category where id=#{id}")
    void deleteById(Long id);

    /**
     * 修改套餐数据
     * @param category
     * @return Integer
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    /**
     * 根据类型查询分类
     * @param type
     * @return Integer
     */
    List<Category> list(Integer type);
}
