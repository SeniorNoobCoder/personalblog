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
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>--%>
    <style>
        #showTitle{
            width: 100%;text-align: left;margin: 10px 0 10px 10px;font-size: 14px; color: #2a91d8;width: 90%;
        }
        #order{text-align: left;margin: 0 0 0 20px}
        #order ul{list-style-type:none}
        #order ul li{list-style-type:none;height: 30px;line-height: 30px;border-bottom: 1px dashed #CACACA;}
        #orderFlow{text-align: center}
        #time{width: 30%;float: left;}
        #content{width: 50%;float: left;}
        #operator{width: 20%;float: left;}
    </style>
</head>
<body>
<div id="order" class="order">
    <div id="customer" class="customer">
        <div style="text-align: center">
            <img src="${p.code_image}"/>
        </div>
        <ul>
            <li>公司名称：${p.authent_name}&nbsp;&nbsp;&nbsp;&nbsp;编号：${p.code}</li>
            <li>证件类型：营业执照&nbsp;&nbsp;&nbsp;&nbsp;证件号码：${p.authent_code}</li>
            <li>法人名称：${p.admin_name}&nbsp;&nbsp;&nbsp;&nbsp;法人身份证号：${p.admin_code}</li>
        </ul>
    </div>
    <div id="showTitle">联系人信息</div>
    <div >
        <ul>
            <li>联系人:${p.user_name}&nbsp;&nbsp;&nbsp;&nbsp;电话：${p.phone}</li>
            <li>邮箱：${p.email}</li>
            <li>地址：${p.address}</li>
        </ul>
    </div>
    <div id="showTitle">认证信息</div>
    <div>
        <img src="${p.authent_photo1}" width="400px"/>
    </div>
</div>

<!-- 引入 -->
<%--<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>--%>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/chosen.jquery.min.js"></script><!-- 下拉框 -->--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->--%>
<%--<!-- 引入 -->--%>

<!--引入弹窗组件start-->
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDrag.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDialog.js"></script>--%>
<%--<!--引入弹窗组件end-->--%>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->--%>

<%--<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.config.js"></script>--%>
<%--<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.all.min.js"> </script>--%>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<%--<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>--%>
<%--<script type="text/javascript">--%>
<%--//实例化编辑器--%>
<%--//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例--%>
<%--UE.getEditor('editor');--%>
<%--</script>--%>
<%--<script type="text/javascript">--%>
<%--//下拉框--%>
<%--$(".chzn-select").chosen();--%>
<%--$(".chzn-select-deselect").chosen({allow_single_deselect:true});--%>
<%--//日期框--%>
<%--$('.date-picker').datepicker();--%>
<%--</script>--%>
</body>
</html>
