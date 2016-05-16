package com.boyaa.service;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.boyaa.base.db.MySQLService;
import com.boyaa.entity.BrushTemplate;

public class BrushTemplateService extends MySQLService{
	static Logger logger = Logger.getLogger(BrushTemplateService.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");

	public BrushTemplateService() throws SQLException {
		super();
	}
	
	public JSONArray query(BrushTemplate brushTemplate) throws SQLException{
		if(brushTemplate != null){
			return this.find("select * from logcenter_mf.template where _plat=" + brushTemplate.getPlat() + " and version=3");
		}
		return null;
	}
	
	public int insert(BrushTemplate brushTemplate) throws SQLException{
		if(brushTemplate != null){
			if(brushTemplate.getPlat() > 0){
				if(StringUtils.isNotBlank(brushTemplate.getContent())){
					return this.getKey("insert into logcenter_mf.template(_plat,sid,name,content,version,uid,uname) values(" + brushTemplate.getPlat() + "," +
							brushTemplate.getSid() + ",'" + brushTemplate.getName() + "','" + brushTemplate.getContent() + "'," +
							brushTemplate.getVersion() + "," + brushTemplate.getUid() + ",'" + brushTemplate.getUname() + "')");
				}
			}
		}
		return 0;
	}
	
	public void update(BrushTemplate brushTemplate) throws SQLException{
		if(brushTemplate != null){
			if(brushTemplate.getPlat() > 0){
				if(StringUtils.isNotBlank(brushTemplate.getContent())){
					this.execute("update logcenter_mf.template set content='" + brushTemplate.getContent() + 
							"',uid=" + brushTemplate.getUid() + ",uname='" + brushTemplate.getUname() + 
							"' where id=" + brushTemplate.getId());
				}
			}
		}
	}
	
	public void delete(){
		
	}

}
