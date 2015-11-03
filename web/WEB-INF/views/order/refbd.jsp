<%--
  Created by IntelliJ IDEA.
  User: chengxiang
  Date: 15/8/28
  Time: 下午7:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Modal -->
<div class="modal fade" id="userDialog" tabindex="-1" data-backdrop="static" role="dialog"
     aria-labelledby="userLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="userLabel"><i class="fa fa-users"></i> 粉丝列表</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12">
            <table class="table table-striped table-bordered table-hover text-nowrap" id="user-table">
              <thead>
              <tr>
                <th class="table-checkbox"></th>
                <th name="index">序号</th>
                <th name="name">用户昵称</th>
                <th name="realName">用户名称</th>
                <th name="sexName">用户性别</th>
                <th name="age">用户年龄</th>
                <th name="phone">电话号码</th>
                <th name="registerTime">关注时间</th>
                <th name="unRegisterTime">取消关注时间</th>
              </tr>
              </thead>
              <tbody>

              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="userSelect">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="employeeDialog" tabindex="-1" data-backdrop="static" role="dialog"
     aria-labelledby="userLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="employeeLabel"><i class="fa fa-users"></i> 员工列表</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12">
            <table class="table table-striped table-bordered table-hover text-nowrap" id="employee-table">
              <thead>
              <tr>
                <th class="table-checkbox"></th>
                <th name="index">序号</th>
                <th name="code">员工编号</th>
                <th name="name">员工名称</th>
                <th name="sexName">员工性别</th>
                <th name="phone">电话号码</th>
              </tr>
              </thead>
              <tbody>

              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="employeeSelect">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
<!-- Modal -->
<div class="modal fade" id="flowDialog" tabindex="-1" data-backdrop="static" role="dialog"
     aria-labelledby="userLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="flowLabel"><i class="fa fa-users"></i> 员工列表</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12">
            <table class="table table-striped table-bordered table-hover text-nowrap" id="flow-table">
              <thead>
              <tr>
                <th class="table-checkbox"></th>
                <th name="index">序号</th>
                <th name="code">员工编号</th>
                <th name="name">员工名称</th>
                <th name="memo">备注</th>
              </tr>
              </thead>
              <tbody>

              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="flowSelect">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>