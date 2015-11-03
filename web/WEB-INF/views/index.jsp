<%--
  Created by IntelliJ IDEA.
  User: hubo
  Date: 2015/8/8
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
  String resourcePath = request.getServletContext().getInitParameter("resource_path") + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]>
<html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
  <base href="<%=basePath%>" />
  <meta charset="utf-8"/>
  <title>跑的快 | 后台管理系统-首页</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8">
  <meta content="" name="description"/>
  <meta content="" name="author"/>
  <!-- BEGIN GLOBAL MANDATORY STYLES -->
  <link href="<%=resourcePath%>static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
  <link href="<%=resourcePath%>static/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
  <!-- END GLOBAL MANDATORY STYLES -->

  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/morris/morris.css"/>

  <!-- BEGIN THEME STYLES -->
  <link href="<%=resourcePath%>static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="<%=resourcePath%>static/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
  <link href="<%=resourcePath%>static/admin/layout4/css/custom.css" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/admin/css/index.css"/>

  <link rel="stylesheet" type="text/css" href="<%=resourcePath%>static/global/plugins/bootstrap-toastr/toastr.min.css"/>

  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="<%=resourcePath%>static/img/logo.ico"/>
</head>

<body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-fixed page-sidebar-closed-hide-logo">

  <jsp:include page="base/header.jsp" />
<!-- BEGIN CONTAINER -->
<div class="page-container">
  <!-- BEGIN SIDEBAR -->
  <jsp:include page="base/menu.jsp" />
  <!-- END SIDEBAR -->
  <!-- BEGIN CONTENT -->
  <div class="page-content-wrapper">
    <div class="page-content">
      <!-- BEGIN PAGE HEADER-->
      <!-- BEGIN PAGE HEAD -->
      <ul class="page-breadcrumb breadcrumb">
      </ul>
      <!-- BEGIN PAGE CONTENT-->
      <div class="row margin-top-10">
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-green-sharp">
                  ￥ <span id="mny">0</span>
                </h3>
                <small>总交易额</small>
              </div>
              <div class="icon">
                <i class="fa fa-money"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
                <span id="payMnyPersentBar" style="width: 0%;" class="progress-bar progress-bar-success green-sharp">
                <span class="sr-only"></span>
                </span>
              </div>
              <div class="status">
                <div class="status-title">
                  支付比例
                </div>
                <div class="status-number">
                  <span id="payMnyPersent"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-red-haze">￥ <span id="feeMny">0</span></h3>
                <small>小费金额</small>
              </div>
              <div class="icon">
                <i class="icon-like"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
                <span id="feePersentBar" style="width: 0%;" class="progress-bar progress-bar-success red-haze">
                </span>
              </div>
              <div class="status">
                <div class="status-title">
                  给小费比例
                </div>
                <div class="status-number">
                  <span id="feePersent"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-blue-sharp"><span id="payedCount">0</span></h3>
                <small>总订单数</small>
              </div>
              <div class="icon">
                <i class="icon-basket"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
								<span id="payedPersentBar" style="width: 0%;" class="progress-bar progress-bar-success blue-sharp">
								</span>
              </div>
              <div class="status">
                <div class="status-title">
                  支付比例
                </div>
                <div class="status-number">
                  <span id="payedPersent"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
          <div class="dashboard-stat2">
            <div class="display">
              <div class="number">
                <h3 class="font-purple-soft"><span id="newUserCount">0</span></h3>
                <small>新用户数</small>
              </div>
              <div class="icon">
                <i class="icon-user"></i>
              </div>
            </div>
            <div class="progress-info">
              <div class="progress">
								<span id="newUserKeepPersentBar" style="width: 0%;" class="progress-bar progress-bar-success purple-soft">
								<%--<span class="sr-only">56% change</span>--%>
								</span>
              </div>
              <div class="status">
                <div class="status-title">
                  新用户留存率
                </div>
                <div class="status-number">
                  <span id="newUserKeepPersent" ></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row" >
        <div class="col-md-6">
          <div class="portlet light ">
            <div class="portlet-title">
              <div class="caption caption-md">
                <i class="icon-bar-chart theme-font-color hide"></i>
                <span class="caption-subject theme-font-color bold uppercase">交易总览</span>
              </div>
          </div>
          <div class="portlet-body">
              <div id = "orderPayInfo"></div>
          </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="portlet light ">
            <div class="portlet-title">
              <div class="caption caption-md">
                <i class="icon-bar-chart theme-font-color hide"></i>
                <span class="caption-subject theme-font-color bold uppercase">订单类型比例</span>
              </div>
            </div>
            <div class="portlet-body">
              <div id = "flowTypePie"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- END PAGE CONTENT-->
    </div>
  </div>
  <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<div class="page-footer">
  <div class="page-footer-inner">
    © 2015 zhenglizhe, Inc. Licensed under MIT license.
  </div>
  <div class="scroll-to-top">
    <i class="icon-arrow-up"></i>
  </div>
</div>
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>

<![endif]-->
<script src="<%=resourcePath%>static/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="<%=resourcePath%>static/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/js/validator.js"  type="text/javascript"></script>


