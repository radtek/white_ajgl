
	//dome js
	 var personNum = "";
	 var count=0;//记录条数
	    $(document).ready(function() {
	    	$('#researchCount').hide();
			testLoad();
			onloadTS();
		});
	    
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
	    		
	    		//$.msghint.init(warningSettings) ;
	    	
	    		$.comet.configure({
	    			url:agtSZPTDLServerIp,
	    			logLevel:"error"
	    		}) ;
	
	    		$.comet.handshake({
	    			additional:{
	    				credentials: {
	    					clientid: '06fd74d7-d914-4f4c-b357-7edbacfacc7a'
	    				}
	    			},
	    			handshakeSuccess:function(handshakeReply){
	    				var subscribe = $.comet.subscribe({
	    					url:"/comet/broadcast/enterbaq",
	    					additional:{
	    			     	},
	    			     	msgCallBack:function(message){
	    						$.util.log("^^^^^^^^^^^^^^^^^^^^broadcast_receiveCallBack^^^^^^^^^^^^^^^^^^^^^^^^") ;
	    						$.util.log(message) ;
	    						var data = message.data ;
	    						var info = $.util.parseJsonByEval(data.msgJSON) ;
//	    						$.util.log(info) ;
	    						//数据处理
//	    						personNum="130412199001233418";//伪数据,
	    						if(info.identy==personNum){//将转换成数字的身份证号转换回来
	    							count++;
	    							showNewMessage("您有新消息", true);
	    							$('#researchCount').show();
	    							$('#researchCount').text(count);
	    							
	    						}
	    						$.util.log("^^^^^^^^^^^^^^^^^^^^broadcast_receiveCallBack^^^^^^^^^^^^^^^^^^^^^^^^") ;
	    					},
	    					repyCallBack:function(subscribeReply){
	    					}
	    				}); 
	    			},
	    			handshakeError:function(handshakeReply){
	    				
	    			}
	    		}) ;
	    }
	    
	  
	    
	    function timeOutFun(mhId, msgId){
	    	function temp(){
	    		$.msghint.close(mhId);
	    		$.msghint.removeMsg(mhId, msgId);
	    		$.msghint.show(mhId);
	    	}
	    	return temp;
	    }
	    
	    function testLoad() {
	        loadComet();
	    }
	    
	    function GetQueryString(name) {
	        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	        var r = window.location.search.substr(1).match(reg);
	        if (r != null) return unescape(r[2]); return null;
	    }
	    function getPersonNum() {
	    	return 	GetQueryString("personNum");
	        
	    }
	    function loadComet() {
	        //根据personNum初始化推送
	    	personNum = getPersonNum();
	    }
	    

	    /**
	     * 打开研判
	     * @returns
	     */
	    function clk3() {
			
	    	count=0;
	    	$('#researchCount').hide();
	    	openWebForm("研判页面","研判页面",agtSZPTServerIp+"/enterbaq/showResearchMessage.action?identy="+personNum,803,653);
		}
	 
	    function clkExit() {
	        parent.window.exitForm();
	    }
	    
	    function myStock() {
	       parent.window.Stock();
	    }
	  //设置小图标鼠标悬浮文字,并指定是否闪动图标
		function showNewMessage(msgText, isStock) {
			window.external.showNewMessage(msgText, isStock);
		}
		/**
    	 * 开启窗口
    	 */
    	function openWebForm(formId,formName,Url, formWidth, formHeight)
    	{
    	    window.external.openUrlByWinForm(formId,formName,Url, formWidth, formHeight);
    	}
