$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(xhr, textStatus) {
		// session timeout
		var sessionstatus = xhr.getResponseHeader("sessionstatus");
		if (sessionstatus == "timeout") {
			showMsg('登录已超时,请刷新页面', '登录超时');
			// window.location = "/log/task?action=list";
		}
	}
});

function updateNavMultiSid(){
	getMultiSid("#navPlat", "#navMultiSid", true);
}

var navForm = (function() {
	function showPlat(options){
		var platLength = $("#navPlat").find("option");
		if (!platLength || platLength.length <= 0) {
			var data = getJsonData("config/getPlat.htm");
			if (data != null) {
				//判断是否存在cookie
				var cookiePlatValue = getCookie("navPlat");
				$.each(data, function(index, value) {
					var select = '';
					if(!isEmpty(cookiePlatValue)){
						if(value.plat==cookiePlatValue){
							select= 'selected="selected"';
						}
					}else{
						if(index==0){
							setCookie("navPlat",value.plat);
						}
					}
					$("#navPlat").append("<option value='"+value.plat+"' svid='"+value.svid+"' "+ select+">" + value.platName + "(" + value.plat + ")" + "</option>");
				});
				
				var selecObj = document.getElementById('navPlat');
				addEvent(selecObj, "change", function() {
					
					var index = selecObj.selectedIndex; // 选中索引
					var value = selecObj.options[index].value; // 选中值
					setCookie("navPlat",value);
					
					getSid("#navPlat", "#navSid", true);

					if (options.platChangeCallback) {
						options.platChangeCallback();
					}
				});
			}
		}
	}
	
	function showSid(options){
		getSid("#navPlat", "#navSid", true);
		if(options.showSid == true && options.sidType != 'Select'){
			getMultiSid("#navPlat", "#navMultiSid");
		}
	}

	return {
		showDate:function(options){
			this.daterange = createDateRgDom({
				isShow:options.showDate.isShow,singleDatePicker:false,startDate:options.showDate.startDate,endDate:options.showDate.endDate,
				applyClickCallBack:options.showDate.applyClickCallBack,timePicker:options.timePicker,format:options.format,opens:options.opens,
				minDate:options.minDate,maxDate:options.maxDate
			});

			this.date = createDateRgDom({
				isShow:options.showDate.isShow,singleDatePicker:true,startDate:options.showDate.startDate,
				applyClickCallBack:options.showDate.applyClickCallBack,timePicker:options.timePicker,format:options.format,opens:options.opens,
				minDate:options.minDate,maxDate:options.maxDate
			});
		},
		
		init : function(options) {
			/*为了适用多TAB页面不同导航配置的需求，所有组件都进行相应的初始化，只控制可见性*/
			showPlat(options);

			showSid(options);

			options.showDate = options.showDate || {isShow:false,singleDatePicker:false};
			
			if(options.showDate.isShow){
				this.showDate(options);
			}
						
			$("#navPlat").css("display",options.showPlat == true?"inline-block":"none");
			
			$(".ui-multiselect").css("display",(options.showSid == true && options.sidType != 'Select')?"inline-block":"none");

			$("#navSid").css("display",(options.showSid == true && options.sidType == 'Select')?"inline-block":"none");

			$("#navdaterange").css("display",(options.showDate.isShow == true && options.showDate.singleDatePicker != true)?"inline-block":"none");
			$("#navdate").css("display",(options.showDate.isShow == true && options.showDate.singleDatePicker == true)?"inline-block":"none");
			
			$("#navSubmit").css("display",options.showBtn == true?"inline-block":"none");

			$("#navExport").css("display",options.showExportBtn == true?"inline-block":"none");

			$("#table-btn").css("display",options.showChart == true?"inline-block":"none");

			$("#chart-btn").css("display",options.showChart == true?"inline-block":"none");

			return this;
		},

		hideAll: function(){
			$("#navPlat").hide();
			$(".ui-multiselect").hide();
			$("#navSid").hide();
			$("#navdaterange").hide();
			$("#navdate").hide();
			$("#navSubmit").hide();
			$("#navExport").hide();
			$("#table-btn").hide();
			$("#chart-btn").hide();
		}
	};
})();

/**
 * 创建时间段组件
 */
