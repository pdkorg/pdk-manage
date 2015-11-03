/**
 * create by liuhaiming 2015/09/01
 * 公共JS方法类
 */
function singleSelect4Table(table) {
    table.on( 'draw.dt', function () {
        $(this).find(".checkboxes").uniform();

        var checks = $(this).find(".checkboxes");
        $(this).find(".checkboxes").bind("change", function(){
            var sel = this;
            var checked = jQuery(this).is(":checked");
            if ( checked ) {
                checks.each(function() {
                    if (this != sel) {
                        $(this).attr("checked", false);
                    }
                });
            }

            jQuery.uniform.update(checks);
        });
    });
}

function getTimeLong(date) {
    var str = date.replace(/-/g,"/");
    return new Date(str).getTime();
}

function initInputMask() {
    $(".mask_number").inputmask({
        rightAlignNumerics:false,
        digits: 0,
        integerDigits: 5
    });

    $(".mask_decimal").inputmask('decimal', {
        rightAlignNumerics:false,
        digits: 2,
        integerDigits: 5
    });
    $(".mask_decimal").bind("blur", function () {
        if ( $(this).val() != null && $(this).val().length > 0 ) {
            $(this).val( fillDecimalZero($(this).val()) );
        }

    });

    $(".mask_phone").inputmask({
        "mask": "9",
        "repeat": 11,
        "greedy": false
    });
    $(".mask_post_no").inputmask({
        "mask": "9",
        "repeat": 6,
        "greedy": false
    });

    $(".mask_id_card").inputmask({
        "mask": "*",
        "repeat": 18,
        "greedy": false
    });
    $(".mask_age").inputmask({
        "mask": "9",
        "repeat": 2,
        "greedy": false
    });
}



function fillDecimalZero(decimal) {
    decimal = "" + decimal;
    if ( decimal.length > 0 && decimal.indexOf(".") == -1 ) {
        decimal += ".00";
        return decimal;
    }

    var left = decimal.substr(0, decimal.indexOf("."));
    var right = decimal.substr(decimal.indexOf(".")+1);
    if ( left == "" ) {
        left = "0";
    }

    if (right.length == 0) {
        right = "00";
    } else if (right.length == 1) {
        right += "0";
    }

    return left + "." + right;
}

function initDatePickerLangCn() {
    $.fn.datepicker.dates['cn'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        today: "今天",
        clear: "清除"
    };
}

function initDateTimePickerLangCn() {
    $.fn.datetimepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        today: "今天",
        suffix: [],
        meridiem: ["上午", "下午"]
    };
}

/*
--------------------------------日期相关工具---------------------------------------------
 */

/**
 * 获取本周、本季度、本月、上月的开始日期、结束日期
 */
var now = new Date();                    //当前日期
var nowDayOfWeek = now.getDay();         //今天本周的第几天
var nowDay = now.getDate();              //当前日
var nowMonth = now.getMonth();           //当前月
var nowYear = now.getYear();             //当前年
nowYear += (nowYear < 2000) ? 1900 : 0;  //

var lastMonthDate = new Date();  //上月日期
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
var lastYear = lastMonthDate.getYear();
var lastMonth = lastMonthDate.getMonth();

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();

    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"-"+mymonth + "-" + myweekday);
}

//获得某月的天数
function getMonthDays(myMonth){
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
    return   days;
}

//获得本季度的开始月份
function getQuarterStartMonth(){
    var quarterStartMonth = 0;
    if(nowMonth<3){
        quarterStartMonth = 0;
    }
    if(2<nowMonth && nowMonth<6){
        quarterStartMonth = 3;
    }
    if(5<nowMonth && nowMonth<9){
        quarterStartMonth = 6;
    }
    if(nowMonth>8){
        quarterStartMonth = 9;
    }
    return quarterStartMonth;
}

//获得本周的开始日期
function getWeekStartDate() {
    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
    return formatDate(weekStartDate);
}

//获得本周的结束日期
function getWeekEndDate() {
    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
    return formatDate(weekEndDate);
}

//获得本月的开始日期
function getMonthStartDate(){
    var monthStartDate = new Date(nowYear, nowMonth, 1);
    return formatDate(monthStartDate);
}

//获得本月的结束日期
function getMonthEndDate(){
    var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
    return formatDate(monthEndDate);
}

//获得上月开始时间
function getLastMonthStartDate(){
    var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
    return formatDate(lastMonthStartDate);
}

//获得上月结束时间
function getLastMonthEndDate(){
    var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
    return formatDate(lastMonthEndDate);
}

//获得本季度的开始日期
function getQuarterStartDate(){

    var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
    return formatDate(quarterStartDate);
}

//或的本季度的结束日期
function getQuarterEndDate(){
    var quarterEndMonth = getQuarterStartMonth() + 2;
    var quarterStartDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth));
    return formatDate(quarterStartDate);
}


Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};