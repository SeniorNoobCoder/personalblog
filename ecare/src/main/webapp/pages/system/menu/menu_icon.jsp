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
		<base href="<%=basePath%>">
		
		<meta charset="utf-8" />
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
		
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
		
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>

		<script type="text/javascript">
				
			function seticon(icon){
				$("#icon").val(icon);
				
			}	
			function save(){
				var icon = $("#icon").val();
				var iconNum = getByteLen(icon);
				if(iconNum==0){
					art.dialog.alert("请选择图标！");
				}else{
					$.ajax({
			            type: "POST",
			            url: '${pageContext.request.contextPath}/system/menu/updateMenu.do',
			            data: $('#menuForm').serialize(),
			            success: function (result) {
			            	$("#zhongxin").hide();
			            	$("#zhongxin2").show();
			            	art.dialog.opener.saveSuccess();
			            },
			            error: function(data) {
			             }
			        });
				
				
				
					
				}
			}
			
			//保存
			function getByteLen(val) {   
				var len = 0;   
				for (var i = 0; i < val.length; i++) {   
					if (val[i].match(/[^\x00-\xff]/ig) != null) //全角   
						len += 2;   
					else   
						len += 1;   
					}   
				return len;   
			}
		</script>
	</head>
	
	<body>
			<div id="zhongxin">
			<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-bottom:0px;">
				<tr>
					<td><label onclick="seticon('icon-desktop');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-desktop"></i></span></label></td>
					<td><label onclick="seticon('icon-list');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-list"></i></span></label></td>
					<td><label onclick="seticon('icon-edit');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-edit"></i></span></label></td>
					<td><label onclick="seticon('icon-list-alt');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-list-alt"></i></span></label></td>
					<td><label onclick="seticon('icon-calendar');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-calendar"></i></span></label></td>
					<td><label onclick="seticon('icon-picture');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-picture"></i></span></label></td>
					<td><label onclick="seticon('icon-th');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-th"></i></span></label></td>
					<td><label onclick="seticon('icon-file');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-file"></i></span></label></td>
					<td><label onclick="seticon('icon-folder-open');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-folder-open"></i></span></label></td>
				</tr>
				<tr>
					<td><label onclick="seticon('icon-book');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-book"></i></span></label></td>
					<td><label onclick="seticon('icon-cogs');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-cogs"></i></span></label></td>
					<td><label onclick="seticon('icon-comments');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-comments"></i></span></label></td>
					<td><label onclick="seticon('icon-envelope-alt');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-envelope-alt"></i></span></label></td>
					<td><label onclick="seticon('icon-film');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-film"></i></span></label></td>
					<td><label onclick="seticon('icon-heart');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-heart"></i></span></label></td>
					<td><label onclick="seticon('icon-lock');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-lock"></i></span></label></td>
					<td><label onclick="seticon('icon-exclamation-sign');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-exclamation-sign"></i></span></label></td>
					<td><label onclick="seticon('icon-facetime-video');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="icon-facetime-video"></i></span></label></td>
				</tr>
				<tr>
				<form action="" method="post" id="menuForm" name="menuForm">
					<input type="hidden" name="icon" id="icon" value=""/>
					<input type="hidden" name="id" id="id" value="${pd.id }"/>
				</form>
				<td style="text-align: center;" colspan="100">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
			</table>
			</div>
			<div id="zhongxin2" class="center" style="display:none"><img src="${pageContext.request.contextPath}/resources/images/jiazai.gif" /></div>
	</body>
</html>