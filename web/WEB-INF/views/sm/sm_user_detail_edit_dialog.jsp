<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/9/25
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="userEditDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="userEditDlgLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="userEditLabel"><i class="fa fa-edit"></i> 用户信息</h4>
      </div>

      <div id="userEditDlgPane" class="modal-body">
        <form id="userEditDlgForm" action="sm/sm_user" method="POST">
          <input type="hidden" name="id" value="" />
          <input type="hidden" name="ts" value="" />
          <div id="edit-method">
            <input type="hidden" name="_method" value="PUT"/>
          </div>
          <div class="form-group">
            <label class="control-label">用户昵称：</label>
            <input type="text" id="name" name="name" class="form-control" value="" maxlength="50" placeholder="请输入用户昵称"/>
          </div>
          <div class="form-group">
            <label class="control-label">姓名：</label>
            <input type="text" id="realName" name="realName" class="form-control" value="" maxlength="50" placeholder="请输入姓名"/>
          </div>
          <div class="form-group">
            <label class="control-label">性别：</label>
            <select id="sex" name="sex" class="form-control select2me-2 pdk-valid">
              <option value="1">男</option>
              <option value="2">女</option>
            </select>
          </div>
          <div class="form-group">
            <label class="control-label">年龄：</label>
            <input type="text" id="age" name="age" class="form-control mask_age" />
          </div>
          <div class="form-group">
            <label class="control-label">电话：</label>
            <input type="text" id="phone" name="phone" class="form-control mask_phone" />
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="saveUser4Dlg()">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
    </div>
  </div>
</div>
