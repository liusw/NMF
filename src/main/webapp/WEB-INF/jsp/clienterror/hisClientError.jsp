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
    <title>客户端异常-数据魔方</title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/top.jsp">
    <jsp:param name="nav" value="data"/>
</jsp:include>
<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
    <jsp:param name="nav" value="clienterror"/>
    <jsp:param name="subnav" value="history"/>
</jsp:include>

<div class="main">
    <jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
        <jsp:param name="history" value="历史客户端异常"/>
    </jsp:include>

    <div class="main2Body">
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade content-form main-content active in">
                <form class="form-inline" role="form">
                    <div class="form-group ">
                        <label>状态</label>
                        <select id="status" name="status" class="form-control multiselect">
                            <option value="">全部</option>
                            <option value="0">未解决</option>
                            <option value="1">已解决</option>
                            <option value="2">处理中</option>
                            <option value="3">已忽略</option>
                            <option value="4">已修复又出现</option>
                        </select>
                    </div>
                    <div class="form-group ">
                        &nbsp;&nbsp;
                        <label>版本</label>
                        <select id="version" name="version" class="form-control multiselect">
                            <option value="">全部</option>
                            <option value="4.0.4">4.0.4</option>
                            <option value="4.0.5">4.0.5</option>
                            <option value="4.0.6">4.0.6</option>
                            <option value="4.0.7">4.0.7</option>
                        </select>
                    </div>
                    <div class="form-group ">
                        &nbsp;&nbsp;
                        <label>Lua版本</label>
                        <select id="version_lua" name="version" class="form-control multiselect">
                            <option value="">全部</option>
                            <option value="4.0.4">4.0.4</option>
                            <option value="4.0.5">4.0.5</option>
                            <option value="4.0.6">4.0.6</option>
                            <option value="4.0.7">4.0.7</option>
                        </select>
                    </div>
                    <div class="form-group ">
                        &nbsp;&nbsp;
                        <label>标题</label>
                        <input type="text" id="title" class="form-control time"  placeholder="title"
                               autocomplete="off">
                    </div>
                    <div class="form-group">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <table id="example" class="table table-striped table-bordered display dtable">
            <thead>
            <tr>
                <th>平台</th>
                <th>站点</th>
                <th>错误摘要</th>
                <th>应用版本</th>
                <th>首次发生</th>
                <th>最近发生</th>
                <th>错误次数</th>
                <th>详情</th>
            </tr>
            </thead>
            <tfoot></tfoot>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/datetimepicke.js"></script>
<script src="static/js/multiselect.js"></script>
<script src="static/js/daterangepicker.js"></script>
<script src="static/js/daterange.by.js"></script>
<script src="static/js/dateUtils.js"></script>
<script src="static/js/jquery.form.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/bootstrap-multiselect.js"></script>
<script type="text/javascript">
    var table, navForm;
    $(function () {
        navForm.init({'showPlat':true,'showSid':true,'sidType':'Select',platChangeCallback:changePlat
        ,sidChangeCallBack:loadData});
        loadData();
        $('#submitBtn').click(function(){
            loadData();
        });
    });


    function changePlat(){
        updateNavMultiSid();
        loadData();
    }

    function loadData() {
        var plat = $("#navPlat").val();
        var sid = $("#navSid").val().split("_")[0];

        var title =$("#title").val();
        var version = $("#version").val();
        var version_lua = $("#version_lua").val();
        var status = $("#status").val();


        var params = {
            plat: plat,sid:sid,title:title,status:status,version:version,version_lua:version_lua
        };

        table = $("#example").DataTable({
            "bFilter": true,
            "pagingType": "full_numbers",
            "bDestroy": true,
            "bProcessing": false,
            "sAjaxSource": "clienterror/getErrorList.htm?params=" + JSON.stringify(params),
            "bServerSide": true,
            "ordering": true,
            "aaSorting": [[0, "desc"]],
            "aoColumnDefs": [{"bSortable": false}],
            "sServerMethod": "POST",
            "columns": [
                {"data": "plat", "orderable": "false"},
                {"data": "sid" , "orderable": "false"},
                {"data": "title", "orderable": "false"},
                {"data": "version", "orderable": "true"},
                {"data": "firstTime", "orderable": "true"},
                {"data": "latestTime", "orderable": "true"},
                {"data": "occurCount", "orderable": "true"},
                {"data": null},
            ],
            "fnRowCallback":function(nRow,aData,iDataIndex){
                $('td:eq(-1)',nRow).html("<button type='button' class='btn btn-primary' data-loading-text='提交中...' onclick='getDetail(\""+aData.descMd5+"\")'>详情</button>");
            } ,
            "oLanguage": commonData.oLanguage,
            "dom": 't<"bottom fl"l><"bottom"p>'
        });
    }
    function getDetail(desc_md5){
        window.location.href = "clienterror/clientErrorDetail.htm?desc_md5="+desc_md5+"&type=0";
    }
</script>
</body>
</html>
