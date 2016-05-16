package com.boyaa.mf.web.controller.data;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.entity.data.ClientError;
import com.boyaa.mf.service.data.ClientErrorService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-21.
 */
@Controller
@RequestMapping("clienterror")
public class ClientErrorController extends BaseController {

    @Autowired
    private ClientErrorService clientErrorService;


    @RequestMapping("clientErrorDetail")
    public ClientError getDetailPage() {
        String desc_md5 = getRequest().getParameter("desc_md5");
        String type = getRequest().getParameter("type");
        if(StringUtils.isBlank(type)) type="0";
        ClientError clientError = clientErrorService.findErrorDetails(desc_md5);
        String desc = clientError.getDesc();
        desc = desc.replace("，","\n");
        clientError.setDesc(desc);
        clientError.setTableType(type);
        return clientError;
    }
    @RequestMapping("todayList")
    @ResponseBody
    public String getTodayList() {
        List<ClientError> data = (List<ClientError>) clientErrorService.getPageList();
        return getDataTableJson(data, data.size()).toJSONString();
    }
    @RequestMapping("errorTerminalList")
    @ResponseBody
    public String getErrorTerminalList(){
        String desc_md5 = getRequest().getParameter("desc_md5");
        String type = getRequest().getParameter("type");
        List<Map<String,Object>> terminalInfos = clientErrorService.findErrorTerminalInfos(desc_md5);
        String resp = getDataTableJson(terminalInfos, terminalInfos.size()).toJSONString();
        return resp;
    }
    @RequestMapping("changeStatus")
    @ResponseBody
    public AjaxObj changeStatus(){
        String desc_md5 = getRequest().getParameter("desc_md5");
        String type = getRequest().getParameter("type");
        String status = getRequest().getParameter("status");
        try{
           clientErrorService.updateErrorStatus(status,desc_md5);
           return new AjaxObj(AjaxObj.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxObj(AjaxObj.FAILURE,"更新状态失败");
        }
    }
    @RequestMapping("getErrorList")
    @ResponseBody
    public String getErrorList(String params){

        JSONObject paramJson = JSONUtil.parseObject(params);

        String plat = paramJson.getString("plat");
        String sid = paramJson.getString("sid");
        if(paramJson ==null || sid == null){
            return getNullDataTable();
        }

        String title = paramJson.getString("title");
        String status = paramJson.getString("status");
        String version = paramJson.getString("version");
        String version_lua = paramJson.getString("version_lua");

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("table", "d_client_error_log");
        queryParams.put("plat", plat);
        queryParams.put("sid", sid);
        queryParams.put("title",title);
        queryParams.put("status",status);
        queryParams.put("version",version);
        queryParams.put("versionLua",version_lua);
        dealOrder(getRequest(),queryParams);


        PageUtil<ClientError> datas = clientErrorService.getPageList(queryParams);
        return getDataTableJson(datas.getDatas(),datas.getTotalRecord()).toJSONString();
    }
    protected void dealOrder(HttpServletRequest request, Map<String,Object> params) throws JSONException {
        String sortIndex = request.getParameter("iSortCol_0");//排序的列索引
        if(StringUtils.isBlank(sortIndex)){
            return;
        }
        if(org.apache.commons.lang.StringUtils.equals(sortIndex,"0")){
            params.put("sort", "plat");
        }
        if(org.apache.commons.lang.StringUtils.equals(sortIndex,"1")){
            params.put("sort", "sid");
        }if(org.apache.commons.lang.StringUtils.equals(sortIndex,"2")){
            params.put("sort", "title");
        }
        if(org.apache.commons.lang.StringUtils.equals(sortIndex,"3")){
            params.put("sort", "version");
        }
        if(org.apache.commons.lang.StringUtils.equals(sortIndex,"4")){
            params.put("sort", "first_time");
        }
        if(org.apache.commons.lang.StringUtils.equals(sortIndex,"5")){
            params.put("sort", "latest_time");
        }if(org.apache.commons.lang.StringUtils.equals(sortIndex,"6")){
            params.put("sort", "occur_count");
        }

        String order = request.getParameter("sSortDir_0");//排序的顺序
        if(StringUtils.isBlank(order)){
            order = "desc";
        }
        params.put("order", order);

    }
}
