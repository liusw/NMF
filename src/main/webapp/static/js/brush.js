var gFormDatas = "";

function initBrush(){
	getMultiPlat("#plats", "#sid",false,getBrushTemplate);
	$("#plats").change(function() {
		getMultiSid("#plats","#sid",true);
		getBrushTemplate($("#plats").val());
	});
	$("#templateId").change(function() {
		changeTemplate();
	});
	initDatetimepicker();
	radioClick();
	$("#ifAutorun").click(function(){
		autorunClick(this);
	});
	$("#saveTemplate").click(function(){
		saveTempate();
	});
	$("#addtask").click(function() {
		beforeAddTask();
	});
	setGFormData();
	window.onbeforeunload = CheckUnsave;
}

function CheckUnsave(){
	var formDatas = getGFormData();
	if(gFormDatas != formDatas){
//		if(confirm("模板数据有修改，确定要离开吗？")){
//
//		}else {
//			window.location.reload(); 
//			return false;
//		}
		return "模板数据有修改，确定要离开吗？";
	}
}

/**
 * 选择“每天自动执行”项后的处理函数
 * @param that : this对象
 */
function autorunClick(that){
	if(that.checked){
		$(that).val("true");
		$("#autorun").val("true");
		$("#rstime").attr("disabled",true);
		$("#retime").attr("disabled",true);
	}else{
		$(that).val("");
		$("#autorun").val("");
		$("#rstime").attr("disabled",false);
		$("#retime").attr("disabled",false);
	}
}

/**
 * 获取表单数据
 */
function getFormData(){
	var formDatas = "";
	try{
		formDatas = JSON.stringify($("#brushForm").serializeJson());
	}catch(e){
		myLog(e);
	}
	return formDatas;
}

function getGFormData(){
	try{
		var jsonData = $("#brushForm").serializeJson();
		jsonData['sid'] = "";
		return JSON.stringify(jsonData).replace(/,"sid":""/g,"");//不比较sid的值
	}catch(e){
		myLog(e);
		return "";
	}
}

function setGFormData(){
	gFormDatas = getGFormData();
}

function saveTempate(){
	var id = $("#templateId").val();
	if((!id || id=="0") && !$("#name").val()){
		$("#nameDiv").fadeIn(500);
		$("#name").focus();
		return false;
	}
	var formDatas = getFormData();					
	
	$('#saveTemplate').button("loading");
	$.post("brusheTemplateServlet?action=save",{formData : formDatas},
		function(data) {
		showMsg("保存模板成功!");
		var nid = data.loop;//插入的数据返回id
		if(!nid || nid==""){
			nid=id;
		}
		getBrushTemplate(null,nid);
		$('#saveTemplate').button("reset");
		$("#nameDiv").fadeOut(500);
		$("#name").val("");
		setGFormData();
	},"json"
	).error(function(){
		showMsg("保存模板失败!");
		$('#saveTemplate').button("reset");
	}); 
}

/**
 * 获取模板
 * @plat : 平台id
 * @showId : 获取模板之后显示哪个模板
 */
function getBrushTemplate(plat,showId){
	if(!plat){
		plat = $("#plats").val();
	}
	if(plat){
		var data = getJsonData("brusheTemplateServlet",{action:"query",plat:plat},true);
		var options = "<option value='0'>空模板</option>";
		if(data){
			$.each(data, function(index, value) {
				options += "<option value='" + value.id + "' content='" + value.content + "'>" + value.name + "</option>";
			});
		}
		$("#templateId").html(options);
		if(showId){
			$("#templateId option[value=" + showId +"]").attr("selected","selected");
		}
		$("#nameDiv").hide();
	}else{
		myLog("plat为空");
	}
}

