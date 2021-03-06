(function($){
	"use strict";
	var index = null;
	var riqiList=["a10","a14","a19","a22","a24","b9","b12","b16","c9","c12","c16","d7","d11","d13","d16"];
	$(document).ready(function() {
		$(document).on("click",".btn",function(){
			$(".btn").removeClass("btn-danger");
			$(".btn").addClass("btn-primary");
			$(this).removeClass("btn-primary");
			$(this).addClass("btn-danger");
			$(".writDiv").hide();
			$("#" + $(this).attr("divId")).show();
		});
		initWrit();
	});

	function initWrit(){
		$.ajax({
			url:context +'/showWrit/sftzs.action',
			type:'post',
			data:{writId : $("#writId").val()},
			dataType:'json',
			success:function(successData){
				var bean = successData.sftzsBean;
				$.each($(".valCell"),function(){
					if(isRIQI($(this).attr("valName")) && bean[$(this).attr("valName")]!=null && bean[$(this).attr("valName")].length>10 ){
						$(this).text(bean[$(this).attr("valName")].substring(0,10));
					}else{
						$(this).text(bean[$(this).attr("valName")]);
					}
				})
			}
		});
	}
	function isRIQI(data){
		return riqiList.indexOf(data)!=-1
	}
})(jQuery);