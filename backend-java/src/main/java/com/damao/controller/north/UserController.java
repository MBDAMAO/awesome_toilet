package com.damao.controller.north;

import com.damao.common.context.BaseContext;
import com.damao.common.properties.JwtProperties;
import com.damao.common.result.Result;
import com.damao.common.utils.IpUtils;
import com.damao.common.utils.JwtUtil;
import com.damao.mapper.LoginHistoryMapper;
import com.damao.mapper.UserMapper;
import com.damao.pojo.dto.user.UserLoginDTO;
import com.damao.pojo.entity.LoginHistory;
import com.damao.pojo.entity.User;
import com.damao.pojo.entity.UserAuth;
import com.damao.pojo.vo.user.UserLoginVO;
import com.damao.service.UserService;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginHistoryMapper loginHistoryMapper;

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Autowired
    public UserController(UserService userService, JwtProperties jwtProperties) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
    }

    @GetMapping("/info")
    public Result<?> getUserInfo(Integer id) {
        User user = userMapper.selectById(id);
        return Result.success(user);
    }

    @GetMapping("/self")
    public Result<?> getSelfUserInfo() {
        Long id = BaseContext.getCurrentUid();
        User user = userMapper.selectById(id);
        return Result.success(user);
    }

    @GetMapping("/verify")
    public Result<?> verifyToken() {
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateUserInfo(@RequestBody User user) {
        Long id = BaseContext.getCurrentUid();
        user.setUid(id);
        userMapper.updateById(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<?> login(
            @RequestBody @Validated UserLoginDTO userLoginDTO,
            HttpServletRequest request) {  // 注入 HttpServletRequest 获取客户端信息
        log.info("用户登录(●'◡'●){}", userLoginDTO.getAccount());
        UserAuth user = userService.login(userLoginDTO);

        // 生成 JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getUid());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        // 构建返回数据
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .uid(user.getUid())
                .token(token)
                .build();

        // 获取客户端信息
        String ipAddress = IpUtils.getIp(request);  // 获取 IP
        UserAgent ua = IpUtils.getUserAgent(request);  // 解析设备类型
        String location = IpUtils.getCityInfo(ipAddress);  // 获取 IP 地理位置（可选）

        // 记录登录历史
        LoginHistory loginHistory = LoginHistory.builder()
                .user(user.getUid())
                .ipAddress(ipAddress)
                .location(location)
                .platform(ua.toString())
                .time(LocalDateTime.now())
                .build();
        loginHistoryMapper.insert(loginHistory);

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
