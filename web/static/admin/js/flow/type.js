
var action = null;

var selectId = null;

var dataTable = null;

var deliverComponent = $("#deliverComponent");
var isAutoAssignDeliver = $("input[name='isAutoAssignDeliver']");

function initTable() {

    dataTable = PdkDataTable.init({
        "tableId": "flow-type-table",
        "url": 'flow/flow_types/table_data',
        "columns": [
            { "data": "checked" },
            { "data": "index" },
            { "data": "code" },
            { "data": "name" },
            { "data": "isAutoAssignDeliver" },
            { "data": "deliverName" },
            { "data": "statusName" },
            { "data": "memo" }
        ],

        "columnDefs": [{ // set default column settings
            'orderable': false,
            'targets': [0, 1, 7]
        }, {
            "searchable": false,
            "targets": [0, 1, 4]
        }],
        "order": [
            [2, "asc"]
        ]
    });

}

function initFlowType() {
	var form = $("#flowTypeDetailForm");

	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#flowTypeDetailPane',
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
					Metronic.unblockUI('#flowTypeDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						$('#flowTypeDialog').modal('hide');
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
					Metronic.unblockUI('#flowTypeDetailPane');
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
                maxlength:"最大长度20字符"
			},
			name:{
				required:"必填",
                maxlength:"最大长度50字符"
			},
			status:{
				required: "必填"
			},
            memo:{
                maxlength:"最大长度200"
            }
		}
	});

    var dialog = $("#flowTypeDialog")

    dialog.on("hidden.bs.modal", function () {
		$(this).removeData("bs.modal");
        form.find("input, textarea").val("");
		validate.resetForm();
		form.find('.form-group').removeClass('has-error');
        form.find("#edit-method").empty();
        form.find("[name='status']").select2("val", 0);
        setAutoAssignDeliverValue(false);
        deliverComponent.hide();
        clearDeliverComponentValue();
        action = null;
	});

    dialog.on("show.bs.modal", function () {

        if(action == "edit") {
            Metronic.blockUI({
                target: '#flowTypeDetailPane',
                overlayColor: 'none',
                animate: true
            });
            $.ajax({
                url: "flow/flow_type/" + selectId,
                data: {},
                dataType: "json",
                async: true,
                type: "GET",
                success: function (data) {
                    Metronic.unblockUI('#flowTypeDetailPane');
                    var result = eval(data).result;
                    if (result == "success") {
                        var flowType = eval(data).data;
                        form.find("[name = 'id']").val(flowType.id);
                        form.find("[name = 'ts']").val(flowType.ts);
                        form.find("[name = 'code']").val(flowType.code);
                        form.find("[name = 'name']").val(flowType.name);
                        form.find("[name = 'memo']").val(flowType.memo);
                        form.find("[name = 'status']").select2("val", flowType.status);
                        form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
                        if(flowType.isAutoAssignDeliver && flowType.isAutoAssignDeliver == "Y") {
                            setAutoAssignDeliverValue(true);
                            deliverComponent.show();
                            deliverComponent.find("input[name='deliverId']").val(flowType.deliverId);
                            deliverComponent.find("input[name='deliverName']").val(flowType.deliverName);
                        } else {
                            setAutoAssignDeliverValue(false);
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    Metronic.unblockUI('#flowTypeDetailPane');
                    $('#flowTypeDialog').modal('hide');
                }
            });
        }
    });

    initAutoAssignDeliver();

}


function save() {
	$("#flowTypeDetailForm").submit();
}

function del() {
    var table = $('#flow-type-table');

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

            var form = $("#flowTypeDelForm");

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

    var table = $('#flow-type-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if(checkeds.length <= 0) {
        alert("请选择一行数据！");
    }else {
        selectId = checkeds.eq(0).parents("tr").attr("id");
        action = "edit";
        $('#flowTypeDialog').modal('show');
    }

}

function updateStatus(status) {

    var statusTxt;

    if(status == 0) {
        statusTxt = "启用";
    }else{
        statusTxt = "禁用";
    }

    var table = $('#flow-type-table');

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

            var form = $("#flowTypeUpdateStatusForm");

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

function clearDeliverComponentValue() {
    deliverComponent.find("input[name='deliverId']").val(null);
    deliverComponent.find("input[name='deliverName']").val(null);
}

function setAutoAssignDeliverValue(value) {
    var autoAssignDeliver = $("#autoAssignDeliver");
    autoAssignDeliver.attr("checked", value);
    jQuery.uniform.update(autoAssignDeliver);
    if(value) {
        isAutoAssignDeliver.val('Y');
    } else {
        isAutoAssignDeliver.val('N');
    }
}

function initAutoAssignDeliver() {

    isAutoAssignDeliver.val("N");
    deliverComponent.hide();

    $("#autoAssignDeliver").click(function () {
        if ($(this).is(":checked")) {
            isAutoAssignDeliver.val("Y");
            deliverComponent.show();
        } else {
            isAutoAssignDeliver.val("N");
            deliverComponent.hide();
            clearDeliverComponentValue();
        }
    });
}

function showDeliverDialog() {
    showEmployeeRefDlg('deliverId','deliverName', 'PDKPREDATA0000000003');
}

