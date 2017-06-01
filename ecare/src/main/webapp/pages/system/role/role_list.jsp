<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="../admin/top.jsp" %>


		<div class="container-fluid" id="main-container">
			<%@include file="../admin/left.jsp" %>


			<div id="main-content" class="clearfix">
					<div id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="#">系统设置</a><span class="divider"><i class="icon-angle-right"></i></span></li>
							<li class="active">角色管理</li>
						</ul><!--.breadcrumb-->
						<div id="nav-search">
						</div><!--#nav-search-->
					</div><!--#breadcrumbs-->

					<div id="page-content" class="clearfix">
					
						  <div class="row-fluid">
							<div class="row-fluid">
									<!-- 检索  -->
									<form action="${pageContext.request.contextPath}/system/role/findList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="roleForm" id="roleForm">
									<div class="page-header">
									<table>
										<tr>
											<td><font color="#808080">角色名称：</font></td>
											<td><input type="text" name="name" value="${pd.name}" placeholder="这里输入角色名称" style="width:130px;"/></td>
											<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
										</tr>
									</table>
									<!-- 检索  -->
									
									
									<c:if test="${fn:contains(power,'role_add')}">
										 <a class="btn btn-success btn-sm" onclick="add()">添加</a>
									</c:if>
									<c:if test="${fn:contains(power,'role_edit')}">
										 <a class="btn btn-warning btn-sm" onclick="update('')">编辑</a>
									</c:if>
									<c:if test="${fn:contains(power,'role_del')}">
										 <a class="btn btn-danger btn-sm" onclick="del('')">删除</a>
									</c:if>
									<c:if test="${fn:contains(power,'role_configPower')}">
										  <a class="btn btn-info btn-sm" onclick="add_menu_role()">权限设置 </a>
									</c:if>
									
										  <%--<a class="btn btn-info btn-sm">职位设置 </a> 
										 --%>
									<c:if test="${fn:contains(power,'role_trash')}">
										 <a class="btn btn-sm" onclick="findDustbin()">回收站(${pd.num }) </a> 
									</c:if>
										</div>
									<c:if test="${pd.superAdministrator==sessionRole}">
										<div class="page-header">
											<li class="icon-group"></li><font color="#666666">系统管理员&nbsp;</font>
											<a class="btn btn-mini btn-purple" onclick="add_menu('${pd.superAdministrator}');"><i class="icon-pencil"></i>菜单权限</a>
										</div>	
									</c:if>	
										
									<div class="widget-box" style="">
											<div class="widget-header">
												<h4>角色信息</h4>
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
											</div>
											<div class="widget-body">
										<div  id="table_print" style="border: 1px white solid;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"> </th>
												<th>角色名称</th>
												<th>角色简称</th>
												<th>类型</th>
												<th>排序</th>
												<th>备注</th>
												<th>创建时间</th>
												<th id="exclude_1" class="center" style="width: 65px;">操作</th>
											</tr>
										</thead>
										<tbody>
										<!-- 开始循环 -->	
										<c:choose>
											<c:when test="${not empty roleList}">
												<c:forEach items="${roleList}" var="role" varStatus="vs">
													<tr>
														<td id="exclude_0_${role.id }"><input type="checkbox" style="opacity: 1;" value="${role.id},${role.type}" name="ids" id="check_${role.id }" onchange="myCheck('${role.id }');"> </td>
														<td>${role.name }</td>
														<td>${role.short_name}</td>
														<td>
															<c:if test="${pd.admin_type==role.type}">
																系统角色
															</c:if>
															<c:if test="${pd.ordinary_type==role.type}">
																普通角色
															</c:if>
														</td>
														<td>${role.order_by}</td>
														<td>${role.remark}</td>
														<td><fmt:formatDate value="${role.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														<td id="exclude_0_${role.id }" style="">
															<c:if test="${fn:contains(power,'role_edit')}">
																<a class='btn btn-mini btn-info' title="编辑" onclick="update('${role.id }','${role.type}');"><i class='icon-edit'></i></a>
															</c:if>
															<c:if test="${fn:contains(power,'role_del')}">
																<a class='btn btn-mini btn-danger' title="删除" onclick="del('${role.id }','${role.type}');"><i class='icon-trash'></i></a>
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
<script type="text/javascript">
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
	function saveSuccess(){
		var num = '${page.currentPage}';
	 	if(num == '0'){
	 		location.href = location.href;
	 	}else{
	 		nextPage('${page.currentPage}');
	 	}
	}

	//检索
	function search(){
		$("#roleForm").submit();
	}

	//新增
	function add(){
		art.dialog.open('${pageContext.request.contextPath}/system/role/toadd.do',{
	   	 	title:'新增',
			width:640,
    		height:380,
    		lock: true
		});//打开子窗体
	}


	//编辑
	var admin_type = '${pd.admin_type}';
	var superAdministrator = '${pd.superAdministrator}';
	var sessionRole = '${sessionRole}';
	function update(roleid,type){
		if(roleid!=""){
			//超级管理员可操作一切
			if(superAdministrator==sessionRole){
				art.dialog.open('${pageContext.request.contextPath}/system/role/toedit.do?id='+roleid,{
		    	 	title:'编辑',
		    	 	width:640,
		    	 	height:380,
		        	lock: true
		    	});//打开子窗体
			}else{
				//系统角色不可操作
				if(admin_type==type){
					art.dialog.alert("没有权限，非系统用户只能操作普通角色。");
				}else{
					//查询中已是自己的
					art.dialog.open('${pageContext.request.contextPath}/system/role/toedit.do?id='+roleid,{
			    	 	title:'编辑',
			    	 	width:640,
			    	 	height:380,
			        	lock: true
			    	});//打开子窗体
				}
			}
		}else{
			var v = $('input:checkbox[id^=check_]:checked');
			if(v.length==1){
				var id = v.val().split(",")[0];
				var type = v.val().split(",")[1];
				//超级管理员可操作一切
				if(superAdministrator==sessionRole){
					art.dialog.open('${pageContext.request.contextPath}/system/role/toedit.do?id='+id,{
			    	 	title:'编辑',
			    	 	width:640,
			    	 	height:380,
			        	lock: true
			    	});//打开子窗体
				}else{
					//系统角色不可操作
					if(admin_type==type){
						art.dialog.alert("没有权限，非系统用户只能操作普通角色。");
					}else{
						//查询中已是自己的
						art.dialog.open('${pageContext.request.contextPath}/system/role/toedit.do?id='+id,{
				    	 	title:'编辑',
				    	 	width:640,
				    	 	height:380,
				        	lock: true
				    	});//打开子窗体
					}
				}
			}else{
				art.dialog.alert("请选择一条数据进行编辑！");
			}
		}
	}
	//查看详情
	function findinfo(){
		var id = $('input:checkbox[id^=check_]:checked');
		if(id.length==1){
			art.dialog.open('${pageContext.request.contextPath}/system/role/findInfo.do?id='+id.val(),{
	    	 	title:'查看详情',
	    	 	width:640,
	    	 	height:380,
	        	lock: true
	    	});//打开子窗体
		}else{
			art.dialog.alert("请选择一条数据进行查看！");
		}
	}

	//删除
	function del(delid,type){
		if(delid!=""){
			//超级管理员可操作一切
			if(superAdministrator==sessionRole){
				bootbox.confirm("确定要删除选中数据吗？", function(result) {
					if(result){
					   $.ajax({
							url : '${pageContext.request.contextPath}/system/role/delete.do?id='+delid+'&remove_logo=Y',
							dataType : 'json',
							success : function(r) {
								saveSuccess();
							}
						});
					}
				});
			}else{
				//系统角色不可操作
				if(admin_type==type){
					art.dialog.alert("没有权限，非系统用户只能操作普通角色。");
				}else{
					//查询中已是自己的
					bootbox.confirm("确定要删除选中数据吗？", function(result) {
						if(result){
						   $.ajax({
								url : '${pageContext.request.contextPath}/system/role/delete.do?id='+delid+'&remove_logo=Y',
								dataType : 'json',
								success : function(r) {
									saveSuccess();
								}
							});
						}
					});
				}
			}
		}else{
			var id = $('input:checkbox[id^=check_]:checked');
			if(id.length>0){
				bootbox.confirm("确定要删除选中数据吗？", function(result) {
					if(result){
						var ids = [];
						var flag = true;
						$("input:checkbox[id^='check_']:checked").each(function(){
							ids.push($(this).attr("value").split(",")[0]);
							if(superAdministrator!=sessionRole){
								if(admin_type==$(this).attr("value").split(",")[1]){
									flag = false;
								}
							}
						});
						if(flag){
							$.ajax({
								url : '${pageContext.request.contextPath}/system/role/delete.do?id='+ids+'&remove_logo=Y',
								dataType : 'json',
								success : function(r) {
									saveSuccess();
								}
							});
						}else{
							art.dialog.alert("没有权限，非系统用户只能操作普通角色。");
						}
					}
				});
				
			}else{
				art.dialog.alert("请至少选择一条数据进行删除！");
			}
		}
	}

	//回收站
	function findDustbin(){
		art.dialog.open('${pageContext.request.contextPath}/system/role/findTrash.do',{
	   	 	title:'回收站',
	   		width:1000,
 			height:540,
 			lock: true,
 			cancelVal: '关闭',
 			cancel: function () {
 				saveSuccess();
 				}
		});//打开子窗体
	}
	//菜单角色配置
	function add_menu_role(){
		var v = $('input:checkbox[id^=check_]:checked');
		if(v.length==1){
			var id = v.val().split(",")[0];
			var type = v.val().split(",")[1];
			//超级管理员可操作一切
			if(superAdministrator==sessionRole){
				art.dialog.open('${pageContext.request.contextPath}/system/role/toaddMenuRole.do?role_id='+id,{
					title:'菜单权限',
					width:280,
		    		height:370,
		    		lock: true
				});//打开子窗体
			}else{
				//系统角色不可操作
				if(admin_type==type){
					art.dialog.alert("没有权限，非系统用户只能操作普通角色。");
				}else{
					//查询中已是自己的
					art.dialog.open('${pageContext.request.contextPath}/system/role/toaddMenuRole.do?role_id='+id,{
						title:'菜单权限',
						width:280,
			    		height:370,
			    		lock: true
					});//打开子窗体
				}
			}	
		}else{
			art.dialog.alert("请选择一个角色进行权限配置！");
		}
	}
	
	
	
	function add_menu(id){
		art.dialog.open('${pageContext.request.contextPath}/system/role/toaddMenuRole.do?role_id='+id,{
			title:'菜单权限',
			width:280,
	   		height:370,
	   		lock: true
		});//打开子窗体
	}
	
	
</script>
<script type="text/javascript">
	
		var $exportLink = document.getElementById('export');
		$exportLink.addEventListener('click', function(e){
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
				tableExport('table_report', '角色信息', e.target.getAttribute('data-type'));
			}
        	//window.location.reload();//刷新当前页面.
			//恢复
			$("#table_report").html(temp);
		}, false);
		
		
		
		function print(){
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
		}
		
		</script>
	</body>
</html>
