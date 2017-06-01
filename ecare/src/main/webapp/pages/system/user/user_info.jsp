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
	  <style>
		  #showTitle{
			  width: 100%;text-align: left;margin: 10px 0 10px 10px;font-size: 14px; color: #2a91d8;width: 90%;
		  }
		  #order{text-align: left;margin: 0 0 0 20px}
		  #order ul{list-style-type:none}
		  #order ul li{list-style-type:none;height: 30px;line-height: 30px;border-bottom: 1px dashed #CACACA;}
		  #orderFlow{text-align: center}
		  #time{width: 30%;float: left;}
		  #content{width: 50%;float: left;}
		  #operator{width: 20%;float: left;}
	  </style>
  </head>
  <body>
	  <div id="zhongxin">
		  <div id="order" class="order">
			  <%--<div id="showTitle">用户基本信息</div>--%>
			  <div id="info">
				  <div style="float: left;width: 30%">
					  <c:if test="${p.head_url == ''}">
						  <img src="/IMAGES/index-title1.jpg" width="80%"/>
					  </c:if>
					  <c:if test="${p.head_url != ''}">
						  <img src="${p.head_url}" width="80%"/>
					  </c:if>
				  </div>
				  <div style="float: left;width: 65%">
					  <ul>
						  <li>组织机构:${p.organizeName}</li>
						  <li>登录账号:${p.login_name}</li>
						  <li>名称：${p.user_name}&nbsp;&nbsp;&nbsp;性别：${p.sex}</li>
						  <li>联系方式:${p.phone}</li>
						  <li>邮箱:${p.email}</li>
						  <li>家庭住址:${p.live_address}</li>
					  </ul>
				  </div>
				  <div style=" clear:both"></div>
			  </div>
		  </div>
	  </div>
  </body>
</html>
