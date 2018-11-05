<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理-涉案物品再入库</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center" class="validform">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>新增涉案物品再入库</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>新增涉案物品再入库</h1>
						<div class="m-ui-caozuobox">
							<button id="print" class="btn btn-success" style="display: none">打印</button>
							<button id="save" class="btn btn-primary">保存</button>
							<button id="cancel" class="btn btn-default">取消</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="alert" style="width: 900px; padding-top: 20px; margin-top: 20px; position: relative; background-color: #f8f8f8; border-color: #ddd;">
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">再入库单编号：</label>
						</div>
						<div class="col-xs-2">
							<input id="bsfId" type="hidden" class="form-control input-sm" >
							<input id="code" type="text" class="form-control input-sm" disabled>
						</div>
						<div class="col-xs-2">
							<label class="control-label">再入库日期：<span class="red-star">*</span></label>
						</div>
						<div id="bsfLaydate" class="col-xs-2 input-group laydate">
							<input type="hidden" class="laydate-fmt dateFmt" value="info@yyyy-MM-dd HH:mm:ss" />
							<input type="text" class="laydate-value form-control input-sm valiform-keyup form-val" readonly="readonly" datatype="*1-85" nullmsg="请填写调整时间">
							<span class="laydate-value-span input-group-addon m-ui-addon">
								<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
							</span>
						</div>
						<div class="col-xs-2">
							<label class="control-label">出库类型：</label>
						</div>
						<div class="col-xs-2">
							<input id="typeName" type="text" class="form-control input-sm" disabled>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应案件编号：</label>
						</div>
						<div class="col-xs-6">
							<input id="caseCode" type="text" class="form-control input-sm" disabled>
						</div>
						<div class="col-xs-3">
							<button id="selectCaseCode" class="btn btn-primary btn-sm">查询案件编号</button>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">出库单编号：<span
								class="red-star">*</span></label>
						</div>
						<div class="col-xs-6 input-group">
							<select id="storageOutCode" class="select2 allowClear" disabled>
								
							</select>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">案件名称：</label>
						</div>
						<div class="col-xs-6">
							<input id="caseName" type="text" class="form-control input-sm" disabled>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">办案民警：</label>
						</div>
						<div class="col-xs-6">
							<input id="polices" type="text" class="form-control input-sm" disabled>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">备注：</label>
						</div>
						<div class="col-xs-6">
							<textarea id="remark" class="form-control input-sm valiform-keyup form-val" rows="3" datatype="*0-85" errormsg="备注不可超过85个字符"></textarea>
						</div>
					</div>

					<div id="bsfCodeImg" style="position: absolute; right: 10px; bottom: 10px; width: 170px;">
						
					</div>
				</div>
				<div class="m-ui-table">
					<table id="bsfTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
						
					</table>
				</div>
				<!--结束-->
			</div>
		</div>
	</div>
	<div id="menuContent" class="ztree-MenuContent validform">
		<ul id="lockerUl" style="width:200px; height: 150px;">
			
		</ul>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/backStorageForm/newBackStorageForm.js"></script>
</html>