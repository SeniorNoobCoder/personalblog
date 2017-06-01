/**
 * Created by zcx on 2016/12/6.
 */
var setting = {
    check: {
        enable: true,
        chkStyle: "radio",
        radioType: "all"
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onCheck: zTreeOnClick
    }
};
function zTreeOnClick(event, treeId, treeNode) {
    $("#server_category_id").val(treeNode.id);
    $("#server_category_name").val(treeNode.name);
    $("#nameSel").val(treeNode.name);
};
function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
    zTree.expandAll(true);
}
function createTree(url){
    var treeNodes = '';
    $.ajax({
        url : url,
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
}
//显示菜单
function showMenu() {
    var nameObj = $("#nameSel");
    var nameOffset = $("#nameSel").offset();
    $("#menuContent").css({ left: nameOffset.left + "px", top: nameOffset.top + nameObj.outerHeight() + "px" }).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
//隐藏
function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
// 当点击窗口其他区域时zTree消息框隐藏
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
        hideMenu();
    }
}