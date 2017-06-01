<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    
    <title></title>
    
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<!-- basic styles -->
	<link href="${pageContext.request.contextPath}/ui/css/bootstrap.min.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/ui/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/datepicker.css" /><!-- 日期框 -->
	<!--[if IE 7]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/font-awesome-ie7.min.css" />
	<![endif]-->
	<!-- page specific plugin styles -->
	
	<!-- ace styles -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
	<!--[if lt IE 9]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
	<![endif]-->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>

  </head>
  
  <body>
    <div id="page-content" class="clearfix">
    <div class="row-fluid">
			<!-- 检索  -->
			<form action="${pageContext.request.contextPath}/system/role/findTrash.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="roleForm" id="roleForm">
			<!-- 检索  -->
			<div class="page-header position-relative">
			<div class="content-hd">
				 <a class="btn btn-danger btn-sm" onclick="clearRole()">清空</a> 
				<a class="btn btn-success btn-sm" onclick="revertRole()">还原 </a>
				<a class="btn btn-danger btn-sm" onclick="clearAll()">全部清空</a> 
				<a class="btn btn-success btn-sm" onclick="revertRoleAll()">全部还原 </a>
			</div>
			</div>
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"> </th>
						<th>角色名称</th>
						<th>角色简称</th>
						<th>排序</th>
						<th>备注</th>
						<th>创建时间</th>
						<th class="center" style="width:40px;">操作</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty roleList}">
						<c:forEach items="${roleList}" var="role" varStatus="vs">
							<tr>
								<td><input type="checkbox" style="opacity: 1;" value="${role.id}" name="ids" id="check_${role.id }" onchange="myCheck('${role.id }');"> </td>
								<td>${role.name }</td>
								<td>${role.short_name}</td>
								<td>${role.order_by}</td>
								<td>${role.remark}</td>
								<td><fmt:formatDate value="${role.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini btn-info' title="详情" onclick="findInfo('${role.id }');">详情</a>
									</div>
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
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
	</div>
<%@include file="../admin/bottom.jsp" %>
	<script type="text/javascript">
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
	//还原
	function revertRole(){
		var id = $('input:checkbox[id^=check_]:checked');
		if(id.length>0){
			bootbox.confirm("确定要还原选中的数据吗？", function(result) {
				if(result){
					var ids = [];
					$("input:checkbox[id^='check_']:checked").each(function(){
						ids.push($(this).attr("value"));
					});
					$.ajax({
						url : '${pageContext.request.contextPath}/system/role/delete.do?id='+ids+'&remove_logo=N',
						dataType : 'json',
						success : function(r) {
							nextPage('${page.currentPage}');
						}
					});
				}
			});
		}else{
			art.dialog.alert("请选择要进行还原的数据！");
		}
	}
	//一键还原
	function revertRoleAll(){
		bootbox.confirm("确定要一键还原所有数据？", function(result) {
			if(result){
				$.ajax({
					url : '${pageContext.request.contextPath}/system/role/revertRoleAll.do?remove_logo_N=N&remove_logo=Y',
					dataType : 'json',
					success : function(r) {
						nextPage('${page.currentPage}');
					}
				});
			}
		});
	}

	//清空
	function clearRole(){
		var id = $('input:checkbox[id^=check_]:checked');
		if(id.length>0){
			bootbox.confirm("确定要清除选中的数据吗?清除之后不可恢复！", function(result) {
				if(result){
					var ids = [];
					$("input:checkbox[id^='check_']:checked").each(function(){
						ids.push($(this).attr("value"));
					});
					$.ajax({
						url : '${pageContext.request.contextPath}/system/role/clearRole.do?id='+ids+'&flag=clear',
						dataType : 'json',
						success : function(r) {
							nextPage('${page.currentPage}');
						}
					});
				}
			});
		}else{
			art.dialog.alert("请选择要进行删除的数据！");
		}
	}
	//全部清空
	function clearAll(){
			bootbox.confirm("确定要清空所有数据？清除之后不可恢复！", function(result) {
				if(result){
					$.ajax({
						url : '${pageContext.request.contextPath}/system/role/clearRole.do?flag=all_clear',
						dataType : 'json',
						success : function(r) {
							nextPage('${page.currentPage}');
						}
					});
				}
			});
	}
	//查看详情
	function findInfo(id){
		art.dialog.open('${pageContext.request.contextPath}/system/role/findInfo.do?id='+id,{
    	 	title:'查看详情',
    	 	width:640,
    	 	height:380,
        	lock: true
    	});//打开子窗体
	}
</script>
		
		
  </body>
</html>
