package com.damao.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TrafficDataDTO implements Serializable {
    private Long toilet;
    private Long pit;
    private Long device;
    private Integer totalVisitors;
    private Integer maleVisitors;
    private Integer femaleVisitors;
    private LocalDateTime timestamp;
}
