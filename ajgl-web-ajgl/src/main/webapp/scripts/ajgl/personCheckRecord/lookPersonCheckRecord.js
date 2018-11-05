(function($){
	"use strict";
	//办案区使用单id
	var harId = "";
	$(document).ready(function() {	
		jumpOn();
		 harId = $("#harId").val();
		 $.common.setSelectedTabsById("checkRecord");
		 //将状态改为false 查看页面
		 $.common.setIsUpdate(false);
		 $.common.showOperateRecord("lrcrOperateRecordTable");
		 /**
		  * 维护人身检查记录按钮事件
		  */
		 $(document).on("click","#updatePersonCheckRecord",function(){
			 window.location.href = $.util.fmtUrl(context +"/personCheckRecord/showNewOrUpdatePersonCheckRecordPage.action?&&harId=" + $("#harId").val());
		 });
		 
	 	/**
		 * 打印
		 */
		$(document).on("click","#printAll",function() {
			var data = {id : harId};
			var form = $.util.getHiddenForm(context +'/harPrint/printHandlingAreaReceipt.action', data);
			$.util.subForm(form);
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
							id:harId,
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
					id:harId
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
										id:harId
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
		initPageField();
	});
	/**
	 * 初始化页面字段信息
	 */
	function initPageField(){
		if(!$.util.isBlank(harId)){
			$.ajax({
				url:context +'/personCheckRecord/searchPersonCheckRecordByHandlingAreaReceiptId.action',
				async:true,
				cache:false,//设置每次都重新请求
				global:false,
				type:'post',
				dataType:'json',
				data:{handlingAreaReceiptId : harId},
				success:function(successData){
					if($.util.exist(successData.personCheckRecordBean)){
						setAllField(successData.personCheckRecordBean);
					}
				},
				error:function(errorData){
					
				}
			});
		}
	}
	
	/**
	 * 设置所有字段值
	 * @param personCheckRecord 页面对应对象
	 */
	function setAllField(personCheckRecord){
		$("#selfReportedSymptoms").text(personCheckRecord.selfReportedSymptoms);
		$("#checkCondition").text(personCheckRecord.checkCondition);
		$("#isCheckedPerson").text(personCheckRecord.isCheckedPerson);
		$("#checkPolice").text(personCheckRecord.checkPolice);
		$("#eyewitness").text(personCheckRecord.eyewitness);
		if($.util.exist(personCheckRecord.modifyPeopleBean)){
			$("#modifyPeopleName").text(personCheckRecord.modifyPeopleBean.name);
		}
		if($.util.exist(personCheckRecord.modifyPeopleBean) 
				&& $.util.isBlank(personCheckRecord.modifyPeopleBean.id) 
				&& personCheckRecord.modifyPeopleBean.id != currentUserId){
			$("#updatePersonCheckRecord").attr("disabled", "disabled");
		}
		$("#updateTime").text(personCheckRecord.updateTime);
		if($.util.exist(personCheckRecord.files)){
			for(var i in personCheckRecord.files){
				$("#pcrAttach").append("<a href='###' class='dlws' fileId='" + personCheckRecord.files[i].base64Str + "'>" + personCheckRecord.files[i].name + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			$(document).on("click",".dlws",function(){
				$.layerAlert.img("data:image/png;base64," + $(this).attr("fileId"));
//				window.open(context + "/personCheckRecord/downloadFile.action?attachmentId="+ $(this).attr("fileId"));					
			});
		}
	}
	
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);