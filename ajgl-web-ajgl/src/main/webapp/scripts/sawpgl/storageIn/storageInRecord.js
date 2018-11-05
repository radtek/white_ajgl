(function($){
	"use strict";
	
	var storageInTable = null;
	
	var today = new Date();
	var yesterday = new Date();
	
	$(document).ready(function() {	
		
		yesterday.setDate(yesterday.getDate() - 1);
		
		initData();
		
		/**
		 * 新建按钮事件
		 */
		$(document).on("click","#add",function(){
			window.location.href = $.util.fmtUrl(context + "/storageIn/toNewRecord.action");					
		});
			
		/**
		 * 查看入库单详情
		 */
		$(document).on("click",".detail",function(){
			var id = $(this).attr("name");
			window.location.href = $.util.fmtUrl(context + "/storageIn/viewStorageIn.action?inStorageFormId="+id);					
		});
		
		/**
		 * 删除按钮事件
		 */
		$(document).on("click","#delete",function(){
			var arr = $.icheck.getChecked("rkd");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"请至少选择一条数据进行操作。"}) ;
				return false;
			}
			
			var formIdList = new Array();
			$.each(arr,function(a,val){
				var id = $(val).val();
				formIdList.push(id);
			});
			
			window.top.$.layerAlert.confirm({
				msg:"删除后不可恢复，确定要删除吗？",
				title:"删除",	  //弹出框标题
				width:'300px',
				hight:'200px',
				shade: [0.5,'black'],  //遮罩
				icon:0,  //弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
				yes:function(index, layero){
					//点击确定按钮后执行
					deleteByIds(formIdList);
				}
			});
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
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			storageInTable.draw(true);
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			resetSearchCondition();
		});
		
		/**
		 * 刷新
		 */
		$(document).on("click","#refresh",function(){
			window.location.href = $.util.fmtUrl(context + "/storageIn/toStorageInRecord.action");
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
			var endTime = new Date();
			endTime.setDate(endTime.getDate() + 1);
			var startTime  = $.date.endRange($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	//时间开始
			var endTime = $.date.endRange($.date.dateToStr(endTime,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	
			if($.common.isFullConditionSearch()){
				var startTime =  $.laydate.getDate("#rkDate", "start");	//时间开始	//时间开始
				var endTime = $.laydate.getDate(("#rkDate", "end"));	    //时间结束
			}
			var sf = "";
//			var obj = $.icheck.getChecked("sf");
//			if (obj.length > 0) {
//				sf = 1; //显示我的入库单
//			} 
			$.util.subForm($.util.getHiddenForm(context + "/storageIn/exportExcel.action?code="+$("#code").val()+"&&caseCode="+$("#caseCode").val()+"&&suspectName="+$("#suspectName").val()+"&&papers="+$("#papers").val()
					+"&&remark="+$("#remark").val()+"&&startTime="+startTime+"&&endTime="+endTime+"&&sf="+sf));
		});	
		
		/**
		 * 查看文书
		 */
		$(document).on("click",".dlws",function(){
			$.layerAlert.img("data:image/png;base64," + $(this).attr("fileId"));
		});
		
		/**
		 * 点击入库单二维码进行查看。
		 */
		$(document).on("click",".qrFile",function(){
			var tempDiv = $("<div/>");
			$(tempDiv).qrcode({
			    "render": 'image',
			    "size": 100,
			    "color": "#3a3",
			    "background": "white",
			    "text": $(this).attr("fileId")
			});
			$.layerAlert.img($(tempDiv).find("img").attr("src"),120);
		});
		
		initStorageInDataTable();
	});
	
	function initStorageInDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/storageIn/queryStorageInRecord.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "5%",
         	    	"title": "选择",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    		var a = '<input type="checkbox" name="rkd" class="icheckbox" value="'+data+'"/>' ;
         	    		if (full.editOrNot) {
         	    			a = '<input type="checkbox" name="rkd" class="icheckbox" disabled="disabled" value="'+data+'"/>' ;
         	    		}
   	    			    return a;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "10%",
					"title" : "入库单编号",
					"data" : "code",
					"render" : function(data, type, full, meta) {
						var td = '<a class="detail bsfCode" name="' + full.id + '" href="###">' + data + '</a>&nbsp;&nbsp;';
	 	    				td += '<a href="###" class="qrFile" name="' + full.id + '" fileId="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
	 	    			return td;
					}
				},
				{
					"targets" : 2,
					"width" : "12%",
					"title" : "入库时间",
					"data" : "createdTimeStr",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "10%",
					"title" : "对应案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return data;
						
					}
				},
				{
					"targets" : 4,
					"width" : "10%",
					"title" : "案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "12%",
					"title" : "办案民警",
					"data" : "caseHandler",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "15%",
					"title" : "对应犯罪嫌疑人/物品持有人",
					"data" : "suspectName",
					"render" : function(data, type, full, meta) {
//						var td = data ;
//						if(full.inStorageFormItems.length > 0 && !$.util.isBlank(full.inStorageFormItems[0].suspectIdentifier)){
//							td += "<br/>" + full.inStorageFormItems[0].suspectIdentifier;
//						}
//						return td ;
						return data;
					}
				},
				{
					"targets" : 7,
					"width" : "15%",
					"title" : "对应文书",
					"data" : "papers",
					"render" : function(data, type, full, meta) {
						var papersText = "";
						$.each(full.docs,function(i,obj){
							papersText +='<p><a class="paper" id="'+obj.paperId+'" type="'+obj.paperType+'" href="#">'+obj.paperName+'</a></p>';
						});
						return papersText; 
					}
				},
				{
					"targets" : 8,
					"width" : "15%",
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
					var fmt = $.laydate.getFmt("#rkDate") ;
					d["storageInBean.code"] = $("#code").val();
					d["storageInBean.caseCode"] = $("#caseCode").val();
					d["storageInBean.suspectName"] = $("#suspectName").val();
					d["storageInBean.papers"] = $("#papers").val();
					d["storageInBean.remark"] = $("#remark").val();
					d["startTime"] = $.laydate.getDate("#rkDate", "start");	//时间开始
//					d["endTime"] = $.laydate.getDate("#rkDate", "end");
					var end=$.laydate.getDate("#rkDate", "end");
					d["endTime"] =$.date.endRange(end,"yyyy-MM-dd HH:mm");
					d["sf"] = "";
//					var obj = $.icheck.getChecked("sf");
//					if (obj.length > 0) {
//						d["sf"] = $.common.Constant.SF_S(); //显示我的入库单
//					} 
				}else{
					var endTime = new Date();
					endTime.setDate(endTime.getDate() + 1);
					d["startTime"] = $.date.endRange($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");	//时间开始
					d["endTime"] = $.date.endRange($.date.dateToStr(endTime,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");
					var codeText = $(".simpleCode").val() == "入库单编号模糊查询" ? "" : $(".simpleCode").val();
					d["storageInBean.code"] = codeText;
					d["sf"] = "";//$.common.Constant.SF_S(); //显示我的入库单
				}
			};
			tb.paramsResp = function(json) {
				var formBeanlist = json.formBeanlist;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = formBeanlist;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			storageInTable = $("#storageInTable").DataTable(tb);
	}
	
	
	
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
				window.top.$.layerAlert.alert({msg:"删除成功。",icon:1}) ;
				storageInTable.draw(true);
			},
			error:function(errorData){
				window.top.$.layerAlert.alert({msg:"使用过的入库单不可删除。",icon:0}) ;
			}
		});
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#code").val("");
		$("#caseCode").val("");
		$("#suspectName").val("");
		$("#remark").val("");
		$("#papers").val("");
		$.laydate.reset("#rkDate");
		$('.sxcheckbox').iCheck('check');
		initData();
		storageInTable.draw(true);
	}
	
	function initData(){
		$.laydate.setRange($.date.dateToStr(yesterday,"yyyy-MM-dd HH:mm"),$.date.dateToStr(today,"yyyy-MM-dd HH:mm"),"#rkDate");
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);