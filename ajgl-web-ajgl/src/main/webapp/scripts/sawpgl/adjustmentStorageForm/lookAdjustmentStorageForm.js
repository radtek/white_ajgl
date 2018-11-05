(function($){
	"use strict";
	
	var asfTable = null;
	var existingNumberSum = 0;//在库数量合计
	
	$(document).ready(function() {	
		initTable();
		
		/**
		 * 刷新按钮点击事件
		 */
		$(document).on("click","#refresh",function(){
			asfTable.draw(true);
		});
	});
	/**
	 * 返回
	 */
	$(document).on("click","#cancel",function(){
		window.location.href = $.util.fmtUrl(context + "/adjustmentStorageForm/showAdjustmentStorageFormListPage.action");
	});
	/**
	 * 设置调整单字段
     * @param object 
	 */
	function setAllField(object){
		$("#code").text(object.code);
		$("#adjustTime").text($.date.timeToStr(object.adjustTime, "yyyy-MM-dd HH:mm"));
		$("#reason").text($.util.isBlank(object.reason)?"":object.reason);
		$("#asfCodeImg").html("");
		$("#asfCodeImg").qrcode({
		    "render": 'image',
		    "size": 170,
		    "color": "#3a3",
		    "background": "white",
		    "text": object.code
		});
		$("#modifyTime").text($.date.timeToStr(object.updatedTime, "yyyy-MM-dd HH:mm"));
		$("#modifyPeopleName").text(object.operator);
	}
	
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/adjustmentStorageForm/findAdjustmentStorageFormById.action";
		tb.columnDefs = [
			{
				"targets": 0,
	 	    	"width": "50px",
	 	    	"title": "序号",
	 	    	"className":"table-checkbox",
	 	    	"data": "id" ,
	 	    	"render": function ( data, type, full, meta ) {
	 	    		return "<input type='hidden' value='" + data + "'>" + (meta.row + 1);
	 	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "物品名称",
				"data" : "articleName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "特征",
				"data" : "articleFeature",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "物品编号",
				"data" : "articleCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "50px",
				"title" : "扣押数量",
				"data" : "detentionNumber",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "50px",
				"title" : "在库数量",
				"data" : "adjustmentNumber",
				"render" : function(data, type, full, meta) {
					existingNumberSum += data;
					$("#existingNumberSum").text(existingNumberSum);
					return data + " " + full.measurementUnit;
				}
			},
			{
				"targets" : 6,
				"width" : "100px",
				"title" : "原存储物证保管区",
				"data" : "oldAreaName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 7,
				"width" : "100px",
				"title" : "原存储物箱",
				"data" : "oldLockerName",
				"render" : function(data, type, full, meta) {
					return  data;
				}
			},
			{
				"targets" : 8,
				"width" : "100px",
				"title" : "调整入库物证保管区",
				"data" : "storageServiceBeans",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data) && data.length > 0){
						td = data[0].areaName;
					}
					return td;
				}
			},
			{
				"targets" : 9,
				"width" : "100px",
				"title" : "调整入库储物箱",
				"data" : "storageServiceBeans",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data) || data.length > 0){
						$.each(data,function(s,ssb){
							td += ssb.lockerLocation + " 数量：" + ssb.adjustNumber + "；<br/>";
						});
					}
					return td;
				}
			},
			{
				"targets" : 10,
				"width" : "100px",
				"title" : "对应案件编号",
				"data" : "caseCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 11,
				"width" : "100px",
				"title" : "对应案件名称",
				"data" : "caseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 12,
				"width" : "80px",
				"title" : "办案民警",
				"data" : "polices",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 13,
				"width" : "100px",
				"title" : "对应犯罪嫌疑人/物品持有人",
				"data" : "suspectName",
				"render" : function(data, type, full, meta) {
					var td = data;
					if(!$.util.isBlank(full.suspectIdentityNumber) && full.suspectIdentityNumber != "null"){
						td += "<br/>" + full.suspectIdentityNumber;
					}
					return td ;
				}
			},
			{
				"targets" : 14,
				"width" : "100px",
				"title" : "备注",
				"data" : "remark",
				"render" : function(data, type, full, meta) {
					return data;
				}
			}
		];
		
		tb.paging = false ;
		//是否排序
		tb.ordering = false ;
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
			existingNumberSum = 0;
			d["adjustmentStorageFormId"] = asfId;
		};
		tb.paramsResp = function(json) {
			var asfiBeanList = json.asfsBean.asfiBeanList;
			json.data = asfiBeanList;
			json.recordsFiltered = asfiBeanList.length;
			setAllField(json.asfsBean);
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		asfTable = $("#asfTable").DataTable(tb);
		
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		lookAdjustmentStorageForm : {
			
		}
	});	
})(jQuery);