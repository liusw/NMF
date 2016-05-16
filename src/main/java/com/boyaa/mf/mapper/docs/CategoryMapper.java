package com.boyaa.mf.mapper.docs;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.docs.Category;
import com.boyaa.mf.mapper.BaseMapper;

/**
 * Created by liusw
 * 创建时间：16-4-13.
 */
@MyBatisRepository
public interface CategoryMapper extends BaseMapper<Category,Integer> {

    public int getMaxOrderNo();
}
