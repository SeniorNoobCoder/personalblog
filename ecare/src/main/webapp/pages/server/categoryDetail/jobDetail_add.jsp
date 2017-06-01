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
            //==========================收费类型隐藏参数=================================
            isShow();
            //================================================================
            var label = '${pd.label}';
            if(label=='add'){
                $("#zhongxin").hide();
                $("#zhongxin2").show();
                alert("添加成功！");
                art.dialog.open.api.close();
                art.dialog.opener.showJobRemark("${pd.server_category_id}");
//                art.dialog.opener.saveSuccess();
            }
        });
        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#cdForm").validate({
                rules: {
                    remark: {
                        required:true,
                        maxlength:500
                    },
                    starting_price: {
                        required:true,
                        maxlength:200
                    }
                },
                messages: {
                    remark: {
                        required:"<span style='color:red'>请输入工作介绍。</span>",
                        maxlength:"<span style='color:red'>输入的最大长度不能超过500。</span>",
                    },
                    url: {
                        required:"<span style='color:red'>请输入APP跳转URL。</span>",
                        maxlength:"<span style='color:red'>输入的最大长度不能超过500。</span>",
                    }
                }
            });
        });

        function save(){
            $("#cdForm").submit();
        }
        function  isShow(){
            var pay_type = $("#pay_type").val();
            if(pay_type == "0"){
                document.getElementById("isShow").style.display="none";//隐藏
                document.getElementById("charge_mode").style.display="none";//隐藏
                document.getElementById("charge_mode_remark").style.display="none";//隐藏
            }else {
                document.getElementById("isShow").style.display="";//显示
                document.getElementById("charge_mode").style.display="";//显示
                document.getElementById("charge_mode_remark").style.display="";//显示
            }
        }
//        function inputNum(){
//            if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}
//        }
    </script>
</head>
<style>
    #zhongxin tr td input{width:97%;}
    #zhongxin tr td select{width:100%;}
    textarea{width:97%;}
</style>
<body>
<form action="${pageContext.request.contextPath}/server/categoryDetail/saveJobDetail.do" name="cdForm" id="cdForm" method="post" enctype="multipart/form-data">
    <c:if test="${pd.label!='b'}">
        <div id="zhongxin">
            <table width="95%">
                <tr>
                    <td width="20%" align="right">工作类型：</td>
                    <td><input type="text" readonly value="${pd.server_category_name}" style="width: 95%" placeholder="父节点" title="父节点"/></td>
                </tr>
                <tr>
                    <td width="20%" align="right">工作内容：</td>
                    <td>
                        <%--<input type="text" name="remark" id="remark" placeholder="这里输入工作内容" title="工作内容"/>--%>
                        <textarea rows="2" cols="8" name="remark" id="remark" placeholder="这里输入工作内容" title="工作内容"></textarea>
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right">收费类型：</td>
                    <td>
                        <select  name="pay_type" id="pay_type" onchange="isShow()">
                            <option value="0">固定收费</option>
                            <option value="1">按时收费</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right">起始价格：</td>
                    <td>
                        <input type="text" name="starting_price" id="starting_price" placeholder="这里输入起始价格" title="起始价格" style="width: 80%"
                               onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}">
                        元起
                    </td>
                </tr>
                <tr id = "isShow">
                    <td colspan="2"><div style="width:100%;height:3px;background-color: #EEEEEE;margin: 10px"></div></td>
                </tr>
                <%--<tr id = "isShow">--%>
                    <%--<td width="20%" align="right">起始标准：</td>--%>
                    <%--<td>前 <input type="text" name="starting_price" id="begin_time" placeholder="起始标准" title="起始标准" style="width: 50px"/> /--%>
                        <%--<input type="text" name="charge_mode_unit" id="begin_unit" placeholder="单位" title="单位" style="width: 50px"/>--%>
                        <%--收费 <input type="text" name="price" id="begin_price" placeholder="20" title="单价" style="width: 50px"/> 元--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr id = "isShow">--%>
                    <%--<td colspan="2"><div style="width:100%;text-align: center;color: red;">*起始标准：每1小时收费20元</div></td>--%>
                <%--</tr>--%>
                <tr id = "charge_mode_remark">
                    <td colspan="2"><div style="width:100%;text-align: center;color: red;">*超出标准格式如：每1小时收费20元</div></td>
                </tr>
                <tr id = "charge_mode">
                    <td width="20%" align="right">超出标准：</td>
                    <td> 每 <input type="text" name="charge_mode_time" id="charge_mode_time" placeholder="计费标准" title="计费标准" style="width: 50px"
                                  onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}">
                        <input type="text" name="charge_mode_unit" id="charge_mode_unit" placeholder="单位" title="单位" style="width: 50px"/>
                        收费 <input type="text" name="price" id="price" placeholder="单价" title="单价" style="width: 50px"
                                  onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else  this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"  onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}">
                        元
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right">排序：</td>
                    <td><input type="text" name="order_by" id="order_by" placeholder="这里输入排序" title="排序" value="0"/></td>
                </tr>
                <tr>
                    <td style="text-align: center;" colspan="2">
                        <input type="hidden" name="server_category_id" id="server_category_id" title="父类id" value="${pd.server_category_id }"/>
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

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/chosen.jquery.min.js"></script><!-- 下拉框 -->--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->--%>
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
    UE.getEditor('editor');
</script>
</body>
</html>