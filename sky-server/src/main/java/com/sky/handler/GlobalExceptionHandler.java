package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.entity.User;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param exception
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException exception){
        log.error("异常信息：{}", exception.getMessage());
        return Result.error(exception.getMessage());
    }
    /**
     * 处理SQL异常
     * @param exception
     * @return
     */
    public Result exceptionHandler(SQLIntegrityConstraintViolationException exception){
        String message = exception.getMessage();
        //用户名已存在
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
