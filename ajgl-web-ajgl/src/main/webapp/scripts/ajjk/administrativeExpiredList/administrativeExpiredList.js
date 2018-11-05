(function($) {
	"use strict";
	var caseTable = null;
	var peoplelst = null;
	$(document).ready(function() {
		resetDate("#caseTime");
		$(document).on("click" , "#sponsorDiv", function(e){
			var tree = $.zTreeMenu.getTree("sponsor") ;
			tree.showMenu() ;
		});
//		
//		/**
//		 * 导出按钮事件
//		 */
//		$(document).on("click","#exportExcel",function(){
//			var demo = $.validform.getValidFormObjById("validformId") ;
//			if(! $.validform.check(demo)){
//				return;
//			}
//			var d = {};
//			d["searchBean.searchDate"] = $.date.endRangeByTime($.laydate.getTime("#searchDate","date"), "yyyy-MM-dd");
//			d["searchBean.attention"] = $.select2.val("#attention");
//			d["searchBean.sponsor"] = $("#sponsorId").val();
//			d["searchBean.caseName"] = $("#caseName").val();
//			d["searchBean.remark"] = $("#remark").val();
//			var form = $.util.getHiddenForm(context +'/administrativeExpiredList/exportExcel.action', d);
//			$.util.subForm(form);
//		});
		
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			var demo = $.validform.getValidFormObjById("validformId") ;
			if(! $.validform.check(demo)){
				return;
			}
			caseTable.draw();
		});
		/**
		 * 重置
		 */
		$(document).on("click",".reset",function(){
			//resetDate("#caseTime");
			$.laydate.reset("#caseTime");
			$.select2.val("#attention", "否");
			$("#sponsor").val("");
			$("#sponsorId").val("");
			$("#caseName").val("");
			$("#caseCode").val("");
			$("#remark").val("");
			$(".btnShow").show();
			$(".btnChange").hide();
			
			$("#disposePerson").val("");
			
			caseTable.draw();
		});
		/**
		 * 修改按钮事件
		 */
		$(document).on("click","#modify",function(){
			$(".showDiv").hide();
			$(".changeDiv").show();
			$(".btnShow").hide();
			$(".btnChange").show();
		});
		
		/**
		 * 保存按钮事件
		 */
		$(document).on("click","#save",function(){
			var btn = $(this);
			btn.attr("disabled", "disabled");
			var lst = new Array;
			var trs = $("#caseTable tbody").children();
			for (var i = 0; i <= trs.length-1; i++) {
				var tds = trs.eq(i).children();
				if(tds[0].className == "dataTables_empty"){
					continue;
				}
				if($.util.exist($(tds[0]).find("span")) && $(tds[0]).find("span").hasClass("notFirstCol")){
					continue;
				}
				var tdOjb = {};
				tdOjb.caseCode = $(tds.eq(0).find("a")[0]).attr("casecode");
				tdOjb.doPerson = tds.eq(3).find("select").val();
				lst.push(tdOjb);
			}
			var dataMap = {};
			$.util.objToStrutsFormData(lst, "dataLst", dataMap);
			$.ajax({
				url:context +'/administrativeExpiredList/saveVerifierAndRemark.action',
				type:'post',
				data: dataMap,
				dataType:'json',
				success:function(successData){
					$(".btnShow").show();
					$(".btnChange").hide();
					btn.attr("disabled", false);
					caseTable.draw(false);
				}
			})
		});
		
		/**
		 * 取消按钮事件
		 */
		$(document).on("click","#cancel",function(){
			$(".btnShow").show();
			$(".btnChange").hide();
			caseTable.draw();
		});
		
		
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showCase",function(){
			window.parent.open($.util.fmtUrl(context +"/caseSearch/showCaseDetail.action?caseCode=" + $(this).attr("caseCode")));
		});
		
		$.ajax({
			url:context +'/penalExpiredList/initData.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				peoplelst = successData.personBeanLst;
				initTable();
			}
		})
		$.zTreeMenu.init("sponsor", context + "/caseSearch/queryUnit.action", {
			async:{
				enable:true,
			},
			callBacks:{
				formatNodeData:function(nodeData){
					nodeData.iconSkin = "dw" ;
		  		}
			}
		},{
			check: {
				enable: false
			},
			callback: {
				onClick:function(event, treeId, treeNode){
					var tobj = $.zTreeMenu.getTree(treeId);
					if($("#sponsorId").val() == treeNode.diyMap["code"]){
						$(tobj.inputDom).val("");
						$("#sponsorId").val("");
						return;
					}
					$(tobj.inputDom).val(treeNode.name);
					$("#sponsorId").val(treeNode.diyMap["code"]);
		  		}
			}
		});
	});
	
	/**
	 * 初始化table
	 */
	function initTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/administrativeExpiredList/searchAdministrativeCaseByPage.action";
			tb.columnDefs = [
				{
					"targets" : 0,
					"width" : "10%",
					"title" : "案件编号<br/>案件名称",
					"data" : "caseInfo.caseCode",
					"render" : function(data, type, full, meta) {
						var str =  "<a href='###' caseCode='" + data + "' class='showCase'>" + data + "</a>"
								+ "<br/>" + "<a href='###' caseCode='" + data + "' class='showCase'>" + full.caseInfo.caseName + "</a>";
						return str;
					}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "办案单位",
					"data" : "caseInfo.handlingUnit",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "办案民警",
					"data" : "caseInfo.handlingPolice",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "法制审核人",
					"data" : "caseInfo.doPeople",
					"render" : function(data, type, full, meta) {
						var str = '<div class="showDiv">' + (data == null? "" : data) + '</div>'
						+ '<div style="display:none;" class="changeDiv">'
						+ '<select caseCode="' + full.caseCode + '" id="verifier' + meta.row + '" class="form-control input-sm select2 allowClear">'
					    + '<option value=""></option>';
						for(var i in peoplelst){
							if(peoplelst[i].name == data){
								str += '<option value="' + peoplelst[i].id + '" selected>' + peoplelst[i].name + '</option>';
							}else{
								str += '<option value="' + peoplelst[i].id + '">' + peoplelst[i].name + '</option>';
							}
						}
						str += '</select></div>';
						return str;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "嫌疑人",
					"data" : "suspectName",
					"render" : function(data, type, full, meta) {
						if($.util.exist(data)){
							return "<span class='notFirstCol'>" + data + "</span>";
						}else{
							return "";
						}
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "进入办案区时间",
					"data" : "enterAreaTime",
					"render" : function(data, type, full, meta) {
						if($.util.exist(data)){
							return $.date.dateToStr(new Date(data),"yyyy-MM-dd");
						}else{
							return "";
						}
					}
				},
				{
					"targets" : 6,
					"width" : "10%",
					"title" : "行政拘留",
					"data" : "administrationArrest",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 7,
					"width" : "",
					"title" : "责令社区戒毒",
					"data" : "communityDrugDeadline",
					"render" : function(data, type, full, meta) {
						if($.util.exist(data)){
							return $.date.dateToStr(new Date(data),"yyyy-MM-dd");
						}else{
							return "";
						}
					}
				},
				{
					"targets" : 8,
					"width" : "",
					"title" : "强制隔离戒毒",
					"data" : "isolationDrugDeadline",
					"render" : function(data, type, full, meta) {
						if($.util.exist(data)){
							return $.date.dateToStr(new Date(data),"yyyy-MM-dd");
						}else{
							return "";
						}
					}
				},
				{
					"targets" : 9,
					"width" : "",
					"title" : "是否生成结案报告",
					"data" : "caseInfo.ifCloseCase",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 10,
					"width" : "",
					"title" : "是否归档",
					"data" : "caseInfo.ifArchive",
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
			//请求参数
			tb.paramsReq = function(d, pagerReq){
				d["searchBean.caseTimeStart"] = $.laydate.getTime("#caseTime", "start");
				d["searchBean.caseTimeEnd"] = $.date.endRangeByTime($.laydate.getTime("#caseTime", "end"), "yyyy-MM-dd");
				var attention = $.select2.val("#attention");
				if( attention == "全部"){
					attention = null;
				}
				d["searchBean.attention"] = attention ;
				d["searchBean.sponsor"] = $.select2.val("#sponsorId");
				d["searchBean.caseName"] = $("#caseName").val();
				d["searchBean.caseCode"] = $("#caseCode").val();
				d["searchBean.remark"] = $("#remark").val();
				
				d["searchBean.disposePerson"] = $("#disposePerson").val();
			};
			tb.paramsResp = function(json) {
				$(".btnShow").show();
				$(".btnChange").hide();
				json.data = json.apsBeanLst;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
			};
			tb.drawCallback = function(settings) {
//				var api = this.api();
//	            var rows = api.rows( {page:'current'} ).nodes();
//	            var tempData = null;
//	 			var tr = null;
//	 			var num = 1;
//	 			var arr = [8,2,1,0];
//	 			for(var col in arr){
//	 				tempData = null;
//	 				num = 1;
//	 				api.column(0, {page:'current'} ).data().each( function ( thisData, i ) {
//		            	tr = $(rows[i]);
//		                if (tempData !== thisData ) {
//		                	$(".rowspanFlag").attr("rowspan",num);
//		                	$(".rowspanFlag").removeClass("rowspanFlag");
//		                	num = 1;
//		                    $("td:eq(" + arr[col] + ")",tr).addClass("rowspanFlag");
//		                    tempData = thisData;
//		                }else{
//		                	$("td:eq(" + arr[col] + ")",tr).remove();
//		                	num++;
//		                }
//		                if(i == rows.length-1){
//		                	$(".rowspanFlag").attr("rowspan",num);
//		                	$(".rowspanFlag").removeClass("rowspanFlag");
//		                }
//		            } );
//	 			}
			};
			caseTable = $("#caseTable").DataTable(tb);
	}
	function resetDate(selecter){
		var date = new Date();
		var endVal = $.date.dateToStr(date, "yyyy-MM-dd");
		date.setDate(1);
		var startVal = $.date.dateToStr(date, "yyyy-MM-dd");
		//$.laydate.setRange(startVal, endVal, selecter, "yyyy-MM-dd");
	}
})(jQuery);