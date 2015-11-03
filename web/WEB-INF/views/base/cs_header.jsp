<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String resourcePath = request.getServletContext().getInitParameter("resource_path") + "/";
%>
<div class="page-header navbar">
  <!-- BEGIN HEADER INNER -->
  <div class="page-header-inner">
    <!-- BEGIN LOGO -->
    <div class="page-logo">
      <a href="">
        <img src="<%=resourcePath%>static/img/icon.png" alt="logo" class="logo-default"/>
      </a>
    </div>
    <!-- BEGIN PAGE TOP -->
    <div class="page-top">
      <!-- BEGIN HEADER SEARCH BOX -->
      <!-- DOC: Apply "search-form-expanded" right after the "search-form" class to have half expanded search box -->
      <form class="search-form" action="extra_search.html" method="GET">
        <div class="input-group">
          <input type="text" class="form-control input-sm" placeholder="Search..." name="query">
					<span class="input-group-btn">
					<a href="javascript:void(0)" class="btn submit"><i class="icon-magnifier"></i></a>
					</span>
        </div>
      </form>
      <!-- END HEADER SEARCH BOX -->
      <!-- BEGIN TOP NAVIGATION MENU -->
      <div class="top-menu">
        <ul class="nav navbar-nav pull-right">
          <li class="separator hide">
          </li>
          <li class="separator hide">
          </li>
          <!-- BEGIN INBOX DROPDOWN -->
          <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
          <li class="dropdown dropdown-extended dropdown-inbox dropdown-dark" id="header_inbox_bar">
            <a href="javascript:void(0);" class="dropdown-toggle inbox" data-toggle="dropdown" data-hover="dropdown"
               data-close-others="true">
              <i class="icon-envelope-open"></i> 收件箱
						<span class="badge badge-danger">
						0 </span>
            </a>
            <ul class="dropdown-menu">
              <li class="external">
                <h3>你有 <span class="bold text-danger">4 </span> 条新的消息</h3>
                <a href="inbox.html">查看所有</a>
              </li>
              <li>
                <ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283">
                </ul>
              </li>
            </ul>
          </li>
          <!-- END INBOX DROPDOWN -->
          <li class="separator hide">
          </li>
          <!-- BEGIN USER LOGIN DROPDOWN -->
          <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
          <li class="dropdown dropdown-user dropdown-dark">
            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
               data-close-others="true">
						<span class="username username-hide-on-mobile">
                          ${user.name} </span>
              <!-- DOC: Do not remove below empty space(&nbsp;) as its purposely used -->
              <c:choose>
                <c:when test="${user.headerImg == null}">
                  <img alt="" id="img-circle" class="img-circle" src="<%=resourcePath%>static/img/user-default-header.png"/>
                </c:when>
                <c:when test="${user.headerImg==''}">
                  <img alt="" id="img-circle" class="img-circle" src="<%=resourcePath%>static/img/user-default-header.png"/>
                </c:when>
                <c:otherwise>
                  <img alt="" id="img-circle" class="img-circle" src="<%=resourcePath%>${user.headerImg}"/>
                </c:otherwise>
              </c:choose>

            </a>
            <ul class="dropdown-menu dropdown-menu-default">
              <li>
                <a href="sm/sm_employee_detail/${user.id}?funcActiveCode=USER&canEdit=true" >
                  <i class="icon-settings"></i> 设置 </a>
              </li>
              <li>
                <a data-toggle="modal" data-target="#modifyPasswordDialog">
                  <i class="icon-lock"></i> 修改密码 </a>
              </li>
              <li>
                <a href="logout">
                  <i class="icon-key"></i> 注销 </a>
              </li>
            </ul>
          </li>
          <!-- END USER LOGIN DROPDOWN -->
        </ul>
      </div>
      <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END PAGE TOP -->
  </div>
  <!-- END HEADER INNER -->
</div>

<div class="modal fade" id="modifyPasswordDialog" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="modifyPasswordLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="modifyPasswordLabel"><i class="fa fa-lock"></i> 修改密码</h4>
      </div>
      <div id="modifyPasswordPane" class="modal-body">
        <form id="modifyPasswordForm" action="sm/password/edit" method="post">
          <div class="form-group">
            <label class="control-label">当前密码：</label>
            <input type="password" name="password" class="form-control" placeholder=""/>
          </div>
          <div class="form-group">
            <label class="control-label">新密码：</label>
            <input type="password" name="newPassword" class="form-control" placeholder=""/>
          </div>
          <div class="form-group">
            <label class="control-label">确认新密码：</label>
            <input type="password" name="confirmPassword" class="form-control" placeholder=""/>
          </div>
        </form>
        <div class="modal-footer">
          <button id="modifyPasswordBtn" type="button" class="btn btn-primary" data-loading-text="保存中...">保存</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="clearfix">
</div>
