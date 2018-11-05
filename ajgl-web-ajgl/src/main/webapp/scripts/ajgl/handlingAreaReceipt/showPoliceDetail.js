(function($){
	"use strict";
	//文件上传
	var index = null;
	var policeTable = null;
	$(document).ready(function() {
		var p$ = window.top.$;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		var cre$ = frameData.initData.cre$;
		var data = frameData.initData.data;
		$("#policeName").text(data.policeName);
		$("#policeNum").text(data.policeNum);
		$("#icNum").text($.util.isBlank(data.icNum)?"":data.icNum);
		if(data.ifMainPolice == $.common.Constant.SF_S()){
			$("#ifMainPolice").iCheck('check');
			var t1 = window.setTimeout(setColor,200);
		}
		$("#remark").text(data.remark == null?"":data.remark);
		$("#sendCardPeopleName").text(data.sendCardPeopleName);
		$("#sendCardTime").text($.date.timeToStr(data.sendCardTime, "yyyy-MM-dd HH:mm"));
	});
	
	function setColor(){
		$(".icheckbox_square-green").removeClass("disabled");
	}
})(jQuery);