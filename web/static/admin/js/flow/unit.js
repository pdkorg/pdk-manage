
var action = null;

var selectId = null;

var dataTable = null;

function initTable() {

    dataTable = PdkDataTable.init({
		"tableId": "flow-unit-table",
		"url": 'flow/flow_units/table_data',
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "name" },
            { "data": "orderStatusName" },
            { "data": "flowActionCodeName" },
			{ "data": "statusName" },
			{ "data": "memo" }
		],

        "autoWidth":false,

		"columnDefs": [{
			'orderable': false,
			'targets': [0, 1, 7]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[2, "asc"]
		]
	});
}


function initflowUnit() {
    var form = $("#flowUnitDetailForm");

    var validate = form.validate({
        submitHandler: function(form) {
            Metronic.blockUI({
                target: '#flowUnitDetailPane',
                overlayColor: 'none',
                animate: true
            });

            $.ajax({
                url: $(form).attr("action"),
                data: $(form).serializeArray(),
                dataType: "json",
                async: true,
                type: "POST",
                success: function (data) {
                    Metronic.unblockUI('#flowUnitDetailPane');
                    var result = eval(data).result;
                    if (result == "success") {
                        $('#flowUnitDialog').modal('hide');
                        alert("保存成功!");
                        dataTable.draw();
                    } else {
                        var tipMsg = "保存失败!";
                        var error = eval(data).errorMsg;
                        if(error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    Metronic.unblockUI('#flowUnitDetailPane');
                    alert("保存失败!");
                }
            });
        },
        rules:{
            code:{
                required:true,
                maxlength:20
            },
            name:{
                required:true,
                maxlength:50
            },
            status:{
                required:true
            },
            memo:{
                maxlength:200
            }
        },
        messages:{
            code:{
                required:"必填",
                maxlength:"最大长度20"
            },
            name:{
                required:"必填",
                maxlength:"最大长度50"
            },
            status:{
                required: "必填"
            },
            memo:{
                maxlength:"最大长度200"
            }
        }
    });

    var dialog = $("#flowUnitDialog");

    dialog.on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
        form.find("input, textarea").val("");
        validate.resetForm();
        form.find('.form-group').removeClass('has-error');
        form.find("#edit-method").empty();
        form.find("[name='status']").select2("val", 0);
        form.find("[name='orderStatus']").select2("val", -1);
        form.find("[name='flowActionCode']").select2("val", -1);
        action = null;
    });

    dialog.on("show.bs.modal", function () {

        if(action == "edit") {
            Metronic.blockUI({
                target: '#flowUnitDetailPane',
                overlayColor: 'none',
                animate: true
            });
            $.ajax({
                url: "flow/flow_unit/" + selectId + "?r=" + Math.random(),
                data: {},
                dataType: "json",
                async: true,
                type: "GET",
                success: function (data) {
                    Metronic.unblockUI('#flowUnitDetailPane');
                    var result = eval(data).result;
                    if (result == "success") {
                        var flowUnit = eval(data).data;
                        form.find("[name = 'id']").val(flowUnit.id);
                        form.find("[name = 'ts']").val(flowUnit.ts);
                        form.find("[name = 'code']").val(flowUnit.code);
                        form.find("[name = 'name']").val(flowUnit.name);
                        form.find("[name = 'orderStatus']").select2('val', flowUnit.orderStatus == null ? -1 : flowUnit.orderStatus);
                        form.find("[name = 'flowActionCode']").select2('val', flowUnit.flowActionCode == null ? -1 : flowUnit.flowActionCode);
                        form.find("[name = 'memo']").val(flowUnit.memo);
                        form.find("[name = 'status']").select2("val", flowUnit.status);
                        form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    Metronic.unblockUI('#flowUnitDetailPane');
                    $('#flowUnitDialog').modal('hide');
                }
            });
        }
    });

}


function save() {
    $("#flowUnitDetailForm").submit();
}

function del() {
    var table = $('#flow-unit-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if(checkeds.length <= 0) {
        alert("请选择一行数据！");
    }else {
        var ids = [];
        var ts = [];
        $(checkeds).each(function() {
            ids.push($(this).parents("tr").attr("id"));
            ts.push($(this).parents("tr").attr("ts"))
        });
        if(window.confirm("确定要删除"+ids.length+"条数据吗？")){

            var form = $("#flowUnitDelForm");

            var idData = $("#id-data").empty();

            $(ids).each(function(){
                idData.append("<input type='hidden' name='ids' value='"+this+"'>");
            });
            $(ts).each(function(){
                idData.append("<input type='hidden' name='ts' value='"+this+"'>");
            });

            $.ajax({
                url: $(form).attr("action"),
                data: $(form).serializeArray(),
                dataType: "json",
                async: true,
                type: "POST",
                success: function (data) {
                    var result = eval(data).result;
                    if (result == "success") {
                        alert("删除成功");
                        dataTable.draw();
                    } else {
                        var tipMsg = "删除失败!";
                        var error = eval(data).errorMsg;
                        if(error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }
}

function edit() {

    var table = $('#flow-unit-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if(checkeds.length <= 0) {
        alert("请选择一行数据！");
    }else {
        selectId = checkeds.eq(0).parents("tr").attr("id");
        action = "edit";
        $('#flowUnitDialog').modal('show');
    }

}


function updateStatus(status) {

    var statusTxt;

    if(status == 0) {
        statusTxt = "启用";
    }else{
        statusTxt = "禁用";
    }

    var table = $('#flow-unit-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if(checkeds.length <= 0) {
        alert("请选择一行数据！");
    }else {
        var ids = [];
        var ts = [];
        $(checkeds).each(function() {
            ids.push($(this).parents("tr").attr("id"));
            ts.push($(this).parents("tr").attr("ts"))
        });
        if(window.confirm("确定要"+ statusTxt + "这" + ids.length+"条数据吗？")){

            var form = $("#flowUnitUpdateStatusForm");

            var idData = $("#status-id-data").empty();

            $(ids).each(function(){
                idData.append("<input type='hidden' name='ids' value='"+this+"'>");
            });
            $(ts).each(function(){
                idData.append("<input type='hidden' name='ts' value='"+this+"'>");
            });

            $.ajax({
                url: $(form).attr("action") + "/" + status,
                data: $(form).serializeArray(),
                dataType: "json",
                async: true,
                type: "POST",
                success: function (data) {
                    var result = eval(data).result;
                    if (result == "success") {
                        alert(statusTxt + "成功");
                        dataTable.draw();
                    } else {
                        var tipMsg = statusTxt + "失败!";
                        var error = eval(data).errorMsg;
                        if(error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }
}

function enable() {
    updateStatus(0);
}


function disable() {
    updateStatus(1);
}

function tableRefresh() {
    dataTable.draw();
}