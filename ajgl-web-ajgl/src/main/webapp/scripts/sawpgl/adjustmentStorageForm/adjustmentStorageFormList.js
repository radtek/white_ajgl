(function($){
	"use strict";
	
	var asfTable = null;

	$(document).ready(function() {	
		initTable();
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			asfTable.draw(true);
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#reset",function(){
			$("#code").val("");
			$("#codeText").val("调整单编号模糊查询");
			$.laydate.reset("#asfDateRange");
			asfTable.draw(true);
		});
		
		/**
		 * 刷新按钮事件
		 */
		$(document).on("click","#refBtn",function(){
			window.location.href=window.location.href;
		});

		
		/**
		 * 调整单二维码点击事件
		 */
		$(document).on("click",".articleCodeImg",function(){
			var code = $(this).attr("code");
			var tempDiv = $("<div/>");
			$(tempDiv).qrcode({
			    "render": 'image',
			    "size": 100,
			    "color": "#3a3",
			    "background": "white",
			    "text": code
			});
			$.layerAlert.img($(tempDiv).find("img").attr("src"),130,10);
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
			var d=new Object();
			if($.common.isFullConditionSearch()){
            	d["no"] = $("#code").val();
            	var startTime = $.laydate.getTime("#asfDateRange", "start");
            	var endTime = $.laydate.getTime("#asfDateRange", "end");
            	d["startTime"] = startTime;
            	d["endTime"] = endTime;
			}else{
				var codeText = $("#codeText").val();
				d["no"] = codeText == "调整单编号模糊查询" ? "" : codeText;
			}

			var form = $.util.getHiddenForm(context +'/adjustmentStorageForm/exportExcel.action', d);
			$.util.subForm(form);
		});	
		
		/**
		 * 调整单单号点击事件
		 */
		$(document).on("click",".articleCode",function(){
			var asfId = $(this).attr("asfId");
			window.location.href = $.util.fmtUrl(context + "/adjustmentStorageForm/showLookAdjustmentStorageFormPage.action?asfId=" + asfId) ;
		});
	});
	
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/adjustmentStorageForm/findAdjustmentStorageFormByCondition.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "200px",
     	    	"title": "调整单编号",
     	    	"className":"table-checkbox",
     	    	"data": "code" ,
     	    	"render": function ( data, type, full, meta ) {
     	    		var td = '<a class="detail articleCode" asfId="' + full.id + '" href="###">' + data + '</a>&nbsp;&nbsp;';
     	    			td += '<a href="###" class="articleCodeImg" code="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
	    			return td;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "调整时间",
				"data" : "adjustTime",
				"render" : function(data, type, full, meta) {
					var td = $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
					return td;
				}
			},
			{
				"targets" : 2,
				"width" : "300px",
				"title" : "调整原因",
				"data" : "reason",
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
            if($.common.isFullConditionSearch()){
            	d["no"] = $("#code").val();
            	d["startTime"] = $.laydate.getTime("#asfDateRange", "start");
            	d["endTime"] = $.laydate.getTime("#asfDateRange", "end");
			}else{
				var codeText = $("#codeText").val();
				d["no"] = codeText == "调整单编号模糊查询" ? "" : codeText;
			}
		};
		tb.paramsResp = function(json) {
			var adjustmentStorageFormServiceBeanList = json.adjustmentStorageFormServiceBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = adjustmentStorageFormServiceBeanList;
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		asfTable = $("#asfTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		adjustmentStorageFormList : {
			
		}
	});	
})(jQuery);