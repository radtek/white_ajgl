(function($) {
	"use strict";
	
	var table;
	var tb;
	//办案区使用单id
	var harId = "";
	var fliId = "";
	var finalLeave = false;
	var newLineIndex = 0;
	var deleteIdLst = [];
	var modifyFlag = null;
	
	var roomLst = null;
	var activityTypeLst = null;
	var otherActivityTypeLst = null;
	var containerLst = {};
	var delFileLst = [];
	var haarInfoId = null;
	$(document).ready(function() {
		jumpOff();
		harId = $("#harId").val();
		$.common.setSelectedTabsById("activityRecord");
		//将状态改为true 修改页面
		$.common.setIsUpdate(true);
		initData();
	});
	/**
	 * 查看详情按钮事件
	 */
	$(document).on("click",".showDetail",function(){
		var id = $(this).attr("harId");
		window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=true&&harId=" + id);
	});
	
	/**
	 * 开启指挥事件
	 */
//	$(document).on("click",".roomli",function(){
//		if($.util.isBlank($(this).attr("sxId"))){
//			return;
//		}
		//ip 大华服务器ip
		//uuid 审讯记录id （分配记录表里）
		//userName 操作人员警号
		//passWord 操作人员警号
		//userUUID 指挥员警号
		//loginIp：P8000平台IP地址 
//		window.open("http://" + dhServerIp + "/itc/dip/dipRemoteCommand_thirdDetail.action?uuid=" + $(this).attr("sxId") + "&userName=" + currentUserCode + "&password=" + currentUserCode + "&loginIp=" + dhServerIp + "&userUUID=" + currentUserCode);
//	});
	
	function initData(){
		var usingFlag = false;
		$.ajax({
			url:context +'/activityRecord/findAllActivityRoom.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				for(var i in successData.activityRoomBeanLst){
					var room = successData.activityRoomBeanLst[i];
					var obj = $("#template").clone(true);
					$(obj).attr("id", room.id);
					$(obj).show();
					$(obj).find(".room-name").text(room.name);
					if(room.status == $.common.Constant.SYZT_SYZ()){
						$(obj).addClass("room-busy");
						$(obj).attr("sxId", room.sxId);
						$(obj).find(".state-mark").addClass("state-busy");
						$(obj).find(".state-mark").text("在用");
						$(obj).find("#roomName").text(room.name + "详情");
						$(obj).find("#harCode").text(room.harInfo.harCode);
						$(obj).find("#harCode").attr("harId", room.harInfo.harId);
						$(obj).find("#byQuestioningPeopleName").text(room.harInfo.byQuestioningPeopleName);
						$(obj).find("#handlingPolice").text(room.harInfo.handlingPolice);
						$(obj).find("#allocateTime").text($.date.timeToStr(room.harInfo.allocateTime, "yyyy-MM-dd HH:mm:ss"));
						$(obj).find(".btn-box").remove();
						if(room.harInfo.harId == harId){
							$(obj).find(".state-mark").addClass("state-busy-current");
							$(obj).find(".state-mark").html('<span class="icon-ok"></span>当前在用');
							usingFlag = true;
						}
					}else if(room.status == $.common.Constant.SYZT_KX()){
						$(obj).addClass("room-free");
						$(obj).find(".state-mark").addClass("state-free");
						$(obj).find(".state-mark").text("空闲");
						$(obj).find(".room-ceng").remove();
						$(obj).find(".allocationBtn").attr("roomId", room.id);
						$(obj).find(".allocationBtn").attr("roomName", room.name);
					}else if(room.status == $.common.Constant.SYZT_BKY()){
						$(obj).addClass("room-disabled");
						$(obj).find(".state-mark").addClass("state-disabled");
						$(obj).find(".state-mark").text("不可用");
						$(obj).find(".room-ceng").remove();
						$(obj).find(".btn-box").remove();
					}
					$('.room-ceng').click(function(){
						event.stopPropagation();
					});
					$("#template").before(obj);
				}
				if(usingFlag){
					$(".btn-box").remove();
				}
			}
		})
	}
	
	/**
	 * 确认
	 */
	$(document).on("click",".allocationBtn",function(){
		var obj = $(this);
		window.top.$.layerAlert.confirm({
			msg:"确认分配给" + obj.attr("roomName") + "？",
			title:"提示",	  //弹出框标题
			yes:function(index, layero){
				save(obj.attr("roomId"));
			}
		});
	});
	
	function save(roomId){
		$.ajax({
			url:context + "/activityRecord/addAskRoomAllocationRecord.action",
			type:'post',
			data:{
				roomId : roomId,
				harId : harId
			},
			dataType:'json',
			customizedOpt:{
				btn:{	    			
					btn:"#confirm"
				}
			},
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:"分配成功！", end:function(){
						window.location.href = $.util.fmtUrl(context +"/activityRecord/showActivityRecord.action?&&harId=" + $("#harId").val());
					}});
				}else{
					window.top.$.layerAlert.alert({msg:"分配失败！\n"+successData.msg ,icon:"2"});
				}
			}
		});
	}
	
	/**
	 * 取消
	 */
	$(document).on("click",".right-vbtn",function(){
		window.location.href = $.util.fmtUrl(context +"/activityRecord/showActivityRecord.action?&&harId=" + $("#harId").val());
	});
	
})(jQuery);