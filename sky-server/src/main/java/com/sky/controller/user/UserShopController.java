package com.sky.controller.user;

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
@RequestMapping("/user/shop")
@Api(tags = "用户店铺相关接口")
@Slf4j
public class UserShopController {
    public static final String KEY = "SHOP_STATUS";

    @Resource
    RedisTemplate redisTemplate;



    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer status = (Integer) valueOperations.get(KEY);
        log.info("获取店铺营业状态{}", status == 1 ? "打烊中" : "营业中");
        return Result.success(status);
    }
}