//更换模板后处理函数
function changeTemplate(){
	//$("#brushForm")[0].reset();//清空表单
	clearForm();
	var data = eval('(' + $("#templateId").find("option:selected").attr("content") + ')');
	if(data){
		if(data.rstime){$("#rstime").val(data.rstime);}
		if(data.retime){$("#retime").val(data.retime);}
		if(data.smfcount){$("#smfcount").val(data.smfcount);}
		if(data.emfcount){$("#emfcount").val(data.emfcount);}
		if(data.smexplevel){$("#smexplevel").val(data.smexplevel);}
		if(data.emexplevel){$("#emexplevel").val(data.emexplevel);}
		if(data.loginip){$("#loginip").val(data.loginip);}
		if(data.smentercount){$("#smentercount").val(data.smentercount);}
		if(data.ementercount){$("#ementercount").val(data.ementercount);}
		if(data.smmoney){$("#smmoney").val(data.smmoney);}
		if(data.emmoney){$("#emmoney").val(data.emmoney);}
		if(data.svmoney){$("#svmoney").val(data.svmoney);}
		if(data.evmoney){$("#evmoney").val(data.evmoney);}
		if(data.swinCount){$("#swinCount").val(data.swinCount);}
		if(data.eloseCount){$("#eloseCount").val(data.eloseCount);}
		if(data.smpcount){$("#smpcount").val(data.smpcount);}
		if(data.empcount){$("#empcount").val(data.empcount);}
		if(data.s2gRate){$("#s2gRate").val(data.s2gRate);}
		if(data.e2gRate){$("#e2gRate").val(data.e2gRate);}
		if(data.splayRate){$("#splayRate").val(data.splayRate);}
		if(data.eplayRate){$("#eplayRate").val(data.eplayRate);}
		if(data.smfree){$("#smfree").val(data.smfree);}
		if(data.emfree){$("#emfree").val(data.emfree);}
		if(data.mhasicon){
			$("#mhasicon").val(data.mhasicon);
			$("input[name='hasicon'][value='" + data.mhasicon + "']")[0].checked = true;
		}
		if(data.stime){
			$("#stime").val(data.stime);
			$("input[name='time'][value='" + data.stime + "']")[0].checked = true;
		}
		if(data.mpay){
			$("#mpay").val(data.mpay);
			$("input[name='pay'][value='" + data.mpay + "']")[0].checked = true;
		}
		if(data.emails){
			$("#emails").val(data.emails);
			var vals = data.emails.split(",");
			for(var i=0;i<vals.length;i++){
				$("input[name='email'][value='" + vals[i] + "']")[0].checked = true;
			}
		}
		if(data.languages){
			$("#languages").val(data.languages);
			var vals = data.languages.split(",");
			for(var i=0;i<vals.length;i++){
				$("input[name='language'][value='" + vals[i] + "']")[0].checked = true;
			}
		}
		if(data.loginipRegions){
			$("#loginipRegions").val(data.loginipRegions);
			var vals = data.loginipRegions.split(",");
			for(var i=0;i<vals.length;i++){
				$("input[name='loginipRegion'][value='" + vals[i] + "']")[0].checked = true;
			}
		}
		if(data.autorun){
			$("#autorun").val(data.autorun);
			if("true" == data.autorun){
				$("#ifAutorun")[0].checked = true;
			}
			autorunClick($("#ifAutorun")[0]);
		}
	}
	$("#nameDiv").hide();
	setGFormData();
}

//清除表单,使用reset()方法会有不能重新设置到情况
function clearForm(){
	$("#brushForm input[type=text]").val("");
	$("#brushForm input[type=hidden]").val("");
	$("#brushForm input[type=radio]:checked").each(function(){
		this.checked = false;
	});
	$("#brushForm input[type=checkbox]:checked").each(function(){
		this.checked = false;
	});
	$("#registerTime")[0].checked = true;//默认注册时间
	$("#rstime").attr("disabled",false);
	$("#retime").attr("disabled",false);
}

function radioClick(){
	$('input[type="radio"]').click(function(){
		if(this.checked){
			var id = $(this).attr("referId");
			$("#" + id).val($(this).val());
		}
	});
	
	$('input[type="checkbox"]').click(function(){
		var id = $(this).attr("referId");
		var name = $(this).attr("name");
		var val = $('input:checkbox[name=' + name + ']:checked').map(function(){
			return this.value;
		}).get().join();
		$("#" + id).val(val);
	});
}

