<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	<!-- 地图 -->
<!-- <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=LwC25kOs7PAF3CHcC4EHQECc"></script> -->
<!-- <script type="text/javascript" src="http://api.map.baidu.com/library/RectangleZoom/1.2/src/RectangleZoom_min.js"></script> -->
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BD452558f66e838888114b2b27da7628"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
    <script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
    
    
    	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
    
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css">
    <link href="${pageContext.request.contextPath}/pages/base/map/ui/uikit.gradient.css" rel="stylesheet" />
	<style type="text/css">
        body, html, #l-map {
            width: 100%;
            height: 100%;
            overflow: hidden;
            margin: 0;
        }
        .input-group {
            position: relative;
            width: 100%;
            display: -webkit-box;
            -webkit-box-pack: start;
            height:38px;
        }
        .input-text.-brand {
            border-color: #6bb1f7;
        }
        .input-text {
            font-size: 14px;
            border: #d4d4d4 solid 1px;
            position: relative;
        }
        .ui-suggestion-mask {
            position: relative;
            z-index: 999;
            display: -webkit-box;
        }
        .btn.-brand {
            color: #fff;
            background-color: #6bb1f7;
            border-color: #6bb1f7;
            height:38px;
            width:51px;
        }
        .input-text input[type=text] {
            width: 100%;
            padding: 8px;
        }
        input[type=text], select {
            -webkit-border-radius: 0;
            -webkit-appearance: none;
            border: 0;
        }
        .card, .container {
            display: block;
            width: 100%;
            padding: 10px;
            overflow: hidden;
        }
        .-shadow-card {
            -webkit-box-shadow: 0 2px 3px -1px rgba(0,0,0,.2)!important;
        }
        .index-widget-searchbox {
            position: relative;
            z-index: 10;
        }
        .card.-lighter, .container.-lighter {
            background-color: #fff;
            color: #3b3b3b;
        }
        .styleguide, .styleguide * {
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
        }
        .input-group .btn, .input-group .input-text {
            height: 38px;
        }

        .input-group .input-text {
            -webkit-box-flex: 1;
        }

        .input-text.-brand {
            border-color: #6bb1f7;
        }

        .input-text {
            font-size: 14px;
            border: #d4d4d4 solid 1px;
            position: relative;
        }

        .styleguide, .styleguide * {
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
        }

        * {
            -webkit-tap-highlight-color: rgba(0,0,0,0);
        }
        .input-text input[type=text] {
            width: 100%;
            padding: 8px;
        }

        .ui-suggestion-mask input {
            -webkit-box-flex: 1;
            display: block;
        }

        input[type=text], select {
            -webkit-border-radius: 0;
            -webkit-appearance: none;
            border: 0;
        }

        .styleguide, .styleguide * {
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
        }

        button, input, optgroup, select, textarea {
            color: inherit;
            font: inherit;
            margin: 0;
        }

        a, button, input {
            outline: 0;
        }

        * {
            -webkit-tap-highlight-color: rgba(0,0,0,0);
        }
        .input-group .btn:last-child {
            border-left: 0;
        }

        .input-group .btn, .input-group .input-text {
            height: 38px;
        }

        .btn.-brand {
            color: #fff;
            background-color: #6bb1f7;
            border-color: #6bb1f7;
        }

        .btn, .btn.-large {
            height: 44px;
            font-size: 15px;
        }

        .btn {
            border: #d4d4d4 solid 1px;
            padding: 0 10px;
            color: inherit;
            background-color: inherit;
        }

        .styleguide, .styleguide * {
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
        }

        button, html input[type=button], input[type=reset], input[type=submit] {
            -webkit-appearance: button;
        }

        button, input, optgroup, select, textarea {
            color: inherit;
            font: inherit;
            margin: 0;
        }
    </style>

	<div style="margin-top:2px;margin-left:10px;height:20px">
    	 经度：<input id="add_jd"  type="text">
     	纬度：<input id="add_wd"  type="text">
    </div>
    <input type="hidden" id="hilat" /><input type="hidden" id="hilng" style="margin:0px" />
    <div class="container -shadow-card index-widget-searchbox styleguide -lighter">
        <div class="input-group ">
            <div class="input-text -brand">
                <div class="ui-suggestion-mask">
                    <input name="wd" type="text" class="se-input-poi" id="se-input-poi" placeholder="根据位置搜索周边网点" autocomplete="off">
                </div>
            </div>
            <button type="submit" class="btn -brand" id="se-btn" style="width:60px" onclick="query();">搜索</button>
        </div>
    </div>
    <div id="l-map"></div>
    
	<script type="text/javascript">
    var watchID = null;
    var map = new BMap.Map("l-map");
    var point2, meg;
    var objId = '<%=request.getParameter("index")%>';
    jQuery(function ($) {
    	map.centerAndZoom(new BMap.Point(117.02496707, 36.68278473), 12);
        
        map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
    	map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
    	map.addEventListener("click",function(e){
    		$("#add_jd").val(e.point.lng);
    		$("#add_wd").val(e.point.lat);
    		//window.parent.saveSuccess(e.point.lng,e.point.lat);
    		art.dialog.opener.saveSuccess(e.point.lng,e.point.lat,objId);
    		map.clearOverlays();
    		// 百度地图API功能
//     		map.centerAndZoom(e.point, 12);
    		var label = new BMap.Label("定位",{offset:new BMap.Size(20,-10)});
    		var marker = new BMap.Marker(e.point);// 创建标注
    		map.addOverlay(marker);             // 将标注添加到地图中
    		marker.setLabel(label);
    		marker.disableDragging();           // 不可拖拽
    	});
    	
        //向地图中添加缩放控件
        var ctrl_nav = new BMap.NavigationControl({ anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_LARGE });
        map.addControl(ctrl_nav);

        var agent = window.navigator.userAgent;
        if (agent.indexOf("Android") >= 0 || agent.indexOf('Linux') >= 0) {

            loadallwd(point2);
        } else {
            var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                    point2 = r.point;
                    loadallwd(point2);
                    var mk = new BMap.Marker(r.point);
                    map.addOverlay(mk);
                    map.panTo(r.point);

                    var circle1 = new BMap.Circle(r.point, 500, { fillColor: "blue", strokeWeight: 1, fillOpacity: 0.3, strokeOpacity: 0.3 });
                    map.addOverlay(circle1);
                }
                else {
                    var status = this.getStatus();

                    switch (status) {
                        case 1:
                            meg = "城市列表";
                            break;
                        case 2:
                            meg = "位置结果未知";
                            break;
                        case 3:
                            meg = "导航结果未知";
                            break;
                        case 4:
                            meg = "非法密钥";
                            break;
                        case 5:
                            meg = "非法请求";
                            break;
                        case 6:
                            meg = "没有权限";
                            break;
                        case 7:
                            meg = "定位服务不可用，请打开手机定位功能";
                            break;
                        case 8:
                            meg = "超时";
                            break;
                    }
                    loadallwd(point2);
                    if (!meg) {
                        alert("请打开手机定位功能");
                    } else {
                        alert(meg);
                    }

                }
            }, { enableHighAccuracy: false })
        }
        
        //var options = { enableHighAccuracy: true, frequency: 0 };
        //watchID = navigator.geolocation.watchPosition(onGpsok, onGpsError, options);
        
    });

    function onGpsok(position) {
        //$("#gps").html( position.coords.latitude+','+position.coords.longitude) ;
        var gpsPoint = new BMap.Point(position.coords.longitude, position.coords.latitude);
        var myGeo = new BMap.Geocoder();
        BMap.Convertor.translate(gpsPoint, 0, function (point) {

            map.centerAndZoom(point, 16);
            map.addOverlay(new BMap.Marker(point));
            var circle1 = new BMap.Circle(point, 500, { fillColor: "blue", strokeWeight: 1, fillOpacity: 0.3, strokeOpacity: 0.3 });
            map.addOverlay(circle1);
        });
    }
    function onGpsError(err) {
        alert("请打开手机定位功能");
        console.log(err.code + err.message);
    }
    // 清除前述已经开始的监视  
    function clearWatch() {
        if (watchID != null) {
            navigator.geolocation.clearWatch(watchID);
            watchID = null;
        }
    }

    function loadwd(curlng, curlat) {
        var rpoint = new BMap.Point(curlng, curlat);
        var mk = new BMap.Marker(rpoint);
        map.addOverlay(mk);
        map.panTo(rpoint);
    }

      function query() {
        map.clearOverlays();
        window.openInfoWinFuns = null;
        var options = {
            onSearchComplete: function (results) {
                // 判断状态是否正确
                if (local.getStatus() == BMAP_STATUS_SUCCESS) {
                    var s = [];
                    s.push('<div style="font-family: arial,sans-serif; border: 1px solid rgb(153, 153, 153); font-size: 12px;">');
                    s.push('<div style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">');
                    s.push('<ol style="list-style: none outside none; padding: 0pt; margin: 0pt;">');

                    var ddcount = results.getCurrentNumPois();
                    if (ddcount) {
                        var curlng = results.getPoi(0).point.lng;
                        var curlat = results.getPoi(0).point.lat;
                        //loadwd(curlng, curlat);

                        var circle1 = new BMap.Circle(results.getPoi(0).point, 500, { fillColor: "blue", strokeWeight: 1, fillOpacity: 0.3, strokeOpacity: 0.3 });
                        map.addOverlay(circle1);
                        map.panTo(results.getPoi(0).point);

                        var marker = addMarker(results.getPoi(0).point, 0);
                        var openInfoWinFun = addInfoWindow(marker, results.getPoi(0), 0);
                    }
                    openInfoWinFuns = [];
                    //for (var i = 0; i < results.getCurrentNumPois() ; i++) {
                    //    var marker = addMarker(results.getPoi(0).point, 0);
                        
                    //    var openInfoWinFun = addInfoWindow(marker, results.getPoi(0), 0);
                    //    openInfoWinFuns.push(openInfoWinFun);
                    //    // 默认打开第一标注的信息窗口
                    //    if (i == 0) {
                    //        openInfoWinFun();

                    //    }
                    //}
                }
            }
        };

        var local = new BMap.LocalSearch(map, options);
        var searchValue = $("#se-input-poi").val();
        if (searchValue) {
            local.search(searchValue);
        }
    }

</script>
