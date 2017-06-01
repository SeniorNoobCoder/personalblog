<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>用户服务分类</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/ace-skins.min.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zTree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/artDialog.source.js?skin=blue"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/artDialog/plugins/iframeTools.source.js"></script>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//            zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
            zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
            zTree.expandAll(true);
        }
        $(document).ready(function(){
            var treeNodes = '';
            $.ajax({
                url : "${pageContext.request.contextPath}/partners/partners/findServerCategory.do?partner_userId=${pd.user_id}",
                data : {},
                type : 'GET',
                dataType : 'json',
                success : function(json) {
                    treeNodes+="["
                    $.each(json,function(i,n){
                        treeNodes += "{";
                        treeNodes += "id:'"+n.id+"'";
                        treeNodes += ",pId:'"+n.pId+"'";
                        treeNodes += ",name:'"+n.name+"'";
                        treeNodes += ",checked:'"+n.checked+"'";
                        treeNodes += ",open:'"+n.checked+"'";
                        treeNodes += "},";
                    });
                    treeNodes = treeNodes.substr(0,treeNodes.length-1)+"]"
                    if(treeNodes != ']'){
                        $.fn.zTree.init($("#treeDemo"), setting, eval(treeNodes));
                        setCheck();
                    }
                },
                error : function(xhr, status) {
                    alert('Sorry, there was a problem!');
                }
            });
        });
        //-->
    </SCRIPT>
    <script type="text/javascript">
        var user_id = '${pd.user_id}';
        var url="${pageContext.request.contextPath}/partners/partners/saveServerCategory.do?user_id="+user_id;
        function save(lx){
        	if(lx=='save_before'){
        		window.location.href="${pageContext.request.contextPath}/partners/partners/toaddAuthent.do?partner_userId="+user_id;
        	}else{
        		var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
                    nodes=treeObj.getCheckedNodes(true),
                    v="";
                    var name = "";
	            for(var i=0;i<nodes.length;i++){
	                v+=nodes[i].id + ",";
	                name+=nodes[i].name + ",";
	            }
	            if(nodes.length<1){
	                art.dialog.alert("请选择服务分类！");
	            }else{
	                $.ajax({
	                    url : url+"&category="+v+"&categoryName="+name,
	                    data : {},
	                    type : 'GET',
	                    success : function(data) {
	                        art.dialog.alert("服务商服务分类设置成功。");
	                        //var win = art.dialog.open.origin;
	                        //win.location.reload();
	                        //art.dialog.open.api.close();
	                        art.dialog.opener.saveSuccess();
	                    },
	                    error : function(xhr, status) {
	                        alert('重新设置服务商服务分类失败!');
	                    },
	                    complete : function(xhr, status) {
	                    }
	                });
	            }
        	}
        }
    </script>
</head>
<body>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div style="float: left;width: 100%;">
    <div class="portlet box green">
        <div class="portlet-body" style="min-height: 400px">
            <div class="clearfix">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
    </div>
    <!-- END EXAMPLE TABLE PORTLET-->
</div>
<div align="center">
	<%--<a class="btn btn-mini btn-primary" onclick="save('save_before');">上一步</a>--%>
    <a class="btn btn-mini btn-primary" onclick="save('save');">保存关闭</a>
    <a class="btn btn-mini btn-danger" onclick="art.dialog.open.api.close();">关闭</a>
</div>
</body>
</html>
