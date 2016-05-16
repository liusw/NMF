<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
    <style type="text/css">
        .ucuser .checkbox-inline + .checkbox-inline {
            margin-left: 0;
        }

        .ucuser .checkbox-inline {
            width: 280px;
            height: 20px;
            line-height: 20px;
            overflow: hidden;
        }

        .ucuser > label {
            width: 280px !important;
        }

    </style>
    <title>系统结余-数据魔方</title>
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
        <jsp:param name="tabActive" value="toollog"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />

    <div class="main2Body">
        <form id="process_1" processId="1" processType="3" processName="上传文件流程" class="form-inline" method="post"
              enctype="multipart/form-data">
            <div id="uploadDiv" style="margin-top: 15px;">
                <div class="form-group">
                    <label class="pdl">上传文件:</label>
                </div>
                <div class="form-group">
                    <input type="file" autocomplete="off" name="file" id="file_1">
                </div>
                <button class="btn btn-default upload_btn" type="button">点击上传</button>
                <br><br>
                <input type="hidden" autocomplete="off" name="fileName">
                <input type="hidden" autocomplete="off" name="columns">
                <input type="hidden" value="1" name="getFileColumn">
                <div class="form-group" style="display: none">
                    <label class="pdl">文件列名:</label>
                    <input type="text" autocomplete="off" placeholder="列名称" class="form-control" style="width: 400px;"
                           name="columnNames">
                </div>
            </div>
        </form>
        <hr>
        <form id="process_3" processId="3" processType="2" processName="获取用户信息流程" class="form-inline">
            <div class="form-group">
                <input type="checkbox" id="checkall" style="margin:2px 6px 3px 0;vertical-align:middle;">
                <label>道具信息</label>
                <div class="ucuser" id="ucuser">

                </div>
            </div>
        </form>
        <br>
        <button type="button" class="btn btn-primary" id="submitTask">提交任务</button>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/daterange.by.js"></script>
<script src="static/js/jquery.form.js"></script>

