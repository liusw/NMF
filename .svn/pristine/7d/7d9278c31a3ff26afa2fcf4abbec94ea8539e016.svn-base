package com.boyaa.mf.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.boyaa.base.mail.Mail;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.config.Plat;
import com.boyaa.mf.entity.data.BanCount;
import com.boyaa.mf.service.config.ConfigService;
import com.boyaa.mf.service.data.BanCountService;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SendBanCountEmailJob implements Job {
	static Logger taskLogger = Logger.getLogger("taskLogger");
	
	@Autowired
	private BanCountService banCountService;
	@Autowired
	private ConfigService configService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(Constants.AUTO_RUNNING_SERVER!=null && Constants.AUTO_RUNNING_SERVER.equals(Constants.ip)){
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
			Date dBefore = calendar.getTime();   //得到前一天的时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
			String beforeDate = sdf.format(dBefore);    //格式化前一天
			
			//发邮件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tm", beforeDate);
			params.put("start", 0);
			params.put("end", Integer.MAX_VALUE);
			params.put("gt604",1);
			
			List<BanCount> banCounts = banCountService.findScrollDataList(params);
					
			if(banCounts!=null && banCounts.size()>0){
				List<Plat> plats = configService.getPlat();
				String platName = null;
				
				StringBuffer table = new StringBuffer();
				
				table.append("查封用户汇总导出,在线查看地址:<a href='http://mf.oa.com/log/banCount.jsp'>http://mf.oa.com/log/banCount.jsp</a>");
				table.append("<br/>");
				
				table.append("<table border='1' cellspacing='0' cellpadding='0' >");
				table.append("<tr><th width='8%'>时间</th><th width='8%'>平台</th><th width='8%'>被封用户数</th><th width='8%'>扣除游戏币总数</th><th width='8%'>清0用户数</th><th width='8%'>清0游戏币</th><th width='8%'>查封用户数同比</th><th width='8%'>查封游戏币同比</th></tr>");
	
				for(BanCount banCount:banCounts){
					for(Plat plat:plats){
						if(plat.getPlat().intValue()==banCount.getPlat()){
							platName = plat.getPlatName();
							break;
						}
					}
					
					table.append("<tr><td align='center' valign='middle'>"+banCount.getTm()+"</td>" +
							"<td>"+banCount.getPlat()+(StringUtils.isNotBlank(platName)?"["+platName+"]":"")+"</td>" +
							"<td align='center' valign='middle'>"+banCount.getBanCount()+"</td>" +
							"<td align='center' valign='middle'>"+banCount.getDeductCoins()+"</td>" +
							"<td align='center' valign='middle'>"+banCount.getClearZeroCount()+"</td>" +
							"<td align='center' valign='middle'>"+banCount.getClearZeroCoins()+"</td>" +
							"<td align='center' valign='middle'>"+banCount.getYesterdayBanCountRate()+"</td>" +
							"<td align='center' valign='middle'>"+banCount.getYesterdayBanCoinsRate()+"</td></tr>");
				}
				
				table.append("</table>");
				table.append("<br/>");
				table.append("问题反馈：黄奕能(MarsHuang@boyaa.com)");
				
				String env = com.boyaa.base.utils.Constants.env;
				String sender = "MarsHuang@boyaa.com"+((env!=null && env.equals("server"))?";"+Constants.BAN_COUNT_EMAIL:"");
				
				new Mail().send(beforeDate+"查封用户汇总导出",sender,table.toString(),null);
				taskLogger.info(beforeDate+"查封用户汇总导出"+","+table.toString());
			}
		}
	}
}