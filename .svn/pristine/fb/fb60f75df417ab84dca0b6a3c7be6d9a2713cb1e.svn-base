//某些浏览器不支持Object.create,手动添加这个方法
if(typeof Object.create!="function") {
	Object.prototype.create = function(obj) {
		var F = function(){};
		F.prototype = obj;
		return new F();
	};
}

(function($,window,document){
	
	var MyTask = {
		
	} ;
	
	$.fn.mytable = function(options){
		//为了满足多个元素的展示，使用each来遍历多个元素,此处的this就是jquery的this
		return this.each(function(){
			var mt = Object.create(MyTask);
			mt.init(this,options);
		});
	};
	
	/**
	 * 自定义的options的常量
	 */
	$.fn.myTask.options = {
			
	};
	
})(jQuery,window,document);