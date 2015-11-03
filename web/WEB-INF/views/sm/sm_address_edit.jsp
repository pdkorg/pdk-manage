<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/8/29
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="addressDetailDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="addressDetailLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="addressDetailLabel"><i class="fa fa-truck"></i> 地址</h4>
      </div>

      <div id="addressDetailPane" class="modal-body">
        <form id="addressDetailForm" action="sm/sm_address" method="post">
          <input type="hidden" name="id" value="" />
          <input type="hidden" name="ts" value="" />
          <input type="hidden" name="userId" value="" />
          <div id="edit-method">
          </div>
          <div class="form-group">
            <label class="control-label">收件人：</label>
            <input type="text" id="receiver" name="receiver" class="form-control" maxlength="20" placeholder="请输入收件人"/>
          </div>
          <div class="form-group">
            <label class="control-label">地区：</label>
            <span class="input-group-btn">
              <select id="cityId" name="cityId" class="form-control select2me" style="width:50%">
              </select>
              <select id="areaId" name="areaId" class="form-control select2me" style="width:50%">
              </select>
            </span>
          </div>
          <div class="form-group">
            <label class="control-label">街道地址：</label>
            <textarea type="text" id="street" name="street" class="form-control" style="max-width:100%;max-height: 100px;" maxlength="200" placeholder="请输入街道"></textarea>
          </div>
          <div class="form-group">
            <label class="control-label">邮编：</label>
            <input type="text" id="postNum" name="postNum" class="form-control mask_post_no" placeholder="请输入邮编"/>
          </div>
          <div class="form-group">
            <label class="control-label">联系电话：</label>
            <input type="text" id="phone" name="phone" class="form-control mask_phone" placeholder="请输入电话"/>
          </div>
          <div class="form-group">
            <input type="hidden" id="isDefault" name="isDefault" value="N" />
            <input type="checkbox" id="isDefCheckBox" name="isDefCheckBox" onchange="updDefault()" />&nbsp;是否默认地址
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="saveAddress()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<form id="addressDelForm" action="sm/sm_address" method="POST">
  <input type="hidden" name="_method" value="DELETE"/>
  <div id="id-data">
  </div>
</form>