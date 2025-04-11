package com.damao.controller.north;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damao.common.context.BaseContext;
import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import com.damao.mapper.DeviceMapper;
import com.damao.pojo.entity.Device;
import com.damao.pojo.entity.Toilet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceMapper deviceMapper;

    @GetMapping("/info")
    public Result<?> getDeviceInfo(Integer id) {
        Device device = deviceMapper.selectById(id);
        return Result.success(device);
    }

    @PostMapping("/new")
    public Result<?> addDevice(Device device) {
        deviceMapper.insert(device);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<?> deleteDeviceById(Integer id) {
        deviceMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> updateDevice(Device device) {
        deviceMapper.updateById(device);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<Object> getAllDeviceInfo() {
        Long uid = BaseContext.getCurrentUid();
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("owner", uid);
        List<Device> allDevices = deviceMapper.selectList(wrapper);
        List<Map<String, Object>> res = allDevices.stream().map(element -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", element.getId());
            map.put("name", element.getName());
            return map;
        }).toList();
        return Result.success(res);
    }

    @GetMapping("/list")
    public Result<PageResult<Device>> getDeviceList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long uid = BaseContext.getCurrentUid();

        Page<Device> pageObj = new Page<>(page, size);
        QueryWrapper<Device> wrapper = new QueryWrapper<>();

        wrapper.eq("owner", uid);

        Page<Device> devicePage = deviceMapper.selectPage(pageObj, wrapper);
        PageResult<Device> pageResult = new PageResult<>();
        pageResult.setTotal(devicePage.getTotal());
        pageResult.setRecords(devicePage.getRecords());
        pageResult.setPage(devicePage.getPages());
        pageResult.setSize(devicePage.getSize());
        return Result.success(pageResult);
    }
}
