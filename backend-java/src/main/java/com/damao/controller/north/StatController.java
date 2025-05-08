package com.damao.controller.north;

import com.damao.common.result.Result;
import com.damao.mapper.stat.ElectricityUsageDailyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stat")
public class StatController {
    @Autowired
    private ElectricityUsageDailyMapper electricityUsageMapper;

    @GetMapping("/electricity-usage-stats")
    public Result<Map<String, Object>> getElectricityUsageStats() {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.atTime(LocalTime.MAX);

        LocalDateTime yesterdayStart = todayStart.minusDays(1);
        LocalDateTime yesterdayEnd = todayEnd.minusDays(1);

        LocalDate sevenDaysAgo = today.minusDays(6);
        LocalDateTime lastSevenDaysStart = sevenDaysAgo.atStartOfDay();

        LocalDateTime thisWeekStart = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime thisWeekEnd = now;

        LocalDateTime lastWeekStart = thisWeekStart.minusWeeks(1);
        LocalDateTime lastWeekEnd = thisWeekStart.minusSeconds(1);

        List<Map<String, Object>> lastSevenDaysUsage = electricityUsageMapper.getDailyElectricityUsage(lastSevenDaysStart, todayEnd);
        Float todayUsage = electricityUsageMapper.getTotalElectricityUsage(todayStart, todayEnd);
        Float yesterdayUsage = electricityUsageMapper.getTotalElectricityUsage(yesterdayStart, yesterdayEnd);
        Float thisWeekUsage = electricityUsageMapper.getTotalElectricityUsage(thisWeekStart, thisWeekEnd);
        Float lastWeekUsage = electricityUsageMapper.getTotalElectricityUsage(lastWeekStart, lastWeekEnd);

        if (todayUsage == null) {
            todayUsage = 0F;
        }
        if (yesterdayUsage == null) {
            yesterdayUsage = 0F;
        }
        if (thisWeekUsage == null) {
            thisWeekUsage = 0F;
        }
        if (lastWeekUsage == null) {
            lastWeekUsage = 0F;
        }
        Map<String, Object> usageStats = new HashMap<>();
        usageStats.put("lastSevenDaysUsage", lastSevenDaysUsage);
        usageStats.put("todayUsage", todayUsage);
        usageStats.put("yesterdayUsage", yesterdayUsage);
        usageStats.put("thisWeekUsage", thisWeekUsage);
        usageStats.put("lastWeekUsage", lastWeekUsage);
        usageStats.put("unit", "kWh");

        return Result.success(usageStats);
    }
}
