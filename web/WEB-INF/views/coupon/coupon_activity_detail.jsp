<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/9/10
  Time: 18:02
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
  <title>跑的快 | 后台管理系统-角色管理</title>
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
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/jquery-multi-select/css/multi-select.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/jstree/dist/themes/default/style.min.css"/>

  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>
  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>

  <link href="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css"/>

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
    <div id="couponSendDetailPane" class="page-content">
      <!-- BEGIN PAGE HEADER-->
      <!-- BEGIN PAGE HEAD -->
      <ul class="page-breadcrumb breadcrumb">
      </ul>
      <div class="row">
        <div class="col-md-12">
          <!-- BEGIN EXAMPLE TABLE PORTLET-->
          <div class="portlet light bordered">
            <div class="portlet-title">
              <div class="caption">
                <i class="fa fa-edit font-blue-sharp"></i>
                <span class="caption-subject font-blue-sharp bold uppercase">优惠券派送编辑</span>
              </div>
              <div class="tools">
              </div>
            </div>
            <div class="portlet-body form">
              <div class="form-body">
                <div class="table-toolbar">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="btn-group">

                        <a id="edit_order" class="btn btn-default" onclick="editActivityInfo()" >
                          修改 <i class="fa fa-edit"></i>
                        </a>
                        <a id="cancel_order" class="btn btn-default" onclick="returnList()" >
                          返回 <i class="fa fa-history"></i>
                        </a>
                        <div class="btn-group">
                          <a type="button" class="btn btn-default" onclick="startAutoSend()">
                            启动 <i class="fa fa-check-circle-o"></i>
                          </a>
                          <a type="button" class="btn btn-default dropdown-toggle"
                             data-toggle="dropdown"><i class="fa fa-angle-down"></i></a>
                          <ul class="dropdown-menu" role="menu">
                            <li>
                              <a onclick="stopAutoSend()">终止 <i class="fa fa-ban"></i></a>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <h3 class="form-section">优惠券派送基本信息</h3>
                <form id="activityDetailForm" action="coupon/coupon_activity_detail" method="POST">
                  <div id="body-datas">
                  </div>
                  <input type="hidden" id="id" name="id" value="${activity.id}" />
                  <input type="hidden" id="ts" name="ts" value="${activity.tsStr}" />
                  <div id="edit-method">
                  </div>
                  <!-- BEGIN FORM HEAD -->
                  <div class="row">
                    <div class="form-group col-md-6">
                      <label class="control-label">派送编码：</label>
                      <input type="text" name="code" class="form-control" value="${activity.code}" readonly />
                    </div>
                    <div class="form-group col-md-6">
                      <label class="control-label">派送名称：</label>
                      <input type="text" name="name" class="form-control" value="${activity.name}" readonly />
                    </div>
                  </div>
                  <div class="row">
                    <div class="form-group col-md-6">
                      <label class="control-label">派送状态：</label>
                      <input type="hidden" id="status" value="${activity.status}" />
                      <input type="text" name="statusName" class="form-control" value="${activity.statusNameOnly}" readonly />
                    </div>
                    <div class="form-group col-md-6">
                      <label class="control-label">系统自动派送时间：</label>
                      <input id="autoSendTime" name="autoSendTime" type="text" value="${activity.autoSendTimeStr}" class="form-control " readonly >
                    </div>
                  </div>
                  <div class="row">
                    <div class="form-group col-md-12">
                      <label class="control-label">系统推送信息：</label>
                      <textarea type="text" name="sendMessage" class="form-control" style="max-width:100%;" rows=3 readonly >${activity.sendMessage}</textarea>
                    </div>
                  </div>
                  <div class="row">
                    <div class="form-group col-md-12">
                      <label class="control-label">备注：</label>
                      <textarea type="text" name="memo" class="form-control" style="max-width:100%;" rows=3 readonly>${activity.memo}</textarea>
                    </div>
                  </div>
                </form>
                <!-- END FORM HEAD -->
                <!-- BEGIN FORM RULE -->
                <h3 class="form-section">发放规则</h3>
                <div class="row">
                  <div class="form-group col-md-12">
                    <table class="table table-striped table-bordered table-hover" id="rule-table">
                      <thead>
                      <tr>
                        <th name="connectType" style="width:150px">关联方式</th>
                        <th name="ruleType" style="width:150px">规则</th>
                        <th name="optType" style="width:170px">连接类型</th>
                        <th name="chkVal" style="width:170px">比较值</th>
                      </tr>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- END FORM RULE -->
                <!-- BEGIN FORM COUPON -->
                <h3 class="form-section">优惠券明细</h3>
                <div class="row">
                  <div class="form-group col-md-6">
                    <span style="font-size:30px">派送人数：</span><span style="font-size: 60px; color:red; text-decoration:underline;" id="sendUserCnt" ></span>
                  </div>

                  <div class="form-group col-md-6">
                    <span style="font-size:30px">派送金额：</span><span style="font-size: 60px;color:red" id="sendTotalMny" ></span>
                  </div>
                </div>

                <div class="row">
                  <div class="form-group col-md-12">
                    <table class="table table-striped table-bordered table-hover" id="coupon-table">
                      <thead>
                      <tr>
                        <th name="code" style="width:150px">优惠券编码</th>
                        <th name="flowType" style="width:150px">优惠券类型</th>
                        <th name="sendMny" style="width:90px">发放金额</th>
                        <th name="minPayMny" style="width:90px">最低消费金额</th>
                        <th name="actBeginDate" style="width:170px">生效开始日期</th>
                        <th name="actEndDate" style="width:170px">生效截止日期</th>
                        <th name="memo" style="min-width: 30%;">备注</th>
                      </tr>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- END FORM COUPON -->
                <div class="modal-footer">
                  <button type="button" class="btn btn-danger"  onclick="sendCoupon()" >派送</button>
                </div>

              </div>
              <!-- END EXAMPLE TABLE PORTLET-->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- END CONTENT -->
