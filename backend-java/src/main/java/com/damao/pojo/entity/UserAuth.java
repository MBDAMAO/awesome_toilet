package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_auth")
public class UserAuth extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long uid;
    private String password;
    private String account;
}
