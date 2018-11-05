(function($){
	"use strict";
	//文件上传
	var index = null;
	var handlingAreaReceiptTable = null;
	var cardId = null;
	var machineId = null;
	var harId = null ; 
	$(document).ready(function() {
		var p$ = window.top.$;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		var cre$ = frameData.initData.cre$;
		cardId = frameData.initData.cardId;
		machineId = frameData.initData.machineId;
		initSelectHandlingAreaReceiptTable(cardId, machineId);
	});

	/**
	 * 初始化table
	 */
	function initSelectHandlingAreaReceiptTable(cardId, machineId){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/handlingAreaReceipt/selectHandlingAreaReceipt.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "50px",
     	    	"title": "选择",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  var a = '<input type="checkbox" name="harCheck" class="icheckbox" value="'+data+'"/>' ;
     	    			  return a;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "使用单编号<br/>状态",
				"data" : "code",
				"render" : function(data, type, full, meta) {
					var state = "";
					if(full.state == $.common.Constant.SYDZT_QT()){
						state = "<span class='state state-blue1'>进行中</span>";
					}else if(full.state == $.common.Constant.SYDZT_YWC()){
						state = "<span class='state state-green2'>已完成</span>";
					}
					return data + "<br/>" + state;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "进入办案区时间",
				"data" : "accessDateStr",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "办案民警",
				"data" : "police",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "被讯（询）问人姓名<br/>证件号码",
				"data" : "askPerson",
				"render" : function(data, type, full, meta) {
					return data + "<br/>" + full.idNum;
				}
			}
		];
		//是否排序
		tb.ordering = false ;
		
		tb.info = false;
		
		tb.paging = false;
		
		tb.processing = true;
		//每页条数
		tb.lengthMenu = [ 5 ];
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
			d["cardId"] = cardId;
			d["machineId"] = machineId;
		};
		tb.paramsResp = function(json) {
			var harSearchBeanlst = json.harSearchBeanlst;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = harSearchBeanlst;
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		handlingAreaReceiptTable = $("#handlingAreaReceiptTable").DataTable(tb);
}
	
	jQuery.extend($.common, { 
		getHarId:function(){
			var arr = $.icheck.getChecked("harCheck");
			if(arr.length != 1){
				window.top.$.layerAlert.alert({msg:"请选择一条使用单进行操作。"}) ;
				return false;
			}
			$.each(arr,function(a,val){
				harId = $(val).val();
			});
			return harId;
		}
	});
})(jQuery);