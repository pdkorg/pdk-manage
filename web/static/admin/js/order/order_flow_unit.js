var currTreeLeafId = null;
var employeetable = null;
var tree = null;
//是否包含辅助角色
var type =0;

function initEvents(){
	$(".list-toggle").click(function(e){
		e.preventDefault();
		var isClose = $(this)[0].getAttribute("data-id");
		if(isClose==0){
			$(this).siblings(".list-detail").show();
			$(this).siblings(".memo").show();
			$(this).html("隐藏");
			$(this)[0].setAttribute("data-id","1");
		} else{
			$(this).siblings(".list-detail").hide();
			$(this).siblings(".memo").hide();
			$(this).html("显示");
			$(this)[0].setAttribute("data-id","0");
		}
	});


	$(".btn.blue-sharp").click(function(e){
		e.preventDefault();
		var clickObj = $(this)[0];
		var orderid = clickObj.getAttribute("data-orderid");
		var ts = clickObj.getAttribute("data-ts");
		var flowAction = clickObj.getAttribute("data-flowAction");
		var flowUnit = clickObj.getAttribute("data-flowunit");

		var msg = "确定要执行"+flowUnit+"操作吗？";
		if('2'==flowAction){
			msg = "确定要执行现金支付完成操作码？"
		}
		if(window.confirm(msg)){
			if('0'==flowAction){
				showEmployeeDialog(orderid,ts);
				return;
			}else if('1'==flowAction){
				showMnyDialog(orderid,ts);
				return;
			}
			$.ajax({
				url:"order/changeorderstatus",
				data:{
					ts:ts,orderId:orderid
				},
				dataType:"json",
				type:"POST",
				success:function(data){
					alert(data.message);
					if(data.result=='success'){
						loadOrderData(currTreeLeafId,1);
						updateNumTips();
					}
				}
			});
		}
	});
}

function updateNumTips(){
	var flowIds=tree.jstree()._model.data.root.children;
	window.console.info(flowIds);
	$.ajax({
		url:"order/flowUnit/getOrderNums",
		data:{flowUnitArr:flowIds.toString(),type:type},
		type:"POST",
		dataType:"json",
		success:function(data){
			tree.find("li a span").remove();
			if(!data || data.length==0){
				return;
			}

			for(var i= 0,l=data.length;i<l;i++){
				var id =data[i].id;
				var num = data[i].num;
				var tap = $("#"+id).find("a");
				if(!!tap){
					tap.append('<span class="flow-type-badge badge badge-danger">'+num+'</span>');
				}
			}
		}
	});

}

function reloadTree(){
	tree.jstree().destroy();
	initFlowTypeTree(type);
}

//去修改
function goEdit(){
	var selectBtn = $("#dia_confir_btn")[0];
	var orderid = selectBtn.getAttribute("data-orderid");
	window.location.href="order/editorder/"+orderid+"?funcActiveCode=DETAIL";
}
//金额确认按钮
function mnyConfirm(){
	var selectBtn = $("#dia_confir_btn")[0];
	var ts = selectBtn.getAttribute("data-ts");
	var orderid = selectBtn.getAttribute("data-orderid");
	$('#moneyDialog').modal('hide');
	$.ajax({
		url:"order/changeorderstatus",
		data:{
			ts:ts,orderId:orderid
		},
		dataType:"json",
		type:"POST",
		success:function(data){
			alert(data.message);
			if(data.result=='success'){
				loadOrderData(currTreeLeafId,1);
				updateNumTips();
			}
		}
	});
}

function roleChange(){
	var check = $("#roleChange")[0].checked;

	if(check){
		//tree.jstree().settings.core.data.url="order/flowUnit/treedata/2";
		//tree.jstree().refresh();
		type = 2;
	}else{
		type = 0;
	}
	tree.jstree().destroy();
	initFlowTypeTree(type);
}

var sitv=0;
function initFlowTypeTree(type) {
	tree = $('#flow-unit-tree');
	tree.jstree({
		"core": {
			"themes": {
				"responsive": false
			},
			'data' : {
				'url' : 'order/flowUnit/treedata/'+type,
				'data' : function (node) {
					return {
						'id' : node.id
					};
				}
			},
			'strings' : {
				'Loading ...' : '加载中...'
			}
		},
		"types": {
			"default": {
				"icon": "fa fa-folder icon-state-warning icon-lg"
			},
			"file": {
				"icon": "fa fa-file icon-state-warning icon-lg"
			}
		},
		"plugins": ["types"]
	});

	tree.on('loaded.jstree', function(e, data){
		var inst = data.instance;
		var root = inst.get_node(e.target.firstChild.firstChild.lastChild);
		if(!inst.is_leaf(root)) {
			var firstNode = inst.get_children_dom(root).eq(0);
			inst.select_node(firstNode);
			inst.disable_node(root);
		}else {
			inst.select_node(root);
		}
		//定时获取订单数量
		clearInterval(sitv);
		updateNumTips();
		sitv = self.setInterval("updateNumTips()", 60 * 1000);
	});

	tree.on("changed.jstree", function (e, data) {

		if(!data.node || currTreeLeafId == data.node.id) {
			return;
		}

		currTreeLeafId = data.node.id;

		if(currTreeLeafId != "root") {
			loadOrderData(currTreeLeafId,1);
		}else{
			loadOrderData("",1);
		}
	});
}

