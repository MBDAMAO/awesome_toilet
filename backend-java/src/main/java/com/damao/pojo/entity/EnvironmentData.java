package com.damao.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentData extends BaseEntity implements Serializable {
    @TableId()
    private Long id;
    private Long toilet;
    private Float temperature;
    private Float humidity;
    private Integer paperUsage;
    private Float electricityUsage;
    private Long createTime;
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
}