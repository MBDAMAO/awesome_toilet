package com.damao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damao.pojo.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/1/5 上午1:00
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {
    List<UserAuth> queryByEntity(Long entityId, Integer cursor);
}
