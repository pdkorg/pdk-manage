<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="orgRefDialog" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="orgLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="orgLabel"><i class="fa fa-cogs"></i>组织结构</h4>
      </div>
      <div id="orgDetailPane" class="modal-body">
          <div class="form-group" style="padding-top: 0">
              <div class="portlet-body">
                <div class="scroller portlet-body-scroller" style="height:400px">
                  <div id="orgRefTree">
                  </div>
                </div>
              </div>
          </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" onclick="selectOrgRef()">确定</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>
</div>
