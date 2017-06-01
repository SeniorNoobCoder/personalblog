<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../system/admin/top.jsp" %>
<div class="container-fluid" id="main-container">
    <%@include file="../../system/admin/left.jsp" %>
    <div id="main-content" class="clearfix">
        <div id="breadcrumbs">
            <ul class="breadcrumb">
                <li><i class="icon-home"></i> <a href="#">订单管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
                <li class="active">订单信息</li>
            </ul><!--.breadcrumb-->
            <div id="nav-search">
            </div><!--#nav-search-->
        </div><!--#breadcrumbs-->

        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <div class="row-fluid">
                    <!-- 检索  -->
                    <form action="${pageContext.request.contextPath}/order/order/findList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="userForm" id="userForm">
                        <div class="page-header position-relative">
                            <table>
                                <tr>
                                    <td><font color="#808080">设备号：</font></td>
                                    <td><input type="text" name="customer_sn"  id="customer_sn" value="${pd.customer_sn }" placeholder="这里输入设备号" style="width:130px;"/></td>
                                    <td><font color="#808080">订单号：</font></td>
                                    <td><input type="text" name="order_no" id="order_no" value="${pd.order_no }" placeholder="这里输入用户名称" style="width:130px;"/></td>
                                    <td><font color="#808080">订单日期：</font></td>
                                    <td>
                                        <input type="text" name="start_time" id="start_time" value="${pd.start_time }" placeholder="开始日期" style="width:80px;"/>
                                        -
                                        <input type="text" name="end_time" id="end_time" value="${pd.end_time }" placeholder="结束日期" style="width:80px;"/>
                                    </td>
                                    <td><font color="#808080">订单状态：</font></td>
                                    <td>
                                        <select id="status" name="status" style="width:80px;">
                                            <option value="">全部</option>
                                            <option value="1" <c:if test="${pd.status=='1'}">selected</c:if>>已下单</option>
                                            <option value="2" <c:if test="${pd.status=='2'}">selected</c:if>>已受理</option>
                                            <option value="3" <c:if test="${pd.status=='3'}">selected</c:if>>已开工</option>
                                            <option value="4" <c:if test="${pd.status=='4'}">selected</c:if>>已完成</option>
                                            <option value="5" <c:if test="${pd.status=='5'}">selected</c:if>>已确认</option>
                                            <option value="6" <c:if test="${pd.status=='6'}">selected</c:if>>已评价</option>
                                            <option value="7" <c:if test="${pd.status=='7'}">selected</c:if>>客户毁单</option>
                                            <option value="0" <c:if test="${pd.status=='0'}">selected</c:if>>已取消</option>
                                            </opt<option value="9" <c:if test="${pd.status=='9'}">selected</c:if>>已删除ion>
                                            <option value="11" <c:if test="${pd.status=='11'}">selected</c:if>>指定单</option>
                                            <option value="-1" <c:if test="${pd.status=='-1'}">selected</c:if>>超时关闭</option>
                                        </select>
                                    <td style="vertical-align:top;"><submit class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></submit></td>
                                    <td style="vertical-align:top;"> <submit class="btn btn-mini btn-light" onclick="reset();"><i id="nav-search-icon" class="icon-reply" title="重置"></i></submit></td>
                                </tr>
                            </table>
                        </div>
                        <!-- 检索  -->
                        <div class="widget-box" style="">
                            <div class="widget-header">
                                <h4>订单信息</h4>
                                <div id="tools_part" style="position: absolute; top: 5px; right: 400px; display: none;">
                                    <a style="cursor: pointer;" onclick="showTools('part');"><img src="${pageContext.request.contextPath}/resources/images/right.png"/></a>
                                </div>
                                <div id="export" style="display: block">
                                    <font style="font-size: 8px;color: red">* 双击查看详情</font>
                                </div>
                                <%--<div id="tools_all" style="position: absolute; top: 5px; right: 180px;">--%>
                                    <%--<a style="cursor: pointer;" onclick="showTools('all');"><img src="${pageContext.request.contextPath}/resources/images/left.png"/></a>--%>
                                <%--</div>--%>
                                <%--<div id="export" style="display: block">--%>
                                    <%--<a class="dc-icon5" data-type="xls" href="javascript:;"></a>--%>
                                    <%--<a class="dc-icon6" data-type="doc" href="javascript:;"></a>--%>
                                    <%--<span style="display: none; width: 290px;" id="tools_span">--%>
                                        <%--<a class="dc-icon8" data-type="pdf" href="javascript:;"></a>--%>
                                        <%--<a class="dc-icon2" style="cursor: pointer;" data-type="txt" onclick="dc('txt');"></a>--%>
                                        <%--<a class="dc-icon7" data-type="image" href="javascript:;"></a>--%>
                                        <%--<a class="dc-icon1" style="cursor: pointer;" data-type="json" onclick="dc('json');"></a>--%>
                                        <%--<a class="dc-icon3" data-type="csv" href="javascript:;"></a>--%>
                                        <%--<a class="dc-icon4" data-type="xml" href="javascript:;"></a>--%>
                                    <%--</span>--%>
                                <%--</div>--%>
                                <%--<a class="dc-icon9" style="cursor: pointer; position:absolute; right:70px;top:6px;" onclick="print();"></a>--%>
                                <span class="widget-toolbar">
                                    <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                                </span>
                            </div>
                            <div class="widget-body">
                                <div  id="table_print" style="border: 1px white solid;">
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>
                                            <th>订单编号</th>
                                            <th>订单来源</th>
                                            <th>订单类型</th>
                                            <th>客户姓名</th>
                                            <th>客户电话</th>
                                            <th>客户地址</th>
                                            <th>订单状态</th>
                                            <th>预约时间</th>
                                            <th id="exclude_1" class="center" style="width:55px;">操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty userList}">
                                                <c:forEach items="${userList}" var="var" varStatus="vs">
                                                    <tr ondblclick="findInfo('${var.id }')">
                                                        <td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
                                                        <td>${var.order_no }</td>
                                                        <td>
                                                            <c:if test="${var.order_source == 'partners'}">家人在商城下单</c:if>
                                                            <c:if test="${var.order_source == 'family'}">家人快速下单</c:if>
                                                            <c:if test="${var.order_source == 'telemarketer'}">客服下单</c:if>
                                                        </td>
                                                        <td>${var.server_category_name }</td>
                                                        <td>${var.customer_name }</td>
                                                        <td>${var.customer_phone}</td>
                                                        <td>${var.customer_address}</td>
                                                        <td>
                                                            <c:if test="${var.status == '-1'}"><span style="color: orangered;">超时关闭</span></c:if>
                                                            <c:if test="${var.status == '0'}">已取消</c:if>
                                                            <c:if test="${var.status == '1'}">已下单</c:if>
                                                            <c:if test="${var.status == '2'}">已受理</c:if>
                                                            <c:if test="${var.status == '3'}">已开工</c:if>
                                                            <c:if test="${var.status == '4'}">已完成</c:if>
                                                            <c:if test="${var.status == '5'}">已确认</c:if>
                                                            <c:if test="${var.status == '6'}">已评价</c:if>
                                                            <c:if test="${var.status == '7'}">客户毁单</c:if>
                                                            <c:if test="${var.status == '9'}"><span style="color: red;">已删除</span></c:if>
                                                            <c:if test="${var.status == '11'}"><span style="color: blue;">指定单</span></c:if>
                                                        </td>
                                                        <td><fmt:formatDate value="${var.appointment_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td id="exclude_1_${var.id }">
                                                            <c:if test="${fn:contains(power,'order_cancel')&& var.status == '1'}">
                                                                <a class='btn btn-mini btn-warning' title="取消订单" onclick="cancelOrder('${var.id }','${var.order_no}');"><i class='icon-reply'></i></a>
                                                            </c:if>
                                                            <c:if test="${fn:contains(power,'order_cancel')&& (var.status == '3'|| var.status == '2')}">
                                                                <a class='btn btn-mini btn-warning' title="客户毁单" onclick="cancelOrderByCustomer('${var.id }','${var.order_no}');"><i class='icon-reply'></i></a>
                                                            </c:if>
                                                            <c:if test="${fn:contains(power,'order_cancel')&& (var.status == '0'||var.status == '9'||var.status == '-1')}">
                                                                <a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.id }','${var.order_no}');"><i class='icon-trash'></i></a>
                                                            </c:if>
                                                        </td>
                                                    </tr>

                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr class="main_info">
                                                    <td colspan="10" class="center">没有相关数据</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="page-header position-relative">
                                    <table style="width:100%;">
                                        <tr>
                                            <!--										<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="add();">新增</a></td>-->
                                            <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- PAGE CONTENT ENDS HERE -->
            </div><!--/row-->

        </div><!--/#page-content-->

        <!--/#ace-settings-container-->
    </div><!-- #main-content -->
    <div id="ace-settings-container">
        <div class="btn btn-app btn-mini btn-warning" id="ace-settings-btn">
            <i class="icon-cog"></i>
        </div>
        <div id="ace-settings-box">
            <div>
                <div class="pull-left">
                    <select id="skin-colorpicker" class="hidden">
                        <option data-class="default" value="#438EB9">#438EB9</option>
                        <option data-class="skin-1" value="#222A2D">#222A2D</option>
                        <option data-class="skin-2" value="#C6487E">#C6487E</option>
                        <option data-class="skin-3" value="#D0D0D0">#D0D0D0</option>
                    </select>
                </div>
                <span>&nbsp; 选择皮肤</span>
            </div>
            <div><input type="checkbox" class="ace-checkbox-2" id="ace-settings-header" /><label class="lbl" for="ace-settings-header"> 固定头</label></div>
            <div><input type="checkbox" class="ace-checkbox-2" id="ace-settings-sidebar" /><label class="lbl" for="ace-settings-sidebar"> 固定侧边栏</label></div>
        </div>
    </div>
