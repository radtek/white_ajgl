(function($){
	"use strict";
	//文件上传
	var index = null;
	var policeTable = null;
	var policeData = [];
	var deleteLst = [];
	$(document).ready(function() {
		var p$ = window.top.$;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		var cre$ = frameData.initData.cre$;
		policeData = frameData.initData.policeData;
		deleteLst = frameData.initData.deleteLst;
		initTable(policeData, frameData.initData.modify);
		$(document).on("click",".deletePolice",function(){
			deleteLst.push($(this).attr("id"));
			$($(this).parents('tr')[0]).remove();
		});
		$(document).on("click",".showDetail",function(){
			var data;
			for(var i in policeData){
				if($(this).attr("id") == policeData[i].id){
					data = policeData[i];
				}
			}
			var initData = {
					cre$ : $,
					data : data
				}
			window.top.$.layerAlert.dialog({
				content : context +  '/handlingAreaReceipt/showPoliceDetail.action',
				pageLoading : true,
				title:"本次讯(询)问办案民警信息查看",
				width : "530px",
				height : "360px",
				initData:function(){
					return $.util.exist(initData)?initData:{} ;
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		btn:["关闭"],
	    		callBacks:{
				    btn1:function(index, layero){
				    	window.top.layer.close(index); 
				    }
				}
			});
		});
	});
	/**
	 * 初始化table
	 */
	function initTable(policeData, flag){
		var tb = $.uiSettings.getLocalOTableSettings();
		tb.data = policeData;
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "10%",
     	    	"title": "序号",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return meta.row+1;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "姓名",
				"data" : "policeName",
				"render" : function(data, type, full, meta) {
					return "<a href='###' id='" + full.id + "' class='a-link mar-right showDetail'>" + data + "</a>";
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "警号（或辅警编号）",
				"data" : "policeNum",
				"render" : function(data, type, full, meta) {
					return "<a href='###' id='" + full.id + "' class='a-link mar-right showDetail'>" + data + "</a>";
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "是否主办民警",
				"data" : "ifMainPolice",
				"render" : function(data, type, full, meta) {
					if(data == $.common.Constant.SF_S()){
						return '<input type="checkbox" class="icheckbox" checked disabled>';
					}else{
						return '<input type="checkbox" class="icheckbox" disabled>';
					}
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "发卡核对时间",
				"data" : "sendCardTime",
				"render" : function(data, type, full, meta) {
					return $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
				}
			},
			{
				"targets" : 5,
				"width" : "",
				"title" : "操作",
				"data" : "policeNum",
				"render" : function(data, type, full, meta) {
					return '<button title="删除并解绑" id="' + data + '" class="deletePolice btn btn-default btn-xs"><span class="icon-trash"></span></button>';
				}
			}
		];
		if(flag != "true"){
			tb.columnDefs.pop();
		}
		tb.info = false ;
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
		tb.myDrawCallback = function(){
			var t1 = window.setTimeout(setColor,200); 
		}
		//请求参数
		policeTable = $("#policeTable").DataTable(tb);
	}

	function setColor(){
		$(".icheckbox_square-green").removeClass("disabled");
	}
	
	jQuery.extend($.common, { 
		closeWindow:function(index){
			window.top.layer.close(index);
		},
		getDelectLst:function(){
			return deleteLst;
		}
	});
})(jQuery);