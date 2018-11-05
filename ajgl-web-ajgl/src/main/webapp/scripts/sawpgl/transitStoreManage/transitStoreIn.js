(function($){
	"use strict";
	
	var transitStoreInTable = null;
	
	$(document).ready(function() {	
		/**
		 * 新建按钮事件
		 */
		$(document).on("click","#add",function(){
			window.location.href = $.util.fmtUrl(context + "/transitStoreManage/toNewRecord.action");					
		});
			
		/**
		 * 查看入库单详情
		 */
		$(document).on("click",".detail",function(){
			var code = $(this).attr("name");
			window.location.href = $.util.fmtUrl(context + "/transitStoreManage/viewTransitStoreIn.action?code="+code);					
		});
		
		/**
		 * 删除按钮事件
		 */
		$(document).on("click","#delete",function(){
			var arr = $.icheck.getChecked("rkd");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"请至少选择一条数据进行操作。"}) ;
				return false;
			}
			
			var formIdList = new Array();
			$.each(arr,function(a,val){
				var id = $(val).val();
				formIdList.push(id);
			});
			
			window.top.$.layerAlert.confirm({
				msg:"删除后不可恢复，确定要删除吗？",
				title:"删除",	  //弹出框标题
				width:'300px',
				hight:'200px',
				shade: [0.5,'black'],  //遮罩
				icon:0,  //弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
				yes:function(index, layero){
					//点击确定按钮后执行
					deleteByIds(formIdList);
				}
			});
		});
		
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			transitStoreInTable.draw(true);
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			resetSearchCondition();
		});
		
		/**
		 * 刷新
		 */
		$(document).on("click","#refresh",function(){
			window.location.href = $.util.fmtUrl(context + "/transitStoreManage/toTransitStoreIn.action");
		});
		/**
		 * 查看详情按钮事件
		 */
		$(document).on("click",".showCaseDetail",function(){
			window.parent.open($.util.fmtUrl(context +"/caseSearch/showCaseDetail.action?caseCode=" + $(this).text()));
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
			var startTime =  $.laydate.getDate("#rkDate", "start");	//时间开始	//时间开始
			var endTime = $.date.endRangeByTime($.laydate.getTime("#rkDate", "end"),"yyyy-MM-dd");	    //时间结束
			var isShow = 0;
			var obj = $.icheck.getChecked("isShow");
			if (obj.length > 0) {
				isShow = 1; //显示我的入库单
			}
			$.util.subForm($.util.getHiddenForm(context + "/transitStoreManage/exportExcel.action?code="+$("#code").val()+"&&caseCode="+$("#caseCode").val()
					+"&&suspectName="+$("#suspectName").val()
					+"&&startDate="+startTime+"&&endDate="+endTime+"&&isShow="+isShow));
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
		});
		
		initTransitStorageInDataTable();
	});
	
	function initTransitStorageInDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/transitStoreManage/queryTransitStoreInList.action";
			tb.columnDefs = [
//				{
//					"targets": 0,
//         	    	"width": "5%",
//         	    	"title": "选择",
//         	    	"className":"table-checkbox",
//         	    	"data": "id" ,
//         	    	"render": function ( data, type, full, meta ) {
//         	    		var a = '<input type="checkbox" name="rkd" class="icheckbox" value="'+data+'"/>' ;
//         	    		if (full.editOrNot) {
//         	    			a = '<input type="checkbox" name="rkd" class="icheckbox" disabled="disabled" value="'+data+'"/>' ;
//         	    		}
//   	    			    return a;
//         	    	}
//				},
				{
					"targets" : 0,
					"width" : "10%",
					"title" : "入库单编号",
					"data" : "storageInCode",
					"render" : function(data, type, full, meta) {
						var td = '<a class="detail bsfCode" name="' + full.storageInCode + '" href="###">' + data + '</a>&nbsp;&nbsp;';
	 	    				td += '<a href="###" class="qrFile" name="' + full.id + '" fileId="' + data + '"><img src="../images/photo/ewm.png" width="30" height="30"></a>';
	 	    			return td;
					}
				},
				{
					"targets" : 1,
					"width" : "12%",
					"title" : "入库时间",
					"data" : "storageInDateTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "10%",
					"title" : "对应案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return '<a class="showCaseDetail" href="###">' + data + '</a>';
						
					}
				},
				{
					"targets" : 3,
					"width" : "10%",
					"title" : "对应案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "12%",
					"title" : "办案民警",
					"data" : "solvePolice",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "15%",
					"title" : "对应嫌疑人/物品持有人",
					"data" : "objectOwnerPerson",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "15%",
					"title" : "备注",
					"data" : "remark",
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
				if($.common.isFullConditionSearch()){
					var fmt = $.laydate.getFmt("#rkDate") ;
					d["transitStorageInBean.code"] = $("#code").val();
					d["transitStorageInBean.caseCode"] = $("#caseCode").val();
					d["transitStorageInBean.suspectName"] = $("#suspectName").val();
					d["transitStorageInBean.startDate"] = $.laydate.getDate("#rkDate", "start");	//时间开始
					var end=$.laydate.getDate("#rkDate", "end");
					d["transitStorageInBean.endDate"] =$.date.endRange(end,"yyyy-MM-dd HH:mm");
					var obj = $.icheck.getChecked("isShow");
					if (obj.length > 0) {
					d["isShow"] = 1; //显示我的入库单
					} else{
						d["isShow"] = 0; 
					}
				}else{
					var codeText = $(".simpleCode").val() == "入库单编号模糊查询" ? "" : $(".simpleCode").val();
					d["transitStorageInBean.code"] = codeText;
					d["isShow"] = 0; 
				}
			};
			tb.paramsResp = function(json) {
				var tempStorageInInfoBeanList = json.tempStorageInInfoBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = tempStorageInInfoBeanList;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			transitStoreInTable = $("#transitStoreInTable").DataTable(tb);
	}
	
	
	
	/**
	 * 删除入库单
	 * @param formIdList 入库单id集合
	 */
	function deleteByIds(formIdList){
		var gData = new Object();
		$.util.objToStrutsFormData(formIdList, "formIdList", gData);
		
		$.ajax({
			url:context +'/transitStoreManage/deleteByIds.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				window.top.$.layerAlert.alert({msg:"删除成功。",icon:1}) ;
				transitStoreInTable.draw(true);
			},
			error:function(errorData){
				window.top.$.layerAlert.alert({msg:"使用过的入库单不可删除。",icon:0}) ;
			}
		});
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#code").val("");
		$("#caseCode").val("");
		$("#suspectName").val("");
		$.laydate.reset("#rkDate");
		transitStoreInTable.draw(true);
		
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 

	});	
})(jQuery);