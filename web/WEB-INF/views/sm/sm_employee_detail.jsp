<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/8/15
  Time: 12:46
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
  <title>跑的快 | 后台管理系统-人员管理</title>
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
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>

  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>
  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>

  <link href="<%=resourcePath%>static/admin/pages/css/profile.css" rel="stylesheet" type="text/css" />
  <link href="<%=resourcePath%>static/global/plugins/jcrop/css/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
  <link href="<%=resourcePath%>static/global/plugins/rater-star/jquery.rater.css" type="text/css" rel="stylesheet" />
  <style type="text/css">

  </style>
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
      <!-- END PAGE HEAD -->
      <!-- BEGIN PAGE BREADCRUMB -->
      <!-- END PAGE BREADCRUMB -->
      <!-- END PAGE HEADER-->
      <!-- BEGIN PAGE CONTENT-->
      <div class="row">
        <div class="col-md-12">
          <!-- BEGIN EXAMPLE TABLE PORTLET-->
          <div class="row">
            <div class="col-md-3">
              <div class="portlet light chart-portlet">
                <div class="profile-userpic">
                  <c:if test="${employee.headerImg == null || employee.headerImg == ''}">
                    <img id="showHeaderImg" src="<%=resourcePath%>static/img/user-default-header.png" class="img-responsive" alt="">
                  </c:if>
                  <c:if test="${employee.headerImg != null && employee.headerImg != ''}">
                    <img id="showHeaderImg" src="<%=resourcePath%>${employee.headerImg}" class="img-responsive" alt="">
                  </c:if>

                </div>
                <div class="profile-usertitle">
                  <div class="profile-usertitle-name" style="width:100%;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;">
                    ${employee.name}
                  </div>
                </div>
                <div class="profile-usermenu">
                  <ul class="nav">
                    <li class="active">
                      <a >
                        <i class="icon-settings"></i>个人信息 </a>
                    </li>
                    <c:if test="${hasPermession}">
                    <li>
                      <a onclick="pointToAuxiliary()" >
                        <i class="icon-settings"></i>兼职角色 </a>
                    </li>
                    </c:if>
                  </ul>
                  </ul>
                </div>
              </div>
            </div>
            <div class="col-md-9">
              <div class="portlet light bordered">
                <div class="portlet-title">
                  <div class="caption">
                    <i class="fa fa-user font-blue-sharp"></i> <span class="caption-subject font-blue-sharp bold uppercase">个人信息</span>
                  </div>
                </div>
                <div class="portlet-body form">
                  <form id="employeeDetailForm" action="sm/sm_employee_detail" method="post">
                    <div class="form-body">
                      <div id="employeeDetailPane" class="modal-body profile-content">
                        <div class="table-toolbar">
                          <div class="row">
                            <div class="col-md-12">
                              <div class="btn-group">
                                <a id="edit_order" class="btn btn-default" onclick="editEmployeeInfo()">
                                  修改 <i class="fa fa-edit"></i>
                                </a>
                                <c:if test="${hasPermession}">
                                <a id="cancel_order" class="btn btn-default" onclick="returnList()">
                                  返回 <i class="fa fa-history"></i>
                                </a>
                                </c:if>
                              </div>
                            </div>
                          </div>
                        </div>
                        <input type="hidden" id="id" name="id" value="${employee.id}" />
                        <input type="hidden" name="ts" value="${employee.tsStr}" />
                        <input type="hidden" id="headerImg" name="headerImg" value="${employee.headerImg}" />
                        <div id="edit-method">
                          <input type="hidden" name="_method" value="POST"/>
                        </div>
                        <div class="form-group">
                          <a class="control-label" onclick="showEmployeeReviewDialog();">员工评价：${employee.score}</a>
                          <div id="start"></div>
                        </div>
                        <div class="form-group">
                          <label class="control-label">员工编码：</label>
                          <input type="text" name="code" class="form-control" maxlength="20" value="${employee.code}" readonly="false" />
                        </div>
                        <div class="form-group">
                          <label class="control-label">员工姓名：</label>
                          <input type="text" name="name" autocomplete="false" class="form-control" value="${employee.name}" readonly="false"  />
                        </div>
                        <div class="form-group">
                          <label class="control-label">员工密码：</label>
                          <input type="password" name="password" class="form-control" value="${employee.password}" readonly="false" />
                        </div>
                        <div class="form-group">
                          <label class="control-label ">状态：</label>
                          <input type="text" name="status" class="form-control" value="${employee.statusName}" readonly="false" />
                        </div>
                        <div class="form-group">
                          <label class="control-label">所属区域：</label>
                          <input type="text" name="orgName" class="form-control" value="${employee.orgName}" readonly="false" />
                        </div>

                        <div class="form-group">
                          <label class="control-label ">员工角色：</label>
                          <input type="text" name="roleName" class="form-control" value="${employee.roleName}" readonly="false" />
                        </div>
                        <div class="form-group">
                          <label class="control-label ">员工职位：</label>
                          <input type="text" name="positionName" class="form-control" value="${employee.positionName}" readonly="false" />
                        </div>
                        <div class="form-group">
                          <label class="control-label ">性别：</label>
                          <input type="text" name="sex" class="form-control" value="${employee.sexName}" readonly="false" />
                        </div>

                        <div class="form-group">
                          <label class="control-label">身份证号：</label>
                          <input type="text" name="idCard" value="${employee.idCard}" class="form-control mask_id_card" readonly="false" />
                        </div>

                        <div class="form-group">
                          <label class="control-label">电话：</label>
                          <input type="text" name="phone" value="${employee.phone}" class="form-control mask_phone" readonly="false" />
                        </div>

                        <div class="form-group">
                          <label class="control-label">备注：</label>
                          <textarea type="text" name="memo" class="form-control" style="max-width:100%;" rows=3 maxlength="400" readonly="false" >${employee.memo}</textarea>
                        </div>
                      </div>
                    </div>
                  </form>
                </div>
                <!-- END EXAMPLE TABLE PORTLET-->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- END CONTENT -->
