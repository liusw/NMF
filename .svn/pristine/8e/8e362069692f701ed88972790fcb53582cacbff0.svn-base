<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="panel panel-warning">
  <div class="panel-body">
	<p>如果发现mf导出的用户信息和cms不一致,上传用户ID同步</p>
	<p style="color:red;font-weight: bold;">上传的文件请不要带标题,并且每行只能是一个用户ID;上传前请核对好右上角选择的平台和你上传用户的平台是否一致</p>
	<p style="color:red;font-weight: bold;">由于每个用户要请求接口上报数据,所以提示"同步成功"后稍等10分钟再获取用户数据</p>
  </div>
</div>

<form class="form-inline" id="form2" method="post" enctype="multipart/form-data">
	<div class="form-group">
		<label>上传文件:</label>
		<input type="file" name="file" id="file"/>
	</div>
	<div class="form-group">
		&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" id="submitBtn" onclick="upload();" data-loading-text="同步中...">同步</button>
	</div>
	
</form>
	<hr>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/md5.js"></script>
<script type="text/javascript">
var hashMap;
$(function(){
	hashMap=new HashMap();
	
	hashMap.put(607,"http://texas.17c.cn/api/user.php?do=mf&mid=");//新浪
	hashMap.put(617,"http://httptwtexas01.boyaagame.com/texas/api/user.php?do=mf&mid=");//繁体
	hashMap.put(1004,"http://httpentexas02.boyaagame.com/texas/api/user.php?do=mf&mid=");//英语
	hashMap.put(630,"http://httprutexas03.boyaagame.com/texas/api/user.php?do=mf&mid=");//俄语
	hashMap.put(615,"http://pclpkrpk02-static.boyaagame.com/texas/api/user.php?do=mf&mid=");//日语
	hashMap.put(616,"http://pclpptpk02-static.boyaagame.com/texas/api/user.php?do=mf&mid=");//葡语
	hashMap.put(1001,"http://httpsptexas03.boyaagame.com/texas/api/user.php?do=mf&mid=");//西语
	hashMap.put(613,"http://httpdetexas01.boyaagame.com/texas/api/user.php?do=mf&mid=");//德语
	hashMap.put(618,"http://lpfrpk01-static.boyaagame.com/texas/api/user.php?do=mf&mid=");//法语
	hashMap.put(604,"http://lpthpk06-static.boyaagame.com/texas/api/user.php?do=mf&mid=");//泰语
	hashMap.put(612,"http://httpidtexas01.boyaagame.com/texas/api/user.php?do=mf&mid=");//印尼
	hashMap.put(620,"http://httpturkeytexas01.boyaagame.com/texas/api/user.php?do=mf&mid=");//土语
	hashMap.put(610,"http://pclpvdpk01.boyaagame.com/texas/api/user.php?do=mf&mid=");//越南
	hashMap.put(619,"http://artexas3.boyaagame.com/texas/api/user.php?do=mf&mid=");//阿语
	hashMap.put(614,"http://httpitalytexas01.boyaagame.com/texas/api/user.php?do=mf&mid=");//意大利
	hashMap.put(1110,"http://lpplpk02.boyaagame.com/texas/api/user.php?do=mf&mid=");//波兰
});
function upload(){
	var plat = $("#navPlat").val();
	if(!hashMap.containsKey(plat)){
		alert("该平台暂未开通同步用户接口");
		return;
	}
	var syncUrl=hashMap.get(plat);
	
	
	var fileVal=$("#file").val();
	if(fileVal!=null && fileVal.length>0){
		var fileExt = fileVal.substring(fileVal.lastIndexOf('.') + 1);
		if(fileExt!='txt' && fileExt!='csv'){
			alert("目录只允许上传.txt和.csv文件");
			return;
		}
						
		$(function(){  
			var options = {
	           	url : "data/user/uploadSyncUser.htm",  
	           	type : "POST",  
	           	dataType : "json",
	           	data:{syncUrl:syncUrl},
	           	success : function(msg) {
	          		if(msg && msg.ok==1){
	          			alert("同步成功,本次同步的用户数为:"+msg.other.line);
	          		}else{
	          			alert(msg.msg);
	          			}
	           		},
	           	error:function(msg){
	           		alert(msg);
	           		}
	      	};
			$("#form2").ajaxSubmit(options);  
       	return false;  
		});  
	}else{
		alert("请选择要上传的文件");
	}
}
/*
time=1442225180&param[type]=1&param[tktype]=39&param[tktitle]=sddddss63336ssddsssss2038071003&param[tkcontent]=ssss2sssssssssssss&param[sid]=10&param[rtx]=&param[pjid]=2&param[mid]=20&param[clientip]=0.0.0.0&method=Task.Add&appid=1
md5 =  sig = md5( $secret . $str . $secret)；
sig 
	$secret = '6fe7f10342bfae481c09665dbb86f5e7';
	$str = time=1442225180&param[type]=1&param[tktype]=39&param[tktitle]=sddddss63336ssddsssss2038071003&param[tkcontent]=ssss2sssssssssssss&param[sid]=10&param[rtx]=&param[pjid]=2&param[mid]=20&param[clientip]=0.0.0.0&method=Task.Add&appid=1&sig=37f598d8b6dc96851a6db29e3a93756a
	post
http://api.ifere.com:58080/list/api/rest.php
*/

function testAddList(){
	var str = "time=1442228613&type=1&tktype=39&tktitle=测试mf提交list&tkcontent=测试mf提交list&sid=10&rtx=MarsHuang&pjid=2&clientip=183.62.196.12&method=Task.Add&appid=1";
	var secret='6fe7f10342bfae481c09665dbb86f5e7';
	var sig = hex_md5(secret+str+secret);
	$.ajax({
        async: false,
        url: "http://api.ifere.com:58080/list/api/rest.php",
        type: "POST",
       // dataType: 'jsonp',
        //jsonp的值自定义,如果使用jsoncallback,那么服务器端,要返回一个jsoncallback的值对应的对象.
        //jsonp: 'jsoncallback',
       	 //要传递的参数,没有传参时，也一定要写上
          data: {
				'time':'1442228613',
				'type':'1',
				'tktype':'39',
				'tktitle':'测试mf提交list',
				'tkcontent':'测试mf提交list',
				'sid':'10',
				'rtx':'MarsHuang',
				'pjid':'2',
				'clientip':'183.62.196.12',
				'method':'Task.Add',
				'appid':'1',
				'sig':sig
			},
        //返回Json类型
      //contentType: "text/json;charset=UTF-8",
      contentType: "html",
        //服务器段返回的对象包含name,data属性.
        success: function (result) {
            alert(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    });
	//http://list.oa.com/api/rest.php
/*	$.post("http://api.ifere.com:58080/list/api/rest.php",
		{
			'time':'1442225180',
			'type':'1',
			'tktype':'39',
			'tktitle':'测试mf提交list',
			'tkcontent':'测试mf提交list',
			'sid':'10',
			'rtx':'MarsHuang',
			'pjid':'2',
			'clientip':'0.0.0.0',
			'method':'Task.Add',
			'appid':'1',
			'sig':sig
		},
		function(data) {
			alert(data);
		},"json").error(function(){
			alert("error");
		});*/
}
</script>
