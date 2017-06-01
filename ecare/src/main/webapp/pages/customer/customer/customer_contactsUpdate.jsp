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
			      name: {
			      	 required:true,
			      	 maxlength:50
			      },
			      phone: {
                        required:true,
                        isPhone:true,
                        maxlength:11
                    }
			    },
			    messages: {
			      name: {
			    	  required: "<span style='color:red'>请输入客户姓名。</span>",
				      maxlength:"<span style='color:red'>输入的最大长度不能超过50。</span>"
			      },
			       phone: {
                        required:"<span style='color:red'>请输入联系方式。</span>",
                        isPhone:"<span style='color:red'>请正确填写您的联系方式。</span>",
                        maxlength:"<span style='color:red'>输入的最大长度不能超过11。</span>",
                    }
			    }
			});
		});
		
		jQuery.validator.addMethod("isPhone", function(value,element) {
            var length = value.length;
            var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
            var tel = /^\d{3,4}-?\d{7,9}$/;
            return this.optional(element) || (tel.test(value) || mobile.test(value));
        }, "请正确填写您的联系电话");
        
		function save(){
			$("#cdForm").submit();
		}
		
	</script>

  </head>
  
  <body>
    <form action="${pageContext.request.contextPath}/customer/customer/updateContacts.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		<c:if test="${pd.label!='b'}">
		<div id="zhongxin">
		<table width="90%">
			<tr>
				<td width="20%" align="right">联系人姓名：<span style="color:#FF0000">*</span></td>
				<td><input type="text" name="name" id="name" style="width: 95%" placeholder="这里输入联系人姓名" title="联系人姓名" value="${pd.name}"/></td>
			</tr>
			<tr>
                <td width="20%" align="right">联系方式：</td>
                <td><input type="text" name="mobile" id="mobile" style="width: 95%" placeholder="这里输入联系方式" title="联系方式" value="${pd.mobile}"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">快捷键：</td>
                <td>
					<select id="key" name="key">
						<option value="" <c:if test="${pd.key==''}">selected</c:if>>无</option>
						<option value="A" <c:if test="${pd.key=='A'}">selected</c:if>>1</option>
						<option value="B" <c:if test="${pd.key=='B'}">selected</c:if>>2</option>
						<option value="C" <c:if test="${pd.key=='C'}">selected</c:if>>3</option>
						<option value="D" <c:if test="${pd.key=='D'}">selected</c:if>>4</option>
						<option value="E" <c:if test="${pd.key=='E'}">selected</c:if>>客服</option>
					</select>
                </td>
            </tr>    
			<tr>
				<td style="text-align: center;" colspan="2">
					<input type="hidden" name="sn" id="sn" value="${pd.sn}"/>
					<input type="hidden" name="cid" id="cid" value="${pd.cid}"/>
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
  </body>
</html>