</div>

<div class="modal fade" id="cutImgDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="cutImgLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="cutImgLabel"><i class="fa fa-upload"></i> 图片裁剪</h4>
      </div>

      <div id="cutImgPane" class="modal-body">
        <div id="cutImage">
          <div id="preview_box" class="previewImg" style="display: none;">
            <img id="previewImg" src="" width="120px"/>
          </div>
          <div class="bigImg" >
            <img id="srcImg" src="" width="400px" height="270px" />
          </div>

          <form action="" method="post" id="crop_form">
            <input type="hidden" id="fileName" name="fileName"/>
            <input type="hidden" id="x" name="x" />
            <input type="hidden" id="y" name="y" />
            <input type="hidden" id="w" name="w" />
            <input type="hidden" id="h" name="h" />
          </form>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-primary" onclick="imgChange()">保存</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="employeeReviewDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="employeeReviewLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="employeeReviewLabel"><i class="fa fa-user"></i> 人员评价</h4>
      </div>

      <div id="employeeReviewPane" class="modal-body">
        <table class="table table-striped table-bordered table-hover" id="employee-review-table">
          <thead>
            <tr>
              <th name="index">序号</th>
              <th name="scoreStar" style="width: 80px">分数</th>
              <th name="description" >评价</th>
              <th name="userName" style="width: 100px">评价用户</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>

        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
        </div>
      </div>
    </div>
  </div>
</div>


<jsp:include page="../sm/sm_org_ref.jsp"/>

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
<script src="<%=resourcePath%>static/admin/js/sm/sm_employee_detail.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/sm/sm_org_ref.js" type="text/javascript" charset="utf-8"></script>

<script src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/common/common-tools.js" type="text/javascript" charset="utf-8"></script>

<script src="<%=resourcePath%>static/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript" charset="utf-8"></script>

<script src="<%=resourcePath%>static/global/plugins/jcrop/js/jquery.color.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/jcrop/js/jquery.Jcrop.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/rater-star/jquery.rater.js" type="text/javascript" charset="utf-8"></script>

<script>
  jQuery(document).ready(function () {
    var roleIdVal = "${employee.roleId}";
    var positionIdVal = "${employee.positionId}";
    var statusVal = "${employee.status}";
    var sexVal = "${employee.sex}";

    loadInboxMsg();
    Metronic.init();
    Layout.init();

    initSelect2(roleIdVal, positionIdVal);

    Validator.init();
    initModifyPasswordDialog();
    initEmployee();
    initOrgRef(false);
    setMenuItemActive("${requestScope.funcActiveCode}");

    initIdCardValidatorMethod();
    initPhoneValidatorMethod();

    initInputMask();

    var id = "${employee.id}";
    if ( id != null && id.length > 0 ) {
      initSelVal(statusVal, sexVal);
    }

    initStart(${employee.score});
    initDescriptionTable();
  });
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->
</html>
