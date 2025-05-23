package com.damao.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistryVO implements Serializable {
    private Long uid;
    private String userName;
    private String token;
}
