package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Resource
    private DishService dishService;



    /**
     * 新增菜品和对应的口味数据
     * @param dishDTO
     */
    @ApiOperation("新增菜品及其对应口味")
    @PostMapping
    public Result<String> save(@RequestBody DishDTO dishDTO){
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 分页获取菜品
     * @param
     */
    @ApiOperation("分页获取菜品")
    @GetMapping("/page")
    public Result<PageResult> getDish(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页获取菜品");
        PageResult dish = dishService.getDish(dishPageQueryDTO);
        return Result.success(dish);
    }
    /**
     * 通过id删除菜品
     * @param ids
     */
    @ApiOperation("通过id删除菜品")
    @DeleteMapping
    public Result<String> deleteById(@RequestParam List<Long> ids){
        log.info("通过id删除菜品");
        dishService.deleteById(ids);
        return Result.success();
    }

    /**
     * 通过id查询菜品
     * @param id
     * @return
     */
    @ApiOperation("通过id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id){
        log.info("通过id查询菜品");
        DishVO dishById = dishService.getDishById(id);
        return Result.success(dishById);
    }


    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @ApiOperation("修改菜品")
    @PutMapping
    public Result<String> changeDish(@RequestBody DishDTO dishDTO){
        log.info("通修改菜品");
        dishService.changeDish(dishDTO);
        return Result.success();
    }
}
