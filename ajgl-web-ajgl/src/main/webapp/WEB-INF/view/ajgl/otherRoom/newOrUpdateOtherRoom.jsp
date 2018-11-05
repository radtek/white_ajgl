<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformAskRoom" class="validform m-ui-layer-body">
	<div class="m-ui-layer-box">
		<div class="m-ui-layer-content">
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">编号：<span class="red-star">*</span></label>
				</div>
				<div class="col-xs-6">
					<input id="code" type="text" class="form-control input-sm valiform-keyup form-val" datatype="*1-25" nullmsg="请填写编号"  errormsg="不可超过25个字符">
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">其他房间名称：<span
						class="red-star">*</span></label>
				</div>
				<div class="col-xs-6">
					<input id="name" type="text" class="form-control input-sm valiform-keyup form-val" datatype="*1-25" nullmsg="请填写名称" errormsg="不可超过25个字符">
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">所属单位：<span class="red-star">*</span></label>
				</div>
				<div class="col-xs-6 input-group">
					<input id="unit" type="hidden" />
					<input id="unitName" type="text" readonly class="form-control input-sm selectTreeUnit valiform-keyup form-val" datatype="*1-25" nullmsg="请选择单位">
					<span class="input-group-addon selectTreeUnit">
						<span class="glyphicon glyphicon-search"></span>
					</span>
				</div>
			</div>
			<div class="row row-mar" style="display:none;">
				<div class="col-xs-5">
					<label class="control-label">状态：</label>
				</div>
				<div class="col-xs-6">
					<select id="status" class="form-control input-sm select2-noSearch"></select>
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">备注：</label>
				</div>
				<div class="col-xs-6">
					<textarea id="note" class="form-control input-sm valiform-keyup form-val" rows="3" datatype="*0-65" errormsg="不可超过65个字符"></textarea>
				</div>
			</div>
		</div>
		<!--内容end-->
	</div>
	<div id="menuContent" class="ztree-MenuContent">
		<input type="text" id="keySelect" style="margin-bottom:5px;display:none;" class="form-control input-sm" />
		<ul id="ztree-unitSelect" class="ztree" style="width:200px; height: 150px;"></ul>
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/otherRoom/newOrUpdateOtherRoom.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>