var action = null;
var selectId = null;
function initTable() {

	PdkDataTable.init({
		"tableId": "bd-unit-table",
		"url": 'bd/bd_unit/table_data',
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "name" },
			{ "data": "statusName" },
			{ "data": "memo" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0, 1, 5]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[2, "asc"]
		]
	});

	}


function initBdUnit() {
	var form = $("#addForm");



	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#bdUnitDetailPane',
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
					Metronic.unblockUI('#bdUnitDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						$('#unitDialog').modal('hide');
						alert("保存成功!");
						window.location.reload();
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
					Metronic.unblockUI('#bdUnitDetailPane');
					alert("保存失败!");
				}
			});
		},
		rules:{
			code:{
				required:true
			},
			name:{
				required:true
			},
			status:{
				required:true
			}
		},
		messages:{
			code:{
				required:"必填",
				maxlength:"请不要超过20位"
			},
			name:{
				required:"必填",
			maxlength:"请不要超过50位"
			},
			status:{
				required: "必填"
			}
		}
	});

	var dialog = $("#unitDialog");
	dialog.on("hidden.bs.modal", function () {
		$(this).removeData("bs.modal");
		form.find("input").val("");
		validate.resetForm();
		form.find('.form-group').removeClass('has-error');
		form.find("#edit-method").empty();
		action = null;

		$("#memo").val("");
		$("#status").select2("val", 0);
	});



	dialog.on("show.bs.modal", function () {

		if(action == "edit") {
			Metronic.blockUI({
				target: '#bdUnitDetailPane',
				overlayColor: 'none',
				animate: true
			});
			$.ajax({
				url: "bd/bd_unit/" + selectId,
				data: {},
				dataType: "json",
				async: true,
				type: "GET",
				success: function (data) {
					Metronic.unblockUI('#bdUnitDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						var unit = eval(data).data;
						form.find("[name = 'id']").val(unit.id);
						form.find("[name = 'ts']").val(unit.ts);
						form.find("[name = 'code']").val(unit.code);
						form.find("[name = 'name']").val(unit.name);
						form.find("[name = 'memo']").val(unit.memo);
						form.find("[name = 'status']").select2("val", unit.status);
						form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'>");
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					Metronic.unblockUI('#bdUnitDetailPane');
					$('#unitDialog').modal('hide');
				}
			});
		}
	});
}

function onAdd() {
	$("#addForm").submit();
}


function onDelete() {
	var table = $('#bd-unit-table');

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

			var form = $("#BdUnitDelForm");

			var idData = $("#id-data");
			idData.empty();

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
						window.location.reload();
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
function updateStatus(status) {

	var statusTxt;

	if(status == 0) {
		statusTxt = "启用";
	}else{
		statusTxt = "禁用";
	}

	var table = $('#bd-unit-table');

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

			var form = $("#shopUpdateStatusForm");

			var idData = $("#status-id-data");
			idData.empty();

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
						window.location.reload();
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


