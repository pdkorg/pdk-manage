var imgIpt = null;

function resizePortlet() {
	
	var baseHeight = Metronic.getViewPort().height - $('.page-header').outerHeight() - 30 - 52;
	
	if(baseHeight < 600) {
		baseHeight = 600;
        $(".wangEditor-textarea-container").height(100);
        $(".wangEditor-textarea").css("min-height", "100px");
	}else {
        $(".wangEditor-textarea-container").height(120);
        $(".wangEditor-textarea").css("min-height", "120px");
    }
	
	var porletHeight = baseHeight - $('.page-footer').outerHeight() + 10;

    var baseLeftHeight = porletHeight - $('.portlet-title').outerHeight() - 11;

    var leftHeight = baseLeftHeight - $('.nav-tabs').outerHeight() - 11;

    var rightHeight = baseLeftHeight - $('.chat-form').outerHeight() - 35 + 30;
	
	$('.chart-portlet').height(porletHeight);
	
	$('#chats').height(leftHeight);
	$('#chats .list-group').height(leftHeight);
	$('#chats .list-group').parent().height(leftHeight);
	
	
	$('.portlet-body-scroller').height(rightHeight);
	$('.portlet-body-scroller').parent().height(rightHeight);
	
	var chatOrderHeight = Metronic.getViewPort().height - 90;
	//
	$('.chart-order').height(chatOrderHeight);
	$('.chart-order').parent().height(chatOrderHeight);
}



function initDatePicker() {
    initDateTimePickerLangCn();
    $('.date-picker').datetimepicker({
        rtl: Metronic.isRTL(),
        language: "zh-CN",
        orientation: "top left",
        autoclose: true,
        format: "yyyy-mm-dd hh:ii"
    });

    $('#defaultrange').daterangepicker({
            parentEl:".page-quick-sidebar-wrapper",
            opens: "right",
            startDate: moment().subtract(6, 'days'),
            endDate: moment(),
            minDate: '01/01/2012',
            maxDate: '12/31/2020',
            ranges: {
                "今天": [
                    moment().format('YYYY-MM-DD'),
                    moment().format('YYYY-MM-DD')
                ],
                "昨天": [
                    moment().subtract(1, 'days').format('YYYY-MM-DD'),
                    moment().format('YYYY-MM-DD')
                ],
                "前7天": [
                    moment().subtract(6, 'days').format('YYYY-MM-DD'),
                    moment().format('YYYY-MM-DD')
                ],
                "前30天": [
                    moment().subtract(29, 'days').format('YYYY-MM-DD'),
                    moment().format('YYYY-MM-DD')
                ],
                "本月": [
                    getMonthStartDate(),
                    getMonthEndDate()
                ],
                "上个月": [
                    getLastMonthStartDate(),
                    getLastMonthEndDate()
                ]
            },
            "locale": {
                "format": "MM/DD/YYYY",
                "separator": " - ",
                "applyLabel": "确定",
                "cancelLabel": "取消",
                "fromLabel": "从",
                "toLabel": "到",
                "customRangeLabel": "自定义",
                "daysOfWeek": [
                    "日",
                    "一",
                    "二",
                    "三",
                    "四",
                    "五",
                    "六"
                ],
                "monthNames": [
                    "一月",
                    "二月",
                    "三月",
                    "四月",
                    "五月",
                    "六月",
                    "七月",
                    "八月",
                    "九月",
                    "十月",
                    "十一月",
                    "十二月"
                ],
                "firstDay": 1
            }

        },
        function (start, end) {
            var value = start.format('YYYY-MM-DD') + '~' + end.format('YYYY-MM-DD');
            $('#defaultrange input').val(value);
            changeDateRange(value);
        }
    );

    initOrderDateRange();

}

