var _fromDate = null;
var _toDate = null;
var _fromTableDate = null;
var _toTableDate = null;
var currEditRow = null;
var currRuleEditRow = null;
var table = $('#coupon-table');
var ruleTable = $('#rule-table');
var sendUserTable;
var flowTypeObj = null;
var flowTypeConvert = {};

var connTypeConvert = ["和", "或"];
var optTypeConvert = ["大于", "大于等于", "等于", "小于等于", "小于"];
var ruleTypeConvert = ["消费次数", "消费金额", "用户首关注日期", "下单日期", "消费类型", "指定用户"];

var delIds = [];
var delTss = [];

var delRuleIds = [];
var delRuleTss = [];

function initTypeList(flowType) {
	flowTypeObj = flowType;
	for (var idx=0;idx< flowType.length;idx++) {
		flowTypeConvert[flowType[idx].id] = flowType[idx].name;
	}
}

function initEditTable(tab) {
	tab.dataTable({
		// Internationalisation. For more info refer to http://datatables.net/manual/i18n
		"language": {
			"sProcessing": "处理中...",
			"sLengthMenu": "显示 _MENU_ 项结果",
			"sZeroRecords": "没有匹配结果",
			"sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
			"sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
			"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
			"sInfoPostFix": "",
			"sSearch": "搜索:",
			"sUrl": "",
			"sEmptyTable": "表中数据为空",
			"sLoadingRecords": "载入中...",
			"sInfoThousands": ",",
			"oPaginate": {
				"sFirst": "首页",
				"sPrevious": "上页",
				"sNext": "下页",
				"sLast": "末页"
			},
			"oAria": {
				"sSortAscending": ": 以升序排列此列",
				"sSortDescending": ": 以降序排列此列"
			}
		},

		"bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

		// set the initial value
		paging:false,
		searching:false,
		ordering:false
	});
}

// ====================== BEGIN coupon info table function =======================================================
function initDetailTable() {
	// begin first table
	initEditTable(table);

	$('#new_order').click(function(e) {
		e.preventDefault();
		if ( currEditRow ) {
			if ( !rowValid() ) {
				return;
			}
			saveRow();
		}

		var val = ['', '', '', '', '', '', ''];
		var newRow = table.fnAddData(val);
		var nRow = table.fnGetNodes(newRow[0]);
		$(nRow).bind("click", function() {
			editOrder(this);
		});
		editRow(nRow);
	});

	$('#del_order').click(function(e){
		delRow();
		getCurrSendUserInfo();
	});

	initBodyData($("#id").val());
}

