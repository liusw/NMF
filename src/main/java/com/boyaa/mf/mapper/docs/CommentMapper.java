package com.boyaa.mf.mapper.docs;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.docs.Comment;
import com.boyaa.mf.mapper.BaseMapper;

/**
 * Created by liusw
 * 创建时间：16-4-14.
 */
@MyBatisRepository
public interface CommentMapper extends BaseMapper<Comment,Integer> {
}
