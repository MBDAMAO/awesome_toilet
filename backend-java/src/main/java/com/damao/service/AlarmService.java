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
        List<EnvThreshold> thresholds = envThresholdMapper.selectList(
                new QueryWrapper<EnvThreshold>()
                        .eq("toilet", environmentData.getToilet())
                        .eq("alert_enabled", true)
        );
        for (EnvThreshold threshold : thresholds) {
            Float value = getValueFromEnvironmentData(environmentData, threshold.getParameterName());
            if (value != null) {
                if ((threshold.getMinThreshold() != null && value < threshold.getMinThreshold()) ||
                        (threshold.getMaxThreshold() != null && value > threshold.getMaxThreshold())) {
                    triggerAlarm(threshold.getAlertMessage());
                }
            }
        }
    }
    private Float getValueFromEnvironmentData(EnvironmentData environmentData, String parameterName) {
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
    private void triggerAlarm(String message) {/* */}
}