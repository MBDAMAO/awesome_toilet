package com.damao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damao.pojo.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/4/7 下午3:35
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
