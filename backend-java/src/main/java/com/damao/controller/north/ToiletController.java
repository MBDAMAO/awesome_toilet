package com.damao.controller.north;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damao.common.context.BaseContext;
import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import com.damao.mapper.ToiletMapper;
import com.damao.pojo.entity.Toilet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/toilet")
public class ToiletController {

    @Autowired
    private ToiletMapper toiletMapper;


    @GetMapping("/info")
    public Result<?> getToiletInfo(Integer id) {
        Toilet toilet = toiletMapper.selectById(id);
        return Result.success(toilet);
    }

    @GetMapping("/delete")
    public Result<?> deleteToilet(Integer id) {
        toiletMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateToilet(Toilet toilet) {
        toiletMapper.updateById(toilet);
        return Result.success();
    }

    @PostMapping("/new")
    public Result<?> addToilet(Toilet toilet) {
        toiletMapper.insert(toilet);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<PageResult<Toilet>> getDeviceList(
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
