<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<%@ page import="com.taiji.pubsec.common.tool.comet.constant.Constants"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/base/skin/utilPart.jsp"%>

<%@include file="/common/library/comet/comet.jsp"%>
<script>
	var COMET_OORT_LOCAL_URL = '<%=Constants.getOORT_LOCAL_URL()%>' ;
</script>
<script>


	
	
	$(document).ready(function() {	
		
		$.comet.configure({
			url:COMET_OORT_LOCAL_URL,
			logLevel:"error"
		}) ;
		
		$.comet.handshake({
			additional:{
				credentials: {
					clientid:"test_push-Example"
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
				
		$(document).on("click", "#push-btn", function(){
			
	    	$.ajax({
	    		url: context + '/test/pushTest.action',
	    		type:"POST",	
	    		dataType:"json",
	    		success:function(data){
	    			
	    		}
	    	});
	    	
		});
	    
    });

	

</script>
</head>

<body class="m-ui-layer-body">

<button id="push-btn">推送</button>



<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>


</html>