package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/4/8 下午4:21
 */
@Data
public class EnvThreshold implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long toilet;
    private String parameterName;
    private Float minThreshold;
    private Float maxThreshold;
    private Boolean alertEnabled;
    private String alertMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
