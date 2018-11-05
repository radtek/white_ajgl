
(function($){
	var id="";
	var index;
	$(document).ready(function() {
		id = $("#attId").val();
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

