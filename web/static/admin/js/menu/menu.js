
function setCSMenuAction() {
    var csLink = $("#CHAT");
	var csHref = csLink.attr("href");
	csLink.removeAttr("href");
	csLink.click(function () {
		isOpen(csHref);
	});
}

function setMenuItemActive(code) {

	var curr = $(".page-sidebar-menu").find("#"+code);

	var parent = curr.parent();
	var ancestors = parent.parent().parent();

	parent.addClass("active");

	var text = null;
	var parentText = null;

	if(parent.parent().attr("class") == "sub-menu") {
		ancestors.addClass("active open");
		ancestors.find("[class='arrow']").addClass("open");

		text = curr.text();
		parentText = ancestors.find("span[class='title']").text();
	}else {
		text = parent.find("span[class='title']").text();
	}

	var pageBreadcrumbHtml = "";

	if(text == "首页") {
		pageBreadcrumbHtml += "<li><a href='/pdk-manage'>首页</a></li>";
	} else {

		pageBreadcrumbHtml += "<li><a href='/pdk-manage'>首页</a><i class='fa fa-circle'></i></li>";

		if(parentText != null) {
			pageBreadcrumbHtml += "<li><a href='#'>"+parentText+"</a><i class='fa fa-circle'></i></li>"
		}

		pageBreadcrumbHtml += "<li><a href='"+curr.attr("href")+"'>"+text+"</a></li>"
	}

	$(".page-breadcrumb").empty().html(pageBreadcrumbHtml);

    //setCSMenuAction();

	var csLink = $("#CHAT");
	csLink.attr("target","_BLANK");

	var cscLink = $("#CHAT_C");
	cscLink.attr("target","_BLANK");

}


function isOpen(href) {
    //判断是否打开
    if (csWin == null || csWin.closed) {
        if(csWin == null) {
            csWin = window.open(href);
        }
    } else {
        csWin.focus();
    }
}
