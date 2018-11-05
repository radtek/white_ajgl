(function($) {
	"use strict";
	var p$ = window.top.$;
	var frameData = p$.layerAlert.getFrameInitData(window) ;
	var loginName = frameData.initData.loginName;
	$(document).ready(function() {
		$("#userName").text(loginName);
	});
	function save(index){
		var originPassword = $("#originPassword").val();
		var newPassword = $("#newPassword").val();
		var confirmNewPassword = $("#confirmNewPassword").val();
		if($.util.isBlank(originPassword) ){
			alertHint("原始密码不允许为空","5");
			return;
		}
		if($.util.isBlank(newPassword) ){
			alertHint("新密码不允许为空","5");
			return;
		}
		if($.util.isBlank(newPassword) ){
			alertHint("再次确认密码不允许为空","5");
			return;
		}
		if(newPassword != confirmNewPassword ){
			alertHint("新密码输入不一致","5");
			return;
		}
		$.ajax({
		    url: context + '/personInfoImage/changePasswordAction.action',
    		type:"POST",	
    		data:{
    		"userLoginName":loginName,
    		"originPassWord":originPassword,
		    "newPassWord": newPassword,
		    "confirmNewPassword":confirmNewPassword
    		},
    		dataType:"json",
    		async:false,
    		success:function(data){
               if(data.flag == "false"){
            	   alertHint(data.message,"5");
               }else{
            	   $.layerAlert.alert({
           			msg:data.message,
           			title:"提示",		//弹出框标题
           			width:'300px',
           			hight:'200px',
           			shade: [0.5,'black'],  //遮罩
           			icon:"6",				//弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
           			shift:1,
           			end:function(){
           				window.top.$.layerAlert.closeWithLoading(index)
           			}         //弹出时的动画效果  有0-6种
           		});
               }
    		}
	});
		
	}
	function alertHint(msg,icon){
 		$.layerAlert.alert({
 			msg:msg,
 			title:"提示",		//弹出框标题
 			width:'300px',
 			hight:'200px',
 			shade: [0.5,'black'],  //遮罩
 			icon:icon,				//弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
 			shift:1,			//弹出时的动画效果  有0-6种
 		});
 	}
	jQuery.extend($.common, { 
        save:save
	});	
})(jQuery);