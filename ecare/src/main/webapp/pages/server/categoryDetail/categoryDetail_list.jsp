<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../system/admin/top.jsp" %>


<div class="container-fluid" id="main-container">
    <%@include file="../../system/admin/left.jsp" %>
    <div id="main-content" class="clearfix">
        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="#">服务分类管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
                <li class="active">服务详情管理</li>
            </ul><!--.breadcrumb-->
            <div id="nav-search">
            </div><!--#nav-search-->
        </div><!--#breadcrumbs-->
        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <div class="row-fluid">
                    <%--<form action="${pageContext.request.contextPath}/server/categoryDetail/treeList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="serverCategoryForm" id="serverCategoryForm">--%>
                        <div>
                            <div style="float: left; width: 23%; margin-right:15px;" id="leftDiv">
                                <div class="widget-box" style="">
                                    <div class="widget-header">
                                        <h4>服务类型</h4>
                                        <span class="widget-toolbar">
                                            <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                                        </span>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-body-inner">
                                            <div class="widget-main">
                                                <div class="row-fluid">
                                                    <ul id="treeDemo" class="ztree"></ul>
                                                    <input type="hidden" name="tree_id" id="tree_id" value="" />
                                                    <input type="hidden" id="tree_name" value="" />
                                                    <input type="hidden" id="tree_level" value="" />
                                                    <input type="hidden" id="server_category_id" value="" />
                                                    <input type="hidden" id="server_category_name" value="" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="sidebar-collapse" style="background:#fff;border-bottom:none;">
                                        <i class="icon-double-angle-left" onclick="closeDiv();"></i>
                                    </div>
                                </div>
                            </div>
                            <div style="float: left; width: 2%; margin-right:15px; display: none;" id="leftDiv2">
                                <div id="sidebar-collapse" style="height:480px;background:#fff;border-bottom:none;">
                                    <div style="display: inline-block; height:98%;border-left: 1px solid #e1e1e1;position: absolute;left: 10px;"></div>
                                    <i class="icon-double-angle-left icon-double-angle-right" onclick="openDiv();" style="top:230px"></i>
                                </div>
                            </div>
                            <div style="float: left; width:30%;margin-right:15px;" id="rightDiv">
                                <div class="widget-box" style="">
                                    <div class="widget-header">
                                        <h4>工作类型</h4>
                                        <div class="">
                                            <c:if test="${fn:contains(power,'server_category_add')}">
                                                <a class="btn btn-success btn-sm" onclick="addCategory()">添加</a>
                                            </c:if>
                                            <c:if test="${fn:contains(power,'server_category_edit')}">
                                                <a class="btn btn-warning btn-sm" onclick="editCategory()">编辑</a>
                                            </c:if>
                                            <c:if test="${fn:contains(power,'server_category_del')}">
                                                <a class="btn btn-danger btn-sm" onclick="delCategory()">删除</a>
                                            </c:if>
                                            <%--<c:if test="${fn:contains(power,'server_category_info')}">--%>
                                                <%--<a class="btn btn-info btn-sm" onclick="findinfo()">查看详情 </a>--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${fn:contains(power,'organize_trash')}">--%>
                                            <%--<a class="btn btn-sm" onclick="findDustbin()">回收站(${pd.num }) </a>--%>
                                            <%--</c:if>--%>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-body-inner">
                                            <div class="widget-main">
                                                <div class="row-fluid">
                                                    <div  id="table_print" style="border: 1px white solid;">
                                                        <table id="table_report_click" class="table table-striped table-bordered table-hover">
                                                            <thead>
                                                            <tr>
                                                                <th id="exclude_category_0" style="width:20px;">
                                                                    <%--<input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check_category" onchange="myCheckAll('category');">--%>
                                                                </th>
                                                                <th>服务类型</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody id="categoryListCenter"></tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%--           --%>
                            <div style="float: left; width: 40%" id="rightDiv">
                                <div class="widget-box" style="">
                                    <div class="widget-header">
                                        <h4>工作介绍</h4>
                                        <c:if test="${fn:contains(power,'server_category_add')}">
                                            <a class="btn btn-success btn-sm" onclick="addCategoryDetail()">添加</a>
                                        </c:if>
                                        <c:if test="${fn:contains(power,'server_category_edit')}">
                                            <a class="btn btn-warning btn-sm" onclick="editJobDetail()">编辑</a>
                                        </c:if>
                                        <c:if test="${fn:contains(power,'server_category_del')}">
                                            <a class="btn btn-danger btn-sm" onclick="delJobDetail()">删除</a>
                                        </c:if>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-body-inner">
                                            <div class="widget-main">
                                                <div class="row-fluid">
                                                    <div  id="table_print" style="border: 1px white solid;">
                                                        <table id="table_report" class="table table-striped table-bordered table-hover">
                                                            <thead>
                                                            <tr>
                                                                <th id="exclude_job_0" style="width:20px;">
                                                                    <%--<input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check_job" onchange="myCheckAll('job');">--%>
                                                                </th>
                                                                <th>服务介绍</th>
                                                                <th>收费标准</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody id="jobDetailListCenter"></tbody>
                                                        </table>
                                                    </div>
                                                    <%--<div class="page-header position-relative">--%>
                                                    <%--<table style="width:100%;">--%>
                                                    <%--<tr>--%>
                                                    <%--<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>--%>
                                                    <%--</tr>--%>
                                                    <%--</table>--%>
                                                    <%--</div>--%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <%--</form>--%>
                </div>
                <!-- PAGE CONTENT ENDS HERE -->
            </div><!--/row-->

        </div><!--/#page-content-->

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
    </div><!--/#ace-settings-container-->
