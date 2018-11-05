(function($){
	"use strict";
	//文件上传
	var index = null;
	var policeData = [];
	var criminalRecordTable = null;
	$(document).ready(function() {
		jumpOn();
		$.common.setSelectedTabsById("personBaseInfo");
		$.common.setIsUpdate(false);
		initData();
		$.common.showOperateRecord("opertionRecord");
		 /**
		  * 维护基本情况按钮事件
		  */
		 $(document).on("click","#updateHandlingAreaReceipt",function(){
			 window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showUpdateHandlingAreaReceiptPage.action?&&harId=" + $("#harId").val());
		 });
		 /**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showCase",function(){
			var id = $(this).attr("lawCase");
			window.parent.open(context +"/caseSearch/showCaseDetail.action?clickOrder=1&&clickListOrder=0&&caseCode=" + id);
		});
	 	/**
		 * 打印
		 */
		$(document).on("click","#printAll",function() {
			var data = {id : $("#harId").val()};
			var form = $.util.getHiddenForm(context +'/harPrint/printHandlingAreaReceipt.action', data);
			$.util.subForm(form);
		});
		
		$(document).on("click",".img",function(){
			$.layerAlert.img($(this).attr("src"), 357);
		});
		
		/**
		 * 解绑手环
		 */
		$(document).on("click","#unbound",function(){
			window.top.$.layerAlert.confirm({
				msg:"解绑后，将初始化嫌疑人手环及民警临时卡,请确认是否解绑？",
				title:"解绑提示",	  //弹出框标题
				yes:function(index, layero){
					$.ajax({
						url:context +'/leaveSituation/braceletControl.action',
						type:'post',
						data:{
							id:$("#harId").val(),
							type:"jb"
						},
						dataType:'json',
						success:function(successData){
							if(successData.flag == "true"){
								window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示"});
							}else{
								window.top.$.layerAlert.alert({msg:successData.errorMessage ,title:"提示"});
							}
						}
					})
				}
			});
		});
		/**
		 * 完成使用单
		 */
		$(document).on("click",".finishReceipt",function() {
			$.ajax({
				url:context +'/handlingAreaReceipt/handlingAreaReceiptCheck.action',
				type:'post',
				data:{
					id:$("#harId").val()
				},
				dataType:'json',
				success:function(successData){
					if(successData.flag == "true"){
						window.top.$.layerAlert.confirm({
							msg:"完成后信息将不可更改，是否要完成本次问讯？",
							title:"提示",	  //弹出框标题
							yes:function(index, layero){
								$.ajax({
									url:context +'/handlingAreaReceipt/doneHandlingAreaReceipt.action',
									type:'post',
									data:{
										id:$("#harId").val()
									},
									dataType:'json',
									success:function(successData){
										if(successData.flag == "true"){
											window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示",end:function(){
												location.reload(true);
											}});
										}else{
											window.top.$.layerAlert.alert({msg:"操作失败！",title:"提示"});
										}
									}
								})
							}
						});
					}else{
						window.top.$.layerAlert.alert({msg:successData.msg,title:"提示",end:function(){
							if(successData.flag == "base"){
								window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showUpdateHandlingAreaReceiptPage.action?&&justShow=false" + $("#justShow").val() + "&&harId=" + $("#harId").val());
							}
						}
					});
					}
				}
			})
		});
		/**
		 * 警员查看
		 */
		$(document).on("click","#showPoliceList",function(){
			var initData = {
				cre$ : $,
				policeData : policeData,
				modify : "false"
			}
			window.top.$.layerAlert.dialog({
				content : context +  '/handlingAreaReceipt/showPoliceList.action',
				pageLoading : true,
				title:"查看办案民警信息",
				width : "650px",
				height : "500px",
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
			
		initQKTable();
	});
	
	function initQKTable(){
		var tb = $.uiSettings.getLocalOTableSettings();
		tb.data = [];
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
				"title" : "涉及案件",
				"data" : "caseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "办案民警",
				"data" : "police",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "涉案时间",
				"data" : "caseTime",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "人像图片",
				"data" : "picture",
				"render" : function(data, type, full, meta) {
					return "<img class='zoomIn' src='data:image/png;base64," + data + "' height='50'>";
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
	
	function initData(){
		$.ajax({
			url:context +'/handlingAreaReceipt/findBasicCaseByHarId.action',
			type:'post',
			data:{
				id:$("#harId").val()
			},
			dataType:'json',
			success:function(successData){
				var bcb = successData.basicCaseBean;
				idCardInfo(bcb.byQuestioningPeopleIdentifyNum);
				policeData = successData.policeInfoBeanLst;
				$("#handlingAreaReceiptNum").text(bcb.handlingAreaReceiptNum);
				$("#enterIntoTimeStr").text((bcb.enterIntoTime == null) ? "" : $.date.timeToStr(bcb.enterIntoTime, "yyyy-MM-dd HH:mm"));
				$("#byQuestioningPeopleName").text(bcb.byQuestioningPeopleName);
				$("#sexStr").text(bcb.sexStr);
				$("#bangleCode").text(bcb.bangleCode);
				$("#door").text((bcb.door == null) ? "" : bcb.door);
				$("#byQuestioningPeopleAddress").text((bcb.byQuestioningPeopleAddress == null) ? "" : bcb.byQuestioningPeopleAddress);
				$("#byQuestioningPeopleTelphoneNumber").text((bcb.byQuestioningPeopleTelphoneNumber == null) ? "" : bcb.byQuestioningPeopleTelphoneNumber);
				$("#byQuestioningPeopleIdentifyNum").text((bcb.byQuestioningPeopleIdentifyNum == null) ? "" : bcb.byQuestioningPeopleIdentifyNum);
				$("#lawCaseName").text((bcb.lawCaseName == null) ? "" : bcb.lawCaseName);
				if(!$.util.isBlank(bcb.lawCase)){
					$("#lawCase").html('<a href="###" id="lawCaseName" lawCase="' + bcb.lawCase + '" class="showCase">' + bcb.lawCaseName + '<br/>' + bcb.lawCase + '</a>');
				}
				$("#causeOfLawCaseStr").text(bcb.causeOfLawCaseStr == null ? "" : bcb.causeOfLawCaseStr);
				$("#enterAreaReasons").text((bcb.enterAreaReasons == null) ? "" : bcb.enterAreaReasons);
				$("#handlingPolice").text((bcb.handlingPolice == null) ? "" : bcb.handlingPolice);
				$("#cameraPicture").attr("src", "data:image/png;base64," + bcb.photo);
				if($.util.exist(bcb.modifyPeopleBean)){
					$("#modifyPeopleName").text(bcb.modifyPeopleBean.name);
				}
				if($.util.exist(bcb.modifyPeopleBean) 
						&& $.util.isBlank(bcb.modifyPeopleBean.id) 
						&& bcb.modifyPeopleBean.id != currentUserId){
					$("#updateHandlingAreaReceipt").attr("disabled", "disabled");
				}
				$("#modifyTime").text((bcb.modifyTime == null) ? "" : $.date.timeToStr(bcb.modifyTime, "yyyy-MM-dd HH:mm"));
				if($.util.exist(bcb.attach)){
					$("#attach").html("<a href='###' id='dlws'>" + bcb.attach.name + "</a>");
					$(document).on("click","#dlws",function(){
						//$.layerAlert.img("data:image/png;base64," + bcb.attach.base64Str);
						window.open(context + "/handlingAreaReceipt/downloadFile.action?attachmentId="+ bcb.attach.id);
					})
				}
			}
		});
	}
	function idCardInfo(idNum){
		var reg = /(^\d{15}$)|(^\d{17}(\d|X|x)$)/;
		if(reg.test(idNum)){
			$.ajax({
				url:context +'/handlingAreaReceipt/findPersonsByIdNum.action',
				type:'post',
				data:{code : idNum},
				dataType:'json',
				success:function(successData){
					var person = successData.person;
					$("#idCardPicture").attr("src", "data:image/png;base64," + person.picture);
					var tb = $.uiSettings.getLocalOTableSettings();
					tb.data = successData.criminalRecordLst;
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
							"title" : "涉及案件",
							"data" : "caseName",
							"render" : function(data, type, full, meta) {
								return data;
							}
						},
						{
							"targets" : 2,
							"width" : "",
							"title" : "办案民警",
							"data" : "police",
							"render" : function(data, type, full, meta) {
								return data;
							}
						},
						{
							"targets" : 3,
							"width" : "",
							"title" : "涉案时间",
							"data" : "caseTime",
							"render" : function(data, type, full, meta) {
								return data;
							}
						},
						{
							"targets" : 4,
							"width" : "",
							"title" : "人像图片",
							"data" : "picture",
							"render" : function(data, type, full, meta) {
								return "<img class='zoomIn' src='data:image/png;base64," + data + "' height='50'>";
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
					criminalRecordTable.destroy();
					criminalRecordTable = $("#criminalRecordTable").DataTable(tb);
				}
			});

		}
	
	}
})(jQuery);