function initBodyData(activityId) {
	activityId = ( activityId == null || activityId.length == 0 ? "ADD" : activityId );
	$.ajax({
		url: "coupon/coupon/" + activityId,
		dataType:"json",
		type:"GET",
		success:function(data){
			var result = eval(data).result;
			var coupons = eval(data).coupons;
			if (result == "success") {
				for (var idx = 0; idx < coupons.length; idx++) {
					initBodyTableData(coupons[idx]);
				}

			} else {
				var tipMsg = "表体数据失败!";
				var error = eval(data).errorMsg;
				if(error != null && error != "") {
					tipMsg += "\n" + error;
				}
				alert(tipMsg);
			}
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}

function initBodyTableData(coupon) {
	var val = ['', '', '', '', '', '', ''];
	var newRow = table.fnAddData(val);
	var nRow = table.fnGetNodes(newRow[0]);
	if (canEdit) {
		$(nRow).bind("click", function() {
			editOrder(this);

		});
	}

	$(nRow).attr("id", coupon.id);
	$(nRow).attr("ts", coupon.ts);
	$(nRow).attr("flowTypeId", coupon.flowTypeId);
	table.fnUpdate(coupon.code, nRow, 0, false);
	table.fnUpdate(flowTypeConvert[coupon.flowTypeId], nRow, 1, false);
	table.fnUpdate(fillDecimalZero(coupon.sendMny), nRow, 2, false);
	table.fnUpdate(fillDecimalZero(coupon.minPayMny), nRow, 3, false);
	table.fnUpdate(coupon.actBeginDate, nRow, 4, false);
	table.fnUpdate(coupon.actEndDate, nRow, 5, false);
	table.fnUpdate(coupon.memo, nRow, 6, false);

	table.fnDraw();

	$(nRow).find("td:eq(2)").attr("style", "text-align:right");
	$(nRow).find("td:eq(3)").attr("style", "text-align:right");
}

function editOrder(row) {
	if (currEditRow == row) {
		return;
	}

	if (currEditRow) {
		if ( !rowValid() ) {
			return;
		}

		saveRow();
		editRow(row);
	} else {
		editRow(row);
	}
}

function editRow(nRow) {
	currEditRow = nRow;

	var rowData = getSavedRowVal(nRow);
	var jqTds = $('>td', nRow);

	$(nRow).attr("id", rowData.id);
	$(nRow).attr("ts", rowData.ts);
	$(nRow).attr("flowTypeId", rowData.flowTypeId);
	$(nRow).attr("sendType", rowData.sendType);


	jqTds[0].innerHTML = '<input id="tCode" name="tCode" type="text" class="form-control" value="' + rowData.code + '" placeholder="系统自动生成" readonly />';
	jqTds[1].innerHTML = getFlowType("tFlowTypeId");
	jqTds[2].innerHTML = '<input id="tSendMny" name="sendMny" type="text" class="form-control mask_decimal search_curr_send_info" value="' + rowData.sendMny + '"/>';
	jqTds[3].innerHTML = '<input id="tMinPayMny" name="minPayMny" type="text" class="form-control mask_decimal" value="' + rowData.minPayMny + '"/>';
	jqTds[4].innerHTML = getDatePicker("tActBeginDate", "table-date-picker");
	jqTds[5].innerHTML = getDatePicker("tActEndDate", "table-date-picker");
	jqTds[6].innerHTML = '<input id="tMemo" name="bodyMemo" maxlength="400" type="text" class="form-control" value="' + rowData.memo + '"/>';

	initDynamicDatePicker("tActBeginDate", "tActEndDate");

	$("#tFlowTypeId").select2({minimumResultsForSearch: -1});
	$("#tFlowTypeId").select2("val", rowData.flowTypeId);

	$("#tActBeginDate").val(rowData.actBeginDate);
	$("#tActEndDate").val(rowData.actEndDate);
	_fromTableDate = new Date(rowData.actBeginDate).getTime();
	_toTableDate = new Date(rowData.actEndDate).getTime();
	initInputMask();

	initEvents();
}

function saveRow() {
	if (currEditRow == null) {
		return;
	}

	var rowData = getCurrRowVal();

	$(currEditRow).attr("id", rowData.id);
	$(currEditRow).attr("ts", rowData.ts);
	$(currEditRow).attr("flowTypeId", rowData.flowTypeId);

	table.fnUpdate(rowData.code, currEditRow, 0, false);
	table.fnUpdate(flowTypeConvert[rowData.flowTypeId], currEditRow, 1, false);
	table.fnUpdate(rowData.sendMny, currEditRow, 2, false);
	table.fnUpdate(rowData.minPayMny, currEditRow, 3, false);
	table.fnUpdate(rowData.actBeginDate, currEditRow, 4, false);
	table.fnUpdate(rowData.actEndDate, currEditRow, 5, false);
	table.fnUpdate(rowData.memo, currEditRow, 6, false);

	table.fnDraw();
	var jqTds = $('>td', currEditRow);
	$(jqTds).find(".checkboxes").uniform();
	$(currEditRow).find("td:eq(2)").attr("style", "text-align:right");
	$(currEditRow).find("td:eq(3)").attr("style", "text-align:right");

	currEditRow = null;
}

function getCurrRowVal() {
	var rowData = {};
	rowData.id = $(currEditRow).attr("id");
	rowData.ts = $(currEditRow).attr("ts");
	rowData.flowTypeId = $(currEditRow).find("[name='tFlowTypeId']").val();

	rowData.code = $('#tCode', currEditRow).val();
	rowData.sendMny = $('#tSendMny', currEditRow).val();
	rowData.minPayMny = $('#tMinPayMny', currEditRow).val();
	rowData.actBeginDate = $('#tActBeginDate', currEditRow).val();
	rowData.actEndDate = $('#tActEndDate', currEditRow).val();
	rowData.memo = $('#tMemo', currEditRow).val();

	return rowData;
}

function getSavedRowVal(nRow) {
	var tableRowData = table.fnGetData(nRow);
	var rowData = {};
	rowData.id = $(nRow).attr("id");
	rowData.id = rowData.id ? rowData.id : "";
	rowData.ts = $(nRow).attr("ts");
	rowData.ts = rowData.ts ? rowData.ts : "";

	rowData.code = tableRowData[0];
	rowData.flowTypeId = $(nRow).attr("flowTypeId");
	rowData.sendMny = tableRowData[2];
	rowData.minPayMny = tableRowData[3];
	rowData.actBeginDate = (tableRowData[4] == null ? "" : tableRowData[4]);
	rowData.actEndDate = (tableRowData[5] == null ? "" : tableRowData[5]);
	rowData.memo = tableRowData[6];

	return rowData;
}

function getFlowType( idName ) {
	var sel = '';
	sel += '<select id="' + idName + '" name="' + idName + '" class="form-control select2me">';
	for ( var idx = 0; idx < flowTypeObj.length; idx++ ) {
		sel += '  <option value="' + flowTypeObj[idx].id + '">' + flowTypeObj[idx].name + '</option>';
	}

	sel += '</select>';

	return sel;
}

function getDatePicker(name, clz) {
	var date = '';
	date += '<div class="input-group">';
	date += '<input id="'+ name +'" name="'+ name +'" type="text" class="form-control ' + clz + '" placeholder="请选择日期" readonly >';
	date += '<span class="input-group-btn">';
	date += '<button class="btn btn-default" type="button"><i class="fa fa-calendar"></i></button>';
	date += '</span>';
	date += '</div>';
	return date;
}

function initDynamicDatePicker(fromId, toId) {
	if (jQuery().datepicker) {
		$('.table-date-picker').datepicker({
			rtl: Metronic.isRTL(),
			language: "cn",
			orientation: "top left",
			autoclose: true,
			format: "yyyy-mm-dd"
		});

		$('.table-date-picker').parent('.input-group').on('click', '.input-group-btn', function (e) {
			e.preventDefault();
			$(this).parent('.input-group').find('.table-date-picker').datepicker('show');
		});

		var fromDate = $('#'+fromId);
		var toDate = $('#'+toId);

		fromDate.on("hide", function(ev) {
			if(ev.date == null) {
				_fromTableDate = null;
				return;
			}

			_fromTableDate = ev.date.getTime();
			if (_toTableDate != null && _fromTableDate > _toTableDate) {
				alert("开始日期不能大于结束日期");
				fromDate.datepicker("clearDates");
				_fromTableDate = null;
			}

		});
		toDate.on("hide", function(ev) {
			if(ev.date == null) {
				_toTableDate = null;
				return;
			}

			_toTableDate = ev.date.getTime();
			if (_fromTableDate != null && _fromTableDate > _toTableDate) {
				alert("结束日期不能小于开始日期");
				toDate.datepicker("clearDates");
				_toTableDate = null;
			}
		});
	}
}
// ====================== END coupon info table function =======================================================

// ====================== BEGIN coupon info table function =======================================================
function initRuleTable() {
	initEditTable(ruleTable);

	$('#new_rule_order').click(function(e) {
		e.preventDefault();
		if ( currRuleEditRow ) {
			if ( !rowRuleValid() ) {
				return;
			}
			saveRuleRow();
		}

		var val = ['', '', '', ''];
		var newRow = ruleTable.fnAddData(val);
		var nRow = ruleTable.fnGetNodes(newRow[0]);
		$(nRow).bind("click", function() {
			editRuleOrder(this);

		});
		editRuleRow(nRow);
	});

	$('#del_rule_order').click(function(e){
		delRuleRow();
		getCurrSendUserInfo();
	});

	initRuleBodyData($("#id").val());
}

function initRuleBodyData(activityId) {
	activityId = ( activityId == null || activityId.length == 0 ? "ADD" : activityId );
	$.ajax({
		url: "coupon/coupon_activity_rule/" + activityId,
		dataType:"json",
		type:"GET",
		success:function(data){
			var result = eval(data).result;
			var rules = eval(data).couponActivityRules;
			if (result == "success") {
				for (var idx = 0; idx < rules.length; idx++) {
					initRuleBodyTableData(rules[idx]);
				}

			} else {
				var tipMsg = "表体数据失败!";
				var error = eval(data).errorMsg;
				if(error != null && error != "") {
					tipMsg += "\n" + error;
				}
				alert(tipMsg);
			}
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}

function initRuleBodyTableData(rule) {
	var val = ['', '', '', ''];
	var newRow = ruleTable.fnAddData(val);
	var nRow = ruleTable.fnGetNodes(newRow[0]);
	if (canEdit) {
		$(nRow).bind("click", function() {
			editRuleOrder(this);

		});
	}

	$(nRow).attr("id", rule.id);
	$(nRow).attr("ts", rule.ts);
	$(nRow).attr("connectType", rule.connectType);
	$(nRow).attr("ruleType", rule.ruleType);
	$(nRow).attr("optType", rule.optType);
	$(nRow).attr("chkVal", rule.chkVal);
	$(nRow).attr("chkValName", rule.chkValName);

	ruleTable.fnUpdate(connTypeConvert[rule.connectType], nRow, 0, false);
	ruleTable.fnUpdate(ruleTypeConvert[rule.ruleType], nRow, 1, false);
	ruleTable.fnUpdate(optTypeConvert[rule.optType], nRow, 2, false);

	if ( rule.ruleType == 4 ) {
		ruleTable.fnUpdate(flowTypeConvert[rule.chkVal], nRow, 3, false);
	} else if ( rule.ruleType == 5 ) {
		ruleTable.fnUpdate(rule.chkValName, nRow, 3, false);
	} else {
		ruleTable.fnUpdate(rule.chkVal, nRow, 3, false);
	}

	table.fnDraw();

	if ( rule.ruleType == 1 ) {
		$(nRow).find("td:eq(3)").attr("style", "text-align:right");
	}

}

function editRuleOrder(row) {
	if (currRuleEditRow == row) {
		return;
	}

	if (currRuleEditRow) {
		if ( !rowRuleValid() ) {
			return;
		}

		saveRuleRow();
		editRuleRow(row);
	} else {
		editRuleRow(row);
	}
}

function editRuleRow(nRow) {
	currRuleEditRow = nRow;

	var rowData = getSavedRuleRowVal(nRow);
	var jqTds = $('>td', nRow);

	$(nRow).attr("id", rowData.id);
	$(nRow).attr("ts", rowData.ts);

	jqTds[0].innerHTML = connTypeSelect();
	jqTds[1].innerHTML = ruleTypeSelect();
	//jqTds[2].innerHTML = optTypeSelect();

	$("#tConnectType").select2({minimumResultsForSearch: -1});
	$("#tRuleType").select2({minimumResultsForSearch: -1});
	//$("#tOptType").select2({minimumResultsForSearch: -1});

	$("#tConnectType").select2("val", rowData.connectType);
	var ruleTypeSel = $("#tRuleType").select2("val", rowData.ruleType);
	//$("#tOptType").select2("val", rowData.optType);
	resetValueInputMask(rowData.ruleType, rowData.chkVal, rowData.chkValName, rowData.optType);

	initEvents();

	ruleTypeSel.bind("change", function() {
		resetValueInputMask($(this).val(), null,null, 2);
		getCurrSendUserInfo();
		initEvents();
	});
}

function resetValueInputMask(ruleType, val, valName, optType) {
	val = (val == null ? "" : val);
	valName = (valName == null ? "" : valName);
	var jqTds = $('>td', currRuleEditRow);

	if ( ruleType == 0 ) { // 消费次数
		jqTds[3].innerHTML = '<input id="tChkVal" name="tChkVal" type="text" class="form-control search_curr_send_info mask_number" value="' + val + '"/>';
		$("#tChkVal").val(val);
	} else if ( ruleType == 1 ) { // 消费金额
		jqTds[3].innerHTML = '<input id="tChkVal" name="tChkVal" type="text" class="form-control search_curr_send_info mask_decimal" value="' + val + '"/>';
		$("#tChkVal").val(val);
	} else if ( ruleType == 2 ) { // 用户首关日期
		jqTds[3].innerHTML = getDatePicker("tChkVal", "search_curr_send_info");
		resetDatePicker();
		$("#tChkVal").val(val);
	} else if ( ruleType == 3 ) { // 下单日期
		jqTds[3].innerHTML = getDatePicker("tChkVal", "search_curr_send_info");
		resetDatePicker();
		$("#tChkVal").val(val);
	} else if ( ruleType == 4 ) { // 消费类型
		jqTds[3].innerHTML = getFlowType("tChkVal");
		$("#tChkVal").select2({minimumResultsForSearch: -1});
		$("#tChkVal").select2("val", val);
	} else if ( ruleType == 5 ) { // 指定用户
		jqTds[3].innerHTML = '<input id="tChkVal" name="tChkVal" type="hidden" value="' + val + '"/><input id="tChkValName" name="tChkValName" type="text" class="form-control search_curr_send_info" value="' + valName + '" readonly />';
		$("#tChkVal").val(val);
		$("#tChkValName").bind("click", function() {
			showUserRefDlg("tChkVal", "tChkValName");
		});
	}

	if ( ruleType == 4 || ruleType == 5 ) { // 消费类型
		jqTds[2].innerHTML = optTypeSelectEquals();
	} else {
		jqTds[2].innerHTML = optTypeSelect();
	}

	$("#tOptType").select2({minimumResultsForSearch: -1});
	$("#tOptType").select2("val", optType);

	initInputMask();
}

function resetDatePicker() {
	if (jQuery().datepicker) {
		$('#tChkVal').datepicker({
			rtl: Metronic.isRTL(),
			language: "cn",
			orientation: "top left",
			autoclose: true,
			format: "yyyy-mm-dd"
		});

		$('#tChkVal').parent('.input-group').bind('click', '.input-group-btn', function (e) {
			e.preventDefault();
			$('#tChkVal').datepicker('show');
		});
	}
}

function connTypeSelect() {
	var sel = '';
	sel += '<select id="tConnectType" name="tConnectType" class="form-control select2me-2 search_curr_send_info">';
	for (var idx = 0; idx < connTypeConvert.length; idx++) {
		sel += '  <option value="' + idx + '">' + connTypeConvert[idx] + '</option>';
	}
	sel += '</select>';

	return sel;
}

function ruleTypeSelect() {
	var sel = '';
	sel += '<select id="tRuleType" name="tRuleType" class="form-control select2me-2">';
	for (var idx = 0; idx < ruleTypeConvert.length; idx++) {
		sel += '  <option value="' + idx + '">' + ruleTypeConvert[idx] + '</option>';
	}
	sel += '</select>';

	return sel;
}

function optTypeSelect() {
	var sel = '';
	sel += '<select id="tOptType" name="tOptType" class="form-control select2me-2 search_curr_send_info">';
	for (var idx = 0; idx < optTypeConvert.length; idx++) {
		sel += '  <option value="' + idx + '">' + optTypeConvert[idx] + '</option>';
	}
	sel += '</select>';

	return sel;
}

function optTypeSelectEquals() {
	var sel = '';
	sel += '<select id="tOptType" name="tOptType" class="form-control select2me-2 search_curr_send_info">';
	sel += '  <option value="2">等于</option>';
	sel += '</select>';

	return sel;
}

function saveRuleRow() {
	if (currRuleEditRow == null) {
		return;
	}

	var rowData = getCurrRuleRowVal();

	$(currRuleEditRow).attr("id", rowData.id);
	$(currRuleEditRow).attr("ts", rowData.ts);
	$(currRuleEditRow).attr("connectType", rowData.connectType);
	$(currRuleEditRow).attr("ruleType", rowData.ruleType);
	$(currRuleEditRow).attr("optType", rowData.optType);
	$(currRuleEditRow).attr("chkVal", rowData.chkVal);
	$(currRuleEditRow).attr("chkValName", rowData.chkValName);

	ruleTable.fnUpdate(connTypeConvert[rowData.connectType], currRuleEditRow, 0, false);
	ruleTable.fnUpdate(ruleTypeConvert[rowData.ruleType], currRuleEditRow, 1, false);
	ruleTable.fnUpdate(optTypeConvert[rowData.optType], currRuleEditRow, 2, false);

	if (rowData.ruleType == 4) {
		ruleTable.fnUpdate(flowTypeConvert[rowData.chkVal], currRuleEditRow, 3, false);
	} else if (rowData.ruleType == 5) {
		ruleTable.fnUpdate(rowData.chkValName, currRuleEditRow, 3, false);
	} else {
		ruleTable.fnUpdate(rowData.chkVal, currRuleEditRow, 3, false);
	}

	ruleTable.fnDraw();

	currRuleEditRow = null;
}

function getCurrRuleRowVal() {
	var rowData = {};
	rowData.id = $(currRuleEditRow).attr("id");
	rowData.ts = $(currRuleEditRow).attr("ts");

	rowData.connectType = $('#tConnectType', currRuleEditRow).val();
	rowData.ruleType = $('#tRuleType', currRuleEditRow).val();
	rowData.optType = $('#tOptType', currRuleEditRow).val();
	rowData.chkVal = $('#tChkVal', currRuleEditRow).val();
	rowData.chkValName = $('#tChkValName', currRuleEditRow).val();

	return rowData;
}

function getSavedRuleRowVal(nRow) {
	var rowData = {};
	rowData.id = $(nRow).attr("id");
	rowData.id = rowData.id ? rowData.id : "";
	rowData.ts = $(nRow).attr("ts");
	rowData.ts = rowData.ts ? rowData.ts : "";

	rowData.connectType = $(nRow).attr("connectType");
	rowData.connectType = rowData.connectType ? rowData.connectType : 0;
	rowData.ruleType = $(nRow).attr("ruleType");
	rowData.ruleType = rowData.ruleType ? rowData.ruleType : 0;
	rowData.optType = $(nRow).attr("optType");
	rowData.optType = rowData.optType ? rowData.optType : 0;
	rowData.chkVal = $(nRow).attr("chkVal");
	rowData.chkValName = $(nRow).attr("chkValName");

	return rowData;
}

function rowRuleValid() {
	if (!currRuleEditRow) {
		return true;
	}

	var result = true;
	var rowData = getCurrRuleRowVal();
	if (!rowData.connectType ) {
		alert("关联方式必填！");
		result = false;
	} else if (!rowData.ruleType ) {
		alert("规则必填！");
		result = false;
	} else if (!rowData.optType ) {
		alert("连接类型必填！");
		result = false;
	} else if (!rowData.chkVal ) {
		alert("比较值必填！");
		result = false;
	}

	return result;
}

function delRuleRow() {
	if (currRuleEditRow == null) {
		alert("请选择要删除的行!");
		return;
	}

	if(window.confirm("确认是否删除此行？")){
		var rowData = getSavedRuleRowVal( currRuleEditRow );
		if ( rowData.id ) {
			delRuleIds[delRuleIds.length] = rowData.id;
			delRuleTss[delRuleTss.length] = rowData.ts;
		}

		ruleTable.fnDeleteRow(currRuleEditRow);
		currRuleEditRow = null;

	}
}
// ====================== END coupon info table function =======================================================

function initDatePicker() {
	initDatePickerLangCn();

	if (jQuery().datepicker) {
		$('.date-picker').datepicker({
			rtl: Metronic.isRTL(),
			language: "cn",
			orientation: "top left",
			autoclose: true,
			format: "yyyy-mm-dd"
		});

		$('.date-picker').parent('.input-group').on('click', '.input-group-btn', function (e) {
			e.preventDefault();
			$(this).parent('.input-group').find('.date-picker').datepicker('show');
		});

		var fromDate = $('#fromDate');
		var toDate = $('#toDate');

		fromDate.on("hide", function(ev) {
			if(ev.date == null) {
				_fromDate = null;
				return;
			}

			_fromDate = ev.date.getTime();
			if (_toDate != null && _fromDate > _toDate) {
				alert("开始日期不能大于结束日期");
				fromDate.datepicker("clearDates");
				_fromDate = null;
			}

		});
		toDate.on("hide", function(ev) {
			if(ev.date == null) {
				_toDate = null;
				return;
			}

			_toDate = ev.date.getTime();
			if (_fromDate != null && _fromDate > _toDate) {
				alert("结束日期不能小于开始日期");
				toDate.datepicker("clearDates");
				_toDate = null;
			}
		});
	}
}

function initValidate(){
	var form = $("#activityDetailForm");
	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#activityDetailPane',
				overlayColor: 'none',
				animate: true
			});

			if( !rowValid() ) {
				Metronic.unblockUI('#activityDetailPane');
				return;
			}

			if( !rowRuleValid() ) {
				Metronic.unblockUI('#activityDetailPane');
				return;
			}

			saveRow();
			saveRuleRow();

			if (overDueCouponValid()) {
				Metronic.unblockUI('#activityDetailPane');
				return;
			}

			var cTable = $("#coupon-table");
			var rTable = $("#rule-table");
			var rows = cTable.find("tbody tr");
			var rowRules = rTable.find("tbody tr");
			if(rows[0].children.length==1){
				alert("优惠券信息为空不能保存！");
				Metronic.unblockUI('#activityDetailPane');
				return;
			} else {
				// == BEGIN 构造优惠券数据 ===========================================================================
				var bodyData = $("#body-datas");
				bodyData.empty();
				var currRow = 0;
				var rowData;

				// 构造表体行数据
				$(rows).each(function(index,ele){
					rowData = getSavedRowVal(this);

					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].id' value='" + rowData.id + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].ts' value='" + rowData.ts + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].code' value='" + rowData.code + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].flowTypeId' value='" + rowData.flowTypeId + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].sendMny' value='" + rowData.sendMny + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].minPayMny' value='" + rowData.minPayMny + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].actBeginDate' value='" + rowData.actBeginDate + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].actEndDate' value='" + rowData.actEndDate + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].memo' value='" + rowData.memo + "'>");
					currRow++;
				});

				// 构造表体删除行数据
				for (var idx = 0; idx < delIds.length; idx++) {
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].id' value='" + delIds[idx] + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].ts' value='" + delTss[idx] + "'>");
					bodyData.append("<input type='hidden' name='coupons[" + currRow + "].dr' value='1'>");
					currRow++;
				}
				// == END 构造优惠券数据 ===============================================================================

				// == BEGIN 构造派送规则数据 ===========================================================================
				var bodyRuleData = $("#rule-datas");
				bodyRuleData.empty();
				var currRuleRow = 0;
				var rowRuleData;

				// 构造表体行数据
				$(rowRules).each(function(index,ele){
					if ( $(rowRules).find("td").length > 1 ) {
						rowRuleData = getSavedRuleRowVal(this);
						bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].id' value='" + rowRuleData.id + "'>");
						bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].ts' value='" + rowRuleData.ts + "'>");
						bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].connectType' value='" + rowRuleData.connectType + "'>");
						bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].ruleType' value='" + rowRuleData.ruleType + "'>");
						bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].optType' value='" + rowRuleData.optType + "'>");
						bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].chkVal' value='" + rowRuleData.chkVal + "'>");
						currRuleRow++;
					}
				});

				// 构造表体删除行数据
				for (var idx = 0; idx < delRuleIds.length; idx++) {
					bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].id' value='" + delRuleIds[idx] + "'>");
					bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].ts' value='" + delRuleTss[idx] + "'>");
					bodyData.append("<input type='hidden' name='rules[" + currRuleRow + "].dr' value='1'>");
					currRuleRow++;
				}
				// == END 构造派送规则数据 =============================================================================
			}

			$.ajax({
				url: $(form).attr("action"),
				data: $(form).serializeArray(),
				dataType:"json",
				type:"POST",
				success:function(data){
					Metronic.unblockUI('#activityDetailPane');
					var result = eval(data).result;
					var activityId = eval(data).activityId;
					if (result == "success") {
						alert("保存成功!");
						window.location.href = "coupon/coupon_activity_detail/" + activityId + "?funcActiveCode=COUPON_ACT&canEdit=false";
					} else {
						var tipMsg = "保存失败!";
						var error = eval(data).errorMsg;
						if(error != null && error != "") {
							tipMsg += "\n" + error;
						}
						alert(tipMsg);
					}
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {

				}
			});
		},
		errorPlacement: function (error, element) {
			if (element.context.name == 'fromDate' || element.context.name == 'toDate') {
				element.parent().parent('div').append(error);
			} else {
				element.parent('div').append(error);
			}
		},
		rules:{
			name:{
				required:true
			},
			status:{
				required:true
			}
		},
		messages:{
			name:{
				required:"必填",
				maxlength:"请不要超过50位"
			},
			status:{
				required: "必填"
			},
			sendMessage:{
				maxlength:"请不要超过400位"
			},
			memo:{
				maxlength:"请不要超过400位"
			}
		}
	});
}

