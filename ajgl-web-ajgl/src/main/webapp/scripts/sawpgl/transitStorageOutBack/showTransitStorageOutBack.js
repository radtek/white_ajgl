(function($){
	"use strict";
	var sawpTable = null;	
	$(document).ready(function() {			
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
//			window.open(context + "/transitStorageOutBack/downloadFile.action?attachmentId="+ $(this).attr("fileId"));					
		})
		/**
		 * 打印
		 */
		$(document).on("click","#print",function(){
			var form = $.util.getHiddenForm(context +'/templateStoragePrint/printTempStorageOut.action?code='+$("#outCode").val(), null);
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
						url:context +'/transitStorageOutBack/delTransitStorageOutBack.action',
						type:'post',
						data:{id:$("#outId").val()},
						dataType:'json',
						success:function(successData){
							if(successData.flag == "true"){
								window.top.$.layerAlert.alert({msg:"删除成功！", end:function(){
									window.location.href = $.util.fmtUrl(context + "/transitStorageOutBack/toTransitStorageOutBackRecord.action?");	
								}}) ;
							}else{
								window.top.$.layerAlert.alert({msg:successData.msg}) ;
							}
						}
					});
				}
			});
		});
		/**
		 * 刷新
		 */
		$(document).on("click","#refresh",function(){
			location.reload(true);  
		});
		/**
		 * 取消
		 */
		$(document).on("click","#cancel",function(){
			window.location.href = $.util.fmtUrl(context + "/transitStorageOutBack/toTransitStorageOutBackRecord.action");
		});
		
		/**
		 * 附件点击事件
		 */
		$(document).on("click",".paper",function(){
			var paperId = $(this).attr("id");
			var paperType = $(this).attr("type");
			window.open(context+"/showWrit/checkWrit.action?paperId=" + paperId + "&paperType=" + paperType);
		});
		
		/**
		 * 确认出库点击事件
		 */
		$(document).on("click","#affirmStorageOut",function(){
			$.ajax({
				url:context +'/transitStorageOutBack/affirmStorageOut.action',
				type:'post',
				data:{code:$("#outCode").val()},
				dataType:'json',
				success:function(successData){
					if(successData.flag == "true"){
						window.top.$.layerAlert.alert({msg:"确认出库成功！", end:function(){
							$('#affirmStorageOut').attr("disabled","disabled");
						}}) ;
					}else{
						window.top.$.layerAlert.alert({msg:successData.msg}) ;
					}
				}
			});
		})
		/**
		 * 图片点击事件
		 */
		 $(document).on("click",".img",function(){
				$.layerAlert.img($(this).attr("src"));
		})
		initTable();
	});
	
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/transitStorageOutBack/queryTransitStorageOutBackByCode.action";
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
				"data" : "objectName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "特征",
				"data" : "objectCharacter",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "本次出库返还数量",
				"data" : "inThisNum",
				"render" : function(data, type, full, meta) {
					return data ;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "计量单位",
				"data" : "measureUnit",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "",
				"title" : "物品图片",
				"data" : "objectPicture",
				"render" : function(data, type, full, meta) {
					return "<img class='img' src='data:image/png;base64," + data + "' height='40'>";;
				}
			},
			{
				"targets" : 6,
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
			d["code"] = $("#outCode").val();
		};
		tb.paramsResp = function(json) {
			json.data = json.tempObjectBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			var temporaryStorageOutBean = json.temporaryStorageOutBean;
			$("#code").text(temporaryStorageOutBean.code);
			$("#qrDiv").qrcode({
			    "render": 'image',
			    "size": 170,
			    "color": "#3a3",
			    "background": "white",
			    "text": temporaryStorageOutBean.code
			});
			$("#ckTime").text($.date.timeToStr(temporaryStorageOutBean.outStorageTime, "yyyy-MM-dd HH:mm"));
			$("#caseName").text((temporaryStorageOutBean.caseName==null?'':temporaryStorageOutBean.caseName));
			$("#caseCode").text((temporaryStorageOutBean.caseCode==null?'':temporaryStorageOutBean.caseCode));
			$("#polices").text((temporaryStorageOutBean.polices==null?'':temporaryStorageOutBean.polices));
			$("#remark").text((temporaryStorageOutBean.remark==null?'':temporaryStorageOutBean.remark));
			$("#modifyPeople").text((json.modifyPeople==null?'':json.modifyPeople));
			$("#modifyTime").text($.date.timeToStr(json.updateTime, "yyyy-MM-dd HH:mm"));
			$("#harCode").text((temporaryStorageOutBean.harCode==null?'':temporaryStorageOutBean.harCode));
			$("#suspectName").text((temporaryStorageOutBean.suspectName==null?'':temporaryStorageOutBean.suspectName));
			$("#suspectId").text((temporaryStorageOutBean.suspectId==null?'':temporaryStorageOutBean.suspectId));
			$("#storageRack").text((temporaryStorageOutBean.storageRack==null?'':temporaryStorageOutBean.storageRack));
			$("#inStorageArea").text((temporaryStorageOutBean.inStorageArea==null?'':temporaryStorageOutBean.inStorageArea));
			if(temporaryStorageOutBean.status!=$.common.Constant.SF_S()){
				$('#affirmStorageOut').removeAttr("disabled","disabled");
			}
			var fileLst = json.fileBeanLst;
			var str = "";
			for(var i in fileLst){
				str += '<li><a href="###" class="dlws" fileId="' + fileLst[i].base64Str + '">' + fileLst[i].name + '</a></li>';
			}
			$("#papers").append(str);
			$('#print').removeAttr("disabled","disabled");
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		sawpTable = $("#sawpTable").DataTable(tb);
	}
	
})(jQuery);