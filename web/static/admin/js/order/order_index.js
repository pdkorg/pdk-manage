var ordertable=null;

var usertable = null;

var employeetable = null;

//谁点击的员工参照
var employeeclick = null;

function initEvents(){

	//用户选择确定按钮点击事件
	$("#userSelect").click(function(){
		selectUser();
	});
	//删除订单
	$("#del_order").click(function(){
		deleteOrders();
	});
	//取消订单
	$("#cancel_order").click(function(){
		cancelOrder();
	});

	//修改订单
	$("#edit_order").click(function(){
		editOrder();
	});

	$("#employeeSelect").click(function(){
		selectEmployee();
	});

	$("#reDistribute").click(function(){
		reDistributeYwy();
	});
}

function orderPay(type){
	var orderid = selectOrder();
	if(orderid==-1) {
		alert("请选择一条订单数据!");
		return;
	}
	var confirmStr = "";
	if(type==0){
		confirmStr = "确认要进行微信支付完成？";
	}else{
		confirmStr = "确认要进行现金支付完成？"
	}
	if(!window.confirm(confirmStr)){
		return;
	}
	$.ajax({
		url:"order/payaction",
		data:{orderId:orderid,payType:type},
		type:"POST",
		dataType:"json",
		success:function(data){
			alert(data.message);
			ordertable.ajax.url("order/table_data").load();
		}
	});

}

function reDistributeYwy(){
	var orderid = selectOrder();
	if(orderid==-1) {
		alert("请选择一条订单数据!");
		return;
	}
	//设置orderid
	$("#employeeSelect")[0].setAttribute("data-orderid",orderid);
	//重新分配业务员
	showEmployeeDialog(3);
}



//1、选择业务员，2、选择客服，3、重新分配业务员
function showEmployeeDialog(type){
	employeeclick = type;
	if(employeetable == null){
		initEmployeeTable();
	}else {
		employeetable.ajax.url("order/employeetable/"+type).load();
	}
	$('#employeeDialog').modal('show');
}
//点击员工参照确认按钮
function selectEmployee(){
	var table = $('#employee-table');

	var checkeds = table.find("tbody .checkboxes:checked");
	if(checkeds.length <= 0) {
		alert("请选择一条员工数据!");
		return;
	}
	var selectId = checkeds.eq(0).parents("tr").attr("id");
	var ipt = null;
	if(employeeclick==1){
		ipt = $("#ywyid");
		var name = checkeds.eq(0).parents("tr")[0].children[3].innerHTML;
		ipt.val(name);
		ipt[0].setAttribute("data-id",selectId);
	}else if(employeeclick==2){
		ipt = $("#waiterid");
		var name = checkeds.eq(0).parents("tr")[0].children[3].innerHTML;
		ipt.val(name);
		ipt[0].setAttribute("data-id",selectId);
	}else if(employeeclick==3){
		//重新分配业务员
		var orderid = $("#employeeSelect")[0].getAttribute("data-orderid");
		$.ajax({
			url:"order/redistributeywy",
			data:{orderid:orderid,ywyid:selectId},
			dataType:"json",
			type:"POST",
			success:function(data){
				alert(data.message);
				ordertable.ajax.url("order/table_data").load();
			}
		});
	}



	$('#employeeDialog').modal('hide');
}


function initFlowTypeList() {
	$.ajax({
		url:"order/flowlistdata",
		dataType:"json",
		type:"get",
		success:function(datas) {
			var ordertypeSelect = $("select[name='ordertype']").empty();
			var option = "<option value=''>全部</option>";
			if(eval(datas).result == "success") {
				var roles = eval(datas).data;
				$(roles).each(function(){
					option += "<option value='"+this.id+"'>"+this.code + "  " + this.name+"</option>";
				});
			}
			ordertypeSelect.html(option).select2();
		}
	});
}

function editOrder(){
	var orderid = selectOrder();
	if(orderid==-1) {
		alert("请选择一条订单数据!");
		return;
	}
	window.location.href="order/editorder/"+orderid+"?funcActiveCode=DETAIL";
}

