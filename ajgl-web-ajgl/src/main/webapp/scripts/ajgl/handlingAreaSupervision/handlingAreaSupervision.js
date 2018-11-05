(function($) {
	"use strict";

	$(document).ready(function() {
		queryUnits();
	});

	/**
	 * 点击查看Room详情。
	 */
	$(document).on("click",".roomDetail",function() {
		var id = $(this).attr("roomId"); //roomId
		window.location.href = $.util.fmtUrl(context + "/handlingAreaSupervision/showAskRoomInfoDetail.action?&&askRoomId="+ id+"&&pageFlag=askRoomInfo");
	});
	
	/**
	 * 点击单位查看Room详情。
	 */
	$(document).on("click",".fromUnitToRoomDetail",function() {
		//添加样式
		$(this).parent().parent().find("li").removeClass("list-group-item-info");
		$(this).parent().addClass("list-group-item-info");
		
		var id = $(this).attr("unitId"); //unitId
		var name = $(this).attr("unitName"); //unitName
		$.ajax({
			url : context + "/handlingAreaSupervision/queryRoomsByUnitId.action?id="+id,
			type : 'post',
			dataType : 'json',
			success : function(successData) {
				var roomAll = successData.activityRoomBeanList; 
				var otherRoom = [];
				var room = [];
				$(roomAll).each(function(i,r){
					if (r.type == $.common.Constant.FJLX_WXS()) {
						room.push(r);
					} else {
						otherRoom.push(r);
					}
				});
				
				$("#titles").html(name+"：讯（询）问室 （"+room.length+"） 、 办案区其它房间 （"+otherRoom.length+"）");
				assembleAskRoom(room,otherRoom);
			},
			error : function(errorData) {
				$.layerAlert.alert({msg:"查询失败"});
			}
		});
	});
	
	/**
	 * 选择单位后拼装右侧房间。
	 */
	function assembleAskRoom(room,otherRoom){
		$("#xws").html("");
		$("#qt").html("");
		var liWxs = "";
		var liQt = "";
		$(room).each(function(i,r){
			if(r.status == $.common.Constant.SYZT_KX()) {
				liWxs+= '<li class="room-free"><h2 class="room-name"><a href="###" roomId="'+r.id+'" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-free">空闲</span></li>';
			} else if(r.status == $.common.Constant.SYZT_SYZ()) {
				liWxs+= '<li class="room-busy"><h2 class="room-name"><a href="###" roomId="'+r.id+'" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-busy">使用中</span></li>';
			} else if(r.status == $.common.Constant.SYZT_BKY()) {
				liWxs+= '<li class="room-disabled"><h2 class="room-name"><a href="###" roomId="'+r.id+'" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-disabled">不可用</span></li>';
			}
		});
	    $(otherRoom).each(function(i,r){
	    	if(r.status == $.common.Constant.QY()) {
	    		liQt+= '<li class="room-qy"><h2 class="room-name"><a href="###" roomId="'+r.id+'" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-qy">启用</span></li>';
			} else if(r.status == $.common.Constant.TY()) {
				liQt+= '<li class="room-ty"><h2 class="room-name"><a href="###" roomId="'+r.id+'" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-ty">停用</span></li>';
			} 
	    });
		$("#xws").append(liWxs);
		$("#qt").append(liQt);
	}
	
	/**
	 * 初始化左侧单位。
	 */
	function queryUnits() {
		$.ajax({
			url : context + "/handlingAreaSupervision/queryUnits.action",
			type : 'post',
			dataType : 'json',
			success : function(successData) {
				var unitLeftList = successData.unitLeftList;
				$(unitLeftList).each(function(i,unit){
					$("#unit").append('<li class="list-group-item"><a class="fromUnitToRoomDetail" unitName="'+unit.name+'" unitId="'+unit.id+'" href="#">'+unit.name+'</a></li>');
		    	});
				//初始进入页面显示第一个单位的房间
				if ($("#unit").find("li a").length > 0) {
					$("#unit").find("li").eq(0).addClass("list-group-item-info");
					$("#unit").find("li a").eq(0).click();
				}
			},
			error : function(errorData) {
				$.layerAlert.alert({msg:"查询单位失败"});
			}
		});
	}
	
	


})(jQuery);