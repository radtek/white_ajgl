(function($) {
	"use strict";
	var table;
	$(document).ready(function() {
		initTable();
	});
	
	/*
	 * 生成表格
	 */
	function initTable(){
	var tb = $.uiSettings.getOTableSettings() ;
	tb.ajax.url = context+"/operationLog/findOperationLog.action";
	tb.columnDefs = [
		{ 
			"targets": 0,
			"width": "5%",
			"title": " 客户端IP",
			"data": "clientIp" ,
			"render": function ( data, type, full, meta ) {		        	
				      return data;
			}
			
		},
		{ 
			"targets": 1,
			"width": "5%",
			"title": "用户名",
			"data": "userName" ,
			"render": function ( data, type, full, meta ) {								  
				      return data;
			}
		},
		{ 
			"targets": 2,
			"width": "30%",
			"title": "功能菜单名称",
			"data": "functionMenuName" ,
			"render": function ( data, type, full, meta ) {		        	
				      return data;
			}
		},
		{ 
			"targets": 3,
			"width": "50%",
			"title": " 操作内容",
			"data": "operateContent" ,
			"render": function ( data, type, full, meta ) {		        	
				      return data;
			}
		},
		{ 
			"targets": 4,
			"width": "5%",
			"title": " 人员姓名",
			"data": "personName" ,
			"render": function ( data, type, full, meta ) {		        	
				      return data;
			}
		},
		{ 
			"targets": 5,
			"width": "5%",
			"title": "操作时间",
			"className":"table-checkbox",
			"data": "operatingTime" ,
			"render": function ( data, type, full, meta ) {	
				      return data ;
			}
			
		},
	] ;
	 tb.paging = true ; 
	 tb.lengthMenu = [ 10 ];
	 tb.lengthChange = false;
	 tb.searching = false ;
	 tb.ordering = false ;
	 tb.paramsReq = function(d, pagerReq){
		 d["startTime"] = $.laydate.getDate("#rkDate", "start");	//时间开始
		 d["endTime"] = $.laydate.getDate("#rkDate", "end");	//时间结束
//		 var end=$.laydate.getDate("#rkDate", "end");
//		 d["endTime"] =$.date.endRange(end,"yyyy-MM-dd HH:mm");
		 d["userName"] = $("#userName").val();
		 d["menuContent"] = $("#menuContent").val();
		 d["clientIp"] = $("#clientIp").val();
	 } ;
		
	 tb.paramsResp=function(json){
	 json.recordsFiltered=json.sumCount;
	 json.recordsTotal=json.sumCount;
	 json.data=json.list;
	 };
	
     table = $("#logTable").DataTable(tb);
	}
	
    /**
     *重置 
     */
	$(document).on('click','.reset',function(){
		$.laydate.reset("#rkDate");
		$("#userName").val("");
		$("#menuContent").val("");
		$("#clientIp").val("");
		table.draw(true);
	});
	

	/**
	 * 查询
	 */
 	$(document).on('click','.search',function(){
		
		table.draw(true);
    });
})(jQuery);