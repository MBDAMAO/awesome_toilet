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
public class LoginHistory extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private String ipAddress;
    private String location;
    private String platform;
    private LocalDateTime time;
}