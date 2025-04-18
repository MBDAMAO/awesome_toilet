package com.damao.controller.north;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damao.common.context.BaseContext;
import com.damao.common.result.Result;
import com.damao.mapper.*;
import com.damao.mapper.stat.TrafficDailyStatsMapper;
import com.damao.pojo.entity.*;
import com.damao.pojo.entity.stat.TrafficDailyStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/screen")
public class ScreenController {

    @Autowired
    EnvDataMapper envDataMapper;

    @Autowired
    ToiletMapper toiletMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    AlarmMapper alarmMapper;

    @Autowired
    TrafficMapper trafficMapper;

    @Autowired
    TrafficDailyStatsMapper trafficDailyStatsMapper;

    @GetMapping("/overview")
    public Result<?> getOverviewData() {
        Long uid = BaseContext.getCurrentUid();
        return null;
    }

    @GetMapping("/roomInfo")
    public Result<?> getRoomInfo(@RequestParam Long toiletId) {
        Long uid = BaseContext.getCurrentUid();
        Toilet toilet = toiletMapper.selectById(toiletId);

        // 今日人流数据
        QueryWrapper<TrafficDailyStats> wrapper = new QueryWrapper<>();
        LocalDate today = LocalDate.now();
        wrapper.eq("toilet", toiletId)
                .apply("DATE(timestamp) = DATE('" + today + "')")
                .orderByDesc("timestamp")
                .last("LIMIT 1");
        List<TrafficDailyStats> trafficDailyStats = trafficDailyStatsMapper.selectList(wrapper);

        // 环境数据
        QueryWrapper<EnvironmentData> envWrapper = new QueryWrapper<>();
        envWrapper.eq("toilet", toiletId)
                  .orderByDesc("timestamp")
                  .last("LIMIT 1");
        List<EnvironmentData> environmentData = envDataMapper.selectList(envWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("toilet", toilet.toString());
        map.put("trafficDailyStats", trafficDailyStats.toString());
        map.put("environmentData", environmentData.toString());
        return Result.success(map);
    }


    @GetMapping("/room")
    public Result<?> getRoomData(@RequestParam Integer id) {
        String videoUrl = "/src/assets/test.mp4";

        CompletableFuture<Toilet> toiletFuture = CompletableFuture.supplyAsync(() ->
                toiletMapper.selectById(id));

        CompletableFuture<List<Comment>> commentsFuture = CompletableFuture.supplyAsync(() ->
                commentMapper.selectList(new QueryWrapper<Comment>()
                        .eq("toilet", id)
                        .orderByDesc("create_time")
                        .last("LIMIT 2")));

        CompletableFuture<List<EnvironmentData>> envDataFuture = CompletableFuture.supplyAsync(() ->
                envDataMapper.selectList(new QueryWrapper<EnvironmentData>()
                        .eq("toilet", id)
                        .orderByDesc("timestamp")
                        .last("LIMIT 1")));

        CompletableFuture<List<Traffic>> trafficFuture = CompletableFuture.supplyAsync(() ->
                trafficMapper.selectList(new QueryWrapper<Traffic>()
                        .eq("toilet", id)
                        .orderByDesc("timestamp")
                        .last("LIMIT 1")));

        CompletableFuture<List<Alarm>> alarmsFuture = CompletableFuture.supplyAsync(() ->
                alarmMapper.selectList(new QueryWrapper<Alarm>()
                        .eq("toilet", id)
                        .orderByDesc("create_time")
                        .last("LIMIT 3")));

        CompletableFuture.allOf(toiletFuture, commentsFuture, envDataFuture, trafficFuture, alarmsFuture).join();

        Map<String, Object> map = new HashMap<>();
        map.put("toilet", toiletFuture.join());
        map.put("videoUrl", videoUrl);
        map.put("comments", commentsFuture.join());
        map.put("environmentData", envDataFuture.join());
        map.put("trafficData", trafficFuture.join());
        map.put("alarms", alarmsFuture.join());

        return Result.success(map);
    }

}
