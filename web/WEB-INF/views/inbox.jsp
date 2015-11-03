<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/9/10
  Time: 19:08
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
<!--[if IE 8]>
<html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
  <base href="<%=basePath%>" />
  <meta charset="utf-8"/>
  <title>跑的快 | 后台管理系统-首页</title>
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

  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/morris/morris.css"/>

  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>

  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/bootstrap-toastr/toastr.min.css"/>

  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>
</head>

<body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-fixed page-sidebar-closed-hide-logo">

  <jsp:include page="base/header.jsp" />
<!-- BEGIN CONTAINER -->
<div class="page-container">
  <!-- BEGIN SIDEBAR -->
  <jsp:include page="base/menu.jsp" />
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
      <div class="row margin-top-10">
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-green-sharp">7800
                  <small class="font-green-sharp">$</small>
                </h3>
                <small>总交易额</small>
              </div>
              <div class="icon">
                <i class="icon-pie-chart"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
								<span style="width: 76%;" class="progress-bar progress-bar-success green-sharp">
								<span class="sr-only">76% progress</span>
								</span>
              </div>
              <div class="status">
                <div class="status-title">
                  progress
                </div>
                <div class="status-number">
                  76%
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-red-haze">1349</h3>
                <small>NEW FEEDBACKS</small>
              </div>
              <div class="icon">
                <i class="icon-like"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
								<span style="width: 85%;" class="progress-bar progress-bar-success red-haze">
								<span class="sr-only">85% change</span>
								</span>
              </div>
              <div class="status">
                <div class="status-title">
                  change
                </div>
                <div class="status-number">
                  85%
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-blue-sharp">567</h3>
                <small>新订单数</small>
              </div>
              <div class="icon">
                <i class="icon-basket"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
								<span style="width: 45%;" class="progress-bar progress-bar-success blue-sharp">
								<span class="sr-only">45% grow</span>
								</span>
              </div>
              <div class="status">
                <div class="status-title">
                  grow
                </div>
                <div class="status-number">
                  45%
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-purple-soft">276</h3>
                <small>新用户数</small>
              </div>
              <div class="icon">
                <i class="icon-user"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
								<span style="width: 57%;" class="progress-bar progress-bar-success purple-soft">
								<span class="sr-only">56% change</span>
								</span>
              </div>
              <div class="status">
                <div class="status-title">
                  change
                </div>
                <div class="status-number">
                  57%
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6 col-sm-12">
          <!-- BEGIN PORTLET-->
          <div class="portlet light ">
            <div class="portlet-title">
              <div class="caption caption-md">
                <i class="icon-bar-chart theme-font-color hide"></i>
                <span class="caption-subject theme-font-color bold uppercase">交易总览</span>
                <span class="caption-helper hide">weekly stats...</span>
              </div>
              <div class="actions">
                <div class="btn-group btn-group-devided" data-toggle="buttons">
                  <label class="btn btn-transparent grey-salsa btn-circle btn-sm active">
                    <input type="radio" name="options" class="toggle" id="option1">今日</label>
                  <label class="btn btn-transparent grey-salsa btn-circle btn-sm">
                    <input type="radio" name="options" class="toggle" id="option2">周</label>
                  <label class="btn btn-transparent grey-salsa btn-circle btn-sm">
                    <input type="radio" name="options" class="toggle" id="option3">月</label>
                </div>
              </div>
            </div>
            <div class="portlet-body">
              <div class="row list-separated">
                <div class="col-md-3 col-sm-3 col-xs-6">
                  <div class="font-grey-mint font-sm">
                    总交易额
                  </div>
                  <div class="uppercase font-hg font-red-flamingo">
                    13,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-6">
                  <div class="font-grey-mint font-sm">
                    实际交易额
                  </div>
                  <div class="uppercase font-hg theme-font-color">
                    4,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-6">
                  <div class="font-grey-mint font-sm">
                    预期交易额
                  </div>
                  <div class="uppercase font-hg font-purple">
                    11,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-6">
                  <div class="font-grey-mint font-sm">
                    交易额增长
                  </div>
                  <div class="uppercase font-hg font-blue-sharp">
                    9,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </div>
              </div>
              <ul class="list-separated list-inline-xs hide">
                <li>
                  <div class="font-grey-mint font-sm">
                    总交易额
                  </div>
                  <div class="uppercase font-hg font-red-flamingo">
                    13,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </li>
                <li>
                </li>
                <li class="border">
                  <div class="font-grey-mint font-sm">
                    实际交易额
                  </div>
                  <div class="uppercase font-hg theme-font-color">
                    4,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </li>
                <li class="divider">
                </li>
                <li>
                  <div class="font-grey-mint font-sm">
                    预期交易额
                  </div>
                  <div class="uppercase font-hg font-purple">
                    11,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </li>
                <li class="divider">
                </li>
                <li>
                  <div class="font-grey-mint font-sm">
                    交易额增长
                  </div>
                  <div class="uppercase font-hg font-blue-sharp">
                    9,760 <span class="font-lg font-grey-mint">$</span>
                  </div>
                </li>
              </ul>
              <div id="sales_statistics" class="portlet-body-morris-fit morris-chart" style="height: 260px">
              </div>
            </div>
          </div>
          <!-- END PORTLET-->
        </div>
        <div class="col-md-6 col-sm-12">
          <!-- BEGIN PORTLET-->
          <div class="portlet light ">
            <div class="portlet-title">
              <div class="caption caption-md">
                <i class="icon-bar-chart theme-font-color hide"></i>
                <span class="caption-subject theme-font-color bold uppercase">会员活跃度</span>
                <span class="caption-helper hide">weekly stats...</span>
              </div>
              <div class="actions">
                <div class="btn-group btn-group-devided" data-toggle="buttons">
                  <label class="btn btn-transparent grey-salsa btn-circle btn-sm active">
                    <input type="radio" name="options" class="toggle" id="option4">今日</label>
                  <label class="btn btn-transparent grey-salsa btn-circle btn-sm">
                    <input type="radio" name="options" class="toggle" id="option5">周</label>
                  <label class="btn btn-transparent grey-salsa btn-circle btn-sm">
                    <input type="radio" name="options" class="toggle" id="option6">月</label>
                </div>
              </div>
            </div>
            <div class="portlet-body">
              <div class="row number-stats margin-bottom-30">
                <div class="col-md-6 col-sm-6 col-xs-6">
                  <div class="stat-left">
                    <div class="stat-chart">
                      <!-- do not line break "sparkline_bar" div. sparkline chart has an issue when the container div has line break -->
                      <div id="sparkline_bar"></div>
                    </div>
                    <div class="stat-number">
                      <div class="title">
                        Total
                      </div>
                      <div class="number">
                        2460
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-6 col-sm-6 col-xs-6">
                  <div class="stat-right">
                    <div class="stat-chart">
                      <!-- do not line break "sparkline_bar" div. sparkline chart has an issue when the container div has line break -->
                      <div id="sparkline_bar2"></div>
                    </div>
                    <div class="stat-number">
                      <div class="title">
                        New
                      </div>
                      <div class="number">
                        719
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="table-scrollable table-scrollable-borderless">
                <table class="table table-hover table-light">
                  <thead>
                  <tr class="uppercase">
                    <th colspan="2">
                      MEMBER
                    </th>
                    <th>
                      Earnings
                    </th>
                    <th>
                      CASES
                    </th>
                    <th>
                      CLOSED
                    </th>
                    <th>
                      RATE
                    </th>
                  </tr>
                  </thead>
                  <tr>
                    <td class="fit">
                      <img class="user-pic" src="<%=resourcePath%>static/admin/layout4/img/avatar4.jpg">
                    </td>
                    <td>
                      <a href="javascript:void(0)" class="primary-link">Brain</a>
                    </td>
                    <td>
                      $345
                    </td>
                    <td>
                      45
                    </td>
                    <td>
                      124
                    </td>
                    <td>
                      <span class="bold theme-font-color">80%</span>
                    </td>
                  </tr>
                  <tr>
                    <td class="fit">
                      <img class="user-pic" src="<%=resourcePath%>static/admin/layout4/img/avatar5.jpg">
                    </td>
                    <td>
                      <a href="javascript:void(0)" class="primary-link">Nick</a>
                    </td>
                    <td>
                      $560
                    </td>
                    <td>
                      12
                    </td>
                    <td>
                      24
                    </td>
                    <td>
                      <span class="bold theme-font-color">67%</span>
                    </td>
                  </tr>
                  <tr>
                    <td class="fit">
                      <img class="user-pic" src="<%=resourcePath%>static/admin/layout4/img/avatar6.jpg">
                    </td>
                    <td>
                      <a href="javascript:void(0)" class="primary-link">Tim</a>
                    </td>
                    <td>
                      $1,345
                    </td>
                    <td>
                      450
                    </td>
                    <td>
                      46
                    </td>
                    <td>
                      <span class="bold theme-font-color">98%</span>
                    </td>
                  </tr>
                  <tr>
                    <td class="fit">
                      <img class="user-pic" src="<%=resourcePath%>static/admin/layout4/img/avatar7.jpg">
                    </td>
                    <td>
                      <a href="javascript:void(0)" class="primary-link">Tom</a>
                    </td>
                    <td>
                      $645
                    </td>
                    <td>
                      50
                    </td>
                    <td>
                      89
                    </td>
                    <td>
                      <span class="bold theme-font-color">58%</span>
                    </td>
                  </tr>
                </table>
              </div>
            </div>
          </div>
          <!-- END PORTLET-->
        </div>
      </div>
      <!-- END PAGE CONTENT-->
    </div>
  </div>
  <!-- END CONTENT -->
</div>
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


<script src="<%=resourcePath%>static/global/plugins/morris/morris.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/morris/raphael-min.js" type="text/javascript" charset="utf-8"></script>
<!-- END CORE PLUGINS -->
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/demo.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/pages/scripts/index3.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script>
  jQuery(document).ready(function () {
    loadInboxMsg();
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    Demo.init(); // init demo features
    Index.init();
    Validator.init();
    setMenuItemActive("${requestScope.funcActiveCode}");
    initModifyPasswordDialog();
  });
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->
</html>

