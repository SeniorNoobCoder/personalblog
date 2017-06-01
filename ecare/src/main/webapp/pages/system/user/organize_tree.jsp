<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<script type="text/javascript">
   var setting = {
			
			check: {
				enable: true,
				chkStyle: "radio",
//				chkboxType : { "Y" : "", "N" : "" }
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeCheck: zTreeBeforeCheck,
				onCheck: onCheck
			}
		};

		$(document).ready(function (){ 
			var treeNodes = '';
			$.ajax({
			    url : "${pageContext.request.contextPath}/system/user/findOraganize.do?user_id=${pd.user_id}",
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
						treeNodes += "},";
					});
					treeNodes = treeNodes.substr(0,treeNodes.length-1)+"]"
					if(treeNodes != ']'){
						$.fn.zTree.init($("#treeDemo"), setting ,eval(treeNodes));
						openAll();
					}
			    },
			    error : function(xhr, status) {
			        alert('Sorry, there was a problem!');
			    },
			    complete : function(xhr, status) {
			    }
			});
		});

		function onCheck(e, treeId, treeNode) {
			//alert(getParentNode(treeNode,true));
			//alert(getCheckChildNodes(treeNode,true,new Array()).length);
			//fpqx(treeNode);
		}
		var bfarray;
		var bfp_array;
		function zTreeBeforeCheck(treeId, treeNode) {
			//alert(getParentNode(treeNode,true).length);
			//alert(getCheckChildNodes(treeNode,true,new Array()).length);
			bfarray = getCheckChildNodes(treeNode,true,new Array());
			bfp_array = getParentNode(treeNode,true);
		}
		
		/*右侧默认显示*/
	function rightDefault(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodesByFilter(filter2);
		if(nodes!=null&&nodes!=''){
			show(nodes[0].urls);
		}
	}
	
	//展开第一个节点
	function expandFirst(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodes();
		treeObj.expandNode(nodes[0],true,false,true,true);
		show(nodes[0].children[0].id,nodes[0].children[0].name);
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

		
	function getParentNode(treeNode,checked){
		var ps = new Array();
		var pn;
		while((pn=treeNode.getParentNode())) {
			if(pn.checked==checked){
				ps.push(pn);
			}
			treeNode = pn;
		}
		return ps;
	}
	
	function getCheckChildNodes(treeNode,checked,cs) {
		var ccs;
		if((ccs=treeNode.children)) {
			for(var i=0;i<ccs.length;i++) {
				var c = ccs[i];
				if(c.checked==checked) {
					cs.push(c);
				}
				getCheckChildNodes(c,checked,cs);
			}
		}
		return cs;
	}
	
	
	function getCheckedChildNodesNum(treeNode) {
		var p = treeNode.getParentNode();
		var ccs;
		var num = 0;
		if(p!=null&&p!=''){
			if((ccs=p.children)) {
				for(var i=0;i<ccs.length;i++) {
					var c = ccs[i];
					if(c.checked) {
						num++;
					}
				}
			}
		}
		return num;
	}
	
</script>
