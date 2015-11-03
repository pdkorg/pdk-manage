var timeout = 3000;
var timer = null;

function loadInboxMsg() {
	if (true) {
		$(".msg_count").text(0);
		return;
	}

	toastr.options = {
		"closeButton": false,
		"debug": false,
		"positionClass": "toast-top-right",
		"onclick": null,
		"showDuration": "1000",
		"hideDuration": "1000",
		"timeOut": "5000",
		"extendedTimeOut": "1000",
		"showEasing": "swing",
		"hideEasing": "linear",
		"showMethod": "fadeIn",
		"hideMethod": "fadeOut"
	}
	initInboxMsg();

	timer = setInterval('reloadMessage()',timeout);
	window.onbeforeunload = function(event) {
		if ( timer ) {
			clearInterval(timer);
		}
	}
}

function reloadMessage() {
	$.ajax({
		type:"GET",
		url: "msg/msg_datas",
		dataType:'json',
		success: function(datas) {
			initMsg(eval(datas).messages);
			showNotific(eval(datas).newMessages);
		},
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("重新加载收件夹信息异常！");
		}
	});
}

function initInboxMsg() {
	$.ajax({
		type:"GET",
		url: "msg/msg_datas",
		dataType:'json',
		data: {
		},
		success: function(datas) {
			initMsg(eval(datas).messages);
		},
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("加载收件夹信息异常！");
		}
	});
}

function initMsg(messages) {
	
	if(isNull(messages)) {
		return;
	}
	
	var inboxMsgHtml = "";
	
	$.each(messages, function() {
		inboxMsgHtml += "<li>";
		inboxMsgHtml += "<div class = 'row' style='max-width:95%;margin-left:5px;cursor:pointer;' onclick=\"readMsg('" + this.id + "')\">";
		inboxMsgHtml += "<span class='photo'>";
		inboxMsgHtml += "<img src='" + this.headerImg + "' class='img-circle' alt=''>";
		inboxMsgHtml += "</span>";
		inboxMsgHtml += "<span class='subject'>";
		inboxMsgHtml += "<span class='from'>" + this.fromName + " </span>";
		inboxMsgHtml += "<span class='time' style='color:#d4dadf;'>" + this.timeMsg + " </span>";
		inboxMsgHtml += "</span>";
		inboxMsgHtml += "<span class='message'>"+this.message+"</span>";
		inboxMsgHtml += "</div>";
		inboxMsgHtml += "</li>";
	});
	
	$(".dropdown-menu-list").empty().html(inboxMsgHtml);
	$(".msg_count").text(messages.length);
}

function isNull(obj) {
	return obj == null || !obj;
}

function showNotific(messages) {
	$.each(messages, function() {
		var msg = this.message;
		var name = this.fromName;
		toastr['info'](msg, messages.length);
	});
}

function readMsg(msgId) {
	$.ajax({
		type:"POST",
		url: "msg/msg_datas",
		dataType:'json',
		data: {
			"_method" : "PUT",
			"messageId" : msgId
		},
		success: function(datas) {
			initMsg(eval(datas).messages);
		},
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}