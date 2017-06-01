<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
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
<body style="background-color: #EFF0F4">
<div id="order" class="order">
    <div id="showTitle">订单概况</div>
    <div >
        <ul>
            <li>订单号:${p.order_no}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态：
            <c:if test="${p.status == '0'}"><span style="color: red">已取消</span></c:if>
            <c:if test="${p.status == '1'}"><span style="color: #00be67">已提交</span></c:if>
            <c:if test="${p.status == '2'}"><span style="color: #0b6cbc">已受理</span></c:if>
            <c:if test="${p.status == '3'}"><span style="color:#1CE30F ">已开工</span></c:if>
            <c:if test="${p.status == '4'}"><span style="color:#1CE30F ">已完成</span></c:if>
            <c:if test="${p.status == '5'}"><span style="color: #E3A70F">已确认</span></c:if>
            <c:if test="${p.status == '6'}"><span style="color: #90C612">已评价</span></c:if>
            <c:if test="${p.status == '7'}"><span style="color: red">客户毁单</span></c:if>
            <c:if test="${p.status == '9'}"><span style="color: #E3A70F">已删除</span></c:if>
            <c:if test="${p.status == '11'}"><span style="color: #E3A70F">指定下单</span></c:if>
            <c:if test="${p.status == '-1'}"><span style="color: #E3A70F">超时关闭</span></c:if>
            </li>
            <c:if test="${p.status != '4'||p.status != '5'}">
            <li>订单类型:${p.server_category_name}</li>
            <li>订单备注:${p.remark}</li>
            </c:if>
            <c:if test="${p.status == '4'||p.status == '5'||p.status == '6'}">
            <li>订单金额:<span style="color: red;font-weight: bolder">${p.amount}元</span></li>
            </c:if>
        </ul>
    </div>
    <c:if test="${p.status == '4'||p.status == '5'||p.status == '6'}">
    <div id="showTitle">工作内容及收费标准</div>
    <div >
        <ul>
            <c:forEach items="${orderJobs}" var="var" varStatus="vs">
                <li>${var.remark}:${var.starting_price}元起，每${var.charge_mode_time}${var.charge_mode_unit}${var.price}元</li>
            </c:forEach>
        </ul>
    </div>
    </c:if>
    <div id="showTitle">客户信息</div>
    <div id="customer" class="customer">
        <ul>
            <li>设备编号：${p.customer_sn}&nbsp;&nbsp;&nbsp;&nbsp;姓名：${p.customer_name}&nbsp;&nbsp;&nbsp;&nbsp;电话：${p.customer_phone}</li>
            <li>地址：${p.customer_address}</li>
        </ul>
    </div>
    <c:if test="${not empty p.server_user_id}">
    <div id="showTitle">商家信息</div>
    <div id="customer" class="customer">
        <ul>
            <li>接单人：${p.serverName}&nbsp;&nbsp;&nbsp;&nbsp;电话：${p.serverPhone}</li>
            <li>所属商家：${p.companyName}&nbsp;&nbsp;&nbsp;&nbsp;商家电话：${p.companyPhone}</li>
            <li>商家地址：${p.companyAddress}</li>
        </ul>
    </div>
    </c:if>
    <div id="showTitle">订单跟踪</div>
    <c:if test="${p.status != '0'&&p.status != '7'&&p.status != '9'&&p.status != '-1'}">
    <div style="width: 100%">
        <img src="${pageContext.request.contextPath}/pages/order/images/${p.status}.jpg" width="99%"/>
    </div>
    </c:if>
    <div id="orderFlow" class="customer">
        <ul>
            <li id="time" style="">处理时间</li><li id="content">订单处理</li><li id="operator">操作人</li>
            <c:forEach items="${orderFlows}" var="var" varStatus="vs">
                <li id="time" style=""><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
                <li id="content">
                        <c:choose>
                            <c:when test="${var.status == '0'}">
                               您的订单已取消
                            </c:when>
                            <c:when test="${var.status == '1'}">
                                您的订单已提交
                            </c:when>
                            <c:when test="${var.status == '2'}">
                                您的订单已受理
                            </c:when>
                            <c:when test="${var.status == '3'}">
                                您的订单已开工
                            </c:when>
                            <c:when test="${var.status == '4'}">
                                您的订单已完成
                            </c:when>
                            <c:when test="${var.status == '5'}">
                                您的订单已确认
                            </c:when>
                            <c:when test="${var.status == '6'}">
                               您已经对本次服务做出了评价，服务已完成
                            </c:when>
                            <c:when test="${var.status == '7'}">
                                您已取消了本次服务，
                            </c:when>
                            <c:otherwise>
                                您的订单已删除
                            </c:otherwise>
                        </c:choose>
                </li>
                <li id="operator">${var.operator_name}</li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>