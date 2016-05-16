//设置默认的查询平台 
function setPlat() {
	$("#plats").val(617);
}

// 查询发牌概率
function searchFapaiRate() {
	var type = $("#type").val();
	var plat = $("#plats").val();
	var stm = $("#stm").val();
	var etm = $("#etm").val();
	if (!stm) {
		showMsg("请填写开始日期！");
		return false;
	}
	if (!etm) {
		showMsg("请填写结束日期！");
		return false;
	}
	$("#searchPaijuRate").button("loading");
	$.ajax({
		type : "get",
		url : 'paijuRate/getRate/fapaiRate.htm',
		data : {
			type : type,
			stm : stm,
			etm : etm,
			plat : plat
		},
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			commonData.oLanguage.sInfo = "";
			var table = $('#fapai').DataTable({
				destroy : true,
				searching : false,
				ordering : false,
				processing : true,
				paging : false,
				data : {},
				"tabIndex" : 1,
				"oLanguage" : commonData.oLanguage,
				"dom" : 'T<"clear">lfrtip',
				"tableTools" : {
					"sSwfPath" : "css/swf/copy_csv_xls_pdf.swf",
					"aButtons" : [ {
						"sExtends" : "copy",
						"sButtonText" : "复制"
					}, {
						"sExtends" : "xls",
						"sButtonText" : "下载",
						"bBomInc" : true
					} ]
				}
			});
			if (!$.isEmptyObject(data.obj)) {
				showFapaiRate(table, data.obj, type, '#fapai');
			}
			$("#searchPaijuRate").button("reset");
		},
		error : function(err) {
			showMsg("请求出错!");
			myLog(err);
			$("#searchPaijuRate").button("reset");
		}
	});
}

// 查询手牌概率
function searchShoupaiRate() {
	var type = $("#type").val();
	var plat = $("#plats").val();
	var stm = $("#stm").val();
	var etm = $("#etm").val();
	if (!stm) {
		showMsg("请填写开始日期！");
		return false;
	}
	if (!etm) {
		showMsg("请填写结束日期！");
		return false;
	}
	$("#searchPaijuRate").button("loading");
	$.ajax({
		type : "get",
		url : 'paijuRate/getRate/shoupaiRate.htm',
		data : {
			type : type,
			stm : stm,
			etm : etm,
			plat : plat
		},
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			var table = $('#shoupai').DataTable({
				destroy : true,
				searching : false,
				ordering : false,
				processing : true,
				paging : false,
				data : {},
				"tabIndex" : 1,
				"oLanguage" : commonData.oLanguage,
				"dom" : 'T<"clear">lfrtip',
				"tableTools" : {
					"sSwfPath" : "css/swf/copy_csv_xls_pdf.swf",
					"aButtons" : [ {
						"sExtends" : "copy",
						"sButtonText" : "复制"
					}, {
						"sExtends" : "xls",
						"sButtonText" : "下载",
						"bBomInc" : true
					} ]
				}
			});
			if (!$.isEmptyObject(data.obj)) {
				showShoupaiRate(table, data.obj, type, '#shoupai');
			}
			$("#searchPaijuRate").button("reset");
		},
		error : function(err) {
			showMsg("请求出错!");
			myLog(err);
			$("#searchPaijuRate").button("reset");
		}
	});
}

