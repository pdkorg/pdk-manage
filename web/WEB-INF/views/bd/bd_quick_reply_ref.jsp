<%--
  Created by IntelliJ IDEA.
  User: liuhaiming
  Date: 2015/9/1
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="quickReplyDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="quickReplyLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="quickReplyLabel"><i class="fa fa-truck"></i> 回复消息列表</h4>
      </div>

      <div id="quickReplyPane" class="modal-body">
        <div class="table-toolbar">
          <div class="row">
            <div class="col-md-12">
              <div class="btn-group">
                <a id="new_order" class="btn btn-default" data-toggle="modal" data-target="#quickReplyDetailDialog">
                  新增 <i class="fa fa-plus"></i>
                </a>
                <a id="edit_order" class="btn btn-default" onclick="quickReplyEdit()">
                  修改 <i class="fa fa-edit"></i>
                </a>
                <a id="del_order" class="btn btn-default" onclick="quickReplyDel()">
                  删除 <i class="fa fa-trash-o"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
        <table class="table table-striped table-bordered table-hover" id="quickReply-table">
          <thead>
          <tr>
            <th class="table-checkbox">
            </th>
            <th name="index">序号</th>
            <th name="info">消息</th>
          </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="quickReplyDetailDialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="quickReplyDetailLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="quickReplyDetailLabel"><i class="fa fa-truck"></i> 回复消息</h4>
      </div>

      <div id="quickReplyDetailPane" class="modal-body">
        <form id="quickReplyDetailForm" action="bd/bd_quick_reply" method="post">
          <input type="hidden" name="id" value="" />
          <input type="hidden" name="ts" value="" />
          <input type="hidden" name="sort" value="" />
          <div id="edit-method">
          </div>
          <div class="form-group">
            <label class="control-label">消息：</label>
            <input type="text" id="info" name="info" class="form-control" placeholder="请输入消息"/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="saveQuickReply()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<form id="quickReplyDelForm" action="bd/bd_quick_reply" method="POST">
  <input type="hidden" name="_method" value="DELETE"/>
  <div id="id-data">
  </div>
</form>

<form id="quickReplyUpdateStatusForm" action="bd/bd_quick_reply/status" method="POST">
  <input type="hidden" name="_method" value="PUT"/>
  <div id="status-id-data">

  </div>
</form>