jQuery(document).ready(function() {
	loadInboxMsg();
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	Demo.init(); // init demo features
    QuickSidebar.init();
	window.onresize = function() {
		resizePortlet();
	};
    editor = $('#chat-input-content').wangEditor({
        menuConfig: [['insertExpression', 'insertChatImage', "viewSourceCode"]]
    });

	initDatePicker();
    Validator.init();

    initModifyPasswordDialog();

	var titleTextObj = $("#tab-name");
	
	$('#tab-orderFormPanel').click(function(){
		titleTextObj.text("新增订单");
	});
	
	$('#tab-userInfo').click(function(){
		titleTextObj.text("用户信息");
	});
	
	$('#tab-orderRecrod').click(function(){
		titleTextObj.text("订单记录");
	});

    $('.orderPanelTabContainer a.tab').click(function() {
        $('.orderPanelTabContainer a.tab').each(function() {
            $(this).removeClass('active');
        });
        $(this).addClass('active');
    });

    $("select[name='payStatus']").select2({minimumResultsForSearch:-1});

    setTimeout(resizePortlet, 300);

	loadQuickReply();

    initFlowTypeList();

    initAddress();

    initOrderForm();

    $("#payStatusBtnGroup").find("a").click(function() {
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        changePayStatus($(this).attr("data-val"));
    });

    $("#orderImageUploadForm").find("input[name='uploadFile']").change(function() {
        if(this.value == null || this.value == '') {
            return;
        }
        submitGoodsImageUploadForm();
    });

    $("#orderImageUploadForm").find("[name='path']").val("/order/img/"+new Date().Format("yyyyMMdd"));


    initUserEditDialog(function(userId) {
        loadUserInfo(userId);
    });

});

function setCurrOrderTimeOneHourDelay() {
    var d = new Date();
    d.setHours(d.getHours() + 1);
    $("input[name='reserveTime']").val(d.Format("yyyy-MM-dd hh:mm"));
    $("input[name='reserveTime']").datetimepicker('update');
    $("input[name='reserveTime']").datetimepicker('hide');
}

function setCurrOrderTimeHalfHourDelay() {
    var d = new Date();
    d.setMinutes(d.getMinutes() + 30);
    $("input[name='reserveTime']").val(d.Format("yyyy-MM-dd hh:mm"));
    $("input[name='reserveTime']").datetimepicker('update');
    $("input[name='reserveTime']").datetimepicker('hide');
}

function showDateTime() {
    $("input[name='reserveTime']").datetimepicker('show');
}

hideQuickReply = function() {
	$("#quickReply").find("ul").hide();
};

function initFlowTypeList() {
    $.ajax({
        url:"flow/flow_types/all",
        type:"get",
        dataType:"json",
        success:function(datas) {
            var flowTypeSelect = $("select[name='flowType']").empty();
            var option = "<option value=''>请选择</option>";
            if(eval(datas).result == "success") {
                var flowUnits = eval(datas).data;
                $(flowUnits).each(function(){
                    option += "<option value='"+this.id+"'>"+this.code + "  " + this.name+"</option>";
                });
            }
            flowTypeSelect.html(option).select2();
        }
    });
}

function loadQuickReply() {
	$.ajax({
		url:"bd/bd_quick_reply",
		type:"get",
		dataType:"json",
		success:function(datas) {
			var ulObj = $("#quickReply").find("ul");
			$.each(eval(datas).data, function(){
				ulObj.append("<li class='msg'>"+this.info+"</li>");
			});
			ulObj.find("li").click(function() {
				editor.html($(this).text());
				$(this).parent().hide();
			});
		}
	});
}

function loadUserInfo(userId) {

    if(userId == null ) {
        var formBody = $("#userInfo").find(".form-body").empty();
        formBody.html('<h3 class="form-section">用户信息</h3>');
        formBody.html('<p>无用户信息</p>');
        $("[name='deliverAddress']").val(null);
        return;
    }

    $.ajax({
        url:"sm/sm_user_all_info/" + userId + "?r=" + new Date().getTime(),
        type:"get",
        dataType:"json",
        success:function(datas) {
            var resultData = eval(datas);
            if(resultData.result == "success") {
                setUserInfo(resultData.data);
            }
        }
    });

}

