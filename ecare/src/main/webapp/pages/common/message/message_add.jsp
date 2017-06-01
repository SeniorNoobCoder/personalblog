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
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace.min.css" />--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/multi.css" />--%>
	<!--[if lt IE 9]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
	<![endif]-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/MultiSelectDropList.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){  
			var label = '${pd.label}';
			if(label=='b'){
				$("#zhongxin").hide();
                $("#zhongxin2").show();
				art.dialog.opener.saveSuccess();
			}
		});  
	
		$().ready(function() {
		  // 在键盘按下并释放及提交后验证提交表单
		  $("#cdForm").validate({
			    rules: {
			    	title:{
			    	required:true,
					maxlength:50
			    	},
//					source: {
//			      	required:true,
//					maxlength:10
//			      },
//					recipients: {
//					required:true
//			      },
					remark:{
						required:true
					},
 			      content: {
 			    	  required:true
 			      }
			    },
			    messages: {
					title:{
						required:"<span style='color:red'>请输入标题。</span>",
						maxlength:"<span style='color:red'>输入的最大长度不能超过50。</span>"
					},
//					source: {
//						required:"<span style='color:red'>请输入消息来源。</span>",
//						maxlength:"<span style='color:red'>输入的最大长度不能超过20。</span>"
//					  },
//					recipients: "<span style='color:red'>请选择接收人。</span>",
					remark:  "<span style='color:red'>请输入消息简介。</span>",
					content:  "<span style='color:red'>请输入消息内容。</span>"
			    }
			});
		});
		/**
		 * 复选框选中事件
		 */
		function jqchk(){ //jquery获取复选框值
			var chk_value = "";
			$('input[name="recipients"]:checked').each(function(){
				chk_value+=$(this).val()+",";
			});
			$("#recipients_id").val(chk_value);
		}
		function save(){
			if($('#recipients_id').val() == ''){
				art.dialog.alert("请选择发送对象。");
				return false;
			}
			if(!UE.getEditor('remark').hasContents()){
				art.dialog.alert("请填写描述。");
				return false;
			}
			if(!UE.getEditor('content').hasContents()){
				art.dialog.alert("请填写内容。");
				return false;
			}
			$("#cdForm").submit();
		}
	</script>

  </head>
  <style>
  #zhongxin tr td input{width:97%;}
  #zhongxin tr td select{width:100%;}
  textarea{width:97%;}
  </style>
  <body>
    <form action="${pageContext.request.contextPath}/common/message/save.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		<c:if test="${pd.label!='b'}">
		<div id="zhongxin">
		<table width="90%">
			<tr>
				<td width="20%" align="right">标&nbsp;&nbsp;&nbsp;&nbsp;题：<span style="color:#FF0000">*</span></td>
				<td><input type="text" name="title" id="title" placeholder="这里输入标题" title="标题"/></td>
			</tr>
			<%--<tr>--%>
				<%--<td width="20%" align="right">来&nbsp;&nbsp;&nbsp;&nbsp;源：<span style="color:#FF0000">*</span></td>--%>
				<%--<td>--%>
					<%--<input type="text" name="source" id="source" placeholder="这里输入消息来源" title="消息来源"/></td>--%>
				<%--</td>--%>
			<%--</tr>--%>
			<tr>
				<td width="20%" align="right">收件人：<span style="color:#FF0000">*</span></td>
				<td>
					<input type="checkbox" name="recipients" value="telemarketer" style="width: 30px;margin-bottom: 8px" onclick="jqchk()"/>客服
					<input type="checkbox" name="recipients"  value="partners" style="width: 30px;margin-bottom: 8px" onclick="jqchk()"/>商家
					<input type="checkbox" name="recipients"  value="server" style="width: 30px;margin-bottom: 8px" onclick="jqchk()"/>商户
					<input type="checkbox" name="recipients"  value="family" style="width: 30px;margin-bottom: 8px" onclick="jqchk()"/>家人
					<input type="hidden" name = "recipients_id" id="recipients_id" value=""/>
				</td>
			</tr>

			<tr>
				<td width="20%" align="right">简&nbsp;&nbsp;&nbsp;&nbsp;介：<span style="color:#FF0000">*</span></td>
				<td>
					<textarea id="remark" style="width:500px;height:100px;" name="remark" class="required"></textarea>
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">内&nbsp;&nbsp;&nbsp;&nbsp;容：<span style="color:#FF0000">*</span></td>
				<td>
					<textarea id="content" style="width:500px;height:300px;" name="content" class="required"></textarea>
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
		</c:if>
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
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDrag.js"></script>--%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDialog.js"></script>--%>
	<!--引入弹窗组件end-->
	
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->--%>
	
	
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		UE.getEditor('content');
		UE.getEditor('remark');
	</script>
		
  </body>
</html>
