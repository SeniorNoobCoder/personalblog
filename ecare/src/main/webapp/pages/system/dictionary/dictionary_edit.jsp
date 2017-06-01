<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>

<script type="text/javascript">
		
			$(document).ready(function(){ 
				var flag = '${pd.flag}';
				if(flag=='edit'){
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
			      type: {
			      	maxlength:25
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
			      		required:"<span style='color:red'>请输入名称。</span>",
			      		maxlength:"<span style='color:red'>输入的最大长度不能超过50。</span>"
			      	},
			      	short_name: "<span style='color:red'>输入的最大长度不能超过50。</span>",
			      	type: "<span style='color:red'>输入的最大长度不能超过25。</span>",
			      	order_by: {
				      		required:"<span style='color:red'>请输入排序。</span>",
				      		 digits:"<span style='color:red'>请输入数字格式。</span>"
			      		},
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
   		<form action="${pageContext.request.contextPath}/system/dictionary/update.do" name="cdForm" id="cdForm" method="post" >
		<div id="zhongxin">
			<table width="90%">
		<tr>
				<td width="20%" align="right">上级节点：<input type="hidden" name="id" value="${pd.id }"/>
				</td>
				<td>
				<input type="text" name="parent_id" readonly="readonly" style="width: 95%" value="${pd.name }"/>
				</td>
				</tr>
			<tr>
					<td width="20%" align="right">字典名称：</td><td><input maxlength="150" type="text" value="${p.name }" name="name" id="name" style="width: 95%" placeholder="这里输入名称" title="名称"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">字典简称：</td>
					<td>
						<input maxlength="150" type="text" name="short_name" id="short_name" value="${p.short_name }" style="width: 95%" placeholder="这里输入简称" title="简称"/>
					</td>
				</tr>
				
				
				<tr>
					<td width="20%" align="right">类型：</td>
					<td><input type="text" name="type" value="${p.type }" id="type" style="width: 95%" placeholder="这里输入类型" title="类型"/></td>
				</tr>
				<tr>
					<td width="20%" align="right">排序：</td><td><input value="${p.order_by }" type="number" name="order_by" id="order_by" style="width: 95%" placeholder="这里输入序号" title="序号"/></td>
				</tr>
					<tr>
						<td width="20%" align="right">备注：</td>
						<td><textarea  name="remark" rows="3" style="width:95%;">${p.remark }</textarea>
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
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="${pageContext.request.contextPath}/resources/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	</form>	
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
	
	<script type="text/javascript">
	
		$(window.parent.hangge());
		//下拉框
		$(".chzn-select").chosen(); 
		$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
	</script>
		
  </body>
</html>
