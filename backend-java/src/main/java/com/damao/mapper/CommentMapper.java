package com.damao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damao.pojo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author damao
 * @version 1.0
 * Create by 2025/4/18 上午10:17
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
