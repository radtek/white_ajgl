(function($){
	"use strict";
	
	var illegalTypeTable = null;
	
	$(document).ready(function() {	
		/**
		 * 新建按钮事件
		 */
		$(document).on("click","#new",function(){
			window.top.$.layerAlert.dialog({
				content : context +  '/illegalType/toIllegalTypeNew.action',
				pageLoading : true,
				title:"新增违规类型",
				width : "500px",
				height : "430px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.save();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		initData:{
	    			id : null
	    		},
	    		end:function(){
	    			illegalTypeTable.draw(true);
	    		}
			});
		});
		/**
		 * 修改按钮事件
		 */
		$(document).on("click","#update",function(){
			var arr = $.icheck.getChecked("illegalType");
			if(arr.length != 1){
				window.top.$.layerAlert.alert({msg:"请选择一条记录进行操作。"}) ;
				return false;
			}
			var id = $(arr[0]).val(); 
			window.top.$.layerAlert.dialog({
				content : context +  '/illegalType/toIllegalTypeNew.action',
				pageLoading : true,
				title:"修改违规类型",
				width : "500px",
				height : "400px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.save();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		initData:{
	    			id : id
	    		},
	    		end:function(){
	    			illegalTypeTable.draw(true);
	    		}
			});
		});
		/**
		 * 启用按钮事件
		 */
		$(document).on("click","#enable",function(){
			var arr = $.icheck.getChecked("illegalType");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"请至少选择一条记录进行操作。"}) ;
				return false;
			}
			var idList = new Array();
			var flag = false;
			$.each(arr,function(i,val){
				var id = $(arr[i]).val();
				var status = $(arr[i]).attr("status");
				if(status == $.common.Constant.TY()){
					idList.push(id);
				}else{
					flag = true;
					return false;
				}
			});
			if(flag){
				window.top.$.layerAlert.alert({msg:"只能启用 停用 状态的违规类型。"}) ;
				return false;
			}
			updateillegalTypeStatus(idList, $.common.Constant.QY(), "启用成功。");
		});
		/**
		 * 停用按钮事件
		 */
		$(document).on("click","#disable",function(){
			var arr = $.icheck.getChecked("illegalType");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"请至少选择一条记录进行操作。"}) ;
				return false;
			}
			var idList = new Array();
			var flag = false;
			$.each(arr,function(i,val){
				var id = $(arr[i]).val();
				var status = $(arr[i]).attr("status");
				if(status == $.common.Constant.QY()){
					idList.push(id);
				}else{
					flag = true;
					return false;
				}
			});
			if(flag){
				window.top.$.layerAlert.alert({msg:"只能停用 启用 状态的违规类型。"}) ;
				return false;
			}
			updateillegalTypeStatus(idList, $.common.Constant.TY(), "停用成功。");
		});
		/**
		 * 删除按钮事件
		 */
		$(document).on("click","#delete",function(){
			var arr = $.icheck.getChecked("illegalType");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"请至少选择一条记录进行操作"}) ;
				return false;
			}
			
			var idList = new Array();
			$.each(arr,function(i,val){
				var id = $(arr[i]).val();
				idList.push(id);
			});
			window.top.$.layerAlert.confirm({
				msg:"删除后不可恢复，确定要删除吗？",
				title:"删除",	  //弹出框标题
				width:'300px',
				hight:'200px',
				shade: [0.5,'black'],  //遮罩
				icon:0,  
				shift:1,  //弹出时的动画效果  有0-6种
				yes:function(index, layero){
					//点击确定按钮后执行
					deleteIllegalTypeByIds(idList);
				}
			});
		});
		
		initIllegalTypeDataTable();
	});
	
	function initIllegalTypeDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/illegalType/queryIllegalTypeList.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "50px",
         	    	"title": "选择",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    		      var a = '<input type="checkbox" name="illegalType" class="icheckbox" value="'+data+'" status="'+full.status+'" code="'+full.code+'"/>' ;
         	    		      if (full.isSystemData == $.common.Constant.SF_S()) {
         	    		    	  a = '<input type="checkbox" name="illegalType" class="icheckbox disabled" disabled="disable" value="'+data+'" status="'+full.status+'" code="'+full.code+'"/>' ;
         	    		      } 
         	    			  return a;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "100px",
					"title" : "编号",
					"data" : "code",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "200px",
					"title" : "违规类型",
					"data" : "name",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "150px",
					"title" : "状态",
					"data" : "statusName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "250px",
					"title" : "备注",
					"data" : "description",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "150px",
					"title" : "最新修改人",
					"data" : "operator",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 6,
					"width" : "150px",
					"title" : "最新修改时间",
					"data" : "updatedTimeStr",
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
			};
			tb.paramsResp = function(json) {
				var illegalTypeBeanList = json.illegalTypeBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = illegalTypeBeanList;
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			illegalTypeTable = $("#illegalTable").DataTable(tb);
	}
	
	/**
	 * 修改违规类型状态
	 * @param illegalTypeIdList id集合
	 * @param status 要更改的状态
	 * @param msg 成功后弹窗显示的说明
	 */
	function updateillegalTypeStatus(illegalTypeIdList, status, msg){
		var gData = new Object();
		$.util.objToStrutsFormData(illegalTypeIdList, "idList", gData);
		$.util.objToStrutsFormData(status, "status", gData);
		
		$.ajax({
			url:context +'/illegalType/updateIllegalTypeStatus.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				window.top.$.layerAlert.alert({msg:msg,icon:1}) ;
				illegalTypeTable.draw(true);
			}
		});
	}
	
	/**
	 * 删除
	 * @param illegalTypeIdList id集合
	 */
	function deleteIllegalTypeByIds(idList){
		var gData = new Object();
		$.util.objToStrutsFormData(idList, "idList", gData);
		
		$.ajax({
			url:context +'/illegalType/deleteIllegalTypeByIds.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				var name = successData.name;
				var msg;
				if ($.util.isBlank(name)) {
					msg = "删除成功。"
				} else {
					msg = "["+name+"]使用中，不可删除。";
				}
				window.top.$.layerAlert.alert({msg:msg}) ;
				illegalTypeTable.draw(true);
			}
		});
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
	});	
})(jQuery);