<script src="<%=resourcePath%>static/global/plugins/morris/morris.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/morris/raphael-min.js" type="text/javascript" charset="utf-8"></script>
<!-- END CORE PLUGINS -->
<script src="<%=resourcePath%>static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/layout.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/layout4/scripts/demo.js" type="text/javascript"></script>
<script src="<%=resourcePath%>static/admin/pages/scripts/index3.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/menu/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/inbox/inbox_msg.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=resourcePath%>static/admin/js/head/header.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/modules/exporting.js"></script>

<script>
  jQuery(document).ready(function () {
    loadInboxMsg();
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    Demo.init(); // init demo features
    Validator.init();
    setMenuItemActive("${requestScope.funcActiveCode}");
    initModifyPasswordDialog();

    initChartData();
    initOrderChartData();
    initFlowTypePieChartData(0);
  });

  function initChartData() {
    $.ajax({
      url: "index/chart_data",
      dataType:"json",
      success:function(datas){
        var data = eval(datas).data;
        if ( eval(datas).result == "success" ) {
          $("#mny").html(data.mny);
          $("#payMnyPersent").html(data.payedMnyPersent + "%");
          $("#payMnyPersentBar").css("width", data.payedMnyPersent + "%");

          $("#feeMny").html(data.feeMny);
          $("#feePersent").html(data.feePersent + "%");
          $("#feePersentBar").css("width", data.feePersent + "%");

          $("#payedCount").html(data.payedOrderCount);
          $("#payedPersent").html(data.payedPersent + "%");
          $("#payedPersentBar").css("width", data.payedPersent + "%");


          $("#newUserCount").html(data.newUserCount);
          $("#newUserKeepPersent").html(data.userKeepPersent + "%");
          $("#newUserKeepPersentBar").css("width", data.userKeepPersent + "%");
        }
      }
    });
  }

  function initOrderChartData() {
    initOrderChart();
    $.ajax({
      url: "index/chart_order_data",
      dataType:"json",
      success:function(datas){
        var data = eval(datas).data;
        if ( eval(datas).result == "success" ) {
          var mnys = [];
          var payedMnys = [];
          var couponMnys = [];
          var feeMnys = [];
          for ( var idx = 0; idx < data.length; idx++ ) {
            mnys[idx] = data[idx].mny;
            payedMnys[idx] = data[idx].payedMny;
            couponMnys[idx] = data[idx].couponMny;
            feeMnys[idx] = data[idx].feeMny;
          }

          initOrderChart(mnys, payedMnys, couponMnys);
        }
      }
    });
  }

  function initOrderChart(mnys, payedMnys, couponMnys) {
    $('#orderPayInfo').highcharts({
      chart: {
        type: 'column'
      },
      title: {
        text: ''
      },
      subtitle: {
        text: '月度对比'
      },
      xAxis: {
        categories: ['一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月'],
        title: {
          text: '月份'
        }
      },
      yAxis: {
        min: 0,
        title: {
          text: '金额',
        }
      },
      tooltip: {
        borderColor: 'black',         // 边框颜色
        borderRadius: 10,             // 边框圆角
        borderWidth: 0,               // 边框宽度
        shadow: true,                 // 是否显示阴影
        animation: true,               // 是否启用动画效果
        headerFormat: '<span style="font-size:10px">{point.key}</span>{}',
        pointFormat : '<table><tbody><tr><td style="color:{series.color};padding:0">{series.name}: </td><td style="padding:0"><b>{point.y:.2f} 元</b></td></tr></tbody></table>',
        footerFormat: '',
        shared: true,
        useHTML: true
      },
      plotOptions: {
        column: {
          pointPadding: 0.1,
          borderWidth: 0,
          events: {
            click: function (event) {
              initFlowTypePieChartData(event.point.index);
            }
          }
        }
      },
      series: [
          {
              name: '总交易额',
              data: mnys
          },
          {
              name: '实付金额',
              data: payedMnys
          },
          {
              name: '优惠券金额',
              data: couponMnys
          },
//          {
//              name: '小费金额',
//              data: feeMnys
//          },
      ]
    });
  }

  function initFlowTypePieChartData(month) {
    $.ajax({
      url: "index/chart_flow_type_pie_data/" + month,
      dataType:"json",
      success:function(datas){
        var data = eval(datas).data;
        if ( eval(datas).result == "success" ) {
          var pieData = [];
          var item = [];
          for (var idx = 0; idx < data.length; idx++) {
            item = [data[idx].flowTypeName, data[idx].orderCount];
            pieData[idx] = item;
          }
          initFlowTypePieChart(pieData);
        }
      }
    });
  }

  function initFlowTypePieChart(pieData) {
//    $('#flowTypePie').highcharts({
    var opt = {
      chart: {
        renderTo: 'flowTypePie',
        type: 'pie',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
      },
      title: {
        text: '类型比例图'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            color: '#000000',
            connectorColor: '#000000',
            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
          }
        }
      },
      series: [{
        name: 'Browser share',
        data: pieData
      }]
    };

    opt.series.data = pieData;
    var chart = new Highcharts.Chart(opt);
  }
</script>
<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->
</html>