// 显示牌局概率
function showShoupaiRate(table, data, type, tableId) {
	var json = eval(data);
	var kv = convertShouPaiData(json);
	var showPai = new Array('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5',
			'4', '3', '2');
	var pai = new Array('0E', '0D', '0C', '0B', '0A', '09', '08', '07', '06',
			'05', '04', '03', '02');
	// for(var i in pai){
	var paiNum = pai.length;
	for ( var i = 0; i < paiNum; i++) {
		var row = new Array();
		row.push(showPai[i]);
		// for(var j in pai){
		for ( var j = 0; j < paiNum; j++) {
			var key = "";
			var rate = 0;
			var a = parseInt(i);// 必须转换成int进行比较
			var b = parseInt(j);
			if (a < b) {
				key = type + "tonghua" + pai[i] + pai[j];// pai[i] > pai[j]
			} else {
				key = type + "butonghua" + pai[j] + pai[i];// pai[i] < pai[j]
			}
			rate = kv.getValue(key);
			if (rate) {
				row.push(rate);
			} else {// 如果牌大到小的顺序取不到数据，牌从小到大去取数据
				if (a < b) {
					key = type + "tonghua" + pai[j] + pai[i];
				} else {
					key = type + "butonghua" + pai[i] + pai[j];
				}
				rate = kv.getValue(key);
				if (rate) {
					row.push(rate);
				} else {
					row
							.push("<span pClass='background-blue' data-toggle='tooltip' data-placement='top' title='0'>-100%</span>");
				}
			}
		}
		table.row.add(row).draw();
	}
	$("#shoupaiTotal").html("总次数：" + kv.total);
	setTdClass(tableId);
	$('[data-toggle="tooltip"]').tooltip();
}

// 显示牌局概率
function showFapaiRate(table, data, type, tableId) {
	var json = eval(data);
	var kv = convertFapaiData(json);
	var showPai = new Array('1', '2', '3', '4');
	var showZhongwenPai = new Array('方块', '梅花', '红桃', '黑桃');
	var pai = new Array('0E', '0D', '0C', '0B', '0A', '09', '08', '07', '06',
			'05', '04', '03', '02');
	// for(var i in showPai){
	for ( var i = 0, showPaiNum = showPai.length; i < showPaiNum; i++) {
		var row = new Array();
		row.push(showZhongwenPai[i]);
		// for(var j in pai){
		for ( var j = 0, paiNum = pai.length; j < paiNum; j++) {
			var key = "";
			var rate = 0;
			key = type + showPai[i] + pai[j];
			rate = kv.getValue(key);
			if (rate) {
				row.push(rate);
			} else {
				row
						.push("<span pClass='background-blue' data-toggle='tooltip' data-placement='top' title='0'>-100%</span>");
			}
		}
		table.row.add(row).draw();
	}
	$("#fapaiTotal").html("总次数：" + kv.total);
	setTdClass(tableId);
	$('[data-toggle="tooltip"]').tooltip();
}

// key-value值排序
function kvSort(data) {
	if ($.isEmptyObject(data)) {
		return data;
	}
	// for(var i in data){
	for ( var i = 0; i < data.length; i++) {
		data[i].rate = (isNaN(data[i].rate) ? -100 : 100 * data[i].rate)
				.toFixed(2);
		if (i < 10) {
			data[i].rate = "<span pClass='background-red' data-toggle='tooltip' data-placement='top' title='"
					+ data[i].num + "'>" + data[i].rate + "%</span>";
		} else if (i >= data.length - 10) {
			data[i].rate = "<span pClass='background-green' data-toggle='tooltip' data-placement='top' title='"
					+ data[i].num + "'>" + data[i].rate + "%</span>";
		} else {
			data[i].rate = "<span data-toggle='tooltip' data-placement='top' title='"
					+ data[i].num + "'>" + data[i].rate + "%</span>";
		}
	}
	data.getValue = function(key) {
		// for(var i in this){
		for ( var i = 0; i < this.length; i++) {
			if (this[i].key == key) {
				return this[i].rate;
			}
		}
	};
	return data;
}

// 将json数据转换成key_value型的json数据
function convertShouPaiData(json) {
	var data = new Array();
	var total = 0;
	// for(var i in json){
	for ( var i = 0; i < json.length; i++) {
		var num = json[i].num;
		num = isNaN(num) ? 0 : num;
		var d = {
			"key" : json[i].type + json[i].daxiao1 + json[i].daxiao2,
			"rate" : json[i].rate,
			"num" : num
		};
		data.push(d);
		total += num;
	}
	data.total = total;
	return kvSort(data);
}

// 将json数据转换成key_value型的json数据
function convertFapaiData(json) {
	var data = new Array();
	var total = 0;
	for ( var i = 0; i < json.length; i++) {
		// for(var i in json){
		var num = json[i].num;
		num = isNaN(num) ? 0 : num;
		var d = {
			"key" : json[i].type + json[i].hua + json[i].daxiao,
			"rate" : json[i].rate,
			"num" : num
		};
		data.push(d);
		total += num;
	}
	data.total = total;
	return kvSort(data);
}

