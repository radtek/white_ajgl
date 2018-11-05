
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>

<style>

body{
   margin:0px;
   padding:0px;
   font-size:14px;
   width:100%;
   height:100%;
   font-family:'Microsoft YaHei','Tahoma','Arial','Times New Roman','LiHei Pro Medium','SimSun',SimHei; 
 }

</style>


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

var dhipForOcx = "<%=ServiceConstant.DHSERVERIP%>";
var dhportForOcx = "<%=ServiceConstant.DHSERVERPORT%>";
var usernameForOcx = "<%=userMap.get("userName")%>";
var passwordForOcx = "<%=userMap.get("password")%>";
</script>

<body style="font-size:0" height="100%" width="100%">
<iframe id="ifmWindow" height="100%" width="100%" src="" marginheight="0" marginwidth="0" topmargin="0" leftmargin="0" noresize style=" border:0px;padding:0px">
</iframe>

<object id="dsspocxObject" classid="CLSID:1D1A0ACA-3A53-4589-88B8-94A0DDC47EA6" onreadystatechange="" style="display: none;"></object>

<img id="imgId" />
<script type="text/javascript"
	src="<%=context%>/scripts/frameworkForOcx.js"></script>
</body>
</html>