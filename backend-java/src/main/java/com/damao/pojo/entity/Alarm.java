package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer type;
    private Long toilet;
    private Long device;
    private String message;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}