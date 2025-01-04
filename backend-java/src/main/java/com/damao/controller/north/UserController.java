package com.damao.controller.north;

import com.damao.common.properties.JwtProperties;
import com.damao.common.result.Result;
import com.damao.common.utils.JwtUtil;
import com.damao.pojo.dto.user.UserLoginDTO;
import com.damao.pojo.entity.UserAuth;
import com.damao.pojo.vo.user.UserLoginVO;
import com.damao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Autowired
    public UserController(UserService userService, JwtProperties jwtProperties) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
    }

    @GetMapping("/info")
    public Result<?> getUserInfo(Integer id) {
        return null;
    }

    @PostMapping("/update")
    public Result<?> updateUserInfo(Integer id, String name, String password) {
        return null;
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        log.info("用户登录(●'◡'●){}", userLoginDTO.getAccount());
        UserAuth user = userService.login(userLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getUid());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .uid(user.getUid())
                .token(token)
                .build();

        return Result.success(userLoginVO, "登录成功");
    }

//    @PostMapping("/registry")
//    public Result<?> registry(@RequestBody @Validated UserRegistryDTO userRegistryDTO) {
//        log.info("用户注册(●'◡'●){}", userRegistryDTO.getEmail());
//        User user = userService.registry(userRegistryDTO);
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("user_id", user.getUid());
//
//        String token = JwtUtil.createJWT(
//                jwtProperties.getAdminSecretKey(),
//                jwtProperties.getAdminTtl(),
//                claims);
//
//        UserRegistryVO userRegistryVO = UserRegistryVO.builder()
//                .token(token)
//                .uid(user.getUid())
//                .userName(user.getName())
//                .build();
//
//        return Result.success(userRegistryVO, "注册成功");
//    }
}
