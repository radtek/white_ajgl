<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="d" uri="/WEB-INF/tag/securityresource.tld"%>
<%@ page import="com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil,
com.taiji.pubsec.springsecurity.resource.util.MySecureUser,
java.util.List,java.util.Map,java.util.HashMap,
com.taiji.pubsec.common.tool.spring.SpringContextUtil,
com.taiji.pubsec.ajqlc.util.ServiceConstant
"%> 

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
<div id="c-top" >
<!-- 换肤  -->
<div class="c-top-content">
<div class="logo"><h1><a href="###" >贵阳市经开分局全流程闭环式执法监督系统</a></h1>
</div>


<div class="c-top-right">
<ul>
<!--<li><a href="###"><span  class="link-index-icon"></span><i>首页</i></a></li>-->
  <li><a class="ajgl" href="###"><span class="link-icon aj-icon-1"></span><i>智能办案区管理</i></a></li>
  <li><a class="ajjk" href="###"><span class="link-icon aj-icon-2"></span><i>案件监控管理</i></a></li>
  <li><a class="sawpgl" href="###"><span class="link-icon aj-icon-3"></span><i>涉案物品管理</i></a></li>
  <li><a class="xxtx" href="###"><span class="link-message-icon"><span class=""></span></span><i>我的消息</i></a></li> 
  <li><div class="nav-ceng-out"><a href="###" class=""><span class="link-menu-icon"></span><i>系统导航</i></a>
         <div class="nav-ceng nav-systems" style="display: none;">
          <ul class="nav">
          <li><a class="ajgl" href="###"><span class="icon aj-icon-1"></span>智能办案区管理</a></li>
          <li><a class="ajjk" href="###"><span class="icon aj-icon-2"></span>案件监控管理</a></li>
          <li><a class="sawpgl" href="###"><span class="icon aj-icon-3"></span>涉案物品管理</a></li>
          <li><a class="xtgl" href="###"><span class="icon aj-icon-4"></span>系统管理</a></li>
          </ul>
</div>
</div></li>
<li><a href="###" class="exit">退出</a></li>
</ul>
</div>

</div>
<!-- 换肤  -->
</div>
<script>
$(document).on("click", ".exit", function(){
	$.layerAlert.confirm({
		msg : "确认退出吗？",
		yes : function(index,layero) {
			window.top.location.href = context + "/logout" ;
		}
	});	
});
$(document).on("click", ".ajgl", function(){
	window.location.href = context + "/handlingAreaReceipt/showHandlingAreaReceiptListPage.action?clickOrder=1&amp;clickListOrder=0" ;
});
$(document).on("click", ".sawpgl", function(){
	window.location.href = context + "/storageArea/showStorageAreaListPage.action?clickOrder=0&&clickListOrder=0" ;
});	
$(document).on("click", ".xtgl", function(){
	window.location.href = context + "/dictionaryType/toDictionaryType.action?clickOrder=0&&clickListOrder=0" ;
});	
$(document).on("click", ".ajjk", function(){
	window.location.href = context + "/caseSearch/showCaseSearchPage.action?clickOrder=1&&clickListOrder=0" ;
});	
$(document).on("click", ".xxtx", function(){
	window.open(context + "/alertMessage/showMessageSearchPage.action?clickOrder=0&&clickListOrder=0");
});	
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/custom/ajgl/js/top.js"></script>

