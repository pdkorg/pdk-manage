<%--
  Created by IntelliJ IDEA.
  User: hubo
  Date: 2015/10/20
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String resourcePath = request.getServletContext().getInitParameter("resource_path") + "/";
    String msgServer = request.getServletContext().getInitParameter("msg_server") + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title>跑的快 | 后台管理系统-聊天记录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta content="" name="description"/>
    <meta content="" name="author"/>

    <link href="<%=resourcePath%>static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet"
          type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
          type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet"
          type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"
          rel="stylesheet" type="text/css"/>

    <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" id="style_color" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/img/logo.ico" rel="shortcut icon"/>
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
                <div class="col-md-12">
                    <!-- BEGIN EXAMPLE TABLE PORTLET-->
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-list"></i>聊天记录列表
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
                                        <h3 class="form-section">查询</h3>

                                        <div class="list_search" style="margin-bottom: 15px;">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3">用户：</label>

                                                        <div class="col-md-9">
                                                            <div class="input-group">
                                                                <input type="text" id="userName" class="form-control"
                                                                       placeholder="请点击选择用户"
                                                                       onclick="" readonly/>
                                                                <input type="hidden" id="userId"/>
                                                                      <span class="input-group-btn">
                                                                        <button class="btn btn-default" type="button" onclick="showUserRefDialog()"><i
                                                                                class="fa fa-search"></i></button>
                                                                        <button class="btn btn-default" type="button" onclick="clearUserQueryArg()"><i
                                                                                class="fa fa-remove"></i></button>
                                                                      </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label class="control-label col-md-3">时间：</label>
                                                            <div class="col-md-9">
                                                                <div class="input-group" id="dataRange">
                                                                    <input name="orderRange" type="text" class="form-control" readonly />
                                                                    <span class="input-group-btn">
                                                                        <button class="btn btn-default date-range-toggle" type="button"><i class="fa fa-calendar"></i></button>
                                                                        <button class="btn btn-default" type="button" onclick="event.preventDefault();event.stopPropagation();clearDateRangeQueryArg()"><i class="fa fa-remove"></i></button>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3">内容：</label>
                                                        <div class="col-md-9">
                                                            <input id="chatmsg-content" type="text" class="form-control" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="row">
                                                        <div class="col-md-offset-3 col-md-9">
                                                            <button type="button" class="btn blue-sharp"
                                                                    onclick="clickQuery()">查询
                                                            </button>
                                                            <button type="button" class="btn btn-default"
                                                                    onclick="clearQueryArg()">清空
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                </div>
                                            </div>
                                        </div>
                                        <h3 class="form-section">聊天记录</h3>
                                        <table class="table table-striped table-bordered table-hover text-nowrap"
                                               id="chatmsg-table">
                                            <thead>
                                            <tr>
                                                <th>序号</th>
                                                <th>时间</th>
                                                <th>客服名称</th>
                                                <th>用户名称</th>
                                                <th>内容</th>
                                                <th>类型</th>
                                                <th>来源</th>
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
    <jsp:include page="../sm/sm_user_ref.jsp" />
    <!-- BEGIN FOOTER -->
    <div class="page-footer">
        <div class="page-footer-inner">
            © 2015 zhenglizhe, Inc. Licensed under MIT license.
        </div>
        <div class="scroll-to-top">
            <i class="icon-arrow-up"></i>
        </div>
    </div>

    <script type="text/javascript">
        var msgServerPath = "<%=msgServer%>";
    </script>

    <script src="<%=resourcePath%>static/global/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
    <!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
    <script src="<%=resourcePath%>static/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
            type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
            type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
            type="text/javascript"></script>
    <!-- END CORE PLUGINS -->

    <script type="text/javascript" src="<%=resourcePath%>static/global/plugins/select2/select2.min.js"></script>
    <script type="text/javascript"
            src="<%=resourcePath%>static/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
    <script type="text/javascript"
            src="<%=resourcePath%>static/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript"
            src="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
    <script type="text/javascript"
            src="<%=resourcePath%>static/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript"
            src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
    <script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/pdk-table.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="<%=resourcePath%>static/admin/js/validator.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/admin/js/common/common-tools.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=resourcePath%>static/global/plugins/bootstrap-daterangepicker/moment.min.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/global/plugins/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/sm/sm_user_ref.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=resourcePath%>static/admin/js/cs/chatmsg.js" type="text/javascript" charset="utf-8"></script>
    <script>
        jQuery(document).ready(function () {
            Metronic.init(); // init metronic core components
            Layout.init(); // init current layout
            loadInboxMsg();
            setMenuItemActive("${requestScope.funcActiveCode}");
            Validator.init();
            initChatMsgItem();
        });
    </script>
</body>
</html>
