(function($) {
	"use strict";
	
	var table;
	var tb;
	//办案区使用单id
	var harId = "";
	var fliId = "";
	var finalLeave = false;
	var newLineIndex = 0;
	var deleteIdLst = [];
	var modifyFlag = null;
	$(document).ready(function() {
		$("#confirm").attr("disabled", "disabled");
		harId = $("#harId").val();
		if($("#modifyBtnFlag").val() == "false"){
			window.top.$.layerAlert.alert({msg:"此使用单已办理最终离开手续，不能继续办理！",title:"提示",end:function(){
				window.location.href = $.util.fmtUrl(context +"/leaveSituation/showLeaveSituation.action?&&harId=" + $("#harId").val());
			}});
			return;
		}
		jumpOff();
		 $.common.setSelectedTabsById("leaveWork");
		 //将状态改为true 修改页面
		 $.common.setIsUpdate(true);
		 $.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
		 initTable();
		 cuffStatus();//手环挂起状态
	});
	
	/**
	 * 取消挂起
	 */
	$(document).on("click","#onLine",function(){
		$.ajax({
			url:context +'/leaveSituation/braceletControl.action',
			type:'post',
			data:{
				id:harId,
				type:"qx"
			},
			dataType:'json',
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示"});
					qxgq();
				}else{
					window.top.$.layerAlert.alert({msg:"操作失败！" + errorMessage ,title:"提示"});
				}
			}
		})
	});
	/**
	 * 挂起
	 */
	$(document).on("click","#offLine",function(){
		$.ajax({
			url:context +'/leaveSituation/braceletControl.action',
			type:'post',
			data:{
				id:harId,
				type:"gq"
			},
			dataType:'json',
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示"});
					gq()
				}else{
					window.top.$.layerAlert.alert({msg:"操作失败！" + errorMessage,title:"提示"});
				}
			}
		})
	});
	/**
	 * 增行
	 */
	$(document).on("click","#addRow",function(){
//		if(!$.util.isBlank($("#useRoomStatus").val()) && $.common.Constant.SYZT_KX() != $("#useRoomStatus").val()){
//			window.top.$.layerAlert.alert({msg:"审讯未结束，不能办理离开手续！",title:"提示"});
//			return;
//		}
		var trs = $("#temporaryLeave tbody").children();
		var demo = $.validform.getValidFormObjById("validformId") ;
		if(! $.validform.check(demo)){
			return;
		}
		var flag = true;
		$.each($(".backCheck"), function(i,val){
			if($.util.isBlank($(this).val())){
				flag = false;
				return false;
			}
		})
		if(!flag){
			window.top.$.layerAlert.alert({msg:"请填写返回时间！",title:"提示"});
			return;
		}
		//添加行
		$.dataTable.addRow("#temporaryLeave","after", tb, [
		    '<input class="changePermissions" type="hidden" value="true"><div id="newLeaveTime' + newLineIndex + '" class="laydate input-group" style="width:200px;"><input type="hidden" class="laydate-fmt dateFmt" value="yyyy-MM-dd HH:mm" /><input type="text" date-pos="top"  class="laydate-value form-control input-sm valiform-keyup" vf-position="3" datatype="*" readonly="readonly" value="' + $.date.dateToStr(new Date(), "yyyy-MM-dd HH:mm") + '"><span class="laydate-value-span input-group-addon m-ui-addon"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span></div>',
	     	'<input type="text" class="form-control input-sm valiform-keyup form-val disabled" vf-position="3" datatype="*1-80">',
	  		'<div id="newReturnTime' + newLineIndex + '" class="laydate input-group" style="width:200px;"><input type="hidden" class="laydate-fmt dateFmt" value="yyyy-MM-dd HH:mm" /><input type="text" date-pos="top"  class="laydate-value form-control input-sm backCheck" readonly="readonly"><span class="laydate-value-span input-group-addon m-ui-addon"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span></div>',
	 		'<span class="maintainer">' + currentUserName + '</span><br/><span class="maintainTime">' +  $.date.dateToStr(new Date(), "yyyy-MM-dd HH:mm") + '</span>',
	 		$("#useRoom").text() + '<input type="hidden" value="' + $("#useRoomId").val() + '">'
	     ]) ;
		$("#useRoom").text("");
		$("#useRoomId").val("");
		$(".dataTables_empty").parent().remove();
		newLineIndex++;
	});
	
