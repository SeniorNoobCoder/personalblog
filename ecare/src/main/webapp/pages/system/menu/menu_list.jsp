<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="../admin/top.jsp" %>
	<style>
	.menu-tab ul{margin:0 !important;}
	.menu-tab li{ float:left; list-style-type:none;  text-align:center; height:38px;  line-height: 40px; width:80px; border-right:1px solid #e3e3e3;}
	.menu-xz-tab{ border-top:2px solid #438eb9; background:#fff;}
	.menu-tab li a{    text-decoration: none;}
	.btnw{width: 140px;}
	.btnw a{margin:3px;}
</style>

		<div class="container-fluid" id="main-container">
			<%@include file="../admin/left.jsp" %>

			<div id="main-content" class="clearfix">
					<div id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="#">系统管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
							<li class="active">菜单管理</li>
						</ul><!--.breadcrumb-->
						<div id="nav-search">
						</div><!--#nav-search-->
					</div><!--#breadcrumbs-->
					<div id="page-content" class="clearfix">
						<form action="${pageContext.request.contextPath}/system/menu/findListPage.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="memuForm" id="memuForm">
						<div class="row-fluid">
							<div class="row-fluid">
									<div style="padding-top: 10px;">
										<div style="float: left; width: 23%; margin-right:15px;" id="leftDiv">
											<div class="widget-box" style="">
												<div class="widget-header">
													<h4>菜单管理</h4>
													<span class="widget-toolbar">
														<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
													</span>
												</div>
												<div class="widget-body">
													<div class="widget-body-inner">
														 <div class="widget-main">
															<div class="row-fluid">
																<ul id="treeDemo" class="ztree"></ul>
																<input type="hidden" name="tree_id" id="tree_id" value="" />
																<input type="hidden" id="tree_name" value="" />
																<input type="hidden" id="tree_level" value="" />
																<input type="hidden" id="tree_type" value="" />
																<input type="hidden" id="type" value="menu" />
															</div>
														 </div>
													</div>
												</div>
												<div id="sidebar-collapse" style="background:#fff;border-bottom:none;">
													<i class="icon-double-angle-left" onclick="closeDiv();"></i>
												</div>
											</div>
										</div>
										<div style="float: left; width: 2%; margin-right:15px; display: none;" id="leftDiv2">
											<div id="sidebar-collapse" style="height:480px;background:#fff;border-bottom:none;">
												<div style="display: inline-block; height:98%;border-left: 1px solid #e1e1e1;position: absolute;left: 10px;"></div>
												<i class="icon-double-angle-left icon-double-angle-right" onclick="openDiv();" style="top:230px"></i>
											</div>
										</div>
										<div style="float: left; width: 75%" id="rightDiv">
											<div class="widget-box" style="">
												<div class="widget-header">
													<div class="menu-tab">
													 <ul>
														<li class="menu-xz-tab" id="menu_li" onclick="cutTab('menu');"><a href="#">菜单列表</a></li>
														<li id="button_li" onclick="cutTab('button');"><a href="#">按钮列表</a></li>
													 </ul>
													</div>
												<div id="tools_part" style="position: absolute; top: 5px; right: 400px; display: none;">
														<a style="cursor: pointer;" onclick="showTools('part');"><img src="${pageContext.request.contextPath}/resources/images/right.png"/></a>
													</div> 
													<div id="tools_all" style="position: absolute; top: 5px; right: 180px;">
														<a style="cursor: pointer;" onclick="showTools('all');"><img src="${pageContext.request.contextPath}/resources/images/left.png"/></a>
													</div>
													<div id="export" style="display: block">
														<a class="dc-icon5" data-type="xls" href="javascript:;"></a>
														<a class="dc-icon6" data-type="doc" href="javascript:;"></a>
														
														<span style="display: none; width: 290px;" id="tools_span">
															<a class="dc-icon8" data-type="pdf" href="javascript:;"></a>
															<a class="dc-icon2" style="cursor: pointer;" data-type="txt" onclick="dc('txt');"></a>
															<a class="dc-icon7" data-type="image" href="javascript:;"></a>
															<a class="dc-icon1" style="cursor: pointer;" data-type="json" onclick="dc('json');"></a>												
															<a class="dc-icon3" data-type="csv" href="javascript:;"></a>
													        <a class="dc-icon4" data-type="xml" href="javascript:;"></a>	
														</span>
														
													</div>
											<a class="dc-icon9" style="cursor: pointer; position:absolute; right:70px;top:6px;" onclick="print();"></a>
													<span class="widget-toolbar">
														<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
													</span>
													<div style="clear:both;"></div>
												</div>
												<!-- 菜单start -->
												<div class="widget-body" id="menu_div">
													<div class="widget-body-inner">
												<c:if test="${fn:contains(power,'menu_add')}">
													<a class="btn btn-mini btn-info" style="width:56px; margin:10px 0 0 12px;" title="添加" onclick="addMenu();">&nbsp;添加&nbsp; </a>
												</c:if>
														 <div class="widget-main">
															<div class="row-fluid">
															<div  id="table_print" style="border: 1px white solid;">
																<table id="table_report" class="table table-striped table-bordered table-hover">
																	<thead>
																		<tr>
																			<th id="exclude_0">序号</th>
																			<th>父级名称</th>
																			<th>菜单名称</th>
																			<th>菜单类型</th>
																			<th>排序</th>
																			<th>创建时间</th>
																			<th id="exclude_1">操作</th>
																		</tr>
																	</thead>
																<tbody id="menuListCenter">
																		
																	<!-- 开始循环 -->	
																	<c:choose>
																		<c:when test="${not empty list}">
																				<c:forEach items="${list}" var="var" varStatus="vs">
																					<tr>
																						<td id="exclude_0_${var.id }" class='center'>${vs.index+1}</td>
																						<td>菜单管理</td>
																						<td><i class="${var.icon }"></i>&nbsp;${var.name }</td>
																						<c:if test="${var.type=='menu' }">
																							<td>菜单</td>
																						</c:if>
																						<c:if test="${var.type=='button' }">
																							<td>按钮</td>
																						</c:if>
																						<td>${var.order_by }</td>
																						<td><fmt:formatDate value="${var.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
																						<td id="exclude_1_${var.id }">
																						<c:if test="${fn:contains(power,'menu_icon')}">
																							<a class="btn btn-mini btn-sm" title="设置图标" onclick="getIcon('${var.id }');">设置图标</a>
																						</c:if>
																						<c:if test="${fn:contains(power,'menu_edit')}">
																							<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.id }');"><i class='icon-edit'></i></a>
																						</c:if>
																						<c:if test="${fn:contains(power,'menu_del')}">
																							<a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.id }');"><i class='icon-trash'></i></a>
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
															</div>
														 </div>
													</div>
												</div>
												<!-- 菜单end -->
												<!-- 按钮start -->
												<div class="widget-body" id="button_div" style="display:none">
													<div class="widget-body-inner">
													<c:if test="${fn:contains(power,'menu_add')}">
													<a class="btn btn-mini btn-info" style="width:56px; margin:10px 0 0 12px;" title="添加" onclick="addButton();">&nbsp;添加&nbsp; </a>
													</c:if>
														 <div class="widget-main">
															<div class="row-fluid">
															<div  id="table_print1" style="border: 1px white solid;">
																<table id="table_report1" class="table table-striped table-bordered table-hover">
																	<thead>
																		<tr>
																			<th id="exclude_0">序号</th>
																			<th>父级名称</th>
																			<th>菜单名称</th>
																			<th>菜单类型</th>
																			<th>排序</th>
																			<th>创建时间</th>
																			<th id="exclude_1">操作</th>
																		</tr>
																	</thead>
																							
																	<tbody id="buttonListCenter">
																	<tr class="main_info">
																		<td colspan="10" class="center">没有相关数据</td>
																	</tr>
																	</tbody>
																	</table>
																	</div>
															</div>
														 </div>
													</div>
												</div>
												
												<!-- end -->
											</div>
										</div>
									</div>
									
								
							</div>
						 
						 
						 
						 
							<!-- PAGE CONTENT ENDS HERE -->
						  </div><!--/row-->
						</form>
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


		
		<%@include file="../admin/bottom.jsp" %>
		
