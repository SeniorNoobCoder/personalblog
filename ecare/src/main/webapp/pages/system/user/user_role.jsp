<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>用户角色</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/multiselect/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/multiselect/lib/google-code-prettify/prettify.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/multiselect/css/style.css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
	
  </head>
  
  <body>
    
		<div class="row">
			<div class="col-xs-5">
				<select name="from" id="undo_redo" class="form-control" size="13" multiple="multiple">
					<option value="${pd.superAdministrator }">系统管理员</option>
					<c:forEach items="${allRoleList}" var="var" varStatus="status">
						<option value="${var.id }">${var.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="col-xs-2">
				<button type="button" id="undo_redo_undo" class="btn btn-primary btn-block">还原</button>
				<button type="button" id="undo_redo_rightAll" class="btn btn-default btn-block"><i class="glyphicon glyphicon-forward"></i></button>
				<button type="button" id="undo_redo_rightSelected" class="btn btn-default btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
				<button type="button" id="undo_redo_leftSelected" class="btn btn-default btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
				<button type="button" id="undo_redo_leftAll" class="btn btn-default btn-block"><i class="glyphicon glyphicon-backward"></i></button>
				<button type="button" id="undo_redo_redo" class="btn btn-warning btn-block">恢复</button>
			</div>
			
			<div class="col-xs-5">
				<select name="to" id="undo_redo_to" class="form-control" size="13" multiple="multiple">
					<c:forEach items="${userRoleList}" var="var" varStatus="status">
						<c:if test="${var.role_id==pd.superAdministrator}">
							<option value="${pd.superAdministrator }">系统管理员</option>	
						</c:if>
						<c:if test="${var.role_id!=pd.superAdministrator}">
							<option value="${var.role_id }">${var.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>
   		
   		<div align="center" style="padding-top: 20px;">
   			<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
			<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">关闭</a>
   		</div>
    
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/multiselect/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/multiselect/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/multiselect/js/prettify.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/multiselect/js/multiselect.min.js"></script>    
	<script type="text/javascript">
	$(document).ready(function() {
		// make code pretty
		window.prettyPrint && prettyPrint();
		
		if ( window.location.hash ) {
			scrollTo(window.location.hash);
		}
		
		$('.nav').on('click', 'a', function(e) {
			scrollTo($(this).attr('href'));
		});		
		$('#multiselect').multiselect();
		$('.multiselect').multiselect();
		$('.js-multiselect').multiselect({
			right: '#js_multiselect_to_1',
			rightAll: '#js_right_All_1',
			rightSelected: '#js_right_Selected_1',
			leftSelected: '#js_left_Selected_1',
			leftAll: '#js_left_All_1'
		});

		$('#keepRenderingSort').multiselect({
			keepRenderingSort: true
		});

		$('#undo_redo').multiselect();
	});
	
	function scrollTo( id ) {
		if ( $(id).length ) {
			$('html,body').animate({scrollTop: $(id).offset().top - 40},'slow');
		}
	}
	
	
	function save(){
		var role_ids = $("#undo_redo_to option").map(function(){return $(this).val();}).get().join(",");
		$.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/system/user/saveUserRole.do?role_ids="+role_ids+"&user_id=${pd.user_id}",
            data: "",
            dataType: "json",
            success: function(data){
            	art.dialog.alert("角色设置成功。");
            }
         });
	}
	</script>
	
  </body>
</html>
