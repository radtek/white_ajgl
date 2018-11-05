(function($){
	"use strict";
	var handlingAreaReceiptTable = null;
	var harSaveBtn = true;
	$(document).ready(function() {
		jumpOn();
		$(document).on("click",".img",function(){
			$.layerAlert.img($(this).attr("src"), 357);
		});
		$(document).on("click",".refresh",function(){
			location.reload();
		});
		
		/**
		 * 新建按钮事件
		 */
		$(document).on("click",".create",function(){
			jumpOff();
			var initData = {
					cre$ : $
				}
			window.top.$.layerAlert.dialog({
				content : context +  '/handlingAreaReceipt/showNewHandlingAreaReceiptPage.action',
				pageLoading : true,
				title:"新增使用单",
				width : "1100px",
				height : "700px",
				shadeClose : false,
				initData:function(){
					return $.util.exist(initData)?initData:{} ;
				},
	    		success:function(layero, index){
	    			
	    		},
	    		end:function(){
	    			handlingAreaReceiptTable.draw(false);
	    			jumpOn();
	    			harSaveBtn = true;
	    		},
	    		btn:["保存", "取消"],
	    		callBacks:{
	    			btn1:function(index, layero){
	    				if(!harSaveBtn){
	    					return;
	    				}
	    				harSaveBtn = false;
						var cm = window.top.frames["layui-layer-iframe"+index].$.common;
						cm.dialogSub(index);
				    },
				    btn2:function(index, layero){
				    	window.top.layer.close(index);
				    }
				}
			});
		});

		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			handlingAreaReceiptTable.draw(true);
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			$("#code").val("");
			$.select2.val("#roomLst","");
			$("#police").val("");
			$.select2.val("#recordLst","");
			$.select2.val("#causeOfLawCaseLst","");
			$.select2.val("#stateLst", $.common.Constant.SYDZT_QT());
			$("#askPerson").val("");
			$("#idNum").val("");
			$("#aboutCaseLst").val("");
			$.laydate.reset("#accessDate");
			handlingAreaReceiptTable.draw(true);
		});
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showDetail",function(){
			var id = $(this).attr("id");
			window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + id);
		});
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showCase",function(){
			var id = $(this).attr("caseCode");
			window.top.open(context +"/caseSearch/showCaseDetail.action?clickOrder=1&&clickListOrder=0&&caseCode=" + id);
		});
		intiData();
	});
	
	/**
	 * 案件选择
	 */
	$(document).on("click",".selectAboutCaseLst",function(){
		var initData = {
				cre$ : $
		}
		window.top.$.layerAlert.dialog({
			content : context +  '/handlingAreaReceipt/showCaseList.action',
			pageLoading : true,
			title:"选择案件",
			width : "960px",
			height : "620px",
			initData:function(){
				return $.util.exist(initData)?initData:{} ;
			},
			shadeClose : false,
    		success:function(layero, index){
    			
    		},
    		btn:["取消"],
    		callBacks:{
			    btn1:function(index, layero){
			    	window.top.layer.close(index);
			    }
			}
		});
	});
	/**
	 * 初始化table
	 */
	function initHandlingAreaReceiptDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/handlingAreaReceipt/searchAllHandlingAreaReceiptByPage.action";
			tb.columnDefs = [
//				{
//					"targets": 0,
//         	    	"width": "50px",
//         	    	"title": "选择",
//         	    	"className":"table-checkbox",
//         	    	"data": "id" ,
//         	    	"render": function ( data, type, full, meta ) {
//         	    			  var a = '<input type="checkbox" name="harCheck" class="icheckbox" value="'+data+'"/>' ;
//         	    			  return a;
//         	    	}
//				},
				{
					"targets" : 0,
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
						return "<a href='###' id='" + full.id + "' class='a-link mar-right showDetail'>" + data + "</a><br/>" + state;
					}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "使用房间",
					"data" : "roomName",
					"render" : function(data, type, full, meta) {
						return data;
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
					"title" : "申请使用人",
					"data" : "proposer",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "案由",
					"data" : "causeOfLawCaseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "",
					"title" : "所属案件",
					"data" : "aboutCaseName",
					"render" : function(data, type, full, meta) {
						if($.util.isBlank(full.aboutCase)){
							return "<span>" + data + "</span>";
						}else{
							return "<a href='###' class='showCase' caseCode='" + full.aboutCase + "'>" + data + "</a>";
						}
					}
				},
				{
					"targets" : 7,
					"width" : "",
					"title" : "被讯（询）问人姓名<br/>证件号码",
					"data" : "askPerson",
					"render" : function(data, type, full, meta) {
						return data + "<br/>" + full.idNum;
					}
				},
				{
					"targets" : 8,
					"width" : "",
					"title" : "人像采集照片",
					"data" : "photo",
					"render" : function(data, type, full, meta) {
						return "<img class='img' src='data:image/png;base64," + data + "' height='50'>";
					}
				}
			];
			//是否排序
			tb.ordering = false ;
			
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
				if($.common.isFullConditionSearch()){
					d["harSearchBean.code"] = $("#code").val();
					d["harSearchBean.room"] = $.select2.val("#roomLst");
					d["harSearchBean.police"] = $("#police").val();
					d["harSearchBean.record"] = $.select2.val("#recordLst");
					d["harSearchBean.state"] = $.select2.val("#stateLst");
					d["harSearchBean.causeOfLawCase"] = $.select2.val("#causeOfLawCaseLst");
					d["harSearchBean.askPerson"] = $("#askPerson").val();
					d["harSearchBean.idNum"] = $("#idNum").val();
					d["harSearchBean.aboutCase"] = $("#aboutCaseLst").val();
					d["harSearchBean.accessDateStart"] = $.laydate.getDate("#accessDate", "start");
					d["harSearchBean.accessDateEnd"] = $.date.endRange($.laydate.getDate("#accessDate", "end"), "yyyy-MM-dd");
					d["harSearchBean.accessDateEndForInit"] = $.laydate.getDate("#accessDate", "end");
					d["fullSearch"] = true;
				}else{
					d["fullSearch"] = false;
					d["harSearchBean.code"] = $(".simpleCode").val()=="使用单编号模糊查询"?"":$(".simpleCode").val();
					d["harSearchBean.state"] = $.common.Constant.SYDZT_QT();
				}
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
	/**
	 * 初始化选项
	 */
	function intiData(){
		$.when(
		$.ajax({
			url:context +'/handlingAreaReceipt/initDataListPage.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#roomLst", successData.roomLst, "id", "name", true, true);
			}
		}),
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.SF()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#recordLst", successData.dictionaryItemLst, "id", "name", true, true);
			}
		}),
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.SYDZT()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#stateLst", successData.dictionaryItemLst, "id", "name", true, true);
				$.select2.val("#stateLst", $.common.Constant.SYDZT_QT());
			}
		}),
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.AY()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#causeOfLawCaseLst", successData.dictionaryItemLst, "id", "name", true, true);
			}
		})
