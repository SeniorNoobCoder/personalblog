<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<!-- basic styles -->
	<link href="${pageContext.request.contextPath}/ui/css/bootstrap.min.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/ui/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/datepicker.css" /><!-- 日期框 -->
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

  </head>
  
  <body>
		<div id="zhongxin">
		<form action="${pageContext.request.contextPath}/customer/device/savedeviceImport.do" method="post" id="cdForm" name="cdForm" enctype="multipart/form-data">
		<table width="90%">
			<tr>
				<td><input type="file" name="import_file" id="import_file" width="240px;"></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
				</td>
			</tr>
		</table>
		</form>	
		</div>
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
	
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->
	
	
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		function save(){
			var oInput = document.cdForm.import_file.value;
			if(oInput == ""){
	    		art.dialog.alert("上传文件不能为空");
	    	}else{
	    		art.dialog({
			    content: '<span style="color:red;">导入将删除所有已添加的设备信息，请慎重！！！</span>',
			    ok: function () {
			    	$("#cdForm").submit();
			    },
			    cancelVal: '关闭',
			    cancel: true //为true等价于function(){}
				});
	    	}
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
	
	<script type="text/javascript">
	$(function() {
		$('#xc').ace_file_input({
				no_file:'请选择文件 ...',
				btn_choose:'选择',
				btn_change:'选择',
				droppable:false,
				onchange:null,
				thumbnail:false //| true | large
				//whitelist:'gif|png|jpg|jpeg'
				//blacklist:'exe|php'
				//onchange:''
				//
			});
		
	});
		</script>
	
	
  </body>
</html>