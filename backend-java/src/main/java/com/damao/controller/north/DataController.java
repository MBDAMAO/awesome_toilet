package com.damao.controller.north;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damao.common.context.BaseContext;
import com.damao.common.result.Result;
import com.damao.mapper.EnvDataMapper;
import com.damao.mapper.LoginHistoryMapper;
import com.damao.mapper.UserMapper;
import com.damao.mapper.stat.ElectricityUsageDailyMapper;
import com.damao.mapper.stat.TrafficDailyStatsMapper;
import com.damao.pojo.entity.LoginHistory;
import com.damao.pojo.entity.User;
import com.damao.pojo.entity.stat.ElectricityUsageDaily;
import com.damao.pojo.entity.stat.TrafficDailyStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    EnvDataMapper envDataMapper;

    @Autowired
    LoginHistoryMapper loginHistoryMapper;

    @Autowired
    TrafficDailyStatsMapper trafficDailyStatsMapper;

    @Autowired
    ElectricityUsageDailyMapper electricityUsageDailyMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/dashboard")
    public Result<?> getDashboardData() {
        Long uid = BaseContext.getCurrentUid();
        // 最近一次登录时间
        QueryWrapper<LoginHistory> queryWrapper = new QueryWrapper<LoginHistory>();
        queryWrapper.eq("user", uid)
                .orderByDesc("time")
                .last("limit 1");
        List<LoginHistory> list = loginHistoryMapper.selectList(queryWrapper);

        User user = userMapper.selectById(uid);

        Map<String, Object> res = new HashMap<>();
        res.put("loginHistory", list);
        res.put("user", user);
        // 今天该用户下所有的厕所人流数据
        LocalDate today = LocalDate.now();
        QueryWrapper<TrafficDailyStats> trafficQueryWrapper = new QueryWrapper<TrafficDailyStats>();
        trafficQueryWrapper.eq("user", uid)
                .apply("DATE(timestamp) = DATE('" + today + "')")
                .orderByDesc("timestamp")
                .last("LIMIT 1");
        List<TrafficDailyStats> trafficDailyStats = trafficDailyStatsMapper.selectList(trafficQueryWrapper);

        // 今天该用户下所有的用电量数据
        QueryWrapper<ElectricityUsageDaily> electricityQueryWrapper = new QueryWrapper<ElectricityUsageDaily>();
        electricityQueryWrapper.eq("user", uid)
               .apply("DATE(timestamp) = DATE('" + today + "')")
               .orderByDesc("timestamp")
               .last("LIMIT 1");
        List<ElectricityUsageDaily> electricityUsageDaily = electricityUsageDailyMapper.selectList(electricityQueryWrapper);

        // 今天该用户下所有的用水量数据 TODO

        return Result.success(res);
    }

    @GetMapping("/overview")
    public Result<?> getOverviewData() {
        Map<?, ?> res = ToiletDataHashMap.buildDashboard();
        return Result.success(res);
    }
}


class ToiletDataHashMap {
    public static Map<String, Object> buildDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        // 1. 环境数据
        List<Map<String, Object>> envData = new ArrayList<>();
        addEnvItem(envData, "温度", 3222, 3222, 3222);
        addEnvItem(envData, "湿度", 3222, 3222, null);
        // 其他环境项...
        dashboard.put("envData", envData);

        // 2. 资源用量
        List<Map<String, Object>> resUsage = new ArrayList<>();
        addResourceUsage(resUsage, "用水量", 3222, 3222);
        addResourceUsage(resUsage, "用电量", 3222, 3222);
        dashboard.put("resUsage", resUsage);

        // 3. 今日人流量
        Map<String, Integer> todayPeople = new HashMap<>();
        todayPeople.put("man", 100);
        todayPeople.put("woman", 100);
        todayPeople.put("third", 100);
        dashboard.put("today_people", todayPeople);

        // 4. 卫生间占用
        List<Map<String, Object>> occupy = new ArrayList<>();
        addOccupancy(occupy, "男卫生间", 100, 100);
        addOccupancy(occupy, "女卫生间", 100, 100);
        dashboard.put("occupy", occupy);

        return dashboard;
    }

    private static void addEnvItem(List<Map<String, Object>> list, String name,
                                   Integer man, Integer woman, Integer third) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("man", man);
        item.put("woman", woman);
        if (third != null) item.put("third", third);
        list.add(item);
    }

    private static void addResourceUsage(List<Map<String, Object>> list, String name,
                                         Integer today, Integer month) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("today", today);
        item.put("month", month);
        list.add(item);
    }

    private static void addOccupancy(List<Map<String, Object>> list, String name,
                                     Integer total, Integer free) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("total", total);
        item.put("free", free);
        list.add(item);
    }
}