function overDueCouponValid() {
	var hasOverDueCoupon = false;
	var currDate = new Date();
	currDate.setHours(0);
	currDate.setMinutes(0);
	currDate.setSeconds(0);

	var cTable = $("#coupon-table");
	var rows = cTable.find("tbody tr");
	var rowData;

	// 构造表体行数据
	$(rows).each(function(index,ele) {
		rowData = getSavedRowVal(this);

		rowData = getSavedRowVal(this);
		if ( new Date(rowData.actEndDate) < currDate  ) {
			hasOverDueCoupon = true;
		}
	});

	if (hasOverDueCoupon) {
		alert("待发优惠券中存在已过期的优惠券，请编辑优惠券列表！");
	}

	return hasOverDueCoupon;
}

function delRow() {
	if (currEditRow == null) {
		alert("请选择要删除的行!");
		return;
	}

	if(window.confirm("确认是否删除此行？")){
		var rowData = getSavedRowVal( currEditRow );
		if ( rowData.id ) {
			delIds[delIds.length] = rowData.id;
			delTss[delTss.length] = rowData.ts;
		}

		table.fnDeleteRow(currEditRow);
		currEditRow = null;

	}
}

function returnList() {
	window.location.href = "coupon/coupon_activity?funcActiveCode=COUPON_ACT";
}

