package com.damao.controller.north;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damao.common.context.BaseContext;
import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import com.damao.mapper.DeviceMapper;
import com.damao.mapper.PitMapper;
import com.damao.mapper.ToiletMapper;
import com.damao.pojo.entity.Device;
import com.damao.pojo.entity.Pit;
import com.damao.pojo.entity.Toilet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/toilet")
public class ToiletController {

    @Autowired
    private ToiletMapper toiletMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private PitMapper pitMapper;


    @GetMapping("/info")
    public Result<?> getToiletInfo(@RequestParam Integer id) {
        Toilet toilet = toiletMapper.selectById(id);
        return Result.success(toilet);
    }

    @GetMapping("/detail")
    public Result<?> getToiletDetail(@RequestParam Integer id) {
        Toilet toilet = toiletMapper.selectById(id);
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("toilet", id);
        List<Device> deviceList = deviceMapper.selectList(queryWrapper);

        QueryWrapper<Pit> queryWrapperPit = new QueryWrapper<>();
        queryWrapperPit.eq("toilet", id);
        List<Pit> pitList = pitMapper.selectList(queryWrapperPit);

        HashMap<String, Object> map = new HashMap<>();
        map.put("devices", deviceList);
        map.put("pits", pitList);
        map.put("toilet", toilet);
        return Result.success(map);
    }

    @GetMapping("/delete")
    public Result<?> deleteToilet(Long id) {
        // 无实体依赖关系才能删除
        Long device = toiletMapper.checkDependence(id);
        if (device != 0) {
            return Result.error("该厕所下存在未删除的设备，请先删除相关设备！");
        }
        toiletMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateToilet(@RequestBody Toilet toilet) {

        toiletMapper.updateById(toilet);
        return Result.success();
    }

    @PostMapping("save")
    public Result<?> saveToilet(@RequestBody Toilet toilet){
        Long uid = BaseContext.getCurrentUid();
        toilet.setOwner(uid);
        toiletMapper.insertOrUpdate(toilet);
        return Result.success();
    }

    @PostMapping("/new")
    public Result<?> addToilet(@RequestBody Toilet toilet) {
        toiletMapper.insert(toilet);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<Object> getAllToiletInfo() {
        Long uid = BaseContext.getCurrentUid();
        QueryWrapper<Toilet> wrapper = new QueryWrapper<>();
        wrapper.eq("owner", uid);
        List<Toilet> allToilets = toiletMapper.selectList(wrapper);
        List<Map<String, Object>> res = allToilets.stream().map(element -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", element.getId());
            map.put("name", element.getName());
            map.put("location", element.getLocation());
            return map;
        }).toList();
        return Result.success(res);
    }


    @GetMapping("/list")
    public Result<PageResult<Toilet>> getToiletList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String name
    ) {
        Long uid = BaseContext.getCurrentUid();

        Page<Toilet> pageObj = new Page<>(page, size);
        QueryWrapper<Toilet> wrapper = new QueryWrapper<>();

        if (status != null) {
            wrapper.eq("status", status);
        }
        if (name != null) {
            wrapper.like("name", name);
        }
        wrapper.eq("owner", uid);

        Page<Toilet> devicePage = toiletMapper.selectPage(pageObj, wrapper);
        PageResult<Toilet> pageResult = new PageResult<>();
        pageResult.setTotal(devicePage.getTotal());
        pageResult.setRecords(devicePage.getRecords());
        pageResult.setPage(devicePage.getPages());
        pageResult.setSize(devicePage.getSize());
        return Result.success(pageResult);
    }
}
