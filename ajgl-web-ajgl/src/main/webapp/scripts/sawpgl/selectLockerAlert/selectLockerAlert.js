$.selectLockerAlert = $.selectLockerAlert || {}; 
(function($){
	"use strict"; 
	
	var lockerTable = null;
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var storageAreaId = initData.storageAreaId;//保管区id
	var strId = initData.listId;//选中的id
	var strCode = initData.listCode;//选中的Code
	var listId=[];
	var listCode=[];
	if(strId!=''){
		listId=strId.split(",");
		listCode=strCode.split(",");
	}	
	$(document).ready(function() {	
		initlockerTable();
		$(document).on("ifUnchecked", "input", function(event){
			var a=$(this).attr("code");
			removeByValue(listCode,$(this).attr("code"));
			removeByValue(listId,$(this).attr("value"));
	     });
		$(document).on("ifChecked", "input", function(event){
			listCode.push($(this).attr("code"));
			listId.push($(this).attr("value"));
		});
	});
	
	/**
	 * 删除数组中指定的元素
	 */
	function removeByValue(arr,val){
		for(var i=0;i<arr.length;i++){
			if(arr[i]==val){
				arr.splice(i,1);
				break;
			}
		}
	}
	/**
	 * 储物箱table
	 */
	function initlockerTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/lockerMaintain/queryLocker.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "50px",
         	    	"title": "选择",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    			  var a = '<input type="checkbox" name="locker" class="icheckbox redioClass" code="'+full.code+'" value="'+data+'"/>' ;
         	    			  return a;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "编号",
					"data" : "code",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "储物箱位置",
					"data" : "location",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "所属物证保管区",
					"data" : "area",
					"render" : function(data, type, full, meta) {
						return data.name;
					}
				},
				{
					"targets" : 4,
					"width" : "400px",
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
			tb.lengthMenu = [ 7 ];
			//默认搜索框
			tb.searching = false ;
			//能否改变lengthMenu
			tb.lengthChange = false ;
			//自动TFoot
			tb.autoFooter = false ;
			//自动列宽
			tb.autoWidth = true ;
			tb.paging = true;
			//请求参数
			tb.paramsReq = function(d, pagerReq){
				d["storageAreaId"] = storageAreaId;
			};
			tb.paramsResp = function(json) {
				var articleLockerBeanList = json.articleLockerBeanList;
				json.data = articleLockerBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
			};
			tb.rowCallback = function(row,data, index) {
				if(listId!=null){
					for(var i=0;i<listId.length;i++){
						 if(data.id==listId[i]){
							 $.icheck.check($(row).find('.table-checkbox'),true);
						 }
					 }
				}
				 
			};
			tb.preDrawCallback=function(settings ){
				
			};
			lockerTable = $("#lockerTable").DataTable(tb);
	}
	
	
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.selectLockerAlert, { 
		getArrId : function(){
			return  listId 
		},
		getArrCode : function(){
			return  listCode 
		}
	});
})(jQuery);