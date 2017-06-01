<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sn = request.getParameter("sn");
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/bootstrap-datetimepicker.min.css" />
	<%--<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=bb9fdd7a153fab68369a8869dcc48d83&plugin=AMap.Autocomplete,AMap.PlaceSearch"></script>--%>
	<%--<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>--%>
	  <%--<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>--%>
	  <script src="http://webapi.amap.com/maps?v=1.3&key=bb9fdd7a153fab68369a8869dcc48d83&plugin=AMap.PolyEditor,AMap.CircleEditor"></script>
	  <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/bootstrap-datetimepicker.min.js"></script><!-- 日期框 -->
	<style>
		.menu-tab ul{margin:0 !important;}
		.menu-tab li{ float:left; list-style-type:none;  text-align:center; height:38px;  line-height: 40px; width:120px; border-right:1px solid #e3e3e3;}
		.menu-xz-tab{ border-top:2px solid #438eb9; background:#fff;}
		.menu-tab li a{    text-decoration: none;}
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
					<table>
						<tr>
							<td><font color="#808080">安全岛名称：</font></td>
							<td><input type="text"  name="name" id="name" placeholder="这里输入安全岛名称" value="" style="width:130px;"/></td>
							<td><font color="#808080">安全岛坐标：</font></td>
							<td><input type="text"  name="lnglat" id="lnglat" disabled value="" style="width:130px;"/></td>
							<td><font color="#808080">安全岛半径：</font></td>
							<td><input type="text"  name="radius" id="radius" disabled value="1000" style="width:80px;"/>米
								<input type="hidden" name="fence_id" id="fence_id" value=""/>
							</td>
							<td style="padding-bottom: 10px">&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-mini btn-primary" onclick="saveSafety();" >保存</a></td>
						</tr>
					</table>
					</div>

					<div id="container" style="width: 100%;height: 440px;"></div>
				</div>
			  </div>
			</div>
		</div>
	</div>

	<!-- 引入 -->
	<script src="${pageContext.request.contextPath}/ui/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/ui/js/ace-elements.min.js"></script>
	<script src="${pageContext.request.contextPath}/ui/js/ace.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
	<script type="text/javascript">
		$("#find_time").val(getCurrentDay());
		var lon;
		var lat;
		//检索
		function search(type){
//			var lngLats = new  Array();
			var find_time = getCurrentDay();
			$.ajax({
				url : "${pageContext.request.contextPath}/customer/customer/getFence.do?sn=<%=sn%>",
				data : {},
				type : 'GET',
				dataType : 'json',
				success : function(json) {
					var lngLat = json;
					document.getElementById("lnglat").value = lngLat.lon + ',' + lngLat.lat;
					var radius = 1000;
					if(json!=null){
						radius = lngLat.radius;
						$("#name").val(lngLat.name);
						$("#radius").val(lngLat.radius);
						$("#fence_id").val(lngLat.fence_id);
					}
					var map = new AMap.Map('container', {
						resizeEnable: true,
						zoom: 13
					});
					map.clearMap();  // 清除地图覆盖物
					// 添加一些分布不均的点到地图上,地图上添加三个点标记，作为参照
					var infoWindow = new AMap.InfoWindow();
					var lineArr = new Array();
//					lngLats.forEach(function(lngLat,index) {
					var lnglatXY = new AMap.LngLat(lngLat.lon,lngLat.lat);
					lineArr=[lngLat.lon,lngLat.lat];//划线的数据
					lon = lngLat.lon;
					lat = lngLat.lat
					var  marker = new AMap.Marker({
						map: map,
						<%--icon: "${pageContext.request.contextPath}/IMAGES/mark_b1.png",--%>
						position: [lngLat.lon,lngLat.lat],
						offset: new AMap.Pixel(-12, -16)
					});
					//加载地理编码插件
					map.plugin(["AMap.Geocoder"], function() {
						MGeocoder = new AMap.Geocoder({
							radius: 1000,
							extensions: "all"
						});
						//逆地理编码
						MGeocoder.getAddress(lnglatXY, function(status, result) {
							if (status === 'complete' && result.info === 'OK') {
								marker.content = lngLat.end_time+"<br>"+result.regeocode.formattedAddress;
								//获得了有效的地址信息:
							}else{
								//获取地址失败
								marker.content = "";
							}
						});
					});
					marker.on('click', markerClick);
					marker.setAnimation('AMAP_ANIMATION_DROP'); //设置点标记的动画效果，此处为弹跳效果
//					});
					//=============在地图上绘制折线 start===============
					document.getElementById("lnglat").value = lineArr;
					//在地图上绘制折线
					var editor={};
					editor._circleEditor=(function(){
						var circle = new AMap.Circle({
							center: lineArr,// 圆心位置
							radius: radius, //半径
							strokeColor: "#F33", //线颜色
							strokeOpacity: 1, //线透明度
							strokeWeight: 3, //线粗细度
							fillColor: "#ee2200", //填充颜色
							fillOpacity: 0.35//填充透明度
						});
						circle.setMap(map);
						return circle;
					})();
//					editor._circle=(function(){
//						var circle = new AMap.Circle({
//							center: lineArr,// 圆心位置
//							radius: 1000, //半径
//							strokeColor: "#0AB752", //线颜色
//							strokeOpacity: 1, //线透明度
//							strokeWeight: 3, //线粗细度
//							fillColor: "#0DCB5D", //填充颜色
//							fillOpacity: 0.35//填充透明度
//						});
//						circle.setMap(map);
//						return circle;
//					})();
					map.setFitView();
					editor._circleEditor= new AMap.CircleEditor(map, editor._circleEditor);
//					editor._circle = new AMap.CircleEditor(map, editor._circle);
					editor._circleEditor.open();
//					editor._circleEditor.move(){
//
//					}
					//===========end=================
					function markerClick(e){
						infoWindow.setContent(e.target.content);
						infoWindow.open(map, e.target.getPosition());
					}
					//===========移动获取圆的中心坐标=============
					editor._circleEditor.on('move', function(e) {
						document.getElementById("lnglat").value = e.lnglat.getLng() + ',' + e.lnglat.getLat();
						lon = e.lnglat.getLng();
						lat = e.lnglat.getLat();
					});
					editor._circleEditor.on('adjust', function(e) {
						document.getElementById("radius").value = e.radius;
					});
//					var newCenter = map.setFitView();
//					alert(newCenter.getCenter());
//					document.getElementById('centerCoord').innerHTML = '当前中心点坐标：' + newCenter.getCenter();
//					document.getElementById('tips').innerHTML = '通过setFitView，地图自适应显示到合适的范围内,点标记已全部显示在视野中！';
				}
			});
		}
		search("location");
		function saveSafety(){
			var name = $("#name").val();
			var radius = $("#radius").val();
			var fence_id = $("#fence_id").val();
			if (name == ""){
				art.dialog.alert("安全岛名称不能为空！");
				return false;
			}
			$.ajax({
				url : "${pageContext.request.contextPath}/customer/customer/setFence.do?sn=<%=sn%>&name="+name+"&lon="+lon+"&lat="+lat+"&radius="+radius+"&fence_id="+fence_id,
				data : {},
				type : 'POST',
				dataType : 'json',
				success : function(json) {
					if(json){
						art.dialog.alert("安全岛设置成功！");
					}else {
						art.dialog.alert("安全岛设置失败！");
					}
				}
			});
		}
	</script>
  </body>
</html>