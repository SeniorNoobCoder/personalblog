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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ht.css" />
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
                    type: {
                        required:true
                    },
                    authent_type: {
                        required:true
                    },
                    authent_code: {
                        required:true
                    },
                    authent_name: {
                        required:true
                    },
                    authent_photo1: {
                        isPhoto:true
                    },
                    admin_name: {
                        required:true
                    },
                    admin_code: {
                        required:true
                    },
                    admin_phone: {
                        required:true,
                        isPhone:true,
                        maxlength:11
                    }
                },
                messages: {
                    type: {
                        required:"<span style='color:red'>请选择认证类型。</span>"
                    },
                    authent_type: {
                        required:"<span style='color:red'>请选择证件类型。</span>"
                    },
                    authent_code: {
                        required:"<span style='color:red'>请输入证件号码。</span>"
                    },
                    authent_name: {
                        required:"<span style='color:red'>请输入企业名称。</span>"
                    },
                    authent_photo1: {
                        isPhoto:"<span style='color:red'>请选择正确的文件格式。</span>"
                    },
                    admin_name: {
                        required:"<span style='color:red'>请输入法人名称。</span>"
                    },
                    admin_code: {
                        required:"<span style='color:red'>请输入法人证件号。</span>"
                    },
                    admin_phone: {
                        required:"<span style='color:red'>请输入法人联系方式。</span>",
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

        jQuery.validator.addMethod("isPhoto", function(value,element) {
            var length = value.length;
            //var reg=/(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/;
            var reg=/\.jpg$|\.jpeg$|\.gif$|\.bmp$|\.ico$|\.pcx$|\.tif$|\.png$|\.raw$|\.tga$/i
            return (reg.test(value)) || value=='' || value==null;
        }, "请选择图片格式文件。");


        function save(submit_type){
        	$("#submit_type").val(submit_type);
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
<form action="${pageContext.request.contextPath}/partners/partners/saveAuthent.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
    <c:if test="${pd.label!='b'}">
    	<div id="pact">
			<div class="pact-head">
                <ul class="processor_bar grid_line" style="">
                    <li class="next" >
                        <h4>1、合作商基本信息</h4>
                    </li>
                    <li class="current">
                        <h4>2、合作商认证信息</h4>
                    </li>
                    <li class="next">
                        <h4>3、设置服务分类</h4>
                    </li>
                </ul>
		    </div>
	    </div>
        <div id="zhongxin">
            <table width="90%">
                <tr>
                    <td width="20%" align="right">类型：</td>
                    <td>
                    	<select id="type" name="type">
                    		<option value="QYRZ">企业认证</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right">证件类型：</td>
                    <td>
                    	<select id="authent_type" name="authent_type">
                    		<option value="YYZZ">营业执照</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right">证件号码：</td>
                    <td><input type="text" name="authent_code" id="authent_code" value="${p.authent_code }" placeholder="这里输入证件号码" title="证件号码"/></td>
                </tr>
                <tr>
                    <td width="20%" align="right">企业名称：</td>
                    <td><input type="text" name="authent_name" id="authent_name" value="${p.authent_name }" placeholder="这里输入企业名称" title="企业名称"/></td>
                </tr>
                <tr>
					<td width="20%" align="right">营业执照图片：</td>
					<td><img alt="" src="${p.authent_photo1_url }" style="width: 80px;height: 80px;margin-bottom:10px;"><br/>
					<input type="file" name="authent_photo1" id="authent_photo1" value="${p.authent_photo1 }"/></td>
				</tr>
				<tr>
                    <td width="20%" align="right">法人名称：</td>
                    <td><input type="text" name="admin_name" id="admin_name" value="${p.admin_name }" placeholder="这里输入管理员姓名" title="管理员姓名"/></td>
                </tr>
				<tr>
                    <td width="20%" align="right">法人身份证号：</td>
                    <td><input type="text" name="admin_code" id="admin_code" value="${p.admin_code }" placeholder="这里输入管理员身份证号" title="管理员身份证号"/></td>
                </tr>
                <%--<tr>--%>
                    <%--<td width="20%" align="right">管理员手机号：</td>--%>
                    <%--<td><input type="text" name="admin_phone" id="admin_phone" value="${p.admin_phone }" placeholder="这里输入管理员手机号" title="管理员手机号"/></td>--%>
                <%--</tr>--%>
                <tr>
                    <td width="20%" align="right">备注：</td>
                    <td>
                       <textarea rows="2" cols="8" name="remark" id="remark">${p.remark }</textarea>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center;" colspan="2">
                    	<input type="hidden" id="submit_type" name="submit_type" value="">
                    	<input type="hidden" id="id" name="id" value="${p.id }">
                    	<input type="hidden" id="partner_id" name="partner_id" value="${pd.partner_id }">
                    	<a class="btn btn-mini btn-primary" onclick="save('save_before');">上一步</a>
                        <a class="btn btn-mini btn-primary" onclick="save('save');">保存</a>
                        <a class="btn btn-mini btn-primary" onclick="save('save_next');">保存继续</a>
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
<%--<!--引入弹窗组件end-->--%>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->--%>


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
    $(function() {
        $('#authent_photo1').ace_file_input({
            no_file:'请选证件照 ...',
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