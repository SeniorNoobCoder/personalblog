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
<table width="90%" class="table table-striped table-bordered table-hover">
    <tr>
        <td width="20%" align="right">合作商名称：</td>
        <td>${pd.partner_name}</td>
    </tr>
    <tr>
        <td width="20%" align="right">服务分类：</td>
        <td>${pd.server_category_name}</td>
    </tr>
    <tr>
        <td width="20%" align="right">信息标题：</td>
        <td>${pd.title}</td>
    </tr>
    <tr>
        <td width="20%" align="right">信息详情：</td>
        <td>${pd.content}</td>
    </tr>
    <tr>
        <td width="20%" align="right">创建时间：</td>
        <td>${pd.create_time}</td>
    </tr>
    <tr>
        <td width="20%" align="right">状态：</td>
        <td>
        <c:if test="${pd.status==0}"><font color="#00008b">新建</font></c:if>
        <c:if test="${pd.status==1}"><font color="#5f9ea0">待审核</font></c:if>
        <c:if test="${pd.status==2}"><font color="green">审核通过</font></c:if>
        <c:if test="${pd.status==3}"><font color="red">审核退回</font></c:if>
        <c:if test="${pd.status==9}"><font color="#808080">消息下架</font></c:if>
        </td>
    </tr>
    <tr>
        <td width="20%" align="right">审核人：</td>
        <td>${pd.operator_name}</td>
    </tr>
    <tr>
        <td width="20%" align="right">审核时间：</td>
        <td>${pd.operator_time}</td>
    </tr>
</table>
<!-- 引入 -->
<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>
<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/chosen.jquery.min.js"></script><!-- 下拉框 -->--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->--%>
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
</body>
</html>