//		$.ajax({
//			url:context +'/webUtil/findDictionaryItemByType.action',
//			type:'post',
//			data:{dictionaryType : $.common.Constant.BAQYY()},
//			dataType:'json',
//			success:function(successData){
//				$.select2.addByList("#enterAreaReasonsLst", successData.dictionaryItemLst, "id", "name", true, true);
//			}
//		})
		).done(function(){
//自动赋值注掉
//			if($("#fullSearchInSession").val() == "true"){
//				$.common.setFullConditionSearch(true);
//				$("#code").val(($("#codeInSession").val()==null?"":$("#codeInSession").val()));
//				$.select2.val("#roomLst", ($("#roomLstInSession").val()==null?"":$("#roomLstInSession").val()));
//				$("#police").val(($("#policeInSession").val()==null?"":$("#policeInSession").val()));
//				$.select2.val("#recordLst", ($("#recordLstInSession").val()==null?"":$("#recordLstInSession").val()));
//				$.select2.val("#stateLst", ($("#stateLstInSession").val()==null?"":$("#stateLstInSession").val()));
//				$.select2.val("#causeOfLawCaseLst", ($("#enterAreaReasonsLstInSession").val()==null?"":$("#enterAreaReasonsLstInSession").val()));
//				$("#askPerson").val(($("#askPersonInSession").val()==null?"":$("#askPersonInSession").val()));
//				$("#idNum").val(($("#idNumInSession").val()==null?"":$("#idNumInSession").val()));
//				$("#aboutCaseLst").val(($("#aboutCaseLstInSession").val()==null?"":$("#aboutCaseLstInSession").val()));
//				var start = $.util.isBlank($("#accessDateStartInSession").val())? "" : $("#accessDateStartInSession").val();
//				var end = $.util.isBlank($("#accessDateEndInSession").val())? "" : $("#accessDateEndInSession").val();
//				$.laydate.setRange(start, end, "#accessDate", "yyyy-MM-dd");
//			}
			initHandlingAreaReceiptDataTable();
		})
	}

	jQuery.extend($.common, { 
		showHarSaveBtn:function(){
			harSaveBtn = true;
			//window.$.util.ableA(".layui-layer-btn .layui-layer-btn0", false, {"class":"btn btn-primary btn-sm"});
			//$.util.ableA(".layui-layer-btn .layui-layer-btn0", true);
		},
		closeWindow:function(index){
			handlingAreaReceiptTable.draw(false);
			window.top.layer.close(index);
		},
		setCase:function(code, name, i){
			$("#aboutCaseLst").val(code);
			$("#lawCaseName").val(name);
			window.top.layer.close(i);
		}
	});
})(jQuery);