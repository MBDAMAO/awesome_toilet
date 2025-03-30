package com.damao.pojo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long uid;
    private String name;
    private String account;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}