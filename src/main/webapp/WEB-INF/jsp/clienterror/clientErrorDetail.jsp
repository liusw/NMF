<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
    <title>客户端异常详情-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
    <jsp:param name="nav" value="data"/>
</jsp:include>
<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
    <jsp:param name="nav" value="clienterror"/>
    <jsp:param name="subnav" value="stastic"/>
</jsp:include>
<script type="text/javascript" language="javascript">
    function autoHeight(id){
        var iframe = document.getElementById(id);//这里括号内的"runtime"其实就是上面的ID，要改成自己上面填写的ID。
        if(iframe.Document){//ie自有属性
            iframe.style.height = iframe.Document.documentElement.scrollHeight+20;
        }else if(iframe.contentDocument){//ie,firefox,chrome,opera,safari
            iframe.height = iframe.contentDocument.body.offsetHeight+20;
        }
    }
</script>
<div class="main">
    <jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
        <jsp:param name="actionNames" value="" />
    </jsp:include>

    <div class=".container-fluid">
        <div class="widget-box">
            <div class="widget-title">
               <h5>错误详情</h5>
               <h5><a href="javascript:history.go(-1);">返回</a></h5>
            </div>
            <div class="widget-content nopadding" style="width: 200px;">
                <select id="staus" name="staus" class="form-control multiselect">
                    <option value="0" <c:if test="${clientError.status == 0}">selected="selected"</c:if>>未解决</option>
                    <option value="1" <c:if test="${clientError.status == 1}">selected="selected"</c:if>>已解决</option>
                    <option value="2" <c:if test="${clientError.status == 2}">selected="selected"</c:if>>处理中</option>
                    <option value="3" <c:if test="${clientError.status == 3}">selected="selected"</c:if>>已忽略</option>
                    <option value="4" <c:if test="${clientError.status == 4}">selected="selected"</c:if>>已修复又出现</option>
                </select>
            </div>
            <div class="widget-content nopadding">
                <form class="table table-bordered">
                    <div style="padding: 5px;"><textarea  style="width:80%; height: 200px;">${clientError.desc}</textarea></div>
                </form>
            </div>
        </div>
        <div class="widget-box">
            <div class="widget-title">
                <h5>基本信息</h5>
            </div>
            <div class="widget-content nopadding">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <td>首次发生时间</td>
                        <td>最近发生时间</td>
                        <td>发生次数</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><fmt:formatDate value="${clientError.firstTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
                        <td><fmt:formatDate value="${clientError.latestTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
                        <td>${clientError.occurCount}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <!--Chart-box-->
        <div class="widget-box">
            <div class="widget-title">
                <h5>终端情况</h5>
            </div>
            <div class="widget-content nopadding">
                <table id="example" class="table table-bordered">
                    <thead>
                        <tr>
                            <th>机型</th>
                            <th>操作系统</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>

</div>

<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/dateUtils.js"></script>
<script src="static/js/daterangepicker.js"></script>
<script src="static/js/daterange.by.js"></script>
<script type="text/javascript">
    var table, navForm;
    $(function () {
        loadterminalInfo();
        $('#staus').change(function(){
            $.ajax({
                url:"clienterror/changeStatus.htm",
                async: false,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data:{
                    type :"${clientError.tableType}",
                    desc_md5 :"${clientError.descMd5}",
                    status:$('#staus').val()
                },
                success:function(data){
                    alert("修改状态成功");
                },
                error:function(data){
                    alert(data.msg);
                }
            });
        });
    });
    function loadterminalInfo() {
        table = $("#example").DataTable({
            "bFilter": true,
            "pagingType": "full_numbers",
            "bDestroy": true,
            "bProcessing": false,
            "sAjaxSource": "clienterror/errorTerminalList.htm?desc_md5="+"${clientError.descMd5}"+"&type=${clientError.tableType}",
            "bServerSide": true,
            "ordering": false,
            "aaSorting": [[0, "desc"]],
            "aoColumnDefs": [{"bSortable": false}],
            "sServerMethod": "POST",
            "columns": [
                {"data": "mobile_type"},
                {"data": "os"}
            ],
            "oLanguage": commonData.oLanguage,
            "dom": 't<"bottom fl"l><"bottom"p>'
        });
    }
</script>
</body>
</html>
