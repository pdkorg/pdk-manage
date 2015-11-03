var action = null;
var selectId = null;
var table = null;

function initTable() {
	table = PdkDataTable.init({
		"tableId": "activity-table",
		"url": 'coupon/coupon_activity/table_data',
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "linkName" },
			{ "data": "autoSendTime" },
			{ "data": "statusName" },
			{ "data": "sendUserCnt" },
			{ "data": "sendTotalMny" },
			{ "data": "memo" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0, 1, 8]
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
	window.location.href = "coupon/coupon_activity_detail/ADD?funcActiveCode=COUPON_ACT&canEdit=true";
}

function onEdit() {
	var table = $('#activity-table');
	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		selectId = checkeds.eq(0).parents("tr").attr("id");
		window.location.href = "coupon/coupon_activity_detail/" + selectId + "?funcActiveCode=COUPON_ACT&canEdit=true";
	}
}

function reloadActivityTableData() {
	table.ajax.url("coupon/coupon_activity/table_data").load();
}

function showDetail(activityId) {
	window.location.href = "coupon/coupon_activity_detail/" + activityId + "?funcActiveCode=COUPON_ACT&canEdit=false";
}

function del() {
	var table = $('#activity-table');

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

			var form = $("#activityDelForm");

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
