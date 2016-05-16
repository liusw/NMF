<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="tableParam_<%=request.getParameter("index") %>"></div>
<script type="text/javascript" src="static/js/map.js"></script>

<script type="text/javascript">
	var index = '<%=request.getParameter("index") %>';
	
	$(function(){
		getWinlogModeTableParam(index);
	});
	
	var hashMap;
	function getWinlogModeTableParam(_index){
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
						'<label class="checkbox-inline"><input type="checkbox" asName="c2" name="g___s___wchips" value="1"  title="总输赢数目" onclick="setVal(this,\'c\')">总输赢数目</label>&nbsp;&nbsp;&nbsp;&nbsp;'+
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