package com.boyaa.mf.mapper.docs;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.docs.Article;
import com.boyaa.mf.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-13.
 */
@MyBatisRepository
public interface ArticleMapper extends BaseMapper<Article,Integer> {
    List<Article> findByKeyWord(Map<String, Object> paraMap);
}
