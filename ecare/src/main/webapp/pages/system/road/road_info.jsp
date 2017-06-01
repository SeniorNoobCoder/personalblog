<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title></title>
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
		<!--[if lt IE 9]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
		<![endif]-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
		
	</head>
<body>
		<form action="${pageContext.request.contextPath}/system/road/save.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
			<div id="zhongxin">
			<table width="90%">
				<tr>
					<td width="20%" align="right">组织：</td><td><input type="text" name="organize_id" readonly value="${pd.name }" id="organize_id" style="width: 95%" /></td>
				</tr>
				<tr>
					<td width="20%" align="right">所属资产：</td><td>
					<select name="assets" id="assets" style="width: 99%" disabled="disabled">
					<c:forEach items="${assetsList}" var="var">
					<option value="${var.id }" <c:if test="${var.id==p.assets }">selected==selected</c:if>>${var.name }</option>
					</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">位置：</td><td><input type="text" name="position" id="position" value="${p.position }" readonly="readonly" style="width: 95%" placeholder="这里输入位置" title="位置"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">路线编号：</td><td><input type="text" name="road_code" value="${p.road_code }" readonly id="road_code" style="width: 95%"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">路线名称：</td><td><input type="text" name="road_name" value="${p.road_name }" readonly id="road_name" style="width: 95%"/></td>
				</tr>
					<tr>
					<td width="20%" align="right">道路类型：</td><td>
					<select name="road_type" id="road_type" style="width: 99%" disabled="disabled">
					<c:forEach items="${roadList}" var="var">
					<option value="${var.id }" <c:if test="${var.id==p.road_type }">selected==selected</c:if>>${var.name }</option>
					</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">起始桩号：</td><td><input type="text" name="start_stake" value="${p.start_stake }" readonly id="start_stake" style="width: 95%"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">结束桩号：</td><td><input type="text" name="end_stake" value="${p.end_stake }" readonly id="end_stake" style="width: 95%"/></td>
				</tr>
					<tr>
					<td width="20%" align="right">主线里程(km)：</td><td><input type="text" name="main_mileage" value="${p.main_mileage }" readonly id="main_mileage" style="width: 95%"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">养护里程(km)：</td><td><input type="text" name="curing_mileage" value="${p.curing_mileage }" readonly id="curing_mileage" style="width: 95%"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">排序：</td>
					<td><input type="number" name="order_by" id="order_by" style="width: 95%" value="${p.order_by}" readonly/></td>
				</tr>
				<tr>
					<td width="20%" align="right">备注：</td>
					<td>
						<textarea style="width: 95%;height:100px;" readonly name="remark" id="remark">${p.remark }</textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2">
						<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">关闭</a>
						<input type="hidden" id="organize_id" name="organize_id" value="${pd.organize_id }">
					</td>
				</tr>
			</table>
			</div>
		</form>
			<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		<!-- 引入 -->
	<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>
	<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->
	<!-- 引入 -->
</body>
</html>