function createDateRgDom(showDate){
	var defaultId = showDate.singleDatePicker == true?"#navdate":"#navdaterange";
	var dateOption = {'dom':defaultId,'singleDatePicker':false};
	
	if(showDate){
		if(showDate.dom && showDate.dom != ""){
			dateOption.dom = showDate.dom;
		}
		
		if(showDate.isShow == false){
			$(dateOption.dom).hide();
		}else{
			$(dateOption.dom).show();
		}
		
		if(showDate.singleDatePicker && showDate.singleDatePicker == true){
			dateOption.singleDatePicker = showDate.singleDatePicker;
		}
		
		if(showDate.startDate && showDate.startDate != ""){
			dateOption.startDate = showDate.startDate;
		}
		
		if(showDate.endDate && showDate.endDate != ""){
			dateOption.endDate = showDate.endDate;
		}
		
		if(showDate.applyClickCallBack){
			dateOption.applyClickCallBack = showDate.applyClickCallBack;
		}
		if (typeof showDate.timePicker === 'boolean') {
			dateOption.timePicker = showDate.timePicker;
		}
		if (typeof showDate.format === 'string') {
			dateOption.format = showDate.format;
		}
		if (typeof showDate.opens === 'string') {
			dateOption.opens = showDate.opens;
		}
		if(showDate.minDate && showDate.minDate != ""){
			dateOption.minDate = showDate.minDate;
		}
		if(showDate.maxDate && showDate.maxDate != ""){
			dateOption.maxDate = showDate.maxDate;
		}
		
	}else{
		$(dateOption.dom).show();
	}
	
	var daterg = {};
	
	try {
		daterg = DaterangeUtil.init(dateOption);
	} catch (e) {
	}
	
	return daterg;
}

/**
 * 初始化顶部的平台下拉框
 * @param callBack 下拉框改变后的回调方法
 */
function initTopPlats(changeCallBack) {
	var platLength = $("#topPlats").find("option");
	if (!platLength || platLength.length <= 0) {
		var data = getJsonData("config/getPlat.htm");
		if (data != null) {
			//判断是否存在cookie
			var cookiePlatValue = getCookie("topPlat");
			$.each(data, function(index, value) {
				var select = '';
				if(!isEmpty(cookiePlatValue)){
					if(value.plat==cookiePlatValue){
						select= 'selected="selected"';
					}
				}else{
					if(index==0){
						setCookie("topPlat",value.plat);
					}
				}
				$("#topPlats").append("<option value='"+value.plat+"' svid='"+value.svid+"' "+ select+">" + value.platName + "(" + value.plat + ")" + "</option>");
			});
			if (changeCallBack) {
				changeCallBack();
			}
		}
	}
	if (changeCallBack) {
		var selecObj = document.getElementById('topPlats');
		addEvent(selecObj, "change", function() {
			var index = selecObj.selectedIndex; // 选中索引
			var value = selecObj.options[index].value; // 选中值
			setCookie("topPlat",value); 
			changeCallBack();
		});
	}
}


var addEvent = function(el, e, callback) {
	if(el){
		if (el.attachEvent) {// for IE
			el.attachEvent("on" + e, callback);
		} else {
			el.addEventListener(e, callback, false);
		}
	}
};

// 获取平台select列表,两个参数分别为plat,sid在页面中的Id,如果sid为null,即不加载sid
function getPlat(plat,sid,unAll){
	// 获取平台select列表，该动作是异步操作，设置默认值时应该在生成option之后
	var data = getJsonData("config/getPlat.htm");
	if(data!=null){
		$.each(data, function(index, value) {
			$(plat).append("<option value=" + value.plat + " svid="+value.svid+">" + value.platName + "(" + value.plat + ")" + "</option>");
		});
		if(sid!=null){
			getSid($(plat).val(),sid,unAll);
		}
	}
}

