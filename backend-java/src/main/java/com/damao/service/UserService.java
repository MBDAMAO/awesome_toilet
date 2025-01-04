package com.damao.service;

import com.damao.pojo.entity.User;
import org.springframework.beans.BeanUtils;
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
    public User login(UserLoginDTO userLoginDTO) {
        String password = userLoginDTO.getPassword();
        String username = userLoginDTO.getUsername();
        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new AccountNotFoundException("账号或密码错误");
        }
        if (!Objects.equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)), user.getPassword())) {
            throw new PasswordErrorException("账号或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new UserStatusErrorException("用户状态不正常");
        }
        return user;
    }

    public User registry(UserRegistryDTO userRegistryDTO) {
        String username = userRegistryDTO.getUsername();
        User user = userMapper.getByUsername(username);
        if (user != null) {
            throw new UserAlreadyExistsException("用户名已存在");
        }
        User newUser = new User();

        BeanUtils.copyProperties(userRegistryDTO, newUser);
        newUser.setAvatar("default png");
        String password = userRegistryDTO.getPassword();
        String newPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        newUser.setPassword(newPassword);
        userMapper.insert(newUser);
        BaseContext.setCurrentId(newUser.getUid());
        // 成功插入后再发邮件
        String email = userRegistryDTO.getEmail();
        SendEmailDTO send = new SendEmailDTO();
        send.setEmail(email);
        send.setUid(newUser.getUid());
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE, "123", send);
        return newUser;
    }
}
