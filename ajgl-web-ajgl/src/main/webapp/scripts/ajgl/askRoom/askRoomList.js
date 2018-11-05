(function($){
	"use strict";
	
	var askRoomTable = null;
	
	$(document).ready(function() {	
		/**
		 * 新建按钮事件
		 */
		$(document).on("click","#newAskRoom",function(){
			window.top.getDsspocxObject().OnOpenVideoForThird("1000011$1$0$0");
			/*window.top.$.layerAlert.dialog({
				content : context +  '/askRoom/showNewOrUpdateAskRoomPage.action',
				pageLoading : true,
				title:"新增房间",
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
	    			askRoomTable.draw(false);
	    		}
			});*/
		});
		/**
		 * 修改按钮事件
		 */
		$(document).on("click","#updateAskRoom",function(){
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
				content : context +  '/askRoom/showNewOrUpdateAskRoomPage.action',
				pageLoading : true,
				title:"修改房间信息",
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
	    			askRoomTable.draw(false);
	    		}
			});
		});
		/**
		 * 启用按钮事件
		 */
		$(document).on("click","#enabledAskRoom",function(){
			var arr = $.icheck.getChecked("askRoom");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var askRoomIdList = new Array();
			var flag = false;
			$.each(arr,function(a,askRoomId){
				var askRoomId = $(arr[a]).val();
				var askRoomStatus = $(arr[a]).attr("status");
				if(askRoomStatus == $.common.Constant.SYZT_BKY()){
					askRoomIdList.push(askRoomId);
				}else{
					flag = true;
					return false;
				}
			});
			if(flag){
				window.top.$.layerAlert.alert({msg:"只能启用不可用状态的讯（询）问室！"}) ;
				return false;
			}
			updateAskRoomStatus(askRoomIdList, $.common.Constant.SYZT_KX(), "启用成功！");
		});
		/**
		 * 停用按钮事件
		 */
		$(document).on("click","#disableAskRoom",function(){
			var arr = $.icheck.getChecked("askRoom");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var askRoomIdList = new Array();
			var flag = false;
			$.each(arr,function(a,askRoomId){
				var askRoomId = $(arr[a]).val();
				var askRoomStatus = $(arr[a]).attr("status");
				if(askRoomStatus == $.common.Constant.SYZT_KX()){
					askRoomIdList.push(askRoomId);
				}else{
					flag = true;
					return false;
				}
			});
			if(flag){
				window.top.$.layerAlert.alert({msg:"只能停用空闲状态的讯（询）问室！"}) ;
				return false;
			}
			updateAskRoomStatus(askRoomIdList, $.common.Constant.SYZT_BKY(), "停用成功！");
		});
		/**
		 * 删除按钮事件
		 */
		$(document).on("click","#deleteAskRoom",function(){
			var arr = $.icheck.getChecked("askRoom");
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			
			var askName = "";
			var askRoomIdList = new Array();
			$.each(arr,function(a,askRoomId){
				var askRoomId = $(arr[a]).val();
				askRoomIdList.push(askRoomId);
				if($(arr[a]).attr("status") == $.common.Constant.SYZT_SYZ()){
					askName += $(arr[a]).attr("askName") + "、";
				}
			});
			askName = askName.substring(0,askName.length-1);
			if(!$.util.isBlank(askName)){
				window.top.$.layerAlert.alert({msg:askName + " 使用过的讯（询）问室不可删除！"}) ;
				return false;
			}
			
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
		$(document).on("click",".searchAskRoom",function(){
			askRoomTable.draw(true);
		});
		/**
		 * 重置按钮事件
		 */
		$(document).on("click","#resetAskRoom",function(){
			resetSearchCondition();
		});
		
		$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		intiStatusDictionaryItem();
		
	});
	
	function initAskRoomDataTable(){
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
					"title" : "讯（询）问室名称",
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
					"title" : "房间状态",
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
			//	d["askRoomBean.type"] = $.common.Constant.FJLX_WXS();
				if($.common.isFullConditionSearch()){
					d["askRoomBean.code"] = $("#code").val();
					d["askRoomBean.name"] = $("#name").val();
					d["askRoomBean.status"] = $.select2.val("#status");
					d["askRoomBean.unit"] = $("#unit").val();
					d["askRoomBean.note"] = $("#note").val();
				}else{
					var nameText = $("#nameText").val() == "讯（询）问室名称模糊查询" ? "" : $("#nameText").val();
					d["askRoomBean.name"] = nameText;
					d["askRoomBean.status"] = $.select2.val("#status");
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
			askRoomTable = $("#askRoomTable").DataTable(tb);
	}
	
	/**
	 * 初始化状态字典项字段
	 * @returns
	 */
	function intiStatusDictionaryItem(){
		$.ajax({
			url:context +'/webUtil/findDictionaryItemByType.action',
			type:'post',
			dataType:'json',
			data:{dictionaryType : $.common.Constant.WXSZT()},
			success:function(successData){
				if(successData.dictionaryItemLst != null){
					$.select2.addByList("#status", successData.dictionaryItemLst,"id","name",true,true);//设置下拉框选项
					$.select2.val("#status",$.common.Constant.SYZT_KX(),true);//设置默认值
					initAskRoomDataTable();
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