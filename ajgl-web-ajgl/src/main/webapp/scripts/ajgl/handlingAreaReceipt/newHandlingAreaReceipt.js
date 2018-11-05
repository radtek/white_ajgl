(function($){
	"use strict";
	//文件上传
	var index = null;
	var byQuestioningPeopleId = null;
	var criminalRecordTable = null;
	var policeData = [];
	var deleteLst = [];
	var count = 0;
	var par$ = null;
	var onlyFlag = true;
	$(document).ready(function() {	
		var p$ = window.top.$;
		var frameData = p$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		par$ = frameData.initData.cre$;
		initPlupload();
		initData();
		events();
		initQKTable();
	});
	
	function initQKTable(){
		var tb = $.uiSettings.getLocalOTableSettings();
		tb.data = [];
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "",
     	    	"title": "序号",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  return meta.row+1;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "涉及案件",
				"data" : "caseName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "办案民警",
				"data" : "police",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "涉案时间",
				"data" : "caseTime",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "人像图片",
				"data" : "picture",
				"render" : function(data, type, full, meta) {
					return "<img class='zoomIn' src='data:image/png;base64," + data + "' height='50'>";
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
		criminalRecordTable = $("#criminalRecordTable").DataTable(tb);
	}
	
	function events(){
		/**
		 * 新增警员
		 */
		$(document).on("click","#checkPolice",function(){
			var initData = {
				cre$ : $,
				id : "newPolice" + count
			}
			count++;
			window.top.$.layerAlert.dialog({
				content : context +  '/handlingAreaReceipt/checkPolice.action',
				pageLoading : true,
				title:"核对办案民警信息",
				width : "650px",
				height : "480px",
				initData:function(){
					return $.util.exist(initData)?initData:{} ;
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		btn:["确认","取消"],
	    		callBacks:{
				    btn1:function(index, layero){
				    	var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
				    	var obj = cm.getPoliceBean();
				    	if($.util.isBlank(obj.icNum)){
				    		window.top.$.layerAlert.alert({msg:"请读取临时卡！",icon:"1",end : function(){
								return;
							}});
				    	}else{
				    		if(policeData != null && policeData.length > 0){
				    			for(var i in policeData){
				    				if(policeData[i].icNum == obj.icNum){
			    						window.top.$.layerAlert.alert({msg:"此临时卡已使用，不能添加！",icon:"1"});
			    						return;
				    				}
				    				if(policeData[i].policeNum == obj.policeNum){
			    						window.top.$.layerAlert.alert({msg:"民警已发卡，不能添加！",icon:"1"});
			    						return;
				    				}
				    			}
				    		}
				    		if(onlyFlag){
				    			onlyFlag = false;
				    			$.ajax({//查询是否有这个人
					    			url:context +'/handlingAreaReceipt/findPoliceInfoByPoliceCode.action',
					    			type:'post',
					    			dataType:'json',
					    			data:{code:obj.policeNum},
					    			success:function(successData){
					    				onlyFlag = true;
					    				if(!$.util.isBlank(successData.piBean.id)){
					    					window.top.$.layerAlert.confirm({
					    						msg:"通过警号查询到警员信息，是否覆盖更新？",
					    						title:"提示",	  //弹出框标题
					    						yes:function(indexAlert, layero){
					    							obj.id = successData.piBean.id;
							    					policeData.push(obj);
										    		window.top.layer.close(index);
										    		changeMainPolice();
					    						},
					    						cancel:function(index, layero){
					    							return;
					    						}
					    					});
					    				}else{
					    					policeData.push(obj);
								    		window.top.layer.close(index);
								    		changeMainPolice();
					    				}
					    			}
					    		});
				    		}else{
	    						return;
				    		}
				    	}
				    },
		    		btn2:function(index, layero){
				    	window.top.layer.close(index);
				    }
				}
			});
		});
		/**
		 * 警员查看
		 */
		$(document).on("click","#showPoliceList",function(){
			var initData = {
				cre$ : $,
				policeData : policeData,
				deleteLst : deleteLst,
				modify : "true"
			}
			window.top.$.layerAlert.dialog({
				content : context +  '/handlingAreaReceipt/showPoliceList.action',
				pageLoading : true,
				title:"查看办案民警信息",
				width : "650px",
				height : "500px",
				initData:function(){
					return $.util.exist(initData)?initData:{} ;
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		btn:["确认","取消"],
	    		callBacks:{
				    btn1:function(index, layero){
				    	var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						deleteLst = cm.getDelectLst();
						for(var i = policeData.length - 1; i >=0; i--){
							for(var j in deleteLst){
								if($.util.exist(policeData[i]) && policeData[i].id == deleteLst[j]){
									policeData.splice(i, 1);
									break;
								}
							}
						}
				    	window.top.layer.close(index);
				    	changeMainPolice();
				    },
		    		btn2:function(index, layero){
				    	window.top.layer.close(index);
				    }
				}
			});
		});
		/**
		 * 照片查看
		 */
		$(document).on("click",".zoomIn",function(){
			window.top.$.layerAlert.img($(this).attr("src"),640,10);
		});
		/**
		 * 获取手环号
		 */
		$(document).on("click","#wristband",function(){
			var str = window.top.getDsspocxObject().GetDevAndBrandIDForThird();
			var obj = JSON.parse(str);
			$("#bangleCode").text(obj.data["BrandID"]);
		});
		/**
		 * 照片获取
		 */
		$(document).on("click",".showCamera",function(){
			var initData = {
				cre$ : $
			}
			window.top.$.layerAlert.dialog({
				content : context +  '/handlingAreaReceipt/showCamera.action',
				pageLoading : true,
				title:"人像采集",
				width : "916px",
				height : "640px",
				initData:function(){
					return $.util.exist(initData)?initData:{} ;
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		btn:["确定","取消"],
	    		callBacks:{
	    			btn1:function(index, layero){
	    				var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						var str = cm.getPicture();
				    	window.top.layer.close(index);
				    	$("#cameraPicture").attr("src", "data:image/png;base64," + str);
				    },
				    btn2:function(index, layero){
				    	window.top.layer.close(index);
				    }
				}
			});
		});
		/**
		 * 查询身份证号
		 */
		$(document).on("click", "#queryPersonId", function(){
			var reg = /(^\d{15}$)|(^\d{17}(\d|X|x)$)/;
			if(reg.test($("#byQuestioningPeopleIdentifyNum").val())){
				$.ajax({
					url:context +'/handlingAreaReceipt/findPersonsByIdNum.action',
					type:'post',
					data:{code : $("#byQuestioningPeopleIdentifyNum").val()},
					dataType:'json',
					success:function(successData){
						var person = successData.person;
						if(person==null){
							window.top.$.layerAlert.alert({msg:"无该人员信息",icon:"5"});
							return;
						}
						$("#byQuestioningPeopleName").val(person.name);
						$.select2.val("#sex", person.sex);
						$("#byQuestioningPeopleTelphoneNumber").val(person.phone);
						$("#byQuestioningPeopleAddress").val(person.liveAddress);
						$("#door").val(person.doorAddress);
						$("#idCardPicture").attr("src", "data:image/png;base64," + person.picture);
						var tb = $.uiSettings.getLocalOTableSettings();
						tb.data = successData.criminalRecordLst;
						tb.columnDefs = [
							{
								"targets": 0,
				     	    	"width": "",
				     	    	"title": "序号",
				     	    	"className":"table-checkbox",
				     	    	"data": "id" ,
				     	    	"render": function ( data, type, full, meta ) {
				     	    			  return meta.row+1;
				     	    	}
							},
							{
								"targets" : 1,
								"width" : "",
								"title" : "涉及案件",
								"data" : "caseName",
								"render" : function(data, type, full, meta) {
									return data;
								}
							},
							{
								"targets" : 2,
								"width" : "",
								"title" : "办案民警",
								"data" : "police",
								"render" : function(data, type, full, meta) {
									return data;
								}
							},
							{
								"targets" : 3,
								"width" : "",
								"title" : "涉案时间",
								"data" : "caseTime",
								"render" : function(data, type, full, meta) {
									return data;
								}
							},
							{
								"targets" : 4,
								"width" : "",
								"title" : "人像图片",
								"data" : "picture",
								"render" : function(data, type, full, meta) {
									return "<img class='zoomIn' src='data:image/png;base64," + data + "' height='50'>";
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
						criminalRecordTable.destroy();
						criminalRecordTable = $("#criminalRecordTable").DataTable(tb);
					}
				});

			}else{
				window.top.$.layerAlert.alert({msg:"请正确填写身份证号",icon:"1"});
			}
		});
		
		$(document).on("select2:select", "#causeOfLawCase", function(){
			if($.select2.val("#causeOfLawCase") == $.common.Constant.AY_QT()){
				$("#otherCauseOfLawCase").css("display", "inline");
			}else{
				$("#otherCauseOfLawCase").hide();
			}
		});
	}

	function changeMainPolice(){
		var str = "";
		for(var i in policeData){
			if(policeData[i].ifMainPolice == $.common.Constant.SF_S()){
				str += policeData[i].policeName + "、";
			}
		}
		$("#handlingPolice").text(str.substr(0,str.length-1));
	}
	
	function initData(){
		$.ajax({
			url:context +'/handlingAreaReceipt/initDataCreatePage.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				$("#handlingAreaReceiptNum").text(successData.code);
				$("#handlingPolice").val(successData.userName);
			}
		});
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.XB()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#sex", successData.dictionaryItemLst, "id", "name", true, true);
			}
		});
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.ZJLX()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#byQuestioningPeopleIdentifyType", successData.dictionaryItemLst, "id", "name", true, true);
			}
		});
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			data:{dictionaryType : $.common.Constant.AY()},
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#causeOfLawCase", successData.dictionaryItemLst, "id", "name", true, true);
			}
		});
		$("#enterIntoTime").text($.date.dateToStr(new Date(),"yyyy-MM-dd HH:mm"));
	}
	function initPlupload(){
		var setting = $.plupload.getBasicSettings() ;
		setting.container = "container" ; //容器id
		setting.url = context + "/handlingAreaReceipt/uploadFile.action";
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
		} ;
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
				return Math.random() ;
			}
		} ;
		setting.chunk_size = '0' ;
		setting.filters.max_file_size = '15mb';
		setting.filters.prevent_duplicates = true ;
		setting.multi_selection = false;
		setting.multi_file_num = 1 ;
		setting.multi_file_num_callback = function(max_file_size, totalSize){
		};
		setting.callBacks = {
				uploadAllFinish:function(up, finishedFiles){
					save(finishedFiles);
				}
		}
		$.plupload.init(setting) ;
	}
	function save(finishedFiles){
		var basicCaseBean = {};
		basicCaseBean.handlingAreaReceiptNum = $("#handlingAreaReceiptNum").text();
		basicCaseBean.byQuestioningPeopleName = $("#byQuestioningPeopleName").val();
		basicCaseBean.sex = $.select2.val("#sex");
		basicCaseBean.byQuestioningPeopleTelphoneNumber = $("#byQuestioningPeopleTelphoneNumber").val();
		basicCaseBean.byQuestioningPeopleAddress = $("#byQuestioningPeopleAddress").val();
		basicCaseBean.byQuestioningPeopleIdentifyNum = $("#byQuestioningPeopleIdentifyNum").val();
		basicCaseBean.lawCase = $("#lawCase").val();
		basicCaseBean.door = $("#door").val();
		basicCaseBean.bangleCode = $("#bangleCode").text();
		basicCaseBean.lawCaseName = $("#lawCaseName").val();
		basicCaseBean.causeOfLawCase = $("#causeOfLawCase").val();
		basicCaseBean.otherCauseOfLawCase = $("#otherCauseOfLawCase").val();
		basicCaseBean.enterAreaReasons = $.select2.val("#enterAreaReasons");
		basicCaseBean.enterIntoTime = $.date.strToTime($("#enterIntoTime").text(),"yyyy-MM-dd HH:mm");
		basicCaseBean.handlingPolice = $("#handlingPolice").text();
		var str = $("#cameraPicture").attr("src");
		basicCaseBean.photo = str.substr(22,str.length-22);		    	
		var dataMap = {};
		$.util.objToStrutsFormData(basicCaseBean, "basicCaseBean", dataMap);
		var arr = [];
		for(var i in policeData){
			arr.push($.util.cloneObj(policeData[i]));
		}
		$.util.objToStrutsFormData(arr, "policeInfoBeanLst", dataMap);
//		arr = [];
//		for(var i in deleteLst){
//			arr.push($.util.cloneObj(deleteLst[i]));
//		}
//		$.util.objToStrutsFormData(arr, "deletePoliceId", dataMap);
		var i = 0;
		for (var key in finishedFiles) {  
			dataMap["basicCaseBean.attach.id"] = key;
			i++;
        }
		$.ajax({
			url:context +'/handlingAreaReceipt/saveHandlingAreaReceipt.action',
			type:'post',
			data:dataMap,
			dataType:'json',
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:"保存成功！\n"+(successData.errorMessage == null ?"": "但是" + successData.errorMessage) ,icon:"1",end : function(){
						par$.common.closeWindow(index);
					}});
				}else{
					window.top.$.layerAlert.alert({msg:"保存失败！\n"+successData.errorMessage ,icon:"2",end: function(){
						par$.common.showHarSaveBtn();
						$.ajax({
							url:context +'/handlingAreaReceipt/initDataCreatePage.action',
							type:'post',
							dataType:'json',
							success:function(successData){
								$("#handlingAreaReceiptNum").text(successData.code);
							}
						});
					}});
				}
			}
		});
	}
	/**
	 * 案件选择
	 */
	$(document).on("click",".lawCaseDiv",function(){
		var initData = {
				cre$ : $
		}
		window.top.$.layerAlert.dialog({
			content : context +  '/handlingAreaReceipt/showCaseList.action',
			pageLoading : true,
			title:"选择案件",
			width : "960px",
			height : "620px",
			initData:function(){
				return $.util.exist(initData)?initData:{} ;
			},
			shadeClose : false,
    		success:function(layero, index){
    			
    		},
    		btn:["取消"],
    		callBacks:{
			    btn1:function(index, layero){
			    	window.top.layer.close(index);
			    }
			}
		});
	});
	function cidInfo(sId){
		var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
		var iSum=0;
		var info="";
		var sBirthday = null;
		if(!/^\d{17}(\d|x)$/i.test(sId)){
			return false;
		}
		sId=sId.replace(/x$/i,"a");
		if(aCity[parseInt(sId.substr(0,2))]==null){
			return false; 
		}
		sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
		var d=new Date(sBirthday.replace(/-/g,"/")) 
		if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
			return false; 
		}
		for(var i = 17;i>=0;i --){
			iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11);
		}
		if(iSum%11!=1){
			return false; 
		}
		return true;
	}
	jQuery.extend($.common, { 
		dialogSub:function(i){
			index = i;
			var demo = $.validform.getValidFormObjById("validformHandlingAreaReceipt") ;
			var flag = $.validform.check(demo) ;
			if(!flag){
				par$.common.showHarSaveBtn();
				return;
			}
			if($.util.isBlank($.select2.val("#causeOfLawCase"))){
				window.top.$.layerAlert.alert({msg:"请选择案由！",icon:"0",end : function(){
					par$.common.showHarSaveBtn();
				}});
				return;
			}else if($.select2.val("#causeOfLawCase") == $.common.Constant.AY_QT()){
				if($.util.isBlank($("#otherCauseOfLawCase").val())){
					window.top.$.layerAlert.alert({msg:"案由其他未填写！",icon:"0",end : function(){
						par$.common.showHarSaveBtn();
					}});
					return;
				}	
			}
			if($.util.isBlank($("#handlingPolice").text())){
				window.top.$.layerAlert.alert({msg:"请添加主办民警！",icon:"1",end : function(){
					par$.common.showHarSaveBtn();
				}});
				return;
			}
			if($.util.isBlank($("#bangleCode").text())){
				window.top.$.layerAlert.alert({msg:"请读取手环！",icon:"1",end : function(){
					par$.common.showHarSaveBtn();
				}});
				return;
			}
			if("../images/man/man-none.png" == $("#cameraPicture").attr("src")){
				window.top.$.layerAlert.alert({msg:"请采集照片！",icon:"1",end : function(){
					par$.common.showHarSaveBtn();
				}});
				return;
			}
			var reg = /(^$)|(^无$)/;
			var idStr = $("#byQuestioningPeopleIdentifyNum").val();
			if(!reg.test(idStr) && !cidInfo(idStr)){
				window.top.$.layerAlert.alert({msg:"请正确填写身份证号，没有请写\"无\"！",icon:"1",end : function(){
					par$.common.showHarSaveBtn();
				}});
				return;
			}
			if($.util.isBlank($("#byQuestioningPeopleIdentifyNum").val())){
				window.top.$.layerAlert.confirm({
					msg:"身份证未填写，是否保存？",
					title:"提示",	  //弹出框标题
					yes:function(index, layero){
						$.plupload.start("container");
					},
					cancel:function(index, layero){
						par$.common.showHarSaveBtn();
					}
				});
			}else{
				$.plupload.start("container");
			}
		},
		setCase:function(code, name, i){
			$("#lawCase").val(code);
			$("#lawCaseName").val(name);
			window.top.layer.close(i);
		}
	});	
})(jQuery);