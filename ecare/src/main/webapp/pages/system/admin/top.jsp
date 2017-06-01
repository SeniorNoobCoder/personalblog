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
    <title>E看护服务系统平台</title>
    
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/mydc.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/myindex.css" />
	<!--[if lt IE 9]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
	<![endif]-->
	<%--<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/myFold.js"></script>
	
  </head>
  <style>
  body{ min-width:960px;}
  .lignt-sele select{ line-height:45px; height:45px; background:#2f8966; width:120px; color:#fff; margin-bottom:0 !important;border: none !important;}
  .lignt-sele option{ background:#fff; color:#000; height:35px; line-height:35px;}
  
  </style>
  <body>
    
    <div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <a class="brand" href="#"><small><img src="${pageContext.request.contextPath}/ui/img/logo.png" width="33" height="33"/>&nbsp;E看护服务系统平台</small> </a>
			 
			<!--	<div class="bignav">
				    	<ul>
				        	<li class="nav-xz"><a href="" class="jjgc2">基建工程</a></li>
				            <li><a href="" class="xxby">小修保养</a></li>
				        </ul>
				    </div>
			  -->
				  <ul class="nav ace-nav pull-right">
					
					
					<li class="purple">
						<a href="${pageContext.request.contextPath}/common/message/findListByUser.do?am=449&bm=452" class="dropdown-toggle">
							<i class="icon-envelope-alt icon-animated-vertical icon-only"></i>
							<span class="badge badge-important">${noticeNum }</span>
						</a>
					</li>
				
					<li class="lignt-sele styled-select">
						<img src="${pageContext.request.contextPath}/ui/img/new_arrow.png" class="new-arrow"/>
						<select id="role_id" name="role_id" onchange="selectRole();">
							<c:if test="${sessionRole==''||sessionRole==null}">
								<option value="" selected="selected">全部角色</option>
							</c:if>
							<c:if test="${sessionRole!=''&&sessionRole!=null}">
								<option value="" selected="selected">全部角色</option>
							</c:if>
							
							<c:forEach items="${userRoleList}" var="var">
								<c:if test="${mPd.superAdministrator==var}">
									<option value="${mPd.superAdministrator }" <c:if test="${sessionRole==mPd.superAdministrator}"> selected="selected" </c:if>>系统管理员</option>
								</c:if>
							</c:forEach>
							
							<c:forEach items="${userAllRoleList}" var="var" varStatus="vs">
								<c:if test="${sessionRole==var.id}">
									<option value="${var.id }" selected="selected">${var.name }</option>
								</c:if>
								<c:if test="${sessionRole!=var.id}">
									<option value="${var.id }">${var.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</li>
					
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="#" data-toggle="dropdown">
							<c:if test="${loginUser.head_address!=''&&loginUser.head_address!=null}">
								<img alt="Jason's Photo" style="width: 36px;height: 36px" src="${loginUser.head_address_url }" class="nav-user-photo" />
							</c:if>
							<c:if test="${loginUser.head_address==''||loginUser.head_address==null}">
								<img alt="Jason's Photo" style="width: 36px;height: 36px" src="${pageContext.request.contextPath}/ui/avatars/user.jpg" class="nav-user-photo" />
							</c:if>
							<span id="user_info">
								<small>欢迎：</small> ${loginUser.login_name }
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a href="javascript:userEdit('${loginUser.id }')"><i class="icon-user"></i> 个人中心</a></li>
							<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/system/login/outLogin.do"><i class="icon-off"></i>退出登录</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
		<script type="text/javascript">
		//修改
			function userEdit(id){
				art.dialog.open('${pageContext.request.contextPath}/system/user/toedit.do?id='+id,{
			   	 	title:'编辑',
					width:550,
		    		height:500,
		    		lock: true
				});//打开子窗体
			}
			
			
			function selectRole(){
				var role_id = $("#role_id option:selected").val();
				$.ajax({
		            type: "GET",
		            url: "${pageContext.request.contextPath}/system/main/updateRole.do?role_id="+role_id,
		            data: "",
		            dataType: "json",
		            success: function(data){
		            	window.location.reload();
		            }
		         });
			}
			
		</script>
		<script type="text/javascript"> 
$(".dropdiv").click(function(e){ 
$(".chooseItems").slideUp(300); 
e.stopPropagation(); 
var quest = $(this).parent(); 
var questwidth = parseInt(quest.width())-2; 
var questheight = quest.height(); 
var left = quest.position().left+parseInt(quest.css("margin-left"))+parseInt(quest.css("padding-left")); 
var top = parseInt(quest.position().top)+parseInt(questheight)+4; 
var attrs = quest.attr("quest"); 
var selectsd = $('.chooseItems[answer='+attrs+']'); 
var selectsdHeight = selectsd.height(); 
if((top+selectsdHeight)>$(window).height()){ 
top = top - selectsdHeight - questheight-7; 
} 
if($(selectsd).is(":visible")){ 
$(selectsd).slideUp(300); 
}else{ 
$(selectsd).css({"left":left+"px","top":top+"px","width":questwidth+"px"}).slideDown(300); 
} 
}); 
$(".chooseItem").click(function(e){ 
e.stopPropagation(); 
var divhtml = $(this); 
var displayMember,valueMember; 
displayMember = divhtml.attr("displayMember"); 
valueMember = divhtml.attr("valueMember"); 
var attrs =$(this).parent().attr("answer"); 
var parent = $("#"+attrs); 
var olddisplayMember,oldvalueMember; 
olddisplayMember = parent.attr("displayMember"); 
oldvalueMember = parent.attr("valueMember"); 
if(olddisplayMember !=displayMember){ 
parent.attr("displayMember",displayMember); 
parent.attr("valueMember",valueMember); 
parent.val(valueMember); 
parent.change(); 
} 
$(this).parent().slideUp(300); 
}); 
$(document).click(function(e){ 
var target = $(e.target); 
if(target.closest(".chooseItems").length == 0){ 
$(".chooseItems").slideUp(300); 
} 
}); 
</script> 