function setUserInfo(userInfo) {
    var formBody = $("#userInfo").find(".form-body").empty();

    $("[name='deliverAddress']").val(null);

    var html = '<h3 class="form-section">用户信息</h3>';

    html += '<div class="actions"><a href="javascript:;" class="btn btn-default" onclick="userEdit(\''+userInfo.DT_RowId+'\')"><i class="fa fa-jpy"></i> 用户维护 </a></div>';

    html += getUserInfoItemHtml("name", "昵称", userInfo.name, true);

    html += getUserInfoItemHtml("sex", "性别", userInfo.sexName, true);

    html += getUserInfoItemHtml("phone", "电话号码", userInfo.phone, true);

    var userDescription = "";

    $.each(userInfo.describeList, function() {
        userDescription += this.vdescribe + ",";
    });

    userDescription = userDescription.substring(0,userDescription.length - 1);

    html += getUserInfoItemHtml("userDescription", "用户描述", userDescription, false);

    html += '<h3 class="form-section">用户地址</h3>';

    html += '<div class="actions"><a href="javascript:;" onclick="addUserAddress()" class="btn btn-default btn-sm" ><i class="fa fa-plus"></i> 新增地址 </a></div>';

    $.each(userInfo.addressList, function(index, el) {
        html += '<div id="'+ this.id +'" class="panel panel-default">';
        html += '<div class="panel-heading">';
        html += '<h3 class="panel-title">';
        html += '<i class="fa fa-truck"></i> 地址 ' + (index + 1) + "&nbsp;&nbsp;&nbsp;";
        if(this.isDefault == "Y") {
            html += '<input type="radio" name="adress" checked class="make-switch" data-size="small">';
            $("[name='deliverAddress']").val(this.fullAddress);
        }else {
            html += '<input type="radio" name="adress" class="make-switch" data-size="small">';
        }
        html += '<div class="btn-group"><a class="btn btn-xs" href="javascript:;" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-gears"></i> 操作 <i class="fa fa-angle-down"></i></a><ul class="dropdown-menu pull-right"><li><a href="javascript:;" onclick="updateUserAddress(\''+this.id+'\')"><i class="fa fa-pencil"></i> 修改 </a></li><li><a href="javascript:;" onclick="delUserAddress(\''+this.id+'\')"><i class="fa fa-trash-o"></i> 删除 </a></li></ul></div>';
        html += '</h3>';
        html += '</div>';
        html += '<div class="panel-body">'+ this.fullAddress +'</div>';
        html += '</div>';
    });

    formBody.html(html);

    $('[name = "userDescription"]').tagsInput({
        'width': 'auto',
        'onAddTag': function(tag) {
            doAddTag(userInfo.DT_RowId, tag);
        },
        onRemoveTag:function(tag){
            doRemoveTag(userInfo.DT_RowId, tag);
        },
        'removeWithBackspace':false,
        'defaultText':''
    });

    $('.make-switch').bootstrapSwitch();

    $('.make-switch').on('switchChange.bootstrapSwitch', function (event, state) {
        if(state) {
            $.ajax({
                url:"sm/sm_address/set_default_address/"+$(this).parents(".panel").attr("id")+"?r=" + new Date().getTime(),
                data:{
                    userId:userInfo.DT_RowId
                },
                type:"post",
                dataType:"json"
            });
            $("[name='deliverAddress']").val($(this).parents(".panel").find(".panel-body").text());
        }
    });
}

function getUserInfoItemHtml(name, title, vaule, readonly) {
    return '<div class="form-group"><label class="control-label">'+title+'：</label><input type="text" name="'+name+'" class="form-control" value="' + vaule + '" '+ (readonly ? 'readonly' : '') +'/></div>';
}

function doAddTag(userId, tag) {
    $.ajax({
        url:"sm/sm_user_describe?r=" + new Date().getTime(),
        data:{
            userId:userId,
            describe:tag
        },
        type:"post",
        dataType:"json"
    });
}

