<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/8/15
  Time: 12:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
  String resourcePath = request.getServletContext().getInitParameter("resource_path") + "/";
%>
<!DOCTYPE html>

<html lang="zh-CN">
<!-- BEGIN HEAD -->
<head>
  <base href="<%=basePath%>">
  <meta charset="utf-8"/>
  <title>跑的快 | 后台管理系统-用户管理</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8">
  <meta content="" name="description"/>
  <meta content="" name="author"/>
  <!-- BEGIN GLOBAL MANDATORY STYLES -->

  <link href="<%=resourcePath%>static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
  <!-- END GLOBAL MANDATORY STYLES -->

  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/select2/select2.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css"/>
  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>
  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-fixed page-sidebar-closed-hide-logo">
<!-- BEGIN HEADER -->
<jsp:include page="../base/header.jsp"/>
<!-- BEGIN CONTAINER -->
<div class="page-container">
  <!-- BEGIN SIDEBAR -->
  <jsp:include page="../base/menu.jsp"/>
  <!-- END SIDEBAR -->
  <!-- BEGIN CONTENT -->
  <div class="page-content-wrapper">
    <div class="page-content">
      <!-- BEGIN PAGE HEADER-->
      <!-- BEGIN PAGE HEAD -->
      <ul class="page-breadcrumb breadcrumb">
      </ul>
      <!-- END PAGE HEAD -->
      <!-- BEGIN PAGE BREADCRUMB -->
      <!-- END PAGE BREADCRUMB -->
      <!-- END PAGE HEADER-->
      <!-- BEGIN PAGE CONTENT-->
      <div class="row">
        <div class="col-md-12">
          <!-- BEGIN EXAMPLE TABLE PORTLET-->
          <div class="portlet light bordered">
            <div class="portlet-title">
              <div class="caption">
                <i class="fa fa-list"></i>用户列表
              </div>
              <div class="actions">

              </div>
              <div class="tools">
                <a href="javascript:;" class="reload" onclick="reloadTableData()">
                </a>
              </div>
            </div>
            <div class="portlet-body form">
              <form action="" class="form-horizontal">
                <div class="form-body">
                  <h3 class="form-section">用户查询</h3>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">用户类型：</label>

                        <div class="col-md-9">
                          <select id="qryType" class="form-control select2me">
                            <option value="-1">全部</option>
                            <option value="0">微信用户</option>
                            <option value="1">注册用户</option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">用户昵称：</label>
                        <div class="col-md-9">
                          <input type="text" id="qryName" name="qryName" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">注册(关注)时间：</label>
                        <div class="col-md-9">
                          <div class="input-group">
                              <input type="text" id="fromDate" name="fromDate" class="form-control date-picker" placeholder="请选择日期">
                            <span class="input-group-addon"> to </span>
                              <input type="text" id="toDate" name="toDate" class="form-control date-picker" placeholder="请选择日期">
                          </div>
						  <span class="help-block">选择时间段 </span>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6" >
                      <div class="form-group">
                        <label class="control-label col-md-3">地址：</label>
                        <div class="col-md-9">
                          <input type="text" id="qryAddr" name="qryAddr" class="form-control" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="row">
                        <div class="col-md-offset-3 col-md-9">
                          <button type="button" class="btn blue-sharp" onclick="qryUser()">查询</button>
                          <button type="button" class="btn btn-default" onclick="clearUserArg()">清空</button>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                    </div>
                  </div>
                  <h3 class="form-section">用户信息</h3>
                  <div class="table-toolbar">
                    <div class="row">
                      <div class="col-md-12">
                        <div class="btn-group">

                          <a id="edit_order" class="btn btn-default " onclick="onDetail(true)">
                            用户信息维护 <i class="fa fa-edit"></i>
                          </a>
                          <a id="edit_address" class="btn btn-default " onclick="showAddress()">
                            用户地址维护 <i class="fa fa-edit"></i>
                          </a>
                          <div class="btn-group">
                            <a type="button" class="btn btn-default" onclick="enable()"> 启用 <i class="fa fa-check-circle-o"></i></a>
                            <a type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i
                                    class="fa fa-angle-down"></i></a>
                            <ul class="dropdown-menu" role="menu">
                              <li>
                                <a onclick="disable()">
                                  禁用 <i class="fa fa-ban"></i></a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <table class="table table-striped table-bordered table-hover" id="user-table">
                    <thead>
                    <tr>
                      <th class="table-checkbox">
                        <input type="checkbox" class="group-checkable"
                               data-set="#user-table .checkboxes"/>
                      </th>
                      <th name="index">序号</th>
                      <th name="name">用户昵称</th>
                      <th name="typeName">用户类型</th>
                      <th name="realName">姓名</th>
                      <th name="sexName">性别</th>
                      <th name="age">年龄</th>
                      <th name="phone">电话</th>
                      <th name="fullAddress">地址</th>
                      <th name="statusName">状态</th>
                      <th name="registerTime">注册(关注)时间</th>
                      <th name="unRegisterTime">取消关注时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                  </table>
                </div>
              </form>
            </div>
          </div>
          <!-- END EXAMPLE TABLE PORTLET-->
        </div>
      </div>
      <!-- END PAGE CONTENT-->
    </div>
  </div>
  <!-- END CONTENT -->
</div>

<jsp:include page="../sm/sm_address_ref.jsp"/>

<form id="userUpdateStatusForm" action="sm/sm_user/status" method="POST">
  <input type="hidden" name="_method" value="PUT"/>
  <div id="user-status-id-data">

  </div>
</form>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<div class="page-footer">
  <div class="page-footer-inner">
    © 2015 zhenglizhe, Inc. Licensed under MIT license.
  </div>
  <div class="scroll-to-top">
    <i class="icon-arrow-up"></i>
  </div>
</div>
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="<%=resourcePath%>static/global/plugins/respond.min.js"></script>
<script src="<%=resourcePath%>static/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="<%=resourcePath%>static/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="<%=resourcePath%>static/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js"  type="text/javascript"></script>

<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script  src="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>

<script type="text/javascript" src="<%=resourcePath%>static/admin/pages/scripts/components-pickers.js"></script>

<!-- END CORE PLUGINS -->
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/pdk-table.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/sm/sm_user.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/sm/sm_address_ref.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<script src="<%=resourcePath%>static/admin/js/common/common-tools.js" type="text/javascript" charset="utf-8"></script>
<script>
  jQuery(document).ready(function () {
    loadInboxMsg();
    Metronic.init();
    Layout.init();
    Validator.init();
    initModifyPasswordDialog();
    initTable();

    setMenuItemActive("${requestScope.funcActiveCode}");
    initDatePicker();
    initPhoneValidatorMethod();

    initSelect2();

    initAddress();
  });
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->
</html>
