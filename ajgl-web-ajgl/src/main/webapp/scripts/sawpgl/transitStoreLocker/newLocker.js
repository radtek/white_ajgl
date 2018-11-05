(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData;
	$(document).ready(function() {	
		$.when(
				$.ajax({
					url:context +'/transitStoreLocker/generateCode.action',
					type:'post',
					dataType:'json',
					data:{"storageAreaId":initData.storageArea},
					success:function(json){
						$("#code").text(initData.storageAreaCode+json.code);
					}
				})
				,
				$.ajax({
					url:context +'/transitStoreArea/findtransitStoreAreaByPaging.action',
					type:'post',
					dataType:'json',
					data:{start : 0,length:100000},
					success:function(json){
						var storageAreaBeanList = json.storageAreaBeanList;
						for(var i=0;i<storageAreaBeanList.length;i++){
							if(storageAreaBeanList[i].id==initData.storageArea){
								$("#storageArea").text(storageAreaBeanList[i].name);      //赋值 val   
								$("#storageArea").attr("selId",storageAreaBeanList[i].id);
							}
						}
//						 $.select2.addByList("#storageArea", storageAreaBeanList, "id", "name", true, true);    //初始化
					}
				})
				
		    ).done(function(){
//		    	$("#storageArea").select2("val", initData.storageArea)      //赋值 val     
//		    	$.select2.able("#storageArea",false);
		    	showImg("cwj" + $("#code").text());
		    });
		
	});
	
	$(document).on("click","#tdcImg",function(){
		$.layerAlert.img($("#code").text());
	})
	
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
		$.ajax({
			url:context +'/transitStoreLocker/newTransitStoreLocker.action',
			type:'post',
			dataType:'json',
			data:dataMap,
			success:function(json){
				window.top.$.layerAlert.alert({msg:json.msg});
				if(json.msg=="保存成功"){
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
	 * 初始化物证保管区
	 */
	function initStorageArea(){
		
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		saveLocker : saveLocker
	});
	
})(jQuery);