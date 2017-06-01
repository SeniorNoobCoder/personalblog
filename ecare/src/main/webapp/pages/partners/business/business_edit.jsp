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
                    login_password: {
                        rangelength:[4,18]
                    },
                    phone: {
                        required:true,
                        isPhone:true,
                        maxlength:11
                    },
                    user_name: {
                        maxlength:50
                    },
                    live_address: {
                        maxlength:120
                    },
                    birth_address: {
                        maxlength:120
                    },
                    remark: {
                        maxlength:500
                    },
                    head_address: {
                        isPhoto:true
                    }
                },
                messages: {
                    login_password: "<span style='color:red'>请输入4-18位登录密码。</span>",
                    phone: {
                        required:"<span style='color:red'>请输入联系方式。</span>",
                        isPhone:"<span style='color:red'>请正确填写您的联系方式。</span>",
                        maxlength:"<span style='color:red'>输入的最大长度不能超过11。</span>"
                    },
                    user_name: "<span style='color:red'>输入的最大长度不能超过50。</span>",
                    live_address: "<span style='color:red'>输入的最大长度不能超过120。</span>",
                    birth_address: "<span style='color:red'>输入的最大长度不能超过120。</span>",
                    remark: "<span style='color:red'>输入的最大长度不能超过500。</span>",
                    head_address: {
                        isPhoto:"<span style='color:red'>请选择正确的文件格式。</span>"
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
        
        jQuery.validator.addMethod("isPhoto", function(value,element) {
            var length = value.length;
            //var reg=/(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/;
            var reg=/\.jpg$|\.jpeg$|\.gif$|\.bmp$|\.ico$|\.pcx$|\.tif$|\.png$|\.raw$|\.tga$/i
            return (reg.test(value)) || value=='' || value==null;
        }, "请选择图片格式文件。");
        
	
		function save(){
			$("#cdForm").submit();
		}
	</script>

  </head>
  
  <body>
    <form action="${pageContext.request.contextPath}/partners/business/update.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
		<c:if test="${pd.label!='b'}">
		<div id="zhongxin">
		<table width="90%">
			<tr>
                <td width="20%" align="right">登录名称：</td>
                <td><input type="text" value="${p.login_name }" readonly="readonly" style="width: 95%" placeholder="这里输入登录名称" title="登录名称"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">登录密码：</td>
                <td><input type="password" name="login_password" id="login_password" value="${p.login_password }" style="width: 95%" placeholder="这里输入登录密码" title="登录密码"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">用户名称：</td>
                <td><input type="text" name="user_name" id="user_name" value="${p.user_name }" style="width: 95%" placeholder="这里输入用户名称" title="用户名称"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">性别：</td>
                <td>
                    <select name="sex" id="sex" style="width: 95%">
                        <option value="男" <c:if test="${p.sex=='男'}"> selected="selected" </c:if>>男</option>
                        <option value="女" <c:if test="${p.sex=='女'}"> selected="selected" </c:if>>女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="20%" align="right">生日：</td>
                <td>
                    <input class="timepicker"  type="text" name="birth_date" id="birth_date" value="${p.birthday }" style="width: 95%" placeholder="这里输入用户生日" title="用户生日" readonly/>
                </td>
            </tr>    
            <tr>
                <td width="20%" align="right">联系方式：</td>
                <td><input type="text" name="phone" id="phone" value="${p.phone }" style="width: 95%" placeholder="这里输入联系方式" title="联系方式"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">居住地区：</td>
                <td><input type="text" name="live_address" id="live_address" value="${p.live_address }" style="width: 95%" placeholder="这里输入居住地区" title="居住地区"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">出生地址：</td>
                <td><input type="text" name="birth_address" id="birth_address" value="${p.birth_address }" style="width: 95%" placeholder="这里输入出生地址" title="出生地址"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">头像地址：</td>
                <td><input type="file" name="head_address" id="head_address" style="width: 95%"/></td>
            </tr>
            <tr>
                <td width="20%" align="right">备注：</td>
                <td>
                    <textarea rows="2" cols="8" name="remark" id="remark" style="width: 95%">${p.remark }</textarea>
                </td>
            </tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<input type="hidden" id="id" name="id" value="${p.id }">
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->
	
	
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datetimepicker.min.js"></script><!-- 日期框 -->
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
			
			
			 $(".timepicker").datetimepicker({
		            format: 'yyyy-mm-dd',
		            weekStart: 1,
		            autoclose: true,
		            startView: 2,
		            minView: 2,
		            forceParse: false
		        });
		        //=====================
		        $('#head_address').ace_file_input({
		            no_file:'请选择头像地址 ...',
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
		        
		</script>
		
  </body>
</html>
