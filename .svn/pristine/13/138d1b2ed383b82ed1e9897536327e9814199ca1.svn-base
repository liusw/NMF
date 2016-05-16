var MyHive = {
	showId : "#hql",// 页面显示hive语句的id
	plat : "#plat",
	bpid : "#bpid",
	sid : "#sid",
	stime : "#stime",
	etime : "#etime",
	tables : {
		
	},
	singleHql : function(table,columns,filter,title,distinctData){
		if(!columns){
			alert("没有选择字段!");
			return false;
		}
		
		var hsql = this.tables[table].sql;
		
		var platId = $("#plats").val() || $("#navPlat").val();
		var svid = $("#plats option:selected").attr("svid") || $("#navPlat option:selected").attr("svid");
		var sid = $("#sid").attr("selectSidValues") || $("#navMultiSid").attr("selectSidValues");
		var bpid = $("#sid").attr("selectBpidValues") || $("#navMultiSid").attr("selectBpidValues");
		
		var fieldsName = '_plat' + ',';
		if(title){
			fieldsName += title;
		}else{
			fieldsName += columns;
		}
		columns = platId + ',' + columns;//默认加上platId，方便获取hbase的用户信息
		
		if(!sid || sid == "undefined"){
			hsql = hsql.replace(/and sid in\(#sid#\)/g,"");
		}
		if(!bpid || bpid == "undefined"){
			hsql = hsql.replace(/and `_bpid` in\(#bpid#\)/g,"");
		}
		if(sid || bpid){
			hsql = hsql.replace(/and `_plat`=#plat#/g,"");
		}
		
		hsql = hsql.replace(/#columns#/g, columns);
		hsql = hsql.replace(/#plat#/g, platId);
		hsql = hsql.replace(/#svid#/g, svid);
		hsql = hsql.replace(/#bpid#/g,bpid);
		hsql = hsql.replace(/#sid#/g,sid);
		hsql = hsql.replace(/#startTime#/g,date2Num($(this.stime).val()) || navForm.daterange.getStartDate("#navdaterange"));
		hsql = hsql.replace(/#endTime#/g,date2Num($(this.etime).val()) || navForm.daterange.getEndDate("#navdaterange"));
		
		if(filter){
			hsql += filter;
		}
		
		if(distinctData && distinctData==1){
			hsql += (" group by " + columns);
		}
		
		$(this.showId).val(hsql);
		
		$("#fieldsName").val(fieldsName.replace(/`/g,""));
	},
	initSelect : function(selectId,sucessFun,param){
		param = param ? param : {};
		$.post('config/genSql/getGenSql.htm', param,function (data, textStatus) {
			var result = data.data;
			if(result){
				//for(var i in result){
				for(var i=0;i<result.length;i++){
					var d = eval(result[i]);
					var tableName = d.tableName;
					MyHive.tables[tableName] = {};
					MyHive.tables[tableName].name = d.showName;
					MyHive.tables[tableName].sql = d.sqlStr;
				}
				if(!selectId){
					selectId = "tableNames";
				}
				$("#" + selectId).html("");
				/*for(var s in MyHive.tables){
					$("#" + selectId).append("<option value='" + s +"'>" + MyHive.tables[s].name + "[" + s + "]</option>");
				}*/
				$.each(MyHive.tables,function(s,o){
					$("#" + selectId).append("<option value='" + s +"'>" + o.name + "[" + s + "]</option>");
				});
				
				if(sucessFun){
					sucessFun();
				}
				
				$("#tableNames").select2();
			}
	    },"json").error(function(){
			showMsg(commonData.errorMsg);
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

function showHql(tableName){
	var columns = $('input:checkbox[name=column]:checked').map(function(){
		var result = this.value;
		if(result.charAt(0) == "_"){
			result = "`" + result + "`";
		}
		return result;
	}).get().join();
	
	var title = $('input:checkbox[name=column]:checked').map(function(){
		var result = this.value;
		var title = $(this).attr("title");
		if(result == "_uid" || result == "_plat"){
			result = "`" + result + "`";
		}else{
			result = title.replace(/,/g,"");;
		}
		return result;
	}).get().join();
	
	var format = {};
	$("#formatDiv .formatValue").each(function(){
		var that = $(this);
		var column = that.attr("column");
		format[column] = that.attr("value") + "$#" + that.attr("columnTitle") + "#$" + that.attr("title");
	});
	
	for(var i in format){
	//for(var i=0;i<format.length;i++){
		var columnAndTitle = format[i].split("$#");
		var column = columnAndTitle[0];
		var titleTmp = columnAndTitle[1].split("#$");
		var columnTitle = titleTmp[0];
		titleTmp = titleTmp[1];
		if(columns.indexOf(i) != -1){
			columns = columns.replace(i,column);
			title = title.replace(titleTmp, columnTitle);
		}else{
			if(columns){
				columns += ",";
				title += ",";
			}
			columns += column;
			title += columnTitle;
		}
	}
	
	if(!columns){
		alert("没有选择字段！");
		return false;
	}
	if(!$("#stime").val()){
		alert("请选择开始时间！");
		return false;
	}
	if(!$("#etime").val()){
		alert("请选择结束时间！");
		return false;
	}
	var filter = "";
	$("#filterDiv .filterValue").each(function(){
		filter += " and " + $(this).attr("value");
	});
	
	var distinctData = false;
	if(document.getElementById("distinctData").checked){
		distinctData = true;
	}
	
	MyHive.singleHql($("#tableNames").val(),columns,filter,title,distinctData);
	$('#showColMsg').modal('hide');
}

function genHSql(){
	//$('#showColMsg').removeAttr("tabindex");
	$('#showColMsg').modal('show');
	setTimeout(function(){
		if(!$("#msgColContent").html()){
			showColumns();
			getMultiPlat("#plats", "#sid");
		}
	},200);
}

/**
*显示可以选择到字段
**/
function showColumns(){
	var tableName=$("#tableNames").val().toLowerCase();
	
	$("#msgColContent").html(commonMsg.loading);
	var columns = getConfigColumns(tableName,'hive',true); 
	var content="";
	var options = "";
	$.each(columns, function(index, value) {
		if(value.column_name != "_plat" && value.column_name != "plat" && (!(tableName=='gambling_detail' && value.column_name == "svid"))){//_plat不显示
			content += '<label class="checkbox-inline"><input type="checkbox" name="column" title="'+value.comment+'" value="' + value.column_name + '" ';
			if(value.column_name == "_uid"){
				content += " checked ";
			}
			content += ' >' + value.comment+'('+ value.column_name+ ')</input></label>';
			options += '<option value="' + value.column_name + '" title="' + value.comment + '">' + value.comment +'('+ value.column_name + ')</option>';
		}
	});
	$("#msgColContent").html(content);
	$("#formatColumns").html(options);
	$("#filterColumns").html(options);
}

function addFilter(){
	var column = $("#filterColumns").val();
	var columnText = $("#filterColumns option:selected").text();
	var compare = $("#compare").val();
	var compareText = $("#compare option:selected").text();
	var compareValue = $("#compareValue").val();
	if(!column || !compareValue){
		alert("请填写完整过滤条件！");
		return false;
	}
	if(column.charAt(0) == "_"){
		column = "`" + column + "`";
	}
	var condition = "";
	var showCondition = "";
	if(compare == "in"){
		compareValue = compareValue.replace(/;|,|，|;/g,",");
		condition = column + " in(" + compareValue + ")";
		showCondition = columnText + " " + compareText + " " + compareValue;
	}else if(compare == "like"){
		condition = column + " like '%" + compareValue + "%'";
		showCondition = columnText + " " + compareText + " " + compareValue;
	}else{
		if(compare == "str="){//字符串等于，加单引号
			condition = column + "='" + compareValue + "'";
		}else if(compare =="="){//实时查询的不需要单引号
			condition = column + compare + compareValue;
		}else if(compare == "!="){
			condition = column + "!='" + compareValue + "'";
		}else{
			condition = "cast("+ column+" as bigint) " + compare + "'" + compareValue + "'";
		}
		showCondition = columnText + compareText + compareValue;
	}
	
	var html = '<div class="labelColumn">' +
					'<p>' +
					'<span class="filterValue" value="' + condition +'">' + showCondition + '</span>' +
					'<span class="labelClose" onclick="labelClose(this)">×</span>' +
					'</p>' +
				'</div>';
	$("#filterDiv").append(html);
}

function addFormat(){
	var column = $("#formatColumns").val();
	var titleTmp = $("#formatColumns option:selected").attr("title");
	var title = titleTmp.replace(/,/g,'') + "_格式化";
	var columnText = $("#formatColumns option:selected").text();
	var type = $("#formatType").val();
	var typeText = $("#formatType option:selected").text();
	var keepOldData = $("#keepOldData")[0].checked;
	var condition = "";
	var showCondition = columnText + typeText;
	if(column.charAt(0) == "_"){
		column = "`" + column + "`";
	}
	if(type == 0){
		var format = $("#timeFormatType").val();
		condition = "from_unixtime(cast(" + column + " as bigint), '" + format + "')";
	}else{
		condition = "geoip(" + column + ")";
	}
	if(keepOldData){
		condition += "," + column;
		showCondition += "#保留原数据";
		title += "," + titleTmp;
	}
	var html = '<div class="labelColumn">' +
					'<p>' +
					'<span class="formatValue" column="' + column + '" value="' + condition +'" columnTitle="' + title + '" title="' + titleTmp + '">' + showCondition + '</span>' +
					'<span class="labelClose" onclick="labelClose(this)">×</span>' +
					'</p>' +
				'</div>';
	$("#formatDiv").append(html);
}

function formatTypeChange(){
	if($("#formatType").val() == 0){
		$("#timeFormatTypeDiv").show();
	}else{
		$("#timeFormatTypeDiv").hide();
	}
}

function labelClose(o) {
	$(o).parent().parent().remove();
}
