(function($) {
	"use strict";
	//办案区使用单id
	var harId = "";
	var goodsTable = null;
	var temporaryTable = null;
	var detainTable = null;
	var keepTable = null;
	var returnObjTable = null;
	
	var bcb = null;
	$(document).ready(function() {
		jumpOn();
		harId = $("#harId").val();
		 $.common.setSelectedTabsById("goodsReturn");
		 //将状态改为false 查看页面
		 $.common.setIsUpdate(false);
		 $.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
		 
//		 $.when(
//				 initNotReturnGoodsTable()
//			    ).done(function(r0, r1){
//			    	initAlreadyRetuernGoodsTable();
//			    })

		 $(document).on("click",".img",function(){
				$.layerAlert.img($(this).attr("src"));
			})
		 initData();
		 qureyBasicCase();
	});
	
	function qureyBasicCase(){
		$.ajax({
			url:context +'/handlingAreaReceipt/findBasicCaseByHarId.action',
			type:'post',
			data:{
				id:harId
			},
			dataType:'json',
			success:function(successData){
				bcb = successData.basicCaseBean;
			}
		});
	}
	
	/**
	 * 打印
	 */
	$(document).on("click","#printAll",function() {
		var data = {id : harId};
		var form = $.util.getHiddenForm(context +'/harPrint/printHandlingAreaReceipt.action', data);
		$.util.subForm(form);
	});
	
	var btn2DCodeFlag = false;
	
	/**
	 * 打印下拉菜单
	 */
	$(document).on("click",".dropup",function() {
		if($(this).hasClass("open")){
			$(this).removeClass("open");
		}else{
			$(this).addClass("open");
		}
		btn2DCodeFlag = true;
	});
	
	/**
	 * 打印下拉菜单
	 */
	$(document).on("click","html",function() {
		if(btn2DCodeFlag){
			btn2DCodeFlag = false;
			return;
		}
		if($(".dropup").hasClass("open")){
			$(".dropup").removeClass("open");
		}
	});
	
	/**
	 * 打印立即返还
	 */
	$(document).on("click","#printLJFH",function() {
		var obj = $('<div></div>');
		var qr = $('<div></div>');
		var options = {
				render: "image",
				ecLevel: "H",
				minVersion: 6,
				color: "#333333",
				bgColor: "#ffffff",
				text: bcb.handlingAreaReceiptNum,
				size: 500,
				radius: 0,
				quiet: 1,
				mode: 2,
				label: "立即返还",
				labelsize: 0.20
			};
		$(qr).qrcode(options);
		$(qr).css("margin-left", "10px");
		$(qr).find("img").css("width", "150px");
		$(obj).append(qr);
		var str=retStr();
		$(obj).append(str);
//		$(obj).append("<span style='font-size:18px;'>" +
//				"办案区使用单：<br/>" + (bcb.handlingAreaReceiptNum == null?"":bcb.handlingAreaReceiptNum) + "<br/>" +
//				"嫌疑人：" + (bcb.byQuestioningPeopleName) + "<br/>" +
//				(bcb.byQuestioningPeopleIdentifyNum == null?"":bcb.byQuestioningPeopleIdentifyNum) + "<br/>" +
//				"案件名称：<br/>" + (bcb.lawCaseName == null?"":bcb.lawCaseName) + "<br/>" +
//				(bcb.lawCase == null?"":bcb.lawCase.substring(0, 13)) + "<br/>" +
//				(bcb.lawCase == null?"":bcb.lawCase.substring(13, bcb.lawCase.length)) + "<br/>" +
//				"办案单位：" + (bcb.sponsorUnitName == null?"":bcb.sponsorUnitName) + "<br/>" +
//				"办案民警：" + bcb.handlingPolice + "<br/>" +
//				"<span>");
		
		printdiv(obj);
	});
	
	/**
	 * 打印临时取出
	 */
	$(document).on("click","#printls",function() {
		var form = $.util.getHiddenForm(context +'/harPrint/printTemporaryLstation.action?handlingAreaReceiptId='+harId, null);
		$.util.subForm(form);
	});
	
	/**
	 * 打印移交暂存/扣押
	 */
	$(document).on("click","#printyj",function() {
		var form = $.util.getHiddenForm(context +'/harPrint/printCarryGoodsReturnRecord.action?handlingAreaReceiptId='+harId, null);
		$.util.subForm(form);
	});
	
	/**
	 * 打印移交暂存
	 */
	$(document).on("click","#printYJZC",function() {
		var obj = $('<div></div>');
		var qr = $('<div></div>');
		var options = {
				render: "image",
				ecLevel: "H",
				minVersion: 6,
				color: "#333333",
				bgColor: "#ffffff",
				text: bcb.handlingAreaReceiptNum,
				size: 500,
				radius: 0,
				quiet: 1,
				mode: 2,
				label: "移交暂存",
				labelsize: 0.15
			};
		$(qr).qrcode(options);
		$(qr).css("margin-left", "10px");
		$(qr).find("img").css("width", "150px");
		$(obj).append(qr);
		var str=retStr();
		$(obj).append(str);
//		$(obj).append("<span style='font-size:18px;'>" +
//				"办案区使用单：<br/>" + (bcb.handlingAreaReceiptNum == null?"":bcb.handlingAreaReceiptNum) + "<br/>" +
//				"嫌疑人：" + (bcb.byQuestioningPeopleName) + "<br/>" +
//				(bcb.byQuestioningPeopleIdentifyNum == null?"":bcb.byQuestioningPeopleIdentifyNum) + "<br/>" +
//				"案件名称：<br/>" + (bcb.lawCaseName == null?"":bcb.lawCaseName) + "<br/>" +
//				(bcb.lawCase == null?"":bcb.lawCase.substring(0, 13)) + "<br/>" +
//				(bcb.lawCase == null?"":bcb.lawCase.substring(13, bcb.lawCase.length)) + "<br/>" +
//				"办案单位：" + (bcb.sponsorUnitName == null?"":bcb.sponsorUnitName) + "<br/>" +
//				"办案民警：" + bcb.handlingPolice + "<br/>" +
//				"<span>");
		printdiv(obj);
	});
	
	/**
	 * 打印移交扣押
	 */
	$(document).on("click","#printYJKY",function() {
		var obj = $('<div></div>');
		var qr = $('<div></div>');
		var options = {
				render: "image",
				ecLevel: "H",
				minVersion: 6,
				color: "#333333",
				bgColor: "#ffffff",
				text: bcb.handlingAreaReceiptNum,
				size: 500,
				radius: 0,
				quiet: 1,
				mode: 2,
				label: "移交扣押",
				labelsize: 0.15
			};
		$(qr).qrcode(options);
		$(qr).css("margin-left", "10px");
		$(qr).find("img").css("width", "150px");
		$(obj).append(qr);
		var str=retStr();
		$(obj).append(str);
//		$(obj).append("<span style='font-size:18px;'>" +
//				"办案区使用单：<br/>" + (bcb.handlingAreaReceiptNum == null?"":bcb.handlingAreaReceiptNum) + "<br/>" +
//				"嫌疑人：" + (bcb.byQuestioningPeopleName) + "<br/>" +
//				(bcb.byQuestioningPeopleIdentifyNum == null?"":bcb.byQuestioningPeopleIdentifyNum) + "<br/>" +
//				"案件名称：<br/>" + (bcb.lawCaseName == null?"":bcb.lawCaseName) + "<br/>" +
//				(bcb.lawCase == null?"":bcb.lawCase.substring(0, 13)) + "<br/>" +
//				(bcb.lawCase == null?"":bcb.lawCase.substring(13, bcb.lawCase.length)) + "<br/>" +
//				"办案单位：" + (bcb.sponsorUnitName == null?"":bcb.sponsorUnitName) + "<br/>" +
//				"办案民警：" + bcb.handlingPolice + "<br/>" +
//				"<span>");
		printdiv(obj);
	});
	/**
	 * 打印的str 
	 * @returns
	 */
	function retStr(){
		var str="";
		str+="<span style='font-size:15px;'>" ;
		if(!$.util.isBlank(bcb.handlingAreaReceiptNum)){
			str+="办案区使用单：<br/>" + bcb.handlingAreaReceiptNum  + "<br/>" ;
		}
		if(!$.util.isBlank(bcb.byQuestioningPeopleIdentifyNum)){
			str+="嫌疑人：" + (bcb.byQuestioningPeopleName) + "<br/>" +
			bcb.byQuestioningPeopleIdentifyNum + "<br/>" ;
		}else{
			str+="嫌疑人：" + (bcb.byQuestioningPeopleName) + "<br/>";
		}
		if(!$.util.isBlank(bcb.lawCaseName)||!$.util.isBlank(bcb.lawCase)){
			str+="案件名称：<br/>" ;
		}
		if(!$.util.isBlank(bcb.lawCaseName)){
			if(bcb.lawCaseName.length > 13){
				str+=bcb.lawCaseName.substring(0, 13) + "<br/>";
				str+=bcb.lawCaseName.substring(13, bcb.lawCaseName.length) + "<br/>";
			}else{
				str+=bcb.lawCaseName+"<br/>";
			}
		}
		if(!$.util.isBlank(bcb.lawCase)){
			str+=bcb.lawCase.substring(0, 13) + "<br/>" +
			bcb.lawCase.substring(13, bcb.lawCase.length) + "<br/>" ;
		}
		if(bcb.sponsorUnitName!=null){
			str+="办案单位：" + bcb.sponsorUnitName + "<br/>" ;
		}
		if(!$.util.isBlank(bcb.handlingPolice)){
			str+="办案民警：" + bcb.handlingPolice + "<br/>" ;
		}
		str+="<span>";
		return str;
	}
	
	var HKEY_Root,HKEY_Path,HKEY_Key;
	HKEY_Root="HKEY_CURRENT_USER";
	HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	//设置网页打印的页眉页脚为空
	function PageSetup_Null()
	{
	      var Wsh=new ActiveXObject("WScript.Shell");
	      HKEY_Key="header";
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
	      HKEY_Key="footer";
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
	      HKEY_Key="margin_left" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--左边边界
	      HKEY_Key="margin_top" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--上边边界
	      HKEY_Key="margin_right" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--右边边界
	      HKEY_Key="margin_bottom" 
	      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--下边边界
	}

	function printdiv(obj)
	{  
		var printData = $(obj).html();
		var win = window.open();
		win.document.body.innerHTML = printData;
		try
	    {
	        PageSetup_Null();
	        win.print();
	    }
	    catch(e)
	    {
	        var errorMsg = e.message+"\r"+"请设置:IE选项->安全->Internet->"+"ActiveX控件和插件"+"\r"+"对未标记为可安全执行脚本的ActiveX的控件初始化并执行脚本->允许/提示";
	        alert(errorMsg);
	        return;
	    }
		win.close();
		return false;
	}

	/**
	 * 解绑手环
	 */
	$(document).on("click","#unbound",function(){
		window.top.$.layerAlert.confirm({
			msg:"解绑后，将初始化嫌疑人手环及民警临时卡,请确认是否解绑？",
			title:"解绑提示",	  //弹出框标题
			yes:function(index, layero){
				$.ajax({
					url:context +'/leaveSituation/braceletControl.action',
					type:'post',
					data:{
						id:harId,
						type:"jb"
					},
					dataType:'json',
					success:function(successData){
						if(successData.flag == "true"){
							window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示"});
						}else{
							window.top.$.layerAlert.alert({msg:successData.errorMessage ,title:"提示"});
						}
					}
				})
			}
		});
	});
	
	/**
	 * 完成使用单
	 */
	$(document).on("click",".finishReceipt",function() {
		$.ajax({
			url:context +'/handlingAreaReceipt/handlingAreaReceiptCheck.action',
			type:'post',
			data:{
				id:harId
			},
			dataType:'json',
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.confirm({
						msg:"完成后信息将不可更改，是否要完成本次问讯？",
						title:"提示",	  //弹出框标题
						yes:function(index, layero){
							$.ajax({
								url:context +'/handlingAreaReceipt/doneHandlingAreaReceipt.action',
								type:'post',
								data:{
									id:harId
								},
								dataType:'json',
								success:function(successData){
									if(successData.flag == "true"){
										window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示",end:function(){
											location.reload(true);
										}});
									}else{
										window.top.$.layerAlert.alert({msg:"操作失败！",title:"提示"});
									}
								}
							})
						}
					});
				}else{
					window.top.$.layerAlert.alert({msg:successData.msg,title:"提示",end:function(){
						if(successData.flag == "base"){
							window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showUpdateHandlingAreaReceiptPage.action?&&justShow=false" + $("#justShow").val() + "&&harId=" + $("#harId").val());
						}
					}
				});
				}
			}
		})
	
		
	});
	/**
	 * 维护随身物品返还信息
	 */
	$(document).on("click","#updateCarryGoodsReturnRecord",function() {
		 window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showUpdateCarryGoodsReturnRecord.action?&&harId=" + $("#harId").val());
	});
	
	function initData(){
		$.ajax({
			url:context +'/carryGoodsInfo/queryCarryGoodsReceipt.action',
			type:'post',
			data:{"handlingAreaReceiptId":harId},
			dataType:'json',
			success:function(successData){
				if(successData.flag == "false"){
					window.top.$.layerAlert.alert({msg:"此使用单未保存随身物品！" ,title:"提示", end:function(){
						window.location.href = $.util.fmtUrl(context +"/carryGoodsInfo/showCarryGoodsInfo.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
					}});
					return;
				}
				var data = successData.handlingPolice.split("、");
				for(var i in data){
					$("#policeP").append('<input id="police' + i + '" type="radio" class="icheckradio policeSelect" name="police" value="' + data[i] + '"><label for="police' + i + '">&nbsp;' + data[i] + '&nbsp;&nbsp;</label>');
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
									strongboxName : bean.strongboxName
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
						+ 'boxId="' + boxLst[i].strongboxNum + '" title="开箱"></a>'
						+ '<span class="state-mark state-busy"></span></li>';
					}
					$("#boxUl").append(str);
					initTable(goodsTable, "goodsTable", dataSet[0]);
					initTable(temporaryTable, "temporaryTable", dataSet[1]);
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
     	    	"width": "15%",
     	    	"title": "物品名称",
     	    	"className":"table-checkbox",
     	    	"data": "goodsName" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return data;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "12%",
				"title" : "特征",
				"data" : "features",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "8%",
				"title" : "数量",
				"data" : "quantity",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "10%",
				"title" : "计量单位",
				"data" : "unitOfMeasurement",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "12%",
				"title" : "物品编号",
				"data" : "num",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
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
				"targets" : 6,
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
				"targets" : 7,
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
	
})(jQuery);