//获取平台信息，可多选
function getMultiPlat(plat,sid,isRebuild,successFun){
	// 获取平台select列表，该动作是异步操作，设置默认值时应该在生成option之后
	var data = getJsonData("config/getPlat.htm");
	if(data!=null){
		$.each(data, function(index, value) {
			$(plat).append("<option value=" + value.plat + " svid="+value.svid+">" + value.platName + "(" + value.plat + ")" + "</option>");
		});
		if(sid!=null){
			getMultiSid(plat,sid,isRebuild,true);
		}
		if(successFun){
			successFun();
		}
	}
}
// 获取平台sid列表,plat为所在平台的值,sid为所在页面中的节点ID,sidAll:true,false,默认是true
function getSid(plat,sid,unAll){
	$(sid).html("");
	if(!unAll){
		$(sid).append("<option value=''>全部站点</option>");
	}
	
	var data = getJsonData2("config/getSites.htm",{plat:$(plat).val() || plat},true);
	$.each(data, function(index, value) {
		$(sid).append("<option value=" + value.sid+"_"+value.bpid + ">" + value.sname + "(" + value.sid + ")" + "</option>");
	});
}

function getSids(plat){
	return getJsonData2("config/getSites.htm",{plat:plat},true);
}

//这个是新的,上面那个很快就会被废弃掉
function getTables(tableType,addDefault) {
	return getJsonData2("config/getTables.htm",{tableType:tableType,addDefault:addDefault},true);
}

function getConfigColumns(tableName,tableType,addDefault) {
	if(tableType == "hive"){
		var columns = getJsonData2("hiveMeta/getColumnInfo.htm",{tableName:tableName},true);
		return columns;
	}else{
		return getJsonData("config/getHbaseColumns.htm",{tableType:tableType,tableName:tableName,addDefault:addDefault},true);
	}
}

function getOptionHtml(tableName,tableType,addDefault){
	var options ="";
	if(tableType=='hbase' && tableName=='user_ucuser'){//如果是取用户信息，直接从JS中取
		if(ucuser){
			$(ucuser).each(function(){
				options += "<option value='" + this.value + "' title='"+this.valueName+"("+ this.value +")'>" + this.valueName+'('+ this.value+ ")</option>";
			});
		};
	}else{
		var columns = getConfigColumns(tableName,tableType,addDefault);
		$.each(columns, function(index, value) {
			options += "<option value='" + value.column_name + "' title='"+this.comment+"("+ this.column_name+ ")'>" + value.comment+'('+ value.column_name+ ")</option>";
		});
	}
	return options;
}

function getMultiSid(plat,sid,isRefresh,notSetDefault){
	$(sid).html("");
	var data = getJsonData2("config/getSites.htm",{plat:$(plat).val()},true);
	$.each(data, function(index, value) {
		if(0 == index){
			var selected='';
			if(!notSetDefault){
				selected = "selected='selected'";
				$(sid).attr({'selectValues':value.sid+"_"+value.bpid,'selectSidValues':value.sid,'selectBpidValues': '\"' + value.bpid + '\"'});
			}else{
				$(sid).attr({'selectValues':'','selectSidValues':'','selectBpidValues': ''});
			}
			
			$(sid).append("<option value='" + value.sid+"_"+value.bpid +"' "+ selected +" ismobile="+value.ismobile + ">" +(value.ismobile==1?'Mobile-':'PC-')+ value.sname + "(" + value.sid + ")" + "</option>");
//			$(sid).attr({'selectValues':value.sid+"_"+value.bpid,'selectSidValues':value.sid,'selectBpidValues': '\"' + value.bpid + '\"'});
//			$(sid).attr({'selectValues':value.sid+"_"+value.bpid,'selectSidValues':value.sid,'selectBpidValues': value.bpid});
		}else{
			$(sid).append("<option value='" + value.sid+"_"+value.bpid + "' ismobile="+value.ismobile+">" +(value.ismobile==1?'Mobile-':'PC-')+ value.sname + "(" + value.sid + ")" + "</option>");
		}
	});
	
	if(isRefresh){
		refreshMultiSelect(sid);
	}
	try {
		if(!isRefresh){
			$(sid).mymultiselect({
				'multiValues' : '',
				'multiTitles' : '',
				//'height' : 'auto',
				'width' : $(plat).outerWidth(),
				selectClose : function(val) {
					$(sid).attr({
						'selectValues' : val.getValues(),
						'selectSidValues' : val.getSidValues(),
						'selectBpidValues' : val.getBpidValues(),
						'selectTitles' : val.getTitles()
					});
				},
			/*create : function(){
			  	if(plat){
			  		$(".ui-multiselect").width($(plat).width());
			  	}
			  }*/
			}).multiselectfilter();
		}
	} catch (e) {
		console.log("Not import multiselect js.");
	}
}

