<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformHandlingAreaReceipt" class="validform m-ui-layer-body">
<div class="m-ui-layer-box" style="width: 500px;">
	<div class="m-ui-layer-content">
<table class="table table-sg" cellspacing="0" width="100%">
 <tbody>
           <tr>
              <td class="td-left" width="150">姓名：</td>
              <td id="policeName"></td>
           </tr>
           <tr>
              <td class="td-left">IC卡号：</td>
              <td id="icNum">PT0001</td>
          </tr>
          <tr>
              <td class="td-left">警号（或辅警编号）：</td>
              <td id="policeNum">110107</td>
          </tr>
          <tr>
              <td class="td-left">是否主办民警：</td>
              <td><input id="ifMainPolice" type="checkbox" class="icheckbox" disabled></td>
          </tr>
          <tr>
              <td class="td-left">备注：</td>
              <td id="remark"></td>
          </tr>
  </tbody>
</table>
<div class="row-mar mar-top color-gray font12">
发卡核对人：<span id="sendCardPeopleName"></span>   <span style="margin-left:30px;">发卡核对时间：<span id="sendCardTime"></span></span>
</div> 
</div>
</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaReceipt/showPoliceDetail.js"></script>
</html>