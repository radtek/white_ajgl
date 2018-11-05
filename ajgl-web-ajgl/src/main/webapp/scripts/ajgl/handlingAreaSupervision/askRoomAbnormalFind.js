(function($) {
	"use strict";
	 var askRoomId = $("#askRoomId").val();
     var askRoomZt = $("#askRoomZt").val();
     var askRoomName = $("#askRoomName").val();
	 var askRoomStatusName =  $("#askRoomStatusName").val();
     var attachFlag = $("#attachFlag").val();
     var attachId = $("#attachId").val();
     var attachName = $("#attachName").val();
     var base64Str = $("#base64Str").val();
     var tabHaveFlag = $("#tabHaveFlag").val();
	$(document).ready(function() {
		
		//只有正在使用状态的询问室显示“使用情况”Tab页。
		if (tabHaveFlag == "one") {
			$("#zyxx").remove();
		}
		
		
		$("#wgxx").click();
		//加载文书。
		if(attachFlag){
			$("#attach").html("<a href='###' id='dlws'>" + attachName + "</a>");
			$(document).on("click","#dlws",function(){
				window.open(context + "/handlingAreaReceipt/downloadFile.action?attachmentId="+ attachId);					
			})
		}
		
		if (askRoomZt == $.common.Constant.SYZT_KX()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-green1'>空闲</span>");
		} else if (askRoomZt == $.common.Constant.SYZT_SYZ()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-red1'>使用中</span>");
		} else if (askRoomZt == $.common.Constant.SYZT_BKY()) {
			$("#roomDetail").html(askRoomName+"&nbsp;&nbsp;&nbsp;<span class='state state-gray1'>不可用</span>");
		}
		//加载违规类型数据
		$.ajax({
			url:context +'/illegalType/queryIllegalTypeForQyList.action',
			type:'post',
			success:function(successData){
				var beanList = successData.illegalTypeBeanList;
				if (null != beanList && beanList.length > 0) {
					var trs;
					$.each(beanList, function(i, obj){
						if (obj.isSystemData == $.common.Constant.SF_S()) {
							trs += '<tr><td><input type="radio" class="icheckradio"  id="'+obj.id+'" value="'+obj.id+'" checked name="jgroup">&nbsp;&nbsp; '+obj.name+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注： <input id="qt" class="form-control input-sm valiform-keyup form-val" datatype="*0-200"  style="width:auto;margin-left:2px;"/></td></tr>';   
						} else {
							trs += '<tr><td><input type="radio" class="icheckradio"  id="'+obj.id+'" value="'+obj.id+'" checked name="jgroup">&nbsp;&nbsp; '+obj.name+' </td></tr>';   
						}
					});
					$("#radioBody").html(trs);
				}
				
			}
		});
		
	});
	
	//查看使用单详情。
	$(document).on("click",".showDetail",function(){
		var id = $(this).attr("id");
		window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=true&&harId=" + id);
	});
	//返回
	$(document).on("click","#back",function(){
		window.location.href = $.util.fmtUrl(context + "/handlingAreaSupervision/showAskRoomInfoDetail.action?&&askRoomId="+ askRoomId+"&&pageFlag=askRoomInfo");
	});
	//刷新
	$(document).on("click","#refresh",function(){
		window.location.href = $.util.fmtUrl(context + "/handlingAreaSupervision/showAskRoomInfoDetail.action?&&askRoomId="+ askRoomId);
	});
	
	//提交违规记录
	$(document).on("click","#tj",function(){
		var askRoomIllegalRecordBean = {};
		var askRoomAllocationId = $("#askRoomAllocationId").val();
		var askRoomId = $("#askRoomId").val();
		var typeObj = $.icheck.getChecked("jgroup");
		var type = $(typeObj[0]).val();
		var bz = $("#qt").val();
		if (type == $.common.Constant.WGLX_QT() && $.util.isEmpty(bz)) {
			$.layerAlert.alert({msg:"违规类型为其他情况时，请填写备注。"}) ;
			return false;
		}
		askRoomIllegalRecordBean.illegalCause = type;		
		askRoomIllegalRecordBean.bz = bz;
		var dataMap = {};
		$.util.objToStrutsFormData(askRoomIllegalRecordBean, "askRoomIllegalRecordBean", dataMap);
		dataMap["askRoomIllegalRecordBean.askRoomAllocationBean.id"] = askRoomAllocationId;
		dataMap["askRoomIllegalRecordBean.activityRoomBean.id"] = askRoomId;
		$.ajax({
			url:context +'/handlingAreaSupervision/saveorUpdateIllegalRecord.action',
			type:'post',
			dataType:'json',
			data:dataMap,
			success:function(successData){
				if (successData.flag == "true") {
					$.layerAlert.alert({msg:"操作成功。",icon:1});	
				} else {
					$.layerAlert.alert({msg:"操作失败"});
				}
			}
		});
	});
	
	
})(jQuery);