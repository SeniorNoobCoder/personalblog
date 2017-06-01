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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
    <![endif]-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/server/info/inputTree.js"></script>
    <style type="text/css">
        ul.ztree
        {
            margin-top: 10px;
            border: 1px solid #617775;
            background: #f0f6e4;
            width: 220px;
            height: 200px;
            overflow-y: scroll;
            overflow-x: auto;
            z-index:9999;
        }
    </style>
    <script type="text/javascript">
        createTree("${pageContext.request.contextPath}/partners/partners/findServerCategory.do?type=category");
        $(document).ready(function(){
            var label = '${pd.label}';
            if(label=='add'){
                $("#zhongxin").hide();
                $("#zhongxin2").show();
                <%--alert("添加成功！");--%>
                <%--art.dialog.open.api.close();--%>
                <%--art.dialog.opener.show("${pd.parent_id}");--%>
                art.dialog.opener.saveSuccess();
            }
        });
        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#cdForm").validate({
                rules: {
                    server_category_name: {
                        required:true
                    },
                    server_category_id: {
                        required:true
                    },
                    nameSel: {
                        required:true
                    },
                    title: {
                        required:true,
                        maxlength:100
                    },
                    content: {
                        required:true,
                        maxlength:500
                    }
                },
                messages: {
                    nameSel: {
                        required:"<span style='color:red'>请选择服务类型。</span>",
                    },
                    title: {
                        required:"<span style='color:red'>请输入服务信息标题。</span>",
                        maxlength:"<span style='color:red'>服务信息标题长度不能超过100。</span>"
                    },
                    content: {
                        required:"<span style='color:red'>请输入服务信息详情。</span>",
                        maxlength:"<span style='color:red'>服务信息详情长度不能超过500。</span>"
                    }
                }
            });
        });
        function save(){
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
<form action="${pageContext.request.contextPath}/server/info/save.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
    <c:if test="${pd.label!='b'}">
        <div id="zhongxin">
            <table width="90%">
                <tr>
                    <td width="20%" align="right">服务类型：</td>
                    <td>
                        <input id="nameSel"  class = "nameSel" name="nameSel" type="text" readonly value="" onclick="showMenu(); return false;" style="width: 95%"  placeholder="服务类型" title="服务类型"/>
                        <div id="menuContent" class="menuContent" style="display: none; position: absolute;">
                            <ul id="treeDemo" class="ztree" style="margin-top: 0; width: 200px;">
                            </ul>
                        </div>
                        <input type="hidden" id="server_category_name" name="server_category_name" value="" style="width: 95%" title="服务类型名称"/>
                        <input type="hidden" id="server_category_id"  name="server_category_id" value="" style="width: 95%" title="服务类型id"/>
                        </td>
                </tr>
                <tr>
                    <td width="20%" align="right">信息标题：</td>
                    <td>
                        <input type="text" name="title" id="title" placeholder="这里输入信息标题" title="信息标题"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right">信息描述：</td>
                    <td>
                        <textarea rows="6" cols="8" name="content" id="content"></textarea>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.config.js"></script>
</body>
</html>