function refreshMultiSelect(jdom){
	$(jdom).mymultiselect("refresh");
	$(jdom).multiselectfilter('updateCache');
}

function getMultiColumns(t,domColumns,tableType,isRebuild){
	$(domColumns).html("");
	var columns = getConfigColumns(t,tableType,true);
	if(columns){
		$.each(columns, function(index, value) {
			$(domColumns).append('<option title="'+value.comment+'" value="' + value.column_name + '">' + value.comment+'('+ value.column_name+ ')</option>');
		});
	}
	if(isRebuild){
		 //$(domColumns).mymultiselect("destroy");
		$(domColumns).mymultiselect("refresh");;
	}
	$(domColumns).mymultiselect({
		'multiValues':'',
		'multiTitles':'',
		'maxWidth':200,
		selectClose:function (val) {
			$(domColumns).attr({'selectValues':val.getValues(),'selectTitles':val.getTitles()});
        }
	}).multiselectfilter();
}

function getJsonData(url,param,onlyJson){
	var loop=null;
	$.ajax({
        type: "get",
        async: false,
        url: url,
        data:param,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false,
        success: function (data) {
        		if(onlyJson){
        			if(data.ok==1) {
            		loop = data.loop;
    				}
        		}else{
        			loop = data;
        		}
        		
        },error: function (err) {
            alert("请求出错!");
            if(console){
            	console.log(err);
            }
        }
    });
	return loop;
}
function getJsonData2(url,param,onlyJson){
	var loop=null;
	$.ajax({
		type: "get",
		async: false,
		url: url,
		data:param,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		cache: false,
		success: function (data) {
			if(onlyJson){
				if(data.status==1) {
					loop = data.obj;
					//loop= $.parseJSON(loop);
				}
			}else{
				loop = data;
			}
		},error: function (err) {
			alert("请求出错!");
			if(console){
				console.log(err);
			}
		}
	});
	return loop;
}

function getPostJsonData2(url,param,onlyJson){
	var loop=null;
	$.ajax({
		type: 'POST',
        async: false,
        url: url,
        data:param,
       // contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false,
        success: function (data) {
        		if(onlyJson){
        			if(data.status==1) {
            		loop = data.obj;
    				}
        		}else{
        			loop = data;
        		}
        },error: function (err) {
            alert("请求出错!");
            if(console){
            	console.log(err);
            }
        }
    });
	return loop;
}

function getPostJsonData(url,param,onlyJson){
	var loop=null;
	$.ajax({
		type: 'POST',
        async: false,
        url: url,
        data:param,
       // contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false,
        success: function (data) {
        		if(onlyJson){
        			if(data.ok==1) {
            		loop = data.loop;
    				}
        		}else{
        			loop = data;
        		}
        },error: function (err) {
            alert("请求出错!");
            if(console){
            	console.log(err);
            }
        }
    });
	return loop;
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
};

function getHqlSelectColumn(hql){
	if(isEmpty(hql)){
		return "";
	}
	hql = hql.toLocaleLowerCase().replaceAll('`','',true);
	var selectIndex = hql.indexOf('select '),fromIndex = hql.indexOf(' from ');
	if(selectIndex==-1 || fromIndex ==-1 || selectIndex>fromIndex){
		return "";
	}
	hql = hql.substr(selectIndex+7,fromIndex-7);
	
	//alert(s.replace(/((?!\/?p\b)[^>]+>/ig,""));
	//alert(hql.replace(/\([^\)]*\)/g,""));
	
	var columns = hql.split(",");
	var newColumns = "";
	for(var i=0;i<columns.length;i++){
		var column = $.trim(columns[i]);
		if(column.lastIndexOf(' ')){
			column = column.substr(column.lastIndexOf(' ')); 
		}
		newColumns+=($.trim(column)+(i==columns.length-1?"":","));
	}
	return newColumns;
}

