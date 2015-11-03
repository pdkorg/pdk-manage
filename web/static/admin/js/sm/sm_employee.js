var table = null;
var validate = null;

function initTable() {
	table = PdkDataTable.init({
		"tableId": "employee-table",
		"url": "sm/sm_employee/table_data",
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "linkName" },
			{ "data": "statusName" },
			{ "data": "orgName" },
			{ "data": "roleName" },
			{ "data": "positionName" },
			{ "data": "sexName" },
			{ "data": "idCard" },
			{ "data": "phone" },
			{ "data": "memo" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0, 1, 11]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[2, "asc"]
		]

	});
}

function del() {
	var table = $('#employee-table');

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

			var form = $("#employeeDelForm");

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
					alert(textStatus);

				}
			});
		}
	}
}

function onAdd() {
	window.location.href = "sm/sm_employee_detail/ADD?funcActiveCode=USER&canEdit=true";
}

function onDetail(canEdit) {
	var table = $('#employee-table');

	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		var selectId = checkeds.eq(0).parents("tr").attr("id");
		window.location.href = "sm/sm_employee_detail/" + selectId + "?funcActiveCode=USER&canEdit="+canEdit;
	}
}

function updateStatus(status) {

	var statusTxt;

	if(status == 0) {
		statusTxt = "启用";
	}else{
		statusTxt = "禁用";
	}

	var table = $('#employee-table');

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

			var form = $("#employeeUpdateStatusForm");

			var idData = $("#status-id-data");
			idData.empty();
			$(ids).each(function(){
				idData.append("<input type='hidden' name='ids' value='"+this+"'>");
			});
			$(ts).each(function(){
				idData.append("<input type='hidden' name='ts' value='"+getTimeLong(this)+"'>");
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

function initSelect2() {
	initRole("qrySelRole", false, true);
}

function initRole(selName, isStatusEnable, hasDefault) {
	$.ajax({
		url:"sm/sm_role/" + (isStatusEnable ? "status" : "all"),
		dataType:"json",
		success:function(datas){
			var roleSelect = $("select[name=" + selName + "]").empty();
			var option = "";
			if ( eval(datas).result == "success" ) {
				var roleUnits = eval(datas).data;
				$(roleUnits).each(function(){
					option += "<option value='" + this.id + "'>" + this.code + " " + this.name + "</option>";
				});

			}
			roleSelect.html(option).select2(
				{
					minimumResultsForSearch: -1,
					formatNoMatches:function(){
						return "未找到匹配的角色";
					}
				});
		}
	});
}

function clearQueryArg() {
	$("#qryName").val(null);
	$("#qryCode").val(null);
	$("#qrySelRole").select2("val", null);
	$("#qryOrgId").val(null);
	$("#qryOrgName").val(null);
}

function qryEmployee() {
	if (table == null) {
		initTable();
	} else {
		var roleIds = $("#qrySelRole").select2("val");
		var ids = "";
		for ( var i = 0; i < roleIds.length; i++ ) {
			ids += ";" + roleIds[i];
		}

		if  ( ids.length > 0 ) {
			ids = ids.substring(1);
		}

		var employeeName = encodeURIComponent($("#qryName").val());
		table.ajax.url( "sm/sm_employee/table_data?qryCode=" + $("#qryCode").val() + "&qryName=" + employeeName + "&qryRoleIds=" + ids + "&qryOrgId=" + $("#qryOrgId").val() ).load();
	}
}

function reloadTableData() {
	table.ajax.url("sm/sm_employee/table_data").load();
}

function clearRef(orgId, orgName) {
	$("#" + orgId).val(null);
	$("#" + orgName).val(null);
}

function showDetail(employeeId) {
	window.location.href = "sm/sm_employee_detail/" + employeeId + "?funcActiveCode=USER&canEdit=false";
}