<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"/></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.all.js"/></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<%@include file="menu_ztree.jsp" %>
		<script type="text/javascript">

		var power = '${power}';
			//设置图标
			function getIcon(id){
					art.dialog.open('${pageContext.request.contextPath}/system/menu/toMenuList.do?id='+id,{
				   	 	title:'选择图标',
						width:650,
			    		height:120,
			    		lock: true
					});//打开子窗体
			}
			var types = 'menu';
			//切换菜单
			function cutTab(type){
				$("#button_li").attr('class','');
				$("#menu_li").attr('class','');
				$('#'+type+'_li').addClass('menu-xz-tab');		
				$("#button_div").hide();
				$("#menu_div").hide();
				$('#'+type+'_div').show();
				$('#type').val(type);
				if(type=="button"){
					showButton();
					types = "button";
				}else if(type=="menu"){
					showMenu();
					types = "menu";
				}
			}

			function showButton(){
				var type = $("#type").val();
				var id =  $("#tree_id").val();
					$.ajax({
			            type: "POST",
			            url: "${pageContext.request.contextPath}/system/menu/findList.do?parent_id="+id+'&type='+type,
			            dataType: "json",
			            success: function (result) {
			            	var tt ='';
			            	if(result.length>0){
			            		$.each(result,function(i,n){
			            			tt+='<tr>';
			            			tt+='<td id="exclude_0_'+n.id+'" class="center" style="width: 30px;">'+(Number(i)+1)+'</td>';
			            			if(n.parent_name==null || n.parent_name=="undefined" ){
										tt+='<td>菜单管理</td>';
									}else{
			            				tt+='<td>'+n.parent_name+'</td>';
									}
			            			tt+='<td><i class="'+n.icon+'"></i>&nbsp;'+n.name+'</td>';
			            			if(n.type=='menu'){
			                  			tt+='<td>菜单</td>';      			
			            			}else if(n.type=='button'){
			            				tt+='<td>按钮</td>';     
			            			}
			            			tt+='<td>'+n.order_by+'</td>';
			            			tt+='<td>'+formatDate(n.create_time)+'</td>';
			            			tt+='<td id="exclude_1_'+n.id+'" class="btnw">';
			            			if(power.indexOf("menu_icon")>=0){
			            				tt+='<a class="btn btn-mini btn-sm" title="设置图标" onclick="getIcon(\''+n.id+'\');">设置图标</a>';
					            	}
			            			if(power.indexOf("menu_edit")>=0){
			            				tt+='<a class="btn btn-mini btn-info" title="编辑" onclick="edit(\''+n.id+'\');"><i class="icon-edit"></i></a>';
				            		}
			            			if(power.indexOf("menu_del")>=0){
			            				tt+='<a class="btn btn-mini btn-danger" title="删除" onclick="del(\''+n.id+'\');"><i class="icon-trash"></i></a>';
				            		}
			            			tt+='</td>';
			            			tt+='</tr>';
			            		});
				            }else{
				            	tt+='<tr class="main_info"><td colspan="10" class="center">没有相关数据</td></tr>';
					        }
			            	$("#buttonListCenter").html(tt);
			            },
			            error: function(data) {
			             }
			        });
				}
			function showMenu(){
				var type = $("#type").val();
				var id =  $("#tree_id").val();
					$.ajax({
			            type: "POST",
			            url: "${pageContext.request.contextPath}/system/menu/findList.do?parent_id="+id+'&type='+type,
			            dataType: "json",
			            success: function (result) {
			            	var tt ='';
			            	if(result.length>0){
			            		$.each(result,function(i,n){
			            			tt+='<tr>';
			            			tt+='<td id="exclude_0_'+n.id+'" class="center" style="width: 30px;">'+(Number(i)+1)+'</td>';
			            			if(n.parent_name==null || n.parent_name=="undefined" ){
										tt+='<td>菜单管理</td>';
									}else{
			            				tt+='<td>'+n.parent_name+'</td>';
									}
			            			tt+='<td><i class="'+n.icon+'"></i>&nbsp;'+n.name+'</td>';
			            			if(n.type=='menu'){
			                  			tt+='<td>菜单</td>';      			
			            			}else if(n.type=='button'){
			            				tt+='<td>按钮</td>';     
			            			}
			            			tt+='<td>'+n.order_by+'</td>';
			            			tt+='<td>'+formatDate(n.create_time)+'</td>';
			            			tt+='<td id="exclude_1_'+n.id+'" class="btnw">';
			            			if(power.indexOf("menu_icon")>=0){
			            				tt+='<a class="btn btn-mini btn-sm" title="设置图标" onclick="getIcon(\''+n.id+'\');">设置图标</a>';
					            	}
			            			if(power.indexOf("menu_edit")>=0){
			            				tt+='<a class="btn btn-mini btn-info" title="编辑" onclick="edit(\''+n.id+'\');"><i class="icon-edit"></i></a>';
				            		}
			            			if(power.indexOf("menu_del")>=0){
			            				tt+='<a class="btn btn-mini btn-danger" title="删除" onclick="del(\''+n.id+'\');"><i class="icon-trash"></i></a>';
				            		}			            			
				            		tt+='</td>';
			            			tt+='</tr>';
			            		});
				            }else{
				            	tt+='<tr class="main_info"><td colspan="10" class="center">没有相关数据</td></tr>';
					        }
			            	$("#menuListCenter").html(tt);
			            },
			            error: function(data) {
			             }
			        });
				}



			
			//新增菜单
			function addMenu(){
				var id =  $("#tree_id").val();
				var name = $("#tree_name").val();
				var level = $("#tree_level").val();
				var type = $("#tree_type").val();
				if(id==''||id==null){
					art.dialog.alert("请在左侧选择上级菜单！");
				}else{
					if(type=='button'){
						art.dialog.alert("按钮下不可添加菜单！");	
					}else if(level=='2'){
						art.dialog.alert("最多添加两级菜单！");
					}else{
						var buttonNum=0;
						//查询当前菜单下 是否有按钮,有按钮则不允许添加下级菜单
					    var url = "${pageContext.request.contextPath}/system/menu/getListNum.do?type=button&parent_id="+id;
					    $.ajax({
				            type: "POST",
				            url: url,
				            data: '',
				            dataType : 'json',
				            async:false,
				            success: function (data) {
					    		buttonNum = data;
				            },
				            error: function(data) {
				             }
				        });
					    if(buttonNum==0){
							art.dialog.open('${pageContext.request.contextPath}/system/menu/toadd.do?type=menu&id='+id+'&level='+level+'&name='+name,{
						   	 	title:'新增',
								width:550,
					    		height:400,
					    		lock: true
							});//打开子窗体
						}else{
							art.dialog.alert("该菜单下不允许添加菜单！请删除按钮后再添加菜单！");
						}
					}
				}
			}

			//新增按钮
			function addButton(){
				var id =  $("#tree_id").val();
				var name = $("#tree_name").val();
				var level = $("#tree_level").val();
				var type = $("#tree_type").val();
				if(id==''||id==null){
					art.dialog.alert("请在左侧选择上级菜单！");
				}else{
					if(type=='button'){
						art.dialog.alert("当前不可以添加按钮！");	
					}else{
						var buttonNum=0;
						//查询当前菜单下 是否有按钮,有按钮则不允许添加下级菜单
					    var url = "${pageContext.request.contextPath}/system/menu/getListNum.do?type=menu&parent_id="+id;
					    $.ajax({
				            type: "POST",
				            url: url,
				            data: '',
				            dataType : 'json',
				            async:false,
				            success: function (data) {
					    		buttonNum = data;
				            },
				            error: function(data) {
				             }
				        });
					    if(buttonNum==0){
							art.dialog.open('${pageContext.request.contextPath}/system/menu/toadd.do?type=button&id='+id+'&level='+level+'&name='+name,{
						   	 	title:'新增',
								width:550,
					    		height:400,
					    		lock: true
							});//打开子窗体
						}else{
							art.dialog.alert("该菜单下不允许添加菜单，请删除菜单后添加按钮！");
						}
					}
					
				}
			}
			
			//修改
			function edit(id){
				art.dialog.open('${pageContext.request.contextPath}/system/menu/toedit.do?id='+id,{
			   	 	title:'编辑',
					width:550,
		    		height:350,
		    		lock: true
				});//打开子窗体
			}
			
			//删除
			function del(id){
					bootbox.confirm("确定要删除该记录?", function(result) {
						if(result) {
							var url = "${pageContext.request.contextPath}/system/menu/del.do?id="+id;
							$.get(url,function(data){
								if(data){
									saveSuccess();
								}
							});
						}
					});		
			}
			
			function saveSuccess(){
				$("#memuForm").submit();
			}
		var $exportLink = document.getElementById('export');
		$exportLink.addEventListener('click', function(e){
			if(types == "menu"){
				e.preventDefault();
				//保存
				var temp=$("#table_report").html();
				
				$('th[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
	        	$('td[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
				
				if(e.target.nodeName === "A"){
					tableExport('table_report', '菜单信息', e.target.getAttribute('data-type'));
				}
	        	//window.location.reload();//刷新当前页面.
				//恢复
				$("#table_report").html(temp);	
			}else {
				e.preventDefault();
				//保存
				var temp=$("#table_report1").html();
				
				$('th[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
	        	$('td[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
				
				if(e.target.nodeName === "A"){
					tableExport('table_report1', '菜单信息', e.target.getAttribute('data-type'));
				}
	        	//window.location.reload();//刷新当前页面.
				//恢复
				$("#table_report1").html(temp);
			}
			
		}, false);
		
		
		
		function print(){
			if(types == "menu"){
				//保存
				var temp=$("#table_report").html();
				
				$('th[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
	        	$('td[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
				
				$("#table_print").jqprint();
				
				//恢复
				$("#table_report").html(temp);
			}else{
				//保存
				var temp=$("#table_report1").html();
				
				$('th[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
	        	$('td[id^="exclude_"]').each(function () {
					$("#"+this.id).remove();
	        	});
				
				$("#table_print1").jqprint();
				
				//恢复
				$("#table_report1").html(temp);
			}
		}
		
		</script>
	</body>
    
</html>
