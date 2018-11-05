(function($){
	"use strict";
	//文件上传
	var index = null;
	var policeTable = null;
	var harId = "";
	var parent$=null;
	$(document).ready(function() {
		var p$=window.top.$;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		harId = frameData.initData.harId;
		initTable();
		/**
		 * 解绑手环
		 */
		$(document).on("click",".deletePolice",function(){
			$.ajax({
				url:context +'/leaveSituation/locationCardControl.action',
				type:'post',
				data:{
					id:harId,
					policeNum:$(this).attr("id")
				},
				dataType:'json',
				success:function(successData){
					if(successData.flag == "true"){
						window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示",end:function(){
								policeTable.draw();
							}
						});
						
					}else{
						window.top.$.layerAlert.alert({msg:successData.errorMessage ,title:"提示"});
					}
				}
			})
		});
	});
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/leaveSituation/queryPoliceCardList.action";
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
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "警号（或辅警编号）",
				"data" : "policeNum",
				"render" : function(data, type, full, meta) {
					return data;
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
					return '<button title="解绑" id="' + data + '" class="deletePolice btn btn-default btn-xs">解绑</button>';
				}
			}
		];
		tb.paging = false ;
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
		//请求参数
		tb.paramsReq = function(d, pagerReq){
			d["id"] = harId;
		};
		tb.paramsResp = function(json) {
			parent$.leaveSituation.setCount(json.piBeanLst.length); //查询解绑后的民警条数
			json.recordsTotal = json.piBeanLst.length;
			json.recordsFiltered = json.piBeanLst.length;
			json.data = json.piBeanLst;
		};
		policeTable = $("#policeTable").DataTable(tb);
	}

	jQuery.extend($.common, { 
		closeWindow:function(index){
			window.top.layer.close(index);
		},
		getDelectLst:function(){
			return deleteLst;
		},
		set$:function(obj){
			parent$=obj;
		}
	});
})(jQuery);