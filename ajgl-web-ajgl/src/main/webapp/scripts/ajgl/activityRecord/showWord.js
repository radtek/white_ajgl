
(function($){
	var id="";
	var index;
	var p$;
	var up$;
	$(document).ready(function() {
		p$ = window.top.$ ;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		var initData = frameData.initData ;
		id = initData.attId;
		//iweboffice
		var setting = {
			containerId:"iwebOffice-container"	
		}
		$.iWebOffice2000.init(setting) ;   
		$.ajax({
			url : context + '/activityRecord/findWordByHarId.action',
			type : "POST",
			dataType : "json",
			data :{
				id : id
			},
			success : function(data) {
				if(!$.util.isBlank(data.wordName)){
					$.iWebOffice2000.openServerDoc("iwebOffice-container", data.wordName, $.iWebOffice2000.Constant["EditType"]["ReadOnly"], $.iWebOffice2000.Constant["ShowMenu"]["hide"]);		
				}else{
					$.layerAlert.alert({msg:"文档打开失败！"});
				}
			},
		});
	});
})(jQuery);

