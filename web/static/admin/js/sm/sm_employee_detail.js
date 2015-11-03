var imgWidth = 370;
var action = null;
var selectId = null;
var validate = null;
var realWidth = null;
var realHeight = null;
var resourcePath = null;
var _token = null;
function initPath(path, token) {
	resourcePath = path;
	_token = token;
}

function initEmployee() {
	var form = $("#employeeDetailForm");

	validate = form.validate({
		submitHandler: function (form) {
			Metronic.blockUI({
				target: '#employeeDetailPane',
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
					Metronic.unblockUI('#employeeDetailPane');
					var result = eval(data).result;
					var employeeId = eval(data).employeeId;
					if (result == "success") {
						alert("保存成功!");
						window.location.href = "sm/sm_employee_detail/" + employeeId + "?funcActiveCode=USER&canEdit=false";
					} else {
						var tipMsg = "保存失败!";
						var error = eval(data).errorMsg;
						if (error != null && error != "") {
							tipMsg += "\n" + error;
						}
						alert(tipMsg);
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					Metronic.unblockUI('#employeeDetailPane');
					alert("保存失败!");
				}
			});
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		errorPlacement: function (error, element) {
			if (element.context.name == 'orgName') {
				element.parent().parent('div').append(error);
			} else {
				element.parent('div').append(error);
			}
		},
		rules: {
			code: {
				required: true,
			},
			name: {
				required: true,
			},
			password: {
				required: true,
			},
			orgName: {
				required: true
			},
			roleId: {
				required: true
			},
			positionId: {
				required: true
			},
			sex: {
				required: true
			},
			idCard: {
				chkIdCard: true
			},
			status: {
				required: true
			}
		},
		messages: {
			code: {
				required: "必填",
				maxlength:"请不要超过20位"
			},
			name: {
				required: "必填",
				maxlength:"请不要超过50位"
			},
			password: {
				required: "必填"
			},
			orgName: {
				required: "必填"
			},
			roleId: {
				required: "必填"
			},
			positionId: {
				required: "必填"
			},
			sex: {
				required: "必填"
			},
			idCard: {
				chkIdCard: "请正确输入您的身份证号码",
				maxlength:"请不要超过18位"
			},
			phone: {
				maxlength:"请不要超过11位"
			},
			status: {
				required: "必填"
			}
		}
	});
}

function save() {
	$("#employeeDetailForm").submit();
}

function initSelect2(roleIdVal, positionIdVal) {
	initRole(roleIdVal);
	initPosition(positionIdVal);

	$("#status").select2({minimumResultsForSearch: -1});
	$("#sex").select2({minimumResultsForSearch: -1});
}

function initSelVal(statusVal, sexVal) {
	$("#status").select2("val", statusVal);
	$("#sex").select2("val", sexVal);
}

function initRole(roleIdVal) {
	$.ajax({
		url:"sm/sm_role/status",
		dataType:"json",
		success:function(datas){
			var roleSelect = $("select[name=roleId]").empty();
			var option = "";
			if ( eval(datas).result == "success" ) {
				var roleUnits = eval(datas).data;
				$(roleUnits).each(function(){
					option += "<option value='" + this.id + "'>" + this.code + " " + this.name + "</option>";
				});

			}
			roleSelect.html(option).select2({
				minimumResultsForSearch: -1,
				formatNoMatches: function() {
					return "找不到匹配项目";
				}
			});
			roleSelect.select2("val", roleIdVal);
		}
	});
}

function initPosition(positionIdVal) {
	$.ajax({
		url:"bd/bd_position/list",
		dataType:"json",
		success:function(datas){
			var positionSelect = $("#positionId").empty();
			var option = "";
			if ( eval(datas).result == "success" ) {
				var positionUnits = eval(datas).data;
				$(positionUnits).each(function(){
					option += "<option value='" + this.id + "'>" + this.code + " " + this.name + "</option>";
				});

			}

			positionSelect.html(option).select2({minimumResultsForSearch: -1});
			positionSelect.select2("val", positionIdVal);
		}
	});
}

var srcWidth;
var srcHeight;
function headImgUpload() {
	$("#imageFile").trigger("click");
}
function submitImageUploadForm() {
	Metronic.blockUI({
		target: '#employeeDetailPane',
		overlayColor: 'none',
		animate: true
	});

	var form = $("#imageUploadForm");
	var options  = {
		url: resourcePath + "file/upload",
		success: sendImage
	};
	form.ajaxSubmit(options);
}


function sendImage(datas) {
	if (!datas.result) {
		alert(datas.errorMsg);
		Metronic.unblockUI('#employeeDetailPane');
		return;
	}

	Metronic.unblockUI('#employeeDetailPane');
	$("#imageFile").val("");

	$("#fileName").val(datas.fileUri);
	var bigImg = $(".bigImg");

	bigImg.empty();
	var img = new Image();
	img.src = resourcePath + datas.fileUri;
	img.id = "srcImg";

	// 图片加载成功后才进行组件初始化，防止找不到图片的真实宽高
	$(img).load(function() {
		realWidth = img.width;
		realHeight = img.height;

		if(img.width>=imgWidth) {
			img.height = (img.height * imgWidth) / img.width;
			img.width = imgWidth;
		}

		srcWidth = img.width;
		srcHeight = img.height;

		bigImg.append(img);
		initImage(img);

		$(".jcrop-holder").each(function() {
			$(this).css("text-align", "center");
			$(this).css("margin", "0 auto");
		});
	});


	showCutImg();
	Metronic.unblockUI('#cutImgPane');
}

function showCutImg() {
	$("#cutImgDialog").modal("show");
}

/**
 * 初始化头像剪切组件
 * @param img
 */
function initImage(img){
	var api = $.Jcrop('#srcImg',{
		onChange: showPreview,
		onSelect: showPreview,
		aspectRatio: 1,
		keySupport: false
	});

	// 设置默认选中区域
	var maxWidth = Number(img.width);
	var maxHeight = Number(img.height);

	var x, y,w, h;
	if ( maxWidth >= maxHeight ) {
		y = 1;
		x = (maxWidth - maxHeight) / 2;
		w = maxHeight - 2;
		h = maxHeight - 2;
	} else {
		y = (maxHeight - maxWidth) / 2;
		x = 1;
		w = maxWidth - 2;
		h = maxWidth - 2;
	}

	api.setSelect([x, y, w, h]);
}

/**
 * 动态记录坐标图片选中区域
 * @param obj
 */
function showPreview(obj) {
	var srcImg = $("#srcImg");
	var persentX = realWidth / srcWidth;
	var persentY = realHeight / srcHeight;

	$("#x").val(Math.round(persentX * obj.x));
	$("#y").val(Math.round(persentY * obj.y));
	$("#w").val(Math.round(persentX * obj.w));
	$("#h").val(Math.round(persentY * obj.h));
}

/**
 * 切图处理
 */
function imgChange() {
	if ($("#w").val() == 0 || $("#h").val() == 0) {
		alert("请选择头像区域");
		return;
	}

	Metronic.blockUI({
		target: '#cutImgPane',
		overlayColor: 'none',
		animate: true
	});

	$.ajax({
		url:"img/img_cut",
		dataType:"json",
		async: true,
		type: "POST",
		data:$("#crop_form").serializeArray(),
		success:function(datas){
			if ( datas.result == "success" ) {
				$("#showHeaderImg").attr("src", resourcePath + datas.headerImage);
				$("#headerImg").val(datas.headerImage);
			} else {
				alert(datas.errMsg);
			}

			$("#cutImgDialog").modal("hide");

			Metronic.unblockUI('#cutImgPane');
		}
	});
}

function editEmployeeInfo() {
	window.location.href = "sm/sm_employee_detail/" + $("#id").val() + "?funcActiveCode=USER&canEdit=true";
}
function returnList() {
	window.location.href = "sm/sm_employee?funcActiveCode=USER";
}

function cancel() {
	history.back(-1);
}

function initStart(score) {
	var options = {
		min	: 1,
		max	: 5,
		step: 1,
		value:score,
		enabled:false
	}
	$("#start").rater(options);
}

function initDescriptionTable() {
	var detailTable = PdkDataTable.init({
		"tableId": "employee-review-table",
		"url": 'sm/employee_review/detail_table_data/'+$("#id").val(),
		"columns": [
			{ "data": "index" },
			{ "data": "scoreStar" },
			{ "data": "description" },
			{ "data": "userName" }
		],
		"columnDefs": [{ // set default column settings
			'orderable': false,
			'targets': [0]
		}, {
			"searchable": false,
			"targets": [0, 1]
		}],
		"order": [
			[1, "asc"]
		]
	});

	detailTable.on('draw.dt', function () {

		$(".employeeReviewStar").each(function() {
			$(this).rater({
				min	: 1,
				max	: 5,
				step: 1,
				value:$(this).attr("score"),
				enabled:false
			});
		});
	});

}

function showEmployeeReviewDialog() {
	$("#employeeReviewDialog").modal("show");
}

function pointToAuxiliary() {
	window.location.href = "sm/sm_employee_auxiliary/" + $("#id").val() + "?funcActiveCode=USER";
}