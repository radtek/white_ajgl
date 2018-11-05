<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="d" uri="/WEB-INF/tag/securityresource.tld"%>
<%@ page import="com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil,com.taiji.pubsec.springsecurity.resource.util.MySecureUser,
java.util.List,java.util.Map,java.util.HashMap"%> 

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

<script type="text/javascript" src="<%=request.getContextPath()%>/custom/ajgl/js/top.js"></script>

<script>
var currentUserName = '<%=mPerson.get("name")%>';
var currentUserCode = '<%=mPerson.get("code")%>';
var currentUnitName = '<%=mOrg.get("shortName")%>';
var currentUnitCode = '<%=mOrg.get("code")%>';
var personCode = '<%=mPerson.get("code")%>';
var loginName = '<%=mPerson.get("userName")%>';
</script>


<div class="c-top-content">
<div class="logo"><h1><a href="<%=request.getContextPath()%>/homePage/showHomePageJsp.action" >贵阳市经开分局全流程闭环式执法监督系统</a></h1>
</div>
<div class="c-top-right">
<ul>
<li><a href="###" class="exit">退出</a></li>
</ul>
</div>
</div>	

<script>

$(document).ready(function(){
	$(document).on("click", ".exit", function(){
		$.layerAlert.confirm({
			msg : "确认退出吗？",
			yes : function(index,layero) {
				window.top.location.href = context + "/logout" ;
			}
		});	
	});
});

</script>
