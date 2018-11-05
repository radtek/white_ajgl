(function($){
	"use strict"; 
	
	var caseTable = null;
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	
	$(document).ready(function() {	
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		
		setDefaultTime();
		
		initCaseTable();
		
		/**
		 * 搜索按钮事件
		 */
		$(document).on("click","#search",function(){
			caseTable.draw(true);
		});
		
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#reset",function(){
			resetFindCondition();
		});
	});
	
	/**
	 * 重置条件
	 */
	function resetFindCondition(){
		$("#caseName").val("");
		$("#caseCode").val("");
		$("#policeName").val("");
		$("#suspectName").val("");
		$("#unit").val("");
		$("#unitName").val("");
		setDefaultTime();
		
		caseTable.draw(true);
	}
	
	/**
	 * 设置默认时间
	 */
	function setDefaultTime(){
		var nowDate = new Date();
		var oldDate = new Date(nowDate.getTime()-1000*60*60*24*2);
		
		var nowDateStr = $.date.dateToStr(nowDate, "yyyy-MM-dd HH:mm");
		var oldDateStr = $.date.dateToStr(oldDate, "yyyy-MM-dd HH:mm");
		$.laydate.setRange(oldDateStr, nowDateStr, "#caseDate");
	}
	
	/**
	 * 初始化案件表
	 */
	function initCaseTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/selectCaseCodeAlert/findCaseByCondition.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "50px",
     	    	"title": "选择",
     	    	"className":"table-checkbox",
     	    	"data": "caseCode" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  var a = '<input type="radio" name="caseCode" class="icheckradio" value="'+data+'"/>' ;
     	    			  return a;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "案件编号",
				"data" : "caseCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "案件名称",
				"data" : "caseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "办案民警",
				"data" : "policeName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "100px",
				"title" : "嫌疑人姓名",
				"data" : "suspectName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "100px",
				"title" : "办案单位",
				"data" : "unitName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "100px",
				"title" : "案发时间起",
				"data" : "caseTimeStart",
				"render" : function(data, type, full, meta) {
					return $.date.timeToStr(data,"yyyy-MM-dd HH:mm");
				}
			},
			{
				"targets" : 7,
				"width" : "100px",
				"title" : "案发时间止",
				"data" : "caseTimeEnd",
				"render" : function(data, type, full, meta) {
					return $.date.timeToStr(data,"yyyy-MM-dd HH:mm");
				}
			}
		];
		//是否排序
		tb.ordering = false ;
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
            d["caseName"] = $("#caseName").val();
            d["caseCode"] = $("#caseCode").val();
            d["policeName"] = $("#policeName").val();
            d["suspectName"] = $("#suspectName").val();
            d["unitId"] = $("#unit").val();
            d["startTime"] = $.laydate.getTime("#caseDate", "start");
            d["endTime"] = $.laydate.getTime("#caseDate", "end");
		};
		tb.paramsResp = function(json) {
			var caseBeanList = json.caseBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = caseBeanList;
		
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		caseTable = $("#caseTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		getCaseCode : function(){
			var arr = $.icheck.getChecked("caseCode");
			if($.util.exist(arr) && arr.length > 0){
				return $(arr[0]).val();
			}else{
				return null;
			}
		}
	});	
})(jQuery);