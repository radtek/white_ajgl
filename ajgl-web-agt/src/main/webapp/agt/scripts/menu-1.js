


	var roomNum="";
	var personNum = "";
	var formCode="";
	var formId="";
	var jyNum='';
	var count =0;
	$(document).ready(function() {
		onloadData();
		onloadTS();		
	});
	function timeOutFun(mhId, msgId){
		function temp(){
			$.msghint.close(mhId);
			$.msghint.removeMsg(mhId, msgId);
			$.msghint.show(mhId);
		}
		return temp;
	}
	
	function onloadTS(){
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
				url:agtServerIp+"/cometd",
				logLevel:"error"
			}) ;
			$.comet.handshake({
				additional:{
					credentials: {
						clientid: getconfigRoomNum()
					}
				},
				handshakeSuccess:function(handshakeReply){
					var lsn = $.comet.addListener({
						url:'/service/receive/hint', 
						msgCallBack:function(message){
							$.util.log("^^^^^^^^^^^^^^^^^^^^receiveCallBack^^^^^^^^^^^^^^^^^^^^^^^^") ;
							addMessage();
							/* $.util.log(message) ;
							alert(1);
							var data = message.data ;
							var info = $.util.parseJsonByEval(data.msgJSON) ;
							alert(info);
							var context = info.businessData;
							var time = info.existingTime;
							var msg = {
									"context" : context,
									"time" : time,
								};
							$.msghint.addMsg("warningHint", msg);
							$.msghint.show("warningHint");
							
							$.util.log(info) ; */

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
	}
	
	function testTS(){
		$.ajax({
			url : context + '/agtTaskbar/findMessageTS.action',
			type : "POST",
			dataType : "json",
			data:{
				"formCode":"12",
			},
			success:function(data){
				//alert(" ajax 成功");
			}
		});
	}
	
	function addMessage(){
		count++;
		showNewMessage("您有新消息", true);
		$('#messageCount').show();
		$('#messageCount').text(count);
	}

    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    //根据地址栏取得房间号
    function getRoomNum() {
        return GetQueryString("roomNum");
    }
     //根据地址栏取得警员
    function getjyName() {
        return GetQueryString("jyName");
    }
    /**
     * 读取配置文件的房间id
     * @returns
     */
    function getconfigRoomNum() {
	    roomNum = getConfig("Basic", "RoomNumber");
	  return roomNum;
	} 

    /**
     * 加载页面内容
     */
    function onloadData() {
    	$('#messageCount').hide();
    	roomNum = getRoomNum();   	
    	$.ajax({
    		url : context + '/agtLogin/findData.action',
    		type : "POST",
    		dataType : "json",
    		data:{
    			"roomId":roomNum,
    		},
    		success:function(json){
    			var data=json.resultMap.loginBean;
    			formCode=data.handlingAreaReceiptNum;
    			formId=data.handlingAreaReceiptId;
    			data.byQuestioningPeopleIdentifyNum;
				personNum=data.byQuestioningPeopleIdentifyNum;				
				if(personNum=='无'){
				  personNum='00000000';
				}
    			parent.window.setPersonNum(personNum);  //身份证号传给父
    		},error:function(){
               
    		}
    	});
    	
    }
    
    function clk() {
    	count=0;
    	$('#messageCount').hide();
        parent.window.openWebForm("功能页面","功能页面",agtServerIp+"/agtTaskbar/messageAlert.action?formCode="+formCode,803,653);
    }
    function clk2()
    {
    	jyNum =getjyName();
 		 parent.window.openWebForm("功能页面","功能页面",agtServerIp+"/agtTaskbar/suspectMessage.action?formId="+formId+"&jyName="+jyNum,803,653);
    }
    function myStock() {
        parent.window.Stock();
    }
    /**
     * 根据表单号查询信息
     * @returns
     */
    function init(formCode){/*
    	$.ajax({
    		url : context + '/agtTaskbar/findMessageCount.action',
    		type : "POST",
    		dataType : "json",
    		data:{
    			"formCode":formCode,
    		},
    		success:function(data){
    			var count=data.resultMap.count;
    			if(count==0){
    				$('#messageCount').hide();
    			}else{
    				showNewMessage("您有新消息", true); 
    				$('#messageCount').show();
    			    $('#messageCount').text(count);
    			}
    		},error:function (data){
    		}
    	});
    */}
  //设置小图标鼠标悬浮文字,并指定是否闪动图标
	function showNewMessage(msgText, isStock) {
		window.external.showNewMessage(msgText, isStock);
	}
    
