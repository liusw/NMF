/**
 * daterange.by.js
 * 
 *	初始化:
 * 	daterange = DaterangeUtil.init({'dom':'#reportrange'});
 * 参数:
 * 	dom:页面dom节点,例如你的表单Id为reportrange,参数值为:#reportrange,这项必须传进来
 * 	singleDatePicker:是不是显示单个日期,如果不传此参数默认是false
 * 	startDate:初始化开始时间,如果不传此参数默认是昨天
 * 	endDate:初始化结束时间,如果不传此参数默认是昨天
 * 
 * 获取开始结束时间:
 * 	var start = daterange.getStartDate("#navdaterange");
 * 	var end = daterange.getEndDate("#navdaterange");
 * 
 * @author: marsHuang
 */
var DaterangeUtil = (function() {
	return {
		init : function(options) {
			this.setOptions(options);
			return this;
		},
		setOptions : function(options) {
			this.dom = options.dom;
			if (typeof options.singleDatePicker === 'boolean') {
				this.singleDatePicker = options.singleDatePicker;
			}
			if (typeof options.timePicker === 'boolean') {
				this.timePicker = options.timePicker;
			}
			if (typeof options.startDate === 'string') {
				this.startDate = options.startDate;
			}
			if (typeof options.endDate === 'string') {
				this.endDate = options.endDate;
			}
			if (typeof options.format === 'string') {
				this.format = options.format;
			}
			if (typeof options.opens === 'string') {
				this.opens = options.opens;
			}
			if (options.applyClickCallBack) {
				this.applyClickCallBack = options.applyClickCallBack;
			}
			this.minDate=null,this.maxDate=null;
			if (typeof options.minDate === 'string') {
				this.minDate = options.minDate;
			}
			if (typeof options.maxDate === 'string') {
				this.maxDate = options.maxDate;
			}
			this.initDateRange();
		},
		initDateRange:function(){
			var _dom = this.dom;
			var _format=this.format?this.format:'YYYY-MM-DD';
			
			var optionSet2 = {
				 format: _format,
	          startDate: this.startDate?this.startDate:moment().subtract(1, 'days'),
	          endDate: this.endDate?this.endDate:moment().subtract(1, 'days'),
	          opens: this.opens?this.opens:'left',
	          timePicker: this.timePicker,
	          singleDatePicker:this.singleDatePicker,
	          applyClickCallBack:this.applyClickCallBack,
	          //minDate:this.minDate,
	          //maxDate:this.maxDate,
	          /*ranges: {
	    			'最近七天': [moment().subtract(7, 'days'), moment().subtract(1, 'days')],
	     			'昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	    			'最近30天': [moment().subtract(30, 'days'),moment().subtract(1, 'days')],
	    			'本月': [moment().startOf('month'), moment().endOf('month')],
	        	 },*/
	         locale: {
	                applyLabel: '确定',
	                cancelLabel: '取消',
	                fromLabel: '开始日期',
	                toLabel: '结束日期',
	                customRangeLabel: '时间段选择',
	                daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
	                monthNames: ['一月', '二月', '三月', '四月', '五月','六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	                firstDay: 1
	            }
	        };
			
			if(this.minDate){
				optionSet2.minDate=this.minDate;
			}
			if(this.maxDate){
				optionSet2.maxDate=this.maxDate;
			}
			
			if(this.timePicker){
				optionSet2.timePickerIncrement=1;
				optionSet2.timePicker12Hour=false;
				optionSet2.timePickerSeconds=true;
			}
			$(_dom).daterangepicker(optionSet2,function(start, end, label) {
						$(_dom + ' span').html(this.singleDatePicker ? start.format(_format) : (start.format(_format)+ ' 至 ' + end.format(_format)));
			});
			$(_dom + ' span').html(this.singleDatePicker ? this.getStartDate() : (this.getStartDate() + ' 至 ' + this.getEndDate()));
			//$(_dom + ' span').html(this.singleDatePicker?start.format(_format) : (start.format(_format)+ ' 至 ' + end.format(_format)));
		},
		getStartDate:function(domId,formatStr){
			//由于this.dom为创建的最后一个时间元素，所以同一页面存在多个时间组件时需要指定ID
			var dateDom = $(this.dom);
			if(undefined != domId && $(domId).length > 0){
				dateDom = $(domId);
			}
			
			var _format=formatStr?formatStr:(this.format?this.format:'YYYY-MM-DD');
			return dateDom.data('daterangepicker').startDate.format(_format);
		},
		getEndDate:function(domId,formatStr){
			//由于this.dom为创建的最后一个时间元素，所以同一页面存在多个时间组件时需要指定ID
			var dateDom = $(this.dom);
			if(undefined != domId && $(domId).length > 0){
				dateDom = $(domId);
			}

			var _format=formatStr?formatStr:(this.format?this.format:'YYYY-MM-DD');
			return dateDom.data('daterangepicker').endDate.format(_format);
		},
		setStartDate: function(domId,dateStr){
			var dateDom = $(this.dom);
			if(undefined != domId && $(domId).length > 0){
				dateDom = $(domId);
			}
        	var thisDP = dateDom.data('daterangepicker');

        	thisDP.setStartDate(dateStr);
        	dateDom.find('span').html(thisDP.singleDatePicker ? dateStr : (dateStr + ' 至 ' + thisDP.endDate.format('YYYY-MM-DD')));
		},
		setEndDate: function(domId,dateStr){
			var dateDom = $(this.dom);
			if(undefined != domId && $(domId).length > 0){
				dateDom = $(domId);
			}
        	var thisDP = dateDom.data('daterangepicker');

        	thisDP.setEndDate(dateStr);
        	dateDom.find('span').html((thisDP.startDate.format('YYYY-MM-DD') + ' 至 ' + dateStr));
		},
		setDate:function(domId,startDate,endDate){
			if(undefined != startDate && "" != startDate){
				this.setStartDate(domId,startDate);
			}
			
			if(undefined != endDate && "" != endDate){
				this.setEndDate(domId,endDate);
			}
		}
	};
})();