</div>

<!-- BEGIN USER_INFO DIALOG -->
<div class="modal fade" id="userListDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="userListLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="userListLabel"><i class="fa fa-user"></i> 待发用户清单</h4>
      </div>
      <div id="userListPane" class="modal-body">
        <table class="table table-striped table-bordered table-hover" id="user-list-table">
          <thead>
          <tr>
            <th name="index" >序号</th>
            <th name="name" >用户昵称</th>
            <th name="realName" >用户名称</th>
            <th name="payMny" >消费金额</th>
            <th name="payTimes" >消费次数</th>
            <th name="firstRegisterDate" >首关日期</th>
          </tr>
          </thead>
          <tbody>
          </tbody>
        </table>

        <div class="modal-footer">
          <button type="button" class="btn btn-primary" onclick="hideSendUserList();">确定</button>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- END USER_INFO DIALOG -->

<form id="currSendUserForm" action="coupon/coupon_activity_detail/search_user_send_info" method="GET">
  <div id="search-coupon-data">
  </div>
  <div id="search-rule-data">
  </div>
</form>

<form id="activityUpdateStatusForm" action="coupon/coupon_activity_detail/status" method="POST">
  <input type="hidden" name="_method" value="PUT"/>
  <div id="status-id-data">

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

<!-- END CORE PLUGINS -->
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/pdk-table.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/coupon/coupon_activity_detail.js" type="text/javascript" charset="utf-8"></script>

<script src="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript" ></script>
<script src="<%=resourcePath%>static/admin/js/common/common-tools.js" type="text/javascript" charset="utf-8"></script>
<script>
  var canEdit = ${canEdit};
  jQuery(document).ready(function () {
    loadInboxMsg();
    Metronic.init();
    Layout.init();
    Validator.init();
    initModifyPasswordDialog();
    initFlowType();

    initDetailTable();
    initRuleTable();
    initSendUserDetailTable();

    initValidate();
    initDatePicker();
    setMenuItemActive("${requestScope.funcActiveCode}");

    var status = '${activity.status}';
    $("#status").select2("val", status);
    getCurrSendUserInfo($(id).val());
  });
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->
</html>
