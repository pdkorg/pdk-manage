/**
 * Created by chengxiang on 15/8/23.
 */

var employeetable = null;

function initEvents(){

    $("#change_order_state").click(function(){
        changeState();
    });

    $("#edit_order").click(function() {
        var orderid = $("#ordercode")[0].getAttribute("data-id");
       window.location.href="order/editorder/"+orderid+"?funcActiveCode=DETAIL";
    });

}


//去修改
function goEdit(){
    var orderid = $("#ordercode")[0].getAttribute("data-id");
    window.location.href="order/editorder/"+orderid+"?funcActiveCode=DETAIL";
}
//金额确认按钮
function mnyConfirm(){
    var ts = $("#ordercode")[0].getAttribute("data-ts");
    var orderid = $("#ordercode")[0].getAttribute("data-id");
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
                window.location.reload();
            }
        }
    });
}

function changeState(){

    var flowAction =$("#change_order_state")[0].getAttribute("data-id");
    var actionName = $("#change_order_state")[0].getAttribute("data-name");
    var ts = $("#ordercode")[0].getAttribute("data-ts");
    var orderid = $("#ordercode")[0].getAttribute("data-id");
    var msg = "确定要执行"+actionName+"操作吗？";
    if('2'==flowAction){
        msg = "确定要执行现金支付完成操作码？"
    }
    if(window.confirm(msg)){
        if('0'==flowAction){
            showEmployeeDialog();
            return;
        }else if('1'==flowAction){
            showMnyDialog(orderid);
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
                    window.location.reload();
                }
            }
        });
    }
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

function showEmployeeDialog(){
    if(employeetable == null){
        initEmployeeTable();
    }else {
        employeetable.ajax.url("order/employeetable/1").load();
    }
    $('#employeeDialog').modal('show');
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

    var ts = $("#ordercode")[0].getAttribute("data-ts");
    var orderid = $("#ordercode")[0].getAttribute("data-id");

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
                window.location.reload();
            }
        }
    });

    $('#employeeDialog').modal('hide');

}

var goodstable = null;
function showMnyDialog(orderid){
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
    $('#moneyDialog').modal('show');
}

//
function goodsDetailTable(orderid) {

    var table = $("#detail-table");
    goodstable= PdkDataTable.init({
        "tableId": "detail-table",
        "searching":false,
        "url": 'order/goodsdetail/'+orderid,
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