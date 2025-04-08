package com.damao.mqtt;

import com.alibaba.fastjson2.JSON;
import com.damao.mapper.DeviceMapper;
import com.damao.mapper.EnvDataMapper;
import com.damao.pojo.entity.Device;
import com.damao.pojo.entity.EnvironmentData;
import com.damao.pojo.entity.Traffic;
import com.damao.pojo.entity.stat.ElectricityUsageDaily;
import com.damao.pojo.entity.stat.PaperUsageDaily;
import com.damao.pojo.entity.stat.WaterUsageDaily;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class MqttMessageService {

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private EnvDataMapper envDataMapper;

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

        EnvironmentData environmentData = new EnvironmentData();
        BeanUtils.copyProperties(data.getData(), environmentData);

        environmentData.setTimestamp(data.getTimestamp());
        environmentData.setToilet(toilet);
        environmentData.setUser(user);
        envDataMapper.insert(environmentData);
    }

    private void handleEnergyMessage(MqttMessage data) {
        Long deviceId = Long.valueOf(data.getDevice());
        Device device = deviceMapper.selectById(deviceId);

        Long user = device.getOwner();
        Long toilet = device.getToilet();

        ElectricityUsageDaily electricityUsageDaily = new ElectricityUsageDaily();
        WaterUsageDaily waterUsageDaily = new WaterUsageDaily();
        PaperUsageDaily paperUsageDaily = new PaperUsageDaily();
    }

    private void handleFlowMessage(MqttMessage data) {
        Long deviceId = Long.valueOf(data.getDevice());
        Device device = deviceMapper.selectById(deviceId);

        Long user = device.getOwner();
        Long toilet = device.getToilet();

        Traffic traffic = new Traffic();
    }
}
