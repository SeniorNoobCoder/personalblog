<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
	</script>
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
		<div id="zhongxin">
			<div id="order" class="order">
				<div id="showTitle">用户基本信息</div>
				<div id="info">
					<div style="float: left;width: 30%">
						<c:if test="${p.head_address == ''}">
						<img src="${pageContext.request.contextPath}/IMAGES/index-title1.jpg" width="80%"/>
						</c:if>
						<c:if test="${p.head_address != ''}">
							<img src="${p.head_address}" width="80%"/>
						</c:if>
					</div>
					<div style="float: left;width: 65%">
						<ul>
							<li>名称：${p.name}&nbsp;性别：${p.sex}&nbsp;生日：${p.birthday}</li>
							<li>家庭住址:${p.address}</li>
							<li>健康状况:${p.health_condition}</li>
						</ul>
					</div>
					<div style=" clear:both"></div>
				</div>
				<c:if test="${p.sn ==null}">
					<div style="font-size:25px;font-weight: bolder;color: #ccd1d9;padding-top: 20px;text-align: center">暂无设备信息</div>
				</c:if>
				<c:if test="${p.sn!=null}">
				<div id="showTitle">设备信息</div>
				<div >
					<ul>
						<li>设备号:${p.sn}&nbsp;&nbsp;
							足迹:&nbsp;<a class='btn btn-mini btn-warning' title="足迹" onclick="findFootprint('${p.sn }');"><i class='icon-map-marker'></i></a>
							&nbsp;&nbsp;安全岛:&nbsp;<a class='btn btn-mini btn-warning' title="安全岛" onclick="findSafety('${p.sn }');"><img src="${pageContext.request.contextPath}/resources/images/safety-1.png" width="20px"/></a>
						</li>
					</ul>
				</div>
				<div id="showTitle">设备联系人</div>
				<div id="orderFlow" class="customer">
					<ul>
						<li id="time" style="">姓名</li><li id="content">电话</li><li id="operator">快捷键</li>
						<c:forEach items="${contactList}" var="var" varStatus="vs">
							<li id="time" style="">${var.name}</li>
							<li id="content">${var.mobile}</li>
							<li id="operator">
								<c:if test="${var.key==null||var.key==''}">无</c:if>
								<c:if test="${var.key=='A'}">1</c:if>
								<c:if test="${var.key=='B'}">2</c:if>
								<c:if test="${var.key=='C'}">3</c:if>
								<c:if test="${var.key=='D'}">4</c:if>
								<c:if test="${var.key=='E'}">客服</c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div id="showTitle">绑定的家人</div>
				<div id="orderFlow" class="customer">
					<ul>
						<li id="time" style="">电话</li><li id="content">住址</li><li id="operator">关系</li>
						<c:forEach items="${famaryList}" var="var" varStatus="vs">
							<li id="time" style="">${var.phone}</li>
							<li id="content">${var.live_address}</li>
							<li id="operator">${var.relationship}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
			</div>
		</div>
		<script type="text/javascript">
			function findFootprint(sn){
				art.dialog.open('${pageContext.request.contextPath}/pages/customer/customer/customer_footprint.jsp?sn='+sn,{
					title:'足迹',
					width:1000,
					height:560,
					lock: true
				});//打开子窗体
			}

			function findSafety(sn){
				art.dialog.open('${pageContext.request.contextPath}/pages/customer/customer/customer_safety.jsp?sn='+sn,{
					title:'安全岛设置',
					width:1000,
					height:560,
					lock: true
				});//打开子窗体
			}
		</script>
	
	<!-- 引入 -->
	<%--<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>--%>
	<%----%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/chosen.jquery.min.js"></script><!-- 下拉框 -->--%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->--%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->--%>
	<%--<!-- 引入 -->--%>
	<%----%>
	<%--<!--引入弹窗组件start-->--%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDrag.js"></script>--%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/attention/zDialog/zDialog.js"></script>--%>
	<%--<!--引入弹窗组件end-->--%>
	<%----%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/jquery.tips.js"></script><!--提示框-->--%>
	<%----%>
	<%----%>
	<%--<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.config.js"></script>--%>
    <%--<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/ueditor.all.min.js"> </script>--%>
    <%--<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->--%>
    <%--<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->--%>
    <%--<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>--%>
	<%--<script type="text/javascript">--%>
		<%--//实例化编辑器--%>
	    <%--//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例--%>
	    <%--UE.getEditor('editor');--%>
	<%--</script>--%>
	<%----%>
	<%--<script type="text/javascript">--%>
			<%--//下拉框--%>
			<%--$(".chzn-select").chosen(); --%>
			<%--$(".chzn-select-deselect").chosen({allow_single_deselect:true}); --%>
			<%--//日期框--%>
			<%--$('.date-picker').datepicker();--%>
		<%--</script>--%>
		
  </body>
</html>