<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../system/admin/top.jsp" %>
<div class="container-fluid" id="main-container">
    <%@include file="../../system/admin/left.jsp" %>
    <div id="main-content" class="clearfix">
        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="#">回访管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
                <li class="active">回访信息</li>
            </ul><!--.breadcrumb-->
            <div id="nav-search">
            </div><!--#nav-search-->
        </div><!--#breadcrumbs-->

        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <div class="row-fluid">
                    <!-- 检索  -->
                    <form action="${pageContext.request.contextPath}/visiting/visiting/findMyList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="userForm" id="userForm">
                        <div class="page-header position-relative">
                            <table>
                                <tr>
                                    <td><font color="#808080">回访日期：</font></td>
                                    <td>
                                        <input type="text" name="start_time" id="start_time" value="${pd.start_time }" placeholder="开始日期" style="width:80px;"/>
                                        -
                                        <input type="text" name="end_time" id="end_time" value="${pd.end_time }" placeholder="结束日期" style="width:80px;"/>
                                    </td>
                                    <td><font color="#808080">回访类型：</font></td>
                                    <td>
                                        <select id="type" name="type" style="width:150px;">
                                            <option value="">全部</option>
                                            <option value="call" <c:if test="${pd.type=='call'}">selected</c:if>>电话记录回访</option>
                                            <option value="customer" <c:if test="${pd.type=='customer'}">selected</c:if>>用户信息回访</option>
                                        </select>
                                    <td style="vertical-align:top;"><submit class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search" title="查询"></i></submit></td>
                                    <td style="vertical-align:top;"> <submit class="btn btn-mini btn-light" onclick="reset();"><i id="nav-search-icon" class="icon-reply" title="重置"></i></submit></td>
                                </tr>
                            </table>
                        </div>
                        <!-- 检索  -->
                        <div class="widget-box" style="">
                            <div class="widget-header">
                                <h4>回访信息</h4>
                                <div id="tools_part" style="position: absolute; top: 5px; right: 400px; display: none;">
                                    <a style="cursor: pointer;" onclick="showTools('part');"><img src="${pageContext.request.contextPath}/resources/images/right.png"/></a>
                                </div>
                                <%--<div id="export" style="display: block">--%>
                                <%--<font style="font-size: 8px;color: red">* 双击查看详情</font>--%>
                                <%--</div>--%>
                                <span class="widget-toolbar">
                                    <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                                </span>
                            </div>
                            <div class="widget-body">
                                <div  id="table_print" style="border: 1px white solid;">
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>
                                            <th>客户姓名</th>
                                            <th>客户电话</th>
                                            <th>回访类型</th>
                                            <th>回访反馈</th>
                                            <th>回访时间</th>
                                            <th>操作人</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty visitingList}">
                                                <c:forEach items="${visitingList}" var="var" varStatus="vs">
                                                    <tr ondblclick="findInfo('${var.id }')">
                                                        <td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
                                                        <td>${var.customer_name }</td>
                                                        <td>${var.customer_phone}</td>
                                                        <td>
                                                            <c:if test="${var.type == 'call'}">电话记录回访</c:if>
                                                            <c:if test="${var.type == 'customer'}">用户信息回访</c:if>
                                                        </td>
                                                        <td>${var.remark }</td>
                                                        <td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td>${var.operator_name }</td>
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
                                <div class="page-header position-relative">
                                    <table style="width:100%;">
                                        <tr>
                                            <!--										<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增</a></td>-->
                                            <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- PAGE CONTENT ENDS HERE -->
            </div><!--/row-->

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
<script type="text/javascript" src="${pageContext.request.contextPath}/pages/base/js/jquery.jedate.js"></script><!-- 日期框 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/pages/base/js/skin/jedate.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/pages/base/js/common.js"></script>
<%@include file="../../system/admin/bottom.jsp" %>
<script>
    var startDate = {
        format: 'YYYY-MM-DD',
//        minDate: $.nowDate(0), //设定最小日期为当前日期
//        isinitVal:true,
        festival:false,
        ishmsVal:false,
        maxDate: getNowFormatDate(), //最大日期
        choosefun: function(elem,datas){
            endDate.minDate = datas; //开始日选好后，重置结束日的最小日期
        }
    };
    var endDate = {
        format: 'YYYY-MM-DD',
//        minDate: $.nowDate(0), //设定最小日期为当前日期
//        isinitVal:true,
        festival:false,
        maxDate: getNowFormatDate(), //最大日期
        choosefun: function(elem,datas){
            startDate.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
        }
    };
    $('#start_time').jeDate(startDate);
    $('#end_time').jeDate(endDate);
    //    //或者是
    //    $.jeDate('#start_time',start);
    //    $.jeDate('#end_time',end);
    function  search(){
        var startDate = $('#start_time').val();
        var endDate = $('#end_time').val();
        if(startDate==""&& endDate!=""){
            alert("请输入开始日期");
            return false;
        }else if(startDate!=""&& endDate==""){
            alert("请输入结束日期");
            return false;
        }else{
            $("#userForm").submit();
        }
    }
    function reset(){
        $('#start_time').val("");
        $('#end_time').val("");
        $('#type').val("");
    }
</script>
</body>
</html>