//支付类型
var payTypes = "[{ id:0,name:'快钱'},{ id:1,name:'支付宝'},{ id:2,name:'易宝银行卡'},{ id:3,name:'未知'},{ id:4,name:'漫游支付'},{ id:5,name:'Offerpay'},{ id:6,name:'Social gold'}" +
		",{ id:7,name:'webatm_NTD'},{ id:8,name:'Paypal'},{ id:9,name:'webatm_MYR'},{ id:10,name:'Babi卡'},{ id:11,name:'MyCard实体卡'},{ id:12,name:'Checkout'},{ id:13,name:'Gash会员'}" +
		",{ id:14,name:'MyCard银行转账'},{ id:15,name:'MyCard会员'},{ id:16,name:'Gash实体卡'},{ id:17,name:'Paysbuy Counter Service'},{ id:18,name:'null'},{ id:19,name:'null'}" +
		",{ id:20,name:'null'},{ id:21,name:'MOL-MYR'},{ id:22,name:'MOPAY手机'},{ id:23,name:'MOL-SGD'},{ id:24,name:'MOL-INR'},{ id:25,name:'MOL-PHP'},{ id:26,name:'Paypal dg'}" +
		",{ id:27,name:'Facebook Credits'},{ id:28,name:'Paypal europe'},{ id:29,name:'payruns'},{ id:30,name:'MOL-THB'},{ id:31,name:'MobileFirst-THB'},{ id:32,name:'MOL-THB-CALL'}" +
		",{ id:33,name:'MOL-THB-PIN'},{ id:34,name:'MOL-IDR'},{ id:35,name:'Paysbuy Online Banking'},{ id:36,name:'MobileFirst-IDR'},{ id:37,name:'MobileFirst-MYR'}" +
		",{ id:38,name:'MobileFirst-SGD'},{ id:39,name:'bycard'},{ id:40,name:'zong-IDR'},{ id:41,name:'gudangvoucher'},{ id:42,name:'heisha'},{ id:43,name:'黑鲨-会员'}" +
		",{ id:44,name:'babi(7-11)'},{ id:45,name:'Indomog'},{ id:46,name:'Indomog实体卡'},{ id:47,name:'新GASH实体卡'},{ id:48,name:'新GASH会员'},{ id:49,name:'Mycard手机市话网路'}" +
		",{ id:51,name:'OneCard支付'},{ id:56,name:'unipay'},{ id:59,name:'nl支付宝'},{ id:61,name:'eicard'},{ id:62,name:'nl电话卡支付'},{ id:63,name:'cashU'},{ id:64,name:'mol-thb-zest'}" +
		",{ id:98,name:'特殊活动(ATM、瑞穗牛奶、EPLAY)'},{ id:99,name:'iphone平台支付'},{ id:100,name:'所有的'},{ id:101,name:'plinga'},{ id:102,name:'蜗牛币'},{ id:103,name:'4399游币'}" +
		",{ id:104,name:'coda'},{ id:105,name:'iPhone支付'},{ id:106,name:'friendster coins'},{ id:107,name:'hi5 coins'},{ id:108,name:'盛大点'},{ id:109,name:'白金币'}" +
		",{ id:110,name:'6币'},{ id:111,name:'淘点'},{ id:112,name:'woyo币'},{ id:113,name:'android-机锋'},{ id:114,name:'android-91'},{ id:115,name:'遨游币'},{ id:116,name:'麻球 coins'}" +
		",{ id:117,name:'android-MARKETS'},{ id:118,name:'51币'},{ id:119,name:'支付宝1'},{ id:120,name:'微币'},{ id:121,name:'淘金币'},{ id:122,name:'Q点'},{ id:123,name:'337支付'}" +
		",{ id:124,name:'爆米花'},{ id:125,name:'酷币'},{ id:126,name:'金币'},{ id:127,name:'元'},{ id:128,name:'元'},{ id:129,name:'天涯贝'},{ id:130,name:'快币'},{ id:131,name:'元'}" +
		",{ id:132,name:'牛仔金币'},{ id:133,name:'彩贝'},{ id:134,name:'mogapay'},{ id:135,name:'人人豆'},{ id:136,name:'元'},{ id:137,name:'元'},{ id:138,name:'360币'}" +
		",{ id:139,name:'游币'},{ id:140,name:'元'},{ id:141,name:'元'},{ id:142,name:'元'},{ id:143,name:'元'},{ id:144,name:'金豆'},{ id:145,name:'元'},{ id:146,name:'元'}" +
		",{ id:147,name:'元'},{ id:148,name:'元'},{ id:149,name:'元'},{ id:150,name:'元'},{ id:151,name:'元'},{ id:152,name:'元'},{ id:153,name:'元'},{ id:154,name:'元'}" +
		",{ id:155,name:'纵横币'},{ id:164,name:'fortumo'},{ id:999,name:'支付渠道测试'}]";

