$.widget("boyaa.mymultiselect", $.ech.multiselect, {
	close : function() {
		if (this.options.selectClose) {
			this.options.selectClose(this);
		}
		return this._super();
	},
	getValues : function() {
		return this.options.multiValues;
	},
	getSidValues : function() {
		return this.options.multiSidValues;
	},
	getBpidValues : function() {
		return this.options.multiBpidValues;
	},
	getTitles : function() {
		return this.options.multiTitles;
	},
	update : function() {
		var $inputs = this.inputs;
		var $checked = $inputs.filter(':checked');
		var numChecked = $checked.length;

		if (numChecked === 0) {
			this.options.multiValues = '';
			this.options.multiSidValues = '';
			this.options.multiBpidValues = '';
			this.options.multiTitles = '';
		} else {
			this.options.multiValues = $checked.map(function() {
				return $(this).val();
			}).get().join(',');
			
			this.options.multiTitles = $checked.map(function() {
				return $(this).attr("title");
			}).get().join(',');
			
			this.options.multiSidValues = $checked.map(function() {
				return $(this).val().split("_")[0];
			}).get().join(',');
			
			this.options.multiBpidValues = $checked.map(function() {
				return '"' + $(this).val().split("_")[1] + '"';
			}).get().join(',');
		}
		this._super();
		if(this.options.mydata && this.options.mydata.callback){
			this.options.mydata.callback(this.options.mydata.processId);
		}
	},
	_setButtonWidth:function(){
		this._super();
		if(this.options.width){
			width = this.options.width;
			this.button.outerWidth(width);
		}
		
		if(this.options.maxWidth){
			var width = this.element.outerWidth();
			if(width > this.options.maxWidth) {
				width = this.options.maxWidth;
			}
			this.button.outerWidth(width);
		}
	}
});