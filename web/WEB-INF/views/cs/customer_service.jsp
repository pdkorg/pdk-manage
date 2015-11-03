<%--
  Created by IntelliJ IDEA.
  User: hubo
  Date: 2015/8/12
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String resourcePath = request.getServletContext().getInitParameter("resource_path") + "/";
    String token = request.getServletContext().getInitParameter("resource_token");
    String msgServer = request.getServletContext().getInitParameter("msg_server");
    String chatCSWebSocket = request.getServletContext().getInitParameter("chat_cs_websocket");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title>跑的快 | 后台管理系统-客服</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta content="" name="description"/>
    <meta content="" name="author"/>

    <link href="<%=resourcePath%>static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/jquery-tags-input/jquery.tagsinput.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
    <link href="<%=resourcePath%>static/global/plugins/wangEditor/css/wangEditor-1.3.5.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/css/components-rounded.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" id="style_color" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/css/cs/cs.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/admin/css/cs/new.css" rel="stylesheet" type="text/css"/>
    <link href="<%=resourcePath%>static/img/logo.ico" rel="shortcut icon"/>
</head>

<body class="page-header-fixed page-full-width">

<jsp:include page="../base/cs_header.jsp" />

<div class="page-container">
    <div class="page-content-wrapper">
        <div class="page-content">
            <div class="row">
                <div class="col-md-offset-2 col-md-8">
                    <div class="portlet light chart-portlet">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-bubble font-red-sunglo"></i>
                                <span class="caption-subject font-red-sunglo bold uppercase">聊天</span>
                            </div>
                            <div class="actions">
                                <div class="btn-group">
                                    <button id="connect" type="button" class="tab btn btn-default"  onclick="connect()"><i class="fa fa-sign-in"></i> 开始连接</button>
                                    <button id="connect-close" type="button" class="tab btn btn-default" disabled="disabled" onclick="disconnect()"><i class="fa fa-sign-out"></i> 断开连接</button>
                                    <button type="button" class="tab btn btn-default quick-sidebar-toggler" ><i class="fa fa-calendar-check-o"></i> 订单信息(alt+o)</button>
                                    <button id="add-user" type="button" class="tab btn btn-default" disabled="disabled" onclick="showUserListDialog()"><i class="fa fa-user"></i> 添加用户</button>
                                </div>
                            </div>
                        </div>
                        <div class="portlet-body" id="chats">
                            <div class="row">
                                <div class="col-md-4">
                                    <ul class="nav nav-tabs" style="display: none">
                                        <li class="active">
                                            <a id="chat-list-tab" href="ui_buttons.html#comment-list" data-toggle="tab"
                                               aria-expanded="true">
                                                当前聊天 </a>
                                        </li>
                                    </ul>
                                    <div class="list-group scroller">
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="comment-list">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-8">
                                    <div id="chat-content" class="portlet-body-scroller">
                                        <div id="chat-pane" class="tab-content">
                                        </div>
                                    </div>

                                    <div class="chat-form">
                                        <textarea name="summernote" id="chat-input-content" style="height: 200px;display: none">
                                        </textarea>
                                        <div id="quickReply" class="msgPanel">
                                            <ul>
                                            </ul>
                                        </div>
                                        <div class="clearfix">
                                        <button id="sendMsg" type="button" class="btn blue-sharp pull-right " disabled="disabled" onclick="echo()"> 发送(Ctrl+回车)</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <audio id="messageAudio" class="audio"><source src="<%=resourcePath%>static/admin/tipsound.mp3" type="audio/mpeg"></audio>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<form id="imageUploadForm" method="post" enctype="multipart/form-data">
    <input name="uploadFile" type="file" style="display: none;" accept="image/gif, image/png ,image/jpg, image/bmp, image/jpeg">
    <input name="token" value="<%=token%>" type="hidden">
    <input name="path" value="/chat/img" type="hidden">
    <input name="module" value="CT" type="hidden">
    <input name="uploadType" value="image" type="hidden">
</form>

