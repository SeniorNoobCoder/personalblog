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
	<!--[if lt IE 9]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
	<![endif]-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>

  </head>
  
  <body>
<c:if test="${pd.flag == 'toadd' }">
		<div id="zhongxin">
   		<form action="${pageContext.request.contextPath}/system/menu/save.do" name="cdForm" id="cdForm" method="post">
		<table width="90%">
			<tr>
				<td width="20%" align="right">上级菜单：</td>
				<td><input type="text" style="width: 95%;" readonly value="${pd.name }"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">菜单类型：</td>
				<c:if test="${pd.type=='menu' }">
					<td><input type="text" style="width: 95%;" readonly value="菜单"/></td>
				</c:if>
				<c:if test="${pd.type=='button' }">
					<td><input type="text" style="width: 95%;" readonly value="按钮"/></td>
				</c:if>
			</tr>
			<tr>
				<td width="20%" align="right">菜单名称：</td>
				<td><input type="text" id="name" name="name" style="width: 95%" placeholder="这里输入菜单名称" title="菜单名称"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">编码：</td>
				<td><input type="text" id="code" name="code" style="width: 95%" placeholder="这里输入编码" title="编码"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">链接：</td>
				<td><input type="text" name="url" id="url" style="width: 95%" placeholder="这里输入链接" title="链接"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">排序：</td>
				<td><input type="number" name="order_by" id="order_by" style="width: 95%" placeholder="这里输入序号" title="序号"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">备注：</td>
				<td>
					<textarea rows="3" cols="8" name="remark" id="remark"  style="width:95%;"></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<input type="hidden" name="parent_id" value="${pd.id }"/>
					<input type="hidden" name="level" value="${pd.level }"/>
					<input type="hidden" name="type" value="${pd.type }"/>
					<a class="btn btn-mini btn-primary" onclick="saveMenu();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
	</form>	
	</div>
</c:if>
<c:if test="${pd.flag == 'success'}">
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->
	
	<script type="text/javascript">

	$().ready(function() {
		  // 在键盘按下并释放及提交后验证提交表单
		  $("#cdForm").validate({
			    rules: {
			 	 name: {
			      	required:true,
			      	maxlength:50
			      },
			      code:{
			      	required:true,
			      	maxlength:50
			      },
			      url: {
			      	maxlength:240
			      },
			      order_by: {
			      	required:true,
				    digits:true
				  },
				  remark: {
				      maxlength:500
				  }
			    },
			    messages: {
			    	name: {
			      		required:"<span style='color:red'>请输入菜单名称。</span>",
			      		maxlength:"<span style='color:red'>输入的最大长度不能超过50。</span>"
			      	},
			    	code: {
			      		required:"<span style='color:red'>请输入编码。</span>",
			      		maxlength:"<span style='color:red'>输入的最大长度不能超过50。</span>"
			      	},
			      	url: "<span style='color:red'>输入的最大长度不能超过240。</span>",
			    	 order_by: {
				      		required:"<span style='color:red'>请输入排序。</span>",
				      		digits:"<span style='color:red'>请输入数字格式。</span>"
			      		},
			    	remark: "<span style='color:red'>输入的最大长度不能超过500。</span>"
			    }
			});
		});

	function saveMenu(){
		$("#cdForm").submit();
	}
	
		$(document).ready(function(){  
			var flag = '${pd.flag}';
			if(flag=='success'){
				$("#zhongxin").hide();
                $("#zhongxin2").show();
               	art.dialog.opener.saveSuccess();
			}
		});  

	</script>
		
  </body>
</html>
