(function($){
	"use strict";
	var caseTable = null;
	$(document).ready(function() {
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			caseTable.draw();
		});
		
		$(document).on("click" , "#caseClassDiv", function(e){
			var tree = $.zTreeMenu.getTree("caseClass") ;
			tree.showMenu() ;
		});
		
		$(document).on("click" , "#caseUnitDiv", function(e){
			var tree = $.zTreeMenu.getTree("caseUnit") ;
			tree.showMenu() ;
		});
		
		$(document).on("click" , "#caseKindDiv", function(e){
			var tree = $.zTreeMenu.getTree("caseKind") ;
			tree.showMenu() ;
		});
		
		$(document).on("click" , "#caseSortDiv", function(e){
			var tree = $.zTreeMenu.getTree("caseSort") ;
			tree.showMenu() ;
		});
		
		/**
		 * 一级级联的X点击事件 id =popedom
		 */
		$(document).on("mousedown" , ".select2-selection__clear", function(e){
			if($(this).closest("select").attr("id")=="popedom"){
				$("#community").empty();
			}
		});
		
		/**
		 * 删除二级级联的内容
		 */
		$(document).on("select2:unselect ", "#popedom", function(){
			$.select2.val("#community","");
			$("#community").empty();
		});
		
		//发案辖区选择级联发案社区
		$(document).on("select2:select", "#popedom", function(){
			var popedom = $.select2.val("#popedom");
			if($.util.isBlank(popedom)){
				return ;
			}
			$.ajax({
				url:context +'/caseSearch/findCommunityByUnitCode.action',
				data:{code : popedom},
				type:"post",
				dataType:"json",
				success:function(successData){
					$("#community").empty();
					$.select2.addByList("#community", successData.dictionaryItemLst, "code", "name", true, true);
				}
			});
		});

		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : "ajlb"},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#caseSort", successData.dictionaryItemLst, "name", "name", true, true);
			}
		});
		
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			$.laydate.reset("#caseTime");
			$("#caseCode").val("");
			$("#caseName").val("");
			$.select2.val("#caseClass","");
			$("#caseKind").val("");
			$("#caseKindId").val("");
			$("#caseSort").val("");
			$("#caseSortId").val("");
			$("#caseUnit").val("");
			$("#caseUnitId").val("");
			$.select2.val("#popedom","");
			$.select2.val("#community","");
			$.select2.val("#caseState","");
			$("#disposePerson").val("");
			$("#caseAddress").val("");
			
			$("#community").empty();
			
			caseTable.draw();
		});
		
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showDetail",function(){
			window.parent.open($.util.fmtUrl(context +"/caseSearch/showCaseDetail.action?caseCode=" + $(this).attr("caseCode")));
		});

		initData();
		initCaseTable();
	});
	/**
	 * 初始化table
	 */
	function initCaseTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/caseSearch/searchAllCaseByPage.action";
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
						return "<a href='###' caseCode='" + data + "' class='showDetail'>" + data + "</a>";
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return "<a href='###' caseCode='" + full.caseCode + "' class='showDetail'>" + data + "</a>";
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "案件类型",
					"data" : "caseClass",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "案件类别",
					"data" : "caseSort",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "案件性质",
					"data" : "caseKind",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "",
					"title" : "案件状态",
					"data" : "caseState",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 7,
					"width" : "",
					"title" : "发案时间",
					"data" : "caseTimeStart",
					"render" : function(data, type, full, meta) {
						if($.util.isBlank(data)){
							return "";
						}
						return $.date.timeToStr(data, "yyyy-MM-dd<br/>HH:mm");
					}
				},
					{
					"targets" : 8,
					"width" : "",
					"title" : "办案单位",
					"data" : "handingUnit",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 9,
					"width" : "",
					"title" : "办案民警",
					"data" : "disposePerson",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}
				,
				{
					"targets" : 10,
					"width" : "",
					"title" : "发案辖区",
					"data" : "popedom",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 11,
					"width" : "",
					"title" : "发案社区",
					"data" : "community",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 12,
					"width" : "",
					"title" : "发案地点",
					"data" : "caseAddress",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}
