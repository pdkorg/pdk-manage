var action = null;
var selectId = null;
var table;
//var _basePath = "";
//function initParam(basePath) {
//    _basePath = basePath;
//}

function initTable() {
    table = PdkDataTable.init({
        "tableId": "user-table",
        "url": "sm/sm_user/table_data",
        "columns": [
            {"data": "checked"},
            {"data": "index"},
            {"data": "linkName"},
            {"data": "typeName"},
            {"data": "realName"},
            {"data": "sexName"},
            {"data": "age"},
            {"data": "phone"},
            {"data": "fullAddress"},
            {"data": "statusName"},
            {"data": "registerTime"},
            {"data": "unRegisterTime"},
        ],
        "pageLength": 20,
        "pagingType": "bootstrap_full_number",

        "columnDefs": [{ // set default column settings
            'orderable': false,
            'targets': [0, 1, 8]
        }, {
            "searchable": false,
            "targets": [0, 1]
        }],
        "order": [
            [9, "asc"]
        ]

    });
}

function clearQueryArg() {
}

function initSelect2() {
    $("#type").select2({minimumResultsForSearch: -1});
    $("#qryType").select2({minimumResultsForSearch: -1});
    $("#status").select2({minimumResultsForSearch: -1});
    $("#sex").select2({minimumResultsForSearch: -1});
}

function save() {
    $("#userDetailForm").submit();
}

function del() {
    var table = $('#user-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if (checkeds.length <= 0) {
        alert("请选择一行数据！");
    } else {
        var ids = [];
        var ts = [];
        $(checkeds).each(function () {
            ids.push($(this).parents("tr").attr("id"));
            ts.push($(this).parents("tr").attr("ts"))
        });
        if (window.confirm("确定要删除" + ids.length + "条数据吗？")) {

            var form = $("#userDelForm");

            var idData = $("#id-data");
            idData.empty();
            $(ids).each(function () {
                idData.append("<input type='hidden' name='ids' value='" + this + "'>");
            });
            $(ts).each(function () {
                idData.append("<input type='hidden' name='ts' value='" + this + "'>");
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
                        window.location.reload();
                    } else {
                        var tipMsg = "删除失败!";
                        var error = eval(data).errorMsg;
                        if (error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }
}

function onDetail(canEdit) {
    var table = $('#user-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if (checkeds.length <= 0) {
        alert("请选择一行数据！");
    } else {
        selectId = checkeds.eq(0).parents("tr").attr("id");
        window.location.href = "sm/sm_user_detail/" + selectId + "?funcActiveCode=CUSTOM&canEdit=" + canEdit;
    }
}

function showDetail(userId) {
    window.location.href = "sm/sm_user_detail/" + userId + "?funcActiveCode=CUSTOM&canEdit=false";
}

function initUser() {
}

function updateStatus(status) {

    var statusTxt;

    if (status == 0) {
        statusTxt = "启用";
    } else {
        statusTxt = "禁用";
    }

    var table = $('#user-table');

    var checkeds = table.find("tbody .checkboxes:checked");

    if (checkeds.length <= 0) {
        alert("请选择一行数据！");
    } else {
        var ids = [];
        var ts = [];
        $(checkeds).each(function () {
            ids.push($(this).parents("tr").attr("id"));
            ts.push($(this).parents("tr").attr("ts"))
        });
        if (window.confirm("确定要" + statusTxt + "这" + ids.length + "条数据吗？")) {

            var form = $("#userUpdateStatusForm");

            var idData = $("#user-status-id-data");
            idData.empty();
            $(ids).each(function () {
                idData.append("<input type='hidden' name='ids' value='" + this + "'>");
            });
            $(ts).each(function () {
                idData.append("<input type='hidden' name='ts' value='" + this + "'>");
            });

            $.ajax({
                url: $(form).attr("action") + "/" + status,
                data: $(form).serializeArray(),
                dataType: "json",
                async: true,
                type: "POST",
                success: function (data) {
                    var result = eval(data).result;
                    if (result == "success") {
                        alert(statusTxt + "成功");
                        window.location.reload();
                    } else {
                        var tipMsg = statusTxt + "失败!";
                        var error = eval(data).errorMsg;
                        if (error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }
    }
}

function enable() {
    updateStatus(0);
}

function disable() {
    updateStatus(1);
}

function qryUser() {
    if (table == null) {
        initTable();
    } else {
        var roleIds = $("#qrySelRole").select2("val");
        var ids = "";
        for (var i = 0; i < roleIds.length; i++) {
            ids += ";" + roleIds[i];
        }

        if (ids.length > 0) {
            ids = ids.substring(1);
        }
        var userName = encodeURIComponent($("#qryName").val());
        var userAddr = encodeURIComponent($("#qryAddr").val());
        table.ajax.url("sm/sm_user/table_data?qryType=" + $("#qryType").select2("val") + "&qryName=" + userName + "&fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val() + "&qryAddr=" + userAddr).load();
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

function clearUserArg() {
    $("#qryType").select2("val", -1);
    $("#qryName").val(null);
    $("#qryAddr").val(null);
    $("#fromDate").val(null);
    $("#toDate").val(null);
}

function reloadTableData() {
    table.ajax.url("sm/sm_user/table_data").load();
}

function showAddress(id) {
    var table = $('#user-table');
    var checkeds = table.find("tbody .checkboxes:checked");

    if (checkeds.length <= 0) {
        alert("请选择一行数据！");
    } else {
        selectId = checkeds.eq(0).parents("tr").attr("id");
        showAddressRef(selectId, function () {
            reloadTableData();
        });
    }
}