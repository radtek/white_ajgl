(function($){
	"use strict";
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var id = initData.id;
	var oldBean = null;
	
	$(document).ready(function() {	
		initPageField();
	});
	
	/**
	 * 初始化页面字段信息
	 */
	function initPageField(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : "zt"},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#status", successData.dictionaryItemLst, "id", "name", true, true);
			}
		});
		if($.util.isBlank(id)){//新建
			setTimeout(function(){
				$.select2.val("#status",$.common.Constant.QY(),true);//设置默认值
				$.select2.able("#status",false);//不可修改
			},300);
		}else{//修改
			$.ajax({
				url:context +'/illegalType/queryIllegalType.action',
				type:'post',
				dataType:'json',
				data:{id : id},
				success:function(successData){
					oldBean = successData.illegalTypeBean;
					setAllField(oldBean);
				}
			});
		}
	}
	
	/**
	 * 设置所有字段值
	 */
	function setAllField(illegalTypeBean){
		$("#code").val(illegalTypeBean.code);
		$("#name").val(illegalTypeBean.name);
		$("#description").val(illegalTypeBean.description);
		$.select2.val("#status",illegalTypeBean.status,true);//设置默认值
	}
	
	/**
	 * 获取所有字段值
	 */
	function getAllField(){
		var illegalType = new Object();
		illegalType['id'] = id;
		illegalType['name'] = $("#name").val();
		illegalType['description'] = $("#description").val();
		illegalType['code'] = $("#code").val();
		illegalType['status'] = $.select2.val("#status");
		return illegalType;
	}
	
		
	/**
	 * 保存
	 */
	function save(){
		var url = context +'/illegalType/saveIllegalType.action';
		var msg = "新建成功。";
		if(!$.util.isBlank(id)){
			url = context +'/illegalType/updateIllegalType.action';
			msg = "修改成功。";
		}
		var illegalType = getAllField();
		var gData = new Object();
		$.util.objToStrutsFormData(illegalType, "illegalType", gData);
		
		var demo = $.validform.getValidFormObjById("validformLocker") ;
		var flag = $.validform.check(demo) ;
		
		if(!flag){
			return ;
		}
		//执行后台操作
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:msg,icon:"1",end : function(){
						window.top.$.layerAlert.closeWithLoading(pageIndex); //关闭弹窗
					}});
				}else{
					window.top.$.layerAlert.alert({msg:successData.msg});
				}
			}
		});
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		save : save
	});	
})(jQuery);