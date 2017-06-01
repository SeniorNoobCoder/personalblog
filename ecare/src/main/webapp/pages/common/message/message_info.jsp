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
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace.min.css" />--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />--%>
	<!--[if lt IE 9]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
	<![endif]-->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
  </head>
  <body style="background-color: #F5F5F5">
		<div style="color: #999999;">
			<div id="mesTitle" style="line-height: 35px;text-align: center;font-size: 16px;font-weight: bolder;margin:10px 0 0 0">${p.title}</div>
			<div id="source" style="line-height: 30px;border-bottom: 1px dashed #CCCCCC;text-align: center;font-size: 10px">
					<fmt:formatDate value="${p.update_time}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;&nbsp;
				来源：<c:if test="${p.source == 'system'}">系统</c:if>
			</div>
			<div id ="remark" style="margin-top: 20px">${p.remark}</div>
			<p></p>
			<div id ="mesContent" style="margin-top: 20px">${p.content}</div>
		</div>
  </body>
</html>