<div class="modal fade" id="csListDialog" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="csListLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="csListLabel"><i class="fa fa-cogs"></i> 在线客服</h4>
            </div>
            <div id="csListDetailPane" class="modal-body">
                <table class="table table-striped table-bordered table-hover" id="cs-list-table">
                    <thead>
                    <tr>
                        <th class="table-checkbox">
                        </th>
                        <th>序号</th>
                        <th>客服编号</th>
                        <th>客服名称</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="doRepeat()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        </div>
        </div>
    </div>
</div>

<div class="modal fade" id="userListDialog" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="userListLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="userListLabel"><i class="fa fa-cogs"></i> 在线客服</h4>
            </div>
            <div id="userListDetailPane" class="modal-body">
                <table class="table table-striped table-bordered table-hover" id="user-list-table">
                    <thead>
                    <tr>
                        <th class="table-checkbox">
                        </th>
                        <th>序号</th>
                        <th>用户头像</th>
                        <th>用户昵称</th>
                        <th>sourceId</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="addChatedUser()">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../sm/sm_address_edit.jsp" />
<jsp:include page="../sm/sm_user_detail_edit_dialog.jsp"/>


<%--<a href="javascript:;" class="page-quick-sidebar-toggler"><i class="icon-login"></i></a>--%>
<div class="page-quick-sidebar-wrapper">
    <div class="page-quick-sidebar">
        <jsp:include page="orderInfo.jsp"/>
    </div>
</div>

<div class="page-footer text-center">
    <div class="page-footer-inner">
        © 2015 zhenglizhe, Inc. Licensed under MIT license.
    </div>
    <div class="scroll-to-top">
        <i class="icon-arrow-up"></i>
    </div>
</div>

<script src="<%=resourcePath%>static/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script>

    var resourcePath = "<%=resourcePath%>";
    var baseUrl = "<%=msgServer%>";

    var editor;

    $(function(){
        $("#imageUploadForm").find('input[name="uploadFile"]').change(function() {
            if(this.value == null || this.value == '') {
                return;
            }
            submitImageUploadForm();
        })
    });

    function submitImageUploadForm() {
        var form = $("#imageUploadForm");
        var $path = form.find("[name='path']");
        var basePath = $path.attr("value");
        $path.attr("value", basePath + "/" + sendToId);
        var options  = {
            url: "<%=resourcePath%>file/upload",
            success: sendImage
        };
        form.ajaxSubmit(options);
        $path.attr("value", basePath);
    }

</script>
<script src="<%=resourcePath%>static/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/quick-sidebar.js" type="text/javascript"></script>

<script src="<%=resourcePath%>static/global/plugins/select2/select2.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/datatables/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"
        type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

<script src="<%=resourcePath%>static/global/plugins/bootstrap-daterangepicker/moment.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>

<script src="<%=resourcePath%>static/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-tags-input/jquery.tagsinput.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/fancybox/source/jquery.fancybox.pack.js" type="text/javascript"></script>

