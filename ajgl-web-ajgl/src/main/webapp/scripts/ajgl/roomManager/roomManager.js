(function($) {
	"use strict";
	$(document).ready(function() {
		//将状态改为true 修改页面
		initActivityRoom();
	});
	/**
	 * 查看详情按钮事件
	 */
	$(document).on("click",".showDetail",function(){
		var id = $(this).attr("harId");
		window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=true&&harId=" + id);
	});
	
//	/**
//	 * 开启指挥事件
//	 */
//	$(document).on("click",".roomli",function(){
//		if($.util.isBlank($(this).attr("sxId"))){
//			return;
//		}
//		//ip 大华服务器ip
//		//uuid 审讯记录id （分配记录表里）
//		//userName 操作人员警号
//		//passWord 操作人员警号
//		//userUUID 指挥员警号
//		//loginIp：P8000平台IP地址
//		window.open("http://" + dhServerIp + "/itc/dip/dipRemoteCommand_thirdDetail.action?uuid=" + $(this).attr("sxId") + "&userName=" + currentUserCode + "&password=" + currentUserCode + "&loginIp=" + dhServerIp + "&userUUID=" + currentUserCode);
//	});
	
	function initActivityRoom(){
		$.ajax({
			url:context +'/activityRecord/findAllActivityRoom.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				for(var i in successData.activityRoomBeanLst){
					newActivityRoom(successData.activityRoomBeanLst[i]);
				}
				for(var i in successData.otherRoomBeanLst){
					newOtherRoom(successData.otherRoomBeanLst[i]);
				}
			}
		})
	}
	
	function newActivityRoom(room){
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
		}
		$("#template").before(obj);
	}
	
	function newOtherRoom(room){
		var obj = $("#template2").clone(true);
		$(obj).attr("id", room.id);
		$(obj).show();
		$(obj).find(".room-name").text(room.name);
		if(room.status == $.common.Constant.SYZT_BKY()){
			$(obj).addClass("room-disabled");
			$(obj).find(".state-mark").addClass("state-disabled");
			$(obj).find(".state-mark").text("不可用");
			$(obj).find(".room-ceng").remove();
		}else{
			$(obj).addClass("room-free");
			$(obj).find(".allocationBtn").attr("roomId", room.id);
			$(obj).find(".allocationBtn").attr("roomName", room.name);
			var i = 0;
			for(var key in room.xyrMap){
				i++;
				$(obj).find(".alert").append('<p class="row"><a href="#" class="pull-right">' + key + '</a>  ' + room.xyrMap[key] + '</p>');
			}
			if(i == 0){
				$(obj).find(".wait-num").text("空");
				$(obj).find(".room-ceng").remove();
			}else{
				$(obj).find("#roomName").text(room.name + "详情");
				$(obj).find(".wait-num").text("共" + i + "人");
			}
			
		}
		$("#template2").before(obj);
	}
})(jQuery);