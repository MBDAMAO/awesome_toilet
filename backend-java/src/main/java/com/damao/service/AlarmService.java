package com.damao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damao.mapper.EnvThresholdMapper;
import com.damao.pojo.entity.EnvThreshold;
import com.damao.pojo.entity.EnvironmentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlarmService {

    @Autowired
    private EnvThresholdMapper envThresholdMapper;

    public void postAlarmCheck(EnvironmentData environmentData) {
        // 获取当前厕所的所有阈值设置
        List<EnvThreshold> thresholds = envThresholdMapper.selectList(
                new QueryWrapper<EnvThreshold>()
                        .eq("toilet", environmentData.getToilet())
                        .eq("alert_enabled", true)
        );

        // 遍历每个阈值设置，检查是否触发报警
        for (EnvThreshold threshold : thresholds) {
            Float value = getValueFromEnvironmentData(environmentData, threshold.getParameterName());
            if (value != null) {
                if ((threshold.getMinThreshold() != null && value < threshold.getMinThreshold()) ||
                        (threshold.getMaxThreshold() != null && value > threshold.getMaxThreshold())) {
                    // 触发报警
                    triggerAlarm(threshold.getAlertMessage());
                }
            }
        }
    }

    private Float getValueFromEnvironmentData(EnvironmentData environmentData, String parameterName) {
        // 根据参数名称从环境数据中获取对应的值
        return switch (parameterName) {
            case "pm10" -> environmentData.getPm10();
            case "temperature" -> environmentData.getTemperature();
            case "humidity" -> environmentData.getHumidity();
            case "pressure" -> environmentData.getPressure();
            case "co2" -> environmentData.getCo2();
            case "pm2d5" -> environmentData.getPm2d5();
            case "tvoc" -> environmentData.getTvoc();
            case "ammonia" -> environmentData.getAmmonia();
            case "h2s" -> environmentData.getH2s();
            default -> null;
        };
    }

    private void triggerAlarm(String message) {
        // 报警逻辑，例如发送通知、记录日志等
        System.out.println("Alarm: " + message);
        // 这里可以扩展为发送邮件、短信或调用其他报警服务
    }
}