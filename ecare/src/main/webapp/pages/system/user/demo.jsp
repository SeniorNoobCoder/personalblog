<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'demo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">  
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	
	<!--导出公共插件-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/tableExport/tableExport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/tableExport/jquery.base64.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/tableExport/html2canvas.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/tableExport/jspdf/libs/sprintf.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/tableExport/jspdf/jspdf.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/tableExport/jspdf/libs/base64.js"></script>
	
  </head>
  
  <body>
                [align=right]  
                    <br><br><br>            
                    <button class="btn btn-success" onClick ="$('#customers').tableExport({type: 'excel', escape: 'false'});">Excel Export</button>  
                    <button class="btn btn-success" onClick ="$('#customers').tableExport({type: 'pdf', escape: 'false'});">CSV Export</button>  
                    <br><br>  
                    [/align]  
                    <table id="customers" class="table table-striped table-bordered" >  
                        <thead>             
                            <tr class='warning'>  
                                <th>Country</th>  
                                <th>Population</th>  
                                <th>Date</th>  
                            </tr>  
                        </thead>  
                        <tbody>  
                            <tr>  
                                <td>Chinna</td>  
                                <td>1,363,480,000</td>  
                                <td>March 24, 2014</td>  
                            </tr>  
                            <tr>  
                                <td>India</td>  
                                <td>1,241,900,000</td>  
                                <td>March 24, 2014</td>  
                            </tr>  
                            <tr>  
                                <td>United States</td>  
                                <td>317,746,000</td>  
                                <td>March 24, 2014</td>  
                            </tr>  
                            <tr>  
                                <td>Indonesia</td>  
                                <td>249,866,000</td>  
                                <td>July 1, 2013</td>  
                            </tr>  
                            <tr>  
                                <td>Brazil</td>  
                                <td>201,032,714</td>  
                                <td>July 1, 2013</td>  
                            </tr>  
                        </tbody>  
                    </table>   
                </div>  
  </body>
</html>
