<!--
参数管理里面的收入参数、扣发参数、补贴对应表获取tree的公共页面。
-->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
    var power = '${power}';
    var setting = {
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parent_id",
                rootPId: 'parent'
            }
        },
        callback: {
            beforeExpand: beforeExpand,
            onExpand: onExpand,
            onClick: zTreeOnClick
        }
    };
    function beforeExpand() {
        //leftResize();
        // 防止右侧出现滚动条
    }
    function onExpand() {
        //leftResize();
        // 防止右侧出现滚动条
    }
    $(document).ready(function (){
        var treeNodes = '';
        $.ajax({
            url : "${pageContext.request.contextPath}/server/category/treeList.do",
            data : {},
            type : 'GET',
            dataType : 'json',
            success : function(json) {
                treeNodes += "[{id:'0',parent_id:'parent',name:'服务类型',level:'0'},";//,org_cascade:''
                $.each(json,function(i,n){
                    treeNodes += "{";
                    treeNodes += "id:'"+n.id+"'";
                    treeNodes += ",parent_id:'"+n.parent_id+"'";
                    treeNodes += ",name:'"+n.name+"'";
                    treeNodes += ",level:'"+n.level+"'";
//                    treeNodes += ",org_cascade:'"+n.org_cascade+"'";
                    treeNodes += "},";
                });
                treeNodes = treeNodes.substr(0,treeNodes.length-1)+"]";
                if(treeNodes != ']'){
                    $.fn.zTree.init($("#treeDemo"), setting ,eval(treeNodes));
                    var tree_id = '${pd.tree_id}';
                    if(tree_id==''||tree_id==null){
                        expandFirst();
                    }else{
                        expandDefault(tree_id);
                    }
                }
            },
            error : function(xhr, status) {
                alert('Sorry, there was a problem!');
            },
            complete : function(xhr, status) {
            }
        });
    });

    /*右侧默认显示*/
    function rightDefault(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodesByFilter(filter2);
        if(nodes!=null&&nodes!=''){
            show(nodes[0].url);
        }
    }

    //展开第一个节点
    function expandFirst(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodes();
        treeObj.expandNode(nodes[0],true,false,true,true);
        $("#tree_id").val(nodes[0].id);
        $("#tree_name").val(nodes[0].name);
        $("#tree_level").val(nodes[0].level);
//        $("#tree_cascade").val(nodes[0].org_cascade);
        show(nodes[0].id);
        //show(nodes[0].children[0].id,nodes[0].children[0].name);
    }
    //展开指定节点
    function expandDefault(tree_id){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodeByTId(tree_id);
        var node = treeObj.getNodeByParam("id", tree_id, null);
        alert(nodes);
        alert(node+"node");
        var ns = node.children;
        if(node.children){
            treeObj.expandNode(node,true,true,true);
        }else{
            var pnode = node.getParentNode();
            treeObj.expandNode(pnode,true,true,true);
        }
        $("#tree_id").val(node.id);
        $("#tree_name").val(node.name);
        $("#tree_type").val(node.type);
//        $("#tree_cascade").val(node.org_cascade);
        show(node.id);
        //show(nodes[0].children[0].id,nodes[0].children[0].name);
    }

    /*展开全部*/
    function openAll(treeId){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.expandAll(true);
    }

    /*折叠全部*/
    function closeAll(treeId){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodes()[0];
        treeObj.expandNode(node, true, false, false);
        var nodes = node.children;
        $.each(nodes,function(i,n){
            treeObj.expandNode(n, false, true, false,true);
        });
    }

    /*点击时操作*/
    function zTreeOnClick(event, treeId, treeNode) {
        show(treeNode.id);
        $("#tree_id").val(treeNode.id);
        $("#tree_name").val(treeNode.name);
        $("#tree_level").val(treeNode.level);
        $("#server_category_id").val("");
        $("#server_category_name").val("");
//        $("#tree_cascade").val(treeNode.org_cascade);
    };

    //显示
    function show(id){
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/server/categoryDetail/treeList.do?parent_id="+id,
            dataType: "json",
            success: function (result) {
                var tt ='';
                $.each(result,function(i,n){
                    tt+='<tr onclick="showJobRemark(\''+n.id+'\');clickCheck(\''+n.id+'\',\'category\',\''+n.name+'\');">';
                    tt+='<td id="exclude_category_0_'+n.id+'" style="width:20px;">' +
                            '<input type="checkbox" style="opacity: 1;" id="check_category_'+n.id+'" value="'+n.id+'">' +
                            '</td>';
                    tt+='<td>'+n.name+'</td>';
                    tt+='</tr>';
                });
                $("#categoryListCenter").html(tt);
                $("#jobDetailListCenter").html("");
            },
            error: function(data) {
            }
        });
    }
    function  showJobRemark(id){
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/server/categoryDetail/detailList.do?server_category_id="+id,
            dataType: "json",
            success: function (result) {
                var tt ='';
                $.each(result,function(i,n){
                    tt+='<tr onclick="clickCheck(\''+n.id+'\',\'job\');">';
                    tt+='<td id="exclude_job_0_'+n.id+'" style="width:20px;">' +
                            '<input type="checkbox" style="opacity: 1;" id="check_job_'+n.id+'" value="'+n.id+'">' +
                            '</td>';
                    tt+='<td>'+n.remark+'</td>';
                    tt+='<td width="20%">'+n.starting_price+'起</td>';
                    tt+='</tr>';
                });
                $("#jobDetailListCenter").html(tt);
            },
            error: function(data) {
            }
        });
    }
    function formatDate(v) {
        if (/^(-)?\d{1,10}$/.test(v)) {
            v = v * 1000;
        } else if (/^(-)?\d{1,13}$/.test(v)) {
            v = v * 1;
        }
        var dateObj = new Date(v);
        var month = dateObj.getMonth() + 1;
        var day =  dateObj.getDate();
        var hours = dateObj.getHours();
        var minutes = dateObj.getMinutes();
        var seconds = dateObj.getSeconds();
        if(month<10){
            month = "0" + month;
        }
        if(day<10){
            day = "0" + day;
        }
        if(hours<10){
            hours = "0" + hours;
        }
        if(minutes<10){
            minutes = "0" + minutes;
        }
        if(seconds<10){
            seconds = "0" + seconds;
        }
        var UnixTimeToDate = dateObj.getFullYear() + '-' + month + '-' +day + ' ' + hours + ':' + minutes + ':' + seconds;
        return UnixTimeToDate;
    }
</script>