//				,
//				{
//					"targets" : 12,
//					"width" : "",
//					"title" : "案件时段",
//					"data" : "caseAddress",
//					"render" : function(data, type, full, meta) {
//						return data;
//					}
//				}
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
				if($.common.isFullConditionSearch()){
					obj.caseTimeStart = $.laydate.getTime("#caseTime", "start");
					obj.caseTimeEnd = $.date.endRangeByTime($.laydate.getTime("#caseTime", "end"), "yyyy-MM-dd HH:mm");
					obj.caseCode = $("#caseCode").val();
					obj.caseName = $("#caseName").val();
					obj.caseClass = $.select2.val("#caseClass");
					obj.caseKind = $("#caseKindId").val();
					obj.caseSort = $("#caseSortId").val();
					obj.handingUnit = $("#caseUnitId").val();
					obj.popedom = $.select2.val("#popedom");
					obj.community = $.select2.val("#community");
					obj.caseState = $.select2.val("#caseState");
					obj.disposePerson = $("#disposePerson").val();
					obj.caseAddress = $("#caseAddress").val();
				}else{
					obj.caseCode = ($("#simpleCode").val() == "编号模糊查询" ? "" : $("#simpleCode").val());
				}
				$.util.objToStrutsFormData(obj,"caseSearchBean",d);
			};
			tb.paramsResp = function(json) {
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = json.cbcBeanLst;
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			caseTable = $("#caseTable").DataTable(tb);
	}
	
	/**
	 * 初始化选项
	 */
	function initData(){
		var arr = [];
		var o1 = {};
		o1.name = "刑事案件";
		arr.push(o1);
		var o2 = {};
		o2.name = "行政案件";
		arr.push(o2);
		$.select2.addByList("#caseClass", arr, "name", "name", true, true);
		
		$.zTreeMenu.init("caseUnit", context + "/caseSearch/queryUnit.action", {
			async:{
				enable:true,
			},
			callBacks:{
				formatNodeData:function(nodeData){
					nodeData.iconSkin = "dw" ;
		  		}
			}
		},{
			check: {
				enable: false
			},
			callback: {
				onClick:function(event, treeId, treeNode){
					var tobj = $.zTreeMenu.getTree(treeId);
					if($("#caseUnitId").val() == treeNode.diyMap["code"]){
						$(tobj.inputDom).val("");
						$("#caseUnitId").val("");
						return;
					}
					$(tobj.inputDom).val(treeNode.name);
					$("#caseUnitId").val(treeNode.diyMap["code"]);
		  		}
			}
		});
		
		$.zTreeMenu.init("caseKind", context + "/webUtil/findDictionaryItemsByTypeForTree.action?dictionaryType=ajxz", {
			async:{
				enable:true,
			},
			callBacks:{
				formatNodeData:function(nodeData){
					nodeData.iconSkin = "dw" ;
		  		}
			}
		},{
			check: {
				enable: false
			},
			callback: {
				onClick:function(event, treeId, treeNode){
					var tobj = $.zTreeMenu.getTree(treeId);
					if($("#caseKindId").val() == treeNode.diyMap["code"]){
						$(tobj.inputDom).val("");
						$("#caseKindId").val("");
						return;
					}
					$(tobj.inputDom).val(treeNode.name);
					$("#caseKindId").val(treeNode.diyMap["code"]);
		  		}
			}
		});
		
		$.zTreeMenu.init("caseSort", context + "/webUtil/findDictionaryItemsByTypeForTree.action?dictionaryType=ajlb", {
			async:{
				enable:true,
			},
			callBacks:{
				formatNodeData:function(nodeData){
					nodeData.iconSkin = "dw" ;
		  		}
			}
		},{
			check: {
				enable: false
			},
			callback: {
				onClick:function(event, treeId, treeNode){
					var tobj = $.zTreeMenu.getTree(treeId);
					if($("#caseSortId").val() == treeNode.diyMap["code"]){
						$(tobj.inputDom).val("");
						$("#caseSortId").val("");
						return;
					}
					$(tobj.inputDom).val(treeNode.name);
					$("#caseSortId").val(treeNode.diyMap["code"]);
		  		}
			}
		});
		
		$.ajax({
			url:context +'/caseSearch/queryPopedom.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#popedom", successData.dictionaryItemLst, "code", "name", true, true);
			}
		});
//		$.ajax({
//			url:context +'/webUtil/findDictionaryItemByType.action',
//			type:'post',
//			data:{dictionaryType : "ajsq"},
//			dataType:'json',
//			success:function(successData){
//				$.select2.addByList("#community", successData.dictionaryItemLst, "code", "name", true, true);
//			}
//		});
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : "ajzt"},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#caseState", successData.dictionaryItemLst, "code", "name", true, true);
			}
		});
	}
})(jQuery);