function doRemoveTag(userId, tag) {
    $.ajax({
        url:"sm/sm_user_describe?r=" + new Date().getTime(),
        data:{
            userId:userId,
            describe:tag,
            _method:"DELETE"
        },
        type:"post",
        dataType:"json"
    });
}

function addUserAddress() {
    addressEdit(null, sendToId, function(){
        loadUserInfo(sendToId);
    });
}

function updateUserAddress(addressId) {
    addressEdit(addressId, sendToId, function(){
        loadUserInfo(sendToId);
    });
}

function delUserAddress(addressId) {
    addressDelete(addressId, function() {
        loadUserInfo(sendToId);
    });
}

function createNewOrderDetail() {
    var orderDetailListPanel = $("#orderDetailListPanel");

    var html = '<div class="row list-body"><div class="col-md-10">';
    html += getOrderDetailTextItem("名称", "name", "请输入商品名称");
    html += '<div class="row list-row">';
    html += getOrderDetailNumItem("数量","num","数量");
    html += getOrderDetailNumItem("价格","goodsMny","价格");
    html += '</div>';
    html += '<div class="row list-row">';
    html += getOrderDetailNumItem("单位","unit","单位");
    html += '</div>';
    html += getOrderDetailDateTimeItem("预约时间", "rt", "预约时间");
    html += getOrderDetailTextItem("购买地点", "address", "购买地点");
    html += getOrderDetailTextItem("备注信息", "info", "备注信息");
    html += '</div>';

    html += '<div class="col-md-2" style="padding: 0;"><div class="list-img">'+
        '<div class="fileinput fileinput-exists" style="width: 100%;"><div class="fileinput-preview thumbnail" style="width: 100%; height: 100px;"><img data-url="" name="goodsImg" src=""></div>' +
        '<div><span class="btn default btn-file"><span class="fileinput-exists">选择</span><input type="file" onclick="event.stopPropagation();event.preventDefault();uploadImg(this)"></span>'+
        '<a href="javascript:;" class="btn red fileinput-exists" onclick="removeImg(this)">移除</a>'+
        '</div></div></div></div>';

    html += '<div class="list-close"><button onclick="removeListBody(this)" class="close" aria-label="Close" data-dismiss="modal" type="button"><span aria-hidden="true">×</span></button></div>';
    html += '</div>';

    orderDetailListPanel.append(html);

    $(".list-body:last [name='name']").attr("maxLength", "50");
    $(".list-body:last [name='num']").inputmask('decimal', {
        rightAlignNumerics: false,
        digits: 2,
        integerDigits:5
    });
    $(".list-body:last [name='goodsMny']").inputmask('decimal', {
        rightAlignNumerics: false,
        digits: 2,
        integerDigits:5
    });
    $(".list-body:last [name='unit']").attr("maxLength", "4");
    $(".list-body:last [name='address']").attr("maxLength", "100");
    $(".list-body:last [name='info']").attr("maxLength", "200");

    var datetimepicker = $('.list-body:last .date-picker');

    datetimepicker.datetimepicker({
        rtl: Metronic.isRTL(),
        language: "zh-CN",
        orientation: "top left",
        autoclose: true,
        format: "yyyy-mm-dd hh:ii"
    });

    datetimepicker.parent('.input-group').on('click', '.input-group-btn', function (e) {
        e.preventDefault();
        $(this).parent('.input-group').find('.date-picker').datetimepicker('show');
    });

}

function submitGoodsImageUploadForm(){
    var form = $("#orderImageUploadForm");
    var options  = {
        url: resourcePath + "file/upload",
        success: setImageURL
    };
    form.ajaxSubmit(options);
}

function setImageURL(datas) {
    imgIpt.attr("src",resourcePath + datas.fileUri);
    imgIpt.attr("data-url",datas.fileUri);
    $("#uploadFile").val(null);
}

