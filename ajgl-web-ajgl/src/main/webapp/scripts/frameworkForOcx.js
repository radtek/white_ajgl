var snum = 0;
var timerJump = null;
var cardId = "";
var machineId = "";
var onlyFlag = true;
$(document).ready(function() {
	initWindow();
	$('body').css('overflow-y','hidden');
	var ifm = document.getElementById("ifm");
	var clientHeight = document.documentElement.clientHeight ;
 	$("#ifmWindow").height(clientHeight) ;
 	$(window).resize(function(){
 		var clientHeight = document.documentElement.clientHeight ;
 	 	$("#ifmWindow").height(clientHeight) ;
 	});
	setTimeout(function(){
		loginDH();
    }, 500);
	jumpOn();
});

function initWindow(){
	$.ajax({
		url:context +'/urlAfterLogin/findUrlByRole.action',
		type:'post',
		dataType:'json',
		success:function(successData){
			$("#ifmWindow").attr("src", context + successData.url);
		}
	})
}

function getDsspocxObject(){
	return document.getElementById("dsspocxObject");
}
function loginDH(){
		var customizeInfo = JSON.stringify({SupportDvr:0, ProductName:'P700', webPort:"80"});
		document.getElementById("dsspocxObject").SetExtendProperty(customizeInfo);
		/* cmsIp：大华平台访问ip; * webPort：大华平台访问端口； * loginName：用户登录账号； * loginPass：用户登录账号（明文） */
		//登录大华平台需要的账户密码应该是当前登陆人，但是现在大华库里的数据没有权限，暂时使用huan：123456登录
		document.getElementById("dsspocxObject").Login(dhipForOcx, dhportForOcx, usernameForOcx, passwordForOcx, "1");
}

function jumpOn(){
	if(timerJump == null){
		timerJump = window.setInterval(checkAndJump,1000);
	}
}

function jumpOff(){
	window.clearInterval(timerJump);
	timerJump = null;
}

function checkAndJump(){
	var str = getDsspocxObject().GetDevAndBrandIDForThird();
	var obj = JSON.parse(str);
	if(cardId == obj.data["BrandID"] && machineId == obj.data["DevID"]){
		return;
	}else{
		cardId = obj.data["BrandID"];
		machineId = obj.data["DevID"];
		setTimeout(function(){
			cardId = "";
			machineId = "";
	    }, 5000);
		
	}
	if(!$.util.isBlank(cardId) && !$.util.isBlank(machineId)){
		 $.ajax({
			url:context +'/handlingAreaReceipt/findUrl.action',
			type:'post',
			data:{
				cardId : cardId,
				machineId : machineId
			},
			dataType:'json',
			success:function(successData){
				if(!$.util.isBlank(successData.url)){
					onlyFlag = true;
					var win = $('#ifmWindow')[0].contentWindow;  
					win.location.href = context + successData.url;
				}else{
					selectHandlingAreaReceipt(cardId, machineId);
				}
			}
		 });
	}
}
function selectHandlingAreaReceipt(cardId, machineId){
	if(onlyFlag){
		onlyFlag = false;
		setTimeout(function(){
			onlyFlag = true;
		}, 15000);
	}else{
		return;
	}
	
	var initData = {
			cre$ : $,
			cardId : cardId,
			machineId : machineId,
			modify : "true"
		}
		window.top.$.layerAlert.dialog({
			content : context +'/handlingAreaReceipt/showSelectHandlingAreaReceipt.action',
			pageLoading : true,
			title:"查看办案民警信息",
			width : "850px",
			height : "500px",
			initData:function(){
				return $.util.exist(initData)?initData:{} ;
			},
			shadeClose : false,
    		success:function(layero, index){
    			
    		},
    		btn:["跳转","取消"],
    		callBacks:{
			    btn1:function(index, layero){
			    	var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
			    	harId = cm.getHarId();
			    	if(harId == false){
			    		return;
			    	}
			    	selectAfterJump(harId, cardId, machineId, index);
			    },
	    		btn2:function(index, layero){
	    			onlyFlag = true;
			    	window.top.layer.close(index);
			    }
			}
		});
	
	function selectAfterJump(harId, cardId, machineId, thsindex){
		$.ajax({
			url:context +'/handlingAreaReceipt/findUrl.action',
			type:'post',
			data:{
				id : harId,
				cardId : cardId,
				machineId : machineId
			},
			dataType:'json',
			success:function(successData){
				if(!$.util.isBlank(successData.url)){
					onlyFlag = true;
					var win = $('#ifmWindow')[0].contentWindow;  
					win.location.href = context + successData.url;
					window.top.layer.close(thsindex);
				}else{
					onlyFlag = true;
				}
			}
		});
	}
}
