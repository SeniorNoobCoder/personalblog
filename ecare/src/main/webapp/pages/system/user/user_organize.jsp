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
    
    <title>用户组织</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.excheck.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
	
	<script type="text/javascript">
		var user_id = '${pd.user_id}';
		var url="${pageContext.request.contextPath}/system/user/saveOrganize.do?user_id="+user_id;
		
		function save(){
			var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
            nodes=treeObj.getCheckedNodes(true),
            v="";
            for(var i=0;i<nodes.length;i++){
            	v+=nodes[i].id + ",";
            }
            if(nodes.length>1){
            	art.dialog.alert("只能分配一个组织。");
            }else{
            	 $.ajax({
				    url : url+"&organizes="+v,
				    data : {},
				    type : 'GET',
				    success : function(data) {
				    	art.dialog.alert("用户组织设置成功。");
				    },
				    error : function(xhr, status) {
				        alert('重新设置组织失败!');
				    },
				    complete : function(xhr, status) {
				    }
				});
            }
		}
		
	</script>
	<%@include file="organize_tree.jsp" %>
  </head>
  
  <body> 
    <!-- BEGIN EXAMPLE TABLE PORTLET-->
	<div style="float: left;width: 100%;">
		<div class="portlet box green">
			<div class="portlet-body" style="min-height: 410px">
				<div class="clearfix">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>

		</div>
		<!-- END EXAMPLE TABLE PORTLET-->	
	</div>
	<div align="center">
		<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
		<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">关闭</a>
	</div>
  </body>
</html>
