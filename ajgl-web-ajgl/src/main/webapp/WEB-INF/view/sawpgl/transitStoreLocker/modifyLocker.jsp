<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validform" class="validform m-ui-layer-body">
	<div class="m-ui-layer-content">

		<div class="row row-mar">
			<div class="col-xs-5">
				<label class="control-label">编号：</label>
			</div>
			<div class="col-xs-6">
				<span id="code"></span>
			</div>
		</div>
		<div class="row row-mar">
			<div class="col-xs-5">
				<label class="control-label">储物箱位置：</label>
			</div>
			<div class="col-xs-6">
				<input type="text" id="location"
					class="form-control input-sm valiform-keyup form-val"
					datatype="*1-80" vf-position="3" nullmsg="请填写储物箱位置">
			</div>
			<span class="red-star">*</span>
		</div>
		<div class="row row-mar">
			<div class="col-xs-5">
				<label class="control-label">所属物证保管区：</label>
			</div>
			<div class="col-xs-6" id="errorId">
				<!-- <select id="storageArea"
					class="form-control input-sm select2-noSearch valiform-keyup form-val"
					msgElId="errorId" vf-position="3" nullmsg="请选择物证保管区"
					vf-position="2" datatype="*1-80"></select> -->
					<span  id="storageArea" algin="left"></span>
			</div>
			<!-- <span class="red-star">*</span> -->
		</div>

		<div class="row row-mar">
			<div class="col-xs-5">
				<label class="control-label">备注：</label>
			</div>
			<div class="col-xs-6">
				<textarea id="remake"
					class="form-control input-sm valiform-keyup form-val" rows="3"
					datatype="*0-80" vf-position="3" ></textarea>
			</div>
		</div>
		<div class="row row-mar">
			<div class="col-xs-5">
				<label class="control-label">本储物箱二维码：</label>
			</div>
			<div class="col-xs-6">
			<div class="text-center" style="padding: 15px;">
				
				<div id="tdcDiv"
					style="position: absolute; background-color: white; width: 200px; height: 200px; top: 70px; left: 50%; margin-top: -50px; margin-left: -100px;">
					<div id="tdcImg"
						style="position: absolute; top: 100%; left: 50%; margin-top: -150px; margin-left: -150px;"></div>
				</div>
			</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/transitStoreLocker/modifyLocker.js"></script>
</html>