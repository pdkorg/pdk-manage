var _fromTableDate = null;
var _toTableDate = null;
var currEditRow = null;
var table = $('#coupon-table');
var flowTypeObj = null;
var flowTypeConvert = {};

var delIds = [];
var delTss = [];

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

		var val = ['', '', '', '', '', '', '', '', '', ''];
		var newRow = table.fnAddData(val);
		var nRow = table.fnGetNodes(newRow[0]);
		$(nRow).bind("click", function() {
			editOrder(this);
		});
		editRow(nRow);
	});

	$('#del_order').click(function(e){
		delRow();
	});

	initBodyData($("#id").val());
}

function initBodyData(activityId) {
	activityId = ( activityId == null || activityId.length == 0 ? "ADD" : activityId );
	$.ajax({
		url: "coupon/coupon_template/" + activityId,
		dataType:"json",
		type:"GET",
		success:function(data){
			var result = eval(data).result;
			var couponTemplates = eval(data).couponTemplates;
			if (result == "success") {
				for (var idx = 0; idx < couponTemplates.length; idx++) {
					initBodyTableData(couponTemplates[idx]);
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
	var val = ['', '', '', '', '', '', '', '', '', ''];
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
	table.fnUpdate(coupon.name, nRow, 1, false);
	table.fnUpdate(flowTypeConvert[coupon.flowTypeId], nRow, 2, false);
	table.fnUpdate(fillDecimalZero(coupon.sendMny), nRow, 3, false);
	table.fnUpdate(fillDecimalZero(coupon.minPayMny), nRow, 4, false);
	table.fnUpdate(coupon.delayDays, nRow, 5, false);
	table.fnUpdate(coupon.actDays, nRow, 6, false);
	table.fnUpdate(coupon.beginDate, nRow, 7, false);
	table.fnUpdate(coupon.endDate, nRow, 8, false);
	table.fnUpdate(coupon.memo, nRow, 9, false);

	table.fnDraw();

	$(nRow).find("td:eq(3)").attr("style", "text-align:right");
	$(nRow).find("td:eq(4)").attr("style", "text-align:right");
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
	jqTds[1].innerHTML = '<input id="tName" name="tName" type="text" class="form-control" value="' + rowData.name + '" />';
	jqTds[2].innerHTML = getFlowType("tFlowTypeId");
	jqTds[3].innerHTML = '<input id="tSendMny" name="sendMny" type="text" class="form-control mask_decimal search_curr_send_info" value="' + rowData.sendMny + '"/>';
	jqTds[4].innerHTML = '<input id="tMinPayMny" name="minPayMny" type="text" class="form-control mask_decimal" value="' + rowData.minPayMny + '"/>';
	jqTds[5].innerHTML = '<input id="tDelayDays" name="delayDays" type="text" class="form-control mask_number" value="' + rowData.delayDays + '"/>';
	jqTds[6].innerHTML = '<input id="tActDays" name="actDays" type="text" class="form-control mask_number" value="' + rowData.actDays + '"/>';
	jqTds[7].innerHTML = getDatePicker("tBeginDate", "table-date-picker");
	jqTds[8].innerHTML = getDatePicker("tEndDate", "table-date-picker");
	jqTds[9].innerHTML = '<input id="tMemo" name="bodyMemo" maxlength="400" type="text" class="form-control" value="' + rowData.memo + '"/>';

	initDynamicDatePicker("tBeginDate", "tEndDate");

	$("#tFlowTypeId").select2({minimumResultsForSearch: -1});
	$("#tFlowTypeId").select2("val", rowData.flowTypeId);

	$("#tBeginDate").val(rowData.beginDate);
	$("#tEndDate").val(rowData.endDate);
	_fromTableDate = new Date(rowData.beginDate).getTime();
	_toTableDate = new Date(rowData.endDate).getTime();
	initInputMask();
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
	table.fnUpdate(rowData.name, currEditRow, 1, false);
	table.fnUpdate(flowTypeConvert[rowData.flowTypeId], currEditRow, 2, false);
	table.fnUpdate(rowData.sendMny, currEditRow, 3, false);
	table.fnUpdate(rowData.minPayMny, currEditRow, 4, false);
	table.fnUpdate(rowData.delayDays, currEditRow, 5, false);
	table.fnUpdate(rowData.actDays, currEditRow, 6, false);
	table.fnUpdate(rowData.beginDate, currEditRow, 7, false);
	table.fnUpdate(rowData.endDate, currEditRow, 8, false);
	table.fnUpdate(rowData.memo, currEditRow, 9, false);

	table.fnDraw();
	var jqTds = $('>td', currEditRow);
	$(jqTds).find(".checkboxes").uniform();
	$(currEditRow).find("td:eq(3)").attr("style", "text-align:right");
	$(currEditRow).find("td:eq(4)").attr("style", "text-align:right");

	currEditRow = null;
}

function getCurrRowVal() {
	var rowData = {};
	rowData.id = $(currEditRow).attr("id");
	rowData.ts = $(currEditRow).attr("ts");
	rowData.flowTypeId = $(currEditRow).find("[name='tFlowTypeId']").val();

	rowData.code = $('#tCode', currEditRow).val();
	rowData.name = $('#tName', currEditRow).val();
	rowData.sendMny = $('#tSendMny', currEditRow).val();
	rowData.minPayMny = $('#tMinPayMny', currEditRow).val();
	rowData.delayDays = $('#tDelayDays', currEditRow).val();
	rowData.actDays = $('#tActDays', currEditRow).val();
	rowData.beginDate = $('#tBeginDate', currEditRow).val();
	rowData.endDate = $('#tEndDate', currEditRow).val();
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
	rowData.name = tableRowData[1];
	rowData.flowTypeId = $(nRow).attr("flowTypeId");
	rowData.sendMny = tableRowData[3];
	rowData.minPayMny = tableRowData[4];
	rowData.delayDays = (tableRowData[5] == null ? "" : tableRowData[5]);
	rowData.actDays = (tableRowData[6] == null ? "" : tableRowData[6]);
	rowData.beginDate = (tableRowData[7] == null ? "" : tableRowData[7]);
	rowData.endDate = (tableRowData[8] == null ? "" : tableRowData[8]);
	rowData.memo = tableRowData[9];

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

function initValidate(){
	var form = $("#activityDetailForm");
	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#detailPanel',
				overlayColor: 'none',
				animate: true
			});

			if( !rowValid() ) {
				Metronic.unblockUI('#detailPanel');
				return;
			}

			var cTable = $("#coupon-table");
			saveRow();
			var rows = cTable.find("tbody tr");
			if(rows[0].children.length==1){
				alert("优惠券信息为空不能保存！");
				Metronic.unblockUI('#detailPanel');
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
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].id' value='" + rowData.id + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].ts' value='" + rowData.ts + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].code' value='" + rowData.code + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].name' value='" + rowData.name + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].flowTypeId' value='" + rowData.flowTypeId + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].sendMny' value='" + rowData.sendMny + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].minPayMny' value='" + rowData.minPayMny + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].delayDays' value='" + rowData.delayDays + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].actDays' value='" + rowData.actDays + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].beginDate' value='" + rowData.beginDate + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].endDate' value='" + rowData.endDate + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].memo' value='" + rowData.memo + "'>");
					currRow++;
				});

				// 构造表体删除行数据
				for (var idx = 0; idx < delIds.length; idx++) {
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].id' value='" + delIds[idx] + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].ts' value='" + delTss[idx] + "'>");
					bodyData.append("<input type='hidden' name='bodys[" + currRow + "].dr' value='1'>");
					currRow++;
				}
				// == END 构造优惠券数据 ===============================================================================
			}

			$.ajax({
				url: $(form).attr("action"),
				data: $(form).serializeArray(),
				dataType:"json",
				type:"POST",
				success:function(data){
					Metronic.unblockUI('#detailPanel');
					var result = eval(data).result;
					var activityTemplateId = eval(data).activityTemplateId;
					if (result == "success") {
						alert("保存成功!");
						window.location.href = "coupon/coupon_activity_template_detail/" + activityTemplateId + "?funcActiveCode=COUPON_TMP&canEdit=false";
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
			code:{
				required:true
			},
			name:{
				required:true
			},
			status:{
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
	window.location.href = "coupon/coupon_activity_template?funcActiveCode=COUPON_TMP";
}

function editActivityInfo() {
	window.location.href = "coupon/coupon_activity_template_detail/" + $("#id").val() + "?funcActiveCode=COUPON_TMP&canEdit=true";
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
	if (!rowData.name ) {
		alert("优惠券模板名称必填！");
		result = false;
	} else if (!rowData.flowTypeId ) {
		alert("优惠券类型必填！");
		result = false;
	} else if (!rowData.sendMny ) {
		alert("发放金额必填！");
		result = false;
	} else if (!rowData.minPayMny ) {
		alert("最低消费金额必填！");
		result = false;
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