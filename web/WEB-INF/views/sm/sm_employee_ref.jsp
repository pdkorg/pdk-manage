<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="employeeRefDialog" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="orgLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="orgLabel"><i class="fa fa-cogs"></i>人员选择</h4>
      </div>
      <div id="employeeDetailPane" class="modal-body">
        <div class="form-group" style="padding-top: 0">
          <div class="portlet-body">
            <table class="table table-striped table-bordered table-hover" id="employee-ref-table">
              <thead>
              <tr>
                <th class="table-checkbox">
                  <input type="checkbox" class="group-checkable"
                         data-set="#employee-table .checkboxes"/>
                </th>
                <th name="index">序号</th>
                <th name="code">员工编号</th>
                <th name="linkName">员工姓名</th>
                <th name="orgName">所属区域</th>
                <th name="sexName">性别</th>
                <th name="phone">电话</th>
              </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" onclick="selectEmployeeRef()">确定</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>
</div>
