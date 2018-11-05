(function($){
	"use strict";
	//文件上传
	var setting = $.plupload.getBasicSettings() ;
	//删除的附件数组
	var deleteFileIdList = new Array();
	//人身检查记录信息
	var personCheckRecordBean = null;
	//办案区使用单id
	var harId = "";
	$(document).ready(function() {
		$("#pcrConfirm").attr("disabled", "disabled");
		jumpOff();
		 harId = $("#harId").val();
		 $.common.setSelectedTabsById("checkRecord");
		 //将状态改为false 查看页面
		 $.common.setIsUpdate(true);
		 $.common.showOperateRecord("lrcrOperateRecordTable");
		 /**
		  * 确认按钮事件
		  */
		 $(document).on("click","#pcrConfirm",function(){
			 $("#pcrConfirm").attr("disabled", "disabled");
			 savePersonCheckRecord();
		 });
		 /**
		  * 取消/返回按钮事件
		  */
		 $(document).on("click",".pcrBack",function(){
			 window.location.href = $.util.fmtUrl(context +"/personCheckRecord/showLookPersonCheckRecordPage.action?&&harId=" + $("#harId").val());
		 });
		 /**
		  * 打印按钮事件
		  */
		 $(document).on("click","#pcrPrint",function(){
			 var data = {
					 id : harId,
					 "personCheckRecordBean.selfReportedSymptoms" : $("#selfReportedSymptoms").val(),
					 "personCheckRecordBean.checkCondition" : $("#checkCondition").val()
			 	}
			 
			 var form = $.util.getHiddenForm(context +'/harPrint/printPersonCheckRecord.action', data);
			 $.util.subForm(form);
		 });
		 /**
		  * 附件删除事件
		  */
		 $(document).on("click",".delAtt",function(){
			 deleteFileIdList.push($(this).attr("fileId"));
			 $(this).parent().remove();
		 });
		 
		 initPlupload();
		 initPageField();
		 $.common.showOperateRecord("nrcrOperateRecordTable");//显示操作记录  ajglUtil.js
	});
	
	/**
	 * 初始化页面字段信息
	 */
	function initPageField(){
		if(!$.util.isBlank(harId)){
			$.ajax({
				url:context +'/personCheckRecord/searchPersonCheckRecordByHandlingAreaReceiptId.action',
				type:'post',
				dataType:'json',
				data:{handlingAreaReceiptId : harId},
				success:function(successData){
					$("#pcrConfirm").attr("disabled", false);
					personCheckRecordBean = successData.personCheckRecordBean;
					if($.util.exist(personCheckRecordBean)){
						setAllField(personCheckRecordBean);
					}else{
						$("#checkPolice").val(currentUserName);
					}
				},
				error:function(errorData){
					
				}
			});
		}
	}
	
	/**
	 * 设置所有字段值
	 * @param personCheckRecordBean 页面对应对象
	 */
	function setAllField(personCheckRecordBean){
		$("#selfReportedSymptoms").val(personCheckRecordBean.selfReportedSymptoms);
		$("#checkCondition").val(personCheckRecordBean.checkCondition);
		$("#isCheckedPerson").val(personCheckRecordBean.isCheckedPerson);
		if($.util.isBlank(personCheckRecordBean.checkPolice)){
			$("#checkPolice").val(currentUserName);
		}else{
			$("#checkPolice").val(personCheckRecordBean.checkPolice);
		}
		$("#eyewitness").val(personCheckRecordBean.eyewitness);
		if($.util.exist(personCheckRecordBean.modifyPeopleBean)){
			$("#modifyPeopleName").text(personCheckRecordBean.modifyPeopleBean.name);
		}
		$("#updateTime").text(personCheckRecordBean.updateTime);
		
		if($.util.exist(personCheckRecordBean.files)){
			for(var i in personCheckRecordBean.files){
				$("#showAttach").append("<div style='float: left;'><a href='###' class='dlws' fileId='" + personCheckRecordBean.files[i].base64Str + "'>" + personCheckRecordBean.files[i].name + "</a><button class='btn btn-primary btn-x delAtt' fileId='" + personCheckRecordBean.files[i].id + "' title='删除'>×</button>&nbsp;&nbsp;&nbsp;&nbsp;</div>");
			}
			$(document).on("click",".dlws",function(){
				$.layerAlert.img("data:image/png;base64," + $(this).attr("fileId"));
//				window.open(context + "/personCheckRecord/downloadFile.action?attachmentId="+ $(this).attr("fileId"));					
			})
		}
	}
	
	/**
	 * 获取所有字段值
	 */
	function getAllField(){
		var pcrb = new Object();
		pcrb["id"] = $.util.exist(personCheckRecordBean)?personCheckRecordBean.id:null;
		pcrb["selfReportedSymptoms"] = $("#selfReportedSymptoms").val();
		pcrb["checkCondition"] = $("#checkCondition").val();
		pcrb["isCheckedPerson"] = $("#isCheckedPerson").val();
		pcrb["checkPolice"] = $("#checkPolice").val();
		pcrb["eyewitness"] = $("#eyewitness").val();
		pcrb["eyewitness"] = $("#eyewitness").val();
		pcrb["handlingAreaReceiptId"] = harId;
		return pcrb;
	}
	
	/**
	 * 清空所有字段值
	 */
	function cleanAllField(){
		$("#selfReportedSymptoms").val("");
		$("#checkCondition").val("");
		$("#isCheckedPerson").val("");
		$("#checkPolice").val("");
		$("#eyewitness").val("");
	}
	
	/**
	 * 设置所有字段不可修改
	 */
	function disableAllFieldInput(){
		$("#selfReportedSymptoms").attr("readonly","readonly");
		$("#checkCondition").attr("readonly","readonly");
		$("#isCheckedPerson").attr("readonly","readonly");
		$("#checkPolice").attr("readonly","readonly");
		$("#eyewitness").attr("readonly","readonly");
	}
	
	/**
	 * 保存或更新人身检查记录
	 */
	function savePersonCheckRecord(){
		var demo = $.validform.getValidFormObjById("validformPersonCheckRecord") ;
		var flag = $.validform.check(demo) ;
		if(!flag){
			$("#pcrConfirm").attr("disabled", false);
			return ;
		}
		$.plupload.start("pcrUpload");
	}
	
	function initPlupload(){
		setting.container = "pcrUpload" ; //容器id
		setting.url = context + "/personCheckRecord/uploadFile.action";
		setting.controlBtn = {
			container:{
				className:"upload-btn"
			},
			selectBtn:{
				className:"btn btn-primary",
				html:'<span class="glyphicon glyphicon-edit" aria-hidden="true" style="margin-right:10px;"></span>选择'
			},
			uploadBtn:{
				init:false
			}
		} ;
		setting.finishlistDom = {
			className:"upload-text",
			downloadBtn:{
				init:false
			},
			deleteBtn:{
				init:false
			}
		};
		setting.filelistDom = {
			className:"upload-text"
		};
		setting.totalInfoDom = {
			className:"upload-text"
		};
		setting.customParams = {
			testCustom1:"123",
			testCustom:function(up, file){
				return Math.random() ;
			}
		} ;
		setting.chunk_size = '0' ;
		setting.filters.max_file_size = '15mb';
		setting.filters.mime_types = [{title: "图片类型", extensions: "jpg,JPG,jpeg,JPEG,gif,GIF,png,PNG,bmp,BMP"}];
		setting.filters.prevent_duplicates = true ;
		setting.multi_selection = false;
		setting.multi_file_num_callback = function(max_file_size, totalSize){
		};
		setting.callBacks = {
				uploadAllFinish:function(up, finishedFiles){
					var pcrb = getAllField();
					var i = 0;
					for (var key in finishedFiles) {  
						pcrb["files["+i+"].id"] = key;
						i ++;
			        }
					
					var url = context +'/personCheckRecord/savePersonCheckRecord.action';
					if($.util.exist(personCheckRecordBean)){
						url = context +'/personCheckRecord/updatePersonCheckRecord.action';
					}
					
					var gData = new Object();
					$.util.objToStrutsFormData(pcrb, "personCheckRecordBean", gData);
					$.util.objToStrutsFormData(deleteFileIdList, "deleteFileIdList", gData);
					//执行后台操作
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						data:gData,
						customizedOpt:{
							btn:{	    			
								btn:"#pcrConfirm"
							}
						},
						success:function(successData){
							window.top.$.layerAlert.alert({msg:"保存成功！",icon:"1",end : function(){
								disableAllFieldInput();
								 $("#pcrPrint").css("display","inline");
								 $("#pcrBack").css("display","inline");
								 $("#pcrConfirm").css("display","none");
								 $("#pcrCancel").css("display","none");
								 $(".container-upload-btn").hide();
								 $(".moxie-shim").remove();
								 $.common.showOperateRecord("nrcrOperateRecordTable");//显示操作记录  ajglUtil.js
								 $.common.setIsUpdate(false);
								 $("#modifyPeopleName").text(successData.modifyPerson);
								 $("#updateTime").text(successData.modifyTime);
								 jumpOn();
							}});
						},
						error:function(errorData){
							
						}
					});
				}
		}
		$.plupload.init(setting) ;
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);