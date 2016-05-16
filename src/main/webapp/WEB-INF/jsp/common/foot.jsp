<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 
<footer class="bs-footer" style="text-align: center;z-index:1999;height: 30px;">
	<div class="mycontainer">
		<p>Copyright&copy; 2012 - 2020 博雅互动(Boyaa Interactive) 技术支持：日志中心&德州扑克开发团队 问题反馈：郑壮杰 黄奕能</p>
	</div>
</footer>
 -->
 
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="showMsg">
	<div class="modal-dialog modal-lg">
	   <div class="modal-content">
	     <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	      <h5 class="modal-title" id="msgTitle">提示</h5></div>
	      <div class="modal-body" id="msgContent"></div>
	      <div class="modal-footer">
	      	<button type="button" class="btn btn-default" id="closeBtn">关闭</button>
	      	<button type="button" class="btn btn-primary" data-loading-text="提交中..." id="confirmBtn">确定</button>
	      </div>
	   </div>
	</div>
</div>

<style type="text/css">
#winpop { width:200px; height:0px; position: fixed;right:0; bottom:0px; border:1px solid #999999; margin:0; padding:1px; overflow:hidden; display:none; background:#FFFFFF}
#winpop .title { width:100%; height:20px; line-height:20px; background:#FFCC00; font-weight:bold; text-align:center; font-size:12px;}
#winpop .con { width:100%; height:100px;font-size:12px; color:#2E363F; text-decoration:underline; text-align:center;padding:10px 0 0 5px;}
#winpop .con li{line-height: 20px;list-style-type:none;text-align: left;}
#winpop .con li a{color:#2E363F;}
#winpop .close { position:absolute; right:4px; top:-1px; color:#2E363F; cursor:pointer}
</style>
<script type="text/javascript">
	function tips_pop() {
		var MsgPop = document.getElementById("winpop");//获取窗口这个对象,即ID为winpop的对象
		var popH = parseInt(MsgPop.style.height);//用parseInt将对象的高度转化为数字,以方便下面比较
		if (popH == 0) { //如果窗口的高度是0
			MsgPop.style.display = "block";//那么将隐藏的窗口显示出来
			show = setInterval("changeH('up')", 2);//开始以每0.002秒调用函数changeH("up"),即每0.002秒向上移动一次
		} else { //否则
			hide = setInterval("changeH('down')", 2);//开始以每0.002秒调用函数changeH("down"),即每0.002秒向下移动一次
		}
	}
	function changeH(str) {
		var MsgPop = document.getElementById("winpop");
		var popH = parseInt(MsgPop.style.height);
		if (str == "up") { //如果这个参数是UP
			if (popH <= 100) { //如果转化为数值的高度小于等于100
				MsgPop.style.height = (popH + 4).toString() + "px";//高度增加4个象素
			} else {
				clearInterval(show);//否则就取消这个函数调用,意思就是如果高度超过100象度了,就不再增长了
			}
		}
		if (str == "down") {
			if (popH >= 4) { //如果这个参数是down
				MsgPop.style.height = (popH - 4).toString() + "px";//那么窗口的高度减少4个象素
			} else { //否则
				clearInterval(hide); //否则就取消这个函数调用,意思就是如果高度小于4个象度的时候,就不再减了
				MsgPop.style.display = "none"; //因为窗口有边框,所以还是可以看见1~2象素没缩进去,这时候就把DIV隐藏掉
			}
		}
	}
	/*window.onload = function() { //加载
		document.getElementById('winpop').style.height = '0px';//我不知道为什么要初始化这个高度,CSS里不是已经初始化了吗,知道的告诉我一下
		setTimeout("tips_pop()", 800); //3秒后调用tips_pop()这个函数
	}*/
	
	/*$(function(){
		if (get_cookie("popped")==""){
   		var html='今天下午分享 <a style="color:red;" target="_blank" href="http://u.oa.com/train/course.php?id=756">数据魔方平台使用</a>,点击"去看看"查看详情(报名)!';
   		$("#confirmBtn").text('去看看');
   		document.cookie="popped=yes";
   		showMsg(html,"请务必查看帮助文档",looked);
		}
	});*/

	function looked(){
		document.cookie="popped=yes";
		window.location.href="http://u.oa.com/train/course.php?id=756";
	}
</script>

<div id="winpop">
	<div class="title">
		系统提示:<span class="close" onclick="tips_pop()">X</span>
	</div>
	<ul class="con">
		<li><a href="http://u.oa.com/train/course.php?id=756">数据魔方平台使用分享</a></li>
	</ul>
</div>
