(function($){
	"use strict";
	//办案区使用单id
	var harId = "";
	$(document).ready(function() {
		jumpOn();
		 harId = $("#harId").val();
		 $.common.setSelectedTabsById("infoGather");
		 //将状态改为false 查看页面
		 $.common.setIsUpdate(false);
		 /**
		  * 维护采集信息情况按钮事件
		  */
		 $(document).on("click","#updateCollectInfoSituation",function(){
			 window.location.href = $.util.fmtUrl(context +"/collectInfoSituation/showNewOrUpdateCollectInfoSituationPage.action?&&harId=" + $("#harId").val());
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
		 $.common.showOperateRecord("lcisOperateRecordTable");//显示操作记录  ajglUtil.js
	});
	
	/**
	 * 初始化页面字段信息
	 */
	function initPageField(){
		if(!$.util.isBlank(harId)){
			$.ajax({
				url:context +'/collectInfoSituation/searchCollectInfoSituationByHandlingAreaReceiptId.action',
				type:'post',
				dataType:'json',
				data:{handlingAreaReceiptId : harId},
				success:function(successData){
					if($.util.exist(successData.collectInfoSituationBean)){
						setAllField(successData.collectInfoSituationBean);
					}else{
						
					}
				},
				error:function(errorData){
					
				}
			});
		}
	}
	
	/**
	 * 设置所有字段值
	 * @param collectInfoSituationBean 页面对应对象
	 */
	function setAllField(collectInfoSituationBean){
		var collectProject = collectInfoSituationBean.collectProject;
		if(!$.util.isBlank(collectInfoSituationBean.collectProjectOther)){
			if(!$.util.isBlank(collectProject)){
				collectProject += "，" ;
			}
			collectProject += "其他：" + collectInfoSituationBean.collectProjectOther;
		}
		$("#collectProject").text(collectProject);
		$("#infoIntoString").text(collectInfoSituationBean.infoIntoStringName);
		$("#inspectComparison").text(collectInfoSituationBean.inspectComparisonName);
		$("#isCollect").text(collectInfoSituationBean.isCollectName);
		var qqNum = collectInfoSituationBean.qqNum.split(",");
		var str = "";
		for(var i in qqNum){
			str += '<span style="margin-right:30px">' + qqNum[i] + '</span>';
		}
		$("#qqNum").html(str);
		var weixinNum = collectInfoSituationBean.weixinNum.split(",");
		str = "";
		for(var i in weixinNum){
			str += '<span style="margin-right:30px">' + weixinNum[i] + '</span>';
		}
		$("#weixinNum").html(str);
		var phoneInfo = collectInfoSituationBean.phoneInfo.split(";");
		str = "";
		if(phoneInfo.length != 0){
			for(var i in phoneInfo){
				var temp = phoneInfo[i].split(",");
				str += '<tr>'
				+ '<td class="td-left">手机号：</td>'
				+ '<td>' + temp[0] + '</td>'
				+ '<td class="td-left">IMEI：</td>'
				+ '<td>' + temp[1] + '</td>'
				+ '<td class="td-left" width="120">MAC：</td>'
				+ '<td>' + temp[2] + '</td>'
				+ '</tr>';
			}
			$(".emptyTrForPhone").remove();
			$("#infoTbody").append(str);
		}
		if($.util.exist(collectInfoSituationBean.modifyPeopleBean)){
			$("#modifyPeopleName").text(collectInfoSituationBean.modifyPeopleBean.name);
		}
		if($.util.exist(collectInfoSituationBean.modifyPeopleBean) 
				&& $.util.isBlank(collectInfoSituationBean.modifyPeopleBean.id) 
				&& collectInfoSituationBean.modifyPeopleBean.id != currentUserId){
			$("#updateCollectInfoSituation").attr("disabled", "disabled");
		}
		$("#updateTime").text(collectInfoSituationBean.updateTime);
	}
	
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);