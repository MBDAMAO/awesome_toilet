package com.damao.pojo.entity.stat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/4/7 下午4:04
 */
@Data
public class PaperUsageWeekly implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long user;
    private Long toilet;
    private Float paperUsage;
    private Long device;
    private LocalDateTime timestamp;
}
