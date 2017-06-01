<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../system/admin/top.jsp" %>
<div class="container-fluid" id="main-container">
    <%@include file="../../system/admin/left.jsp" %>
    <div id="main-content" class="clearfix">
        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="#">订单管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
                <li class="active">客户来电记录</li>
            </ul><!--.breadcrumb-->
            <div id="nav-search">
            </div><!--#nav-search-->
        </div><!--#breadcrumbs-->

        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <div class="row-fluid">
                    <!-- 检索  -->
                    <form action="${pageContext.request.contextPath}/order/callRecord/findList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="userForm" id="userForm">
                        <div class="page-header position-relative">
                            <table>
                                <tr>
                                    <td><font color="#808080">设备号：</font></td>
                                    <td><input type="text" name="sn" value="${pd.sn }" placeholder="这里输入设备号" style="width:130px;"/></td>
                                    <%--<td><font color="#808080">用户姓名：</font></td>--%>
                                    <%--<td><input type="text" name="name" value="${pd.name }" placeholder="这里输入用户名称" style="width:130px;"/></td>--%>
                                    <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
                                </tr>
                            </table>
                        </div>
                        <!-- 检索  -->
                        <div class="widget-box" style="">
                            <div class="widget-header">
                                <h4>客户来电记录</h4>
                                <div id="tools_part" style="position: absolute; top: 5px; right: 400px; display: none;">
                                    <a style="cursor: pointer;" onclick="showTools('part');"><img src="${pageContext.request.contextPath}/resources/images/right.png"/></a>
                                </div>
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
                                            <th>设备编号</th>
                                            <th>来电时间</th>
                                            <th>状态</th>
                                            <th>处理内容</th>
                                            <th>受理人</th>
                                            <th>受理时间</th>
                                            <th id="exclude_1" class="center" style="width:55px;">操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty callRecordList}">
                                                <c:forEach items="${callRecordList}" var="var" varStatus="vs">
                                                    <tr ondblclick="findInfo('${var.customer_id }')">
                                                        <td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
                                                        <td>${var.customer_sn }</td>
                                                        <td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td>
                                                            <c:if test="${var.status == '0'}"><span style="color: #00be67">未受理</span></c:if>
                                                            <c:if test="${var.status == '1'}"><span style="color: #b4d5ac">已受理</span></c:if>
                                                        </td>
                                                        <td>${var.remark }</td>
                                                        <td>${var.operator_name }</td>
                                                        <td><fmt:formatDate value="${var.operator_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td id="exclude_1_${var.id }">
                                                            <a class='btn btn-mini btn-info' title="用户信息" onclick="findInfo('${var.customer_id }');"><i class='icon-search'></i></a>
                                                            <%--<c:if test="${fn:contains(power,'telemarketer_add')&&var.status == '0'}">--%>
                                                                <%--<a class='btn btn-mini btn-info' title="受理" onclick="acceptCall('${var.id }');"><i class='icon-ok'></i></a>--%>
                                                            <%--</c:if>--%>
                                                            <%--<c:if test="${fn:contains(power,'telemarketer_add')&&pd.user_id == var.operator_id&&var.status == '1'}">--%>
                                                                <%--<a class='btn btn-mini btn-success' title="填写订单" onclick="orderAdd('${var.id }','${var.customer_sn }');"><i class='icon-pencil'></i></a>--%>
                                                            <%--</c:if>--%>
                                                            <%--<c:if test="${fn:contains(power,'telemarketer_add')&&pd.user_id == var.operator_id&&var.status == '1'}">--%>
                                                                <%--<a class='btn btn-mini btn-warning' title="退回" onclick="replyCall('${var.id }');"><i class='icon-reply'></i></a>--%>
                                                            <%--</c:if>--%>
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

<%@include file="../../system/admin/bottom.jsp" %>
<script type="text/javascript">
    //check选择
    function myCheckAll(){
        var c = $("#check").prop("checked");
        if(c){
            $("input:checkbox[id^='check_']").each(function(){
                $(this).prop("checked",true);
            });
        }else{
            $("input:checkbox[id^='check_']").each(function(){
                $(this).prop("checked",false);
            });
        }
    }
    function myCheck(id){
        var c = $("#check_"+id).prop("checked");
        if(c){
            $("#check").prop("checked",true);
        }else{
            var flag = true;
            $("input:checkbox[id^='check_']").each(function(){
                var cc = $(this).prop("checked");
                if(cc){
                    flag=false;
                }
            });
            if(flag){
                $("#check").prop("checked",false);
            }
        }
    }
    //检索
    function search(){
        $("#userForm").submit();
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
</script>
</body>
</html>