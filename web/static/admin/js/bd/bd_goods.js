var action = null;
var selectId = null;
function initGoods() {
	var form = $("#goodsDetailForm");

	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#goodsDetailPane',
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
					Metronic.unblockUI('#goodsDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						$('#goodsDialog').modal('hide');
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
					Metronic.unblockUI('#goodsDetailPane');
					alert("保存失败!");
				}
			});
		},
		errorPlacement: function (error, element) {
			if (element.context.name == 'goodstypeName') {
				element.parent().parent('div').append(error);
			} else {
				element.parent('div').append(error);
			}
		},
		rules:{
			code:{
				required:true
			},
			name:{
				required:true
			},
			goodstypeName: {
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
			goodstypeName: {
				required:"必填"
			}

		}
	});

	var dialog = $("#goodsDialog")

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
				target: '#goodsDetailPane',
				overlayColor: 'none',
				animate: true
			});
			$.ajax({
				url: "bd/bd_goods/" + selectId,
				data: {},
				dataType: "json",
				async: true,
				type: "GET",
				success: function (data) {
					Metronic.unblockUI('#goodsDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						var flowType = eval(data).data;
						form.find("[name = 'id']").val(flowType.id);
						form.find("[name = 'code']").val(flowType.code);
						form.find("[name = 'name']").val(flowType.name);
						form.find("[name = 'goodstypeId']").val(flowType.goodstypeId);
						form.find("[name = 'goodstypeName']").val(flowType.goodstypeName);
						form.find("[name = 'memo']").val(flowType.memo);
						form.find("[name = 'status']").select2("val", flowType.status);
						form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					Metronic.unblockUI('#goodsDetailPane');
					$('#goodsDialog').modal('hide');
				}
			});
		}
	});

}

function initTable() {

	PdkDataTable.init({
		"tableId": "bd-goods-table",
		"url": 'bd/bd_goods/table_data',
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "name" },
			{ "data": "goodstypeName"},
			{ "data": "statusName" },
			{ "data": "memo" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0, 1, 6]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[2, "asc"]
		]
	});

}

function save() {
	$("#goodsDetailForm").submit();
}

function del() {
	var table = $('#bd-goods-table');

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

			var form = $("#goodsDelForm");

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

function edit() {

	var table = $('#bd-goods-table');

	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		selectId = checkeds.eq(0).parents("tr").attr("id");
		action = "edit";
		$('#goodsDialog').modal('show');
	}

}


function updateStatus(status) {

	var statusTxt;

	if(status == 0) {
		statusTxt = "启用";
	}else{
		statusTxt = "禁用";
	}

	var table = $('#bd-goods-table');

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

			var form = $("#goodsUpdateStatusForm");

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
