(function($) {
	"use strict";	
	var table;
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var cuffId = initData.cuffId; //手环的物理地址-编号
	var hearingRoomId = initData.hearingRoomId; //询问室id
	var gridId = initData.gridId; //网格id
	var startTime = initData.startTime; //开始时间
	var endTime = initData.endTime; //使用单编号
	var place = initData.place; //使用单编号
	$(document).ready(function() {
		initTable();
	});
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/activityRecord/findWarningMessageByPage.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "",
     	    	"title": "序号",
     	    	"className":"table-checkbox",
     	    	"data": "" ,
     	    	"render": function ( data, type, full, meta ) {
     	    		return  meta.row + 1;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "预警内容",
				"data" : "warningContent",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "预警位置",
				"data" : "warningPlace",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "预警时间",
				"data" : "warningTimeStr",
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
			var data = {
					"cuffId" : cuffId,
					"hearingRoomId" : hearingRoomId,
					"gridId" : gridId,
					"startTime" : startTime,
					"endTime" : endTime,
					"place" : place
				}
			var queryStr = $.util.toJSONString(data);
			$.util.objToStrutsFormData(queryStr,"queryStr",d);
		};
		tb.paramsResp = function(json) {
			json.recordsTotal = json.resultMap.totalNum;
			json.recordsFiltered = json.resultMap.totalNum;
			json.data = json.resultMap.mesList;
		};
		table = $("#mesTable").DataTable(tb);
	}
	
})(jQuery);