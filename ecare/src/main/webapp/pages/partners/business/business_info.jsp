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
	<!-- Bootstrap core CSS -->
	<%--<link href="${pageContext.request.contextPath}/pages/assets/css/bootstrap.css" rel="stylesheet">--%>
	<link href="${pageContext.request.contextPath}/ui/css/bootstrap.min.css" rel="stylesheet" />
	<!--external css-->
	<link href="${pageContext.request.contextPath}/pages/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
	<!-- Custom styles for this template -->
	<link href="${pageContext.request.contextPath}/pages/assets/css/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/pages/assets/css/style-responsive.css" rel="stylesheet">
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<style>
		.bus-authent{margin-top: 30px}
		.bus-info{}
		.info-content{}
		.server-content ul{list-style-type: none;}
		.info-content ul{list-style-type:none}
		.info-content ul li{list-style-type:none;height: 30px;line-height: 30px;border-bottom: 1px dashed #CACACA;font-size: 12px;color: #999999}
		.info-title{width: 100%;text-align: left;margin: 10px 0 10px 10px;font-size: 14px; color: #2a91d8;width: 90%;}
	</style>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
</head>
<body style="background-color: #F1F2F7">
<%--基本信息--%>
<div class="bus-info">
	<div class="info-title">基本信息</div>
	<div class="info-content">
		<ul>
			<li>昵称：${p.nickname}(${p.sex}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li>电话：${p.phone} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱：${p.email}</li>
			<li>家庭住址：${p.live_address}</li>
		</ul>
	</div>
</div>
<div class="bus-info">
	<div class="info-title">公司信息</div>
	<div class="info-content">
		<ul>
			<li>公司名称：${companyInfo.authent_name}</li>
			<li>联系人：${companyInfo.user_name}</li>
			<li>电话：${companyInfo.phone} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱：${companyInfo.email}</li>
			<li>公司住址：${companyInfo.address}</li>
		</ul>
	</div>
</div>
<%--服务类型--%>
<div class="bus-info">
	<div class="info-title">
		<div style="float: left;width: 60%">服务类型</div>
		<div style="float: right;width: 30%;text-align: right">
			<button class="btn btn-info btn-xs" title="修改" onclick="updateCategory('${p.id }');"><i class="fa fa-cog">&nbsp;&nbsp;查看/修改&nbsp;</i></button>
		</div>
	</div>
	<div style="clear: both"></div>
	<%--<div class="server-content">--%>
	<%--<ul>--%>
	<%--<li style="font-weight: bolder">生活服务</li>--%>
	<%--<li>--%>
	<%--<ul>--%>
	<%--<li style="float: left;width: 20%">维修</li>--%>
	<%--<li style="float: left;width: 20%">保姆</li>--%>
	<%--<li style="float: left;width: 20%">打扫</li>--%>
	<%--<li style="float: left;width: 20%">打扫</li>--%>
	<%--</ul>--%>
	<%--</li>--%>
	<%--</ul>--%>
	<%--</div>--%>
</div>
<%--认证信息--%>
<div class="bus-authent">
	<div class="info-title">认证信息</div>
	<c:if test="${p.isAuthent =='Y'}">
		<div class="info-content">
			<ul>
				<li>真实姓名：${p.authent_name}  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号：${p.PID}</li>
			</ul>
		</div>
		<div class="authent-content">
			<ul>
				<li style="float: left;width: 50%;height: 150px"><img src="${p.idCard_front}" width="200px"></li>
				<li style="float: left;width: 50%;height: 150px"><img src="${p.idCard_back}" width="200px"></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${p.isAuthent=='N'}">
		<div style="text-align: center;font-weight: bolder;font-size: 20px;color: #ccd1d9;height: 100px">暂无认证信息</div>
	</c:if>
</div>
<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->
<script type="text/javascript">
	function updateCategory(business_id){
//		  bootbox.alert(id);
		art.dialog.open('${pageContext.request.contextPath}/pages/partners/business/business_serverCategory.jsp?business_id='+business_id,{
			title:'服务分类信息',
//			background:'#F1F2F7',
			width:200,
			height:300,
			lock: true,
			cancelVal:'关闭',
			cancel:function(){
			}
		});//打开子窗体
	}
</script>
</body>
</html>
