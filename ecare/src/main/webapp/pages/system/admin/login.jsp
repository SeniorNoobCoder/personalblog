<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	//out.print(path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/dl.css" rel="stylesheet" type="text/css" />
<title>E看护服务系统平台</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/login/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/login/css/camera.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/login/matrix-login.css" />
<link href="${pageContext.request.contextPath}/plugin/login/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>

<c:if test="${error != null && error != '' }">
	<script type="text/javascript">
		alert("${error}");
	</script>
	</c:if>
	
	<script type="text/javascript">
		function denglu(){
			$("#loginForm").submit();
		}
		function quxiao(){
			$("#loginname").val('');
			$("#password").val('');
		}
	</script>
	
</head>
<style>
.dlbtn{
	background:#0fa135;
	width:260px;
	height:35px;
	text-align:center;
	font-size:18px;
	border:none;
	cursor:pointer;
	border-radius:5px;
	color:#fff;
	margin-top:30px;
	margin-left:15px;
	font-family:"微软雅黑";
	letter-spacing: 10px;
}
.controls{clear: both;
  position: absolute;
  bottom: 60px;margin-left: 70px;}
</style>
<body>
<div class="all">
	<div class="logo">
    	<img src="${pageContext.request.contextPath}/resources/img/logo.png" />
    </div>
    
</div>
	<div class="bg-blue">
    	<div class="content">
        	<div class="content-left">
            	<img src="${pageContext.request.contextPath}/resources/img/dl-img.png" />
            </div>
            <div class="content-right">
            <form action="${pageContext.request.contextPath}/system/login/login.do" method="post" name="loginForm" id="loginForm" onsubmit="return check();">
            	<div class="dlbox">
                    <div class="name">
                        <p>用户名：</p>
                        <input type="hidden" value="staff" name="type"/>
                       <input type="text" name="login_name" id="login_name" value="${pd.login_name }" placeholder="请输入用户名" />
                    </div>
                    <div class="name">
                        <p>密码：</p>
                        <input  type="password" name="login_password" id="login_password" placeholder="请输入密码" value="${pd.login_password }" />
                    </div>
                    <div class="name">
                        <p>验证码：</p>
                       <input type="text" name="code" id="code" class="yzm"/>
                        <div class="yzmimg"><img id="codeImg" alt="点击更换" title="点击更换" src="" width="60" height="30;"/></div>
                    </div>
                        <div class="controls">
                        <div class="main_input_box">
                                  
									<font color="red"><span id="pwderr"></span></font>
									<font color="red"><span id="codeerr">${errMsg }</span></font>
                        </div>
                    </div>
                    <div class="dl-bot">
	                	<input name="" type="submit" value="登录" id="to-recover" onclick="denglu();" class="dlbtn" style="width:260px;"/>
	                </div>
				
                </div>
            </form>
            
            </div>
        
        </div>
    </div>
    <div style=" width:1000px; margin:0 auto; color:#000;"><font><span id="nameerr"></span></font></div>
    <script type="text/javascript">
		var errInfo = "${errInfo}";
		$(document).ready(function(){
			changeCode();
			$("#codeImg").bind("click",changeCode);
			if(errInfo!=""){
				if(errInfo.indexOf("验证码")>-1){
					$("#nameerr").hide();
					$("#codeerr").show();
					$("#codeerr").html(errInfo);
					$("#code").focus();
				}else{
					$("#codeerr").show();
					$("#codeerr").html(errInfo);
				}
			}
			$("#login_name").focus();
		});
		
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			    $("#to-recover").trigger("click");
			  }
			});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function changeCode(){
			$("#codeImg").attr("src","${pageContext.request.contextPath}/code.do?t="+genTimestamp());
		}
		
		function resetErr(){
			$("#codeerr").html("");
		}
		
		function check(){
			resetErr();
			if($("#login_name").val()==""){
				$("#codeerr").html("用户名不得为空！");
				$("#login_name").focus();
				return false;
			}else{
				$("#login_name").val(jQuery.trim($('#login_name').val()));
			}
			
			if($("#login_password").val()==""){
				$("#codeerr").html("密码不得为空！");
				$("#login_password").focus();
				return false;
			}
			if($("#code").val()==""){
				$("#codeerr").html("验证码不得为空！");
				$("#code").focus();
				return false;
			}
			
			return true;
		}
		</script>		
		<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href; 
		}
	  </script>
</body>
</html>
