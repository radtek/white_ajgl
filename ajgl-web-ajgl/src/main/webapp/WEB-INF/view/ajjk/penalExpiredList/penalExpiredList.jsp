<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 案件监控</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<script type="text/javascript"
	src="<%=context%>/common/library/zTree/custom/zTreeMenu.js"></script>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformId" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajjk.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">

				<ol class="breadcrumb m-ui-breadcrumb">
					<li>案件监控管理</li>
					<li>刑事案件到期提醒表</li>
				</ol>
				<!--整体查询板块--begin-->
				<div class="m-ui-searchbox-con">
				<div class="row row-mar">
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">案件编号：</label>
							</div>
							<div class="col-xs-6">
								<input id="caseCode" type="text" class="form-control input-sm">
							</div>
						</div>
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">案件名称：</label>
							</div>
							<div class="col-xs-6">
								<input id="caseName" type="text" class="form-control input-sm">
							</div>
						</div>
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">办案单位：</label>
							</div>
							<div id="sponsorDiv" class="col-xs-6 input-group">
								<input id="sponsor" type="text" class="form-control input-sm"  readonly="readonly"> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-search"></span></span>
									<input id="sponsorId" type="hidden">
							</div>
						</div>
						<!-- <div class="col-xs-3" style="display:none;">
							<div class="col-xs-6">
								<label class="control-label">备注：</label>
							</div>
							<div class="col-xs-6">
								<input id="remark" type="text" class="form-control input-sm">
							</div>
						</div> -->
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">办案民警(姓名或警号)：</label>
							</div>
							<div class="col-xs-6">
								<input type="text" id="disposePerson" class="form-control input-sm">
							</div>
						</div>
						
					</div>
					<div class="row row-mar">
						<div id="caseTime" class="col-xs-6 dateRange">
							<input type="hidden" id="dtfmt" class="dateFmt"
								value="yyyy-MM-dd HH:mm" />
							<div class="col-xs-3">
								<label class="control-label">进入办案区时间：</label>
							</div>
							<div class="col-xs-3 input-group">
								<input type="text" id="fixed_start" date-pos="bottom"
									class="laydate-start form-control input-sm" readonly="readonly">
								<span class="laydate-start-span input-group-addon m-ui-addon">
									<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
								</span>
							</div>
							<div class="col-xs-3 to">――</div>
							<div class="col-xs-3 input-group">
								<input type="text" id="fixed_end"
									class="laydate-end form-control input-sm" readonly="readonly">
								<span class="laydate-end-span input-group-addon m-ui-addon">
									<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
								</span>
							</div>
						</div>

						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">案件是否归档：</label>
							</div>
							<div class="col-xs-6">
								<select id="attention" class="form-control input-sm select2-noSearch">
									<option value="否">否</option>
									<option value="是">是</option>
									<option value="全部">全部</option>
								</select>
							</div>
						</div>
						<div class="col-xs-2"></div>
						<div class="col-xs-1">
							<button class="btn btn-primary btn-sm search">查询</button>
							<button class="btn btn-default btn-sm reset">重置</button>
						</div>
					</div>

					
				</div>
				<!--查询结束-->

				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>刑事案件到期提醒表</h1>
						<div class="m-ui-caozuobox">
							<button id="modify" class="btn btn-primary btnShow">编辑</button>
							<!-- 
							<button id="exportExcel" class="btn btn-primary btnShow">导出EXCEL</button>
							 -->
							<button id="save" class="btn btn-danger btnChange" style="display:none;">确认</button>
							<button id="cancel" class="btn btn-default btnChange" style="display:none;">取消</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->

				<div style="width: 100%; overflow: auto;">
					<div class="m-ui-table" style="width: 2000px">
						<table id="caseTable"
							class="table table-bordered table-hover m-ui-table-whole"
							cellspacing="0" width="100%">
						</table>
					</div>
				</div>
				<!--结束-->


			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajjk/penalExpiredList/penalExpiredList.js"></script>
</html>
