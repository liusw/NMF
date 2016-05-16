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
    <title>美女荷官-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
    <jsp:param name="nav" value="data"/>
</jsp:include>
<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
    <jsp:param name="nav" value="businessData"/>
    <jsp:param name="subnav" value="heguan"/>
</jsp:include>

<div class="main">
    <jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
        <jsp:param name="actionName" value=" 美女荷官"/>
    </jsp:include>

    <div class="main2Body">
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade content-form main-content active in">
                <form class="form-inline" role="form">
                    <div class="form-group ">
                        <label>用户ID</label>
                        <input type="text" id="uid" class="form-control time" placeholder="用户ID"
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
                <th>日期</th>
                <th>荷官小费</th>
                <th>荷官台费</th>
                <th>荷官总收入</th>
                <th>系统小费</th>
                <th>系统台费</th>
                <th>系统总收入</th>
                <th>荷官开房时段</th>
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
<script src="static/js/common.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/dateUtils.js"></script>
<script src="static/js/daterangepicker.js"></script>
<script src="static/js/daterange.by.js"></script>
<script type="text/javascript">
    var table, navForm;
    $(function () {
        var std = new Date();
        navForm = navForm.init({
            'showPlat': true,
            'showDate': {'isShow': true, applyClickCallBack: loadData},
            platChangeCallback: loadData
        });

        $('#submitBtn').click(function () {
            loadData();
        });
    });

    function loadData() {

        var uid = $("#uid").val();
        if(isEmpty(uid)){
            return;
        }

        var start = navForm.daterange.getStartDate("#navdaterange", "YYYYMMDD");
        var end = navForm.daterange.getEndDate("#navdaterange", "YYYYMMDD");
        var plat = $("#navPlat").val();

        var params = {
            id: "10000001|10000002", "dataType": "dataTable",
            args: {
                plat: plat, etm: end,uid:uid,stm: start, table: "d_heguan_room"
            }
        };

        table = $("#example").DataTable({
            "bFilter": true,
            "pagingType": "full_numbers",
            "bDestroy": true,
            "bProcessing": false,
            "sAjaxSource": "data/common/mysqlQuery.htm?params=" + JSON.stringify(params),
            "bServerSide": true,
            "ordering": false,
            "aaSorting": [[0, "desc"]],
            "aoColumnDefs": [{"bSortable": false}],
            "sServerMethod": "POST",
            "columns": [
                {"data": "tm"},
                {"data": "hgxf"},
                {"data": "hgtf"},
                {"data": "hgsum"},
                {"data": "xtxf"},
                {"data": "xttf"},
                {"data": "xtsum"},
                {"data": "timerange"}
            ],
            "oLanguage": commonData.oLanguage,
            "dom": 't<"bottom fl"l><"bottom"p>'
        });
    }
    ;
</script>
</body>
</html>