//	/**
//	 * 删行
//	 */
//	$(document).on("click","#deleRow",function(){
//		var arr = $.icheck.getChecked("check") ;
//		$.validform.closeAllTips("validformId") ;
//    	$.each(arr, function(i, val){
//    		if(!$.util.isEmpty($(val).val())){
//    			deleteIdLst.push($(val).val());
//    		}
//    		var tr = $(val).parents("tr").remove() ;
//    	});
//	});
	
	/**
	 * 确认
	 */
	$(document).on("click","#confirm",function(){
		$("#confirm").attr("disabled", "disabled");
		var trs = $("#temporaryLeave tbody").children();
		var demo = $.validform.getValidFormObjById("validformId") ;
		if(! $.validform.check(demo)){
			$("#confirm").attr("disabled", false);
			return;
		}
		var beans = [];
		var checkTime = null;
		for (var i = 0; i <= trs.length-1; i++) {
			var tds = trs.eq(i).children();
			if(tds[0].className == "dataTables_empty" || tds.eq(0).find(".changePermissions").val() == "false"){
				continue;
			}
			var tdOjb = {};
			tdOjb.id = tds.eq(0).find(".changePermissions").attr("id");
			tdOjb.leaveTime = $.laydate.getTime("#" + tds.eq(0).find("div").attr("id"), "date");	//离开时间
			tdOjb.leaveCause = tds.eq(1).find("input").val();	//离开原因
			tdOjb.returnTime = $.laydate.getTime("#" + tds.eq(2).find("div").attr("id"), "date");	//返回时间
			tdOjb.maintainer = tds.eq(3).find(".maintainer").text();	//维护人
			tdOjb.maintainTime = $.date.strToDate(tds.eq(3).find(".maintainTime").text(), "yyyy-MM-dd HH:mm").getTime();	//维护时间
			tdOjb.askRoom = null; //离开前使用的讯（询）问室
			if(!$.util.isBlank(tds.eq(4).find("input").val())){
				tdOjb.askRoom = {};
				tdOjb.askRoom.id = tds.eq(4).find("input").val();
			}
			beans.push(tdOjb);
			if(checkTime != null && tdOjb.leaveTime <= checkTime){
				window.top.$.layerAlert.alert({msg:"离开时间必须晚于上一返回时间！(" + (++i) + "行)",title:"提示"});
				$("#confirm").attr("disabled", false);
				return;
			}
			checkTime = tdOjb.leaveTime;
			if($.util.exist(tdOjb.returnTime)){
				if(tdOjb.returnTime <= checkTime){
					window.top.$.layerAlert.alert({msg:"返回时间必须晚于离开时间！(" + (++i) + "行)",title:"提示"});
					$("#confirm").attr("disabled", false);
					return;
				}
				checkTime = tdOjb.returnTime;
			}else if(finalLeave){
				window.top.$.layerAlert.alert({msg:"请填写返回时间！(" + (++i) + "行)",title:"提示"});
				$("#confirm").attr("disabled", false);
				return;
			}
		}
		var fliBean = {};
		if($.util.isBlank(fliId)){
			fliBean.id = null;
		}else{
			fliBean.id = fliId;
		}
		fliBean.handlingAreaReceiptId = harId;	//办案区使用单
		if(finalLeave){
			fliBean.finallyLeaveTime = $.laydate.getTime("#finalLeaveTime", "date");
			if(fliBean.finallyLeaveTime == null){
				window.top.$.layerAlert.alert({msg:"最终离开时间不可为空！",title:"提示"});
				$("#confirm").attr("disabled", false);
				return;
			}
			if(fliBean.finallyLeaveTime <= checkTime){
				window.top.$.layerAlert.alert({msg:"最终离开时间必须晚于最后一次临时离开的返回时间！",title:"提示"});
				$("#confirm").attr("disabled", false);
				return;
			}
			fliBean.finallyLeaveCause = $("#leaveReason").val();	//最终离开原因
			if($.util.isBlank(fliBean.finallyLeaveCause)){
				window.top.$.layerAlert.alert({msg:"最终离开原因不可为空！",title:"提示"});
				$("#confirm").attr("disabled", false);
				return;
			}
			if($.util.isBlank($("#askRoomId").val())){
				fliBean.askRoom = null;
			}else{
				fliBean.askRoom = {};			
				fliBean.askRoom.id = $("#askRoomId").val(); //最终离开前使用的讯（询）问室
			}
		}
		if($.util.isBlank(fliId)){
			modifyFlag = false;
		}else{
			modifyFlag = true;
		}
		var dataMap = {};
		$.util.objToStrutsFormData(beans, "tliBeanLst", dataMap);
		$.util.objToStrutsFormData(deleteIdLst, "deleteIdLst", dataMap);
		$.util.objToStrutsFormData(fliBean, "fliBean", dataMap);
		$.util.objToStrutsFormData(modifyFlag, "modifyFlag", dataMap);
		$.ajax({
			url:context + "/leaveSituation/saveorUpdateLeaveSituation.action",
			type:'post',
			data:dataMap,
			dataType:'json',
			customizedOpt:{
				btn:{	    			
					btn:"#confirm"
				}
			},
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:"保存成功！"});
					$(".laydate").addClass("date-disabled");
					$(".disabled").attr("readonly", "readonly");
					$("#updateLeaveInfo").attr("disabled", "disabled");
					$("#addRow").attr("disabled", "disabled");
					$("#deleRow").attr("disabled", "disabled");
					$("#leaveReason").attr("readonly", "readonly");
					
					$("#confirm").hide();
					$("#cancel").hide();
					$("#return").show();
					$.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
					$.common.setIsUpdate(false);
					$("#modifyPeopleName").text(successData.modifyPerson);
					$("#updateTime").text(successData.modifyTime);
					jumpOn();
				}else{
					window.top.$.layerAlert.alert({msg:"保存失败！"+successData.errorMessage});
				}
			}
		});
	});
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function(){
		window.location.href = $.util.fmtUrl(context +"/leaveSituation/showLeaveSituation.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 返回
	 */
	$(document).on("click","#return",function(){
		window.location.href = $.util.fmtUrl(context +"/leaveSituation/showLeaveSituation.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 维护最终离开情况
	 */
	$(document).on("click","#updateLeaveInfo",function(){
//		if(!$.util.isBlank($("#useRoomStatus").val()) && $.common.Constant.SYZT_KX() != $("#useRoomStatus").val()){
//			window.top.$.layerAlert.alert({msg:"审讯未结束，不能办理离开手续！",title:"提示"});
//			return;
//		}
		var flag = true;
		$.each($(".backCheck"), function(i,val){
			if($.util.isBlank($(this).val())){
				flag = false;
				return false;
			}
		})
		if(!flag){
			window.top.$.layerAlert.alert({msg:"请填写返回时间！",title:"提示"});
			return;
		}
		$.ajax({
			url:context +'/handlingAreaReceipt/finalLeaveCheck.action',
			type:'post',
			data:{
				id:harId
			},
			dataType:'json',
			success:function(successData){
				if(successData.flag == "true"){
					$("#leaveInfo").show();
					$("#askRoomName").text($("#useRoom").text());
					$("#askRoomId").val($("#useRoomId").val());
					$("#useRoom").text("");
					$("#useRoomId").val("");
					$.laydate.setTime(new Date().getTime(), "#finalLeaveTime");
					finalLeave = true;
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
	 * 判断手环状态并button置为不可选
	 * @returns
	 */
	function cuffStatus(){
		$.ajax({
			url:context +'/leaveSituation/queryCuffStatus.action',
			type:'post',
			dataType:'json',
			data:{
				id:harId
			},
			success:function(successData){
				var flag=successData.flag;
				if(flag=='true'){
					var mes=successData.cuffStatusMessage; //挂起状态
					if(mes=='1'){ //挂起状态
						gq();
					}else{
						qxgq();
					}
				}else{
					window.top.$.layerAlert.alert({msg:"手环状态查询失败："+successData.errorMessage,icon:"5"});
				}
			}
		})
	}
	
	function gq(){
		$('#offLine').attr("disabled","disabled");
		$('#onLine').removeAttr("disabled");
		$('#offLine').removeClass("btn-info");
		$('#offLine').addClass("btn-default");
		$('#onLine').removeClass("btn-default");
		$('#onLine').addClass("btn-info");
	}
	function qxgq(){
		$('#onLine').attr("disabled","disabled");
		$('#offLine').removeAttr("disabled");
		$('#onLine').removeClass("btn-info");
		$('#onLine').addClass("btn-default");
		$('#offLine').removeClass("btn-default");
		$('#offLine').addClass("btn-info");
	}
	var index = 0;
	
	function initTable(){
		tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/leaveSituation/queryLeaveSituationList.action";
		tb.columnDefs = [{
			"targets": 0,
 	    	"width": "",
 	    	"title": "离开时间<span class='red-star'>*</span>",
 	    	"className":"table-checkbox",
 	    	"data": "leaveTime" ,
 	    	"render": function ( data, type, full, meta ) {
 	    		var a = '<input id="' + full.id + '" class="changePermissions" type="hidden" ';
 	    		if(!full.changePermissions){
					a += 'value="false">';
				}else{
					a += 'value="true">';
				}
 	    		a += '<div id="leaveTime' + meta.row + '"class="laydate input-group';
 	    		if(!full.changePermissions){
					a += ' date-disabled';
				}
 	    		a += '" style="width:200px;"><input type="hidden" class="laydate-fmt dateFmt" value="yyyy-MM-dd HH:mm" /><input type="text" date-pos="top"  class="laydate-value form-control input-sm valiform-keyup" vf-position="3" datatype="*" readonly="readonly" value="' + $.date.timeToStr(data, "yyyy-MM-dd HH:mm") + '"><span class="laydate-value-span input-group-addon m-ui-addon"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span></div>';
 	    		return a;	
 	    	}
		},
		{
			"targets" : 1,
			"width" : "",
			"title" : "离开原因<span class='red-star'>*</span>",
			"data" : "leaveCause",
			"render" : function(data, type, full, meta) {
				var a = '<input type="text" class="form-control input-sm valiform-keyup form-val disabled" vf-position="3" datatype="*1-80" value="' + data + '"';
				if(!full.changePermissions){
					a += ' readonly';
				}
	    	  	a += '>';
	    	  	return a;
			}
		},
		{
			"targets" : 2,
			"width" : "",
			"title" : "返回时间",
			"data" : "returnTime",
			"render" : function(data, type, full, meta) {
				var tmpD = "";
				if(!$.util.isBlank(data)){
					tmpD = $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
				}else{
					tmpD = $.date.timeToStr(new Date(), "yyyy-MM-dd HH:mm");
				}
				var a = '<div id="returnTime' + meta.row + '" class="laydate input-group';
				if(!full.changePermissions){
					a += ' date-disabled';
				}
				a += '" style="width:200px;"><input type="hidden" class="laydate-fmt dateFmt" value="yyyy-MM-dd HH:mm" /><input type="text" date-pos="top"  class="laydate-value form-control input-sm backCheck" readonly="readonly"  value="' + tmpD + '"><span class="laydate-value-span input-group-addon m-ui-addon"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span></div>';
				return a;
			}
		},
		{
			"targets" : 3,
			"width" : "",
			"title" : "维护人/维护时间",
			"data" : "maintainer",
			"render" : function(data, type, full, meta) {
				return "<span class='maintainer'>" + data + "</span><br/><span class='maintainTime'>" + $.date.timeToStr(full.maintainTime, "yyyy-MM-dd HH:mm") + "</span>";
			}
		},
		{
			"targets" : 4,
			"width" : "",
			"title" : "离开前使用的讯（询）问室",
			"data" : "id",
			"render" : function(data, type, full, meta) {
				var roomId = null;
				var roomName = "";
				if($.util.exist(full.askRoom)){
					roomName = full.askRoom.name;
					roomId = full.askRoom.id;
				}
				return roomName + '<input type="hidden" value="' + roomId + '">';
			}
		}
	];
	
		tb.ordering = false;
		tb.paging = false;
		tb.hideHead = false;
		tb.dom = "";
		tb.searching = false;
		tb.lengthChange = false;
		tb.lengthMenu = [ 3, 5, 10 ];
		tb.paramsReq = function(d, pagerReq){
			d["id"] = harId;
		};
		tb.paramsResp = function(json) {
			$("#confirm").attr("disabled", false);
			if(!$.util.exist(json.tliBeanLst)){
				json.data = [];
			}else{
				json.data = json.tliBeanLst;
			}
			$("#modifyPeopleName").text((json.fliBean.modifyPeopleBean == null)? " " : json.fliBean.modifyPeopleBean.name);
			$("#updateTime").text((json.fliBean.updatedTime == null)? " " : $.date.timeToStr(json.fliBean.updatedTime, "yyyy-MM-dd HH:mm"));
			
			if(json.fliBean.finallyLeaveTime != null){
				finalLeave = true;
				$("#updateLeaveInfo").click();
				$.laydate.setTime(json.fliBean.finallyLeaveTime, "#finalLeaveTime");
				$("#leaveReason").val(json.fliBean.finallyLeaveCause);
				if($.util.exist(json.fliBean.askRoom)){
					$("#askRoomName").text(json.fliBean.askRoom.name);
					$("#askRoomId").val(json.fliBean.askRoom.id);
				}
			}
			if(!json.fliBean.changePermissions){
				$(".laydate").addClass("date-disabled");
				$("#leaveReason").attr("readonly", "readonly");
			}
			$("#useRoom").text($.util.isBlank(json.useRoom)?"":json.useRoom);
			$("#useRoomId").val(json.useRoomId);
			$("#useRoomStatus").val(json.useRoomStatus);
			fliId = json.fliBean.id;
		};
		table = $("#temporaryLeave").DataTable(tb);
	}
})(jQuery);