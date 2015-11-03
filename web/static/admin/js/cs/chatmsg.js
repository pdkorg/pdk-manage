
var userIdComponent = $("#userId");
var userNameComponent = $("#userName");
var dataRangeComponent = $('#dataRange');

var contentComponent = $("#chatmsg-content");

var dataTable = null;

function initChatMsgItem() {
    initUserRef();
    initDataRange();
    initChatMsgTable();
}

function initChatMsgTable() {

    var table = $("#chatmsg-table");

    dataTable = table.DataTable({
        "language": {
            "sProcessing": "加载中...",
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

        paging: true,

        "ajax": {
            url: msgServerPath + "chatmsg/record",
            type: 'POST',
            data: buildRequestData
        },

        "serverSide": true,
        "searching" : false,
        "ordering"  : false,
        "bStateSave": true,

        "lengthMenu": [
            [20, 40, 60],
            [20, 40, 60]
        ],

        "pageLength": 20,

        "pagingType": "bootstrap_full_number",

        "columns":  [
            { "data": "index" },
            { "data": "createTime" },
            { "data": "csName" },
            { "data": "userName" },
            { "data": "content" },
            { "data": "typeName" },
            { "data": "sourceName" }
        ]
    });

    var tableWrapper = jQuery("#chatmsg-table_wrapper");

    tableWrapper.find('.dataTables_length select').select2({
        formatNoMatches: function() {
            return "找不到匹配项目";
        },
        minimumResultsForSearch:-1
    });

    tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline");
}

function buildRequestData(d) {

    d.userId = userIdComponent.val();

    var dataRange = dataRangeComponent.find("input[name='orderRange']").val();
    var content = contentComponent.val();

    if(dataRange != null && dataRange != "") {
        var datas = dataRange.split("~");
        d.startDateStr = datas[0] + " 00:00:00";
        d.endDateStr = datas[1] + " 23:59:59";
    }

    if(content != null && content != "") {
        d.content = content;
    }

}

function showUserRefDialog() {
    showUserRefDlg(userIdComponent.attr("id"), userNameComponent.attr("id"));
}

function clearUserQueryArg() {
    userIdComponent.val(null);
    userNameComponent.val(null);
}

function initDataRange() {
    dataRangeComponent.daterangepicker({
            opens: "left",
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
            dataRangeComponent.find("input[name='orderRange']").val(value);
        }
    );

    clearDateRangeQueryArg();
}

function clearDateRangeQueryArg() {
    dataRangeComponent.data('daterangepicker').setStartDate(moment().subtract(15, 'days'));
    dataRangeComponent.data('daterangepicker').setEndDate(moment());
    dataRangeComponent.find("input[name='orderRange']").val(null);
}

function clickQuery() {
    dataTable.draw();
}

function clearQueryArg() {
    clearUserQueryArg();
    clearDateRangeQueryArg();
    contentComponent.val(null);
}