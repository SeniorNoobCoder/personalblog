	function closeDiv(){
		$("#leftDiv").hide();
		$("#leftDiv2").show();
		$("#rightDiv").attr("style","float: left; width: 96%");
	}
	function openDiv(){
		$("#leftDiv").show();
		$("#leftDiv2").hide();
		$("#rightDiv").attr("style","float: left; width: 75%");
	}
	
	
	function showTools(flag){
		if(flag=='part'){
			$("#tools_all").css("display","block");
			$("#tools_part").css("display","none");
			$("#tools_span").css("display","none");
		}else if(flag=='all'){
			$("#tools_all").css("display","none");
			$("#tools_part").css("display","block");
			$("#tools_span").css("display","block");
		}
	}