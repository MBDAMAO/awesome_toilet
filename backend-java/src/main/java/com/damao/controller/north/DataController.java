package com.damao.controller.north;

import com.damao.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
    @GetMapping("/dashboard")
    public Result<?> getDashboardData() {
        return null;
    }
}
