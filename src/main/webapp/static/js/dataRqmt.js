function listDataRqmt(action){
	var status = ["","下达","分配","交付","停止"];
	$("#example").DataTable({
	     "bFilter": true,
	     "bDestroy" : true,
	     "bStateSave" : true,
	     "bProcessing" : false,
	     "sAjaxSource" : "data/dataRqmt/listDataRqmts.htm?action=" + action,
	     "bServerSide" : true,
	     "ordering": false,
	     "columns" : [
				{ "data": "id"},
				{ "data": "title",
					"render":function(data, type, row){
						data = '<a href="data/dataRqmt/detail.htm?id='+row.id+'" target="_blank">'+ data+'</span>';
						return data;
					}
				},
				{ "data": "userName"},
				{ "data": "createTime"},
				{ "data": "dealer"},
				{
					"data": "status",
					"render":function(data, type, row){
						var pClass = "font-default";
						if(data == 2){
		        	 		pClass = "font-green";
		        	 	}else if(data == 3){
		        	 		pClass = "font-grey";
		        	 	}else if(data == 4){
		        	 		pClass="font-pink";
		        	 	}
		        	 	var str = "<span pClass='" + pClass + "'>" + status[data] + "</span>";
						return str;
					}
				},
				{ "data": "receiver"},
				{ "data": "finishTime"}
	         ],
	       "oLanguage": commonData.oLanguage,
	       "dom": 't<"col-sm-6"l><"col-sm-6"p>',
    	   "fnDrawCallback": function (oSettings) {
    		   setTdClass("#example");
    	    }
	});
}

function listDataRqmtOpt(referId){
	if(referId){
		var optTypeStr = ["创建","变更需求","交付","评论","停止","其他","分配"];
		$.ajax({
	        type: "get",
	        url: "data/dataRqmtOpt/listDataRqmtOpts.htm?referId=" + referId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function (data) {
	        	 var json = eval(data.obj);
	        	 var table = $('#remarkTable').DataTable({
	        		 destroy		: true,
	        		 paging			: false,
	        		 searching		: false,
	        		 processing		: true,
	    	         data 			: json,
	    	         "ordering" 	: false,
	    	         "tabIndex" 	: 1,
	    	         "columns": [
	    	                     { "data": "userName"},
	    	                     { "data": "content"},
	    	                     { "data": "id"}
	    	          ],
	    	    	 "oLanguage": commonData.oLanguage,
	    	    	 "dom": 't<"col-sm-6"l><"col-sm-6"p>',
			         "fnRowCallback":function(nRow,aData,iDataIndex){
			        	 	var content = eval('(' + aData.content + ')');
			        	 	var remark = content.optRemark;
			        	 	var type = content.operateType;
			        	 	var typeStr = "";
			        	 	var pClass = "background-default";
			        	 	if(type == 1){
			        	 		pClass = "background-red";
			        	 	}else if(type == 2){
			        	 		pClass="background-green";
			        	 	}
			        	 	typeStr = "<span pClass='" + pClass + "'>" + optTypeStr[type] + "@" + aData.createTime + "</span>";
			              	$('td:eq(-2)',nRow).html(typeStr);
			          		$('td:eq(-1)',nRow).html(remark);
			          }
	    	    });
	        	setTdClass("#remarkTable");
	        },
	        error: function (err) {
	            showMsg("请求出错!");
	            myLog(err);
	        }
	    });
	}
}

function setDefaultDate(){
	var date = new Date();
	date.addDays(10);
	$("#needTime").val(date.format('yyyy-MM-dd'));
}

$("#addDataRqmtOpt").click(function() {
	addDataRqmtOpt();
});

function addDataRqmtOpt(optType,remark,finishTime,loggers){
	var id = $("#dataRqmtId").val();
	myLog(optType);
	if(!optType){
		optType=$("input[name='operateType']:checked").val();
	}
	if(!remark){
		remark=$("#optRemark").val();
	}
	if(!optType){
		alert("请选择操作类型");
		return false;
	}
	if(!remark){
		alert("请填写备注!");
		return false;
	}
	var content = {};
	content["optRemark"] = remark;
	content["operateType"] = optType;
	$("#addDataRqmtOpt").button("loading");
	$.post("data/dataRqmtOpt/addOrUpdateOpt.htm", {
		content : JSON.stringify(content),
		referId : id,
		optType : optType,
		finishTime : finishTime,
		loggers : loggers
	}, function(data) {
		$("#addDataRqmtOpt").button("reset");
		if(data.status == 1){
			refresh();
		}else{
			alert(data.msg);
		}
	}, "json").error(function() {
		$("#addDataRqmtOpt").button("reset");
	});
}


function initEditor(basePath){
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			cssPath:  basePath + 'static/kindeditor-4.1.10/plugins/code/prettify.css', 
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			uploadJson : 'upload?action=uploadImages',
			allowImageRemote : true,
	      filterMode : false,
	      urlType:'domain',
			items : [
				'source','code','|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			afterCreate : function() {
                this.sync();
            },
            afterBlur : function() {
                this.sync();
            }
		});
		 editor.sync();
	});
}

$("#addDataRqmt").click(function() {
	if ($('#stime').val() == '' || $('#etime').val() == '') {
		alert("请选择开始与结束时间");
		return;
	}
	
	var title = $("#dataTtile").val();
	var plat = $("#plats").val();
	var dataItem = $("#dataItem").val();
	var resultUser = $("#resultUser").val();
	var dataPurpose = $("#dataPurpose").val();
	
	if(!plat){
		alert("请选择平台!");
		return false;
	}
	if(!title){
		alert("请填写需求标题!");
		return false;
	}
	if(!resultUser){
		alert("请填写目标用户!");
		return false;
	}
	if(!dataItem){
		alert("请填写你需要的数据项!");
		return false;
	}
	if(!dataPurpose){
		alert("请填写数据用途！");
		return false;
	}
	var content = {};
	
	$("#formDiv").find(".content").each(function(){
		var that = $(this);
		var id = that.attr("id");
		if(id){
			if(id != "sid"){
			content[id] = that.val();
			}else{
				content[id] = $("#sid").attr("selectSidValues");
			}
		}
	});
	$("#addDataRqmt").button("loading");
	$.post("data/dataRqmt/addOrUpdate.htm", {
		plat 	: plat,
		title 	: title,
		remark	: $("#remark").val(),
		content : JSON.stringify(content)
	}, function(data) {
		$("#addDataRqmt").button("reset");
		if(data.status == 1){
			window.location = "data/dataRqmt/detail.htm?id=" + data.obj;
		}else{
			alert(data.msg);
		}
	}, "json").error(function() {
		$("#addDataRqmt").button("reset");
		alert("请求出错!");
	});
});


function setDataRqmtContent(content,id,createTime,creater){
	for(var i in content){
	
	//console.info();
	//for(var i=0;i<content.length;i++){
		var element = $("#" +i);
		if(element.is("div")){
			element.html(content[i]);
		}else{
			element.val(content[i]);
		}
	}
	$("#dataRqmtId").val(id);
	$("#dataRqmtCreateTime").val(createTime);
	$("#dataRqmtCreater").val(creater);
}

function setTdClass(tableId){
	$(tableId + " td span").each(function(){
		var that = $(this);
		var clazz = that.attr("pClass");
		if(clazz){
			that.parent("td").parent("tr").addClass(clazz);
		}
	});
}
