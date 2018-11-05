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
	<div class="m-ui-layer-content" style="margin-left: 35px;">
<div class="row">
<div class="col-xs-12">
<div style="padding-right:220px;">
 <table class="table table-sg" cellspacing="0" width="100%">
	 <tbody>
		<tr>
			<td class="td-left" width="50%">使用单编号：</td>
			<td colspan="3"><span id="formCode"></span></td>
		</tr>
		<tr>
			<td class="td-left" width="15%">被问询人：</td>
			<td colspan="3"><span id="askPerson"></span></td>
		</tr>
		<tr>
			<td class="td-left" width="15%">性别：</td>
			<td colspan="3"><span id="sex"></span></td>
		</tr>
		<tr>
			<td class="td-left" width="15%">身份证件证号：</td>
			<td colspan="3"><span id="askPersonID"></span></td>
		</tr>

	</tbody>
    </table> 
<div class="m-ui-title3 mar-top"><h2 class="m-bold">刻录信息</h2></div>  
<div class="m-ui-table m-ui-table-sm">
	<table id="criminalRecordTable" class="table m-ui-table-no-paginate table-bordered " cellspacing="0">
    </table>
</div>    
 </div> 
 </div>
<div class="col-xs-2" style="width:200px; margin-left:-200px;">
<div class="alert-default font14 text-center">
<div style="padding:10px 0; width:150px;overflow:hidden; margin:0 auto;"><img id="cameraPicture" class="zoomIn" src="../images/man/man-none.png" height="180"><p style="padding-top:5px;"> 
<span class="mar-right-sm"></span>人像采集照片</p></div>
 </div> 
  </div>  
 </div>

</div>
</body>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/yykl/recordingDetail.js"></script>
</html>