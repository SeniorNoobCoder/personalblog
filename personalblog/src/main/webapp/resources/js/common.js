/**
 * Created by shaox_000 on 2015/3/6.
 */
// 检查是否是合法的经度
function checkLng(lng) {
    var re = /^\d{1,3}$|^\d{1,3}\.\d{1,15}$/;

    if(!re.test(lng) || lng > 180 || lng <0) {
        return false;
    }
    return true;
}

// 检查是否是合法的纬度
function checkLat(lat) {
    var re = /^\d{1,2}$|^\d{1,2}\.\d{1,15}$/;

    if(!re.test(lat) || lat > 90 || lat <0) {
        return false;
    }
    return true;
}

// 检查是否是合法的桩号和桩号差值
function checkPilenoOffset(num) {
    var re = /^\d{1,4}$|^\d{1,4}\.\d{1,3}$/;

    if(re.test(num)) {
        return true;
    }
    return false;
}

// 检查是否是合法的手机号码（以1开头的11位数字）
function checkMobile(mobile) {
    var re = /^1\d{10}$/;
    if(re.test(mobile)) {
        return true;
    }
    return false;
}

// 检查是否是合法的电话号码（最多12位数字或者中间有-）
function checkTel(tel) {
    var re = /^\d{12}$|^\d{2,4}-\d{7,8}$/;
    if(re.test(tel)) {
        return true;
    }
    return false;
}

//毫秒数转换成日期
function update(d) {
    return d > 9 ? d : '0' + d;
}
function  changeTime(time){
    if(!time)  return "";
    var d=new Date();
    d.setTime(time);
    var year=d.getFullYear();
    var day=d.getDate();
    var month=d.getMonth()+1;
    var hour=d.getHours();
    var minute=d.getMinutes();
    var second=d.getSeconds();
    return  year+"-"+update(month)+"-"+update(day)+" "+update(hour)+":"+update(minute)+":"+update(second);
}



//获取当前日期  2016-07-01
var getCurrentDay = function(){
    var d=new Date();
    var year=d.getFullYear();
    var month=d.getMonth()+1;
    var day=d.getDate();
    return year+"-"+update(month)+"-"+update(day);
};
//获取当前日期-30天  2016-07-01
var getCurrentDaySub = function(num){
    var time = new Date().getTime()-1000*60*60*24*num;
    var d=new Date();
    d.setTime(time);
    var year=d.getFullYear();
    var month=d.getMonth()+1;
    var day=d.getDate();
    return year+"-"+update(month)+"-"+update(day);
};
//获取某个日期的前num天的日期
var getThisDaySub = function(timeStr, num){
    var time = new Date(timeStr).getTime()-1000*60*60*24*num;
    var d=new Date();
    d.setTime(time);
    var year=d.getFullYear();
    var month=d.getMonth()+1;
    var day=d.getDate();
    return year+"-"+update(month)+"-"+update(day);
};
//获取某个日期的后num天的日期
var getThisDayPlus = function(timeStr, num){
    var time = new Date(timeStr).getTime()+1000*60*60*24*num;
    var d=new Date();
    d.setTime(time);
    var year=d.getFullYear();
    var month=d.getMonth()+1;
    var day=d.getDate();
    return year+"-"+update(month)+"-"+update(day);
};

//获取上一个月日期 2016-07-01
var getLastMonth = function(){
    var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth();
    var day = d.getDate();
    return year+"-"+update(month)+"-"+update(day);
}

//获取当前月份  2016-07
var getCurrentMonth = function(){
    var d=new Date();
    var year=d.getFullYear();
    var month=d.getMonth()+1;
    return year+"-"+update(month);
};
//获取当前月份前N月的日期
var getCurrentMonthPlus = function(strMonth,num){
    var time = new Date(strMonth).getTime()-60*60*24*30*1000*num;
    var d=new Date();
    d.setTime(time);
    var year=d.getFullYear();
    var month=d.getMonth()+1;
    var day=d.getDate();
    return year+"-"+update(month);
};
/**
 * Created by shaox_000 on 2015/3/6.
 */