function uploadImg(obj){
    imgIpt = $(obj).parents(".fileinput.fileinput-exists").find("img");
    $("#uploadFile").click();
}

function removeImg(obj){
    var img = $(obj).parents(".fileinput.fileinput-exists").find("img");
    img.attr("src","");
    img.attr("data-url","");
    $("#uploadFile").val(null);
}

function removeListBody(obj) {
    if(confirm("是否确认删除？")){
        $(obj).parents(".list-body").remove();
    }
}

function getOrderDetailTextItem(title, name, placeholder) {
    return '<div class="row list-row"><div class="form-group"><label class="control-label col-md-2 list-bt">'+
        title+' </label><div class="col-md-10"><input name="'+
        name+'" type="text" class="form-control" placeholder="'+placeholder+'"></div></div></div>';
}

function getOrderDetailNumItem(title, name, placeholder) {
    return '<div class="col-md-6" style="padding: 0;"><div class="form-group"><label class="control-label col-md-4 list-bt">'+title+'</label>'+
        '<div class="col-md-8"><input name="'+name+'" type="text" class="form-control" placeholder="'+placeholder+'"></div></div></div>';
}

function getOrderDetailDateTimeItem(title, name, placeholder) {
    return '<div class="row list-row"><div class="form-group"><label class="control-label col-md-2 list-bt">'+
        title+' </label><div class="col-md-10"><div class="input-group"><input name="'+
        name+'" type="text" class="form-control date-picker" placeholder="'+placeholder+'" readonly><span class="input-group-btn"><button class="btn btn-default" type="button"><i class="fa fa-calendar"></i></button></span></div></div></div></div>';
}


var userOrderCache = {};

function initUserOrderCache(userId) {
    userOrderCache[userId] = {};
    userOrderCache[userId].flowType = '';
    userOrderCache[userId].payStatus = '';
    userOrderCache[userId].reserveTime = null;
    userOrderCache[userId].memo = null;
    userOrderCache[userId].orderDetailList = [];
}

function delUserOrderFromCache(userId) {
    delete userOrderCache[userId];
}

function OrderDetail(name, num, unit, address, info, img, rt, goodsMny){
    var orderDetail = {};
    orderDetail.name = name;
    orderDetail.num = num;
    orderDetail.unit = unit;
    orderDetail.address = address;
    orderDetail.info = info;
    orderDetail.img = img;
    orderDetail.rt = rt;
    orderDetail.goodsMny = goodsMny;
    return orderDetail;
}



function saveOrderToCache(userId) {
    if(userOrderCache[userId] == null) {
        return;
    }
    userOrderCache[userId].flowType = $("select[name='flowType']").select2("val");
    userOrderCache[userId].payStatus = $("select[name='payStatus']").select2("val");
    userOrderCache[userId].reserveTime = $("input[name='reserveTime']").val();
    userOrderCache[userId].memo = $("textarea[name='memo']").val();
    var orderDetailList = [];
    var orderDetailListPanel = $("#orderDetailListPanel");
    $(".list-body").each(function(){

        var name = $(this).find("[name='name']").val();
        var num = $(this).find("[name='num']").val();
        var goodsMny = $(this).find("[name='goodsMny']").val();
        var unit = $(this).find("[name='unit']").val();
        var address = $(this).find("[name='address']").val();
        var info = $(this).find("[name='info']").val();
        var rt = $(this).find("[name='rt']").val();
        var img = $(this).find("[name='goodsImg']").attr("data-url");

        orderDetailList.push(new OrderDetail(name, num, unit, address, info, img, rt, goodsMny));
    });
    userOrderCache[userId].orderDetailList = orderDetailList;
}

