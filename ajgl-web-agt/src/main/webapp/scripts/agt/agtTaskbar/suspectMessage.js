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
	var criminalRecordTable = null;
	var setting = $.plupload.getBasicSettings() ;
	$(document).ready(function() {
		initQKTable();
//		$.common.setSelectedTabsById("personBaseInfo");
//		$.common.setIsUpdate(true);
		initData();
//		initPlupload(); // --$
//		$.common.showOperateRecord("opertionRecord");
		/**
		 * 照片查看
		 */
		$(document).on("click",".zoomIn",function(){
		    var url=$(this).attr("src");
			$.layerAlert.img(url,640,10);
		});
		/**
		 * 输入身份证失去光标自动写入个人信息
		 */
		$(document).on("click", "#queryPersonId", function(){
			initQKTable();
			findPersonMessage();
//			var idNum= $("#byQuestioningPeopleIdentifyNum").val();
//			idCardInfo(idNum);
		});
		/**
		 * 关闭按钮
		 */
		$(document).on("click", "#btn-close-window", function(){
			exitForm();//关闭方法 
		});
		
		$(document).on("select2:select", "#causeOfLawCase", function(){ //案由select选中事件
			if($.select2.val("#causeOfLawCase") == $.common.Constant.AY_QT()){
				$("#otherCauseOfLawCase").css("display", "block");
				$("#otherCauseOfLawCase").css("border-style", "");
			}else{
				$("#otherCauseOfLawCase").hide();
			}
		});
		
		 /**
		  * 确定
		  */
		 $(document).on("click","#harConfirm",function(){//保存
			 $("#harConfirm").attr("disabled", "disabled");
			 var demo = $.validform.getValidFormObjById("validformHandlingAreaReceipt") ;
				var flag = $.validform.check(demo) ;
				if(!flag){
					$("#harConfirm").attr("disabled", false);
					return;
				}
				var reg = /(^$)|(^无$)/;
				var idStr = $("#byQuestioningPeopleIdentifyNum").val();
				if(!reg.test(idStr) && !cidInfo(idStr)){
					window.top.$.layerAlert.alert({msg:"请正确填写身份证号，没有请写\"无\"！",icon:"1"});
					$("#harConfirm").attr("disabled", false);
					return;
				}
				if($.util.isBlank($("#byQuestioningPeopleIdentifyNum").val())){
					window.top.$.layerAlert.confirm({
						msg:"身份证未填写，是否保存？",
						title:"提示",	  //弹出框标题
						yes:function(index, layero){
							//$.plupload.start("container");
							save();
						},
						cancel:function(index, layero){
							$("#harConfirm").attr("disabled", false);
						}
					});
				}else{
//					$.plupload.start("container");
					save();
				}
		 });
		 
		 /**
		  * 重置
		  */
		 $(document).on("click","#resBtn",function(){
			 window.location.href=window.location.href;
		 });
		 /**
		 * 案件选择
		 */
		$(document).on("click",".selectAboutCaseLst",function(){
			var initData = {
					cre$ : $
			}
			window.top.$.layerAlert.dialog({
				content : context +  '/agtTaskbar/showCaseList.action',
				pageLoading : true,
				title:"选择案件",
				width : "700px",
				height : "600px",
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
		 
	});
	/**
	 * 身份证号处理
	 * @param sId
	 * @returns
	 */
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
	
	 //拖动处理
    var oldX=0;
    var oldY=0;
    var addPressEvent=0;
    document.getElementById("divMouseMove").onmousedown = function()
     {
       oldX=event.screenX;
       oldY=event.screenY;
       if (addPressEvent==0) 
       { 
            addPressEvent=1;
            if(document.attachEvent)
                {
                   document.detachEvent('onmousemove', moveOnMousePress); 
                   document.detachEvent('onmouseup', moveOnMouseUP);
                   document.attachEvent('onmousemove', moveOnMousePress,false); 
                   document.attachEvent('onmouseup', moveOnMouseUP,false);
                }
                else
                {
                   document.removeEventListener('mousemove', moveOnMousePress); 
                   document.removeEventListener('mouseup', moveOnMouseUP);
                   document.addEventListener('mousemove', moveOnMousePress,false); 
                   document.addEventListener('mouseup', moveOnMouseUP,false);
                }
       }
     }
        var rushCount=0;
        function moveOnMousePress() 
        {
            rushCount++;//降低频率
            if(rushCount>7){
            rushCount=0;
            moveForm(event.screenX-oldX,event.screenY-oldY);
            oldX=event.screenX;
            oldY=event.screenY;
          }
        }
        function moveOnMouseUP() 
        {
               addPressEvent=0;
               if(document.attachEvent)
                {
                   document.detachEvent('onmousemove', moveOnMousePress); 
                   document.detachEvent('onmouseup', moveOnMouseUP);
                }
                else
                {
                   document.removeEventListener('mousemove', moveOnMousePress); 
                   document.removeEventListener('mouseup', moveOnMouseUP);
                }
          //松开最后执行一次
          moveForm(event.screenX-oldX,event.screenY-oldY);
          oldX=event.screenX;
          oldY=event.screenY;
        }
    //--拖动处理完
        //windows方法
        //IE打开url
        function openUrlExplorer(Url) {
            window.external.openUrlExplorer(Url);
        }
        function moveForm(addX,addY) {
            if(addX==0&&addY==0)return;
            window.external.moveForm(addX,addY);
        }
        function exitForm() {
            window.external.exitForm();
        }
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
        //根据地址栏取得房间号
        function getjyName() {
            return GetQueryString("jyName");
        }
	function initData(){
		$.when(
			$.ajax({
				url:context +'/agtTaskbar/findDictionaryItemByType.action',
				type:'post',
				data:{dictionaryType : $.common.Constant.XB()},
				dataType:'json',
				success:function(successData){
					$.select2.addByList("#sex", successData.dictionaryItemLst, "id", "name", true, true);
				}
			}),
			$.ajax({
				url:context +'/agtTaskbar/findDictionaryItemByType.action',
				type:'post',
				data:{dictionaryType : $.common.Constant.AY()},
				dataType:'json',
				success:function(successData){
					$.select2.addByList("#causeOfLawCase", successData.dictionaryItemLst, "id", "name", true, true);
				}
			})
		).done(function(r0, r1){
			$.ajax({
				url:context +'/agtTaskbar/findBasicCaseByHarId.action',
//				url:context +'/handlingAreaReceipt/findBasicCaseByHarId.action',
				type:'post',
				data:{
					id:formId
				},
				dataType:'json',
				success:function(successData){
					var bcb = successData.basicCaseBean;
					policeData = successData.policeInfoBeanLst;
					bcId = bcb.id;
//					idCardInfo(bcb.byQuestioningPeopleIdentifyNum);
					//$("#handlingAreaReceiptNum").text(bcb.handlingAreaReceiptNum);
					$("#byQuestioningPeopleName").val((bcb.byQuestioningPeopleName==null?'':bcb.byQuestioningPeopleName));
					$("#door").val((bcb.door==null?'':bcb.door));
					$.select2.val("#sex", bcb.sex);
					$("#byQuestioningPeopleTelphoneNumber").val((bcb.byQuestioningPeopleTelphoneNumber==null?'':bcb.byQuestioningPeopleTelphoneNumber));
					$("#byQuestioningPeopleAddress").val((bcb.byQuestioningPeopleAddress==null?'':bcb.byQuestioningPeopleAddress));
					$("#byQuestioningPeopleIdentifyNum").val((bcb.byQuestioningPeopleIdentifyNum == null) ? "" : bcb.byQuestioningPeopleIdentifyNum);
					$("#aboutCaseLst").val((bcb.lawCase==null?'':bcb.lawCase));
					$("#lawCaseName").val((bcb.lawCaseName==null?'':bcb.lawCaseName));
					$.select2.val("#causeOfLawCase", bcb.causeOfLawCase);
					if(bcb.causeOfLawCase == $.common.Constant.AY_QT()){
						$("#otherCauseOfLawCase").val(bcb.otherCauseOfLawCase);
						$("#otherCauseOfLawCase").show();
					}
					$("#cameraPicture").attr("src", "data:image/png;base64," + bcb.photo);
					$("#cameraPicture").attr("photoId", bcb.photoId);
					if($.util.exist(bcb.modifyPeopleBean)){
						$("#modifyPeopleName").text(bcb.modifyPeopleBean.name);
					}
					$("#modifyTime").text((bcb.modifyTime == null) ? "" : $.date.timeToStr(bcb.modifyTime, "yyyy-MM-dd HH:mm"));
					if($.util.exist(bcb.attach)){
//						$("#attach").append("<a href='###' id='dlws'>" + bcb.attach.name + "</a><button class='btn btn-primary btn-x' id='uploadNew' title='删除'>×</button>");
						$("#attach").append("<a href='###' id='dlws'>" + bcb.attach.name + "</a>");
						$(document).on("click","#dlws",function(){
							window.open(context + "/agtTaskbar/downloadFile.action?attachmentId="+ bcb.attach.id);
							//$.layerAlert.img("data:image/png;base64," + bcb.attach.base64Str);
						})
					}
//					else{
//						$("#attach").hide();
//						$("#container").show();
//						uploadNew = true;
//					}
				}
			})
		})
	}
	
	
	/**
	 * --$
	 * @returns
	 */
	function initPlupload(){
		setting.container = "container" ; //容器id
		setting.url = context + "/agtTaskbar/uploadFile.action";
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
	
	function findPersonMessage(){
		var reg = /(^$)|(^无$)/;
		var idStr = $("#byQuestioningPeopleIdentifyNum").val();
		if(!reg.test(idStr) && !idCardInfo(idStr)){
			window.top.$.layerAlert.alert({msg:"请正确填写身份证号，没有请写\"无\"！",icon:"1",end : function(){
//				par$.common.showHarSaveBtn();
			}});
			return;
		}
		idCardInfo(idStr);
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
	
	function idCardInfo(idNum){
		var reg = /(^\d{15}$)|(^\d{17}(\d|X|x)$)/;
		if(reg.test(idNum)){
			$.ajax({
				url:context +'/agtTaskbar/findPersonsByIdNum.action',
				type:'post',
				data:{code :idNum},
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
					if(criminalRecordTable!=null){
						criminalRecordTable.destroy();
					}
					criminalRecordTable = $("#criminalRecordTable").DataTable(tb);
				}
			});

		}else{
			window.top.$.layerAlert.alert({msg:"请正确填写身份证号",icon:"1"});
		}
	
	}
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
		if(criminalRecordTable!=null){
			criminalRecordTable.destroy();
		}
		criminalRecordTable = $("#criminalRecordTable").DataTable(tb);
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
		basicCaseBean.lawCase = $("#aboutCaseLst").val();
		basicCaseBean.lawCaseName = $("#lawCaseName").val();
		basicCaseBean.door = $("#door").val();
		basicCaseBean.causeOfLawCase = $("#causeOfLawCase").val();
		basicCaseBean.otherCauseOfLawCase = $("#otherCauseOfLawCase").val();
		var str = $("#cameraPicture").attr("src");
		basicCaseBean.photo = str.substr(22,str.length-22);
		basicCaseBean.uploadNew = uploadNew;
		basicCaseBean.photoChange = photoChange;
		var jyName=getjyName();
		basicCaseBean.updataPersonByAgt = $.base64.decode(jyName);  //url 传递过来的警员名称
		var dataMap = {};
		$.util.objToStrutsFormData(basicCaseBean, "basicCaseBean", dataMap);
//		var arr = [];
//		for(var i in policeData){
//			if(policeData[i].id.indexOf("newPolice") >= 0){
//				arr.push($.util.cloneObj(policeData[i]));
//			}
//		}
//		$.util.objToStrutsFormData(arr, "policeInfoBeanLst", dataMap);
//		var arr = [];
//		for(var i in deleteLst){
//			arr.push($.util.cloneObj(deleteLst[i]));
//		}
//		$.util.objToStrutsFormData(arr, "deletePoliceId", dataMap);
		
		
		//上传需要的内容
//		var i = 0;
//		for (var key in finishedFiles) {  
//			dataMap["basicCaseBean.attach.id"] = key;
//			i++;
//        }
		$.ajax({
			url:context +'/agtTaskbar/updateBasicCase.action',
			type:'post',
			data:dataMap,
			dataType:'json',
			customizedOpt:{
				btn:{	    			
					btn:"#harConfirm"
				}
			},
			success:function(successData){
				if(successData.bn ==true){
					window.top.$.layerAlert.alert({msg:"保存成功！",icon:"1",end : function(){
						window.location.href = window.location.href;
					}});
				}else{
					window.top.$.layerAlert.alert({msg:"保存失败！",icon:"2"});
				}
			}
		});
	}
	
	$(document).on("click","#uploadNew",function(){
		 $("#attach").hide();
		 $("#container").show();
		 uploadNew = true;
	 });
	jQuery.extend($.common, { 
		setCase:function(code, name, i){
			$("#aboutCaseLst").val(code);
			$("#lawCaseName").val(name);
			window.top.layer.close(i);
		}
	});	
})(jQuery);





