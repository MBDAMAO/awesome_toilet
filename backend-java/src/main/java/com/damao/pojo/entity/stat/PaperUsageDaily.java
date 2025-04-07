package com.damao.pojo.entity.stat;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/4/7 下午4:03
 */
@Data
public class PaperUsageDaily implements Serializable {
    private Long id;
    private Long user;
    private Long toilet;
    private Long paperUsage;
    private Long device;
    private LocalDateTime timestamp;
}
