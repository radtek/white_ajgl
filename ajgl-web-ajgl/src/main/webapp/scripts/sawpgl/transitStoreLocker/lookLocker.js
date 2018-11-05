(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var lockerId = initData.lockerId;	//储物箱id
	var articleLockerBean;
	$(document).ready(function() {	
		init();
	});
	
	//设置字段值
	function init(){
		$.ajax({
			url:context +'/transitStoreLocker/lookTransitStoreLocker.action',
			type:'post',
			dataType:'json',
			data:{
				"lockerId":lockerId
			},
			success:function(json){
				articleLockerBean = json.articleLockerBean;
				if($.util.isBlank(articleLockerBean.code)){
					$("#code").closest("p").hide();
				}else{
					$("#code").text(articleLockerBean.code);
				}
				if($.util.isBlank(articleLockerBean.location)){
					$("#location").closest("p").hide();
				}else{
					$("#location").text(articleLockerBean.location);
				}
				if($.util.isBlank(articleLockerBean.area.name)){
					$("#storageArea").closest("p").hide();
				}else{
					$("#storageArea").text(articleLockerBean.area.name);
				}
				if($.util.isBlank(articleLockerBean.remark)){
					$("#remark").closest("p").hide();
				}else{
					$("#remark").text(articleLockerBean.remark);
				}
//				$("#code").text(articleLockerBean.code);
//				$("#location").text(articleLockerBean.location);
//				$("#storageArea").text(articleLockerBean.area.name);   
//				$("#remark").text(articleLockerBean.remark);
				showImg("cwj" + articleLockerBean.code);
			}
		});
	}
	
	/**
	 * 打印
	 */
	function print(){
		var obj = $('<div></div>');
		var qr = $('<div></div>');
		var options = {
				render: "image",
				ecLevel: "H",
				minVersion: 6,
				color: "#333333",
				bgColor: "#ffffff",
				text: articleLockerBean.code,
				size: 500,
				radius: 0,
				quiet: 1,
				mode: 2,
				label: "储物箱",
				labelsize: 0.15
			};
		$(qr).qrcode(options);
		$(qr).css("margin-left", "10px");
		$(qr).find("img").css("width", "150px");
		$(obj).append(qr);
		var str="";
		str+="<span style='font-size:15px;'>" ;//修改字体大小
		if(!$.util.isBlank(articleLockerBean.code)){
			str+="编号：" + articleLockerBean.code + "<br/>" ;
		}
		if(!$.util.isBlank(articleLockerBean.location)){
			"储物箱位置：<br/>" + articleLockerBean.location + "<br/>"  ;
		}
		if(!$.util.isBlank(articleLockerBean.area.name)){
			str+="所属物证保管区：<br/>" + articleLockerBean.area.name + "<br/>"  ;
		}
		str+="<span>";
		$(obj).append(str);
//		$(obj).append("<span style='font-size:20px;'>" +
//				"编号：" + articleLockerBean.code + "<br/>" +
//				"储物箱位置：<br/>" + (articleLockerBean.location==null?"":articleLockerBean.location) + "<br/>" +
//				"所属物证保管区：<br/>" + articleLockerBean.area.name + "<br/>" +
//				"<span>");
		printdiv(obj);
	}
	
	var HKEY_Root,HKEY_Path,HKEY_Key;
	HKEY_Root="HKEY_CURRENT_USER";
	HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	//设置网页打印的页眉页脚为空
	function PageSetup_Null()
	{
	      var Wsh=new ActiveXObject("WScript.Shell");
	      HKEY_Key="header";
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
	      HKEY_Key="footer";
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
	      HKEY_Key="margin_left" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--左边边界

	      HKEY_Key="margin_top" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--上边边界

	      HKEY_Key="margin_right" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--右边边界

	      HKEY_Key="margin_bottom" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--下边边界
	}

	function printdiv(obj)
	{  
		var printData = $(obj).html();
		var win = window.open();
		win.document.body.innerHTML = printData;
		try
	    {
	        PageSetup_Null();
	        win.print();
	    }
	    catch(e)
	    {
	        var errorMsg = e.message+"\r"+"请设置:IE选项->安全->Internet->"+"ActiveX控件和插件"+"\r"+"对未标记为可安全执行脚本的ActiveX的控件初始化并执行脚本->允许/提示";
	        alert(errorMsg);
	        return;
	    }
		win.close();
		return false;
	}

	/**
	 * 显示二维码
	 */
	function showImg(text){
		$("#tdcImg").qrcode({
		    "render": 'image',
		    "size": 150,
		    "color": "#3a3",
		    "background": "white",
		    "text": text
		});
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		print : print
	});
	
	
	
})(jQuery);