(function($) {
	"use strict";
	var carryGoods = new Array;	//随身物品
	var table;
	var st1 ;
	var objNum = 0;
	var newLineIndex = 0;
	//数据源
	var dataSet = null;
	//办案区使用单id
	var harId = "";
	var a;
	var delLst = [];
	var containerLst = {};
	var base64Lst = {};
	$(document).ready(function() {
		$("#confirm").attr("disabled", "disabled");
		jumpOff();
		harId = $("#harId").val();
		$("#goodsAdmin").val(currentUserName);
		$.common.setSelectedTabsById("goodsCheckRecord");
		//将状态改为true 新建或修改页面
		$.common.setIsUpdate(true);
		$.common.showOperateRecord("history");//显示操作记录  ajglUtil.js

		tableDate();
		$(document).on("click",".img",function(){
			$.layerAlert.img($(this).attr("src"));
		})
		setTimeout(allowCamera, 3000);
		
	});
	
	function allowCamera(){
		$("#hdCameraAdd").removeAttr("disabled");
	}
	
	$(document).on("click",".selectBox",function(){
		var initData = {
				"id" : $(this).attr("id"),
				"p$" : $,
				"harId" : harId
			}
		window.top.$.layerAlert.dialog({
			content : context +  '/carryGoodsInfo/selectBox.action',
			pageLoading : true,
			title:"选择储物箱",
			width : "1000px",
			height : "650px",
			initData:function(){
				return $.util.exist(initData)?initData:{} ;
			},
			shadeClose : false,
    		success:function(layero, index){
    			
    		},
    		btn:["取消"],
    		callBacks:{
			    btn2:function(index, layero){
			    	window.top.layer.close(index);
			    }
			}
		});
	})
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function() {
		window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsInfo.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 名称校验
	 */
	$(document).on("blur",".checkName",function abc(){
		var trs = $("#goodsTable tbody").children();
		var checkNameLst = [];
		for (var i = 0; i <= trs.length-1; i++) {
			var tds = trs.eq(i).children();
			if(tds[0].className == "dataTables_empty"){
				continue;
			}
			var tdOjb = {};
			tdOjb.goodsName = tds.eq(0).find("input").val();	//物品名称
			tdOjb.features = tds.eq(1).find("input").val();		//特征
			if($.util.isBlank(tdOjb.goodsName) || $.util.isBlank(tdOjb.goodsName)){
				continue;
			}
			for (var j = 0; j < checkNameLst.length; j++) {
				if(checkNameLst[j].goodsName == tdOjb.goodsName){
					window.top.$.layerAlert.alert({msg:"第" + (j+1) + "行和第" + (i+1) + "行的物品名称完全相同，请修改物品名称！",title:"提示"});
					return;
				}
				if(checkNameLst[j].features == tdOjb.features){
					window.top.$.layerAlert.alert({msg:"第" + (j+1) + "行和第" + (i+1) + "行的特征完全相同，请修改特征！",title:"提示"});
					return;	
				}
			}
			checkNameLst.push(tdOjb);
		}
	});
	
	/**
	 * 确认
	 */
	$(document).on("click","#confirm",function() {
		$("#confirm").attr("disabled", "disabled");
		var demo = $.validform.getValidFormObjById("goodsTable") ;
		if(! $.validform.check(demo)){
			$("#confirm").attr("disabled", false);
			return;
		}
		var trs = $("#goodsTable tbody").children();
		var checkNameLst = [];
		for (var i = 0; i <= trs.length-1; i++) {
			var tds = trs.eq(i).children();
			if(tds[0].className == "dataTables_empty"){
				window.top.$.layerAlert.alert({msg:"未维护物品，不能保存！",title:"提示"});
				$("#confirm").attr("disabled", false);
				return;
			}
			var tdOjb = {};
			tdOjb.goodsName = tds.eq(0).find("input").val();	//物品名称
			tdOjb.features = tds.eq(1).find("input").val();		//特征
			for (var j = 0; j < checkNameLst.length; j++) {
				if(checkNameLst[j].goodsName == tdOjb.goodsName){
					window.top.$.layerAlert.alert({msg:"第" + (j+1) + "行和第" + (i+1) + "行的物品名称完全相同，请修改物品名称！",title:"提示"});
					$("#confirm").attr("disabled", false);
					return;
				}
				if(checkNameLst[j].features == tdOjb.features){
					window.top.$.layerAlert.alert({msg:"第" + (j+1) + "行和第" + (i+1) + "行的特征完全相同，请修改特征！",title:"提示"});
					$("#confirm").attr("disabled", false);
					return;
				}
			}
			checkNameLst.push(tdOjb);
		}
		var emptyFlag = true;
		for (var key in containerLst) {
			emptyFlag = false;
			if($("#"+key).css("display") == "block"){
				$.plupload.start(key);
			}else{
				$("#"+key).parent().find(".delAtt").click();
				uploadNext(key, []);
			}
			break;
        }
		if(emptyFlag){
			save();
		}
	});
	
	//循环调用所有的上传控件
	function uploadNext(container, finishedFiles){
		var i = 0;
		//先把上一次传的文件id存到containerLst中对应的对象里
		for (var key in finishedFiles) {
			containerLst[container].push(key);
        }
		//查找该上传的控件
		var lastContainer = false;
		for (var key in containerLst) {
			if(key == container){
				//如果是，改flag为true，然后进入下一循环节
				lastContainer = true;
				continue;
			}
			//如果flag为true，则该上传此控件了，执行上传并修改flag为false，退出循环
			if(lastContainer){
				$.plupload.start(key);
				lastContainer = false;
				break;
			}
        }
		//如果flag在退出循环后依然为true，则说明最后一个控件也上传完毕，调用save保存
		if(lastContainer){
			save();
		}
	}
	
	
	/**
	 * 获取所有base64字符串和行号存到List里
	 * @returns
	 */
	function save(){
		var trs = $("#goodsTable tbody").children();
		var i = 0;
		var base64Num = 0;
		for (; i <= trs.length-1; i++) {
			var tds = trs.eq(i).children();
			if(tds[0].className == "dataTables_empty"){
				continue;
			}
			if("IMG" == tds.eq(7).children()[0].tagName){
				var str = $(tds.eq(7).children()[0]).attr("src");
				base64Lst[$(tds.eq(7).children()[0]).attr("id")] = {};
				base64Lst[$(tds.eq(7).children()[0]).attr("id")].photoName = $(tds.eq(0).children()[0]).val();
				base64Lst[$(tds.eq(7).children()[0]).attr("id")].photo = str.substr(22, str.length-22);
				base64Num++;
			}
		}
		if(base64Num == 0){
			saveData();
			return;
		}
		for (var key in base64Lst) {
			saveBase64Img(key);
			break;
        }
	}
	function saveBase64Img(key){
		$.ajax({
			url:context + "/carryGoodsInfo/saveBase64Photo.action",
			type:'post',
			customizedOpt:{
				ajaxLoading:true
			},
			data:{
				photoName:base64Lst[key].photoName,
				photo:base64Lst[key].photo
			},
			dataType:'json',
			success:function(successData){
				$("#" + key).attr("imgId", successData.photo);
				var nextFlag = false;
				for (var tempKey in base64Lst) {
					if(nextFlag){
						saveBase64Img(tempKey);
						return;
					}
					if(tempKey == key){
						nextFlag = true;
					}
		        }
				if(nextFlag){
					saveData();
				}
			}
		});
	}
	
	function saveData(){

		var trs = $("#goodsTable tbody").children();
		var i = 0;
		for (; i <= trs.length-1; i++) {
			var tds = trs.eq(i).children();
			if(tds[0].className == "dataTables_empty"){
				continue;
			}
			var a = tds.find("input").val();
			var tdOjb = {};
				tdOjb.id = $(tds.eq(0).children()[1]).val();	//物品id
				tdOjb.goodsName = $(tds.eq(0).children()[0]).val();	//物品名称
				tdOjb.features = tds.eq(1).find("input").val();		//特征
				tdOjb.quantity = tds.eq(2).find("input").val();		//数量
				tdOjb.unitOfMeasurement = tds.eq(3).find("input").val();	//计量单位
				tdOjb.status = tds.eq(4).find("input").val();				//状态
				tdOjb.num = tds.eq(5).text();			//编号
				if("INPUT" == tds.eq(6).children()[0].tagName){
					tdOjb.position = $(tds.eq(6).children()[0]).val();//储存位置
				}else{
					tdOjb.strongboxId = $($(tds.eq(6).children()[0]).children()[0]).text(); //箱子名
					tdOjb.strongboxNum = $(tds.eq(6).children()[1]).val();//箱子号
				}
				//物品图片
				if("IMG" == tds.eq(7).children()[0].tagName){
					tdOjb.photoBase64 = $(tds.eq(7).children()[0]).attr("imgId");
				}else{
					var containerId = tds.eq(7).children(".uploadDiv").attr("id");
					if($.util.exist(containerLst)){
						for (var key in containerLst) {
							if(key == containerId){
								tdOjb.photoId = containerLst[key][0];
								break;
							}
				        }
					}
				}
				tdOjb.remark = tds.eq(9).children().text();			//备注
				carryGoods.push(tdOjb);
		}
		var dataMap = new Object();
		$.each(carryGoods, function(i, itm){
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].id"] = itm.id;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].goodsName"] = itm.goodsName;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].features"] = itm.features;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].quantity"] = itm.quantity;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].unitOfMeasurement"] = itm.unitOfMeasurement;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].status"] = itm.status;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].num"] = itm.num;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].remark"] = itm.remark;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].position"] = itm.position;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].strongboxNum"] = itm.strongboxNum;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].photoId"] = itm.photoId;
			dataMap["carryGoodsInfoBean.carryGoodsRecordBeans[" + i +"].photoBase64"] = itm.photoBase64;
    	});
		dataMap["carryGoodsInfoBean.id"] = $("#goodsId").val();
		dataMap["carryGoodsInfoBean.handlingAreaReceiptId"] = harId;
		var url ="";
		if($("#goodsId").val()!=""){
			$.util.objToStrutsFormData(delLst, "delLst", dataMap);
			url = "/carryGoodsInfo/modifyCreateReceipt.action";
		}else{
			url = "/carryGoodsInfo/addCarryGoodsInfo.action";
		}
		$.ajax({
			url:context + url,
			type:'post',
			data:dataMap,
			dataType:'json',
			customizedOpt:{
				btn:{	    			
					btn:"#confirm"
				}
			},
			success:function(successData){
				window.top.$.layerAlert.alert({msg:"保存成功！",icon:"1",end : function(){
					 $(".disabled").attr("disabled","disabled");
					 $(".container-upload-btn").hide();
					 $(".moxie-shim").remove();
					 $("#print").show();
					 $("#return").show();
					 $("#cancel").hide();
					 $("#confirm").hide();
					 $("#addRow").hide();
					 $("#hdCameraAdd").hide();
					 $("#getBox").hide();
					 $(".deleteRow").hide();
					 $(".delAtt").hide();
					 $.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
					 $.common.setIsUpdate(false);
					 $("#modifyPeopleName").text(successData.modifyPerson);
					 $("#updateTime").text(successData.modifyTime);
					 jumpOn();
				}});
			}
		});
	
	}
	/**
	 * 打印
	 */
	$(document).on("click","#print",function() {
		 var data = {
				 id : harId,
		 }
		 
		 var form = $.util.getHiddenForm(context +'/harPrint/printCarryGoodsCheckRecord.action', data);
		 $.util.subForm(form);
	});
	
	/**
	 * 返回
	 */
	$(document).on("click","#return",function() {
		window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsInfo.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 获取箱子
	 */
	$(document).on("click","#getBox",function() {
		$.ajax({
			url:context + "/carryGoodsInfo/findAnEmptyBox.action",
			type:'post',
			dataType:'json',
			success:function(successData){
				var str = successData.boxName;
				var boxCode = successData.boxCode;
				var boxId = successData.boxId;
				var trs = $("#goodsTable tbody").children();
				var i = 0;
				var hasOpen = false;
				for (i; i <= trs.length-1; i++) {
					var tds = trs.eq(i).children();
					if(tds[0].className == "dataTables_empty"){
						continue;
					}
					if("DIV" == tds.eq(6).children()[0].tagName && $.util.isBlank($($(tds.eq(6).children()[0]).children()[0]).val())){
						$($(tds.eq(6).children()[0]).children()[0]).val(str);
						$(tds.eq(6).children()[1]).val(boxId);
						if(!hasOpen){
							openLocker(boxCode);
							hasOpen = true;
						}
					};
				}
			}
		});
	});
	/**
	 * 手动增行
	 */
	$(document).on("click","#addRow",function() {
		$(".dataTables_empty").parent().remove();
		objNum++;
		//添加行
		$.dataTable.addRow("#goodsTable","after", st1, [
		    '<input type="text" class="checkName form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">',
	     	'<input type="text" class="checkName form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">',
	  		'<input type="text" class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/" errormsg="请输入标准数字(小数点前不超过10位,小数点后不超过2位)">',
	 		'<input type="text" class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">',
	 		'<span>'+"在库"+'</span><input type="hidden" value="0000000010001">',
	 		pad(objNum,4),
	 		'<input type="text" class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80"><input type="hidden" value="">',
	 		'<div id="newContainer' + newLineIndex + '" class="uploadDiv upload-control"></div>',
	 		'<button class="deleteRow btn btn-default btn-xs"><span class="icon-trash"></span></button>',
	 		''
	     ]) ;
		containerLst["newContainer" + newLineIndex] = [];
		initPlupload("newContainer" + newLineIndex);
		newLineIndex++;
	});
	
	/**
	 * 高拍仪增行
	 */
	$(document).on("click","#hdCameraAdd",function() {
		$(".dataTables_empty").parent().remove();
		objNum++;
		//添加行
		$.dataTable.addRow("#goodsTable","after", st1, [
		    '<input type="text" class="checkName form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">',
	     	'<input type="text" class="checkName form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">',
	  		'<input type="text" class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="/^[1-9][0-9]{0,9}(\\.[0-9]{0,9}[1-9])?$/" errormsg="请输入数字">',
	 		'<input type="text" class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">',
	 		'<span>'+"在库"+'</span><input type="hidden" value="0000000010001">',
	 		pad(objNum,4),
	 		'<div id="selectBoxDiv' + newLineIndex + '" class="input-group selectBox"><input readonly class="form-control input-sm valiform-keyup form-val" value="" vf-position="3" datatype="*"><span class="input-group-addon"><span class="icon-search"></span></span></div><input type="hidden" value="">',
	 		
	 		'<img class="img gpyImg" id="gpy' + objNum + '" imgId="" src="data:image/png;base64,' + $.common.getPicture() + '" height="40">',
	 		//使用Chrome时用这个放错错误
	 		//'<img class="img gpyImg" id="gpy' + objNum + '" imgId="" src="data:image/png;base64,123aaa" height="40">',
	 		
	 		'<button class="deleteRow btn btn-default btn-xs"><span class="icon-trash"></span></button>',
	 		''
	     ]) ;
		newLineIndex++;
	});
	
	/**
	 * 删行
	 */
	$(document).on("click",".deleteRow",function() {
		var rowId = $(this).attr("id");
		$.validform.closeAllTips("goodsTable") ;
		var tr = $(this).parents("tr");
		var row=table.row(tr);	//行对象
		var data=row.data();	//行所有数据
		if(data != undefined ){
			$.ajax({
				url:context +'/carryGoodsInfo/queryCarryGoodsById.action',
				type:'post',
				data:{"carryGoodsId":data.carryGoodsReceiptId},
				dataType:'json',
				success:function(successData){
					if(successData.carryGoodsId!= null){
						window.top.$.layerAlert.alert({msg:"物品“"+data.goodsName+"”已存在返还记录不可删除！",title:"提示"});
					}else{
						window.top.$.layerAlert.confirm({
							msg:"确认删除？",
							title:"提示",	  //弹出框标题
							yes:function(index, layero){
								if(!$.util.isBlank(rowId)){
									delLst.push(rowId);
								}
								tr.remove();
							}
						});
					}
				}
			});
		}else{
			window.top.$.layerAlert.confirm({
				msg:"确认删除？",
				title:"提示",	  //弹出框标题
				yes:function(index, layero){
					if(!$.util.isBlank(rowId)){
						delLst.push(rowId);
					}
					tr.remove() ;
				}
			});
		}
	
	});
	
	/**
	 * 查询随身物品记录
	 */
	function tableDate(){
		$.ajax({
			url:context +'/carryGoodsInfo/queryCarryGoodsReceipt.action',
			type:'post',
			data:{"handlingAreaReceiptId":harId},
			dataType:'json',
			success:function(successData){
				$("#confirm").attr("disabled", false);
				if(successData.carryGoodsInfoBean!=null){
					dataSet = successData.carryGoodsInfoBean.carryGoodsRecordBeans;
					if($.util.exist(successData.carryGoodsInfoBean.modifyPeopleBean)){
						$("#modifyPeopleName").text(successData.carryGoodsInfoBean.modifyPeopleBean.name);
					}
					$("#updateTime").text(successData.carryGoodsInfoBean.updatedTime);
					$("#goodsId").val(successData.carryGoodsInfoBean.id);
				}else{
					dataSet = [];
				}
				initTable();
			}
		});
		
	}

	function initTable(){
		//本地table
		st1 = $.uiSettings.getLocalOTableSettings();
		st1.data = dataSet;
		st1.columnDefs = [{
			"targets" : 0,
			"width" : "",
			"title" : "物品名称<span class='red-star'>*</span>",
			"data" : "goodsName",
			"render" : function(data, type, full, meta) {
				return '<input disabled type="text" value="'+data+'" class="checkName form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80"><input type="hidden" value="' + full.id + '">';
			}
		},{
			"targets" : 1,
			"width" : "",
			"title" : "特征<span class='red-star'>*</span>",
			"data" : "features",
			"render" : function(data, type, full, meta) {
				return '<input disabled type="text" value="'+data+'" class="checkName form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">';
			}
		}, {
			"targets" : 2,
			"width" : "",
			"title" : "数量<span class='red-star'>*</span>",
			"data" : "quantity",
			"render" : function(data, type, full, meta) {
				return '<input disabled type="text" value="'+data+'" class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="/^[1-9][0-9]{0,9}(\\.[0-9]{0,9}[1-9])?$/" errormsg="请输入数字">';
			}
		}, {
			"targets" : 3,
			"width" : "",
			"title" : "计量单位<span class='red-star'>*</span>",
			"data" : "unitOfMeasurement",
			"render" : function(data, type, full, meta) {
				return '<input disabled type="text" value="'+data+'" class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80">';
			}
		}, {
			"targets" : 4,
			"width" : "80px",
			"title" : "物品状态",
			"data" : "status",
			"render" : function(data, type, full, meta) {
				return '<span>'+full.statusStr+'</span><input type="hidden" value="'+data+'">';
			}
		}, {
			"targets" : 5,
			"width" : "",
			"title" : "编号",
			"data" : "num",
			"render" : function(data, type, full, meta) {
				if(objNum < parseInt(data, 10)){
					objNum = parseInt(data, 10);
				}
				return data;
			}
		}, {
			"targets" : 6,
			"width" : "100px",
			"title" : "存储位置<span class='red-star'>*</span>",
			"data" : "operateFlag",
			"render" : function(data, type, full, meta) {
				var str = "";
				if(!$.util.isBlank(full.strongboxNum)){
					str = '<div id="selectBoxDivOld' + meta.row + '"class="input-group '
					if(data == 'true'){
						str += 'selectBox';
					}
					str +='"><input readonly class="form-control input-sm" value="' + full.strongboxName + '"><span class="input-group-addon"><span class="icon-search"></span></span></div><input type="hidden" value="' + full.strongboxNum + '">';
				}else{
					str = '<input type="text" ';
					if(data != 'true'){
						str += 'readonly ';
					}
					str += 'class="form-control input-sm valiform-keyup form-val disabled"  vf-position="3" datatype="*1-80" value="' + full.position + '">';
				}
				return str;
			}
		}, {
			"targets" : 7,
			"width" : "",
			"title" : "物品图片",
//			"data" : "photoBase64",
			"data" : "photoId",
			"render" : function(data, type, full, meta) {
//				return "<img class='img' src='data:image/png;base64," + data + "' height='40'>";
				return "<img class='img' fileId="+data+" height='40'>";
			}
		}, {
			"targets" : 8,
			"width" : "",
			"title" : "操作",
			"data" : "operateFlag",
			"render" : function(data, type, full, meta) {
				if(data == "true"){
					return '<button id="' + full.id + '" class="deleteRow btn btn-default btn-xs"><span class="icon-trash"></span></button>';
				}else{
					return '';
				}
			}
		}, {
			"targets" : 9,
			"width" : "",
			"title" : "备注",
			"data" : "remark",
			"render" : function(data, type, full, meta) {
				return data;
			}
		}];
		st1.ordering = false;
		st1.paging = false;
		st1.hideHead = false;
		st1.dom = "";
		st1.searching = false;
		st1.lengthMenu = [ 10 ];
		st1.drawCallback = function() {
			var arr=$('.img')
			onloadPhotoDG(arr);
		};
		table = $("#goodsTable").DataTable(st1);
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
	
	function initPlupload(container){
		var setting = $.plupload.getBasicSettings();
		setting.container = container; //容器id
		setting.url = context + "/activityRecord/uploadFile.action";
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
		};
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
				return Math.random();
			}
		};
		setting.chunk_size = '0';
		setting.filters.max_file_size = '2mb';
		setting.multi_selection = false;
		setting.filters.mime_types = [{title: "图片类型", extensions: "jpg,JPG,jpeg,JPEG,gif,GIF,png,PNG,bmp,BMP"}];
		setting.multi_file_num_callback = function(max_file_size, totalSize){
		};
		setting.callBacks = {
				uploadAllFinish:function(up, finishedFiles){
					uploadNext(up.settings.container, finishedFiles);
				}
		}
		$.plupload.init(setting);
	}
	
	function pad(num, n) {  
	    var len = num.toString().length;  
	    while(len < n) {  
	        num = "0" + num;  
	        len++;  
	    }  
	    return num;  
	}  
	
	function barCode(){
		var options = {
				bgColor:"#ffffff",
				color:"#333333",
				ecLevel:"H",
				fontcolor:"#ff9818",
				fontname:"Ubuntu",
				label:"jQuery.qrcode",
				labelsize:0.09,
				minVersion:6,
				mode:2,
				quiet:1,
				radius:0.5,
				render:"image",
				size:200,
				text:"10000050658947",
			};

		$("#container").empty().qrcode(options);
	}
	function openLocker(code){
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
	}
	jQuery.extend($.common, { 
		setBox:function(id,boxId,boxName,boxCode,index){
			$($("#" + id).children()[0]).val(boxName);
			$($("#" + id).parent().children()[1]).val(boxId);
			window.top.layer.close(index);
			$.validform.closeAllTips("goodsTable");
			openLocker(boxCode);
		}
	});	
})(jQuery);