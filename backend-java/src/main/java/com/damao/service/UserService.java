package com.damao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damao.common.exception.user.AccountNotFoundException;
import com.damao.mapper.UserAuthMapper;
import com.damao.mapper.UserMapper;
import com.damao.pojo.dto.user.UserLoginDTO;
import com.damao.pojo.entity.User;
import com.damao.pojo.entity.UserAuth;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author damao
 * @version 1.0
 * Create by 2024/11/10 下午12:45
 */
@Service
public class UserService {

    @Autowired
    UserAuthMapper userAuthMapper;

    public UserAuth login(UserLoginDTO userLoginDTO) {
        String password = userLoginDTO.getPassword();
        String account = userLoginDTO.getAccount();
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        UserAuth userAuth = userAuthMapper.selectOne(queryWrapper);
        if (userAuth == null) {
            throw new AccountNotFoundException("账号或密码错误");
        }
        if (!Objects.equals(password, userAuth.getPassword())) {
            throw new AccountNotFoundException("账号或密码错误");
        }
        return userAuth;
    }

//    public User registry(UserRegistryDTO userRegistryDTO) {
//        String username = userRegistryDTO.getUsername();
//        User user = userMapper.getByUsername(username);
//        if (user != null) {
//            throw new UserAlreadyExistsException("用户名已存在");
//        }
//        User newUser = new User();
//
//        BeanUtils.copyProperties(userRegistryDTO, newUser);
//        newUser.setAvatar("default png");
//        String password = userRegistryDTO.getPassword();
//        String newPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
//        newUser.setPassword(newPassword);
//        userMapper.insert(newUser);
//        BaseContext.setCurrentId(newUser.getUid());
//        // 成功插入后再发邮件
//        String email = userRegistryDTO.getEmail();
//        SendEmailDTO send = new SendEmailDTO();
//        send.setEmail(email);
//        send.setUid(newUser.getUid());
//        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE, "123", send);
//        return newUser;
//    }
}
