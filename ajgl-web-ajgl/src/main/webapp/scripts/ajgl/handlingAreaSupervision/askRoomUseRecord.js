(function($) {
	"use strict";
	var askRoomUseRecordTable = null;
	$(document).ready(function() {
		initData();
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		initAskRoomUseRecordTable()	
	});
	
	/**
	 * 查询
	 */
	$(document).on("click",".queryRecord",function() {
		askRoomUseRecordTable.draw(false);
	});
	
	/**
	 * 重置
	 */
	$(document).on("click","#reset",function() {
		$("#simpleCode").val("");
		$("#code").val("");
		$.select2.val("#roomLst","");
		$("#idNum").val("");
		$.laydate.reset("#accessDate");
		$("#askPerson").val("");
		$("#handlingPolice").val("");
		$("#unit").val("");
		$("#unitName").val("");
	});
	
	/**
	 * 下载笔录
	 */
	setTimeout(function () {		
		$(document).on("click",".xzbl",function() {
			var id = $(this).attr("id");
			if ($.util.isBlank(id)) {
				window.top.$.layerAlert.alert({msg:"没有上传笔录。"});
			} else {
				window.open(context + "/handlingAreaReceipt/downloadFile.action?attachmentId="+ id);					
			}
			
		});
	},500);
	
	
	/**
	 * 初始化table
	 */
	function initAskRoomUseRecordTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/handlingAreaSupervision/queryAskRoomUseRecord.action";
			tb.columnDefs = [
				{
					"targets" : 0,
					"width" : "90",
					"title" : "使用的讯（询）问室",
					"data" : "activityRoomName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 1,
					"width" : "120",
					"title" : "分配讯（询）问室时间",
					"data" : "startAllocationTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "120",
					"title" : "离开讯（询）问室时间",
					"data" : "leaveTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "100",
					"title" : "使用单编号",
					"data" : "handlingAreaReceiptNum",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				
				{
					"targets" : 4,
					"width" : "200",
					"title" : "所属单位",
					"data" : "unitName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "60",
					"title" : "办案民警",
					"data" : "handlingPolice",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "150",
					"title" : "被讯（询）问人姓名<br/>证件号码",
					"data" : "byQuestioningPeopleName",
					"render" : function(data, type, full, meta) {
						return data + "<br/>" + (full.byQuestioningPeopleIdentifyNum == null ? "" : full.byQuestioningPeopleIdentifyNum);
					}
				},
				{
					"targets" : 7,
					"width" : "100",
					"title" : "所属案件",
					"data" : "lawCase",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 8,
					"width" : "200",
					"title" : "操作",
					"data" : "unitId",
					"render" : function(data, type, full, meta) {
						var a = '<button class="btn btn-primary"><span class="glyphicon glyphicon-play-circle micon-lg"></span>回放</button><button class="btn btn-success"><span class="glyphicon glyphicon-download micon-lg"></span>下载音视频</button><button class="btn btn-info xzbl" id="'+data+'"  ><span class="glyphicon glyphicon-edit micon-lg"></span>查看笔录</button>';
						return a;
					}
				}
				
			];
			//是否排序
			tb.ordering = false ;
			//每页条数
			tb.lengthMenu = [ 5 ];
			//默认搜索框
			tb.searching = false ;
			//能否改变lengthMenu
			tb.lengthChange = false ;
			//自动TFoot
			tb.autoFooter = false ;
			//自动列宽
			tb.autoWidth = false ;
			//请求参数
			tb.paramsReq = function(d, pagerReq){
				if($.common.isFullConditionSearch()){
					d["askRoomId"] = $("#roomLst").val();	//询问室名称
					d["askRoomUseRecordQueryBean.handlingAreaReceiptNum"] = $("#code").val();	//使用单号
					d["askRoomUseRecordQueryBean.lawCase"] = $.select2.val("#lawCase");	//所属案件
					d["askRoomUseRecordQueryBean.byQuestioningPeopleName"] = $("#askPerson").val();	//被问讯人姓名
					d["bz"] = $("#idNum").val();	//被问讯人身份证件号码
					d["askRoomUseRecordQueryBean.startAllocationTime"] = $.laydate.getDate("#accessDate", "start");	//分配时间开始
					d["askRoomUseRecordQueryBean.endAllocationTime"] = $.date.endRange($.laydate.getDate("#accessDate", "end"), "yyyy-MM-dd");	//分配时间结束
					d["askRoomUseRecordQueryBean.unitId"] = $("#unit").val();	//所属单位
					d["askRoomUseRecordQueryBean.handlingPolice"] = $("#handlingPolice").val();	//办案民警
				}else{
					d["askRoomUseRecordQueryBean.handlingAreaReceiptNum"] = $("#simpleCode").val()=="使用单编号模糊查询"?"":$("#simpleCode").val();
				}
				d["orderCondition"] = "allocationTime";
				
			};
			tb.paramsResp = function(json) {
				var askRoomUseRecordQueryBeanList = json.askRoomUseRecordQueryBeanList;
				json.data = askRoomUseRecordQueryBeanList;
				json.recordsTotal = json.pageNum;
				json.recordsFiltered = json.pageNum;
				
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			askRoomUseRecordTable = $("#askRoomUseRecordTable").DataTable(tb);
	}
	
	/**
	 * 初始化选项
	 */
	function initData(){
		$.ajax({
			url:context +'/handlingAreaReceipt/initDataListPage.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#roomLst", successData.roomLst, "id", "name", true, true);
			}
		});		
		
	}
	

})(jQuery);