<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/8/10
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
<html lang="zh-CN">
<head>
        <base href="<%=basePath%>" />
        <meta charset="utf-8"/>
        <title>跑的快 | 后台管理系统-组织管理</title>
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

        <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/jstree/dist/themes/default/style.min.css"/>
        <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/select2/select2.css"/>

        <!-- BEGIN THEME STYLES -->
        <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
        <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
        <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
        <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
        <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>
        <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/sm/sm_org.css">
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

                <div class="row">
                    <div class="col-md-4">
                        <div id="tree-portlet" class="portlet light">
                            <div class="portlet-title">
                                <div class="table-toolbar">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="btn-group">
                                                <a id="new_order" class="btn btn-default" data-toggle="modal" data-target="#orgDialog">
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
                                                       data-toggle="dropdown"><i class="fa fa-angle-down"></i></a>
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
                                <div class="caption">
                                    <i class="fa fa-cogs"></i>组织结构
                                </div>
                                <div class="tools">
                                    <a href="javascript:;" class="reload" onclick="reloadOrgTree();">
                                    </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="scroller portlet-body-scroller">
                                    <div id="orgTree">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 padding-left-0">
                        <div id="tree-node-info-portlet" class="portlet light">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="fa fa-cogs"></i>组织信息
                                </div>
                                <div class="tools">
                                    <a href="javascript:;" class="reload" onclick="reloadSelOrgInfo()">
                                    </a>
                                </div>
                            </div>
                           <div class="portlet-body">
                                <div class="scroller portlet-body-scroller">
                                    <div class="center-block">
                                        <form action="">
                                            <div class="form-body">
                                                <div class="form-group">
                                                    <label class="control-label">组织编码：</label>
                                                    <input type="text" id="dispCode" name="dispCode" class="form-control" readonly="true" />
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label">组织名称：</label>
                                                    <input type="text" id="dispName" name="dispName" class="form-control" readonly="true" />
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label">组织信息：</label>
                                                    <textarea type="text" id="dispInfo" name="dispInfo" style="max-width:100%;" rows="3" class="form-control" readonly="true" ></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label ">状态：</label>
                                                    <input type="text" id="dispStatus" name="dispStatus" class="form-control" readonly="true" />
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label">备注：</label>
                                                    <textarea type="text" id="dispMemo" name="dispMemo" style="max-width:100%" rows="3" class="form-control" readonly="true" ></textarea>
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
        </div>
        <!-- END CONTENT -->
    </div>

    <div class="modal fade" id="orgDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="roleLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="roleLabel"><i class="fa fa-user"></i> 组织信息</h4>
                </div>

                <div id="orgDetailPane" class="modal-body">
                    <form id="orgDetailForm" action="sm/sm_org" method="post">
                        <input type="hidden" name="id" value="" />
                        <input type="hidden" name="ts" value="" />
                        <input type="hidden" name="parentId" value="" />
                        <div id="edit-method">
                        </div>
                        <div class="form-group">
                            <label class="control-label">组织编码：</label>
                            <input type="text" name="code" class="form-control" maxlength="20" placeholder="请输入组织编码"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label">组织名称：</label>
                            <input type="text" name="name" class="form-control" maxlength="50" placeholder="请输入组织名称"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label">组织信息：</label>
                            <textarea type="text" name="info" class="form-control" maxlength="400" style="max-width:100%;" rows="3" placeholder="请输入组织信息"></textarea>
                        </div>
                        <div class="form-group">
                            <label class="control-label ">状态：</label>
                            <select id="status" name="status" class="form-control select2me">
                                <option value="0">启用</option>
                                <option value="1">禁用</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label">备注：</label>
                            <textarea type="text" name="memo" style="max-width:100%;" rows="3" class="form-control" maxlength="400" placeholder=""></textarea>
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
    <form id="orgDelForm" action="sm/sm_org" method="POST">
        <input type="hidden" name="_method" value="DELETE"/>
        <div id="id-data">

        </div>
    </form>

    <form id="orgUpdateStatusForm" action="sm/sm_org/status" method="POST">
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

    <script src="<%=resourcePath%>static/global/plugins/jstree/dist/jstree.min.js"></script>
    <script src="<%=resourcePath%>static/global/plugins/select2/select2.min.js" type="text/javascript"></script>
    <!-- END CORE PLUGINS -->
    <script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/sm/sm_org.js" type="text/javascript" charset="utf-8"></script>
    <script>
        jQuery(document).ready(function () {
            loadInboxMsg();
            Metronic.init();
            Layout.init();
            Validator.init();
            initModifyPasswordDialog();
            initOrgTree();
            resizePortlet();
            setMenuItemActive("${requestScope.funcActiveCode}");
            window.onresize = function () {
                resizePortlet();
            }

            $("#status").select2({minimumResultsForSearch: -1});
        });
    </script>
    <!-- END JAVASCRIPTS -->
    </body>

    <!-- END BODY -->
    </html>
