(function($){
	"use strict";
	var table = null;
	$(document).ready(function() {
		var dateEnd = (new Date()).getTime();
		var dateStart = dateEnd - 172800000;
		$.laydate.setRange($.date.timeToStr(dateStart,"yyyy-MM-dd HH:mm:ss"), $.date.timeToStr(dateEnd,"yyyy-MM-dd HH:mm:ss"), "#messageDate", "yyyy-MM-dd HH:mm:ss");
		initTable();
	});
	
	$(document).on("click","#query",function(){
		var demo = $.validform.getValidFormObjById("validform") ;
		var flag = $.validform.check(demo) ;
		if(!flag){
			return ;
		}
		table.draw(true);
	});
	
	$(document).on("click","#reset",function(){
		var dateEnd = (new Date()).getTime();
		var dateStart = dateEnd - 172800000;
		$.laydate.setRange($.date.timeToStr(dateStart,"yyyy-MM-dd HH:mm:ss"), $.date.timeToStr(dateEnd,"yyyy-MM-dd HH:mm:ss"), "#messageDate", "yyyy-MM-dd HH:mm:ss");
	});
	
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/alertMessage/findMessage.action";
			tb.columnDefs = [
				{
					"targets" : 0,
					"width" : "20px",
					"title" : "序号",
					"data" : "",
					"render" : function(data, type, full, meta) {
						return meta.row + 1;
					}
				},
				{
					"targets" : 1,
					"width" : "200px",
					"title" : "内容",
					"data" : "content",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "250px",
					"title" : "提醒时间",
					"data" : "createdTime",
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
			tb.autoWidth = false ;
			//请求参数
			tb.paramsReq = function(d, pagerReq){
				d["startTime"] = $.laydate.getTime("#messageDate","start");
				d["endTime"] = $.laydate.getTime("#messageDate","end");
				
				};
			tb.paramsResp = function(json) {
				var amLst = json.amLst;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = amLst;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			table = $("#alertMessageTable").DataTable(tb);
	}

})(jQuery);