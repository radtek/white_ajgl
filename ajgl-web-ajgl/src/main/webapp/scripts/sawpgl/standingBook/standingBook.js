(function($){
	"use strict";
	
	var sbTable = null;
	var selectedStorageIdArray = new Array();// 挑选出来的涉案物品保管位置id数组
	var listId=[];
	$(document).ready(function() {	
		initStorageAreaSelect();
		initTable();
		/**
		 * 查询按钮点击事件
		 */
		$(document).on("click",".search",function(){
			selectedStorageIdArray = new Array();
			sbTable.draw(true);
		});
		
		/**
		 * 重置按钮点击事件
		 */
		$(document).on("click","#reset",function(){
			resetSearchCondition();
		});
		/**
		 * 刷新
		 */
		$(document).on("click","#refBtn",function(){
			window.location.href=window.location.href
		});
		
		/**
		 * 列表iCheck勾选事件
		 */
		$(document).on("ifChecked","input[name='sb']",function(){
			addSelectedStorageId($(this).val());
		});
		
		/**
		 * 列表iCheck取消勾选事件
		 */
		$(document).on("ifUnchecked","input[name='sb']",function(){
			deleteSelectedStorageId($(this).val());
		});
		
		/**
		 * 涉案物品保管区select失去焦点事件
		 */
		$(document).on("change","#storageArea",function(){
//			var storageAreaId = $.select2.val("#storageArea");
//			initLockerSelect(storageAreaId);
			$("#locker").val("");
			$("#locker").attr("arrId","");
			listId=[];
		});
		
		/**
		 * 储物箱点击事件
		 */
		$(document).on("click","#locker",function(){
			var storageAreaId = $.select2.val("#storageArea");//保管区id
            if(storageAreaId!=null&&storageAreaId!=""){
            	alertLockerSelectPage(storageAreaId);
            }
		});
		
		/**
		 * 涉案物品二维码点击事件
		 */
		$(document).on("click",".articleCodeImg",function(){
			var code = $(this).attr("code");
			var tempDiv = $("<div/>");
			$(tempDiv).qrcode({
			    "render": 'image',
			    "size": 100,
			    "color": "#3a3",
			    "background": "white",
			    "text": code
			});
			$.layerAlert.img($(tempDiv).find("img").attr("src"),130,10);
		});
		
		/**
		 * 调整位置按钮点击事件
		 */
		$(document).on("click","#adjustment",function(){
			if(selectedStorageIdArray.length < 1){
				window.top.$.layerAlert.alert({msg:"未勾选任何在库物品，无法调整！"}) ;
				return ;
			}
			var str = "";
			for(var i in selectedStorageIdArray){
				str += selectedStorageIdArray[i] + ",";
			}
			var storageIdArray = str.substr(0, str.length-1);
			window.location.href =$.util.fmtUrl(context + "/adjustmentStorageForm/showNewAdjustmentStorageFormPage.action?storageIdArray=" + storageIdArray);
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
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
			var d=new Object();
			if($.common.isFullConditionSearch()){
            	d["nameAndFeature"] = $("#nameOrFeature").val();
            	d["caseCode"] = $("#code").val();
            	d["suspectNameOrNumber"] = $("#nameOrCardId").val();
            	var optionArray = $("#articleLocker").select2("val");	
        		if($.util.exist(optionArray)){
        			$.each(optionArray,function(o,option){
        				d["lockerIdList[" + o + "]"] = option;
        			});
        		}
            	var arr = $.icheck.getChecked("goodsNum");
            	d["zero"] = arr.length == 1 ? true : false;
            	var startTime = $.laydate.getTime("#sbDateRange", "start");
            	var endTime = $.laydate.getTime("#sbDateRange", "end");
            	d["startTime"] = startTime;
            	d["endTime"] = endTime;
			}else{
				var nameAndFeature = $("#speedinessSearchText").val();
				d["nameAndFeature"] = nameAndFeature == "物品名称及特征模糊查询" ? "" : nameAndFeature;
			}
			
			var form = $.util.getHiddenForm(context +'/standingBook/exportExcel.action', d);
			$.util.subForm(form);
		});	
		
	});
	
	/**
	 * 弹出储物箱编号选择页面
	 */
	function alertLockerSelectPage(storageAreaId){
		window.top.$.layerAlert.dialog({
			content : context + '/lockerStatistics/findLockerByAreaId.action',
			pageLoading : true,
			title:"查询储物箱编号",
			width : "850px",
			height : "500px",
			btn:["确定","取消"],
			callBacks:{
				btn1:function(index, layero){					
					var cm = window.top.frames["layui-layer-iframe"+index].$.selectLockerAlert ;
				    listId= cm.getArrId();
				    var listCode= cm.getArrCode();
				    var str="";
				    var strId="";
				    if(listCode!=null){
				    	for(var i=0;i<listCode.length;i++){
							str+=listCode[i]+",";
							strId+=listId[i]+",";
						}				    	
				    	str=str.substring(0,str.length-1);
				    	strId=strId.substring(0,strId.length-1);
				    }
				    $('#locker').val(str);
				    $('#locker').attr("arrId",strId);
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				},
				btn2:function(index, layero){
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				
			},
			initData:{
				storageAreaId:storageAreaId,
				listId:$('#locker').attr("arrId"),
				listCode:$('#locker').val()
			},
			end:function(){
				
			}
		});
	}
	
	
	/**
	 * 初始化涉案物品保管区select选项
	 */
	function initStorageAreaSelect(){
		$.ajax({
			url: context + '/standingBook/findAllStorageArea.action',
			type:"POST",	
			dataType:"json",
			data:{status:null},
			success:function(data){
				$.select2.addByList("#storageArea", data.storageAreaBeanList, "id", "name" ,true ,true);//设置下拉框选项
			}
		});
	}
	
	/**
	 * 初始化可用储物箱select选项
	 * @param storageAreaId 涉案物品保管区id
	 */
	function initLockerSelect(storageAreaId){
		$("#articleLocker").html("");
		$.ajax({
			url: context + '/standingBook/findAllLockerByStorageAreaId.action',
			type:"POST",	
			dataType:"json",
			data:{storageAreaId : storageAreaId},
			success:function(data){
				if(!$.util.isBlank($.select2.val("#storageArea"))){
					$.select2.addByList("#articleLocker", data.lockerList, "id", "location" ,true ,true);//设置下拉框选项
				}
			}
		});
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#speedinessSearchText").val("物品名称及特征模糊查询");
		$("#code").val("");
		$("#nameOrFeature").val("");
		$.laydate.reset("#sbDateRange");
		$("#nameOrCardId").val("");
		$.select2.selectByOrder("#storageArea",0,true);
//		$.select2.selectByOrder("#articleLocker",0,true);
		$("#locker").val("");
		$("#locker").attr("arrId","");
		listId=[];
		$("#goodsNum").iCheck('uncheck');
		$("#disposePerson").val("");
		sbTable.draw(true);
	}
	
	/**
	 * 向挑选出来的涉案物品保管位置id数组添加勾选的id
	 * @param storageId 涉案物品保管位置id
	 */
	function addSelectedStorageId(storageId){
		var index = $.inArray(storageId,selectedStorageIdArray);
		if(index == -1){
			selectedStorageIdArray.push(storageId);
		}
	}
	
	/**
	 * 从挑选出来的涉案物品保管位置id数组中删除取消勾选的id
	 * @param storageId 涉案物品保管位置id
	 */
	function deleteSelectedStorageId(storageId){
		var index = $.inArray(storageId,selectedStorageIdArray);
		if(index == -1){
			return ;
		}
		selectedStorageIdArray.splice(index,1);
	}
	
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/standingBook/findInvolvedArticleListByCondition.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "50px",
     	    	"title": "选择",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    		var a = "";
	    			if(full.existingNumber != 0){
	    				a = '<input type="checkbox" name="sb" class="icheckbox" value="'+data+'"/>' ;
	    				if($.inArray(data,selectedStorageIdArray) != -1){
		    				a = '<input type="checkbox" name="sb" class="icheckbox" value="'+data+'" checked/>' ;
		    			}
	    			}
	    			return a;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "物品名称",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.name;
					}
					return td;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "特征",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.feature;
					}
					return td;
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "物品编号",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.code;
						td += '&nbsp;&nbsp;<a href="###" class="articleCodeImg" code="' + data.code + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
					}
					return td;
				}
			},
			{
				"targets" : 4,
				"width" : "100px",
				"title" : "V3文书中物品数量",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.detentionNumber;
					}
					return td;
				}
			},
			{
				"targets" : 5,
				"width" : "100px",
				"title" : "物品性质",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.typeName;
					}
					return td;
				}
			},
			{
				"targets" : 6,
				"width" : "100px",
				"title" : "在库数量",
				"data" : "existingNumber",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(full.article)){
						td = data;
					}
					return td;
				}
			},
			{
				"targets" : 7,
				"width" : "100px",
				"title" : "计量单位",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.measurementUnit;
					}
					return td;
				}
			},
			{
				"targets" : 8,
				"width" : "100px",
				"title" : "物证保管区/<br/>储物箱",
				"data" : "locker",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data) && $.util.exist(data.area)){
						td = data.area.name + "<br>" + data.location;
					}
					return td;
				}
			},
			{
				"targets" : 9,
				"width" : "100px",
				"title" : "入库时间/<br/>再入库时间",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = $.date.timeToStr(data.inStorageTime, "yyyy-MM-dd HH:mm");
					}
					if(!$.util.isBlank(data.backStorageTime)){
						td += "<br>" + $.date.timeToStr(data.backStorageTime, "yyyy-MM-dd HH:mm");
					}
					return td;
				}
			},
			{
				"targets" : 10,
				"width" : "100px",
				"title" : "对应案件编号/对应案件名称",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.caseCode + "</br>" + data.caseName;
					}
					return td;
				}
			},
			{
				"targets" : 11,
				"width" : "100px",
				"title" : "办案民警",
				"data" : "policeName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 12,
				"width" : "100px",
				"title" : "对应犯罪嫌疑人/物品持有人",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = data.suspectName + "<br>" + data.suspectIdentityNumber;
					}
					return td;
				}
			},
			{
				"targets" : 13,
				"width" : "100px",
				"title" : "对应入库单文书",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var td = "";
					if($.util.exist(data)){
						td = '<a class="paper" id="'+data.paperId+'" type="'+data.paperType+'" href="#">'+data.paper+'</a>';
					}
					return td;
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
            	d["nameAndFeature"] = $("#nameOrFeature").val();
            	d["caseCode"] = $("#code").val();
            	d["suspectNameOrNumber"] = $("#nameOrCardId").val();
//            	var optionArray = $("#articleLocker").select2("val");	
//            	if(!$.util.exist(optionArray) || optionArray.length < 1){
//            		optionArray = new Array();
//            		$("#articleLocker").find("option").each(function(o,option){
//            			var lockerId = $(option).val();
//            			if(!$.util.isBlank(lockerId)){
//            				optionArray.push(lockerId);
//            			}
//            		});
//            	}
//        		if($.util.exist(optionArray)){
//        			$.each(optionArray,function(o,option){
//        				d["lockerIdList[" + o + "]"] = option;
//        			});
//        		}
        		if($.util.exist(listId)){
        			$.each(listId,function(o,option){
        				d["lockerIdList[" + o + "]"] = option;
        			});
        		}
            	var arr = $.icheck.getChecked("goodsNum");
            	d["zero"] = arr.length == 1 ? true : false;
            	d["startTime"] = $.laydate.getTime("#sbDateRange", "start");
            	d["endTime"] = $.laydate.getTime("#sbDateRange", "end");
            	d["lockAreaId"] = $.select2.val("#storageArea");
            	d["disposePerson"] = $("#disposePerson").val();
			}else{
				var nameAndFeature = $("#speedinessSearchText").val();
				d["nameAndFeature"] = nameAndFeature == "物品名称及特征模糊查询" ? "" : nameAndFeature;
			}
		};
		tb.paramsResp = function(json) {
			var storageBeanList = json.storageBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = storageBeanList;
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		sbTable = $("#sbTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		standingBook : {
			
		}
	});	
})(jQuery);