package com.damao.pojo.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
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

    private String avatar;
    private String description;
    private String phone;
    private LocalDate birthday;
    private String gender;
    private String status;
    private String role;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}