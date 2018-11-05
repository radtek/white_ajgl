(function($){
	"use strict";
	
	var bsfTable = null;
	var backStorageFormServiceBean = null;
	var outStorageId = null;
	var code = null;
	var printDataMap = {};
	var storageAreaList = [];// 保管区可选项集合
	var bczrkslSum=0.0;//获取本次再入库数量之和
	var storageAreaSelectInitComplete = false;//存储区域是否初始化完成
	
	var inComingSnapshoot={};   //快照对象;
	var backStorageSnapshotBeanArr=[];   //再入库快照项数组  
	
	var formid=null;
	
	$(document).ready(function() {	
		getCode();
		initStorageAreaSelect();
		initOutStorageCodeSelect();
		
		alertCaseCodeSelectPage();
		
		/**
		 * 查询案件编码
		 */
		$(document).on("click","#selectCaseCode",function(){
			$("#caseCode").val("");
			alertCaseCodeSelectPage();
		});
		
		/**
		 * 添加储物箱按钮点击事件
		 */
		$(document).on("click",".addLockerBut",function(){
			var storageDiv = $(this).parent("div").prev();
			
			var areaSelectId = $(this).parents("td").prev().find("select").attr("id");
			var areaId = $.select2.val("#" + areaSelectId);
			
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
			var trObj = $(this).parents("tr");
			var lockerSelectId = $(this).parent("div").parent("div").find("select").attr("id");
			var lockerId = $.select2.val("#" + lockerSelectId);
			var lockerName = $.select2.text("#" + lockerSelectId);
			var addLockerBut = $(this).parent("div").parent("div").parent("div").next("div").find("button");
			
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
					$(div).find("select").val(null);
				});
				
				//删除当前点击的储物箱行
				$(this).parent("div").parent("div").remove();
				//启用添加按钮
				$(addLockerBut).removeAttr("disabled");
			}
			
			setBackStatusAndRemarkVerify(trObj);
		});
		
		/**
		 * 保管区改变事件
		 */
		$(document).on("change",".areaSelect",function(){
			//清除已选储物箱
			var trObj = $(this).parents("tr");
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
			
			setBackStatusAndRemarkVerify(trObj);
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
		 * 在库数量失去焦点事件
		 */
		$(document).on("blur",".zksl",function(){
			var trObj = $(this).parents("tr");
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
			var count = getLockerNumberCount(trObj);
			
			//获取出库数量
			var cksl = getOutNumber(trObj);
			if(count > cksl){
				$.layerAlert.tips({
					msg:'储物箱存储总数不能大于出库数量。',
					selector:"#" + $(this).attr("id"),  //需要弹出层的元素选择器
					color:'#FF0000',
					position:1,
					closeBtn:2,
					time:3000,
					shift:1
				});
//				window.top.$.layerAlert.alert({msg:"储物箱存储总数不能大于入库数量。"});
				$(this).val(oldnumber);
			}else{// 实时更新再入库数量和是否全部再入库字段
				setBackStatusAndRemarkVerify(trObj,this);
			}
		});
		
		
		/**
		 * 取消按钮点击事件
		 */
		$(document).on("click","#cancel",function(){
			window.location.href = $.util.fmtUrl(context + "/backStorageForm/showBackStorageFormListPage.action") ;
		});
		
		/**
		 * 保存按钮点击事件
		 */
		$(document).on("click","#save",function(){
			var demo = $.validform.getValidFormObjById("c-center") ;
			var flag = $.validform.check(demo) ;
			if(!flag){
				return ;
			}
			
			var bsfsb = getBackStorageFormField();
			var bsfisbList = getBackStorageFormItemField();
			if(bczrkslSum==0){
				window.top.$.layerAlert.alert({msg:"本次再入库数量不可都为0。"});
				return;
			}
			var gData = {};//new一个空对象存放转换后的对象
			$.util.objToStrutsFormData(bsfsb, "backStorageFormServiceBean", gData);
			$.util.objToStrutsFormData(bsfisbList, "backStorageFormItemServiceBeanList", gData);
			$.util.objToStrutsFormData(inComingSnapshoot, "inComingSnapshootBean", gData);
			$.util.objToStrutsFormData(backStorageSnapshotBeanArr, "backstorageLst", gData);
			printDataMap = gData;
			$.ajax({
				url: context + '/backStorageForm/newBackStorageForm.action',
				type:"POST",	
				customizedOpt:{
					ajaxLoading:true,
				},
				data:gData,
				dataType:"json",
				success:function(data){
					if(data.flag == "true"){
						var bsfId = data.backStorageFormId;
						if($.util.isBlank(bsfId)){
							bsfId = $("#bsfId").val();
						}
						formid=bsfId;
						window.top.$.layerAlert.alert({msg:"保存成功！",end:function(){
							$("#bsfTable").find("input,button,textarea,select").attr("disabled", "disabled");
							$("#remark").attr("disabled", "disabled");
							$("#bsfLaydate").addClass("date-disabled");
							$("#selectCaseCode").attr("disabled", "disabled");
							$("#storageOutCode").attr("disabled", "disabled");
							$("#save").hide();
							$("#print").show();
							$("#cancel").html("返回");
						}});
					}else{
						window.top.$.layerAlert.alert({msg:"保存失败!"});
					}
				}
			});
			
		});
		/**
		 * 在入库数量失去焦点事件
		 */
		$(document).on("blur",".bczrksl",function(){
			var trObj = $(this).parents("tr");
			var zrksl = $(this).val();
			var cknumber = $(this).attr("cksl");
			var ljzrknumber=$(this).parents("tr").find("td").eq(7).find("span").text();
			if(parseFloat(zrksl) > (parseFloat(cknumber)-parseFloat(ljzrknumber))){
				$.layerAlert.tips({
					msg:'不可大于出库数量于累积再入库之差。',
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
		 * 出库单select改变事件
		 */
		$(document).on("change","#storageOutCode",function(){
			var str=$.select2.val("#storageOutCode");
			if(str!=null){
				var arr=str.split(",");
				outStorageId =arr[0];
			}
//			outStorageId = $.select2.val("#storageOutCode");
			
			if($.util.exist(bsfTable)){
				bsfTable.draw(true);
			}else{
				initTable();
			}
			if($.util.isBlank(outStorageId)){
				emptyAllField();
			}
			$("#save").show();
			$("#print").hide();
			$("#cancel").html("取消");
		});
		
		/**
		 * 再入库单项备注输入框改变事件
		 */
		$(document).on("keyup change",".remarkInput",function(){
			var tr = $(this).parents("tr");
			updateMaintainTime(tr);
		});
		
		/**
		 * 涉案物品二维码点击事件
		 */
		$(document).on("click",".articleCodeImg",function(){
			
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
		
	});
	
	/**
	 * 更新维护时间
	 * @param 行
	 */
	function updateMaintainTime(tr){
		$(tr).children().eq(12).text($.date.dateToStr(new Date(),"yyyy-MM-dd HH:mm"));
	}
	
	/**
	 * 初始化涉案物品保管区select选项
	 */
	function initStorageAreaSelect(){
		$.ajax({
			url: context + '/standingBook/findAllStorageArea.action',
			type:"POST",
			dataType:"json",
			data:{status:$.common.Constant.QY()},
			success:function(data){
				storageAreaList = data.storageAreaBeanList; 
			}
		});
	}
	
	/**
	 * 初始化locker选项
	 * @param areaId 保管区id
	 * @param button 添加储物箱按钮对象
	 */
	function initLockerOptions(areaId, button){
		
		$.ajax({
			url:context +'/backStorageForm/initDataForStorageSelectPage.action',
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
	 * 获取再入库单字段
	 */
	function getBackStorageFormField(){
		inComingSnapshoot={};
		var bsfb = new Object();
		bsfb.id = $.util.isBlank($("#bsfId").val())?null:$("#bsfId").val();
//		bsfb.outStorageFormCode = $.select2.text("#storageOutCode");
		var str=$.select2.val("#storageOutCode");
		if(str!=null){
			var arr=str.split(",");
			bsfb.outStorageFormCode = arr[1];
		}
		bsfb.code = $("#code").val();
		bsfb.createdTime = $.laydate.getTime("#bsfLaydate", "date");
		bsfb.operator = currentUserId;
		bsfb.remark = $("#remark").val();
		bsfb.updatedTime = null;
		bsfb.caseCode = $("#caseCode").val();
		bsfb.caseName = $("#caseName").val();
		bsfb.polices = $("#polices").val();
		bsfb.isReturned = $.common.Constant.SF_S();
		$("span[name='back']").each(function(s,span){
			if($(span).text() == "否"){
				bsfb.isReturned = $.common.Constant.SF_F();
			}
		});
		//快照
		inComingSnapshoot.snapshotTime=$.laydate.getTime("#bsfLaydate", "date");
//		inComingSnapshoot.formId=outStorageId;//出库单id
		inComingSnapshoot.formId=$.util.isBlank($("#bsfId").val())?null:$("#bsfId").val();//出库单id
		return bsfb;
	}
	
	/**
	 * 获取再入库单项字段
	 */
	function getBackStorageFormItemField(){
		backStorageSnapshotBeanArr=[];
		var bsfisbArray = new Array();
		var trArray = $("#bsfTable tbody").children();
		bczrkslSum=0.0;
		$.each(trArray,function(t,tr){
			var rowData = $(tr).data("data");
			var bsfisb = new Object();
			bsfisb.backItemId = rowData.backItemId;
			bsfisb.involvedArticleId = rowData.involvedArticleId;//涉案物品id
			bsfisb.involvedArticleCode = rowData.involvedArticleCode;// 涉案物品编号
			bsfisb.outItemId = rowData.outItemId;//出库单项id
			bsfisb.remark = $(tr).children().eq(13).find("input").val();//备注
			bsfisb.isReturend = $(tr).children().eq(11).find("span").text();//是否全部再入库
			//储物箱再入库数量集合
			var cksl = getOutNumber(tr);
			var lockerCount = 0.0;
			
			var lockerInput = $(tr).children("td").eq(9).find("input");
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
						ssb.returnNumber = parseFloat(inputNum,10);
						ssb.id = ssbId;
						ssb.backFormRecordId = ssbId;
						ssb.backFormItemId = rowData.backItemId;
						storageServiceBeanArray.push(ssb);
					}
				});
			}
			if(lockerCount > cksl){
				$($(this).find("td")[0]).css("background-color","#ff0000");
				return true;
			}else{
				$($(this).find("td")[0]).css("background-color","");
			}
			var maintainTimeStr = $(tr).children().eq(12).text();
			if($.util.isBlank(maintainTimeStr)){
				bsfisb.maintainTime = new Date().getTime();
			}else{
				bsfisb.maintainTime = $.date.strToDate(maintainTimeStr, "yyyy-MM-dd HH:mm").getTime();
			}
			bsfisb.storageServiceBeans = storageServiceBeanArray;
			bsfisbArray.push(bsfisb);
			
			//出库单快照
			var count=parseFloat($(tr).children().eq(6).find("input").val());
			bczrkslSum+=count;
			if(count!=0){
				var backStorageSnapshotBean = new Object();
				backStorageSnapshotBean.articleName=rowData.involvedArticleName;//物品名称
				backStorageSnapshotBean.articleFeature=rowData.involvedArticleFeature;//物品特征
				backStorageSnapshotBean.articleCode=rowData.involvedArticleCode;//物品编号
				backStorageSnapshotBean.numberRequested=rowData.detentionNumber;//扣押数量
				backStorageSnapshotBean.outComingNum=rowData.outcomingNumber;//出库数量
				backStorageSnapshotBean.thisIncomingNum=count;//本次再入库数量
				backStorageSnapshotBean.measurementUnit=rowData.measurementUnit;//计量单位
				backStorageSnapshotBean.suspectName=rowData.suspectedName;//嫌疑人/物品持有人姓名
				backStorageSnapshotBean.suspectIdCardNo=rowData.suspectIdentityNumber;//嫌疑人/物品持有人证件号码
				backStorageSnapshotBean.ifInStorageAll=$(tr).children().eq(11).find("span").text();;//是否全部再入库
				backStorageSnapshotBeanArr.push(backStorageSnapshotBean);
			}
		});
		return bsfisbArray;
	}
	
	/**
	 * 设置再入库单字段
     * @param object 
	 */
	function setBackStorageFormField(object){
//		$('#bsfLaydate').addClass("date-disabled");
		if($.util.isBlank(object.id)){
			$("#bsfId").val("");
			$("#code").val(code);
			setCode(code);
			$.laydate.setDate($.date.dateToStr(new Date(), $.laydate.getFmt("#bsfLaydate")), "#bsfLaydate"); 
			$("#remark").val("");
		}else{
			$("#bsfId").val(object.id);
			$("#code").val(object.code);
			setCode(object.code);
			$.laydate.setDate($.date.timeToStr(object.createdTime, $.laydate.getFmt("#bsfLaydate")), "#bsfLaydate"); 
			$("#remark").val(object.remark);
		}
		$("#caseCode").val(object.caseCode);
		$("#typeName").val(object.typeName);
		$("#caseName").val(object.caseName);
		$("#polices").val(object.polices);
	}
	
	/**
	 * 设置再入库单项字段
	 * @param storageAreaBeanList 可选区域集合
	 */
	function setBackStorageFormItemField(storageAreaBeanList){
		if(!$.util.exist(backStorageFormServiceBean)){
			return ;
		}
		var backStorageFormItemServiceBeanList = backStorageFormServiceBean.backStorageFormItemServiceBeanList;
		$.each(backStorageFormItemServiceBeanList,function(b,bsfisb){
			$.select2.empty("#" + bsfisb.involvedArticleId + "storageArea");
			$.select2.addByList("#" + bsfisb.involvedArticleId + "storageArea", storageAreaBeanList, "id", "name" ,true ,true);//设置下拉框选项
			
			if(!$.util.isBlank(bsfisb.areaId)){
				$.select2.val("#" + bsfisb.involvedArticleId + "storageArea",bsfisb.areaId,true);//设置默认值
				if(bsfisb.storageServiceBeans.length > 0){
					$.select2.able("#" + bsfisb.involvedArticleId + "storageArea",false);
					$("#" + bsfisb.involvedArticleId + "locker").text("修改储物箱");
				}
			}
			
			$("#" + bsfisb.involvedArticleId + "selectedLocker").html("");
			var lockerAndNumArray = new Array();
			var storageServiceBeans = bsfisb.storageServiceBeans;
			$.each(storageServiceBeans,function(s,ssb){
				var lockerId = ssb.lockerId;
				var lockerName = ssb.lockerName;
				var returnNumber = ssb.returnNumber;
				
				var lockerObject = new Object();
				lockerObject.lockerId = lockerId;
				lockerObject.lockerName = lockerName;
				lockerObject.returnNumber = returnNumber;
				lockerObject.backFormItemId = ssb.backFormItemId;
				lockerObject.disable = "true";
				lockerObject.oldReturnNumber = returnNumber;
				lockerAndNumArray.push(lockerObject);
				
				var p = $("<p />",{
					"text": lockerName + " 数量：" + returnNumber
				});
				$("#" + bsfisb.involvedArticleId + "selectedLocker").append(p);
				
				setBackAndRemark(bsfisb.involvedArticleId,bsfisb.outcomingNumber,bsfisb.returnedNumber,bsfisb.returnedNumber);
			});
			
			$("#" + bsfisb.involvedArticleId + "locker").data("data",lockerAndNumArray);
		});
		storageAreaSelectInitComplete = true;
	}
	
	
	
	/**
	 * 清空页面字段
	 */
	function emptyAllField(){
//		$("#caseCode").val("");
		$("#typeName").val("");
		$("#caseName").val("");
		$("#polices").val("");
	}
	
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + '/backStorageForm/findBackStorageFormByOsfId.action';
		tb.columnDefs = [
			{
				"targets" : 0,
				"width" : "8%",
				"title" : "物品名称",
				"data" : "involvedArticleName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 1,
				"width" : "8%",
				"title" : "特征",
				"data" : "involvedArticleFeature",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "10%",
				"title" : "物品编号",
				"data" : "involvedArticleCode",
				"render" : function(data, type, full, meta) {
					return data + '&nbsp;&nbsp;<a href="###" class="articleCodeImg" code="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
				}
			},
			{
				"targets" : 3,
				"width" : "5%",
				"title" : "扣押数量",
				"data" : "detentionNumber",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "8%",
				"title" : "物品性质",
				"data" : "involvedArticleTypeName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "5%",
				"title" : "出库数量",
				"data" : "outcomingNumber",
				"render" : function(data, type, full, meta) {
					var td = '<span id="' + full.involvedArticleId + 'outcomingNumber">' + data +'</span>';
					return td;
				}
			},
			{
				"targets" : 6,
				"width" : "5%",
				"title" : "本次再入库数量",
				"data" : "",
				"render" : function(data, type, full, meta) {						
				       return '<input  cksl="'+full.outcomingNumber+'"   id="bczrk' + (Math.random()+"").substring(2,8) + '" type="text" datatype="/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/" errormsg="请填写数量" class="form-control input-sm valiform-keyup form-val bczrksl" style="width:50px"  value="0"/>';
				}
			},
			{
				"targets" : 7,
				"width" : "5%",
				"title" : "累计再入库数量",
				"data" : "returnedNumber",
				"render" : function(data, type, full, meta) {
					var returnNumber = 0;
					if(!$.util.isBlank(data)){
						returnNumber = data;
					}
					var td = $("<span />",{
						"id" : full.involvedArticleId + "returnNumber",
						"returnNumber" : returnNumber,
						"text" : returnNumber
					});
					return  td[0].outerHTML;
				}
			},
			{
				"targets" : 8,
				"width" : "8%",
				"title" : "再入库物证保管区",
				"data" : "areaName",
				"render" : function(data, type, full, meta) {
					var areaSelect = $("<select />",{
						"id" : "area" + meta.row ,
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
						//判断当前要展示的保管区是否被禁用
						if($.inArray(full.areaId,storageAreaList) == -1){
							$("<option />",{
								"value" : full.areaId ,
								"text" : data
							}).appendTo(areaSelect);
						}
						$.each(storageAreaList,function(s,sa){
							if(sa.id == full.storageServiceBeans[0].areaId){
								$("<option />",{
									"value" : sa.id ,
									"text" : sa.name
								}).appendTo(areaSelect);
							}
						});
//						$(areaSelect).attr("disabled","disabled");
					}
					$(areaSelect).attr("disabled","disabled");
					return areaSelect[0].outerHTML;
				}
			},
			{
				"targets" : 9,
				"width" : "8%",
				"title" : "所在储物箱/入库数量",
				"data" : "storageServiceBeans",
				"render" : function(data, type, full, meta) {
					var storageServiceBeans = full.storageServiceBeans;
					var storageDiv = $("<div />",{
						"class" : "row" ,
					});
					var butDiv = $("<div />",{
						"class" : "row" ,
						"style" : "margin-left:auto; margin-right:auto;"
					});
//					var addBut = $("<button />",{
//						"html" : '<span class="glyphicon glyphicon-plus color-green1"></span>',
//						"style" : "margin-left:0px;" ,
//						"class" : "btn btn-success btn-xs addLockerBut",
//					}).appendTo(butDiv);
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
				"targets" : 10,
				"width" : "10%",
				"title" : "对应犯罪嫌疑人/物品持有人",
				"data" : "suspectedName",
				"render" : function(data, type, full, meta) {
					var td = data + "<br>" + ($.util.isBlank(full.suspectIdentityNumber)?"":full.suspectIdentityNumber);
					return td;
				}
			},
			{
				"targets" : 11,
				"width" : "5%",
				"title" : "是否全部再入库",
				"data" : "",
				"render" : function(data, type, full, meta) {
					var isBackComplete = "否";
					if(full.outcomingNumber == full.returnedNumber){
						isBackComplete = "是";
						$("#remarkIdentifying").hide();
					}
					var td = $("<span />",{
						"id":full.involvedArticleId + "back",
						"name":"back",
						"text" : isBackComplete
					});
					return td[0].outerHTML;
				}
			},
			{
				"targets" : 12,
				"width" : "10%",
				"title" : "维护时间",
				"data" : "maintainTime",
				"render" : function(data, type, full, meta) {
					var td = $.date.dateToStr(new Date(),"yyyy-MM-dd HH:mm");
					if($.util.exist(data)){
						td = $.date.timeToStr(data,"yyyy-MM-dd HH:mm");
					}
					return td;
				}
			},
			{
				"targets" : 13,
				"width" : "5%",
//				"title" : "备注<span id='remarkIdentifying' class='red-star'>*</span>",
				"title" : "备注",
				"data" : "remark",
				"render" : function(data, type, full, meta) {
					var td = $("<input />",{
						"id":full.involvedArticleId + "remarkInput",
						"type":"text",
						"value":data,
						"datatype":"*0-85",
						"errormsg":"不可超过85个字符",
//						"nullmsg":"请填写备注",
						"class":"form-control input-sm valiform-keyup form-val remarkInput"
					});
					return td[0].outerHTML;
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
			d["outStorageFormId"] = outStorageId;
		};
		tb.paramsResp = function(json) {
			backStorageFormServiceBean = json.backStorageFormServiceBean;
			if($.util.exist(backStorageFormServiceBean)){
				backStorageFormServiceBean.backStorageFormItemServiceBeanList = json.backStorageFormItemServiceBeanList;
				setBackStorageFormField(backStorageFormServiceBean);
			}
			json.data = json.backStorageFormItemServiceBeanList;
			json.recordsFiltered = json.backStorageFormItemServiceBeanList.length;
		};
		tb.rowCallback = function(row,data, index) {
			$(row).data("data",data); 
		};
		bsfTable = $("#bsfTable").DataTable(tb);
		
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
			"datatype" : "/^(0|[1-9][0-9]{0,9})(\\.[0-9]?[1-9])?$/" ,
			"nullmsg" : "请填写存储数量" ,
			"errormsg" : "请填写数量" ,
			"oldNumber" : storageServiceBean.returnNumber==0?"":storageServiceBean.returnNumber ,
			"value" : storageServiceBean.returnNumber==0?"":storageServiceBean.returnNumber,
			"disabled" : "disabled" 
		}).appendTo(lockerNumberDiv);
		
		return [div[0].outerHTML,$(lockerSelect).attr("id")];
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
	 * 获取储物箱存储数量总和
	 * 
	 * @param trObj 操作行对象
	 * @returns
	 */
	function getLockerNumberCount(trObj){
		var count = 0;
		var inputArray = $(trObj).children("td").eq(9).find("input");
		$.each(inputArray,function(i,input){
			var value = $(input).val();
			if(!$.util.isBlank(value)){
				count += parseFloat(value);
			}
		});
		return count;
	}
	
	/**
	 * 获取出库数量
	 * 
	 * @param trObj 操作行对象
	 * @returns
	 */
	function getOutNumber(trObj){
		var number = $(trObj).children("td").eq(5).find("span").text();
		return number;
	}
	
	
	
	/**
	 * 初始化出库单select选择项
	 * @param 案件编号
	 */
	function initOutStorageCodeSelect(caseCode){
		$.select2.empty("#storageOutCode",true);
		$.ajax({
			url: context + '/backStorageForm/acquireStorageOutCodeList.action',
			type:"POST",	
			dataType:"json",
			data:{"caseCode" : caseCode},
			success:function(data){
				var outStorageFormIdAndCodeList = data.outStorageFormIdAndCodeList;
				$.each(outStorageFormIdAndCodeList,function(i,val){
					var option = '<option value="' + val.id + ","+val.code+'">' + val.code + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+val.time+'</option>';
					$("#storageOutCode").append(option);
				});
				$("#storageOutCode").val(null).trigger("change");
			}
		});
	}
	
	/**
	 * 从后台获取code
	 */
	function getCode(){
		$.ajax({
			url: context + '/backStorageForm/acquireNum.action',
			type:"POST",	
			dataType:"json",
			success:function(data){
				code = data.code;
			}
		});
	}
	
	/**
	 * 设置调整单编号
	 * @param code 编号
	 */
	function setCode(code){
		$("#bsfCodeImg").html("");
		$("#bsfCodeImg").qrcode({
		    "render": 'image',
		    "size": 170,
		    "color": "#3a3",
		    "background": "white",
		    "text": code
		});
	}
	
	/**
	 * 根据再入库数量设置是否返还完成和备注是否必填
	 * 
	 * @param trObj 操作行对象 tr
	 */
	function setBackStatusAndRemarkVerify(trObj ,dom){
		var outNumber = getOutNumber(trObj);
		var returnNumber = getLockerNumberCount(trObj);
		
		if(returnNumber < outNumber || returnNumber == 0){
			$(trObj).children("td").eq(11).find("span").text("否");
			$(trObj).children("td").eq(13).find("input").attr("datatype","*1-85");
			$("#remarkIdentifying").show();
		}else if(returnNumber == outNumber){
			$(trObj).children("td").eq(11).find("span").text("是");
			$(trObj).children("td").eq(13).find("input").attr("datatype","*0-85");
			$("#remarkIdentifying").hide();
		}
		var ck=parseFloat($(trObj).children("td").eq(5).find("span").text());
		if(ck<returnNumber){
			window.top.$.layerAlert.alert({msg:"入库数量不可大于出库数量。"});
			$(dom).val(0);
		}
		$(trObj).children("td").eq(7).find("span").text(returnNumber);
	}
	
	
	/**
	 * 弹出案件编号选择页面
	 */
	function alertCaseCodeSelectPage(){
		var caseCode = null;
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
					caseCode = cm.getCaseCode();
					if(!$.util.isBlank(caseCode)){
						$("#caseCode").val(caseCode);
						$.select2.able("#storageOutCode", "true");
						$("#selectCaseCode").hide();
					}
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
				initOutStorageCodeSelect(caseCode);
			}
		});
	}
	
	$(document).on("click","#print",function(){
//		var form = $.util.getHiddenForm(context +'/backStorageForm/printBackStorageFormById.action', printDataMap);
//		$.util.subForm(form);
		var form = $.util.getHiddenForm(context +'/backStorageForm/printBackStorageFormById.action?backStorageFormId='+formid, null);
		$.util.subForm(form);
	});
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		newBackStorageForm : {
			
		}
	});	
})(jQuery);