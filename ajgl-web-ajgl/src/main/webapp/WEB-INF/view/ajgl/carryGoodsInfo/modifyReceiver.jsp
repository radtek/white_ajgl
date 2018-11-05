<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<script>
	$(document).ready(function() {

	});
</script>
</head>

<body class="m-ui-layer-body validform" id="validformId">
<br/>
		<div class="m-ui-layer-searchbox-sm">
			<div class="row row-mar2">
				<div class="col-xs-4">
					<label class="control-label">领取人：</label>
				</div>
				<div class="col-xs-6">
					<input id="getPeople"
						class="form-control input-sm valiform-keyup form-val disabled"
						msgElId="getPeople" vf-position="2" datatype="*1-80"
						style="width: 150px"><span class='red-star'>*</span>
				</div>
			</div>
			<br/>
			<div class="row row-mar2">
				<div class="col-xs-4">
					<label class="control-label">身份证号码：</label>
				</div>
				<div class="col-xs-6">
					<input id="idNum"
						class="form-control input-sm valiform-keyup form-val disabled"
						msgElId="idNum" vf-position="2" datatype="/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/"
						style="width: 150px" value=""><span class='red-star'>*</span>
				</div>
			</div>
		</div>
	<script type="text/javascript"
		src="<%=context%>/scripts/ajgl/carryGoodsInfo/modifyReceiver.js"></script>
</body>
</html>