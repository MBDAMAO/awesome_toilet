package com.damao.mqtt;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/4/8 上午11:32
 */
@Data
public class MqttMessage implements Serializable {
    private String device;
    private String type;
    private LocalDateTime timestamp;
    private Map<String, Object> data;
}
