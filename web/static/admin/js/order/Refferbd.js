/**
 * Created by chengxiang on 15/8/28.
 */

var goodstable = null;
var flowtable = null;
var usertable = null;
var employeetable = null;

function initCommonRefEvents(){

    $("#ordertypediv").click(function(){
        showFlowRefDialog();
    });
    //订单类型
    $("#flowSelect").click(function(){
        selectFlowType();
    });

}


//选择用户操作
function selectUser(){
    var table = $('#user-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if(checkeds.length <= 0) {
        $("#username").val("");
        $("#userid").val("");
        $("#userphone").val("");
        $("#usersex").val("");
        $("#nickname").val("");
    }else {
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
    }
    $('#userDialog').modal('hide');

}
function showUserRefDialog(){
    if(usertable==null){
        initUserTable();
    }else{
        usertable.ajax.url("order/usertabel_data").load();
    }
    $('#userDialog').modal('show');
}

//选择订单类型
function selectFlowType(){

    var table = $('#flow-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if(checkeds.length <= 0) {
        $("#ordertypename").val("");
        $("#ordertype").val("");
    }else {
        var selectId = checkeds.eq(0).parents("tr").attr("id");
        var name = checkeds.eq(0).parents("tr")[0].children[3].innerHTML;

        $("#ordertypename").val(name);
        $("#ordertype").val(selectId);
    }

    $('#flowDialog').modal('hide');
}

function showFlowRefDialog(){
    if(flowtable==null){
        initFlowTable();
    }else{
        flowtable.ajax.url("order/flowtable_data").load();
    }
    $('#flowDialog').modal('show');
}



