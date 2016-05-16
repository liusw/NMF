Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds() // 毫秒
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)){
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

Date.prototype.addMinu = function(m) {
	this.setMinutes(this.getMinutes() + m);
};

Date.prototype.addHour = function(h) {
	this.setHours(this.getHours() + h);
};

Date.prototype.addDays = function(d) {
	this.setDate(this.getDate() + d);
};

Date.prototype.addWeeks = function(w) {
	this.addDays(w * 7);
};

Date.prototype.addMonths = function(m) {
	var d = this.getDate();
	this.setMonth(this.getMonth() + m);

	if (this.getDate() < d) {
		this.setDate(0);
	}
};

Date.prototype.addYears = function(y) {
	var m = this.getMonth();
	this.setFullYear(this.getFullYear() + y);

	if (m < this.getMonth()) {
		this.setDate(0);
	}
};

Date.newInstance = function(dateStr){
	if(dateStr){
		if(dateStr.indexOf("/") == -1 || dateStr.indexOf("-") == -1){
			var year = dateStr.substring(0, 4);
			var m = dateStr.substring(4, 6);
			var d = dateStr.substring(6, 8);
			dateStr = year + "-" + m + "-" + d;
		}
		return new Date(dateStr);
	}else{
		return new Date();
	}
};

Date.prototype.chinaDay = function(simple) {
	var d = this.getDay();
	var x = new Array("星期日", "星期一", "星期二","星期三","星期四", "星期五","星期六");
	var s = new Array("日", "一", "二","三","四", "五","六");
	if(simple){
		return s[d];
	}else{
		return x[d];
	}
};

Date.prototype.toFormatDate = function()   
{    
    var myDate= this;   
    var month = myDate.getMonth() + 1;
    var str = myDate.getFullYear() + "-" + month + "-" + myDate.getDate();   
    return str;   
} 
