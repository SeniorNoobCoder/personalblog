<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../system/admin/top.jsp" %>
<style>
    .menu-tab ul{margin:0 !important;}
    .menu-tab li{ float:left; list-style-type:none;  text-align:center; height:38px;  line-height: 40px; width:80px; border-right:1px solid #e3e3e3;}
    .menu-xz-tab{ border-top:2px solid #438eb9; background:#fff;}
    .menu-tab li a{    text-decoration: none;}
    .btnw{width: 140px;}
    .btnw a{margin:3px;}
</style>
<div class="container-fluid" id="main-container">
    <%@include file="../../system/admin/left.jsp" %>
    <div id="main-content" class="clearfix">
        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="#">综合管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
                <li class="active">菜单管理</li>
            </ul><!--.breadcrumb-->
            <div id="nav-search">
            </div><!--#nav-search-->
        </div><!--#breadcrumbs-->
        <div id="page-content" class="clearfix">
            <%--<form action="${pageContext.request.contextPath}/common/commonMenu/findListPage.do?classify=${pd.classify }&am=${mPd.am }&bm=${mPd.bm }" method="post" name="memuForm" id="memuForm">--%>
                <div class="row-fluid">
                    <div class="row-fluid">
                        <div style="padding-top: 10px;">
                            <div class="widget-box" style="">
                                <div class="widget-header">
                                    <div class="menu-tab">
                                        <ul>
                                            <li class="menu-xz-tab" id="menu_li" onclick="cutTab('menu');"><a href="#">来电记录</a></li>
                                            <li id="button_li" onclick="cutTab('button');"><a href="#">客户列表</a></li>
                                        </ul>
                                    </div>
                                    <div id="tools_part" style="position: absolute; top: 5px; right: 400px; display: none;">
                                        <a style="cursor: pointer;" onclick="showTools('part');"><img src="${pageContext.request.contextPath}/resources/images/right.png"/></a>
                                    </div>
                                    <%--<div id="tools_all" style="position: absolute; top: 5px; right: 180px;">--%>
                                        <%--<a style="cursor: pointer;" onclick="showTools('all');"><img src="${pageContext.request.contextPath}/resources/images/left.png"/></a>--%>
                                    <%--</div>--%>
                                    <%--<div id="export" style="display: block">--%>
                                        <%--<a class="dc-icon5" data-type="xls" href="javascript:;"></a>--%>
                                        <%--<a class="dc-icon6" data-type="doc" href="javascript:;"></a>--%>
                                        <%--<span style="display: none; width: 290px;" id="tools_span">--%>
                                            <%--<a class="dc-icon8" data-type="pdf" href="javascript:;"></a>--%>
                                            <%--<a class="dc-icon2" style="cursor: pointer;" data-type="txt" onclick="dc('txt');"></a>--%>
                                            <%--<a class="dc-icon7" data-type="image" href="javascript:;"></a>--%>
                                            <%--<a class="dc-icon1" style="cursor: pointer;" data-type="json" onclick="dc('json');"></a>--%>
                                            <%--<a class="dc-icon3" data-type="csv" href="javascript:;"></a>--%>
                                            <%--<a class="dc-icon4" data-type="xml" href="javascript:;"></a>--%>
                                        <%--</span>--%>
                                    <%--</div>--%>
                                    <%--<a class="dc-icon9" style="cursor: pointer; position:absolute; right:70px;top:6px;" onclick="print();"></a>--%>
                                    <span class="widget-toolbar">
                                        <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                                    </span>
                                    <div style="clear:both;"></div>
                                </div>
                                <!-- 菜单start -->
                                <div class="widget-body" id="menu_div">
                                    <div class="widget-body-inner">
                                        <form action="${pageContext.request.contextPath}/order/order/toAdd.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="callRecordForm" id="userForm">
                                        <div style="padding: 10px 0 0 10px">
                                            <table>
                                                <tr>
                                                    <td><font color="#808080">设备号：</font></td>
                                                    <td><input type="text" name="customer_sn" id="customer_sn" value="${pd.sn }" placeholder="这里输入设备号" style="width:130px;" onblur="showCallRecordList()"/></td>
                                                    <%--<td><font color="#808080">用户姓名：</font></td>--%>
                                                    <%--<td><input type="text" name="name" value="${pd.name }" placeholder="这里输入用户名称" style="width:130px;"/></td>--%>
                                                    <td style="vertical-align:top;"><a href="#" class="btn btn-mini btn-light" onclick="showCallRecordList();"><i id="nav-search-icon" class="icon-search"></i></a></td>
                                                </tr>
                                            </table>
                                        </div>
                                        <%--<c:if test="${fn:contains(power,'menu_add')}">--%>
                                            <%--<a class="btn btn-mini btn-info" style="width:56px; margin:10px 0 0 12px;" title="添加" onclick="addMenu();">&nbsp;添加&nbsp; </a>--%>
                                        <%--</c:if>--%>
                                        <div class="widget-main">
                                            <div class="row-fluid">
                                                <div  id="table_print" style="border: 1px white solid;">
                                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                                        <thead>
                                                        <tr>
                                                            <th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>
                                                            <th>设备编号</th>
                                                            <th>客户名称</th>
                                                            <th>客户性别</th>
                                                            <th>客户电话</th>
                                                            <th>来电时间</th>
                                                            <th>状态</th>
                                                            <th>受理人</th>
                                                            <th>受理时间</th>
                                                            <th id="exclude_1" class="center" style="width:135px;">操作</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody id="callRecordListCenter">
                                                        <!-- 开始循环 -->
                                                        <c:choose>
                                                            <c:when test="${not empty callRecordList}">
                                                                <c:forEach items="${callRecordList}" var="var" varStatus="vs">
                                                                    <tr ondblclick="findInfo('${var.customer_id }')">
                                                                        <td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
                                                                        <td>${var.customer_sn }</td>
                                                                        <td>${var.name }</td>
                                                                        <td>${var.sex }</td>
                                                                        <td>${var.phone }</td>
                                                                        <%--<td>${var.address }</td>--%>
                                                                        <td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                                        <td>
                                                                            <c:if test="${var.status == '0'}"><span style="color: #00be67">未受理</span></c:if>
                                                                            <c:if test="${var.status == '1'}"><span style="color: #b4d5ac">已受理</span></c:if>
                                                                        </td>
                                                                        <td>${var.operator_name }</td>
                                                                        <td><fmt:formatDate value="${var.operator_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                                        <td id="exclude_1_${var.id }">
                                                                            <c:if test="${fn:contains(power,'order_add')&&var.status == '0'}">
                                                                                <a class='btn btn-mini btn-info' title="受理" onclick="acceptCall('${var.id }');"><i class='icon-ok'></i></a>
                                                                            </c:if>
                                                                            <c:if test="${fn:contains(power,'order_add')&&pd.user_id == var.operator_id&&var.status == '1'&&var.name!=null}">
                                                                                <a class='btn btn-mini btn-success' title="填写订单" onclick="orderAdd('${var.id }','${var.customer_sn }','callRecord');"><i class='icon-pencil'></i></a>
                                                                            </c:if>
                                                                            <c:if test="${fn:contains(power,'order_add')&&pd.user_id == var.operator_id&&var.status == '1'&&var.name==null}">
                                                                                <a class='btn btn-mini btn-success' title="添加客户信息" onclick="addCustomer();"><i class='icon-user'></i></a>
                                                                            </c:if>
                                                                            <c:if test="${fn:contains(power,'order_add')&&pd.user_id == var.operator_id&&var.status == '1'}">
                                                                                <a class='btn btn-mini btn-primary' title="回访" onclick="visitingAdd('${var.id }','${var.customer_sn }','callRecord');"><i class='icon-phone-sign'></i></a>
                                                                            </c:if>
                                                                            <%--<c:if test="${fn:contains(power,'telemarketer_del')&&pd.user_id == var.operator_id&&var.status == '1'}">--%>
                                                                                <%--<a class='btn btn-mini btn-danger' title="毁单" onclick="del('${var.id }','${var.order_no}');"><i class='icon-trash'></i></a>--%>
                                                                            <%--</c:if>--%>
                                                                            <c:if test="${fn:contains(power,'order_add')&&pd.user_id == var.operator_id&&var.status == '1'}">
                                                                                <a class='btn btn-mini btn-default' title="退回" onclick="replyCall('${var.id }');"><i class='icon-reply'></i></a>
                                                                            </c:if>
                                                                        </td>
                                                                    </tr>

                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <tr class="main_info">
                                                                    <td colspan="10" class="center">没有相关数据</td>
                                                                </tr>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <%--<div class="page-header position-relative">--%>
                                                    <%--<table style="width:100%;">--%>
                                                        <%--<tr>--%>
                                                            <%--<!--										<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增</a></td>-->--%>
                                                            <%--<td style="vertical-align:top;">--%>
                                                                <%--<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;" id="callRecordListPage">${page.pageStr}</div>--%>
                                                            <%--</td>--%>
                                                        <%--</tr>--%>
                                                    <%--</table>--%>
                                                <%--</div>--%>
                                            </div>
                                        </div>
                                        </form>
                                    </div>
                                </div>
                                <!-- 菜单end -->
                                <!-- 按钮start -->
                                <div class="widget-body" id="button_div" style="display:none">
                                    <div class="widget-body-inner">
                                        <div style="padding: 10px 0 0 10px">
                                            <table>
                                                <tr>
                                                    <td><font color="#808080">设备号：</font></td>
                                                    <td><input type="text" name="sn" id="sn" value="${pd.sn }" placeholder="这里输入设备号" style="width:130px;"/></td>
                                                    <td><font color="#808080">用户姓名：</font></td>
                                                    <td><input type="text" name="name" id="name" value="${pd.name }" placeholder="这里输入用户名称" style="width:130px;"/></td>
                                                    <td style="vertical-align:top;"><a href="#" class="btn btn-mini btn-light" onclick="showCustomerList();"><i id="nav-search-icon" class="icon-search"></i></a></td>
                                                </tr>
                                            </table>
                                        </div>
                                        <div class="widget-main">
                                            <div class="row-fluid">
                                                <div  id="table_print1" style="border: 1px white solid;">
                                                    <table id="table_report" class="table table-striped table-bordered  table-hover">
                                                        <thead>
                                                        <tr>
                                                            <th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>
                                                            <th>设备编号</th>
                                                            <th>客户姓名</th>
                                                            <th>性别</th>
                                                            <th>联系方式</th>
                                                            <th>出生日期</th>
                                                            <th>家庭住址</th>
                                                            <th>健康状况</th>
                                                            <th>状态</th>
                                                            <th>创建时间</th>
                                                            <th id="exclude_1" class="center" style="width:80px;text-align: center">操作</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody id="customerListCenter">
                                                        <!-- 开始循环 -->
                                                        <c:choose>
                                                            <c:when test="${not empty customerList}">
                                                                <c:forEach items="${customerList}" var="var" varStatus="vs">
                                                                    <tr ondblclick="findInfo('${var.id }')">
                                                                        <td id="exclude_0_${var.id }" style="width:20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id },${var.status}" onchange="myCheck('${var.id }');"></td>
                                                                        <c:if test="${var.sn==''||var.sn==null}"><td style="color: red;">未绑定</td></c:if>
                                                                        <c:if test="${var.sn!=''&&var.sn!=null}"><td>${var.sn }</td></c:if>
                                                                        <td>${var.name }</td>
                                                                        <td>${var.sex }</td>
                                                                        <td>${var.phone }</td>
                                                                        <td>${var.birthday }</td>
                                                                        <td>${var.address }</td>
                                                                        <td>${var.health_condition }</td>
                                                                        <td id="exclude_2_${var.id }">
                                                                            <c:if test="${var.status=='Y'}">使用中</c:if>
                                                                            <c:if test="${var.status=='N'}">已停用</c:if>
                                                                        </td>
                                                                        <td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                                        <td id="exclude_1_${var.id }" style="">
                                                                            <c:if test="${fn:contains(power,'order_add')&&var.status == 'Y'}">
                                                                                <a class='btn btn-mini btn-success' title="填写订单" onclick="orderAdd('${var.id }','${var.customer_sn }','customer');"><i class='icon-pencil'></i></a>
                                                                            </c:if>
                                                                            <c:if test="${fn:contains(power,'order_add')&&var.status == 'Y'}">
                                                                                <a class='btn btn-mini btn-primary' title="回访" onclick="visitingAdd('${var.id }','${var.customer_sn }','customer');"><i class='icon-phone-sign'></i></a>
                                                                            </c:if>
                                                                        </td>
                                                                    </tr>

                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <tr class="main_info">
                                                                    <td colspan="20" class="center">没有相关数据</td>
                                                                </tr>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <%--<div class="page-header position-relative">--%>
                                                    <%--<table style="width:100%;">--%>
                                                        <%--<tr>--%>
                                                            <%--<!--										<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增</a></td>-->--%>
                                                            <%--<td style="vertical-align:top;">--%>
                                                                <%--<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;" id="callRecordListPage">${page.pageStr}</div>--%>
                                                            <%--</td>--%>
                                                        <%--</tr>--%>
                                                    <%--</table>--%>
                                                <%--</div>--%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- end -->
                            </div>
                        </div>
                    </div>
                    <!-- PAGE CONTENT ENDS HERE -->
                </div><!--/row-->
            <%--</form>--%>
        </div><!--/#page-content-->

        <!--/#ace-settings-container-->
    </div><!-- #main-content -->
    <div id="ace-settings-container">
        <div class="btn btn-app btn-mini btn-warning" id="ace-settings-btn">
            <i class="icon-cog"></i>
        </div>
        <div id="ace-settings-box">
            <div>
                <div class="pull-left">
                    <select id="skin-colorpicker" class="hidden">
                        <option data-class="default" value="#438EB9">#438EB9</option>
                        <option data-class="skin-1" value="#222A2D">#222A2D</option>
                        <option data-class="skin-2" value="#C6487E">#C6487E</option>
                        <option data-class="skin-3" value="#D0D0D0">#D0D0D0</option>
                    </select>
                </div>
                <span>&nbsp; 选择皮肤</span>
            </div>
            <div><input type="checkbox" class="ace-checkbox-2" id="ace-settings-header" /><label class="lbl" for="ace-settings-header"> 固定头</label></div>
            <div><input type="checkbox" class="ace-checkbox-2" id="ace-settings-sidebar" /><label class="lbl" for="ace-settings-sidebar"> 固定侧边栏</label></div>
        </div>
    </div>
</div><!--/.fluid-container#main-container-->



<%@include file="../../system/admin/bottom.jsp" %>

<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.all.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript">
    function formatDate(v) {
        if(v == null){
            return "";
        }
        if (/^(-)?\d{1,10}$/.test(v)) {
            v = v * 1000;
        } else if (/^(-)?\d{1,13}$/.test(v)) {
            v = v * 1;
        }
        var dateObj = new Date(v);
        var month = dateObj.getMonth() + 1;
        var day =  dateObj.getDate();
        var hours = dateObj.getHours();
        var minutes = dateObj.getMinutes();
        var seconds = dateObj.getSeconds();
        if(month<10){
            month = "0" + month;
        }
        if(day<10){
            day = "0" + day;
        }
        if(hours<10){
            hours = "0" + hours;
        }
        if(minutes<10){
            minutes = "0" + minutes;
        }
        if(seconds<10){
            seconds = "0" + seconds;
        }
        var UnixTimeToDate = dateObj.getFullYear() + '-' + month + '-' +day + ' ' + hours + ':' + minutes + ':' + seconds;
        return UnixTimeToDate;
    }
    function callRecordStatus(status){
        if(status == '0'){
            return "<span style='color: #00be67'>未受理</span>";
        }else  if(status == '1'){
            return "<span style='color: #b4d5ac'>已受理</span>";
        }
    }
    //判断是否为空
    function isNull(str){
        if(str == null){
            return "";
        }else{
            return str;
        }
    }
    function findInfo(id){
        art.dialog.open('${pageContext.request.contextPath}/customer/customer/findInfo.do?id='+id,{
        title:'查看详情',
        width:550,
        height:400,
        lock: true,
        cancelVal:'关闭',
        cancel:function(){
        }
        });
    }
    <%--function checkButton(str){--%>
        <%--var power = "${power}";--%>
        <%--if(power.indexOf(str) > 0 ){--%>
            <%--return true;--%>
        <%--}--%>
    <%--}--%>
    //查询用户列表
    function  showCustomerList(){
        var sn = $("#sn").val() ;
        var name = $("#name").val() ;
        if(sn == "" && name==""){
            art.dialog.alert("请输入设备编码或用户姓名查询");
            return;
        }
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/order/order/customerList.do?sn="+sn+"&&name="+name,
            dataType: "json",
            success: function (result) {
                var tt ='';
                $.each(result.customerList,function(i,n){
                    tt += '<tr ondblclick="findInfo('+n.id+')">';
                    tt += '<td id="exclude_0_'+ n.id+'" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_'+ n.id+'" value="'+ n.id+'" onchange="myCheck('+ n.id+');"></td>';
                    tt += '<td>'+ isNull(n.sn)+'</td>';
                    tt += '<td>'+ isNull(n.name)+'</td>';
                    tt += '<td>'+ isNull(n.sex)+'</td>';
                    tt += '<td>'+ isNull(n.phone)+'</td>';
                    tt += '<td>'+ isNull(n.birthday)+'</td>';
                    tt += '<td>'+ isNull(n.address)+'</td>';
                    tt += '<td>'+ isNull(n.health_condition)+'</td>';
                    tt += '<td>'+ isNull(n.status)+'</td>';
                    tt += '<td>'+formatDate(n.create_time)+'</td>';
                    tt += '<td id="exclude_1_'+ n.id+'">';
                    if("${fn:contains(power,'order_add')}"){
                        tt += '<a class="btn btn-mini btn-success" title="填写订单" onclick="orderAdd('+n.id+','+n.sn +',\'customer\');"><i class="icon-pencil"></i></a>';
                    }
                    if("${fn:contains(power,'order_add')}"){
                        tt += ' <a class="btn btn-mini btn-primary" title="回访" onclick="visitingAdd('+n.id+','+n.sn +',\'customer\');"><i class="icon-phone-sign"></i></a>';
                    }
                    tt += '</td>';
                    tt += '</tr>';
                });
                $("#customerListCenter").html(tt);
//                $("#customerListPage").html(result.page.pageStr);
            },
            error: function(data) {
            }
        });
    }
    //检索
    function  showCallRecordList(){
        var customer_sn = $("#customer_sn").val() ;
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/order/order/callRecordList.do?customer_sn="+customer_sn,
            dataType: "json",
            success: function (result) {
//                var idvalue= result;
//                alert(result.page.pageStr);
                var tt ='';
                $.each(result.callRecordList,function(i,n){
                    tt += '<tr ondblclick="findInfo('+n.customer_id+')">';
                    tt += '<td id="exclude_0_'+ n.id+'" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_'+ n.id+'" value="'+ n.id+'" onchange="myCheck('+ n.id+');"></td>';
                    tt += '<td>'+ isNull(n.customer_sn)+'</td>';
                    tt += '<td>'+ isNull(n.name)+'</td>';
                    tt += '<td>'+ isNull(n.sex)+'</td>';
                    tt += '<td>'+ isNull(n.phone)+'</td>';
                    tt += '<td>'+formatDate(n.create_time)+'</td>';
                    tt += '<td>'+callRecordStatus(n.status)+'</td>';
                    tt += '<td>'+ isNull(n.operator_name)+'</td>';
                    tt += '<td>'+formatDate(n.operator_time)+'</td>';
                    tt += '<td id="exclude_1_'+ n.id+'">';
                    if("${fn:contains(power,'order_add')}"&& n.status == '0'){
                    tt += ' <a class="btn btn-mini btn-info" title="受理" onclick="acceptCall('+n.id+');"><i class="icon-ok"></i></a>';
                    }
                    if("${fn:contains(power,'order_add')}"&& n.status == '1'&&"${pd.user_id}" == n.operator_id&&n.name!=null){
                        tt += ' <a class="btn btn-mini btn-success" title="填写订单" onclick="orderAdd('+n.id+','+n.customer_sn +',\'callRecord\');"><i class="icon-pencil"></i></a>';
                    }
                    if("${fn:contains(power,'order_add')}"&& n.status == '1'&&"${pd.user_id}" == n.operator_id&&n.name==null){
                        tt += ' <a class="btn btn-mini btn-success" title="添加用户信息" onclick="addCustomer();"><i class="icon-user"></i></a>';
                    }
                    if("${fn:contains(power,'order_add')}"&& n.status == '1'&&"${pd.user_id}" == n.operator_id){
                        tt += ' <a class="btn btn-mini btn-primary" title="回访" onclick="visitingAdd('+n.id+','+n.customer_sn +',\'callRecord\');"><i class="icon-phone-sign"></i></a>';
                    }
                    <%--if(checkButton("telemarketer_add")&& n.status == '1'&&"${pd.user_id}" == n.operator_id){--%>
                        <%--tt += ' <a class="btn btn-mini btn-danger" title="毁单" onclick="orderAdd('+n.id+','+n.customer_sn +',\'callRecord\');"><i class="icon-trash"></i></a>';--%>
                    <%--}--%>
                    if("${fn:contains(power,'order_add')}"&& n.status == '1'&&"${pd.user_id}" == n.operator_id){
                        tt += ' <a class="btn btn-mini btn-warning" title="退回" onclick="replyCall('+ n.id+');"><i class="icon-reply"></i></a>';
                    }
                    tt += '</td>';
                    tt += '</tr>';
                });
                $("#callRecordListCenter").html(tt);
//                $("#callRecordListPage").html(result.page.pageStr);
            },
            error: function(data) {
            }
        });
    }
    //切换菜单
    function cutTab(type){
        $("#button_li").attr('class','');
        $("#menu_li").attr('class','');
        $('#'+type+'_li').addClass('menu-xz-tab');
        $("#button_div").hide();
        $("#menu_div").hide();
        $('#'+type+'_div').show();
        $('#type').val(type);
        if(type=="button"){
            types = "button";
        }else if(type=="menu"){
            types = "menu";
        }
    }
    /**跳转订单添加界面
     *
     * */
    function orderAdd(id,sn,addType){
        art.dialog.open('${pageContext.request.contextPath}/order/order/toAddOrderByCall.do?sn='+sn+'&&id='+id+'&&addType='+addType,{
            title:'填写订单',
            width:550,
            height:500,
            lock: true
        });//打开子窗体
    }
    function addCustomer(){
        location.href = "${pageContext.request.contextPath}/customer/device/findList.do?am=340&bm=402";
    }
    /**
     * 跳转回访添加界面
     */
    function visitingAdd(id,sn,addType){
        art.dialog.open('${pageContext.request.contextPath}/order/order/toAddVisitingByCall.do?sn='+sn+'&&id='+id+'&&addType='+addType,{
            title:'填写回访单',
            width:550,
            height:500,
            lock: true
        });//打开子窗体
    }
    //受理电话记录
    function acceptCall(id){
        bootbox.confirm("确定要受理该数据?", function(result) {
            if(result) {
                var url = "${pageContext.request.contextPath}/order/callRecord/acceptCall.do?id="+id;
                $.get(url,function(data){
                    if(data){
                        showCallRecordList()
                    }
                });
            }
        });
    }
    //撤回
    function replyCall(id){
        bootbox.confirm("确定要撤回该数据?", function(result) {
            if(result) {
                var url = "${pageContext.request.contextPath}/order/callRecord/replyCall.do?id="+id;
                $.get(url,function(data){
                    if(data){
                        showCallRecordList()
                    }
                });
            }
        });
    }
</script>
</body>
</html>
