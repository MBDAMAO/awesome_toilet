package com.damao.controller.north;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import com.damao.mapper.DeviceMapper;
import com.damao.pojo.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/device")
public class DeviceController {

    @Autowired
    DeviceMapper deviceMapper;

    @GetMapping("info")
    public Result<?> getDeviceInfo(Integer id) {
        return null;
    }

    @PostMapping("new")
    public Result<?> addDevice(Integer id) {
        return null;
    }

    @GetMapping("list")
    public Result<PageResult<?>> getDeviceList(Integer id) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq()
        List<Device> deviceList = deviceMapper.selectList(wrapper);
        System.out.println(deviceList);
        return null;
    }
}