function editActivityInfo() {
	if ( $("#status").val() == 2 ) {
		alert("已派送不允许修改！");
		return;
	}

	window.location.href = "coupon/coupon_activity_detail/" + $("#id").val() + "?funcActiveCode=COUPON_ACT&canEdit=true";
}

function cancel() {
	history.back(-1);
}

function rowValid() {
	if (!currEditRow) {
		return true;
	}

	var result = true;
	var rowData = getCurrRowVal();
	if (!rowData.flowTypeId ) {
		alert("优惠券类型必填！");
		result = false;
	} else if (!rowData.sendMny ) {
		alert("发放金额必填！");
		result = false;
	} else if (!rowData.minPayMny ) {
		alert("最低消费金额必填！");
		result = false;
	} else {
		if (!rowData.actBeginDate ) {
			alert("生效开始日期必填！");
			result = false;
		} else if (!rowData.actEndDate ) {
			alert("生效截止日期必填！");
			result = false;
		}
	}

	return result;
}

function initFlowType(flowTypeIdVal) {
	$.ajax({
		url:"flow/flow_types/all",
		dataType:"json",
		success:function(datas){
			if ( eval(datas).result == "success" ) {
				var flowTypeUnits = eval(datas).data;
				initTypeList(flowTypeUnits);
			}
		}
	});
}

