(function($){
	"use strict";
	
	var storageOutRecordTable = null;
	var today = new Date();
	var yesterday = new Date();
	
	$(document).ready(function() {
		yesterday.setDate(yesterday.getDate() - 1);
		
		$(document).on("click",".dlws",function(){
			$.layerAlert.img("data:image/png;base64," + $(this).attr("fileId"),400);
//			window.open(context + "/activityRecord/downloadFile.action?attachmentId="+ $(this).attr("fileId"));
		});
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
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','.excel',function(){
			var d=new Object();
			if($.common.isFullConditionSearch()){
				d["storageOutBean.code"] = $("#code").val();
				d["storageOutBean.caseCode"] = $("#caseCode").val();
				d["storageOutBean.suspectName"] = $("#suspectName").val();
				d["storageOutBean.papers"] = $("#paper").val();
				d["storageOutBean.remark"] = $("#remark").val();
				d["startTime"] = $.laydate.getTime("#ckDate", "start");	//时间开始
				d["endTime"] = $.date.endRangeByTime($.laydate.getTime("#ckDate", "end"),"yyyy-MM-dd HH:mm");	    //时间结束
				d["sf"] = "";
//				var obj = $.icheck.getChecked("sf");
//				if (obj.length > 0) {
//					d["sf"] = 1; //显示我的入库单
//				} else {
//					d["sf"] = 0; //不显示我的入库单
//				}
				d["storageOutBean.restitution"] = $.select2.val("#restitution");
				d["storageOutBean.type"] = $.select2.val("#type");
				
			}else{
				var endTime = new Date();
				endTime.setDate(endTime.getDate() + 1);
				d["startTime"] = $.date.strToTime($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	//时间开始
				d["endTime"] = $.date.strToTime($.date.dateToStr(endTime,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	 
				d["storageOutBean.code"] = $("#simpleCode").val() == "出库单编号模糊查询" ? "" : $("#simpleCode").val();
				d["sf"] = ""; // $("#sf").val(); //是否显示我的出库单
			}
			var form = $.util.getHiddenForm(context +'/storageOut/exportExcel.action', d);
			$.util.subForm(form);
		});	
		/**
		 * 查看按钮事件
		 */
		$(document).on("click",".showOut",function(){
			window.location.href = $.util.fmtUrl(context + "/storageOut/toShowRecord.action?&&id=" + $(this).attr("outId"));					
		});
		/**
		 * 新建按钮事件
		 */
		$(document).on("click",".addOut",function(){
			window.location.href = $.util.fmtUrl(context + "/storageOut/toNewRecord.action");					
		});
		/**
		 * 删除按钮事件
		 */
		$(document).on("click",".delOut",function(){
			var arr = $.icheck.getChecked("ckd");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"请选择出库单！"}) ;
				return false;
			}
			if(arr.length > 1){
				window.top.$.layerAlert.alert({msg:"最多选择一张出库单！"}) ;
				return false;
			}
			var id = $(arr[0]).val(); 
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
						data:{id:id},
						dataType:'json',
						success:function(successData){
							if(successData.flag == "true"){
								window.top.$.layerAlert.alert({msg:"删除成功！",end:function(){
									storageOutRecordTable.draw(true);
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
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			storageOutRecordTable.draw(true);
		});
		
		/**
		 * 刷新按钮事件
		 */
		$(document).on("click",".reloadBtn",function(){
			location.reload(true);   
		});
		
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#reset",function(){
			resetSearchCondition();
		});
		
		initData();
		initSelect();
		initStorageInDataTable();
	});
	
	function initData(){
		$.laydate.setRange($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),$.date.dateToStr(today,"yyyy-MM-dd HH:mm"),"#ckDate");
	}
	
	function initSelect(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.SF()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#restitution", successData.dictionaryItemLst, "id", "name", true, true);
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
	function initStorageInDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/storageOut/queryStorageOutRecord.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "50px",
         	    	"title": "选择",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    		var a = '<input type="checkbox" name="ckd" class="icheckbox" value="'+data+'"/>' ;
   	    			    return a;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "出库单编号",
					"data" : "code",
					"render" : function(data, type, full, meta) {
						var td = '<a class="detail showOut" outId="' + full.id + '" href="###">' + data + '</a>&nbsp;&nbsp;';
     	    				td += '<a href="###" class="qrFile" fileId="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
     	    			return td;
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "出库时间",
					"data" : "createdTime",
					"render" : function(data, type, full, meta) {
						return $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "对应案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "对应案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "办案民警",
					"data" : "police",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "",
					"title" : "出库类型",
					"data" : "typeStr",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 7,
					"width" : "",
					"title" : "对应出库文书",
					"data" : "id",
					"render" : function(data, type, full, meta) {
						var a = "";
						for(var i in full.attachLst){
							a += "<a href='###' class='dlws' fileId='" + full.attachLst[i].base64Str+ "'>" + full.attachLst[i].name + "</a>";
							if(i != full.attachLst.length - 1){
								a += "<br/>";
							}
						}
						return a;
					}
				},
				{
					"targets" : 8,
					"width" : "",
					"title" : "对应犯罪嫌疑人/物品持有人",
					"data" : "suspectName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 9,
					"width" : "",
					"title" : "是否再入库完成",
					"data" : "restitutionStr",
					"render" : function(data, type, full, meta) {
						if(full.typeStr=='其他'||full.typeStr=='借出'){
							return data;
						}
						return "--";
					}
				},
				{
					"targets" : 10,
					"width" : "",
					"title" : "备注",
					"data" : "remark",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}
			];
			//是否排序
			tb.ordering = false ;
			//每页条数
			tb.lengthMenu = [ 10 ];
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
					d["storageOutBean.code"] = $("#code").val();
					d["storageOutBean.caseCode"] = $("#caseCode").val();
					d["storageOutBean.suspectName"] = $("#suspectName").val();
					d["storageOutBean.papers"] = $("#paper").val();
					d["storageOutBean.remark"] = $("#remark").val();
					d["startTime"] = $.laydate.getTime("#ckDate", "start");	//时间开始
					d["endTime"] = $.date.endRangeByTime($.laydate.getTime("#ckDate", "end"),"yyyy-MM-dd HH:mm");	    //时间结束
					d["sf"] = "";
//					var obj = $.icheck.getChecked("sf");
//					if (obj.length > 0) {
//						d["sf"] = 1; //显示我的入库单
//					} else {
//						d["sf"] = 0; //不显示我的入库单
//					}
					d["storageOutBean.restitution"] = $.select2.val("#restitution");
					d["storageOutBean.type"] = $.select2.val("#type");
					
				}else{
					var endTime = new Date();
					endTime.setDate(endTime.getDate() + 1);
					d["startTime"] = $.date.strToTime($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	//时间开始
					d["endTime"] = $.date.strToTime($.date.dateToStr(endTime,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	 
					d["storageOutBean.code"] = $("#simpleCode").val() == "出库单编号模糊查询" ? "" : $("#simpleCode").val();
					d["sf"] = "";//$("#sf").val(); //是否显示我的出库单
				}
			};
			tb.paramsResp = function(json) {
				var storageOutBeanList = json.storageOutBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = storageOutBeanList;
			};
			storageOutRecordTable = $("#storageOutRecordTable").DataTable(tb);
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#code").val("");
		$("#caseCode").val("");
		$("#suspectName").val("");
		$("#paper").val("");
		$("#remark").val("");
		initData();
		$.icheck.check("#sf",true);
		$.select2.val("#restitution","");
		$.select2.val("#type","");
		storageOutRecordTable.draw(true);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);