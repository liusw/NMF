<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="static/js/jquery-1.9.1.js"></script>
<script src="static/js/ucuser.js"></script>
<style>
.ucuser .checkbox-inline + .checkbox-inline{
	margin-left:0;
}

.ucuser .checkbox-inline{
	width:200px;
	height: 20px;
	line-height: 20px;
	overflow: hidden;
}
</style>
<script>
$(function(){
	loadUcuser();
});
</script>
</head>
<body>
<input type="checkbox" id="checkall" style="margin:2px 6px 3px 0;vertical-align:middle;">
<label>用户信息[<span style="color: green;font-weight: bold;">如果不用导出用户信息,不用勾选即可</span>]：</label>
<div class="ucuser" id="ucuser">
	
</div>
</body>
</html>