function initDatetimepicker(tFormat,sView){
	var startView = 2;//默认显示天
	var timeFormat = 'yyyy-mm-dd';//默认时间格式
	if(tFormat){
		timeFormat = tFormat;
	}
	if(sView){
		startView = sView;
	}
	// 日期选择器
	$('.time').datetimepicker({
		language : 'zh-CN',
		todayHighlight : 1,
		autoclose : 1,
		format : timeFormat,
		weekStart : 1,
		todayBtn : 1,
		startView : startView,
		forceParse : 0,
		minView : 2,
		pickSeconds: true
	});
}

function setValue(v) {
	if (v === undefined || isNaN(v)) {
		return 0;
	} else {
		return v;
	}
}

function isEmpty(v) {
	if (v == null || $.trim(v) == '') {
		return true;
	}
	return false;
}

function op(v){
	if(v==0){
		return "等于";
	}else if(v==1){
		return "小于";
	}else if(v==2){
		return "小于或等于";
	}else if(v==3){
		return "不等于";
	}else if(v==4){
		return "大于";
	}else if(v==5){
		return "大于或等于";
	}else if(v==6){
		return "[in]";
	}else if(v==7){
		return "[like]";
	}else if(v==8){
		return "[between]";
	}else if(v==9){
		return "[not in]";
	}
}

// js map 工具类
function HashMap() {
	/** Map 大小 * */
	var size = 0;
	/** 对象 * */
	var entry = new Object();

	/** 存 * */
	this.put = function(key, value) {
		if (!this.containsKey(key)) {
			size++;
		}
		entry[key] = value;
	};

	/** 取 * */
	this.get = function(key) {
		if (this.containsKey(key)) {
			return entry[key];
		} else {
			return null;
		}
	};

	/** 删除 * */
	this.remove = function(key) {
		if (delete entry[key]) {
			size--;
		}
	};

	/** 是否包含 Key * */
	this.containsKey = function(key) {
		return (key in entry);
	};

	/** 是否包含 Value * */
	this.containsValue = function(value) {
		for ( var prop in entry) {
			if (entry[prop] == value) {
				return true;
			}
		}
		return false;
	};

	/** 所有 Value * */
	this.values = function() {
		var values = new Array();
		for ( var prop in entry) {
			values.push(entry[prop]);
		}
		return values;
	};

	/** 所有 Key * */
	this.keys = function() {
		var keys = new Array();
		for ( var prop in entry) {
			keys.push(prop);
		}
		return keys;
	};

	/** Map Size * */
	this.size = function() {
		return size;
	};
}

/**
 * 消息弹出框
 * @param content：显示内容
 * @param title : 显示标题
 * @param confirmAction : 确认操作执行的函数(没有设置，确定按钮不显示)
 * @param confirmParam : 确认操作执行函数的参数
 * @param closeAction : 关闭操作执行的函数(默认关闭弹出框)
 * @param closeParam : 关闭操作执行函数的参数
 */
function showMsg(content,title,confirmAction,confirmParam,closeAction,closeParam){
	if(!content){
		return false;
	}
	if(!title){
		title = "提示";
	}
	//解除之前到绑定
	$("#confirmBtn").unbind("click");
	$("#closeBtn").unbind("click");
	
	if(confirmAction){
		$("#confirmBtn").bind('click',function(){
			confirmAction(confirmParam);
		});
		$("#confirmBtn").show();
	}else{
		$("#confirmBtn").hide();
	}
	if(closeAction){
		$("#closeBtn").bind('click',function(){
			closeAction(closeParam);
		});
	}else{
		$("#closeBtn").bind('click',function(){
			$('#showMsg').modal('hide');
		});
	}
	$('#msgContent').html(content);
	$('#msgTitle').html(title);
	$('#showMsg').modal('show');
}

function closeMsg(){
	$('#showMsg').modal('hide');
}

