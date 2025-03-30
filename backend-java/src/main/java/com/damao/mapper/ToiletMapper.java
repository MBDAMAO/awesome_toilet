package com.damao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damao.pojo.entity.Toilet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ToiletMapper extends BaseMapper<Toilet> {
    List<Toilet> queryByEntity(Long entityId, Integer cursor);
}
