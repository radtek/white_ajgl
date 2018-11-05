$.lookArticleStatistics = $.lookArticleStatistics || {};
(function($){
	"use strict";
	
	var wpTable = null;
	var jlTable = null;// 历史记录table
	
	$(document).ready(function() {	
		initWpTable();
		initJlTable();
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#reset",function(){
			$("#wpNameOrFeature").val("");
			$("#paperName").val("");
			$("#caseCodeOrName").val("");
			$("#suspectedNameOrIdcode").val("");
			$("#police").val("");
			$("#paper").val("");
			
			wpTable.draw(true);
			initJlTable();
		});

		/**
		 * 文书点击事件
		 */
		$(document).on("click",".paper",function(){
			var paperId = $(this).attr("id");
			var paperType = $(this).attr("type");
			window.top.open(context+"/showWrit/checkWrit.action?paperId=" + paperId + "&paperType=" + paperType);
		});
		
		/**
		 * 单据编号点击事件
		 */
		$(document).on("click",".form",function(){
			var formId = $(this).attr("formId");
			var formType = $(this).attr("formType");
			window.top.open(context+"/lookForm/checkForm.action?formId=" + formId + "&formType=" + formType);
		});
		
		/**
		 * 刷新按钮事件
		 */
		$(document).on("click","#refBtn",function(){
			window.location.href=window.location.href;
		});
		
		/**
		 * 物品列表行点击事件
		 */
		$(document).on("click","#wpTable tbody tr",function(){
			//改变行颜色
			$("#wpTable tbody tr").each(function(){
				$(this).css("background-color","");
			});
			$(this).css("background-color","#E2E2E2");
			//查询操作记录
			if(!$.util.exist($(this).data("data"))){
				return ;
			}
			var articleCode = $(this).data("data").articleCode;
			findOperationRecordByArticleCode(articleCode);
		});
		
		/**
		 * 刷新按钮点击事件
		 */
		$(document).on("click",".search",function(){
			wpTable.draw(true);
			//清空操作记录
			initJlTable(new Array());
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
			var d=new Object();
			if($.common.isFullConditionSearch()){
				d["wpNameOrFeature"] = $("#wpNameOrFeature").val();
				d["caseCodeOrName"] = $("#caseCodeOrName").val();
				d["suspectedNameOrIdcode"] = $("#suspectedNameOrIdcode").val();
				d["police"] = $("#police").val();
				d["paperName"] = $("#paperName").val();
			}else{
				var wpCode = $("#wpCode").val();
				d["wpCode"] = wpCode == "物品编号模糊查询" ? "" : wpCode;
			}

			var form = $.util.getHiddenForm(context +'/articleStatistics/exportExcel.action', d);
			$.util.subForm(form);
		});
	});
	
	/**
	 * 根据物品编码查询操作记录
	 * @param articleCode 物品编码
	 */
	function findOperationRecordByArticleCode(articleCode){
		$.ajax({
			url: context + '/articleStatistics/findAllOperationRecord.action',
			type:"POST",	
			data:{wpCode:articleCode},
			customizedOpt:{
				//设置是否loading
				ajaxLoading:true,
			},
			dataType:"json",
			success:function(data){
				if($.util.exist(data.operationRecordBeanList)){
					initJlTable(data.operationRecordBeanList);
				}
			}
		});
	}
	
	/**
	 * 初始化物品table
	 */
	function initWpTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/articleStatistics/findAllInvolvedArticle.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "",
     	    	"title": "物品名称",
     	    	"className":"table-checkbox",
     	    	"data": "articleName" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return data;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "特征",
				"data" : "feature",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "物品编号",
				"data" : "articleCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "V3文书中物品数量",
				"data" : "detentionNumber",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "物品性质",
				"data" : "typeName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "",
				"title" : "在库数量",
				"data" : "numberInStorage",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "",
				"title" : "计量单位",
				"data" : "measurementUnit",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 7,
				"width" : "",
				"title" : "出库数量",
				"data" : "outComingNumber",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 8,
				"width" : "",
				"title" : "再入库数量",
				"data" : "returnedNumber",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 9,
				"width" : "",
				"title" : "对应案件编号",
				"data" : "caseCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 10,
				"width" : "",
				"title" : "对应案件名称",
				"data" : "caseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 11,
				"width" : "",
				"title" : "办案民警",
				"data" : "caseHandler",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 12,
				"width" : "",
				"title" : "对应犯罪嫌疑人/物品持有人",
				"data" : "suspectName",
				"render" : function(data, type, full, meta) {
					var td = data;
					if(!$.util.isBlank(full.suspectIdentifier)){
						td += "<br/>" + full.suspectIdentifier;
					}
					return td;
				}
			},
			{
				"targets" : 13,
				"width" : "",
				"title" : "对应入库文书",
				"data" : "paper",
				"render" : function(data, type, full, meta) {
					var td = '<a class="paper" id="'+full.papersId+'" type="'+full.papersType+'" href="#">'+data+'</a>';
					return td;
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
				d["wpNameOrFeature"] = $("#wpNameOrFeature").val();
				d["caseCodeOrName"] = $("#caseCodeOrName").val();
				d["suspectedNameOrIdcode"] = $("#suspectedNameOrIdcode").val();
				d["police"] = $("#police").val();
				d["paperName"] = $("#paperName").val();
			}else{
				var wpCode = $("#wpCode").val();
				d["wpCode"] = wpCode == "物品编号模糊查询" ? "" : wpCode;
			}
		};
		tb.paramsResp = function(json) {
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = json.articleInvolvedServiceBeanList;
		
		};
		tb.rowCallback = function(row,data, index) {
			$(row).data("data",data);
			
		};
		wpTable = $("#wpTable").DataTable(tb);
	}
	
	/**
	 * 初始化物品操作历史记录table
	 * @param dataArray 操作记录数组
	 */
	function initJlTable(dataArray){
		var index = 1;
		if($.util.exist(jlTable)){
			jlTable.destroy();
			$("#jlTable").empty();
		}
		var st1 = $.uiSettings.getLocalOTableSettings();
		st1.data = dataArray;
		st1.columnDefs = [{
			"targets" : 0,
			"width" : "",
			"title" : "序号",
			"data" : "id",
			"render" : function(data, type, full, meta) {
				return index ++;
			}
		}, {
			"targets" : 1,
			"width" : "",
			"title" : "操作内容",
			"data" : "operation",
			"render" : function(data, type, full, meta) {
				return data;
			}
		}, {
			"targets" : 2,
			"width" : "",
			"title" : "操作人",
			"data" : "operator",
			"render" : function(data, type, full, meta) {
				return data;
			}
		}, {
			"targets" : 3,
			"width" : "",
			"title" : "操作时间",
			"data" : "operationTime",
			"render" : function(data, type, full, meta) {
				var td = $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
				return td;
			}
		},
		{
			"targets" : 4,
			"width" : "",
			"title" : "说明",
			"data" : "number",
			"render" : function(data, type, full, meta) {
				if($.util.isBlank(data)){
					return "";
				}
				var formA = "<a href='#' class='form' formType='" + full.formType + "' formId='" + full.formId + "'>" + full.formCode + "</a><br/>";
				switch (full.operation){
					case $.common.Constant.WP_CZJL_RK() : //入库
						var td = "入库单编号：" + formA;
							td += '对应文书：<a class="paper" id="'+full.paperId+'" type="'+full.paperType+'" href="#">'+full.paperName+'</a>';
						return td;
						break;
					case $.common.Constant.WP_CZJL_CK() : //出库
						var td = "出库单编号：" + formA;
							td += "本次出库数量：" + data;
						return td;
						break;
					case $.common.Constant.WP_CZJL_FH() : //再入库
						var td = "再入库单编号：" + formA;
							td += "本次再入库数量：" + data;
						return td;
						break;
					case $.common.Constant.WP_CZJL_TZ() : //调整储存位置
						var td = "调整单编号：" + formA;
							td += "原储存位置：" + data;
						return td;
						break;
				}
			}
		}];
		
		st1.ordering = false;
		st1.paging = true;
		st1.autoFoot = false;
		st1.dom = null;
		st1.searching = false;
		st1.lengthChange = false;
//		st1.lengthMenu = [ 5 ];
		
		jlTable = $("#jlTable").DataTable(st1);
	}
	
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.lookArticleStatistics, { 
		
	});	
})(jQuery);