(function($){
	"use strict";
	var suspectTable = null;
	$(document).ready(function() {
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			suspectTable.draw();
		});
		
//		$(document).on("click" , "#accentDiv", function(e){
//			var tree = $.zTreeMenu.getTree("accent") ;
//			tree.showMenu() ;
//		});
		
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			$("#name").val("");
			$.select2.val("#sex","");
			$.laydate.reset("#birthday");
			$("#idNum").val("");
			$("#address").val("");
//			$("#nativeAddress").val("");
//			$.select2.val("#accent","");
			$("#caseCode").val("");
			$("#caseName").val("");
			$("#aboutCase").val("");
			$("#accentId").val("");
			$("#disposePerson").val("");
			suspectTable.draw();
		});
		
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showDetail",function(){
			window.parent.open($.util.fmtUrl(context +"/suspectSearch/showSuspectInfoPage.action?dataId=" + $(this).attr("suspectCode")));
		});

		initData();
		initSuspectTable();
	});
	/**
	 * 初始化table
	 */
	function initSuspectTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/suspectSearch/searchAllSuspectByPage.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "40px",
         	    	"title": "序号",
         	    	"className":"table-checkbox",
         	    	"data": "suspectCode" ,
         	    	"render": function ( data, type, full, meta ) {
         	    			  return meta.row+1;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "50px",
					"title" : "姓名",
					"data" : "name",
					"render" : function(data, type, full, meta) {
						return "<a href='###' suspectCode='" + full.id + "' class='showDetail'>" + data + "</a>";
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "是否已抓获",
					"data" : "catched",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "是否已处理",
					"data" : "disposed",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "性别",
					"data" : "sex",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "出生年月",
					"data" : "birthday",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "",
					"title" : "身份证号",
					"data" : "idNumber",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 7,
					"width" : "",
					"title" : "联系电话",
					"data" : "telephone",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 8,
					"width" : "",
					"title" : "现居住地",
					"data" : "address",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 9,
					"width" : "",
					"title" : "案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
//				{
//					"targets" : 8,
//					"width" : "",
//					"title" : "籍贯地址",
//					"data" : "nativePlace",
//					"render" : function(data, type, full, meta) {
//						return data;
//					}
//				},
				
				{
					"targets" : 10,
					"width" : "",
					"title" : "案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data ;
					}
				},
//				{
//					"targets" : 10,
//					"width" : "",
//					"title" : "口音、身高、体重",
//					"data" : "tone",
//					"render" : function(data, type, full, meta) {
//						return data + "  /  " + full.stature + "  /  " + full.weight;
//					}
//				},
			];
			//是否排序
			tb.ordering = false ;
			//每页条数
			tb.lengthMenu = [ 15 ];
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
					obj.name = $("#name").val();
					obj.sex = $.select2.val("#sex");
					obj.birthdayStart = $.laydate.getTime("#birthday", "start");
					obj.birthdayEnd = $.date.endRangeByTime($.laydate.getTime("#birthday", "end"), "yyyy-MM-dd");
					obj.idNumber = $("#idNum").val();
					obj.address = $("#address").val();
//					obj.nativePlace = $("#nativeAddress").val();
					obj.caseCode = $("#caseCode").val();
					obj.caseName = $("#caseName").val();
					
					obj.disposePerson = $("#disposePerson").val();
					
//					obj.tone = $("#accentId").val();
					obj.cases = $("#aboutCase").val();
				}else{
					obj.name = ($("#simpleName").val() == "姓名模糊查询" ? "" : $("#simpleName").val());
				}
				$.util.objToStrutsFormData(obj,"criminalPersonSearchBean",d);
			};
			tb.paramsResp = function(json) {
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = json.cpsBeanLst;
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			suspectTable = $("#suspectTable").DataTable(tb);
	}
	
	/**
	 * 初始化选项
	 */
	function initData(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : "xb"},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#sex", successData.dictionaryItemLst, "code", "name", true, true);
			}
		});
//		$.zTreeMenu.init("accent", context + "/webUtil/findDictionaryItemsByTypeForTree.action?dictionaryType=xzqh", {
//			async:{
//				enable:true,
//			},
//			callBacks:{
//				formatNodeData:function(nodeData){
//					nodeData.iconSkin = "dw" ;
//		  		}
//			}
//		},{
//			check: {
//				enable: false
//			},
//			callback: {
//				onClick:function(event, treeId, treeNode){
//					var tobj = $.zTreeMenu.getTree(treeId);
//					if($("#accentId").val() == treeNode.diyMap["code"]){
//						$(tobj.inputDom).val("");
//						$("#accentId").val("");
//						return;
//					}
//					$(tobj.inputDom).val(treeNode.name);
//					$("#accentId").val(treeNode.diyMap["code"]);
//		  		}
//			}
//		});
	}
})(jQuery);