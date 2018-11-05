(function($) {
	"use strict";
	
	//办案区使用单id
	var harId = "";
	var handlingPolice = "";
	var dataLst = [];
	var dataChangeMap = {};
	var tableSettings = {};
	var temporary = null;
	var goodsTable = null;
	var temporaryTable = null;
	var detainTable = null;
	var keepTable = null;
	var returnObjTable = null;
	var printDataMap = null;
	$(document).ready(function() {
		$("#confirm").attr("disabled", "disabled");
		jumpOff();
		harId = $("#harId").val();
		$.common.setSelectedTabsById("goodsReturn");
		//将状态改为true 修改页面
		$.common.setIsUpdate(true);
		$.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
		$(document).on("click",".img",function(){
			$.layerAlert.img($(this).attr("src"));
		})
		initData();
	});
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function() {
		window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsReturnRecord.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 返回
	 */
	$(document).on("click","#return",function() {
		window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsReturnRecord.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 选中警员
	 */
	$(document).on("ifChecked",".policeSelect",function() {
		handlingPolice = $(this).val();
	});
	
	/**
	 * 取消选中
	 */
	$(document).on("ifUnchecked",".allCheck",function() {
		var str = $(this).attr("subCheck");
		$("." + str).iCheck('uncheck');
	});
	
	/**
	 * 全选
	 */
	$(document).on("ifChecked",".allCheck",function() {
		var str = $(this).attr("subCheck");
		$("." + str).iCheck('check');  
	});
	
	/**
	 * 开箱按钮事件
	 */
	$(document).on("click",".open",function(event){
	   var code=$(this).attr("boxId");
	   $.ajax({
			url: context + "/locker/openLocker.action",
			type:'post',
			dataType:'json',
			data:{
				code:code
			},
			success:function(successData){
				
			},
			error:function(errorData){
				
			}
		});
	});
	/**
	 * 提交
	 */
	$(document).on("click","#confirm",function() {
		if($.util.isBlank(handlingPolice)){
			window.top.$.layerAlert.alert({msg:"请选择操作警员！",title:"提示"});
			return;
		}
		if((!$.util.exist(dataChangeMap["goods"]) || dataChangeMap["goods"].length == 0)
				&& (!$.util.exist(dataChangeMap["temporary"]) || dataChangeMap["temporary"].length == 0)
				&& (!$.util.exist(dataChangeMap["detain"]) || dataChangeMap["detain"].length == 0)
				&& (!$.util.exist(dataChangeMap["keep"]) || dataChangeMap["keep"].length == 0)
				&& (!$.util.exist(dataChangeMap["returnObj"]) || dataChangeMap["returnObj"].length == 0)){
			window.top.$.layerAlert.alert({msg:"未对物品进行处理！",title:"提示"});
			return;
		}
		$("#confirm").attr("disabled", "disabled");
		var dataMap = {};
		dataMap["handlingAreaReceiptId"] = harId;
		dataMap["handlingPolice"] = handlingPolice;
		for(var i in dataChangeMap["goods"]){
			dataMap["returnMap.goods[" + i + "]"] = dataChangeMap["goods"][i];
		}
		for(var i in dataChangeMap["temporary"]){
			dataMap["returnMap.temporary[" + i + "]"] = dataChangeMap["temporary"][i];
		}
		for(var i in dataChangeMap["detain"]){
			dataMap["returnMap.detain[" + i + "]"] = dataChangeMap["detain"][i];
		}
		for(var i in dataChangeMap["keep"]){
			dataMap["returnMap.keep[" + i + "]"] = dataChangeMap["keep"][i];
		}
		for(var i in dataChangeMap["returnObj"]){
			dataMap["returnMap.returnObj[" + i + "]"] = dataChangeMap["returnObj"][i];
		}

		printDataMap = dataMap;
		
		$.ajax({
			url:context +'/carryGoodsInfo/saveCarryGoodsManagement.action',
			type:'post',
			data:dataMap,
			dataType:'json',
			customizedOpt:{
				btn:{
					btn:"#confirm"
				}
			},
			success:function(successData){
				//carryGoodsInfo/addReturnRecord.action
				window.top.$.layerAlert.alert({msg:"保存成功！",icon:"1",end : function(){
					$(".disabledBtn").attr("disabled","disabled");
					$("#policeP").hide();
					$(".print").show();
					$("#return").show();
					$("#cancel").hide();
					$("#confirm").hide();
					$.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
					$.common.setIsUpdate(false);
					$("#modifyPeopleName").text(successData.modifyPerson);
					$("#updateTime").text(successData.modifyTime);
					jumpOn();
				}});
			}
		});
	});
	
	
	/**
	 * 打印临时取出
	 */
	$(document).on("click","#printls",function() {
		if(!$.util.exist(dataChangeMap["temporary"]) || dataChangeMap["temporary"].length == 0){
			window.top.$.layerAlert.alert({msg:"没有临时取出的物品！"}) ;
			return false;
		}
//		TODO 打印临时取出单 Lst中数据是物品Id数组
//		var form = $.util.getHiddenForm(context +'/harPrint/printTemporaryLstation.action', printDataMap);
//		$.util.subForm(form);
		var form = $.util.getHiddenForm(context +'/harPrint/printTemporaryLstation.action?handlingAreaReceiptId='+harId, null);
		$.util.subForm(form);
	});
	
	/**
	 * 打印移交暂存/扣押
	 */
	$(document).on("click","#printyj",function() {
		if((!$.util.exist(dataChangeMap["detain"]) || dataChangeMap["detain"].length == 0)
				&& (!$.util.exist(dataChangeMap["keep"]) || dataChangeMap["keep"].length == 0)
				&& (!$.util.exist(dataChangeMap["returnObj"]) || dataChangeMap["returnObj"].length == 0)){
			window.top.$.layerAlert.alert({msg:"没有移交或返还的物品！"}) ;
			return false;
		}
//		var form = $.util.getHiddenForm(context +'/harPrint/printCarryGoodsReturnRecord.action', printDataMap);
//		$.util.subForm(form);
		var form = $.util.getHiddenForm(context +'/harPrint/printCarryGoodsReturnRecord.action?handlingAreaReceiptId='+harId, null);
		$.util.subForm(form);
	});
	
	/**
	 * 临时取出
	 */
	$(document).on("click","#temporaryBtn",function(){
		var arr = $.icheck.getChecked("goodsTableCheck");
		if(arr.length == 0){
			window.top.$.layerAlert.alert({msg:"请先选择物品！"}) ;
			return false;
		}
		for(var i in arr){
			//记录id
			var id = $(arr[i]).val();
			//删除tr
			$(arr[i]).parents("tr").remove();
			//查询数据生成新tr
			var storage = null;
			for(var j in dataLst){
				if(dataLst[j].id == id){
					storage = dataLst[j];
					break;
				}
			}
			var location = "";
			if($.util.isBlank(storage.strongboxName)){
				location = storage.position;
			}else{
				location = storage.strongboxName;
			}
			$("#temporaryTable tbody tr .dataTables_empty").parent().remove();
			$.dataTable.addRow("#temporaryTable","after", tableSettings["temporaryTable"], [
				'<input type="checkbox" name="temporaryTableCheck" class="icheckbox temporaryTableCheck" value="' + storage.id + '"/>' ,
				storage.goodsName,
				storage.features,
				storage.quantity,
				storage.unitOfMeasurement,
				storage.num,
				location,
//				"<img class='img' src='data:image/png;base64," + storage.photoBase64 + "' height='40'>",
				"<img class='img' fileId='" + storage.photoId + "' height='40'>",
				$.util.isBlank(storage.remark)?"":storage.remark
		     ]);
			//记录变化
			if(!$.util.exist(dataChangeMap["goods"])){
				if(!$.util.exist(dataChangeMap["temporary"])){
					dataChangeMap["temporary"] = [];
				}
				dataChangeMap["temporary"].push(id);
			}else{
				var returnFlag = true;
				for(var i in dataChangeMap["goods"]){
					if(dataChangeMap["goods"][i] == id){
						removeByValue(dataChangeMap["goods"], id);
						returnFlag = false;
						break;
					}
				}
				if(returnFlag){
					if(!$.util.exist(dataChangeMap["temporary"])){
						dataChangeMap["temporary"] = [];
					}
					dataChangeMap["temporary"].push(id);
				}
			}
		}
		onloadPhotoDG($('#temporaryTable .img'));
		
	});
	
	/**
	 * 移交扣押
	 */
	$(document).on("click","#detainBtn",function(){
		var arr = $.icheck.getChecked("goodsTableCheck");
		if(arr.length == 0){
			window.top.$.layerAlert.alert({msg:"请先选择物品！"}) ;
			return false;
		}
		for(var i in arr){
			//记录id
			var id = $(arr[i]).val();
			//删除tr
			$(arr[i]).parents("tr").remove();
			//查询数据生成新tr
			var storage = null;
			for(var j in dataLst){
				if(dataLst[j].id == id){
					storage = dataLst[j];
					break;
				}
			}
			var location = "";
			if($.util.isBlank(storage.strongboxName)){
				location = storage.position;
			}else{
				location =  storage.strongboxName;
			}
			$("#detainTable tbody tr .dataTables_empty").parent().remove();
			$.dataTable.addRow("#detainTable","after", tableSettings["detainTable"], [
				'<input type="checkbox" name="detainTableCheck" class="icheckbox detainTableCheck" value="' + storage.id + '"/>' ,
				storage.goodsName,
				storage.features,
				storage.quantity,
				storage.unitOfMeasurement,
				storage.num,
				location,
//				"<img class='img' src='data:image/png;base64," + storage.photoBase64 + "' height='40'>",
				"<img class='img' fileId='" + storage.photoId + "' height='40'>",
				$.util.isBlank(storage.remark)?"":storage.remark
		     ]);
			//记录变化
			if(!$.util.exist(dataChangeMap["detain"])){
				dataChangeMap["detain"] = [];
			}
			dataChangeMap["detain"].push(id);
		}
		onloadPhotoDG($('#detainTable .img'));
	});
	
	/**
	 * 移交暂存
	 */
	$(document).on("click","#keepBtn",function(){
		var arr = $.icheck.getChecked("goodsTableCheck");
		if(arr.length == 0){
			window.top.$.layerAlert.alert({msg:"请先选择物品！"}) ;
			return false;
		}
		for(var i in arr){
			//记录id
			var id = $(arr[i]).val();
			//删除tr
			$(arr[i]).parents("tr").remove();
			//查询数据生成新tr
			var storage = null;
			for(var j in dataLst){
				if(dataLst[j].id == id){
					storage = dataLst[j];
					break;
				}
			}
			var location = "";
			if($.util.isBlank(storage.strongboxName)){
				location = storage.position;
			}else{
				location =  storage.strongboxName;
			}
			$("#keepTable tbody tr .dataTables_empty").parent().remove();
			$.dataTable.addRow("#keepTable","after", tableSettings["keepTable"], [
				'<input type="checkbox" name="keepTableCheck" class="icheckbox keepTableCheck" value="' + storage.id + '"/>' ,
				storage.goodsName,
				storage.features,
				storage.quantity,
				storage.unitOfMeasurement,
				storage.num,
				location,
//				"<img class='img' src='data:image/png;base64," + storage.photoBase64 + "' height='40'>",
				"<img class='img' fileId='" + storage.photoId + "' height='40'>",
				$.util.isBlank(storage.remark)?"":storage.remark
		     ]);
			//记录变化
			if(!$.util.exist(dataChangeMap["keep"])){
				dataChangeMap["keep"] = [];
			}
			dataChangeMap["keep"].push(id);
		}
		onloadPhotoDG($('#keepTable .img'));
	});
	
	/**
	 * 立即返还
	 */
	$(document).on("click","#returnObjBtn",function(){
		var arr = $.icheck.getChecked("goodsTableCheck");
		if(arr.length == 0){
			window.top.$.layerAlert.alert({msg:"请先选择物品！"}) ;
			return false;
		}
		for(var i in arr){
			//记录id
			var id = $(arr[i]).val();
			//删除tr
			$(arr[i]).parents("tr").remove();
			//查询数据生成新tr
			var storage = null;
			for(var j in dataLst){
				if(dataLst[j].id == id){
					storage = dataLst[j];
					break;
				}
			}
			var location = "";
			if($.util.isBlank(storage.strongboxName)){
				location = storage.position;
			}else{
				location =  storage.strongboxName;
			}
			$("#returnObjTable tbody tr .dataTables_empty").parent().remove();
			$.dataTable.addRow("#returnObjTable","after", tableSettings["returnObjTable"], [
				'<input type="checkbox" name="returnObjTableCheck" class="icheckbox returnObjTableCheck" value="' + storage.id + '"/>' ,
				storage.goodsName,
				storage.features,
				storage.quantity,
				storage.unitOfMeasurement,
				storage.num,
				location,
//				"<img class='img' src='data:image/png;base64," + storage.photoBase64 + "' height='40'>",
				"<img class='img' fileId='" + storage.photoId + "' height='40'>",
				$.util.isBlank(storage.remark)?"":storage.remark
		     ]);
			//记录变化
			if(!$.util.exist(dataChangeMap["returnObj"])){
				dataChangeMap["returnObj"] = [];
			}
			dataChangeMap["returnObj"].push(id);
		}
		onloadPhotoDG($('#returnObjTable .img'));
	});
		
	
	/**
	 * 取消移交/返还及临时物品归还
	 */
	$(document).on("click","#temporaryBackBtn,#detainBackBtn,#keepBackBtn,#returnObjBackBtn",function(){
		var arr = [];
		var btnId = $(this).attr("id");
		switch (btnId) {
		case "temporaryBackBtn":
			arr = $.icheck.getChecked("temporaryTableCheck");
			break;
		case "detainBackBtn":
			arr = $.icheck.getChecked("detainTableCheck");
			break;
		case "keepBackBtn":
			arr = $.icheck.getChecked("keepTableCheck");
			break;
		case "returnObjBackBtn":
			arr = $.icheck.getChecked("returnObjTableCheck");
			break;
		}
		if(arr.length == 0){
			window.top.$.layerAlert.alert({msg:"请先选择物品！"}) ;
			return false;
		}
		for(var i in arr){
			//记录id
			var id = $(arr[i]).val();
			//删除tr
			$(arr[i]).parents("tr").remove();
			//查询数据生成新tr
			var storage = null;
			for(var j in dataLst){
				if(dataLst[j].id == id){
					storage = dataLst[j];
					break;
				}
			}
			var location = "";
			if($.util.isBlank(storage.strongboxName)){
				location =  storage.position;
			}else{
				location =  storage.strongboxName;
			}
			$("#goodsTable tbody tr .dataTables_empty").parent().remove();
			$.dataTable.addRow("#goodsTable","after", tableSettings["goodsTable"], [
				'<input type="checkbox" name="goodsTableCheck" class="icheckbox goodsTableCheck" value="' + storage.id + '"/>' ,
				storage.goodsName,
				storage.features,
				storage.quantity,
				storage.unitOfMeasurement,
				storage.num,
				location,
//				"<img class='img' src='data:image/png;base64," + storage.photoBase64 + "' height='40'>",
				"<img class='img' fileId='" + storage.photoId + "' height='40'>",
				$.util.isBlank(storage.remark)?"":storage.remark
		     ]);
			//记录变化
			switch (btnId) {
			case "temporaryBackBtn":
				if(!$.util.exist(dataChangeMap["temporary"])){
					if(!$.util.exist(dataChangeMap["goods"])){
						dataChangeMap["goods"] = [];
					}
					dataChangeMap["goods"].push(id);
				}else{
					var returnFlag = true;
					for(var i in dataChangeMap["temporary"]){
						if(dataChangeMap["temporary"][i] == id){
							removeByValue(dataChangeMap["temporary"], id);
							returnFlag = false;
							break;
						}
					}
					if(returnFlag){
						if(!$.util.exist(dataChangeMap["goods"])){
							dataChangeMap["goods"] = [];
						}
						dataChangeMap["goods"].push(id);
					}
				}
				break;
			case "detainBackBtn":
				if(!$.util.exist(dataChangeMap["detain"])){
					dataChangeMap["detain"] = [];
				}else{
					removeByValue(dataChangeMap["detain"], id);
				}
				break;
			case "keepBackBtn":
				if(!$.util.exist(dataChangeMap["keep"])){
					dataChangeMap["keep"] = [];
				}else{
					removeByValue(dataChangeMap["keep"], id);
				}
				break;
			case "returnObjBackBtn":
				if(!$.util.exist(dataChangeMap["returnObj"])){
					dataChangeMap["returnObj"] = [];
				}else{
					removeByValue(dataChangeMap["returnObj"], id);
				}
				break;
			}
		}
		onloadPhotoDG($('#goodsTable .img'));
	});
	
	function initData(){
		$.ajax({
			url:context +'/carryGoodsInfo/queryCarryGoodsReceipt.action',
			type:'post',
			data:{"handlingAreaReceiptId":harId},
			dataType:'json',
			success:function(successData){
				$("#confirm").attr("disabled", false);
				if(successData.flag == "false"){
					window.top.$.layerAlert.alert({msg:"此使用单未保存随身物品！" ,title:"提示", end:function(){
						window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsInfo.action?&&harId=" + $("#harId").val());
					}});
					return;
				}
				var data = successData.handlingPolice.split("、");
				for(var i in data){
					if($.base64.decode($("#policeName").val())==data[i]){
						$("#policeP").append('<input id="police' + i + '" type="radio" class="icheckradio policeSelect" checked name="police" value="' + data[i] + '"><label for="police' + i + '">&nbsp;' + data[i] + '&nbsp;&nbsp;</label>');
						handlingPolice = data[i];
					}else{
						$("#policeP").append('<input id="police' + i + '" type="radio" class="icheckradio policeSelect" name="police" value="' + data[i] + '"><label for="police' + i + '">&nbsp;' + data[i] + '&nbsp;&nbsp;</label>');
					}
				}
				var carryGoodsInfoBean = successData.carryGoodsInfoBean;
				if(!$.util.exist(carryGoodsInfoBean)){
					$("#modifyPeopleName").text("");
					$("#updateTime").text("");
					initTable(goodsTable, "goodsTable", []);
					initTable(temporaryTable, "temporaryTable", []);
					initTable(detainTable, "detainTable", []);
					initTable(keepTable, "keepTable", []);
					initTable(returnObjTable, "returnObjTable", []);
				}else{
					if($.util.exist(carryGoodsInfoBean.returnModifyPeopleBean) && !$.util.isBlank(carryGoodsInfoBean.returnModifyPeopleBean.name)){
						$("#modifyPeopleName").text(carryGoodsInfoBean.returnModifyPeopleBean.name);
					}
					if(!$.util.isBlank(carryGoodsInfoBean.returnUpdatedTime)){
						$("#updateTime").text(carryGoodsInfoBean.returnUpdatedTime);
					}
					var dataSet = [];
					dataSet[0] = [];
					dataSet[1] = [];
					dataSet[2] = [];
					dataSet[3] = [];
					dataSet[4] = [];
					var boxLst = [];
					dataLst = carryGoodsInfoBean.carryGoodsRecordBeans;
					for(var i in carryGoodsInfoBean.carryGoodsRecordBeans){
						var bean = carryGoodsInfoBean.carryGoodsRecordBeans[i];
						if(!$.util.isBlank(bean.strongboxNum)){
							var flag = true;
							for(var j in boxLst){
								if(boxLst[j].strongboxNum == bean.strongboxNum){
									flag = false;
									break;
								}
							}
							if(flag){
								boxLst.push({
									strongboxNum : bean.strongboxNum,
									strongboxName : bean.strongboxName,
									strongboxNumStr : bean.strongboxNumStr
								})
							}
						}
						if(bean.status == $.common.Constant.WPZT_ZK()){
							dataSet[0].push(bean);
						}
						if(bean.status == $.common.Constant.WPZT_LSQC()){
							dataSet[1].push(bean);
						}
						if(bean.status == $.common.Constant.WPZT_YJKY()){
							dataSet[2].push(bean);
						}
						if(bean.status == $.common.Constant.WPZT_YJZC()){
							dataSet[3].push(bean);
						}
						if(bean.status == $.common.Constant.WPZT_YFH()){
							dataSet[4].push(bean);
						}
					}
					var str = "";
					for(var i in boxLst){
						str += '<li><h2>' + boxLst[i].strongboxName + '</h2><a href="#" class="open"'
						+ 'boxId="' + boxLst[i].strongboxNumStr + '" title="开箱"></a>'
						+ '<span class="state-mark state-busy"></span></li>';
					}
					$("#boxUl").append(str);
					initTable(goodsTable, "goodsTable", dataSet[0]);
					initTable(temporaryTable, "temporaryTable", dataSet[1]);
					temporary = dataSet[1];
					initTable(detainTable, "detainTable", dataSet[2]);
					initTable(keepTable, "keepTable", dataSet[3]);
					initTable(returnObjTable, "returnObjTable", dataSet[4]);
				}
			}
		});
	}
	
	function initTable(tableObj, tableId, dataSet){
		var tb = $.uiSettings.getLocalOTableSettings();
		tb.data = dataSet;
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "5%",
     	    	"title": '<input type="checkbox" name="harCheck" class="icheckbox allCheck" subCheck="' + tableId + 'Check" value=""/>',
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			 if(tableId == "detainTable" || tableId == "keepTable"|| tableId == "returnObjTable"){
     	    				 return "";
     	    			 }else{
     	    				 return '<input type="checkbox" name="' + tableId + 'Check" class="icheckbox ' + tableId + 'Check" value="'+data+'"/>' ;
     	    			 }
     	    	}
			},
			{
				"targets": 1,
     	    	"width": "15%",
     	    	"title": "物品名称",
     	    	"className":"table-checkbox",
     	    	"data": "goodsName" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return data;
     	    	}
			},
			{
				"targets" : 2,
				"width" : "12%",
				"title" : "特征",
				"data" : "features",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "8%",
				"title" : "数量",
				"data" : "quantity",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "10%",
				"title" : "计量单位",
				"data" : "unitOfMeasurement",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "12%",
				"title" : "物品编号",
				"data" : "num",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "10%",
				"title" : "存储位置",
				"data" : "strongboxName",
				"render" : function(data, type, full, meta) {
					if($.util.isBlank(full.position)){
						return data;
					}else{
						return full.position;
					}
				}
			},
			{
				"targets" : 7,
				"width" : "10%",
				"title" : "物品图片",
//				"data" : "photoBase64",
				"data" : "photoId",
				"render" : function(data, type, full, meta) {
//					return "<img class='img' src='data:image/png;base64," + data + "' height='40'>";
					return "<img class='img' fileId="+data+" height='40'>";
				}
			}, 
			{
				"targets" : 8,
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
		//是否分页
		tb.paging = false;
		//每页条数
		tb.lengthMenu = [ 10 ];
		//默认搜索框
		tb.searching = false ;
		//能否改变lengthMenu
		tb.lengthChange = false ;
		//自动TFoot
		tb.autoFooter = false ;
		//自动列宽
		tb.autoWidth = true ;
		tb.drawCallback = function() {
			var arr=$('.img')
			onloadPhotoDG(arr);
		};
		tableSettings[tableId] = tb;
		tableObj = $("#" + tableId).DataTable(tb);
	}
	
	/**
	 * 依次加载图片
	 * @param arr
	 * @returns
	 */
	function onloadPhotoDG(arr){
		if(arr.length>0){
		 var pohtoid=$(arr[0]).attr("fileId");
			$.ajax({
				url:context +'/carryGoodsInfo/findBase64Byid.action',
				type:'post',
				data:{
					photoId:pohtoid
				},
				dataType:'json',
				success:function(successData){
					var attBase64= successData.photoBase64;
					$(arr[0]).attr("src","data:image/png;base64,"+attBase64);
					arr.splice(0,1);//移除第一个元素
					onloadPhotoDG(arr);					
				}
			})
		}
	}
	
	
	function removeByValue(arr, val) {
		for(var i=0; i<arr.length; i++) {
		    if(arr[i] == val) {
		    	arr.splice(i, 1);
		    	break;
		    }
		}
	}
})(jQuery);