(function($){
	"use strict";
	
	var transitStoreLockerTable;
	var storageAreaId;
	var code;
	var storageAreaBeanArray = new Array();
	$(document).ready(function() {	
		initStorageArea()
		/**
		 * 新增按钮点击事件
		 */
		$(document).on("click","#newLocker",function(){
			if($.util.isBlank(code)){
				window.top.$.layerAlert.alert({msg:"请先新建暂存物品保管区！"}) ;
				return;
			}
			window.top.$.layerAlert.dialog({
				content : context +  '/transitStoreLocker/showNewLockerListPage.action',
				pageLoading : true,
				title:"新增暂存物品储物箱",
				width : "500px",
				height : "600px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.saveLocker();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
				success:function(layero, index){
					
				},
				initData:{
					storageArea:storageAreaId,
					storageAreaCode:code
				},
				end:function(){
					transitStoreLockerTable.draw(false);
				}
			});
		});
		/**
		 * 修改按钮点击事件
		 */
		$(document).on("click","#modify",function(){
			var arr = $.icheck.getChecked("locker");
			if(arr.length > 1 || arr.length == 0){
				window.top.$.layerAlert.alert({msg:"请选择一条数据进行修改！"}) ;
				return false;
			}
			var tr = $(arr).parents("tr");		//按钮所在的tr
			var row=transitStoreLockerTable.row(tr);	//行对象
			var data=row.data();	//行所有数据
			
			window.top.$.layerAlert.dialog({
				content : context +  '/transitStoreLocker/showModifyTransitStoreLockerListPage.action',
				pageLoading : true,
				title:"修改暂存物品储物箱",
				width : "500px",
				height : "600px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.saveLocker();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
				success:function(layero, index){
					
				},
				initData:{
					lockerId:data.id
				},
				end:function(){
					transitStoreLockerTable.draw(false);
				}
			});
		});
		/**
		 * 删除按钮点击事件
		 */
		$(document).on("click","#delete",function(){
			var arr = $.icheck.getChecked("locker");
			if(arr.length == 0){
				window.top.$.layerAlert.alert({msg:"请至少选择一条数据进行删除！"}) ;
				return false;
			}
			var ids = new Array() ;
			var dataMap = new Object();
			$.each(arr,function(index,itm){
				var tr = $(itm).parents("tr");		//按钮所在的tr
				var row=transitStoreLockerTable.row(tr);	//行对象
				var data=row.data();	//行所有数据
				ids.push(data.id);
			})
			$(ids).each(function(i,id){
				dataMap["ids["+ i+"]"] = id;
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
					$.ajax({
						url:context +'/transitStoreLocker/deleteTransitStoreLocker.action',
						type:'post',
						dataType:'json',
						data:dataMap,
						success:function(json){
							window.top.$.layerAlert.alert({msg:json.msg}) ;
							transitStoreLockerTable.draw(false);
						}
					});	
				}
			});
		});
		
	});
	
	/**
	 * 初始化暂存物品保管区
	 */
	function initStorageArea(){
		$.ajax({
			url:context +'/transitStoreArea/findTransitStoreAreaByFrame.action',
			type:'post',
			dataType:'json',
			data:{start : 0,length:100000},
			success:function(json){
				var storageAreaBeanList = json.storageAreaBeanList;
				$.each(storageAreaBeanList,function(index,sabl){
					storageAreaBeanArray[sabl.id]=sabl;
					if(index==0){
						var a= '<a href="###" id="'+sabl.id+'" code="'+sabl.code+'" class="list-group-item list-group-item-info a"><span class="glyphicon glyphicon-menu-right pull-right span" id="span'+sabl.id+'" style="margin-top: 4px"></span>'+sabl.name+'</a>';
						$("#storageArea").append(a);
						storageAreaId = sabl.id;
						code = sabl.code;
						initTransitStoreLockerTable();
					}else{
						var a= '<a href="###" id="'+sabl.id+'" code="'+sabl.code+'" class="list-group-item a"><span class="span" id="span'+sabl.id+'" style="margin-top: 4px"></span>'+sabl.name+'</a>';
						$("#storageArea").append(a);
					}
				});
			}
		});
	}
	
	$(document).on('click','.a',function(){
		var tr =$(this).parent("a");
		var id = tr.context.id;
		$(".a").removeClass("list-group-item-info");
		$("#"+id).addClass("list-group-item-info");
		$(".span").removeClass("glyphicon glyphicon-menu-right pull-right");
		$("#span"+id).addClass("glyphicon glyphicon-menu-right pull-right");
		storageAreaId = id
		code = storageAreaBeanArray[id].code;
		transitStoreLockerTable.draw(false);
	});
	
	/**
	 * 暂存物品储物箱table
	 */
	function initTransitStoreLockerTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/transitStoreLocker/queryTransitStoreLocker.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "50px",
         	    	"title": "选择",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    			  var a = '<input type="checkbox" name="locker" class="icheckbox" value="'+data+'"/>' ;
         	    			  return a;
         	    	}
				},
				{
					"targets" : 1,
					"width" : "",
					"title" : "编号",
					"data" : "code",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 2,
					"width" : "",
					"title" : "暂存物品储物箱位置",
					"data" : "location",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "",
					"title" : "所属暂存物品保管区",
					"data" : "area",
					"render" : function(data, type, full, meta) {
						return data.name;
					}
				},
				{
					"targets" : 4,
					"width" : "400px",
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
			tb.autoWidth = true ;
			tb.paging = true;
			//请求参数
			tb.paramsReq = function(d, pagerReq){
				d["storageAreaId"] = $.util.exist(storageAreaId) ? storageAreaId : "" ;
			};
			tb.paramsResp = function(json) {
				var articleLockerBeanList = json.articleLockerBeanList;
				json.data = articleLockerBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
			};
			tb.rowCallback = function(row,data, index) {
				 $($(row).children("td")).each(function(i, val){
						if(i!=0){
							$(val).on("click", function(){		
								var tr = $(this).parents("tr");
								var row=transitStoreLockerTable.row(tr);	//行对象
								var data=row.data();	//行所有数据
								var id=data.id;
								window.top.$.layerAlert.dialog({
									content : context +  '/transitStoreLocker/showLookTransitStoreLockerListPage.action',
									pageLoading : true,
									title:"查看储物箱",
									width : "450px",
									height : "470px",
									btn:["打印二维码","关闭"],
									callBacks:{
										btn1:function(index, layero){
											var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
											cm.print();
										},
										btn2:function(index, layero){
											window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
										}
									},
									shadeClose : false,
									success:function(layero, index){
										
									},
									initData:{
										lockerId:id
									},
									end:function(){
									}
								});
							});
						}
					});
			};
			transitStoreLockerTable = $("#transitStoreLockerTable").DataTable(tb);
	}
	
	
})(jQuery);