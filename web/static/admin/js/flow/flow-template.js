var table = null;

var currFlowTypeId = null;

var form = $("#flowTemplateUnitForm");

var action = null;

var selectId = null;

var validate = null;

var msgTemplateEditor = $('#flowEditor');

function init() {
    initFlowTypeTree();
    initFlowTemplateUnitValidator();
    initFlowTemplateEditor();
    initRoleList();
    initFlowUnitList();
    initFlowTemplateUnitDialog();
}

function initFlowTypeTree() {

    var tree = $('#flow-type-tree');

    tree.jstree({
        "core": {
            "themes": {
                "responsive": false
            },
            'data': {
                'url': 'flow/flow_template/flow_type_tree',
                'data': function (node) {
                    return {
                        'id': node.id
                    };
                }
            },
            'strings': {
                'Loading ...': '加载中...'
            }
        },
        "types": {
            "default": {
                "icon": "fa fa-folder icon-state-warning icon-lg"
            },
            "file": {
                "icon": "fa fa-file icon-state-warning icon-lg"
            }
        },
        "plugins": ["types"]
    });

    tree.on('loaded.jstree', function (e, data) {
        var inst = data.instance;
        var root = inst.get_node(e.target.firstChild.firstChild.lastChild);
        if (!inst.is_leaf(root)) {
            var firstNode = inst.get_children_dom(root).eq(0);
            inst.select_node(firstNode);
            inst.disable_node(root);
        } else {
            inst.select_node(root);
        }
    });

    tree.on("changed.jstree", function (e, data) {

        if (!data.node || currFlowTypeId == data.node.id) {
            return;
        }

        currFlowTypeId = data.node.id;

        if (currFlowTypeId != "root") {
            loadFlowUnitData();
        }
    });
}

function loadFlowUnitData() {

    form.find("input[name='flowTypeId']").val(currFlowTypeId);

    if (table == null) {
        initFlowTable();
    } else {
        table.ajax.url("flow/flow_template/" + currFlowTypeId).load();
    }

}

function resizePortlet() {
    var baseHeight = Metronic.getViewPort().height - $('.page-header').outerHeight() - 30 - 52;
    if (baseHeight < 600) {
        baseHeight = 600;
    }
    var treePortlet = $('#tree-portlet');
    var portletHeight = baseHeight - $('.page-footer').outerHeight() - $('.page-breadcrumb').outerHeight();
    treePortlet.height(portletHeight);
    var baseLeftHeight = portletHeight - $('.portlet-title').outerHeight() - 11;
    var scrollerBody = treePortlet.find('.portlet-body-scroller');
    scrollerBody.height(baseLeftHeight);
    scrollerBody.parent().height(baseLeftHeight);
}

function initFlowTable() {

    table = PdkDataTable.init({
        "tableId": "flow-table",
        "url": "flow/flow_template/" + currFlowTypeId,
        "columns": [
            {"data": "checked"},
            {"data": "index"},
            {"data": "flowUnitName"},
            {"data": "roleName"},
            {"data": "pushMsg"},
            {"data": "memo"}
        ],
        "autoWidth":false,
        "paging": false,
        "searching": false,
        "ordering" : false
    });
    table.on('xhr', function () {
        var json = table.ajax.json();
        form.find("input[name='templateId']").val(json.templateId);
    });
}

function initFlowTemplateEditor() {

    $("input[name='isPushMsg']").val("N");
    msgTemplateEditor.hide();

    $("#pushMsgFlag").click(function () {
        if ($(this).is(":checked")) {
            $("input[name='isPushMsg']").val("Y");
            initSummernote();
        } else {
            $("input[name='isPushMsg']").val("N");
            msgTemplateEditor.hide();
            msgTemplateEditor.val("");
        }
    });
}

function initRoleList() {
    $.ajax({
        url: "sm/sm_role/all",
        dataType: "json",
        type: "get",
        success: function (datas) {
            var roleSelect = $("select[name='roleId']").empty();
            var option = "<option value='-1'>请选择</option>";
            if (eval(datas).result == "success") {
                var roles = eval(datas).data;
                $(roles).each(function () {
                    option += "<option value='" + this.id + "'>" + this.code + "  " + this.name + "</option>";
                });
            }
            roleSelect.html(option).select2();
        }
    });
}

function initFlowUnitList() {
    $.ajax({
        url: "flow/flow_unit/all",
        dataType: "json",
        type: "get",
        success: function (datas) {
            var roleSelect = $("select[name='flowUnitId']").empty();
            var option = "<option value='-1'>请选择</option>";
            if (eval(datas).result == "success") {
                var flowUnits = eval(datas).data;
                $(flowUnits).each(function () {
                    option += "<option value='" + this.id + "'>" + this.code + "  " + this.name + "</option>";
                });
            }
            roleSelect.html(option).select2();
        }
    });
}

