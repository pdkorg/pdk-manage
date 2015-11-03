
var table;
var employeetable;

function initTable() {
    var ywyId = $("#ywyName")[0].getAttribute("data-id");
    if(!ywyId){
        ywyId="";
    }
    table = PdkDataTable.init({
        "tableId": "order-table",
        "url": "report/ywymny/table_data?ywyId=" + ywyId + "&queryDate=" + $("#toDate").val(),
        "columns": [
            {"data": "hrefCode"},
            {"data": "ywyName"},
            {"data": "mny"},
            {"data": "tipMny"}
        ],
        "pageLength": 1000,
        "paging" : false,
        "searching" : false,
        "ordering"  : false

    });
}

function qryOrder() {
    if (table == null) {
        initTable();
    } else {
        var ywyId = $("#ywyName")[0].getAttribute("data-id");
        if(!ywyId){
            ywyId="";
        }
        table.ajax.url("report/ywymny/table_data?ywyId=" + ywyId + "&queryDate=" + $("#toDate").val()).load();
    }
}

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
    }
}

function reloadTableData() {
    qryOrder();
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
function selectEmployee() {
    var table = $('#employee-table');

    var checkeds = table.find("tbody .checkboxes:checked");
    if (checkeds.length <= 0) {
        alert("请选择一条员工数据!");
        return;
    }
    var selectId = checkeds.eq(0).parents("tr").attr("id");
    var ipt = $("#ywyName");
    var name = checkeds.eq(0).parents("tr")[0].children[3].innerHTML;
    ipt.val(name);
    ipt[0].setAttribute("data-id",selectId);
    $('#employeeDialog').modal('hide');
    qryOrder();
}

function clearRef(divId){
    $("#"+divId).val(null);
    $("#"+divId)[0].setAttribute("data-id","");

    qryOrder();
}