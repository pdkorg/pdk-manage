<%--
  Created by IntelliJ IDEA.
  User: hubo
  Date: 2015/8/7
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String resourcePath = request.getServletContext().getInitParameter("resource_path") + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>">
  <title>登录</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
  <link href="<%=resourcePath%>static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/admin/css/login.css" rel="stylesheet" type="text/css">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>
</head>

<body>
<div class="logo-title">
  <a href=""><img src="<%=resourcePath%>static/img/logo_lg.png" style="margin: auto;"/></a>
</div>
<div class="container width-400">

    <h2 class="login-title">系统登录</h2>

    <form id="loginForm" method="post" action="login" autocomplete="off">
        <div class="form-group">
            <div class="input-group input-group-lg">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input type="text" name="username" class="form-control" placeholder="用户名" value="${username}">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group input-group-lg">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" name="password" class="form-control" placeholder="密码">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group input-group-lg">
                <input type="text" name="validateCode" class="form-control" placeholder="验证码">
                <span id="validate-code-addon" class="input-group-addon"><img src="img/validate_code" onclick="javascript:refreshValidateCode(this)"></span>
            </div>

        </div>

        <div class="form-group">
            <button type="submit" data-loading-text="登录中..." class="btn btn-block btn-lg btn-login">登录</button>
        </div>

    </form>

</div>
<div class="footer text-center">
    <p id="copy-right">Copyright ©2015 整理者科技. All Rights Reserved</p>
    <p>京ICP备14001244号-1</p>
</div>

<script src="<%=resourcePath%>static/global/plugins/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js"  type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript" ></script>
<script src="<%=resourcePath%>static/admin/js/login.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $(function(){
       showErrorMsg("${errorMsg}");
    });
</script>
</body>
</html>
