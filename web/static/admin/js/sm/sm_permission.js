var currSelectNode;
var roleId;
var ts;
function initRoleTree() {
	tree = $("#role-tree").jstree({
		"core": {
			"themes" : {
				"responsive": false
			},
			"data" : {
				"url" : "sm/sm_role_tree",
				"data" : function (node) {
					return {"id" : node.id};
				}
			},
			"multiple":false,
			"strings" : {
				"loading..." : "加载中..."
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

	tree.on('changed.jstree', function(e, data) {
		if ( data == null || data.node == null ) {
			return;
		} else if ( currSelectNode != null && (currSelectNode.id == data.node.id) ) {
			return;
		}

		initSelFuncList(data.node.id);
		currSelectNode = data.node;
	});

	tree.on('loaded.jstree', function(e, data){
		var inst = data.instance;
		var root = inst.get_node(e.target.firstChild.firstChild.lastChild);
		if(!inst.is_leaf(root)) {
			var firstNode = inst.get_children_dom(root).eq(0);
			inst.select_node(firstNode);
			inst.disable_node(root);
		}else {
			inst.select_node(root);
		}

		$(".portlet-body-scroller").css("overflow","auto");
		$(".portlet-body-scroller").css("overflow-x","auto");
		$(".portlet-body-scroller").css("overflow-y","hidden");
	});
}

function initMultiSelect() {
	$('#func-select').multiSelect({
		selectableHeader: "待选择功能节点",
		selectionHeader: "已选择功能节点",
		selectableOptgroup: true
	});
}

function initFuncList() {
	$.ajax({
		url: "sm/sm_permission/data_func_tree",
		type:"GET",
		dataType:'json',
		success: function(datas) {
			initList(eval(datas).menus);
			initMultiSelect();
		},
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
       	}
	});
}

function initList(menus) {

	if(isNull(menus)) {
		return;
	}

	var menuHtml = "";
	$.each(menus, function() {

		var name = this.name;
		var childrens = this.children;
		menuHtml += "<optgroup label='"+name+"'>";

		if(!isNull(childrens) && childrens.length > 0) {
			$.each(childrens, function() {
				menuHtml += "<option value="+this.id+">" + this.name + "</option>";
			});
		}else {
			menuHtml += "<option value="+this.id+">" + name + "</option>";
		}

		menuHtml += "</optgroup>";

	});
	$("#func-select").empty().html(menuHtml);

}

function onSelAll() {
	$("#func-select").multiSelect("select_all");
}

function onDisSelAll() {
	$("#func-select").multiSelect("deselect_all");
}

function initSelFuncList(roleId) {
	$.ajax({
		url: "sm/sm_permission/permission_func_list/" + roleId,
		data: {},
		dataType: "json",
		async: true,
		type: "POST",
		success: function (data) {
			onDisSelAll();

			for (var idx = 0; idx < data.funcIds.length; idx++) {
				$("#func-select").multiSelect("select", data.funcIds[idx]);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}

function savePermissionFuncList() {
	var selIds = $("#func-select").val();

	if ( currSelectNode == null || currSelectNode.id == null ) {
		alert("请选择一个角色!");
	} else	if(window.confirm("确定要保存吗？")){
		var form = $("#permissionSaveForm");
		var idData = $("#id-data");
		idData.empty();
		idData.append("<input type='hidden' name='roleId' value='"+ currSelectNode.id +"'>");

		var cnt = 0;
		$(selIds).each(function(){
			idData.append("<input type='hidden' name='ids' value='"+this+"'>");
			cnt++;
		});

		if(cnt==0) {
			idData.append("<input type='hidden' name='ids' value=''>");
		}

		$.ajax({
			url: $(form).attr("action"),
			data: $(form).serializeArray(),
			dataType: "json",
			async: true,
			type: "POST",
			success: function (data) {
				var result = eval(data).result;
				if (result == "success") {
					alert("保存成功");
				} else {
					var tipMsg = "保存失败!";
					var error = eval(data).errorMsg;
					if(error != null && error != "") {
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