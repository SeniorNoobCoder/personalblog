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
				var bol = '${pd.flag}';
				if(bol=='b'){
					$("#zhongxin").hide();
                   	$("#zhongxin2").show();
                   	art.dialog.opener.saveSuccess();
				}
			});
			
			
			
			$().ready(function() {
			  // 在键盘按下并释放及提交后验证提交表单
			  $("#roleForm").validate({
				    rules: {
				 	 one_menu: {
				      	required:true
				      },
				      two_menu: {
				      	required:true
				      },
				      remark:{
				      maxlength:500
				      }
				    },
				    messages: {
				      	one_menu: {
				      		required:"<span style='color:red'>请输选择一级菜单。</span>"
				      	},
				      	two_menu: {
				      		required:"<span style='color:red'>请输选择二级菜单。</span>"
				      	},
				      	  remark: "<span style='color:red'>输入的最大长度不能超过500。</span>"
				    }
				});
			});
	
			
			function save(){
				$("#cdForm").submit();
			}
			
			
			function selectOneMenu(){
				//获取组织id
				var parent_id = $("#one_menu option:selected").val();
				$.ajax({
		            type: "GET",
		            url: "${pageContext.request.contextPath}/system/quickmenu/findMenuJson.do?parent_id="+parent_id,
		            data: "",
		            dataType: "json",
		            success: function(data){
		            	var tt = '<option value="" title="">请选择</option>';
		            	$.each(data,function(i,n){
							tt += '<option value="'+n.id+'" title="'+n.code+'">'+n.name+'</option>';
						});
						$("#two_menu").html(tt);
		            }
		         });
			}
		
		</script>
		
	</head>
<body>
	<c:if test="${pd.flag!='b'}">
		<form action="${pageContext.request.contextPath}/system/quickmenu/save.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
			<div id="zhongxin">
			<table width="90%">
				<tr>
					<td width="20%" align="right">一级菜单：</td>
					<td>
						<select name="one_menu" id="one_menu" style="width: 95%" onchange="selectOneMenu();">
							<option value="">请选择</option>
							<c:forEach items="${list}" var="var" varStatus="vs">
								<option value="${var.id }">${var.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				<td width="20%" align="right">二级菜单：</td>
						<td>
							<select name="two_menu" id="two_menu" style="width: 95%" onchange="selectUnit();">
							</select>
						</td>
				</tr>
				<tr>
					<td width="20%" align="right">级别：</td>
					<td>
					<select name="level" id="level" style="width: 95%">
						<option value="1" >1</option>
						<option value="2" >2</option>
						<option value="3" >3</option>
						<option value="4" >4</option>
					</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">备注：</td>
					<td>
						<textarea style="width: 95%;height:100px;" name="remark" id="remark"></textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
						<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
					</td>
				</tr>
			</table>
			</div>
		</form>
	</c:if>
	<c:if test="${pd.flag=='add'}">
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