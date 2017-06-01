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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){ 
				var bol = '${pd.label}';
				if(bol=='edit'){
					$("#zhongxin").hide();
                   	$("#zhongxin2").show();
                   	art.dialog.opener.saveSuccess();
				}
			});

			$().ready(function() {
			  // 在键盘按下并释放及提交后验证提交表单
			  $("#cdForm").validate({
				    rules: {
				      name: {
				      	required:true,
				      	maxlength:50
				      },
				       short_name: {
				      	maxlength:50
				      },
				 	 remark: {
				      	maxlength:500
				      }
				    },
				    messages: {
				    	name: {
				      		required:"<span style='color:red'>请输入组织名称。</span>",
				      		maxlength:"<span style='color:red'>输入的最大长度不能超过50。</span>"
			      		},
				      short_name: "<span style='color:red'>输入的最大长度不能超过50。</span>",
				      remark: "<span style='color:red'>输入的最大长度不能超过500。</span>"
				    }
				});
			});
			
			function save(){
				$("#cdForm").submit();
			}
		</script>
		
	</head>
<body>
	<c:if test="${pd.label!='edit'}">
		<form action="${pageContext.request.contextPath}/system/organize/update.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
			<div id="zhongxin">
			<table width="90%">
				<tr>
					<td width="20%" align="right">上级菜单：</td><td><input type="text" readonly value="${pd.name }" style="width: 95%" placeholder="父节点" title="父节点"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">组织名称：</td><td><input type="text" name="name" id="name" style="width: 95%" value="${p.name }" placeholder="这里输入组织名称" title="组织名称"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">组织简称：</td><td><input type="text" name="short_name" id="short_name" value="${p.short_name }" style="width: 95%" placeholder="这里输入组织简称" title="组织简称"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">排序：</td>
					<td><input type="number" name="order_by" id="order_by" style="width: 95%" value="${p.order_by }" placeholder="这里输入排序" title="排序"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">备注：</td>
					<td>
						<textarea style="width: 95%;height:100px;" name="remark" id="remark">${p.remark }</textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
						<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
						<input type="hidden" id="id" name="id" value="${pd.id }">
					</td>
				</tr>
			</table>
			</div>
		</form>
	</c:if>
	<c:if test="${pd.label=='edit'}">
			<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/resources/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	</c:if>		
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