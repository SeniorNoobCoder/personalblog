<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
	<head>
		<title>自定义表单</title>
		<link rel='stylesheet prefetch' href='${pageContext.request.contextPath}/pages/builder/ui/css/form-builder.min.css'>
		<link rel='stylesheet prefetch' href='${pageContext.request.contextPath}/pages/builder/ui/css/form-render.min.css'>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/builder/ui/css/style.css">
		
		<script type="text/javascript">
		</script>
		
	</head>
	<body>
		<div id="edit-form">
			<textarea id="fb-template">
			</textarea>
		</div>
		<div id="rendered-form">
			<form action="#"></form>
			<button class="btn btn-default edit-form">
				编辑
			</button>
		</div>
		
		<script type="text/javascript">
		</script>
		
		<button type="button" onclick="saveDraft();">测试</button>
		
		<input type="text" id="xml" name="xml" value="">
		
		<script src='${pageContext.request.contextPath}/pages/builder/ui/js/jquery-1.8.0.min.js'></script>
		<script src='${pageContext.request.contextPath}/pages/builder/ui/js/jquery-ui.min.js'></script>
		<script src='${pageContext.request.contextPath}/pages/builder/ui/js/form-builder.js'></script>
		<script src='${pageContext.request.contextPath}/pages/builder/ui/js/form-render.min.js'></script>
<!--		<script src="${pageContext.request.contextPath}/pages/builder/ui/js/index.js"></script>-->
	
	
		<script type="text/javascript">
			jQuery(document).ready(function($) {
			  var fbTemplate = document.getElementById('fb-template'),
			    formContainer = document.getElementById('rendered-form'),
			    $fBInstance = $(document.getElementById('edit-form')),
			    formRenderOpts = {
			      container: $('form', formContainer)
			    };
			
			  $(fbTemplate).formBuilder();
			
			  $('.fb-save').click(function() {
			    $fBInstance.toggle();
			    $(formContainer).toggle();
			    $(fbTemplate).formRender(formRenderOpts);
			    alert($("#fb-template").html());
			    alert($("#rendered-form").html());
			    alert($("#edit-form").html());
			  });
			
			  $('.edit-form', formContainer).click(function() {
			    $fBInstance.toggle();
			    $(formContainer).toggle();
			  });
			});
			
			
			    
		</script>

	</body>
</html>
