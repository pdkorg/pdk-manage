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
  String token = request.getServletContext().getInitParameter("resource_token");
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
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/add.css"/>
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
        type="text/css"/>
  <!-- END GLOBAL MANDATORY STYLES -->
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/select2/select2.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
  <link rel="stylesheet" type="text/css"
        href="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>
  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>
  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>
  <link rel="stylesheet" type="text/css" href=" "/>
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
        <!-- /.modal-dialog -->
      </div>
      <ul class="page-breadcrumb breadcrumb">
      </ul>
      <div class="row">
        <div class="col-md-12">
          <div class="portlet light bordered">
            <div class="portlet-title">
              <div class="caption">
                <i class="icon-basket  font-blue-sharp"></i>
                <span class="caption-subject font-blue-sharp bold uppercase">新增订单</span>
              </div>
              <div class="tools">
              </div>
            </div>
            <div class="portlet-body form">
              <!-- BEGIN FORM-->
              <form id="saveorderform" class="form-horizontal" method="post">
                <div class="form-body">
                  <h3 class="form-section">用户信息</h3>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">昵称：</label>
                        <div class="col-md-9">
                          <div class="input-group">
                            <input type="hidden" id="userid" name="userid"  class="form-control" readonly/>
                            <input id="nickname" type="text" name="nickname" class="form-control" onclick="showUserRefDialog()" placeholder="请选择用户" readonly>
                            <span class="input-group-btn">
                              <button class="btn btn-default" type="button" onclick="showUserRefDialog()"><i class="fa fa-search"></i></button>
                              <button class="btn btn-default" type="button" onclick="clearRef('user')"><i class="fa fa-remove"></i></button>
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">电话：</label>
                        <div class="col-md-9">
                          <input id="userphone" type="text" class="form-control" readonly/>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                  </div>
                  <!--/row-->
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">性别：</label>

                        <div class="col-md-9">
                          <input type="text" id="usersex" class="form-control" readonly/>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">姓名：</label>
                        <div class="col-md-9">
                          <input type="text" id="username" name="username"  class="form-control"  readonly/>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                  </div>
                  <h3 class="form-section">订单详情</h3>
                  <!--/row-->
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">订单编号：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" id="ordercode" name="ordercode" placeholder="自动生成订单编号" readonly>
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
                        <label class="control-label col-md-3">配送地址：</label>
                        <div class="col-md-9">
                          <div class="input-group" id="addressdiv">
                            <input type="text" id="orderaddress" name="orderaddress" class="form-control" maxlength="300" placeholder="配送地址">
                            <span class="input-group-btn">
                              <button class="btn btn-default" type="button" onclick="showAddressRef()"><i class="fa fa-search"></i></button>
                              <button class="btn btn-default" type="button" onclick="clearRef('orderaddress')"><i class="fa fa-remove"></i></button>
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">支付方式：</label>
                        <div class="col-md-9">
                          <select class="form-control select2me-2 pdk-valid" id="paytype" name="paytype" onchange="$('#paytype').valid()">
                            <option value="">请选择</option>
                            <option value="0">微信支付</option>
                            <option value="1">现金支付</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">订单金额：</label>
                        <div class="col-md-9">
                          <input type="text" id="ordermny" name="ordermny" class="form-control mask_decimal" maxlength="10" placeholder="请输入金额" value="0">
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">实际消费：</label>
                        <div class="col-md-9">
                          <input id="realcostmny" name="realcostmny" type="text" class="form-control mask_decimal" placeholder="实际消费金额" value="0" readonly>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">优惠金额：</label>
                        <div class="col-md-9">
                          <input id="onsalemny" name="onsalemny" type="text" class="form-control mask_decimal" placeholder="优惠金额" value="0" readonly>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">小费金额：</label>
                        <div class="col-md-9">
                          <input id="feemny" name="feemny" type="text" class="form-control mask_decimal" maxlength="10" placeholder="小费金额" value="0">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">预约时间：</label>
                        <div class="col-md-9">
                          <div class="input-group">
                            <input type="text" id="reservetime" name="reservetime" class="form-control date-picker" placeholder="请选择日期">
                            <span class="input-group-btn">
                              <button class="btn btn-default" type="button">
                                <i class="fa fa-calendar"></i>
                              </button>
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-9">
                      <div class="form-group">
                        <label class="control-label col-md-2">备注说明：</label>
                        <div class="col-md-10">
                          <textarea id="memo" name="memo" rows="4" class="form-control" maxlength="400" placeholder="备注说明"></textarea>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">支付状态：</label>
                        <div class="col-md-9">
                          <input id="payState" name="payState" type="text" class="form-control" value="未支付" readonly/>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">下单客服姓名：</label>
                        <div class="col-md-9">
                          <input id="kefuid" name="kefuid" type="text" class="form-control" value="${waiter}" readonly/>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                  </div>
                  <!--/row-->
                </div>
                <!--/row-->
                <h3 class="form-section">订单明细</h3>
                <div class="row">
                  <div class="col-md-12">
                    <div class="table-toolbar">
                      <div class="row">
                        <div class="col-md-12">
                          <div class="btn-group">
                            <a id="new_order" class="btn btn-default">
                              新增商品 <i class="fa fa-plus"></i>
                            </a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div id="goodsdiv">

              </div>


                <div class="form-actions">
                  <div class="row">
                    <div class="col-md-6">
                      <div class="row">
                        <div class="col-md-offset-3 col-md-9">
                          <button type="button" class="btn blue-sharp" id="saveBtn" >保存</button>
                          <button type="button" class="btn btn-default" id="cancelBtn">取消</button>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                    </div>
                  </div>
                </div>
              </form>
              <!-- END FORM-->
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- END CONTENT -->
  </div>

  <!-- Modal -->
  <div class="modal fade" id="userDialog" tabindex="-1" data-backdrop="static" role="dialog"
       aria-labelledby="userLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                  aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="userLabel"><i class="fa fa-users"></i> 粉丝列表</h4>
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
                  <th name="age">用户年龄</th>
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

  <!-- Modal -->
  <div class="modal fade" id="addressDialog" tabindex="-1" data-backdrop="static" role="dialog"
       aria-labelledby="userLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                  aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="addressLabel"><i class="fa fa-users"></i> 地址信息</h4>
        </div>
        <div class="modal-body">
          <div class="row">
            <div class="col-md-12">
              <table class="table table-striped table-bordered table-hover text-nowrap" id="address-table">
                <thead>
                <tr>
                  <th class="table-checkbox"></th>
                  <th name="index">序号</th>
                  <th name="receiver">收件人</th>
                  <th name="fullAddress">详细地址</th>
                  <th name="isDefault">是否默认地址</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" id="addressSelect">确定</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>

  <div class="collapse" id="orderDetail">
    <div class="well">

    </div>
  </div>

  <form id="imageUploadForm" method="post" enctype="multipart/form-data">
    <input id="uploadFile" name="uploadFile" type="file" style="display: none;" accept="image/gif, image/png ,image/jpg, image/bmp, image/jpeg">
    <input name="token" value="<%=token%>" type="hidden">
    <input name="path" value="" type="hidden">
    <input name="module" value="ORDER" type="hidden">
    <input name="uploadType" value="image" type="hidden">
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
<script src="<%=resourcePath%>static/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->

<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=resourcePath%>static/admin/js/common/common-tools.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/pdk-table.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/pages/scripts/components-pickers.js"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>

<script src="<%=resourcePath%>static/admin/js/order/order_detail_add.js" type="text/javascript" charset="utf-8"></script>

<script>
  jQuery(document).ready(function () {
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    loadInboxMsg();
    Validator.init();
    setMenuItemActive("${requestScope.funcActiveCode}");
//    initUnitSelect();
    initEvents();
    initAll();
    initFlowTypeList();
    initRefButton();
    initPath("<%=resourcePath%>", "<%=token%>");
  });
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->

</html>