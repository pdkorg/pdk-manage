var currSelectNode;
var tree;
var qryOrgId;
var qryOrgName;
function initOrgRef(isEnableOnly) {
	var uri = "sm/sm_org_tree";
	if ( isEnableOnly ) {
		uri = "sm/sm_org_tree_enable";
	}

	tree = $("#orgRefTree").jstree({
		"core": {
			"themes" : {
				"responsive": false
			},
			"data" : {
				"url" : uri,
				"data" : function (node) {
				}
			},
			"multiple":false,
			"strings" : {
				"loading..." : "加载中..."
			},
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
		$(".portlet-body-scroller").css("padding-bottom","15px");
	})

	tree.bind('changed.jstree', function(e, data) {
		if (data.node) {
			currSelectNode = data.node;
		}
	});

	tree.bind('dblclick.jstree', function(e, data) {
		selectRef();
	});
}

function showOrgTreeRefDlg(orgId, orgNameId, type) {
	$("#orgRefTree").jstree("destroy");
	initOrgRef(type);

	qryOrgId = orgId;
	qryOrgName = orgNameId;
	$('#orgRefDialog').modal("show");
}

function selectOrgRef() {
	if (currSelectNode == null) {
		return;
	} else if (currSelectNode.children.length > 0) {
		//alert("不允许选择非末级组织!");
		//return;
	}

	$('#orgRefDialog').modal('hide');
	$("#" + qryOrgId).val(currSelectNode.id);
	$("#" + qryOrgName).val(currSelectNode.text);
	$("#" + qryOrgName).trigger("change");
}