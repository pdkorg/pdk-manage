var selectId = null;

function initUserEditDialog(callBack) {
	var form = $("#userEditDlgForm");
	var validate = form.validate({
		submitHandler: function(form) {
			Metronic.blockUI({
				target: '#userEditDlgPane',
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
					Metronic.unblockUI('#userEditDlgPane');
					var result = eval(data).result;
					var userId = eval(data).userId;
					if (result == "success") {
						$('#userEditDialog').modal('hide');
						alert("保存成功!");
						if (callBack) {
							callBack(userId);
						}
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
					Metronic.unblockUI('#userEditDlgPane');
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
			sex:{
				required: "必填"
			}
		}
	});

	var dialog = $("#userEditDialog")

	dialog.on("hidden.bs.modal", function () {
		$(this).removeData("bs.modal");
		form.find("input").val("");
		form.find("[name=_method]").val("PUT");
		validate.resetForm();
		form.find('.form-group').removeClass('has-error');
		action = null;
	});

	dialog.on("show.bs.modal", function () {
		Metronic.blockUI({
			target: '#userEditDlgPane',
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
				Metronic.unblockUI('#userEditDlgPane');
				var result = eval(data).result;
				if (result == "success") {
					var user = eval(data).data;
					form.find("[name = 'id']").val(user.DT_RowId);
					form.find("[name = 'ts']").val(user.tsStr);
					form.find("[name = 'name']").val(user.name);
					form.find("[name = 'realName']").val(user.realName);
					form.find("[name = 'sex']").select2("val", user.sex);
					form.find("[name = 'age']").val(user.age);
					form.find("[name = 'phone']").val(user.phone);
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				Metronic.unblockUI('#userEditDlgPane');
				$('#userEditDialog').modal('hide');
			}
		});
	});
}

function saveUser4Dlg() {
	$("#userEditDlgForm").submit();
}

function userEdit(selId) {
	selectId = selId;
	$("#userEditDialog").modal("show");
}