function initSendUserDetailTable() {
	var id = ( $("#id").val() == null || $("#id").val().length == 0 ? "ADD" : $("#id").val() );

	sendUserTable = PdkDataTable.init({
		"tableId": "user-list-table",
		"url": "coupon/coupon_activity_detail/search_user_send_detail_table/" + id,
		"columns": [
			{ "data": "index" },
			{ "data": "name" },
			{ "data": "realName" },
			{ "data": "payMny" },
			{ "data": "payTimes" },
			{ "data": "firstRegisterDate" }
		],
		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0]
		}, {
			"searchable": false,
			"targets": [0, 2]
		}],
		"order": [
			[1, "asc"]
		]
	});


	var dialog = $("#userListDialog");
	dialog.on("hidden.bs.modal", function () {
	});

	dialog.on("show.bs.modal", function () {
	});

}

function showSendUserList() {
	var id = ( $("#id").val() == null || $("#id").val().length == 0 ? "ADD" : $("#id").val() );
	sendUserTable.ajax.url("coupon/coupon_activity_detail/search_user_send_detail_table/" + id).load();
	$("#userListDialog").modal("show");
}

function hideSendUserList() {
	$("#userListDialog").modal("hide");
}

function getCurrSendUserInfo(id) {
	if ( id == null ) {
		initSearchCondition();
	}

	var form = $("#currSendUserForm");
	$.ajax({
		url: "coupon/coupon_activity_detail/search_user_send_info" + (id == null ? "" : "/id/" + id),
		data: $(form).serializeArray(),
		dataType:"json",
		success:function(datas){
			var sendTotalMny = eval(datas).sendTotalMny;
			var sendUserCnt = eval(datas).sendUserCnt;
			if ( eval(datas).result == "success" ) {
				if (canEdit) {
					$("#sendUserCnt").html(sendUserCnt);
				} else {
					$("#sendUserCnt").html('<a style="hover:red;color:red;" onclick="showSendUserList();">' + sendUserCnt + '</a>');
				}

				$("#sendTotalMny").html(sendTotalMny);
			}
		}
	});
}

