$.comet = $.comet || {};
;(function($) {
	"use strict";
	
	var boxes = {
			
	};
	
	var basicSettings = {
		url:null,
		logLevel:'debug'
	}
	
	
	
	jQuery.extend($.comet, {
		configure:function(opt){
			var tpsettings = $.util.cloneObj(basicSettings) ;
			$.util.mergeObject(tpsettings, opt) ;
			
	        $.cometd.configure({
	            url: tpsettings.url,
	            logLevel: tpsettings.logLevel
	        });
		},
		getBasicSettings:function(){
			return $.util.cloneObj(basicSettings) ;
		},
		handshake:function(handshakeSettings){
			/**
			 * 
			 * 	handshakeSettings = {
			 *		additional:{
			 *			credentials: {
			 *				clientid:"" //必须
			 *			}
			 *			
			 *		},	
			 *		handshakeSuccess:function(handshakeReply){
			 *			
			 *		},
			 *		handshakeError:function(handshakeReply){
			 *			
			 *		}
			 *	}
			 * 
			 * 
			 */
			
	        var handshake = $.cometd.handshake(handshakeSettings.additional, function(handshakeReply){
	        	if(handshakeReply.successful){
	        		$.util.log("连接服务器成功:");
	        		$.util.log(handshakeReply) ;
	        		handshakeSettings.handshakeSuccess(handshakeReply) ;
	        	}else{
	        		$.util.log("连接服务器失败:");
	        		$.util.log(handshakeReply) ;
	        		handshakeSettings.handshakeError(handshakeReply) ;
	        	}
	        });
			
			return handshake ;
		},
		addListener:function(settings){
			/**
			 * settings = {
			 * 	 url:url
			 *   msgCallBack:function(message){
			 *   
			 *   }
			 * }
			 */
			var listener = $.cometd.addListener(settings.url, function(message){
				settings.msgCallBack(message) ;
			});
			return listener ;
		},
		removeListener:function(listener){
			$.cometd.removeListener(listener);
		},
		subscribe:function(settings){
			/**
			 * settings = {
			 * 	 url:url
			 * 	 additional:{
			 * 
			 * 	 },
			 *   msgCallBack:function(message){
			 *   
			 *   },
			 *   repyCallBack:function(subscribeReply){
			 *   
			 *   }
			 * }
			 */
			var subscribe = $.cometd.subscribe(settings.url, function(message) {
				settings.msgCallBack(message) ;
			}, settings.additional, function(subscribeReply){
				settings.repyCallBack(subscribeReply) ;
			});
			
			return subscribe ;
		},
		unsubscribe:function(subscribe){
			$.cometd.unsubscribe(subscribe);
		},
		
	});	

})(jQuery);
