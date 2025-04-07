package com.damao.mapper.stat;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damao.pojo.entity.stat.ElectricityUsageDaily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/4/7 下午4:20
 */
@Mapper
public interface ElectricityUsageDailyMapper extends BaseMapper<ElectricityUsageDaily> {
    @Select("SELECT DATE(timestamp) as date, SUM(electricity_usage) as total_usage " +
            "FROM electricity_usage_daily " +
            "WHERE timestamp BETWEEN #{start} AND #{end} " +
            "GROUP BY DATE(timestamp) " +
            "ORDER BY DATE(timestamp)")
    List<Map<String, Object>> getDailyElectricityUsage(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Select("SELECT SUM(electricity_usage) FROM electricity_usage_daily " +
            "WHERE timestamp BETWEEN #{start} AND #{end}")
    Long getTotalElectricityUsage(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