function setOrderValueByCache(userId) {
    $("select[name='flowType']").select2("val", userOrderCache[userId].flowType);
    $("select[name='payStatus']").select2("val", userOrderCache[userId].payStatus);
    $("input[name='reserveTime']").val(userOrderCache[userId].reserveTime);
    $("input[name='reserveTime']").datetimepicker('update');
    $("textarea[name='memo']").val(userOrderCache[userId].memo);
    var orderDetailListPanel = $("#orderDetailListPanel");
    orderDetailListPanel.empty();
    $.each(userOrderCache[userId].orderDetailList, function() {
        createNewOrderDetail();
        var el = orderDetailListPanel.find(".list-body:last");
        el.find("[name='name']").val(this.name);
        el.find("[name='num']").val(this.num);
        el.find("[name='goodsMny']").val(this.goodsMny);
        el.find("[name='unit']").val(this.unit);
        el.find("[name='address']").val(this.address);
        el.find("[name='info']").val(this.info);
        el.find("[name='rt']").val(this.rt);
        el.find("[name='goodsImg']").attr("src", resourcePath + this.img);
        el.find("[name='goodsImg']").attr("data-url", this.img);
    });

}

function transOrder2JsonStr(userId) {
    saveOrderToCache(userId);
    var json = {};
    var order = {};
    order.flowtypeId = userOrderCache[userId].flowType;
    order.payType = userOrderCache[userId].payStatus;
    order.reserveTime = getTime(userOrderCache[userId].reserveTime);
    order.memo = userOrderCache[userId].memo;
    order.userId = userId;

    var orderDetailList = [];

    var sumMny = 0;

    $.each(userOrderCache[userId].orderDetailList, function(){
        var orderDetail = {};
        orderDetail.name = this.name;
        orderDetail.num = this.num;
        orderDetail.unitId = this.unit;
        orderDetail.goodsMny = this.goodsMny;
        orderDetail.buyAdress = this.address;
        orderDetail.memo = this.info;
        orderDetail.reserveTime = this.rt;
        orderDetail.imgUrl = this.img;
        orderDetailList.push(orderDetail);
        if(orderDetail.goodsMny) {
            sumMny = sumMny + Number(orderDetail.goodsMny);
        }
    });

    order.mny = sumMny;

    json.head = order;

    json.body = orderDetailList;

    return JSON.stringify(json);

}



