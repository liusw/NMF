<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="/tld/privilege" prefix="privilege" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en-us">
<head>
    <%@ include file="/WEB-INF/jsp/common/meta.jsp" %>
    <style type="text/css">
        .well {
            background: #FFFFFF;
            border-radius: 0;
        }

        .block-title {
            border-bottom: 1px solid #C9CCD3;
            height: 40px;
            line-height: 39px;
            margin: -20px -20px 20px;
            padding-left: 20px;
        }

        .block-title .help {
            border-bottom: 1px solid #c9ccd3;
            height: 40px;
            line-height: 39px;
            margin-bottom: 8px;
        }

        .block-title h4 {
            color: #28aee9;
            display: inline-block;
            float: left;
            font-size: 15px;
            font-weight: bold;
            line-height: 40px;
            margin: 0 15px 0 0;
            padding: 0;
        }

        .block-title .item-tabs a {
            display: inline-block;
            float: left;
        }

        .block-title .item-tabs a.active, .block-title .item-tabs a.active:hover {
            background-color: #3389d4;
            box-shadow: 0 0 5px rgba(9, 52, 89, 0.7) inset;
            color: #fff;
            font-weight: bold;
        }

        .block-title .item-tabs a:first-child {
            border-left: 1px solid #ddd;
        }

        .block-title .item-tabs > a {
            border-right: 1px solid #ddd;
        }

        .block-title .item-tabs a {
            padding: 0 12px;
        }

        .block-title .item-tabs, .block-title .item-tabs a {
            display: inline-block;
            float: left;
        }

        #genSqlMain {
            margin-bottom: 40px;
        }

        #fields {
            margin-bottom: 40px;
        }

        .spanTip {
            color: red;
        }

        form .placeholder {
            color: #b20f08;
            font-size: 25px;
        }
    </style>
    <title>hive表管理-数据魔方</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp">
    <jsp:param name="nav" value="sysconfig"/>
</jsp:include>

<jsp:include page="../common/sysconfigMenu.jsp">
    <jsp:param name="nav" value="config"/>
    <jsp:param name="subnav" value="hbaseMeta"/>
</jsp:include>

<div class="main">
    <jsp:include page="/WEB-INF/jsp/common/mainNav.jsp">
        <jsp:param name="actionName" value="hbase表配置管理"/>
    </jsp:include>
    <div class="well">
        <div class="block-title">
            <div class="item-tabs">
                <a tag="#fields" class="item active" href="javascript:;">字段信息</a>
                <privilege:operate url="/#tmp#/createHiveTable"><a tag="#addTable" class="item" href="javascript:;">新增hbase表</a></privilege:operate>
            </div>
        </div>
        <div id="fields">
            <form role="form">
                <div class="form-group col-xs-16">
                    <div class="input-group">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-expanded="false">
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu" role="menu" id="tableMenu"
                                style="max-height:400px;overflow: auto;"></ul>
                        </div>


                        <input type="text" id="hbaseMetaTableName" placeholder="表名" class="form-control">
                        <span onclick="syncXml2Hive()" class="input-group-addon glyphicon glyphicon-refresh"
                              style="cursor: pointer;" title="同步xml注释到hive表"></span>
					      <span class="input-group-btn">
					        <button class="btn btn-default" type="button" onclick="getHBaseMetaInfo()">查询</button>
					      </span>
                    </div>
                </div>
            </form>
            <div style="clear:both;">
                <table id="example" class="table table-striped table-bordered display" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th width="20%">字段名</th>
                        <th width="16%">默认值</th>
                        <th width="16%">字段类型</th>
                        <th width="40%">描述</th>
                        <th width="8%">编辑</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <form role="form">
                <div class="form-group col-xs-16">
                    <div class="input-group">
						<span class="input-group-btn">
							<button class='btn btn-primary btn-xs' type="button" onclick="addColumn()">增加字段</button>
						</span>
                    </div>
                </div>
            </form>
        </div>

        <div id="addTable" class="displayNone">
            <form role="form" id="addForm">
                <h5 class="page-header">表名</h5>
                <div class="form-group">
                    <input type="text" class="form-control" id="newTableName" placeholder="表名">
                </div>
                <h5 class="page-header">表别名</h5>
                <div class="form-group">
                    <input type="text" class="form-control" id="newTableAlias" placeholder="表中文名">
                </div>
                <h5 class="page-header">字段</h5>
                <div id="columnsDiv">
                    <div id="firstField" class="rowDiv">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" flag="fieldName" placeholder="字段名" style="">
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" flag="fieldType">
                                        <option value="string">string</option>
                                        <option value="int">int</option>
                                        <option value="double">double</option>
                                        <option value="bigint">bigint</option>
                                    </select>
                                </div>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" placeholder="默认值" flag="fieldDefault">
                                </div>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" placeholder="中文描述" flag="fieldDescr">
                                </div>
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-default" onclick="addField();">添加</button>
                                    <button type="button" class="btn btn-default" onclick="removeField(this);">删除
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <h5 class="page-header">rowkey配置</h5>
                <div class="form-group">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" value="1" onclick="selectSysPro(this);" id="ifSysPro"> 使用系统配置
                        </label>
                    </div>
                </div>
                <div class="form-group" id="rowKeyPropDiv">
                    <div class="row">
                        <div class="col-sm-2">
                            <label>reverse:</label>
                            <select class="form-control" id="reverse">
                                <option value="true">true</option>
                                <option value="false">false</option>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <label>line_num:</label>
                            <select class="form-control" id="line_num">
                                <option value="true">true</option>
                                <option value="false">false</option>
                            </select>
                        </div>
                        <div class="col-sm-6">
                            <label>multi:</label>
                            <input class="form-control" id="multi" placeholder="multi"/>
                        </div>
                    </div>
                </div>
                <div id="rowkeyDiv">
                    <div id="firstRowkey">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-5">
                                    <input class="form-control" flag="rkFieldName" placeholder="column"/>
                                </div>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" flag="rkFieldLen" placeholder="长度">
                                </div>
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-default" onclick="addRowkeyField();">添加
                                    </button>
                                    <button type="button" class="btn btn-default" onclick="removeField(this);">删除
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <h5 class="page-header"></h5>
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-12">
                            <button type="button" class="btn btn-default" id="createTable" data-loading-text="提交中..."
                                    onclick="creatHbaseTabel();">创建
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/foot.jsp" %>

