$.lockerList = $.lockerList || {};
(function($){
	"use strict";
	
		var lockerTable = null;
		var lockerIdList=null;
		var list=[];
		$(document).ready(function() {
			$(document).on("click",".img",function(){
				$.layerAlert.img("data:image/png;base64," + $(this).attr("imgSrc"), 657);
			});
		//	$.common.intiSelectUnitTree()//初始化单位树 ajglUtil.js 数据加载在该js 的回调里
					
			onload();
			
			/**
			 * 鼠标移入
			 */
			$(document).on("mouseenter",".boxEvent",function(){				
				if($(this).find('span').hasClass('state-busy')){//状态为在用的添加div
					var cwgid=$(this).attr("id");
					creatDiv(cwgid,this);
				}
			});
			
			/**
			 * 点击事件
			 */
			$(document).on("click",".boxEvent",function(){
				if($(this).hasClass('checked')){
					$(this).removeClass("checked");
					$(this).attr("style","");
				}else{
					$(this).css( "background-color","#98bf21");
					$(this).addClass("checked");
				}
				
			});
			/**
			 * 查询按钮事件--高亮显示
			 */
			$(document).on("click","#checkBtn",function(){
				findByCondition();
			});
			/**
			 * 重置按钮事件
			 */
			$(document).on("click","#resetLocker",function(){
				resetSearchCondition();
			});
			
			/**
			 * select 二级加载事件
			 */
			$(document).on("change","#areaCode",function(){
				var a=$(this).children('option:selected').text();
				onloadSecond(a);
			});
			/**
			 * 新建按钮事件
			 */
			$(document).on("click","#newLocker",function(){
				addMethod();
			});
			/**
			 * 修改按钮事件
			 */
			$(document).on("click","#updateLocker",function(){
				updataMethod();
			});
			/**
			 * 启用按钮事件
			 */
			$(document).on("click","#enabledLocker",function(){
				startMethod();
			});
			/**
			 * 停用按钮事件
			 */
			$(document).on("click","#disableLocker",function(){
				stopMethod();
			});	
			/**
			 * 删除按钮事件
			 */
			$(document).on("click","#deleteLocker",function(){
				delMethod();
			});	
			/**
			 * 开箱按钮事件
			 */
			$(document).on("click",".open",function(event){
			   var code=$(this).attr("openId");
			   openLocker(code);//开箱
			   event.stopPropagation(); //阻止事件冒泡 不执行父级..祖父级的事件
			});	
			/**
			 * 阻止子按钮的点击事件
			 */
			$(document).on("click",".box-storage-ceng",function(event){
				event.stopPropagation(); //阻止事件冒泡 不执行父级..祖父级的事件
			});	
		
		});
		
		
		function openLocker(code){
			$.ajax({
				url: context + "/locker/openLocker.action",
				type:'post',
				dataType:'json',
				data:{
					code:code
				},
				success:function(successData){
					
				},
				error:function(errorData){
					
				}
			});
		}
		/**
		 * 新建按钮事件
		 */
		function  addMethod() {
			window.top.$.layerAlert.dialog({
				content : context +  '/locker/showNewOrUpdateLockerPage.action',
				pageLoading : true,
				title:"新增储物柜",
				width : "500px",
				height : "430px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.saveLocker();
						findByCondition();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		initData:{
	    			lockerId : null
	    		},
	    		end:function(){
	    			//lockerTable.draw(false);
	    			onload();
	    		}
			});
		};
		/**
		 * 修改按钮事件
		 */
		function updataMethod(){
			var arr =$('.checked');
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			if(arr.length > 1){
				window.top.$.layerAlert.alert({msg:"不可对多条数据同时修改！"}) ;
				return false;
			}
			var bn=false;
			arr.find("span").each(function(){
				bn=$(this).hasClass('state-busy');
			})
			if(bn==true){
				window.top.$.layerAlert.alert({msg:"该储物柜不可修改！"}) ;
				return false;
			}
			var lockerId = $(arr[0]).attr("id"); 
			window.top.$.layerAlert.dialog({
				content : context +  '/locker/showNewOrUpdateLockerPage.action',
				pageLoading : true,
				title:"修改储物柜信息",
				width : "500px",
				height : "400px",
				btn:["保存","取消"],
				callBacks:{
					btn1:function(index, layero){
						var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
						cm.saveLocker();
						findByCondition();
					},
					btn2:function(index, layero){
						window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
					}
				},
				shadeClose : false,
	    		success:function(layero, index){
	    			
	    		},
	    		initData:{
	    			lockerId : lockerId
	    		},
	    		end:function(){
	    			//lockerTable.draw(false);
	    			onload();
	    		}
			});
		};
		/**
		 * 启用按钮事件
		 */
		function  startMethod(){
			var arr = $('.checked');
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var lockerIdList = new Array();
			var flag = false;
			var codeTemp = "";
			$.each(arr,function(i,val){
				if($(arr[i]).find("span").hasClass('state-busy')){
					codeTemp += $(arr[i]).find("a").attr("openid") + ",";
				}else{
					var lockerId = $(arr[i]).attr("id");
					lockerIdList.push(lockerId);
				}
			});
			if(codeTemp != ""){
				window.top.$.layerAlert.alert({msg: "使用中的柜子" + codeTemp + "不能启用！", end:function(){
					updateLockerStatus(lockerIdList, $.common.Constant.CWGZT_KX(), "其他柜子启用成功！");
				}});
			}else{
				updateLockerStatus(lockerIdList, $.common.Constant.CWGZT_KX(), "启用成功！");
			}
			
		};
		/**
		 * 停用按钮事件
		 */
		function stopMethod(){
			var arr = $('.checked');
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			var lockerIdList = new Array();
			var flag = false;
			var codeTemp = "";
			$.each(arr,function(i,val){
				if($(arr[i]).find("span").hasClass('state-busy')){
					codeTemp += $(arr[i]).find("a").attr("openid") + ",";
				}else{
					var lockerId = $(arr[i]).attr("id");
					lockerIdList.push(lockerId);
				}
			});
			if(codeTemp != ""){
				window.top.$.layerAlert.alert({msg: "使用中的柜子" + codeTemp + "不能停用！", end:function(){
					updateLockerStatus(lockerIdList, $.common.Constant.CWGZT_TY(), "其他柜子停用成功！");
				}});
			}else{
				updateLockerStatus(lockerIdList, $.common.Constant.CWGZT_TY(), "停用成功！");
			}
		};
		/**
		 * 删除按钮事件
		 */
		function delMethod(){
			var arr = $('.checked');
			if(arr.length < 1){
				window.top.$.layerAlert.alert({msg:"未选中任何一条数据！"}) ;
				return false;
			}
			
			var lockerCode = "";
			var lockerIdList = new Array();
			$.each(arr,function(i,val){
				var lockerId = $(arr[i]).attr("id");
				lockerIdList.push(lockerId);
				if($(arr[i]).attr("status") == $.common.Constant.CWGZT_SYZ()){
					lockerCode += $(arr[i]).attr("lockerCode") + "、";
				}
			});
			lockerCode = lockerCode.substring(0,lockerCode.length-1);
			if(!$.util.isBlank(lockerCode)){
				window.top.$.layerAlert.alert({msg:lockerCode + " 使用过的储物柜不可删除！"}) ;
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
					deleteLockerByIds(lockerIdList);
				}
			});
		};	
	/**
	 * 根据条件查询储物柜
	 * @returns
	 */
	function findByCondition(){
		    var lockerBean=new Object();
		    var area=$("#areaCode").find("option:selected").text();
		    if(area=='全部'){
		    	lockerBean.areaCode='';
		    }else{
		    	lockerBean.areaCode = area;
		    }
		    var locker=$("#lockerCode").find("option:selected").text();
		    if(locker=='全部'){
		    	lockerBean.lockerCode='';
		    }else{
		    	lockerBean.lockerCode = locker;
		    }
		    lockerBean.unit = $("#unit").val();
		    var name= $("#name").val() == "模糊查询，多个可用逗号隔开" ? "" : $("#name").val();
			var arr=name.split(",");
			if(arr[arr.length-1]==''){
				arr.pop();
			}
			lockerBean.name = arr;
			var lockerBeanJson = $.util.toJSONString(lockerBean);
			$.ajax({
				url: context + "/locker/searchAllLockerListByCondition.action",
				type:'post',
				dataType:'json',
				contentType : "application/json; charset=utf-8",
				data:lockerBeanJson,
				success:function(successData){
					$('#boxDiv').empty();
					list=successData.lockerBeanList;
					var k=0;
					var str='';
					if(list.length==0){
						
					}else{
						str+='<div class="row alert-default"><div class="col-xs-4">'
							+'<div class="row alert alert-default">'
							+'<h2 class="box-storage-tt" style="display:none;">'+list[0].areaCode+'-'+list[0].lockerCode+'</h2>'
							+'<div class="box-storage-list"><ul>'
							+'<li class="boxEvent"  id="'+list[0].id+'"><h2>'+list[0].name+'</h2>';
							
						if(list[0].status==$.common.Constant.CWGZT_SYZ()){
							str+='<a  openId="'+list[0].code+'" class="open" title="开箱"></a>';
							str+='<span class="state-mark state-busy"></span></li>';
						}else if(list[0].status==$.common.Constant.CWGZT_KX()){
							str+='<a  openId="'+list[0].code+'" class="open" title="开箱"></a>';
							str+='<span class="state-mark state-free"></span></li>';						
						}else{
							str+='<span class="state-mark state-disable"></span></li>';//不可用
						}
						for(var i=1;i<list.length;i++){
							if(list[i].areaCode==list[i-1].areaCode){
								if(i%20!=0){
									str+='<li class="boxEvent"  id="'+list[i].id+'"><h2>'+list[i].name+'</h2>';
									if(list[i].status==$.common.Constant.CWGZT_SYZ()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-busy"></span></li>';
									}else if(list[i].status==$.common.Constant.CWGZT_KX()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-free"></span></li>';							
									}else{
										str+='<span class="state-mark state-disable"></span></li>';//不可用
									}
								}else{
									k++;
									str+='</ul></div></div></div>';
									if(k/3==0){
										str+='</div><div class="row alert-default">';
									}
									str+='<div class="col-xs-4">'
										+'<div class="row alert alert-default">'
										+'<h2 class="box-storage-tt" style="display:none;">'+list[i].areaCode+'-'+list[i].lockerCode+'</h2>'
										+'<div class="box-storage-list"><ul>';
									str+='<li class="boxEvent" id="'+list[i].id+'"><h2>'+list[i].name+'</h2>';
									if(list[i].status==$.common.Constant.CWGZT_SYZ()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-busy"></span></li>';
									}else if(list[i].status==$.common.Constant.CWGZT_KX()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-free"></span></li>';							
									}else{
										str+='<span class="state-mark state-disable"></span></li>';//不可用
									}
								}
							}else{
								str+='</ul></div></div></div>'; //关闭上一个分区
								str+='</div><div class="row alert-default">';  //开启下一个分区
								str+='<div class="col-xs-4">'   //开启分柜
									+'<div class="row alert alert-default">'
									+'<h2 class="box-storage-tt" style="display:none;">'+list[i].areaCode+'</h2>'
									+'<div class="box-storage-list"><ul>';
								str+='<li class="boxEvent" id="'+list[i].id+'"><h2>'+list[i].lockerCode+'</h2>';//开启箱体
								if(list[i].status==$.common.Constant.CWGZT_SYZ()){
									str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
									str+='<span class="state-mark state-busy"></span></li>';
								}else if(list[i].status==$.common.Constant.CWGZT_KX()){
									str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
									str+='<span class="state-mark state-free"></span></li>';							
								}else{
									str+='<span class="state-mark state-disable"></span></li>';//不可用
								}
							}
						}
						str+='</ul></div></div></div></div>';
						$('#boxDiv').append(str);
					}
					if($("#name").val() == "模糊查询，多个可用逗号隔开"||$("#name").val()==''){
						
					}else{
						findByConditionToHighlight();
					}
				
				},
				error:function(errorData){
					
				}
			});
	}
	/**
	 * 高亮显示
	 * @returns
	 */
	function findByConditionToHighlight(){
	    var lockerBean=new Object();
	    var area=$("#areaCode").find("option:selected").text();
	    if(area=='全部'){
	    	lockerBean.areaCode='';
	    }else{
	    	lockerBean.areaCode = area;
	    }
	    var locker=$("#lockerCode").find("option:selected").text();
	    if(locker=='全部'){
	    	lockerBean.lockerCode='';
	    }else{
	    	lockerBean.lockerCode = locker;
	    }
		lockerBean.unit = $("#unit").val();
		var name= $("#name").val() == "模糊查询，多个可用逗号隔开" ? "" : $("#name").val();
		var arr=name.split(",");
		if(arr[arr.length-1]==''){
			arr.pop();
		}
		lockerBean.name = arr;
		var lockerBeanJson = $.util.toJSONString(lockerBean);
		$.ajax({
			url: context + "/locker/searchAllLockerListByCondition.action",
			type:'post',
			dataType:'json',
			contentType : "application/json; charset=utf-8",
			data:lockerBeanJson,
			success:function(successData){
				list=successData.lockerBeanList;
				$('.selected').removeClass("selected");
				if(list.length <= 0){
					$.util.topWindow().$.layerAlert.alert({msg:"没有与搜索条件(储物柜编码)匹配的内容",title:"提示",icon:0,time:3000});	
				}else{
					for(var i=0;i<list.length;i++){
						$('#'+list[i].id).addClass('selected');
					}
				}
			},
			error:function(errorData){
				
			}
		});
}
	
	/**
	 * 初始化状态字典项字段(初始化区号)
	 * @returns
	 */
	function intiDictionaryItem(){
		$.ajax({
			url: context + "/locker/findAllAreaCode.action",
			type:'post',
			dataType:'json',
			success:function(successData){
				$('#areaCode').empty();
				var str='<option>全部</option>';
				if(successData.lockerNameList != null){
					for(var i=0;i<successData.lockerNameList.length;i++){
						str+='<option value='+i+'>'+successData.lockerNameList[i]+'</option>';
					}
				}
				$('#areaCode').append(str);
			},
			error:function(errorData){
				
			}
		});
		
	}
	
	/**
	 * 加载二级目录
	 * @returns
	 */
	function onloadSecond(data){
		if(data=='全部'){
			data='';
		}
		$.ajax({
			url: context + "/locker/findAllAreaCodeSecond.action",
			type:'post',
			dataType:'json',
			data:{status:data},
			success:function(successData){
				$('#lockerCode').empty();
				var str='<option >全部</option>';
				if(successData.lockerNameList != null){
					for(var i=0;i<successData.lockerNameList.length;i++){
						str+='<option value='+i+'>'+successData.lockerNameList[i]+'</option>';
					}
				}
				$('#lockerCode').append(str);
				if(data==''){
					findByCondition();
				}
			},
			error:function(errorData){
				
			}
		});
		
	}
	
	/**
	 * 修改储物柜状态
	 * @param lockerIdList 储物柜id集合
	 * @param status 要更改的状态
	 * @param msg 成功后弹窗显示的说明
	 */
	function updateLockerStatus(lockerIdList, status, msg){
		var gData = new Object();
		$.util.objToStrutsFormData(lockerIdList, "lockerIdList", gData);
		$.util.objToStrutsFormData(status, "status", gData);
		
		$.ajax({
			url:context +'/locker/updateLockerStatus.action',
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				window.top.$.layerAlert.alert({msg:msg}) ;
				//lockerTable.draw(false);
				onload();
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 删除储物柜
	 * @param lockerIdList 储物柜id集合
	 */
	function deleteLockerByIds(lockerIdList){
		var gData = new Object();
		$.util.objToStrutsFormData(lockerIdList, "lockerIdList", gData);
		
		$.ajax({
			url:context +'/locker/deleteLockerByIds.action',
			async:true,
			cache:false,//设置每次都重新请求
			global:false,
			type:'post',
			dataType:'json',
			data:gData,
			success:function(successData){
				var failLockerNameList = successData.lockerNameList;
				var length = failLockerNameList.length;
				if(length != 0){
					var msg = "";
					$.each(failLockerNameList,function(n,name){
						msg += name;
						if(n<length-1){
							msg += "、";
						}
					});
					msg = msg.substring(0,msg.length-1);
					msg += " 使用过的储物柜不可删除！";
				}else{
					msg = "删除成功！"
				}
				window.top.$.layerAlert.alert({msg:msg}) ;
				//lockerTable.draw(false);
				onload();
			},
			error:function(errorData){
				
			}
		});
	}
	/**background: #fff
	 * 拼接要显示详情的div
	 * @returns
	 */
	function  creatDiv(id,node){
		$.ajax({
			url:context +'/locker/findMessageByLockerRoomId.action',
			type:'post',
			dataType:'json',
			data:{
				lockerId:id
			},
			success:function(successData){
				$('.newDiv').remove();
				var lockerStockedArticleBean = successData.lockerStockedArticleBean;
				if(lockerStockedArticleBean!=null){
					var str='<div id="div'+id+'" class="fi-ceng newDiv box-storage-ceng" style="display:block;color:#333;">'
							+'<h4>'+lockerStockedArticleBean.lockerNum+'&nbsp;在库详情</h4>'
							+'<div class="alert">'
							+'<p><span class="color-gray">办案区使用单：</span><a href="#" >'+lockerStockedArticleBean.handlingAreaReceiptNum+'</a>'+lockerStockedArticleBean.byQuestioningPeopleName+'</p>'
							+'<p><span class="color-gray">存入时间：</span>'+$.date.timeToStr(lockerStockedArticleBean.stockTime, "yyyy-MM-dd HH:mm:ss") +'</p>'
							+'<p class="m-line"></p>'
							+'<h5 class="row-mar mar-top m-bold">在存物品：</h5>'
							+'<ul>';
						for(var i=0;i<lockerStockedArticleBean.articles.length;i++){
							str+='<li class="row row-mar-sm"><span class="pull-left">'+lockerStockedArticleBean.articles[i].name+'</span><a href="###" imgSrc="'+lockerStockedArticleBean.articles[i].code+'"  class="pull-right img">'+lockerStockedArticleBean.articles[i].num+'</a></li>';
						}
						str+='</ul>'
							+'<p class="m-line"></p>'
							+'<h5 class="row-mar  mar-top  m-bold">临时取出：</h5>'
					    	+'<ul class="list-group-my">';
						for(var i=0;i<lockerStockedArticleBean.takeOutArticles.length;i++){
							str+='<li>'+lockerStockedArticleBean.takeOutArticles[i].name+'<span class="pull-right"> <span class="color-gray">取出民警：</span>'+lockerStockedArticleBean.takeOutArticles[i].policeName+'</span></p>'
								+'<p><span class="color-gray">取出时间：</span>'+$.date.timeToStr(lockerStockedArticleBean.takeOutArticles[i].removalTime, "yyyy-MM-dd HH:mm:ss")+' </p></li>';
						}
						str+='</ul></div></div>';
						$(node).append(str);
						$('#'+id).mouseleave(function(){
							var cwgid=$(this).attr("id");
							$('#div'+cwgid).remove();
						});
				}
				
			},
			error:function(errorData){
				
			}
		});
//		for(var i=0;i<list.length;i++){
//			if(id==list[i].id){
//			str='<div id="div'+id+'" class="fi-ceng box-storage-ceng" style="display:block;color:#333;">'
//				+'<h4>A-1-01&nbsp;在库详情</h4>'
//				+'<div class="alert">'
//				+'<p><span class="color-gray">办案区使用单：</span><a href="#" >sz0001</a>王二宝</p>'
//				+'<p><span class="color-gray">存入时间：</span>2017-01-06 16：03</p>'
//				+'<p class="m-line"></p>'
//				+'<h5 class="row-mar mar-top m-bold">在存物品：</h5>'
//				+'<ul>'
//				+'<li class="row row-mar-sm"><span class="pull-left">三星手机</span><a href="#" target="_blank" class="pull-right">20170106001.jpeg</a></li>'
//				+'<li class="row row-mar-sm"><span class="pull-left">钥匙一串</span><a href="#" target="_blank" class="pull-right">20170106002.jpeg</a></li>'
//				+'<li class="row row-mar-sm"><span class="pull-left">皮带</span><a href="#" target="_blank" class="pull-right">20170106003.jpeg</a></li>'
//				+'</ul>'
//				+'<p class="m-line"></p>'
//				+'<h5 class="row-mar  mar-top  m-bold">临时取出：</h5>'
//				+'<ul class="list-group-my">'
//				+'<li>三星手机<span class="pull-right"> <span class="color-gray">取出民警：</span>李白</span></p>'
//				+'<p><span class="color-gray">取出时间：</span>2017-01-06 18：00 </p></li>'
//				+'<li>三星手机<span class="pull-right"> <span class="color-gray">取出民警：</span>李白</span></p>'
//				+'<p><span class="color-gray">取出时间：</span>2017-01-06 18：00 </p></li>	</ul></div></div>';
//			}
//		}
		
		
	}
	/**
	 * 重置查询条件
	 */
	function resetSearchCondition(){
//		$("#unit").val("");
//		$("#unitName").val("");
		intiDictionaryItem();
		onloadSecond("");
		$("#name").val("模糊查询，多个可用逗号隔开");
		$("#name").attr("style","color:#aaa;");
	//	$.common.intiSelectUnitTree();//初始化单位树 ajglUtil.js
		
	}
	function onload(){
		intiDictionaryItem(),
		onloadSecond("")
		//findByCondition()
			
		
		;
	}
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.lockerList, { 
		onload:onload
	});	
})(jQuery);