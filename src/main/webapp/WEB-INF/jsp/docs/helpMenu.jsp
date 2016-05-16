<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--sidebar-menu-->
<div id="sidebar">
  <ul class="sidebar_ul" cid="<%=request.getParameter("cid")%>"></ul>
</div>
<script src="static/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(function(){
	$(".sidebar_ul").append('<li class="cutover_li <c:if test="${param.nav=='index'}">active</c:if>"><a href="docs/home.htm" class="li_a"><i class="glyphicon glyphicon-home"></i> 首页</a>');
	
	//获取所有分类
	var data = getJsonData("docs/category/getPDatas.htm",true);
	if(data){
		$.each(data, function(index, value) {
			
			var li = $('<li class="cutover_li" value="'+value.id+'"><a href="docs/article/index.htm?cId='+value.id+'" class="li_a"><i class="glyphicon glyphicon-plus"></i> '+value.name+'</a></li>');
			if(value.children && value.children.length>0){
				var ul = $('<ul style="display: none;"></ul>');
				$.each(value.children, function(cindex, cvalue) {
					if(0 == cindex){
						li.find("a").attr("href","docs/article/index.htm?cId=" + cvalue.id);
					}
					ul.append('<li class="liulli" value="'+cvalue.id+'" id="liulli_'+cvalue.id+'"><a href="docs/article/index.htm?cId='+cvalue.id+'"><i class="glyphicon glyphicon-file"></i>'+cvalue.name+'</a></li>');
				});
				li.append(ul);
			}
			
			$(".sidebar_ul").append(li);
		});
		
		$("#sidebar").find("li").each(function(){
			var cVal = $(".sidebar_ul").attr('cid');
			/*if(null == cVal || "" == cVal){
				cVal = 2;
			}*/
			
			if($(this).attr('value')==cVal){
				if($(this).hasClass('liulli')){
					$(this).addClass("active");
					$(this).parent().parent().addClass("active");
					$(this).parent().parent().find('i').removeClass("glyphicon-plus").addClass("glyphicon-minus");
					$(this).parent().show();
				}else if($(this).hasClass('cutover_li')){
					$(this).addClass("active");
					$(this).find('i').removeClass("glyphicon-plus").addClass("glyphicon-minus");
					$(this).find("ul").show();
				}
				return;
			}
		});
	}
	//$("#sidebar").height($("#sidebar").height()-80);
});	
/*	$(".li_a").click(function(){
		$(this).parent().find("ul").toggle(function(){
			if($(this).is(":hidden")){
				$(this).parent().removeClass("active");
			}else{
				$(this).parent().addClass("active");
			}
		});
		if($(this).parent().find('i').hasClass("glyphicon-minus")){
			$(this).parent().find('i').removeClass("glyphicon-minus").addClass("glyphicon-plus");
		}else{
			$(this).parent().find('i').removeClass("glyphicon-plus").addClass("glyphicon-minus");
		}
	});

function cclick(id){
	$("#sidebar").find(".liulli").each(function(){
		$(this).removeClass("active");
	});
	$("#liulli_"+id).addClass("active");
	$("#cid").val(id);
	initTable();
	
}
*/
</script>