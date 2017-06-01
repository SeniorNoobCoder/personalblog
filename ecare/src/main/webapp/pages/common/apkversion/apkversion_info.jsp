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
			width: 200px;text-align: left;margin: 10px 0 10px 10px;font-size: 14px; color: #2a91d8;width: 90%;
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
	<%--<div id="showTitle">版本详情</div>--%>
	<div >
		<ul>
			<li><span id="showTitle">标&nbsp;&nbsp;题:</span>${p.title}</li>
			<li><span id="showTitle">版本信息：</span>${p.content}</li>
			<li><span id="showTitle">状&nbsp;&nbsp;态：</span>
				<c:if test="${p.status == 'Y'}"><span style="color: red">已发布</span></c:if>
				<c:if test="${p.status == 'N'}"><span style="color: #00be67">未发布</span></c:if>
			</li>
			<li><span id="showTitle">终端类型：</span>${p.type}</li>
			<li><span id="showTitle">版本编号：</span>${p.version_code}</li>
			<li><span id="showTitle">设备类型：</span>${p.device_type}</li>
			<li><span id="showTitle">文件下载：</span><a href="${p.file_address }">点击下载文件</a>
			</li>
			<li><span id="showTitle">备&nbsp;&nbsp;注:</span>${p.content }</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
//	//下拉框
//	$(".chzn-select").chosen();
//	$(".chzn-select-deselect").chosen({allow_single_deselect:true});
//	//日期框
//	$('.date-picker').datepicker();
	function Download(file_address){
		location.href="${pageContext.request.contextPath}/common/apkVersion/Download.do?file_address="+file_address;
	}

</script>
</body>
</html>