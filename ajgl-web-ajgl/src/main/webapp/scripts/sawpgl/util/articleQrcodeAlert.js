(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var articleCode = initData.articleCode;	//物品编号
	var involvedArticleBean;
	$(document).ready(function() {	
		init();
	});
	
	//设置字段值
	function init(){
		$.ajax({
			url:context +'/storageIn/findArticleByCode.action',
			type:'post',
			dataType:'json',
			data:{
				"articleCode":articleCode
			},
			success:function(json){
				involvedArticleBean = json.involvedArticleBean;
				if($.util.exist(involvedArticleBean)){
					if($.util.isBlank(involvedArticleBean.code)){
						$("#code").closest("p").hide();
					}else{
						$("#code").text(involvedArticleBean.code);
					}
					if($.util.isBlank(involvedArticleBean.name)){
						$("#name").closest("p").hide();
					}else{
						$("#name").text(involvedArticleBean.name);
					}
					if($.util.isBlank(involvedArticleBean.feature)){
						$("#feature").closest("p").hide();
					}else{
						$("#feature").text(involvedArticleBean.feature);
					}
					if($.util.isBlank(involvedArticleBean.typeName)){
						$("#typeName").parent("div").parent("div").hide();
					}else{
						$("#typeName").text(involvedArticleBean.typeName);
					}
					
					showImg(involvedArticleBean.code);
				}
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
				text: involvedArticleBean.code,
				size: 500,
				radius: 0,
				quiet: 1,
				mode: 2,
				label: "涉案物品",
				labelsize: 0.15
			};
		$(qr).qrcode(options);
		$(qr).css("margin-left", "10px");
		$(qr).find("img").css("width", "150px");
		$(obj).append(qr);
		var str="";
		str+="<span style='font-size:15px;'>" ;//修改字体大小
		if(!$.util.isBlank(involvedArticleBean.code)){
			str+="物品编号：<br/>" + (involvedArticleBean.code.substring(0, 15)) + "<br/>" +
			 + (involvedArticleBean.code.substring(15, involvedArticleBean.code.length)) + "<br/>"
		}
		if(!$.util.isBlank(involvedArticleBean.name)){
			str+="物品名称：<br/>" + involvedArticleBean.name + "<br/>" ;
		}
		if(!$.util.isBlank(involvedArticleBean.feature)){
			str+="物品特征：<br/>" + involvedArticleBean.feature + "<br/>" ;
		}
		str+="<span>";
		$(obj).append(str);
//		$(obj).append("<span style='font-size:20px;'>" +
//				"物品编号：<br/>" + (involvedArticleBean.code.substring(0, 15)) + "<br/>" +
//				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (involvedArticleBean.code.substring(15, involvedArticleBean.code.length)) + "<br/>" +
//				"物品名称：<br/>" + (involvedArticleBean.name==null?"":involvedArticleBean.name) + "<br/>" +
//				"物品特征：<br/>" + (involvedArticleBean.feature==null?"":involvedArticleBean.feature) + "<br/>" +
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