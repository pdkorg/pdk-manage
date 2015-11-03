<%--
  Created by IntelliJ IDEA.
  User: chengxiang
  Date: 15/8/17
  Time: 下午8:16
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

  <link href="<%=resourcePath%>static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
  <link href="<%=resourcePath%>static/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>

  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" id="style_color" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/css/index.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/img/logo.ico" rel="shortcut icon"/>
</head>

<body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-fixed page-sidebar-closed-hide-logo">

<jsp:include page="../base/header.jsp"/>

<div class="page-container">
  <jsp:include page="../base/menu.jsp"/>
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
      <div class="page-content">
        <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
        <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Modal title</h4>
              </div>
              <div class="modal-body">
                Widget settings form goes here
              </div>
              <div class="modal-footer">
                <button type="button" class="btn blue">Save changes</button>
                <button type="button" class="btn default" data-dismiss="modal">Close</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
        </div>
        <ul class="page-breadcrumb breadcrumb">
        </ul>
        <div class="row">
          <div class="col-md-12">
            <!-- BEGIN EXAMPLE TABLE PORTLET-->
            <div class="portlet light bordered">
              <div class="portlet-title">
                <div class="caption">
                  <i class="fa fa-list"></i>订单列表
                </div>
                <div class="actions"></div>
                <div class="tools">
                  <a href="javascript:;" class="reload"></a>
                </div>
              </div>
              <div class="portlet-body form">
                <div class="portlet-body">
                  <form class="form-horizontal">
                    <div class="form-body">
                      <h3 class="form-section">订单查询</h3>
                      <div class="list_search" style="margin-bottom: 15px;">
                        <div class="row">
                          <div class="col-md-6">
                            <div class="form-group">
                              <label class="control-label col-md-3">
                                订单编号：
                              </label>
                              <div class="col-md-9">
                                <input type="text" id="ordercode" class="form-control mask_phone" placeholder="">
                              </div>
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="form-group">
                              <label class="control-label col-md-3">用户：</label>
                              <div class="col-md-9">
                                <div class="input-group">
                                  <input type="text" id="customid" class="form-control" placeholder="请点击选择用户" onclick="showUserRefDialog()" readonly/>
                                  <span class="input-group-btn">
                                    <button class="btn btn-default" type="button" onclick="showUserRefDialog()"><i class="fa fa-search"></i></button>
                                    <button class="btn btn-default" type="button" onclick="clearRef('customid')"><i class="fa fa-remove"></i></button>
                                  </span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6">
                            <div class="form-group">
                              <label class="control-label col-md-3">业务员：</label>
                              <div class="col-md-9">
                                <div class="input-group">
                                  <input type="text" id="ywyid" class="form-control" placeholder="请点击选择业务员" onclick="showEmployeeDialog(1)" readonly/>
                                  <span class="input-group-btn">
                                    <button class="btn btn-default" type="button" onclick="showEmployeeDialog(1)"><i class="fa fa-search"></i></button>
                                    <button class="btn btn-default" type="button" onclick="clearRef('ywyid')"><i class="fa fa-remove"></i></button>
                                  </span>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="form-group">
                              <label class="control-label col-md-3">客服：</label>
                              <div class="col-md-9">
                                <div class="input-group">
                                  <input type="text" id="waiterid" class="form-control" onclick="showEmployeeDialog(2)" placeholder="请点击选择客服" readonly/>
                                  <span class="input-group-btn">
                                    <button class="btn btn-default" type="button" onclick="showEmployeeDialog(2)"><i class="fa fa-search"></i></button>
                                    <button class="btn btn-default" type="button" onclick="clearRef('waiterid')"><i class="fa fa-remove"></i></button>
                                  </span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6">
                            <div class="form-group">
                              <label class="control-label col-md-3">订单状态：</label>
                              <div class="col-md-9">
                                <select class="form-control select2me-2" id="orderState">
                                  <option value="">全部</option>
                                  <option value="0">未指派</option>
                                  <option value="1">未定价</option>
                                  <option value="2">未完成</option>
                                  <option value="3">已完成</option>
                                  <option value="4">已取消</option>
                                </select>
                              </div>
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="form-group">
                              <label class="control-label col-md-3">订单类型：</label>
                              <div class="col-md-9">
                                  <select class="form-control select2me" id="ordertype" name="ordertype" onchange="$('#ordertype').valid()">
                                  </select>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6">
                            <div class="form-group">
                              <label class="control-label col-md-3">订单时间：</label>
                              <div class="col-md-9">
                                <div class="input-group">
                                  <input type="text" class="form-control date-picker" id="fromDate">
                                  <span class="input-group-addon">to </span>
                                  <input type="text" class="form-control date-picker" id="toDate">
                                </div>
                                <span class="help-block">选择时间段 </span>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6">
                            <div class="row">
                              <div class="col-md-offset-3 col-md-9">
                                <button type="button" class="btn blue-sharp" onclick="clickQuery()">查询</button>
                                <button type="button" class="btn btn-default" onclick="clearQueryArg()">清空</button>
                              </div>
                            </div>
                          </div>
                          <div class="col-md-6">
                          </div>
                        </div>
                      </div>
                      <h3 class="form-section">订单列表</h3>
                      <div class="table-toolbar">
                        <div class="row">
                          <div class="col-md-12">
                            <div class="btn-group">
                              <a class="btn btn-default" href="order/addorder?funcActiveCode=DETAIL">
                                新增 <i class="fa fa-plus"></i>
                              </a>
                              <a id="del_order" class="btn btn-default ">
                                删除 <i class="fa fa-trash-o"></i>
                              </a>
                              <a id="edit_order" class="btn btn-default">
                                修改 <i class="fa fa-edit"></i>
                              </a>
                              <a id="cancel_order" class="btn btn-default ">取消订单
                                <i class="fa fa-times"></i>
                              </a>
                              <a id="reDistribute" class="btn btn-default">重新分配业务员
                                <i class="fa fa-user"></i>
                              </a>
                              <a id="cashpay" class="btn btn-default" onclick="orderPay(1)">现金支付完成
                                <i class="fa fa-money"></i>
                              </a>
                              <a id="weixinpay" class="btn btn-default" onclick="orderPay(0)">微信支付完成
                                <i class="fa fa-weixin"></i>
                              </a>
                            </div>
                          </div>
                        </div>
                      </div>
                      <table class="table table-striped table-bordered table-hover text-nowrap" id="order-table">
                        <thead>
                        <tr>
                          <th class="table-checkbox">
                            <input type="checkbox" class="group-checkable" data-set="#order-table .checkboxes"/>
                          </th>
                          <th name="index">序号</th>
                          <th name="hrefCode">订单编号</th>
                          <th name="flowTypeName">订单类型</th>
                          <th name="nickname">用户昵称</th>
                          <th name="realname">用户姓名</th>
                          <th name="phonenum">联系电话</th>
                          <th name="adress">配送地址</th>
                          <th name="payTypeName">支付方式</th>
                          <th name="statusName">订单状态</th>
                          <th name="startTime">开始时间</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                      </table>
                    </div>
                  </form>
                </div>
              </div>
            </div>
            <!-- END EXAMPLE TABLE PORTLET-->
          </div>
        </div>
        <!-- END PAGE CONTENT-->
      </div>
      <!-- END CONTENT -->
    </div>
    <!-- END CONTAINER -->
    <!-- Modal -->
    <div class="modal fade" id="userDialog" tabindex="-1" data-backdrop="static" role="dialog"
         aria-labelledby="userLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="userLabel"><i class="fa fa-users"></i> 用户列表</h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-12">
                <table class="table table-striped table-bordered table-hover text-nowrap" id="user-table">
                  <thead>
                  <tr>
                    <th class="table-checkbox"></th>
                    <th name="index">序号</th>
                    <th name="name">用户昵称</th>
                    <th name="realName">用户名称</th>
                    <th name="sexName">用户性别</th>
                    <th name="age">用户性别</th>
                    <th name="phone">电话号码</th>
                    <th name="registerTime">关注时间</th>
                    <th name="unRegisterTime">取消关注时间</th>
                  </tr>
                  </thead>
                  <tbody>

                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="userSelect">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div>
      </div>
    </div>


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
              <h3 class="form-section">订单明细</h3>
              <div class="row">
                <div class="col-md-12">

                </div>
              </div>
            </form>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
          </div>
        </div>
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
            <button type="button" class="btn btn-primary" id="employeeSelect">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
  <!-- BEGIN FOOTER -->
<div class="page-footer">
  <div class="page-footer-inner">
    © 2015 zhenglizhe, Inc. Licensed under MIT license.
  </div>
  <div class="scroll-to-top">
    <i class="icon-arrow-up"></i>
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
    <script src="<%=resourcePath%>static/admin/js/common/common-tools.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/order/order_index.js"  type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
    <script>
  jQuery(document).ready(function () {
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    loadInboxMsg();
    setMenuItemActive("${requestScope.funcActiveCode}");
    Validator.init();
    initTable();
    initDatePicker();
    initFlowTypeList();
    initEvents();
  });
</script>
</body>
</html>
