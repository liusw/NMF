function getHbTables(param, onlyJson) {
    var loop = null;
    var url = "hbaseMeta/getHbTables.htm";
    $.ajax({
        type: "get",
        async: false,
        url: url,
        data: param,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false,
        success: function (data) {
            if (onlyJson) {
                if (data.status == 1) {
                    loop = data.obj;
                }
            } else {
                loop = data;
            }
        }, error: function (err) {
            alert("请求出错!");
            if (console) {
                console.log(err);
            }
        }
    });
    return loop;
}
function getHBaseMetaInfo() {
    var tableName = $("#hbaseMetaTableName").val().toLowerCase();
    var url = 'hbaseMeta/getHBaseMetaInfo.htm';
    $.ajax({
        type: "get",
        url: url,
        data: {"tableName": tableName},
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            var json = eval(data.obj);
            var table = $('#example').DataTable({
                destroy: true,
                searching: false,
                processing: true,
                stateSave: true,
                data: json,
                "tabIndex": 1,
                "columns": [
                    {"data": "colName"},
                    {"data": "colValue"},
                    {"data": "colType"},
                    {"data": "desc"},
                    {"data": null}
                ],
                "oLanguage": commonData.oLanguage,
                "dom": 't<"col-sm-6"l><"col-sm-6"p>',
                "fnRowCallback": function (nRow, aData, iDataIndex) {
                    //alert("<button type='button' class='btn btn-primary btn-xs' onclick='forEditColMeta(\"" + aData.colName + "\",\"" + aData.colValue + "\",\"" + aData.desc + "\"" + ",\"" + aData.colType + "\")'>编辑</button>");
                    $('td:eq(-1)', nRow).html("<button type='button' class='btn btn-primary btn-xs' onclick='forEditColMeta(\"" + aData.colName + "\",\"" + aData.colValue + "\",\"" + aData.desc + "\"" + ",\"" + aData.colType + "\")'>编辑</button>");
                }
            });
        },
        error: function (err) {
            showMsg("请求出错!");
            myLog(err);
        }
    });
}
function forEditColMeta(colName, colValue, desc, colType) {
    var html = '<form class="form-horizontal">'
        + '<div class="form-group">'
        + '<label class="col-xs-3 control-label">字段名:</label>'
        + '<div class="col-xs-6">'
        + '<div class="form-control">' + colName + '</div>'
        + '</div>'
        + '</div>'
        + '<div class="form-group">'
        + '<label class="col-xs-3 control-label">注释:</label>'
        + '<div class="col-xs-6">'
        + '<input type="text" id="comment" class="form-control" value="' + desc + '">'
        + '<input type="text" class="displayNone">'
        + '</div>'
        + '</div>'
        + '<div class="form-group">'
        + '<label class="col-xs-3 control-label">默认值:</label>'
        + '<div class="col-xs-6">'
        + '<input type="text" id="colValue" class="form-control" value="' + colValue + '">'
        + '<input type="text" class="displayNone">'
        + '</div>'
        + '</div>'
        + '</form>';
    var param = {};
    param.colName = colName;
    param.colType = colType;
    param.colValue = colValue;
    showMsg(html, "编辑", editMeta, param);
    setTimeout(function () {
        $("#comment").focus();
    }, 500);
    pressEnter('comment', editMeta, param);
}

function initPlaceHolder() {
    $('[placeholder]').focus(function () {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
            input.val('');
            input.removeClass('placeholder');
        }
    });
}

function editMeta(param) {
    param.comment = $("#comment").val();
    param.tableName = $("#hbaseMetaTableName").val();
    param.colValue = $("#colValue").val();
    $.post('hbaseMeta/updateColumnMeta.htm', param, function (data, textStatus) {
        getHBaseMetaInfo();
        closeMsg();
    }, "json");
}
function initCreateHBaseTable() {
    $("#addForm")[0].reset();
    column = $("#columnDemo").html();//保存添加字段html
    partition = $("#partitionDiv").html();//保存添加字段html
    //initFieldChange("#partitionDiv");
    //initFieldChange("#columnsDiv");
    //$("#tmValue").val("tm");
    //$("#sorted").val("`_uid`");
    //$("#tmDesc").val("时间分区");
    //$("#uidValue").val("`_uid`");
    //$("#uidDesc").val("用户id");
    //$("#clustered").val("`_uid`");
    //$("#createTable").click(function () {
    //    createHiveTabel();
    //});
}

