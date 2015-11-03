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
    <title>跑的快 | 后台管理系统-流程模板配置</title>
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

    <link href="<%=resourcePath%>static/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css">

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
                <div class="col-md-3">
                    <div id="tree-portlet" class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-cogs"></i>业务类型
                            </div>
                            <div class="tools">
                                <a href="javascript:;" class="reload">
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
                <div class="col-md-9 padding-left-0">
                    <!-- BEGIN EXAMPLE TABLE PORTLET-->
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-list"></i>流程模板
                            </div>
                            <div class="tools">
                                <a href="javascript:;" class="reload"></a>
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <form>
                                <div class="form-body">
                                    <h3 class="form-section">流程环节</h3>

                                    <div class="table-toolbar">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="btn-group">
                                                    <a class="btn btn-default" data-toggle="modal"
                                                       data-target="#flowDialog">
                                                        新增 <i class="fa fa-plus"></i>
                                                    </a>

                                                    <a class="btn btn-default" onclick="editFlowTemplateUnit()">
                                                        修改 <i class="fa fa-edit"></i>
                                                    </a>

                                                    <a class="btn btn-default" onclick="delFlowTemplateUnit()">
                                                        删除 <i class="fa fa-trash-o"></i>
                                                    </a>

                                                    <a class="btn btn-default" onclick="upFlowTemplateUnit()">
                                                        上移 <i class="fa fa-arrow-up"></i>
                                                    </a>

                                                    <a class="btn btn-default" onclick="downFlowTemplateUnit()">
                                                        下移 <i class="fa fa-arrow-down"></i>
                                                    </a>

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <table class="table table-striped table-bordered table-hover"
                                           id="flow-table">
                                        <thead>
                                        <tr>
                                            <th class="table-checkbox">
                                                <input type="checkbox" class="group-checkable"
                                                       data-set="#flow-table .checkboxes"/>
                                            </th>
                                            <th style="width:40px; text-align:center;">序号</th>
                                            <th style="width:30%;text-align:center;">环节名称</th>
                                            <th style="width:30%;text-align:center;">分配角色</th>
                                            <th style="width:10%; text-align:center;">推送消息</th>
                                            <th style="width:30%;text-align:center;">业务备注</th>
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

<div class="modal fade" id="flowDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="flowLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="flowLabel"><i class="fa fa-cogs"></i> 业务类型信息</h4>
            </div>
            <div id="flowTemplateUnitDetailPane" class="modal-body">
                <form id="flowTemplateUnitForm" action="flow/flow_template/flow_template_unit" method="post">
                    <input type="hidden" name="id" value="" />
                    <input type="hidden" name="ts" value="" />
                    <input type="hidden" name="flowTypeId" value="" />
                    <input type="hidden" name="templateId" value="" />
                    <div id="edit-method"></div>
                    <div class="form-group">
                        <label class="control-label ">流程环节名称：</label>
                        <select name="flowUnitId" class="form-control">
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label ">分配角色：</label>
                        <select name="roleId" class="form-control">
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label">业务备注：</label>
                        <textarea name="memo" class="form-control" placeholder="" maxlength="200"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="control-label">是否发送消息：
                            <input id="pushMsgFlag" type='checkbox' class='checkboxes'/>
                            <input type='hidden' name="isPushMsg" />
                        </label>
                    </div>

                    <h3 class="form-section">流程信息模板</h3>

                    <div class="form-group">
                        <label class="control-label">信息模板：</label>
                        <textarea id="flowEditor" class="form-control" name="msgTemplate" maxlength="200" rows="10"></textarea>
                    </div>

                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveFlowTemplateUnit()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="flowTemplateUnitDelForm" action="flow/flow_template/flow_template_unit" method="POST">
    <input type="hidden" name="_method" value="DELETE"/>
    <div id="id-data">

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
<script src="<%=resourcePath%>static/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js"  type="text/javascript"></script>

<script src="<%=resourcePath%>static/global/plugins/jstree/dist/jstree.min.js"></script>
<script src="<%=resourcePath%>static/global/plugins/select2/select2.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/select2/select2_locale_zh-CN.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/datatables/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"
        type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>

<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/pdk-table.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/flow/flow-template.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function () {
        loadInboxMsg();
        Metronic.init();
        Layout.init();
        Validator.init();
        initModifyPasswordDialog();
        setMenuItemActive("${requestScope.funcActiveCode}");
        init();
        resizePortlet();
        window.onresize = function () {
            resizePortlet();
        };
    });
</script>
</body>
</html>