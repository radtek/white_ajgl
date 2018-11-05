(function($) {
	"use strict";
	
	var table;
	//办案区使用单id
	var harId = "";
	var dateTime='';
	var blank='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'; 
	$(document).ready(function() {
		jumpOn();
		harId = $("#harId").val();
		$.common.setSelectedTabsById("activityRecord");
		//将状态改为false 查看页面
		$.common.setIsUpdate(false);
		$.common.showOperateRecord("history");//显示操作记录  ajglUtil.js
		//initTable();
		initActivationRecord();
		$(document).on("click",".dlws",function(){
			window.open(context + "/carryGoodsInfo/downloadFile.action?attachmentId="+ $(this).attr("fileId"));					
		})
		$(document).on("mouseleave","#menuContent",function(){
			$("#menuContent").fadeOut("fast");
	        $("body").off("mousedown", onBodyDown);
		})
		$(document).on("click",".showdll",function(){
			var obj = $(this);
			var tr = obj.parents("tr");
    		var row = table.row(tr);
    		var files = row.data().files;
    		var str = "";
    		for(var i in files){
                str += "<li><a href='###' class='dlws' fileId='" + files[i].id + "'>" + files[i].name + "</a></li>";
            }
    		$("#downloadLst").empty();
    		$("#downloadLst").append(str);
    		
	        var oOffset = $(this).offset();
	        $("#menuContent").css({left:oOffset.left + "px", top:oOffset.top + obj.outerHeight() + "px"}).slideDown("fast");
	        $("body").on("mousedown", onBodyDown);
		})
		/**
		 * 获取摄像头信息按钮
		 */
		$(document).on("click",".icon-facetime-video",function(){
			openVCR($(this));			
			//window.open(context + "/carryGoodsInfo/downloadFile.action?attachmentId="+ $(this).attr("fileId"));					
		})
		/**
		 * 预警消息弹窗
		 */
		$(document).on("click",".msgAlert",function(){
			openMessage($(this));			
		})
		/**
		 * 超时预警弹窗
		 */
		$(document).on("click",".timeoutWarning",function(){
			openTimeOutWarning($(this));			
		})
		/**
		 * 笔录doc弹窗
		 */
		$(document).on("click",".attClass",function(){
			openWord($(this).attr("attId"));			
		})
	});
	
	/**
	 * 打开笔录word
	 * @returns
	 */
	function openWord(attId){
		var initData = {
				attId :attId
		}
		window.top.$.layerAlert.dialog({
			content:context + '/activityRecord/showWordJsp.action',
			pageLoading:true,
			title:"查看笔录",
			width:"1000px",
			height:"100%",
			initData:function(){
				return $.util.exist(initData)?initData:{} ;
			},
			shadeClose: false,
			btn: ['关闭'],
			success:function(layero, index){
				var win2 = win.$.layerAlert.frameWindow(index) ;
				win2.$.common.setIndex(index) ;
    		},
			callBacks:{
			    btn1:function(index, layero){
			    	window.top.layer.close(index);
			    }
			}
		});
	}
	/**
	 * 超时预警弹窗
	 * @returns
	 */
	function openTimeOutWarning(doc){
		var harCode=$(doc).attr("harCode");
		$.util.topWindow().$.layerAlert.dialog({
			content : context +  '/activityRecord/showTimeOutWarningMessage.action',
			pageLoading : true,
			title:"查看超时预警消息",
			width : "930px",
			height : "520px",
			btn:["返回"],
			callBacks:{				
				btn1:function(index, layero){
					$.util.topWindow().$.layerAlert.closeWithLoading(index);
					 //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				//弹窗打开成功后执行的操作	
			},
			initData:{  //传递给弹窗界面的参数
				"harCode" : harCode
			},
			end:function(){ //关闭弹窗后执行的操作
			}
		});
	}
	
	/**
	 * 预警消息页面
	 * @returns
	 */
	function openMessage(doc){		
		var harCode=$(doc).attr("harCode");
		$.util.topWindow().$.layerAlert.dialog({
			content : context +  '/activityRecord/showWarningMessage.action',
			pageLoading : true,
			title:"查看预警消息",
			width : "730px",
			height : "460px",
			btn:["返回"],
			callBacks:{				
				btn1:function(index, layero){
					$.util.topWindow().$.layerAlert.closeWithLoading(index);
					 //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				//弹窗打开成功后执行的操作	
			},
			initData:{  //传递给弹窗界面的参数
				"cuffId" : $(doc).attr("cuffId"),
				"hearingRoomId" : $(doc).attr("hearingRoomId"),
				"gridId" : $(doc).attr("gridId"),
				"startTime" : $(doc).attr("startTime"),
				"endTime" : $(doc).attr("endTime"),
				"place" : $(doc).attr("place"),
			},
			end:function(){ //关闭弹窗后执行的操作
			}
		});

	}
	/**
	 * 打开大华平台的视频接口
	 * @param doc
	 * @returns
	 */
	function openVCR(doc){
		var obj = {};
		obj.playbackdata = [];
		var time1 = {
			"beginTime" : $.date.timeToStr(parseInt($(doc).closest('a').attr(
					"strtime")), "yyyy-MM-dd HH:mm:ss"), // 录像开始时间
			"endTime" : $.date.timeToStr(parseInt($(doc).closest('a').attr(
					"endtime")), "yyyy-MM-dd HH:mm:ss"), // 录像结束时间
			"channelId" : $(doc).closest('a').attr("videoid"), // 视频通道ID
			"position" : $(doc).closest('a').attr("roomName") // 位置信息
		
		}
		obj.playbackdata.push(time1);
		var str = JSON.stringify(obj);
		window.top.getDsspocxObject().OnPlayBackForThird(str);
	}
	
	
	function onBodyDown(event) {
		if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			$("#menuContent").fadeOut("fast");
	        $("body").off("mousedown", onBodyDown);
		}
	}
	
	/**
	 * 打印
	 */
	$(document).on("click","#printAll",function() {
		var data = {id : harId};
		var form = $.util.getHiddenForm(context +'/harPrint/printHandlingAreaReceipt.action', data);
		$.util.subForm(form);
	});

	/**
	 * 解绑手环
	 */
	$(document).on("click","#unbound",function(){
		window.top.$.layerAlert.confirm({
			msg:"解绑后，将初始化嫌疑人手环及民警临时卡,请确认是否解绑？",
			title:"解绑提示",	  //弹出框标题
			yes:function(index, layero){
				$.ajax({
					url:context +'/leaveSituation/braceletControl.action',
					type:'post',
					data:{
						id:harId,
						type:"jb"
					},
					dataType:'json',
					success:function(successData){
						if(successData.flag == "true"){
							window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示"});
						}else{
							window.top.$.layerAlert.alert({msg:successData.errorMessage ,title:"提示"});
						}
					}
				})
			}
		});
	});
	/**
	 * 完成使用单
	 */
	$(document).on("click",".finishReceipt",function() {
		$.ajax({
			url:context +'/handlingAreaReceipt/handlingAreaReceiptCheck.action',
			type:'post',
			data:{
				id:harId
			},
			dataType:'json',
			success:function(successData){
				if(successData.flag == "true"){
					window.top.$.layerAlert.confirm({
						msg:"完成后信息将不可更改，是否要完成本次问讯？",
						title:"提示",	  //弹出框标题
						yes:function(index, layero){
							$.ajax({
								url:context +'/handlingAreaReceipt/doneHandlingAreaReceipt.action',
								type:'post',
								data:{
									id:harId
								},
								dataType:'json',
								success:function(successData){
									if(successData.flag == "true"){
										window.top.$.layerAlert.alert({msg:"操作成功！",title:"提示",end:function(){
											location.reload(true);
										}});
									}else{
										window.top.$.layerAlert.alert({msg:"操作失败！",title:"提示"});
									}
								}
							})
						}
					});
				}else{
					window.top.$.layerAlert.alert({msg:successData.msg,title:"提示",end:function(){
							if(successData.flag == "base"){
								window.location.href = $.util.fmtUrl(context +"/handlingAreaReceipt/showUpdateHandlingAreaReceiptPage.action?&&justShow=false" + $("#justShow").val() + "&&harId=" + $("#harId").val());
							}
						}
					});
				}
			}
		})
	
		
	});
	/**
	 * 维护活动记录
	 */
	$(document).on("click","#updateActivityRecord",function() {
		 window.location.href = $.util.fmtUrl(context +"/activityRecord/showUpdateActivityRecord.action?&&harId=" + $("#harId").val());
	});
	
	/**
	 * 活动记录信息
	 * @returns
	 */
	function initActivationRecord(){
		$.ajax({
			url:context +'/activityRecord/queryActivityRecordList.action',
			type:'post',
			data:{
				id:harId
				},
			dataType:'json',
			success:function(successData){
				var alertMsg =successData.resultMap.alertMsg;
				if(alertMsg!=null){
					$.layerAlert.alert({title:"提示",msg:alertMsg});
					return;
				}
				$('#tabs-5').css("display","block");
				var activityRecordBeanLst =successData.resultMap.recBean;
				if(activityRecordBeanLst.length ==0){
					return;
				}
				var closeDiv='</li><li class="s-f"><span class="icon-sf icon-finish">结束</span></li></ul>';
				var spresdDiv='<div class="row spresdClass" style="position: relative">'+  //展开按钮
							  '		<div style="position: absolute; bottom: -15px; left: 50%;">'+
							  '			<button class="btn btn-info">'+
							 '				<span class="icon-double-angle-down font20"></span>'+
								'		</button>'+
								'	</div>'+
								'</div>';
				var str=openDivAddClass("first");
				if(activityRecordBeanLst.length==1){
					str+=firstDataToStr(activityRecordBeanLst[0]); //只有一项,默认为一级
				}else{
					str+=firstDataToStr(activityRecordBeanLst[0]);
					var m=0;
					for(var i=1;i<activityRecordBeanLst.length;i++){
						if(activityRecordBeanLst[i].activityTypeName=='进入办案区'){//判断是否新开的一条记录
							if(activityRecordBeanLst[i-1].sjid!=null){
								str+='</ul></div>';
							}
							m++;
							if(m==1){
								str+=closeDiv+spresdDiv+'</div>'; //判断是否为第一块记录
							}else{
								str+=closeDiv+'</div>'; //关闭div
							}
							str+=openDivAddClass("selClass");  //打开下一个div并添加样式
							str+=firstDataToStr(activityRecordBeanLst[i]);
						}else if(activityRecordBeanLst[i].sjid==null||activityRecordBeanLst[i].sjid==''){ //代表是一级目录
							if(activityRecordBeanLst[i-1].sjid==null||activityRecordBeanLst[i-1].sjid==''){ //上一条是一级目录
								str+='</li>'+firstDataToStr(activityRecordBeanLst[i]);
							}else{//上一条是子目录
								str+='</ul></div></li>'+firstDataToStr(activityRecordBeanLst[i]);
							}
						}else if(activityRecordBeanLst[i].sjid==activityRecordBeanLst[i-1].id){//是上一条的子目录
							str+='<div class="son-history"><ul>';
							str+=secondDataToStr(activityRecordBeanLst[i]);
						}else if(activityRecordBeanLst[i].sjid==activityRecordBeanLst[i-1].sjid){//同级且为子级目录
							str+=secondDataToStr(activityRecordBeanLst[i]);
						}
					}
					if(activityRecordBeanLst[activityRecordBeanLst.length-1].sjid!=null&&activityRecordBeanLst[activityRecordBeanLst.length-1].sjid!=''){//最后一项是二级目录
						str+='</ul></div>';
					}
				}
				str+=closeDiv+'</div>';//关闭下模
				$('#tabs-5').append(str);
				$('.selClass').hide(); //将多余的隐藏
				$('.spresdClass').click(function(){
					$('.selClass').toggle();
				} )
			}
		})
	}
	/**
	 * 给div添加class样式
	 * @param data 要添加的class样式
	 * @returns
	 */
	function openDivAddClass(data){
		var openDiv='<div class="alert '+data+' alert-default history-content-activity">'+
		'<ul class="nav nav-tabs" width="100%">'+
		'<li class="s-f"><span class="icon-sf icon-start">开始</span></li>'; //开始建立div的上模
		return openDiv;
	}
	/**
	 * 一级目录转换
	 * @param data
	 * @returns
	 */
	function firstDataToStr(data){
		var retStr='<li><span class="icon-dot"></span>';
		    retStr+='<p class="time-box">';
		if(dateTime!=$.date.timeToStr(data.startTime, "yyyy-MM-dd")){
			dateTime=$.date.timeToStr(data.startTime, "yyyy-MM-dd");
			retStr+=$.date.timeToStr(data.startTime, "yyyy-MM-dd");
		}else{
			retStr+='<font color="aliceblue">'+blank+'</font>';
//			retStr+='<font color="aliceblue">'+$.date.timeToStr(data.startTime, "yyyy-MM-dd")+'</font>';
		}
		retStr+='<strong class="mar-left">'+$.date.timeToStr(data.startTime, "HH:mm")+'</strong>——<br/>';
		if(dateTime!=$.date.timeToStr(data.endTime, "yyyy-MM-dd")){
			dateTime=$.date.timeToStr(data.endTime, "yyyy-MM-dd");
			retStr+=$.date.timeToStr(data.endTime, "yyyy-MM-dd");
		}else{
//			retStr+='<font color="aliceblue">'+$.date.timeToStr(data.endTime, "yyyy-MM-dd")+'</font>';
			retStr+='<font color="aliceblue">'+blank+'</font>';
		}
		retStr+='<strong class="mar-left">'+$.date.timeToStr(data.endTime, "HH:mm")+'</strong></p>';
		
	     retStr+='<div class="con-box"><span class="arrow"></span>'+data.activityTypeName+'</div>'+
	     '<div class="append">';
	     if(data.tdid!=null){//判断是否有视频通道
	    	 retStr+='<a href="#" class="a-icon mar-right" videoId="'+data.tdid+'" roomName="'+(data.roomName==null?'':data.roomName)+'" strTime="'+(data.startTime==null?'':data.startTime)+'" endTime="'+(data.endTime==null?'':data.endTime)+'"><span class="icon-facetime-video micon-lg mar-right-sm"></span></a>';
	     }
	     	
			retStr+='<span class="name-tip mar-right"><span class="micon micon-room"></span>'+(data.roomName==null?'':data.roomName)+'</span>';
		if(data.ifOverTime>0){//判断是否为超时预警
			retStr+='<a class="timeoutWarning" harCode="'+data.formCode+'"><span class="name-tip2"><span class="icon-exclamation-sign micon-lg mar-right-sm color-red1 mar-left"></span>超时预警</span><span class="micon micon-comment">"'+data.ifOverTimeCount+'"</span></a>';
		}
		if(data.mesCount>0){
			retStr+='<a href="#" place="'+(data.roomName==null?'':data.roomName)+'" cuffId="'+(data.cuffId==null?'':data.cuffId)+'" hearingRoomId="'+(data.hearingRoomId==null?'':data.hearingRoomId)+'"  gridId="'+(data.roomId==null?'':data.roomId)+'" startTime="'+(data.startTime==null?'':data.startTime)+'" endTime="'+(data.endTime==null?'':data.endTime)+'" class="a-icon mar-right name-tip2 msgAlert">预警消息'+
			        '<span class="micon micon-comment">"'+data.mesCount+'"</span></a>';
		}
		if(data.attId!=null){ //附件判断
			retStr+='<a href="#" attId="'+data.attId+'" class="a-icon mar-right attClass"><span class="icon-file-alt micon-lg mar-right-sm"></span></a>';
		}
	     retStr+= '</div>';
		return retStr;
	}
	/**
	 * 二级目录转换
	 * @param data
	 * @returns
	 */
	function secondDataToStr(data){
		var retStr='<li><span class="icon-dot"></span>'+
						'<p class="son-conbox">'+data.activityTypeName+'</p> ';
		    retStr+='<span class="son-time">';
		if(dateTime!=$.date.timeToStr(data.startTime, "yyyy-MM-dd")){
			dateTime=$.date.timeToStr(data.startTime, "yyyy-MM-dd");
			retStr+=$.date.timeToStr(data.startTime, "yyyy-MM-dd HH:mm");
		}else{
			retStr+='<font color="aliceblue">'+blank+'</font>'+$.date.timeToStr(data.startTime, " HH:mm");
//			retStr+='<font color="aliceblue">'+$.date.timeToStr(data.startTime, "yyyy-MM-dd")+'</font>'+$.date.timeToStr(data.startTime, " HH:mm");
		}
		retStr+='——<br/>';
		if(dateTime!=$.date.timeToStr(data.endTime, "yyyy-MM-dd")){
			dateTime=$.date.timeToStr(data.endTime, "yyyy-MM-dd");
			retStr+=$.date.timeToStr(data.endTime, "yyyy-MM-dd HH:mm");
		}else{
			retStr+='<font color="aliceblue">'+blank+'</font>'+$.date.timeToStr(data.endTime, " HH:mm");
//			retStr+='<font color="aliceblue">'+$.date.timeToStr(data.endTime, "yyyy-MM-dd")+'</font>'+$.date.timeToStr(data.endTime, " HH:mm");
		}
		retStr+='</span>';
		if(data.tdid!=null){
			retStr+='<a href="#" videoId="'+data.tdid+'" roomName="'+(data.roomName==null?'':data.roomName)+'" strTime="'+(data.startTime==null?'':data.startTime)+'" endTime="'+(data.endTime==null?'':data.endTime)+'" class="a-icon mar-right">'+
			'<span	class="icon-facetime-video micon-lg mar-right-sm"></span></a>';
		}
		retStr+='<span class="name-tip mar-right"><span class="micon micon-room"></span>'+(data.roomName==null?'':data.roomName)+' </span> ';
		
		if(data.mesCount>0){
			retStr+='<a href="#" place="'+(data.roomName==null?'':data.roomName)+'" cuffId="'+(data.cuffId==null?'':data.cuffId)+'" hearingRoomId="'+(data.hearingRoomId==null?'':data.hearingRoomId)+'"  gridId="'+(data.roomId==null?'':data.roomId)+'" startTime="'+(data.startTime==null?'':data.startTime)+'" endTime="'+(data.endTime==null?'':data.endTime)+'" class="a-icon mar-right name-tip2 msgAlert">预警消息'+
			        '<span class="micon micon-comment">"'+data.mesCount+'"</span></a>';
		}
		return retStr;
	}
	
})(jQuery);