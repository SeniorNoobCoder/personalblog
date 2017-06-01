<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="../../system/admin/top.jsp" %>


		<div class="container-fluid" id="main-container">
			<%@include file="../../system/admin/left.jsp" %>


			<div id="main-content" class="clearfix">
					<div id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="#">综合管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
							<li class="active">用户反馈管理</li>
						</ul><!--.breadcrumb-->
						<div id="nav-search">
						</div><!--#nav-search-->
					</div><!--#breadcrumbs-->

					<div id="page-content" class="clearfix">
						  <div class="row-fluid">
							<div class="row-fluid">
									<!-- 检索  -->
									<form action="${pageContext.request.contextPath}/common/feedback/findList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="userForm" id="userForm">
									<div class="page-header position-relative">
										<table>
											<tr>
												<td><font color="#808080">状态：</font></td>
												<td>
												<select name="status" style="width:120px;">
												<option value="">请选择</option>
													<option value="N" <c:if test="${pd.status=='N'}"> selected="selected" </c:if> >未处理</option>
													<option value="Y" <c:if test="${pd.status=='Y'}"> selected="selected" </c:if>>已处理</option>
													<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"><i id="nav-search-icon" class="icon-search"></i></button></td>
												</select>
												</td>
											</tr>
										</table>
									<%--<c:if test="${fn:contains(power,'feedback_add')}">--%>
									    <%--<a class="btn btn-success btn-sm" onclick="add()">添加</a>--%>
									<%--</c:if>--%>
									<%--<c:if test="${fn:contains(power,'feedback_edit')}">--%>
										<%--<a class="btn btn-warning btn-sm" onclick="edits()">处理</a> --%>
									<%--</c:if>--%>
									<c:if test="${fn:contains(power,'feedback_del')}">
										<a class="btn btn-danger btn-sm" onclick="dels()">删除</a>
									</c:if>
									<%--<c:if test="${fn:contains(power,'feedback_info')}"> --%>
										<%--<a class="btn btn-info btn-sm" onclick="findInfo()">查看详情 </a>--%>
									<%--</c:if>--%>
									<c:if test="${fn:contains(power,'feedback_trash')}">
										<a class="btn btn-sm" onclick="findTrash()">回收站(${pd.num })</a>
									</c:if>
									</div>
									<!-- 检索  -->
									<div class="widget-box" style="">
												<div class="widget-header">
													<h4>用户反馈信息</h4>
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
												<th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"></th>
												<th>反馈人</th>
												<th>内容</th>
												<th style="width:70px;">状态</th>
												<th>处理人</th>
												<th>反馈时间</th>
												<th id="exclude_1" class="center" style="width:65px;">操作</th>
											</tr>
										</thead>
										<tbody>
										<!-- 开始循环 -->	
										<c:choose>
											<c:when test="${not empty feedbackList}">
												<c:forEach items="${feedbackList}" var="var" varStatus="vs">
													<tr ondblclick="findInfo('${var.id }')">
														<td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"></td>
														<td>${var.userPhone }</td>
														<td>${var.content }</td>
														<td>
															<c:if test="${var.status=='N'}"><font color="orange">未处理</font></c:if>
															<c:if test="${var.status=='Y'}"><font color="blue">已处理</font></c:if>
														</td>
														<td>${var.dealwith_user }</td>
														<td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														<td id="exclude_1_${var.id }">
															<c:if test="${fn:contains(power,'feedback_edit')}">
																<a class='btn btn-mini btn-info' title="处理" onclick="edit('${var.id }');"><i class='icon-edit'></i></a>
															</c:if>
															<c:if test="${fn:contains(power,'feedback_del')}">
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
				$("#userForm").submit();
			}
			
			//子页面成功回调方法
			function saveSuccess(){
				var num = '${page.currentPage}';
			 	if(num == '0'){
			 		location.href = location.href;
			 	}else{
			 		nextPage('${page.currentPage}');
			 	}
			}
			
			
			//新增
			function add(){
				 art.dialog.open('${pageContext.request.contextPath}/common/feedback/toadd.do',{
			   	 	title:'新增',
					width:550,
		    		height:500,
		    		lock: true
				});//打开子窗体
			}
			
			
			//修改
			function edit(id){
				art.dialog.open('${pageContext.request.contextPath}/common/feedback/toedit.do?id='+id,{
			   	 	title:'受理',
					width:350,
		    		height:200,
		    		lock: true
				});//打开子窗体
			}

			//删除
			function del(id){
				bootbox.confirm("确定要删除该数据?", function(result) {
					if(result) {
						var url = "${pageContext.request.contextPath}/common/feedback/del.do?ids="+id;
						$.get(url,function(data){
							if(data){
								saveSuccess();
							}
						});
					}
				});		
			}
			
			
			
			//编辑
			<%--function edits(){--%>
				<%--var data = $('input:checkbox[id^=check_]:checked');--%>
				<%--if(data.length==1){--%>
					<%--art.dialog.open('${pageContext.request.contextPath}/common/feedback/toedit.do?id='+data.val(),{--%>
				   	 	<%--title:'编辑',--%>
						<%--width:250,--%>
			    		<%--height:200,--%>
			    		<%--lock: true--%>
					<%--});//打开子窗体--%>
				<%--}else{--%>
					<%--art.dialog.alert("请选择一条数据进行处理。");--%>
				<%--}--%>
			<%--}--%>
			
			//批量删除
			function dels(){
				var data = $('input:checkbox[id^=check_]:checked');
				if(data.length>0){
					var ids = [];
					$("input:checkbox[id^='check_']:checked").each(function(){
						ids.push($(this).attr("value"));
					});
					bootbox.confirm("确定要删除选中的数据?", function(result) {
					if(result) {
						var url = "${pageContext.request.contextPath}/common/feedback/del.do?ids="+ids;
						$.get(url,function(data){
							if(data){
								saveSuccess();
							}
						});
					}
				});	
				}else{
					art.dialog.alert("请选择要进行删除的数据。");
				}
			}
			
			
			//获取详情
			function findInfo(id){
				art.dialog.open('${pageContext.request.contextPath}/common/feedback/findInfo.do?id='+id,{
					title:'查看详情',
					width:550,
					height:250,
					lock: true,
					cancelVal: '关闭',
					cancel: function () {
					}
				});//打开子窗体
			}
			
			
			//回收站
			function findTrash(){
				art.dialog.open('${pageContext.request.contextPath}/common/feedback/findTrash.do',{
			   	 	title:'回收站',
					width:1000,
		    		height:520,
		    		lock: true,
		    		cancelVal: '关闭',
		    		cancel: function () {
		    			saveSuccess();
					}
				});//打开子窗体
			}
			
			//更新状态
			function updateStatus(id){
				var c = $("#status_"+id).prop("checked");
				var flag='';
				if(c){
					flag='Y'
				}else{
					flag='N'
				}
				$.ajax({
		            type: "GET",
		            url: "${pageContext.request.contextPath}/system/user/updateStatus.do?id="+id+"&status="+flag,
		            data: "",
		            dataType: "json",
		            success: function(data){
		            }
		         });
			}
			
			
			
			
			
			
			
			function excelExport(){
				$('#table_report').tableExport({type: 'excel', escape: 'false',ignoreColumn: [0,8]});
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
				tableExport('table_report', '用户信息', e.target.getAttribute('data-type'));
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
