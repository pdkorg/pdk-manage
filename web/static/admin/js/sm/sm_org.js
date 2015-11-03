var action = null;
var selectId = null;
var selTs = null;
var tree;
var currSelectNode;
function initOrgTree() {
	tree = $("#orgTree").jstree({
		"core": {
			"themes" : {
				"responsive": false
			},
			"data" : {
				"url" : "sm/sm_org_tree",
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
		}
		else if ( currSelectNode != null && (currSelectNode.id == data.node.id) ) {
			return;
		}

		loadOrgData( data.node.id );

		currSelectNode = data.node;
	});

	tree.on('loaded.jstree', function(e, data){
		var inst = data.instance;
		var root = inst.get_node(e.target.firstChild.firstChild.lastChild);
		if(!inst.is_leaf(root)) {
			var firstNode = inst.get_children_dom(root).eq(0);
			inst.select_node(firstNode);
			//inst.disable_node(root);
		}else {
			inst.select_node(root);
		}

		$(".portlet-body-scroller").css("overflow","auto");
		$(".portlet-body-scroller").css("overflow-x","auto");
		$(".portlet-body-scroller").css("overflow-y","hidden");
		$(".portlet-body-scroller").css("padding-bottom","15px");

	});

	var form = $("#orgDetailForm");
	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#orgDetailPane',
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
					Metronic.unblockUI('#orgDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						$('#orgDialog').modal('hide');
						alert("保存成功!");
						window.location.reload();
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
					Metronic.unblockUI('#orgDetailPane');
					alert("保存失败!");
				}
			});

		},
		rules:{
			code:{
				required:true
			},
			name:{
				required:true
			},
			info:{
				required:true
			}
		},
		messages:{
			code:{
				required:"必填",
				maxlength:"请不要超过20位"
			},
			name:{
				required:"必填",
				maxlength:"请不要超过50位"
			},
			info:{
				required:"必填",
				maxlength:"请不要超过400位"
			}
		}
	});
	var dialog = $("#orgDialog");

	dialog.on("hidden.bs.modal", function () {
		$(this).removeData("bs.modal");
		form.find("input").val("");
		validate.resetForm();
		form.find('.form-group').removeClass('has-error');
		form.find("#edit-method").empty();
		action = null;
	});
	dialog.on("show.bs.modal", function () {

		if(action == "edit") {
			Metronic.blockUI({
				target: '#orgDetailPane',
				overlayColor: 'none',
				animate: true
			});

			$.ajax({
				url: "sm/sm_org/" + selectId,
				data: {},
				dataType: "json",
				async: true,
				type: "GET",
				success: function (data) {
					Metronic.unblockUI('#orgDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						var org = eval(data).data;
						form.find("[name = 'id']").val(org.id);
						form.find("[name = 'ts']").val(org.ts);
						form.find("[name = 'code']").val(org.code);
						form.find("[name = 'name']").val(org.name);
						form.find("[name = 'info']").val(org.info);
						form.find("[name = 'memo']").val(org.memo);
						form.find("[name = 'parentId']").val(org.parentId);
						form.find("[name = 'status']").select2("val", org.status);
						form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					Metronic.unblockUI('#orgDetailPane');
					$('#orgDialog').modal('hide');
				}
			});
		} else {
			form.find("[name = 'id']").val(null);
			form.find("[name = 'ts']").val(null);
			form.find("[name = 'code']").val(null);
			form.find("[name = 'name']").val(null);
			form.find("[name = 'info']").val(null);
			form.find("[name = 'memo']").val(null);
			form.find("[name = 'status']").select2("val", 0);

			form.find("[name = 'parentId']").val(selectId);
		}
	});
}

function loadOrgData(id) {
	if ( id == null || id == "root" ) {
		$("#dispCode").val( null );
		$("#dispName").val( null );
		$("#dispInfo").val( null );
		$("#dispStatus").select2("val", 2);
		$("#dispMemo").val( null );

		selectId = null;
		selTs = null;
		return ;
	}


	$.ajax({
		url: "sm/sm_org/" + id,
		data: {},
		dataType: "json",
		async: true,
		type: "GET",
		success: function (data) {
			var success = data.result;
			var org = data.data;
			if (success) {
				$("#selNodeId").val(id);
				 initEditData(org);
			} else {
				alert("数据加载失败!");
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert("数据加载失败!");
		}
	});
}

function initEditData(org) {
	$("#dispCode").val( org.code );
	$("#dispName").val( org.name );
	$("#dispInfo").val( org.info );
	//$("#dispStatus").select2("val", org.status);
	if (org.status == 0) {
		$("#dispStatus").val("启用");
	}else {
		$("#dispStatus").val("停用");
	}
	$("#dispMemo").val( org.memo );

	selectId = org.id;
	selTs = org.ts;
}

function resizePortlet() {

	var baseHeight = Metronic.getViewPort().height - $('.page-header').outerHeight() - 30 - 52;

	if(baseHeight < 600) {
		baseHeight = 600;
	}

	var porletHeight = baseHeight - $('.page-footer').outerHeight() - $('.page-breadcrumb').outerHeight() ;
	$('#tree-portlet, #tree-node-info-portlet').height(porletHeight);

	var baseLeftHeight = porletHeight - $('.portlet-title').outerHeight() - 11;

	$('.portlet-body-scroller').height(baseLeftHeight);
	$('.portlet-body-scroller').parent().height(baseLeftHeight);
}

function save() {
	$("#orgDetailForm").submit();
}

function del() {
	if( selectId == null ) {
		alert("请选择组织！");
	} else if (currSelectNode.children.length > 0) {
		alert("非末级组织不允许删除!");
	} else {
		if(window.confirm("确定要删除选择条数据吗？")){
			var form = $("#orgDelForm");
			$.ajax({
				url: $(form).attr("action"),
				data: {id:selectId, ts:selTs, _method:"DELETE"},
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
						if(error != null && error != "") {
							tipMsg += "\n" + error;
						}
						alert(tipMsg);
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				}
			});
		}
	}
}

function edit() {
	if (currSelectNode != null && currSelectNode.id == 'root') {
		alert("组织根节点不允许编辑！");
	} else if( selectId == null ) {
		alert("请选择组织！");
	}else {
		action = "edit";
		$('#orgDialog').modal('show');
	}
}

function updateStatus(status) {

	var statusTxt;

	if(status == 0) {
		statusTxt = "启用";
	}else{
		statusTxt = "禁用";
	}

	if (currSelectNode != null && currSelectNode.id == 'root') {
		alert("组织根节点不允许编辑！");
	} else if( selectId == null ) {
		alert("请选择组织！");
	}else {
		if(window.confirm("确定要"+ statusTxt + "选中组织吗？")){
			var form = $("#orgUpdateStatusForm");
			$.ajax({
				url: $(form).attr("action") + "/" + status,
				data: {id:currSelectNode.id, ts:currSelectNode.ts, _method:"PUT"},
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
}

function enable() {
	updateStatus(0);
}

function disable() {
	updateStatus(1);
}

function reloadOrgTree() {
	tree.jstree("refresh",false,0);
}

function reloadSelOrgInfo() {
	if ( currSelectNode == null ) {
		return;
	}
	loadOrgData(currSelectNode.id);
}