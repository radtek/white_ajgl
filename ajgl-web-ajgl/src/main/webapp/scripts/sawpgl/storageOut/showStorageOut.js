(function($){
	"use strict";
	var sawpTable = null;
	var tb = $.uiSettings.getOTableSettings();
	tb.ajax.url = context + "/storageOut/queryStorageOutById.action";
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
				return data + '&nbsp;&nbsp;<a href="###" class="qrFile" fileId="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
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
				return data;
			}
		},
		{
			"targets" : 7,
			"width" : "",
			"title" : "本次出库数量",
			"data" : "outcomingNumber",
			"render" : function(data, type, full, meta) {
				return  data;
			}
		},
		{
			"targets" : 8,
			"width" : "",
			"title" : "计量单位",
			"data" : "measurementUnit",
			"render" : function(data, type, full, meta) {
				return  data;
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
		d["id"] = $("#outId").val();
	};
	tb.paramsResp = function(json) {
		json.data = json.outStorageFormItemBeanList;
		json.recordsTotal = json.totalNum;
		json.recordsFiltered = json.totalNum;
		var outStorageFormBean = json.outStorageFormBean;
		$("#code").text(outStorageFormBean.code);
		$("#qrDiv").qrcode({
		    "render": 'image',
		    "size": 170,
		    "color": "#3a3",
		    "background": "white",
		    "text": outStorageFormBean.code
		});
		$("#ckTime").text($.date.timeToStr(outStorageFormBean.outStorageTime, "yyyy-MM-dd HH:mm"));
		$("#type").text(outStorageFormBean.type);
		$("#caseName").text(outStorageFormBean.caseName);
		$("#caseCode").text(outStorageFormBean.caseCode);
		$("#polices").text(outStorageFormBean.polices);
		$("#remark").text(outStorageFormBean.remark);
		$("#isReturned").text(outStorageFormBean.isReturned);
		$("#receiver").text(outStorageFormBean.receiver);
		$("#modifyPeople").text(json.modifyPeople);
		$("#modifyTime").text($.date.timeToStr(json.updateTime, "yyyy-MM-dd HH:mm"));
		var fileLst = json.fileBeanLst;
		var str = "";
		for(var i in fileLst){
			str += '<li><a href="###" class="dlws" fileId="' + fileLst[i].base64Str + '">' + fileLst[i].name + '</a></li>';
		}
		$("#papers").append(str);
	};
	tb.rowCallback = function(row,data, index) {
		
	};
	$(document).ready(function() {	
		sawpTable = $("#sawpTable").DataTable(tb);
		$(document).on("click",".qrFile",function(){
			var tempDiv = $("<div/>");
			$(tempDiv).qrcode({
			    "render": 'image',
			    "size": 110,
			    "color": "#3a3",
			    "background": "white",
			    "text": $(this).attr("fileId")
			});
			$.layerAlert.img($(tempDiv).find("img").attr("src"),130,10);
		});
		$(document).on("click",".dlws",function(){
			$.layerAlert.img("data:image/png;base64," + $(this).attr("fileId"),400);
//			window.open(context + "/storageOut/downloadFile.action?attachmentId="+ $(this).attr("fileId"));					
		})
		$(document).on("click","#print",function(){
			var data = {id : $("#outId").val()};
			var form = $.util.getHiddenForm(context +'/storageOut/printStorageOutById.action', data);
			$.util.subForm(form);
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
					$.ajax({
						url:context +'/storageOut/delStorageOut.action',
						type:'post',
						data:{id:$("#outId").val()},
						dataType:'json',
						success:function(successData){
							if(successData.flag == "true"){
								window.top.$.layerAlert.alert({msg:"删除成功！", end:function(){
									window.location.href = $.util.fmtUrl(context + "/storageOut/toStorageOutRecord.action?");	
								}}) ;
							}else{
								window.top.$.layerAlert.alert({msg:successData.msg}) ;
							}
						}
					});
				}
			});
		});
		$(document).on("click","#refresh",function(){
			location.reload(true);  
		});
		/**
		 * 取消
		 */
		$(document).on("click","#cancel",function(){
			window.location.href = $.util.fmtUrl(context + "/storageOut/toStorageOutRecord.action");
		});
		
		/**
		 * 文书点击事件
		 */
		$(document).on("click",".paper",function(){
			var paperId = $(this).attr("id");
			var paperType = $(this).attr("type");
			window.open(context+"/showWrit/checkWrit.action?paperId=" + paperId + "&paperType=" + paperType);
		});
	});
})(jQuery);