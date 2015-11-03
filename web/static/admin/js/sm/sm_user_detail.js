var selectId = null;

function initUser() {
	var form = $("#userDetailForm");

	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#userDetailPane',
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
					Metronic.unblockUI('#userDetailPane');
					var result = eval(data).result;
					var userId = eval(data).userId;
					if (result == "success") {
						$('#userDialog').modal('hide');
						alert("保存成功!");
						window.location.href = "sm/sm_user_detail/" + userId + "?funcActiveCode=CUSTOM&canEdit=false";
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
					Metronic.unblockUI('#userDetailPane');
					alert("保存失败!");
				}
			});
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		rules:{
			name:{
				required:true,
			},
			realName:{
				required:true,
			},
			status:{
				required:true
			},type:{
				required:true,
			},
			sex:{
				required:true,
			}
		},
		messages:{
			name:{
				required:"必填",
				maxlength:"请不要超过50位"
			},
			realName:{
				required:"必填",
				maxlength:"请不要超过50位"
			},
			status:{
				required: "必填"
			},
			type:{
				required: "必填"
			},
			sex:{
				required: "必填"
			}
		}
	});

	var dialog = $("#userDialog")

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
				target: '#userDetailPane',
				overlayColor: 'none',
				animate: true
			});
			$.ajax({
				url: "sm/sm_user/" + selectId,
				data: {},
				dataType: "json",
				async: true,
				type: "GET",
				success: function (data) {
					Metronic.unblockUI('#userDetailPane');
					var result = eval(data).result;
					if (result == "success") {
						var user = eval(data).data;
						form.find("[name = 'id']").val(user.id);
						form.find("[name = 'ts']").val(user.ts);
						form.find("[name = 'name']").val(user.name);
						form.find("[name = 'type']").select2("val", user.type);
						form.find("[name = 'realName']").val(user.realName);
						form.find("[name = 'sex']").select2("val", user.sex);
						form.find("[name = 'age']").val(user.age);
						form.find("[name = 'phone']").val(user.phone);
						form.find("[name = 'address']").val(user.address);
						form.find("[name = 'memo']").val(user.memo);
						form.find("[name = 'eventKey']").val(user.eventKey);
						form.find("[name = 'registerTime']").val(user.registerTime);
						form.find("[name = 'unRegisterTime']").val(user.unRegisterTime);
						form.find("[name = 'status']").select2("val", user.status);
						form.find("[name = 'sourceId']").val(user.sourceId);
						form.find("#edit-method").html("<input type='hidden' name='_method' value='PUT'></div>");
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					Metronic.unblockUI('#userDetailPane');
					$('#userDialog').modal('hide');
				}
			});
		}
	});
}

function initSelect2(type, status, sex) {
	$("#type").select2({minimumResultsForSearch: -1});
	$("#status").select2({minimumResultsForSearch: -1});
	$("#sex").select2({minimumResultsForSearch: -1});

	$("#type").select2("val", type);
	$("#status").select2("val", status);
	$("#sex").select2("val", sex);
}

function save() {
	$("#userDetailForm").submit();
}

function editUserInfo() {
	window.location.href = "sm/sm_user_detail/" + $("#id").val() + "?funcActiveCode=CUSTOM&canEdit=true";
}

function cancel() {
	history.back(-1);
}

function returnList() {
	window.location.href = "sm/sm_user?funcActiveCode=CUSTOM";
}