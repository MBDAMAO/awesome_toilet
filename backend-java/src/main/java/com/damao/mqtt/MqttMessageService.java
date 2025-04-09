package com.damao.mqtt;

import com.alibaba.fastjson2.JSON;
import com.damao.mapper.DeviceMapper;
import com.damao.mapper.EnvDataMapper;
import com.damao.mapper.stat.ElectricityUsageDailyMapper;
import com.damao.pojo.entity.Alarm;
import com.damao.pojo.entity.Device;
import com.damao.pojo.entity.EnvironmentData;
import com.damao.pojo.entity.Traffic;
import com.damao.pojo.entity.stat.ElectricityUsageDaily;
import com.damao.pojo.entity.stat.PaperUsageDaily;
import com.damao.pojo.entity.stat.WaterUsageDaily;
import com.damao.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
public class MqttMessageService {

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private EnvDataMapper envDataMapper;
    @Autowired
    private ElectricityUsageDailyMapper electricityUsageDailyMapper;

    @Autowired
    private AlarmService alarmService;

    /**
     * {
     * "deviceId": "toilet-gateway-001",
     * "timestamp": "2025-04-08T10:17:00Z",
     * "type": "energy",      // env, flow
     * "data": {
     * "electricity": 1.23, // kWh
     * "water": 15.7        // liters
     * }
     * }
     */
    @Async("mqttExecutor")
    public void processMessage(String topic, String payload) {
        log.info("【异步处理】主题: {}，内容: {}", topic, payload);
        MqttMessage map = JSON.parseObject(payload, MqttMessage.class);
        switch (map.getType()) {
            case "energy": {
                log.info("energy");
                handleEnergyMessage(map);
                break;
            }
            case "env": {
                log.info("env");
                handleEnvMessage(map);
                break;
            }
            case "flow": {
                log.info("flow");
                handleFlowMessage(map);
                break;
            }
            default:
                break;
        }
    }

    private void handleEnvMessage(MqttMessage data) {
        Long deviceId = Long.valueOf(data.getDevice());
        Device device = deviceMapper.selectById(deviceId);

        Long user = device.getOwner();
        Long toilet = device.getToilet();
        Map<String, Object> dataMap = data.getData();

        EnvironmentData environmentData = new EnvironmentData();
        environmentData.setTemperature(Float.parseFloat(dataMap.get("temperature").toString()));
        environmentData.setHumidity(Float.parseFloat(dataMap.get("humidity").toString()));
        environmentData.setAmmonia(Float.parseFloat(dataMap.get("ammonia").toString()));
        environmentData.setPm2d5(Float.parseFloat(dataMap.get("pm2d5").toString()));
        environmentData.setPm10(Float.parseFloat(dataMap.get("pm10").toString()));
        environmentData.setPressure(Float.parseFloat(dataMap.get("pressure").toString()));
        environmentData.setH2s(Float.parseFloat(dataMap.get("h2s").toString()));
        environmentData.setCo2(Float.parseFloat(dataMap.get("co2").toString()));
        environmentData.setTvoc(Float.parseFloat(dataMap.get("tvoc").toString()));
        environmentData.setTimestamp(data.getTimestamp());
        environmentData.setToilet(toilet);
        environmentData.setUser(user);
        envDataMapper.insert(environmentData);

        alarmService.postAlarmCheck(environmentData);
    }

    private void handleEnergyMessage(MqttMessage data) {
        Long deviceId = Long.valueOf(data.getDevice());
        Device device = deviceMapper.selectById(deviceId);

        Long user = device.getOwner();
        Long toilet = device.getToilet();
        Map<String, Object> dataMap = data.getData();

        ElectricityUsageDaily electricityUsageDaily = new ElectricityUsageDaily();
        electricityUsageDaily.setUser(user);
        electricityUsageDaily.setToilet(toilet);
        electricityUsageDaily.setTimestamp(data.getTimestamp());
        electricityUsageDaily.setElectricityUsage(Float.parseFloat(dataMap.get("electricityUsage").toString()));
        electricityUsageDailyMapper.insert(electricityUsageDaily);

        WaterUsageDaily waterUsageDaily = new WaterUsageDaily();
        waterUsageDaily.setUser(user);
        waterUsageDaily.setTimestamp(data.getTimestamp());
        waterUsageDaily.setWaterUsage(Float.parseFloat(dataMap.get("waterUsage").toString()));
        waterUsageDaily.setToilet(toilet);


        PaperUsageDaily paperUsageDaily = new PaperUsageDaily();
        paperUsageDaily.setPaperUsage(Float.parseFloat(dataMap.get("paperUsage").toString()));
        paperUsageDaily.setTimestamp(data.getTimestamp());
        paperUsageDaily.setUser(user);
        paperUsageDaily.setToilet(toilet);
    }

    private void handleFlowMessage(MqttMessage data) {
        Long deviceId = Long.valueOf(data.getDevice());
        Device device = deviceMapper.selectById(deviceId);

        Long user = device.getOwner();
        Long toilet = device.getToilet();

        Traffic traffic = new Traffic();
    }
}
