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
public class Comment extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private Long toilet;
    private Long area;
    private String suggestion;
    private Integer type;
    private LocalDateTime createTime;
}