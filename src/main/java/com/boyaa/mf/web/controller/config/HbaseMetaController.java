package com.boyaa.mf.web.controller.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boyaa.mf.util.ServletUtil;
import com.boyaa.mf.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boyaa.mf.entity.config.HbaseMeta;
import com.boyaa.mf.service.config.HBaseMetaService;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.mf.web.dto.HbColumnMeta;

/**
 * Created by liusw
 * 创建时间：16-3-22.
 */
@Controller
@RequestMapping("hbaseMeta")
public class HbaseMetaController extends BaseController {

    @Autowired
    private HBaseMetaService hBaseMetaService;

    static Logger logger = Logger.getLogger(HbaseMetaController.class);
    static Logger errorLogger = Logger.getLogger("errorLogger");
    static Logger fatalLogger = Logger.getLogger("fatalLogger");

    @RequestMapping(value = "initConfig")
    @ResponseBody
    public AjaxObj initHbtablesConfig(HttpServletRequest request) {
        logger.info("request ip:" + ServletUtil.getRemortIP(request));
        AjaxObj data = null;
        try {
            hBaseMetaService.initHbaseMetaConfig();
            data = new AjaxObj(AjaxObj.SUCCESS, "init success..");
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            data = new AjaxObj(AjaxObj.FAILURE);
            data.setMsg(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "getHbTables")
    @ResponseBody
    public AjaxObj getHbTables(HttpServletRequest request) {

        logger.info("request ip:" + ServletUtil.getRemortIP(request));
        String tableName = request.getParameter("tableName");
        AjaxObj data = null;
        try {
            List<HbaseMeta> tables = hBaseMetaService.getHbTables(tableName);
            data = new AjaxObj(AjaxObj.SUCCESS, "", tables);
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            data = new AjaxObj(AjaxObj.FAILURE);
            data.setMsg(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "getHBaseMetaInfo")
    @ResponseBody
    public AjaxObj getHBaseMetaInfo(HttpServletRequest request, String tableName) {
        logger.info("request ip:" + ServletUtil.getRemortIP(request));
        AjaxObj data = null;
        if (StringUtils.isEmpty(tableName)) {
            return new AjaxObj(AjaxObj.FAILURE, "hbTableName can not be empty.");
        }
        try {
            List<HbColumnMeta> hbColumnMeta = hBaseMetaService.getHbaseMetaInfo(tableName);
            data = new AjaxObj(AjaxObj.SUCCESS, "", hbColumnMeta);
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            data = new AjaxObj(AjaxObj.FAILURE);
            data.setMsg(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "updateColumnMeta")
    @ResponseBody
    public AjaxObj updateColumnMeta(HttpServletRequest request) {
        logger.info("request ip:" + ServletUtil.getRemortIP(request));
        AjaxObj data = null;

        String tableName = request.getParameter("tableName");
        String colName = request.getParameter("colName");
        String colType = request.getParameter("colType");
        String colValue = request.getParameter("colValue");
        String comment = request.getParameter("comment");

        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(colName)) {
            return new AjaxObj(AjaxObj.FAILURE, "params error.");
        }
        try {
            hBaseMetaService.updateHbaseMeta(tableName, colName, colType, colValue, comment);
            data = new AjaxObj(AjaxObj.SUCCESS);
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            data = new AjaxObj(AjaxObj.FAILURE);
            data.setMsg(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "addHbaseTable")
    @ResponseBody
    public AjaxObj addHbaseTable() {
        AjaxObj data = null;
        String tableName = getRequest().getParameter("tableName");
        String tableAlias = getRequest().getParameter("tableAlias");
        String fields = getRequest().getParameter("fields");
        String rowkeyFields = getRequest().getParameter("rowkeyFields");
        String systemPro = getRequest().getParameter("systemPro");

        try {
            hBaseMetaService.addHbTable(tableName, tableAlias, fields, rowkeyFields, systemPro);
            data = new AjaxObj(AjaxObj.SUCCESS);
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
            data = new AjaxObj(AjaxObj.FAILURE);
            data.setMsg(e.getMessage());
        }
        return data;
    }
}
