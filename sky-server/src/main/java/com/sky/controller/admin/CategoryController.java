package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param
     * @return
     */
    @ApiOperation("新增分类接口")
    @PostMapping
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类:{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param
     * @return
     */
    @ApiOperation("分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询:{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 按id删除分类
     * @param
     * @return
     */
    @ApiOperation("按id删除分类")
    @DeleteMapping
    public Result<String> deleteById(Long id){
        log.info("删除分类:{}",id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改分类
     * @param
     * @return
     */
    @ApiOperation("修改分类")
    @PutMapping
    public Result<String> changeCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类" );
        categoryService.changeCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 修改分类状态
     * @param
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改分类状态")
    public Result<String> changeStatus(@PathVariable("status") Integer status, Long id){
        log.info("修改分类状态" );
        categoryService.changeStatus(status,id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param
     * @return
     */
    @ApiOperation("根据类型查询分类(用于菜品和套餐查询,1是菜品。2,是套餐)")
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        log.info("根据类型查询分类" );
        return Result.success(categoryService.list(type));
    }
}
