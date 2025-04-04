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
public class Message extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private Long user;
    private String message;
    private Boolean isRead;
    private LocalDateTime createTime;
}