var refEmployeeTable;
var employeeCallBack;
var qryEmployeeId;
var qryEmployeeName;
function initEmployeeRef() {
	refEmployeeTable = PdkDataTable.init({
		"tableId": "employee-ref-table",
		"url": "sm/sm_employee/table_data/position/-1",
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "name" },
			{ "data": "orgName" },
			{ "data": "sexName" },
			{ "data": "phone" }
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

	singleSelect4Table(refEmployeeTable);
}

function showEmployeeRefDlg(employeeId, employeeNameId, positionId, callback) {
	employeeCallBack = callback;

	refEmployeeTable.ajax.url("sm/sm_employee/table_data/position/" + positionId).load();

	qryEmployeeId = employeeId;
	qryEmployeeName = employeeNameId;
	$('#employeeRefDialog').modal("show");
}

function selectEmployeeRef() {
	var table = $('#employee-ref-table');
	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
		return;
	}

	var checkeds = table.find("tbody .checkboxes:checked");
	refSelectId =  checkeds.eq(0).parents("tr").attr("id");
	refSelectName = checkeds.eq(0).parents("tr").find("td:eq(3)").html();

	$('#employeeRefDialog').modal('hide');
	$("#" + qryEmployeeId).val(refSelectId);
	$("#" + qryEmployeeName).val(refSelectName);
	$("#" + qryEmployeeId).trigger("change");
	$("#" + qryEmployeeName).trigger("change");

	if(employeeCallBack != null) {
		employeeCallBack();
	}
}