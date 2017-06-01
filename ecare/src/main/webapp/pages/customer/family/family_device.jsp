<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    
    <title></title>
    
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=family-width, initial-scale=1.0" />
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
		<div class="row-fluid">
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>编号</th>
							<th>设备编号</th>
							<th>第三方凭证(APP_ID)</th>
							<th>设备密钥</th>
							<th>设备管理人关系</th>
							<th>关联人名称</th>
							<th>关联人性别</th>
							<th>联系方式</th>
							<th>创建时间</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->	
					<c:choose>
						<c:when test="${not empty list}">
							<c:forEach items="${list}" var="var" varStatus="vs">
								<tr>
									<td style="width: 20px;">${vs.index+1 }</td>
									<td>${var.sn }</td>
									<td>${var.app_id }</td>
									<td>${var.secret }</td>
									<td>${var.relationship }</td>
									<td>${var.name }</td>
									<td>${var.sex}</td>
									<td>${var.phone}</td>
									<td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
							
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="9" class="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
		</div>
		<!-- PAGE CONTENT ENDS HERE -->
	  </div><!--/row-->
	
</div><!--/#page-content-->

<%@include file="../../system/admin/bottom.jsp" %>

		
  </body>
</html>