</div><!-- #main-content -->

</div><!--/.fluid-container#main-container-->

<%@include file="../../system/admin/bottom.jsp" %>
<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.all.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<%@include file="categoryDetail_tree.jsp" %>
<script type="text/javascript">
    $(function () {
        $("table > tbody > tr").click(function () {
            location.href = $(this).find("a").attr("href");
        });
    })
    function myCheckAll(name){
        var c = $("#check_"+name).prop("checked");
        if(c){
            $("input:checkbox[id^='check_"+name+"_']").each(function(){
                $(this).prop("checked",true);
            });
        }else{
            $("input:checkbox[id^='check_"+name+"_']").each(function(){
                $(this).prop("checked",false);
            });
        }
    }

    function myCheck(id,name){
        var c = $("#check_"+name+"_"+id).prop("checked");
        if(c){
            $("#check").prop("checked",true);
        }else{
            var flag = true;
            $("input:checkbox[id^='check_"+name+"']").each(function(){
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
    //单机选中
    function clickCheck(id,name,jobName){
        $("input:checkbox[id^='check_"+name+"_']").each(function(){
            $(this).prop("checked",false);
        });
        $("#check_"+name+"_"+id).prop("checked",true);
        if(name == 'category'){
            $("#server_category_id").val(id);
            $("#server_category_name").val(jobName);
        }
    }
    //子页面成功回调方法
    function saveSuccess(){
        location.href = location.href;
        <%--var num = '${page.currentPage}';--%>
        <%--if(num == '0'){--%>
        <%--location.href = location.href;--%>
        <%--}else{--%>
        <%--nextPage(${page.currentPage});--%>
        <%--}--%>
    }

    //新增
    function addCategory(){
        var parent_id =  $("#tree_id").val();
        var name = $("#tree_name").val();
        var level = $("#tree_level").val();
        if(level == '0'){
            alert("请在左侧服务分类。");
        }else{
            art.dialog.open('${pageContext.request.contextPath}/server/categoryDetail/toadd.do?parent_id='+parent_id+'&parentName='+name+'&level='+level,{
                title:'新增',
                width:500,
                height:200,
                lock: true
            });//打开子窗体
        }
    }
    //新增工作详情
    function addCategoryDetail() {
        var server_category_id = $("#server_category_id").val();
        var server_category_name = $("#server_category_name").val();

        if(server_category_id ==""){
            alert("请选择工作类型!");
        }else{
            art.dialog.open('${pageContext.request.contextPath}/server/categoryDetail/toAddDetail.do?server_category_id='+server_category_id+'&server_category_name='+server_category_name,{
                title:'新增',
                width:500,
                height:400,
                lock: true
            });//打开子窗体
        }
    }
    //修改工作分类
    function editCategory(){
        var name = $("#tree_name").val();
        var data = $('input:checkbox[id^=check_category_]:checked');

        if(data.length==1){
            art.dialog.open('${pageContext.request.contextPath}/server/categoryDetail/toedit.do?id='+data.val()+'&parentName='+name,{
                title:'编辑',
                width:550,
                height:300,
                lock: true
            });//打开子窗体
        }else{
            art.dialog.alert("请选择一条数据进行编辑。");
        }
    }
    //修改工作描述
    function editJobDetail(){
        var name = $("#server_category_name").val();
        var data = $('input:checkbox[id^=check_job_]:checked');
        if(data.length==1){
            art.dialog.open('${pageContext.request.contextPath}/server/categoryDetail/toEditJobDetail.do?id='+data.val()+'&parentName='+name,{
                title:'编辑',
                width:550,
                height:400,
                lock: true
            });//打开子窗体
        }else{
            art.dialog.alert("请选择一条数据进行编辑。");
        }
    }
    //查看详情
    function findinfo(){
        var name = $("#tree_name").val();
        var data = $('input:checkbox[id^=check_]:checked');
        if(data.length==1){
            art.dialog.open('${pageContext.request.contextPath}/server/categoryDetail/findInfo.do?id='+data.val()+'&parentName='+name,{
                title:'编辑',
                width:550,
                height:500,
                lock: true
            });//打开子窗体
        }else{
            art.dialog.alert("请选择一条数据进行查看。");
        }
    }

    //删除工作详情
    function delJobDetail(){
        var data = $('input:checkbox[id^=check_job_]:checked');
        var server_category_id = $("#server_category_id").val();
        if(data.length>0){
            var ids = [];
            $("input:checkbox[id^='check_job_']:checked").each(function(){
                ids.push($(this).attr("value"));
            });
            bootbox.confirm("确定要删除选中的数据?", function(result) {
                if(result) {
                    var url = "${pageContext.request.contextPath}/server/categoryDetail/delJobDetail.do?remove_logo=Y&ids="+ids+"&server_category_id="+server_category_id;
                    $.get(url,function(data){
                        var models = eval("("+data+")");
                        if(models.code){
                            alert("删除成功");
                            showJobRemark(models.server_category_id);
                        }
                    });
                }
            });
        }else{
            art.dialog.alert("请选择要进行删除的数据。");
        }
    }
    //删除工作类型
    function delCategory(){
        var data = $('input:checkbox[id^=check_category_]:checked');
        var tree_id = $("#tree_id").val();
        if(data.length>0){
            var ids = [];
            $("input:checkbox[id^='check_category_']:checked").each(function(){
                ids.push($(this).attr("value"));
            });
            bootbox.confirm("确定要删除选中的数据?", function(result) {
                if(result) {
                    var url = "${pageContext.request.contextPath}/server/categoryDetail/delCategory.do?remove_logo=Y&ids="+ids+"&parent_id="+tree_id;
                    $.get(url,function(data){
                        var models = eval("("+data+")");
                        if(models.code){
//                            saveSuccess();
                            alert("删除成功！");
                            show(models.parent_id);
                        }else{
                            alert("请先删除此条信息下的工作介绍！");
                        }
                    });
                }
            });
        }else{
            art.dialog.alert("请选择要进行删除的数据。");
        }
    }
</script>
</body>
</html>