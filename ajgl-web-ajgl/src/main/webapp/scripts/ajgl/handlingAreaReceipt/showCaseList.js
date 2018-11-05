(function($){
	"use strict";
	//文件上传
	var index = null;
	var caseListTable = null;
	$(document).ready(function() {
		var p$ = window.top.$;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		var cre$ = frameData.initData.cre$;
		 
		initCaseTable();
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			caseListTable.draw();
		});
		/**
		 * 选择事件
		 */
		$(document).on("click",".selectIt",function(){
			cre$.common.setCase($(this).attr("caseCode"), $(this).attr("caseName"), index);
		});
		
		/**
		 * 确定事件
		 */
		$(document).on("click",".save",function(){
			cre$.common.setCase("", $("#otherCaseName").val(), index);
		});
		
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			$("#caseCode").val("");
			$("#caseName").val("");
			$("#disposePerson").val("");
			caseListTable.draw();
		});
	});
	/**
	 * 初始化table
	 */
	function initCaseTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/caseSearch/searchCaseByPageForHAR.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "50px",
         	    	"title": "序号",
         	    	"className":"table-checkbox",
         	    	"data": "caseCode" ,
         	    	"render": function ( data, type, full, meta ) {
         	    			  return meta.row+1;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return "<a href='###' caseCode='" + full.caseCode + "' caseName='" + full.caseName + "' class='selectIt'>" + data + "</a>";
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return "<a href='###' caseCode='" + full.caseCode + "' caseName='" + full.caseName + "' class='selectIt'>" + data + "</a>";
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "办案民警",
					"data" : "disposePerson",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "案件状态",
					"data" : "caseState",
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
				var obj = {};
				obj.caseCode = $("#caseCode").val();
				obj.caseName = $("#caseName").val();
				obj.disposePerson = $("#disposePerson").val();
				$.util.objToStrutsFormData(obj,"caseSearchBean",d);
			};
			tb.paramsResp = function(json) {
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = json.cbcBeanLst;
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			caseListTable = $("#caseListTable").DataTable(tb);
	}
})(jQuery);