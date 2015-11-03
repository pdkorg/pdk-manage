<%--
  Created by IntelliJ IDEA.
  User: hubo
  Date: 2015/8/23
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String token = request.getServletContext().getInitParameter("resource_token");
%>
    <div class="portlet light order-portlet">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-basket font-red-sunglo"></i>
                <span class="caption-subject font-red-sunglo bold uppercase">订单信息</span>
            </div>
            <div class="actions">
                <div class="orderPanelTabContainer btn-group">
                    <a id="tab-orderFormPanel" type="button" class="tab btn btn-default active"
                       href="customer_service.html#orderFormPanel" data-toggle="tab"><i
                            class="fa fa-cart-arrow-down"></i> 订单操作 </a>
                    <a id="tab-userInfo" type="button" class="tab btn btn-default"
                       href="customer_service.html#userInfo" data-toggle="tab"><i
                            class="fa fa-users"></i> 用户信息</a>
                    <a id="tab-orderRecrod" type="button" class="tab btn btn-default"
                       href="customer_service.html#orderRecrod" data-toggle="tab"><i
                            class="fa fa-list"></i> 订单记录</a>
                </div>
            </div>
        </div>
        <div class="portlet-body">
            <div class="chart-order scroller">
                <div class="portlet light bordered">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="icon-basket  font-blue-sharp"></i>
                            <span id="tab-name" class="caption-subject font-blue-sharp bold uppercase">新增订单</span>
                        </div>
                        <div class="tools">
                        </div>
                    </div>
                    <div class="portlet-body form">
                        <div class="tab-content">
                            <div class="tab-pane active" id="orderFormPanel">
                                <form id="orderForm" action="order/chartgenorder">
                                    <div class="form-body">
                                        <h3 class="form-section">订单信息</h3>

                                        <div class="form-group">
                                            <label class="control-label">订单类型：</label>

                                            <select name="flowType" class="form-control select2me" onchange="$(this).valid()">
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label">支付方式：</label>

                                            <select name="payStatus" class="form-control select2me" onchange="$(this).valid()">
                                                <option value="">请选择</option>
                                                <option value="0">微信支付</option>
                                                <option value="1">现金支付</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">订单预约时间：</label>

                                            <div class="input-group">
                                                <input name="reserveTime" type="text" class="form-control date-picker" placeholder="请选择日期" onchange="$(this).valid()" readonly>
                                                <span class="input-group-btn">
                                                    <button class="btn btn-default" type="button" onclick="showDateTime()"><i class="fa fa-calendar"></i></button>
                                                    <button class="btn btn-default" type="button" onclick="setCurrOrderTimeHalfHourDelay()" data-toggle="tooltip" data-placement="bottom" title="30分钟后">30</button>
                                                    <button class="btn btn-default" type="button" onclick="setCurrOrderTimeOneHourDelay()" data-toggle="tooltip" data-placement="bottom" title="60分钟后">60</button>
                                                </span>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label">配送地址：</label>
                                            <textarea name="deliverAddress" type="text" class="form-control"></textarea>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label">备注：</label>
                                            <textarea name="memo" type="text" class="form-control" placeholder="" maxlength="100"></textarea>
                                        </div>

                                        <h3 class="form-section">订单明细</h3>

                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="table-toolbar">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="btn-group">
                                                                <a id="new_order"
                                                                   class="btn btn-default btn-sm"
                                                                   href="javascript:void(0)" onclick="createNewOrderDetail()">
                                                                    新增 <i class="fa fa-plus"></i>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="orderDetailListPanel">

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="row">
                                                    <div class="col-md-offset-3 col-md-6">
                                                        <button type="submit"
                                                                class="btn blue-sharp btn-block"> 确认下单
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="tab-pane" id="userInfo">
                                <form action="form_layouts.html">
                                    <div class="form-body">
                                        <h3 class="form-section">用户信息</h3>

                                        <p>无用户信息</p>
                                    </div>
                                </form>
                            </div>

                            <div class="tab-pane form" id="orderRecrod">
                                <form action="form_layouts.html#">
                                    <div class="form-body">

                                        <div class="form-group">
                                            <label class="control-label">订单时间：</label>

                                            <div class="input-group input-medium" id="defaultrange">
                                                <input name="orderRange" type="text" class="form-control" readonly>
												<span class="input-group-btn">
												    <button class="btn btn-default date-range-toggle" type="button"><i class="fa fa-calendar"></i></button>
												</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">支付状态：</label>

                                            <div id="payStatusBtnGroup" class="btn-group input-group">
                                                <a href="javascript:;"
                                                   class="btn btn-default active" data-val="-1">
                                                    <i class="fa fa-jpy"></i> 全部 </a>
                                                <a href="javascript:;" class="btn btn-default" data-val="0">
                                                    <i class="fa fa-exclamation-triangle"></i> 未支付
                                                </a>
                                                <a href="javascript:;" class="btn btn-default" data-val="1">
                                                    <i class="fa fa-credit-card"></i> 已支付 </a>
                                            </div>
                                        </div>
                                        <div id="queryOrderListPanel">
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <form id="orderImageUploadForm" method="post" enctype="multipart/form-data">
        <input id="uploadFile" name="uploadFile" type="file" style="display: none;" accept="image/gif, image/png ,image/jpg, image/bmp, image/jpeg">
        <input name="token" value="<%=token%>" type="hidden">
        <input name="path" value="" type="hidden">
        <input name="module" value="ORDER" type="hidden">
        <input name="uploadType" value="image" type="hidden">
    </form>
