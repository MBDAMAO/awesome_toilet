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
public class EnvironmentData extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private Long user;
    private Long toilet;
    private Float temperature;
    private Float humidity;
    private Float ammonia;
    private Float pm2d5;
    private Float pm10;
    private Float pressure;
    private Float h2s;
    private Float co2;
    private Float tvoc;
    private LocalDateTime timestamp;
}