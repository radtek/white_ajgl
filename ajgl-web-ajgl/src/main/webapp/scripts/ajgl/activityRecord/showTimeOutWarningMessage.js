(function($) {
	"use strict";	
	var table;
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var harCode = initData.harCode; //使用单编号
	$(document).ready(function() {
		initTable();
	});
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/activityRecord/findTimeOutWarningMessageByPage.action";
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
				"title" : "预警时间",
				"data" : "warningTime",
				"render" : function(data, type, full, meta) {
					return $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
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
					"harCode" : harCode,//单位id
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