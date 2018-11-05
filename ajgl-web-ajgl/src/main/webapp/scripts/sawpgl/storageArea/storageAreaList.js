(function($){
	"use strict";
	
	var saTable = null;//物证管理区table
	
	$(document).ready(function() {	
		initSaTable();
		/**
		 * 新增按钮点击事件
		 */
		$(document).on("click","#saNew",function(){
			window.top.$.layerAlert.dialog({
				content : context +  '/storageArea/showNewOrUpdateStorageAreaPage.action',
				pageLoading : true,
				title:"新增物证保管区",
				width : "500px",
				height : "400px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.newOrUpdateStorageArea.save();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
				success:function(layero, index){
					
				},
				initData:{
					saId : null
				},
				end:function(){
					saTable.draw(false);
				}
			});
		});
		/**
		 * 修改按钮点击事件
		 */
		$(document).on("click","#saUpdate",function(){
			var arr = $.icheck.getChecked("sa");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			if(arr.length > 1){
				window.top.$.layerAlert.alert({msg:"不可对多条数据同时修改！"}) ;
				return false;
			}
			var saId = $(arr[0]).val(); 
			window.top.$.layerAlert.dialog({
				content : context +  '/storageArea/showNewOrUpdateStorageAreaPage.action',
				pageLoading : true,
				title:"修改物证保管区信息",
				width : "550px",
				height : "450px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.newOrUpdateStorageArea.save();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
				success:function(layero, index){
					
				},
				initData:{
					saId : saId
				},
				end:function(){
					saTable.draw(false);
				}
			});
		});
		/**
		 * 删除按钮点击事件
		 */
		$(document).on("click","#saDelete",function(){
			var arr = $.icheck.getChecked("sa");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			
			var saIdList = new Array();
			$.each(arr,function(a,val){
				var saId = $(arr[a]).val();
				saIdList.push(saId);
			});
			window.top.$.layerAlert.confirm({
				msg:"删除后不可恢复，确定要删除吗？",
				title:"删除",	  //弹出框标题
				width:'300px',
				hight:'200px',
				shade: [0.5,'black'],  //遮罩
				icon:0,  //弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
				shift:1,  //弹出时的动画效果  有0-6种
				yes:function(index, layero){
					//点击确定按钮后执行
					deleteSaByIds(saIdList);
				}
			});
		});
		
		/**
		 * 启用按钮点击事件
		 */
		$(document).on("click","#saEnabled",function(){
			var arr = $.icheck.getChecked("sa");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var stateEnabledName = "";
			var saIdList = new Array();
			$.each(arr,function(a,val){
				var saId = $(arr[a]).val();
				var state = $(arr[a]).attr("state");
				var name = $(arr[a]).attr("saName");
				if($.common.Constant.QY() == state){
					stateEnabledName += name + "、";
				}else{
					saIdList.push(saId);
				}
			});
			if(!$.util.isBlank(stateEnabledName && stateEnabledName.length > 1)){
				if(stateEnabledName.indexOf("、") != -1){
					stateEnabledName = stateEnabledName.substring(0,stateEnabledName.length - 1);
				}
				window.top.$.layerAlert.alert({msg:stateEnabledName + " 不可重复启用！"}) ;
				return ;
			}
			updateSaStatus(saIdList, $.common.Constant.QY(), "启用成功！");
		});
		/**
		 * 停用按钮点击事件
		 */
		$(document).on("click","#saDisable",function(){
			var arr = $.icheck.getChecked("sa");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var stateDisableName = "";
			var saIdList = new Array();
			$.each(arr,function(a,val){
				var saId = $(arr[a]).val();
				var state = $(arr[a]).attr("state");
				var name = $(arr[a]).attr("saName");
				if($.common.Constant.TY() == state){
					stateDisableName += name + "、";
				}else{
					saIdList.push(saId);
				}
			});
			if(!$.util.isBlank(stateDisableName && stateDisableName.length > 1)){
				if(stateDisableName.indexOf("、") != -1){
					stateDisableName = stateDisableName.substring(0,stateDisableName.length - 1);
				}
				window.top.$.layerAlert.alert({msg:stateDisableName + " 不可重复停用！"}) ;
				return ;
			}
			updateSaStatus(saIdList, $.common.Constant.TY(), "停用成功！");
		});
	});
	
	/**
	 * 修改物证保管区状态
	 * @param saIdList 物证保管区id集合
	 * @param state 要更改的状态
	 * @param msg 成功后弹窗显示的说明
	 */
	function updateSaStatus(saIdList, state, msg){
		var gData = new Object();
		$.util.objToStrutsFormData(saIdList, "saIdList", gData);
		$.util.objToStrutsFormData(state, "state", gData);
		
		$.ajax({
			url:context +'/storageArea/updateStorageAreaState.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				window.top.$.layerAlert.alert({msg:msg}) ;
				saTable.draw(false);
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 删除物证保管区
	 * @param saIdList 物证保管区id集合
	 */
	function deleteSaByIds(saIdList){
		var gData = new Object();
		$.util.objToStrutsFormData(saIdList, "saIdList", gData);
		
		$.ajax({
			url:context +'/storageArea/deleteStorageAreaByIds.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				var failSaNameList = successData.saNameList;
				var length = failSaNameList.length;
				var msg = "";
				if(length != 0){
					$.each(failSaNameList,function(n,name){
						msg += name;
						if(n<length-1){
							msg += "、<br>";
						}
					});
					msg += "<br> 有储物箱的物证保管区不可删除！";
				}else{
					msg = "删除成功！";
				}
				window.top.$.layerAlert.alert({msg:msg}) ;
				saTable.draw(false);
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 初始化物证保管区table
	 */
	function initSaTable(){
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/storageArea/findStorageAreaByPaging.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "50px",
     	    	"title": "选择",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    			  var a = '<input type="checkbox" name="sa" class="icheckbox" value="' + data + '" state="' + full.state + '" saName="' + full.name + '"/>' ;
     	    			  return a;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "100px",
				"title" : "编号",
				"data" : "code",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-wrap:break-word;word-break:break-all",
						"text" : data
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 2,
				"width" : "100px",
				"title" : "物证保管区名称",
				"data" : "name",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-wrap:break-word;word-break:break-all",
						"text" : data
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 3,
				"width" : "100px",
				"title" : "所属单位",
				"data" : "unitName",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-wrap:break-word;word-break:break-all",
						"text" : data
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 4,
				"width" : "100px",
				"title" : "物证保管区详细地址",
				"data" : "address",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-wrap:break-word;word-break:break-all",
						"text" : data
					});
					return div[0].outerHTML;
				}
			},
			{
				"targets" : 5,
				"width" : "50px",
				"title" : "状态",
				"data" : "stateName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 6,
				"width" : "100px",
				"title" : "备注",
				"data" : "remark",
				"render" : function(data, type, full, meta) {
					var div = $("<div />",{
						"style" : "width:100%;word-wrap:break-word;word-break:break-all",
						"text" : data
					});
					return div[0].outerHTML;
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
			var storageAreaBeanList = json.storageAreaBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
			json.data = storageAreaBeanList;
		
		};
		tb.rowCallback = function(row,data, index) {
			
		};
		saTable = $("#saTable").DataTable(tb);
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 

	});	
})(jQuery);