function initSummernote() {
    msgTemplateEditor.show();
}
function initFlowTemplateUnitDialog() {

    var dialog = $("#flowDialog");

    dialog.on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
        form.find("[name = 'id']").val("");
        form.find("[name = 'ts']").val("");
        form.find("[name = 'msgTemplate']").val("");
        form.find("textarea").val("");
        form.find("select").select2("val", "-1");
        form.find('.form-group').removeClass('has-error');
        form.find("#edit-method").empty();
        action = null;
        $("input[name='isPushMsg']").val("");
        msgTemplateEditor.val("");
        msgTemplateEditor.hide();
        $("#pushMsgFlag").attr("checked", false);
        jQuery.uniform.update($("#pushMsgFlag"));
        validate.resetForm();
    });

    dialog.on("show.bs.modal", function () {

        if (action == "edit") {
            Metronic.blockUI({
                target: '#flowTemplateUnitDetailPane',
                overlayColor: 'none',
                animate: true
            });
            $.ajax({
                url: "flow/flow_template/flow_template_unit/" + selectId,
                data: {},
                dataType: "json",
                async: true,
                type: "GET",
                success: function (data) {
                    Metronic.unblockUI('#flowTemplateUnitDetailPane');
                    var result = eval(data).result;
                    if (result == "success") {
                        var flowTemplateUnit = eval(data).data;
                        form.find("[name = 'id']").val(flowTemplateUnit.id);
                        form.find("[name = 'ts']").val(flowTemplateUnit.ts);
                        form.find("[name = 'memo']").val(flowTemplateUnit.memo);
                        form.find("[name = 'flowTypeId']").val(flowTemplateUnit.flowTypeId);
                        form.find("[name = 'templateId']").val(flowTemplateUnit.templateId);
                        form.find("[name = 'roleId']").select2("val", flowTemplateUnit.roleId);
                        form.find("[name = 'flowUnitId']").select2("val", flowTemplateUnit.flowUnitId);
                        form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
                        if (flowTemplateUnit.isPushMsg && flowTemplateUnit.isPushMsg == "Y") {
                            $("#pushMsgFlag").click();
                            msgTemplateEditor.val(flowTemplateUnit.msgTemplate);
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    Metronic.unblockUI('#flowTemplateUnitDetailPane');
                    $('#flowDialog').modal('hide');
                }
            });
        }

    });
}


function saveFlowTemplateUnit() {
    form.submit();
}

function initFlowTemplateUnitValidator() {
    validate = form.validate({
        submitHandler: function (form) {
            Metronic.blockUI({
                target: '#flowTemplateUnitDetailPane',
                overlayColor: 'none',
                animate: true
            });
            $.ajax({
                url: $(form).attr("action"),
                data: $(form).serializeArray(),
                dataType: "json",
                async: true,
                type: "POST",
                success: function (data) {
                    Metronic.unblockUI('#flowTemplateUnitDetailPane');
                    var result = eval(data).result;
                    if (result == "success") {
                        $('#flowDialog').modal('hide');
                        alert("保存成功!");
                        table.ajax.url("flow/flow_template/" + currFlowTypeId).load();
                    } else {
                        var tipMsg = "保存失败!";
                        var error = eval(data).errorMsg;
                        if (error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        if (eval(data).err_msgTemplate != null) {
                            tipMsg += "\n" + "消息模板最大字符不能超过400！";
                        }
                        alert(tipMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    Metronic.unblockUI('#flowTemplateUnitDetailPane');
                    alert("保存失败!");
                }
            });
        },
        rules: {
            flowUnitId: {
                remote: {
                    type: "POST",
                    url: "validate/id",
                    dataType: 'json',
                    data: {
                        id: function () {
                            return $("select[name='flowUnitId']").val();
                        }
                    }
                }
            },
            roleId: {
                remote: {
                    type: "POST",
                    url: "validate/id",
                    dataType: 'json',
                    data: {
                        id: function () {
                            return $("select[name='roleId']").val();
                        }
                    }
                }
            },
            memo: {
                maxlength: 200
            },
            msgTemplate : {
                maxlength: 200
            }
        },
        messages: {
            flowUnitId: {
                remote: "必填"
            },
            roleId: {
                remote: "必填"
            },
            memo: {
                maxlength: "最大长度200"
            },
            msgTemplate : {
                maxlength: "最大长度200"
            }
        }
    });
}


function editFlowTemplateUnit() {

    var checkeds = $('#flow-table').find("tbody .checkboxes:checked");

    if (checkeds.length <= 0) {
        alert("请选择一行数据！");
    } else {
        selectId = checkeds.eq(0).parents("tr").attr("id");
        action = "edit";
        $('#flowDialog').modal('show');
    }

}

function delFlowTemplateUnit() {

    var checkeds = $('#flow-table').find("tbody .checkboxes:checked");

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

            var form = $("#flowTemplateUnitDelForm");

            var idData = $("#id-data").empty();

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
                        table.ajax.url("flow/flow_template/" + currFlowTypeId).load();
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

function getFlowTemplateUnitId() {
    var checkeds = $('#flow-table').find("tbody .checkboxes:checked");

    var id = null;
    if (checkeds.length <= 0) {
        alert("请选择一行数据！");
    } else if(checkeds.length != 1){
        alert("目前只支持挪动一行！");
    } else {
       id = checkeds.eq(0).parents("tr").attr("id");
    }

    return id;
}

function upFlowTemplateUnit() {

    var flowTemplateUnitId = getFlowTemplateUnitId();

    if(flowTemplateUnitId != null) {
        $.ajax({
            url: "flow/flow_template/flow_template_unit/" + flowTemplateUnitId + "?action=up",
            dataType: "json",
            type: "post",
            success: function () {
                table.ajax.url("flow/flow_template/" + currFlowTypeId).load(function() {
                    $('#' + flowTemplateUnitId).find(".checkboxes").click();
                });
            }
        });
    }

}

function downFlowTemplateUnit() {
    var flowTemplateUnitId = getFlowTemplateUnitId();

    if(flowTemplateUnitId != null) {
        $.ajax({
            url: "flow/flow_template/flow_template_unit/" + flowTemplateUnitId + "?action=down",
            data: {},
            dataType: "json",
            type: "post",
            success: function () {
                table.ajax.url("flow/flow_template/" + currFlowTypeId).load(function() {
                    $('#' + flowTemplateUnitId).find(".checkboxes").click();
                });
            }
        });
    }

}

