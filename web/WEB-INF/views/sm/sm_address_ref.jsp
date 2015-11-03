<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/8/29
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="addressDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="addressLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="addressLabel"><i class="fa fa-truck"></i> 地址信息</h4>
      </div>

      <div id="addressPane" class="modal-body">
        <div class="table-toolbar">
          <div class="row">
            <div class="col-md-12">
              <div class="btn-group">
                <a id="new_order" class="btn btn-default" data-toggle="modal" onclick="addressAdd()">
                  新增 <i class="fa fa-plus"></i>
                </a>

                <a id="edit_order" class="btn btn-default" onclick="addressEdit()">
                  修改 <i class="fa fa-edit"></i>
                </a>

                <a id="del_order" class="btn btn-default" onclick="addressDel()">
                  删除 <i class="fa fa-trash-o"></i>
                </a>

                <a id="def_order" class="btn btn-default" onclick="address2Def()">
                  设为默认 <i class="fa fa-check-circle-o"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
        <table class="table table-striped table-bordered table-hover" id="address-table">
          <thead>
          <tr>
            <th class="table-checkbox">
            </th>
            <th name="index">序号</th>
            <th name="receiver">收件人</th>
            <th name="fullAddress">详细地址</th>
            <th name="isDefault">是否默认地址</th>
          </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>


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
              <select id="cityId" name="cityId" class="form-control select2me-2 pdk-valid" style="width:50%">
              </select>
              <select id="areaId" name="areaId" class="form-control select2me-2 pdk-valid" style="width:50%">
              </select>
            </span>
          </div>
          <div class="form-group">
            <label class="control-label">街道地址：</label>
            <textarea type="text" id="street" name="street" class="form-control" style="max-width:100%;max-height: 100px;" maxlength="200" placeholder="请输入街道"></textarea>
          </div>
          <div class="form-group">
            <label class="control-label">邮编：</label>
            <input type="text" id="postNum" name="postNum" class="form-control mask_post_no" maxlength="6" placeholder="请输入邮编"/>
          </div>
          <div class="form-group">
            <label class="control-label">联系电话：</label>
            <input type="text" id="phone" name="phone" class="form-control mask_phone" maxlength="11" placeholder="请输入电话"/>
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

<form id="addressUpdateStatusForm" action="sm/sm_address/status" method="POST">
  <input type="hidden" name="_method" value="PUT"/>
  <div id="status-id-data">

  </div>
</form>