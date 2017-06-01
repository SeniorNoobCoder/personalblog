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

<title></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/zTree/zTreeStyle.css" type="text/css" />
<!--<link rel="stylesheet" href="css/ace.min.css" />-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
<style type="text/css">
footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
</style>

</head>
<body>
	<div id="zhongxin">
		<ul id="tree" class="tree" style="overflow:auto;"></ul>
	</div>
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/resources/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/zTree/jquery.ztree-2.6.min.js"></script>
	

	
	<script type="text/javascript">
	var zTree;
	$(document).ready(function(){
			
			var setting = {
			    showLine: true,
			    checkable: true
			};
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			zTree = $("#tree").zTree(setting, zTreeNodes);

			//zTree.expandAll(true);
			var nodes = zTree.getNodes();
			for (var i = 0; i < nodes.length; i++) { //设置节点展开
			zTree.expandNode(nodes[0], true, false, true);
			}
		});
	</script>
	<script type="text/javascript">
		 function save(){
			  var nodes = zTree.getCheckedNodes();
				var tmpNode;
				var ids = "";
				for(var i=0; i<nodes.length; i++){
					tmpNode = nodes[i];
					if(i!=nodes.length-1){
						ids += tmpNode.id+",";
					}else{
						ids += tmpNode.id;
					}
				}
				
				var userId = "${userId}";
				var roleId = "${roleId}";
				var url = "${pageContext.request.contextPath}/system/role/saveMenuRole.do";
				var postData;
				if(userId!=""){
					postData = {"userId":userId,"menuIds":ids};
				}else{
					postData = {"ROLE_ID":roleId,"menuIds":ids};
				}
				
				$("#zhongxin").hide();
				$("#zhongxin2").show();
				$.post(url,postData,function(data){
					if(data && data=="success"){
						art.dialog.open.api.close();
					}
				});
		 }
	
	</script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/artDialog.source.js?skin=blue"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/iframeTools.source.js"></script>
<script>
		(function () {
			var api = art.dialog.open.api;	// 			art.dialog.open扩展方法
			if (!api) return;
			// 操作对话框
			api.title('角色菜单配置')
				// 自定义按钮
				.button({
						name: '确定',
						callback: function () {
						    //art.dialog.opener.addHf(ny);
							//art.dialog.close();
							save();
							return false;
						},
						focus: true
					},{
						  name: '取消',
						  callback: function () {
						}
					}
				);
			})();
		</script>
		
</body>
</html>