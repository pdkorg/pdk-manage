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

  <!-- BEGIN GLOBAL MANDATORY STYLES -->
  <link href="<%=resourcePath%>static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
  <!-- END GLOBAL MANDATORY STYLES -->
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/select2/select2.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
 <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/add.css"/>
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
        </div>
      </div>
      <ul class="page-breadcrumb breadcrumb">
      </ul>
      <div class="row">
        <div class="col-md-12">
          <div class="portlet light bordered">
            <div class="portlet-title">
              <div class="caption">
                <i class="icon-basket  font-blue-sharp"></i>
                <span class="caption-subject font-blue-sharp bold uppercase">订单详情</span>
              </div>
              <div class="tools">
              </div>
            </div>
            <c:if test="${1==isShowBtn}">
              <div class="row">
                <div class="col-md-12">
                  <div class="btn-group">
                    <a id="edit_order" class="btn btn-default">
                      修改订单 <i class="fa fa-edit"></i>
                    </a>
                    <a id="new_order" class="btn btn-default" href="order/addorder?funcActiveCode=DETAIL">
                      新增订单 <i class="fa fa-plus"></i>
                    </a>
                    <c:if test="${obj.status!=3 and obj.status!=4 and isManager==1}">
                      <a id="change_order_state" class="btn btn-default " data-toggle="modal" data-id="${obj.flowAction}" data-name="${obj.deliveryStatus}">${obj.deliveryStatus}
                        <i class="fa fa-database"></i>
                      </a>
                    </c:if>
                  </div>
                </div>
              </div>
            </c:if>
            <div class="portlet-body form">
              <!-- BEGIN FORM-->
              <form action="form_layouts.html#" class="form-horizontal">
                <div class="form-body">
                  <h3 class="form-section">用户信息</h3>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">昵称：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" value="${obj.nickname}" readonly>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">电话：</label>

                        <div class="col-md-9">
                          <input type="text" class="form-control mask_phone" value="${obj.phonenum}" readonly>
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
                          <input type="text" class="form-control" value="${obj.userSexName}" readonly>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">姓名：</label>
                        <div class="col-md-9">
                          <input type="text" name="name" class="form-control" readonly value="${obj.realname}" data-id="${obj.csId}"/>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                  </div>
                  <!--/row-->
                  <h3 class="form-section">订单详情</h3>
                  <!--/row-->
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">订单编号：</label>
                        <div class="col-md-9">
                          <input id="ordercode" type="text" class="form-control" value="${obj.code}" data-id="${obj.id}" data-ts="${obj.tsIden}" readonly>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">订单类型：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" value="${obj.flowTypeName}" readonly>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">配送地址：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" value="${obj.adress}" readonly>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">支付方式：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" value="${obj.payTypeName}" readonly>
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
                          <input type="text" class="form-control mask_decimal" value="${obj.mny}" readonly>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">实际消费：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control mask_decimal" value="${obj.actualMny}" readonly>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">优惠金额：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control mask_decimal" value="${obj.couponMny}" readonly>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">小费金额：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control mask_decimal" value="${obj.feeMny}" readonly>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">预约时间：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" value="${obj.reserveTime}" readonly>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-9">
                      <div class="form-group">
                        <label class="control-label col-md-2">备注说明：</label>
                        <div class="col-md-10">
                          <textarea rows="4" class="form-control"  readonly >${obj.memo}</textarea>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!--/row-->
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">支付状态：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" id="payStatus" value="${obj.payStatusName}" readonly>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">订单状态：</label>
                        <div class="col-md-9">
                          <input type="text" id="orderSate" class="form-control" value="${obj.statusName}" data-id="${obj.status}" readonly>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <!--/span-->
                  </div>
                  <!--/row-->
                  <div class="row">
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">订单开始时间：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" value="${obj.startTime}" readonly>
                        </div>
                      </div>
                    </div>
                    <!--/span-->
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="control-label col-md-3">订单结束时间：</label>
                        <div class="col-md-9">
                          <input type="text" class="form-control" value="${obj.endTime}" readonly>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!--/span-->
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="control-label col-md-3">店长姓名：</label>
                      <div class="col-md-9">
                        <input type="text" class="form-control" value="${obj.leaderName}" readonly>
                      </div>
                    </div>
                  </div>
                  <!--/span-->
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="control-label col-md-3">下单客服姓名：</label>
                      <div class="col-md-9">
                        <input type="text" class="form-control" value="${obj.waitertName}" readonly>
                      </div>
                    </div>
                  </div>
                  <!--/span-->
                </div>

                <h3 class="form-section">订单明细</h3>
                <c:forEach items="${body}" var="bodyObj" >
                  <div class="row list-body">
                    <div class="col-md-2">
                      <div class="list-img">
                        <div class="fileinput fileinput-exists" style="width: 90%;">
                          <div class="fileinput-preview thumbnail" style="width: 100%; height: 160px; line-height: 160px;">
                            <c:if test="${bodyObj.imgUrl!=null && bodyObj.imgUrl.length()>5}">
                              <img name="goodsImg" src="<%=resourcePath%>${bodyObj.imgUrl}">
                            </c:if>
                            <c:if test="${bodyObj.imgUrl==null || bodyObj.imgUrl.length()<5}">
                              <img name="goodsImg" src="">
                            </c:if>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-10">
                      <div class="row list-row">
                        <div class="col-md-6">
                          <div class="form-group">
                            <label class="control-label col-md-2 list-bt">名称</label>
                            <div class="col-md-10">
                              <input type="text" class="form-control" value="${bodyObj.name}" readonly placeholder="请输入商品名称">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-6">
                          <div class="form-group">
                            <label class="control-label col-md-2 list-bt">数量</label>
                            <div class="col-md-10">
                              <input type="text" class="form-control mask_decimal" value="${bodyObj.num}" readonly placeholder="请输入数量">
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="row list-row">
                        <div class="col-md-6">
                          <div class="form-group">
                            <label class="control-label col-md-2 list-bt">总价</label>
                            <div class="col-md-10">
                              <input type="text" class="form-control mask_decimal" value="${bodyObj.goodsMny}" readonly placeholder="请输入价格">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-6">
                          <div class="form-group">
                            <label class="control-label col-md-2 list-bt">单位</label>
                            <div class="col-md-10">
                              <input type="text" class="form-control" value="${bodyObj.unitId}" readonly
                                     placeholder="请输入单位">
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="row list-row">
                        <div class="col-md-6">
                          <div class="form-group">
                            <label class="control-label col-md-2 list-bt">预约时间</label>
                            <div class="col-md-10">
                              <input type="text" class="form-control" value="${bodyObj.reserveTime}" readonly placeholder="选择日期">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-6">
                          <div class="form-group">
                            <label class="control-label col-md-2 list-bt">购买地点</label>
                            <div class="col-md-10">
                              <input type="text" class="form-control" value="${bodyObj.buyAdress}" readonly
                                     placeholder="请输入购买地点">
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="row list-row" style="margin-bottom: 0;">
                        <div class="col-md-6">
                          <div class="form-group">
                            <label class="control-label col-md-2 list-bt">备注</label>
                            <div class="col-md-10">
                              <input type="text" class="form-control" value="${bodyObj.memo}" readonly
                                     placeholder="请输入备注信息">
                            </div>
                          </div>
                        </div>

                      </div>
                    </div>
                  </div>
                </c:forEach>
                <h3 class="form-section">业务分派信息</h3>
                <div class="row">
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="control-label col-md-3">业务员姓名:</label>
                      <div class="col-md-9">
                        <input type="text" class="form-control" value="${obj.ywyName}" readonly>
                      </div>
                    </div>
                  </div>
                  <!--/span-->
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="control-label col-md-3">业务员电话：</label>
                      <div class="col-md-9">
                        <input type="text" class="form-control" value="${obj.ywyPhone}" readonly>
                      </div>
                    </div>
                  </div>
                  <!--/span-->
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="control-label col-md-3">配送状态：</label>
                      <div class="col-md-9">
                        <input type="text" class="form-control" value="${obj.deliveryStatus}" readonly>
                      </div>
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
            <button type="button" onclick="mnyConfirm()" class="btn btn-primary">确定</button>
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
<script src="<%=resourcePath%>static/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript" charset="utf-8"></script>
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
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/pages/scripts/components-pickers.js"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>

<script src="<%=resourcePath%>static/admin/js/order/order_detail.js" type="text/javascript" charset="utf-8"></script>

<script>
  jQuery(document).ready(function () {
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    loadInboxMsg();
    setMenuItemActive("${requestScope.funcActiveCode}");
    Validator.init();
    initInput();
    ComponentsPickers.init();
    initEvents();
  });
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->

</html>