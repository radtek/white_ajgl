<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
<style type="text/css">
.noprint {
	visibility: hidden
}
</style>
</head>
<body class="m-ui-layer-body">

	<div class="m-ui-layer-box">

		<div class="m-ui-layer-content">

				<div class="row row-mar">
	            <div class="col-xs-3"> <label class="control-label">办案区使用单编号：</label></div>
	            <div class="col-xs-2 m-label-right"><input id="MakingCode" type="text"/></div>
	            <div class="col-xs-3"> <label class="control-label">案件编号：</label></div>
	            <div class="col-xs-3 m-label-right"><input id="caseCode" type="text"/></div>
	        </div>
	
	        <div class="row row-mar">
	            <div class="col-xs-3"> <label class="control-label">嫌疑人姓名：</label></div>
	            <div class="col-xs-2 m-label-right"><input id="suspectName" type="text"/></div>
	            <div class="col-xs-3"> <label class="control-label">嫌疑人证件号：</label></div>
	            <div class="col-xs-3 m-label-right"><input id="suspectNum" type="text"/></div>
	        </div>
	        <div class="row row-mar">
	            <div class="col-xs-3"> <label class="control-label">嫌疑人进入办案区时间：</label></div>
	            <div id="makingDate" class="dateRange form-group col-xs-9" style="height: 30px;">
						<input type="hidden" id="dtfmt" class="dateFmt" value="info@yyyy-MM-dd HH:mm" />
						<div class="col-xs-4">
							<div class="input-group" style="margin-right: 10px;">
								<input type="text" id="fixed_start"
									class="laydate-start form-control input-sm" readonly="readonly">
								<span class="laydate-start-span input-group-addon m-ui-addon">
									<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
								</span>
							</div>
						</div>
						<div class="col-xs-4">
							<span style="float: left; margin-top: 4px; margin-left: -2px;">--</span>
							<div class="input-group" style="padding-left: 5px;">
								<input type="text" id="fixed_end"
									class="laydate-end form-control input-sm" readonly="readonly">
								<span class="laydate-end-span input-group-addon m-ui-addon">
									<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
								</span>
							</div>
						</div>
					</div>
        </div>
        <div class="row row-mar" style="text-align:right;">
	        <div class="col-xs-6">
				<button id="search" class="btn btn-primary btn-sm">查询</button>
				<button id="reset" class="btn btn-default btn-sm">重置</button>
			</div>
        </div>
			</div>
			<div class="m-ui-commitbox">
				<div class="m-ui-table">
					<table id="makingTable"
						class="table table-bordered table-hover m-ui-table-whole"
						cellspacing="0" style="width: 100%">

					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="menuContent" class="ztree-MenuContent">
		<input type="text" id="keySelect"
			style="margin-bottom: 5px; display: none;"
			class="form-control input-sm" />
		<ul id="ztree-unitSelect" class="ztree"
			style="width: 200px; height: 150px;"></ul>
	</div>

</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/selectMakingCodeAlert/selectMakingCodeAlert.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>