//通用消息
var commonMsg = {
	addedTask : "<p>添加任务完成!</p>" +
	'<div class="alert alert-warning">' +
		'<div>' +
			'<p>提示:</p>' +
			'<p>1.提交任务后,在<a href="task/list/myTask.htm" target="_blank">我的任务</a>可查看导数据情况;</p>' +
			'<p>2.在我的任务中如果看到"结束执行时间",说明已执行完成,点击下载地址下载导好的数据;</p>' +
			'<p>3.因为执行要花点时间,建议添加后10分钟查看导出结果.</p>' +
		'</div>' +
	'</div>',
	postError : "请求出错!",
	loading   : "<div style='text-align:center;'>正在加载数据....</div>"
};

//通用动作
var commonDo = {
	addedTaskSuccess : function(data){
		var msg = commonMsg.addedTask;
		if (data.ok == 0) {
			msg = data.msg;
		}
		showMsg(msg);
		$('#addtask').button("reset");
	},
	addedTaskError : function(data){
		showMsg(commonMsg.postError);
		$('#addtask').button("reset");
	}
};

//共用数据
var commonData = {};

commonData.oLanguage = {//汉化
        "sLengthMenu":"显示 _MENU_ 条记录",
        "sZeroRecords":"没有检索到数据",
        "sInfo":"显示 _START_ to _END_ 条记录；共有 _TOTAL_ 条记录",
        "sInfoEmpty":"没有数据",
        "sProcessing":"正在加载数据......",
        "sSearch":"查询",
        "sInfoFiltered": "(过滤自 _MAX_ 条记录)",
        "oPaginate":{
            "sFirst":"首页",
            "sPrevious":"前页",
            "sNext":"后页",
            "sLast":"尾页"
       	}
};
commonData.errorMsg = "请求出错!";

/**
 * 将表单数据转换成json数据
 */
(function($){  
    $.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
      //  var str=this.serialize();  
        $(array).each(function(){  
            if(serializeObj[this.name]){  
            	serializeObj[this.name] += "," + this.value;
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    };  
})(jQuery);

/**
 * 输出日志
 * @param message
 */
function myLog(message){
	if(console){
		console.log(message);
	}
}

/**
 * 刷新窗口
 */
function refresh(){
	window.location.href=window.location.href;
}

/**
 * 监听并执行enter事件,参数有标记的id和需要执行的函数
 * @author darcy
 * @param id 	: 标签id
 * @param fun 	: 执行函数 
 * @return
 */
function pressEnter(id,fun,param){
	$('#'+id).keydown(function(event){
		if(event.keyCode==13){
			 fun(param);
			 return false;
		}
	});
}

//js操作cookie
function setCookie(name,value){ 
    var Days = 30;
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/"; 
} 

//读取cookies 
function getCookie(name){ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg)){
        return unescape(arr[2]); 
    }else{
        return null;
    }
} 

//删除cookies 
function delCookie(name){ 
    var exp = new Date(); 
    exp.setTime(exp.getTime()-1); 
    var cval=getCookie(name); 
    if(cval!=null){
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
     }
}

var firstPopoverClick=true;
function showHelp(o,content){
	$(o).popover("destroy").popover({
		content:content,
		html:true,
		placement:'bottom'
	});
	if(firstPopoverClick){
		$(o).popover('show');
		firstPopoverClick=false;
	}
}

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

function checkAll(id,inputName){
	id ? id = "#" + id : id = "#checkAll"; 
	inputName ? null : inputName = "items";
	$(id).click(
		function(){
			var inputs = $("input[name='" + inputName +"']");
			if(this.checked){
				inputs.each(function(){this.checked=true;});
			}else{
				inputs.each(function(){this.checked=false;});
			}
		}
	);
}

/**
 * items菜单，参考hivemeta.jsp页面
 */
function initItems(){
	var items = $(".item-tabs .item");
	var url = window.location.href;
	var tag = null;
	var index = url.lastIndexOf("#");
	if(index != -1){
		 tag = url.substr(index);
		 url = url.substr(0,index);
	}
	if(tag){
		items.each(function(){
			var that = $(this);
			var tmpTag = that.attr("tag");
			var fn = that.attr("fn");
			if(tmpTag != tag){
				$(tmpTag).hide();
				that.removeClass("active");
			}else{
				$(tmpTag).show();
				that.addClass("active");
				if(fn){
					eval(fn);
				}
			}
		});
	}
	items.each(function(){
		var that = $(this);
		that.click(function(){
			var tag = $(this).attr("tag");
			items.each(function(){
				var that = $(this);
				var tmpTag = that.attr("tag");
				var fn = that.attr("fn");
				if(tmpTag != tag){
					$(tmpTag).hide();
					that.removeClass("active");
				}else{
					$(tmpTag).show();
					that.addClass("active");
					if(fn){
						eval(fn);
					}
					try{
						history.pushState({}, null, url + tmpTag);
					}catch(e){
						myLog(e);
					}
				}
			});
		});
	});
}

