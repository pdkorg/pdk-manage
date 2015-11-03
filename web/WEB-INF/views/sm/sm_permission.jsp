<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/8/15
  Time: 16:21
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
  <title>跑的快 | 后台管理系统-权限管理</title>
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

  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/bootstrap-select/bootstrap-select.min.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/select2/select2.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/jquery-multi-select/css/multi-select.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/jstree/dist/themes/default/style.min.css"/>

  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/sm/permission.css"/>
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
          <div class="portlet light">
            <div class="portlet-title tabbable-line">
              <!--<div class="caption caption-md">
                  <i class="icon-globe theme-font hide"></i>
                  <span class="caption-subject font-blue-madison bold uppercase">权限管理</span>
              </div>-->
              <div class="caption">
                <i class="fa fa-list"></i>权限管理
              </div>
              <ul class="nav nav-tabs">
                <li class="active">
                  <a href="sm/sm_permission#tab_1_1" data-toggle="tab">节点权限</a>
                </li>
                <li>
                  <a href="sm/sm_permission.html#tab_1_2" data-toggle="tab">数据权限</a>
                </li>
              </ul>
            </div>
            <div class="portlet-body">
              <div class="tab-content">
                <div class="tab-pane form active" id="tab_1_1">
                  <form action="" class="form-horizontal">
                    <div class="form-body">
                      <h3 class="form-section">功能节点权限</h3>
                      <div class="row">
                        <div class="col-md-3">
                          <div class="scroller portlet-body-scroller" style="height: 569px;">
                            <div id="role-tree">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-8">
                          <div class="table-toolbar">
                            <div class="row">
                              <div class="col-md-12">
                                <div class="btn-group">
                                  <a id="new_order" class="btn btn-default" onclick="savePermissionFuncList()">
                                    保存 <i class="fa fa-save"></i>
                                  </a>
                                  <a type="button" class="btn btn-default" onclick="onSelAll()">
                                    全选 <i class="fa fa-check-circle-o"></i>
                                  </a>
                                  <a type="button" class="btn btn-default" onclick="onDisSelAll()">
                                    全消 <i class="fa fa-ban"></i></i>
                                  </a>
                                </div>
                              </div>
                            </div>
                          </div>
                          <select multiple="multiple" class="multi-select" id="func-select" name="my_multi_select2[]">
                          </select>
                        </div>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="tab-pane" id="tab_1_2">
                  <form action="" role="form">
                    <div class="form-body">

                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- END PAGE CONTENT-->
    </div>
  </div>
  <!-- END CONTENT -->
</div>

<form id="permissionSaveForm" action="sm/sm_permission" method="POST">
  <input type="hidden" name="_method" value="PUT"/>
  <div id="id-data">

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

<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/bootstrap-select/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script src="<%=resourcePath%>static/global/plugins/jstree/dist/jstree.min.js"></script>
<!-- END CORE PLUGINS -->
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/sm/sm_permission.js" type="text/javascript" charset="utf-8"></script>
<script>
  jQuery(document).ready(function () {
    loadInboxMsg();
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    Validator.init();
    initModifyPasswordDialog();
    setMenuItemActive("${requestScope.funcActiveCode}");
    initRoleTree();
    initFuncList();
  });
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
