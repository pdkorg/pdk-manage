var action = null;
var selectId = null;
var table = null;

function initTable() {
	table = PdkDataTable.init({
		"tableId": "template-table",
		"url": 'coupon/coupon_activity_template/table_data',
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "linkName" },
			{ "data": "statusName" },
			{ "data": "memo" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0, 1]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[2, "asc"]
		]
	});

}

function onAdd() {
	window.location.href = "coupon/coupon_activity_template_detail/ADD?funcActiveCode=COUPON_TMP&canEdit=true";
}

function onEdit() {
	var table = $('#template-table');
	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		selectId = checkeds.eq(0).parents("tr").attr("id");
		window.location.href = "coupon/coupon_activity_template_detail/" + selectId + "?funcActiveCode=COUPON_TMP&canEdit=true";
	}
}

function reloadActivityTableData() {
	table.ajax.url("coupon/coupon_activity_template/table_data").load();
}

function showDetail(activityId) {
	window.location.href = "coupon/coupon_activity_template_detail/" + activityId + "?funcActiveCode=COUPON_TMP&canEdit=false";
}

function del() {
	var table = $('#template-table');

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

			var form = $("#templateDelForm");

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
					Metronic.blockUI({
						target: '#activityDetailPane',
						overlayColor: 'none',
						animate: true
					});

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
						idData.empty();
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					idData.empty();
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

	var table = $('#template-table');

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

			var form = $("#tmpUpdateStatusForm");

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
						idData.empty();
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					idData.empty();
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