function setTdClass(tableId) {
	$(tableId + " td span").each(function() {
		var that = $(this);
		var clazz = that.attr("pClass");
		that.parent("td").addClass(clazz);
	});
}

// 起手牌
// 查询牌局概率
function searchStartHandRate() {
	var plat = $("#plats").val();
	var stm = $("#stm").val();
	var etm = $("#etm").val();
	if (!stm) {
		showMsg("请填写开始日期！");
		return false;
	}
	if (!etm) {
		showMsg("请填写结束日期！");
		return false;
	}
	$("#searchStartHandRate").button("loading");
	$.ajax({
		type : "get",
		url : 'paijuRate/getRate/startHandRate.htm',
		data : {
			stm : stm,
			etm : etm,
			plat : plat
		},
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			var table = $('#startHand').DataTable({
				destroy : true,
				searching : false,
				ordering : false,
				processing : true,
				paging : false,
				data : {},
				"tabIndex" : 1,
				"oLanguage" : commonData.oLanguage,
				"dom" : 'T<"clear">lfrtip',
				"tableTools" : {
					"sSwfPath" : "css/swf/copy_csv_xls_pdf.swf",
					"aButtons" : [ {
						"sExtends" : "copy",
						"sButtonText" : "复制"
					}, {
						"sExtends" : "xls",
						"sButtonText" : "下载",
						"bBomInc" : true
					} ]
				}
			});
			if (!$.isEmptyObject(data.obj)) {
				showStartHandRate(table, data.obj);
			}
			$("#searchStartHandRate").button("reset");
		},
		error : function(err) {
			showMsg("请求出错!");
			myLog(err);
			$("#searchStartHandRate").button("reset");
		}
	});
}

function showStartHandRate(table, data) {
	var json = eval(data);
	var kv = convertStartHandData(json);
	var showPai = new Array('口袋对子', '同花', '连牌', '同花连牌', '杂牌');
	// for(var i in showPai){
	for ( var i = 0; i < showPai.length; i++) {
		var key = parseInt(i) + 1;
		var row = new Array();
		row.push(showPai[i]);
		var dataTmp = kv.getValue(key);
		if (dataTmp != null) {
			row.push(dataTmp.theoryRate + "%");
			row.push(dataTmp.trueRate + "%");
			row
					.push("<span data-toggle='tooltip' data-placement='top' title='实际概率/理论概率'>"
							+ dataTmp.offsetMul + "</span>");
			row
					.push("<span data-toggle='tooltip' data-placement='top' title='实际概率-理论概率'>"
							+ dataTmp.offsetPer + "%</span>");
			row
					.push("<span data-toggle='tooltip' data-placement='top' title='(实际概率-理论概率)*牌型次数'>"
							+ dataTmp.offset + "</span>");
			row.push(dataTmp.num);
			row.push(dataTmp.count);
			table.row.add(row).draw();
		}
	}
}

function dealRate(data) {
	if ($.isEmptyObject(data)) {
		return data;
	}
	// for(var i in data){
	for ( var i = 0; i < data.length; i++) {
		data[i].theoryRate = (isNaN(data[i].theoryRate) ? -100
				: 100 * data[i].theoryRate).toFixed(4);
		data[i].trueRate = (isNaN(data[i].trueRate) ? -100
				: 100 * data[i].trueRate).toFixed(4);
		data[i].offsetMul = (isNaN(data[i].offsetMul) ? 0 : data[i].offsetMul)
				.toFixed(4);
		data[i].offsetPer = (isNaN(data[i].offsetPer) ? -100
				: 100 * data[i].offsetPer).toFixed(4);
	}
	data.getValue = function(key) {
		// for(var i in this){
		for ( var i = 0; i < this.length; i++) {
			if (this[i].type == key) {
				return this[i];
			}
		}
	};
	return data;
}

