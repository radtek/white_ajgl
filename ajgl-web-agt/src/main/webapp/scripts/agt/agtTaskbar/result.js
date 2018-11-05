$.result = $.result || {};
(function($) {
	"use strict";
    $(document).ready(function() {    	
    	var data = window.parent.jsonData ;
    	onloadTable(data);
    	$(document).on("click","#resCon tr",function(){
    		var id= $(this).attr("baseId");
    		 window.parent.delJson(id);
    	} )
    });
    
    /**
     * 加载研判结果
     * @returns
     */
    function onloadTable(dataArr){

		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/askRoom/searchAllAskRoomListByCondition.action";
			tb.columnDefs = [ {
				"targets" : 0,
				"width" : "",
				"title" : "序号",
				"data" : "",
				"render" : function(data, type, full, meta) {
					return meta.row + 1;
				}
			}, {
				"targets" : 1,
				"width" : "",
				"title" : "研判类型",
				"data" : "instructionType",
				"render" : function(data, type, full, meta) {
					return data;
				}
			}, {
				"targets" : 2,
				"width" : "",
				"title" : "推送原因",
				"data" : "instructionContent",
				"render" : function(data, type, full, meta) {
					return data;
				}
			}, {
				"targets" : 3,
				"width" : "",
				"title" : "推送时间",
				"data" : "instructionTime",
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
			
			};
			tb.paramsResp = function(json) {
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = askRoomBeanList;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			 $("#resConTable").DataTable(tb);
	
    	
//    	var st1 = $.uiSettings.getLocalOTableSettings();
//    	st1.data = dataArr;
//    	st1.columnDefs = [ {
//    					"targets" : 0,
//    					"width" : "",
//    					"title" : "序号",
//    					"data" : "",
//    					"render" : function(data, type, full, meta) {
//    						return meta.row + 1;
//    					}
//    				}, {
//    					"targets" : 1,
//    					"width" : "",
//    					"title" : "研判类型",
//    					"data" : "instructionType",
//    					"render" : function(data, type, full, meta) {
//    						return data;
//    					}
//    				}, {
//    					"targets" : 2,
//    					"width" : "",
//    					"title" : "推送原因",
//    					"data" : "instructionContent",
//    					"render" : function(data, type, full, meta) {
//    						return data;
//    					}
//    				}, {
//    					"targets" : 3,
//    					"width" : "",
//    					"title" : "推送时间",
//    					"data" : "instructionTime",
//    					"render" : function(data, type, full, meta) {
//    						return data;
//    					}
//    				}
//
//    				];
//
//    				st1.ordering = false;
//    				st1.paging = true;
//    				st1.hideHead = false;
//    				st1.dom = null;
//    				st1.searching = false;
//    				st1.lengthChange = false;
//    				st1.lengthMenu = [ 5, 10 ];
//    				$("#resConTable").DataTable(st1);
    }
    

    
    
})(jQuery);