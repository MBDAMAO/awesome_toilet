package com.damao.pojo.vo.user;


import com.damao.pojo.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends BaseEntity implements Serializable {
    private Long uid;
    private String username;
    private String email;
    private String password;
    private String avatar;
}
