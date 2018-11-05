(function($) {
	"use strict";

	$(document).ready(function() {	

		var warningSettings = {
			id:"warningHint",
			dialog:{
				title:"预警消息",
				shift:2
			},
			callBacks:{
				formatMsgCallBack:function(msgs, id, obj, settings){
					var div = $("<div />") ;
					var val = msgs.pop();
					if(val == null){
						return false;
					}
					$("<div />", {
						"msgId":val.id,
						"timestamp":val.timestamp,
						"text":val.msg.context
					}).appendTo(div) ;
					setTimeout(timeOutFun(id, val.id),val.msg.time);
					return div ;
				}
			}
		}
		
		$.msghint.init(warningSettings) ;
		
		$.comet.configure({
			url:COMET_OORT_URL,
			logLevel:"error"
		}) ;

		$.comet.handshake({
			additional:{
				credentials: {
					clientid: currentAccNameForPush
				}
			},
			handshakeSuccess:function(handshakeReply){
				var lsn = $.comet.addListener({
					url:'/service/receive/hint', 
					msgCallBack:function(message){
						$.util.log("^^^^^^^^^^^^^^^^^^^^receiveCallBack^^^^^^^^^^^^^^^^^^^^^^^^") ;
						$.util.log(message) ;
						
						var data = message.data ;
						var info = $.util.parseJsonByEval(data.msgJSON) ;
						
						var context = info.businessData;
						var time = info.existingTime;
						var msg = {
								"context" : context,
								"time" : time,
							};
						$.msghint.addMsg("warningHint", msg);
						$.msghint.show("warningHint");
						
						$.util.log(info) ;

						$.util.log("^^^^^^^^^^^^^^^^^^^^receiveCallBack^^^^^^^^^^^^^^^^^^^^^^^^") ;
					}
				}) ;
				
				var subscribe = $.comet.subscribe({
					url:"/comet/broadcast",
					additional:{
			     	},
			     	msgCallBack:function(message){
						$.util.log("^^^^^^^^^^^^^^^^^^^^broadcast_receiveCallBack^^^^^^^^^^^^^^^^^^^^^^^^") ;
						$.util.log(message) ;
						
						var data = message.data ;
						var info = $.util.parseJsonByEval(data.msgJSON) ;
						
						$.util.log(info) ;
						$.util.log("^^^^^^^^^^^^^^^^^^^^broadcast_receiveCallBack^^^^^^^^^^^^^^^^^^^^^^^^") ;
					},
					repyCallBack:function(subscribeReply){
					  	$.util.log("^^^^^^^^^^^^^^^^^^^^broadcast_subscribeReply^^^^^^^^^^^^^^^^^^^^^^^^") ;
					  	$.util.log(subscribeReply) ;
						$.util.log("^^^^^^^^^^^^^^^^^^^^broadcast_subscribeReply^^^^^^^^^^^^^^^^^^^^^^^^") ;
					}
				}); 
			},
			handshakeError:function(handshakeReply){
				
			}
		}) ;
	});

	function timeOutFun(mhId, msgId){
		function temp(){
			$.msghint.close(mhId);
			$.msghint.removeMsg(mhId, msgId);
			$.msghint.show(mhId);
		}
		return temp;
	}
	
})(jQuery);