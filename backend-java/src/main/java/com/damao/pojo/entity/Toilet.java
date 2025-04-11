package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("toilet")
public class Toilet extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long owner;
    private String name;
    private String location;
    private Integer status;
    private String description;
    private String designMap;
    private Integer devices;
    private Integer pits;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}