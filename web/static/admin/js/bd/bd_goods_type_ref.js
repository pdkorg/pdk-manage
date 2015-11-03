/**
 * Created by liangyh on 2015/8/23.
 */

var refSelectId;
var refSelectName;
function initGoodsTypeRefTable() {

    var goodsTypeTable = PdkDataTable.init({
        "tableId": "bd-goods-type-table",
        "url": 'bd/bd_goodsType/table_data',
        "columns": [
            { "data": "checked" },
            { "data": "index" },
            { "data": "code" },
            { "data": "name" },
            { "data": "statusName" },
            { "data": "memo" }
        ],

        "columnDefs": [{ // set default column settings
            'orderable': false,
            'targets': [0, 1, 5]
        }, {
            "searchable": false,
            "targets": [0, 1]
        }],
        "order": [
            [2, "asc"]
        ]
    });

    singleSelect4Table(goodsTypeTable);
}

function doConfirm() {

    var table = $('#bd-goods-type-table');

    var checkeds = table.find("tbody .checkboxes:checked");
    refSelectId =  checkeds.eq(0).parents("tr").attr("id");
    refSelectName = checkeds.eq(0).parents("tr").find("td:eq(3)").html();
    if (!refSelectId) {
        alert("请选择参照数据！");
        return;
    }


    var form = $("#goodsDetailForm");
    form.find("[name = 'goodstypeId']").val(refSelectId);
    form.find("[name = 'goodstypeName']").val(refSelectName);
    form.find("[name = 'goodstypeName']").valid();

    table.find(".checkboxes").attr("checked", false);
    table.find(".checkboxes").uniform();
    $("#goodsTypeDialog").modal("hide");
}