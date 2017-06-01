<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="../../system/admin/top.jsp" %>

		<div class="container-fluid" id="main-container">
			<%@include file="../../system/admin/left.jsp" %>


			<div id="main-content" class="clearfix">
					<div id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="#">设备管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
							<li class="active">家人信息</li>
						</ul><!--.breadcrumb-->
						<div id="nav-search">
						</div><!--#nav-search-->
					</div><!--#breadcrumbs-->

					<div id="page-content" class="clearfix">
						  <div class="row-fluid">
							<div class="row-fluid">
									<!-- 检索  -->
									<form action="${pageContext.request.contextPath}/customer/family/findList.do?am=${mPd.am}&bm=${mPd.bm}&pm=${topMPd}" method="post" name="familyForm" id="familyForm">
									<div class="page-header position-relative">
										<table>
											<tr>
												<td><font color="#808080">用户名称：</font></td>
												<td><input type="text" name="user_name" value="${pd.user_name }" placeholder="这里输入用户名称" style="width:130px;"/></td>
												<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
											</tr>
										</table>
									<%--<c:if test="${fn:contains(power,'family_add')}">--%>
									    <%--<a class="btn btn-success btn-sm" onclick="add()">添加</a>--%>
									<%--</c:if>--%>
									<%--<c:if test="${fn:contains(power,'family_edit')}">--%>
										<%--<a class="btn btn-warning btn-sm" onclick="edits()">编辑</a> --%>
									<%--</c:if>--%>
									<%--<c:if test="${fn:contains(power,'family_del')}">--%>
										<%--<a class="btn btn-danger btn-sm" onclick="dels()">删除</a>--%>
									<%--</c:if>--%>
									<%--<c:if test="${fn:contains(power,'family_info')}"> --%>
										<%--<a class="btn btn-info btn-sm" onclick="findInfo()">查看详情 </a>--%>
									<%--</c:if>--%>
									<%--<c:if test="${fn:contains(power,'family_trash')}">--%>
										<%--<a class="btn btn-sm" onclick="findTrash()">回收站(${pd.num })</a>--%>
									<%--</c:if>--%>
									</div>
									<!-- 检索  -->
									<div class="widget-box" style="">
												<div class="widget-header">
													<h4>设备信息</h4>
													
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
									<table id="table_report" class="table table-striped table-bordered  table-hover">
										<thead>
											<tr>
												<th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>
												<th>账号</th>
												<th>昵称</th>
												<th>性别</th>
												<%--<th>出生日期</th>--%>
												<th>居住地区</th>
												<%--<th>出生地址</th>--%>
												<th>状态</th>
												<th>创建时间</th>
												<th id="exclude_1" class="center" style="width:100px;">操作</th>
											</tr>
										</thead>
										<tbody>
										<!-- 开始循环 -->	
										<c:choose>
											<c:when test="${not empty list}">
												<c:forEach items="${list}" var="var" varStatus="vs">
													<tr ondblclick="findInfo('${var.id }')">
														<td id="exclude_0_${var.id }" style="width:20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id },${var.status}" onchange="myCheck('${var.id }');"></td>
														<td>${var.login_name }</td>
														<td>${var.nickname }</td>
														<td>${var.sex }</td>
														<%--<td>${var.birth_date }</td>--%>
														<td>${var.live_address }</td>
														<%--<td>${var.birth_address }</td>--%>
														<td id="exclude_2_${var.id }">
															<c:if test="${var.status=='1'}">
																<label><input id="status_${var.id }" name="switch-field-1" class="ace-switch ace-switch-6" type="checkbox" checked="checked" onchange="updateStatus('${var.id }');" /><span class="lbl"></span></label>
															</c:if>
															<c:if test="${var.status=='0'}">
																<label><input id="status_${var.id }" name="switch-field-1" class="ace-switch ace-switch-6" type="checkbox" onchange="updateStatus('${var.id }');"/><span class="lbl"></span></label>
															</c:if>
														</td>
														<td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														<td id="exclude_1_${var.id }" style="">
															<%--<c:if test="${var.isbinddevice!=''&&var.isbinddevice!=null}">--%>
																<%--<a class='btn btn-mini btn-info' title="查询客户信息" onclick="findDevice('${var.id }');">查询设备信息</a>--%>
															<%--</c:if>--%>
															<%--<c:if test="${var.isbinddevice==''||var.isbinddevice==null}">--%>
																<%--<a class='btn btn-mini' title="尚未绑定设备">尚未绑定设备</a>--%>
															<%--</c:if>--%>
															<%--<c:if test="${fn:contains(power,'family_edit')}">--%>
																<a class='btn btn-mini btn-info' title="查看" onclick="findInfo('${var.id }');"><i class='icon-search'></i></a>
															<%--</c:if>--%>
															<%--<c:if test="${fn:contains(power,'family_del')}">--%>
																<%--<a class='btn btn-mini btn-danger' title="禁用" onclick="del('${var.id }','${var.status}');"><i class='icon-remove'></i></a>--%>
															<%--</c:if>--%>
														</td>
													</tr>
												
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="20" class="center">没有相关数据</td>
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
					</div><!--/#ace-settings-container-->
			</div><!-- #main-content -->
			
		</div><!--/.fluid-container#main-container-->
		
		<%@include file="../../system/admin/bottom.jsp" %>
		
		<script type="text/javascript">
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
				$("#familyForm").submit();
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
			//获取详情
			function findInfo(id){
				art.dialog.open('${pageContext.request.contextPath}/customer/family/findInfo.do?id='+id,{
					title:'查看详情',
					width:550,
					height:400,
					lock: true,
					cancelVal:'关闭',
					cancel:function(){
					}
				});//打开子窗体
			}
			//更新状态
			function updateStatus(id){
				var c = $("#status_"+id).prop("checked");
				var flag='';
				if(c){
					flag='1'
				}else{
					flag='0'
				}
				$.ajax({
		            type: "GET",
		            url: "${pageContext.request.contextPath}/customer/family/updateStatus.do?id="+id+"&status="+flag,
		            data: "",
		            dataType: "json",
		            success: function(data){
		            	nextPage(${page.currentPage});
		            }
		         });
			}
		</script>
	</body>
</html>