// 检查是否是合法的经度
function checkLng(lng) {
    var re = /^\d{1,3}$|^\d{1,3}\.\d{1,15}$/;

    if(!re.test(lng) || lng > 180 || lng <0) {
        return false;
    }
    return true;
}

// 检查是否是合法的纬度
function checkLat(lat) {
    var re = /^\d{1,2}$|^\d{1,2}\.\d{1,15}$/;

    if(!re.test(lat) || lat > 90 || lat <0) {
        return false;
    }
    return true;
}

// 检查是否是合法的桩号和桩号差值
function checkPilenoOffset(num) {
    var re = /^\d{1,4}$|^\d{1,4}\.\d{1,3}$/;

    if(re.test(num)) {
        return true;
    }
    return false;
}

// 检查是否是合法的手机号码（以1开头的11位数字）
function checkMobile(mobile) {
    var re = /^1\d{10}$/;
    if(re.test(mobile)) {
        return true;
    }
    return false;
}

// 检查是否是合法的电话号码（最多12位数字或者中间有-）
function checkTel(tel) {
    var re = /^\d{12}$|^\d{2,4}-\d{7,8}$/;
    if(re.test(tel)) {
        return true;
    }
    return false;
}

//将毫秒数转换成时间格式
function changeDateFormat(time){
    if(!time)  return "";
    //首先要new一个日期对象
    var date = new Date();
    //将日期对象指向当前毫秒数对应的日期时间
    date.setTime(time);
    //往下就可以通过各种方法获取年月日时分秒了
    return date.toString();
    //return date.toDateString();//返回yyyy-MM-dd
    //return date.toTimeString();//返回hh:mm:ss
}
//传入毫秒数 返回yyyy-MM-dd hh:mm:ss（时间格式可以自己组装）
function changeFormat(time){
    if(!time)  return "";
    //首先要new一个日期对象
    var date = new Date();
    //将日期对象指向当前毫秒数对应的日期时间
    date.setTime(time);
    //往下就可以通过各种方法获取年月日时分秒了
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()

}
//传入毫秒数 返回yyyy年MM月dd日 hh时mm分ss秒
function changeDate(time) {
    if (!time)  return "";
    var date = new Date();
    date.setTime(time);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var date1 = date.getDate();
    var hour = date.getHours();
    var minutes = date.getMinutes();
    var second = date.getSeconds();
    return (update(year) + "年" + update(month) + "月" + update(date1) + "日" + update(hour) + "时" + update(minutes) + "分" + update(second) + "秒" );
}
function change1Date(time) {
    if (!time)  return "";
    var date = new Date();
    date.setTime(time);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var date1 = date.getDate();
    var hour = date.getHours();
    var minutes = date.getMinutes();
    var second = date.getSeconds();
    return ( update(month) + "月" + update(date1) + "日" + update(hour) + "时");
}

function initEventDate() {
    var d = new Date();
    var year = d.getFullYear();
    var day = d.getDate();
    var month = d.getMonth() + 1;
    var hour = d.getHours();
    var minute = d.getMinutes();
    return year + "-" + update(month) + "-" + update(day) + " " + update(hour) + ":" + update(minute) + ":00" ;
}


//毫秒数转换成日期
function update(d) {
    return d > 9 ? d : '0' + d;
}
function  changeTime(time){
    if(!time)  return "";
    var d=new Date();
    d.setTime(time);
    var year=d.getFullYear();
    var day=d.getDate();
    var month=d.getMonth()+1;
    var hour=d.getHours();
    var minute=d.getMinutes();
    var second=d.getSeconds();
    return  year+"-"+update(month)+"-"+update(day)+" "+update(hour)+":"+update(minute)+":"+update(second);
}

