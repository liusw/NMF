package com.boyaa.mf.web.controller.open;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.config.HbaseMeta;
import com.boyaa.mf.service.config.HBaseMetaService;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liusw
 * 创建时间：16-3-31.
 */
@Controller
@RequestMapping(value = "hbtables")
public class OpenController extends BaseController {

    @Autowired
    private HBaseMetaService hBaseMetaService;

    @RequestMapping(value = "getTables/{sign}/{timestamp}")
    @ResponseBody
    public AjaxObj getTables(@PathVariable String sign, @PathVariable String timestamp) {
        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp)) {
            return new AjaxObj(AjaxObj.FAILURE, "请求参数错误.");
        }
        try {
            if(!DigestUtils.md5Hex(timestamp+"|"+ Constants.SIGN_KEY).equals(sign)){
                return new AjaxObj(AjaxObj.FAILURE, "验证sign不正确");
            }
            if((new Date().getTime()/1000-60*60)>Long.parseLong(timestamp)){
                return new AjaxObj(AjaxObj.FAILURE, "请求时间已过期,time过期时间为1个小时");
            }
            List<HbaseMeta> tables = hBaseMetaService.getHbTables(null);
            List<String> tableNames = new ArrayList<String>();
            for (int i = 0; i < tables.size(); i++) {
                HbaseMeta meta = tables.get(i);
                tableNames.add(meta.getTableName());
            }
            return new AjaxObj(AjaxObj.SUCCESS, "", tableNames);

        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            return new AjaxObj(ResultState.FAILURE, "query error.");
        }
    }

    @RequestMapping(value = "getHbtableColumns/{tableName}/{md5}/{timestamp}")
    @ResponseBody
    public AjaxObj getHbtableColumns(@PathVariable String tableName, @PathVariable String sign, @PathVariable String timestamp) {
        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(tableName)) {
            return new AjaxObj(AjaxObj.FAILURE, "请求参数错误.");
        }
        try {
            if (!DigestUtils.md5Hex(timestamp + "|" + Constants.SIGN_KEY).equals(sign)) {
                return new AjaxObj(AjaxObj.FAILURE, "验证sign不正确");
            }
            if ((new Date().getTime() / 1000 - 60 * 60) > Long.parseLong(timestamp)) {
                return new AjaxObj(AjaxObj.FAILURE, "请求时间已过期,time过期时间为1个小时");
            }
            List<HbaseMeta> tables = hBaseMetaService.getHbTables(tableName);
            return new AjaxObj(AjaxObj.SUCCESS, "", (tables != null && tables.size() > 0) ? tables.get(0) : null);

        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            return new AjaxObj(ResultState.FAILURE, "query error.");
        }
    }

}
