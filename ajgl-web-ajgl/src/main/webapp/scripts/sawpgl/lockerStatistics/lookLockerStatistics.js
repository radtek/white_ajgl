$.lookLockerStatistics = $.lookLockerStatistics || {};
(function($){
	
	"use strict";
	var cwgTable = null;
	var listId=[];
	$(document).ready(function(){
		initSelect();
		initCwgTable();
		
		/**
		 * 保管区select选择事件
		 */
		$(document).on("change","#area",function(){
//			storageAreaId = $.select2.val("#area");
//			initLockerSelect(storageAreaId);
			$("#locker").val("");
			$("#locker").attr("arrId","");
			listId=[];
		});
		/**
		 * 储物箱点击事件
		 */
		$(document).on("click","#locker",function(){
			var storageAreaId = $.select2.val("#area");//保管区id
            if(storageAreaId!=null&&storageAreaId!=""){
            	alertLockerSelectPage(storageAreaId);
            }
		});
		
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			cwgTable.draw(true);
		});
		/**
		 * 刷新按钮事件
		 */
		$(document).on("click","#refBtn",function(){
			window.location.href=window.location.href;
		});
		
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#reset",function(){
			$.select2.clear("#area");
			$.select2.clear("#status");
//			$.select2.clear("#locker");
			$("#locker").val("");
			$("#locker").attr("arrId","");
			listId=[];
			$("#caseCodeOrName").val("");
			$("#suspectedNameOrIdcode").val("");
			$("#policeName").val("");
			cwgTable.draw(true);
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
			var d=new Object();
			if($.common.isFullConditionSearch()){
				d["storageAreaId"] = $.select2.val("#area");
				d["status"] = $.select2.val("#status");
				d["caseCodeOrName"] = $("#caseCodeOrName").val();
				d["suspectedNameOrIdcode"] = $("#suspectedNameOrIdcode").val();
				d["police"] = $("#policeName").val();
			//	d["lockerIds"] = $.select2.val("#locker");	
				var optionArray = $("#locker").select2("val");	
        		if($.util.exist(optionArray)){
        			$.each(optionArray,function(o,option){
        				d["lockerIds[" + o + "]"] = option;
        			});
        		}
//				d["lockerIds"] = $.util.cloneObj($.select2.val("#locker"));				
			}else{
				var caseCodeOrName = $("#caseCodeOrName").val();
				d["caseCodeOrName"] = caseCodeOrName == "对应案件编号/名称模糊查询" ? "" : caseCodeOrName;
			}
            
			var form = $.util.getHiddenForm(context +'/lockerStatistics/exportExcel.action', d);
			$.util.subForm(form);
		});
		
	});
	
	/**
	 * 弹出储物箱编号选择页面
	 */
	function alertLockerSelectPage(storageAreaId){
		window.top.$.layerAlert.dialog({
			content : context + '/lockerStatistics/findLockerByAreaId.action',
			pageLoading : true,
			title:"查询储物箱编号",
			width : "850px",
			height : "500px",
			btn:["确定","取消"],
			callBacks:{
				btn1:function(index, layero){					
					var cm = window.top.frames["layui-layer-iframe"+index].$.selectLockerAlert ;
				     listId= cm.getArrId();
				    var listCode= cm.getArrCode();
				    var str="";
				    var strId="";
				    if(listCode!=null){
				    	for(var i=0;i<listCode.length;i++){
							str+=listCode[i]+",";
							strId+=listId[i]+",";
						}				   
				    	str=str.substring(0,str.length-1);
				    	strId=strId.substring(0,strId.length-1);
				    }
				    $('#locker').val(str);
				    $('#locker').attr("arrId",strId);
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				},
				btn2:function(index, layero){
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				
			},
			initData:{
				storageAreaId:storageAreaId,
				listId:$('#locker').attr("arrId"),
				listCode:$('#locker').val()
			},
			end:function(){
				
			}
		});
	}
	
	/**
	 * 初始化查询条件select
	 */
	function initSelect(){
		//查询保管区域
		$.ajax({
			url: context + '/lockerStatistics/findAllArea.action',
			type:"POST",	
			data:{},
			customizedOpt:{
				//设置是否loading
				ajaxLoading:false,
			},
			dataType:"json",
			success:function(data){
				$.select2.addByList("#area", data.optionItemBeanList,"id","name",true,true);//设置下拉框选项
			}
		});
		
		//是否
		$.ajax({
			url: context + '/lockerStatistics/findStatusSelect.action',
			type:"POST",	
			data:{},
			customizedOpt:{
				//设置是否loading
				ajaxLoading:false,
			},
			dataType:"json",
			success:function(data){
				$.select2.addByList("#status", data.optionItemBeanList,"id","name",true,true);//设置下拉框选项
			}
		});
	}
	
	function initLockerSelect (storageAreaId){
		$("#locker").html("");
		$.ajax({
			url: context + '/lockerStatistics/findAllLockerByAreaId.action',
			type:"POST",	
			data:{storageAreaId : storageAreaId},
			customizedOpt:{
				//设置是否loading
				ajaxLoading:false,
			},
			dataType:"json",
			success:function(data){
				if(!$.util.isBlank($.select2.val("#area"))){
					$.select2.addByList("#locker", data.optionItemBeanList,"id","name",true,true);//设置下拉框选项
				}
			}
		});
	}
	
	/**
	 * 初始化储物箱Table
	 */
	function initCwgTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/lockerStatistics/findLockerStatistics.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "",
     	    	"title": "物证保管区",
     	    	"className":"table-checkbox",
     	    	"data": "areaName" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return data;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "储物箱",
				"data" : "lockerName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "是否空闲",
				"data" : "statutsName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "当前存放物品",
				"data" : "articleList",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data) && data.length > 0){
						$.each(data,function(a,article){
							td += article +"<br/>";
						});
					}
					return td;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "在库总件数",
				"data" : "numberInStorage",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "",
				"title" : "对应案件编号",
				"data" : "caseCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "",
				"title" : "对应案件名称",
				"data" : "caseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 7,
				"width" : "",
				"title" : "对应犯罪嫌疑人姓名/身份证号",
				"data" : "suspectIdentityNumber",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 8,
				"width" : "",
				"title" : "办案民警",
				"data" : "polices",
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
				d["storageAreaId"] = $.select2.val("#area");
				d["status"] = $.select2.val("#status");
				d["caseCodeOrName"] = $("#caseCodeOrName").val();
				d["suspectedNameOrIdcode"] = $("#suspectedNameOrIdcode").val();
				d["police"] = $("#policeName").val();
				if($.util.exist(listId)){
        			$.each(listId,function(o,option){
        				d["lockerIds[" + o + "]"] = option;
        			});
        		}
//				d["lockerIds"] = $.util.cloneObj($.select2.val("#locker"));
				
			}else{
				var caseCodeOrName = $("#caseCodeText").val();
				d["caseCodeOrName"] = caseCodeOrName == "对应案件编号/名称模糊查询" ? "" : caseCodeOrName;
			}
		};
		tb.paramsResp = function(json) {
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = json.lockerStatisticsServiceBeanList;
			console.log(json.lockerStatisticsServiceBeanList);
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		cwgTable = $("#cwgTable").DataTable(tb);
	}
	
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.lookLockerStatistics, { 
		
	});
	
})(jQuery)