package com.damao.controller.north;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damao.common.context.BaseContext;
import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import com.damao.mapper.MessageMapper;
import com.damao.pojo.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageMapper messageMapper;

    @GetMapping("/info")
    public Result<?> getMessageInfo(Integer id) {
        Message msg = messageMapper.selectById(id);
        return Result.success(msg);
    }

    @GetMapping("/list")
    public Result<PageResult<?>> getMessageList(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Long uid = BaseContext.getCurrentUid();

        Page<Message> pageObj = new Page<>(page, size);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();

        wrapper.eq("user", uid);

        Page<Message> devicePage = messageMapper.selectPage(pageObj, wrapper);
        PageResult<Message> pageResult = new PageResult<>();
        pageResult.setTotal(devicePage.getTotal());
        pageResult.setRecords(devicePage.getRecords());
        pageResult.setPage(devicePage.getPages());
        pageResult.setSize(devicePage.getSize());
        return Result.success(pageResult);
    }

    @GetMapping("/n_viewed")
    public Result<PageResult<?>> getUnViewedMessageList() {
        return null;
    }
}
