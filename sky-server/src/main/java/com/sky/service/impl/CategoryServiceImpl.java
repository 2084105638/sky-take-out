package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;
    @Resource
    DishMapper dishMapper;
    @Resource
    SetmealMapper setmealMapper;

    /**
     * 新增分类
     * @param
     */
    public void save(CategoryDTO categoryDTO){
        //拷贝属性
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //添加其他属性
        category.setStatus(StatusConstant.DISABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        //调用Mapper进行添加
        categoryMapper.insert(category);
    }

    /**
     * 分页查询
     * @param
     */
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        //开启分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> categories = categoryMapper.pageQuery(categoryPageQueryDTO);

        return new PageResult(categories.getPageNum(),categories.getResult());
    }

    /**
     * 通过id删除分类
     * @param
     */
    public void deleteById(Long id){
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        count = setmealMapper.countByCategoryId(id);
        if(count > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    }

    /**
     * 修改分类
     * @param
     */
    public void changeCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.changeCategory(category);
    }

    /**
     * 启用，禁用分类
     * @param
     */
    public void changeStatus(Integer status,Long id){
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.changeCategory(category);
    }

    /**
     * 根据类型查询分类
     * @param
     */
    public List<Category> list(Integer type){
        return categoryMapper.list(type);
    }
}