<!-- Bootstrap core JavaScript navbar-fixed-bottom
   ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="static/js/basic.js"></script>
<script src="static/js/dataTable.js"></script>
<script src="static/js/common.js"></script>
<script src="static/js/hbaseMeta.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //获取所有表名
        var tables = getHbTables(null, true);
        $("#tableMenu").empty();
        $.each(tables, function (index, value) {
            if (value.alias == '' || value.alias == null)value.alias = value.tableName;
            $("#tableMenu").append('<li v="' + value.tableName + '"><a href="javascript:void(0)">' + value.alias + '</a></li>');
        });

        getHBaseMetaInfo();
        initPlaceHolder();
        initItems();
        pressEnter('hbaseMetaTableName', getHBaseMetaInfo);
        initCreateHBaseTable();

        $("#tableMenu li").click(function () {
            $('#hbaseMetaTableName').val($(this).attr('v'));
            getHBaseMetaInfo();
        });
    });
    function addColumn() {
        var html = '<div>' +
                '<div id="firstField" class="rowDiv">' +
                '<div class="form-group">' +
                '<div class="row">' +
                '<div class="col-sm-3">' +
                '<input type="text" id="colName" class="form-control" flag="fieldName" placeholder="字段名" style="">' +
                '</div>' +
                '<div class="col-sm-2">' +
                '<select class="form-control" id="colType" flag="fieldType">' +
                '<option value="string">string</option>' +
                '<option value="int">int</option>' +
                '<option value="double">double</option>' +
                '<option value="bigint">bigint</option>' +
                '</select>' +
                '</div>' +
                '<div class="col-sm-2">' +
                '<input type="text" class="form-control" id="defVal" placeholder="默认值" flag="fieldDefault">' +
                '</div>' +
                '<div class="col-sm-3">' +
                '<input type="text" class="form-control" id="desc" placeholder="中文描述" flag="fieldDescr">' +
                '</div>' +
                '<div class="col-sm-2">' +
                '<button type="button" class="btn btn-default" onclick="add()" ">添加</button>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
                showMsg(html, "增加字段");
    }
    function add(){
        var tableName = $("#hbaseMetaTableName").val().toLowerCase();
        var colName = $('#colName').val();
        var colType = $('#colType').val();
        var defVal = $('#defVal').val();
        $('#desc').val();

        $.getJSON("hbaseMeta/addColumn.htm", {
            'tableName': tableName,
            'fieldName': colName,
            'fieldType': colType,
            'defaultValue':defVal,
            'description':{}
        }, function (data) {
            console.log(data);
            window.location.href = "hbaseMeta/getHbTables.htm?tableName="+tableName;
        });
    }
</script>
</body>
</html>
