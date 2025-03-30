package com.damao.controller.north;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damao.common.context.BaseContext;
import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import com.damao.mapper.DeviceMapper;
import com.damao.pojo.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
