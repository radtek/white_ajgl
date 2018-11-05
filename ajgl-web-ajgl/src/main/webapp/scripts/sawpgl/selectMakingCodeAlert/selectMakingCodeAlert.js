(function($){
	"use strict"; 
	
	var makingTable = null;
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	
	$(document).ready(function() {	
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		
		setDefaultTime();
		
		initMakingTable();
		
		/**
		 * 搜索按钮事件
		 */
		$(document).on("click","#search",function(){
			makingTable.draw(true);
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
		$("#MakingCode").val("");
		$("#caseName").val("");
		$("#suspectName").val("");
		$("#suspectNum").val("");
		setDefaultTime();
		
		makingTable.draw(true);
	}
	
	/**
	 * 设置默认时间
	 */
	function setDefaultTime(){
		var nowDate = new Date();
		var oldDate = new Date(nowDate.getTime()-1000*60*60*24*2);
		
		var nowDateStr = $.date.dateToStr(nowDate, "yyyy-MM-dd HH:mm");
		var oldDateStr = $.date.dateToStr(oldDate, "yyyy-MM-dd HH:mm");
		$.laydate.setRange(oldDateStr, nowDateStr, "#makingDate");
	}
	
	/**
	 * 初始化案件表
	 */
	function initMakingTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/selectMakingCodeAlert/findMakingFromByPage.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "50px",
     	    	"title": "选择",
     	    	"className":"table-checkbox",
     	    	"data": "code" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  var a = '<input type="checkbox" name="makingCode" id ="'+data+'"  data ="'+full.id+'" class="icheckbox" value="'+data+'"/>' ;
     	    			  return a;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "使用单编号",
				"data" : "code",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "使用房间",
				"data" : "roomName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "进入办案区时间",
				"data" : "accessDateStr",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "100px",
				"title" : "办案民警",
				"data" : "police",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "100px",
				"title" : "使用单状态",
				"data" : "code",
				"render" : function(data, type, full, meta) {
					var state = "";
					if(full.state == $.common.Constant.SYDZT_QT()){
						state = "<span class='state state-blue1'>进行中</span>";
					}else if(full.state == $.common.Constant.SYDZT_YWC()){
						state = "<span class='state state-green2'>已完成</span>";
					}
					return state;
				}
			},
			{
				"targets" : 6,
				"width" : "100px",
				"title" : "申请使用人",
				"data" : "proposer",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 7,
				"width" : "100px",
				"title" : "案由",
				"data" : "causeOfLawCaseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 8,
				"width" : "100px",
				"title" : "所属案件",
				"data" : "aboutCaseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 9,
				"width" : "100px",
				"title" : "被问讯人姓名",
				"data" : "askPerson",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 10,
				"width" : "100px",
				"title" : "身份证号",
				"data" : "idNum",
				"render" : function(data, type, full, meta) {
					return data;
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
            d["MakingCode"] = $("#MakingCode").val();
            d["caseCode"] = $("#caseCode").val();
            d["suspectName"] = $("#suspectName").val();
            d["suspectNum"] = $("#suspectNum").val();
            d["startTime"] = $.laydate.getTime("#makingDate", "start");
            d["endTime"] = $.laydate.getTime("#makingDate", "end");
		};
		tb.paramsResp = function(json) {
			var harSearchBeanlst = json.harSearchBeanlst;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = harSearchBeanlst;
		
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		makingTable = $("#makingTable").DataTable(tb);
	}
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		getMakingCode : function(){
			var arr = $.icheck.getChecked("makingCode");
			if($.util.exist(arr) && arr.length > 0){
				return $(arr[0]).val();
			}else{
				return null;
			}
		},
		getMakingId : function(){
			var arr = $.icheck.getChecked("makingCode");
			if($.util.exist(arr) && arr.length > 0){
				var code =  $(arr[0]).val();
				return $("#"+code).attr("data");
			}else{
				return null;
			}
		}
	});	
})(jQuery);