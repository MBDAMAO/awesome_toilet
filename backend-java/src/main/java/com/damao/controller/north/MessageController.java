package com.damao.controller.north;

import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @GetMapping("/info")
    public Result<?> getMessageInfo(Integer id) {
        return null;
    }

    @GetMapping("/list")
    public Result<PageResult<?>> getMessageList() {
        return null;
    }

    @GetMapping("/n_viewed")
    public Result<PageResult<?>> getUnViewedMessageList() {
        return null;
    }
}
