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
public class Comment extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long toilet;
    private Long area;
    private String username;
    private String suggestion;
    private Integer type;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}