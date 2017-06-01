<!-- 
	参数管理里面的收入参数、扣发参数、补贴对应表获取tree的公共页面。 
-->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
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
			    url : "${pageContext.request.contextPath}/system/organize/treefindList.do",
			    data : {},
			    type : 'GET',
			    dataType : 'json',
			    success : function(json) {
				   treeNodes += "[{id:'0',parent_id:'parent',name:'公司分管领导',type:'1'},";
					$.each(json,function(i,n){
						treeNodes += "{";
						treeNodes += "id:'"+n.id+"'";
						treeNodes += ",parent_id:'"+n.parent_id+"'";
						treeNodes += ",name:'"+n.name+"'";
						treeNodes += ",type:'"+n.type+"'";
						treeNodes += "},";
					});
					treeNodes = treeNodes.substr(0,treeNodes.length-1)+"]";
					if(treeNodes != ']'){
						$.fn.zTree.init($("#treeDemo"), setting ,eval(treeNodes));
						expandFirst();
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
		$("#tree_type").val(nodes[0].type);
		//show(nodes[0].id);
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
		$("#tree_type").val(treeNode.type);
	};
	
	//显示
	function show(id){
		$.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/system/road/dustbinfindList.do?organize_id="+id+"&remove_logo=N",
            dataType: "json",
            success: function (result) {
            	var tt ='';
            		$.each(result,function(i,n){
            			tt+='<tr>';
            			tt+='<td style="width: 30px;"><input type="checkbox" style="opacity: 1;margin-top: -7px;" id="check_'+n.id+'" value="'+n.id+'" onchange="myCheck(\''+n.id+'\');"></td>';
            			if(n.parent_name==null || n.parent_name=="undefined" ){
							tt+='<td>公司分管领导</td>';
						}else{
            				tt+='<td>'+n.parent_name+'</td>';
						}
            			tt+='<td>'+n.name+'</td>';
            			tt+='<td>'+n.road_code+'</td>';
            			tt+='<td>'+n.road_name+'</td>';
            			tt+='<td>'+n.main_mileage+'</td>';
            			tt+='<td>'+formatDate(n.create_time)+'</td>';
            			tt+='<td style="width:120px;">';
            				tt+='<a class="btn btn-mini btn-info" title="详情" onclick="info(\''+n.id+'\');">详情</a>&nbsp;';
            			tt+='</td>';
            			tt+='</tr>';
            		});
            		$("#listCenter").html(tt);
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