function initSearchCondition() {
	var cTable = $("#coupon-table");
	var rTable = $("#rule-table");
	var rows = cTable.find("tbody tr");
	var rowRules = rTable.find("tbody tr");

	var bodyData = $("#search-coupon-data");
	bodyData.empty();
	var currRow = 0;
	var rowData;

	// 构造表体行数据
	$(rows).each(function(index,ele){
		if ( $(this).find("td").length > 1 ) {
			if ( currEditRow == this ) {
				rowData = getCurrRowVal();
			} else {
				rowData = getSavedRowVal(this);
			}

			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].id' value='" + rowData.id + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].ts' value='" + rowData.ts + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].code' value='" + rowData.code + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].flowTypeId' value='" + rowData.flowTypeId + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].sendMny' value='" + rowData.sendMny + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].minPayMny' value='" + rowData.minPayMny + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].actBeginDate' value='" + rowData.actBeginDate + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].actEndDate' value='" + rowData.actEndDate + "'>");
			bodyData.append("<input type='hidden' name='coupons[" + currRow + "].memo' value='" + rowData.memo + "'>");
			currRow++;
		}
	});

	var bodyRuleData = $("#search-rule-data");
	bodyRuleData.empty();
	var currRuleRow = 0;
	var rowRuleData;
	// 构造表体行数据
	$(rowRules).each(function(index,ele){
		if ( $(this).find("td").length > 1 ) {
			if ( currRuleEditRow == this ) {
				rowRuleData = getCurrRuleRowVal();
			} else {
				rowRuleData = getSavedRuleRowVal(this);
			}
			bodyRuleData.append("<input type='hidden' name='rules[" + currRuleRow + "].id' value='" + rowRuleData.id + "'>");
			bodyRuleData.append("<input type='hidden' name='rules[" + currRuleRow + "].ts' value='" + rowRuleData.ts + "'>");
			bodyRuleData.append("<input type='hidden' name='rules[" + currRuleRow + "].connectType' value='" + rowRuleData.connectType + "'>");
			bodyRuleData.append("<input type='hidden' name='rules[" + currRuleRow + "].ruleType' value='" + rowRuleData.ruleType + "'>");
			bodyRuleData.append("<input type='hidden' name='rules[" + currRuleRow + "].optType' value='" + rowRuleData.optType + "'>");
			bodyRuleData.append("<input type='hidden' name='rules[" + currRuleRow + "].chkVal' value='" + rowRuleData.chkVal + "'>");
			currRuleRow++;
		}
	});
}

