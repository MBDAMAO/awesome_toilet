package com.damao.controller.north;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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