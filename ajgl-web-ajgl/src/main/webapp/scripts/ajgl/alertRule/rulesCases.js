(function($){
	"use strict";

	var data ;	//第一个预警数据
	var ruleList	//本模块所有的预警数据
	var ruleId;		//预警id;
	var flag = true;	//false时修改时点击保存  左侧列表不记录预警id
	var arrAlertTime=[];//触发消息时间
	$(document).ready(function() {	
		initSelect();
		//initDate();
		$(document).on("click",".delbtn",function(){//删除指定节点
			$(this).closest('span').remove();
		})
		$(document).on("click","#selUser1",function(){//选择用户1
			openUser(1);
		})
		$(document).on("click","#selUser2",function(){//选择用户2
			openUser(2);
		})
		$(document).on("click","#selRole1",function(){//选择角色1
			openRole(1);
		})
		$(document).on("click","#selRole2",function(){//选择角色2
			openRole(2);
		})
	});
	
	/**
	 * 角色弹窗
	 * @param num num为1:角色1  2:角色2
	 * @returns
	 */
	function openRole(num){
		$.util.topWindow().$.layerAlert.dialog({
			content : context +  '/alertRule/showRoleByPage.action',
			pageLoading : true,
			title:"角色查询",
			width : "900px",
			height : "400px",
			btn:["添加","返回"],
			callBacks:{
				btn1:function(index, layero){
					var cm = $.util.topWindow().frames["layui-layer-iframe"+index].$.roleAlert;
					var data = cm.getData();
					var personArr = [];//页面上有的值					
					if(num==1){
						$('#role1 .oldarr').each(function() {//获取已有的值
							personArr.push($(this).attr('strId'));
						    }
						)
					}else if(num==2){
						$('#role2 .oldarr').each(function() {//获取已有的值
							personArr.push($(this).attr('strId'));
						    }
						)
					}
					for(var k=0;k<data.roleId.length;k++){ //将新增的子案件code集合和已经添加的比较,有的就不添加
						var m=0;
						for(var j=0;j<personArr.length;j++){
							if(data.roleId[k]==personArr[j]){
								m=1;
								break;
							}
						}
						if(m==0){
							if(num==1){
								$('#role1').append('<span><span class="mar-left oldarr " strId="'+data.roleId[k]+'" >'+data.roleName[k]+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
							}else if(num==2){
								$('#role2').append('<span><span class="mar-left oldarr " strId="'+data.roleId[k]+'" >'+data.roleName[k]+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
							}
						}
					}
					$.util.topWindow().$.layerAlert.closeWithLoading(index);
				},
				btn2:function(index, layero){
					$.util.topWindow().$.layerAlert.closeWithLoading(index);
					 //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				//弹窗打开成功后执行的操作	
			},
			initData:{  //传递给弹窗界面的参数
				//"zazllxCode" : zazllxCode
			},
			end:function(){ //关闭弹窗后执行的操作
			  
			}
		});
	}
	
	/**
	 * 人员的弹窗
	 * @param num
	 * @returns
	 */
	function openUser(num){
		$.util.topWindow().$.layerAlert.dialog({
			content : context +  '/alertRule/showpersonByPage.action',
			pageLoading : true,
			title:"人员查询",
			width : "1150px",
			height : "600px",
			btn:["添加","返回"],
			callBacks:{
				btn1:function(index, layero){
					var cm = $.util.topWindow().frames["layui-layer-iframe"+index].$.personAlert;
					var data = cm.getData();
					var personArr = [];//页面上有的值					
					if(num==1){
						$('#user1 .oldarr').each(function() {//获取已有的值
							personArr.push($(this).attr('strId'));
						    }
						)
					}else if(num==2){
						$('#user2 .oldarr').each(function() {//获取已有的值
							personArr.push($(this).attr('strId'));
						    }
						)
					}
					for(var k=0;k<data.personId.length;k++){ //将新增的子案件code集合和已经添加的比较,有的就不添加
						var m=0;
						for(var j=0;j<personArr.length;j++){
							if(data.personId[k]==personArr[j]){
								m=1;
								break;
							}
						}
						if(m==0){
							if(num==1){
								$('#user1').append('<span><span class="mar-left oldarr " strId="'+data.personId[k]+'" >'+data.personName[k]+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
							}else if(num==2){
								$('#user2').append('<span><span class="mar-left oldarr " strId="'+data.personId[k]+'" >'+data.personName[k]+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
							}
						}
					}
					$.util.topWindow().$.layerAlert.closeWithLoading(index);
				},
				btn2:function(index, layero){
					$.util.topWindow().$.layerAlert.closeWithLoading(index);
					 //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				//弹窗打开成功后执行的操作	
			},
			initData:{  //传递给弹窗界面的参数
				//"zazllxCode" : zazllxCode
			},
			end:function(){ //关闭弹窗后执行的操作
			  
			}
		});
	}
	
	/**
	 * 初始化时间点
	 */
	/*function initDate(){
		var optionHour; 
		var optionMinute; 
		optionHour= "<option value='00'>00</option>";
		optionMinute= "<option value='00'>00</option>";
		$("#oneHour").append(optionHour);
		$("#oneMinute").append(optionMinute);
		$("#twoHour").append(optionHour);
		$("#twoMinute").append(optionMinute);
		$("#threeHour").append(optionHour);
		$("#threeMinute").append(optionMinute);
		$("#updateOneHour").append(optionHour);
		$("#updateOneMinute").append(optionMinute);
		$("#updateTwoHour").append(optionHour);
		$("#updateTwoMinute").append(optionMinute);
		$("#updateThreeHour").append(optionHour);
		$("#updateThreeMinute").append(optionMinute);
		for(var i = 1; i <= 23; i++){
			if(i<10){
				optionHour= "<option value='0"+ i +"'>0" + i + "</option>";
			}else{
				optionHour = "<option value='"+ i +"'>" + i + "</option>";
			}
			$("#oneHour").append(optionHour);
			$("#twoHour").append(optionHour);
			$("#threeHour").append(optionHour);
			$("#updateOneHour").append(optionHour);
			$("#updateTwoHour").append(optionHour);
			$("#updateThreeHour").append(optionHour);
		}
		for(var i = 1; i <= 59; i++){
			if(i<10){
				optionMinute= "<option value='0"+ i +"'>0" + i + "</option>";
			}else{
				optionMinute = "<option value='"+ i +"'>" + i + "</option>";
			}
			$("#oneMinute").append(optionMinute);
			$("#twoMinute").append(optionMinute);
			$("#threeMinute").append(optionMinute);
			$("#updateOneMinute").append(optionMinute);
			$("#updateTwoMinute").append(optionMinute);
			$("#updateThreeMinute").append(optionMinute);
		}
	}*/
	
	/**
	 * 配置
	 */
	$(document).on("click","#configuration",function(){
		if($.util.isEmpty(ruleId)){
			$.layerAlert.alert({msg:"请选择需要配置的预警规则"}) ;
			return;
		}
		
		$("#disable").attr('disabled',"true");
		$("#enabled").attr('disabled',"true");
		$("#refresh").attr('disabled',"true");
		
		$.each(ruleList,function(index,itm){
			if(ruleId == itm.id){
				$('input').iCheck('enable');
				$("#body").hide();
				$("#infoBody").show();
				$("#lookBody").empty();
				$("#enabled").show();
				$("#disable").show();
				
				$('#updateChufa1').attr('disabled',"true");
				$('#updateChufa2').attr('disabled',"true");
				
				$("#updateName").val(itm.name);
				$("#updateCode").val(itm.code);
				$("#updateShowTime").val(itm.popWindowDuaring);
				$("#updateFunctionExplain").val(itm.description);
				$("#updateYjtqsj").val(itm.updateTime);
				var arr=itm.way.split(",");
				$.each(arr,function(i,val){
					if(val == $.common.Constant.YJFS_XTXX()){
						$('#updateXtxxtx').iCheck('check');
					}else if(val == $.common.Constant.YJFS_PDA()){
						$('#updatePda').iCheck('check');
					}else if(val == $.common.Constant.YJFS_DX()){
						$('#updateDx').iCheck('check');
					}
				});
				
				if(itm.trigger == $.common.Constant.CFFS_DZ()){
					$('#updateChufa1').iCheck('check');
				}else{
					$('#updateChufa2').iCheck('check');
				}
				if(itm.alertTimeBefore!=null){ //提前预警时间
					$("#updateYjtqsj").val(itm.alertTimeBefore);
				}else{
					$("#updateYjtqsj").val("");
				}
				$('#firstAlertTimeAt').val((typeof(arrAlertTime[0])=="undefined"||arrAlertTime[0]==null)?"":arrAlertTime[0]);
				$('#secondAlertTimeAt').val((typeof(arrAlertTime[1])=="undefined"||arrAlertTime[1]==null)?"":arrAlertTime[1]);
				$('.delbtn').closest('span').remove();
				if(itm.alertUsers!=null){
					for(var i=0;i<itm.alertUsers[0].length;i++){
						$('#user1').append('<span><span class="mar-left oldarr " strId="'+itm.alertUsers[0][i].id+'" >'+itm.alertUsers[0][i].name+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
					}
				}
				if(itm.alertRoles!=null){
					for(var i=0;i<itm.alertRoles[0].length;i++){
						$('#role1').append('<span><span class="mar-left oldarr" strId="'+itm.alertRoles[0][i].id+'">'+itm.alertRoles[0][i].name+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
					}
				}
				if(itm.alertUsers!=null&&itm.alertUsers.length>1){
					for(var i=0;i<itm.alertUsers[1].length;i++){
						$('#user2').append('<span><span class="mar-left oldarr" strId="'+itm.alertUsers[1][i].id+'" >'+itm.alertUsers[1][i].name+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
					}
				}
				if(itm.alertRoles!=null&&itm.alertRoles.length>1){
					for(var i=0;i<itm.alertRoles[1].length;i++){
						$('#role2').append('<span><span class="mar-left oldarr" strId="'+itm.alertRoles[1][i].id+'">'+itm.alertRoles[1][i].name+'</span><button class="btn btn-xs btn-x delbtn">×</button></span>');
					}
				}
				/*if($.util.isEmpty(itm.alertTimeAt)){
					$('#updateTql').iCheck('check');
					$("#updateYjtqsj").val(itm.alertTimeBefore);
				}else{
					$('#updateSjd').iCheck('check');
					var alertTimeAt = itm.alertTimeAt.split(",")
					var oneArr = alertTimeAt[0].split(":");
					var twoArr = alertTimeAt[1].split(":");
					var threeArr = alertTimeAt[2].split(":");
					$("#updateOneHour").val(oneArr[0]);
					$("#updateOneMinute").val(oneArr[1]);
					$("#updateTwoHour").val(twoArr[0]);
					$("#updateTwoMinute").val(twoArr[1]);
					$("#updateThreeHour").val(threeArr[0]);
					$("#updateThreeMinute").val(threeArr[1]);
//					$.laydate.setTime(alertTimeAt[0], "#updateOne");
//					$.laydate.setTime(alertTimeAt[1], "#updateTwo");
//					$.laydate.setTime(alertTimeAt[2], "#updateThree");
					$("#updateTqlBody").hide();
					$("#updateSjdBody").show();
				}*/
				
			}
		});
	});
	
	/**
	 * 刷新
	 */
	$(document).on("click","#refresh",function(){
		location.reload();		
	});
	
	
	/**
	 * 启用
	 */
	$(document).on("click","#enabled",function(){
		operation(ruleId,$.common.Constant.QY())
		$("#enabled").attr('disabled',"true");
		$("#disable").removeAttr("disabled");
	});
	/**
	 * 停用按钮事件
	 */
	$(document).on("click","#disable",function(){
		operation(ruleId,$.common.Constant.TY())
		$("#disable").attr('disabled',"true");
		$("#enabled").removeAttr("disabled");
	});
	
	function operation(id,status){
		$.ajax({
			url:context +'/alertRule/updateStatus.action',
			type:'post',
			dataType:'json',
			data:{
				"id":id,
				"status":status
			},
			success:function(successData){
				if(!$.util.isEmpty(successData.msg)){
					$.layerAlert.alert({msg:successData.msg,icon:1,end:function(){
						location.reload();	
					}}) ;
				}else{
					$.layerAlert.alert({msg:"操作失败",icon:5,end:function(){
						
					}}) ;
				}
			},
			error:function(errorData){
				
			}
		});
	}
	
	/**
	 * 新建
	 */
	$(document).on("click","#addRules",function(){
		$("#lookBody").empty();
		$('input').iCheck('enable');
		$("#body").show();
		$("#infoBody").hide();
	});
	
	/**
	 * 保存
	 */
	$(document).on("click","#save",function(){
		var msg = "";
		var eg = /^[0-9]{1,10}$/;
		if($.util.isEmpty($("#name").val())||$("#name").val().length>20){
			msg += "预警名称(不能超出20个字符),";
		}
		if($.util.isEmpty($("#code").val())||$("#code").val().length>20){
			msg += "预警编码(不能超出20个字符),";
		}
		if($.icheck.getChecked("yjfs").length < 1){
			msg += "预警方式（至少选一项）,";
		}
		if(!eg.test($("#showTime").val()) || $.util.isEmpty($("#showTime").val())){
			msg += "浮出时间（请填写数字）,";
		}
		if($.util.isEmpty($("#functionExplain").val())||$("#functionExplain").val().length>150){
			msg += "预警规则(不能超出150个字符),";
		}
		var alertTimeArr = $.icheck.getChecked("yjsj");
		if($(alertTimeArr[0]).val()== "tql"){
			if($.util.isEmpty($("#yjtqsj").val())){
				msg += "预警提前时间,";
			}
		}else{
//			if($("#oneHour").val()=="00"){
//			msg += "每日第一次触发消息时间,";
//			}
			if($("#twoHour").val()+$("#twoMinute").val() < $("#oneHour").val()+$("#oneMinute").val()){
				msg += "每日第二次触发消息时间(时间开始需大于第一次提醒时间),";
			}
			if($("#threeHour").val()+$("#threeMinute").val() < $("#twoHour").val()+$("#twoMinute").val()){
				msg += "每日第三次触发消息时间(时间开始需大于第二次提醒时间),";
			}
		}
		if(!$.util.isEmpty(msg)){
			msg=msg.substring(msg.length-1,0);
			msg+="。";
			$.layerAlert.alert({msg:msg}) ;
			return;
		}
		var dataMap = new Object();
		var wayArr = $.icheck.getChecked("yjfs");
		var way = "";
		$.each(wayArr,function(index,val){
			way += $(val).val()+",";
		})
		way = way.substr(0, way.length-1);
		var triggerArr = $.icheck.getChecked("chufa");
		var trigger = $(triggerArr[0]).val();
		var alertTimeArr = $.icheck.getChecked("yjsj");
		if($(alertTimeArr[0]).val()== "tql"){
			dataMap["alertRuleBean.alertTimeBefore"] = $("#yjtqsj").val();
		}else{
			var one = $("#oneHour").val()+":"+$("#oneMinute").val();
			var two = $("#twoHour").val()+":"+$("#twoMinute").val();
			var three = $("#threeHour").val()+":"+$("#threeMinute").val();
			dataMap["alertRuleBean.alertTimeAt"] = one+","+two+","+three;
		}
		dataMap["alertRuleBean.name"] = $("#name").val();
		dataMap["alertRuleBean.code"] = $("#code").val();
		dataMap["alertRuleBean.theirModulu"] = ssmk;
		dataMap["alertRuleBean.trigger"] = trigger;
		dataMap["alertRuleBean.description"] = $("#functionExplain").val();
		dataMap["alertRuleBean.popWindowDuaring"] = $("#showTime").val();
		dataMap["alertRuleBean.status"] = $.common.Constant.QY();
		dataMap["alertRuleBean.way"] = way;
		$.ajax({
			url:context +'/alertRule/saveRule.action',
			type:'post',
			dataType:'json',
			data:dataMap,
			success:function(successData){
				if(!$.util.isEmpty(successData.msg)){
					$.layerAlert.alert({msg:successData.msg}) ;
				}else{
					$.layerAlert.alert({msg:"新建成功",end:function(){
						$("#body").hide();
						location.reload();
					}}) ;
				}
			},
			error:function(errorData){
					
			}
		});
	});
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function(){
		$("#body").hide();
		lookInfo(data);
	});
	
	/**
	 * 修改保存事件
	 */
	$(document).on("click","#update",function(){
		var msg = "";
		var eg = /^[0-9]{1,10}$/;
		if($.util.isEmpty($("#updateName").val())||$("#updateName").val().length>20){
			msg += "预警名称(不能超出20个字符),";
		}
		if($.util.isEmpty($("#updateCode").val())||$("#updateCode").val().length>20){
			msg += "预警编码(不能超出20个字符),";
		}
		if($.icheck.getChecked("updateYjfs").length < 1){
			msg += "预警方式（至少选一项）,";
		}
		if(!eg.test($("#updateShowTime").val()) || $.util.isEmpty($("#updateShowTime").val())){
			msg += "浮出时间（请填写数字）,";
		}
		if($.util.isEmpty($("#updateFunctionExplain").val())||$("#updateFunctionExplain").val().length>150){
			msg += "预警规则(不能超出150个字符),";
		}
//		var alertTimeArr = $.icheck.getChecked("updateYjsj");
//		if($(alertTimeArr[0]).val()== "tql"){
//			if($.util.isEmpty($("#updateYjtqsj").val())){
//				msg += "预警提前时间,";
//			}
//		}else{
//			if($("#updateOneHour").val()=="00"){
//				msg += "每日第一次触发消息时间,";
//			}
		if(!(/^\d+$/.test($("#updateYjtqsj").val()))){
			msg += "请正确填写提前预警分钟,";
		}
		if($("#firstAlertTimeAt").val()*60-$("#updateYjtqsj").val()<60){
			msg+="提醒时间设置过短“不得小于一小时";
		}
		if(!(/^\d+(\.\d{1})?$/.test($("#firstAlertTimeAt").val()))){
			msg += "每日第一次触发消息时间需以纯数字为起点，并支持小数后一位,";
		}
		if(!(/^\d+(\.\d{1})?$/.test($("#secondAlertTimeAt").val()))){
			msg += "每日第二次触发消息时间需以纯数字为起点，并支持小数后一位,";
		}
		if(parseInt($("#secondAlertTimeAt").val())< parseInt($("#firstAlertTimeAt").val())){
			msg += "每日第二次触发消息时间(时间需大于第一次提醒时间),";
		}
		var a=$('#user1 .delbtn').length;
		if($('#user1 .delbtn').length==0&&$('#role1 .delbtn').length==0){
			msg += "每日第一次触发消息必须选择用户或角色,";
		}
		if($('#user2 .delbtn').length==0&&$('#role2 .delbtn').length==0){
			msg += "每日第二次触发消息必须选择用户或角色,";
		}
//		if($("#updateThreeHour").val()+$("#updateThreeMinute").val() < $("#updateTwoHour").val()+$("#updateTwoMinute").val()){
//			msg += "每日第三次触发消息时间(时间需大于第二次提醒时间),";
//		}
//		}
		if(!$.util.isEmpty(msg)){
			msg=msg.substring(msg.length-1,0);
			msg+="。";
			$.layerAlert.alert({msg:msg}) ;
			return;
		}
		var dataMap = new Object();
		var wayArr = $.icheck.getChecked("updateYjfs");
		var way = "";
		$.each(wayArr,function(index,val){
			way += $(val).val()+",";
		})
		way = way.substr(0, way.length-1);
		var triggerArr = $.icheck.getChecked("updateChufa");
		var trigger = $(triggerArr[0]).val();
//		if($(alertTimeArr[0]).val()== "tql"){
			dataMap["alertRuleBean.alertTimeBefore"] = $("#updateYjtqsj").val();
//		}else{
//			var one = $("#updateOneHour").val()+":"+$("#updateOneMinute").val();
//			var two = $("#updateTwoHour").val()+":"+$("#updateTwoMinute").val();
//			var three = $("#updateThreeHour").val()+":"+$("#updateThreeMinute").val();
		var one =$("#firstAlertTimeAt").val();
		var two=$("#secondAlertTimeAt").val();
		dataMap["alertRuleBean.alertTimeAt"] = one+","+two;
//		}
		
		var person='';		
		if($('#user1 .delbtn').length!=0){
			$('#user1 .oldarr').each(function() { //获取已有的值
				person+=$(this).attr('strId')+",";
			    }
			)
			person=person.substr(0,person.length-1);
		}
		if($('#user2 .delbtn').length!=0){
			//if(person!=''){
				person+=";";
//			}
			$('#user2 .oldarr').each(function() { //获取已有的值
				person+=$(this).attr('strId')+",";
			}
			)
			person=person.substr(0,person.length-1)
		}
		if(person!=''){
			dataMap["alertRuleBean.alertUsersStr"] =person;
		}
		var role='';		
		if($('#role1 .delbtn').length!=0){
			$('#role1 .oldarr').each(function() { //获取已有的值
				role+=$(this).attr('strId')+",";
			    }
			)
			role=role.substr(0,role.length-1)
		}
		if($('#role2 .delbtn').length!=0){
//			if(role!=''){
				role+=";";
//			}
			$('#role2 .oldarr').each(function() { //获取已有的值
				role+=$(this).attr('strId')+",";
			}
			)
			role=role.substr(0,role.length-1)
		}
		if(role!=''){
			dataMap["alertRuleBean.alertRolesStr"] = role;
		}
		dataMap["alertRuleBean.name"] = $("#updateName").val();
		dataMap["alertRuleBean.code"] = $("#updateCode").val();
		dataMap["alertRuleBean.theirModulu"] = ssmk;
		dataMap["alertRuleBean.trigger"] = trigger;
		dataMap["alertRuleBean.description"] = $("#updateFunctionExplain").val();
		dataMap["alertRuleBean.popWindowDuaring"] = $("#updateShowTime").val();
		dataMap["alertRuleBean.status"] = $.common.Constant.QY();
		dataMap["alertRuleBean.way"] = way;
		dataMap["alertRuleBean.id"] = ruleId;
		$.ajax({
			url:context +'/alertRule/saveRule.action',
			type:'post',
			dataType:'json',
			data:dataMap,
			success:function(successData){
				if(!$.util.isEmpty(successData.msg)){
					$.layerAlert.alert({msg:successData.msg}) ;
				}else{
					$.layerAlert.alert({msg:"修改成功",end:function(){
						$("#infoBody").hide();
						flag = false;
						initSelect();
						lookInfoData(ruleId);
					}}) ;
				}
			},
			error:function(errorData){
					
			}
		});
		
		
	});
	
	/**
	 * 修改取消事件
	 */
	$(document).on("click","#updateCancel",function(){
		$("#infoBody").hide();
		//$("#enabled").hide();
		//$("#disable").hide();
		location.reload();
	});
	
	/**
	 * 提前量
	 */
	$(document).on("ifChecked","#tql",function(){
		$("#tqlBody").show();
		$("#sjdBody").hide();
	});
	/**
	 * 时间段
	 */
	$(document).on("ifChecked","#sjd",function(){
		$("#tqlBody").hide();
		$("#sjdBody").show();
	});
	
	/**
	 * 修改时的提前量
	 */
	$(document).on("ifChecked","#updateTql",function(){
		$("#updateTqlBody").show();
		$("#updateSjdBody").hide();
	});
	/**
	 * 修改时的时间段
	 */
	$(document).on("ifChecked","#updateSjd",function(){
		$("#updateTqlBody").hide();
		$("#updateSjdBody").show();
	});
	
	/**
	 * 初始化左侧预警列表
	 */
	function initSelect(){
		$.ajax({
			url:context +'/alertRule/findRulesByTheirModulu.action',
			async:false,
			type:'post',
			dataType:'json',
			data:{
				"theirModulu":ssmk
			},
			success:function(successData){
				ruleList = successData.alertRuleBeanList;
				if(flag){
					$("#select").empty();
					$.each(ruleList,function(index,itm){
						var a;
						if(index == 0){
							data = itm;
							a = '<a href="#" class="list-group-item active">预警业务对象 </a>'+
							'<a href="#" class="list-group-item list-group-item-info a" id="'+ itm.id +'">'+
							'<span class="glyphicon glyphicon-menu-right pull-right span" id="span'+itm.id+'" style="margin-top: 4px">'+
							'</span>'+ itm.name +'</a>';
							ruleId = itm.id;
							lookInfo(itm);
						}else{
							a = '<a href="#" id="'+ itm.id +'" class="list-group-item a"><span class="span" id="span'+itm.id+'" style="margin-top: 4px"></span>'+ itm.name +'</a>'
						}
						$("#select").append(a);
					})
				}
			},
			error:function(errorData){
					
			}
		});
	}
	
	/**
	 * 左侧预警标签点击事件
	 */
	$(document).on('click','.a',function(){
		var tr =$(this).parent("a");
		var id = tr.context.id;
		lookInfoData(id);
	});
	
	function lookInfoData(id){
		$(".a").removeClass("list-group-item-info");
		$("#"+id).addClass("list-group-item-info");
		$(".span").removeClass("glyphicon glyphicon-menu-right pull-right");
		$("#span"+id).addClass("glyphicon glyphicon-menu-right pull-right");
		$("#infoBody").hide();
		$("#body").hide();
		//$("#enabled").hide();
		//$("#disable").hide();
		$.each(ruleList,function(index,val){
			if(id==val.id){
				ruleId = val.id;
				lookInfo(val)
				if(!$.util.isEmpty(val.alertTimeAt)){
					$("#tqlShow").hide();
					$("#sjdShow").show();
				}
			}
		})
	}
	

	/**
	 * 点击左侧标签查看预警规则详情
	 */
	function lookInfo(itm){
		if(itm == null){
			return;
		}
		$("#enabled").removeAttr("disabled");
		$("#disable").removeAttr("disabled");
		$("#refresh").removeAttr("disabled");
		if(itm.statusName == "启用"){
			$("#enabled").attr('disabled',"true");
		}else if(itm.statusName == "停用"){
			$("#disable").attr('disabled',"true");
		}
		$("#lookBody").empty();
		var body='<div style="margin: 0 auto;">' +
		'<div class="row row-mar">' +
				'<div class="col-xs-3">' +
					'<label class="control-label">预警名称：</label>' +
				'</div>' +
				'<div class="col-xs-3">' +
					'<p style="padding:4px;">'+ itm.name +'</p>' +
				'</div>' +
				'<div class="col-xs-3">' +
					'<label class="control-label">预警编码：</label>' +
				'</div>' +
				'<div class="col-xs-3">' +
					'<p style="padding:4px;">'+ itm.code +'</p>' +
				'</div>' +
		'</div>' +
			'<div class="row row-mar">' +
				'<div class="col-xs-3">' +
					'<label class="control-label">预警方式：</label>' +
				'</div>';
		var xtxx="";
		var pda="";
		var dx="";
		var result=itm.way.split(",");
		$.each(result,function(index,r){
			if(r == $.common.Constant.YJFS_XTXX()){
				xtxx = 'checked="checked"';
			}else if(r == $.common.Constant.YJFS_PDA()){
				pda = 'checked="checked"';
			}else if(r == $.common.Constant.YJFS_DX()){
				dx = 'checked="checked"';
			}
		})
		body += '<div class="col-xs-8">' +
					'<div class="m-icheck-group">' +
						'<p class="col-xs-4">' +
							'<input type="checkbox" '+xtxx+' readonly="readonly" class="icheckbox" name="lookyjfs">系统消息提示' +
						'</p>' +
						'<p class="col-xs-4">' +
							'<input type="checkbox" '+pda+' readonly="readonly" class="icheckbox" name="lookyjfs">PDA' +
						'</p>' +
						'<p class="col-xs-4">' +
							'<input type="checkbox" '+dx+' readonly="readonly" class="icheckbox" name="lookyjfs"> 短信' +
						'</p>' +
					'</div>' +
				'</div>' +
			'</div>' +
			'<div class="row row-mar">' +
				'<div class="col-xs-3">' +
					'<label class="control-label">消息展示方式：</label>' +
				'</div>' +
				'<div class="col-xs-8">' +
					'<div class="m-icheck-group">' +
						'<p class="col-xs-4">' +
							'右下角浮窗' +
						'</p>' +
						'<p class="col-xs-6">' +
							'浮出时间：<input type="text" readonly="readonly" style="width: 50px" value="'+itm.popWindowDuaring+'">秒' +
						'</p>' +
					'</div>' +
				'</div>' +
			'</div>' +
			'<div class="row row-mar">' +
				'<div class="col-xs-3">' +
					'<label class="control-label">预警功能说明：</label>' +
				'</div>' +
				'<div class="col-xs-8">' +
					'<textarea readonly="readonly" class="form-control input-sm" rows="3">'+itm.description+'</textarea>' +
				'</div>' +
			'</div>';
		body+='<div class="row row-mar">' +
				'<div class="col-xs-2">' +
					'<label class="control-label">触发方式：</label>' +
				'</div>';
		var dzcf = "";
		var xtcf = "";
		if(itm.trigger == $.common.Constant.CFFS_DZ()){
			dzcf = 'checked="checked"';
		}else{
			xtcf = 'checked="checked"';
		}
		body+='<div class="col-xs-10">'+
		       '<div class="row m-icheck-group" style="width:400px; margin-bottom:5px;"> '+
		       '<p class="col-xs-12 row-mar"><input type="radio" '+dzcf+' class="icheckradio" readonly="readonly"> 动作触发</p>'+
		       '<p class="col-xs-4"><input type="radio" '+xtcf+' class="icheckradio" readonly="readonly"> 系统自动</p>'+		
		        '<p class="col-xs-8">预警提前时间：&nbsp;<input class="form-control input-sm  m-inline" style="width:60px;" value="'+(itm.alertTimeBefore==null?'':itm.alertTimeBefore)+'" readonly="readonly"> 分钟</p>'+
		        ' </div>';
		
		if(itm.alertTimeAt != null){//触发时间
			arrAlertTime=itm.alertTimeAt.split(",");
		}
				
//		if(itm.alertUsers!=null&&itm.alertUsers!=""){//人员
//			 arrUsers=itm.alertUsers.split(";");
//			 itm.alertUsers[0]=arrUsers[0].split(",");
//			 if(arrUsers[1]!=null&&arrUsers[1]!=""){
//				 itm.alertUsers[1]=arrUsers[1].split(",");
//			 }
//		}
//				
//		if(itm.alertRoles!=null&&itm.alertRoles!=""){//角色
//			arrRoles=itm.alertRoles.split(";");
//			itm.alertRoles[0]=arrRoles[0].split(",");
//			 if(arrUsers[1]!=null&&arrRoles[1]!=""){
//				 itm.alertRoles[1]=arrRoles[1].split(",");
//			 }
//		}
		body+='<div class="alert alert-info" style="width:70%;min-width:620px;"><h4 class="font14 m-bold">预警时间：</h4>'+
		      '<p class="m-line"></p>'+
		      '<p class="row-mar"><span style="display:inline-block; width:180px;">首次进入办案区时长：</span>'+
		      '<input class="form-control input-sm  m-inline" style="width:60px;" readonly="readonly" value="'+((typeof(arrAlertTime[0])=="undefined"||arrAlertTime[0]==null)?"":arrAlertTime[0])+'"> 小时  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      '+
		      ' 消息接收用户/角色<button class="btn btn-xs btn-bordered">选择用户</button><button class="btn btn-xs btn-bordered">选择角色</button></p>'+
		      '<p class="row-mar"><span class="color-gray">用户：</span>';
		if(itm.alertUsers!=null){
			for(var i=0;i<itm.alertUsers[0].length;i++){
				body+='<span><span class="mar-left">'+itm.alertUsers[0][i].name+'</span><button class="btn btn-xs btn-x">×</button></span>';
			}
		}
		    //  '<span><span class="mar-left">李龟年</span><button class="btn btn-xs btn-x">×</button></span><span class="mar-left">韩愈</span><button class="btn btn-xs btn-x">×</button>'+
		body+= '</p>'+
		      '<p><span class="color-gray">角色：</span>';
		if(itm.alertRoles!=null){
			for(var i=0;i<itm.alertRoles[0].length;i++){
				body+='<span><span class="mar-left">'+itm.alertRoles[0][i].name+'</span><button class="btn btn-xs btn-x">×</button></span>';
			}      
		}
		   // '<span class="mar-left">值班监控员</span><button class="btn btn-xs btn-x">×</button>'+
		body+='</p>'+
		      '<p class="m-line"></p>'+
		      ' <p class="row-mar"><span style="display:inline-block; width:180px;">每日第二次触发消息时间：</span>'+
		      '<input class="form-control input-sm  m-inline" style="width:60px;"readonly="readonly" value="'+((typeof(arrAlertTime[1])=="undefined"||arrAlertTime[1]==null)?"":arrAlertTime[1])+'"> 小时  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       '+
		      '消息接收用户/角色<button class="btn btn-xs btn-bordered">选择用户</button><button class="btn btn-xs btn-bordered">选择角色</button></p>'+
		      '<p class="row-mar"><span class="color-gray">用户：</span>';
		if(itm.alertUsers!=null&&itm.alertUsers.length>1){
			for(var i=0;i<itm.alertUsers[1].length;i++){
				body+='<span><span class="mar-left">'+itm.alertUsers[1][i].name+'</span><button class="btn btn-xs btn-x">×</button></span>';
			}
		}
		//    '<span class="mar-left">孟非</span><button class="btn btn-xs btn-x">×</button>'+
		body+='</p>'+
		      '<p><span class="color-gray">角色：</span>';
		if(itm.alertRoles!=null&&itm.alertRoles.length>1){
			for(var i=0;i<itm.alertRoles[1].length;i++){
				body+='<span><span class="mar-left">'+itm.alertRoles[1][i].name+'</span><button class="btn btn-xs btn-x">×</button></span>';
			}
		}
//		      '<span class="mar-left">监控指挥员</span><button class="btn btn-xs btn-x">×</button>'+
		body+='</p>'+
		      '</div>'+
		      '</div>'+
		      '</div>';
//		body +='<div class="col-xs-8">' +
//					'<div class="row m-icheck-group" style="margin-bottom: 10px;">' +
//						'<p class="col-xs-4">' +
//							'<input type="radio" '+dzcf+' readonly="readonly" class="icheckradio" name="lookchufa" >动作触发' +
//						'</p>' +
//						'<p class="col-xs-4">' +
//							'<input type="radio" '+xtcf+' readonly="readonly" class="icheckradio" name="lookchufa" >系统自动' +
//						'</p>' +
//					'</div>' +
//				'</div>' +
//			'</div>' +
//			'<div class="row row-mar">' +
//				'<div class="col-xs-3">' +
//					'<label class="control-label">预警时间方案：</label>' +
//				'</div>';
//		var tql = "";
//		var sjd = "";
//		if($.util.isEmpty(itm.alertTimeAt)){
//			tql = 'checked="checked"';
//		}else{
//			sjd = 'checked="checked"';
//		}
//				body += '<div class="col-xs-8">' +
//				'<div class="row m-icheck-group" style="margin-bottom: 10px;">' +
//				'<p class="col-xs-4">' +
//				'<input type="radio" '+ tql +' readonly="readonly" class="icheckradio" name="lookyjsj" >提前量' +
//				'</p>' +
//				'<p class="col-xs-4">' +
//				'<input type="radio" '+ sjd +' readonly="readonly" class="icheckradio" name="lookyjsj" >时间点' +
//				'</p>' +
//				'</div>';
//			if(itm.alertTimeAt == null){
//				body += '<div class="alert alert-info" style="padding: 10px;" id="tqlShow">' +
//				'<div class="row">' +
//				'<p class="col-xs-4 m-label-left">预警提前时间：</p>' +
//				'<p class="col-xs-2 input-group" style="width: 120px;">' +
//				'<input class="form-control input-sm" readonly="readonly" value="'+itm.alertTimeBefore+'"><span' +
//				' class="input-group-addon">分钟</span>' +
//				'</p>' +
//				'</div>' +
//				'</div>';
//		}else{
//			var arr = itm.alertTimeAt.split(",");
//			body += '<div class="alert alert-info" style="padding:10px; display: none;"  id="sjdShow">' +
//			'<div class="row row-mar">' +
//			'<p class="col-xs-4 m-label-left">每日第一次触发消息时间</p>' +
//			'<p class="col-xs-3 input-group"><input class="form-control input-sm" id="lookOne" readonly="readonly" value="'+arr[0]+'"><span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span></p>' +
//			'</div> ' +
//			'<div class="row row-mar">' +
//			'<p class="col-xs-4 m-label-left">每日第二次触发消息时间</p>' +
//			'<p class="col-xs-3 input-group"><input class="form-control input-sm" id="lookTwo" readonly="readonly" value="'+arr[1]+'"><span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span></p>' +
//			'</div> ' +
//			'<div class="row row-mar">' +
//			'<p class="col-xs-4 m-label-left">每日第三次触发消息时间</p>' +
//			'<p class="col-xs-3 input-group"><input class="form-control input-sm" id="lookThree" readonly="readonly" value="'+arr[2]+'"><span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span></p>' +
//			'</div> ' +
//			'</div> ' +
//			'</div>';
//		}
	    body += '</div>' +
			'<div id="timeAndStart">' +
				'<div class="row row-mar" >' +
					'<div class="col-xs-3">' +
						'<label class="control-label">更新时间：</label>' +
					'</div>' +
					'<div class="col-xs-3">' +
						'<input class="form-control input-sm" readonly="readonly" value="'+itm.updateTimeStr+'">' +
					'</div>' +
				'</div>' +
				'<div class="row row-mar">' +
					'<div class="col-xs-3">' +
						'<label class="control-label">状态：</label>' +
					'</div>' +
					'<div class="col-xs-3">' +
						'<input class="form-control input-sm" readonly="readonly"  value="'+itm.statusName+'">' +
					'</div>' +
				'</div>' +
			'</div>' +
		'</div>';
		$("#lookBody").append(body);
		$('input').iCheck('disable');
	}
				
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
//		findAllAutoFlow:findAllAutoFlow
	});	
})(jQuery);