<script src="<%=resourcePath%>static/global/plugins/wangEditor/js/wangEditor-1.3.5.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/demo.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/sm/sm_address_edit.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/common/common-tools.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js"  type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/sm/sm_user_edit_dialog.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/cs/cs.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">

    var ws = null;

    var chatEchoMap = {};
    var loadChatPageNumMap = {};

    var imageHeadImg = "";

    var id = "${user.id}";

    var name = "${user.name}";

    var code = "${user.code}";

    var headImg = "${user.headerImg}";

    var headerImgPath;

    if(headImg == "") {
        headerImgPath = "static/img/user-default-header.png";
    }else {
        headerImgPath = headImg;
    }

    var sendToId = null;

    var sendName = null;

    var currChatPaneTab = null;

    var chatPane = $("#chat-pane");

    var repeatUserId = null;

    var csListDialog = $('#csListDialog');
    var userListDialog = $('#userListDialog');

    var csListDataTable = null;
    var userListDataTable = null;

    var messageAudio = document.getElementById("messageAudio");

    function initCSListTableData(datas) {
        csListDataTable.rows().remove().draw();

        var index = 0;
        $.each(datas, function () {
            index++;
            if (this.id != id) {
                csListDataTable.row.add({
                    "DT_RowId": this.id,
                    "checked": "<input type='checkbox' class='checkboxes'/>",
                    "index": index,
                    "code": this.code,
                    "name": this.name
                }).draw();
            }
        });

        $("#cs-list-table").find(".checkboxes").uniform();
    }

    function initUserListTableData(datas) {

        userListDataTable.rows().remove().draw();

        var index = 0;

        $.each(datas, function () {
            index++;
            userListDataTable.row.add({
                "DT_RowId":  "_" + this.id,
                "checked": "<input type='checkbox' class='checkboxes'/>",
                "index": index,
                "headImgHtml": "<img width='24' height='24' src='" + this.headImgHtml + "' />",
                "name": this.name,
                "sourceId": this.sourceId
            }).draw();
        });

        $("#user-list-table").find(".checkboxes").uniform();
    }

    $(function() {
        window.onbeforeunload = function() {
            if(ws != null) {
                return "当前正在服务中,确认是否关闭服务？";
            }
        };

        csListDialog.on('hidden.bs.modal', function (e) {
            repeatUserId = null;
        });

        csListDialog.on('show.bs.modal', function (e) {

            Metronic.blockUI({
                target: '#csListDetailPane',
                overlayColor: 'none',
                animate: true
            });

            $.ajax({
                url: baseUrl + "/online/cs?r=" + new Date().getTime(),
                data: {},
                dataType: "json",
                async: true,
                type: "GET",
                success: function (datas) {
                    Metronic.unblockUI('#csListDetailPane');

                    if (csListDataTable == null) {
                        initCSListTable();
                    }

                    initCSListTableData(datas);
                }
            });
        });


        userListDialog.on('hidden.bs.modal', function (e) {

        });

        userListDialog.on('show.bs.modal', function (e) {

            Metronic.blockUI({
                target: '#userListDetailPane',
                overlayColor: 'none',
                animate: true
            });

            $.ajax({
                url: baseUrl + "/chat/today/u?csId="+id + "&r=" + new Date().getTime(),
                data: {},
                dataType: "json",
                async: true,
                type: "GET",
                success: function (datas) {
                    Metronic.unblockUI('#userListDetailPane');

                    if (userListDataTable == null) {
                        initUserListTable();
                    }

                    var response = eval(datas);

                    if (response.result == "success") {
                        initUserListTableData(response.data);
                    }
                }
            });
        });

        $(document).keydown(function(event){
            if(event.ctrlKey && event.which == 13)  {
                echo();
            } else if(event.altKey && event.which == "O".charCodeAt())  {
                $(".quick-sidebar-toggler").click();
            }
        });
    });


    function getUrl() {
        <%--if (window.location.protocol == 'http:') {--%>
        <%--} else {--%>
        <%--url = 'wss://' +chatIp + ":" + chatPort + chatContext + "/websocket/cs"+ "?id=" + id + "&name=" + encodeURIComponent(name) + "&code=" + encodeURIComponent(code) +"&headImg=" + headerImgPath;--%>
        <%--}--%>
        return "<%=chatCSWebSocket%>?id=" + id + "&name=" + encodeURIComponent(name)
                + "&code=" + encodeURIComponent(code) +"&headImg=" + headerImgPath;
    }

    function setConnected(connected) {
        if(connected) {
            $("#connect").attr("disabled","disabled");
            $("#connect-close").removeAttr("disabled");
            $("#add-user").removeAttr("disabled");
            $("#sendMsg").removeAttr("disabled");
        }else {
            $("#connect").removeAttr("disabled");
            $("#connect-close").attr("disabled","disabled");
            $("#add-user").attr("disabled","disabled");
            $("#sendMsg").attr("disabled","disabled");
        }
    }

    function connect() {

        var url = getUrl();

        ws = new WebSocket(url);

        ws.onopen = function () {
            setConnected(true);
        };

        ws.onmessage = function (event) {
            var msg =  eval("(" + event.data + ")");
            if(isUpdateUserList(msg.msgType)) {
                addChatUserList(msg.fromId, msg.fromName, msg.fromHeadImg, msg.content);
            } else if(isText(msg.msgType) || isImage(msg.msgType) || isVoice(msg.msgType)) {
                appendConsole(msg, "left", false);
                messageAudio.play();
            } else if(isDuplicateConnect(msg.msgType)) {
                alert(msg.content);
                if (ws != null) {
                    ws.close();
                    ws = null;
                }
                window.close();
            } else if(isUserNotOnline(msg.msgType)) {
                setUserStatusNotOnline(msg.fromId);
            } else if(isLocation(msg.msgType)) {
                addUserLocation(msg.fromId, msg.content);
            } else if(isSendToUserError(msg.msgType)) {
                appendTimeClassMsg(msg.fromId, msg.content);
            }
        };

        ws.onclose = function() {
            closeWebSocket();
        };
        ws.onerror = function(evt) {
            closeWebSocket();
        };
    }
    function disconnect() {
        if (ws != null && confirm("确定是否断开连接？")) {
            ws.close();
            ws = null;
        }
    }


    function closeWebSocket() {
        setConnected(false);
        chatPane.empty();
        sendToId = null;
        sendName = null;
        $("#comment-list").empty();
        setConnected(false);
        var badge = $("#chat-list-tab").find(".badge");
        if(badge.length > 0) {
            badge.remove();
        }
        chatEchoMap={};
        loadChatPageNumMap={};
        userOrderCache = {};
        loadUserInfo(null);
        clearQueryOrder();
    }

    function adjustScroll() {
        if(currChatPaneTab == null) {
            return;
        }

        var chatContent = $("#chat-content");

        var height = chatContent.height();

        var contentHeight = currChatPaneTab.height();

        if(contentHeight > height) {

            chatContent.scrollTop(contentHeight);

//            chatContent.slimScroll({
//                start:"bottom"
//            });

        }
    }

    function addUserLocation(userId, content) {
        var $location = $("#" + userId).find(".user-location");
        $location.addClass("fa").addClass("fa-map-marker");
        $location.popover({container: 'body',title:'用户地理位置',content:content,trigger:'hover'});
    }

    function addChatUserList(userId, userName ,userImg, userType) {

        var $userId = $("#"+userId);

        if($userId.length > 0) {
            if($userId.attr("offline")) {
                $userId.find(".from").text("(" + userType + ")" + userName);
                $userId.removeAttr("offline");
            }
            return;
        }
        loadChatPageNumMap[userId] = 1;
        initUserOrderCache(userId);

        var img = userImg == null ? "<%=resourcePath%>" + headerImgPath : userImg;
        var itemHtml = "<a id='" + userId + "' class='list-group-item border-none' onclick='changeCurrChatListActive($(this))'> ";
        itemHtml += "<span class='photo'><img src='" + img + "' class='img' style='height: 40px; border-radius: 4px;' alt=''></span>";
        itemHtml += "<span class='subject'><span class='from'> "+ "(" + userType + ")" + userName + "</span><span>&nbsp;&nbsp;<i class='fa fa-share-square-o' onclick='event.stopPropagation();repeat(\""+userId+"\")'></i>&nbsp;&nbsp;<i class='user-location'></i></span></span>";
        itemHtml += "<button type='button' class='close' onclick='event.stopPropagation();removeChatUserFromList($(this).parent())'><span aria-hidden='true'>&times;</span></button></a>";
        $("#comment-list").append(itemHtml);
        chatEchoMap[userId] = "";
        createNewChatPane(userId);
        if($("#comment-list").children().length == 1) {
            changeCurrChatListActive($("#"+userId));
        }
        $("#"+userId).find(".close").tooltip({
            container: '#'+userId ,
            placement:'left',
            title: '关闭'
        });
        $("#"+userId).find(".fa-share-square-o").tooltip({
            container: 'body',
            title: '重新指派'
        });
    }

    function setUserStatusNotOnline(userId) {
        var name = $("#" + userId).find(".from").text();
        $("#" + userId).find(".from").text(name + "(离线)");
        $("#" + userId).attr("offline", true);
    }

    function loadChatMsg(userId) {
        $.ajax({
            url: baseUrl + "/chatmsg/" + userId + "?pageNum="+loadChatPageNumMap[userId]+ "&r=" + new Date().getTime(),
            data:{},
            dataType:"json",
            type:"get",
            success:function(datas) {
                currChatPaneTab.find(".chatMsgMore").remove();
                loadChatPageNumMap[userId] = loadChatPageNumMap[userId] + 1;

                var currHtml = currChatPaneTab.html();

                currChatPaneTab.empty();

                $(eval(datas)).each(function(){
                    var dir;
                    if(this.fromId == userId) {
                        dir = "left";
                    } else {
                        dir = "right";
                    }
                    appendConsole(this, dir, true);
                });

                currChatPaneTab.append(currHtml);

                if(loadChatPageNumMap[userId] > 10) {
                    prependChatMsgMoreMsg(userId, "更多消息请在消息记录中查阅")
                } else {
                    if(datas.length == 10) {
                        prependChatMsgMoreLink(userId);
                    }
                }
            }
        });
    }


    function appendConsole(msg, dir, isHistoryMsg) {

        var content = null;
        if(msg.content == null || msg.content == "") {
            content = " ";
        } else {
            content = msg.content;
        }

        var msgTemplateHtml;

        if(isImage(msg.msgType)) {
            content = "<a href='<%=resourcePath%>" + msg.content+"' style='display:block;' class='fancybox-button' data-rel='fancybox-button'><img class='tu' src='<%=resourcePath%>" + msg.content+"' alt=''></a>";
            msgTemplateHtml = "<li class='"+dir+" li-img'>";
        } else if(isVoice(msg.msgType)){
            msgTemplateHtml = "<li class='video video-" + dir + "' onclick='playAudio(this)'> ";
            content = '<audio preload="metadata" class="audio"><source src="<%=resourcePath%>' + msg.content + '" type="audio/mpeg"></audio><img src="<%=resourcePath%>static/img/video-'+dir+'.png" alt=""><div class="miao"><b></b><span>"</span></div><span class="dian"></span>';
        } else {
            msgTemplateHtml = "<li class='"+dir+"'>";
        }

        if(dir == "left") { //微信用户头像直接从微信获取
            msgTemplateHtml += "<div class='tx'><img src='"+msg.fromHeadImg+"' alt=''></div>";
        } else {
            msgTemplateHtml += "<div class='tx'><img src='<%=resourcePath%>"+msg.fromHeadImg+"' alt=''></div>";
        }

        msgTemplateHtml += content + "</li>";

        var msgContainer;

        if(dir == "left"){
            msgContainer = $("#tab-" + msg.fromId);
        }else {
            msgContainer = $("#tab-" + msg.sendToId);
        }

        appendDateTimeToChatPane(msg.createTime, msgContainer);

        msgContainer.append(msgTemplateHtml);

        if(isVoice(msg.msgType)) {
            setTimeout("setVoiceTime('<%=resourcePath%>" + msg.content +"')", 500);
        }

        if(isImage(msg.msgType)) {
            msgContainer.find(".fancybox-button").fancybox({
                groupAttr: 'data-rel',
                prevEffect: 'none',
                nextEffect: 'none',
                closeBtn: true,
                helpers: {
                    title: {
                        type: 'inside'
                    }
                }
            });
        }

        if(!isHistoryMsg) {
            if(msgContainer.attr("id") == currChatPaneTab.attr("id")) {
                adjustScroll();
            }else {
                if(dir == "left") {
                    increaseUnReadMsgHitNum(msg.fromId);
                }
            }
        }

    }

    function setVoiceTime(audioSrc) {
        var source = $(".audio source[src='"+audioSrc+"']");
        var audioLi = source.parents(".video");
        var duration = source.parent()[0].duration;
        audioLi.find(".miao b").text(duration.toFixed(0));
        var width = 10 + 5 * duration;
        audioLi.css("padding-left", width+ "px");
    }

    function appendDateTimeToChatPane(msgTimeStr, msgContainer) {
        var currTime = getTime(msgTimeStr);

        if(isToDay(currTime)) {
            msgTimeStr = msgTimeStr.split(" ")[1];
        }

        var lastTimeStr = msgContainer.find(".time:last").text();

        if(lastTimeStr == null || lastTimeStr == "") {
            msgContainer.append("<li class='time'>"+msgTimeStr+"</li>");
        } else {
            var lastTime = getTime(lastTimeStr);
            if(currTime - lastTime > 1000 * 60 * 2 ) {
                msgContainer.append("<li class='time'>"+msgTimeStr+"</li>");
            }
        }
    }

    function isToDay(currTime) {
        var d1 = new Date();
        var d2 = new Date();
        d2.setTime(currTime);
        return d1.getFullYear() == d2.getFullYear() && d1.getMonth() == d2.getMonth()
                && d1.getDay() == d2.getDay();
    }

    function getTime(timeStr) {

        if(timeStr.length == "hh:mm:ss".length) {
            var d = new Date();
            timeStr = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate() + " " + timeStr;
        }else if(timeStr.length == "yyyy-MM-dd hh:mm".length) {
            timeStr = timeStr + ":00";
        }

        return new Date(timeStr.replace(/-/,"/")).getTime();
    }

    function getInputText() {
        var html = editor.html();
        html = html.replace("<div>", "\r");
        html = html.replace(/<div>c<\/div>/g, "\r");
        html = html.replace(/<div>/g, "");
        html = html.replace(/<\/div>/g, "\r");
        html = html.replace(/<br>/g, "\r");
        html = html.replace(/<span[^>]*>/g, "");
        html = html.replace(/<\/span>/g, "");
        html = html.replace(/&nbsp;/g, "");
        return html;
    }

    function echo() {
        if (ws != null && sendToId != null) {
            if(!$("#" + sendToId).attr("offline")) {
                var message = getInputText();
                if(message == "") {
                    return;
                }
                var msg = {
                    fromId:id,
                    fromName:name,
                    fromHeadImg:headerImgPath,
                    content:message,
                    createTime:new Date().Format("yyyy-MM-dd hh:mm:ss"),
                    sendToId:sendToId,
                    sendToName:sendName,
                    msgType: 1
                };
                var json = JSON.stringify(msg);
                ws.send(json);

                msg.content = editor.html();
                appendConsole(msg, "right", false);
                editor.html("");
            } else {
                alert('不能发送，当前用户已经下线！');
            }
        } else {
            alert('不能发送，当前还没有用户连接！');
        }
    }

    function createNewChatPane(userId) {
        var chatPaneTabHtml = "<ul id='tab-"+ userId +"' class='tab-pane chat-thread clearfix'></ul>";
        chatPane.append(chatPaneTabHtml);
        prependChatMsgMoreLink(userId, "查看历史消息");
    }

    function prependChatMsgMoreLink(userId, text) {
        var linkText = "查看更多历史消息";
        if(text != null) {
            linkText = text;
        }
        $("#tab-" + userId).prepend("<li class='chatMsgMore more'><a href='javascript:void(0)' onclick='loadChatMsg(\""+userId+"\")'><i class='fa fa-clock-o'></i>"+linkText+"</a></li>");
    }

    function prependChatMsgMoreMsg(userId, text) {
        $("#tab-" + userId).prepend("<li class='chatMsgMore more'>"+text+"</li>");
    }

    function appendTimeClassMsg(userId, text) {
        $("#tab-" + userId).append("<li class='time'>"+text+"</li>");
    }

    function changeCurrChatListActive($currA) {

        if($currA.attr("id") == sendToId) {
            return;
        }

        if(sendToId != null) {
            chatEchoMap[sendToId] = editor.html()
            saveOrderToCache(sendToId);
        }

        $("#comment-list").find("a.active").removeClass("active");

        $currA.addClass("active");
        sendToId = $currA.attr("id");
        sendName = $currA.find(".from").text();

        editor.html(chatEchoMap[sendToId]);
        setOrderValueByCache(sendToId);

        changeChatPaneTab("#tab-" + sendToId);

        clearUnReadMsgHitNum(sendToId);

        adjustScroll();

        loadUserInfo(sendToId);
        initQueryOrder(sendToId);
    }

    function changeChatPaneTab(chatTabId) {
        currChatPaneTab = $(chatTabId);
        $("#chat-pane").find("ul.tab-pane").removeClass("active");
        currChatPaneTab.addClass("active");
    }

    function removeUser($currA) {

        var currAId = $currA.attr("id");

        delete chatEchoMap[currAId];
        delete loadChatPageNumMap[currAId];
        delUserOrderFromCache(currAId);

        $("#tab-" + currAId).remove();

        var badge = $("#" + currAId).find(".badge");

        if (badge.length > 0) {
            decreaseAllUnReadMsgHitNum(parseInt(badge.text().trim()));
        }

        $currA.remove();

        var children = $("#comment-list").find(".list-group-item");

        if (children.length > 0 && currAId == sendToId) {
            children.eq(0).click();
        }

        if(children.length == 0) {
            sendToId = null;
            sendName = null;
            loadUserInfo(null);
            clearQueryOrder();
        }

    }

    function removeChatUserFromList($currA) {
        if(confirm("是否确定删除？")) {
            $.ajax({
                url:baseUrl+ "/" + id + "/close/"+$currA.attr("id"),
                data:{},
                dataType:"json",
                type:"post",
                success:function() {
                    removeUser($currA);
                }
            });

        }
    }

    function increaseAllUnReadMsgHitNum() {

        var chatListTab = $("#chat-list-tab");

        var badge = chatListTab.find(".badge");

        if(badge.length == 0) {
            chatListTab.append("<span class='badge badge-danger'>0</span>");
            badge = chatListTab.find(".badge");
        }

        var sum = parseInt(badge.text().trim()) + 1;

        badge.text(" " + sum);
    }

    function increaseUnReadMsgHitNum(userItemId) {
        var userItem = $("#" + userItemId);

        var badge = userItem.find(".badge");
        if(badge.length > 0) {
            badge.text(" " + (parseInt(badge.text().trim()) + 1));
        } else {
            userItem.find(".subject").append("<span class='badge badge-danger' style='margin-left: 10px'> 1 </span>");
        }

        increaseAllUnReadMsgHitNum();
    }

    function clearUnReadMsgHitNum(userItemId) {

        var badge = $("#"+userItemId +" .badge");

        if(badge.length > 0) {
            var count = parseInt(badge.text().trim());
            badge.remove();
            decreaseAllUnReadMsgHitNum(count);
        }
    }

    function decreaseAllUnReadMsgHitNum(count) {
        var badge = $("#chat-list-tab").find(".badge");

        var sum = parseInt(badge.text().trim()) - count;

        if(sum == 0) {
            badge.remove();
        }else {
            badge.text(" " + sum);
        }
    }

    function isText(msgType) {
        return msgType != null && msgType == 1;
    }

    function isImage(msgType) {
        return msgType != null && msgType == 2;
    }

    function isVoice(msgType) {
        return msgType != null && msgType == 3;
    }

    function isLocation(msgType) {
        return msgType != null && msgType == 4;
    }

    function isUpdateUserList(msgType) {
        return msgType != null && msgType == 6;
    }

    function isDuplicateConnect(msgType) {
        return msgType != null && msgType == 7;
    }

    function isUserNotOnline(msgType) {
        return msgType != null && msgType == 8;
    }

    function isSendToUserError(msgType) {
        return msgType != null && msgType == 9;
    }

    function repeat(userId) {
        repeatUserId = userId;
        if($("#" + repeatUserId).attr("offline")) {
            alert("用户已经离线，不能重新指派！");
            return;
        }
        $('#csListDialog').modal('show');
    }

    function showUserListDialog() {
        $('#userListDialog').modal('show');
    }

    function initCSListTable() {
        var table = $("#cs-list-table");
        csListDataTable = table.DataTable({
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },

            "columns": [
                { "data": "checked" },
                { "data": "index" },
                { "data": "code" },
                { "data": "name" }
            ],

            "paging": true,
            "serverSide": false,
            "bStateSave": true,
            "lengthMenu": [
                [10, 20, 30],
                [10, 20, 30]
            ],
            "pageLength": 10,
            "pagingType": "bootstrap_full_number",
            "columnDefs": [{
                'orderable': false,
                'targets': [0, 1]
            }, {
                "searchable": false,
                "targets": [0, 1]
            }],
            "order": [
                [2, "asc"]
            ]
        });

        $("#cs-list-table_wrapper").find('.dataTables_length select').select2({
            formatNoMatches: function(term) {
                return "找不到匹配项目";
            },
            minimumResultsForSearch:-1
        });


    }

    function initUserListTable() {
        var table = $("#user-list-table");
        userListDataTable = table.DataTable({
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },

            "columns": [
                { "data": "checked" },
                { "data": "index" },
                { "data": "headImgHtml" },
                { "data": "name" },
                { "data": "sourceId" }
            ],

            "paging": true,
            "serverSide": false,
            "bStateSave": true,
            "ordering":false,
            "lengthMenu": [
                [10, 20, 30],
                [10, 20, 30]
            ],
            "pageLength": 10,
            "pagingType": "bootstrap_full_number",
            "columnDefs": [
                {
                    "searchable": false,
                    "targets": [0, 1, 2]
                }
            ]
        });

        $("#user-list-table_wrapper").find('.dataTables_length select').select2({
            formatNoMatches: function(term) {
                return "找不到匹配项目";
            },
            minimumResultsForSearch:-1
        });


    }

    function doRepeat() {

        var checkeds = $("#cs-list-table").find(".checkboxes:checked");

        if(checkeds.length > 0) {
            var destCSId = checkeds.eq(0).parents("tr").attr("id");
            $.ajax({
                url: baseUrl + "/"+id+"/change/"+repeatUserId+"/to/"+destCSId+"?r=" + new Date().getTime(),
                data: {},
                dataType: "json",
                async: true,
                type: "GET",
                success: function (data) {
                    var result = eval(data).result;
                    if (result == "success") {
                        removeUser($("#"+repeatUserId));
                        alert("重新指派成功");
                        csListDialog.modal("hide");
                    } else {
                        var tipMsg = "重新指派失败!";
                        var error = eval(data).errorMsg;
                        if(error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                }
            });
        } else {
            alert("请选择一行数据！");
        }
    }

    function addChatedUser() {

        var checkeds = $("#user-list-table").find(".checkboxes:checked");

        if(checkeds.length > 0) {
            var userId = checkeds.eq(0).parents("tr").attr("id").substring(1);
            $.ajax({
                url: baseUrl + "/chat/today/u?userId=" + userId + "&csId=" + id ,
                data: {},
                dataType: "json",
                async: true,
                type: "POST",
                success: function (data) {
                    var result = eval(data).result;
                    if (result == "success") {
                        userListDialog.modal("hide");
                    } else {
                        var tipMsg = "添加用户失败!";
                        var error = eval(data).errorMsg;
                        if(error != null && error != "") {
                            tipMsg += "\n" + error;
                        }
                        alert(tipMsg);
                    }
                }
            });
        } else {
            alert("请选择一行数据！");
        }
    }

    function sendImage(data) {

        $('input[name="uploadFile"]').val(null);

        var result = eval(data);

        if(result.result) {
            var msg = {
                fromId: id,
                fromName: name,
                fromHeadImg:headerImgPath,
                content: eval(data).fileUri,
                createTime: new Date().Format("yyyy-MM-dd hh:mm:ss"),
                sendToId: sendToId,
                sendToName: sendName,
                msgType: 2
            };

            ws.send(JSON.stringify(msg));

            appendConsole(msg, "right", false);

        } else {
            alert(result.errorMsg);
        }
    }


    function playAudio(obj) {
        var au=$(obj).children('.audio')[0];
        $(obj).siblings('.video').each(function() {
            $(this).children(".audio")[0].pause();
        });
        au.play();
        $(obj).children('.dian').hide();
    }

</script>
</body>
</html>