(function($){
	"use strict";
	//文件上传
	var index = null;
	var policeTable = null;
	var id = null;
	var sendCardPeopleName = null;
	var sendCardPeopleCode = null;
	var sendCardTime = null;
	var relationWithCard = true;
	$(document).ready(function() {
		var p$ = window.top.$;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		var cre$ = frameData.initData.cre$;
		id = frameData.initData.id;
	});
	/**
	 * 获取手环号
	 */
	$(document).on("click","#wristband",function(){
		
		$("#wristband").attr("disabled", "disabled");
		var str = window.top.getDsspocxObject().GetDevAndBrandIDForThird();
		var obj = JSON.parse(str);
		$("#icNum").text(obj.data["BrandID"]);
		
		$.ajax({
			url:context +'/handlingAreaReceipt/findPoliceByBrandId.action',
			type:'post',
			data:{cardId : obj.data["BrandID"]},
			dataType:'json',
			success:function(successData){
				if(!$.util.isBlank(successData.piBean.id)){
					relationWithCard = false;	// 是否需要绑定定位卡
					$("#policeName").attr("readonly", "readonly");
					$("#policeNum").attr("readonly", "readonly");
					$("#remark").attr("readonly", "readonly");
					$("#ifMainPolice").iCheck('disable');
					$(".icheckbox_square-green").removeClass("disabled");
					id = successData.piBean.id;
					$("#policeName").val(successData.piBean.policeName);
					$("#policeNum").val(successData.piBean.policeNum);
					$("#remark").val(successData.piBean.remark);
					if(successData.piBean.ifMainPolice = $.common.Constant.SF_F()){
						$("#ifMainPolice").iCheck('check');
					}
					sendCardPeopleCode = successData.piBean.sendCardPeopleCode;
					sendCardPeopleName = successData.piBean.sendCardPeopleName;
					sendCardTime = successData.piBean.sendCardTime;
				}else{
					$("#wristband").removeAttr("disabled");
					relationWithCard = true;	// 是否需要绑定定位卡
				}
			}
		});
	});
	jQuery.extend($.common, { 
		getPoliceBean:function(){
			var demo = $.validform.getValidFormObjById("validformHandlingAreaReceipt") ;
			var flagDemo = $.validform.check(demo) ;
			if(!flagDemo){
				return;
			}
			var flag = $.common.Constant.SF_F();
			if($.icheck.isChecked("#ifMainPolice")) {
				flag = $.common.Constant.SF_S();
			}
			var policeBean = {
				"id" : id,
				"policeName" : $("#policeName").val(),
				"policeNum" : $("#policeNum").val(),
				"icNum" : $("#icNum").text(),
				"ifMainPolice" : flag,
				"remark" : $("#remark").val(),
				"sendCardPeopleName" : sendCardPeopleName == null ? window.top.currentUserName : sendCardPeopleName,
				"sendCardPeopleCode" : sendCardPeopleCode == null ? window.top.currentUserCode : sendCardPeopleCode,
				"sendCardTime" : sendCardTime == null ? (new Date()).getTime() : sendCardTime,
				"relationWithHar" : true,
				"relationWithCard" : relationWithCard
			};
			return policeBean;
		}
	});	
})(jQuery);