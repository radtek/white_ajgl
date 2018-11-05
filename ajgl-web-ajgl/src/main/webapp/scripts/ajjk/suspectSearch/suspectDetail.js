(function($){
	"use strict";
	$(document).ready(function() {
		$(document).on("click","#back",function(){
			history.go(-1);
		});
		$.ajax({
			url:context +'/suspectSearch/searchSuspectDetail.action',
			type:'post',
			data:{dataId : $("#dataId").val()},
			dataType:'json',
			success:function(successData){
				$.each($(".valCell"),function(){
					$(this).text(successData.caseSupectRelationBean[$(this).attr("valName")]);
				})
				$.each($(".valShow"),function(){
					if(successData.caseSupectRelationBean[$(this).attr("valName")] != "是"){
						$("tr[valTr=" + $(this).attr("valName") + "]" ).hide();
					}
				})
			}
		});
	});
})(jQuery);