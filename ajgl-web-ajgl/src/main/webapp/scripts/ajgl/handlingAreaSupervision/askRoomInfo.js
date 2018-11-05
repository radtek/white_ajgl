(function($) {
	"use strict";
	 var askRoomUseAbnormalTable = null;
	 var askRoomId = $("#askRoomId").val();
	 var askRoomName = $("#askRoomName").val();
	 var askRoomStatusName =  $("#askRoomStatusName").val();
     var askRoomZt = $("#askRoomZt").val();
     var attachFlag = $("#attachFlag").val();
     var attachId = $("#attachId").val();
     var attachName = $("#attachName").val();
     var tabHaveFlag = $("#tabHaveFlag").val();
    
	$(document).ready(function() {	
		//只有正在使用状态的询问室显示“使用情况”Tab页。
		if (tabHaveFlag == "one") {
			$("#zyxx").remove();
		} 
				
		//加载文书。
		if(attachFlag){
			$("#attach").html("<a href='###' id='dlws'>" + attachName + "</a>");
			$(document).on("click","#dlws",function(){
				window.open(context + "/handlingAreaReceipt/downloadFile.action?attachmentId="+ attachId);					
			})
		}
		
		if (askRoomZt == $.common.Constant.SYZT_KX()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-green1'>空闲</span>");
		} else if (askRoomZt == $.common.Constant.SYZT_SYZ()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-red1'>使用中</span>");
		} else if (askRoomZt == $.common.Constant.SYZT_BKY()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-gray1'>不可用</span>");
		} else if (askRoomZt == $.common.Constant.QY()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-green1'>启用</span>");
		} else if (askRoomZt == $.common.Constant.TY()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-red1'>停用</span>");
		}
		
		if (askRoomZt == $.common.Constant.TY() || askRoomZt == $.common.Constant.SYZT_BKY()) {
			$("#unusual").hide();
		} else {
			initAskRoomUseAbnormalTable();
		}
		
		
	});
	
	//查看使用单详情。
	$(document).on("click",".showDetail",function(){
		var id = $(this).attr("id");
		window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=true&&harId=" + id);
	});
	//异常识别
	$(document).on("click","#unusual",function(){
		window.location.href = $.util.fmtUrl(context + "/handlingAreaSupervision/showAskRoomInfoDetail.action?&&askRoomId="+ askRoomId+"&&pageFlag=askRoomAbnormalFind");
	});
	//返回
	$(document).on("click","#back",function(){
		window.location.href = $.util.fmtUrl(context + "/handlingAreaSupervision/showHandlingAreaSupervision.action");
	});
	//刷新
	$(document).on("click","#refresh",function(){
		window.location.href = $.util.fmtUrl(context + "/handlingAreaSupervision/showAskRoomInfoDetail.action?&&askRoomId="+ askRoomId+"&&pageFlag=askRoomInfo");
	});
	
	
	function initAskRoomUseAbnormalTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/handlingAreaSupervision/queryRoomUseAbnormalList.action";
			tb.columnDefs = [
				{
					"targets" : 0,
					"width" : "15%",
					"title" : "违规类型",
					"data" : "illegalCauseStr",
					"render" : function(data, type, full, meta) {
						if (full.illegalCause == $.common.Constant.WGLX_QT()) {
							return data+"("+full.bz+")";
						} else {
							return data;
						}
					}
				},
				
				{
					"targets" : 1,
					"width" : "15%",
					"title" : "违规时间",
					"data" : "illegalTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "12%",
					"title" : "提交人",
					"data" : "commitPeople",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "15%",
					"title" : "异常画面",
					"data" : "commitPeople",
					"render" : function(data, type, full, meta) {
						return "";
					}
				}
			];
			//是否排序
			tb.ordering = false ;
			//每页条数
			tb.lengthMenu = [ 5 ];
			//默认搜索框
			tb.searching = false ;
			//能否改变lengthMenu
			tb.lengthChange = false ;
			//自动TFoot
			tb.autoFooter = false ;
			//自动列宽
			tb.autoWidth = false ;
			//请求参数
			tb.paramsReq = function(d, pagerReq){
				d["askRoomUseRecordQueryBean.activityRoomName"] = $("#roomLst").val();	//询问室名称
				d["askRoomId"] = askRoomId;	//询问室ID
			};
			tb.paramsResp = function(json) {
				var askRoomIllegalRecordBeanList = json.askRoomIllegalRecordBeanList;
				json.data = askRoomIllegalRecordBeanList;
				json.recordsTotal = json.pageNum;
				json.recordsFiltered = json.pageNum;
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			askRoomUseAbnormalTable = $("#askRoomUseAbnormalTable").DataTable(tb);
	}
	
	
})(jQuery);