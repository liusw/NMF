<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
    <title>牌局-数据魔方</title>
    <link rel="stylesheet" type="text/css" href="static/css/bootstrap-multiselect.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
    <jsp:param name="nav" value="data"/>
</jsp:include>

<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
    <jsp:param name="nav" value="businessData"/>
    <jsp:param name="subnav" value="tableData"/>
</jsp:include>

<div class="main2">
    <jsp:include page="/WEB-INF/jsp/common/topTabMenu.jsp">
        <jsp:param name="tabActive" value="gambling"/>
    </jsp:include>
   <jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />

    <div class="main2Body">
        <jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
            <jsp:param name="tab" value="gambling"/>
            <jsp:param name="subnav" value="growthcurve"/>
        </jsp:include>

        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade content-form main-content active in">
                <form class="form-inline" role="form">
                    <div class="form-group ">
                        <label>用户ID</label>
                        <input type="text" id="_uid" class="form-control time" placeholder="用户ID"
                               autocomplete="off">
                    </div>
                    <div class="form-group">
                        <label>&nbsp;&nbsp;显示维度</label>
                        <select id="dimension" name="dimension" class="form-control">
                            <option value="blindmin">小盲</option>
                            <option value="vmoney">台费</option>
                            <option value="gtype">桌子类型</option>
                            <option value="cgcoins">金币变化</option>
                        </select>
                    </div>
                    <div class="form-group">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-default" id="submitBtn" data-loading-text="查询中...">查询
                        </button>
                    </div>
                </form>
                <div class=".container-fluid">
                    <!--Chart-box-->
                    <div class="widget-box">
                        <div class="widget-title">
                            <h5>成长曲线</h5>
                        </div>
                        <div class="widget-content">
                            <div class="row">
                                <div style="width: 100%;height: auto;" id="curveChart"></div>
                            </div>
                        </div>
                    </div>
                    <!--End-Chart-box-->
                </div>
            </div>
            <div id="div_load" style="border:1px solid #99ff00;display:none;"></div>
        </div>
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
<script src="static/Highcharts-4.0.4/js/highcharts.js"></script>
<script src="static/Highcharts-4.0.4/js/modules/exporting.js"></script>
<script type="text/javascript">

    var curveChart;
    var xAxis = [];
    var yAxis = [];
    $(function () {
        var dimensionName = $("#dimension").find("option:selected").text();
        curveChart = $('#curveChart').highcharts({
            loading: {
                labelStyle: {
                    color: 'white'
                },
                style: {
                    backgroundColor: 'gray'
                }
            },
            title: {
                text: dimensionName + " 成长曲线",
                x: -20 //center
            },
            subtitle: {
                text: "",
                x: -20
            },
            xAxis: {
                categories: xAxis,
                labels: {
                    enabled: false
                },
                tickInterval: 10,
                scrollbar: {
                    enabled: true
                },
                tickPixelInterval: 30,
                title: {
                    text: '时间轴'
                }
            },
            yAxis: {
                title: {
                    text: dimensionName
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                name: dimensionName,
                data: yAxis
            }]
        });
        navForm.init({
            'showPlat': true, 'showSid': false, 'sidType': 'MultiSelect', 'showDate': {'isShow': true},
            platChangeCallback: updateNavMultiSid
        });
        $('#submitBtn').click(function () {
            $(this).attr({"disabled": "disabled"});
            loadChart();
        });
    });
    function loadChart() {
        var plat = $("#navPlat").val();
        var dimension = $("#dimension").val();
        var dimensionName = $("#dimension").find("option:selected").text();
        var uid = $('#_uid').val();
        var sTime = navForm.daterange.getStartDate("#navdaterange", "YYYYMMDD");
        var eTime = navForm.daterange.getEndDate("#navdaterange", "YYYYMMDD");
        if (uid == null || uid == '') {
            alert("用户ID不能为空");
            $("#submitBtn").removeAttr("disabled");
            return;
        }
        $('#curveChart').highcharts().series[0].setData([]);
        $('#curveChart').highcharts().redraw();
        var hql = "select _tm ," + dimension + " from user_gambling where  _plat=" + plat + " and _uid = " + uid;
        hql += " and _tm between " + sTime + " and " + eTime;
        $('#curveChart').highcharts().showLoading();
        $.post("data/hbase/query.htm", {"sql": hql, "pageIndex": 1, "pageSize": 1000000000}, function (data, textStatus) {
            xAxis = [];
            yAxis = [];
            if (data && data.obj && data.obj.o) {
                data.obj.o.reverse();
                $.each(data.obj.o, function (key, obj) {
                    yAxis.push(obj[dimension]);
                    var tm = new Date(obj._tm * 1000).format('yyyy-MM-dd');
                    xAxis.push(tm);
                });
            }
            $('#curveChart').highcharts().setTitle({"text": dimensionName + " 成长曲线"});
            $('#curveChart').highcharts().xAxis[0].categories = xAxis;
            $('#curveChart').highcharts().series[0].setData(yAxis);
            $('#curveChart').highcharts().series[0].name = dimensionName;
            $('#curveChart').highcharts().yAxis[0].setTitle({"text": dimensionName});
            $('#curveChart').highcharts().redraw();
        }, "json").error(function () {
        }).complete(function () {
            $("#submitBtn").removeAttr("disabled");
            $('#curveChart').highcharts().hideLoading();
        });
    }
</script>
</body>
</html>