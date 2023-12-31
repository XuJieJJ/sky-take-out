package com.sky.controller.user;


import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信用户登录
 */
@RestController
@RequestMapping("/user/user")
@Api(tags = "微信用户登录接口")
@Slf4j
public class UserController {

    @Autowired
    public UserService userService;
    @Autowired
    public JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("微信用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){

        log.info("微信用户登录..:{}",userLoginDTO);
        User user = userService.wxlogin(userLoginDTO);

        //为用户生成jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

        System.out.println("登录成功");
        return  Result.success(userLoginVO);

    }
}