function convertStartHandData(json) {
	var data = new Array();
	// for(var i in json){
	for ( var i = 0; i < json.length; i++) {
		var type = json[i].type;
		var theoryRate = 0;
		if (type == 1) {
			theoryRate = 0.058814;
		} else if (type == 2) {
			theoryRate = 0.235294;
		} else if (type == 3) {
			theoryRate = 0.156863;
		} else if (type == 4) {
			theoryRate = 0.039216;
		} else if (type == 5) {
			theoryRate = 0.509804;
		}
		var d = {
			"type" : json[i].type,
			"theoryRate" : theoryRate,
			"trueRate" : json[i].num / json[i].count,
			"offsetMul" : theoryRate == 0 ? 0 : (json[i].num / json[i].count)
					/ theoryRate,
			"offsetPer" : json[i].num / json[i].count - theoryRate,
			"offset" : parseInt(json[i].num
					* (json[i].num / json[i].count - theoryRate)),
			"num" : json[i].num,
			"count" : json[i].count
		};
		data.push(d);
	}
	return dealRate(data);
}

// 成牌
// 查询牌局概率
function searchEndHandRate() {
	var plat = $("#plats").val();
	var stm = $("#stm").val();
	var etm = $("#etm").val();
	if (!stm) {
		showMsg("请填写开始日期！");
		return false;
	}
	if (!etm) {
		showMsg("请填写结束日期！");
		return false;
	}
	$("#searchEndHandRate").button("loading");
	$.ajax({
		type : "get",
		url : 'paijuRate/getRate/endHandRate.htm',
		data : {
			stm : stm,
			etm : etm,
			plat : plat
		},
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			var table = $('#endHand').DataTable({
				destroy : true,
				searching : false,
				ordering : false,
				processing : true,
				paging : false,
				data : {},
				"tabIndex" : 1,
				"oLanguage" : commonData.oLanguage,
				"dom" : 'T<"clear">lfrtip',
				"tableTools" : {
					"sSwfPath" : "css/swf/copy_csv_xls_pdf.swf",
					"aButtons" : [ {
						"sExtends" : "copy",
						"sButtonText" : "复制"
					}, {
						"sExtends" : "xls",
						"sButtonText" : "下载",
						"bBomInc" : true
					} ]
				}
			});
			if (!$.isEmptyObject(data.obj)) {
				showEndHandRate(table, data.obj);
			}
			$("#searchEndHandRate").button("reset");
		},
		error : function(err) {
			showMsg("请求出错!");
			myLog(err);
			$("#searchEndHandRate").button("reset");
		}
	});
}

function showEndHandRate(table, data) {
	var json = eval(data);
	var kv = convertEndHandData(json);
	var showPai = new Array('皇家同花顺', '同花顺', '四条', '葫芦', '同花', '顺子', '三条', '两对',
			'一对', '高牌/杂牌', '弃牌');
	// for(var i in showPai){
	for ( var i = 0; i < showPai.length; i++) {
		var key = parseInt(i);
		if (key == 9) {// 高牌杂牌放在一起了 特殊处理下
			key = 2;
		} else if (key == 10) {// 弃牌paixing为0 也特殊处理下
			key = 0;
		} else {
			key = 11 - key;
		}
		var row = new Array();
		row.push(showPai[i]);
		var dataTmp = kv.getValue(key);
		if (dataTmp != null) {
			row.push(dataTmp.theoryRate == 0 ? 0 : dataTmp.theoryRate + "%");
			row.push(dataTmp.trueRate + "%");
			row
					.push(dataTmp.trueRate1 == 0 ? "<span data-toggle='tooltip' data-placement='top' title='弃牌此项不统计'>"
							+ 0 + "</span>"
							: "<span data-toggle='tooltip' data-placement='top' title='牌型次数/(牌型总数-弃牌的牌型次数)'>"
									+ dataTmp.trueRate1 + "%</span>");
			row
					.push("<span data-toggle='tooltip' data-placement='top' title='实际概率/理论概率'>"
							+ dataTmp.offsetMul + "</span>");
			row
					.push("<span data-toggle='tooltip' data-placement='top' title='实际概率-理论概率'>"
							+ dataTmp.offsetPer + "%</span>");
			row
					.push("<span data-toggle='tooltip' data-placement='top' title='(实际概率-理论概率)*牌型次数'>"
							+ dataTmp.offset + "</span>");
			row.push(dataTmp.num);
			row.push(dataTmp.count);
			table.row.add(row).draw();
		}
	}
}

