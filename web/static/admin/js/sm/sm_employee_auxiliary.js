var auxiliaryTable;
function pointToDetail() {
	window.location.href = "sm/sm_employee_detail/" + $("#employeeId").val() + "?funcActiveCode=USER&canEdit=false";
}

function initAuxiliaryTable() {
	auxiliaryTable = PdkDataTable.init({
		"tableId": "auxiliary-role-table",
		"url": 'sm/sm_employee_auxiliary/table_data/' + $("#employeeId").val(),
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "name" },
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

function addAuxiliaryTable(roleIds) {
	var form = $("#auxiliaryForm");
	var idData = $("#role-datas");
	idData.empty();

	idData.append("<input type='hidden' name='employeeId' value='" + $("#employeeId").val() + "' >");
	$(roleIds).each(function(){
		idData.append("<input type='hidden' name='roleIds' value='"+this+"'>");
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
				alert("保存成功");
				auxiliaryTable.ajax.url("sm/sm_employee_auxiliary/table_data/" + $("#employeeId").val()).load();
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
			idData.empty();
		}
	});

}

function addAuxiliary() {
	showRoleRefDlg($("#employeeId").val(), addAuxiliaryTable);
}

function delAuxiliary() {
	var table = $('#auxiliary-role-table');
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

			var form = $("#auxiliaryDelForm");

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