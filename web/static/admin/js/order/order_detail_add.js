var usertable = null;

var imgIpt = null;

var resourcePath = null;
var _token = null;
function initPath(path, token) {
	resourcePath = path;
	_token = token;
}

function initEvents(){

	//用户选择确定按钮点击事件
	$("#userSelect").click(function(){
		selectUser();
	});
	//订单类型
	$("#flowSelect").click(function(){
		selectFlowType();
	});

	//选择配送地址
	$("#addressSelect").click(function(){
		selectAddress();
	});

	//新增商品
	$("#new_order").click(function(e){
		e.preventDefault();
		$("#goodsdiv").append(generateBodyHtml());
		initAll();
	});
	//保存动作
	$('#saveBtn').click(function(e){
		e.preventDefault();
		$("#saveorderform").submit();
	});
	//取消新增操作
	$('#cancelBtn').click(function(){
		window.location.href="order/orderindex?funcActiveCode=DETAIL";
	});

	//提交表单
	initValidate();

	$("#uploadFile").change(function() {
		if(this.value == null || this.value == '') {
			return;
		}
		submitImageUploadForm();
	});
	$("#imageUploadForm").find("[name='path']").val("/order/img/"+new Date().Format("yyyyMMdd"));
}


//新增保存订单，由于存在表格数据，不知道怎么用form表单传表格数据，用js构造数据后传给后台