function convertEndHandData(json) {
	var data = new Array();
	var temp = null;
	var total = 0;// 所有的加起来
	var total1 = 0;// 除了弃牌之后的加起来
	// for(var i in json){
	for ( var i = 0; i < json.length; i++) {
		var num = json[i].num;
		var type = json[i].type;
		num = isNaN(num) ? 0 : num;
		total += parseInt(num);
		if (type != 0) {
			total1 += parseInt(num);
		}
	}
	// for(var i in json){
	for ( var i = 0; i < json.length; i++) {
		var num = json[i].num;
		num = isNaN(num) ? 0 : num;
		var type = json[i].type;
		var theoryRate = 0;
		if (type == 1) {
			theoryRate = 0.1728770000;
		} else if (type == 2) {
			theoryRate = 0.1728770000;
		} else if (type == 3) {
			theoryRate = 0.4377290000;
		} else if (type == 4) {
			theoryRate = 0.2349550000;
		} else if (type == 5) {
			theoryRate = 0.0482987000;
		} else if (type == 6) {
			theoryRate = 0.0479329000;
		} else if (type == 7) {
			theoryRate = 0.0302859000;
		} else if (type == 8) {
			theoryRate = 0.0259610000;
		} else if (type == 9) {
			theoryRate = 0.0016806700;
		} else if (type == 10) {
			theoryRate = 0.0002785070;
		} else if (type == 11) {
			theoryRate = 0.0000323206;
		}
		var d = null;
		var d = {
			"type" : json[i].type,
			"theoryRate" : theoryRate,
			"trueRate" : num / total,
			"trueRate1" : json[i].type == 0 ? 0 : num / total1,
			"offsetMul" : theoryRate == 0 ? 0 : (num / total) / theoryRate,
			"offsetPer" : num / total - theoryRate,
			"offset" : parseInt(num * (num / total - theoryRate)),
			"num" : num,
			"count" : total
		};
		if (type == 1 || type == 2) {
			if (temp == null) {
				d.type = 2;
				temp = d;
			} else {
				temp.num = parseInt(temp.num) + parseInt(num);
				temp.trueRate = temp.num / total;
				temp.trueRate1 = d.type == 0 ? 0 : temp.num / total1;
				temp.offsetMul = temp.theoryRate == 0 ? 0 : temp.trueRate
						/ temp.theoryRate;
				temp.offsetPer = temp.num / total - temp.theoryRate;
				temp.offset = parseInt(temp.num
						* (temp.num / total - temp.theoryRate));
			}
		} else {
			data.push(d);
		}
	}
	data.push(temp);
	return dealEndHand(data);
}

function dealEndHand(data) {
	if ($.isEmptyObject(data)) {
		return data;
	}
	// for(var i in data){
	for ( var i = 0; i < data.length; i++) {
		data[i].theoryRate = (isNaN(data[i].theoryRate) ? -100
				: 100 * data[i].theoryRate).toFixed(8);
		data[i].trueRate = (isNaN(data[i].trueRate) ? -100
				: 100 * data[i].trueRate).toFixed(8);
		data[i].trueRate1 = (isNaN(data[i].trueRate1) || data[i].trueRate1 == 0) ? 0
				: data[i].trueRate1.toFixed(8);
		data[i].offsetMul = (isNaN(data[i].offsetMul) || data[i].offsetMul == 0) ? 0
				: data[i].offsetMul.toFixed(4);
		data[i].offsetPer = (isNaN(data[i].offsetPer) ? -100
				: 100 * data[i].offsetPer).toFixed(4);
	}
	data.getValue = function(key) {
		// for(var i in this){
		for ( var i = 0; i < this.length; i++) {
			if (this[i].type == key) {
				return this[i];
			}
		}
	};
	return data;
}