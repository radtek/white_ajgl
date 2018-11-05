(function($){
	"use strict";
	var sawpTable = null;
	var formId = $("#formId").val();
	$(document).ready(function() {	
		initStorageInDataTable();
		dataSet();
	});
	
	/**
	 * 打印
	 */
	$(document).on("click","#print",function(){
		var form = $.util.getHiddenForm(context +'/storageIn/printStorageInById.action?inStorageFormId='+$("#formId").val(), null);
		$.util.subForm(form);
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
	 * 删除入库单
	 * @param formIdList 入库单id集合
	 */
	function deleteByIds(formIdList){
		var gData = new Object();
		$.util.objToStrutsFormData(formIdList, "formIdList", gData);
		
		$.ajax({
			url:context +'/storageIn/deleteByIds.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				window.top.$.layerAlert.alert({msg:"删除成功。",icon:1, end:function(){
					window.location.href = $.util.fmtUrl(context + "/storageIn/toStorageInRecord.action");	
				}}) ;
			},
			error:function(errorData){
				window.top.$.layerAlert.alert({msg:"使用过的入库单不可删除。",icon:0}) ;
			}
		});
	}
	
	function dataSet () {
		//加载二维码
		$("#qrcode").qrcode({
		    "render": 'image',
		    "size": 170,
		    "color": "#3a3",
		    "background": "white",
		    "text":$("#code").html()
		});
		//加载文书信息
		$.ajax({
			url: context + '/storageIn/findDocBeanBySuspectIdAndCaseId.action',
			type:"POST",
			data:{suspectId:$("#suspectId").val(),caseCode:$("#caseCode").text()},
			dataType:"json",
			success:function(data){
				if(data.paperList.length < 1){
					return ;
				}
				var papersText = "";
				$.each(data.paperList, function(i, obj){
					papersText += '<li><a class="paper" id="'+obj.paperId+'" type="'+obj.paperType+'" href="#">'+obj.paperName+'</a></li>';
				});
				$("#paperUl").html(papersText);
			}
		});
		
	}
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function(){
		history.back();
	});
	
	/**
	 * 点击入库单二维码进行查看。
	 */
	$(document).on("click",".qrFile",function(){
         var articleCode = $(this).attr("fileId");
		window.top.$.layerAlert.dialog({
			content : context +  '/storageIn/showArticleQrcodePage.action',
			pageLoading : true,
			title:"查看物品二维码",
			width : "500px",
			height : "600px",
			btn:["打印二维码","关闭"],
			callBacks:{
				btn1:function(index, layero){
					var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
					cm.print();
				},
				btn2:function(index, layero){
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				
			},
			initData:{
				articleCode:articleCode
			},
			end:function(){
			}
		});
//		var tempDiv = $("<div/>");
//		$(tempDiv).qrcode({
//		    "render": 'image',
//		    "size": 100,
//		    "color": "#3a3",
//		    "background": "white",
//		    "text": $(this).attr("fileId")
//		});
//		$.layerAlert.img($(tempDiv).find("img").attr("src"),120);
	});
	
	$(document).on("click","#delete",function(){
		window.top.$.layerAlert.confirm({
			msg:"删除后不可恢复，确定要删除吗？",
			title:"删除",	  //弹出框标题
			width:'300px',
			hight:'200px',
			shade: [0.5,'black'],  //遮罩
			icon:0,  //弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
			yes:function(index, layero){
				var formIdList = new Array();
				formIdList.push(formId);
				//点击确定按钮后执行
				deleteByIds(formIdList);
			}
		});
	});
	$(document).on("click","#refresh",function(){
		location.reload(true);  
	});
	
	
	function initStorageInDataTable(){
		    var xh = 1;
		    var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/storageIn/findInStorageFormById.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "5%",
         	    	"title": "序号",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    		return xh++;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "10%",
					"title" : "物品名称",
					"data" : "articleName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "8%",
					"title" : "物品特征",
					"data" : "articleFeature",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "8%",
					"title" : "物品编号",
					"data" : "articleCode",
					"render" : function(data, type, full, meta) {
						return data + '&nbsp;&nbsp;<a href="###" class="qrFile" fileId="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
					}
				},
				{
					"targets" : 4,
					"width" : "5%",
					"title" : "扣押数量",
					"data" : "detentionNumber",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "8%",
					"title" : "物品性质",
					"data" : "articleTypeName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "5%",
					"title" : "扣押应入库数量",
					"data" : "numberRequested",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 7,
					"width" : "5%",
					"title" : "计量单位",
					"data" : "measurementUnit",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 8,
					"width" : "5%",
					"title" : "累计已入库数量",
					"data" : "accumulateSum",
					"render" : function(data, type, full, meta) {
						var storages = full.storageServiceBeans;
						var sumCount = 0 ;
						if (null != storages && storages.length > 0) {
							$.each(storages, function(i, obj){
								sumCount += obj.incomingNumber;
							});
						}
						return sumCount;
					}
				},
				{
					"targets" : 9,
					"width" : "10%",
					"title" : "对应文书",
					"data" : "paperName",
					"render" : function(data, type, full, meta) {
						var td = '<a class="paper" id="'+full.paperId+'" type="'+full.paperType+'" href="#">'+data+'</a>';
						return td;
					}
				},
				{
					"targets" : 10,
					"width" : "8%",
					"title" : "所在物证保管区",
					"data" : "areaName",
					"render" : function(data, type, full, meta) {
						var areaName = "";
						if($.util.exist(full.storageServiceBeans) && full.storageServiceBeans.length > 0){
							areaName = full.storageServiceBeans[0].areaName
						}
						return areaName;
					}
				},
				{
					"targets" : 11,
					"width" : "8%",
					"title" : "所在储物箱/入库数量",
					"data" : "",  
					"render" : function(data, type, full, meta) {
						var storages = full.storageServiceBeans;
						var info = "";
						if (null != storages && storages.length > 0) {
							$.each(storages, function(i, obj){
								info += obj.lockerLocation + "，数量：" + obj.incomingNumber;
								if (i < (storages.length-1)) {
									info += "<br/>";
								}
							});
						}
						return info;
					}
				},
				{
					"targets" : 12,
					"width" : "10%",
					"title" : "维护时间",
					"data" : "updatedTime",
					"render" : function(data, type, full, meta) {
						
						return ($.util.isBlank(data) ? $.date.dateToStr(new Date(), "yyyy-MM-dd HH:mm") : $.date.timeToStr(data, "yyyy-MM-dd HH:mm"));
					}
				},
				{
					"targets" : 13,
					"width" : "10%",
					"title" : "备注",
					"data" : "remark",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}
			];
			//是否排序
			tb.ordering = false ;
			tb.paging = false ; 
			tb.info = false ;
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
				d["id"] = formId;
			};
			tb.paramsResp = function(json) {
				var itemBeanlist = json.isfisBeanList;
				json.data = itemBeanlist;
			};
			tb.rowCallback = function(row,data, index) {
					
					
			};
			sawpTable = $("#sawqTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);