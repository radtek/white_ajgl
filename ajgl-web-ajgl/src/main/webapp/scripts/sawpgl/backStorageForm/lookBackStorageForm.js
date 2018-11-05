(function($){
	"use strict";
	
	var bsfTable = null;
	
	$(document).ready(function() {	
		initTable();
		/**
		 * 刷新按钮点击事件
		 */
		$(document).on("click","#refresh",function(){
			bsfTable.draw(true);
		});
		
		/**
		 * 打印按钮点击事件
		 */
		$(document).on("click","#bsfPrint",function(){
			var data = {backStorageFormId : bsfId};
			var form = $.util.getHiddenForm(context +'/backStorageForm/printBackStorageFormById.action', data);
			$.util.subForm(form);
		});
		
		/**
		 * 涉案物品二维码点击事件
		 */
		$(document).on("click",".articleCodeImg",function(){
			
			   var articleCode = $(this).attr("code");
				window.top.$.layerAlert.dialog({
					content : context +  '/storageIn/showArticleQrcodePage.action',
					pageLoading : true,
					title:"查看物品二维码",
					width : "500px",
					height : "600px",
					btn:["打印二维码","关闭"],
					callBacks:{
						btn1:function(index, layero){
							var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
							cm.print();
						},
						btn2:function(index, layero){
							window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
						}
					},
					shadeClose : false,
					success:function(layero, index){
						
					},
					initData:{
						articleCode:articleCode
					},
					end:function(){
					}
				});
			
//			var code = $(this).attr("code");
//			var tempDiv = $("<div/>");
//			$(tempDiv).qrcode({
//			    "render": 'image',
//			    "size": 100,
//			    "color": "#3a3",
//			    "background": "white",
//			    "text": code
//			});
//			$.layerAlert.img($(tempDiv).find("img").attr("src"),130,10);
		});
	});
	
	/**
	 * 设置再入库单字段
	 * 
	 * @param object 再入库单对象
	 */
	function setAllField(object){
		$("#code").text(object.code);
		$("#updatedTime").text($.date.timeToStr(object.updatedTime,"yyyy-MM-dd HH:mm"));
		$("#caseCode").text(object.caseCode);
		$("#outStorageFormCode").text(object.outStorageFormCode);
		$("#typeName").text(object.typeName);
		$("#caseName").text(object.caseName);
		$("#polices").text(object.polices == null ? "" : object.polices);
		$("#remark").text(object.remark);
		$("#bsfCodeImg").html("");
		$("#bsfCodeImg").qrcode({
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
		tb.ajax.url = context + "/backStorageForm/findBackStorageFormById.action";
		tb.columnDefs = [
			{
				"targets": 0,
	 	    	"width": "50px",
	 	    	"title": "序号",
	 	    	"className":"table-checkbox",
	 	    	"data": "backItemId" ,
	 	    	"render": function ( data, type, full, meta ) {
	 	    		return "<input type='hidden' value='" + data + "'>" + (meta.row + 1);
	 	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "物品名称",
				"data" : "involvedArticleName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "特征",
				"data" : "involvedArticleFeature",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "物品编号",
				"data" : "involvedArticleCode",
				"render" : function(data, type, full, meta) {
					return data + '&nbsp;&nbsp;<a href="###" class="articleCodeImg" code="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
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
				"width" : "100px",
				"title" : "物品性质",
				"data" : "involvedArticleTypeName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "50px",
				"title" : "出库数量",
				"data" : "outcomingNumber",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 7,
				"width" : "50px",
				"title" : "累计再入库数量",
				"data" : "returnedNumber",
				"render" : function(data, type, full, meta) {
					return  data;
				}
			},
			{
				"targets" : 8,
				"width" : "50px",
				"title" : "计量单位",
				"data" : "measurementUnit",
				"render" : function(data, type, full, meta) {
					return  data;
				}
			},
			{
				"targets" : 9,
				"width" : "100px",
				"title" : "再入库物证保管区",
				"data" : "areaName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 10,
				"width" : "100px",
				"title" : "所在储物箱/再入库数量",
				"data" : "storageServiceBeans",
				"render" : function(data, type, full, meta) {
					var selectedDiv = $("<div />",{});
					$.each(data,function(s,ssb){
						var p = $("<p />",{
							"text": ssb.lockerLocation + "，数量：" + ssb.returnNumber +"；"
						}).appendTo(selectedDiv);
					});
					return selectedDiv[0].outerHTML;
				}
			},
			{
				"targets" : 11,
				"width" : "100px",
				"title" : "对应犯罪嫌疑人/物品持有人",
				"data" : "suspectedName",
				"render" : function(data, type, full, meta) {
					var td = data ;
					if(!$.util.isBlank(full.suspectIdentityNumber) && full.suspectIdentityNumber != "null"){
						td += "<br/>" + full.suspectIdentityNumber;
					}
					return td ;
				}
			},
			{
				"targets" : 12,
				"width" : "50px",
				"title" : "是否全部再入库",
				"data" : "",
				"render" : function(data, type, full, meta) {
					var td = "否";
					if(full.outcomingNumber == full.returnedNumber){
						td = "是";
					}
					return td;
				}
			},
			{
				"targets" : 13,
				"width" : "100px",
				"title" : "备注",
				"data" : "remark",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 14,
				"width" : "80px",
				"title" : "维护时间",
				"data" : "maintainTime",
				"render" : function(data, type, full, meta) {
					return $.date.timeToStr(data,"yyyy-MM-dd HH:mm");
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
			d["backStorageFormId"] = bsfId;
		};
		tb.paramsResp = function(json) {
			json.data = json.backStorageFormItemServiceBeanList;
			json.recordsFiltered = json.backStorageFormItemServiceBeanList.length;
			setAllField(json.backStorageFormServiceBean);
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		bsfTable = $("#bsfTable").DataTable(tb);
	}
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function(){
		history.back();
	});
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		lookBackStorageForm : {
			
		}
	});	
})(jQuery);