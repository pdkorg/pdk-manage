/**
 * Created by hubo on 2015/8/11.
 */
function refreshValidateCode(obj) {
    obj.src="img/validate_code?"+Math.random();
}

$(function() {

    Validator.init();

    var form = $("#loginForm");

    form.validate({
        submitHandler: function(form) {
            $('[type="submit"]').button('loading');
            form.submit();
        },
        errorPlacement : function(error, element) {
            element.parent().parent('div').append(error);
        },
        rules:{
            username:{
                required:true
            },
            password:{
                required:true
            },
            validateCode:{
                required:true,
                rangelength:[4,4]
            }
        },
        messages:{
            username:{
                required:"请输入用户名"
            },
            password:{
                required:"请输入密码"
            },
            validateCode:{
                required: "请输入验证码",
                rangelength:"请输入4位验证码"
            }
        }
    });

    $("[name='validateCode']").inputmask({
        "mask": "9",
        "repeat": 4,
        "greedy": false
    });
});

function showErrorMsg(errorMsg) {
    if(errorMsg == null || errorMsg == "") {
        return;
    }

    Metronic.alert({
        container: ".container", // alerts parent container(by default placed after the page breadcrumbs)
        type: "danger",  // alert's type
        message: errorMsg,  // alert's message
        icon: "warning" // put icon before the message
    });
}

