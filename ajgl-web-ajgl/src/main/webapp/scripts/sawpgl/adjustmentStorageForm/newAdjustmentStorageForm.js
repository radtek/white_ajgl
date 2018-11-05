(function($){
	"use strict";
	
	var asfTable = null;
	var storageIdArray = [];
	$(document).ready(function() {	
		storageIdArray = $("#storageIdArray").val().split(",");
		setCode();
		initStorageAreaSelect();
		
		
		/**
		 * 取消按钮点击事件
		 */
		$(document).on("click","#cancel",function(){
			window.location.href = $.util.fmtUrl(context + "/standingBook/showStandingBookPage.action");
		});
		
		/**
		 * 保存按钮点击事件
		 */
		$(document).on("click","#save",function(){
			var demo = $.validform.getValidFormObjById("asfValidform") ;
			var flag = $.validform.check(demo) ;
			if(!flag){
				return ;
			}
			var resultFlag = verifyInvolvedArticleArea();
			
			if(!resultFlag){
				window.top.$.layerAlert.alert({msg:"请保持相同物品在同一保管区！"}) ;
				return ;
			}
			save();
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
			
			//删除其它已添加的select里的对应option选项
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
//			var addLockerBut = $(this).parent("div").parent("div").parent("div").next("div").find("button");
//			$(addLockerBut).removeAttr("disabled");
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
				});
				
				//删除当前点击的储物箱行
				$(this).parent("div").parent("div").remove();
				//启用添加按钮
				$(addLockerBut).removeAttr("disabled");
			}
		});
		

		
	});
	
	/**
	 * 初始化locker选项
	 * @param areaId 保管区id
	 * @param button 添加储物箱按钮对象
	 */
	function initLockerOptions(areaId, button){
		var rowData = $(button).parents("tr").data("data");
		var caseCode = rowData.article.caseCode;
		$.ajax({
			url: context + '/adjustmentStorageForm/initDataForStorageSelectPage.action',
			type:"POST",
			dataType:"json",
			data:{storageAreaId : areaId ,caseCode : caseCode},
			customizedOpt:{
				//设置是否loading
				ajaxLoading:true,
			},
			success:function(data){
				var list = data.articleLockerList;
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
			"datatype" : "/^(([0-9]+[\\.]?[0-9]+)|[1-9])$/" ,
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
	 * 设置调整单编号
	 */
	function setCode(){
		$.ajax({
			url: context + '/adjustmentStorageForm/acquireNum.action',
			type:"POST",	
			dataType:"json",
			success:function(data){
				$("#code").text(data.no);
				$.laydate.setDate($.date.dateToStr(new Date(), $.laydate.getFmt("#nasfLaydate")), "#nasfLaydate"); 
				$("#asfCodeImg").qrcode({
				    "render": 'image',
				    "size": 170,
				    "color": "#3a3",
				    "background": "white",
				    "text": data.no
				});
			}
		});
	}
	
	/**
	 * 获取页面所有字段
	 */
	function getAllField(){
		var flag = true;
		var asfbObj = new Object();
		asfbObj.id = null;
		asfbObj.code = $("#code").text();
		asfbObj.adjustTime = $.laydate.getTime("#nasfLaydate", "date");
		asfbObj.operator = currentUserId ;
		asfbObj.reason = $("#reason").val();
		var asfiArray = new Array();
		$("#asfTable tbody tr").each(function(t,tr){
			var rowData = $(tr).data("data");
			var asfiObj = new Object();
			asfiObj.id = null;
			asfiObj.articleId = rowData.article.id;
			asfiObj.adjustmentNumber = rowData.existingNumber;
			asfiObj.storageId = rowData.id;
			asfiObj.remark = $(tr).find("td").eq(14).find("input").val();
			
			//设置位置，及数量
			var lockerCount = 0;
			var zksl = rowData.existingNumber;
			var lockerInput = $(tr).children("td").eq(9).find("input");
			var storageServiceBeanArray = new Array();
			if($.util.exist(lockerInput)){
				$.each(lockerInput,function(i,input){
					var inputNum = $(input).val();
					
					if(!$.util.isBlank(inputNum)){
						lockerCount += parseInt(inputNum,10);
						//添加到位置集合
						var lockerSelectId = $(input).parent("div").parent("div").find("select").attr("id");
						var ssb = new Object(); 
						ssb.lockerId = $.select2.val("#" + lockerSelectId);
						ssb.adjustNumber = inputNum;
						storageServiceBeanArray.push(ssb);
					}
				});
			}
			if(lockerCount != 0 && lockerCount != zksl){
				flag = false;
				window.top.$.layerAlert.alert({msg:"第" + (t+1) + "行存在错误数据，调整数量必须等于在库数量。"}) ;
				return false;
			}
			
			asfiObj.storageServiceBeans = storageServiceBeanArray;
			asfiArray.push(asfiObj);
		});
		asfbObj.asfiBeanList = asfiArray;
		
		if(!flag ){
			return null;
		}else{
			return asfbObj;
		}
	}
	
	/**
	 * 保存调整单
	 */
	function save(){
		
		var asfbObj = getAllField();
		if(!$.util.exist(asfbObj)){
			return ;
		}
		var gData = new Object();
		$.util.objToStrutsFormData(asfbObj, "asfsBean", gData);
		$.ajax({
			url: context + '/adjustmentStorageForm/newAdjustmentStorageForm.action',
			type:"POST",	
			dataType:"json",
			data:gData,
			customizedOpt:{
				ajaxLoading:true
			},
			success:function(data){
				if(data.flag == "true"){
					window.top.$.layerAlert.alert({icon : 6,msg:"保存成功！"}) ;
					var asfId = data.adjustmentStorageFormId;
					window.location.href = $.util.fmtUrl(context + "/adjustmentStorageForm/showLookAdjustmentStorageFormPage.action?asfId=" + asfId) ;
				}else{
					window.top.$.layerAlert.alert({icon : 5,msg:"保存失败！"}) ;
				}
			}
		});
	}
	
	/**
	 * 验证同一个涉案物品是否选择的同一个存储区域
	 */
	function verifyInvolvedArticleArea(){
		var flag = true;// 默认在同一个区域
		//取出可编辑保管区的行
		var trArray = $("#asfTable").find("select[disabled!='disabled']").parents("tr");
		//遍历行判断有没有同一物品不同保管区的
		$.each(trArray,function(t,tr){
			var nowArticleCode = $(tr).data("data").article.code;
			var nowAreaSelectId = $(tr).find("td").eq(8).find("select").attr("id");
			var nowAreaId = $.select2.val("#" + nowAreaSelectId);
			
			var index = t+1;
			if(index > trArray.lenth){
				return false;
			}
			for(var i=index;i<trArray.length;i++){
				var nextArticleCode = $(trArray[i]).data("data").article.code;
				var nextAreaSelectId = $(trArray[i]).find("td").eq(8).find("select").attr("id");
				var nextAreaId = $.select2.val("#" + nextAreaSelectId);
				
				if(nowArticleCode == nextArticleCode && nowAreaId != nextAreaId){
					flag = false;
					return false;
				}
			}
			return false;
		});
		
		return flag;
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
				initTable(data.storageAreaBeanList);
			}
		});
	}
	
	/**
	 * 初始化table
	 * @param storageAreaBeanList 涉案物品存储区域选择项
	 */
	function initTable(storageAreaBeanList){
		var existingNumberSum = 0;
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + '/adjustmentStorageForm/findSotrageByIdList.action';
		tb.columnDefs = [
			{
				"targets" : 0,
				"width" : "20px",
				"title" : "序号",
				"data" : "",
				"render" : function(data, type, full, meta) {
					return meta.row + 1;
				}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "物品名称",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.name:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "特征",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.feature:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "物品编号",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.code:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 4,
				"width" : "50px",
				"title" : "扣押数量",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.detentionNumber:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 5,
				"width" : "50px",
				"title" : "在库数量",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?full.existingNumber:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 6,
				"width" : "100px",
				"title" : "原存储物证保管区",
				"data" : "locker",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data.area)?data.area.name:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 7,
				"width" : "100px",
				"title" : "原存储物箱",
				"data" : "locker",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.location:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 8,
				"width" : "100px",
				"title" : "调整入库物证保管区",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var area = full.locker.area;
					//判断该行是否需要限制区域选择
					if($.util.exist(data)){
						var allCount = 0;// 物品所有在库数量大于0的位置数量合计
						var trStorageArray = data.storages;
						//过滤掉在库数量为0的位置
						$.each(trStorageArray,function(t,ts){
							if(ts.existingNumber > 0){
								allCount ++;
							}
						});
						
						var nowCount = 0;
						$.each(trStorageArray,function(t,ts){
							if($.inArray(ts.id,storageIdArray) != -1){
								nowCount ++;
							}
						});
					}
					//根据是否能改变保管区分别设置保管区可选项
					var areaSelect = $("<select />",{
						"id" : "area" + meta.row ,
						"class" : "form-control select2-noSearch areaSelect" ,
					});
					if(nowCount == allCount){// 可更改保管区
						$("<option />",{
							"value" : null ,
							"text" : "请选择"
						}).appendTo(areaSelect);
						$.each(storageAreaBeanList,function(s,sa){
							$("<option />",{
								"value" : sa.id ,
								"text" : sa.name
							}).appendTo(areaSelect);
						});
					}else{// 不可更改保管区
						//判断当前行修改之前存储区域选项是否被停用
						var exist = false;
						var areaSelectArray = new Array();
						if($.util.exist(area)){
							$.each(storageAreaBeanList,function(i,sab){
								if(sab.id == area.id){
									exist = true;
									areaSelectArray.unshift(sab);
								}else{
									areaSelectArray.push(sab);
								}
							});
							if(!exist){
								areaSelectArray.unshift({id:area.id,name:area.name});
							}
							$.each(areaSelectArray,function(o,option){
								$("<option />",{
									"text":option.name,
									"value":option.id
								}).appendTo(areaSelect);
							});
						}
						$(areaSelect).attr("disabled","disabled");
					}
					
					return areaSelect[0].outerHTML;
				}
			},
			{
				"targets" : 9,
				"width" : "100px",
				"title" : "所在储物箱/入库数量",
				"data" : "storageServiceBeans",
				"render" : function(data, type, full, meta) {
					var storageDiv = $("<div />",{
						"class" : "row" ,
					});
					var butDiv = $("<div />",{
						"class" : "row" ,
						"style" : "margin-left:auto; margin-right:auto;"
					});
					var addBut = $("<button />",{
						"html" : '<span class="glyphicon glyphicon-plus color-green1"></span>',
						"style" : "margin-left:0px;" ,
						"class" : "btn btn-success btn-xs addLockerBut",
					}).appendTo(butDiv);
					
					return storageDiv[0].outerHTML + butDiv[0].outerHTML;
				}
			},
			{
				"targets" : 10,
				"width" : "100px",
				"title" : "对应案件编号",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.caseCode:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 11,
				"width" : "100px",
				"title" : "对应案件名称",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.caseName:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 12,
				"width" : "80px",
				"title" : "办案民警",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.polices:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 13,
				"width" : "100px",
				"title" : "对应犯罪嫌疑人/物品持有人",
				"data" : "article",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-break:break-word;",
						"text" : $.util.exist(data)?data.suspectName + " " + data.suspectIdentityNumber:""
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 14,
				"width" : "100px",
				"title" : "备注",
				"data" : "",
				"render" : function(data, type, full, meta) {
					var td = $("<input />",{
						"class":"input-bordered form-control valiform-keyup form-val",
						"datatype":"*0-85",
						"errormsg":"不可超过85个字符"
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
			$.each(storageIdArray,function(s,storageId){
				d["storageIdList[" + s + "]"] = storageId;
			});
		};
		tb.paramsResp = function(json) {
			json.data = json.storageBeanList;
			json.recordsFiltered = json.storageBeanList.length;
		};
		tb.rowCallback = function(row,data, index) {
			$(row).data("data",data);
			
			//更新在库总数
			existingNumberSum += data.existingNumber;
			$("#existingNumberSum").text(existingNumberSum);
		};
		asfTable = $("#asfTable").DataTable(tb);
		//设置最新修改人和最新修改时间
		$("#modifyTime").text($.date.dateToStr(new Date(), "yyyy-MM-dd HH:mm"));
		$("#modifyPeopleName").text(currentUserName);
	}
	

	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		newAdjustmentStorageForm : {
			
		}
	});	
})(jQuery);