<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>E看护服务系统平台</title>
  </head>
  
  <body>
      	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	    <script type="text/javascript">
	    	$(document).ready(function (){ 
	    		tz();
	    	});
	    	function tz(){
	    		window.location.href="${pageContext.request.contextPath}/system/login/toLogin.do";
	    	}
	    </script>
  </body>
</html>
