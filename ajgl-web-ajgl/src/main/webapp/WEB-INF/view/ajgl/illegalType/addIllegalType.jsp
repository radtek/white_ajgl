<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformLocker" class="validform m-ui-layer-body">
	<div class="m-ui-layer-box">
		<div class="m-ui-layer-content">

			<div class="row row-mar">
				<div class="col-xs-3">
					<label class="control-label">编号：<span class="red-star">*</span></label>
				</div>
				<div class="col-xs-6">
					<input id="code" type="text" class="form-control input-sm valiform-keyup form-val" datatype="*1-30" nullmsg="请填写编号"  errormsg="不可超过30个字符"/>
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-3">
					<label class="control-label">违规类型名称：<span class="red-star">*</span></label>
				</div>
				<div class="col-xs-6">
					<input id="name" type="text" class="form-control input-sm valiform-keyup form-val" datatype="*1-50" nullmsg="请填写违规类型名称"  errormsg="不可超过50个字符"/>
				</div>
			</div>
			
			<div class="row row-mar">
				<div class="col-xs-3">
					<label class="control-label">状态：</label>
				</div>
				<div class="col-xs-6">
					<select id="status" class="form-control input-sm select2-noSearch "></select>
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-3">
					<label class="control-label">备注：</label>
				</div>
				<div class="col-xs-6">
					<textarea id="description" class="form-control input-sm valiform-keyup form-val" rows="3" datatype="*0-250" errormsg="不可超过250个字符"></textarea>
				</div>
			</div>
		</div>
		<!--内容end-->
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/illegalType/addIllegalType.js"></script>
</html>