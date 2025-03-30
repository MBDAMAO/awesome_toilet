package com.damao.controller.south;

import com.damao.common.result.Result;
import com.damao.mapper.EnvDataMapper;
import com.damao.mapper.TrafficMapper;
import com.damao.pojo.dto.DataSendDTO;
import com.damao.pojo.dto.TrafficDataDTO;
import com.damao.pojo.entity.EnvironmentData;
import com.damao.pojo.entity.Traffic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/south/data")
@Slf4j
public class DataReceiver {

    @Autowired
    private EnvDataMapper envDataMapper;

    @Autowired
    private TrafficMapper trafficMapper;

    @PostMapping("/env")
    public Result<?> getEnvData(@RequestBody DataSendDTO data) {
        EnvironmentData envData = new EnvironmentData();
        BeanUtils.copyProperties(data, envData);
        envDataMapper.insert(envData);
        return Result.success();
    }

    @PostMapping("/traffic")
    public Result<?> getTrafficData(@RequestBody TrafficDataDTO data) {
        Traffic traffic = new Traffic();
        BeanUtils.copyProperties(data, traffic);
        trafficMapper.insert(traffic);
        return Result.success();
    }
}
