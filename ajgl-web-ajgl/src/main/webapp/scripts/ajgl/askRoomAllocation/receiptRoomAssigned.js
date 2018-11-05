(function($){
	"use strict";
	
	var receiptTable = null;
	
	$(document).ready(function() {
		enterAreaReasonsSelectInit();
		initReceiptTable();
		$.laydate.setRange($.date.dateToStr(new Date(),"yyyy-MM-dd"), $.date.dateToStr(new Date(),"yyyy-MM-dd"),"#accessDate");
//		alert(askRoomId);	//询问室id
	});
	
	/**
	 * 查看详情按钮事件
	 */
	$(document).on("click",".showDetail",function(){
		var id = $(this).attr("id");
		window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=true&&harId=" + id);
	});
	
	/**
	 * 查询可以分配讯（询）问室的使用单
	 */
	$(document).on("click",".queryReceipt",function() {
		receiptTable.draw(false);
	});
	
	/**
	 * 重置
	 */
	$(document).on("click","#reset",function() {
		$(".simpleCode").val("");
		$("#handlingAreaReceiptNum").val("");
		$("#handlingPolice").val("");
		$("#applyForUser").val("");
		$("#askPerson").val("");
		$("#idNum").val("");
		$.select2.val("#enterAreaReasons","");
		$("#lawCase").val("");
		$.laydate.setRange($.date.dateToStr(new Date(),"yyyy-MM-dd"), $.date.dateToStr(new Date(),"yyyy-MM-dd"),"#accessDate");
	});
	
	/**
	 * 确定分配
	 */
	$(document).on("click","#confirm",function() {
		var arr = $.icheck.getChecked("receiptCheck") ;
		if(arr.length==0||arr.length>1){
			$.layerAlert.alert({msg:"请选择一条使用单",title:"提示"});
			return ;
		}
    	var id = "";
    	$.each(arr, function(i, val){
    		var tr = $(val).parents("tr") ;
    		var row = receiptTable.row(tr) ;
    		var data = row.data() ;
    		id = data.id;
    	});
    	
    	$.ajax({
			url : context + "/askRoomAllocation/addAskRoomAllocationRecord.action",
			type : 'post',
			dataType : 'json',
			data:{
				"askRoomId":askRoomId,
				"receiptId":id
			},
			success : function(successData) {
				window.top.$.layerAlert.alert({msg:successData.msg, end:function(){
					window.location.href = $.util.fmtUrl(context + "/askRoomAllocation/showAskRoomAllocationSelectPage.action");
				}}) ;
			},
			error : function(errorData) {

			}
		});
	});
	
	/**
	 * 取消分配
	 */
	$(document).on("click","#cancel",function() {
		history.go(-1);
	});
	
	/**
	 * 初始化进入办案区原因、申请使用人下拉框
	 */
	function enterAreaReasonsSelectInit(){
		//进入办案区原由
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.BAQYY()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#enterAreaReasons", successData.dictionaryItemLst, "id", "name", true, true);
			}
		});
		//申请使用人
//		$.ajax({
//			url:context +'/webUtil/findDictionaryItemByType.action',
//			type:'post',
//			data:{dictionaryType : $.common.Constant.BAQYY()},
//			dataType:'json',
//			success:function(successData){
//				$.select2.addByList("#enterAreaReasons", successData.dictionaryItemLst, "id", "name", true, true);
//			}
//		});
	}
	
	function initReceiptTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/askRoomAllocation/searchSelectedHandlingAreaReceiptByPage.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "50px",
     	    	"title": "选择",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  var a = '<input type="radio" name="receiptCheck" class="icheckradio" value="'+data+'"/>' ;
     	    			  return a;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "使用单编号",
				"data" : "code",
				"render" : function(data, type, full, meta) {
					return "<a href='###' id='" + full.id + "' class='a-link mar-right showDetail'>" + data + "</a>";
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "进入办案区时间",
				"data" : "accessDateStr",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "办案民警",
				"data" : "police",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "申请使用人",
				"data" : "proposer",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "",
				"title" : "所属案件",
				"data" : "aboutCaseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "",
				"title" : "被讯（询）问人姓名",
				"data" : "askPerson",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 7,
				"width" : "",
				"title" : "证件号码",
				"data" : "idNum",
				"render" : function(data, type, full, meta) {
					return data;
				}
			}
		];
		//是否排序
		tb.ordering = true ;
		//是否分页
		tb.paging = false;
		//默认搜索框
		tb.searching = false ;
		//能否改变lengthMenu
		tb.lengthChange = false ;
		//自动TFoot
		tb.autoFooter = false ;
		//自动列宽
		tb.autoWidth = true ;
		tb.dom = "";
		//请求参数
		tb.paramsReq = function(d, pagerReq){
			if($.common.isFullConditionSearch()){
				d["harSearchBean.code"] = $("#handlingAreaReceiptNum").val();	//案件编码
				d["harSearchBean.police"] = $("#handlingPolice").val();	//警员
				d["harSearchBean.modifyPeopleName"] = $.select2.val("#applyForUser");	//申请人
				d["harSearchBean.enterAreaReasons"] = $.select2.val("#causeOfLawCase");	//进入办案区原因
				d["harSearchBean.askPerson"] = $("#askPerson").val();	//被询问人
				d["harSearchBean.idNum"] = $("#idNum").val();	//证件号码
				d["harSearchBean.aboutCase"] = $("#lawCase").val();	//所属案件
				d["harSearchBean.accessDateStart"] = $.laydate.getDate("#accessDate", "start");	//进入办案区日期
				d["harSearchBean.accessDateEnd"] = $.date.endRange($.laydate.getDate("#accessDate", "end"), "yyyy-MM-dd");
			}else{
				d["harSearchBean.code"] = $(".simpleCode").val()=="使用单编号模糊查询"?"":$(".simpleCode").val();
				d["harSearchBean.accessDateStart"] = $.date.dateToStr(new Date(), "yyyy-MM-dd");	//进入办案区日期
				d["harSearchBean.accessDateEnd"] = $.date.endRange($.date.dateToStr(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			}
		};
		tb.paramsResp = function(json) {
			var harSearchBeanlst = json.harSearchBeanlst;
			json.data = harSearchBeanlst;
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		receiptTable = $("#receiptTable").DataTable(tb);
	}
	
})(jQuery);