function goodsDetailTable() {
    var table = $("#goods-table");
    var orderid = $("#ordercode")[0].getAttribute("data-id");
    goodstable= PdkDataTable.init({
        "tableId": "goods-table",
        "url": 'order/goodsdetail/'+orderid,
        "columns": [
            { "data": "checked" },
            { "data": "index" },
            { "data": "name" },
            { "data": "num" },
            { "data": "buyAdress"},
            { "data": "price" },
            { "data": "servicePrice" },
            { "data": "otPrice" },
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

function initEmployeeTable(){
    var table = $("#employee-table");
    employeetable= PdkDataTable.init({
        "tableId": "employee-table",
        "url": 'order/employeetable_data',
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
            'targets': [0,4]
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



function restoreRow(oTable, nRow) {
    var aData = oTable.fnGetData(nRow);
    var jqTds = $('>td', nRow);

    for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
        oTable.fnUpdate(aData[i], nRow, i, false);
    }

    oTable.fnDraw();
}

function editRow(oTable, nRow) {
    var aData = oTable.fnGetData(nRow);
    var jqTds = $('>td', nRow);
    jqTds[0].innerHTML = '';
    jqTds[1].innerHTML = '<input type="text" class="form-control  input-tabel" value="' + aData[1] + '">';
    jqTds[2].innerHTML = '<input type="text" class="form-control mask_decimal  input-tabel" value="' + aData[2] + '">';
    jqTds[3].innerHTML = '<input type="text" class="form-control input-tabel" value="' + aData[3] + '">';
    jqTds[4].innerHTML = '<input type="text" class="form-control input-tabel" value="' + aData[4] + '">';
    jqTds[5].innerHTML = '<input type="text" class="form-control mask_decimal  input-tabel" value="' + aData[5] + '">';
    jqTds[6].innerHTML = '<input type="text" class="form-control mask_decimal  input-tabel" value="' + aData[6] + '">';
    jqTds[7].innerHTML = '<input type="text" class="form-control mask_decimal  input-tabel" value="' + aData[7] + '">';
    jqTds[8].innerHTML = '<input type="text" class="form-control input-tabel" value="' + aData[8] + '">';
    jqTds[9].innerHTML = '<a class="save_order btn btn-default btn-xs">保存 <i class="fa fa-edit"></i></a> <a class="cal_order btn btn-default btn-xs">取消 <i class="fa fa-times"></i></a>';

    $(jqTds).find(".mask_decimal").inputmask('decimal', {
        rightAlignNumerics: false
    });

}

function saveRow(oTable, nRow) {
    var jqInputs = $('input', nRow);
    oTable.fnUpdate('<input type="checkbox" class="checkboxes" value="1" />', nRow, 0, false);
    oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
    oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
    oTable.fnUpdate(jqInputs[2].value, nRow, 3, false);
    oTable.fnUpdate(jqInputs[3].value, nRow, 4, false);
    oTable.fnUpdate(jqInputs[4].value, nRow, 5, false);
    oTable.fnUpdate(jqInputs[5].value, nRow, 6, false);
    oTable.fnUpdate(jqInputs[6].value, nRow, 7, false);
    oTable.fnUpdate(jqInputs[7].value, nRow, 8, false);
    oTable.fnUpdate('<a class="edit_order btn btn-default btn-xs">修改 <i class="fa fa-edit"></i></a> <a class="del_order btn btn-default btn-xs">删除 <i class="fa fa-trash-o"></i></a>', nRow, 9, false);
    oTable.fnDraw();
    var jqTds = $('>td', nRow);
    $(jqTds).find(".checkboxes").uniform();
}

function initTable() {

    var table = $('#detail-table');
    // begin first table
    table.dataTable({
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

        "lengthMenu": [
            [-1],
            ["All"] // change per page values here
        ],
        // set the initial value
        "pageLength": 10,
        "pagingType": "bootstrap_full_number",
        "columnDefs": [{ // set default column settings
            'orderable': false,
            'targets': [0,8]
        }, {
            "searchable": false,
            "targets": [0]
        }],
        "order": [
            [1, "asc"]
        ] // set first column as a default sort by asc
    });

    var tableWrapper = jQuery('#detail-table_wrapper');

    table.find('.group-checkable').change(function() {
        var set = jQuery(this).attr("data-set");
        var checked = jQuery(this).is(":checked");
        jQuery(set).each(function() {
            if (checked) {
                $(this).attr("checked", true);
                //				$(this).parents('tr').addClass("active");
            } else {
                $(this).attr("checked", false);
                //				$(this).parents('tr').removeClass("active");
            }
        });
        jQuery.uniform.update(set);
    });

    tableWrapper.find('.dataTables_length select').select2();

    var nEditing = null;
    var nEdit = false;

    //保存动作
    $('#saveBtn').click(function(e){
        e.preventDefault();
        if (nEdit && nEditing) {
            if (confirm("存在未保存的行，是否保存?")) {
                saveRow(table, nEditing); // save
            } else {
                return;
            }
        }
        $("#saveorderform").submit();
    });

    $('#new_order').click(function(e) {
        e.preventDefault();

        if (nEdit && nEditing) {
            if (confirm("存在未保存的行，是否保存后新增行?")) {
                saveRow(table, nEditing); // save
            } else {
                return;
            }
        }
        var aiNew = table.fnAddData(['','', '0', '','', '0', '0', '0','','']);
        var nRow = table.fnGetNodes(aiNew[0]);
        editRow(table, nRow);
        nEditing = nRow;
        nEdit = true;
    });

    table.on('click', '.save_order', function (e) {
        e.preventDefault();

        var nRow = $(this).parents('tr')[0];

        if (nEditing == nRow) {
            saveRow(table, nEditing);
            nEditing = null;
        }
    });

    table.on('click', '.del_order', function (e) {
        e.preventDefault();

        if (confirm("确认是否删除此行?") == false) {
            return;
        }
        var nRow = $(this).parents('tr')[0];
        table.fnDeleteRow(nRow);
    });

    table.on('click', '.edit_order', function (e) {
        e.preventDefault();

        var nRow = $(this).parents('tr')[0];
        if (nEditing !== null && nEditing != nRow) {
            saveRow(table, nEditing);
            editRow(table, nRow);
        } else {
            editRow(table, nRow);
        }
        nEditing = nRow;
        nEdit = true;
    });

    table.on('click', '.cal_order', function (e) {
        e.preventDefault();
        saveRow(table, nEditing);
        nEditing = null;
        nEdit = false;
    });
}