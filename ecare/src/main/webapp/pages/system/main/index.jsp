<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="../admin/top.jsp" %>
	<style>
		.chengshi{background:transparent; border:1px solid #fff;color:#fff; width:80px;}
		.chengshi option{ background:#fff; color:#000;}
	</style>
		<div class="container-fluid" id="main-container">
			<%@include file="../admin/left.jsp" %>
			<div id="main-content" class="clearfix">
				<div id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home"></i> <a href="#">首页</a><span class="divider"><i class="icon-angle-right"></i></span></li>
					</ul><!--.breadcrumb-->
					<!--<div id="nav-search">
						<form class="form-search">
								<span class="input-icon">
									<input autocomplete="off" id="nav-search-input" type="text" class="input-small search-query" placeholder="搜索" />
									<i id="nav-search-icon" class="icon-search"></i>
								</span>
						</form>
					</div>#nav-search-->
				</div><!--#breadcrumbs-->
				<div id="page-content" class="clearfix">
				<%--<div class="row-fluid">--%>
			<%--<!-- PAGE CONTENT BEGINS HERE -->--%>
					<%--<div class="alert alert-block alert-success">--%>
					 <%--<button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>--%>
					 <%--<i class="icon-ok green"></i> 开发中……--%>
					<%--</div>--%>
				<%--</div><!-- #main-content -->--%>
			<div class="main">
				<div style="float: left;width: 50%;">
					<div class="widget-box" style="border-bottom:0px">
						<div class="widget-header">
							<h4>通知消息</h4>
						</div>
						<%--<form action="${pageContext.request.contextPath}/system/main/index.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="userForm" id="userForm">--%>
						<div class="widget-body">
							<table id="table_report" class="table table-striped table-bordered table-hover" style="min-height: 158px">
								<%--<thead>--%>
								<%--<tr>--%>
									<%--<th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>--%>
									<%--<th>标题</th>--%>
									<%--&lt;%&ndash;<th>状态</th>&ndash;%&gt;--%>
									<%--<th>发布时间</th>--%>
								<%--</tr>--%>
								<%--</thead>--%>
								<tbody>
								<c:choose>
									<c:when test="${not empty messageList}">
										<c:forEach items="${messageList}" var="var" varStatus="vs">
											<tr  ondblclick="dbFindInfo('${var.id }','${var.message_id }')">
												<td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
												<td>${var.title }</td>
												<%--<td>--%>
													<%--<c:if test="${var.status=='Y'}"><font color="blue">已读</font></c:if>--%>
													<%--<c:if test="${var.status=='N'}"><font color="#808080">未读</font></c:if>--%>
												<%--</td>--%>
												<td><fmt:formatDate value="${var.update_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="10" class="center">没有未读通知</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>
						</div>
						<%--<div class="page-header position-relative" style="height: 30px;">--%>
							<%--<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>--%>
						<%--</div>--%>
					<%--</form>--%>
					</div>
				</div>
				<div style="float: right;width: 50%;">
					<div class="info-news">
						<div class="info-news-blue" style="width: 46%">
							<div class="infobox-top">
								<div class="infobox-top-left1"></div>
								<div class="infobox-top-right" align="right">
									<p><a href="">${orderNum}单</a></p>
									<h1>当月订单</h1>
								</div>
							</div>
							<div class="infobox-bot infobox-bot-blue">
								<div class="fl">VIEW MORE</div>
								<div class="fr">
									<a href="${pageContext.request.contextPath}/order/order/toAdd.do?am=318&bm=415"><img src="${pageContext.request.contextPath}/ui/img/dj.png" /></a>
								</div>
							</div>
						</div>
						<div class="info-news-green" style="width: 46%">
							<div class="infobox-top">
								<div class="infobox-top-left2"></div>
								<div class="infobox-top-right" align="right">
									<p><a href="">${visitingNum}条</a></p>
									<h1>当月回访</h1>
								</div>
							</div>
							<div class="infobox-bot infobox-bot-green">
								<div class="fl">VIEW MORE</div>
								<div class="fr">
									<a href="${pageContext.request.contextPath}/visiting/visiting/findMyList.do?am=434&bm=436"><img src="${pageContext.request.contextPath}/ui/img/dj.png" /></a>
								</div>
							</div>
						</div>
						<div class="info-news-pink" style="width: 46%;margin-top: 20px">
							<div class="infobox-top">
								<div class="infobox-top-left3"></div>
								<div class="infobox-top-right" align="right">
									<p><a href="">${customerNum}个</a></p>
									<h1>设备用户</h1>
								</div>
							</div>
							<div class="infobox-bot infobox-bot-pink">
								<div class="fl">VIEW MORE</div>
								<div class="fr">
									<a href="${pageContext.request.contextPath}/customer/customer/findList.do?am=340&bm=341"><img src="${pageContext.request.contextPath}/ui/img/dj.png" /></a>
								</div>
							</div>
						</div>
						<div class="info-news-red" style="width: 46%;margin-top: 20px">
							<div class="infobox-top">
								<div class="infobox-top-left4"></div>
								<div class="infobox-top-right" align="right">
									<p><a href="">${partnersInfoNum}家</a></p>
									<h1>合作商家</h1>
								</div>
							</div>
							<div class="infobox-bot infobox-bot-red">
								<div class="fl">VIEW MORE</div>
								<div class="fr">
									<a href="${pageContext.request.contextPath}/partners/partners/findList.do?am=317&bm=339"><img src="${pageContext.request.contextPath}/ui/img/dj.png" /></a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

	</div><!--/.fluid-container#main-container-->
</div>
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
</div>
		<%@include file="../admin/bottom.jsp" %>
		<script type="text/javascript">
$(function() {
	$('.dialogs,.comments').slimScroll({
        height: '300px'
    });
	$('#tasks').sortable();
	$('#tasks').disableSelection();
	$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
		if(this.checked) $(this).closest('li').addClass('selected');
		else $(this).closest('li').removeClass('selected');
	});
	var oldie = $.browser.msie && $.browser.version < 9;
	$('.easy-pie-chart.percentage').each(function(){
		var $box = $(this).closest('.infobox');
		var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
		var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
		var size = parseInt($(this).data('size')) || 50;
		$(this).easyPieChart({
			barColor: barColor,
			trackColor: trackColor,
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: parseInt(size/10),
			animate: oldie ? false : 1000,
			size: size
		});
	})
	$('.sparkline').each(function(){
		var $box = $(this).closest('.infobox');
		var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
		$(this).sparkline('html', {tagValuesAttribute:'data-values', type: 'bar', barColor: barColor , chartRangeMin:$(this).data('min') || 0} );
	});
	
	
  var data = [
	{ label: "social networks",  data: 38.7, color: "#68BC31"},
	{ label: "search engines",  data: 24.5, color: "#2091CF"},
	{ label: "ad campaings",  data: 8.2, color: "#AF4E96"},
	{ label: "direct traffic",  data: 18.6, color: "#DA5430"},
	{ label: "other",  data: 10, color: "#FEE074"}
  ];
 var placeholder = $('#piechart-placeholder').css({'width':'90%' , 'min-height':'150px'});
 $.plot(placeholder, data, {
	
	series: {
        pie: {
            show: true,
			tilt:0.8,
			highlight: {
				opacity: 0.25
			},
			stroke: {
				color: '#fff',
				width: 2
			},
			startAngle: 2
			
        }
    },
    legend: {
        show: true,
		position: "ne", 
	    labelBoxBorderColor: null,
		margin:[-30,15]
    }
	,
	grid: {
		hoverable: true,
		clickable: true
	},
	tooltip: true, //activate tooltip
	tooltipOpts: {
		content: "%s : %y.1",
		shifts: {
			x: -30,
			y: -50
		}
	}
	
 });
 
  var $tooltip = $("<div class='tooltip top in' style='display:none;'><div class='tooltip-inner'></div></div>").appendTo('body');
  placeholder.data('tooltip', $tooltip);
  var previousPoint = null;
  placeholder.on('plothover', function (event, pos, item) {
	if(item) {
		if (previousPoint != item.seriesIndex) {
			previousPoint = item.seriesIndex;
			var tip = item.series['label'] + " : " + item.series['percent']+'%';
			$(this).data('tooltip').show().children(0).text(tip);
		}
		$(this).data('tooltip').css({top:pos.pageY + 10, left:pos.pageX + 10});
	} else {
		$(this).data('tooltip').hide();
		previousPoint = null;
	}
	
 });
		var d1 = [];
		for (var i = 0; i < Math.PI * 2; i += 0.5) {
			d1.push([i, Math.sin(i)]);
		}
		var d2 = [];
		for (var i = 0; i < Math.PI * 2; i += 0.5) {
			d2.push([i, Math.cos(i)]);
		}
		var d3 = [];
		for (var i = 0; i < Math.PI * 2; i += 0.2) {
			d3.push([i, Math.tan(i)]);
		}
		
		var sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});
		$.plot("#sales-charts", [
			{ label: "Domains", data: d1 },
			{ label: "Hosting", data: d2 },
			{ label: "Services", data: d3 }
		], {
			hoverable: true,
			shadowSize: 0,
			series: {
				lines: { show: true },
				points: { show: true }
			},
			xaxis: {
				tickLength: 0
			},
			yaxis: {
				ticks: 10,
				min: -2,
				max: 2,
				tickDecimals: 3
			},
			grid: {
				backgroundColor: { colors: [ "#fff", "#fff" ] },
				borderWidth: 1,
				borderColor:'#555'
			}
		});
		$('[data-rel="tooltip"]').tooltip();
})

		  function saveSuccess(){
			 		location.href = location.href;
			}

		<%--function updateWeatherCity(){--%>
			<%--var cityId = $("#weatherCityId").val();--%>
			<%--$.ajax({--%>
	            <%--type: "GET",--%>
	            <%--url: "${pageContext.request.contextPath}/system/main/updateWeatherCity.do?wearth_city_id="+cityId,--%>
	            <%--data: "",--%>
	            <%--dataType: "json",--%>
	            <%--success: function(data){--%>
	            	<%--window.location.reload();--%>
	            <%--}--%>
	         <%--});--%>
		<%--}--%>
			function dbFindInfo(id,customer_id){
				var url = "${pageContext.request.contextPath}/common/message/updateStatus.do?id="+id;
				$.get(url,function(data){
					if(data){
						saveSuccess();
					}
				});
				art.dialog.open('${pageContext.request.contextPath}/common/message/findInfo.do?id='+customer_id,{
					title:'消息详情',
					width:550,
					height:500,
					background: '#F5F5F5',
					lock: true,
					cancelVal:'关闭',
					cancel:function(){
					}
				});//打开子窗体
			}
		</script>
		
		
	</body>
    
</html>
