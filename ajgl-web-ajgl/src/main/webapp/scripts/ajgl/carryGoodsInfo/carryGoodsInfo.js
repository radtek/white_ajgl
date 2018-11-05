(function($) {
	"use strict";
	
	var goodsTable;
	//办案区使用单id
	var harId = "";
	$(document).ready(function() {
		jumpOn();
		harId = $("#harId").val();
		$.common.setSelectedTabsById("goodsCheckRecord");
		//将状态改为false 查看页面
		$.common.setIsUpdate(false);
		$.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
		initTable();
		$(document).on("click",".img",function(){
			$.layerAlert.img($(this).attr("src"));
		})
	});
	
	/**
	 * 打印
	 */
	$(document).on("click","#printAll",function() {
		var data = {id : harId};
		var form = $.util.getHiddenForm(context +'/harPrint/printHandlingAreaReceipt.action', data);
		$.util.subForm(form);
	});
	
	/**
	 * 解绑手环
	 */
	$(document).on("click","#unbound",function(){
		window.top.$.layerAlert.confirm({
			msg:"解绑后，将初始化嫌疑人手环及民警临时卡,请确认是否解绑？",
			title:"解绑提示",	  //弹出框标题
			yes:function(index, layero){
				$.ajax({
					url:context +'/leaveSituation/braceletControl.action',
					type:'post',
					data:{
						id:harId,
						type:"jb"
					},
					dataType:'json',
					success:function(successData){
						if(successData.flag == "true"){
							window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示"});
						}else{
							window.top.$.layerAlert.alert({msg:successData.errorMessage ,title:"提示"});
						}
					}
				})
			}
		});
	});
	/**
	 * 完成使用单
	 */
	$(document).on("click",".finishReceipt",function() {
		$.ajax({
			url:context +'/handlingAreaReceipt/handlingAreaReceiptCheck.action',
			type:'post',
			data:{
				id:harId
			},
			dataType:'json',
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.confirm({
						msg:"完成后信息将不可更改，是否要完成本次问讯？",
						title:"提示",	  //弹出框标题
						yes:function(index, layero){
							$.ajax({
								url:context +'/handlingAreaReceipt/doneHandlingAreaReceipt.action',
								type:'post',
								data:{
									id:harId
								},
								dataType:'json',
								success:function(successData){
									if(successData.flag == "true"){
										window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示",end:function(){
											location.reload(true);
										}});
									}else{
										window.top.$.layerAlert.alert({msg:"操作失败！",title:"提示"});
									}
								}
							})
						}
					});
				}else{
					window.top.$.layerAlert.alert({msg:successData.msg,title:"提示",end:function(){
						if(successData.flag == "base"){
							window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showUpdateHandlingAreaReceiptPage.action?&&justShow=false" + $("#justShow").val() + "&&harId=" + $("#harId").val());
						}
					}
				});
				}
			}
		})
	
		
	});
	
	
	/**
	 * 维护随身物品信息
	 */
	$(document).on("click","#updatePersonCheckRecord",function() {
		 window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/shoNewOrUpdateCarryGoodsInfo.action?&&harId=" + $("#harId").val());
	});
	
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/carryGoodsInfo/queryCarryGoodsReceipt.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "",
     	    	"title": "物品名称",
     	    	"className":"table-checkbox",
     	    	"data": "goodsName" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return data;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "特征",
				"data" : "features",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "数量",
				"data" : "quantity",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "计量单位",
				"data" : "unitOfMeasurement",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "物品状态",
				"data" : "statusStr",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "",
				"title" : "编号",
				"data" : "num",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "",
				"title" : "存储位置",
				"data" : "strongboxName",
				"render" : function(data, type, full, meta) {
					if($.util.isBlank(full.position)){
						return data;
					}else{
						return full.position;
					}
				}
			},
			{
				"targets" : 7,
				"width" : "",
				"title" : "物品图片",
//				"data" : "photoBase64",
				"data" : "photoId",
				"render" : function(data, type, full, meta) {
//					return "<img class='img' src='data:image/png;base64," + data + "' height='40'>";
					return "<img class='img' fileId="+data+" height='40'>";
				}
			}, 
			{
				"targets" : 8,
				"width" : "",
				"title" : "备注",
				"data" : "remark",
				"render" : function(data, type, full, meta) {
					return data;
				}
			}
		];
		//是否排序
		tb.ordering = false ;
		//是否分页
		tb.paging = false;
		//每页条数
		tb.lengthMenu = [ 10 ];
		//默认搜索框
		tb.searching = false ;
		//能否改变lengthMenu
		tb.lengthChange = false ;
		//自动TFoot
		tb.autoFooter = false ;
		//自动列宽
		tb.autoWidth = true ;
		tb.dom = "";
		//请求参数
		tb.paramsReq = function(d, pagerReq){
			d["handlingAreaReceiptId"] = harId;
		};
		tb.paramsResp = function(json) {
			var carryGoodsInfoBean = json.carryGoodsInfoBean;
			if(!$.util.exist(carryGoodsInfoBean)){
				json.data = [];
				$("#modifyPeopleName").text("");
				$("#updateTime").text("");
			}else{
				json.data = carryGoodsInfoBean.carryGoodsRecordBeans;
				if($.util.exist(carryGoodsInfoBean.modifyPeopleBean)){
					$("#modifyPeopleName").text(carryGoodsInfoBean.modifyPeopleBean.name);
				}
				if($.util.exist(carryGoodsInfoBean.modifyPeopleBean) 
						&& $.util.isBlank(carryGoodsInfoBean.modifyPeopleBean.id) 
						&& carryGoodsInfoBean.modifyPeopleBean.id != currentUserId){
					$("#updatePersonCheckRecord").attr("disabled", "disabled");
				}
				$("#updateTime").text(carryGoodsInfoBean.updatedTime);
				if($.util.exist(carryGoodsInfoBean.files)){
					$("#attTable").show();
					for(var i in carryGoodsInfoBean.files){
						$("#attLst").append("<a href='###' class='dlws' fileId='" + carryGoodsInfoBean.files[i].base64Str + "'>" + carryGoodsInfoBean.files[i].name + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
					}
				}
				
			}
		};
		tb.drawCallback = function() {
			var arr=$('.img')
			onloadPhotoDG(arr);
		};
		goodsTable = $("#goodsTable").DataTable(tb);
	}
	
	/**
	 * 依次加载图片
	 * @param arr
	 * @returns
	 */
	function onloadPhotoDG(arr){
		if(arr.length>0){
		 var pohtoid=$(arr[0]).attr("fileId");
			$.ajax({
				url:context +'/carryGoodsInfo/findBase64Byid.action',
				type:'post',
				data:{
					photoId:pohtoid
				},
				dataType:'json',
				success:function(successData){
					var attBase64= successData.photoBase64;
					$(arr[0]).attr("src","data:image/png;base64,"+attBase64);
					arr.splice(0,1);//移除第一个元素
					onloadPhotoDG(arr);					
				}
			})
		}
	}
	
})(jQuery);