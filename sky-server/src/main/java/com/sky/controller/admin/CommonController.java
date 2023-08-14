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

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file)  {
        log.info("文件上传: {}",file);
        /**
         * TODO 目前存储位置在本地 后期更改至阿里云存储
         */
        //获取文件原始名字
        String originalFilename = file.getOriginalFilename();

        //将文件保存在本地磁盘
        try {
            file.transferTo((new File("E:\\STUDY\\imgs\\"+originalFilename)));
            String path ="E:\\STUDY\\imgs\\"+originalFilename;
            return Result.success(path);
        } catch (IOException e) {
            log.error("文件上传失败",e);
        }

        return  Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