function loadOrderData(unitId,pageNum){

	$.ajax({
		url:"order/flowUnit/queryOrderData",
		data:{
			flowUnitId:unitId,pageNum:pageNum,type:type
		},
		dataType:"json",
		type:"POST",
		success:function(data){
			$('#orderList').empty();
			if(data==null){
				window.console.info("no data~");
				return;
			}
			var list = data.orderAggModel;
			if(list!=null){
				generateOrderHtml(list);
			}
			//设置分页显示
			var pageTool=$("#pageTool");
			if(data.pages==0){
				pageTool.hide();
			}else{
				pageTool.show();
			}
			var pageShow = $("#currentPage");
			pageShow.html(data.pageNum+'/'+data.pages);
			pageShow[0].setAttribute("data-pageNum",data.pageNum);
			pageShow[0].setAttribute("data-pages",data.pages);
		}
	});
}

function prePage(){
	var pageNum = $("#currentPage")[0].getAttribute("data-pageNum");
	if(pageNum<=1){
		alert("已经是第一页了噢~");
		return;
	}
	loadOrderData(currTreeLeafId,parseInt(pageNum)-1);
}
function nextPage(){
	var pageNum = $("#currentPage")[0].getAttribute("data-pageNum");
	var pages = $("#currentPage")[0].getAttribute("data-pages");
	if(pageNum>=pages){
		alert("已经是最后一页了噢~");
		return;
	}
	loadOrderData(currTreeLeafId,parseInt(pageNum)+1);
}

function generateOrderHtml(listData){
	var divHtml = "";
	$(listData).each(function(index,ele){
		//window.console.info("headId:"+ele.head.id);
		var head = ele.head;
		divHtml = divHtml.concat('<div class="padding-left-0"> <div class="portlet light bordered list-body"> <div class="portlet-title"> <div class="caption list-num" style="float: left;"> 订单编号:');
		divHtml = divHtml.concat('<a target="_Blank" href="order/orderdetail/'+head.id+'?funcActiveCode=DETAIL">'+head.code).concat('</a></div>');
		if(head.status!='3' && head.status!='4'){
			divHtml = divHtml.concat('<button class="btn blue-sharp" type="button" style="float: right; margin-top: 4px;" data-orderid="'+head.id+'" data-ts="'+head.tsIden+'" data-flowunit="'+head.deliveryStatus+'" data-flowAction="'+head.flowAction+'">');
			divHtml = divHtml.concat(head.deliveryStatus).concat('</button>');
		}
		divHtml = divHtml.concat('</div> <div class="row list-nr"> <div class="col-md-6">微信昵称：<font>');
		divHtml = divHtml.concat(isNull(head.nickname)).concat('</font> </div> <div class="col-md-6">用户姓名：<font>');
		divHtml = divHtml.concat(isNull(head.realname)).concat('</font> </div> </div> <div class="row list-nr"> <div class="col-md-6">联系电话：<font>');
		divHtml = divHtml.concat(isNull(head.phonenum)).concat('</font> </div> <div class="col-md-6">支付方式：<font>');
		divHtml = divHtml.concat(head.payTypeName).concat('</font> </div> </div> <div class="row list-nr"> <div class="col-md-6">配送地址：<font>');
		divHtml = divHtml.concat(head.adress).concat('</font> </div> <div class="col-md-6">派送环节：<font class="list-zt">');
		divHtml = divHtml.concat(head.deliveryStatus).concat('</font> </div> </div> <div class="row list-nr"> <div class="col-md-6">预约时间：<font>');
		divHtml = divHtml.concat(head.reserveTime).concat('</font> </div> <div class="col-md-6"> <span class="label label-success type">');
		divHtml = divHtml.concat(head.flowTypeName).concat('</span> </div> </div> ');

		divHtml = divHtml.concat('<div class="row list-nr"> <div class="col-md-6">订单金额：<font>');
		divHtml = divHtml.concat(isNull(head.mny)).concat('</font> </div></div>');


		divHtml = divHtml.concat('<div class="row list-nr"> <div class="col-md-6">业务员：<font>');
		divHtml = divHtml.concat(isNull(head.ywyName)).concat('</font> </div> <div class="col-md-6">客服：<font>');
		divHtml = divHtml.concat(isNull(head.waitertName)).concat('</font> </div> </div>');

		divHtml = divHtml.concat('<div class="row list-nr"> <div class="col-md-12">订单详情：<font class="list-toggle" data-id="0">显示</font> ');
		divHtml = divHtml.concat('<div class="row list-nr memo" style="display: none"> <div class="col-md-12">订单备注：<font>');
		divHtml = divHtml.concat(isNull(head.memo)).concat('</font> </div> </div>')
		divHtml = divHtml.concat('<table class="list-detail col-md-12" style="display: none;"> <tr> <th>购买地点</th> <th>商品名称</th> <th>数量</th> <th>备注</th> </tr>');
		var bodyList = ele.body;
		if(bodyList!=null){
			$(bodyList).each(function(index,bele){
				divHtml = divHtml.concat('<tr><td>').concat(bele.buyAdress).concat('</td><td>');
				divHtml = divHtml.concat(bele.name).concat('</td><td>');
				divHtml = divHtml.concat(bele.num).concat(bele.unitId).concat('</td><td>');
				divHtml = divHtml.concat(bele.memo).concat('</td></tr>');
			});
		}else{
			divHtml = divHtml.concat('<tr><td colspan="4" align="center">该订单没有添加商品</td></tr>');
		}
		divHtml = divHtml.concat('</table> </div> </div> <div class="line"></div> </div> </div>');

	});
	$('#orderList').html(divHtml);
	initEvents();
}

