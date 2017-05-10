<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>500</title>
	<link href="<%=path %>/pages/base/css/css.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/pages/base/css/comment.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<div class="bw">
	 	<div class="cw">  
	      <div class="bcfh wz1"><a href="javascript:history.go(-1);">返回</a></div>
	    </div>
	</div>
	</body>
	</html>

