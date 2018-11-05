<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<%@ page import="com.taiji.pubsec.ajqlc.util.ServiceConstant"%>
<html>
<head>
<title>案件管理 – 案件监控</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<script type="text/javascript"
	src="<%=context%>/common/library/zTree/custom/zTreeMenu.js">
	</script>
	<script >
	var startDate = '<%=ServiceConstant.STRAT_DATE%>';
	</script>
<%@include file="/WEB-INF/view/util/constant.jsp" %>
</head>
<body id="validformId" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajjk.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">

				<ol class="breadcrumb m-ui-breadcrumb">
					<li>案件监控管理</li>
					<li>破/结案情况月报表</li>
				</ol>
				<!--整体查询板块--begin-->
				<div class="m-ui-searchbox-con">
				<div class="row row-mar">
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">案件编号或名称：</label>
							</div>
							<div class="col-xs-6">
								<input id="caseCodeOrName" type="text"
									class="form-control input-sm">
							</div>
						</div>
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">案件类别：</label>
							</div>
							<div id="caseSortDiv" class="col-xs-6 input-group">
								<input id="caseSort" type="text" class="form-control input-sm" readonly="readonly">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-search"></span></span>
								<input id="caseSortId" type="hidden">
							</div>
						</div>
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">办案单位：</label>
							</div>
							<div id="sponsorDiv" class="col-xs-6 input-group">
								<input id="sponsor" type="text" class="form-control input-sm" readonly="readonly">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-search"></span></span>
								<input id="sponsorId" type="hidden">
							</div>
						</div>
						
					</div>
					<div class="row row-mar">
					<div class="col-xs-3">
						<div class="col-xs-6">
							<label class="control-label">查询月份：</label>
						</div>
						<div class="col-xs-6">
							<select class="form-control select2" id="caseTimeSelect"></select>
						</div>
					</div>
						<div id="caseTime" class="col-xs-6 dateRange" style="display: none" >
							<input type="hidden" id="dtfmt" class="dateFmt"
								value="yyyy-MM-dd HH:mm" />
							<div class="col-xs-3">
								<label class="control-label">发案时间：</label>
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
								<label class="control-label">办案民警(姓名或警号)：</label>
							</div>
							<div class="col-xs-6">
								<input type="text" id="disposePerson" class="form-control input-sm">
							</div>
						</div>
						
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">是否破/结案：</label>
							</div>
							<div class="col-xs-6">
								<select id="ifSolved" class="form-control input-sm select2-noSearch">
									<option value="全部">全部</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</div>
						</div>
						
						<div class="col-xs-2"> </div>
						<div class="col-xs-1">
							<button class="btn btn-primary btn-sm search">查询</button>
							<button class="btn btn-default btn-sm reset">重置</button>
						</div>
					</div>

					
				</div>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>破/结案情况月报表</h1>
						<div class="m-ui-caozuobox">
							<button id="exportExcel" class="btn btn-primary">导出EXCEL</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->

				<!-----------------tabs--------------------->
				<div id="tabs" class="m-ui-tabs2">
					<ul class="nav nav-tabs">
						<li><a id="xingshi" href="#tabs-1">刑事案件</a></li>
						<li><a id="xingzheng" href="#tabs-2">行政案件</a></li>
					</ul>
					<div id="tabs-1" class="tabxon">
						<div>
							<table id="pCaseTable"
								class="table table-bordered table-hover m-ui-table-whole"
								cellspacing="0" width="100%">
							</table>
						</div>
						<div class="alert alert-info" style="font-size: 20px; margin-top: 10px;" id="xsDiv" >
							<div class="row">
								<p class="col-xs-4" style="text-align: right;">受案数量：</p>
								<p class="col-xs-2" style="width: 120px; text-align: left;" id="xingshilian">
								</p>
								<p class="col-xs-4" style="text-align: right;">其中未结案数量：</p>
								<p class="col-xs-2" style="width: 120px; text-align: left;" id="xingshipoan">
								</p>
							</div>
						</div>
					</div>
					<!--刑事案件-->
					<div id="tabs-2" class="tabxon">
						<div>
							<table id="aCaseTable"
								class="table table-bordered table-hover m-ui-table-whole"
								cellspacing="0" width="100%">
							</table>
						</div>
						<div class="alert alert-info" style="font-size: 20px; margin-top: 10px;" id="xzDiv">
							<div class="row">
								<p class="col-xs-4" style="text-align: right;">受案数量：</p>
								<p class="col-xs-2" style="width: 120px;text-align: left;" id="xingzhenglian">
								</p>
								<p class="col-xs-4" style="text-align: right;">其中未结案数量：</p>
								<p class="col-xs-2" style="width: 120px; text-align: left;" id="xingzhengpoan">
								</p>
							</div>
						</div>
					</div>
					<!----行政案件----->
				</div>
				<!-----------------tabs--------------------->
				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajjk/unsolvedCaseList/unsolvedCaseList.js"></script>
</html>
