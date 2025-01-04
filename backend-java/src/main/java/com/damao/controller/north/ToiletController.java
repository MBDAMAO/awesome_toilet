package com.damao.controller.north;

import com.damao.common.result.PageResult;
import com.damao.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toilet")
public class ToiletController {

    @GetMapping("/info")
    public Result<?> getToiletInfo(Integer id) {
        return null;
    }

    @PostMapping("/new")
    public Result<?> addToilet(Integer id) {
        return null;
    }

    @GetMapping("/list")
    public Result<PageResult<?>> getToiletList(Integer id) {
        return null;
    }
}
