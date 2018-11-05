(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var lockerId = initData.lockerId;
	
	var oldLockerBean = null;
	
	$(document).ready(function() {	
		/**
		 * 验证编号是否唯一
		 */
		$(document).on("keyup change","#code",function(){
			verifyCodeIsOnly();
		});
		/**
		 * 名称输入框失去焦点事件
		 */
		$(document).on("blur","#unitName",function (){
			$.validform.closeAllTips("validformLocker") ;
		});
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		intiDictionaryItem();
		initPageField();
	});
	
	/**
	 * 初始化状态字典项字段
	 * @returns
	 */
	function intiDictionaryItem(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			dataType:'json',
			data:{dictionaryType : $.common.Constant.CWGZT()},
			success:function(successData){
				if(successData.dictionaryItemLst != null){
					var arr=[]
					for(var i=0;i<successData.dictionaryItemLst.length;i++){
						if(successData.dictionaryItemLst[i].id!=$.common.Constant.CWGZT_SYZ()){
							arr.push(successData.dictionaryItemLst[i]);
						}
					}
					$.select2.addByList("#status",arr,"id","name");//设置下拉框选项
					if($.util.isBlank(lockerId)){
						$.select2.val("#status",$.common.Constant.CWGZT_KX(),true);//设置默认值
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
		if($.util.isBlank(lockerId)){//新建
			$.select2.able("#status",false);//不可修改
		}else{//修改
			$.ajax({
				url:context +'/locker/searchLockerById.action',
				type:'post',
				dataType:'json',
				data:{lockerId : lockerId},
				success:function(successData){
					oldLockerBean = successData.lockerBean;
					setAllField(successData.lockerBean);
				},
				error:function(errorData){
					
				}
			});
		}
	}
	
	/**
	 * 设置所有字段值
	 */
	function setAllField(lockerBean){
		$("#code").val(lockerBean.code);
		//$("#position").val(lockerBean.position);
		$("#areaCode").val(lockerBean.areaCode);
		$("#lockerCode").val(lockerBean.lockerCode);
		$("#unit").val(lockerBean.unit);
		$("#unitName").val(lockerBean.unitName);
		$.select2.val("#status",lockerBean.status,true);//设置默认值
		$("#note").val(lockerBean.note);
		//如果该房间被使用过，则设置为不可修改单位
		if(lockerBean.useRecord){
			$(document).off("click", ".selectTreeUnit");
			$("#unitName").attr("disabled","disabled");
		}
	}
	
	/**
	 * 获取所有字段值
	 */
	function getAllField(){
		var lockerBean = new Object();
		lockerBean['id'] = lockerId;
		lockerBean['unit'] = $("#unit").val();
		lockerBean['unitName'] = $("#unitName").val();
		//lockerBean['position'] = $("#position").val();
		lockerBean['areaCode'] = $("#areaCode").val();
		lockerBean['lockerCode'] = $("#lockerCode").val();
		lockerBean['note'] = $("#note").val();
		lockerBean['code'] = $("#code").val();
		lockerBean['status'] = $.select2.val("#status");
		lockerBean['statusName'] = $.select2.text("#status");
		return lockerBean;
	}
	
	/**
	 * 验证编号是否唯一
	 * @return 
	 */
	function verifyCodeIsOnly(){
		var id = lockerId;
		var code = $("#code").val();
		var unit = $("#unit").val();
		
		var gData = new Object();
		var lockerBean = {id : id,code : code ,unit : unit};
		$.util.objToStrutsFormData(lockerBean, "lockerBean", gData);
		$.ajax({
			url:context +'/locker/verifyCodeIsOnly.action',
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
	 * 保存储物柜
	 */
	function saveLocker(){
		var url = context +'/locker/saveLocker.action';
		if(!$.util.isBlank(lockerId)){
			url = context +'/locker/updateLocker.action';
		}
		var lockerBean = getAllField();
		var gData = new Object();
		$.util.objToStrutsFormData(lockerBean, "lockerBean", gData);
		
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
					window.top.$.layerAlert.closeWithLoading(pageIndex); //关闭弹窗
				}else{
					verifyCodeIsOnly();
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
		saveLocker : saveLocker
	});	
})(jQuery);