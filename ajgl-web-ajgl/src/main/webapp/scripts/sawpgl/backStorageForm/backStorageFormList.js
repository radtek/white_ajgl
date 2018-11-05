(function($){
	"use strict";
	
	var bsfTable = null;

	$(document).ready(function() {	
		initTypeSelect();
		initStartAndEndTime();
		$("#operator").iCheck('check');
		$("#operator").attr("operator",currentUserId);
		initTable();
		/**
		 * 新增按钮点击事件
		 */
		$(document).on("click","#newBSF",function(){
			window.location.href = $.util.fmtUrl(context + "/backStorageForm/showNewBackStorageFormPage.action") ;
		});
		
		/**
		 * 重置按钮点击事件
		 */
		$(document).on("click","#reset",function(){
			resetSearchCondition();
			bsfTable.draw(true);
		});
		
		/**
		 * 查询按钮点击事件
		 */
		$(document).on("click",".search",function(){
			bsfTable.draw(true);
		});
		
		/**
		 * 刷新按钮事件
		 */
		$(document).on("click","#refBtn",function(){
			window.location.href=window.location.href;
		});

		/**
		 * 删除按钮点击事件
		 */
		$(document).on("click","#deleteBSF",function(){
			var arr = $.icheck.getChecked("bsf");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			
			var bsfIdList = new Array();
			$.each(arr,function(a,val){
				var bsfId = $(arr[a]).val();
				bsfIdList.push(bsfId);
			});
			window.top.$.layerAlert.confirm({
				msg:"删除后不可恢复，确定要删除吗？",
				title:"删除",	  //弹出框标题
				width:'300px',
				hight:'200px',
				shade: [0.5,'black'],  //遮罩
				icon:0,  //弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
				shift:1,  //弹出时的动画效果  有0-6种
				yes:function(index, layero){
					//点击确定按钮后执行
					deleteBsfByIds(bsfIdList);
				}
			});
		});
		
		/**
		 * 是否显示我的再入库单iCheck选中事件
		 */
		$(document).on("ifChecked","#operator",function(){
			$(this).attr("operator",currentUserId);
		});
		
		/**
		 * 是否显示我的再入库单iCheck取消选中事件
		 */
		$(document).on("ifUnchecked","#operator",function(){
			$(this).attr("operator",null);
		});
		
		/**
		 * 涉案物品二维码点击事件
		 */
		$(document).on("click",".bsfCodeImg",function(){
			var articleCode = $(this).attr("code");			
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
//			var code = $(this).attr("code");
//			var tempDiv = $("<div/>");
//			$(tempDiv).qrcode({
//			    "render": 'image',
//			    "size": 100,
//			    "color": "#3a3",
//			    "background": "white",
//			    "text": code
//			});
//			$.layerAlert.img($(tempDiv).find("img").attr("src"),130,10);
		});
		
		/**
		 * 再入库单单号点击事件
		 */
		$(document).on("click",".bsfCode",function(){
			var bsfId = $(this).attr("bsfId");
			window.location.href =  $.util.fmtUrl(context + "/backStorageForm/showLookBackStorageFormPage.action?bsfId=" + bsfId) ;
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
			var d=new Object();
			d["backStorageFormServiceBean.operator"] = "";
			d["startTime"] = $.laydate.getTime("#bsfDateRange", "start");
			d["endTime"] = $.laydate.getTime("#bsfDateRange", "end");
			if($.common.isFullConditionSearch()){
				d["backStorageFormServiceBean.code"] = $("#code").val();
				d["backStorageFormServiceBean.outStorageFormCode"] = $("#outStorageFormCode").val();
				d["backStorageFormServiceBean.caseCode"] = $("#caseCode").val();
				d["backStorageFormServiceBean.remark"] = $("#remark").val();
				d["backStorageFormServiceBean.type"] = $.util.isBlank($.select2.val("#type"))?"":$.select2.val("#type");
			}else{
				var codeText = $("#codeText").val();
				d["backStorageFormServiceBean.code"] = codeText == "再入库单编号模糊查询" ? "" : codeText;
			}
			
			var form = $.util.getHiddenForm(context +'/backStorageForm/exportExcel.action', d);
			$.util.subForm(form);
		});	
	});
	
	/**
	 * 初始化出库类型select
	 */
	function initTypeSelect(){
		var optionArray = new Array();
		optionArray.push({id:$.common.Constant.CKLX_JC(),name:"借出"});
		optionArray.push({id:$.common.Constant.CKLX_QT(),name:"其他"});
		$.select2.addByList("#type", optionArray,"id","name",true,true);
	}
	
	/**
	 * 设置默认开始时间和结束时间
	 */
	function initStartAndEndTime(){
		var dateTime = new Date().getTime()+1000*60;
		var startTimeStr = $.date.dateToStr(new Date(dateTime-1000*60*60*24), "yyyy-MM-dd HH:mm");
		var endTimeStr = $.date.dateToStr(new Date(dateTime), "yyyy-MM-dd HH:mm")
		$.laydate.setRange(startTimeStr, endTimeStr, "#bsfDateRange");
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#speedinessSearchText").val("再入库单编号模糊查询");
		$("#code").val("");
		$("#outStorageFormCode").val("");
		$.laydate.reset("#bsfDateRange");
		$("#caseCode").val("");
		$("#remark").val("");
		$("#operator").val("");
		$.select2.selectByOrder("#type",0,true);
		$("#operator").iCheck('check');
		initStartAndEndTime();
	}
	
	/**
	 * 删除再入库单
	 * @param saIdList 再入库单id集合
	 */
	function deleteBsfByIds(bsfIdList){
		var gData = new Object();
		$.util.objToStrutsFormData(bsfIdList, "backStorageFormIdList", gData);
		
		$.ajax({
			url:context +'/backStorageForm/deleteBackStorageFormByIds.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				window.top.$.layerAlert.alert({msg:"删除成功！"}) ;
				bsfTable.draw(false);
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/backStorageForm/findBackStorageFormByCondition.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "30px",
     	    	"title": "选择",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  var a = '<input type="checkbox" name="bsf" class="icheckbox" value="'+data+'"/>' ;
     	    			  return a;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "再入库单编号",
				"data" : "code",
				"render" : function(data, type, full, meta) {
					var td = '<a class="detail bsfCode" bsfId="' + full.id + '" href="###">' + data + '</a>&nbsp;&nbsp;';
 	    				td += '<a href="###" class="bsfCodeImg" code="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
					return td;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "再入库日期",
				"data" : "updatedTime",
				"render" : function(data, type, full, meta) {
					return $.date.timeToStr(data, "yyyy-MM-dd HH:mm");
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "出库单编号",
				"data" : "outStorageFormCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "100px",
				"title" : "案件编号",
				"data" : "caseCode",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "100px",
				"title" : "案件名称",
				"data" : "caseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "100px",
				"title" : "办案民警",
				"data" : "polices",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 7,
				"width" : "100px",
				"title" : "出库类型",
				"data" : "typeName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 8,
				"width" : "100px",
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
			d["backStorageFormServiceBean.operator"] = "";
			var end=$.laydate.getTime("#bsfDateRange", "end");
			d["endTime"] = $.date.endRangeByTime(end,"yyyy-MM-dd HH:mm");
			d["startTime"] = $.laydate.getTime("#bsfDateRange", "start");
			if($.common.isFullConditionSearch()){
				d["backStorageFormServiceBean.code"] = $("#code").val();
				d["backStorageFormServiceBean.outStorageFormCode"] = $("#outStorageFormCode").val();
				d["backStorageFormServiceBean.caseCode"] = $("#caseCode").val();
				d["backStorageFormServiceBean.remark"] = $("#remark").val();
				d["backStorageFormServiceBean.type"] = $.util.isBlank($.select2.val("#type"))?"":$.select2.val("#type");
			}else{
				var codeText = $("#codeText").val();
				d["backStorageFormServiceBean.code"] = codeText == "再入库单编号模糊查询" ? "" : codeText;
			}
		};
		tb.paramsResp = function(json) {
			var backStorageFormServiceBeanList = json.backStorageFormServiceBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = backStorageFormServiceBeanList;
		
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		bsfTable = $("#bsfTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		backStorageFormList : {
			
		}
	});	
})(jQuery);