function changeEventDate(time){
    if(!time)  return "";
    var d=new Date();
    d.setTime(time);
    var day=d.getDate();
    var month=d.getMonth()+1;
    var hour=d.getHours();
    var minutes=d.getMinutes();
    if(minutes == 0){
        return parseInt(month) + "月" + parseInt(day) + "日" + parseInt(hour) + "时" ;
    }else{
        return parseInt(month) + "月" + parseInt(day) + "日" + parseInt(hour) + "时" + parseInt(minutes) + "分";
    }
    //return parseInt(month) + "月" + parseInt(day) + "日" + parseInt(hour) + "时" + parseInt(minutes) + "分";
}


//(不建议使用)日期时间转成毫秒数  （2012-12-25 20:17:24 转换成2012/12/25 20:17:24）
function changeDateTime(date){
    if(!date)  {
        return "";
    }
    date = date.replace(new RegExp("-","gm"),"/");  //时间格式的转换 2012-12-25 20:17:24 转换成2012/12/25 20:17:24
    return (new Date(date)).getTime(); //得到毫秒数
}


//时间转换成毫秒数
function changeDate2Mills(date){
    if(!date)  return "";
    var a=date.split(/[^0-9]/);
    var d=new Date(a[0],a[1]-1,a[2],a[3],a[4],a[5]);
    return d.getTime();
}

//check纯数字（不能以0开头  不能为负  长度为2到11位）
function checkNum(num) {
    var re = /^[^0|-]\d{1,10}$/;
    if(re.test(num)) {
        return true;
    }
    return false;
}

function checkName(name){
    var patt = ['傻','二','蛋','杀'];
    for(var i=0;i<patt.length;i++){
        if(name.indexOf(patt[i])>=0){
            return true;
        }
    }
    return false;
}
//改变桩号的显示样式
function change(positionType){
    if(!positionType)  return "";
    if(/^\d{1,4}$/.test(positionType)){
        return "K" + positionType;
    }else {
        var str1 = (positionType+"").split(".")[0];
        var str2 = (positionType+"").split(".")[1];
        return "K" + str1 + "+" + str2;
    }
}

function getDate(){
    var dateTime = new Date();
    var year = dateTime.getFullYear();
    var month = dateTime.getMonth() + 1;
    var day =  dateTime.getDate();
    //alert(year);
    //alert(month);
    //alert(day);
    var nums = ["零","一","二","三","四","五","六","七","八","九"];
    var years = [];
    var months = [];
    var days = [];
    do{
        years.unshift(nums[year%10]);
        year = parseInt(year/10);
    }while(year!= 0);

    do{
        if(months.length > 0){
            months.unshift("十");
        }
        months.unshift(nums[month%10]);
        month = parseInt(month/10);
    }while(month!= 0);

    if(months.length == 3){
        if(months[2]=="零"){
            months[2] = "";
        }
        if(months[0]=="一"){
            months[0] = "";
        }

    }

    do{
        if(days.length > 0){
            days.unshift("十");
        }
        days.unshift(nums[day%10]);
        day = parseInt(day/10);
    }while(day!= 0);

    if(days.length == 3){
        if(days[2]=="零"){
            days[2] = "";
        }
        if(months[0]=="一"){
            days[0] = "";
        }

    }
    //alert(years.join("")+"年"+months.join("")+"月"+days.join("")+"日");
    return years.join("")+"年"+months.join("")+"月"+days.join("")+"日";
}
// 检查是否是数字和字母组合的密码
function checkKey(key) {
    var re = /^[0-9a-zA-Z]*$/g;

    if(re.test(key)) {
        return true;
    }
    return false;
}

//获取年份下拉
function  getYearList(){
    var yearList =  [];
    var nowDate = new Date();
    var nowYear = Number(nowDate.getFullYear());        //获取当前年份(2位)
    var endYear = 2015;
    var num = nowYear - endYear;
    for(var i=0;i<num+1;i++){
        yearList[i] = nowYear;
        nowYear = nowYear-1;
    }
    return yearList;
}