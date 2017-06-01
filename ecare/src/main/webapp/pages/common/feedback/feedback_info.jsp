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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
	<style>
		#showTitle{
			width: 100%;text-align: left;margin: 10px 0 10px 10px;font-size: 14px; color: #2a91d8;width: 90%;
		}
		#order{text-align: left;margin: 0 0 0 20px}
		#order ul{list-style-type:none}
		#order ul li{list-style-type:none;min-height: 30px;line-height: 30px;border-bottom: 1px dashed #CACACA;}
		#orderFlow{text-align: center}
		#time{width: 30%;float: left;}
		#content{width: 50%;float: left;}
		#operator{width: 20%;float: left;}
	</style>
</head>
<body style="background-color: #EFF0F4">
<div id="order" class="order">
	<div id="showTitle">反馈信息</div>
	<div >
		<ul>
			<li>反馈人：${p.userPhone}&nbsp;&nbsp;${p.create_time}</li>
			<li>反馈内容：${p.content}</li>
			<c:if test="${p.status == 'N'}">
				<li>状态：<font color="red">未受理</font></li>
			</c:if>
		</ul>
	</div>
	<c:if test="${p.status == 'Y'}">
	<div id="showTitle">受理信息</div>
	<div id="customer" class="customer">
		<ul>
			<li>受理人：${p.dealwith_Name}&nbsp;&nbsp;&nbsp;&nbsp;受理时间：${p.dealwith_time}</li>
			<li>备注：${p.dealwith_remark}</li>
		</ul>
	</div>
	</c:if>
</div>
</body>
</html>