<script type="text/javascript">
    $(function () {
        navForm = navForm.init({'showPlat': true});
        loadUcuser();
        Flow.init();
    });

    var ucuser = [
        {value: "not_used_10", valueName: "紫钻未使用个数"}, {
            value: "used_10",
            valueName: "紫钻使用个数"
        }, {
            value: "left_10",
            valueName: "紫钻剩余次数"
        }, {value: "ltype_10", valueName: "紫钻卡类型"},
        {value: "not_used_12", valueName: "蓝钻未使用个数"}, {value: "used_12", valueName: "蓝钻使用个数"}, {
            value: "left_12",
            valueName: "蓝钻剩余次数"
        }, {value: "ltype_12", valueName: "蓝钻卡类型"},
        {value: "not_used_13", valueName: "红钻未使用个数"}, {value: "used_13", valueName: "红钻使用个数"}, {
            value: "left_13",
            valueName: "红钻剩余次数"
        }, {value: "ltype_13", valueName: "红钻卡类型"},
        {value: "not_used_15", valueName: "黄钻未使用个数"}, {value: "used_15", valueName: "黄钻使用个数"}, {
            value: "left_15",
            valueName: "黄钻剩余次数"
        }, {value: "ltype_15", valueName: "黄钻卡类型"},
        {value: "not_used_25", valueName: "VIP2续卡未使用个数"}, {
            value: "used_25",
            valueName: "VIP2续卡使用个数"
        }, {value: "left_25", valueName: "VIP2续卡剩余次数"}, {value: "ltype_25", valueName: "VIP2续卡卡类型"},
        {value: "not_used_26", valueName: "VIP3续卡未使用个数"}, {
            value: "used_26",
            valueName: "VIP3续卡使用个数"
        }, {value: "left_26", valueName: "VIP3续卡剩余次数"}, {value: "ltype_26", valueName: "VIP3续卡卡类型"},
        {value: "not_used_33", valueName: "救济卡未使用个数"}, {value: "used_33", valueName: "救济卡使用个数"}, {
            value: "left_33",
            valueName: "救济卡剩余次数"
        }, {value: "ltype_33", valueName: "救济卡卡类型"},
        {value: "not_used_37", valueName: "踢人卡未使用个数"}, {value: "used_37", valueName: "踢人卡使用个数"}, {
            value: "left_37",
            valueName: "踢人卡剩余次数"
        }, {value: "ltype_37", valueName: "踢人卡卡类型"},
        {value: "not_used_50", valueName: "恭喜发财未使用个数"}, {value: "used_50", valueName: "恭喜发财使用个数"}, {
            value: "left_50",
            valueName: "恭喜发财剩余次数"
        }, {value: "ltype_50", valueName: "恭喜发财卡类型"},
        {value: "not_used_51", valueName: "年年有余未使用个数"}, {value: "used_51", valueName: "年年有余使用个数"}, {
            value: "left_51",
            valueName: "年年有余剩余次数"
        }, {value: "ltype_51", valueName: "年年有余卡类型"},
        {value: "not_used_52", valueName: "新年快乐未使用个数"}, {value: "used_52", valueName: "新年快乐使用个数"}, {
            value: "left_52",
            valueName: "新年快乐剩余次数"
        }, {value: "ltype_52", valueName: "新年快乐卡类型"},
        {value: "not_used_60", valueName: "乐透大礼包未使用个数"}, {value: "used_60", valueName: "乐透大礼包使用个数"}, {
            value: "left_60",
            valueName: "乐透大礼包剩余次数"
        }, {value: "ltype_60", valueName: "乐透大礼包卡类型"}
    ];

    var Flow = {

        init: function () {
            var _this = this;
            $("#process_1 .upload_btn").click(function () {
                _this.upload();
            });
            $("#submitTask").unbind('click').click(function () {
                Flow.submitFlow();
            });
            _this.formatDate();
        },
        formatDate: function () {
            Date.prototype.Format = function (fmt) { //author: meizz
                var o = {
                    "M+": this.getMonth() + 1, //月份
                    "d+": this.getDate(), //日
                    "h+": this.getHours(), //小时
                    "m+": this.getMinutes(), //分
                    "s+": this.getSeconds(), //秒
                    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                    "S": this.getMilliseconds() //毫秒
                };
                if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o)
                    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                return fmt;
            }
        },
        upload: function () {
            var _this = this;
            var fileVal = $("#file_1").val();
            if (fileVal != null && fileVal.length > 0) {
                var fileExt = fileVal.substring(fileVal.lastIndexOf('.') + 1);
                if (fileExt != 'txt' && fileExt != 'csv') {
                    alert("目录只允许上传.txt和.csv文件");
                    return;
                }
                $(function () {
                    var options = {
                        url: "upload",
                        type: "POST",
                        dataType: "json",
                        success: function (msg) {
                            if (msg && msg.ok == 1) {
                                $("#process_1").find("[name=fileName]").val(msg.other.fileName);
                                $("#process_1").find("[name=columns]").val(msg.other.fileColumn);
                                $("#process_1").find("[name=columnNames]").val(msg.other.fileColumn);
                                $("#process_1").find("[name=upload-sucess]").remove();
                                $("#process_1 .upload_btn").before('<span class="glyphicon glyphicon-ok" name="upload-sucess" style="color: #3c763d" title="上传成功">上传成功</span>&nbsp;&nbsp;&nbsp;');
                            } else {
                                alert(msg);
                            }
                        },
                        error: function (msg) {
                            alert(msg);
                        }
                    };
                    $("#process_1").ajaxSubmit(options);
                    return false;
                });
            } else {
                alert("请选择要上传的文件");
            }
        },
        getSelectColumns: function () {
            var data = {}, _this = this;
            if ($("#ucuser").find("[name=items]:checked").length > 0) {
                var first = true, column = '', columnName = '';
                $("#ucuser").find("[name=items]").each(function (index, data) {
                    if ($(this).is(":checked")) {
                        var _column = $(this).val(), _columnName = $(this).attr("valuename") ? $(this).attr("valuename") : $(this).val();
                        //格式化
                        column += (first ? '' : ',') + _column;
                        columnName += (first ? '' : ',') + _columnName;
                        first = false;
                    }
                });
                data.selectTitles = columnName;
                data.selectValues = column;
            }
            return data;
        },
        submitFlow: function () {

            var _this = this, plat = $("#navPlat").val(), svid = $("#navPlat").find("option:selected").attr('svid');
            var taskInfo = {
                taskName: "道具日志|" + new Date().Format("yyyy-MM-dd"),
                plat: plat,
                svid: svid, email: $("#email").val()
            };
            taskInfo.outputColumn = '';
            taskInfo.outputLink = '';
            taskInfo.outputType = 0;
            var processList = [];
            //上传文件
            if (isEmpty($("#process_1").find("[name=file]").val())) {
                alert("请点击上传文件按钮,并上传成功后再提交任务!");
                return false;
            }
            var data = _this.getSelectColumns(3);
            if (isEmpty(data.selectTitles)) {
                alert("文件内容对应的列别名不能为空");
                return false;
            }

            var processInfo1 = {processId: 1, processType: 3};
            processInfo1.fileName = $("#process_1").find("[name=fileName]").val();
            processInfo1.columnNames = "_uid";
            processInfo1.columns = "_uid";
            processList.push(processInfo1);


            var processInfo = {processId: 2, processType: 2};
            processInfo.tableName = "user_toollog";
            processInfo.plat = plat;
            processInfo.columns = data.selectValues;
            processInfo.columnNames = data.selectTitles;
            processInfo.dependOn = '1';
            processInfo.linkParam = "_uid#0#_uid";
            processList.push(processInfo);

            taskInfo.processList = processList;
            if (taskInfo.processList.length <= 0) {
                alert("至少要添加一个导出流程才能提交");
                return;
            }
            $.post("task/process/addLinkTask.htm", {taskInfo: JSON.stringify(taskInfo)}, function (data, textStatus) {
                if (data.ok == 0) {
                    alert(data.msg);
                } else {
                    alert("添加成功!");
                }
            }, "json");
        }
    }
    //用户信息的全选与取消
    function userCheckAll() {
        $("#checkall").click(
                function () {
                    if (this.checked) {
                        $("input[name='items']").each(function () {
                            this.checked = true;
                        });
                    } else {
                        $("input[name='items']").each(function () {
                            this.checked = false;
                        });
                    }
                }
        );
    }
    function loadUcuser() {
        if (ucuser) {
            var html = '<label class="checkbox-inline" title="_uid">' +
                    '<input type="checkbox" checked="checked" valuename="_uid" value="_uid" name="items">_uid</label>';
            $("#ucuser").append(html).append("<br>");

            $(ucuser).each(function (index) {
                html = '<label class="checkbox-inline" title="' + this.valueName + '(' + this.value + ')">' +
                        '<input type="checkbox" valuename="' + this.valueName + '" value="' + this.value + '" name="items">' + this.valueName + "(" + this.value + ")" +
                        '</label>';
                $("#ucuser").append(html).append((index + 1) % 4 == 0 ? "<br>" : "");
            });
        };
        userCheckAll();
        $('[data-toggle="tooltip"]').tooltip();
    }

</script>
</body>
</html>
