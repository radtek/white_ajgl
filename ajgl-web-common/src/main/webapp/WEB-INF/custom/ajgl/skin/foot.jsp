<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.taiji.pubsec.common.tool.comet.constant.Constants,
com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil,
com.taiji.pubsec.common.tool.spring.SpringContextUtil
"%>
<%
String accName = (String)(SessionUserDetailsUtil.getMySecureUser().getUserMap()).get("userName");
%>
<%@include file="/common/library/comet/comet.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/library/msghint/js/msghint.js"></script>
<script>
var currentAccNameForPush = '<%=accName%>';
var COMET_OORT_URL = '<%=Constants.getOORT_URL()%>' ;
var COMET_OORT_CLOUD = '<%=Constants.getOORT_CLOUD()%>' ;
var COMET_OORT_LOCAL_URL = '<%=Constants.getOORT_LOCAL_URL()%>' ;
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/custom/ajgl/js/foot.js"></script>
