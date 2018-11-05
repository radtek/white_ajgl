(function($){
	"use strict";
	//采集信息情况信息
	var collectInfoSituationBean = null;
	//办案区使用单id
	var harId = "";
	var errMsg = "";
	$(document).ready(function() {
		$("#cisConfirm").attr("disabled", "disabled");
		jumpOff();
		 harId = $("#harId").val();
		 $.common.setSelectedTabsById("infoGather");
		 //将状态改为false 查看页面
		 $.common.setIsUpdate(true);
		 /**
		  * 确认按钮事件
		  */
		 $(document).on("click","#cisConfirm",function(){
			 $.validform.closeAllTips("dataTable") ;
			$("#cisConfirm").attr("disabled", "disabled");
			 saveCollectInfoSituation();
		 });
		 /**
		  * 取消/返回按钮事件
		  */
		 $(document).on("click",".cisBack",function(){
			 window.location.href = $.util.fmtUrl(context +"/collectInfoSituation/showLookCollectInfoSituationPage.action?&&harId=" + $("#harId").val());
		 });
		 /**
		  * 打印按钮事件
		  */
		 $(document).on("click","#cisPrint",function(){
			 var cis = new Object();
			 cis = getAllField();
			 
			 var phoneInfolst = "";
			 var size = $(".phoneInfo").size();
			 
			for(var i = 0; i < size - 1; i++ ){
				phoneInfolst = phoneInfolst + $($(".phoneInfo")[i]).find(".phoneNum").val()+",";
				phoneInfolst = phoneInfolst + $($(".phoneInfo")[i]).find(".phoneIMEI").val()+",";
				phoneInfolst = phoneInfolst + $($(".phoneInfo")[i]).find(".phoneMAC").val()+";";
			}
			 
			 var data = {
					 id : harId,
					 "collectInfoSituationBean.collectProject" : cis["collectProject"],
					 "collectInfoSituationBean.collectProjectOther" : cis["collectProjectOther"],
					 "collectInfoSituationBean.infoIntoString" : cis["infoIntoString"],
					 "collectInfoSituationBean.inspectComparison" : cis["inspectComparison"],
					 "collectInfoSituationBean.isCollect" : cis["isCollect"],
					 "collectInfoSituationBean.qqNum":$("#qqNum").val(),
					 "collectInfoSituationBean.weixinNum":$("#weiXinNum").val(), 
					 "collectInfoSituationBean.phoneInfo":phoneInfolst
			 };
			 var form = $.util.getHiddenForm(context +'/harPrint/printCollectInfoSituation.action', data);
			 $.util.subForm(form);
		 });
		 /**
		  * 选中其它
		  */
		 $(document).on("ifChecked","#otherCheckbox",function(){
			 $("#collectProjectOther").css("display","inline");
			 $("#collectProjectOther").attr("datatype","*1-26");
		 });
		 /**
		  * 取消选中其它
		  */
		 $(document).on("ifUnchecked","#otherCheckbox",function(){
			 $("#collectProjectOther").css("display","none");
			 $("#collectProjectOther").removeAttr("datatype");
			 $("#collectProjectOther").val("");
		 });
		 
		 /**
		  * +号事件
		  */
		 $(document).on("click","#newQqNum,#newWeixinNum",function(){
			 var obj = $("#tempGroup").clone(true);
			 $(obj).removeAttr("id");
			 $(obj).find("input").addClass("valiform-keyup");
			 $(obj).find("input").addClass("form-val");
			 $(obj).find("input").attr("vf-position", "1");
			 $(obj).find("input").attr("datatype", "*1-26");
			 $(obj).show();
			 $(this).before(obj);
			 $.validform.closeAllTips("dataTable") ;
		 }); 
		 /**
		  * -号事件
		  */
		 $(document).on("click", ".trashInput",function(){
			 $(this).parent(".removeSpan").remove();
			 $.validform.closeAllTips("dataTable") ;
		 });
		 
		 /**
		  * +号事件(tr)
		  */
		 $(document).on("click","#newPhoneInfo",function(){
			 var obj = $("#tempTr").clone(true);
			 $(obj).removeAttr("id");
			 $(obj).find("input").addClass("valiform-keyup");
			 $(obj).find("input").addClass("form-val");
			 $(obj).find("input").attr("vf-position", "1");
			 $(obj).find("input").attr("datatype", "*1-26");
			 $(obj).show();
			 $("#tempTr").before(obj);
			 $.validform.closeAllTips("dataTable") ;
		 }); 
		 /**
		  * -号事件(tr)
		  */
		 $(document).on("click", ".trashTr",function(){
			 $(this).parents(".removeTr").remove();
			 $.validform.closeAllTips("dataTable") ;
		 });
		 initPageField();
		 $.common.showOperateRecord("ncisOperateRecordTable");//显示操作记录  ajglUtil.js
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
					$("#cisConfirm").attr("disabled", false);
					collectInfoSituationBean = successData.collectInfoSituationBean;
					if($.util.exist(collectInfoSituationBean)){
						setAllField(collectInfoSituationBean);
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
		if(collectInfoSituationBean.infoIntoString == $.common.Constant.SF_S()){
			$('#infoIntoStringY').iCheck('check');
		}else if(collectInfoSituationBean.infoIntoString == $.common.Constant.SF_F()){
			$('#infoIntoStringN').iCheck('check');
		}
		
		if(collectInfoSituationBean.inspectComparison == $.common.Constant.SF_S()){
			$('#inspectComparisonY').iCheck('check');
		}else if(collectInfoSituationBean.inspectComparison == $.common.Constant.SF_F()){
			$('#inspectComparisonN').iCheck('check');
		}
		
		if(collectInfoSituationBean.isCollect == $.common.Constant.SF_S()){
			$('#isCollectY').iCheck('check');
		}else if(collectInfoSituationBean.isCollect == $.common.Constant.SF_F()){
			$('#isCollectN').iCheck('check');
		}
		
		var collectProjectArray = collectInfoSituationBean.collectProject.split("，");
		$('.icheckbox').iCheck('uncheck');
		$.each(collectProjectArray,function(s,collectProject){
			switch (collectProject){
			case "指纹":
				$('#fingerprintCheckbox').iCheck('check');
				break;
			case "血液":
				$('#sanguisCheckbox').iCheck('check');
				break;
			case "尿液":
				$('#urineCheckbox').iCheck('check');
				break;
			}
		});
		
		if(!$.util.isBlank(collectInfoSituationBean.collectProjectOther)){
			$('#otherCheckbox').iCheck('check');
			$("#collectProjectOther").val(collectInfoSituationBean.collectProjectOther);
		}
		
		var qqNum = collectInfoSituationBean.qqNum.split(",");
		for(var i in qqNum){
			if(i == 0){
				$($("#qqTd").find("input")[i]).val(qqNum[i]);
				continue;
			}
			var obj = $("#tempGroup").clone(true);
			$(obj).removeAttr("id");
			$(obj).find("input").addClass("valiform-keyup");
			$(obj).find("input").addClass("form-val");
			$(obj).find("input").attr("vf-position", "1");
			$(obj).find("input").attr("datatype", "*1-26");
			$(obj).show();
			$(obj).find("input").val(qqNum[i]);
			$("#newQqNum").before(obj);
		}
		
		var weixinNum = collectInfoSituationBean.weixinNum.split(",");
		for(var i in weixinNum){
			if(i == 0){
				$($("#wxTd").find("input")[i]).val(weixinNum[i]);
				continue;
			}
			var obj = $("#tempGroup").clone(true);
			$(obj).removeAttr("id");
			$(obj).find("input").addClass("valiform-keyup");
			$(obj).find("input").addClass("form-val");
			$(obj).find("input").attr("vf-position", "1");
			$(obj).find("input").attr("datatype", "*1-26");
			$(obj).show();
			$(obj).find("input").val(weixinNum[i]);
			$("#newWeixinNum").before(obj);
		}
		var phoneInfo = collectInfoSituationBean.phoneInfo.split(";");
		for(var i in phoneInfo){
			var info = phoneInfo[i].split(",");
			if(i == 0){
				var arr = $("#firstInfoTr").find("input");
				for(var j = 0; j < arr.length; j++){
					$(arr[j]).val(info[j]);
				}
				continue;
			}
			var obj = $("#tempTr").clone(true);
			$(obj).removeAttr("id");
			$(obj).find("input").addClass("valiform-keyup");
			$(obj).find("input").addClass("form-val");
			$(obj).find("input").attr("vf-position", "1");
			$(obj).find("input").attr("datatype", "*1-26");
			$(obj).show();
			var arr = $(obj).find("input");
			for(var j = 0; j < arr.length; j++){
				$(arr[j]).val(info[j]);
			}
			$("#tempTr").before(obj);
		}
		if($.util.exist(collectInfoSituationBean.modifyPeopleBean)){
			$("#modifyPeopleName").text(collectInfoSituationBean.modifyPeopleBean.name);
		}
		$("#updateTime").text(collectInfoSituationBean.updateTime);
	}
	
	/**
	 * 获取所有字段值
	 */
	function getAllField(){
		var cis = new Object();
		cis["id"] = $.util.exist(collectInfoSituationBean)?collectInfoSituationBean.id:null;
		var collectProject = "";
		var arr = $.icheck.getChecked("collectProject");
		$.each(arr,function(i,input){
			var cp = $(arr[i]).val();
			if(cp != "其他"){
				collectProject += cp + "，";
			}
		});
		cis["collectProject"] = collectProject.substring(0,collectProject.length-1);
		cis["infoIntoString"] = $($.icheck.getChecked("infoIntoString")[0]).val();
		cis["inspectComparison"] = $($.icheck.getChecked("inspectComparison")[0]).val();
		cis["isCollect"] = $($.icheck.getChecked("isCollect")[0]).val();
		cis["collectProjectOther"] = $("#collectProjectOther").val();
		cis["handlingAreaReceiptId"] = harId;
		var arr = $("#qqTd").find("input");
		var str = "";
		var line = 0;
		for(var i = 0; i<arr.length; i++){
			line++;
			if($(arr[i]).parent().attr("id") == "tempGroup"){
				continue;
			}
			var inData = $(arr[i]).val();
			var reg = /(^\d{5,10}$)|(^[无]$)/;
			if(!reg.test(inData)){
				errMsg = "第" + line + "个qq号不符合规则！"
				return false;
			}
			str += inData + ",";
		}
		cis["qqNum"] = str.substr(0,str.length-1);
		arr = $("#wxTd").find("input");
		str = "";
		line = 0;
		for(var i = 0; i<arr.length; i++){
			line++;
			if($(arr[i]).parent().attr("id") == "tempGroup"){
				continue;
			}
			var inData = $(arr[i]).val();
			var reg = /(^[a-zA-Z\d_]{5,}$)|(^[无]$)/;
			if(!reg.test(inData)){
				errMsg = "第" + line + "个微信号不符合规则！"
				return false;
			}
			str += inData + ",";
		}
		cis["weixinNum"] = str.substr(0,str.length-1);
		arr = $(".phoneInfo");
		str = "";
		line = 0;
		for(var i = 0; i<arr.length; i++){
			line++;
			if($(arr[i]).attr("id") == "tempTr"){
				line--;
				continue;
			}
			var tempArr = $(arr[i]).find("input");
			var reg1 = /(^\d{11}$)|(^[无]$)/;
			if(!reg1.test($(tempArr[0]).val())){
				errMsg = "第" + line + "个手机号不符合规则！"
				return false;
			}
			var reg2 = /(^\d{15}$)|(^[无]$)/;
			if(!reg2.test($(tempArr[1]).val())){
				errMsg = "第" + line + "个IMEI不符合规则！"
				return false;
			}
			var reg3 = /(^([0-9a-fA-F]{2})(([/\s:][0-9a-fA-F]{2}){5})$)|(^[无]$)/;
			if(!reg3.test($(tempArr[2]).val())){
				errMsg = "第" + line + "个MAC不符合规则！"
				return false;
			}
			str += $(tempArr[0]).val() + "," + $(tempArr[1]).val() + "," + $(tempArr[2]).val() + ";";
		}
		cis["phoneInfo"] = str.substr(0,str.length-1);
		return cis;
	}
	
	/**
	 * 清空所有字段值
	 */
	function cleanAllField(){
		$('.icheckradio').iCheck('uncheck');
		$('.icheckbox').iCheck('uncheck');
		$("#collectProjectOther").val("");
		$("#collectProjectOther").css("dispaly","none");
	}
	
	/**
	 * 设置所有字段不可修改
	 */
	function disableAllFieldInput(){
		$.icheck.able(".icheckradio",false) ;
		$.icheck.able(".icheckbox",false) ;
		$("#collectProjectOther").attr("readonly","readonly");
		
		$("#dataTable").find("input").attr("readonly","readonly");
		$("#dataTable").find(".trashInput,.trashTr").hide();
		$("#newQqNum,#newWeixinNum,#newPhoneInfo").hide();
	}
	
	/**
	 * 保存或更新人身检查记录
	 */
	function saveCollectInfoSituation(){
		var demo = $.validform.getValidFormObjById("dataTable") ;
		var flag = $.validform.check(demo) ;
		if(!flag){
			$("#cisConfirm").attr("disabled", false);
			return;
		}
		var url = context +'/collectInfoSituation/saveCollectInfoSituation.action';
		if($.util.exist(collectInfoSituationBean)){
			url = context +'/collectInfoSituation/updateCollectInfoSituation.action';
		}
		var cis = getAllField();
		if(!cis){
			$("#cisConfirm").attr("disabled", false);
			window.top.$.layerAlert.alert({msg:errMsg});
			return;
		}
		var gData = new Object();
		$.util.objToStrutsFormData(cis, "collectInfoSituationBean", gData);
		
		if(!verifyInput()){
			$("#cisConfirm").attr("disabled", false);
			return ;
		}
		//执行后台操作
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:gData,
			customizedOpt:{
				btn:{	    			
					btn:"#cisConfirm"
				}
			},
			success:function(successData){
				window.top.$.layerAlert.alert({msg:"保存成功！",icon:"1",end : function(){
					disableAllFieldInput();
					 $("#cisPrint").css("display","inline");
					 $("#cisBack").css("display","inline");
					 $("#cisConfirm").css("display","none");
					 $("#cisCancel").css("display","none");
					 $.common.showOperateRecord("ncisOperateRecordTable");//显示操作记录  ajglUtil.js
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
	
	/**
	 * 验证输入框
	 * @return
	 */
	function verifyInput(){
		var flag = true;
		if($.icheck.getChecked("collectProjectOther").length < 1 && $.icheck.getChecked("collectProject").length < 1){
			flag = false;
			$.layerAlert.tips({
				msg:'请勾选采集项目',
				selector:"#collectProjectVerify",
				color:'#FF0000',
				position:1,
				closeBtn:2,
				time:2000,
				shift:1
			});
		}
		if($.icheck.getChecked("collectProjectOther").length == 1 && $.util.isBlank($("#collectProjectOther").val())){
			flag = false;
			$.layerAlert.tips({
				msg:'请填写内容',
				selector:"#collectProjectOther",
				color:'#FF0000',
				position:1,
				closeBtn:2,
				time:2000,
				shift:1
			});
		}
		if($.icheck.getChecked("infoIntoString").length < 1){
			flag = false;
			$.layerAlert.tips({
				msg:'请勾选是否信息入库',
				selector:"#infoIntoStringVerify",
				color:'#FF0000',
				position:1,
				closeBtn:2,
				time:2000,
				shift:1
			});
		}
		if($.icheck.getChecked("inspectComparison").length < 1){
			flag = false;
			$.layerAlert.tips({
				msg:'请勾选是否核查对比',
				selector:"#inspectComparisonVerify",
				color:'#FF0000',
				position:1,
				closeBtn:2,
				time:2000,
				shift:1
			});
		}
		if($.icheck.getChecked("isCollect").length < 1){
			flag = false;
			$.layerAlert.tips({
				msg:'请勾选是否信息采集',
				selector:"#isCollectVerify",
				color:'#FF0000',
				position:1,
				closeBtn:2,
				time:2000,
				shift:1
			});
		}
		return flag;
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);