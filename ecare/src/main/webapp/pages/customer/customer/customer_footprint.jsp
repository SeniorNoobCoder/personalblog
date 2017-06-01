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
	
	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=bb9fdd7a153fab68369a8869dcc48d83&plugin=AMap.Autocomplete,AMap.PlaceSearch"></script>     
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
							<td><font color="#808080">日期足迹：</font></td>
							<td><input type="text" class="timepicker" name="find_time" id="find_time" placeholder="这里选择日期足迹" value="" style="width:130px;"/></td>
							<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search('footprint');">
								<i id="nav-search-icon" class="icon-search" title="足迹"></i></button>
							</td>
							<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search('location');">
								<i id="nav-search-icon" class="icon-map-marker" title="当前位置"></i></button>
							</td>
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
			//检索
		function search(type){
			var lngLats = new  Array();
			var find_time = $("#find_time").val();
			$.ajax({
				url : "${pageContext.request.contextPath}/customer/customer/findFootprint.do?sn=<%=sn%>&find_time="+find_time+"&type="+type,
				data : {},
				type : 'GET',
				dataType : 'json',
				success : function(json) {
					lngLats = json;
					var map = new AMap.Map('container', {
						resizeEnable: true,
						zoom: 13
					});
					map.clearMap();  // 清除地图覆盖物
					// 添加一些分布不均的点到地图上,地图上添加三个点标记，作为参照
					var infoWindow = new AMap.InfoWindow();
					var lineArr = new Array();
					lngLats.forEach(function(lngLat,index) {
						var lnglatXY = new AMap.LngLat(lngLat.lon,lngLat.lat);
						lineArr[index]=[lngLat.lon,lngLat.lat];//划线的数据
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
						if(index == 0){
						marker.setAnimation('AMAP_ANIMATION_BOUNCE'); //设置点标记的动画效果，此处为弹跳效果
						}
					});

//					var lineArr = [
//						[117.1452, 36.65377],
//						[117.02396393, 36.67736828],
//						[117.13013649, 36.672343],
//						[117.13659525, 36.67359935],
//						[117.13635921, 36.66972697]
//					];
					var polyline = new AMap.Polyline({
						path: lineArr,          //设置线覆盖物路径
						strokeColor: "#0091FF", //线颜色
						strokeOpacity: 0.8,       //线透明度
						strokeWeight: 5,        //线宽
						strokeStyle: "solid",   //线样式
						showDir:true,//是否延路径显示白色方向箭头
						strokeDasharray: [10, 5] //补充线样式
					});
					polyline.setMap(map);
					function markerClick(e){
						infoWindow.setContent(e.target.content);
						infoWindow.open(map, e.target.getPosition());
					}
					var newCenter = map.setFitView();
					document.getElementById('centerCoord').innerHTML = '当前中心点坐标：' + newCenter.getCenter();
					document.getElementById('tips').innerHTML = '通过setFitView，地图自适应显示到合适的范围内,点标记已全部显示在视野中！';
				}
			});
		}
		$(function() {
			$(".timepicker").datetimepicker({
				format: 'yyyy-mm-dd',
				weekStart: 1,
				 autoclose: true,
				 startView: 2,
				 minView: 2,
				 forceParse: false,
				startDate: getCurrentDaySub(30),//开始日期
				endDate: getCurrentDay()//结束日期
			});
		});
		search("location");
	</script>
  </body>
</html>