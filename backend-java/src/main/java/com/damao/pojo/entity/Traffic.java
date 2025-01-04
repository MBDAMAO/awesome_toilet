package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Traffic extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private Long toilet;
    private Long pit;
    private Long device;
    private Integer totalVisitors;
    private Integer maleVisitors;
    private Integer femaleVisitors;
    private Long timestamp;
}