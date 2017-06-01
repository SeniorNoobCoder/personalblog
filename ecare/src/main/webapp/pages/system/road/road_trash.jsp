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
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/myFold.js"></script>
  </head>
  
  <body>
    <div id="page-content" class="clearfix">
    <div class="row-fluid">
			<!-- 检索  -->
			<form action="${pageContext.request.contextPath}/system/road/findTrash.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="organizeForm" id="organizeForm">
			<!-- 检索  -->
			<div class="page-header position-relative">
			<div class="content-hd">
				 <a class="btn btn-danger btn-sm" onclick="myClear();">清空</a> 
				<a class="btn btn-success btn-sm" onclick="revert();">还原</a>
				<a class="btn btn-danger btn-sm" onclick="all_clear();">全部清空</a> 
				<a class="btn btn-success btn-sm" onclick="all_revert();">全部还原</a>
			</div>
			</div>
			
			<div style="float: left; width: 23%; margin-right:10px;" id="leftDiv">
				<div class="widget-box" style="">
					<div class="widget-header">
						<h4>辖区管理</h4>
						<span class="widget-toolbar">
							<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
						</span>
					</div>
					<div class="widget-body">
						<div class="widget-body-inner">
							 <div class="widget-main">
								<div class="row-fluid">
									<ul id="treeDemo" class="ztree"></ul>
									<input type="hidden" id="tree_id" value="" />
									<input type="hidden" id="tree_name" value="" />
									<input type="hidden" id="tree_type" value="" />
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
			<div style="float: left; width: 75%" id="rightDiv">
				<div class="widget-box" style="">
					<div class="widget-header">
						<h4>辖区详情</h4>
						<span class="widget-toolbar">
							<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
						</span>
					</div>
					<div class="widget-body">
						<div class="widget-body-inner">
							 <div class="widget-main">
								<div class="row-fluid">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>
						<th>父级名称</th>
						<th>养护所</th>
						<th>位置</th>
						<th>线路编号</th>
						<th>线路名称</th>
						<th>主线里程(km)</th>
						<th>创建时间</th>
						<th class="center" style="width:45px;">操作</th>
					</tr>
				</thead>
				<tbody id="listCenter">
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty roadList}">
						<c:forEach items="${roadList}" var="var" varStatus="vs">
							<tr>
								<td style="width:20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
								<td>公司分管领导</td>
								<td>${var.name }</td>
								<td>${var.position }</td>
								<td>${var.road_code }</td>
								<td>${var.road_name }</td>
								<td>${var.main_mileage}</td>
								<td><fmt:formatDate value="${organize.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									<a class='btn btn-mini btn-info' title="详情" onclick="info('${var.id }');">详情</a>
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
		</div>
		</div>
		</div>
		</div>
		</div>
		</div>
		
		</form>
	</div>
	</div>
	<!-- 引入 -->
	<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>
	<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->
	<!-- 引入 -->
	
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->
	
	
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"/></script>
	<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.all.js"/></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
	<%@include file="dustbin_ztree.jsp" %>
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
	
	function myClear(){
			var data = $('input:checkbox[id^=check_]:checked');
			if(data.length>0){
				var ids = [];
				$("input:checkbox[id^='check_']:checked").each(function(){
					ids.push($(this).attr("value"));
				});
				bootbox.confirm("确定要清除选中的数据吗?清除之后不可恢复！", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/system/road/clearRoad.do?ids="+ids+"&flag=clear";
					$.get(url,function(data){
						if(data){
							//document.location.reload();
							nextPage('${page.currentPage}');
						}
					});
				}
			});	
			}else{
				art.dialog.alert("请选择要进行删除的数据。");
			}
		}
		
		function revert(){
			var data = $('input:checkbox[id^=check_]:checked');
			if(data.length>0){
				var ids = [];
				$("input:checkbox[id^='check_']:checked").each(function(){
					ids.push($(this).attr("value"));
				});
				bootbox.confirm("确定要还原选中的数据吗？", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/system/road/revertRoad.do?ids="+ids+"&flag=revert";
					$.get(url,function(data){
						if(data){
							//document.location.reload();
							nextPage('${page.currentPage}');
						}
					});
				}
			});	
			}else{
				art.dialog.alert("请选择要进行还原的数据。");
			}
		}
		
		function all_clear(){
			bootbox.confirm("确定要清除全部数据吗?清除之后不可恢复！", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/system/road/clearRoad.do?flag=all_clear";
					$.get(url,function(data){
						if(data){
							//document.location.reload();
							nextPage('${page.currentPage}');
						}
					});
				}
			});	
		}
		
		function all_revert(){
			bootbox.confirm("确定要还原全部数据吗？", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/system/road/revertRoad.do?flag=all_revert";
					$.get(url,function(data){
						if(data){
							//document.location.reload();
							nextPage('${page.currentPage}');
						}
					});
				}
			});	
		}
		function info(id){
			var name = $("#tree_name").val();
				art.dialog.open('${pageContext.request.contextPath}/system/road/findInfo.do?id='+id+'&name='+name,{
			   	 	title:'详情',
					width:550,
		    		height:500,
		    		lock: true
				});//打开子窗体
			}
		
		
</script>
  </body>
</html>
