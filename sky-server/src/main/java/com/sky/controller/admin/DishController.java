package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}
