
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
		$("#getPeople").val(initData.receiver);
		$("#idNum").val(initData.receiverIdCard);
	});
	/**
	 * 向父页面暴露的方法
	 */
	jQuery.extend($.common, { 
		getReceiver:function(obj){
			var demo = $.validform.getValidFormObjById("validformId") ;
			var flag = $.validform.check(demo) ;
			if(!flag){
				return null;
			}
			return $("#getPeople").val();
		},
		getReceiverIdCard:function(obj){
			var demo = $.validform.getValidFormObjById("validformId") ;
			var flag = $.validform.check(demo) ;
			if(!flag){
				return null;
			}
			return $("#idNum").val();
		},
		setIndex:function(diaIndex){
			index=diaIndex; 
		}
	});	
})(jQuery);

