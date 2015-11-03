var selUserId;
var callBack;
var addrTable;
var action;
var validate;

function initAddress() {
	initAddressTable();

	initListAddress();
	initAddressDetail();

	initAddrSelect2();
	initInputMask();
}
function initListAddress() {
	var dialog = $("addressDetailDialog");
	dialog.on("hidden.bs.modal", function () {
		alert("hidden");
	});

	dialog.on("show.bs.modal", function () {
	});
}

function initAddressDetail() {
	var form = $("#addressDetailForm");

	validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#addressDetailPane',
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
					Metronic.unblockUI('#addressDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						$('#addressDetailDialog').modal('hide');
						alert("保存成功!");
						addrTable.ajax.url("sm/sm_address/table_data/"+selUserId).load();
						if(callBack != null) {
							callBack();
						}
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
					Metronic.unblockUI('#addressDetailPane');
					alert("保存失败!");
				}
			});
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		rules:{
			receiver:{
				required:true
			},
			cityId:{
				required:true
			},
			areaId:{
				required:true
			},
			street:{
				required:true
			},
			phone:{
				required:true
			}
		},
		messages:{
			receiver:{
				required:"必填"
			},
			cityId:{
				required:"必填"
			},
			areaId:{
				required:"必填"
			},
			street:{
				required:"必填"
			},
			postNum:{
				maxlength:"请不要超过6位"
			},
			phone:{
				required:"必填"
			}
		}
	});

	var dialog = $("#addressDetailDialog");

	dialog.on("hidden.bs.modal", function () {
		$(this).removeData("bs.modal");
		form.find("input").val("");
		validate.resetForm();
		form.find('.form-group').removeClass('has-error');
		form.find("#edit-method").empty();
		action = null;
	});

	dialog.on("show.bs.modal", function () {
		var selId = "";
		if (action == "edit") {
			selId = selectId;
		} else {
			selId = "-1";
		}

		//if(action == "edit") {
			Metronic.blockUI({
				target: '#addressDetailPane',
				overlayColor: 'none',
				animate: true
			});
			$.ajax({
				url: "sm/sm_address/" + selUserId + "/" + selId,
				data: {},
				dataType: "json",
				async: true,
				type: "GET",
				success: function (data) {
					Metronic.unblockUI('#addressDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						var address = eval(data).data;
						form.find("[name = 'id']").val(address.DT_RowId);
						form.find("[name = 'ts']").val(address.ts);
						form.find("[name = 'phone']").val(address.phone);
						form.find("[name = 'receiver']").val(address.receiver);
						form.find("[name = 'cityId']").select2("val", address.cityId);
						form.find("[name = 'areaId']").select2("val", address.areaId);
						form.find("[name = 'street']").val(address.street);
						form.find("[name = 'postNum']").val(address.postNum);

						var isDef = !(!address.isDefault || address.isDefault == 'N');
						form.find("[name = 'isDefault']").val( isDef ? 'Y' : 'N');
						jQuery($("#isDefCheckBox")).attr("checked", isDef);
						jQuery.uniform.update($("#isDefCheckBox"));
						if (action == "edit") {
							form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
						} else {
							form.find("#edit-method").html("<input type='hidden' name='_method' value=''></div>");
						}
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					Metronic.unblockUI('#addressDetailPane');
					$('#addressDialog').modal('hide');
				}
			});
		//} else {
			//form.find("[name = 'id']").val(null);
			//form.find("[name = 'ts']").val(null);
			//form.find("[name = 'phone']").val(null);
			//form.find("[name = 'receiver']").val(null);
			//form.find("[name = 'cityId']").select2("val", form.find("[name = 'cityId']").find("option").eq(0).val());
			//form.find("[name = 'areaId']").select2("val", form.find("[name = 'areaId']").find("option").eq(0).val());
			//form.find("[name = 'street']").val(null);
			//form.find("[name = 'postNum']").val(null);
            //
			//form.find("[name = 'isDefault']").val("N");
			//form.find("[name = 'isDefCheckBox']").attr("checked", false);
		//}

		form.find("[name = 'userId']").val(selUserId);
	});
}

function addressAdd() {
	var jTable = $('#address-table');
	var rows = jTable.find("tbody tr");
	if ( rows.length >= 5 ) {
		alert("用户地址不能超过5条");
	} else {
		$("#isDefault").val("N");
		jQuery($("#isDefCheckBox")).attr("checked", false);
		jQuery.uniform.update($("#isDefCheckBox"));

		$("#addressDetailDialog").modal("show");
	}

}

function addressEdit() {
	var table = $('#address-table');

	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		selectId = checkeds.eq(0).parents("tr").attr("id");
		action = "edit";
		$('#addressDetailDialog').modal('show');
	}
}

function addressDel() {
	var table = $('#address-table');

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

			var form = $("#addressDelForm");

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
						addrTable.ajax.url("sm/sm_address/table_data/"+selUserId).load();
						if(callBack != null) {
							callBack();
						}
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

function initAddrSelect2() {
	$.ajax({
		url:"bd/bd_city_area",
		dataType:"json",
		success:function(datas){
			if ( eval(datas).result == "success" ) {
				var citys = eval(datas).citys;
				var areas = eval(datas).areas;
				initCitySel2("cityId", citys);
				initCitySel2("areaId", areas);
			}
		}
	});
}

function initCitySel2(name, items) {
	var select = $("select[name=" + name + "]").empty();
	var option = "";
	$(items).each(function(){
		option += "<option value='" + this.id + "'>" + this.name + "</option>";
	});

	select.html(option).select2({minimumResultsForSearch: -1});
}

function initAddressTable() {
	addrTable = PdkDataTable.init({
		"tableId": "address-table",
		"url": "sm/sm_address/table_data/-1",
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "receiver" },
			{ "data": "fullAddress" },
			{ "data": "isDefault" }
		],
		"paging" : false,
		"ordering":false,
		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0, 1, 2, 3]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[2, "asc"]
		]

	});

	singleSelect4Table(addrTable);
}

function showAddressRef(userId, callback) {
	selUserId = userId;
	callBack = callback;

	$("#isDefault").val("N");
	jQuery($("#isDefCheckBox")).attr("checked", false);
	jQuery.uniform.update($("#isDefCheckBox"));

	addrTable.ajax.url("sm/sm_address/table_data/"+selUserId).load();
	$('#addressDialog').modal('show');
}

function saveAddress() {
	$("#addressDetailForm").submit();
}

function updDefault() {
	var isDef = $("#isDefCheckBox").attr("checked");
	$("#isDefault").val(isDef ? "Y" : "N");
}

function address2Def() {
	var jTable = $('#address-table');
	var checkeds = jTable.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一行数据！");
	}else {
		if(window.confirm("确定要设为默认地址吗？")){
			var selectId = checkeds.eq(0).parents("tr").attr("id");

			$.ajax({
				url: "sm/sm_address/set_default_address/" + selectId,
				dataType: "json",
				async: true,
				type: "POST",
				success: function (data) {
					var result = eval(data).result;
					if (result == "success") {
						alert("操作成功!");

						addrTable.ajax.url("sm/sm_address/table_data/"+selUserId).load();
						if(callBack != null) {
							callBack();
						}
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