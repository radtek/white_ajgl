(function($){
	"use strict";
	var caseTable = null;
	$(document).ready(function() {
		$(document).on("click","#refresh",function(){
			location.reload(true);  
		});
		$(document).on("click","#back",function(){
			history.go(-1);
		});
		$(document).on("click",".showWrit",function(){
			window.parent.open(context+"/showWrit/checkWrit.action?archivedFileId=" + $(this).attr("writId"));
		});
		/**
		 * 文书点击事件
		 */
		$(document).on("click",".paper",function(){
			var paperId = $(this).attr("paperId");
			var paperType = $(this).attr("paperType");
			window.parent.open(context+"/showWrit/checkWrit.action?paperId=" + paperId + "&paperType=" + paperType);
		});
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showDetail",function(){
			window.parent.open($.util.fmtUrl(context +"/caseSearch/showCaseDetail.action?caseCode=" + $(this).attr("caseCode")));
		});
		/**
		 * 按钮点击事件
		 */
		$(document).on("click","#tabs-2 .man-group a",function(){
			var btn = $("#tabs-2 .man-group .btn-primary");
			if(btn.attr("objId") == $(this).attr("objId")){
				return;
			}
			btn.addClass("btn-default");
			btn.removeClass("btn-primary");
			$("#tabs-2 table[objId=" + btn.attr("objId") + "]").hide();
			$("ul[objId=" + btn.attr("objId") + "]").hide();
			$(this).addClass("btn-primary");
			$(this).removeClass("btn-default");
			$("#tabs-2 table[objId=" + $(this).attr("objId") + "]").show();
			$("ul[objId=" + $(this).attr("objId") + "]").show();
		});
		
		
		/**
		 * 按钮点击事件
		 */
		$(document).on("click",".tabsLi",function(){
			$("#susTrace").hide();
			$("#caseTrace").show();
		});
		/**
		 * 按钮点击事件
		 */
		$(document).on("click",".tabsLi-xyr",function(){
			$("#susTrace").show();
			$("#caseTrace").hide();
		});
		
		/**
		 * 按钮点击事件
		 */
		$(document).on("click","#tabs-3 .man-group a",function(){
			var btn = $("#tabs-3 .man-group .btn-primary");
			if(btn.attr("objId") == $(this).attr("objId")){
				return;
			}
			btn.addClass("btn-default");
			btn.removeClass("btn-primary");
			$("#tabs-3 table[objId=" + btn.attr("objId") + "]").hide();
			$(this).addClass("btn-primary");
			$(this).removeClass("btn-default");
			$("#tabs-3 table[objId=" + $(this).attr("objId") + "]").show();
		});
		/**
		 * 按钮点击事件
		 */
		$(document).on("click","#tabs-7 .man-group a",function(){
			var btn = $("#tabs-7 .man-group .btn-primary");
			if(btn.attr("objId") == $(this).attr("objId")){
				return;
			}
			btn.addClass("btn-default");
			btn.removeClass("btn-primary");
			$("#tabs-7 div[objId=" + btn.attr("objId") + "]").hide();
			$(this).addClass("btn-primary");
			$(this).removeClass("btn-default");
			$("#tabs-7 div[objId=" + $(this).attr("objId") + "]").show();
		});
		/**
		 * 按钮点击事件
		 */
		$(document).on("click","#tabs-4 .man-group a",function(){
			var btn = $("#tabs-4 .man-group .btn-primary");
			if(btn.attr("objId") == $(this).attr("objId")){
				return;
			}
			btn.addClass("btn-default");
			btn.removeClass("btn-primary");
			$("#tabs-4 table[objId=" + btn.attr("objId") + "]").hide();
			$(this).addClass("btn-primary");
			$(this).removeClass("btn-default");
			$("#tabs-4 table[objId=" + $(this).attr("objId") + "]").show();
		});
		if($("#justShow").val() == "true"){
			
		}
		$.ajax({
			url:context +'/caseSearch/initCase.action',
			type:'post',
			data:{caseCode : $("#caseCode").val()},
			dataType:'json',
			success:function(successData){
				var data = successData.criminalBasicCaseDetailBean;
				//开头信息初始化
				$("#caseTitleUp").text(data.caseName);
				$("#caseStateUp").text(data.caseState);
				$("#ifArchive").text(data.ifArchive);
				$("#caseCodeUp").text(data.caseCode);
				$("#caseTimeStartUp").text(data.caseTimeStart);
				$("#caseAddressUp").text(data.caseAddress);
				$("#caseReasonUp").text(data.caseReason);
				$("#disposePersonUp").text(data.handlingPeople1 + "  " + data.handlingPeople2);
				var time = data.filingTime;
				if($.util.isBlank(time)){
					time = "" ;
				}
				$("#filingTime").text(time);
				$("#dqbldwUp").text(data.dqbldw);
				$("#detailsUp").text(data.details);
				//基本信息初始化
				$.each($("#tabs-1 .caseTable .valCell"),function(){
					$(this).text(data[$(this).attr("valName")]);
				})
				//接处警初始化
				if(data.alarmInfoObj != null){
					$.each($("#tabs-8 .alarmTable .valCell"),function(){
						$(this).text(data.alarmInfoObj[$(this).attr("valName")]);
					})
				}else{
					$("#tabs-8").html("<h2>无此信息</h2>");
				}
				//扣押物品初始化
				var impoundedObjectMap = data.impoundedObjectMap;
				if(impoundedObjectMap != null){
					var btnTemplate = $("#tabs-7 .man-group a");
					var kywpInfoTemplate = $("#tabs-7 .kywpInfoDiv");
					var firstFlag = true;
					var notNullFlag = false;
					for(var key in impoundedObjectMap){
						notNullFlag = true;
						//按钮
						var kywpBtn = btnTemplate.clone(true);
						if(firstFlag == true){
							kywpBtn.addClass("btn-primary");
						}else{
							kywpBtn.addClass("btn-default");
						}
						kywpBtn.text(impoundedObjectMap[key][0].suspectName);
						kywpBtn.attr("objId", key);
						kywpBtn.show();
						kywpBtn.insertBefore(btnTemplate);
						//基本信息
						var kywpTableDiv = kywpInfoTemplate.clone(true);
						if(firstFlag == true){
							kywpTableDiv.show();
							firstFlag = false;
						}
						kywpTableDiv.attr("objId", key);
						kywpTableDiv.insertBefore(kywpInfoTemplate);
						initKYWPTable($("#tabs-7 div[objId=" + key + "] table"), impoundedObjectMap[key]);
					}
					if(!notNullFlag){
						$("#tabs-7").html("<h2>无此信息</h2>");
					}
				}else{
					$("#tabs-7").html("<h2>无此信息</h2>");
				}
				//嫌疑人初始化
				var caseSupectRelationLst = data.caseSupectRelationLst;
				if(caseSupectRelationLst != null && caseSupectRelationLst.length > 0){
					var btnTemplate = $("#tabs-2 .man-group a");
					var personTableTemplate = $("#tabs-2 .personForXYR");
					var xyrTableTemplate = $("#tabs-2 .xyrTable");
					for(var i in caseSupectRelationLst){
						//按钮
						var xyrBtn = btnTemplate.clone(true);
						if(i == 0){
							xyrBtn.addClass("btn-primary");
						}else{
							xyrBtn.addClass("btn-default");
						}
						xyrBtn.text(caseSupectRelationLst[i].criminalPerson.name);
						xyrBtn.attr("objId", caseSupectRelationLst[i].id);
						xyrBtn.show();
						xyrBtn.insertBefore(btnTemplate);
						//基本信息
						var personTable = personTableTemplate.clone(true);
						if(i == 0){
							personTable.show();
						}
						personTable.attr("objId", caseSupectRelationLst[i].id);
						personTable.insertBefore(personTableTemplate);
						$.each(personTable.find(".valCell"),function(){
							if($(this).attr("valName") == "birthdayStart"){
								var arr = caseSupectRelationLst[i].criminalPerson[$(this).attr("valName")].split(" ");
								$(this).text(arr[0]);
							}else{
								$(this).text(caseSupectRelationLst[i].criminalPerson[$(this).attr("valName")]);
							}
						})
						//嫌疑人信息
						var xyrTable = xyrTableTemplate.clone(true);
						if(i == 0){
							xyrTable.show();
						}
						xyrTable.attr("objId", caseSupectRelationLst[i].id);
						xyrTable.insertBefore(xyrTableTemplate);
						$.each(xyrTable.find(".valCell"),function(){
							$(this).text(caseSupectRelationLst[i][$(this).attr("valName")]);
						})
						$.each(xyrTable.find(".valShow"),function(){
							if(caseSupectRelationLst[i][$(this).attr("valName")] != "是"){
								$("tr[valTr=" + $(this).attr("valName") + "]" ).hide();
							}
						})
						var str = "";
						var caseExecutionProcessBeanMap = caseSupectRelationLst[i].caseExecutionProcessBeanMap;
						$.each(caseExecutionProcessBeanMap,function(key,val){
							str +='<ul objId="' + caseSupectRelationLst[i].id + '" class="nav nav-tabs" style="display:none;">';
							$.each(val,function(index,itm){
								str += '<li class="addClass ">'+
								'<span class="icon-red-dot"></span>'+
								'<p class="time-box">'+(itm.issuedTime == "" ? "未填写":itm.issuedTime)+'</p>'+
								'<div class="con-box">'+
									'<span class="arrow"></span>'+itm.stepName+''+
								'</div>'+
								'<div class="append-btn"></div>';
								str += '</li>';
							})
							str += '</ul>';
						});
						$("#personTrace").append(str);
						if(i == 0){
							$("ul[objId=" + caseSupectRelationLst[i].id + "]").show();
						}
					}
				}else{
					$("#tabs-2").html("<h2>无此信息</h2>");
				}
				//涉案物品初始化
				var criminalObjectLst = data.criminalObjectLst;
				if(criminalObjectLst != null && criminalObjectLst.length > 0){
					var btnTemplate = $("#tabs-3 .man-group a");
					var objTableTemplate = $("#tabs-3 .criminalObjectTable");
					for(var i in criminalObjectLst){
						//按钮
						var objBtn = btnTemplate.clone(true);
						if(i == 0){
							objBtn.addClass("btn-primary");
						}else{
							objBtn.addClass("btn-default");
						}
						objBtn.text(criminalObjectLst[i].objectName);
						objBtn.attr("objId", criminalObjectLst[i].id);
						objBtn.show();
						objBtn.insertBefore(btnTemplate);
						//物品信息
						var objTable = objTableTemplate.clone(true);
						if(i == 0){
							objTable.show();
						}
						objTable.attr("objId", criminalObjectLst[i].id);
						objTable.insertBefore(objTableTemplate);
						$.each(objTable.find(".valCell"),function(){
							$(this).text(criminalObjectLst[i][$(this).attr("valName")]);
						})
					}
				}else{
					$("#tabs-3").html("<h2>无此信息</h2>");
				}
				//报警人受害人初始化
				var sufferCaseRelationLst = data.sufferCaseRelationLst;
				if(sufferCaseRelationLst != null && sufferCaseRelationLst.length > 0){
					var btnTemplate = $("#tabs-4 .man-group a");
					var personInfoTemplate = $("#tabs-4 .personInfo");
					var bjrTableTemplate = $("#tabs-4 .bjrTable");
					for(var i in sufferCaseRelationLst){
						//按钮
						var xyrBtn = btnTemplate.clone(true);
						if(i == 0){
							xyrBtn.addClass("btn-primary");
						}else{
							xyrBtn.addClass("btn-default");
						}
						xyrBtn.text(sufferCaseRelationLst[i].criminalPerson.name);
						xyrBtn.attr("objId", sufferCaseRelationLst[i].id);
						xyrBtn.show();
						xyrBtn.insertBefore(btnTemplate);
						//基本信息
						var personTable = personInfoTemplate.clone(true);
						if(i == 0){
							personTable.show();
						}
						personTable.attr("objId", sufferCaseRelationLst[i].id);
						personTable.insertBefore(personInfoTemplate);
						$.each(personTable.find(".valCell"),function(){
							$(this).text(sufferCaseRelationLst[i].criminalPerson[$(this).attr("valName")]);
						})
						//报警人信息
						var bjrTable = bjrTableTemplate.clone(true);
						if(i == 0){
							bjrTable.show();
						}
						bjrTable.attr("objId", sufferCaseRelationLst[i].id);
						bjrTable.insertBefore(bjrTableTemplate);
						$.each(bjrTable.find(".valCell"),function(){
							$(this).text(sufferCaseRelationLst[i][$(this).attr("valName")]);
						})
					}
				}else{
					$("#tabs-4").html("<h2>无此信息</h2>");
				}
				//文书列表
				var writBeanLst = successData.writBeanLst;
				if(writBeanLst != null && writBeanLst.length > 0){
					initWrit(writBeanLst);
				}else{
					$("#tabs-5").html("<h2>无此信息</h2>");
				}
				//并案从案
				var cbcBeanLst = successData.cbcBeanLst;
				if(cbcBeanLst != null && cbcBeanLst.length > 0){
					initCaseTable(cbcBeanLst);
				}else{
					$("#tabs-6").html("<h2>无此信息</h2>");
				}
				//证据笔录
				var zjblBeanLst = successData.zjblBeanLst;
				if(zjblBeanLst != null && zjblBeanLst.length > 0){
					initZjbl(zjblBeanLst);
				}else{
					$("#tabs-9").html("<h2>无此信息</h2>");
				}
				$("#tabs").show();
				//全流程
				var caseExecutionProcessLst = data.caseExecutionProcessLst;
				if(caseExecutionProcessLst != null && caseExecutionProcessLst.length > 0){
					var str = "";
					for(var i in caseExecutionProcessLst){
						str += '<li><span class="icon-red-dot"></span><p class="time-box">' 
							+ caseExecutionProcessLst[i].issuedTime 
							+ '</p><div class="con-box"><span class="arrow"></span>' 
							+ caseExecutionProcessLst[i].stepName;
						if(caseExecutionProcessLst[i].personName != "无"){
							str += "(" + caseExecutionProcessLst[i].personName + ")";
						}
						str += '</div></li>';
					}
					$("#processUl").append(str);
				}
			}
		});
	});
	function initWrit(writBeanLst){
		for(var i in writBeanLst){
			var str = '<li><span class="right color-green1">完成</span>'
				 + '<p><span class="glyphicon glyphicon-open-file color-red2">'
				 + '</span><a class="showWrit" writId="' + writBeanLst[i].id + '" href="#">' + writBeanLst[i].name + '</a></p></li>';
				$("#writLstUl").append(str);
		}
	}
	
	function initZjbl(zjblBeanLst){
		for(var i in zjblBeanLst){
			var str = '<li>'
				 + '<p><span class="glyphicon glyphicon-open-file color-red2">'
				 + '</span><a class="showZjbl" img64="'+zjblBeanLst[i].photoCopy +'" zjblId="' + zjblBeanLst[i].id + '">' + zjblBeanLst[i].baseName + ' ' + zjblBeanLst[i].sort + '</a></p></li>';
				$("#zjblLstUl").append(str);
		}
	}
	
	$(document).on("click",".showZjbl",function(){
		$.layerAlert.img("data:image/png;base64,"+$(this).attr("img64"),400);
//		$.layerAlert.img("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3VJREFUeNrEmctrE1EUxieTtNqa1lpatT5KUeMTUVRcVHAhUgriQnAh7pW6UAktLhQRwYUIBUUE8S+QIogLQVyILtxUUYq0io8i2Fg11kZtG9OH8Tv6TR3GmcncmzE58GMSOnfulzP3nsdtJJ/PGxoWA/WkGtR43PcdjIMvYBRMq04UURRYC5aBBhmrOJdM9BkMgW9hCxQvrQILjHBMPPoGTIQhsBm0aHgsiEcH6VEtgSJoNVhs/F/7AF5ScGCBFWCjz+IP276CfjDl/IPp4bn1JRQnNp9zRoIITIA6o/RWx43oK3AJaDLKZ//MH7N9ngNWaD54G9gPtnL9ij0Dd8Ft8FPhWSsZhnLOTbIWLFIUJm/gKNjH7/0MHeKFTRTbB06rBGfYR/DCLrCaXlCNdYfBQXrrAnjnyDrHwG7wBHQpeFJEPQJZ0/buVcXJcjjAGNblEGfQY+fAQ7AFtKmkYLDUekXyZaHGumvn+MvWevGwbv59j+LzRVPE5Kuo0BC4GWT4egvlXblng0dY8zLRVGsWEfNqKDCIZSiuSjU2mkVkDPFMY0CvNPE1j6s6QedXWdYL5oEdATbTOvBUY44qEVipKVACcBYcZwHrZhL8T9LLNzTmqJSBUU2BUh1forgrYJdjs0loucj8egs81pgjKoF6Z5HFqISbJL1lMJO02NbmA3BWMd3NBmxTp5GxmTRNa2zfX/N5cu0Bz0Er2Kv5/JkYd5dOHJRce4ZR4Ca4zp3ttgaTzPPXFOf4EeNCjysOlKzQCYaZ5gY97svxR3QwZ2fo2aD2OxePaZRWnRR1xEec3a6Ce+CQW1HqY2MmG2qV9JOkZ04pllDd3PknFMaMWh7MBRzQxiqjhzWbikkWOc9OcXuA+3OWB/MKk7XyekdzV/ZxZ7cHLFrzVqx679WXOkxSVoqbQ9d6mR7NAgVryt405SiyUHlfbw0swoYZfpp97hEtk86m6S1jVcwr7fAq6+d+CB2cV/SYphbXk4VGNtDltAGQ9uqL0yG8wmIsZRfndbIgx2IjZRA3wrkLHn3k6eaREosbcIskhY7fEiU4CpFd/Ur1+M25cRKaFY+fTbIsS/s2yAGPgCXELDf+nE9HixQ2wyZ/iJ+NMATO9gjMxQ08LlGxCePvIfpU4CMGzX9DiM1l9x9nZxh3CcRZXj9J8akzyS8BBgBeyuDgjB4cUQAAAABJRU5ErkJggg==",240);
	});
	
	function initKYWPTable(tableIn, dataLst){
		var tb = $.uiSettings.getLocalOTableSettings();
		tb.data = dataLst;
		tb.columnDefs = [
			{
				"targets" : 0,
				"width" : "",
				"title" : "编码",
				"data" : "code",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 1,
				"width" : "",
				"title" : "特征",
				"data" : "feature",
				"render" : function(data, type, full, meta) {
					return "";
				}
			},
			{
				"targets" : 2,
				"width" : "",
				"title" : "名称",
				"data" : "name",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "",
				"title" : "数量",
				"data" : "number",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "",
				"title" : "文书",
				"data" : "paper",
				"render" : function(data, type, full, meta) {
					return "<a href='###' paperId='" + data + "' paperType='" + full.paperType + "' class='paper'>" + full.paperName + "</a>";
				}
			}
//			,
//			{
//				"targets" : 5,
//				"width" : "",
//				"title" : "状态",
//				"data" : "state",
//				"render" : function(data, type, full, meta) {
//					return data;
//				}
//			}
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
		
		tableIn.DataTable(tb);
	}
	
	function initCaseTable(cbcBeanLst){
		var tb = $.uiSettings.getLocalOTableSettings();
			tb.data = cbcBeanLst;
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "50px",
         	    	"title": "序号",
         	    	"className":"table-checkbox",
         	    	"data": "caseCode" ,
         	    	"render": function ( data, type, full, meta ) {
         	    			  return meta.row+1;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return "<a href='###' caseCode='" + data + "' class='showDetail'>" + data + "</a>";
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return "<a href='###' caseCode='" + full.caseCode + "' class='showDetail'>" + data + "</a>";
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "案件类别",
					"data" : "caseSort",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "案件性质",
					"data" : "caseKind",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "主案标识",
					"data" : "principal",
					"render" : function(data, type, full, meta) {
						return data;
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
			
			caseTable = $("#caseTable").DataTable(tb);
	}
})(jQuery);