//选择用户操作
function selectUser(){
	var table = $('#user-table');

	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一条用户数据！");
		return;
	}
	var selectId = checkeds.eq(0).parents("tr").attr("id");
	var nickName = checkeds.eq(0).parents("tr")[0].children[2].innerHTML;
	var realname = checkeds.eq(0).parents("tr")[0].children[3].innerHTML;
	var sexName = checkeds.eq(0).parents("tr")[0].children[4].innerHTML;
	var phone = checkeds.eq(0).parents("tr")[0].children[6].innerHTML;

	$("#username").val(realname);
	$("#userid").val(selectId);
	$("#userphone").val(phone);
	$("#usersex").val(sexName);
	$("#nickname").val(nickName);

	$('#userDialog').modal('hide');
	$('#nickname').valid();


	$.ajax({
		url:"order/userAddress",
		data:{
			userId:selectId
		},
		type:"GET",
		success:function(data){
			if(data.result=="success"){
				$("#orderaddress").val(data.address);
				$("#orderaddress").valid();
			}else{
				alert(data.message);
			}
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}


function showUserRefDialog(){
	if(usertable==null){
		initUserTable();
	}else{
		usertable.ajax.url("order/usertabel_data").load();
	}
	$('#userDialog').modal('show');
}

function initInput() {
	$(".mask_number").inputmask({
		"mask": "9",
		"repeat": 10,
		"greedy": false
	});

	$(".mask_decimal").inputmask('decimal', {
		rightAlignNumerics: false,
		digits:2,
		integerDigits:5
	});
	$(".mask_phone").inputmask({
		"mask": "9",
		"repeat": 11,
		"greedy": false
	});
}

function initRefButton() {

}


var addrTable = null;
function initAddressTable(userId) {
	addrTable = PdkDataTable.init({
		"tableId": "address-table",
		"url": "sm/sm_address/table_data/"+userId,
		"columns": [
			{ "data": "checked" },
			{ "data": "index" },
			{ "data": "receiver" },
			{ "data": "fullAddress" },
			{ "data": "isDefault" }
		],
		"paging" : false,

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

function showAddressRef() {

	var userId = $("#userid").val();
	if(!userId){
		alert("请先选择一位用户！");
		showUserRefDialog();
		return;
	}

	if(addrTable ==null){
		initAddressTable(userId);
		$('#addressDialog').modal('show');
	}else{
		addrTable.ajax.url("sm/sm_address/table_data/"+userId).load();
		$('#addressDialog').modal('show');
	}

}

function selectAddress(){
	var table = $('#address-table');

	var checkeds = table.find("tbody .checkboxes:checked");

	if(checkeds.length <= 0) {
		alert("请选择一条地址信息！");
		return;
	}else {
		var address = checkeds.eq(0).parents("tr")[0].children[3].innerHTML;
		$("#orderaddress").val(address);
	}

	$('#addressDialog').modal('hide');
	$('#orderaddress').valid()
}

function getIptVal(name,i){
	return $(".row.list-body").find("[name='"+name+"']")[i].value;
}

function mnyOnchange(){
	var orderMny = 0.00;
	var headDiv = $(".row.list-body");
	$(headDiv).each(function(index,ele){
		var tempMny =getIptVal("goodsMny",index);

		orderMny += parseFloat(tempMny);
	});
	$("#ordermny").val(orderMny);

}
function accMul(arg1,arg2)
{
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length}catch(e){}
	try{m+=s2.split(".")[1].length}catch(e){}
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

function initValidate(){
	$("#saveorderform").validate({
		submitHandler: function(form) {
			var headDiv = $(".row.list-body");
			var bodystr = "[";
			if(headDiv.length==0){
				//没数据
			}else{
				$(headDiv).each(function(index,ele){
					var imgUrlTemp = $(".row.list-body").find("[name='goodsImg']")[index].getAttribute("data-url");
					if(!imgUrlTemp){
						imgUrlTemp='';
					}
					bodystr = bodystr.concat("{\"name\":\"").concat(getIptVal("goodsName",index)).concat("\",");
					bodystr = bodystr.concat("\"num\":\"").concat(getIptVal("num",index)).concat("\",");
					bodystr = bodystr.concat("\"unitId\":\"").concat(getIptVal("unit",index)).concat("\",");
					bodystr = bodystr.concat("\"buyAdress\":\"").concat(getIptVal("address",index)).concat("\",");
					bodystr = bodystr.concat("\"goodsMny\":\"").concat(getIptVal("goodsMny",index)).concat("\",");
					bodystr = bodystr.concat("\"reserveTime\":\"").concat(getIptVal("restime",index)).concat("\",");
					bodystr = bodystr.concat("\"imgUrl\":\"").concat(imgUrlTemp).concat("\",");
					bodystr = bodystr.concat("\"memo\":\"").concat(getIptVal("memo",index)).concat("\"}");
					if(index!=headDiv.length-1){
						bodystr = bodystr.concat(",");
					}
				});
			}
			bodystr = bodystr.concat("]");
			console.info(bodystr);

			$.ajax({
				url:"order/saveorder",
				data:{
					userid:$("#userid").val(), ordercode:$("#ordercode").val(),
					ordertype:$("#ordertype").val(),paytype:$("#paytype").val(),feemny:$("#feemny").val(),
					ordermny:$("#ordermny").val(),realcostmny:$("#realcostmny").val(),reservetime:$("#reservetime").val(),
					onsalemny:$("#onsalemny").val(),memo:$("#memo").val(),managerid:$("#managerid").val(),
					orderaddress:$("#orderaddress").val(),bodydata:bodystr
				},
				dataType:"json",
				type:"POST",
				success:function(data){
					if(data.result=="success"){
						alert(data.message);
						window.location.href="order/orderdetail/"+data.orderid+"?funcActiveCode=DETAIL";
					}else{
						alert(data.message);
					}
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {

				}
			});
		},
		errorPlacement : function(error, element) {
			var elename = element.context.name;
			if ( elename == 'nickname'||elename == 'orderaddress') {
				element.parent().parent('div').append(error);
			} else {
				element.parent('div').append(error);
			}
		},
		rules:{
			nickname:{
				required:true
			},
			ordertype:{
				required:true
			},
			orderaddress:{
				required:true
			},
			paytype:{
				required:true
			}
		},
		messages:{
			nickname:{
				required:"必填"
			},
			ordertype:{
				required:"必填"
			},
			orderaddress:{
				required:"必填"
			},
			paytype:{
				required: "必填"
			}
		}
	});
}

function initUserTable(){
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
			"targets": [2,3]
		}],
		"order": [
			[2, "asc"]
		]
	});

	var tableWrapper = jQuery('#user-table_wrapper');

	tableWrapper.find('.dataTables_length select').select2();
}

var unitSelectHtml = null;
function initUnitSelect(){

	$.ajax({
		url:"order/unitlistdata",
		dataType:"json",
		type:"get",
		success:function(datas) {
			unitSelectHtml ='<select class="form-control select2me" name="unit">'
			var option = "<option value=''>请选择</option>";
			if(eval(datas).result == "success") {
				var roles = eval(datas).data;
				$(roles).each(function(){
					option += "<option value='"+this.name+"'>"+this.name+"</option>";
				});
			}
			unitSelectHtml = unitSelectHtml + option+' </select>';
		}
	});
}

function initFlowTypeList() {
	$.ajax({
		url:"order/flowlistdata",
		dataType:"json",
		type:"get",
		success:function(datas) {
			var ordertypeSelect = $("select[name='ordertype']").empty();
			var option = "<option value=''>请选择</option>";
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

function initAll(){
	initDatePicker();
	initInput();
	//$("select[name='unit']").select2();
}

function initDatePicker() {
	initDateTimePickerLangCn();
	if (jQuery().datepicker) {
		$('.date-picker').datetimepicker({
			rtl: Metronic.isRTL(),
			language: "zh-CN",
			orientation: "top left",
			autoclose: true,
			format: "yyyy-mm-dd hh:ii"
		});

		$('.date-picker').parent('.input-group').on('click', '.input-group-btn', function (e) {
			e.preventDefault();
			$(this).parent('.input-group').find('.date-picker').datetimepicker('show');
		});
	}
}



function clearRef(type) {
	if('user'==type){
		$("#userid").val(null);
		$("#username").val(null);
		$("#userphone").val(null);
		$("#usersex").val(null);
		$("#nickname").val(null);
	}else if('orderaddress'==type){
		$("#orderaddress").val(null);
	}

}

function deleteGoods(obj){
	$(obj).parents(".row.list-body").remove();
	mnyOnchange();
}

function uploadImg(obj){

	imgIpt = $(obj).parents(".fileinput.fileinput-exists").find("img");
	$("#uploadFile").trigger("click");
}
function submitImageUploadForm(){
	var form = $("#imageUploadForm");
	var options  = {
		url: resourcePath + "file/upload",
		success: sendImage
	};
	form.ajaxSubmit(options);
}

function sendImage(datas) {

	window.console.info( resourcePath + datas.fileUri);
	imgIpt[0].setAttribute("src",resourcePath + datas.fileUri);
	imgIpt[0].setAttribute("data-url",datas.fileUri);
}

function removeImg(obj){
	var tempImg = $(obj).parents(".fileinput.fileinput-exists").find("img")[0];
	tempImg.setAttribute("src","");
	tempImg.setAttribute("data-url","");
	$("#uploadFile").val("");
}

function generateBodyHtml(){

	var resultHtml = '<div class="row list-body">';
	resultHtml= resultHtml+'<div class="col-md-2">';
	resultHtml= resultHtml+'<div class="list-img">';
	resultHtml= resultHtml+'<div class="fileinput fileinput-exists" style="width: 90%;">';
	resultHtml= resultHtml+'<div class="fileinput-preview thumbnail" style="width: 100%; height: 160px; line-height: 160px;">';
	resultHtml= resultHtml+'<img name="goodsImg" src=""> </div> ';
	resultHtml= resultHtml+'<div><span class="btn default btn-file">';
	resultHtml= resultHtml+'<span class="fileinput-exists">选择 </span>';
	resultHtml= resultHtml+'<input type="text" name="imageFile" onclick="uploadImg(this)"></span>';
	resultHtml= resultHtml+'<a href="javascript:;" class="btn red fileinput-exists" onclick="removeImg(this)">移除</a> </div> </div> </div> </div>';
	resultHtml= resultHtml+'<div class="col-md-10"> <div class="row list-row">';
	resultHtml= resultHtml+'<div class="col-md-6"> <div class="form-group">';
	resultHtml= resultHtml+'<label class="control-label col-md-2 list-bt">名称</label>';
	resultHtml= resultHtml+'<div class="col-md-10">';
	resultHtml= resultHtml+'<input type="text" name="goodsName" class="form-control" placeholder="请输入商品名称" maxlength="50">';
	resultHtml= resultHtml+'</div> </div> </div> <div class="col-md-6">';
	resultHtml= resultHtml+'<div class="form-group"> <label class="control-label col-md-2 list-bt">数量</label>';
	resultHtml= resultHtml+'<div class="col-md-10"> <input type="text" name="num" class="form-control mask_decimal" maxlength="10" placeholder="请输入数量" value="0" onchange="mnyOnchange()">';
	resultHtml= resultHtml+'</div> </div> </div> </div> <div class="row list-row"> <div class="col-md-6"> <div class="form-group">';
	resultHtml= resultHtml+'<label class="control-label col-md-2 list-bt">总价</label>';
	resultHtml= resultHtml+'<div class="col-md-10">';
	resultHtml= resultHtml+'<input type="text" name="goodsMny" class="form-control mask_decimal" placeholder="请输入商品总价" maxlength="10" value="0" onchange="mnyOnchange()"></div> </div> </div>';

	resultHtml= resultHtml+'<div class="col-md-6"> <div class="form-group">';
	resultHtml= resultHtml+'<label class="control-label col-md-2 list-bt">单位</label>';
	resultHtml= resultHtml+'<div class="col-md-10">';
	resultHtml= resultHtml+'<input type="text" name="unit" class="form-control" placeholder="请输入商品单位" >'+'</div> </div> </div> </div>';

	resultHtml= resultHtml+'<div class="row list-row"><div class="col-md-6"> <div class="form-group">';
	resultHtml= resultHtml+'<label class="control-label col-md-2 list-bt">预约时间</label>';
	resultHtml= resultHtml+'<div class="col-md-10"> <div class="input-group">';
	resultHtml= resultHtml+'<input type="text" name="restime" class="form-control date-picker" placeholder="请选择日期">';
	resultHtml= resultHtml+'<span class="input-group-btn"> <button class="btn btn-default" type="button">';
	resultHtml= resultHtml+'<i class="fa fa-calendar"></i> </button> </span> </div> </div> </div> </div>';
	resultHtml= resultHtml+'<div class="col-md-6"> <div class="form-group"> <label class="control-label col-md-2 list-bt">购买地点</label>';
	resultHtml= resultHtml+'<div class="col-md-10">';
	resultHtml= resultHtml+'<input type="text" name="address" class="form-control" maxlength="200" placeholder="请输入购买地点">';
	resultHtml= resultHtml+'</div> </div> </div> </div> <div class="row list-row" style="margin-bottom: 0;">';
	resultHtml= resultHtml+'<div class="col-md-6"> <div class="form-group">';
	resultHtml= resultHtml+'<label class="control-label col-md-2 list-bt">备注</label> <div class="col-md-10">';
	resultHtml= resultHtml+'<input type="text" name="memo" class="form-control" maxlength="400" placeholder="请输入备注信息">';
	resultHtml= resultHtml+'</div> </div> </div> </div> </div> ';
	resultHtml= resultHtml+'<div class="list-close"> <button class="close" onclick="deleteGoods(this)" aria-label="Close" type="button">span aria-hidden="true"&gt;× </button> </div></div>';
	return resultHtml;
}