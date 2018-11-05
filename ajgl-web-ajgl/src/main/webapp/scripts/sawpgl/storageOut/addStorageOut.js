(function($){
	"use strict";
	
	var sawpTable = null;
	
	var tb = $.uiSettings.getOTableSettings();
	tb.ajax.url = context + "/storageOut/queryStorageByCase.action";
	tb.columnDefs = [
		{
			"targets": 0,
 	    	"width": "5%",
 	    	"title": "序号",
 	    	"className":"table-checkbox",
 	    	"data": "involvedArticleId" ,
 	    	"render": function ( data, type, full, meta ) {
 	    			  return "<input type='hidden' value='" + data + "'>" + (meta.row + 1);
 	    	}
		},
		{
			"targets" : 1,
			"width" : "",
			"title" : "物品名称",
			"data" : "involvedArticleName",
			"render" : function(data, type, full, meta) {
				return data;
			}
		},
		{
			"targets" : 2,
			"width" : "",
			"title" : "特征",
			"data" : "involvedArticleFeature",
			"render" : function(data, type, full, meta) {
				return data;
			}
		},
		{
			"targets" : 3,
			"width" : "",
			"title" : "物品编号",
			"data" : "involvedArticleCode",
			"render" : function(data, type, full, meta) {
				return data + '<a href="###" class="qrFile" fileId="' + data + '">&nbsp;&nbsp;<img src="../images/photo/ewm.png" width="30" height="30"></a>';
			}
		},
		{
			"targets" : 4,
			"width" : "",
			"title" : "扣押数量",
			"data" : "detentionNumber",
			"render" : function(data, type, full, meta) {
				return data;
			}
		},
		{
			"targets" : 5,
			"width" : "",
			"title" : "物品性质",
			"data" : "involvedArticleType",
			"render" : function(data, type, full, meta) {
				return data;
			}
		},
		{
			"targets" : 6,
			"width" : "",
			"title" : "在库数量",
			"data" : "numberInStorage",
			"render" : function(data, type, full, meta) {
				return "<span class='existNum'>" + data + "</span>";
			}
		},
		{
			"targets" : 7,
			"width" : "",
			"title" : "本次出库数量<span class='red-star'>*</span>",
			"data" : "outcomingNumber",
			"render" : function(data, type, full, meta) {
				return "<input id='" + (Math.random()+"").substring(2,8) + "' style='width:50px'  datatype='/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/' errormsg='请填写数量'  class='input-bordered outNum' value='" + data + "'>";
			}
		},
		{
			"targets" : 8,
			"width" : "",
			"title" : "计量单位",
			"data" : "measurementUnit",
			"render" : function(data, type, full, meta) {
				return data;
			}
		},
		{
			"targets" : 9,
			"width" : "",
			"title" : "所在物证保管区",
			"data" : "areaName",
			"render" : function(data, type, full, meta) {
				return data;
			}
		},
		{
			"targets" : 10,
			"width" : "",
			"title" : "所在储物箱",
			"data" : "lockerName",
			"render" : function(data, type, full, meta) {
				return "<input type='hidden' value='" + full.lockerId + "'>" + data;
			}
		},
		{
			"targets" : 11,
			"width" : "",
			"title" : "对应犯罪嫌疑人/物品持有人",
			"data" : "suspectedName",
			"render" : function(data, type, full, meta) {
				return data;
			}
		},
		{
			"targets" : 12,
			"width" : "",
			"title" : "对应入库文书",
			"data" : "papers",
			"render" : function(data, type, full, meta) {
				var td = '<a class="paper" id="'+full.papersId+'" type="'+full.papersType+'" href="#">'+data+'</a>';
				return td;
			}
		},
		{
			"targets" : 13,
			"width" : "",
			"title" : "备注",
			"data" : "remark",
			"render" : function(data, type, full, meta) {
				return data;
			}
		}
	];
	
	tb.paging = false ;
	//是否排序
	tb.ordering = false ;
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
		d["caseCode"] = $.select2.val("#caseCode");
	};
	tb.paramsResp = function(json) {
		json.data = json.outStorageFormItemBeanList;
		json.recordsTotal = json.totalNum;
		json.recordsFiltered = json.totalNum;
		$("#caseName").val(json.caseName);
		$("#polices").val(json.polices);
	};
	tb.rowCallback = function(row,data, index) {
		$(row).data("data",data);
	};
	
	$(document).ready(function() {	
		initCaseCode();
		initPlupload();
		
		alertCaseCodeSelectPage();
		
		sawpTable = $("#sawpTable").DataTable(tb);
		$.laydate.setDate($.date.dateToStr(new Date, "yyyy-MM-dd HH:mm"),"#ckTime");
		$(document).on("click",".qrFile",function(){
			var tempDiv = $("<div/>");
			$(tempDiv).qrcode({
			    "render": 'image',
			    "size": 110,
			    "color": "#3a3",
			    "background": "white",
			    "text": $(this).attr("fileId")
			});
			$.layerAlert.img($(tempDiv).find("img").attr("src"),120);
		});
		
		$(document).on("blur",".outNum",function(){
			if(parseFloat($(this).val(),10) > parseFloat($(this).closest("tr").find(".existNum").text(), 10)){
				$.layerAlert.tips({
					msg:'出库数量不可大于在库数量！',
					selector:"#" + $(this).attr("id"),  //需要弹出层的元素选择器
					color:'#FF0000',
					position:1,
					closeBtn:2,
					time:3000,
					shift:1
				});
				$(this).val(0);
				return;
			}
		});
		
		$(document).on("click","#saveBtn",function(){
			var demo = $.validform.getValidFormObjById("validformStorageOut");
			var flag = $.validform.check(demo);
			if(!flag){
				return;
			}
			var trs = $("#sawpTable tbody").children();
			var outTotalNum = 0.0;
			for (var i = 0; i <= trs.length-1; i++) {
				var tds = trs.eq(i).children();
				if(tds[0].className == "dataTables_empty"){
					window.top.$.layerAlert.alert({msg:"无物品可出库，不能保存！"});
					return;
				}
				outTotalNum += parseFloat(tds.eq(7).find(".outNum").val(),10);
			}
			if(outTotalNum <= 0){
				window.top.$.layerAlert.alert({msg:"请填写出库物品的出库数量！"});
				return;
			}
			if($.select2.val("#type") != $.common.Constant.CKLX_SAYJ() && $.select2.val("#type") != $.common.Constant.CKLX_ZLFH() && $.plupload.getUploader("container").files.length == 0){
				window.top.$.layerAlert.alert({msg:"请上传文书！"});
				return;
			}
//			if($.plupload.getUploader("container").files.length == 0){
//				window.top.$.layerAlert.alert({msg:"请上传文书！"});
//				return;
//			}
			$.plupload.start("container");
		});
		
		//TODO 20161101 修改为不管什么类型的出库都必须上传文书
//		$(document).on("select2:select", "#type", function(){
//			if($.select2.val("#type") != $.common.Constant.CKLX_SAYJ() && $.select2.val("#type") != $.common.Constant.CKLX_ZLFH()){
//				$("#containerStar").text("*");
//			}else{
//				$("#containerStar").text("");
//			}
//		});
		$(document).on("click","#cancel",function(){
			history.go(-1);
		});
		
		/**
		 * 案件编号确定按钮事件
		 */
		$(document).on("click","#caseCodeConfirm",function(){
			sawpTable.draw();
			//设置编号不可修改
			$("#caseCode").attr("readonly","readonly");
			$("#caseCodeConfirm").hide();
			$("#caseCodeUpdate").show();
		});

		/**
		 * 案件编号修改按钮事件
		 */
		$(document).on("click","#caseCodeUpdate",function(){
			//设置编号可修改
			$("#caseCode").removeAttr("readonly");
			$("#caseCodeConfirm").show();
			$("#caseCodeUpdate").hide();
			//案件名称、办案民警、嫌疑人、对应文书信息
			$("#caseName").val("");
			$("#polices").val("");
			$("#receiver").val("");
			$("#sawpTable tbody").html("");
		});
		
		/**
		 * 文书点击事件
		 */
		$(document).on("click",".paper",function(){
			var paperId = $(this).attr("id");
			var paperType = $(this).attr("type");
			window.top.open(context+"/showWrit/checkWrit.action?paperId=" + paperId + "&paperType=" + paperType);
		});
		
		/**
		 * 查询案件编码
		 */
		$(document).on("click","#selectCaseCode",function(){
			$("#caseCode").val("");
			alertCaseCodeSelectPage();
		});
	});
	
	function initCaseCode(){
		$.ajax({
			url:context +'/storageOut/findAllCaseCodeAndfindCode.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				$("#code").val(successData.code);
				$("#qrDiv").qrcode({
				    "render": 'image',
				    "size": 170,
				    "color": "#3a3",
				    "background": "white",
				    "text": successData.code
				});
			}
		});
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.CKLX()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#type", successData.dictionaryItemLst, "id", "name", true, true);
			}
		});
	}
	$(document).on("select2:unselect", "#caseCode", function(){
		sawpTable.draw();
	});
	$(document).on("select2:select", "#caseCode", function(){
		sawpTable.draw();
	});
	
	function initPlupload(){
		var setting = $.plupload.getBasicSettings() ;
		setting.container = "container" ; //容器id
		setting.url = context + "/storageOut/uploadFile.action";
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
		setting.multi_file_num = 1 ;
		setting.multi_file_num_callback = function(max_file_size, totalSize){
		};
		setting.callBacks = {
				uploadAllFinish:function(up, finishedFiles){
					save(finishedFiles);
				}
		}
		$.plupload.init(setting) ;
	}
	
	
	function save(finishedFiles){
		var outStorageFormBean = {};
		var fileBeanLst = [];
		outStorageFormBean.code = $("#code").val();
		outStorageFormBean.outStorageTime = $.laydate.getTime("#ckTime", "date");
		outStorageFormBean.type = $.select2.val("#type");
		outStorageFormBean.caseCode = $("#caseCode").val();
		outStorageFormBean.caseName = $("#caseName").val();
		outStorageFormBean.polices = $("#polices").val();
		var str = "";
		for (var key in finishedFiles) {
			var obj = {};
			obj.id = key;
			fileBeanLst.push(obj);
			str += finishedFiles[key].name + "，";
        }
		if(str != "" && str.indexOf("，") != -1){
			str = str.substr(0, str.length - 1);
		}
		outStorageFormBean.papers = str;
		outStorageFormBean.remark = $("#remark").val();
		outStorageFormBean.receiver = $("#receiver").val();
		
		var outStorageFormItemBeanList = [];
		var trs = $("#sawpTable tbody").children();
		for (var i = 0; i <= trs.length-1; i++) {
			var tds = trs.eq(i).children();
			if(tds[0].className == "dataTables_empty"){//|| tds.eq(7).find("input").val() == 0
				continue;
			}
			var tdOjb = {};
			tdOjb.involvedArticleId = tds.eq(0).find("input").val();
			tdOjb.involvedArticleCode = trs.eq(i).data("data").involvedArticleCode;
			tdOjb.numberInStorage = trs.eq(i).data("data").numberInStorage;
			tdOjb.outcomingNumber = tds.eq(7).find("input").val();
			tdOjb.lockerId = tds.eq(10).find("input").val();
			outStorageFormItemBeanList.push(tdOjb);
		}
		var dataMap = {};
		$.util.objToStrutsFormData(outStorageFormBean, "outStorageFormBean", dataMap);
		$.util.objToStrutsFormData(fileBeanLst, "fileBeanLst", dataMap);
		$.util.objToStrutsFormData(outStorageFormItemBeanList, "outStorageFormItemBeanList", dataMap);
		$.ajax({
			url:context +'/storageOut/saveStorageOut.action',
			type:'post',
			data:dataMap,
			dataType:'json',
			customizedOpt:{
				btn:{	    			
					btn:"#saveBtn"
				}
			},
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:"保存成功！",end:function(){
						window.location.href = $.util.fmtUrl(context + "/storageOut/toShowRecord.action?&&id=" + successData.id);
					}});
				}else{
					window.top.$.layerAlert.alert({msg:"保存失败!"});
				}
				
			}
		});
	}
	
	/**
	 * 弹出案件编号选择页面
	 */
	function alertCaseCodeSelectPage(){
		window.top.$.layerAlert.dialog({
			content : context +  '/selectCaseCodeAlert/showSelectCaseCodeAlertPage.action',
			pageLoading : true,
			title:"查询案件编号",
			width : "850px",
			height : "500px",
			btn:["确定","取消"],
			callBacks:{
				btn1:function(index, layero){
					var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
					var caseCode = cm.getCaseCode();
					if(!$.util.isBlank(caseCode)){
						$("#caseCode").val(caseCode);
						$("#selectCaseCode").hide();
					}
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					sawpTable.draw();
				},
				btn2:function(index, layero){
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				
			},
			initData:{
				
			},
			end:function(){
				
			}
		});
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, {
		
	});	
})(jQuery);