function isNull(obj){
	if(!obj){
		return "";
	}
	return obj;
}

function resizePortlet() {
	var baseHeight = Metronic.getViewPort().height - $('.page-header').outerHeight() - 30 - 52;
	if(baseHeight < 600) {
		baseHeight = 600;
	}
	var porletHeight = baseHeight - $('.page-footer').outerHeight() - $('.page-breadcrumb').outerHeight() ;
	$('#tree-portlet').height(porletHeight);
	var baseLeftHeight = porletHeight - $('.portlet-title').outerHeight() - 11;
	var scrollerBody = $('#tree-portlet').find('.portlet-body-scroller');
	scrollerBody.height(baseLeftHeight);
	scrollerBody.parent().height(baseLeftHeight);
}

function initEmployeeTable(){
	var table = $("#employee-table");

	employeetable= PdkDataTable.init({
		"tableId": "employee-table",
		"url": 'order/employeetable/1',
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

//点击员工参照确认按钮
function selectEmployee(){
	var table = $('#employee-table');

	var checkeds = table.find("tbody .checkboxes:checked");
	if(checkeds.length <= 0) {
		alert("请选择一条员工数据!");
		return;
	}
	var selectId = checkeds.eq(0).parents("tr").attr("id");

	var selectBtn = $("#employeeSelect")[0];
	var orderid = selectBtn.getAttribute("data-orderid");
	var ts = selectBtn.getAttribute("data-ts");

	$.ajax({
		url:"order/changeorderstatus",
		data:{
			ts:ts,orderId:orderid, ywyId:selectId
		},
		dataType:"json",
		type:"POST",
		success:function(data){
			alert(data.message);
			if(data.result=='success'){
				loadOrderData(currTreeLeafId,1);
				updateNumTips();
			}
		}
	});

	$('#employeeDialog').modal('hide');

}

function showEmployeeDialog(orderid,ts){
	if(employeetable == null){
		initEmployeeTable();
	}else {
		employeetable.ajax.url("order/employeetable/1").load();
	}
	$('#employeeDialog').modal('show');
	var selectBtn = $("#employeeSelect")[0];
	selectBtn.setAttribute('data-orderid',orderid);
	selectBtn.setAttribute('data-ts',ts);
}


var goodstable = null;
function showMnyDialog(orderid,ts){
	if(goodstable==null){
		goodsDetailTable(orderid);
	}else{
		goodstable.ajax.url("order/goodsdetail/"+orderid).load();
	}
	//显示表头金额
	$.ajax({
		url:"order/orderheadmny",
		data:{
			orderId:orderid
		},
		dataType:"json",
		type:"GET",
		success:function(data){
			if(data.result=='success'){
				$("#dia_orderMny").val(data.obj.mny);
				$("#dia_realCost").val(data.obj.actualMny);
				$("#dia_couponMny").val(data.obj.couponMny);
				$("#dia_feeMny").val(data.obj.feeMny);
			}else{
				alert(data.message);
			}
		}
	});
	var selectBtn = $("#dia_confir_btn")[0];
	selectBtn.setAttribute('data-orderid',orderid);
	selectBtn.setAttribute('data-ts',ts);
	$('#moneyDialog').modal('show');
}

//
function goodsDetailTable(orderid) {

	var table = $("#detail-table");
	goodstable= PdkDataTable.init({
		"tableId": "detail-table",
		"url": 'order/goodsdetail/'+orderid,
		"searching":false,
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "name" },
			{ "data": "num" },
			{ "data": "unitId"},
			{ "data": "goodsMny" },
			{ "data": "buyAdress"},
			{ "data": "memo" }
		],

		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0,1,7]
		}, {
			"searchable": true,
			"targets": [2]
		}],
		"order": [
			[2, "asc"]
		]
	});

	var tableWrapper = jQuery('#detail-table_wrapper');

	tableWrapper.find('.dataTables_length select').select2();
}