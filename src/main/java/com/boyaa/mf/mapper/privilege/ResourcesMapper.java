package com.boyaa.mf.mapper.privilege;

import java.util.List;

import com.boyaa.mf.annotation.MyBatisRepository;
import com.boyaa.mf.entity.privilege.Resources;
import com.boyaa.mf.mapper.BaseMapper;
import com.boyaa.mf.vo.ResourcesTree;

@MyBatisRepository
public interface ResourcesMapper extends BaseMapper<Resources,Integer>{
	List<ResourcesTree> getTree();

}
