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

    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>

    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>--%>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/order/callRecord/inputTree.js"></script>
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
        .datainp{ width:200px; height:30px; border:1px #ccc solid;}
        .datep{ margin-bottom:40px;}
         #zhongxin tr td input{width:97%;}
        #zhongxin tr td select{width:100%;}
        textarea{width:97%;}
    </style>
    <script type="text/javascript">
        createTree("${pageContext.request.contextPath}/partners/partners/findServerCategory.do");
        <%--$(document).ready(function(){--%>
            <%--var label = '${pd.label}';--%>
            <%--if(label=='b'){--%>
                <%--$("#zhongxin").hide();--%>
                <%--$("#zhongxin2").show();--%>
                <%--alert("订单添加成功!");--%>
                <%--art.dialog.open.api.close();--%>
                <%--art.dialog.opener.showCallRecordList();--%>
            <%--}else if (label=='false'){--%>
                <%--$("#zhongxin").hide();--%>
                <%--$("#zhongxin2").show();--%>
                <%--alert("订单添加失败!");--%>
            <%--}--%>
        <%--});--%>

        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#cdForm").validate({
                rules: {
                    nameSel: {
                        required:true
                    },
                    appointment_time: {
                        required:true
                    },
                    remark: {
                        required:true,
                        maxlength:300
                    }
                },
                messages: {
                    nameSel: {
                        required:"<span style='color:red'>请选择订单类型。</span>"
                    },
                    appointment_time: {
                        required:"<span style='color:red'>请选择预约时间。</span>"
                    },
                    remark: {
                        required:"<span style='color:red'>请填写订单备注。</span>",
                        maxlength:"<span style='color:red'>输入的最大长度不能超过300</span>"
                    }
                }
            });
        });
        //保存
        function save(){
            if (! $("#cdForm").valid()) {
                return;
            }
            $.ajax({
                cache: true,
                type: "POST",
                url:"${pageContext.request.contextPath}/order/order/saveOrderByCall.do",
                data:$('#cdForm').serialize(),
                async: false,
                error: function(request) {
                    alert(request);
                },
                success: function(data) {
                    if(data) {
                        alert("添加订单成功，到我的订单下面去查看已提交的订单！");
                        art.dialog.open.api.close();
                        art.dialog.opener.showCallRecordList();
                    }else {
                        alert("添加订单失败！");
                    }
                }
            });
        }
    </script>
    <style>
        #zhongxin{color: #666666}
        #headTitle{font-weight: bolder}
        #infoTable{border: 0px}
        #infoTable td{border: 0px;border-bottom: 1px dashed #CACACA;}
        #showTitle{
            width: 100%;text-align: left;margin: 10px 0 10px 10px;font-size: 14px; color: #2a91d8;width: 90%;font-weight: bolder;
        }
    </style>
</head>
<body>
    <c:if test="${pd.label!='b'}">
        <form name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
        <div id="zhongxin">
            <div id="showTitle">客户信息</div>
            <table id="infoTable" class="table table-striped table-bordered table-hover">
                <tr>
                    <td rowspan="4" width="150px"><img alt="" src="${p.head_url }" style="width: 80px;height: 80px"></td>

                    <td width="90px"><span id="headTitle">设备编号：</span></td>
                    <td>
                    ${customer.sn}
                        <input type="hidden" name="customer_sn" id="customer_sn" value="${customer.sn}">
                    </td>
                </tr>
                <tr>
                    <td><span id="headTitle">用户名称：</span></td>
                    <td>${customer.name}
                        <input type="hidden" name="customer_name" id="customer_name" value="${customer.name}">
                    </td>
                </tr>
                <tr>
                    <td><span id="headTitle">性&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
                    <td>${customer.sex}</td>
                </tr>
                <tr>
                    <td><span id="headTitle">联系方式：</span></td>
                    <td>${customer.phone}
                        <input type="hidden" name="customer_phone" id="customer_phone" value="${customer.phone}">
                    </td>
                </tr>
                <tr>
                    <td><span id="headTitle">家庭住址：</span></td>
                    <td colspan="2">${customer.address}
                        <input type="hidden" name="customer_address" id="customer_address" value="${customer.address}">
                    </td>
                </tr>
            </table>
            <div id="showTitle">客户信息</div>
            <table width="90%">
                <tr>
                    <td width="20%" align="right" id="headTitle">订单类型：</td>
                    <td>
                        <input style="height: 30px;line-height: 30px"  id="nameSel"  class = "nameSel" name="nameSel" type="text" readonly value="" onclick="showMenu(); return false;" style="width: 95%"  placeholder="服务类型" title="服务类型"/>
                        <div id="menuContent" class="menuContent" style="display: none; position: absolute;">
                            <ul id="treeDemo" class="ztree" style="margin-top: 0; width: 200px;">
                            </ul>
                        </div>
                        <input type="hidden" id="server_category_name" name="server_category_name" value="" style="width: 95%" title="服务类型名称"/>
                        <input type="hidden" id="server_category_id"  name="server_category_id" value="" style="width: 95%" title="服务类型id"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right" id="headTitle">预约时间：</td>
                    <td><input style="height: 30px;line-height: 30px" type="text" name="appointment_time" id="appointment_time" readonly /></td>
                </tr>
                <tr>
                    <td width="20%" align="right" id="headTitle">订单备注：</td>
                    <td>
                        <textarea rows="3" cols="10" name="remark" id="remark"></textarea>
                </tr>
                <tr>
                    <td style="text-align: center;" colspan="2">
                        <input type="hidden" id="callRecordId" name="callRecordId" value="${pd.id}" title="电话记录id">
                        <a class="btn btn-mini btn-primary" onclick="save();">下订单</a>
                        <a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">取消</a>
                    </td>
                </tr>
            </table>
        </div>
        </form>
    </c:if>
<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/>
    <img src="${pageContext.request.contextPath}/resources/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4>
</div>
<!-- 引入 -->
<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>
<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/base/js/jquery.jedate.js"></script><!-- 日期框 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/pages/base/js/skin/jedate.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/base/js/common.js"></script>
<script type="text/javascript">
    //初始化时间控件
    //初始化时间控件
    var create_time = {
        format: 'YYYY-MM-DD',
//        minDate: $.nowDate(0), //设定最小日期为当前日期
        isinitVal:true,
        festival:false,
        ishmsVal:false,
        minDate: getNowFormatDate(), //最大日期
        choosefun: function(elem,datas){
//            endDate.minDate = datas; //开始日选好后，重置结束日的最小日期
        }
    };
    $('#create_time').jeDate(create_time);
</script>
</body>
</html>