<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="../admin/top.jsp" %>
		<div class="container-fluid" id="main-container">
			<%@include file="../admin/left.jsp" %>
			<div id="main-content" class="clearfix">
					<div id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="#">系统管理</a><span class="divider"><i class="icon-angle-right"></i></span></li>
							<li class="active">字典管理</li>
						</ul><!--.breadcrumb-->
					</div><!--#breadcrumbs-->
					<div id="page-content" class="clearfix">
						
						<div class="row-fluid">
							<div class="row-fluid">
							<!-- 检索  -->
									<form action="${pageContext.request.contextPath}/system/dictionary/findList.do?am=${mPd.am }&bm=${mPd.bm }" method="post" name="cdForm" id="cdForm">
									<!-- 检索  -->
									<div class="page-header position-relative">
									<div class="content-hd">
									<c:if test="${fn:contains(power,'dictionary_add')}">
										 <a class="btn btn-success btn-sm" onclick="add()">添加</a>
									</c:if>
									<c:if test="${fn:contains(power,'dictionary_edit')}">
										 <a class="btn btn-warning btn-sm" onclick="update()">编辑</a> 
									</c:if>
									<c:if test="${fn:contains(power,'dictionary_del')}">
										 <a class="btn btn-danger btn-sm" onclick="del()">删除</a>
									</c:if>
									<c:if test="${fn:contains(power,'dictionary_info')}"> 
										<a class="btn btn-info btn-sm" onclick="view()">查看详情 </a>
									</c:if>
									<c:if test="${fn:contains(power,'dictionary_trash')}">
										 <a class="btn btn-sm" onclick="findDustbin()">回收站(${pd.num }) </a>
									</c:if>
										</div>
										</div>
									
										<div style="float: left; width: 23%; margin-right:15px;" id="leftDiv">
											<div class="widget-box" style="">
												<div class="widget-header">
													<h4>字典管理</h4>
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
													<h4>信息详情</h4>
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
													<div class="widget-body-inner">
														 <div class="widget-main">
															<div class="row-fluid">
															<div  id="table_print" style="border: 1px white solid;">
																<table id="table_report" class="table table-striped table-bordered table-hover">
																	<thead>
																		<tr>
																		<th id="exclude_0" style="width:20px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check" onchange="myCheckAll();"> </th>
																		<th>父级名称</th>
																		<th>字典名称</th>
																		<th>字典简称</th>
																		<th>类型</th>
																		<th>创建时间</th>
																		<th id="exclude_1" class="center" style="width:65px;">操作</th>
																		</tr>
																	</thead>
																<tbody id="listCenter">	
																	<!-- 开始循环 -->	
																	<c:choose>
																		<c:when test="${not empty dictionarylist}">
																			<c:forEach items="${dictionarylist}" var="var" varStatus="vs">
																				<tr>
																					<td id="exclude_0_${var.id }" style="width: 20px;"><input type="checkbox" style="opacity: 1;" id="check_${var.id }" value="${var.id }" onchange="myCheck('${var.id }');"> </td>
																					<td>${var.parent_name}</td>
																					<td>${var.name }</td>
																					<td>${var.short_name }</td>
																					<td>${var.type }</td>
																					<td><fmt:formatDate value="${var.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
																					<td style="" id="exclude_1_${var.id }">
																					<c:if test="${fn:contains(power,'dictionary_edit')}">
																						<a class='btn btn-mini btn-info' title="编辑" onclick="edits('${var.id }');"><i class='icon-edit'></i></a>
																					</c:if>
																					<c:if test="${fn:contains(power,'dictionary_del')}">
																						<a class='btn btn-mini btn-danger' title="删除" onclick="dels('${var.id }');"><i class='icon-trash'></i></a>
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
														 </div>
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
		<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"/></script>
		<script type="text/javascript"  src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.all.js"/></script>
				<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		
		<%@include file="dictionary_ztree.jsp" %>
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
		//检索
		function search(){
			$("#cdForm").submit();
		}	
		function saveSuccess(){
				var num = '${page.currentPage}';
			 	if(num == '0'){
			 		location.href = location.href;
			 	}else{
			 		nextPage('${page.currentPage}');
			 	}
			}
		
	
			//修改
			function edits(id){
			var name = $("#tree_name").val();
				art.dialog.open('${pageContext.request.contextPath}/system/dictionary/toedit.do?id='+id+'&name='+name,{
			   	 	title:'编辑',
					width:550,
		    		height:500,
		    		lock: true
				});//打开子窗体
			}
			
			//删除
				function dels(id){
					bootbox.confirm("确定要删除该记录?", function(result) {
						if(result) {
							var url = "${pageContext.request.contextPath}/system/dictionary/delete.do?ids="+id;
							$.get(url,function(data){
								if(data){
									saveSuccess();
								}
							});
						}
					});		
			}
			
	
			//新增字典
			function add(){
				var id =  $("#tree_id").val();
				var name = $("#tree_name").val();
				art.dialog.open('${pageContext.request.contextPath}/system/dictionary/toadd.do?id='+id+'&name='+name,{
			   	 	title:'新增',
					width:550,
		    		height:500,
		    		lock: true
				});//打开子窗体
			}
			
			
			//修改
			function update(){
				var data = $('input:checkbox[id^=check_]:checked');
				var name = $("#tree_name").val();
				if(data.length==1){
					art.dialog.open('${pageContext.request.contextPath}/system/dictionary/toedit.do?id='+data.val()+'&name='+name,{
				   	 	title:'编辑',
						width:550,
			    		height:500,
			    		lock: true
					});//打开子窗体
				}else{
					art.dialog.alert("请选择一条数据进行编辑。");
				}
	}
			
			
			//查看
			function view(){
				var idsnum = $('input:checkbox[id^=check_]:checked');
				var name = $("#tree_name").val();
				if(idsnum.length==1){
					art.dialog.open('${pageContext.request.contextPath}/system/dictionary/findinfo.do?id='+idsnum.val()+'&name='+name,{
			    	 	title:'查看',
			    	 	width:550,
			        	height:500,
			        	lock: true
			    	});//打开子窗体
				}else{
					art.dialog.alert("请选择一条数据进行查看！");
				}
			}
			
			//删除
			
			function del(){
				var data = $('input:checkbox[id^=check_]:checked');
				if(data.length>0){
					var ids = [];
					$("input:checkbox[id^='check_']:checked").each(function(){
						ids.push($(this).attr("value"));
					});
					bootbox.confirm("确定要删除选中的数据?", function(result) {
					if(result) {
						var url = "${pageContext.request.contextPath}/system/dictionary/delete.do?ids="+ids;
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
			
			

	
	//回收站
	function findDustbin(){
		art.dialog.open('${pageContext.request.contextPath}/system/dictionary/findTrash.do',{
	   	 	title:'回收站',
			width:1000,
    		height:520,
    		lock: true,
	    	cancelVal: '关闭',
		    	cancel:function(){
		    		saveSuccess();
			}
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
				tableExport('table_report', '字典信息', e.target.getAttribute('data-type'));
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
