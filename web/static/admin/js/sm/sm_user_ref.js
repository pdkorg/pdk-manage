var refUserTable;
var userCallBack;
var qryUserId;
var qryUserName;
function initUserRef() {
	refUserTable = PdkDataTable.init({
		"tableId": "user-ref-table",
		"url": "sm/sm_user/table_data/ref",
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "name" },
			{ "data": "realName" },
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

	singleSelect4Table(refUserTable);
}

function showUserRefDlg(userId, userNameId, callback) {
	userCallBack = callback;

	refUserTable.ajax.url("sm/sm_user/table_data/ref");

	qryUserId = userId;
	qryUserName = userNameId;
	$('#userRefDialog').modal("show");
}

function selectUserRef() {
	var table = $('#user-ref-table');
	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
		return;
	}

	var checkeds = table.find("tbody .checkboxes:checked");
	refSelectId =  checkeds.eq(0).parents("tr").attr("id");
	refSelectName = checkeds.eq(0).parents("tr").find("td:eq(2)").html();

	$('#userRefDialog').modal('hide');
	$("#" + qryUserId).val(refSelectId);
	$("#" + qryUserName).val(refSelectName);
	$("#" + qryUserId).trigger("change");
	$("#" + qryUserName).trigger("change");

	table.find(".checkboxes").attr("checked", false);
	table.find(".checkboxes").uniform();

	if(userCallBack != null) {
		userCallBack();
	}
}