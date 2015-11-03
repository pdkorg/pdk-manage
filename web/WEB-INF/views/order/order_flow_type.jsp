<%--
  Created by IntelliJ IDEA.
  User: chengxiang
  Date: 15/9/8
  Time: 上午11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  String resourcePath = request.getServletContext().getInitParameter("resource_path") + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>">
  <meta charset="utf-8"/>
  <title>跑的快 | 后台管理系统-流程环节</title>
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
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
  <!-- END GLOBAL MANDATORY STYLES -->

  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/jstree/dist/themes/default/style.min.css" />
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/select2/select2.css" />
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />

  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css" />
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css" />
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css" />
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css" />
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css" />
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/allot.css" />
  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>

</head>

<body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-fixed page-sidebar-closed-hide-logo">

<jsp:include page="../base/header.jsp"/>

<div class="page-container">
  <jsp:include page="../base/menu.jsp"/>
  <!-- BEGIN CONTENT -->
  <div class="page-content-wrapper">
    <div class="page-content">
      <ul class="page-breadcrumb breadcrumb">
      </ul>
      <div class="row">
        <div class="col-md-4">
          <div id="tree-portlet" class="portlet light">
            <div class="portlet-title">
              <div class="caption">
                <i class="fa fa-cogs"></i>业务类型
              </div>
              <div class="tools">
                <input type="checkbox" id="roleChange" onclick="roleChange()">包含辅角色
                <a href="javascript:;" class="reload" onclick="reloadTree()">
                </a>
              </div>
            </div>
            <div class="portlet-body">
              <div class="scroller portlet-body-scroller">
                <div id="flow-type-tree">
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-8" id="orderList">

        </div>
      </div>
      <div class="row" id="pageTool">
        <div class="col-md-7 col-sm-12">
          <div class="dataTables_paginate paging_bootstrap_full_number" id="order-table_paginate">
            <ul class="pagination">
              <li class="prev">
                <a href="javascript:void(0);" title="上页" onclick="prePage()">
                  <i class="fa fa-angle-left">
                  </i>
                </a>
              </li>
              <li class="active disabled">
                <a href="javascript:void(0);" id="currentPage"></a>
              </li>
              <li class="next">
                <a href="javascript:void(0);" title="下页" onclick="nextPage()">
                  <i class="fa fa-angle-right"></i>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <!-- END PAGE CONTENT-->
    </div>
  </div>
  <!-- END CONTENT -->
</div>


  <div class="collapse" id="orderDetail">
    <div class="well">

    </div>
  </div>

<!-- Modal -->
<div class="modal fade" id="employeeDialog" tabindex="-1" data-backdrop="static" role="dialog"
     aria-labelledby="userLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="employeeLabel"><i class="fa fa-users"></i> 员工列表</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12">
            <table class="table table-striped table-bordered table-hover text-nowrap" id="employee-table">
              <thead>
              <tr>
                <th class="table-checkbox"></th>
                <th name="index">序号</th>
                <th name="code">员工编号</th>
                <th name="name">员工名称</th>
                <th name="sexName">员工性别</th>
                <th name="phone">电话号码</th>
              </tr>
              </thead>
              <tbody>

              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="employeeSelect" onclick="selectEmployee()">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
    </div>
  </div>
</div>
<!-- Modal -->

<div class="modal fade" id="moneyDialog" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="unitLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="unitLabel"><i class="fa fa-money"></i> 金额确认</h4>
      </div>
      <div class="modal-body">
        <form action="form_layouts.html#" class="form-horizontal">
          <h3 class="form-section">订单金额信息</h3>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-5">订单金额：</label>

                <div class="col-md-7">
                  <input type="text" id="dia_orderMny" class="form-control mask_decimal" placeholder="" readonly>
                </div>
              </div>
            </div>
            <!--/span-->
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-5">实际消费：</label>

                <div class="col-md-7">
                  <input type="text" id="dia_realCost" class="form-control mask_decimal" placeholder="" readonly>
                </div>
              </div>
            </div>
            <!--/span-->
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-5">优惠金额：</label>
                <div class="col-md-7">
                  <input type="text" id="dia_couponMny" class="form-control mask_decimal" placeholder="" readonly>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-5">小费金额：</label>
                <div class="col-md-7">
                  <input type="text" id="dia_feeMny" class="form-control mask_decimal" placeholder="" readonly>
                </div>
              </div>
            </div>
          </div>
          <h3 class="form-section">订单明细</h3>
          <div class="row">
            <div class="col-md-12">
              <table class="table table-striped table-bordered table-hover text-nowrap"
                     id="detail-table">
                <thead>
                <tr>
                  <th class="table-checkbox"></th>
                  <th name="index">序号</th>
                  <th name="name">商品名称</th>
                  <th name="num">数量</th>
                  <th name="unitId">单位</th>
                  <th name="goodsMny">商品总价</th>
                  <th name="buyAdress">购买地址</th>
                  <th name="memo">备注</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
              </table>
            </div>
          </div>
        </form>
        <div class="modal-footer">
          <button type="button" id="dia_confir_btn" onclick="mnyConfirm()" class="btn btn-primary">确定</button>
          <button type="button" onclick="goEdit()" class="btn btn-default">去修改</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>
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
</div>


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
<!-- END CORE PLUGINS -->

<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/pdk-table.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/pages/scripts/components-pickers.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/admin/js/menu/menu.js" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jstree/dist/jstree.min.js"></script>

<script type="text/javascript" src="<%=resourcePath%>static/admin/js/order/order_flow_type.js" charset="utf-8"></script>

<script>
  jQuery(document).ready(function () {
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    setMenuItemActive("${requestScope.funcActiveCode}");
    loadInboxMsg();
    initFlowTypeTree(0);
    initEvents();
    resizePortlet();
    window.onresize = function() {
      resizePortlet();
    }


  });
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->

</html>

