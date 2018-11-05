<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="saValidform" class="validform m-ui-layer-body">
	<div class="m-ui-layer-box">
		<div class="m-ui-layer-content">
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">编号：<span class="red-star">*</span></label>
				</div>
				<div class="col-xs-6">
					<input id="code" type="text" class="form-control input-sm valiform-keyup form-val"  maxlength="10" datatype="n1-20" nullmsg="请填写编号" errormsg="编号只能为数字">
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">暂存物品保管区名称：<span class="red-star">*</span></label>
				</div>
				<div class="col-xs-6">
					<input id="name" type="text" class="form-control input-sm valiform-keyup form-val" datatype="*1-75" nullmsg="请填写名称" errormsg="不可超过75个字符">
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">所属单位：<span class="red-star">*</span></label>
				</div>
				<div class="col-xs-6 input-group">
					<input id="unit" type="hidden" >
					<input id="unitName" type="text" readonly class="form-control input-sm selectTreeUnit valiform-keyup form-val" datatype="*1-75" nullmsg="请选择单位">
					<span class="input-group-addon selectTreeUnit">
						<span class="glyphicon glyphicon-search"></span>
					</span>
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">暂存物品保管区详细地址：<span
						class="red-star">*</span></label>
				</div>
				<div class="col-xs-6">
					<input id="address" type="text" class="form-control input-sm valiform-keyup form-val" datatype="*1-75" nullmsg="请填写详细地址" errormsg="不可超过75个字符">
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">状态：</label>
				</div>
				<div class="col-xs-6">
					<select id="state" class="form-control input-sm select2-noSearch">
						
					</select>
				</div>
			</div>
			<div class="row row-mar">
				<div class="col-xs-5">
					<label class="control-label">备注：</label>
				</div>
				<div class="col-xs-6">
					<textarea id="remark" class="form-control input-sm valiform-keyup form-val" rows="3" datatype="*0-75" errormsg="不可超过75个字符"></textarea>
				</div>
			</div>
		</div>
	</div>
	<div id="menuContent" class="ztree-MenuContent">
		<input type="text" id="keySelect" style="margin-bottom:5px;display:none;" class="form-control input-sm" />
		<ul id="ztree-unitSelect" class="ztree" style="width:200px; height: 150px;"></ul>
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/transitStoreArea/newOrUpdateTransitStoreArea.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>