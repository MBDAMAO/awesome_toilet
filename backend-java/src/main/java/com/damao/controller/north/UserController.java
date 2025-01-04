package com.damao.controller.north;

import com.damao.common.result.Result;
import com.damao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author damao
 * @version 1.0
 * Create by 2024/11/10 下午12:44
 */
@RestController("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("info")
    public Result<?> getUserInfo(Integer id) {
        return null;
    }

    @PostMapping("update")
    public Result<?> updateUserInfo(Integer id, String name, String password) {
        return null;
    }

    @PostMapping("login")
    public Result<?> login(String username, String password) {
        return null;
    }

    @PostMapping("register")
    public Result<?> register(Integer id, String name, String password) {
        return null;
    }
}
