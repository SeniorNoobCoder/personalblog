<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../system/admin/top.jsp" %>
<div class="container-fluid" id="main-container">
    <%@include file="../../system/admin/left.jsp" %>
    <div id="main-content" class="clearfix">
        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="#">服务管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
                <li class="active">服务信息维护</li>
            </ul><!--.breadcrumb-->
            <div id="nav-search">
            </div><!--#nav-search-->
        </div><!--#breadcrumbs-->

        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <div class="row-fluid">
                    <!-- 检索  -->
                    <form action="${pageContext.request.contextPath}/server/info/findList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="userForm" id="userForm">
                        <div class="page-header position-relative">
                            <table>
                                <tr>
                                    <td><font color="#808080">合作商名称：</font></td>
                                    <td><input type="text" id="partner_name" name="partner_name" value="${pd.partner_name }" placeholder="这里输入合作商名称" style="width:130px;"/></td>
                                    <%--<td><font color="#808080">服务分类：</font></td>--%>
                                    <%--<td>--%>
                                        <%--<input type="text" id="server_category_name"  name="server_category_name" value="${pd.server_category_name }" placeholder="这里输入用户名称" style="width:130px;"/></td>--%>
                                    <td><font color="#808080">信息状态：</font></td>
                                    <td>
                                        <select name="status" id="status" value="${pd.status }" style="width:100px;">
                                            <option value="" <c:if test="${pd.status == ''}">selected</c:if>>全部</option>
                                            <option value="1" <c:if test="${pd.status == 1}">selected</c:if>>待审核</option>
                                            <option value="2" <c:if test="${pd.status == 2}">selected</c:if>>审核通过</option>
                                            <option value="3" <c:if test="${pd.status == 3}">selected</c:if>>审核退回</option>
                                            <option value="4" <c:if test="${pd.status == 9}">selected</c:if>>消息下架</option>
                                        </select>
                                    <td style="vertical-align:top;">
                                    <button class="btn btn-mini btn-light" onclick="searchForm();"><i id="nav-search-icon" class="icon-search"></i></button>
                                    </td>
                                </tr>
                            </table>
                            <%--<c:if test="${fn:contains(power,'telemarketer_add')}">--%>
                                <%--<a class="btn btn-success btn-sm" onclick="add()">添加</a>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${fn:contains(power,'telemarketer_edit')}">--%>
                                <%--<a class="btn btn-warning btn-sm" onclick="edits()">编辑</a>--%>
                            <%--</c:if>--%>
                            <c:if test="${fn:contains(power,'server_info_audit ')}">
                                <a class="btn btn-success btn-sm" onclick="updates('1')">审核通过</a>
                            </c:if>
                            <c:if test="${fn:contains(power,'server_info_audit ')}">
                                <a class="btn btn-danger btn-sm" onclick="updates('9')">审核驳回</a>
                            </c:if>
                        </div>
                        <!-- 检索  -->
                        <div class="widget-box" style="">
                            <div class="widget-header">
                                <h4>用户管理</h4>
                                <div id="tools_part" style="position: absolute; top: 5px; right: 400px; display: none;">
                                    <a style="cursor: pointer;" onclick="showTools('part');"><img src="${pageContext.request.contextPath}/resources/images/right.png"/></a>
                                </div>
                                <div id="export" style="display: block">
                                    <font style="font-size: 8px;color: red">* 双击查看详情</font>
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
                                            <th>发布人</th>
                                            <th>服务分类</th>
                                            <th>服务信息</th>
                                            <th>状态</th>
                                            <th>创建时间</th>
                                            <th id="exclude_1" class="center" style="width:105px;">操作</th>
                                        </tr>
                                        </thead>
                                        <tbody id="listCenter">
                                        <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty infoList}">
                                                <c:forEach items="${infoList}" var="var" varStatus="vs">
                                                    <tr ondblclick="findInfo('${var.id }')" >
                                                        <td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
                                                        <td>${var.partner_name }</td>
                                                        <td>${var.server_category_name }</td>
                                                        <td>${var.title }</td>
                                                        <td>
                                                            <c:if test="${var.status == '0'}"> <font color="#00008b">新建</font></c:if>
                                                            <c:if test="${var.status == '1'}"><font color="#5f9ea0">待审核</font></c:if>
                                                            <c:if test="${var.status == '2'}"><font color="#green">审核通过</font></c:if>
                                                            <c:if test="${var.status == '3'}"><font color="red">审核退回</font></c:if>
                                                            <c:if test="${var.status == '9'}"><font color="#808080">消息下架</font></c:if>
                                                        </td>
                                                        <td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td id="exclude_1_${var.id }">
                                                            <c:if test="${fn:contains(power,'server_info_audit')}">
                                                                <c:if test="${var.status==1}">
                                                                <a class='btn btn-mini btn-success' title="审核" onclick="updateStatus('${var.id }','2');"><i class='icon-ok'></i></a>
                                                                <a class='btn btn-mini btn-danger' title="退回" onclick="updateStatus('${var.id }','3');"><i class='icon-remove'></i></a>
                                                                </c:if>
                                                                <c:if test="${var.status==2}">
                                                                    <a class='btn btn-mini btn-danger' title="退回" onclick="updateStatus('${var.id }','3');"><i class='icon-remove'></i></a>
                                                                </c:if>
                                                                <a class='btn btn-mini btn-warning' title="查看" onclick="findInfo('${var.id }','2');"><i class='icon-search'></i></a>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr class="main_info" id="main_info">
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
    //子页面成功回调方法
    function saveSuccess(){
        var num = '${page.currentPage}';
        if(num == '0'){
            location.href = location.href;
        }else{
            nextPage('${page.currentPage}');
        }
    }


    //新增
    function add(){
        art.dialog.open('${pageContext.request.contextPath}/server/info/toadd.do',{
            title:'新增',
            width:550,
            height:300,
            lock: true
        });//打开子窗体
    }


    //修改
    function edit(id){
        art.dialog.open('${pageContext.request.contextPath}/callCenter/telemarketer/toedit.do?id='+id,{
            title:'编辑',
            width:550,
            height:500,
            lock: true
        });//打开子窗体
    }

    //删除
    function del(id){
        bootbox.confirm("确定要删除该数据?", function(result) {
            if(result) {
                var url = "${pageContext.request.contextPath}/callCenter/telemarketer/del.do?ids="+id;
                $.get(url,function(data){
                    if(data){
                        saveSuccess();
                    }
                });
            }
        });
    }


    //编辑
    function edits(){
        var data = $('input:checkbox[id^=check_]:checked');
        if(data.length==1){
            art.dialog.open('${pageContext.request.contextPath}/callCenter/telemarketer/toedit.do?id='+data.val(),{
                title:'编辑',
                width:550,
                height:500,
                lock: true
            });//打开子窗体
        }else{
            art.dialog.alert("请选择一条数据进行编辑。");
        }
    }

    //批量删除
    function dels(){
        var data = $('input:checkbox[id^=check_]:checked');
        if(data.length>0){
            var ids = [];
            $("input:checkbox[id^='check_']:checked").each(function(){
                ids.push($(this).attr("value"));
            });
            bootbox.confirm("确定要删除选中的数据?", function(result) {
                if(result) {
                    var url = "${pageContext.request.contextPath}/callCenter/telemarketer/del.do?ids="+ids;
                    $.get(url,function(data){
                        if(data){
                            saveSuccess();
                        }
                    });
                }
            });
        }else{
            art.dialog.alert("请选择要进行删除的数据。");
        }
    }
    //获取详情
    function findInfo(id){
        $("input:checkbox[id^='check_']").each(function(){
            var cc = $(this).prop("checked",false);
        });
        $("#check").prop("checked",false);
        $("#check_"+id).prop("checked",true);
        var data = $('input:checkbox[id^=check_]:checked');
        if(data.length==1){
            art.dialog.open('${pageContext.request.contextPath}/server/info/findInfo.do?id='+data.val(),{
                title:'服务信息',
                width:550,
                height:300,
                lock: true,
                cancelVal:'关闭',
                cancel:function(){

                }
            });//打开子窗体
        }else{
            art.dialog.alert("请选择一条要查看的数据。");
        }
    }
    //更新状态
    function updates(status){
        var data = $('input:checkbox[id^=check_]:checked');
        if(data.length>0){
            var ids = [];
            $("input:checkbox[id^='check_']:checked").each(function(){
                ids.push($(this).attr("value"));
            });
            var remark = "确定要审核通过选中的数据?";
            if(status ==  '3'){
                remark = "确定要退回选中的数据?";
            }
            bootbox.confirm(remark, function(result) {
                if(result) {
                    $.ajax({
                        type: "GET",
                        url: "${pageContext.request.contextPath}/server/info/updateStatus.do?ids="+ids+"&status="+status,
                        data: "",
                        dataType: "json",
                        success: function(data){
                            if(data) {
                                alert("操作成功");
                                $("#userForm").submit();
                            }
                        }
                    });
                }
            });
        }else{
            art.dialog.alert("请选择要审核的数据。");
        }
    }
    //更新状态
    function updateStatus(id,status){
        var remark = "确定要审核通过选中的数据?";
        if(status ==  '3'){
            remark = "确定要退回选中的数据?";
        }
        bootbox.confirm(remark, function(result) {
            if(result) {
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/server/info/updateStatus.do?ids="+id+"&status="+status,
                    data: "",
                    dataType: "json",
                    success: function(data){
                        if(data) {
                            alert("操作成功");
                            $("#userForm").submit();
                        }
                    }
                });
            }
        });
    }
    function  searchForm(){
        $("#userForm").submit();
    }
</script>
</body>

</html>
