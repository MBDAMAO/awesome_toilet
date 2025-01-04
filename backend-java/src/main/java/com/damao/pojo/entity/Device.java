package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private Long owner;
    private Long toilet;
    private String name;
    private String description;
    private Integer status;
    private Long createTime;
    private Long updateTime;
}