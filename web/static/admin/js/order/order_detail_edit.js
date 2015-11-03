/**
 * Created by chengxiang on 15/8/25.
 */

var resourcePath = null;
var _token = null;
function initPath(path, token) {
    resourcePath = path;
    _token = token;
}

function initEvents(){

    //新增商品
    $("#new_order").click(function(e){
        e.preventDefault();
        $("#goodsdiv").append(generateBodyHtml());
        initAll();
    });
    //保存动作
    $('#editSave').click(function(e){
        e.preventDefault();
        $("#saveorderform").submit();
    });
    //取消保存
    $("#editCancel").click(function(){
        var id = $("#ordercode")[0].getAttribute("data-id");
        window.location.href="order/orderdetail/"+id+"?funcActiveCode=DETAIL";
    });


    //选择配送地址
    $("#addressSelect").click(function(){
        selectAddress();
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

function clearRef(type) {
    $("#"+type).val(null);
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

    if(addrTable ==null){
        initAddressTable(userId);
        $('#addressDialog').modal('show');
    }else{
        addrTable.ajax.url("bd/bd_address/table_data/"+userId).load();
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

function afterInitData(){
    //付款类型下拉框选中初始化
    var payType = $("#paytype");
    payType.select2("val",payType[0].getAttribute("data-val"));

    //商品单位下拉框初始化
    /**
    var unitselect = $("[name='unit']");
    $(unitselect).each(function(index,ele){
        var temp = ele.getAttribute('data-unit');
        var tempObj = "[data-unit='"+temp+"']"
        $(tempObj).select2("val",temp);

    });
     */

}

function initInput() {
    $(".mask_number").inputmask({
        "mask": "9",
        "repeat": 10,
        "greedy": false
    });

    $(".mask_decimal").inputmask('decimal', {
        rightAlignNumerics: false
    });
    $(".mask_phone").inputmask({
        "mask": "9",
        "repeat": 11,
        "greedy": false
    });
}

var unitSelectHtml = null;
function initUnitSelect(){

    $.ajax({
        url:"order/unitlistdata",
        dataType:"json",
        type:"get",
        success:function(datas) {
            unitSelectHtml = '';
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
                    bodystr = bodystr.concat("\"id\":\"").concat(getIptVal("detailId",index)).concat("\",");
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
                url:"order/editSaveOrder",
                data:{
                    orderid:$("#ordercode")[0].getAttribute("data-id"),
                    ts:$("#ordercode")[0].getAttribute("data-ts"),
                    ordercode:$("#ordercode").val(),paytype:$("#paytype").val(),reservetime:$("#reservetime").val(),
                    ordermny:$("#ordermny").val(),realcostmny:$("#realcostmny").val(),feemny:$("#feemny").val(),
                    onsalemny:$("#onsalemny").val(),payState:$("#payState").val(),memo:$("#memo").val(),
                    managerid:$("#managerid").val(), orderaddress:$("#orderaddress").val(),bodydata:bodystr
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
            if (elename=='ordertypename'|| elename=='orderaddress' ) {
                element.parent().parent('div').append(error);
            } else {
                element.parent('div').append(error);
            }
        },
        rules:{
            ordertypename:{
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
            ordertypename:{
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

function getIptVal(name,i){
    var temp = $(".row.list-body").find("[name='"+name+"']");
    if(!temp||temp.length==0||i>=temp.length){
        return "";
    }
    return temp[i].value;
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
    resultHtml= resultHtml+'<img name="goodsImg" src=""> </div> <div>';
    resultHtml= resultHtml+'<span class="btn default btn-file">';
    resultHtml= resultHtml+'<span class="fileinput-exists">选择 </span>';
    resultHtml= resultHtml+'<input type="text" name="imageFile" onclick="uploadImg(this)">';
    resultHtml= resultHtml+'</span><a href="javascript:;" class="btn red fileinput-exists" onclick="removeImg(this)">移除</a> </div> </div> </div> </div>';
    resultHtml= resultHtml+'<div class="col-md-10"> <div class="row list-row">';
    resultHtml= resultHtml+'<div class="col-md-6"> <div class="form-group">';
    resultHtml= resultHtml+'<label class="control-label col-md-2 list-bt">名称</label>';
    resultHtml= resultHtml+'<div class="col-md-10">';
    resultHtml= resultHtml+'<input type="text" name="goodsName" class="form-control" maxlength="50" placeholder="请输入商品名称">';
    resultHtml= resultHtml+'</div> </div> </div> <div class="col-md-6">';
    resultHtml= resultHtml+'<div class="form-group"> <label class="control-label col-md-2 list-bt">数量</label>';
    resultHtml= resultHtml+'<div class="col-md-10"> <input type="text" name="num" class="form-control mask_decimal" placeholder="请输入数量" value="0" onchange="mnyOnchange()">';
    resultHtml= resultHtml+'</div> </div> </div> </div> <div class="row list-row"> <div class="col-md-6"> <div class="form-group">';
    resultHtml= resultHtml+'<label class="control-label col-md-2 list-bt">总价</label>';
    resultHtml= resultHtml+'<div class="col-md-10">';
    resultHtml= resultHtml+'<input type="text" name="goodsMny" class="form-control mask_decimal" placeholder="请输入商品总价" value="0" onchange="mnyOnchange()"></div> </div> </div>';

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