(function($){
	"use strict";
	var policeTable = null;
	var harSaveBtn = true;
	$(document).ready(function() {
		jumpOff();
		
		/**
		 * 获取手环号
		 */
		$(document).on("click",".read",function(){
			var str = window.top.getDsspocxObject().GetDevAndBrandIDForThird();
			var obj = JSON.parse(str);
			$("#icNum").val(obj.data["BrandID"]);
			policeTable.draw();
		});
		/**
		 * 解绑按钮事件
		 */
		$(document).on("click",".unbindPolice",function(){
			$.ajax({
				url:context +'/handlingAreaReceipt/locationCardControl.action',
				type:'post',
				data:{
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
		/**
		 * 查询
		 */
		$(document).on("click",".search",function(){
			policeTable.draw();
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			$("#policeName").val("");
			$("#policeNum").val("");
			$("#icNum").val("");
			policeTable.draw();
		});
		
		intiData();
	});
	
	/**
	 * 初始化table
	 */
	function intiData(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/handlingAreaReceipt/showAllPoliceWhoHasCard.action";
			tb.columnDefs = [
				{
					"targets" : 0,
					"width" : "",
					"title" : "卡号",
					"data" : "icNum",
					"render" : function(data, type, full, meta) {
						return data;
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
					"title" : "警号",
					"data" : "policeNum",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "发卡时间",
					"data" : "sendCardTime",
					"render" : function(data, type, full, meta) {
						return $.date.timeToStr(data);
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "发卡人",
					"data" : "sendCardPeopleName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "备注",
					"data" : "remark",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "",
					"title" : "操作",
					"data" : "policeNum",
					"render" : function(data, type, full, meta) {
						return '<button id="' + data + '" class="unbindPolice btn btn-xs btn-primary">解绑</button>';
					}
				}
			];
			//是否排序
			tb.ordering = false ;
			
			tb.paging = false ; 
			
			tb.info = false ;
			
			tb.processing = true;
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
				if(!$.util.isBlank($("#policeName").val())){
					d["piBean.policeName"] = $("#policeName").val();
				}
				if(!$.util.isBlank($("#policeNum").val())){
					d["piBean.policeNum"] = $("#policeNum").val();
				}
				if(!$.util.isBlank($("#icNum").val())){
					d["piBean.icNum"] = $("#icNum").val();
				}
			};
			tb.paramsResp = function(json) {
				json.data = json.policeInfoBeanLst;
			};
			policeTable = $("#policeTable").DataTable(tb);
	}

})(jQuery);