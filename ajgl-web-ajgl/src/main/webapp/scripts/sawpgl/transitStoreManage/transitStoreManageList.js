(function($){
	var transitStoreManageTable = null;
	
	$(document).ready(function() {
		
		initTransitStoreManageDataTable();
		selectStoreLocker();
		
		//选择保管区
		$(document).on("change","#storageArea",function(){
			var id = $.select2.val("#storageArea");
			$("#storageLocker").html("");
			$.ajax({
				url:context +"/transitStoreLocker/queryTransitStoreLocker.action",
				data:{storageAreaId : id,start : 0,length:100000},
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
			transitStoreManageTable.draw(true);
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
			$("#policeNameOrNum").val("");
			$("#caseNameOrCode").val("");
			$.select2.val("#isFree", "全部");
			transitStoreManageTable.draw(true);
			
		}
		
		/**
		 * 刷新
		 */
		$(document).on("click","#refresh",function(){
			window.location.href = $.util.fmtUrl(context + "/transitStoreManage/toTransitStoreManage.action");
		});
		
		/**
		 * 导出EXCEL
		 */
		$(document).on('click','#excel',function(){
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
    		d["policeNameOrNum"] = $("#policeNameOrNum").val();
    		d["caseNameOrCode"] = $("#caseNameOrCode").val();
    		d["isFree"] =  $.select2.val("#isFree");
			
			var form = $.util.getHiddenForm(context +'/transitStoreManage/transitStoreManageExportExcel.action', d);
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
				$.select2.addByList("#storageArea",list,"id","name",true,true);
			}
		});
	}
	
	function initTransitStoreManageDataTable(){
		var xh = 1;
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/transitStoreManage/queryTransitStoreManageList.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "5%",
         	    	"title": "序号",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    		return xh++;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "10%",
					"title" : "储物箱",
					"data" : "address",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "10%",
					"title" : "是否空闲",
					"data" : "status",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "10%",
					"title" : "当前存放物品",
					"data" : "goodsList",
					"render" : function(data, type, full, meta) {
						var goodsList = full.goodsList;
						var td = "";
						if($.util.exist(goodsList) && goodsList.length > 0){
							$.each(goodsList,function(s,ssb){
								td = '<span>' + ssb + '</span>' + "<br/>";
							});
						}
						td += td;
						return td;
					}
				},
				{
					"targets" : 4,
					"width" : "12%",
					"title" : "在库总件数",
					"data" : "inStorageNum",
					"render" : function(data, type, full, meta) {
						if(data==null||data==0){
							return "";
						}
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "10%",
					"title" : "对应案件编号",
					"data" : "caseCode",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "10%",
					"title" : "对应案件名称",
					"data" : "caseName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 7,
					"width" : "12%",
					"title" : "办案民警",
					"data" : "handlingPolices",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 8,
					"width" : "15%",
					"title" : "对应犯罪嫌疑人/物品持有人",
					"data" : "suspectName",
					"render" : function(data, type, full, meta) {
						var str =  "<span >" + (data==null?'':data) + "</span>"
						+ "<br/>" + "<span >" + (full.suspectIdCardNum==null?'':full.suspectIdCardNum) + "</span>";
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
				 var storageArea = $.select2.val("#storageArea");
				 var optionArray = $("#storageLocker").select2("val");
				 if($.util.exist(optionArray)){
	        			$.each(optionArray,function(o,option){
	        				d["storageCodeList[" + o + "]"] = option;
	        			});
	        		}
		    		d["areaCode"] = storageArea;
		    		d["suspectNameOrIdCard"] = $("#suspectNameOrIdCard").val();
		    		d["policeNameOrNum"] = $("#policeNameOrNum").val();
		    		d["caseNameOrCode"] = $("#caseNameOrCode").val();
		    		d["isFree"] =  $.select2.val("#isFree");
			};
			tb.paramsResp = function(json) {
				var storageShelfBeanList = json.storageShelfBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = storageShelfBeanList;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			transitStoreManageTable = $("#transitStoreManageTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
	});	
})(jQuery);