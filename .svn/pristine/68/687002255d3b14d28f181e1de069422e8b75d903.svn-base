/****************查询hive元数据信息***********************/
function forEditMeta(id,type,columnName,comment){
	var html = '<form class="form-horizontal">'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">字段名:</label>'
					+	'<div class="col-xs-6">'
					+   	'<div class="form-control">' + columnName + '</div>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">注释:</label>'
					+	'<div class="col-xs-6">'
					+   	'<input type="text" id="comment" class="form-control" value="' + comment + '">'
					+   	'<input type="text" class="displayNone">'
					+	'</div>'
					+ '</div>'
				+ '</form>';
	var param = {};
	param.id = id;
	param.type = type;
	param.columnName = columnName;
	showMsg(html,"编辑注释",editMeta,param);
	setTimeout(function(){$("#comment").focus();},500);
	pressEnter('comment',editMeta,param);
}

function editMeta(param){
	param.comment = $("#comment").val();
	param.tableName = $("#tableName").val();
	$.post('hiveMeta/updateComment.htm', param,function (data, textStatus) {
		getHiveMetaInfo();
		closeMsg();
    },"json");
}

function getHiveMetaInfo(){
	var tableName = $("#hiveMetaTableName").val().toLowerCase();
	var url = 'hiveMeta/getColumnInfo.htm';
	if(tableName){
		url = 'hiveMeta/getColumnInfo.htm?tableName='+tableName;
	}
	$.ajax({
        type: "get",
        url: url,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
        	 var json = eval(data.loop);
        	 var table = $('#example').DataTable({
        		 destroy		: true,
        		 searching		: false,
        		 processing		: true,
        		 stateSave		: true,
    	         data 			: json,
    	         "tabIndex" 	: 1,
    	         "columns": [
    	                     { "data": "id"},
    	                     { "data": "column_name"},
    	                     { "data": "comment"},
    	                     { "data": "valueType" },
    	                     { "data": "type" }
    	          ],
    	    	 "oLanguage": commonData.oLanguage,
    	    	 "dom": 't<"col-sm-6"l><"col-sm-6"p>',
		         "fnRowCallback":function(nRow,aData,iDataIndex){
		              	var aDataId = aData.id;
		              	var type = aData.type;
		              	var comment = aData.comment.replace("'","");
		              	var typeName = "字段";
		              	var column_name = aData.column_name;
		              	if(type == 1){
		              		typeName = "分区";
		              	}
		          		$('td:eq(-1)',nRow).html("<button type='button' class='btn btn-primary btn-xs' onclick='forEditMeta(" + aDataId + "," + type + ",\"" + column_name + "\"" + ",\"" + comment + "\")'>编辑</button>");
		          		//$('td:eq(-2)',nRow).html(typeName);
		          }
    	    });
        },
        error: function (err) {
            showMsg("请求出错!");
            myLog(err);
        }
    });
}

/**************创建hive表*************************/
function initCreateHiveTable(){
	alert(1);
	$("#addForm")[0].reset();
	column = $("#columnDemo").html();//保存添加字段html
	partition = $("#partitionDiv").html();//保存添加字段html
	//initFieldChange("#partitionDiv");
	//initFieldChange("#columnsDiv");
	$("#tmValue").val("tm");
	$("#sorted").val("`_uid`");
	$("#tmDesc").val("时间分区");
	$("#uidValue").val("`_uid`");
	$("#uidDesc").val("用户id");
	$("#clustered").val("`_uid`");
	$("#createTable").click(function(){
		createHiveTabel();
	});
}

function addField(type){
	var parentId = "#columnsDiv";
	if(type == "column"){
		$(parentId).append(column);
	}else{
		parentId = "#partitionDiv";
		$(parentId).append(partition);
	}
	//initFieldChange(parentId);
}

function removeField(type,that){
	var parentId = "#columnsDiv";
	if(type == "partition"){
		parentId = "#partitionDiv";
	}
	if($(parentId + " .form-group").size() <= 1){
		showMsg("最少要有一项！");
		return false;
	};
	$(that).parents('.form-group').remove();
}

