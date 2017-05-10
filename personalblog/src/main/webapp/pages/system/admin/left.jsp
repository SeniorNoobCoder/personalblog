<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->
<script type="text/javascript">
function aa(){
alert("aa");
}
</script>


			<div id="sidebar">

				<div id="sidebar-shortcuts">
					<div id="sidebar-shortcuts-large">
						<c:forEach items="${quickmenuList}" var="var" varStatus="status">
							<c:if test="${var.level=='1'}">
								<a class="btn btn-small btn-success" href="${pageContext.request.contextPath}/${var.url }?am=${var.one_menu }&bm=${var.two_menu }" title="${var.two_menu_name }"><i class="${var.icon }"></i></a>
							</c:if>
							<c:if test="${var.level=='2'}">
								<a class="btn btn-small btn-info" href="${pageContext.request.contextPath}/${var.url }?am=${var.one_menu }&bm=${var.two_menu }" title="${var.two_menu_name }"><i class="${var.icon }"></i></a>
							</c:if>
							<c:if test="${var.level=='3'}">
								<a class="btn btn-small btn-warning" href="${pageContext.request.contextPath}/${var.url }?am=${var.one_menu }&bm=${var.two_menu }" title="${var.two_menu_name }"><i class="${var.icon }"></i></a>
							</c:if>
							<c:if test="${var.level=='4'}">
								<a class="btn btn-small btn-danger" href="${pageContext.request.contextPath}/${var.url }?am=${var.one_menu }&bm=${var.two_menu }" title="${var.two_menu_name }"><i class="${var.icon }"></i></a>
							</c:if>
						</c:forEach>
					</div>
					<div id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>
						<span class="btn btn-info"></span>
						<span class="btn btn-warning"></span>
						<span class="btn btn-danger"></span>
					</div>
				</div><!-- #sidebar-shortcuts -->


				<ul class="nav nav-list">
					<c:if test="${mPd.am!=''&&mPd.am!=null}">
						<li>
					</c:if>
					<c:if test="${mPd.am==''||mPd.am==null}">
						<li class="active">
					</c:if>
					  <a href="${pageContext.request.contextPath}/system/main/index.do">
						<i class="icon-dashboard"></i>
						<span>首页</span>
					  </a>
					</li>
					<c:choose>
						<c:when test="${not empty menuList}">
							<c:forEach items="${menuList}" var="var" varStatus="status">
								<c:if test="${var.id==mPd.am}">
									<c:if test="${fn:length(var.menu2List)>0}">
										<li class="active open">
									</c:if>
									<c:if test="${fn:length(var.menu2List)==0}">
										<li class="active">
									</c:if>
								</c:if>
								<c:if test="${var.id!=mPd.am}">
									<li>
								</c:if>
								
								<c:choose> 
								      <c:when test="${fn:contains(var.url,'?')}">    
								          <a href="${pageContext.request.contextPath}/${var.url }&am=${var.id }" class="dropdown-toggle" title="${var.name }">
								      </c:when> 
								      <c:otherwise>    
								           <a href="${pageContext.request.contextPath}/${var.url }?am=${var.id }" class="dropdown-toggle" title="${var.name }">
								      </c:otherwise> 
								</c:choose> 
									<i class="${var.icon }"></i>
									<span>${var.name }</span>
									<c:if test="${fn:length(var.menu2List)>0}">
										<b class="arrow icon-angle-down"></b>
									</c:if>
								  </a>
								  <c:if test="${fn:length(var.menu2List)>0}">
								  	  <ul class="submenu">
								  	  	<c:forEach items="${var.menu2List}" var="v" varStatus="vs">
								  	  		<c:if test="${v.id==mPd.bm}">
												<li class="active">
											</c:if>
											<c:if test="${v.id!=mPd.bm}">
												<li>
											</c:if>
												<c:choose> 
												      <c:when test="${fn:contains(v.url,'?')}">    
												          <a href="${pageContext.request.contextPath}/${v.url }&am=${var.id }&bm=${v.id }"><i class="icon-double-angle-right"></i>${v.name }</a>
												      </c:when> 
												      <c:otherwise>    
												           <a href="${pageContext.request.contextPath}/${v.url }?am=${var.id }&bm=${v.id }"><i class="icon-double-angle-right"></i>${v.name }</a>
												      </c:otherwise> 
												</c:choose> 
								
								  	  			</li>
								  	  	</c:forEach>
									  </ul>
								  </c:if>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<li>
								没有相关数据
							</li>
						</c:otherwise>
					</c:choose>
					

				</ul><!--/.nav-list-->



				<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>





			</div><!--/#sidebar-->
			