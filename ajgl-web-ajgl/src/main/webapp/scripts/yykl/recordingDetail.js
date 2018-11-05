(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var formId = initData.formCode;
	$(document).ready(function() {
		onloadData();
	});
	
	function onloadData(){
		$.ajax({
			url:context +'/videoRecording/findBasicCaseByFormId.action',
			type:'post',
			data:{
				formId:formId
			},
			dataType:'json',
			success:function(successData){
				var bean = successData.resultMap.result;
				$('#formCode').text(bean.formCode);
				$('#askPerson').text(bean.askPerson);
				$('#sex').text(bean.sex);
				$('#askPersonID').text(bean.askPersonID);
				$("#cameraPicture").attr("src", "data:image/png;base64," + bean.photo);
				idCardInfo(bean.formCode,bean.inTime); //根据使用单号查询
			}
		});
	}
	//刻录列表
	function idCardInfo(code,inTime){
		$.ajax({
			url:context +'/videoRecording/findRecordingByFormId.action',
			type:'post',
			data:{
				"formCode" : code,
				"inTime" :inTime
				},
			dataType:'json',
			success:function(successData){
				var burnRecordBeanList = successData.resultMap.result;
				var tb = $.uiSettings.getLocalOTableSettings();
				tb.data = burnRecordBeanList;
				tb.columnDefs = [
					{
						"targets": 0,
		     	    	"width": "",
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
						"title" : "用户编号",
						"data" : "userUUID",
						"render" : function(data, type, full, meta) {
							return data;
						}
					},
					{
						"targets" : 2,
						"width" : "",
						"title" : "用户姓名",
						"data" : "userName",
						"render" : function(data, type, full, meta) {
//							return $.date.timeToStr(data, "yyyy-MM-dd HH:mm:ss");
							return data;
						}
					},
					{
						"targets" : 3,
						"width" : "",
						"title" : "刻录时间",
						"data" : "createDate",
						"render" : function(data, type, full, meta) {
							return data;
						}
					},
					{
						"targets" : 4,
						"width" : "",
						"title" : "刻录内容",
						"data" : "optContent",
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
				criminalRecordTable = $("#criminalRecordTable").DataTable(tb);
			}
		});
	}
})(jQuery);