package com.damao.controller.north;

import com.damao.common.context.BaseContext;
import com.damao.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/screen")
public class ScreenController {
    @GetMapping("/overview")
    public Result<?> getOverviewData() {
        Long uid = BaseContext.getCurrentUid();
        return null;
    }

    @GetMapping("/room")
    public Result<?> getRoomData(@RequestParam Integer id) {

        return null;
    }
}
