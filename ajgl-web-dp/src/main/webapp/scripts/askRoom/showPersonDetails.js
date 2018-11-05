(function($) {
	"use strict";
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	var harId = initData.harId;
	var harCode=initData.harCode;
	var list=[]; //存储活动记录的list 
    var listId=[];//活动记录视频定时timeID的集合
    var channelId; //视频通道
	$(document).ready(function() {		
		initHarData(harId);
		initTimerLst(harId);//加载活动记录
		$(document).on("click",".play",function(){
			var inde=$(this).attr("indexId");
			onloadVCR(inde);
		})
		window.setTimeout(loginDH,500); //延迟加载
	})
	//加载大华插件
	function loginDH() {
		var customizeInfo = JSON.stringify({
			SupportDvr : 0,
			ProductName : 'P700',
			webPort : "80"
		});
		document.getElementById("dsscocx").SetExtendProperty(customizeInfo);
		/*
		 * cmsIp：大华平台访问ip; * webPort：大华平台访问端口； * loginName：用户登录账号； *
		 * loginPass：用户登录账号（明文）
		 */
		document.getElementById("dsscocx").Login("52.4.1.210", "9000", "system",
				"123456", "1");
	}
	function getDsspocxObject() {
		return document.getElementById("dsscocx");
	}
	
	/**
	 * 加载基本信息
	 * @param harId
	 * @returns
	 */
	function initHarData(harId){
		$.ajax({
			url:context +'/handlingAreaReceipt/findBasicCaseByHarId.action',
			type:'post',
			data:{
				id:harId
			},
			dataType:'json',
			success:function(successData){
				var bcb = successData.basicCaseBean;
				initQkData(bcb.byQuestioningPeopleIdentifyNum);
				$("#handlingAreaReceiptNum").text((bcb.handlingAreaReceiptNum==null?'':bcb.handlingAreaReceiptNum));
				$("#enterIntoTimeStr").text((bcb.enterIntoTime == null) ? "" : $.date.timeToStr(bcb.enterIntoTime, "yyyy-MM-dd HH:mm"));
				$("#byQuestioningPeopleName").text((bcb.byQuestioningPeopleName==null?'':bcb.byQuestioningPeopleName));
				$("#sexStr").text((bcb.sexStr==null?'':bcb.sexStr));
				$("#bangleCode").text((bcb.bangleCode==null?'':bcb.bangleCode));
				$("#door").text((bcb.door == null) ? "" : bcb.door);
				$("#byQuestioningPeopleAddress").text((bcb.byQuestioningPeopleAddress == null) ? "" : bcb.byQuestioningPeopleAddress);
				$("#byQuestioningPeopleTelphoneNumber").text((bcb.byQuestioningPeopleTelphoneNumber == null) ? "" : bcb.byQuestioningPeopleTelphoneNumber);
				$("#byQuestioningPeopleIdentifyNum").text((bcb.byQuestioningPeopleIdentifyNum == null) ? "" : bcb.byQuestioningPeopleIdentifyNum);
				$("#lawCaseName").text((bcb.lawCaseName == null) ? "" : bcb.lawCaseName);
				if(!$.util.isBlank(bcb.lawCase)){
					$("#lawCase").html('<a href="###" id="lawCaseName" lawCase="' + bcb.lawCase + '" class="showCase">' + (bcb.lawCaseName==null?'':bcb.lawCaseName) + '</a>');
					
				}else{
					$("#lawCase").html('<span id="lawCaseName">' + (bcb.lawCaseName==null?'':bcb.lawCaseName) + '</span>');
				}
				$("#causeOfLawCaseStr").text(bcb.causeOfLawCaseStr == null ? "" : bcb.causeOfLawCaseStr);
				$("#enterAreaReasons").text((bcb.enterAreaReasons == null) ? "" : bcb.enterAreaReasons);
				$("#handlingPolice").text((bcb.handlingPolice == null) ? "" : bcb.handlingPolice);
				$("#cameraPicture").attr("src", "data:image/png;base64," + bcb.photo);
				if($.util.exist(bcb.attach)){
					$("#attach").html("<a href='###' id='dlws'>" + bcb.attach.name + "</a>");
					$(document).on("click","#dlws",function(){
						//$.layerAlert.img("data:image/png;base64," + bcb.attach.base64Str);
						window.open(context + "/handlingAreaReceipt/downloadFile.action?attachmentId="+ bcb.attach.id);
					})
				}
				switch(bcb.status){
				case "进行中":
					$("#statusSpan").addClass("e-green-lg");
					$("#statusSpan").text("进行中");
					break;
				case "待问":
					$("#statusSpan").addClass("e-red-lg");
					$("#statusSpan").text("待问");
					break;
				case "已完成":
					$("#statusSpan").addClass("e-blue-lg");
					$("#statusSpan").text("已完成");
					break;
				}
			}
		});
		//状态设置
	}
	/**
	 * 加载活动记录
	 * @param harId
	 * @returns
	 */
	function initTimerLst(harId){
		$.ajax({
			url:context +'/dp/findActivationRecord.action',
			type:'post',
			data:{
				harCode:harCode
			},
			dataType:'json',
			success:function(successData){
				list=successData.resultMap.activationRecordList;
				var todayLong=successData.resultMap.todayLong;
				$("#timerLst").empty();
				if(list.length>0){
					$('#gridName').text(list[0].gridName);
				}
				var str='<div class="room-group" style="display: block;">';
				for(var i=0;i<list.length;i++){
					if(i%6==0&&i!=0){  //六个为一组
						str+='</div><div class="room-group" style="display: none;">';
					}
					if(list[i].endTime==null&&list[i].startTime<todayLong){
						str+='<li class="current" passageid="'+list[i].passageId+'">';
					}else{
						str+='<li>';
					}
					str+='<a href="##" class="dot"></a>'
							+ '<div class="time"><p>'+list[i].description+'</p><p>'+$.date.timeToStr(list[i].startTime, "yyyy-MM-dd")+'</p><p>'+$.date.timeToStr(list[i].startTime, "HH:mm")+'</p>'
							+'<p>——</p>';
					if(list[i].endTime!=null){
						str+='<p>'+$.date.timeToStr(list[i].endTime, "yyyy-MM-dd")+'</p><p>'+$.date.timeToStr(list[i].endTime, "HH:mm")+'</p>';
					}
					str+='</div>'
						+ '<a href="##" indexId="'+i+'" starttime="'+list[i].startTime+'" endtime="'+list[i].endTime+'" passageid="'+list[i].passageId+'" gridid="'+list[i].gridId+'" class="play"></a></li>';
				}
				str+='</div>';
				$("#timerLst").append(str);
//				$.when(
//						window.setTimeout(loginDH,500)
//				).done(
//						onloadVCR(0)//加载视频从零开始
//				)
				setTimeout(onloadVCR(0),2500) //加载视频从零开始
				changePage();//延时切换视频
			},error:function(){
				
			}
			})
		
	}
	
	/**
	 * 延时加载视频
	 * @returns
	 */
	function onloadVCR(index){
		alert("我是加载延时视频....");
		clearTime();//加载前先清空
		var delayTime=0;
		if(list.length==0){
			return ;
		}else if(list.length==1){//只有一条的话,延时0毫秒加载
			var timeId= setTimeout(initVCRByStartAndEnd,0,parseInt(list[index].startTime),parseInt(list[index].endTime),list[index].passageId);
			listId.push(timeId);
		}else if(list.length>1){
			var timeId= setTimeout(initVCRByStartAndEnd,0,parseInt(list[index].startTime),parseInt(list[index].endTime),list[index].passageId);
			listId.push(timeId);
			for(var i=parseInt(index)+1;i<list.length;i++){
				delayTime+=parseInt(list[i-1].endTime)-parseInt(list[i-1].startTime); //获取视频播放的时间--可在此处添加延时的时间
				var strTime=parseInt(list[i-1].startTime);
				var endTime=parseInt(list[i-1].endTime);
				var tmId=setTimeout(initVCRByStartAndEnd,delayTime,strTime,endTime,list[index].passageId);//延时方法,延时时间,参数.....
                listId.push(tmId);		
			}
		}
	}
	
	/**
	 * 删除定时
	 * @returns
	 */
	function clearTime(){
		for(var i=0;i<listId.length;i++){
			clearTimeout(listId[i]);//循环删除定时
		}
		listId=[];//定时id集合置空
	}
	
	
	/**
	 * 通过开始时间和结束时间加载大华视频
	 * @returns
	 */
	function initVCRByStartAndEnd(str,end,channelId){
//		alert(str+"==="+end+"==="+channelId);
		var data={

			      "channelId": channelId,      //视频通道ID

			      "beginTime": $.date.timeToStr(parseInt(str), "yyyy-MM-dd HH:mm:ss"),      //录像开始时间

			      "endTime": $.date.timeToStr(parseInt(end), "yyyy-MM-dd HH:mm:ss")     //录像结束时间

			   }
		getDsspocxObject().OnPlayBcakForThird(data,  118);
	}
	
	/**
	 * 休眠方法
	 * @param numberMillis
	 * @returns
	 */
	function sleep(numberMillis) {
	    var now = new Date();
	    var exitTime = now.getTime() + numberMillis;
	    while (true) {
	        now = new Date();
	        if (now.getTime() > exitTime)
	            return;
	    }
	}
	
	function changePage() {
		var $obj = $('#timerLst .room-group');
		var len = $obj.length;
		var i = 0;
		$("#next").click(function() {
			i++;
			if (i == len) {
				i = 0;
			}
			$obj.stop(true, true).hide().eq(i).fadeIn(600);
			return false;
		});
     //每2秒，自动切换。触发".next"的click事件
		var MyTime = setInterval(function() {
			$("#next").trigger("click");
		}, 4000);
	}
	
	
	function initQkData(idNum){
		var reg = /(^\d{15}$)|(^\d{17}(\d|X|x)$)/;
		if(reg.test(idNum)){
			$.ajax({
				url:context +'/handlingAreaReceipt/findPersonsByIdNum.action',
				type:'post',
				data:{code : idNum},
				dataType:'json',
				success:function(successData){
					var person = successData.person;
					$("#idCardPicture").attr("src", "data:image/png;base64," + person.picture);
					var data = successData.criminalRecordLst;
					for(var i in data){
						var str = '<tr><td width="10%">' + i + '</td>'
							+ '<td width="50%">' + data[i].caseName + '</td>'
							+ '<td width="20%">' + data[i].police + '</td>'
							+ '<td>' + data[i].caseTime + '</td></tr>';
						$("#qkTable tbody").append(str);
					}
				}
			});
		}
	}
})(jQuery);