<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>异常</title>
	<link href="<%=path %>/pages/base/css/css.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/pages/base/css/comment.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
		function showErrorMsg(){
			var target=document.getElementById("errorMsg");
            if (target.style.display=="block"){
                target.style.display="none";
            } else {
                target.style.display="block";
            }
		}
	</script>
	
	</head>
	<body>
	<div class="bw">
	 	<div class="bc">  
	       <div class="bcfh wz1"><a href="javascript:history.go(-1);">返回</a></div>
	       <div class="bcfh wz2"><a style="cursor: pointer;" onclick="showErrorMsg();">报错</a></div>
	    </div>
	</div>
	<div class="bw" id="errorMsg" style="display: none;">
		${errorMsg }
	</div>
	</body>
</html>
