package com.boyaa.mf.web.controller.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.entity.data.DataRqmt;
import com.boyaa.mf.entity.data.DataRqmtEnum;
import com.boyaa.mf.entity.data.DataRqmtOpt;
import com.boyaa.mf.entity.data.DataRqmtOptEnum;
import com.boyaa.mf.service.data.DataRqmtOptService;
import com.boyaa.mf.service.data.DataRqmtService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;

@Controller
public class DataRqmtController extends BaseController{
	static Logger logger = Logger.getLogger(DataRqmtController.class);
	
	@Autowired
	private DataRqmtService dataRqmtService;
	@Autowired
	private DataRqmtOptService dataRqmtOptService;
	
	@RequestMapping(value="data/dataRqmt/listDataRqmts")
	@ResponseBody
	public String listDataRqmts(String action){
		Map<String, Object> params = new HashMap<String, Object>();
		if("myList".equals(action)){
			params.put("userId",getLoginUserInfo().getCode());
		}
		PageUtil<DataRqmt> page = dataRqmtService.getPageList(params);
		return getDataTableJson(page.getDatas(), page.getTotalRecord()).toJSONString();
	}
	
	@RequestMapping(value="data/dataRqmt/detail")
	public String detail(Integer id,Model model){
		if(id==null){
			return errorPage("系统提示","传入参数不正确",model);
		}
		
		DataRqmt dataRqmt = dataRqmtService.findById(id);
		model.addAttribute("content",dataRqmt.getContent());
		model.addAttribute("id", dataRqmt.getId());
		model.addAttribute("createTime", dataRqmt.getCreateTime());
		model.addAttribute("creater", dataRqmt.getUserName());
		return "dataRqmt/dataRqmtDetail";
	}

	@RequestMapping(value="data/dataRqmt/addOrUpdate")
	@ResponseBody
	public AjaxObj addOrUpdate(String title,String content,Integer plat,String remark,String status,String finishTime,Integer id){
		if(StringUtils.isBlank(title) || StringUtils.isBlank(content) || plat==null){
			return new AjaxObj(AjaxObj.FAILURE, "请求参数不正确!");
		}
		
		LoginUserInfo userInfo = getLoginUserInfo();
		
		DataRqmt dataRqmt = new DataRqmt();
		
		if(id!=null){//更新
			dataRqmt.setId(id);
			dataRqmt.setDealer(userInfo.getRealName());
			if(StringUtils.isNotBlank(status)){
				dataRqmt.setStatus(Integer.parseInt(status));
			}
			if(StringUtils.isNotBlank(finishTime)){
				dataRqmt.setFinishTime(finishTime);
			}
			dataRqmtService.update(dataRqmt);
			
		}else{
			dataRqmt.setTitle(title);
			dataRqmt.setContent(content);
			dataRqmt.setPlat(plat);
			dataRqmt.setUserId(userInfo.getCode());
			dataRqmt.setUserName(userInfo.getRealName());
			dataRqmt.setDealer(userInfo.getRealName());
			dataRqmt.setStatus(DataRqmtEnum.CREATE.getValue());
			dataRqmtService.insert(dataRqmt);
			id = dataRqmt.getId();
			
			DataRqmtOpt dataRqmtOpt = new DataRqmtOpt();
			dataRqmtOpt.setContent("{\"optRemark\":\"" + remark + "\",\"operateType\":\"0\"}");
			dataRqmtOpt.setReferId(Integer.valueOf(id));
			dataRqmtOpt.setUserId(userInfo.getCode());
			dataRqmtOpt.setUserName(userInfo.getRealName());
			dataRqmtOptService.insert(dataRqmtOpt);
		}
		return new AjaxObj(AjaxObj.SUCCESS, "", id);
	}

	@RequestMapping(value="data/dataRqmtOpt/listDataRqmtOpts")
	@ResponseBody
	public AjaxObj listDataRqmtOpts(String referId){
		if(StringUtils.isNumeric(referId)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("referId", referId);
			List<DataRqmtOpt> datas = dataRqmtOptService.findDataRqmtOptList(params);
			return new AjaxObj(AjaxObj.SUCCESS, "请求正确", datas);
		}else{
			return new AjaxObj(AjaxObj.FAILURE, "参数设置不对!");
		}
	}
	
	@RequestMapping(value="data/dataRqmtOpt/addOrUpdateOpt")
	@ResponseBody
	public AjaxObj addOrUpdateOpt(String content,int referId,String optType,Integer id,String finishTime,String loggers){
		if(StringUtils.isBlank(content)){
			return new AjaxObj(AjaxObj.FAILURE, "请求参数不正确!");
		}
		
		LoginUserInfo userInfo = getLoginUserInfo();
			
		DataRqmtOpt dataRqmtOpt = new DataRqmtOpt();
		dataRqmtOpt.setContent(content);
		dataRqmtOpt.setReferId(referId);
			
		if(id!=null){//更新
			dataRqmtOpt.setId(id);
			dataRqmtOptService.update(dataRqmtOpt);
		}else{
			dataRqmtOpt.setUserId(userInfo.getCode());
			dataRqmtOpt.setUserName(userInfo.getRealName());
			
			dataRqmtOptService.insert(dataRqmtOpt);
			id = dataRqmtOpt.getId();
			
			if(StringUtils.isNotBlank(optType)){
				if(String.valueOf(DataRqmtOptEnum.DELIVER.getValue()).equals(optType) || String.valueOf(DataRqmtOptEnum.STOP.getValue()).equals(optType)
						|| String.valueOf(DataRqmtOptEnum.ALLOT.getValue()).equals(optType)){
					DataRqmtService dataRqmtService = new DataRqmtService();
					DataRqmt d = new DataRqmt();
					d.setId(Integer.valueOf(referId));
					d.setDealer(userInfo.getRealName());
					if(String.valueOf(DataRqmtOptEnum.DELIVER.getValue()).equals(optType)){
						d.setStatus(DataRqmtEnum.DELIVER.getValue());
					}else if(String.valueOf(DataRqmtOptEnum.STOP.getValue()).equals(optType)){
						d.setStatus(DataRqmtEnum.STOP.getValue());
					}else if(String.valueOf(DataRqmtOptEnum.ALLOT.getValue()).equals(optType)){
						d.setStatus(DataRqmtEnum.ALLOT.getValue());
						d.setFinishTime(finishTime);
						d.setReceiver(loggers);
					}
					dataRqmtService.update(d);
				}
			}
		}
		return new AjaxObj(AjaxObj.SUCCESS, "请求正确", id);
	}
}
