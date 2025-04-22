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
import com.damao.mapper.ToiletMapper;
import com.damao.mapper.UserMapper;
import com.damao.mapper.stat.ElectricityUsageDailyMapper;
import com.damao.mapper.stat.PaperUsageDailyMapper;
import com.damao.mapper.stat.TrafficDailyStatsMapper;
import com.damao.mapper.stat.WaterUsageDailyMapper;
import com.damao.pojo.entity.LoginHistory;
import com.damao.pojo.entity.Toilet;
import com.damao.pojo.entity.User;
import com.damao.pojo.entity.stat.ElectricityUsageDaily;
import com.damao.pojo.entity.stat.PaperUsageDaily;
import com.damao.pojo.entity.stat.TrafficDailyStats;
import com.damao.pojo.entity.stat.WaterUsageDaily;
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
    WaterUsageDailyMapper waterUsageDailyMapper;

    @Autowired
    PaperUsageDailyMapper paperUsageDailyMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    private ToiletMapper toiletMapper;

    @GetMapping("/dashboard")
    public Result<?> getDashboardData() {
        Long uid = BaseContext.getCurrentUid();
        // 最近一次登录时间
        QueryWrapper<LoginHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user", uid)
                .orderByDesc("time")
                .last("limit 1");
        List<LoginHistory> list = loginHistoryMapper.selectList(queryWrapper);

        User user = userMapper.selectById(uid);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", user.getName());

        Map<String, Object> res = new HashMap<>();
        res.put("loginHistory", list);
        res.put("user", userMap);

        // 今天和昨天该用户下所有的厕所人流数据
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        QueryWrapper<TrafficDailyStats> trafficQueryWrapper = new QueryWrapper<>();
        trafficQueryWrapper.eq("user", uid)
                .apply("DATE(timestamp) IN ('" + today + "', '" + yesterday + "')")
                .orderByDesc("timestamp");
        List<TrafficDailyStats> trafficDailyStats = trafficDailyStatsMapper.selectList(trafficQueryWrapper);

        Float totalTodayFemaleTraffic = 0f;
        Float totalTodayMaleTraffic = 0f;
        Float totalYesterdayFemaleTraffic = 0f;
        Float totalYesterdayMaleTraffic = 0f;
        for (TrafficDailyStats trafficDailyStat : trafficDailyStats) {
            if (trafficDailyStat.getTimestamp().toLocalDate().equals(today)) {
                totalTodayFemaleTraffic += trafficDailyStat.getFemaleVisitors();
                totalTodayMaleTraffic += trafficDailyStat.getMaleVisitors();
            } else {
                totalYesterdayFemaleTraffic += trafficDailyStat.getFemaleVisitors();
                totalYesterdayMaleTraffic += trafficDailyStat.getMaleVisitors();
            }
        }
        Map<String, Object> traffic = new HashMap<>();
        traffic.put("totalYesterdayFemaleTraffic", totalYesterdayFemaleTraffic);
        traffic.put("totalYesterdayMaleTraffic", totalYesterdayMaleTraffic);
        traffic.put("totalTodayFemaleTraffic", totalTodayFemaleTraffic);
        traffic.put("totalTodayMaleTraffic", totalTodayMaleTraffic);

        res.put("traffic", traffic);

        // 今天和昨天该用户下所有的用电量数据
        QueryWrapper<ElectricityUsageDaily> electricityQueryWrapper = new QueryWrapper<>();
        electricityQueryWrapper.eq("user", uid)
                .apply("DATE(timestamp) IN ('" + today + "', '" + yesterday + "')")
                .orderByDesc("timestamp");
        List<ElectricityUsageDaily> electricityUsageDaily = electricityUsageDailyMapper.selectList(electricityQueryWrapper);
        Float totalTodayElectricityUsage = 0f;
        Float totalYesterdayElectricityUsage = 0f;
        for (ElectricityUsageDaily daily : electricityUsageDaily) {
            if (daily.getTimestamp().toLocalDate().equals(today)) {
                totalTodayElectricityUsage += daily.getElectricityUsage();
            } else {
                totalYesterdayElectricityUsage += daily.getElectricityUsage();
            }
        }
        Map<String, Object> electricity = new HashMap<>();
        electricity.put("totalYesterdayElectricityUsage", totalYesterdayElectricityUsage);
        electricity.put("totalTodayElectricityUsage", totalTodayElectricityUsage);
        res.put("electricityUsage", electricity);

        // 今天和昨天该用户下所有的用水量数据
        QueryWrapper<WaterUsageDaily> waterQueryWrapper = new QueryWrapper<>();
        electricityQueryWrapper.eq("user", uid)
                .apply("DATE(timestamp) IN ('" + today + "', '" + yesterday + "')")
                .orderByDesc("timestamp");
        List<WaterUsageDaily> waterUsageDaily = waterUsageDailyMapper.selectList(waterQueryWrapper);
        Float totalTodayWaterUsage = 0f;
        Float totalYesterdayWaterUsage = 0f;
        for (WaterUsageDaily daily : waterUsageDaily) {
            if (daily.getTimestamp().toLocalDate().equals(today)) {
                totalTodayWaterUsage += daily.getWaterUsage();
            } else {
                totalYesterdayWaterUsage += daily.getWaterUsage();
            }
        }
        Map<String, Object> water = new HashMap<>();
        water.put("totalTodayWaterUsage", totalTodayWaterUsage);
        water.put("totalYesterdayWaterUsage", totalYesterdayWaterUsage);
        res.put("water", water);

        // 今天和昨天该用户下所有的用纸量数据
        QueryWrapper<PaperUsageDaily> paperQueryWrapper = new QueryWrapper<>();
        electricityQueryWrapper.eq("user", uid)
                .apply("DATE(timestamp) IN ('" + today + "', '" + yesterday + "')")
                .orderByDesc("timestamp");
        List<PaperUsageDaily> paperUsageDailies = paperUsageDailyMapper.selectList(paperQueryWrapper);
        Float totalTodayPaperUsage = 0f;
        Float totalYesterdayPaperUsage = 0f;
        for (PaperUsageDaily daily : paperUsageDailies) {
            if (daily.getTimestamp().toLocalDate().equals(today)) {
                totalTodayPaperUsage += daily.getPaperUsage();
            } else {
                totalYesterdayPaperUsage += daily.getPaperUsage();
            }
        }
        Map<String, Object> paper = new HashMap<>();
        paper.put("totalTodayPaperUsage", totalTodayPaperUsage);
        paper.put("totalYesterdayPaperUsage", totalYesterdayPaperUsage);
        res.put("paper", paper);

        return Result.success(res);
    }

    @GetMapping("/overview")
    public Result<?> getOverviewData() {
        Map<String, Object> overview = new HashMap<>();
        // 获取本用户下所有厕所位置
        Long uid = BaseContext.getCurrentUid();
        QueryWrapper<Toilet> toiletQueryWrapper = new QueryWrapper<>();
        toiletQueryWrapper.eq("owner", uid);
        // map 出来位置信息,和id
        List<Toilet> toilets = toiletMapper.selectList(toiletQueryWrapper);
        List<Map<String, Object>> mappedToilets = new ArrayList<>();
        for (Toilet toilet : toilets) {
            Map<String, Object> toiletMap = new HashMap<>();
            toiletMap.put("id", toilet.getId());
            toiletMap.put("name", toilet.getName());
            toiletMap.put("location", toilet.getLocation());
            mappedToilets.add(toiletMap);
        }
        overview.put("toilets", mappedToilets);


        Map<?, ?> res = buildDashboard();
        return Result.success(res);
    }

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

