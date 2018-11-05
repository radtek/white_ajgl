(function($) {
	"use strict";
	var pCaseTable = null;
	var aCaseTable = null;
	var showTable = null;
	var fixedValue = null;
	var ifSolved=null;
	$(document).ready(function() {
		resetDate("#caseTime");
		$(document).on("click" , "#sponsorDiv", function(e){
			var tree = $.zTreeMenu.getTree("sponsor") ;
			tree.showMenu() ;
		});
		$(document).on("click" , "#caseSortDiv", function(e){
			var tree = $.zTreeMenu.getTree("caseSort") ;
			tree.showMenu() ;
		});
		$(document).on("click" , "#xingshi", function(e){
			showTable = "xingshi";
		});
		$(document).on("click" , "#xingzheng", function(e){
			showTable = "xingzheng";
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
		 * 导出按钮事件
		 */
		$(document).on("click","#exportExcel",function(){
			var url ="";
			if(showTable == "xingzheng"){
				url = context +'/unsolvedCaseList/exportExcelAdministrative.action'
			}else{
				url = context +'/unsolvedCaseList/exportExcelPenal.action'
			}
			var d = {};
			//d["searchMonth"] = $.select2.val("#searchMonth");
			d["caseCodeOrName"] = $("#caseCodeOrName").val();
			d["sponsor"] = $("#sponsorId").val();
			d["caseSort"] = $("#caseSortId").val();
			d["queryTime"] = $("#caseTimeSelect").val();
			
			d["disposePerson"] = $("#disposePerson").val();
			
			ifSolved = $.select2.val("#ifSolved");
			if( ifSolved == "全部"){
				ifSolved = null;
			}
			d["ifSolved"] = ifSolved;
			
			var form = $.util.getHiddenForm(url, d);
			$.util.subForm(form);
		});
		
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			pCaseTable.draw();
			aCaseTable.draw();
		});
		/**
		 * 重置
		 */
		$(document).on("click",".reset",function(){
			//resetDate("#caseTime");
			$("#caseCodeOrName").val("");
			$("#sponsor").val("");
			$("#sponsorId").val("");
			$("#caseSort").val("");
			$.select2.val("#caseTimeSelect", fixedValue);
			$("#caseSortId").val("");
			$("#disposePerson").val("");
			
			$.select2.val("#ifSolved", "全部");
			
			pCaseTable.draw();
			aCaseTable.draw();
		});
		
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showCase",function(){
			window.parent.open($.util.fmtUrl(context +"/caseSearch/showCaseDetail.action?caseCode=" + $(this).attr("caseCode")));
		});
		
		$.zTreeMenu.init("sponsor", context + "/caseSearch/queryUnit.action", {
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
					if($("#sponsorId").val() == treeNode.diyMap["code"]){
						$(tobj.inputDom).val("");
						$("#sponsorId").val("");
						return;
					}
					$(tobj.inputDom).val(treeNode.name);
					$("#sponsorId").val(treeNode.diyMap["code"]);
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
		

		caseTimeSelect();
		
		initTable();
	});
	
	/*
	 * 查询月份选择
	 */
	function caseTimeSelect(){
		var date =  new Date() ;
		var str ="";
		date.setDate(1);
//测试时候i的值设置为47，正式上线i的值设置为11			
		for(var  i = 0; i<= 47; i++){
			var time = $.date.dateToStr(date, "yyyy-MM");
			date.setMonth(date.getMonth()-1);
			if(i == 1){
				fixedValue = time;
			}
			str = str += '<option value="'+time+'">' ;
			  str += time ;
			  str += "</option>" ;
//测试注销，正式上线取消注释			  
//			  if(startDate == time){
//				  break;
//			  }
		}
		$("#caseTimeSelect").append(str) ;
		
		$.select2.val("#caseTimeSelect", fixedValue);
	}
	
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "5%",
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
						return "<a href='###' caseCode='" + data + "' class='showCase'>" + data + "</a>";
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return "<a href='###' caseCode='" + full.caseCode + "' class='showCase'>" + data + "</a>";
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "案件类别",
					"data" : "caseSort",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "办案单位",
					"data" : "sponsor",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "主办侦查员",
					"data" : "investigator",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "6%",
					"title" : "案件状态",
					"data" : "state",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				
				{
					"targets" : 7,
					"width" : "",
					"title" : "发案时间",
					"data" : "happenedTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 8,
					"width" : "",
					"title" : "发案地点",
					"data" : "happenedAddress",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 9,
					"width" : "",
					"title" : "简要案情",
					"data" : "detail",
					"render" : function(data, type, full, meta) {
						if(data.length > 30){
							return '<span title="' + data + '">' + data.substr(0,30) + '...</span>';
						}else{
							return '<span>' + data + '</span>';
						}
					}
				},
				{
					"targets" : 10,
					"width" : "",
					"title" : "是否破案",
					"data" : "ifSolved",
					"render" : function(data, type, full, meta) {
						return '<span>' + data + '</span>';
						
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
				d["queryTime"] = $("#caseTimeSelect").val();
				//d["caseTimeStart"] = $.laydate.getTime("#caseTime", "start");
				//d["caseTimeEnd"] = $.date.endRangeByTime($.laydate.getTime("#caseTime", "end"), "yyyy-MM-dd");
				d["caseCodeOrName"] = $("#caseCodeOrName").val();
				d["sponsor"] = $("#sponsorId").val();
				d["caseSort"] = $("#caseSortId").val();
				
				d["disposePerson"] = $("#disposePerson").val();
				
				ifSolved = $.select2.val("#ifSolved");
				if( ifSolved == "全部"){
					ifSolved = null;
				}
				d["ifSolved"] = ifSolved;
			};
			tb.paramsResp = function(json) {
				json.data = json.unsolvedCaseBeanLst;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				if(ifSolved!=null){
					$('#xsDiv').hide();
					$('#xzDiv').hide();
				}else{
					$('#xsDiv').show();
					$('#xzDiv').show();
					if(json.xingshilian != null){
						$("#xingshilian").text(json.xingshilian);
					}
					if(json.xingshipoan != null){
						$("#xingshipoan").text(json.xingshipoan);
					}
					if(json.xingzhenglian != null){
						$("#xingzhenglian").text(json.xingzhenglian);
					}
					if(json.xingzhengpoan != null){
						$("#xingzhengpoan").text(json.xingzhengpoan);
					}
				}
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			var pTb = $.util.cloneObj(tb);
			pTb.ajax.url = context + "/unsolvedCaseList/searchPenalCaseByPage.action";
			pCaseTable = $("#pCaseTable").DataTable(pTb);
			var aTb = $.util.cloneObj(tb);
			aTb.ajax.url = context + "/unsolvedCaseList/searchAdministrativeCaseByPage.action";
			aTb.columnDefs[10].title="是否结案";
			aCaseTable = $("#aCaseTable").DataTable(aTb);
	}
	function resetDate(selecter){
		var date = new Date();
		
		var monthStartDate = new Date(date.getYear(), date.getMonth()-1, 1); 
		var monthEndDate = new Date(date.getYear(), date.getMonth(), 1); 
		var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24); 
		
		date.setMonth(date.getMonth()-1);
		date.setDate(days);
		
		var endVal = $.date.dateToStr(date, "yyyy-MM-dd");
		date.setDate(1);
		var startVal = $.date.dateToStr(date, "yyyy-MM-dd");
		//$.laydate.setRange(startVal, endVal, selecter, "yyyy-MM-dd");
	}
})(jQuery);