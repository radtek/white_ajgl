
var roomNum = "";
var formCode = "";
var personNum = "";
var jyName='';
var trialInfo=null;//打开笔录传递过来的参数
var userName=null;
var password=null;
$(document).ready(function() {
	testLoad();
	$(document).on("click","#openWriting",function(){
		onload();
	})

});
function testTS() {
	$.ajax({
		url : context + '/agtTaskbar/findMessageTS.action',
		type : "POST",
		dataType : "json",
		data : {
			"formCode" : "12",
		},
		success : function(data) {

		}
	});
}

function onload(){
	if(trialInfo!=null){
		var code=getjyCode();
		onloadSon($.base64.decode(code));
		//document.getElementById("dsspocxObject").UiMainCall(102, trialInfo);
	}else{
		var code=getjyCode();
		findUserMessage($.base64.decode(code));
	}
	
//	roomNum = getRoomNum();
	/*openWebForm("登陆","登陆",agtServerIp+"/agtLogin/login.action?roomId="
					+ roomNum, 496, 398); //后面可拼参数 https://www.baidu.com?a=XX
*/}

/*******************************************************************/

/**
 * 获取登录人的用户名和密码
 */
function findUserMessage(code){
	  $.ajax({
  		url : context + "/agtLogin/findUserMessage.action",
  		type : 'post',
  		dataType : 'json',
  		data:{
  			"code":code
  		},
  		success : function(successData) {
  			var user= successData.resultMap.userName; 
  			var psd= successData.resultMap.password;        			
  			if(user!=null){
  				userName=user;
  			 }else{
  				 userName=code;
  			 }
  			if(psd!=null){
  				password=psd;
  			}else{
  				password=code;
  			}
  			loginDH();
  			setTimeout(function(){
  				onloadSon(code);
			},3000)
  		},
  		error : function(errorData) {
  			
  		}
  	});
}



/**
 * 子页面登录
 * @returns
 */
function onloadSon(code){
	$.ajax({
		url : context + "/agtLogin/findOCX.action",
		type : 'post',
		dataType : 'json',
		data:{
			"code":code
		},
		success : function(successData) {
		    trialInfo = successData.resultMap.data; 
			document.getElementById("dsspocxObject").UiMainCall(102, trialInfo);
		},
		error : function(errorData) {
			
		}
	});
}

function  dsspocxObject :: NotifyWebTrialStatus(id, stat, strTime){
	$.ajax({
		url : context + "/agtLogin/findSyncTrialNoteStat.action",
		type : 'post',
		dataType : 'json',
		data:{
			"id":id,
			"stat":stat,
			"strTime":strTime
		},
		success : function(successData) {
			var bean=successData.resultMap.bean;
			if(bean.result==false){
				$.layerAlert.alert({title:"提示",msg:bean.errorMsg});
			}else{
				if(stat==2){//关闭并保持
					updateAskroomAllocationRecord(); //维护分配讯询问室活动记录离开时间
					exitProgram();//结束程序
				}
			}
		},
		error : function(errorData) {
			
		}
	});
}

function loginDH(){
	   var customizeInfo = JSON.stringify({SupportDvr:0, ProductName:'P700', webPort:"80"});
		document.getElementById("dsspocxObject").SetExtendProperty(customizeInfo);
		// cmsIp：大华平台访问ip; * webPort：大华平台访问端口； * loginName：用户登录账号； * loginPass：用户登录账号（明文） 
		document.getElementById("dsspocxObject").Login(dhServerIp, dhServerPort, userName, password, "1");
	}
	function getDsspocxObject(){
		return document.getElementById("dsspocxObject");
	}




/*******************************************************************/
function testLoad() {
	roomNum = getRoomNum();
/*	//初始化IE打开登陆url
	openWebForm("登陆","登陆",	agtServerIp+ "/agtLogin/login.action?roomId="
			+ roomNum, 496, 398); //后面可拼参数 https://www.baidu.com?a=XX
*/	
	
	loadWeb1(roomNum);
	//设定menu大小
	setMenuWindowSize(122, 390);
}
$(document).on("click", '#exit', function() {
	exitProgram();
})

function getRoomNum() {
	    roomNum = getConfig("Basic", "RoomNumber");
	  return roomNum;
	}
function setPersonNum(num) {
	personNum = num;//转成数字的身份证号	
	loadWeb2(personNum);//加载web2
}
function setFormCode(code) {
	formCode = code;
}
function loadWeb1(num) {
	jyName=getjyNum();//解析为数字的警员名称
	document.frames[0].location.href = "menu-1.jsp?roomNum=" + num+"&jyName="+jyName;
}

function loadWeb2(num) {
	personNum = num; //转成数字的身份证号
	document.frames[1].location.href = "menu-2.jsp?personNum=" + personNum;
}

//读取配置文件
function getConfig(node,key) {
    return window.external.getConfig(node,key); 
}
//读取配置文件
function getConfigAll(node) {
    return window.external.getConfigAllString(node); 
}

//windows方法
//IE打开url
function openUrlExplorer(Url) {
	window.external.openUrlExplorer(Url);
}
/*//winform内嵌ie打开url
function openWebForm(formName, Url, formWidth, formHeight) {
	window.external.openUrlByWinForm(formName, Url, formWidth, formHeight);
}*/
//winform内嵌ie打开url
function openWebForm(formId,formName,Url, formWidth, formHeight)
{
    window.external.openUrlByWinForm(formId,formName,Url, formWidth, formHeight);
}
//闪动小图标
function myStock() {
	window.external.Stock();
}
//设置小图标右键菜单大小
function setMenuWindowSize(x, y) {
	window.external.setWindowSize(x, y);
}
//设置小图标鼠标悬浮文字,并指定是否闪动图标
function showNewMessage(msgText, isStock) {
	window.external.showNewMessage(msgText, isStock);
}
//结束程序
function exitForm() {
	window.external.exitForm();
}
//结束程序
function exitProgram() {
    window.external.exitProgram();
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//维护分配讯询问室活动记录离开时间
function updateAskroomAllocationRecord(){
	$.ajax({
		url : context + "/agtLogin/updateAskroomAllocationRecord.action?id="+roomNum,
		type : 'post',
		dataType : 'json',
		success : function() {

		}
	});
}

//根据地址栏取得房间号
function getjyNum() {
    return GetQueryString("jyNum");
}
//根据地址栏取得房间号
function getjyCode() {
	return GetQueryString("jyCode");
}
