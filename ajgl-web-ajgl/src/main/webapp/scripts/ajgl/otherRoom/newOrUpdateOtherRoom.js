(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var askRoomId = initData.askRoomId;
	
	var oldAskRoomBean = null;
	
	$(document).ready(function() {	
		/**
		 * 验证编号是否唯一
		 */
		$(document).on("keyup change","#code",function(){
			verifyCodeIsOnly();
		});
		/**
		 * 验证名称是否唯一
		 */
		$(document).on("keyup change","#name",function(){
			verifyNameIsOnly();
		});
		/**
		 * 名称输入框失去焦点事件
		 */
		$(document).on("blur","#unitName",function (){
			$.validform.closeAllTips("validformAskRoom") ;
		});
		
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		intiDictionaryItem();
		initPageField();
	});
	
	/**
	 * 初始化状态字典项字段--其它房间状态
	 * @returns
	 */
	function intiDictionaryItem(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			dataType:'json',
			data:{dictionaryType : $.common.Constant.ZT()},
			success:function(successData){
				if(successData.dictionaryItemLst != null){
					$.select2.addByList("#status", successData.dictionaryItemLst,"id","name");//设置下拉框选项
					if($.util.isBlank(askRoomId)){
						$.select2.val("#status",$.common.Constant.QY(),true);//设置默认值
					}
				}
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 初始化页面字段信息
	 */
	function initPageField(){
		if($.util.isBlank(askRoomId)){//新建
			$.select2.able("#status",false);//不可修改
		}else{//修改
			$.ajax({
				url:context +'/askRoom/searchAskRoomById.action',
				type:'post',
				dataType:'json',
				data:{askRoomId : askRoomId},
				success:function(successData){
					oldAskRoomBean = successData.askRoomBean;
					setAllField(successData.askRoomBean);
				},
				error:function(errorData){
					
				}
			});
		}
	}
	
	/**
	 * 设置所有字段值
	 */
	function setAllField(askRoom){
		$("#code").val(askRoom.code);
		$("#name").val(askRoom.name);
		$("#unit").val(askRoom.unit);
		$("#unitName").val(askRoom.unitName);
		$.select2.val("#status",askRoom.status,true);//设置状态下拉框默认值
		$("#note").val(askRoom.note);
		//如果该房间被使用过，则设置为不可修改单位
		if(askRoom.useRecord){
			$(document).off("click", ".selectTreeUnit");
			$("#unitName").attr("disabled","disabled");
		}
	}
	
	/**
	 * 获取所有字段值
	 */
	function getAllField(){
		var askRoom = new Object();
		askRoom['id'] = askRoomId;
		askRoom['unit'] = $("#unit").val();
		askRoom['unitName'] = $("#unitName").val();
		askRoom['monitorNum'] = null;
		askRoom['name'] = $("#name").val();
		askRoom['note'] = $("#note").val();
		askRoom['code'] = $("#code").val();
		askRoom['type'] = $.common.Constant.FJLX_QT();
		askRoom['status'] = $.select2.val("#status");
		askRoom['statusName'] = $.select2.text("#status");
		return askRoom;
	}
	
	/**
	 * 验证编号是否唯一
	 * @return 
	 */
	function verifyCodeIsOnly(){
		var id = askRoomId;
		var code = $("#code").val();
		var unit = $("#unit").val();
		
		var gData = new Object();
		var askRoomBean = {id : id,code : code ,unit : unit};
		$.util.objToStrutsFormData(askRoomBean, "askRoomBean", gData);
		$.ajax({
			url:context +'/askRoom/verifyCodeIsOnly.action',
			type:'post',
			dataType:'json',
			ajaxLoading:false,
			data:gData,
			success:function(successData){
				var isOnly = successData.isOnly;
				if(!isOnly){
					$.layerAlert.tips({
						msg:'该编号已存在',
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
	 * 验证名称是否唯一
	 */
	function verifyNameIsOnly(){
		var id = askRoomId;
		var name = $("#name").val();
		var unit = $("#unit").val();
		
		var gData = new Object();
		var askRoomBean = {id : id,name : name ,unit : unit};
		$.util.objToStrutsFormData(askRoomBean, "askRoomBean", gData);
		$.ajax({
			url:context +'/askRoom/verifyNameIsOnly.action',
			type:'post',
			dataType:'json',
			ajaxLoading:false,
			data:gData,
			success:function(successData){
				var isOnly = successData.isOnly;
				if(!isOnly){
					$.layerAlert.tips({
						msg:'该名称已存在',
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
	 * 保存其他房间
	 */
	function saveAskRoom(){
		var url = context +'/askRoom/saveAskRoom.action';
		if(!$.util.isBlank(askRoomId)){
			url = context +'/askRoom/updateAskRoom.action';
		}
		var askRoom = getAllField();
		var gData = new Object();
		$.util.objToStrutsFormData(askRoom, "askRoomBean", gData);
		
		var demo = $.validform.getValidFormObjById("validformAskRoom") ;
		var flag = $.validform.check(demo) ;
		if(!flag){
			return ;
		}
		//执行后台操作
		$.ajax({
			url:url,
			async:true,
			cache:false,//设置每次都重新请求
			global:false,
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.closeWithLoading(pageIndex); //关闭弹窗
				}else{
					verifyCodeIsOnly();
					verifyNameIsOnly();
				}
				
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		saveAskRoom : saveAskRoom
	});	
})(jQuery);