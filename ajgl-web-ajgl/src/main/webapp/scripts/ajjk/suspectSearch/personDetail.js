(function($){
	"use strict";
	$(document).ready(function() {
		$(document).on("click","#back",function(){
			history.go(-1);
		});
		$.ajax({
			url:context +'/suspectSearch/searchPersonDetail.action',
			type:'post',
			data:{dataId : $("#dataId").val()},
			dataType:'json',
			success:function(successData){
				$.each($(".valCell"),function(){
					if($(this).attr("valName") == "birthdayStart"){
						var arr = successData.criminalPersonBean[$(this).attr("valName")].split(" ");
						$(this).text(arr[0]);
					}else{
						$(this).text(successData.criminalPersonBean[$(this).attr("valName")]);
					}
				})
			}
		});
	});
})(jQuery);