var refRoleTable;
var roleCallBack;
function initRoleRef() {
	refRoleTable = PdkDataTable.init({
		"tableId": "role-ref-table",
		"url": "sm/sm_role/table_data/auxiliary_ref/-1",
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

function showRoleRefDlg(employeeId, callback) {
	roleCallBack = callback;
	refRoleTable.ajax.url("sm/sm_role/table_data/auxiliary_ref/" + employeeId).load(function() {
		$('#roleRefDialog').modal("show");
	});

}

function selectRoleRef() {
	var table = $('#role-ref-table');
	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
		return;
	}

	var checkeds = table.find("tbody .checkboxes:checked");
	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		var ids = [];
		$(checkeds).each(function() {
			ids.push($(this).parents("tr").attr("id"));
		});
		if(window.confirm("确定要增加这"+ids.length+"个兼职吗？")){
			if(roleCallBack != null) {
				roleCallBack(ids);
			}
			$('#roleRefDialog').modal("hide");
		}
	}

}