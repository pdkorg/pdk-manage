<%--
  Created by IntelliJ IDEA.
  User: hubo
  Date: 2015/8/12
  Time: 12:08
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

    <link href="<%=resourcePath%>static/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>

    <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" id="style_color" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/img/logo.ico" rel="shortcut icon"/>
</head>

<body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-fixed page-sidebar-closed-hide-logo">

<jsp:include page="../base/header.jsp"/>

<div class="page-container">
    <jsp:include page="../base/menu.jsp"/>
    <div class="page-content-wrapper">
        <div class="page-content">
            <ul class="page-breadcrumb breadcrumb">
            </ul>
            <div class="row">
                <div class="col-md-12">


                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-list"></i>流程环节列表
                            </div>
                            <div class="tools">
                                <a href="javascript:;" class="reload" onclick="tableRefresh()"></a>
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <form>
                                <div class="form-body">
                                    <div class="table-toolbar">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="btn-group">
                                                    <a id="new_order" class="btn btn-default" data-toggle="modal"
                                                       data-target="#flowUnitDialog">
                                                        新增 <i class="fa fa-plus"></i>
                                                    </a>

                                                    <a id="edit_order" class="btn btn-default" onclick="edit()">
                                                        修改 <i class="fa fa-edit"></i>
                                                    </a>

                                                    <a id="del_order" class="btn btn-default" onclick="del()">
                                                        删除 <i class="fa fa-trash-o"></i>
                                                    </a>

                                                    <div class="btn-group">
                                                        <a type="button" class="btn btn-default" onclick="enable()"> 启用 <i
                                                                class="fa fa-check-circle-o"></i></a>
                                                        <a type="button" class="btn btn-default dropdown-toggle"
                                                           data-toggle="dropdown"><i
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

                                    <table class="table table-striped table-bordered table-hover"
                                           id="flow-unit-table">
                                        <thead>
                                        <tr>
                                            <th class="table-checkbox">
                                                <input type="checkbox" class="group-checkable"
                                                       data-set="#flow-unit-table .checkboxes"/>
                                            </th>
                                            <th style="width:40px; text-align:center;">序号</th>
                                            <th style="width:20%; text-align:center;">环节编码</th>
                                            <th style="width:20%; text-align:center;">环节名称</th>
                                            <th style="width:14%; text-align:center;">订单状态</th>
                                            <th style="width:13%; text-align:center;">动作</th>
                                            <th style="width:13%; text-align:center;">状态</th>
                                            <th style="width:20%; text-align:center;">业务备注</th>
                                        </tr>
                                        </thead>
                                        <tbody style="text-align:center;">
                                        </tbody>
                                    </table>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="flowUnitDialog" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="flowUnitLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="flowUnitLabel"><i class="fa fa-cogs"></i> 流程环节信息</h4>
            </div>
            <div id="flowUnitDetailPane" class="modal-body">
                <form id="flowUnitDetailForm" action="flow/flow_unit" method="post">
                    <input type="hidden" name="id" value=""/>
                    <input type="hidden" name="ts" value=""/>

                    <div id='edit-method'>
                    </div>
                    <div class="form-group">
                        <label class="control-label">流程环节编码：</label>
                        <input type="text" name="code" class="form-control" placeholder="请输入环节编码"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">流程环节名称：</label>
                        <input type="text" name="name" class="form-control" placeholder="请输入环节名称"/>
                    </div>

                    <div class="form-group">
                        <label class="control-label ">订单状态：</label>
                        <select name="orderStatus" class="form-control select2me-2">
                            <option value="-1">请选择</option>
                            <option value="0">未指派</option>
                            <option value="1">未定价</option>
                            <option value="2">未完成</option>
                            <option value="3">已完成</option>
                            <option value="4">已取消</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label class="control-label ">动作：</label>
                        <select name="flowActionCode" class="form-control select2me-2">
                            <option value="-1">请选择</option>
                            <option value="0">指派</option>
                            <option value="1">定价</option>
                            <option value="2">支付</option>
                            <option value="3">送达</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label class="control-label ">状态：</label>
                        <select name="status" class="form-control select2me-2">
                            <option value="0">启用</option>
                            <option value="1">禁用</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label">业务备注：</label>
                        <textarea type="text" name="memo" class="form-control" rows="5" placeholder=""></textarea>
                    </div>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="save()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<form id="flowUnitDelForm" action="flow/flow_unit" method="POST">
    <input type="hidden" name="_method" value="DELETE"/>

    <div id="id-data">

    </div>
</form>

<form id="flowUnitUpdateStatusForm" action="flow/flow_unit/status" method="POST">
    <input type="hidden" name="_method" value="PUT"/>

    <div id="status-id-data">

    </div>
</form>

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
<script src="<%=resourcePath%>static/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js" type="text/javascript"></script>

<script src="<%=resourcePath%>static/global/plugins/select2/select2.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/datatables/media/js/jquery.dataTables.min.js" type="text/javascript" ></script>
<script src="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript" ></script>

<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/pdk-table.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/flow/unit.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function () {
        Metronic.init(); // init metronic core components
        Layout.init(); // init current layout
        loadInboxMsg();
        setMenuItemActive("${requestScope.funcActiveCode}");
        Validator.init();
        initTable();
        initflowUnit();
    });
</script>
</body>
</html>
