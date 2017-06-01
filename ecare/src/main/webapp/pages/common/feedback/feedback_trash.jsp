<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>

<title></title>

<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- basic styles -->
<link href="${pageContext.request.contextPath}/ui/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/ui/css/bootstrap-responsive.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/ui/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/ui/css/datepicker.css" />
<!-- 日期框 -->
<!--[if IE 7]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/font-awesome-ie7.min.css" />
	<![endif]-->
<!-- page specific plugin styles -->

<!-- ace styles -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/ui/css/ace.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
<!--[if lt IE 9]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-ie.min.css" />
	<![endif]-->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/messages_zh.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>

</head>

<body>
	<div id="page-content" class="clearfix">
		<div class="row-fluid">
			<div class="row-fluid">
				<!-- 检索  -->
				<form
					action="${pageContext.request.contextPath}/common/feedback/findTrash.do?am=${mPd.am }&bm=${mPd.bm }"
					method="post" name="userForm" id="userForm">
					<div class="page-header position-relative">
						<table>
							<tr>
								<td><font color="#808080">状态：</font></td>
								<td><select name="status" style="width:120px;">
										<option value="">请选择</option>
										<option value="0"
											<c:if test="${pd.status=='0'}"> selected="selected" </c:if>>未处理</option>
										<option value="1"
											<c:if test="${pd.status=='1'}"> selected="selected" </c:if>>已处理</option>
										<td style="vertical-align:top;"><button
												class="btn btn-mini btn-light" onclick="search();">
												<i id="nav-search-icon" class="icon-search"></i>
											</button></td>
								</select>
								</td>
							</tr>
						</table>
						<a class="btn btn-danger btn-sm" onclick="myClear();">清空</a> <a
							class="btn btn-success btn-sm" onclick="revert();">还原</a> <a
							class="btn btn-danger btn-sm" onclick="all_clear();">全部清空</a> <a
							class="btn btn-success btn-sm" onclick="all_revert();">全部还原</a>
					</div>
					<!-- 检索  -->
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><input type="checkbox"
									style="opacity: 1;margin-top: -7px;" id="check"
									onchange="myCheckAll();"></th>
								<th>类型</th>
								<th>反馈人</th>
								<th>处理人</th>
								<th style="width:70px;">状态</th>
								<th>反馈时间</th>
								<th class="center" style="width:40px;">操作</th>
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty list}">
									<c:forEach items="${list}" var="var" varStatus="vs">
										<tr>
											<td style="width: 20px;"><input type="checkbox"
												style="opacity: 1;" id="check_${var.id }" value="${var.id }"
												onchange="myCheck('${var.id }');"></td>
											<td>${var.type }</td>
														<td>${var.create_user }</td>
														<td>${var.dealwith_user }</td>
														<td>
															<c:if test="${var.status=='0'}"><font color="orange">未处理</font></c:if>
															<c:if test="${var.status=='1'}"><font color="blue">已处理</font></c:if>
														</td>
														<td><fmt:formatDate value="${var.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>
												<div class='hidden-phone visible-desktop btn-group'>
													<a class='btn btn-mini btn-info' title="详情"
														onclick="findInfo('${var.id }');">详情</a>
												</div>
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

					<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;"><div class="pagination"
										style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<!-- PAGE CONTENT ENDS HERE -->
		</div>
		<!--/row-->

	</div>
	<!--/#page-content-->

	<%@include file="../../system/admin/bottom.jsp"%>

	<script type="text/javascript">
		function myCheckAll() {
			var c = $("#check").prop("checked");
			if (c) {
				$("input:checkbox[id^='check_']").each(function() {
					$(this).prop("checked", true);
				});
			} else {
				$("input:checkbox[id^='check_']").each(function() {
					$(this).prop("checked", false);
				});
			}
		}

		function myCheck(id) {
			var c = $("#check_" + id).prop("checked");
			if (c) {
				$("#check").prop("checked", true);
			} else {
				var flag = true;
				$("input:checkbox[id^='check_']").each(function() {
					var cc = $(this).prop("checked");
					if (cc) {
						flag = false;
					}
				});
				if (flag) {
					$("#check").prop("checked", false);
				}
			}
		}

		//检索
		function search() {
			$("#userForm").submit();
		}

		function myClear() {
			var data = $('input:checkbox[id^=check_]:checked');
			if (data.length > 0) {
				var ids = [];
				$("input:checkbox[id^='check_']:checked").each(function() {
					ids.push($(this).attr("value"));
				});
				bootbox
						.confirm(
								"确定要清除选中的数据吗?清除之后不可恢复！",
								function(result) {
									if (result) {
										var url = "${pageContext.request.contextPath}/common/feedback/clearFeedback.do?ids="
												+ ids + "&flag=clear";
										$
												.get(
														url,
														function(data) {
															if (data) {
																//document.location.reload();
																nextPage('${page.currentPage}');
															}
														});
									}
								});
			} else {
				art.dialog.alert("请选择要进行删除的数据。");
			}
		}

		function revert() {
			var data = $('input:checkbox[id^=check_]:checked');
			if (data.length > 0) {
				var ids = [];
				$("input:checkbox[id^='check_']:checked").each(function() {
					ids.push($(this).attr("value"));
				});
				bootbox
						.confirm(
								"确定要还原选中的数据吗？",
								function(result) {
									if (result) {
										var url = "${pageContext.request.contextPath}/common/feedback/revertFeedback.do?ids="
												+ ids + "&flag=revert";
										$
												.get(
														url,
														function(data) {
															if (data) {
																//document.location.reload();
																nextPage('${page.currentPage}');
															}
														});
									}
								});
			} else {
				art.dialog.alert("请选择要进行还原的数据。");
			}
		}

		function all_clear() {
			bootbox
					.confirm(
							"确定要清除全部数据吗?清除之后不可恢复！",
							function(result) {
								if (result) {
									var url = "${pageContext.request.contextPath}/common/feedback/clearFeedback.do?flag=all_clear";
									$.get(url, function(data) {
										if (data) {
											//document.location.reload();
											nextPage('${page.currentPage}');
										}
									});
								}
							});
		}

		function all_revert() {
			bootbox
					.confirm(
							"确定要还原全部数据吗？",
							function(result) {
								if (result) {
									var url = "${pageContext.request.contextPath}/common/feedback/revertFeedback.do?flag=all_revert";
									$.get(url, function(data) {
										if (data) {
											//document.location.reload();
											nextPage('${page.currentPage}');
										}
									});
								}
							});
		}

		function findInfo(id) {
			art.dialog.open(
					'${pageContext.request.contextPath}/common/feedback/findInfo.do?id='
							+ id, {
						title : '查看详情',
						width : 550,
						height : 500,
						lock : true
					});//打开子窗体
		}
	</script>


</body>
</html>
