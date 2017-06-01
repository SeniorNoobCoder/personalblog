<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String sn = request.getParameter("sn");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/bootstrap-datetimepicker.min.css" />--%>
	<%--<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=bb9fdd7a153fab68369a8869dcc48d83&plugin=AMap.Autocomplete,AMap.PlaceSearch"></script>--%>
	<%--<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>--%>
	  <%--<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>--%>
	  <%--<script src="http://webapi.amap.com/maps?v=1.3&key=bb9fdd7a153fab68369a8869dcc48d83&plugin=AMap.PolyEditor,AMap.CircleEditor"></script>--%>
	  <%--<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>--%>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datetimepicker.min.js"></script><!-- 日期框 -->
	<style>
		.menu-tab ul{margin:0 !important;}
		.menu-tab li{ float:left; list-style-type:none;  text-align:center; height:38px;  line-height: 40px; width:120px; border-right:1px solid #e3e3e3;}
		.menu-xz-tab{ border-top:2px solid #438eb9; background:#fff;}
		.menu-tab li a{    text-decoration: none;}
		#showTitle{
		  width: 100%;text-align: left;margin: 10px 0 10px 10px;font-size: 14px; color: #2a91d8;width: 90%;
		}
		#order{text-align: left;margin: 0 0 0 0px}
		#order ul{list-style-type:none}
		#order ul li{list-style-type:none;min-height: 30px;line-height: 30px;border-bottom: 1px dashed #CACACA;}
		#orderFlow{text-align: left}
		#time{width: 30%;float: left;}
		#content{width: 30%;float: left;}
		#operator{width: 20%;float: left;}
	  </style>
  </head>
  <body>
  	<div class="widget-box" style="border-bottom:none;">
		<!-- 菜单start -->
		<div class="widget-body" id="footprint_map_div">
			<div id="page-content" class="clearfix">
			  <div class="row-fluid">
				<div class="row-fluid">
					<div class="page-header position-relative" style="padding-bottom: 0px; margin: 0px;">
						<div style="text-align: right;padding-bottom: 10px">
							<a class="btn btn-mini btn-primary" onclick="add();">添加</a>
						</div>
					</div>
					<%--<div id="container" style="width: 100%;height: 300px;">--%>
					<div id="order" class="order" style="width: 100%;height:150px;">
						<div id="orderFlow" class="customer" >
							<ul>
								<li id="time" style="">联系人名称</li><li id="content">电话</li><li id="operator">快捷键</li><li id="operator">操作</li>
								<c:forEach items="${list}" var="var" varStatus="vs">
								<li id="time" style="">${var.name}</li><li id="content">${var.mobile}</li>
								<li id="operator">
									<c:if test="${var.key==null||var.key==''}">无</c:if>
									<c:if test="${var.key=='A'}">1</c:if>
									<c:if test="${var.key=='B'}">2</c:if>
									<c:if test="${var.key=='C'}">3</c:if>
									<c:if test="${var.key=='D'}">4</c:if>
									<c:if test="${var.key=='E'}">客服</c:if>
								</li>
								<li id="operator">
								<a class='btn btn-mini btn-success' title="编辑" onclick="edit('${var.cid}','${var.name}','${var.key}','${var.mobile}');"><i class='icon-edit'></i></a>
								<a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.cid }','${var.status}');"><i class='icon-trash'></i></a>
								</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			  </div>
			</div>
		</div>
	</div>

	<!-- 引入 -->
	<%--<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>--%>
	<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->
	<%--<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/resources/js/common.js"></script--%>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootbox.min.js"></script><!-- 确认窗口 -->--%>
	<script type="text/javascript">
		function saveSuccess(){
			location.href = location.href;
		}
//		$("#find_time").val(getCurrentDay());
		var lon;
		var lat;
		//检索
		function search(){
			var lngLats = new  Array();
			$.ajax({
				url : "${pageContext.request.contextPath}/customer/customer/getContacts.do?sn=${pd.sn}",
				data : {},
				type : 'GET',
				dataType : 'json',
				success : function(json) {
					lngLats = json;
					lngLats.forEach(function(lngLat,index) {
						lineArr=[lngLat.lon,lngLat.lat];//划线的数据
					});
				}
			});
		}
//		search();
		function add(){
			art.dialog.open('${pageContext.request.contextPath}/customer/customer/toContactsAdd.do?sn=${pd.sn}',{
				title:'添加联系人',
				width:500,
				height:180,
				lock: true,
				cancelVal: '关闭',
				cancel: function () {
				}
			});//打开子窗体
		}
		function edit(cid,name,key,mobile){
			art.dialog.open('${pageContext.request.contextPath}/customer/customer/toContactsUpdate.do?sn=${pd.sn}&cid='+cid+'&name='+name+'&key='+key+'&mobile='+mobile,{
				title:'添加联系人',
				width:500,
				height:180,
				lock: true,
				cancelVal: '关闭',
				cancel: function () {
				}
			});//打开子窗体
		}
		function  del(cid){
			bootbox.confirm("确定要删除选中的数据?", function(result) {
				if(result) {
					var url = "${pageContext.request.contextPath}/customer/customer/delContacts.do?sn=${pd.sn}&cid="+cid;
					$.get(url,function(data){
						if(data){
							saveSuccess();
						}
					});
				}
			});
		}
	</script>
  </body>
</html>