function initOrderForm() {

    var form = $("#orderForm");

    var validate = form.validate({
        submitHandler: function(form) {

            if(sendToId == null) {
                alert("当前没有用户，不能下单");
                return;
            }

            Metronic.blockUI({
                target: '#orderFormPanel',
                overlayColor: 'none',
                animate: true
            });

            $.ajax({
                url: $(form).attr("action"),
                data: {jsonStr:transOrder2JsonStr(sendToId)},
                dataType: "json",
                async: true,
                type: "POST",
                success: function (data) {
                    Metronic.unblockUI('#orderFormPanel');
                    var result = eval(data).result;
                    if (result == "success") {
                        alert("下单成功!");
                        initUserOrderCache(sendToId);
                        setOrderValueByCache(sendToId);
                        initQueryOrder(sendToId);
                    } else {
                        var tipMsg = "下单失败!";
                        var error = eval(data).message;
                        if(error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    Metronic.unblockUI('#orderFormPanel');
                    alert("下单失败!");
                }
            });
        },
        errorPlacement: function (error, element) {
            if (element.context.name == 'reserveTime') {
                element.parent().parent('div').append(error);
            } else {
                element.parent('div').append(error);
            }
        },
        rules:{
            flowType:{
                required:true
            },
            payStatus:{
                required:true
            },
            reserveTime:{
                required:true
            },
            deliverAddress : {
                required:true
            }
        },
        messages:{
            flowType:{
                required:"必填"
            },
            payStatus:{
                required:"必填"
            },
            reserveTime:{
                required:"必填"
            },
            deliverAddress : {
                required:"必填"
            }
        }
    });
}

var payStatus;

var dateRange;

var pageNo;

var totalPage;

var queryOrderListPanel = $("#queryOrderListPanel");

function initOrderDateRange() {
    $('#defaultrange input').val(moment().subtract(6, 'days').format('YYYY-MM-DD') + '~' + moment().format('YYYY-MM-DD'));
}

function initPayStatus() {
    $("#payStatusBtnGroup").find("a").each(function() {
        $(this).removeClass("active");
    });
    $("#payStatusBtnGroup").find("a:first").addClass("active");
}

function initQueryOrder(userId) {
    initOrderDateRange();
    initPayStatus();
    payStatus = -1;
    dateRange = $('#defaultrange input').val();
    pageNo = 1;
    totalPage = 0;
    queryOrder(userId);
}

function queryOrder(userId) {
    if(userId == null) {
        clearQueryOrder();
        return;
    }
    $.ajax({
        url:"cs/" + userId + "/order?r=" + new Date().getTime(),
        data:{
            payStatus:payStatus,
            dateRange:dateRange,
            pageNo:pageNo
        },
        type:"get",
        dataType:"json",
        success: function (datas) {
            var result = eval(datas);
            totalPage = result.totalPage;
            queryOrderListPanel.empty();

            $.each(result.data, function() {

                var itemHtml = '<div class="note note-info"><div class="row"><div class="col-md-4">';

                itemHtml+= '<h2>￥'+this.head.mny+'</h2>';
                var ps;
                if(this.head.payStatus == 0) {
                    ps = "未支付";
                }else {
                    ps = "已支付";
                }
                if(this.head.deliveryStatus) {
                    itemHtml+= '<h4>'+ps+'</h4><h4>'+this.head.deliveryStatus+'</h4></div>';
                } else {
                    itemHtml+= '<h4>'+ps+'</h4></div>';
                }

                itemHtml+= '<div class="col-md-8"><h4 style="line-height: 30px; padding-left: 16px;">';
                itemHtml+= '<a target="_blank" href="order/orderdetail/'+this.head.id+'?funcActiveCode=DETAIL">订单编号：'+this.head.code+'</a></h4>';
                itemHtml+= '<ul>';
                if(this.body != null && this.body.length != null && this.body.length > 0) {
                    $.each(this.body, function(){
                        itemHtml+='<li>' + this.name + ' * ' + this.num + this.unitId +'</li>';
                    });
                }
                itemHtml+= '</ul></div></div>';
                itemHtml+= '<div class="note-footer"><div class="row"><div class="col-md-3">';
                itemHtml+= '<span class="label label-danger">'+$("option[value='"+this.head.flowtypeId+"']").text()+'</span>';
                itemHtml+= '</div><div class="col-md-offset-4 col-md-5">';
                itemHtml+= '<h4 class="text-right">'+this.head.startTime+'</h4>';
                itemHtml+= '</div></div></div></div>';

                queryOrderListPanel.append(itemHtml);
            });

            if(result.data.length > 0) {
                var pageNav = '<nav><ul class="pager">';
                pageNav += '<li class="previous"><a href="javascript:void(0);" onclick="prePage()"><span aria-hidden="true">&larr;</span> 上一页</a></li>';
                pageNav +='<li><a>'+pageNo +'/'+ totalPage +'</a></li>';
                pageNav +='<li class="next"><a href="javascript:void(0);" onclick="nextPage()">下一页 <span aria-hidden="true">&rarr;</span></a></li>';
                pageNav +='</ul> </nav>';
                queryOrderListPanel.append(pageNav);
            }
        }
    });
}

function clearQueryOrder() {
    queryOrderListPanel.empty();
}

function nextPage() {
    pageNo = pageNo + 1;
    if(pageNo >= totalPage){
        pageNo = totalPage;
    }
    queryOrder(sendToId);
}

function prePage() {
    pageNo = pageNo - 1;
    if(pageNo <= 0){
        pageNo = 1;
    }
    queryOrder(sendToId);
}

function changePayStatus(ps) {
    payStatus = ps;
    pageNo = 1;
    queryOrder(sendToId);
}

function changeDateRange(daterange) {
    dateRange = daterange;
    pageNo = 1;
    queryOrder(sendToId);
}







