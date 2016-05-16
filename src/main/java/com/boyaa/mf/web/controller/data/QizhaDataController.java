package com.boyaa.mf.web.controller.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.entity.data.QizhaData;
import com.boyaa.mf.service.data.QizhaDataService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;

/**
 * 欺诈数据是由壮杰那边入库,MF只是展示
 *
 * @作者 : huangyineng
 * @日期 : 2016-4-1  下午6:30:48
 */
@Controller
@RequestMapping("data/qizhaData")
public class QizhaDataController extends BaseController {

	@Autowired
	private QizhaDataService qizhaDataService;

	/**
	 *欺诈用户查询
	 */
	
	@RequestMapping(value="findQizhaUser")
	@ResponseBody
	public String findQizhaUser(String sSearch_0,String sSearch_1,String sSearch_2,String sSearch_3){
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(sSearch_0)){
			params.put("_plat", sSearch_0);
		}
		if(StringUtils.isNotBlank(sSearch_1)){
			params.put("_bpid", sSearch_1);
		}
		if(StringUtils.isNotBlank(sSearch_2)){
			params.put("_uid", sSearch_2);
		}
		if(StringUtils.isNotBlank(sSearch_3)){
			params.put("tm", sSearch_3);
		}
		
		PageUtil<QizhaData> datas = qizhaDataService.getPageList(params);
		return getDataTableJson(datas.getDatas(),datas.getTotalRecord()).toJSONString();
	}
}
