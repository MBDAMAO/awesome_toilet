package com.damao.pojo.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserRegistryDTO implements Serializable {
    private String username;
    private String password;
}
