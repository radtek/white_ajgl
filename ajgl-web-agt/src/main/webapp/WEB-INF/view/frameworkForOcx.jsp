<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
</head>
<%@ page import="com.taiji.pubsec.ajqlc.util.ServiceConstant,
com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil,
com.taiji.pubsec.springsecurity.resource.util.MySecureUser"%> 
<%
MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
Map<String, Object> userMap = user.getUserMap() ;
Map<String, String> mPerson = new HashMap<String, String>(0) ;
Map<String, String> mOrg = new HashMap<String, String>(0) ;
if(userMap.get("person")!=null){
	mPerson = (Map<String, String>)userMap.get("person");
}
if(userMap.get("org")!=null){
	mOrg = (Map<String, String>)userMap.get("org");
}
%>

<script>
var currentUserId = '<%=mPerson.get("id")%>';
var currentUserName = '<%=mPerson.get("name")%>';
var currentUserCode = '<%=mPerson.get("code")%>';
var currentUnitName = '<%=mOrg.get("shortName")%>';
var currentUnitCode = '<%=mOrg.get("code")%>';
var personCode = '<%=mPerson.get("code")%>';
var dhServerIp = '<%=ServiceConstant.DHSERVERIP%>';
</script>
<object id="dsspocxObject" classid="CLSID:1D1A0ACA-3A53-4589-88B8-94A0DDC47EA6" onreadystatechange="" style="display: none;"></object>
<iframe id="ifmWindow" height="100%" width="100%" frameborder="0" src="<%=context%>/askRoom/showAskRoomListPage.action">
</iframe>
<script type="text/javascript">
var snum = 0;
var timerJump = "";
var cardId = "";
var machineId = "";
$(document).ready(function() {
	var ifm = document.getElementById("ifm");
	$("body").height(document.documentElement.clientHeight);
	setTimeout(function(){
		loginDH();
    }, 500);
});
function getDsspocxObject(){
	return document.getElementById("dsspocxObject");
}
function loginDH(){
		var customizeInfo = JSON.stringify({SupportDvr:0, ProductName:'P700', webPort:"80"});
		document.getElementById("dsspocxObject").SetExtendProperty(customizeInfo);
		/* cmsIp：大华平台访问ip; * webPort：大华平台访问端口； * loginName：用户登录账号； * loginPass：用户登录账号（明文） */
		//登录大华平台需要的账户密码应该是当前登陆人，但是现在大华库里的数据没有权限，暂时使用huan：123456登录
		document.getElementById("dsspocxObject").Login("<%=ServiceConstant.DHSERVERIP%>", "<%=ServiceConstant.DHSERVERPORT%>", "huan", "123456", "1");
}

function jumpOn(){
	timerJump = window.setInterval(checkAndJump,1000);
}

function jumpOff(){
	window.clearInterval(timerJump);
}

function checkAndJump(){
	//手环fed00451 机器fffe009e
	var str = getDsspocxObject().GetDevAndBrandIDForThird();
	var obj = JSON.parse(str);
	if(cardId == obj.data["BrandID"] && machineId == obj.data["DevID"]){
		return;
	}else{
		cardId = obj.data["BrandID"];
		machineId = obj.data["DevID"];
	}
	if(!$.util.isBlank(cardId) && !$.util.isBlank(machineId)){
		$.ajax({
			url:context +'/handlingAreaReceipt/findUrl.action',
			type:'post',
			data:{
				cardId : cardId,
				machineId : machineId
			},
			dataType:'json',
			success:function(successData){
				if(!$.util.isBlank(successData.url)){
					var win = $('#ifmWindow')[0].contentWindow;  
					win.location.href = $.util.fmtUrl(context + successData.url);
				}
			}
		});
	}
}
</script>
</html>