function initEvents() {
	$(".search_curr_send_info").each(function() {
		$(this).unbind("change");
		$(this).bind("change", function() {
			getCurrSendUserInfo();
		});
	});
}

function sendCoupon() {
	Metronic.blockUI({
		target: '#couponSendDetailPane',
		overlayColor: 'none',
		animate: true
	});

	if ( $("#status").val() == 2 ) {
		alert("已派送不允许重复派送");
		Metronic.unblockUI('#couponSendDetailPane');
		return;
	}



	$.ajax({
		url:"coupon/coupon_activity_detail/send_coupon/" + $("#id").val(),
		dataType:"json",
		type:"POST",
		success:function(datas){
			if ( eval(datas).result == "success" ) {
				alert("派送成功！");
				window.location.reload();
			} else {
				alert(eval(datas).message);
			}
			Metronic.unblockUI('#couponSendDetailPane');
		}
	});
}

function updateStatus(status) {
	var statusTxt;

	if(status == 0) {
		statusTxt = "终止";
	}else if (status == 1) {
		statusTxt = "启动";
	}

	var table = $('#activity-table');
	if ( $("#status").val() == status ) {
		alert("该次派送已经" + statusTxt + "不允许重复操作！");
		return;
	} else if ( $("#status").val() == 2 ) {
		alert("已经派送，不允许修改状态！");
		return;
	} else if ( status == 1 && $("#autoSendTime").val() == "" ) {
		alert("未设定系统自动派送时间，请设定后再启动！");
		return;
	}

	if(window.confirm("确定要"+ statusTxt + "这次派送吗？")){
		var form = $("#activityUpdateStatusForm");
		$(form).find("[name=_method]").val("PUT");
		var idData = $("#status-id-data");
		idData.empty();
		idData.append("<input type='hidden' name='id' value='" + $("#id").val() + "'>");
		idData.append("<input type='hidden' name='ts' value='" + $("#ts").val() + "'>");

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

function startAutoSend() {
	updateStatus(1);
}

function stopAutoSend() {
	updateStatus(0);
}