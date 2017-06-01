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
	<table width="90%">
		<tr>
			<td width="20%" align="right">上级地区名称：</td>
			<c:if test="${p.parent_name==''||p.parent_name==null}">
				<td><input type="text" style="width: 95%" value="地区管理" readonly="readonly" /></td>
			</c:if>
			<c:if test="${p.parent_name!=''&&p.parent_name!=null}">
				<td><input type="text" style="width: 95%" value="${p.parent_name }" readonly="readonly"/></td>
			</c:if>
		</tr>
		<tr>
			<td width="20%" align="right">地区名称：</td>
			<td><input type="text" name="name" id="name" style="width: 95%" value="${p.name }" readonly="readonly" /></td>
		</tr>
		<tr>
			<td width="20%" align="right">排序：</td>
			<td><input type="text" name="order_by" id="order_by" style="width: 95%" value="${p.order_by }" readonly="readonly"/></td>
		</tr>
		<tr>
			<td width="20%" align="right">备注：</td>
			<td>
				<textarea rows="2" cols="8" name="remark" id="remark"  style="width:95%;" readonly="readonly">${p.remark }</textarea>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;" colspan="2">
				<a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">关闭</a>
			</td>
		</tr>
	</table>
	
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
		//实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    UE.getEditor('editor');
	</script>
	
	<script type="text/javascript">
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			//日期框
			$('.date-picker').datepicker();
		</script>
		
  </body>
</html>
