package com.damao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damao.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/1/5 上午12:56
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> queryByEntity(Long entityId, Integer cursor);
}
