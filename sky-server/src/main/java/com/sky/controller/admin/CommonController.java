package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    /**
     * 文件上传
     * @param file
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) throws IOException {
      log.info("文件上传:{}", file);
      if(file == null){
          System.out.println("上传失败，文件为空");
          return Result.error(MessageConstant.UPLOAD_FAILED);
      }
      File file1 = new File("/Users/Zhuanz1/work/Java/sky-take-out/sky-server/src/main/resources/update.png");

      file.transferTo(file1);
      return Result.success();
    }
}
