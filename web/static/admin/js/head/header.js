/**
 * Created by hubo on 2015/8/11
 */

function initModifyPasswordDialog() {

    var form = $("#modifyPasswordForm");

    var validate = form.validate({
        submitHandler: function(form) {
            Metronic.blockUI({
                target: '#modifyPasswordPane',
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
                    Metronic.unblockUI('#modifyPasswordPane');
                    var success = eval(data).success;
                    var errorMsg = eval(data).errorMsg;
                    if (success) {
                        alert("更新成功!");
                        $('#modifyPasswordDialog').modal('hide');
                    } else {
                        alert(errorMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    Metronic.unblockUI('#modifyPasswordPane');
                    $('#modifyPasswordDialog').modal('hide');
                }
            });
        },
        rules:{
            password:{
                required:true
            },
            newPassword:{
                required:true
            },
            confirmPassword:{
                required:true,
                equalTo : "input[name='newPassword']"
            }
        },
        messages:{
            password:{
                required:"必填"
            },
            newPassword:{
                required:"必填"
            },
            confirmPassword:{
                required: "必填",
                equalTo: "两次密码输入不一致"
            }
        }
    });

    $("#modifyPasswordDialog").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
        form.find("input").val("");
        validate.resetForm();
        form.find('.form-group').removeClass('has-error');
    });

    $('#modifyPasswordBtn').on('click', function () {
        form.submit();
    });

}
