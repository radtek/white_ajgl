<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<input type="hidden" id="harId"
		value=<%=request.getParameter("harId")%>>
	<input type="hidden" id="justShow"
		value=<%=request.getParameter("justShow")%>>
	<input type="hidden" id="modifyBtnFlag"
		value=<%=request.getAttribute("modifyBtnFlag")%>>
	<input type="hidden" id="printBtnFlag"
		value=<%=request.getAttribute("printBtnFlag")%>>
	<input type="hidden" id="finishBtnFlag"
		value=<%=request.getAttribute("finishBtnFlag")%>>
	<ul id="handlingAreaReceiptMenuUl">
		<li id="personBaseInfo"><a href="#tabs-1">进入办案区人员基本情况<span class=""></span></a></li>
		<li id="checkRecord"><a href="#tabs-2">人身检查记录<span class=""></span></a></li>
		<li id="infoGather"><a href="#tabs-4">信息采集情况<span class=""></span></a></li>
		<li id="goodsCheckRecord"><a href="#tabs-3">随身物品保管<span class=""></span></a></li>
		<li id="activityRecord"><a href="#tabs-5">活动记录</a></li>
		<li id="goodsReturn"><a href="#tabs-6">随身物品日常管理<span class=""></span></a></li>
		<li id="leaveWork"><a href="#tabs-7">离开办案区情况<span class=""></span></a></li>
	</ul>
</body>
<script type="text/javascript">
(function($){
	"use strict";
	var isUpdate = false;//是否是修改
	$(document).ready(function() {
		initIco();
		if($("#modifyBtnFlag").val() == "true"){
			$(".modifyBtnFlag").show();
		}else{
			$(".modifyBtnFlag").show();
			$(".modifyBtnFlag").attr("disabled", "disabled");
		}
		if($("#printBtnFlag").val() == "true"){
			$(".printBtnFlag").show();
		}else{
			$(".printBtnFlag").show();
			$(".printBtnFlag").attr("disabled", "disabled");
		}
		if($("#finishBtnFlag").val() == "true"){
			$(".finishBtnFlag").show();
		}else{
			$(".finishBtnFlag").show();
			$(".finishBtnFlag").attr("disabled", "disabled");
		}
		/**
		 * 进入办案区人员基本情况
		 */
		$(document).on("click","#personBaseInfo",function(){
			if(isUpdate){
			}else{
				window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
			}
		});
		/**
		 * 人身检查记录
		 */
		$(document).on("click","#checkRecord",function(){
			if(isUpdate){
			}else{
				window.location.href = $.util.fmtUrl(context +"/personCheckRecord/showLookPersonCheckRecordPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
			}
		});
		/**
		 * 随身物品检查记录
		 */
		$(document).on("click","#goodsCheckRecord",function(){
			if(isUpdate){
			}else{
				window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsInfo.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
			}
		});
		/**
		 * 信息采集情况
		 */
		$(document).on("click","#infoGather",function(){
			if(isUpdate){
			}else{
				window.location.href = $.util.fmtUrl(context +"/collectInfoSituation/showLookCollectInfoSituationPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
			}
		});
		/**
		 * 活动记录
		 */
		$(document).on("click","#activityRecord",function(){
			if(isUpdate){
			}else{
				window.location.href = $.util.fmtUrl(context +"/activityRecord/showActivityRecord.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
			}
		});
		/**
		 * 随身物品返还情况
		 */
		$(document).on("click","#goodsReturn",function(){
			if(isUpdate){
			}else{
				window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsReturnRecord.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
			}
		});
		/**
		 * 离开办案区情况
		 */
		$(document).on("click","#leaveWork",function(){
			if(isUpdate){
			}else{
				window.location.href = $.util.fmtUrl(context +"/leaveSituation/showLeaveSituation.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
			}
		});
	});
	
	function initIco(){
		$.ajax({
			url:context +'/handlingAreaReceipt/findAllIco.action',
			type:'post',
			data:{
				harId : $("#harId").val()
			},
			dataType:'json',
			success:function(successData){
				var i = 0;
				$("#handlingAreaReceiptMenuUl").find("span").each(function(){
					if(successData.icoLst[i] == "done"){
						$(this).addClass("color-green2");
						$(this).addClass("icon-ok");
					}else{
						$(this).addClass("color-red2");
						$(this).addClass("icon-remove");
					}
					i++;
				});
			}
		})
	
	}
	/**
	 * 设置被选中的页签
	 * @param tabsLiId 页签li元素id
	 */
	function setSelectedTabsById(tabsLiId){
		var menuLiArray = $("#handlingAreaReceiptMenuUl li");
		$.each(menuLiArray,function(l,li){
			var id = $(li).attr("id");
			if(id == tabsLiId){
				$(li).addClass("ui-tabs-active");
			}else{
				$(li).removeClass("ui-tabs-active");
			}
		});
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		setSelectedTabsById : setSelectedTabsById,
		setIsUpdate : function(flag){
			isUpdate = flag;
		}
	});	
})(jQuery);
</script>
</html>
