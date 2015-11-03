var infoTable;

function initQuickReply() {
	initInfoTable();

	initQuickReplyDetail();
}

function initInfoTable() {
	infoTable = PdkDataTable.init({
		"tableId": "quickReply-table",
		"url": "bd/bd_quick_reply/table_data",
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "info" }
		],
		"columnDefs": [{ // set default column settings
			'orderable': true,
			'targets': [0, 1]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[2, "asc"]
		]

	});

	singleSelect4Table(infoTable);
}

function initQuickReplyDetail() {
	var form = $("#quickReplyDetailForm");

	validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#quickReplyDetailPane',
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
					Metronic.unblockUI('#quickReplyDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						$('#quickReplyDetailDialog').modal('hide');
						alert("保存成功!");
						infoTable.ajax.url("bd/bd_quick_reply/table_data").load();
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
					Metronic.unblockUI('#quickReplyDetailPane');
					alert("保存失败!");
				}
			});
		},
		errorPlacement : function(error, element) {
			element.parent('div').append(error);
		},
		rules:{
			info:{
				required:true,
				rangelength:[0,400],
			},
		},
		messages:{
			info:{
				required:"必填",
				rangelength:"位数不能超过400",
			},
		}
	});

	var dialog = $("#quickReplyDetailDialog");

	dialog.on("hidden.bs.modal", function () {
		$(this).removeData("bs.modal");
		form.find("input").val("");
		validate.resetForm();
		form.find('.form-group').removeClass('has-error');
		form.find("#edit-method").empty();
		action = null;
	});

	dialog.on("show.bs.modal", function () {

		if(action == "edit") {
			Metronic.blockUI({
				target: '#quickReplyDetailPane',
				overlayColor: 'none',
				animate: true
			});
			$.ajax({
				url: "bd/bd_quick_reply/" + selectId,
				data: {},
				dataType: "json",
				async: true,
				type: "GET",
				success: function (data) {
					Metronic.unblockUI('#quickReplyDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						var quickReply = eval(data).data;
						form.find("[name = 'id']").val(quickReply.DT_RowId);
						form.find("[name = 'ts']").val(quickReply.ts);
						form.find("[name = 'info']").val(quickReply.info);
						form.find("[name = 'sort']").val(quickReply.sort);
						form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					Metronic.unblockUI('#quickReplyDetailPane');
					$('#quickReplyDialog').modal('hide');
				}
			});
		} else {
			form.find("[name = 'id']").val(null);
			form.find("[name = 'ts']").val(null);
			form.find("[name = 'info']").val(null);
			form.find("[name = 'sort']").val(null);
		}
	});
}

function quickReplyEdit() {
	var table = $('#quickReply-table');

	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		selectId = checkeds.eq(0).parents("tr").attr("id");
		action = "edit";
		$('#quickReplyDetailDialog').modal('show');
	}
}

function quickReplyDel() {
	var table = $('#quickReply-table');

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

			var form = $("#quickReplyDelForm");

			var idData = $("#id-data");
			idData.empty();
			$(ids).each(function(){
				idData.append("<input type='hidden' name='ids' value='"+this+"'>");
			});
			$(ts).each(function(){
				idData.append("<input type='hidden' name='ts' value='"+getTimeLong(this)+"'>");
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
						infoTable.ajax.url("bd/bd_quick_reply/table_data").load();
					} else {
						var tipMsg = "删除失败!";
						var error = eval(data).errorMsg;
						if(error != null && error != "") {
							tipMsg += "\n" + error;
						}
						alert(tipMsg);
						idData.empty();
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);

				}
			});
		}
	}
}

function saveQuickReply() {
	$("#quickReplyDetailForm").submit();
}

function showInfoRef() {
	infoTable.ajax.url("bd/bd_quick_reply/table_data").load();
	$('#quickReplyDialog').modal('show');
}