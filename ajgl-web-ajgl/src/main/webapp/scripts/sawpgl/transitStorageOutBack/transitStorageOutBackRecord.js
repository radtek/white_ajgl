(function($){
	"use strict";
	
	var transitStorageOutBackRecordTable = null;
	var today = new Date();
	var yesterday = new Date();
	
	$(document).ready(function() {
		yesterday.setDate(yesterday.getDate() - 1);
//		$("#checkedYes").closest("div").addClass("checked");
		$.icheck.check("#checkedYes",true);
		$('#checkedYes').val($.common.Constant.SF_S());
		$('#checkedNo').val($.common.Constant.SF_F());
		$(document).on("click",".dlws",function(){
			$.layerAlert.img("data:image/png;base64," + $(this).attr("fileId"),400);
//			window.open(context + "/activityRecord/downloadFile.action?attachmentId="+ $(this).attr("fileId"));
		});
		$(document).on("click",".qrFile",function(){
			var tempDiv = $("<div/>");
			$(tempDiv).qrcode({
			    "render": 'image',
			    "size": 110,
			    "color": "#3a3",
			    "background": "white",
			    "text": $(this).attr("fileId")
			});
			$.layerAlert.img($(tempDiv).find("img").attr("src"),130,10);
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','.excel',function(){
			var d=new Object();
			if($.common.isFullConditionSearch()){
				d["transitStorageOutBackBean.code"] = $("#code").val();
				d["transitStorageOutBackBean.caseCode"] = $("#caseCode").val();
				d["transitStorageOutBackBean.suspectName"] = $("#suspectName").val();
				d["startTime"] = $.laydate.getTime("#ckDate", "start");	//时间开始
				d["endTime"] = $.date.endRangeByTime($.laydate.getTime("#ckDate", "end"),"yyyy-MM-dd HH:mm");	    //时间结束
				d["sf"] = $('.checked input').val();
			}else{
				var endTime = new Date();
				endTime.setDate(endTime.getDate() + 1);
				d["startTime"] = $.date.strToTime($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	//时间开始
				d["endTime"] = $.date.strToTime($.date.dateToStr(endTime,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	 
				d["transitStorageOutBackBean.code"] = $("#simpleCode").val() == "出库返还单编号模糊查询" ? "" : $("#simpleCode").val();
//				d["sf"] = $('.checked input').val(); //是否显示我的出库单
				d["sf"] = $.common.Constant.SF_S(); //是否显示我的出库单
			}
			var form = $.util.getHiddenForm(context +'/transitStorageOutBack/exportExcel.action', d);
			$.util.subForm(form);
		});	
		/**
		 * 查看按钮事件
		 */
		$(document).on("click",".showTransitStorageOutBack",function(){
			window.location.href = $.util.fmtUrl(context + "/transitStorageOutBack/toShowRecord.action?&&code=" + $(this).text());					
		});
		/**
		 * 新建按钮事件
		 */
		$(document).on("click",".addOut",function(){
			window.location.href = $.util.fmtUrl(context + "/transitStorageOutBack/toNewRecord.action");					
		});
		
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			transitStorageOutBackRecordTable.draw(true);
		});
		
		/**
		 * 刷新按钮事件
		 */
		$(document).on("click",".reloadBtn",function(){
			location.reload(true);   
		});
		
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#reset",function(){
			resetSearchCondition();
		});
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showCaseDetail",function(){
			window.parent.open($.util.fmtUrl(context +"/caseSearch/showCaseDetail.action?caseCode=" + $(this).text()));
		});
		
		initData();
		initStorageInDataTable();
	});
	
	function initData(){
		$.laydate.setRange($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),$.date.dateToStr(today,"yyyy-MM-dd HH:mm"),"#ckDate");
	}
	
	function initStorageInDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/transitStorageOutBack/queryTransitStorageOutBackRecord.action";
			tb.columnDefs = [
//				{
//					"targets": 0,
//         	    	"width": "50px",
//         	    	"title": "选择",
//         	    	"className":"table-checkbox",
//         	    	"data": "id" ,
//         	    	"render": function ( data, type, full, meta ) {
//         	    		var a = '<input type="checkbox" name="ckd" class="icheckbox" value="'+data+'"/>' ;
//   	    			    return a;
//         	    	}
//				},
				{
					"targets" : 0,
					"width" : "",
					"title" : "出库单编号",
					"data" : "storageOutCode",
					"render" : function(data, type, full, meta) {
						var td = '<a class="detail showTransitStorageOutBack" href="###">' + data + '</a>&nbsp;&nbsp;';
     	    				td += '<a href="###" class="qrFile" fileId="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
     	    			return td;
					}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "出库时间",
					"data" : "storageOutDateTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "对应案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return '<a class="showCaseDetail" href="###">' + data + '</a>';
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "对应案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "办案民警",
					"data" : "solvePolice",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "出库物品对应嫌疑人或物品持有人",
					"data" : "objectOwnerPerson",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "",
					"title" : "是否返还完成",
					"data" : "ifAllOut",
					"render" : function(data, type, full, meta) {
						if(data==$.common.Constant.SF_S()){
							return '是';
						}else if(data==$.common.Constant.SF_F()){
							return '否';
						}
						return '--';
					}
				},
				{
					"targets" : 7,
					"width" : "",
					"title" : "备注",
					"data" : "remark",
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
					d["transitStorageOutBackBean.code"] = $("#code").val();
					d["transitStorageOutBackBean.caseCode"] = $("#caseCode").val();
					d["transitStorageOutBackBean.suspectName"] = $("#suspectName").val();
					d["startTime"] = $.laydate.getTime("#ckDate", "start");	//时间开始
					d["endTime"] = $.date.endRangeByTime($.laydate.getTime("#ckDate", "end"),"yyyy-MM-dd HH:mm");	    //时间结束
					d["sf"] =$('.checked input').val();;
					
				}else{
					var endTime = new Date();
					endTime.setDate(endTime.getDate() + 1);
					d["startTime"] = $.date.strToTime($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	//时间开始
					d["endTime"] = $.date.strToTime($.date.dateToStr(endTime,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	 
					d["transitStorageOutBackBean.code"] = $("#simpleCode").val() == "出库返还单编号模糊查询" ? "" : $("#simpleCode").val();
//					d["sf"] =$('.checked input').val(); //是否显示我的出库单
					d["sf"] =d["sf"] = $.common.Constant.SF_S(); //是否显示我的出库单
				}
			};
			tb.paramsResp = function(json) {
				var transitStorageOutBackBeanList = json.transitStorageOutBackBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = transitStorageOutBackBeanList;
			};
			transitStorageOutBackRecordTable = $("#transitStorageOutBackRecordTable").DataTable(tb);
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#code").val("");
		$("#caseCode").val("");
		$("#suspectName").val("");
//		$("#checkedYes").closest("div").addClass("checked");
//		$("#checkedNo").closest("div").removeClass("checked");
		initData();
		$.icheck.check("#checkedYes",true);
		transitStorageOutBackRecordTable.draw(true);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);