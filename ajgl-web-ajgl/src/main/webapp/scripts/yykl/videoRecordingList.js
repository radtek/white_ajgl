(function($){
	"use strict";
	
	var askRoomTable = null;
	
	$(document).ready(function() {	
		
	
		/**
		 * 查询按钮事件
		 */
		$(document).on("click","#checkBtn",function(){
			askRoomTable.draw(true);
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#resetLocker",function(){
			resetSearchCondition();
		});
		/**
		 * 刻录事件
		 */
		$(document).on("click",".recordClass",function(){
			window.top.getDsspocxObject().OnOpenDownLoadDlgForThird($(this).attr("formCode"),currentUserCode,dhServerIp,$.base64.encode(currentUserCode),"刻录页面");
		});
		/**
		 * 刻录详情
		 */
		$(document).on("click",".detailClass",function(){
			openRecordingDetail($(this).attr("formId"));
			//wiodow.open(context + '/videoRecording/recordingDetail.action?formCode='+formCode);
		});
		
		intiStatusDictionaryItem();
		initOnloadData();
		
		/**
		 * 案件选择
		 */
		$(document).on("click",".selectAboutCaseLst",function(){
			selectCase();
		});
		
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showDetail",function(){
			var id = $(this).attr("id");
			window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + id);
//			window.top.open(context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + id);
//			openDeile(id);
		});
		
	});
	
	/**
	 * 打开详情页面
	 * @param data
	 * @returns
	 */
	function openDeile(data){
		$.util.topWindow().$.layerAlert.dialog({
			content : context +"/handlingAreaReceipt/showLookHandlingAreaReceiptPage.action?&&justShow=" + $("#justShow").val() + "&&harId=" + data,
			pageLoading : true,
			title:"办案区使用单详情",
			width : "930px",
			height : "520px",
			btn:["返回"],
			callBacks:{				
				btn1:function(index, layero){
					//关闭弹窗
					$.util.topWindow().$.layerAlert.closeWithLoading(index); 
				}
			},
			shadeClose : false,
			success:function(layero, index){
				//弹窗打开成功后执行的操作	
			},
			initData:{  //传递给弹窗界面的参数
				//"zazllxCode" : zazllxCode
			},
			end:function(){ //关闭弹窗后执行的操作
			}
		});
	}
	
	function openRecordingDetail(data){
		$.util.topWindow().$.layerAlert.dialog({
			content : context +  '/videoRecording/recordingDetail.action',
			pageLoading : true,
			title:"刻录情况记录",
			width : "930px",
			height : "520px",
			shadeClose : false,
			success:function(layero, index){
				//弹窗打开成功后执行的操作	
			},
			initData:{  //传递给弹窗界面的参数
				"formCode" : data
			},
			end:function(){ //关闭弹窗后执行的操作
				askRoomTable.draw(true);
			}
		});
	}
	
	function selectCase(){
		var initData = {
				cre$ : $
		}
		window.top.$.layerAlert.dialog({
			content : context +  '/handlingAreaReceipt/showCaseList.action',
			pageLoading : true,
			title:"选择案件",
			width : "960px",
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
	}
	/**
	 * 加载table
	 * @returns
	 */
	function initRecordDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/videoRecording/searchAllRecordFormListByCondition.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "auto",
         	    	"title": "办案区使用单编号",
         	    	"className":"table-checkbox",
         	    	"data": "formCode" ,
         	    	"render": function ( data, type, full, meta ) {
         	    			  return '<a href="###" class="showDetail" id="'+full.id+'" >'+data+'</a>';
         	    	}
				},
				{
					"targets" : 1,
					"width" : "auto",
					"title" : "进入办案区时间",
					"data" : "inTime",
					"render" : function(data, type, full, meta) {
						return $.date.timeToStr(data, "yyyy-MM-dd HH:mm:ss"); ;
					}
				},
				{
					"targets" : 2,
					"width" : "auto",
					"title" : "办案民警",
					"data" : "policeName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "auto",
					"title" : "所属案件",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "auto",
					"title" : "被问讯人姓名",
					"data" : "askPerson",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "身份证号",
					"data" : "askPersonID",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" :6,
					"width" : "",
					"title" : "是否已刻录",
					"data" : "ifRecord",
					"render" : function(data, type, full, meta) {
						if(data==$.common.Constant.SF_S()){
							return '是';
						}
						return '否';
					}
				},
				{
					"targets" : 7,
					"width" : "auto",
					"title" : "操作",
					"data" : "ifRecord",
					"render" : function(data, type, full, meta) {
						var str="";
						str+='<button formCode="'+full.formCode+'" class="btn btn-primary btn-sm recordClass">刻录</button>';
						if(data==$.common.Constant.SF_S()){
							str+='<button formId="'+full.id+'" class="btn btn-primary btn-sm detailClass">刻录详情</button>';
						}
						return str;
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
				 d["harSearchBean.code"]= $("#code").val()=='模糊查询'?'':$("#code").val();
				 d["harSearchBean.room"]= $.select2.val("#roomLst"); //房间
				 d["harSearchBean.police"]= $("#police").val()=='模糊查询'?'':$("#police").val();//办案民警
				 d["harSearchBean.askPerson"]= $("#askPerson").val()=='模糊查询'?'':$("#askPerson").val();//被询问人
				 d["harSearchBean.idNum"]= $("#idNum").val()=='模糊查询'?'':$("#idNum").val();//身份证号
				 d["harSearchBean.aboutCase"]= $("#aboutCaseLst").val();  //所属案件
//				 d["harSearchBean.accessDateStart"]= $.laydate.getDate("#accessDate", "start");//开始时间
//				 d["harSearchBean.accessDateEnd"]= $.laydate.getDate("#accessDate", "end");
				 d["harSearchBean.accessDateStart"]=$('#fixed_start').val();
				 d["harSearchBean.accessDateEnd"]=$('#fixed_end').val();
				 d["harSearchBean.ifRecord"]= $.select2.val("#recordstatus");
				 d["harSearchBean.state"]= $.common.Constant.SYDZT_YWC ;//已完成的使用单
				 
			};
			tb.paramsResp = function(json) {
				var askRoomBeanList = json.resultMap.resultList;
				json.recordsTotal = json.resultMap.totalNum;
				json.recordsFiltered = json.resultMap.totalNum;
				json.data = askRoomBeanList;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			askRoomTable = $("#askRoomTable").DataTable(tb);
	}
	
	/**
	 * 
	 * @returns
	 */
	function initOnloadData(){
		$.ajax({
			url:context +'/videoRecording/findRoomList.action',
			type:'post',
			dataType:'json',
			success:function(successData){
				$.select2.addByList("#roomLst", successData.resultMap.roomLst, "id", "name", true, true);
			}
		})
	};	
	
	
	/**
	 * 初始化状态字典项字段
	 * @returns
	 */
	function intiStatusDictionaryItem(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			dataType:'json',
			data:{dictionaryType : $.common.Constant.SF()},
			success:function(successData){
				if(successData.dictionaryItemLst != null){
					$.select2.addByList("#recordstatus", successData.dictionaryItemLst,"id","name",true,true);//设置下拉框选项
					$.select2.val("#recordstatus",$.common.Constant.SF_F(),true);//设置默认值
					initRecordDataTable();
				}
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 修改讯（询）问室状态
	 * @param askRoomIdList 询问室id集合
	 * @param status 要更改的状态
	 * @param msg 成功后弹窗显示的说明
	 */
	function updateAskRoomStatus(askRoomIdList, status, msg){
		var gData = new Object();
		$.util.objToStrutsFormData(askRoomIdList, "askRoomIdList", gData);
		$.util.objToStrutsFormData(status, "status", gData);
		
		$.ajax({
			url:context +'/askRoom/updateAskRoomStatus.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				window.top.$.layerAlert.alert({msg:msg}) ;
				askRoomTable.draw(false);
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 删除讯（询）问室
	 * @param askRoomIdList 讯（询）问室id集合
	 */
	function deleteAskRoomByIds(askRoomIdList){
		var gData = new Object();
		$.util.objToStrutsFormData(askRoomIdList, "askRoomIdList", gData);
		
		$.ajax({
			url:context +'/askRoom/deleteAskRoomByIds.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				var failAskRoomNameList = successData.askRoomNameList;
				var length = failAskRoomNameList.length;
				var msg = "";
				if(length != 0){
					$.each(failAskRoomNameList,function(n,name){
						msg += name;
						if(n<length-1){
							msg += "、";
						}
					});
					msg = msg.substring(0,msg.length-1);
					msg += " 使用过的讯（询）问室不可删除！";
				}else{
					msg = "删除成功！";
				}
				window.top.$.layerAlert.alert({msg:msg}) ;
				askRoomTable.draw(false);
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#code").val("模糊查询");
		$("#askPerson").val("模糊查询");
		$("#police").val("模糊查询");
		$("#idNum").val("模糊查询");
		$("#code").attr("style","color:#aaa;");
		$("#askPerson").attr("style","color:#aaa;");
		$("#police").attr("style","color:#aaa;");
		$("#idNum").attr("style","color:#aaa;");
		$("#aboutCaseLst").val("");
		$("#lawCaseName").val("");
		$.laydate.reset("#dateRangeId_2");
		$.select2.val("#recordstatus",$.common.Constant.SF_F());//设置默认值
		$.select2.val("#roomLst","");//设置默认值
		askRoomTable.draw(false);
	}
	
	jQuery.extend($.common, { 
//		showHarSaveBtn:function(){
//			harSaveBtn = true;
//			//window.$.util.ableA(".layui-layer-btn .layui-layer-btn0", false, {"class":"btn btn-primary btn-sm"});
//			//$.util.ableA(".layui-layer-btn .layui-layer-btn0", true);
//		},
//		closeWindow:function(index){
//			handlingAreaReceiptTable.draw(false);
//			window.top.layer.close(index);
//		},
		setCase:function(code, name, i){
			$("#aboutCaseLst").val(code);
			$("#lawCaseName").val(name);
			window.top.layer.close(i);
		}
	});
})(jQuery);