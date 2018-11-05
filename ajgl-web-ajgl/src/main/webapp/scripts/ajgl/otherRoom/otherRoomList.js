(function($){
	"use strict";
	
	var otherRoomTable = null;
	
	$(document).ready(function() {	
		/**
		 * 新建按钮事件
		 */
		$(document).on("click","#newOR",function(){
			window.top.$.layerAlert.dialog({
				content : context +  '/otherRoom/showNewOrUpdateOtherRoomPage.action',
				pageLoading : true,
				title:"新增其他房间",
				width : "500px",
				height : "430px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.saveAskRoom();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		initData:{
	    			askRoomId : null
	    		},
	    		end:function(){
	    			otherRoomTable.draw(false);
	    		}
			});
		});
		/**
		 * 修改按钮事件
		 */
		$(document).on("click","#updateOR",function(){
			var arr = $.icheck.getChecked("askRoom");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			if(arr.length > 1){
				window.top.$.layerAlert.alert({msg:"不可对多条数据同时修改！"}) ;
				return false;
			}
			var askRoomId = $(arr[0]).val(); 
			window.top.$.layerAlert.dialog({
				content : context +  '/otherRoom/showNewOrUpdateOtherRoomPage.action',
				pageLoading : true,
				title:"修改其他房间信息",
				width : "500px",
				height : "430px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.saveAskRoom();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		initData:{
	    			askRoomId : askRoomId
	    		},
	    		end:function(){
	    			otherRoomTable.draw(false);
	    		}
			});
		});
		/**
		 * 启用按钮事件
		 */
		$(document).on("click","#enabledOR",function(){
			var arr = $.icheck.getChecked("askRoom");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var askRoomIdList = new Array();
			var flag = false;
			$.each(arr,function(a,askRoomId){
				var askRoomId = $(arr[a]).val();
				askRoomIdList.push(askRoomId);
			});
			updateAskRoomStatus(askRoomIdList, $.common.Constant.QY(), "启用成功！");
		});
		/**
		 * 停用按钮事件
		 */
		$(document).on("click","#disableOR",function(){
			var arr = $.icheck.getChecked("askRoom");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var askRoomIdList = new Array();
			var flag = false;
			$.each(arr,function(a,askRoomId){
				var askRoomId = $(arr[a]).val();
				askRoomIdList.push(askRoomId);
			});
			updateAskRoomStatus(askRoomIdList, $.common.Constant.TY(), "停用成功！");
		});
		/**
		 * 删除按钮事件
		 */
		$(document).on("click","#deleteOR",function(){
			var arr = $.icheck.getChecked("askRoom");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var askRoomIdList = new Array();
			var flag = false;
			$.each(arr,function(a,askRoomId){
				var askRoomId = $(arr[a]).val();
				askRoomIdList.push(askRoomId);
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
					deleteAskRoomByIds(askRoomIdList);
				}
			});
		});
		/**
		 * 查询按钮事件
		 */
		$(document).on("click",".searchOR",function(){
			otherRoomTable.draw(true);
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#resetOR",function(){
			resetSearchCondition();
		});
		
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		intiDictionaryItem();
		initOtherRoomDataTable();
	});
	
	function initOtherRoomDataTable(){
		var tb = $.uiSettings.getOTableSettings();
			tb.ajax.url = context + "/askRoom/searchAllAskRoomListByCondition.action";
			tb.columnDefs = [
				{
					"targets": 0,
         	    	"width": "50px",
         	    	"title": "选择",
         	    	"className":"table-checkbox",
         	    	"data": "id" ,
         	    	"render": function ( data, type, full, meta ) {
         	    			  var a = '<input type="checkbox" name="askRoom" class="icheckbox" value="'+data+'" status="'+full.status+'" askName="'+full.name+'"/>' ;
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
					"title" : "其他房间名称",
					"data" : "name",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 3,
					"width" : "250px",
					"title" : "所属单位",
					"data" : "unitName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 4,
					"width" : "100px",
					"title" : "其他房间状态",
					"data" : "statusName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				},
				{
					"targets" : 5,
					"width" : "",
					"title" : "备注",
					"data" : "note",
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
				d["askRoomBean.type"] = $.common.Constant.FJLX_QT();
				if($.common.isFullConditionSearch()){
					d["askRoomBean.code"] = $("#code").val();
					d["askRoomBean.name"] = $("#name").val();
					d["askRoomBean.status"] = $.select2.val("#status");
					d["askRoomBean.unit"] = $("#unit").val();
					d["askRoomBean.note"] = $("#note").val();
				}else{
					var nameText = $("#nameText").val() == "其他房间名称模糊查询" ? "" : $("#nameText").val();
					d["askRoomBean.name"] = nameText;
				}
			};
			tb.paramsResp = function(json) {
				var askRoomBeanList = json.askRoomBeanList;
				json.recordsTotal = json.totalNum;
				json.recordsFiltered = json.totalNum;
				json.data = askRoomBeanList;
			
			};
			tb.rowCallback = function(row,data, index) {
				
			};
			otherRoomTable = $("#otherRoomTable").DataTable(tb);
	}
	
	/**
	 * 初始化状态字典项字段--其它房间状态
	 * @returns
	 */
	function intiDictionaryItem(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			dataType:'json',
			data:{dictionaryType : $.common.Constant.ZT()},
			success:function(successData){
				if(successData.dictionaryItemLst != null){
					$.select2.addByList("#status", successData.dictionaryItemLst,"id","name",true,true);//设置下拉框选项
				}
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 修改其他房间状态
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
				otherRoomTable.draw(false);
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 删除其他房间
	 * @param askRoomIdList 其他房间id集合
	 */
	function deleteAskRoomByIds(otherRoomIdList){
		var gData = new Object();
		$.util.objToStrutsFormData(otherRoomIdList, "otherRoomIdList", gData);
		
		$.ajax({
			url:context +'/askRoom/deleteOtherRoomByIds.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				var failAskRoomNameList = successData.otherRoomNameList;
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
					msg += " 使用过的房间不可删除！";
				}else{
					msg = "删除成功！";
				}
				window.top.$.layerAlert.alert({msg:msg}) ;
				otherRoomTable.draw(false);
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
		$("#code").val("");
		$("#name").val("");
		$.select2.selectByOrder("#status",0,true);
		$("#unit").val("");
		$("#unitName").val("");
		$("#note").val("");
	}
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);