function clickQuery(){
	var ordercode = $("#ordercode").val();
	var ywyid = $("#ywyid")[0].getAttribute("data-id");
	var waiterid = $("#waiterid")[0].getAttribute("data-id");
	var customid = $("#customid")[0].getAttribute("data-id");
	var orderState = $("#orderState").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var flowtypeid = $("#ordertype").val();
	var ajaxurl = "order/table_data?";

	ajaxurl += "orderCode="+isNotEmpty(ordercode)+"&ywyid="+isNotEmpty(ywyid)+"&waiterid="+isNotEmpty(waiterid)
		+"&customid="+isNotEmpty(customid)+"&orderState="+isNotEmpty(orderState)+"&flowtypeid="
		+ isNotEmpty(flowtypeid)+"&fromDate="+isNotEmpty(fromDate)+"&toDate="+isNotEmpty(toDate);
	ordertable.ajax.url(ajaxurl).load();
	//alert("查询成功！");
}

function isNotEmpty(obj){
	if(typeof obj =="undefined" || obj==null){
		return "";
	}
	return obj;
}

function clearQueryArg(){
	$("#ordercode").val("");
	$("#ywyid").val("");
	$("#ywyid")[0].setAttribute("data-id","");
	$("#waiterid").val("");
	$("#waiterid")[0].setAttribute("data-id","");
	$("#customid").val("");
	$("#customid")[0].setAttribute("data-id","");
	$("#ordertype").select2("val","");
	$("#orderState").select2("val","");
	$("#fromDate").val("");
	$("#toDate").val("");
	_fromDate = null;
	_toDate = null;
	ordertable.ajax.url("order/table_data").load();
}


//删除订单
function deleteOrders(){
	var table = $("#order-table");
	var checkeds = table.find("tbody .checkboxes:checked");
	if(checkeds.length<=0){
		alert("请选择一条订单！");
		return;
	}else{

		var id =checkeds.eq(0).parents("tr").attr("id");
		var ts =checkeds.eq(0).parents("tr").attr("ts");
		var row =checkeds.eq(0).parents("tr")[0].children[1].innerHTML
		if(window.confirm("确定要删除第"+row+"行的订单吗？")){
			$.ajax({
				url: "order/deleteorder",
				data: {id:id,ts:ts},
				dataType: "json",
				async: true,
				type: "POST",
				success: function (data) {
					if(data.result=='success'){
						alert("删除成功~");
						window.location.reload();
					}else if(data.result=='error'){
						alert("删除失败~");
						window.location.reload();
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {

				}
			});
		}

	}
}
//取消订单
function cancelOrder(){
	var table = $("#order-table");
	var checkeds = table.find("tbody .checkboxes:checked");
	if(checkeds.length<=0){
		alert("请选择一条订单!");
		return;
	}
	var orderid =checkeds.eq(0).parents("tr").attr("id");
	var ts = checkeds.eq(0).parents("tr").attr("ts");
	var index = checkeds.eq(0).parents("tr")[0].children[1].innerHTML;
	if(window.confirm("您确定要取消第"+index+"行的订单吗？")){
		$.ajax({
			url: "order/cancelOrder",
			data: {id:orderid,ts:ts},
			dataType: "json",
			async: true,
			type: "POST",
			success: function (data) {
				console.info(data.value);
				if(data.result=='success'){
					alert(data.message);
					window.location.reload();
				}else if(data.result=='error'){
					alert(data.message);
					window.location.reload();
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {

			}
		});
	}
}


function showUserRefDialog(){
	if(usertable==null){
		userDetailTable();
	}else{
		usertable.ajax.url("order/usertabel_data").load();
	}
	$('#userDialog').modal('show');
}


//选择用户操作
function selectUser(){
	var clickinput = $("#customid");
	var table = $('#user-table');

	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一条用户信息！");
		return;
		clickinput.val("");
		clickinput[0].setAttribute("data-id","");
	}else {
		var selectId = checkeds.eq(0).parents("tr").attr("id");
		var nickName = checkeds.eq(0).parents("tr")[0].children[2].innerHTML;
		clickinput.val(nickName);
		clickinput[0].setAttribute("data-id",selectId);
	}
	$('#userDialog').modal('hide');

}

//选择订单，返回订单id
function selectOrder(){
	var table = $("#order-table");
	var checkeds = table.find("tbody .checkboxes:checked");
	if(checkeds.length<=0){
		return -1;
	}else{
		var orderid =checkeds.eq(0).parents("tr").attr("id");
		return orderid;
	}
}


function initTable() {
	ordertable = PdkDataTable.init({
		"tableId": "order-table",
		"url": 'order/table_data',
		"searching":false,
		"processing":true,
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "hrefCode" },
			{ "data": "flowTypeName" },
			{ "data": "nickname" },
			{ "data": "realname" },
			{ "data": "phonenum" },
			{ "data": "adress" },
			{ "data": "payTypeName" },
			{ "data": "statusName" },
			{ "data": "startTime" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0,1,4,5,6,7]
		}, {
			"searchable": false,
			"targets": [2]
		}],
		"order": [
			[10, "desc"]
		]
	});
	var table = $('#order-table');
	table.on('draw.dt', function () {
		$(this).find(".checkboxes").uniform();
	});
	table.on('click', ' tbody td .row-details', function () {
		var nTr = $(this).parents('tr')[0];

	});
}


