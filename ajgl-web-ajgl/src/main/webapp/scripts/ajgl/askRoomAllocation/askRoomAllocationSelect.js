(function($) {
	"use strict";

	$(document).ready(function() {
		queryUnits();
	});

	/**
	 * 点击分配按钮
	 */
	$(document).on("click",".btn-primary",function() {
		var id =  $(this).attr("roomId"); //roomId // 讯（询）问室id
		window.location.href = $.util.fmtUrl(context + "/askRoomAllocation/showReceiptRoomAssigned.action?askRoomId="+ id);
	});
	/**
	 * 点击问询室名称按钮
	 */
	$(document).on("click",".roomDetail",function() {
		var id =  $(this).attr("roomId"); //roomId // 讯（询）问室id
		window.location.href = $.util.fmtUrl(context + "/handlingAreaSupervision/showAskRoomInfoDetail.action?&&askRoomId="+ id+"&&pageFlag=askRoomAllocationDetailInfo");
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
				var room = [];
				$(roomAll).each(function(i,r){
					if (r.type == $.common.Constant.FJLX_WXS()) {
						room.push(r);
					} 
				});
				$("#titles").html(name+"：讯（询）问室 （"+room.length+"）");
				assembleAskRoom(room);
			},
			error : function(errorData) {
				$.layerAlert.alert({msg:"查询失败"});
			}
		});
	});
	
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
	
	/**
	 * 选择单位后拼装右侧房间。
	 */
	function assembleAskRoom(room){
		$("#askRoomUl").html("");
		var liWxs = "";
		$(room).each(function(i,r){
			if(r.status == $.common.Constant.SYZT_KX()) {
				liWxs+= '<li class="room-free"><a href="###" roomId="'+r.id+'" class="roomDetail"><h2 class="room-name">'+r.name+'</h2></a><span class="state-mark state-free">空闲</span><p class="row text-center btn-box"><button roomId="'+r.id+'" class="btn btn-primary">分配</button></p></li>';
			} else if(r.status == $.common.Constant.SYZT_SYZ()) {
				liWxs+= '<li class="room-busy"><a href="###" roomId="'+r.id+'" class="roomDetail"><h2 class="room-name">'+r.name+'</h2></a><span class="state-mark state-busy">使用中</span></li>';
			} else if(r.status == $.common.Constant.SYZT_BKY()) {
				liWxs+= '<li class="room-disabled"><a href="###" roomId="'+r.id+'" class="roomDetail"><h2 class="room-name">'+r.name+'</h2></a><span class="state-mark state-disabled">不可用</span></li>';
			}
		});
		$("#askRoomUl").append(liWxs);
	}
	
//	function queryAllAskRoom() {
//		$.ajax({
//			url : context + "/askRoomAllocation/queryAllAskRoom.action",
//			type : 'post',
//			dataType : 'json',
//			success : function(successData) {
//				var askRoomBeanList = successData.queryAllAskRoomBeanList;
//				$(askRoomBeanList).each(function(i,room){
//					assembleAskRoom(room);
//		    	});
//			},
//			error : function(errorData) {
//
//			}
//		});
//	}
	
//	function assembleAskRoom(room){
//		var li = "";
//		$(room.activityRoomBean).each(function(i,r){
//			if(r.status == $.common.Constant.SYZT_SYZ()){	//使用中
//				li = '<ul id="'+ r.id +'"><li><h2 class="room-name"><a href="###" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-busy"></span></li>';
//			}else if(r.status == $.common.Constant.SYZT_KX()){	//空闲
//				li = '<ul id="'+ r.id +'"><li><h2 class="room-name"><a href="###" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-free"></span><p class="row text-center btn-box"><button class="btn btn-primary btn-lg" style="width:150px;">分配</button></p></li>';
//			}else if(r.status == $.common.Constant.SYZT_BKY()){	//不可用
//				li = '<ul id="'+ r.id +'"><li><h2 class="room-name"><a href="###" class="roomDetail">'+r.name+'</a></h2><span class="state-mark state-disabled"></span></li>';
//			}
//			$("#askRoomUl").append(li);
//		});
		
		
		
//		var state = ""
//			$(room.activityRoomBean).each(function(i,r){
//		var li = "<li>"
//					+ "<div class='m-ui-table'>"
//						+ "<table class='table table-bordered table-hover m-ui-table-no-paginate' cellspacing='0' width='100%'>"
//							+ "<thead>" 
//								+ "<tr>" 
//									+ "<th colspan='2'>"+r.name+"</th>" 
//								+ "</tr>"
//							+ "</thead>" 
//							+ "<tbody>" ;
//								li+= "<tr id='"+r.id+"'>" 
//									+ "<td width='60%'>"
//										+ "<a href='###' class='roomDetail'>"+r.name+"</a>" 
//									+ "</td>" ;
//									if(r.status == $.common.Constant.SYZT_KX()){
//										li+= "<td width='36%'>"
//											+ "<span class='state state-green1'>空闲</span>"
//											+ "<button class='btn btn-primary btn-xs'>分配</button>" 
//										+ "</td>";
//									}else if(r.status == $.common.Constant.SYZT_SYZ()){
//										li+= "<td width='36%'>"
//											+ "<span class='state state-red1'>使用中</span>"
//										+ "</td>";
//									}else if(r.status == $.common.Constant.SYZT_BKY()){
//										li+= "<td width='36%'>"
//											+ "<span class='state state-gray1'>不可用</span>"
//										+ "</td>";
//									}
//									li+= "</tr><tr><td ><img alt='' align='center' src='"+ path +"/images/house_147.45288753799px_1196125_easyicon.net.png'></td></tr>" ;
//									
//							li+= "</tbody>" 
//						+ "</table>" 
//					+ "</div>" 
//				+ "</li>"
//		$("#askRoomUl").append(li);
//			});
//	}


})(jQuery);