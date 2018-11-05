(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var lockerId = initData.lockerId;	//储物箱id
	var articleLockerBean ;
	var oldLocation='';
	$(document).ready(function() {	
		$.when(
//				$.ajax({
//					url:context +'/storageArea/findStorageAreaByPaging.action',
//					type:'post',
//					dataType:'json',
//					data:{start : 0,length:100000},
//					success:function(json){
//						var storageAreaBeanList = json.storageAreaBeanList;
//						 $.select2.addByList("#storageArea", storageAreaBeanList, "id", "name", true, true);    //初始化
//					}
//				}),
				$.ajax({
					url:context +'/lockerMaintain/lookLocker.action',
					type:'post',
					dataType:'json',
					data:{
						"lockerId":lockerId
					},
					success:function(json){
						articleLockerBean = json.articleLockerBean;
						$("#code").text(articleLockerBean.code);
						$("#location").val(articleLockerBean.location);
						$("#remake").val(articleLockerBean.remark);
						oldLocation=articleLockerBean.location;
						$("#storageArea").text(articleLockerBean.area.name);
						$("#storageArea").attr("selId",articleLockerBean.area.id);
						if(articleLockerBean.status==$.common.Constant.SF_S()){
							$('#lockerStatus').text("是");
						}else if(articleLockerBean.status==$.common.Constant.SF_F()) {
							$('#lockerStatus').text("否");
						}
					}
				})
				
		    ).done(function(){
//		    	$("#storageArea").select2("val",articleLockerBean.area.id);   
//		    	$.select2.able("#storageArea",false);
		    	showImg("cwj" + $("#code").text());
		    });
	});
	
	/**
	 * 保存方法
	 */
	function saveLocker(){
		var demo = $.validform.getValidFormObjById("validform") ;
		var flag = $.validform.check(demo) ;
		if(!flag){
			return ;
		}
		var dataMap = new Object();
		dataMap["articleLockerBean.code"] = $("#code").text();
		dataMap["articleLockerBean.location"] = $("#location").val();
		dataMap["articleLockerBean.area.id"] = $("#storageArea").attr("selId");    //    取值
//		dataMap["articleLockerBean.area.id"] = $("#storageArea").select2("val");    //    取值
		dataMap["articleLockerBean.remark"] = $("#remake").val();
		dataMap["oldLocation"] = oldLocation;
		dataMap["articleLockerBean.id"] = articleLockerBean.id;
		if($("#lockerStatus")=='是'){
			dataMap["articleLockerBean.status"] = $.common.Constant.SF_S();
		}else if($("#lockerStatus")=='否'){
			dataMap["articleLockerBean.status"] = $.common.Constant.SF_F();
		}
		$.ajax({
			url:context +'/lockerMaintain/modifyLocker.action',
			type:'post',
			dataType:'json',
			data:dataMap,
			success:function(json){
				window.top.$.layerAlert.alert({msg:json.msg});
				if(json.flag=="true"){
					window.top.$.layerAlert.closeWithLoading(pageIndex);
				}
			}
		});
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
		saveLocker : saveLocker
	});
	
})(jQuery);