package com.damao.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DataSendDTO implements Serializable {
    private Long toilet;
    private Float temperature;
    private Float humidity;
    private Integer paperUsage;
    private Float electricity_usage;
    private String free1;
    private String free2;
    private String free3;
    private String free4;
    private String free5;
    private String free6;
    private String free7;
    private String free8;
    private String free9;
    private String free10;
    private String free11;
    private String free12;
    private String free13;
    private String free14;
    private String free15;
    private LocalDateTime createTime;
}
