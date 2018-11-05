$.leaveSituation = $.leaveSituation || {};
(function($) {
	"use strict";
	
	var table;
	//办案区使用单id
	var harId = "";
	var policeCount;
	$(document).ready(function() {
		jumpOn();
		harId = $("#harId").val();
		 $.common.setSelectedTabsById("leaveWork");
		 //将状态改为false 查看页面
		 $.common.setIsUpdate(false);
		 $.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
		 initTable();
		 ifShowPoliceBtn();
		 //ifShowUnboundBtn();
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
	 * 警员查看
	 */
	$(document).on("click","#showPoliceList",function(){
		var initData = {
			"harId" : harId
		}
		window.top.$.layerAlert.dialog({
			content : context +  '/leaveSituation/showPoliceCardList.action',
			pageLoading : true,
			title:"解绑民警临时卡",
			width : "650px",
			height : "500px",
			initData:function(){
				return $.util.exist(initData)?initData:{} ;
			},
			shadeClose : false,
    		success:function(layero, index){
    			window.top.frames["layui-layer-iframe"+index].$.common.set$($); //将打开的页面$传递到被打开子页面
    		},
    		btn:["关闭"],
    		callBacks:{
	    		btn1:function(index, layero){
			    	window.top.layer.close(index);
			    }
			},
			end:function (){
//				ifShowPoliceBtn();
				if(policeCount==0){
					$('#showPoliceList').attr("disabled","disabled");
				}
			}
		});
	});
	
	/**
	 * 解绑手环
	 */
	$(document).on("click","#unbound",function(){
		window.top.$.layerAlert.confirm({
			msg:"解绑后，将初始化嫌疑人手环,请确认是否解绑？",
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
//							$('#unbound').attr("disabled","disabled");
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
										window.top.$.layerAlert.alert({msg:"完成本次询问成功！",title:"提示",end:function(){
											location.reload(true);
										}});
									}else{
										window.top.$.layerAlert.alert({msg:"完成本次询问失败！",title:"提示"});
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
	 * 维护离开办案区信息
	 */
	$(document).on("click","#updateLeaveSituation",function() {
		 window.location.href = $.util.fmtUrl(context +"/leaveSituation/showUpdateLeaveSituation.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 判断是否将解绑民警临时卡按钮 置为不可选
	 * @returns
	 */
	function ifShowPoliceBtn(){
		$.ajax({
			url:context +'/leaveSituation/queryPoliceCardList.action',
			type:'post',
			dataType:'json',
			data:{
				id:harId
			},
			success:function(successData){
			  var count=successData.piBeanLst.length;
			  if(count==0){
					$('#showPoliceList').attr("disabled","disabled");
				}
			}
		})
	}
	
	/**
	 * 判断是否将解绑手环按钮置为不可选
	 * @returns
	 */
	function ifShowUnboundBtn(){
		$.ajax({
			url:context +'/leaveSituation/queryUnboundStatus.action',
			type:'post',
			dataType:'json',
			data:{
				id:harId
			},
			success:function(successData){
				var bn=successData.boundStatusFlag;
				if(bn==true){
					$('#unbound').attr("disabled","disabled");
				}
			}
		})
	}
	
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/leaveSituation/queryLeaveSituationList.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "",
     	    	"title": "离开时间",
     	    	"className":"table-checkbox",
     	    	"data": "leaveTime" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "离开原因",
				"data" : "leaveCause",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "返回时间",
				"data" : "returnTime",
				"render" : function(data, type, full, meta) {
					if($.util.isBlank(data)){
						return "";
					}
					return $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "维护人/维护时间",
				"data" : "maintainer",
				"render" : function(data, type, full, meta) {
					return data + "<br/>" + $.date.timeToStr(full.maintainTime, "yyyy-MM-dd HH:mm");
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "离开前使用的讯（询）问室",
				"data" : "id",
				"render" : function(data, type, full, meta) {
					var roomName = "";
					if($.util.exist(full.askRoom)){
						roomName = full.askRoom.name;
					}
					return roomName;
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
			d["id"] = harId;
		};
		tb.paramsResp = function(json) {
			if(!$.util.exist(json.tliBeanLst)){
				json.data = [];
			}else{
				json.data = json.tliBeanLst;
			}
			$("#modifyPeopleName").text((json.fliBean.modifyPeopleBean == null)? " " : json.fliBean.modifyPeopleBean.name);
			$("#updateTime").text((json.fliBean.updatedTime == null)? " " : $.date.timeToStr(json.fliBean.updatedTime, "yyyy-MM-dd HH:mm"));
			$("#leaveDate").text((json.fliBean.finallyLeaveTime == null)? " " : $.date.timeToStr(json.fliBean.finallyLeaveTime, "yyyy-MM-dd HH:mm"));
			$("#leaveReason").text((json.fliBean.finallyLeaveCause == null)? " " : json.fliBean.finallyLeaveCause);
			if($.util.exist(json.fliBean.askRoom)){
				$("#askRoomName").text(json.fliBean.askRoom.name);
				$("#askRoomId").val(json.fliBean.askRoom.id);
			}
			$("#useRoom").text($.util.isBlank(json.useRoom)?"":json.useRoom);
			$("#useRoomId").val(json.useRoomId);
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		table = $("#temporaryLeave").DataTable(tb);
	}
	jQuery.extend($.leaveSituation, {
		setCount:function(a){
			policeCount =a ;
		}
	});
	
})(jQuery);