/*
 * 获取网页地址栏的URL参数的值
 * */
function getUrlParam(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r!=null) return unescape(r[2]); return null; //返回参数值
} 

function deepClone(obj){
    var result,oClass=isClass(obj);
        //确定result的类型
    if(oClass==="Object"){
        result={};
    }else if(oClass==="Array"){
        result=[];
    }else{
        return obj;
    }
    for(key in obj){
        var copy=obj[key];
        if(isClass(copy)=="Object"){
            result[key]=arguments.callee(copy);//递归调用
        }else if(isClass(copy)=="Array"){
            result[key]=arguments.callee(copy);
        }else{
            result[key]=obj[key];
        }
    }
    return result;
}

function isClass(o){
    if(o===null) return "Null";
    if(o===undefined) return "Undefined";
    return Object.prototype.toString.call(o).slice(8,-1);
}

//计算两个整数的百分比值 
function getPercent(num, total) {
	num = parseFloat(num);
	total = parseFloat(total);
	if (isNaN(num) || isNaN(total)) {
		return "-";
	}
	return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00 + "%");
}

//计算流量值（ flowVlueBytes为流量的bytes字节数）
function getFlow(flowVlueBytes){
    var flow = "";
    //如果流量小于1MB.则显示为KB
    if(flowVlueBytes/1024 < 1024){
        flow = (Math.round(flowVlueBytes/1024) > 0 ? Math.round(flowVlueBytes/1024) : 0) + 'KB';
    }else if(flowVlueBytes/1024 >= 1024 && flowVlueBytes/1024/1024 < 1024){
        //如果流量大于1MB且小于1    GB的则显示为MB
        flow = (Math.round(flowVlueBytes/1024/1024) > 0 ? Math.round(flowVlueBytes/1024/1024) : 0)+'MB';
    }else if(flowVlueBytes/1024/1024 >= 1024){
        //如果流量大于1Gb
        var gb_Flow = flowVlueBytes/1024/1024/1024;
        //toFixed(1);四舍五入保留一位小数
        flow = gb_Flow.toFixed(1)+'GB';
    }else{
        flow = "0KB";
    }
    return flow;
}

String.prototype.trim=function() {
   return this.replace(/(^\s*)|(\s*$)/g,'');
}

Array.prototype.contains = function(obj) {
	var i = this.length;
	while (i--) {
		if (this[i] === obj) {
			return true;
		}
	}
	return false;
};
/* 
*  方法:Array.remove(dx) 通过遍历,重构数组 
*  功能:删除数组元素. 
*  参数:dx删除元素的下标. 
*/ 
Array.prototype.remove = function(dx) {
	if (isNaN(dx) || dx >= this.length) {
		return false;
	}
	for ( var i = 0, n = 0; i < this.length; i++) {
		if (this[i] != this[dx]) {
			this[n++] = this[i];
		}
	}
	this.length -= 1;
};

function isFunction(o){
	try{
		if(o && typeof(o) == "function"){
			return true;
		}
	}catch(e){
	}
	return false;
}

function toThousands(num) {
    var num = (num || 0).toString(), result = '';
    while (num.length > 3) {
        result = ',' + num.slice(-3) + result;
        num = num.slice(0, num.length - 3);
    }
    if (num) { result = num + result; }
    return result;
}

function get_cookie(Name) {
	var search = Name + "=";
	var returnvalue = "";
	if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search);
		if (offset != -1) {// 如果指定的cookie已经存在
			offset += search.length;// 长度指定到cookie值的位置               
			end = document.cookie.indexOf(";", offset); // 判断是否还包括其他cookie值
			if (end == -1) //如果不包括
				end = document.cookie.length; //获取cookie的长度
			returnvalue = unescape(document.cookie.substring(offset, end)); //获取cookie的值
		}
	}
	return returnvalue;
}