//粉丝用户表格
function userDetailTable(){
	var table = $("#user-table");
	usertable= PdkDataTable.init({
		"tableId": "user-table",
		"url": 'order/usertabel_data',
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "name" },
			{ "data": "realName" },
			{ "data": "sexName" },
			{ "data": "age" },
			{ "data": "phone" },
			{ "data": "registerTime" },
			{ "data": "unRegisterTime" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0,1,6]
		}, {
			"searchable": true,
			"targets": [2,3,6]
		}],
		"order": [
			[2, "asc"]
		]
	});

	var tableWrapper = jQuery('#user-table_wrapper');

	tableWrapper.find('.dataTables_length select').select2();
}


function initEmployeeTable(){
	var table = $("#employee-table");

	employeetable= PdkDataTable.init({
		"tableId": "employee-table",
		"url": 'order/employeetable/'+employeeclick,
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "name" },
			{ "data": "sexName" },
			{ "data": "phone" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0,1]
		}, {
			"searchable": true,
			"targets": [2,3]
		}],
		"order": [
			[2, "asc"]
		]
	});

	var tableWrapper = jQuery('#employee-table_wrapper');

	tableWrapper.find('.dataTables_length select').select2();
}


function initFlowTable(){
	var table = $("#flow-table");
	flowtable= PdkDataTable.init({
		"tableId": "flow-table",
		"url": 'order/flowtable_data',
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "code" },
			{ "data": "name" },
			{ "data": "memo" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0,1,4]
		}, {
			"searchable": true,
			"targets": [2,3]
		}],
		"order": [
			[2, "asc"]
		]
	});

	var tableWrapper = jQuery('#flow-table_wrapper');

	tableWrapper.find('.dataTables_length select').select2();
}

function clearRef(orgId) {
	$("#" + orgId).val(null);
	$("#" + orgId)[0].setAttribute("data-id","");
}


var _fromDate = null;
var _toDate = null;
function initDatePicker() {
	initDatePickerLangCn();

	var fromDate = $('#fromDate');
	var toDate = $('#toDate');

	fromDate.datepicker({
		rtl: Metronic.isRTL(),
		orientation: "top left",
		autoclose: true,
		language:"cn",
		format: "yyyy-mm-dd"
	});
	toDate.datepicker({
		rtl: Metronic.isRTL(),
		orientation: "top left",
		autoclose: true,
		language:"cn",
		format: "yyyy-mm-dd"
	});

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