function initFieldChange(parentId){
	$(parentId + " input," +parentId+ " select").unbind("change");
	$(parentId + " input," +parentId+ " select").on('change',function(){
		genHSQL();
	});
}

function genHSQL(){
	
	var hsql = 'create table #tableName#_text(#columns#) ROW FORMAT SERDE "org.openx.data.jsonserde.JsonSerDe" STORED AS TEXTFILE;\n' +
	'create table #tableName#(#columns#) #partitions# #clustered# #sorted# #buckets# STORED AS ORC\n';
	
	var tableName = $("#newTableName").val();
	if(!tableName){
		showMsg("请填写表名!");
		return false;
	}
	var columns = getFields("#columnsDiv");
	if(!columns){
		showMsg("请填写字段!");
		return false;
	}
	var partitions = getFields("#partitionDiv");
	var clustered = $("#clustered").val();
	var sorted = $("#sorted").val();
	var buckets = $("#buckets").val();
	
	var notContains = checkConstains(getFields("#columnsDiv",false),clustered);
	if(notContains){
		showMsg("请检查你的clustered,字段没有：" + notContains);
		return false;
	}
	notContains = checkConstains(getFields("#columnsDiv",false),sorted);
	if(notContains){
		showMsg("请检查你的sorted,字段没有：" + notContains);
		return false;
	}
	
	if(isNaN(buckets)){
		showMsg("buckets必须为数字类型!");
		return false;
	}
	
	hsql = hsql.replace(/#tableName#/g,tableName);
	hsql = hsql.replace(/#columns#/g,columns);
	if(!partitions){
		hsql = hsql.replace(/#partitions#/g,"");
	}else{
		hsql = hsql.replace(/#partitions#/g,"PARTITIONED BY (" + partitions + ")");
	}
	if(clustered){
		hsql = hsql.replace(/#clustered#/g,"CLUSTERED BY (" + clustered + ")");
	}else{
		hsql = hsql.replace(/#clustered#/g,"");
	}
	if(sorted){
		hsql = hsql.replace(/#sorted#/g,"SORTED BY (" + sorted + ")");
	}else{
		hsql = hsql.replace(/#sorted#/g,"");
	}
	if(clustered && buckets){
		hsql = hsql.replace(/#buckets#/g,"INTO " + buckets + " BUCKETS");
	}else{
		hsql = hsql.replace(/#buckets#/g,"");
	}
	return hsql;	
}

/**
 * 判断字符串是否含有子字符串
 */
function checkConstains(column,sub){
	var subs = sub.split(",");
	var columns = column.split(",");
	var notContains = "";
	//for(var j in subs){
	for(var j=0;j<subs.length;j++){
		var subTmp = subs[j];
		var contain = false;
		//for(var i in columns){
		for(var i=0;i<columns.length;i++){
			var pTmp = columns[i];
			if(subTmp == pTmp){
				contain = true;
			};
		};
		if(!contain){
			if(notContains){
				notContains += ",";
			}
			notContains += subTmp;
		};
	}
	return notContains;
}

/**
 * 获取字段
 */
function getFields(parentId,all){
	var fields = "";
	if (typeof(all) == "undefined"){
		all = true;
	}
	$(parentId + " div[class=form-group]").each(function(){
		var that = $(this);
		var fieldName = that.find("input[flag=fieldName]").val();
		var fieldType = that.find("select[flag=fieldType]").val();
		var fieldDescr = that.find("input[flag=fieldDescr]").val();
		if(fieldName){
			if(fields){
				fields += ",";
			}
			fields += fieldName;
			if(all){
				fields += " " + fieldType;
				fields += ' comment "' + fieldDescr + '"';
			}
		}
	});
	return fields;
}

function createHiveTabel(){
	var tableName = $("#newTableName").val();
	var hsql = genHSQL();
	if(hsql == false){
		return false;
	}
	$("#createTable").button("loading");
	$.post("hiveMeta/createHiveTable.htm",{'hsql':hsql,'tableName': tableName},function(data){
		showMsg("创建hive表请求完成,请在<a href='task/list/myTask.htm' target='_blank'>我的任务</a>发送审批邮件!");
		//$("#createTable").button("reset");
	},"json").error(function(){
		//$("#createTable").button("reset");
		showMsg(commonData.errorMsg);
	});
}


/**************生成hive语句管理********************/
function forEditGenSql(id,tableName,showName,sqlStr,sort,hasLog){
	var html = '<form class="form-horizontal">'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">表名:</label>'
					+	'<div class="col-xs-6">'
					+   	'<input type="text" id="editTableName" class="form-control" value="' + tableName + '"><span id="editTableNameTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">显示名:</label>'
					+	'<div class="col-xs-6">'
					+   	'<input type="text" id="showName" class="form-control" value="' + showName + '"><span id="showNameTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">语句:</label>'
					+	'<div class="col-xs-6">'
					+   	'<textarea type="text" id="sqlStr" class="form-control">' + sqlStr + '</textarea><span id="sqlStrTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">日志:</label>'
					+	'<div class="col-xs-6">' 
					+   	'<select id="hasLog" class="form-control">' 
					+			'<option value="1">有</option>' 
					+			'<option value="2">无</option>' 
					+ 		'</select><span id="hasLogTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">排序:</label>'
					+	'<div class="col-xs-6">'
					+   	'<input type="text" id="sort" class="form-control" placeholder="升序排序" value="' + sort + '"><span id="sortTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
				+ '</form>';
	var param = {};
	param.id = id;
	showMsg(html,"编辑生成语句",editGenSql,param);
	setTimeout(function(){$("#editTableName").focus();$("#hasLog").val(hasLog);},500);
	pressEnter('editTableName',editGenSql,param);
	pressEnter('showName',editGenSql,param);
	pressEnter('sqlStr',editGenSql,param);
	pressEnter('sort',editGenSql,param);
}

function editGenSql(param){
	param.tableName = $("#editTableName").val();
	param.showName = $("#showName").val();
	param.sort = $("#sort").val();
	param.sqlStr = $("#sqlStr").val();
	param.hasLog = $("#hasLog").val();
	$(".spanTip").html("");
	if(!param.tableName){
		$("#editTableNameTip").html("请填写表名！");
		return false;
	}else if(!param.showName){
		$("#showNameTip").html("请填写显示名！");
		return false;
	}else if(!param.sqlStr){
		$("#sqlStrTip").html("请填写sql语句!");
		return false;
	}else if(!param.sort){
		$("#sortTip").html("请填写排序!");
		return false;
	}else if(isNaN(param.sort)){
		$("#sortTip").html("排序必须是数字！");
		return false;
	}else if(!param.hasLog){
		$("#hasLogTip").html("请填写是否有日志!");
		return false;
	}else if(isNaN(param.hasLog)){
		$("#hasLogTip").html("是否有日志必须是数字！");
		return false;
	}
	$.post('config/genSql/updateGenSql.htm', param,function (data, textStatus) {
		getGenSql();
		closeMsg();
    },"json");
}

function getGenSql(){
	var tableName = $("#genSqlTableName").val().toLowerCase();;
	var url = 'config/genSql/getGenSql.htm';
	if(tableName){
		url = 'config/genSql/getGenSql.htm?tableName='+tableName;
	}
	$.ajax({
        type: "get",
        url: url,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
        	 var json = eval(data.data);
        	 var table = $('#genSqlTable').DataTable({
        		 "destroy"		: true,
        		 "searching"		: false,
        		 "processing"		: true,
        		 "stateSave"		: true,
    	         "data" 			: json,
        	     "oLanguage": commonData.oLanguage,
        	     "dom": 't<"col-sm-6"l><"col-sm-6"p>',
                 "tabIndex" 	: 1,
                 "columns": [
                             { "data": "id"},
                             { "data": "tableName"},
                             { "data": "showName"},
                             { "data": "sqlStr"},
                             { "data": "sort" },
                             { "data": "hasLog"},
                             { "data": "tm" },
                             { "data": "status"}
                  ],
                 "fnRowCallback":function(nRow,aData,iDataIndex){
                      	var aDataId = aData.id;
                      	var tableName = aData.tableName;
                      	var showName = aData.showName;
                      	var sort = aData.sort;
                      	var hasLog = aData.hasLog;
                      	var sqlStr = aData.sqlStr;
                  		$('td:eq(-1)',nRow).html(
          					"&nbsp;<button type='button' class='btn btn-primary btn-xs' onclick='forEditGenSql(" + aDataId + ",\"" + tableName + "\",\"" + showName + "\"" + ",\"" + sqlStr + "\"" + "," + sort + "," + hasLog + ")'>编辑</button>" +
          					"&nbsp;<button type='button' class='btn btn-primary btn-xs' onclick='forDeleteGenSql(" + aDataId + ",\"" + showName + "\"" + ")'>删除</button>"
                  		);
                  		var hasLogStr = "有";
                  		if(hasLog == "2"){
                  			hasLogStr = "无";
                  		}
                  		$('td:eq(-3)',nRow).html(hasLogStr);
                  }
            });
        },
        error: function (err) {
            showMsg("请求出错!");
            myLog(err);
        }
    });
}

function forDeleteGenSql(id,showName){
	showMsg("确定删除：" + showName +"吗？","删除",deleteGenSql,id);
}

function deleteGenSql(id){
	if(!id){
		showMsg("请选择要删除的id!");
	}else{
		$.post("config/genSql/deleteGenSql.htm",{'id':id},function(data){
			getGenSql();
			closeMsg();
		},"json").error(function(){
			showMsg(commonData.errorMsg);
		});
	}
}

function forCreateGenSql(id,tableName,showName,sqlStr,sort){
	var html = '<form class="form-horizontal">'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">表名:</label>'
					+	'<div class="col-xs-6">'
					+   	'<input type="text" id="createTableName" class="form-control"><span id="createTableNameTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">显示名:</label>'
					+	'<div class="col-xs-6">'
					+   	'<input type="text" id="showName" class="form-control"><span id="showNameTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">语句:</label>'
					+	'<div class="col-xs-6">'
					+   	'<textarea type="text" id="sqlStr" class="form-control"></textarea><span id="sqlStrTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">日志:</label>'
					+	'<div class="col-xs-6">' 
					+   	'<select id="hasLog" class="form-control">' 
					+			'<option value="1">有</option>' 
					+			'<option value="2">无</option>' 
					+ 		'</select><span id="hasLogTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
					+ '<div class="form-group">'
					+	'<label class="col-xs-3 control-label">排序:</label>'
					+	'<div class="col-xs-6">'
					+   	'<input type="text" id="sort" class="form-control" placeholder="升序排序" value="1"><span id="sortTip" class="spanTip"></span>'
					+	'</div>'
					+ '</div>'
				+ '</form>';
	showMsg(html,"增加生成语句",createGenSql);
	setTimeout(function(){$("#createTableName").focus();},500);
	pressEnter('createTableName',createGenSql);
	pressEnter('showName',createGenSql);
	pressEnter('sqlStr',createGenSql);
	pressEnter('sort',createGenSql);
}

function createGenSql(){
	var param = {};
	param.tableName = $("#createTableName").val();
	param.showName = $("#showName").val();
	param.sort = $("#sort").val();
	param.sqlStr = $("#sqlStr").val();
	param.hasLog = $("#hasLog").val();
	$(".spanTip").html("");
	if(!param.tableName){
		$("#createTableNameTip").html("请填写表名！");
		return false;
	}else if(!param.showName){
		$("#showNameTip").html("请填写显示名！");
		return false;
	}else if(!param.sqlStr){
		$("#sqlStrTip").html("请填写sql语句!");
		return false;
	}else if(!param.sort){
		$("#sortTip").html("请填写排序!");
		return false;
	}else if(isNaN(param.sort)){
		$("#sortTip").html("排序必须是数字！");
		return false;
	}else if(!param.hasLog){
		$("#hasLogTip").html("请填写是否有日志!");
		return false;
	}else if(isNaN(param.hasLog)){
		$("#hasLogTip").html("是否有日志必须是数字！");
		return false;
	}
	$.post('config/genSql/insertGenSql.htm', param,function (data, textStatus) {
		getGenSql();
		closeMsg();
    },"json").error(function(){
		showMsg(commonData.errorMsg);
	});
}