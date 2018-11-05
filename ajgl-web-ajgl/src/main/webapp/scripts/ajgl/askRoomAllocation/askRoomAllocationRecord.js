(function($) {
	"use strict";
	
	var recordTable = null ;
	var today = null ;
	var tomorrow = null ;
	$(document).ready(function() {
		today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		tomorrow = new Date(today);
		tomorrow.setHours(23);
		tomorrow.setMinutes(59);
		tomorrow.setSeconds(59);
		$.laydate.setRange($.date.dateToStr(today,"yyyy-MM-dd HH:mm"), $.date.dateToStr(tomorrow,"yyyy-MM-dd HH:mm"), "#allocationDate");
		initDate();
		initRecrdTable();
	});
	
	function initDate(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.BAQYY()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#enterAreaReasons", successData.dictionaryItemLst, "id", "name", true, true);
			}
		});
	}
	
	/**
	 * 查询
	 */
	$(document).on("click",".queryRecord",function() {
		recordTable.draw(false);
	});
	
	/**
	 * 查看使用单
	 */
	$(document).on("click",".showHar",function() {
		window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=true&&harId=" + $(this).attr("harId"));
	});
	
	/**
	 * 重置
	 */
	$(document).on("click","#reset",function() {
		$("#roomName").val("");
		$(".code").val("");
		$.select2.val("#enterAreaReasons","");
		$("#idNum").val("");
		$("#askPerson").val("");
		$.laydate.setRange($.date.dateToStr(today,"yyyy-MM-dd HH:mm"), $.date.dateToStr(tomorrow,"yyyy-MM-dd HH:mm"), "#allocationDate");
	});
	
	/**
	 * 刷新
	 */
	$(document).on("click","#refresh",function() {
		location.reload(true);
	});
	
	function initRecrdTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/askRoomAllocation/queryAskRoomAllocationRecord.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "",
     	    	"title": "分配讯（询）问室",
     	    	"className":"table-checkbox",
     	    	"data": "activityRoomName" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return data;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "分配人",
				"data" : "allocationPeople",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "分配时间",
				"data" : "startAllocationTime",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "使用单编号",
				"data" : "handlingAreaReceiptNum",
				"render" : function(data, type, full, meta) {
					return "<a href='###' harId='" + full.harId + "' class='showHar'>" + data + "</a>";
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "办案民警",
				"data" : "handlingPolice",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "",
				"title" : "被讯（询）问人姓名",
				"data" : "byQuestioningPeopleName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "",
				"title" : "证件号码",
				"data" : "byQuestioningPeopleIdentifyNum",
				"render" : function(data, type, full, meta) {
					return data;
				}
			}
		];
		//是否排序
		tb.ordering = false ;
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
		//请求参数
		tb.paramsReq = function(d, pagerReq){
			if($.common.isFullConditionSearch()){
				d["askRoomAllocationRecordQueryBean.activityRoomName"] = $("#roomName").val();	//询问室名称
				d["askRoomAllocationRecordQueryBean.handlingAreaReceiptNum"] = $(".code").val();	//使用单号
				d["askRoomAllocationRecordQueryBean.enterAreaReasons"] = $.select2.val("#enterAreaReasons");	//进入办案区原因
				d["askRoomAllocationRecordQueryBean.byQuestioningPeopleName"] = $("#askPerson").val();	//被问讯人姓名
				d["askRoomAllocationRecordQueryBean.byQuestioningPeopleIdentifyNum"] = $("#idNum").val();	//被问讯人身份证件号码
				d["askRoomAllocationRecordQueryBean.startAllocationTime"] = $.laydate.getDate("#allocationDate", "start");	//分配时间开始
				d["askRoomAllocationRecordQueryBean.endAllocationTime"] = $.laydate.getDate("#allocationDate", "end");	//分配时间结束
				d["askRoomAllocationRecordQueryBean.orderCondition"] = $.select2.val("#orderCondition");
			}else{
				d["askRoomAllocationRecordQueryBean.handlingAreaReceiptNum"] = $(".simpleCode").val()=="使用单编号模糊查询"?"":$(".simpleCode").val();
				d["askRoomAllocationRecordQueryBean.startAllocationTime"] = $.date.dateToStr(today, "yyyy-MM-dd HH:mm");	//进入办案区日期
				d["askRoomAllocationRecordQueryBean.endAllocationTime"] = $.date.endRange($.date.dateToStr(tomorrow, "yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm");
				d["askRoomAllocationRecordQueryBean.orderCondition"] = "allocationTime";
			}
		};
		tb.paramsResp = function(json) {
			var askRoomAllocationRecordQueryBeanList = json.askRoomAllocationRecordQueryBeanList;
			json.data = askRoomAllocationRecordQueryBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		recordTable = $("#recordTable").DataTable(tb);
	}

})(jQuery);