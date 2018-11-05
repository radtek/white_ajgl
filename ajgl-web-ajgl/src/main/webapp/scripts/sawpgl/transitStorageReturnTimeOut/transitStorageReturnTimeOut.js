(function($){
	var returnTimeOutTable = null;
	
	$(document).ready(function() {
		
		initTransitStoreManageDataTable();
		selectStoreLocker();
		
		//选择保管区
		$(document).on("change","#storageArea",function(){
			var id = $.select2.val("#storageArea");
			$("#storageLocker").html("");
			$.ajax({
				url:context +"/transitStoreLocker/queryTransitStoreLocker.action",
				data:{storageAreaCode : id,start : 0,length:100000},
				type:"post",
				dataType:"json",
				success:function(successData){
					var list = successData.articleLockerBeanList;
					$.select2.addByList("#storageLocker",list,"code","location",true,true);
				}
			});
		});
		
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".search",function(){
			returnTimeOutTable.draw(true);
		});
	
		/**
		 * 重置按钮事件
		 */
		$(document).on("click",".reset",function(){
			resetSearchCondition();
		});
		
		/**
		 * 重置查询条件
		 */
		function resetSearchCondition(){
			$.select2.selectByOrder("#storageArea",0,true);
			$.select2.selectByOrder("#storageLocker",0,true);
			$("#suspectNameOrIdCard").val("");
			$("#code").val("");
			$("#caseNameOrCode").val("");
			$.select2.val("#isFree", "全部");
			returnTimeOutTable.draw(true);
			
		}
		
		/**
		 * 刷新
		 */
		$(document).on("click",".reloadBtn",function(){
			window.location.href = $.util.fmtUrl(context + "/transitStorageReturnTimeOut/toTransitStorageReturnTimeOut.action");
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','.excel',function(){
			 var d=new Object();
			
			 var storageArea = $.select2.val("#storageArea");
			 var optionArray = $("#storageLocker").select2("val");
			 if($.util.exist(optionArray)){
       			$.each(optionArray,function(o,option){
       				d["storageCodeList[" + o + "]"] = option;
       			});
       		}
    		d["areaCode"] = storageArea;
    		d["suspectNameOrIdCard"] = $("#suspectNameOrIdCard").val();
    		d["code"] = $("#code").val();
    		d["caseNameOrCode"] = $("#caseNameOrCode").val();
			var form = $.util.getHiddenForm(context +'/transitStorageReturnTimeOut/exportExcel.action', d);
			$.util.subForm(form);
		});	
	});
	
	/**
	 * 初始化保管区
	 * @returns
	 */
	function selectStoreLocker(){
		$.ajax({
			url:context +'/transitStoreArea/findTransitStoreAreaByFrame.action',
			type:'post',
			dataType:'json',
			data:{start : 0,length:100000},
			success:function(json){
				var list = json.storageAreaBeanList;
				$("#storageArea").empty();
				$.select2.addByList("#storageArea",list,"code","name",true,true);
			}
		});
	}
	
	function initTransitStoreManageDataTable(){
		var xh = 1;
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/transitStorageReturnTimeOut/queryTransitStorageReturnTimeOut.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "",
         	    	"title": "序号",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    		return xh++;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "超时时长",
					"data" : "storageInDateTime",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "对应案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "对应案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "",
					"title" : "办案民警",
					"data" : "solvePolice",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "对应犯罪嫌疑人/物品持有人",
					"data" : "objectOwnerPerson",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "",
					"title" : "物品名称",
					"data" : "tempObjectBeanList",
					"render" : function(data, type, full, meta) {
						var str="";
						var kg="&nbsp;&nbsp;"
						for(var i=0;i<data.length;i++){
							if(i!=0){
								str+='</br>';
							}
							str+=data[i].objectName+kg+data[i].objectCharacter+kg+data[i].inThisNum+data[i].measureUnit;
						}
						return str;
					}
				},
				{
					"targets" : 7,
					"width" : "",
					"title" : "所在物证保管区",
					"data" : "inStorageArea",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 8,
					"width" : "",
					"title" : "储物箱",
					"data" : "inStorageSelf",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 9,
					"width" : "",
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
				 var storageArea = $.select2.val("#storageArea");
				 var optionArray = $("#storageLocker").select2("val");
				 if($.util.exist(optionArray)){
	        			$.each(optionArray,function(o,option){
	        				d["storageCodeList[" + o + "]"] = option;
	        			});
	        		}
		    		d["areaCode"] = storageArea;
		    		d["suspectNameOrIdCard"] = $("#suspectNameOrIdCard").val();
		    		d["code"] = $("#code").val();
		    		d["caseNameOrCode"] = $("#caseNameOrCode").val();
			};
			tb.paramsResp = function(json) {
				var returnTimeOutList = json.returnTimeOutList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = returnTimeOutList;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			returnTimeOutTable = $("#returnTimeOutTable").DataTable(tb);
	}
	
})(jQuery);