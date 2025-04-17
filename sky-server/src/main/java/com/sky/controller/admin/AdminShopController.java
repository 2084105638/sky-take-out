package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/16
 * */
@RestController
@RequestMapping("/admin/shop")
@Api(tags = "员工店铺相关接口")
@Slf4j
public class AdminShopController {
    @Resource
    RedisTemplate redisTemplate;

    public static final String KEY = "SHOP_STATUS";

    /**
     * 设置店铺状态
     * @return
     */
    @ApiOperation("设置店铺营业状态")
    @PutMapping("/{status}")
    public Result<String> changeShopStatus(@PathVariable("status") Integer status){
        log.info("设置店铺营业状态{}", status == 1 ? "打烊中" : "营业中");

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY, status);
        return Result.success();
    }

    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer status = (Integer) valueOperations.get(KEY);
        log.info("获取店铺营业状态{}", status == 1 ? "打烊中" : "营业中");
        return Result.success(status);
    }
}
