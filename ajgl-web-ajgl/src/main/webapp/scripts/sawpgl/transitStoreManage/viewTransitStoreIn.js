(function($){
	"use strict";
	var zcwpInTable = null;
	var formCode = $("#formCode").val();
	$(document).ready(function() {	
		initTransitStoreInDataTable();
		dataSet();
	});
	
	$(document).on("click",".img",function(){
		$.layerAlert.img($(this).attr("src"));
	})
	
	/**
	 * 打印
	 */
	$(document).on("click","#print",function(){
		var form = $.util.getHiddenForm(context +'/templateStoragePrint/printTempStorageIn.action?code='+formCode, null);
		$.util.subForm(form);
	});
	
	
	function dataSet () {
		//加载二维码
		$("#qrcode").qrcode({
		    "render": 'image',
		    "size": 170,
		    "color": "#3a3",
		    "background": "white",
		    "text":$("#code").html()
		});
	}
	
	/**
	 * 返回
	 */
	$(document).on("click","#cancel",function(){
		history.back();
	});
	/**
	 * 刷新
	 */
	$(document).on("click","#refresh",function(){
		location.reload(true);  
	});
	
	
	function initTransitStoreInDataTable(){
		    var xh = 1;
		    var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/transitStoreManage/findTransitStoreInFormByInCode.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "5%",
         	    	"title": "序号",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    		return xh++;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "10%",
					"title" : "物品名称",
					"data" : "objectName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "8%",
					"title" : "特征",
					"data" : "objectCharacter",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "5%",
					"title" : "本次入库数量",
					"data" : "inThisNum",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "5%",
					"title" : "计量单位",
					"data" : "measureUnit",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "10%",
					"title" : "物品图片",
					"data" : "objectPicture",
					"render" : function(data, type, full, meta) {
						return "<img class='img' src='data:image/png;base64," + data + "' height='50'>";
					}
				},
				{
					"targets" : 6,
					"width" : "10%",
					"title" : "备注",
					"data" : "remark",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}
			];
			//是否排序
			tb.ordering = false ;
			tb.paging = false ; 
			tb.info = false ;
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
				d["code"] = formCode;
			};
			tb.paramsResp = function(json) {
				var tempObjectBeanList = json.tempObjectBeanList;
				json.data = tempObjectBeanList;
			};
			tb.rowCallback = function(row,data, index) {
					
					
			};
			zcwpInTable = $("#zcwpInTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 

	});	
})(jQuery);