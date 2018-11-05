(function($){
	"use strict";
	//文件上传
	var index = null;
	var uploadNew = false;
	var bcId = null;
	var policeData = [];
	var deleteLst = [];
	var count = 0;
	var photoChange = false;
	var setting = $.plupload.getBasicSettings() ;
	var criminalRecordTable = null;
	var policeModifyFlag = false;
	var onlyFlag = true;
	$(document).ready(function() {
		jumpOff();
		$.common.setSelectedTabsById("personBaseInfo");
		$.common.setIsUpdate(true);
		initData();
		initPlupload();
		$.common.showOperateRecord("opertionRecord");
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
				    		window.top.$.layerAlert.alert({msg:"请读取临时卡！",icon:"1"});
				    		return;
				    	}else{
				    		var repetitionFlag = "false";
				    		if(policeData != null && policeData.length > 0){
				    			for(var i in policeData){
				    				if(policeData[i].icNum == obj.icNum){
			    						window.top.$.layerAlert.alert({msg:"此临时卡已使用，不能添加！",icon:"1"});
			    						return;
				    				}
				    				if(policeData[i].policeNum == obj.policeNum){
				    					if(!$.util.isBlank(policeData[i].icNum)){
				    						window.top.$.layerAlert.alert({msg:"民警已发卡，不能添加！",icon:"1"});
				    						return;
				    					}else{
				    						repetitionFlag = i;
				    						$.util.log("已检测到重新发卡，i值为" + repetitionFlag);
				    					}
				    				}
				    			}
				    		}
				    		if(repetitionFlag == "false"){
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
				    		}else{
				    			obj.id = policeData[repetitionFlag].id;
				    			obj.relationWithHar = false;
	    						policeData[repetitionFlag] = obj;
				    		}
				    		window.top.layer.close(index);
				    		changeMainPolice();
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
				modify : policeModifyFlag
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
			$.layerAlert.img($(this).attr("src"),640,10);
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
				    	photoChange = true;
				    	$("#cameraPicture").attr("src", "data:image/png;base64," + str);
				    },
				    btn2:function(index, layero){
				    	window.top.layer.close(index);
				    }
				}
			});
		});
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
		
		 /**
		  * 确定
		  */
		 $(document).on("click","#harConfirm",function(){
			 $("#harConfirm").attr("disabled", "disabled");
			 var demo = $.validform.getValidFormObjById("validformHandlingAreaReceipt") ;
				var flag = $.validform.check(demo) ;
				if(!flag){
					$("#harConfirm").attr("disabled", false);
					return;
				}
				if($.util.isBlank($("#handlingPolice").text())){
					window.top.$.layerAlert.alert({msg:"请添加主办民警！",icon:"1",end : function(){
						$("#harConfirm").attr("disabled", false);
					}});
					return;
				}
				if("../images/man/man-none.png" == $("#cameraPicture").attr("src")){
					window.top.$.layerAlert.alert({msg:"请采集照片！",icon:"1",end : function(){
						window.top.$.common.showBtn();
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
							$("#harConfirm").attr("disabled", false);
						}
					});
				}else{
					$.plupload.start("container");
				}
		 });
		 
		 /**
		  * 取消
		  */
		 $(document).on("click","#harCancel",function(){
			 window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
		 });
		 /**
		  * 返回
		  */
		 $(document).on("click","#harBack",function(){
			 window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
		 });
		 $(document).on("click","#uploadNew",function(){
			 $("#attach").hide();
			 $("#container").show();
			 uploadNew = true;
		 });
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
		$.when(
			$.ajax({
				url:context +'/webUtil/findDictionaryItemByType.action',
				type:'post',
				data:{dictionaryType : $.common.Constant.XB()},
				dataType:'json',
				success:function(successData){
					$.select2.addByList("#sex", successData.dictionaryItemLst, "id", "name", true, true);
				}
			}),
			$.ajax({
				url:context +'/webUtil/findDictionaryItemByType.action',
				type:'post',
				data:{dictionaryType : $.common.Constant.AY()},
				dataType:'json',
				success:function(successData){
					$.select2.addByList("#causeOfLawCase", successData.dictionaryItemLst, "id", "name", true, true);
				}
			})
		).done(function(r0, r1){
			$.ajax({
				url:context +'/handlingAreaReceipt/findBasicCaseByHarId.action',
				type:'post',
				data:{
					id:$("#harId").val()
				},
				dataType:'json',
				success:function(successData){
					var bcb = successData.basicCaseBean;
					policeData = successData.policeInfoBeanLst;
					policeModifyFlag = successData.policeModifyFlag;
					bcId = bcb.id;
					$("#enterIntoTime").text($.date.timeToStr(bcb.enterIntoTime, "yyyy-MM-dd HH:mm"));
					$("#handlingAreaReceiptNum").text(bcb.handlingAreaReceiptNum);
					$("#byQuestioningPeopleName").val(bcb.byQuestioningPeopleName);
					$("#door").val(bcb.door);
					$("#bangleCode").text(bcb.bangleCode);
					$.select2.val("#sex", bcb.sex);
					$("#byQuestioningPeopleTelphoneNumber").val(bcb.byQuestioningPeopleTelphoneNumber);
					$("#byQuestioningPeopleAddress").val(bcb.byQuestioningPeopleAddress);
					$("#byQuestioningPeopleIdentifyNum").val(bcb.byQuestioningPeopleIdentifyNum);
					$("#lawCase").val(bcb.lawCase);
					$("#lawCaseName").val(bcb.lawCaseName);
					$("#enterAreaReasons").val(bcb.enterAreaReasons);
					$.select2.val("#causeOfLawCase", bcb.causeOfLawCase);
					if(bcb.causeOfLawCase == $.common.Constant.AY_QT()){
						$("#otherCauseOfLawCase").val(bcb.otherCauseOfLawCase);
						$("#otherCauseOfLawCase").show();
					}
					$("#handlingPolice").text(bcb.handlingPolice == null ? "" : bcb.handlingPolice);
					$("#cameraPicture").attr("src", "data:image/png;base64," + bcb.photo);
					$("#cameraPicture").attr("photoId", bcb.photoId);
					if($.util.exist(bcb.modifyPeopleBean)){
						$("#modifyPeopleName").text(bcb.modifyPeopleBean.name);
					}
					$("#modifyTime").text((bcb.modifyTime == null) ? "" : $.date.timeToStr(bcb.modifyTime, "yyyy-MM-dd HH:mm"));
					if($.util.exist(bcb.attach)){
						$("#attach").append("<a href='###' id='dlws'>" + bcb.attach.name + "</a><button class='btn btn-primary btn-x' id='uploadNew' title='删除'>×</button>");
						$(document).on("click","#dlws",function(){
							window.open(context + "/handlingAreaReceipt/downloadFile.action?attachmentId="+ bcb.attach.id);
							//$.layerAlert.img("data:image/png;base64," + bcb.attach.base64Str);
						})
					}else{
						$("#attach").hide();
						$("#container").show();
						uploadNew = true;
					}
				}
			})
		})
	}
	function initPlupload(){
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
		basicCaseBean.id = bcId;
		basicCaseBean.handlingAreaReceiptNum = $("#handlingAreaReceiptNum").text();
		basicCaseBean.byQuestioningPeopleName = $("#byQuestioningPeopleName").val();
		basicCaseBean.sex = $.select2.val("#sex");
		basicCaseBean.byQuestioningPeopleTelphoneNumber = $("#byQuestioningPeopleTelphoneNumber").val();
		basicCaseBean.byQuestioningPeopleAddress = $("#byQuestioningPeopleAddress").val();
		basicCaseBean.byQuestioningPeopleIdentifyNum = $("#byQuestioningPeopleIdentifyNum").val();
		basicCaseBean.lawCase = $("#lawCase").val();
		basicCaseBean.lawCaseName = $("#lawCaseName").val();
		basicCaseBean.door = $("#door").val();
		basicCaseBean.bangleCode = $("#bangleCode").text();
		basicCaseBean.causeOfLawCase = $("#causeOfLawCase").val();
		basicCaseBean.otherCauseOfLawCase = $("#otherCauseOfLawCase").val();
		basicCaseBean.enterAreaReasons = $.select2.val("#enterAreaReasons");
		basicCaseBean.enterIntoTime = $.date.strToTime($("#enterIntoTime").text(),"yyyy-MM-dd HH:mm");
		basicCaseBean.handlingPolice = $("#handlingPolice").text();
		var str = $("#cameraPicture").attr("src");
		basicCaseBean.photo = str.substr(22,str.length-22);
		basicCaseBean.uploadNew = uploadNew;
		basicCaseBean.photoChange = photoChange;
		basicCaseBean.photoId = $("#cameraPicture").attr("photoId");
		var dataMap = {};
		$.util.objToStrutsFormData(basicCaseBean, "basicCaseBean", dataMap);
		var arr = [];
		for(var i in policeData){
			arr.push($.util.cloneObj(policeData[i]));
		}
		$.util.objToStrutsFormData(arr, "policeInfoBeanLst", dataMap);
		arr = [];
		for(var i in deleteLst){
			var str = deleteLst[i];
			arr.push(str);
		}
		$.util.objToStrutsFormData(arr, "deletePoliceId", dataMap);
		var i = 0;
		for (var key in finishedFiles) {  
			dataMap["basicCaseBean.attach.id"] = key;
			i++;
        }
		$.ajax({
			url:context +'/handlingAreaReceipt/updateBasicCase.action',
			type:'post',
			data:dataMap,
			dataType:'json',
			customizedOpt:{
				btn:{	    			
					btn:"#harConfirm"
				}
			},
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.alert({msg:"保存成功！",icon:"1",end : function(){
						window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + $("#harId").val());
					}});
				}else{
					window.top.$.layerAlert.alert({msg:"保存失败！"+successData.errorMessage,icon:"2"});
					$("#harConfirm").attr("disabled", false);
				}
			}
		});
	}
	
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
		setCase:function(code, name, i){
			$("#lawCase").val(code);
			$("#lawCaseName").val(name);
			window.top.layer.close(i);
		}
	});	
})(jQuery);