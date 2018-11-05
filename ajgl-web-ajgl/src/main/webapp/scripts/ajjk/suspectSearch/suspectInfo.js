(function($){
	"use strict";
	$(document).ready(function() {
		$(document).on("click","#back",function(){
			history.go(-1);
		});
		$(document).on("click","#refresh",function(){
			location.reload(true);  
		});
		/**
		 * 查看案件详情
		 */
		$(document).on("click",".showDetail",function(){
			window.parent.open($.util.fmtUrl(context +"/caseSearch/showCaseDetail.action?caseCode=" + $(this).attr("caseCode")));
		});
		/**
		 * 查看人员详情
		 */
		$(document).on("click",".personDetail",function(){
			window.location.href = $.util.fmtUrl(context +"/suspectSearch/showPersonDetailPage.action?dataId=" + $("#dataId").val());
		});
		/**
		 * 查看嫌疑详情
		 */
		$(document).on("click",".suspectDetail",function(){
			window.location.href = $.util.fmtUrl(context +"/suspectSearch/showSuspectDetailPage.action?dataId=" + $(this).attr("personId"));
		});
		$.ajax({
			url:context +'/suspectSearch/searchPersonInfo.action',
			type:'post',
			data:{dataId : $("#dataId").val()},
			dataType:'json',
			success:function(successData){
				$.each($(".valCell"),function(){
					if($(this).attr("valName") == "birthdayStart"){
						var arr = successData.criminalPersonBean[$(this).attr("valName")].split(" ");
						$(this).text(arr[0]);
					}else{
						$(this).text(successData.criminalPersonBean[$(this).attr("valName")]);
					}
				})
				$("#personState").text(successData.personState == null ? "" : successData.personState);
				$("#dispose").text(successData.dispose == null ? "" : successData.dispose);
				initTable(successData.criminalBasicCaseBeanLst);
				initPersonTrace(successData.caseExecutionProcessBeanMap);
			}
		});
	});
	
	/**
	 * 加载人员追踪
	 */
	function initPersonTrace(caseExecutionProcessBeanMap){
		$("#personTrace").empty();
		var str = "";
		$.each(caseExecutionProcessBeanMap,function(key,val){
			str +='<ul class="nav nav-tabs"><li class="event-title"><h2>'+val[0].caseName+'</h2></li>';
			$.each(val,function(index,itm){
				str += '<li class="addClass ">'+
				'<span class="icon-red-dot"></span>'+
				'<p class="time-box">'+(itm.issuedTime == "" ? "未填写":itm.issuedTime)+'</p>'+
				'<div class="con-box">'+
					'<span class="arrow"></span>'+itm.stepName+''+
				'</div>'+
				'<div class="append-btn"></div>';
				str += '</li>';
			})
			str += '</ul>';
		});
		$("#personTrace").append(str);
	}
	/**
	 * 单机改变颜色
	 */
	$(document).on("click",".addClass",function(){
		$(".addClass").removeClass("active");
		$(this).addClass("active");
	});
	
	function initTable(suspectLst){
		var tb = $.uiSettings.getLocalOTableSettings();
			tb.data = suspectLst;
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
					"title" : "案件状态",
					"data" : "caseState",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
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
					"targets" : 5,
					"width" : "",
					"title" : "涉案信息",
					"data" : "suspectPersonId",
					"render" : function(data, type, full, meta) {
						return "<a href='###' class='suspectDetail' personId='" + data + "'>查看涉案人员信息</a>";
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
			
			$("#suspectInfo").DataTable(tb);
	}
	
})(jQuery);
