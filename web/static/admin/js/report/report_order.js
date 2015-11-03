var orderTable;
var table;

function initTable() {
    table = PdkDataTable.init({
        "tableId": "order-table",
        "url": "report/order/table_data?fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val(),
        "columns": [
            {"data": "index"},
            {"data": "orderDate"},
            {"data": "orderCount"},
            {"data": "totalMny"},
            {"data": "useCouponMny"},
            {"data": "feeMny"},
            {"data": "payedMny"},
            {"data": "unPayedMny"},
            {"data": "unPayedCountLink"}
        ],
        "pageLength": 20,
        "paging" : false,
        "searching" : false,
        "ordering"  : false

    });
}

function qryOrder() {
    if (table == null) {
        initTable();
    } else {
        table.ajax.url("report/order/table_data?fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val()).load();
    }
}

var _fromDate = null;
var _toDate = null;
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

        var fromDate = $('#fromDate');
        var toDate = $('#toDate');

        fromDate.on("hide", function (ev) {
            if (ev.date == null) {
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
        toDate.on("hide", function (ev) {
            if (ev.date == null) {
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
}

function reloadTableData() {
    table.ajax.url("report/order/table_data?fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val()).load();
}

function initOrderListTable() {
    orderTable = PdkDataTable.init({
        "tableId": "order-list-table",
        "url": "order/table_data",
        "columns": [
            { "data": "index" },
            { "data": "blankHrefCode" },
            { "data": "flowTypeName"},
            { "data": "nickname" },
            { "data": "realname" },
            { "data": "phonenum" },
            { "data": "payTypeName" },
            { "data": "statusName" }
        ],
        "ordering": false,
        "searching": false
    });
}

function showUnPayOrders(date) {
    orderTable.ajax.url("order/table_data?payStatus=0&fromDate=" + date + " 00:00:00&toDate=" + date + " 23:59:59" ).load(function() {
        $("#orderListDialog").modal("show");
    });
}


function hideUnPayOrders() {
    $("#orderListDialog").modal("hide");
}