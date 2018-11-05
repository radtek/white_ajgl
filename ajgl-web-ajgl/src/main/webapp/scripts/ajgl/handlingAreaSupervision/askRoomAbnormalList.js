(function($) {
	"use strict";
	var askRoomUseAbnormalTable = null;
	$(document).ready(function() {
		initData();		
		initAskRoomUseAbnormalTable();
	});
	
	/**
	 * 查询
	 */
	$(document).on("click",".queryRecord",function() {
		askRoomUseAbnormalTable.draw(true);
	});
	
	/**
	 * 重置
	 */
	$(document).on("click","#reset",function() {
		$("#simpleCode").val("");
		$("#code").val("");
		$.select2.val("#roomLst","");
		$.laydate.reset("#abnormalDate");
		$("#commitPeople").val("");
		$("#unit").val("");
		$("#unitName").val("");
	});
	
	$(document).on("click",".showDetail",function(){
		var id = $(this).attr("id");
		window.location.href = $.util.fmtUrl(context +"/handlingAreaSupervision/showAskRoomInfoDetail.action?&&askRoomId="+ id+"&&pageFlag=askRoomAbnormalDetailInfo");
	});
	
	
	/**
	 * 初始化table
	 */
	function initAskRoomUseAbnormalTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/handlingAreaSupervision/queryRoomUseAbnormalList.action";
			tb.columnDefs = [
				{
					"targets" : 0,
					"width" : "80px",
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
					"width" : "100",
					"title" : "违规时间",
					"data" : "illegalTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "60",
					"title" : "提交人",
					"data" : "commitPeople",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "150",
					"title" : "异常画面",
					"data" : "commitPeople",
					"render" : function(data, type, full, meta) {
						return "";
					}
				},
				{
					"targets" : 4,
					"width" : "80px",
					"title" : "使用的办案区房间",
					"data" : "activityRoomBean.name",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "90",
					"title" : "使用单编号",
					"data" : "handlingAreaReceiptNum",
					"render" : function(data, type, full, meta) {
						return "<a href='###' id='" + full.askRoomAllocationBean.askRoom.id + "' class='a-link mar-right showDetail'>" + data + "</a>";
					}
				}				
			];
			//是否排序
			tb.ordering = false ;
			//每页条数
			tb.lengthMenu = [ 8 ];
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
				
				if($.common.isFullConditionSearch()){
					d["askRoomId"] = $("#roomLst").val();	//询问室名称
					d["askRoomUseRecordQueryBean.handlingAreaReceiptNum"] = $("#code").val();	//使用单号
					d["commitPeople"] = $("#commitPeople").val();	//提交人
					d["startTime"] = $.laydate.getDate("#abnormalDate", "start");	//时间开始
					d["endTime"] = $.laydate.getDate("#abnormalDate", "end");	//时间结束
					d["askRoomUseRecordQueryBean.unitId"] = $("#unitId").val();	//所属单位
				}else{
					d["askRoomUseRecordQueryBean.handlingAreaReceiptNum"] = $(".simpleCode").val()=="使用单编号模糊查询"?"":$(".simpleCode").val();
				}
				
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
	
	/**
	 * 初始化选项
	 */
	function initData(){
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		$.ajax({
			url:context +'/handlingAreaReceipt/initDataListPage.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#roomLst", successData.roomLst, "id", "name", true, true);
			}
		});		
		
	}
	

})(jQuery);