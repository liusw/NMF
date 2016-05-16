<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="tableParam_<%=request.getParameter("index") %>"></div>
<script type="text/javascript" src="static/js/map.js"></script>

<script type="text/javascript">
	var index = '<%=request.getParameter("index") %>';
	
	$(function(){
		getUserGamblingTableParam(index);
	});
	
	var hashMap;
	function getUserGamblingTableParam(_index){
		hashMap=new HashMap();
		$("#tableParam_"+index).removeAttr("column");
		$("#tableParam_"+index).removeAttr("title");
		
		var distinctType = -1;
		var callBack;
		if(_index==-1){
			distinctType = $("#distinctType");
		}else{
			distinctType = $("#process_"+_index).find("[name=distinctType]");
		}
		
		var html='';
		if(distinctType.val()==1 || distinctType.val()==2|| distinctType.val()==5){
			html='<div class="form-group myrow">'+
						//'按盲注统计牌局数:<input type="text" autocomplete="off" placeholder="多个盲注以,号分开" class="form-control" name="blindminCount">&nbsp;&nbsp;'+
						'<label class="checkbox-inline"><input type="checkbox" asName="c0" name="g___c___cgcoins___cgcoins#4#0" value="1"  title="赢牌局数" onclick="setVal(this,\'c\')">赢牌局数</label>&nbsp;&nbsp;&nbsp;&nbsp;'+
						'<label class="checkbox-inline"><input type="checkbox" asName="c1" name="g___c___cgcoins___cgcoins#1#0" value="1"  title="输牌局数" onclick="setVal(this,\'c\')">输牌局数</label>&nbsp;&nbsp;&nbsp;&nbsp;'+
						'<label class="checkbox-inline"><input type="checkbox" asName="c2" name="g___s___cgcoins___cgcoins#4#0" value="1"  title="赢金币" onclick="setVal(this,\'c\')">赢金币</label>&nbsp;&nbsp;&nbsp;&nbsp;'+
						'<label class="checkbox-inline"><input type="checkbox" asName="c3" name="g___s___cgcoins___cgcoins#1#0" value="1"  title="输金币" onclick="setVal(this,\'c\')">输金币</label>'+
						'<label class="checkbox-inline"><input type="checkbox" asName="c4" name="g___s___vmoney" value="1"  title="台费消耗" onclick="setVal(this,\'c\')">台费消耗</label>&nbsp;&nbsp;&nbsp;&nbsp;'+
					'</div>';
		}
		$("#tableParam_"+_index).html(html);
	}
	
	function setVal(o,t){
		if(t=='c'){//checkBox
			if(o.checked){
				var title_column=$(o).attr('title')+"|"+$(o).attr('asName');
				hashMap.put($(o).attr('name'),title_column);
			}else{
				hashMap.removeByKey($(o).attr('name'));
			}
			$("#tableParam_"+index).attr("column",hashMap.keys().join(','));
			$("#tableParam_"+index).attr("title",hashMap.values().join(','));
			
			if(index==-1){
				Task.hiveCheck();
			}
		}
	}
</script>