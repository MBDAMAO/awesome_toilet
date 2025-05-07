//package com.damao.mqtt;
//
//import com.alibaba.fastjson2.JSON;
//import com.damao.mapper.DeviceMapper;
//import com.damao.mapper.EnvDataMapper;
//import com.damao.mapper.TrafficMapper;
//import com.damao.mapper.stat.ElectricityUsageDailyMapper;
//import com.damao.pojo.entity.Device;
//import com.damao.pojo.entity.EnvironmentData;
//import com.damao.pojo.entity.Traffic;
//import com.damao.pojo.entity.stat.ElectricityUsageDaily;
//import com.damao.pojo.entity.stat.PaperUsageDaily;
//import com.damao.pojo.entity.stat.WaterUsageDaily;
//import com.damao.service.AlarmService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//
//@Service
//@Slf4j
//public class MqttMessageService {
//    @Async("mqttExecutor")
//    public void processMessage(String topic, String payload) {
//        MqttMessage map = JSON.parseObject(payload, MqttMessage.class);
//        switch (map.getType()) {
//            case "energy": {handleEnergyMessage(map);break;}
//            case "env": {handleEnvMessage(map);break;}
//            case "flow": {handleFlowMessage(map);break;}
//            default: break;
//        }
//    }
//    private void handleEnvMessage(MqttMessage data) {
//
//    }
//    private void handleEnergyMessage(MqttMessage data) {
//
//    }
//    private void handleFlowMessage(MqttMessage data) {
//
//    }
//}