</div><!--/.fluid-container#main-container-->
<script type="text/javascript" src="${pageContext.request.contextPath}/pages/base/js/jquery.jedate.js"></script><!-- 日期框 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/pages/base/js/skin/jedate.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/pages/base/js/common.js"></script>
<%@include file="../../system/admin/bottom.jsp" %>
<script type="text/javascript">
    //初始化时间控件
    var startDate = {
        format: 'YYYY-MM-DD',
//        minDate: $.nowDate(0), //设定最小日期为当前日期
//        isinitVal:true,
        festival:false,
        ishmsVal:false,
        maxDate: getNowFormatDate(), //最大日期
        choosefun: function(elem,datas){
            endDate.minDate = datas; //开始日选好后，重置结束日的最小日期
        }
    };
    var endDate = {
        format: 'YYYY-MM-DD',
//        minDate: $.nowDate(0), //设定最小日期为当前日期
//        isinitVal:true,
        festival:false,
        maxDate: getNowFormatDate(), //最大日期
        choosefun: function(elem,datas){
            startDate.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
        }
    };
    $('#start_time').jeDate(startDate);
    $('#end_time').jeDate(endDate);
    //check选择
    function myCheckAll(){
        var c = $("#check").prop("checked");
        if(c){
            $("input:checkbox[id^='check_']").each(function(){
                $(this).prop("checked",true);
            });
        }else{
            $("input:checkbox[id^='check_']").each(function(){
                $(this).prop("checked",false);
            });
        }
    }

    function myCheck(id){
        var c = $("#check_"+id).prop("checked");
        if(c){
            $("#check").prop("checked",true);
        }else{
            var flag = true;
            $("input:checkbox[id^='check_']").each(function(){
                var cc = $(this).prop("checked");
                if(cc){
                    flag=false;
                }
            });
            if(flag){
                $("#check").prop("checked",false);
            }
        }
    }
    //检索
    function search(){
        var startDate = $('#start_time').val();
        var endDate = $('#end_time').val();
        if(startDate==""&& endDate!=""){
            alert("请输入开始日期");
            return false;
        }else if(startDate!=""&& endDate==""){
            alert("请输入结束日期");
            return false;
        }else{
            $("#userForm").submit();
        }
    }
    function reset(){
        $('#customer_sn').val("");
        $('#order_no').val("");
        $('#status').val("");
        $('#start_time').val("");
        $('#end_time').val("");
        $("#userForm").submit();
    }
    //子页面成功回调方法
    function saveSuccess(){
        var num = '${page.currentPage}';
        if(num == '0'){
            location.href = location.href;
        }else{
            nextPage(${page.currentPage});
        }
    }
    //取消订单
    function cancelOrder(id,orderNo){
        bootbox.confirm("确定要取消该数据?", function(result) {
            if(result) {
                var url = "${pageContext.request.contextPath}/order/order/cancelOrder.do?status=0&&id="+id+"&&order_no="+orderNo;
                $.get(url,function(data){
                    if(data){
                        saveSuccess();
                    }
                });
            }
        });
    }
    //取消订单
    function cancelOrderByCustomer(id,orderNo){
        bootbox.confirm("确定要取消该数据?", function(result) {
            if(result) {
                var url = "${pageContext.request.contextPath}/order/order/cancelOrderByCustomer.do?status=7&&id="+id+"&&order_no="+orderNo;
                $.get(url,function(data){
                    if(data){
                        saveSuccess();
                    }
                });
            }
        });
    }
    //删除订单
    function del(id,orderNo){
        bootbox.confirm("确定要删除此订单?", function(result) {
            if(result) {
                var url = "${pageContext.request.contextPath}/order/order/del.do?id="+id+"&&order_no="+orderNo;
                $.get(url,function(data){
                    if(data){
                        saveSuccess();
                    }
                });
            }
        });
    }
    //获取订单详情
    function findInfo(id){
        $("input:checkbox[id^='check_']").each(function(){
            var cc = $(this).prop("checked",false);
        });
        $("#check").prop("checked",false);
        $("#check_"+id).prop("checked",true);
        var data = $('input:checkbox[id^=check_]:checked');
        if(data.length==1){
            art.dialog.open('${pageContext.request.contextPath}/order/order/findInfo.do?id='+data.val(),{
                title:'订单详情',
                width:650,
                height:400,
                lock: true,
                cancelVal:'关闭',
                cancel:function(){
                }
            });//打开子窗体
        }else{
            art.dialog.alert("请选择一条要查看的数据。");
        }
    }
</script>
</body>
</html>