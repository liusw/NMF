var MyHive = {
	showId : "#hsql",// 页面显示hive语句的id
	listId : "#listId",
	plat : "#plat",
	bpid : "#bpid",
	sid : "#sid",
	stime : "#stime",
	etime : "#etime",
	tables : {
		user_order : {
			sql 	: "select `_uid`,count(1) orderNum,sum(cast(pamount as double)*cast(prate as double)) sumPamount from user_order where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat# group by `_uid`",
			name	: '订单',
			fields	: {
				orderNum	:	'付费次数',
				sumPamount	: 	'付费总额'
			}
		},
		last_user_order : {
			sql 	: "select `_uid`,lastPamount,lastOrderTm,pmoneynow from" +
					" (select `_uid`,cast(pamount as double)*cast(prate as double) lastPamount,pmoneynow,from_unixtime(cast(`_tm` as bigint), 'yyyy-MM-dd') lastOrderTm,rank() over(partition by `_uid` order by `_tm` desc) as rank from user_order where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat#)a where a.rank=1",
			name	: '最后一次订单',
			fields	: {

			}
		},
		user_login : {
			sql		: "select `_uid`,count(1) countLogin from user_login where plat=#plat# and tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat# group by `_uid`",
			name	: '登录',
			fields	: {
				countLogin	:	'登录次数'
			}
		},
		last_user_login : {
			sql		: "select `_uid`,logincoins,lastLoginTm from" +
					" (select `_uid`,logincoins,from_unixtime(cast(`_tm` as bigint), 'yyyy-MM-dd') lastLoginTm,rank() over(partition by `_uid` order by `_tm` desc) as rank from user_login where plat=#plat# and tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat#)a where a.rank=1",
			name	: '最后一次登录',
			fields	: {

			}
		},
		gamecoins_stream : {
			sql		: "select `_uid` from gamecoins_stream where plat=#plat# and tm between #startTime# and #endTime# and sid=#sid# group by `_uid`",
			name	: '金币流水',
			fields	: {
				
			}
		},
		user_gambling : {
			sql		: "select `_uid` from user_gambling where plat=#plat# and tm between #startTime# and #endTime# and sid=#sid# group by `_uid`",
			name	: '牌局'
		},
		gambling_detail : {
			sql		: "select `_uid`,tid,bid from gambling_detail where plat=#plat# and tm between #startTime# and #endTime# and sid=#sid# group by `_uid`",
			name	: '牌局详情'
		},
		user_signup : {
			sql 	: "select `_uid` from user_signup where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat# group by `_uid`",
			name	: '注册',
			fields	: {
				
			}
		},
		bankrupt : {
			sql		: "select `_uid`,count(1) as bankruptNun from bankrupt where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat# group by `_uid`",
			name	: '破产',
			fields	: {
				
			}
		},
		last_bankrupt : {
			sql		: "select `_uid`,lastBankruptTm from" +
					" (select `_uid`,from_unixtime(cast(`_tm` as bigint), 'yyyy-MM-dd') lastBankruptTm,rank() over(partition by `_uid` order by `_tm` desc) as rank from bankrupt where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat#)a where a.rank=1",
			name	: '最后一次破产',
			fields	: {
				
			}
		},
		user_uid : {
			sql		: "select `_uid` from user_uid where tm between #startTime# and #endTime# and sid=#sid# and plat=#plat# group by `_uid`",
			name	: '活跃'
		},
		relief : {
			sql 	: "select `_uid`,count(1) countRelief from relief where tm>=#startTime# and `_bpid`='#bpid#' and `_plat`=#plat# group by `_uid`",
			name	: '救济',
			fields	: {
				
			}
		},
		baglog : {
			sql		: "select `_uid`,sum(val) sumval from baglog where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat# group by `_uid`",
			name	: '礼包',
			fields	: {
				
			}
		},
		admin_act_logs : {
			sql 	: "select `_uid`,isban from admin_act_logs where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat# group by `_uid`",
			name	: '管理员日志',
			fields	: {
				
			}
		},
		free_chips : {
			sql 	: "select `_uid`,sum(mfc) sumMfc from free_chips where tm between #startTime# and #endTime# and `_plat`=#plat# group by `_uid`",
			name	: '免费筹码',
			fields	: {
				
			}
		},
		winlog_mode : {
			sql 	: "select `_uid` from winlog_mode where plat=#plat# and tm between #startTime# and #endTime# and sid=#sid# and `_plat`=#plat#",
			name	: '金币明细'
		},
		event_logs : {
			sql 	: "select `_uid` from event_logs where tm between #startTime# and #endTime# and `_plat`=#plat# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '事件'
		},
		guess_hand_poker : {
			sql 	: "select `_uid` from guess_hand_poker where tm between #startTime# and #endTime# and `_plat`=#plat# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '猜手牌'
		},
		interactive_props_logs : {
			sql 	: "select `_uid` from interactive_props_logs where tm between #startTime# and #endTime# and `_plat`=#plat# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '道具'
		},
		feed_send : {
			sql 	: "select `_uid` from feed_send where tm between #startTime# and #endTime# and `_plat`=#plat# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '发送feed'
		},
		feed_clicked : {
			sql 	: "select `_uid` from feed_clicked where tm between #startTime# and #endTime# and `_plat`=#plat# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '点击feed'
		},
		mb_device_login_log : {
			sql 	: "select `_uid`,phone_num from mb_device_login_log where tm between #startTime# and #endTime# and `_plat`=#plat# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '移动设备信息'
		},
		autotest : {
			sql 	: "select `_uid` from autotest where plat=#plat# and tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '用户行为'
		},
		user_actions : {
			sql 	: "select `_uid` from user_actions where plat=#plat# and tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '用户动作'
		},
		banklogs : {
			sql 	: "select `_uid` from banklogs where tm between #startTime# and #endTime# and `_bpid`='#bpid#' and `_plat`=#plat#",
			name	: '银行操作日志'
		}
	},
	getTableNames : function() {
		for ( var s in this.tables) {
			console.log(s);
		}
	},
	hsqlHeader : "insert overwrite local directory '/data/mf/tmp/#listId#' ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' <br/>" +
			"select #plat#,t0.`_uid` from",
	combineTable : [],
	addCombineTable : function(table) {
		this.combineTable.push(table);
		this.combine();
	},
	combine : function() {
		hsql = this.hsqlHeader + "<br/>";
		for( var s in this.combineTable){
			var tableName = "t" + s;
			if(s == 0){
				hsql += "(" + this.tables[this.combineTable[s]].sql +")" + tableName;
			}else{
				hsql += "(" + this.tables[this.combineTable[s]].sql +")" + tableName + " on(t0.`_uid`=" + tableName + ".`_uid`)";
			}
			if(s != this.combineTable.length-1){
				hsql += " left join <br/>";
			}else{
				hsql += ";";
			}
		}
		
		var platId = $("#plats").val();
		var sid_bpid = $("#sid").val().split("_");
		var sid = sid_bpid[0];
		var bpid = sid_bpid[1];
	
		if(!sid || sid == "undefined"){
			hsql = hsql.replace(/and sid=#sid#/g,"");
		}
		if(!bpid || bpid == "undefined"){
			hsql = hsql.replace(/and `_bpid`='#bpid#'/g,"");
		}
		if(sid || bpid){
			hsql = hsql.replace(/and `_plat`=#plat#/g,"");
		}
		
		hsql = hsql.replace(/#plat#/g, platId);
		hsql = hsql.replace(/#bpid#/g,bpid);
		hsql = hsql.replace(/#sid#/g,sid);
		hsql = hsql.replace(/#startTime#/g,date2Num($(this.stime).val()));
		hsql = hsql.replace(/#endTime#/g,date2Num($(this.etime).val()));
		hsql = hsql.replace(/#listId#/g,$(this.listId).val());
		$(this.showId).html(hsql);
	},
	hbase : "java -jar /data/mf/app/newexport/MultiThreadQuery.jar /data/mf/app/newexport/app.config /data/mf/tmp/#listId# /data/mf/tmp/#listId#.csv  " +
			"\"{'_tnm':'user_ucuser','retkey':'#retkey#','rowkey':'false'," +
			"'file_column':'_plat,_uid','output_column':'_uid,#retkey#','output_title':'#itemsName#','delExists':'true'," +
			"'threadCount':'5','format':{'mtime':'yyyy-MM-dd','logintime':'yyyy-MM-dd','mactivetime':'yyyy-MM-dd'},comparator__condition':{'mstatus':'1,3'}," +
			"'ipformat':'loginip','replace':{'email':'***,3'}}\"",
	combineHbase : function(){
		var items = $('input:checkbox[name=items]:checked').map(function(){
			return this.value;
		}).get().join();
		var itemsName = $('input:checkbox[name=items]:checked').map(function(){
			return $(this).attr("valueName");
		}).get().join();
		if(items){
			var hbase = this.hbase.replace(/#listId#/g,$(this.listId).val());
			hbase = hbase.replace(/#retkey#/g,items);
			hbase = hbase.replace(/#itemsName#/, itemsName);
			$("#hbase").html(hbase);
		}else{
			$("#hbase").html("");
		}
	},
	singleHql : function(table,columns){
		if(!columns){
			alert("没有选择字段!");
			return false;
		}
		
		hsql = this.tables[table].sql;

		var platId = $("#plats").val() || $("#navPlat").val();
		
		var sid_bpid = ($("#sid").val()|| $("#navMultiSid").val()).split("_");
		var sid = sid_bpid[0];
		var bpid = sid_bpid[1];
	
		if(!sid || sid == "undefined"){
			hsql = hsql.replace(/and sid=#sid#/g,"");
		}
		if(!bpid || bpid == "undefined"){
			hsql = hsql.replace(/and `_bpid`='#bpid#'/g,"");
		}
		
		hsql = hsql.replace(hsql.substring(7,hsql.indexOf("from",0)-1), columns);
		
		hsql = hsql.replace(/#plat#/g, platId);
		hsql = hsql.replace(/#bpid#/g,bpid);
		hsql = hsql.replace(/#sid#/g,sid);
		hsql = hsql.replace(/#startTime#/g,date2Num($(this.stime).val()) || navForm.daterange.getStartDate("#navdaterange"));
		hsql = hsql.replace(/#endTime#/g,date2Num($(this.etime).val()) || navForm.daterange.getEndDate("#navdaterange"));
		hsql = hsql.replace(/#listId#/g,$(this.listId).val());
		hsql = hsql.replace("group by `_uid`","");//这个方法的生成不用group by 
		
		$("#hql").val(hsql);
		
		$("#fieldsName").val(columns.replace(/`/g,""));
	},
	initSelect : function(selectId){
		if(!selectId){
			selectId = "tableNames";
		}
		/*for(var s in MyHive.tables){
			$("#" + selectId).append("<option value='" + s +"'>" + MyHive.tables[s].name + "</option>");
		}*/
		$.each(MyHive.tables,function(s,o){
			$("#" + selectId).append("<option value='" + s +"'>" + o.name + "[" + s + "]</option>");
		});
	}

};

/**
 * 日期格式转换为数字
 * @param dateStr  格式：xxxx-xx-xx or xxxx/xx/xx
 * @returns {Number}
 */
function date2Num(dateStr){
	if(dateStr){
		var now = new Date(dateStr);
		var years = now.getFullYear();
		var month = now.getMonth()+1;
		if(month <10){
			month = "0" + month;
		}
		var day = now.getDate();
		if(day < 10){
			day = "0" + day;
		}
		return years.toString() + month.toString() + day.toString();
	}else{
		return 0;
	}
}

/**
 * 日期加减
 * @param dateStr  格式：xxxx-xx-xx or xxxx/xx/xx
 * @param addNum 加的天数
 * return 
 */
function addDate(dateStr,addNum){
	if(dateStr && addNum){
		var a = new Date(dateStr);
		a = a.valueOf();
		a = a + addNum * 24 * 60 * 60 * 1000;
		a = new Date(a);
		return a;
	}else{
		return null;
	}
	
}




