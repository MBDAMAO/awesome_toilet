package com.damao.pojo.entity;

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
    @TableId()
    private Long id;
    private Integer type;
    private Long toilet;
    private Long device;
    private String message;
    private LocalDateTime createTime;
}