package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pit extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private Long toilet;
    private Long type;
    private String coordinates;
    private String description;
    private Long status;
    private Long createTime;
}