function addField() {
    var field = $("#firstField").html();
    $("#columnsDiv").append(field);
    setRowkeyField();
    filedNamefocusOut();
}

function filedNamefocusOut() {
    $("#columnsDiv input[flag=fieldName] :last").focusout(function () {
        setRowkeyField();
    });
}

function removeField(that) {
    var fieldNum = $(that).parent().parent()
        .parent().parent().children().length;
    if (fieldNum > 1) {
        $(that).parents('.form-group').remove();
    }
}

function addRowkeyField() {
    var tmp = $("#firstRowkey").html();
    $("#rowkeyDiv").append(tmp);
}

//设置rowkey到选项
function setRowkeyField() {
    var options = "";
    $("#fieldsDiv input[flag=fieldName]").each(function () {
        var value = $(this).val();
        if (value) {
            options += "<option value='" + value + "'>" + value + "</option>";
        }
    });

    $("#rowkeyDiv select[flag=rkFieldName]").each(function () {
        var that = $(this);
        var value = that.val();
        that.html(options);
        if (value) {
            that.val(value);
        }
    });

}

//选择系统配置
function selectSysPro(that) {
    if (that.checked == true) {
        $("#rowkeyDiv").fadeOut(300);
        $("#rowKeyPropDiv").fadeOut(300);

    } else {
        $("#rowkeyDiv").fadeIn(300);
        $("#rowKeyPropDiv").fadeIn(300);
    }
}

function creatHbaseTabel() {
    if (isNotAddTableFormValidate()) {
        alert("表单信息不完整");
        return;
    }
    var tableName = $("#newTableName").val();
    var tableAlias = $("#newTableAlias").val();

    var fields = "";
    $("#columnsDiv div[class=form-group]").each(function () {
        var that = $(this);
        var fieldName = that.find("input[flag=fieldName]").val();
        var fieldType = that.find("select[flag=fieldType]").val();
        var fieldDefault = that.find("input[flag=fieldDefault]").val();
        var fieldDescr = that.find("input[flag=fieldDescr]").val();
        if (fieldName) {
            fields += fieldName;
            fields += "," + fieldType;
            fields += "," + fieldDefault;
            fields += "," + fieldDescr + "#";
        }
    });

    var rowkeyFields = '{"fields":{';
    $("#rowkeyDiv div[class=form-group]").each(function () {
        var that = $(this);
        rowkeyFields += '"' + that.find("input[flag=rkFieldName]").val() + '":';
        rowkeyFields += that.find("input[flag=rkFieldLen]").val() + ',';
    });
    rowkeyFields = rowkeyFields.substring(0, rowkeyFields.length - 1);
    rowkeyFields += '},"reverse":' + $("#reverse").val() + ',"line_num":' + $("#line_num").val();

    var multi = $("#multi").val();
    if (multi != null && multi != '') {
        rowkeyFields += ',"multi":' + multi;
    }
    rowkeyFields += ""+'}';
    if (fields.length > 0) {

        $.getJSON("hbaseMeta/addHbaseTable.htm", {
            'tableName': tableName,
            'tableAlias': tableAlias,
            'fields': fields,
            'rowkeyFields': rowkeyFields,
            systemPro: $("#ifSysPro")[0].checked,
        }, function (data) {
            console.log(data);
            window.location.href = "hbaseMeta/getHbTables.htm";
        });
    }
}
function isNotAddTableFormValidate() {
    var isNotValidate = false;
    var tableName = $("#newTableName").val();

    if (tableName == null && tableName == '') {
        $("#newTableName").addClass('placeholder');
        return true;
    }
    $("#rowkeyDiv div[class=form-group]").each(function () {
        var that = $(this);
        var fieldName = that.find("input[flag=fieldName]").val();
        that.find("input[flag=fieldName]").addClass('placeholder');
        return true;
    });
    return isNotValidate;
}
