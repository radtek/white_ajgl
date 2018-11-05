(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var saId = initData.saId;
	
	$(document).ready(function() {	
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		initDictionaryItem();
		
		/**
		 * 名称输入框失去焦点事件
		 */
		$(document).on("blur","#unitName",function (){
			$.validform.closeAllTips("saValidform") ;
		});
		
		/**
		 * 名称输入事件
		 */
//		$(document).on("keyup change","#name",function (){
//			verifyNameIsOnly();
//		});
		
		/**
		 * 编号输入事件
		 */
//		$(document).on("keyup change","#code",function (){
//			verifyCodeIsOnly();
//		});
		
	});
	
	/**
	 * 初始化字典项
	 */
	function initDictionaryItem(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			dataType:'json',
			data:{dictionaryType : $.common.Constant.ZT()},
			success:function(successData){
				if(successData.dictionaryItemLst != null){
					$.select2.addByList("#state", successData.dictionaryItemLst,"id","name");//设置下拉框选项
					if($.util.isBlank(saId)){
						$.select2.val("#state",$.common.Constant.QY(),true);//设置默认值
						$.select2.able("#state",false);
					}else{
						initPageField(saId);
					}
				}
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 修改时候初始化页面字段值
	 * @param id 物品管理区id
	 */
	function initPageField(id){
		$.ajax({
			url: context + '/storageArea/findStorageAreaById.action',
			type:"POST",	
			dataType:"json",
			data:{storageAreaId : saId},
			success:function(data){
				var storageAreaBean = data.storageAreaBean;
				setAllField(storageAreaBean);
			}
		});
	}
	
	/**
	 * 设置页面所有字段
	 * @param object
	 */
	function setAllField(object){
		$("#address").val(object.address);
		$("#code").val(object.code);
		$("#name").val(object.name);
		$("#remark").val(object.remark);
		$("#unit").val(object.unitId);
		$("#unitName").val(object.unitName);
		$.select2.val("#state",object.state,true);
		$.select2.able("#state",false);
	}
	
	/**
	 * 获取页面所有字段
	 */
	function getAllField(){
		var sa = new Object();
		sa.id = $.util.isBlank(saId)?null:saId;
		sa.address = $("#address").val();
		sa.code = $("#code").val();
		sa.name = $("#name").val();
		sa.remark = $("#remark").val();
		sa.state = $.select2.val("#state");
		sa.unitId = $("#unit").val();
		return sa;
	}
	
	/**
	 * 验证编号是否唯一
	 */
	function verifyCodeIsOnly(){
		var code = $("#code").val();
		var unitId = $("#unit").val();
		if($.util.isBlank(unitId)){
			return ;
		}
		var gData = new Object();
		var storageAreaBean = {id : saId,code : code ,unitId : unitId};
		$.util.objToStrutsFormData(storageAreaBean, "storageAreaBean", gData);
		$.ajax({
			url:context +'/storageArea/verifyCodeIsOnly.action',
			type:'post',
			dataType:'json',
			ajaxLoading:false,
			data:gData,
			success:function(successData){
				var only = successData.only;
				if(!only){
					$.layerAlert.tips({
						msg:'编号已存在',
						selector:"#code",
						color:'#FF0000',
						position:1,
						closeBtn:2,
						time:2000,
						shift:1
					});
				}
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 *验证名称是否唯一
	 */
	function verifyNameIsOnly(){
		var name = $("#name").val();
		var unitId = $("#unit").val();
		if($.util.isBlank(unitId)){
			return ;
		}
		var gData = new Object();
		var storageAreaBean = {id : saId,name : name ,unitId : unitId};
		$.util.objToStrutsFormData(storageAreaBean, "storageAreaBean", gData);
		$.ajax({
			url:context +'/storageArea/verifyNameIsOnly.action',
			type:'post',
			dataType:'json',
			ajaxLoading:false,
			data:gData,
			success:function(successData){
				var only = successData.only;				
				if(!only){
					$.layerAlert.tips({
						msg:'名称已存在',
						selector:"#name",
						color:'#FF0000',
						position:1,
						closeBtn:2,
						time:2000,
						shift:1
					});
				}
			},
			error:function(errorData){
				
			}
		});
		
	}
	
	/**
	 * 保存
	 */
	function save(){
		var demo = $.validform.getValidFormObjById("saValidform") ;
		var flag = $.validform.check(demo) ;
		if(!flag){
			return ;
		}
		
		var msg='';
		var code = $("#code").val();
		var name = $("#name").val();
		var unitId = $("#unit").val();
		
		
		var gData = new Object();
		var storageAreaBean = {id : saId,code : code ,unitId : unitId,name : name};
		$.util.objToStrutsFormData(storageAreaBean, "storageAreaBean", gData);
		$.when(
		$.ajax({
			url:context +'/storageArea/verifyCodeIsOnly.action',
			type:'post',
			dataType:'json',
			ajaxLoading:false,
			data:gData,
			success:function(successData){
				var only = successData.only;
				if(!only){
					msg+="编号";
				}
			}
		}),
		$.ajax({
			url:context +'/storageArea/verifyNameIsOnly.action',
			type:'post',
			dataType:'json',
			ajaxLoading:false,
			data:gData,
			success:function(successData){
				var only = successData.only;				
				if(!only){
					if (msg!=''){
						msg+="和";
					}
					msg+="名称";
				}
			}
		})
		).done(function(){
			 if(msg!=''){
			    	window.top.$.layerAlert.alert({title:"提示",msg:msg+"已存在,请重新输入",icon:5});
					return;
			    }
			 var url = context + '/storageArea/newStorageArea.action';
			 if(!$.util.isBlank(saId)){
				 url = context + '/storageArea/updateStorageArea.action';
			 }
			 
			 var sa =  getAllField();
			 var gData = new Object();
			 $.util.objToStrutsFormData(sa, "storageAreaBean", gData);
			 $.ajax({
				 url: url,
				 type:"POST",	
				 dataType:"json",
				 data:gData,
				 success:function(data){
					 if(data.flag == "true"){
						 window.top.$.layerAlert.alert({title:"提示",msg:"保存成功",icon:6});
						 window.top.$.layerAlert.closeWithLoading(pageIndex); //关闭弹窗
					 }else{//重新验证
						 window.top.$.layerAlert.alert({title:"提示",msg:"保存失败",icon:5});
//					verifyCodeIsOnly();
//					verifyNameIsOnly();
					 }
				 }
			 });	
		})
		
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		newOrUpdateStorageArea : {
			save : save 
		}
	});	
})(jQuery);