/**
 * 检查任务中是否有相同任务数
 */
function beforeAddTask(){
	var autorun = $('#ifAutorun').val();
	if("true" != autorun){
		var rstime = $('#rstime').val();
		var retime = $('#retime').val();
		if(!rstime || !retime){
			showMsg("请填写注册时间范围!");
			return false;
		}
	}
	
	var templateId = $("#templateId").val();
	$('#addtask').button("loading");
	$.post("task/auto/getSameTmpIdCount.htm",{templateId:templateId},function(data) {
			$('#addtask').button("reset");
			var num = 0;
			if (data.status == 1){
				num = data.obj;
			} 
			if(num && num>0){
				$("#brushMsgContent").html("已经有" + num + "个使用相同模板的任务：替换 或 新增？");
				$('#confirmBrushTask').modal('show');
			}else{
				addTask();
			}
			
		},"json").error(function(){
		showMsg(commonMsg.postError);
		$('#addtask').button("reset");
	}); 
}

function addTask(addType){
	if(!addType){
		addType = 'add';//默认为新增
	}
	$('#confirmBrushTask').modal('hide');
	var emails = $('input:checkbox[name=email]:checked').map(function(){
		return this.value;
	}).get().join();
	
	var allEmails = $('input:checkbox[name=email]').map(function(){
		return this.value;
	}).get().join();
	
	var languages = $('input:checkbox[name=language]:checked').map(function(){
		return this.value;
	}).get().join();
	
	var allLangs = $('input:checkbox[name=language]').map(function(){
		return this.value;
	}).get().join();
	
	var loginipRegions = $('input:checkbox[name=loginipRegion]:checked').map(function(){
		return this.value;
	}).get().join();
	
	var allRegions = $('input:checkbox[name=loginipRegion]').map(function(){
		return this.value;
	}).get().join();
	
	var mpay =  $('input[name="pay"]:checked').val();
	
	var mhasicon =  $('input[name="hasicon"]:checked').val();
	
	var stime = $('input[name="time"]:checked').val();//选择到时间类型
	
	var autorun = $('#ifAutorun').val();
	
	var json = {
			plat : $("#plats").val(),
			sid : $("#sid").attr("selectSidValues"),
			bpid : $("#sid").attr("selectBpidValues"),
			rstime : $('#rstime').val(),
			retime : $('#retime').val(),
			smfcount : $('#smfcount').val(),
			emfcount : $('#emfcount').val(),
			smexplevel : $('#smexplevel').val(),
			emexplevel :$('#emexplevel').val(),
			emails : emails,
			loginip :$('#loginip').val(),
			smentercount :$('#smentercount').val(),
			ementercount :$('#ementercount').val(),
			lang : languages,
			smmoney :$('#smmoney').val(),
			emmoney :$('#emmoney').val(),
			svmoney :$('#svmoney').val(),
			evmoney :$('#evmoney').val(),
			swinCount :$('#swinCount').val(),
			ewinCount :$('#ewinCount').val(),
			smpcount :$('#smpcount').val(),
			empcount :$('#empcount').val(),
			s2gRate :$('#s2gRate').val(),
			e2gRate :$('#e2gRate').val(),
			splayRate :$('#splayRate').val(),
			eplayRate :$('#eplayRate').val(),
			smfree :$('#smfree').val(),
			emfree :$('#emfree').val(),
			mpay : mpay,
			mhasicon : mhasicon,
			loginipRegions : loginipRegions,
			allRegions : allRegions,
			templateId : $("#templateId").val(),
			templateName : $("#templateId option:selected").text(),
			stime : stime,
			autorun : autorun,
			allEmails : allEmails,
			allLangs : allLangs,
			addType	 : addType
	};
	
	$('#addtask').button("loading");
	$.post("log/brushServlet?action=importBrushUser",json ,
		function(data) {
			commonDo.addedTaskSuccess(data);
		},
	"json").error(function(){
		commonDo.addedTaskError();
	}); 
}