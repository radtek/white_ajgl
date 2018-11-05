
(function($){
	
	"use strict";
	
	var sawpTable = null;
	var typeSelectOptions = [];
	var storageAreaList = [];
	var printDataMap = {};
	var operator;
	var bcrkslSum=0.0;//获取本次入库数量之和
	$(document).ready(function() {
		
		alertCaseCodeSelectPage();
		
		getTypeOptions();
		
		/**
		 * 查询案件编码
		 */
		$(document).on("click","#selectCaseCode",function(){
			$("#caseCode").val("");
			alertCaseCodeSelectPage();
		});
		
		/**
		 * 入库数量失去焦点事件
		 */
		$(document).on("blur",".rksl",function(){
			var numberRequested = $(this).val();
			$(this).parents("tr").find("td").eq(8).find("input").attr("yrksl",numberRequested);
			var oldRksl = $(this).attr("oldRksl");
			if($.util.isBlank(oldRksl)){
//				$(this).val(numberRequested.replace(/\b(0+)/gi,""));
			}else{
				if(numberRequested < parseFloat(oldRksl)){
					$.layerAlert.tips({
						msg:'入库数量只可增加，不可减小。',
						selector:"#" + $(this).attr("id"),  //需要弹出层的元素选择器
						color:'#FF0000',
						position:1,
						closeBtn:2,
						time:3000,
						shift:1
					});
//					window.top.$.layerAlert.alert({msg:"入库数量只可增加，不可减小。"});
					$(this).val(oldRksl);
				}
			}
		});
		
		/**
		 * 在库数量失去焦点事件
		 */
		$(document).on("blur",".zksl",function(){
			var zksl = $(this).val();
			var oldnumber = $(this).attr("oldnumber");
			if($.util.isBlank(oldnumber)){
				$(this).val(zksl.replace(/\b(0+)/gi,""));
			}else{//验证是否小于已有数量
				if(zksl < parseFloat(oldnumber)){
					$.layerAlert.tips({
						msg:'不可小于已有数量。',
						selector:"#" + $(this).attr("id"),  //需要弹出层的元素选择器
						color:'#FF0000',
						position:1,
						closeBtn:2,
						time:3000,
						shift:1
					});
//					window.top.$.layerAlert.alert({msg:"不可小于已有数量。"});
					$(this).val(oldnumber);
				}
			}
			
			//验证储物箱总存储量是否大于入库数量
			var count = 0.0;
			var inputArray = $(this).parent("div").parent("div").parent("div").find("input");
			$.each(inputArray,function(i,input){
				var value = $(input).val();
				if(!$.util.isBlank(value)){
					count += parseFloat(value);
				}
			});
			
			//获取入库数量
			var rksl = $(this).parents("tr").find("td").eq(6).find("input").val();
			if(count > rksl){
				$.layerAlert.tips({
					msg:'储物箱存储总数不能大于入库数量。',
					selector:"#" + $(this).attr("id"),  //需要弹出层的元素选择器
					color:'#FF0000',
					position:1,
					closeBtn:2,
					time:3000,
					shift:1
				});
//				window.top.$.layerAlert.alert({msg:"储物箱存储总数不能大于入库数量。"});
				$(this).val(oldnumber);
			}else{
				$(this).parents("tr").find("td").eq(9).text(count);
			}
		});
		
		/**
		 * 本次入库数量失去焦点事件
		 */
		$(document).on("blur",".bcrksl",function(){
			var trObj = $(this).parents("tr");
			var rksl = $(this).val();
			var yrknumber = $(this).attr("yrksl");	
			var ljyrknumber=$(this).parents("tr").find("td").eq(9).find("span").text();
			if(parseFloat(rksl) > (parseFloat(yrknumber)-parseFloat(ljyrknumber))){
				$.layerAlert.tips({
					msg:'不可大于应入库数量于累积已入库数量之差。',
					selector:"#" + $(this).attr("id"),  //需要弹出层的元素选择器
					color:'#FF0000',
					position:1,
					closeBtn:2,
					time:3000,
					shift:1
				});
				$(this).val(0);
			}
		})

		/**
		 * 文书点击事件
		 */
		$(document).on("click",".paper",function(){
			var paperId = $(this).attr("id");
			var paperType = $(this).attr("type");
			window.top.open(context+"/showWrit/checkWrit.action?paperId=" + paperId + "&paperType=" + paperType);
		});
		
		
		/**
		 * 案件编号失去焦点事件--$
//		 */
//		$(document).on("blur","#caseCode",function(){
//			var caseCode = $("#caseCode").val();
//			setCaseInfo(caseCode);
////			$("#delXBtn").hide();
//		});
		/**
		 * 案件编号获取焦点事件--$
//		 */
//		$(document).on("focus","#caseCode",function(){
//		//	$("#caseCode").removeAttr("readonly");
//			if($("#caseCode").val()!=''){
//				//$("#delXBtn").show();
//			}
//			
//		});
		

		/**
		 * X的点击事件--$
		 */
		$(document).on("click","#delXBtn",function(){
			event.stopPropagation();
			$("#caseCode").val("");
			delContext();
//			//设置编号可修改
//			$("#caseCode").removeAttr("readonly");
//			$("#caseCodeConfirm").show();
//			$("#caseCodeUpdate").hide();
//			//案件名称、办案民警、嫌疑人、对应文书信息
//			$("#caseName").val("");
//			$("#polices").val("");
//			$("#suspectRadio").html("");
//			$("#papers").html("");
//			
//			$("#sawqTable tbody").html("");
//			$("#delXBtn").hide();
			
		});
		/**
		 * 案件编号确定按钮事件
		 */
		$(document).on("click","#caseCodeConfirm",function(){
			var caseCode = $("#caseCode").val();
			setCaseInfo(caseCode);
		});

		/**
		 * 案件编号修改按钮事件
		 */
		$(document).on("click","#caseCodeUpdate",function(){
			
		});
		
		/**
		 * 保管区改变事件
		 */
		$(document).on("change",".areaSelect",function(){
			//清除已选储物箱
			var td = $(this).parent("td");
			$($(td).next().children("div")[0]).html("");
			
			//获取已选保管区id
			var areaSelectId =  $(this).attr("id");
			var areaId = $.select2.val("#" + areaSelectId);
			
			//查询可用储物箱并将数据绑定到增加按钮上
			var addLockerBut = $(td).next().find("button");
			initLockerOptions(areaId, addLockerBut);
			
			//启用添加按钮
			var addLockerBut = $(this).parents("td").next("td").find("button");
			$(addLockerBut).removeAttr("disabled");
		});
		
		/**
		 * 储物箱改变事件
		 */
		$(document).on("change",".lockerSelect",function(){
			var lockerId = $.select2.val("#" + $(this).attr("id"));
			var lockerName = $.select2.text("#" + $(this).attr("id"));
			
			if($.util.isBlank(lockerId)){
				//删除后面input数量，且设置为不可填写
				$(this).parent("div").next("div").find("input").val("").attr("readonly","readonly");
				return ;
			}
			//将input设置为可填写
			$(this).parent("div").next("div").find("input").removeAttr("readonly");
			
			//删除其它已添加的储物箱select里的对应option选项
			var siglingDivArr = $(this).parent("div").parent("div").siblings("div");
			$.each(siglingDivArr,function(d,div){
				$(div).find("option[value='"+ lockerId +"']").remove();
			});
			
			//取旧值
			var oldValue = $(this).attr("oldValue");
			var oldText = $(this).attr("oldText");
			//当前值替换旧值
			$(this).attr("oldValue",lockerId);
			$(this).attr("oldText",lockerName);
			
			//将旧的储物箱添加到其它储物箱备选select里
			if(!$.util.isBlank(oldValue)){
				var siglingDivArr = $(this).parent("div").parent("div").siblings("div");
				$.each(siglingDivArr,function(d,div){
					$(div).find("select").append("<option value='"+ oldValue +"'>" + oldText + "</option>");
				});
			}
			
			//启用添加按钮
			var addLockerBut = $(this).parent("div").parent("div").parent("div").next("div").find("button");
			$(addLockerBut).removeAttr("disabled");
		});
		
		
		/**
		 * 添加储物箱按钮点击事件
		 */
		$(document).on("click",".addLockerBut",function(){
			var storageDiv = $(this).parent("div").prev();
			
			var areaSelectId = $(this).parents("td").prev().find("select").attr("id");
			var areaId = $.select2.val("#" + areaSelectId);
			
			//判断是否填写了要求数量
			var numberRequested = $(this).parents("tr").children().eq(6).find("input").val();
			if($.util.isBlank(numberRequested)){
				window.top.$.layerAlert.alert({msg:"请先填写入库数量。"});
				return ;
			}
			
			//判断是否选择了保管区
			if(areaId == "请选择" || $.util.isBlank(areaId)){
				window.top.$.layerAlert.alert({msg:"请先选择物证保管区。"});
				return ;
			}
			//获取按钮里存储的可选储物箱数组
			var lockerData = $(this).data("data");
			//按钮上没有数据，则需要重新查询
			if(!$.util.exist(lockerData)){
				var articleLockerList = $(this).parents("tr").data("data").articleLockerList;
				if($.util.exist(articleLockerList)){
					lockerData = articleLockerList;
				}else{
					lockerData = new Array();
				}
			}
			var usableLockerDataArr = getUsableLockerOption(lockerData, storageDiv);
			
			//获取储物箱行
			var resultArray = addNullLocker(usableLockerDataArr);
			$(storageDiv).append(resultArray[0]);
			//设置为请选择
			$("#" + resultArray[1]).val(null).trigger("change");
			
			//禁用添加按钮
			$(this).attr("disabled","disabled");
		});
		
		/**
		 * 删除储物箱
		 */
		$(document).on("click",".deleteLocker",function(){
			var lockerSelectId = $(this).parent("div").parent("div").find("select").attr("id");
			var lockerId = $.select2.val("#" + lockerSelectId);
			var lockerName = $.select2.text("#" + lockerSelectId);
			var addLockerBut = $(this).parent("div").parent("div").parent("div").next("div").find("button");
			
			var count =  $(this).parent("div").parent("div").find("input").val();

			var sum = $(this).parents("tr").find("td").eq(9).text();
			
			$(this).parents("tr").find("td").eq(9).text(sum - count);
			
			if($.util.isBlank(lockerId)){
				//删除当前点击的储物箱行
				$(this).parent("div").parent("div").remove();
				//启用添加按钮
				$(addLockerBut).removeAttr("disabled");
			}else{
				//将删除的储物箱添加到其它储物箱备选select里
				var siglingDivArr = $(this).parent("div").parent("div").siblings("div");
				$.each(siglingDivArr,function(d,div){
					$(div).find("select").append("<option value='"+ lockerId +"'>" + lockerName + "</option>");
				});
				
				//删除当前点击的储物箱行
				$(this).parent("div").parent("div").remove();
				//启用添加按钮
				$(addLockerBut).removeAttr("disabled");
			}
		});
		
	});
	/**
	 * 重置案件编号带入内容
	 * @returns
	 */
	function delContext(){
		//设置编号可修改
		$("#caseCode").removeAttr("readonly");
//		$("#caseCodeConfirm").show();
//		$("#caseCodeUpdate").hide();
		//案件名称、办案民警、嫌疑人、对应文书信息
		$("#caseName").val("");
		$("#polices").val("");
		$("#suspectRadio").html("");
		$("#papers").html("");
		$("#sawqTable tbody").html("");
		$("#delXBtn").hide();
	}
	
	/**
	 * @param allLockerArr 全部可用储物箱
	 * @param lockerSelectDivObj 已选储物箱外边div对象
	 */
	function getUsableLockerOption(allLockerArr, lockerSelectDivObj){
		//获取所有已选的储物箱id
		var selectedLockerIdArr = new Array();
		var selectedArr = $(lockerSelectDivObj).find("select");
		$.each(selectedArr,function(s,select){
			var lockerSelectId = $(select).attr("id");
			var lockerId = $.select2.val("#" + lockerSelectId);
			if(!$.util.isBlank(lockerId)){
				selectedLockerIdArr.push(lockerId);
			}
		});
		
		if(selectedLockerIdArr.length < 1){// 不需要过滤
			return allLockerArr;
		}else{// 需要过滤掉已选择的储物箱
			var newLockerOptionArr = new Array();
			$.each(allLockerArr,function(a,al){
				var flag = false;
				$.each(selectedLockerIdArr,function(i,id){
					if(al.id == id){
						flag = true;
					}
				});
				if(!flag){
					newLockerOptionArr.push(al);
				}
			});
			return newLockerOptionArr;
		}
	}
	
	/**
	 * 初始化locker选项
	 * @param areaId 保管区id
	 * @param button 添加储物箱按钮对象
	 */
	function initLockerOptions(areaId, button){
		
		$.ajax({
			url:context +'/storageIn/initDataForStorageSelectPage.action',
			type:'post',
			dataType:'json',
			data:{"storageAreaId":areaId ,caseCode : $("#caseCode").val()},
			customizedOpt:{
				//设置是否loading
				ajaxLoading:true,
			},
			success:function(successData){
				var list = successData.articleLockerList;
				$(button).data("data",list);
			}
		});
	}
	
	/**
	 * 选择案件编号时获取案件信息
	 */
	function setCaseInfo(caseCode) {
		if($.util.isBlank(caseCode)){
			return ;
		}
		$.ajax({
			url:context +'/storageIn/queryCaseInfo.action?caseCode='+caseCode,
			type:'post',
			dataType:'json',
			success:function(successData){
				if($.util.isBlank(successData.caseName) && $.util.isBlank(successData.operator) && successData.suspectBeanLst.length < 1){
					delContext();
					window.top.$.layerAlert.alert({msg:"请核对案件编号是否正确。"});
					return ;
				}
				//设置编号不可修改
				$("#caseCode").attr("readonly","readonly");
//				$("#caseCodeConfirm").hide();
//				$("#caseCodeUpdate").show();
				
				$("#caseName").val(successData.caseName);
				$("#polices").val(successData.operator);
				$("#policeNumber1").val(successData.policeNumber1);
				$("#policeNumber2").val(successData.policeNumber2);
				var suspectText = "";
				$.each(successData.suspectBeanLst, function(i, obj){
					suspectText += '<p class="col-xs-4"><input type="radio" class="icheckradio" name="xianyirenRadio" id="xyr'+obj.id+'" value="'+obj.id+'" xyrName="'+obj.name+'" />'+obj.name+'</p>';
				});
				$("#suspectRadio").html(suspectText);
				//嫌疑人勾选事件
				$("#suspectRadio").on("ifChanged", "p .icheckradio", function () {
					if (null != $("input[name='xianyirenRadio']:checked").val() && "" != $("input[name='xianyirenRadio']:checked").val()) {
						var suspectId = $(this).val();
						//根据嫌疑人+案件编号查询对应文书
						findDocBeanBySuspectIdAndCaseId(suspectId ,$("#caseCode").val());
						//点击某个嫌疑人/涉案人,其他涉案人隐藏.
						var len = $("#suspectRadio .icheckradio").length;
						for(var i=0 ; i< len ;i++){
							if($($("#suspectRadio .icheckradio")[i]).val() != suspectId){
								$($("#suspectRadio .icheckradio")[i]).parent().parent("p").hide();
							}
						}
						//查询涉案物品
						if($.util.exist(sawpTable)){
							sawpTable.draw();
						}else{
							initStorageInDataTable();
						}
					}
				});
				
			}
		});
	}
	
	/**
	 * 根据嫌疑人id和案件编号查询对应文书
	 * @param suspectId 嫌疑人id
	 * @param caseId 案件编号
	 */
	function findDocBeanBySuspectIdAndCaseId(suspectId, caseId){
		$("#papers").html("");
		$.ajax({
			url: context + '/storageIn/findDocBeanBySuspectIdAndCaseId.action',
			type:"POST",
			data:{suspectId:suspectId,caseCode:caseId},
			dataType:"json",
			success:function(data){
				if(data.paperList.length < 1){
					return ;
				}
				var papersText = "";
				$.each(data.paperList, function(i, obj){
					papersText += '<li><a class="paper formPaper" id="'+obj.paperId+'" type="'+obj.paperType+'" href="#">'+obj.paperName+'</a></li>';
				});
				$("#papers").html(papersText);
			}
		});
	}
	
	//页面数据初始化。
	function getCode() {
		$.ajax({
			url:context +'/storageIn/initData.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				operator = successData.operator;
				$("#code").val(successData.code);
				$("#fixed_date").val(successData.startTime);
				$("#qrcode").html("");	
				$("#qrcode").qrcode({
				    "render": 'image',
				    "size": 170,
				    "color": "#3a3",
				    "background": "white",
				    "title" : successData.code,
				    "text":successData.code
				});
			}
		});
	}
	
	//获取物品性质信息
	function getTypeOptions () {
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : "sawpxz"},
			dataType:'json',
			success:function(successData){
				typeSelectOptions = successData.dictionaryItemLst;
			}
		});
	}
		
	//获取保管区信息
	$.ajax({
		url:context +'/storageIn/queryStorageAreaInfo.action',
		type:'post',
		dataType:'json',
		success:function(successData){
			storageAreaList = successData.storageAreaList;
		}
	});
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function(){
		window.location.href = $.util.fmtUrl(context + "/storageIn/toStorageInRecord.action");
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
//		var articleCode = $(this).attr("fileId");
//		
//		window.top.$.layerAlert.dialog({
//			content : context +  '/storageIn/showArticleQrcodePage.action',
//			pageLoading : true,
//			title:"查看物品二维码",
//			width : "500px",
//			height : "600px",
//			btn:["打印二维码","关闭"],
//			callBacks:{
//				btn1:function(index, layero){
//					var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
//					cm.print();
//				},
//				btn2:function(index, layero){
//					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
//				}
//			},
//			shadeClose : false,
//			success:function(layero, index){
//				
//			},
//			initData:{
//				articleCode:articleCode
//			},
//			end:function(){
//			}
//		});
		
	});
	
	/**
	 * 保存
	 */
	$(document).on("click","#save",function(){
		
		var demo = $.validform.getValidFormObjById("c-center") ;
		var flag = $.validform.check(demo) ;
		if(!flag){
			return ;
		}
		var dataMap = {};
		var flag = getData(dataMap);
		if(bcrkslSum==0){
			window.top.$.layerAlert.alert({msg:"本次入库数量不可都为0。"});
			return;
		}
		printDataMap = dataMap;
		if(!flag){
			window.top.$.layerAlert.confirm({
				msg:"有未填写完整项，点击确定将不对其进行入库保存操作！",
				title:"保存",	  //弹出框标题
				width:'500px',
				hight:'300px',
				shade: [0.5,'black'],  //遮罩
				icon:0,  //弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
				shift:1,  //弹出时的动画效果  有0-6种
				yes:function(index, layero){
					save(dataMap);
				}
			});
		}else{
			save(dataMap);
		}
	});
	
	function save(dataMap){
		$.ajax({
			url:context + '/storageIn/saveStorageInfo.action',
			type : 'post',
			dataType : 'json',
			data : dataMap,
			success : function(successData){
				window.top.$.layerAlert.alert({msg:"保存成功。",icon:1, end:function(){
					$("#sawqTable").find("input,button,textarea,select").attr("disabled", "disabled");
					$("#rkDate").addClass("date-disabled");
					$("#remarkBig").attr("disabled", "disabled");
					$("#selectCaseCode").attr("disabled", "disabled");
					$("#save").hide();
					$("#print").show();
					$("#cancel").html("返回");
					$("#formId").val(successData.id);
					//window.location.href = $.util.fmtUrl(context + "/storageIn/viewStorageIn.action?inStorageFormId="+successData.id);					
				}}) ;
			},
			error:function(errorData){
				$.layerAlert.alert({msg:"保存失败。"}) ;	
			}
		});
	}
	
	function getData(dataMap) {
		var flag = true;
		//验证入库时间
		var rkDate = $.laydate.getTime("#rkDate", "date");
		if($.util.isBlank(rkDate)){
			window.top.$.layerAlert.alert({msg:"请设置入库时间。"});
			return ;
		}
		//验证是否勾选了嫌疑人
		var peopleArr = $.icheck.getChecked("xianyirenRadio");
		if (null == peopleArr || peopleArr.length < 1) {
			window.top.$.layerAlert.alert({msg:"请勾选嫌疑人/物品持有人。"});
			return ;
		}
		var inStorageForm = {};           //入库单对象
		var inStorageFormItemArr = [];    //入库单项数组
		var inComingSnapshoot={};   //快照对象;
		var inStorageSnapshotBeanArr=[];   //快照项数组  
		$("#sawqTable tbody tr").each(function(t,tr){
			var trData = $(tr).data("data");
			
			//判断该行物品性质是否填写
			var wpxzIdArr = $(tr).find(".wpxz option:selected");
			var rksl =$(tr).find(".rksl").val();
			var bcrksl=parseFloat($(tr).find(".bcrksl").val()); //获取本次入库数量
			
			var wpxzStr = "";
			var wpxzNameStr = "";
			$.each(wpxzIdArr,function(i,val){
				wpxzStr += $(val).val() + ",";
				wpxzNameStr += $(val).text() + ",";
			});
			
			//快照项数组 
			if(bcrksl!=0){
				var InStorageSnapshotBean = new Object();
				InStorageSnapshotBean.articleName=trData.articleName; //物品名称
				InStorageSnapshotBean.articleFeature=trData.articleFeature;//物品特征
				InStorageSnapshotBean.articleCode=trData.articleCode;//物品编号
				InStorageSnapshotBean.numberRequested=rksl;//应入库数量
//				InStorageSnapshotBean.articleType=wpxzStr.substring(0,wpxzStr.length-1);//物品性质
				InStorageSnapshotBean.articleType=wpxzNameStr.substring(0,wpxzNameStr.length-1);//物品性质
				InStorageSnapshotBean.thisIncomingNum=bcrksl;//本次入库数量
				InStorageSnapshotBean.measurementUnit=$(tr).find(".rkdw").val();//计量单位
				InStorageSnapshotBean.paper=trData.paperName;//文书名称
				inStorageSnapshotBeanArr.push(InStorageSnapshotBean);
			}
			bcrkslSum+=bcrksl; //获取本次入库数量之和
			var lockerCount = 0;
			//累计已入库数量
			var accumulateSum = $(tr).children("td").eq(9).text();
			//取储物箱存放数量合计，并给Bean位置数组复制
			var lockerInput = $(tr).children("td").eq(11).find("input");
			var storageServiceBeanArray = new Array();
			if($.util.exist(lockerInput)){
				$.each(lockerInput,function(i,input){
					var inputNum = $(input).val();
					var oldnumber = $(input).attr("oldnumber");
					
					if(!$.util.isBlank(inputNum) && !$.util.isBlank(oldnumber)){
						inputNum = parseFloat(inputNum,10) - parseFloat(oldnumber,10);
					}
					if(!$.util.isBlank(inputNum)){
						lockerCount += inputNum;
						//添加到位置集合
						var lockerSelectId = $(input).parent("div").parent("div").find("select").attr("id");
						var ssbId = $(input).parent("div").parent("div").attr("id");
						var ssb = new Object(); 
						ssb.lockerId = $.select2.val("#" + lockerSelectId);
						ssb.incomingNumber = parseFloat(inputNum,10);
						ssb.id = ssbId;
						storageServiceBeanArray.push(ssb);
					}
				});
			}
			if(!$.util.exist(wpxzIdArr) || wpxzIdArr.length < 1 || $.util.isBlank(rksl) || lockerCount > rksl){
				$($(this).find("td")[0]).css("background-color","#ff0000");
				flag = false;
				return true;
			}else{
				$($(this).find("td")[0]).css("background-color","");
			}
			
			//设置入库单项值
			var inStorageFormItemServiceBean = new Object();
			inStorageFormItemServiceBean.id = trData.id;
			inStorageFormItemServiceBean.numberRequested = rksl;
			inStorageFormItemServiceBean.accumulateSum = accumulateSum;
			inStorageFormItemServiceBean.articleId = trData.articleId;
			inStorageFormItemServiceBean.articleCode = trData.articleCode;
			inStorageFormItemServiceBean.detentionNumber = trData.detentionNumber;
			inStorageFormItemServiceBean.articleFeature = trData.articleFeature;
			inStorageFormItemServiceBean.measurementUnit = $(tr).find(".rkdw").val();
			inStorageFormItemServiceBean.articleName = trData.articleName;
			inStorageFormItemServiceBean.paperId = trData.paperId;
			inStorageFormItemServiceBean.paperType = trData.paperType;
			inStorageFormItemServiceBean.paperName = trData.paperName;
			inStorageFormItemServiceBean.polices = trData.polices;
			inStorageFormItemServiceBean.suspectId = trData.suspectId;
			inStorageFormItemServiceBean.suspectName = trData.suspectName;
			inStorageFormItemServiceBean.suspectIdentifier = trData.suspectIdentifier;
			//--
			inStorageFormItemServiceBean.articleType = wpxzStr.substring(0,wpxzStr.length-1);
			inStorageFormItemServiceBean.articleTypeName = wpxzNameStr.substring(0,wpxzNameStr.length-1);
			inStorageFormItemServiceBean.inStorageTime = rkDate;
			inStorageFormItemServiceBean.remark = $(tr).find(".remark").val();
			inStorageFormItemServiceBean["storageServiceBeans"] = storageServiceBeanArray;
			
			inStorageFormItemArr.push(inStorageFormItemServiceBean);
		});
		
		var paperArr = [];
		var paperObj = $(".formPaper");
		$.each(paperObj, function(i, val){
			paperArr.push($(val).html());
		});
		//入库单赋值
		var formId = $("#formId").val();
		inStorageForm.id = formId;
		inStorageForm.code = $("#code").val();
		inStorageForm.createdTime = rkDate;
		inStorageForm.operator = operator;
		inStorageForm.remark = $("#remarkBig").val();
		inStorageForm.caseCode = $("#caseCode").val();
		inStorageForm.caseName = $("#caseName").val();
		inStorageForm.caseHandler = $("#polices").val();
		inStorageForm.papers = paperArr.join(",");
		inStorageForm.suspectName = $(peopleArr[0]).attr("xyrName");
		inStorageForm.suspectId = $(peopleArr[0]).val();
		inStorageForm.policeNumber1 = $("#policeNumber1").val();
		inStorageForm.policeNumber2 = $("#policeNumber2").val();
		
		//快照赋值
		inComingSnapshoot.formId=$("#formId").val();//入库单id
		inComingSnapshoot.snapshotTime=rkDate; //创建时间
		inComingSnapshoot.transactor=operator; //操作人员&办理人
		
		$.util.objToStrutsFormData(inStorageForm, "inStorageFormBean", dataMap);
		$.util.objToStrutsFormData(inStorageFormItemArr, "isfisBeanList", dataMap);
		$.util.objToStrutsFormData(inComingSnapshoot, "InComingSnapshootBean", dataMap);
		$.util.objToStrutsFormData(inStorageSnapshotBeanArr, "instorageLst", dataMap);
		return flag;
	}
	
	/**
	 * 添加空储物箱
	 * @param lockerData 可选储物箱数据
	 */
	function addNullLocker(lockerData){
		var div = $("<div />",{
			"id" : null ,
			"class" : " row" ,
			"style" : "margin-bottom:3px;width:170px;"
		});
		//储物箱选择select
		var lockerSelectDiv = $("<div />",{
			"style" : "width:100px;" ,
			"class" : "col-xs-6"
		}).appendTo(div);
		var lockerSelect = $("<select />",{
			"id" : "locker" + (Math.random()+"").substring(2,8),
			"class" : "select2 lockerSelect" ,
			"style" : "width:120px;" ,
			"oldValue" : "" ,
			"oldText" : ""
		}).appendTo(lockerSelectDiv);
		if($.util.exist(lockerData) && lockerData.length > 0){
			$.each(lockerData,function(i,val){
				var option = '<option value="' + val.id + '">' + val.location + '</option>';
				$(lockerSelect).append(option);
			});
		}
		
		//储物箱数量input
		var lockerNumberDiv = $("<div />",{
			"style" : "width:70px;" ,
			"class" : "col-xs-6"
		}).appendTo(div);
		$("<input />",{
			"id" : (Math.random()+"").substring(2,8) ,
			"type" : "text" ,
			"style" : "width:40px;margin-left:3px;height:28px;" ,
			"class" : "form-control input-sm valiform-keyup form-val zksl" ,
			"readonly" : "readonly" ,
			"datatype" : "/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/" ,
			"nullmsg" : "请填写存储数量" ,
			"errormsg" : "请填写数量" ,
			"oldNumber" : ""
		}).appendTo(lockerNumberDiv);
		
		//删除按钮
		$("<span />",{
			"class" : "glyphicon glyphicon-remove color-red1 deleteLocker" ,
			"style" : "margin-left:5px"
		}).appendTo(lockerNumberDiv);
		
		return [div[0].outerHTML,$(lockerSelect).attr("id")];
	}
	
	/**
	 * 添加已有数据储物箱
	 * @param storageServiceBean 已有储物箱数据集合
	 */
	function addExistLocker(storageServiceBean){
		var div = $("<div />",{
			"id" : storageServiceBean.id ,
			"class" : " row" ,
			"style" : "margin-bottom:3px;width:170px;"
		});
		//储物箱选择select
		var lockerSelectDiv = $("<div />",{
			"style" : "width:100px;" ,
			"class" : "col-xs-6"
		}).appendTo(div);
		var lockerSelect = $("<select />",{
			"id" : "locker" + (Math.random()+"").substring(2,8),
			"class" : "select2 lockerSelect" ,
			"style" : "width:120px;" ,
			"disabled" : "disabled" 
		}).appendTo(lockerSelectDiv);
		
		var option = '<option value="' + storageServiceBean.lockerId + '">' + storageServiceBean.lockerLocation + '</option>';
		$(lockerSelect).append(option);
		
		//储物箱数量input
		var lockerNumberDiv = $("<div />",{
			"style" : "width:70px;" ,
			"class" : "col-xs-6"
		}).appendTo(div);
		$("<input />",{
			"id" : (Math.random()+"").substring(2,8) ,
			"type" : "text" ,
			"style" : "width:40px;margin-left:3px;height:28px;background:#ddd" ,
			"class" : "form-control input-sm valiform-keyup form-val zksl" ,
			"datatype" : "/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/",
			"nullmsg" : "请填写存储数量" ,
			"errormsg" : "请填写数量" ,
			"oldNumber" : storageServiceBean.incomingNumber==0?"":storageServiceBean.incomingNumber ,
			"value" : storageServiceBean.incomingNumber==0?"":storageServiceBean.incomingNumber,
			"disabled" : "disabled" 
		}).appendTo(lockerNumberDiv);
		
		return [div[0].outerHTML,$(lockerSelect).attr("id")];
	}
	
	function initStorageInDataTable(){
		    var xh = 1;
		    var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/storageIn/findImpoundedObjectByCaseCodeAndSuspect.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "20px",
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
					"title" : "物品性质<span class='red-star'>*</span>",
					"data" : "articleType",
					"render" : function(data, type, full, meta) {
						var selectDiv = $("<div />",{
							"style" : "width:100px;"
						});
						var articleTypeSelect = $("<select />",{
							"id" : "wpxz" + xh ,
							"class" : "select2-multiple wpxz" , 
							"style" : "width:250px;" ,
							"multiple" : "multiple" ,
						}).appendTo(selectDiv);
						
						if ($.util.exist(typeSelectOptions) && typeSelectOptions.length > 0) {
							$.each(typeSelectOptions, function(i, obj){
								if (null != data && data.indexOf(obj.id) >= 0) {
									$("<option />",{
										"selected" : "selected" ,
										"value" : obj.id ,
										"text" : obj.name
									}).appendTo(articleTypeSelect);
								} else {
									$("<option />",{
										"value" : obj.id ,
										"text" : obj.name
									}).appendTo(articleTypeSelect);
								}
							});
						}
						return selectDiv[0].outerHTML;
					}
				},
				{
					"targets" : 6,
					"width" : "5%",
					"title" : "扣押应入库数量<span class='red-star'>*</span>",
					"data" : "numberRequested",
					"render" : function(data, type, full, meta) {
						
						var data = (data==0?"":data);
					return '<input id="' + (Math.random()+"").substring(2,8) + '" type="text" datatype="/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/" errormsg="请正确填写(小数位不超过2位,整数位不超过10位)" class="form-control input-sm valiform-keyup form-val rksl" style="width:50px" oldRksl="0" nullmsg="请填写入库数量" value="' + data + '"/>';
					}
				},
				{
					"targets" : 7,
					"width" : "5%",
					"title" : "计量单位",
					"data" : "measurementUnit",
					"render" : function(data, type, full, meta) {
						
						var unit = "";
						if($.util.isBlank(data)){
							if(!$.util.isBlank(full.detentionNumber)){
								unit = full.detentionNumber;
							}
						}else{
							unit = data;
						}
						return '<input type="text" class="form-control input-sm valiform-keyup form-val rkdw" style="width:50px" value="'+unit+'" datatype="*0-4" errormsg="不可超过4个字符"/>';
					}
				},
				{
					"targets" : 8,
					"width" : "5%",
					"title" : "本次入库数量",
					"data" : "",
					"render" : function(data, type, full, meta) {						
					       return '<input yrksl="'+full.numberRequested+'" id="bcrk' + (Math.random()+"").substring(2,8) + '" type="text" datatype="/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/" errormsg="请填写数量" class="form-control input-sm valiform-keyup form-val bcrksl" style="width:50px"  value="0"/>';
					}
				},
				{
					"targets" : 9,
					"width" : "5%",
					"title" : "累计已入库数量",
					"data" : "accumulateSum",
					"render" : function(data, type, full, meta) {
						var sumCount = (full.accumulateSum == null ? 0 : full.accumulateSum);
						var storageServiceBeans = full.storageServiceBeans;
						if($.util.exist(storageServiceBeans) && storageServiceBeans.length > 0){
							$.each(storageServiceBeans,function(s,ssb){
								sumCount = sumCount + ssb.incomingNumber ;
							});
						}
						var td = '<span "style="text-align:center; width:30px;">' + sumCount + '</span>';
						return td;
					}
				},
				{
					"targets" : 10,
					"width" : "8%",
					"title" : "所在物证保管区",
					"data" : "",
					"render" : function(data, type, full, meta) {
						var areaSelect = $("<select />",{
							"id" : "area" + xh ,
							"class" : "form-control select2-noSearch areaSelect" ,
						});
						if(!$.util.exist(full.storageServiceBeans) || full.storageServiceBeans.length < 1){//新建
							$("<option />",{
								"value" : null ,
								"text" : "请选择"
							}).appendTo(areaSelect);
							$.each(storageAreaList,function(s,sa){
								$("<option />",{
									"value" : sa.id ,
									"text" : sa.name
								}).appendTo(areaSelect);
							});
						}else{//更新
							$.each(storageAreaList,function(s,sa){
								if(sa.id == full.storageServiceBeans[0].areaId){
									$("<option />",{
										"value" : sa.id ,
										"text" : sa.name
									}).appendTo(areaSelect);
								}
							});
//							$(areaSelect).attr("disabled","disabled");
						}
						$(areaSelect).attr("disabled","disabled");
						return areaSelect[0].outerHTML;
					}
				},
				{
					"targets" : 11,
					"width" : "8%",
					"title" : "所在储物箱/入库数量",
					"data" : "",  
					"render" : function(data, type, full, meta) {
						var storageServiceBeans = full.storageServiceBeans;
						var storageDiv = $("<div />",{
							"class" : "row" ,
						});
						var butDiv = $("<div />",{
							"class" : "row" ,
							"style" : "margin-left:auto; margin-right:auto;"
						});
//						var addBut = $("<button />",{
//							"html" : '<span class="glyphicon glyphicon-plus color-green1"></span>',
//							"style" : "margin-left:0px;" ,
//							"class" : "btn btn-success btn-xs addLockerBut",
//						}).appendTo(butDiv);
						if($.util.exist(storageServiceBeans) && storageServiceBeans.length > 0){//有保存位置信息
							$.each(storageServiceBeans,function(s,ssb){
								var resultData = addExistLocker(ssb);
								$(storageDiv).append(resultData[0]);
							});
						}
						return storageDiv[0].outerHTML + butDiv[0].outerHTML;
					}
				},
				{
					"targets" : 12,
					"width" : "10%",
					"title" : "对应文书",
					"data" : "paperName",
					"render" : function(data, type, full, meta) {
						var td = '<a class="paper" id="'+full.paperId+'" type="'+full.paperType+'" href="#">'+data+'</a>';
						return td;
					}
				},
				{
					"targets" : 13,
					"width" : "10%",
					"title" : "维护时间",
					"data" : "updatedTime",
					"render" : function(data, type, full, meta) {
						return ($.util.isBlank(data) ? $.date.timeToStr(new Date().getTime(),"yyyy-MM-dd HH:mm") : $.date.timeToStr(data,"yyyy-MM-dd HH:mm"));
					}
				},
				{
					"targets" : 14,
					"width" : "5%",
					"title" : "备注",
					"data" : "remark",
					"render" : function(data, type, full, meta) {
						var remark = $.util.isBlank(data) ? "" : data;
						return '<input type="text" datatype="*0-50" errormsg="不可超过50个字符" class="remark" style="width:100px" value="' + remark + '"/>';
					}
				}
			];
			//是否排序
			tb.ordering = false ;
			tb.paging = false ; 
			tb.info = false ;
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
				d["caseCode"] = $.select2.val("#caseCode");
				d["suspectId"] = $("input[name='xianyirenRadio']:checked").val();
			};
			tb.paramsResp = function(json) {
				xh = 1;
				var itemBeanlist = json.isfisBeanList;
				json.data = itemBeanlist;
				var code = json.code;
				if (null != code && "" != code) {
					$("#formId").val(json.id);
					$("#code").val(json.code);
					$("#fixed_date").val(json.startTime);
					$("#remarkBig").text(json.remark);
					$("#qrcode").html("");					
					$("#qrcode").qrcode({
						"render": 'image',
						"size": 170,
						"color": "#3a3",
						"background": "white",
						"title" : json.code,
						"text":json.code
					});
				}else{
					getCode();
				}
			};
			tb.rowCallback = function(row,data, index) {
				$(row).data("data",data);
			};
			sawpTable = $("#sawqTable").DataTable(tb);
	}
	
	/**
	 * 弹出案件编号选择页面
	 */
	function alertCaseCodeSelectPage(){
		window.top.$.layerAlert.dialog({
			content : context +  '/selectCaseCodeAlert/showSelectCaseCodeAlertPage.action',
			pageLoading : true,
			title:"查询案件编号",
			width : "850px",
			height : "500px",
			btn:["确定","取消"],
			callBacks:{
				btn1:function(index, layero){					
					var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
					var caseCode = cm.getCaseCode();
					if(!$.util.isBlank(caseCode)){
						$("#caseCode").val(caseCode);
						//$("#delXBtn").show();
						setCaseInfo(caseCode);//加载内容
						$("#selectCaseCode").hide();
					}
//					setCaseInfo(caseCode);
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
				
			},
			end:function(){
				
			}
		});
	}
	
	$(document).on("click","#print",function(){
//		var form = $.util.getHiddenForm(context +'/storageIn/printStorageInById.action', printDataMap);
//		$.util.subForm(form);
		var form = $.util.getHiddenForm(context +'/storageIn/printStorageInById.action?inStorageFormId='+$("#formId").val(), null);
		$.util.subForm(form);
	});
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		
	});	
})(jQuery);