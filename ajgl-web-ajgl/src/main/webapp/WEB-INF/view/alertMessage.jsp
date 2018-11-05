<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 - 预警消息</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
</head>
<body id="validform" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-xx.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">

			<div class="m-ui-searchbox ">
					<div class="m-ui-searchbox-con">
						<div class="row row-mar">
							<div id="messageDate" class="col-xs-6 dateRange">
								<input type="hidden" id="dtfmt" class="dateFmt"
											value="info@yyyy-MM-dd HH:mm:ss" />
						      	<div class="col-xs-3"> <label class="control-label">提醒时间：<span class="red-star">*</span></label></div>
						      	<div class="col-xs-3 input-group">
								<input type="text" id="time_start" date-pos="bottom"
										class="laydate-start form-control input-sm valiform-keyup form-val" datatype="*1-50" nullmsg="请选择开始时间"
										readonly="readonly"> <span
										class="laydate-start-span input-group-addon m-ui-addon">
										<span class="glyphicon glyphicon-calendar"
										aria-hidden="true"></span>
									</span>
								  </div>
							      <div class="col-xs-1 to">—</div>
							      <div class="col-xs-3 input-group">
									 <input type="text" id="time_end"
											class="laydate-end form-control input-sm valiform-keyup form-val" datatype="*1-50" nullmsg="请选择开始时间"
											readonly="readonly"> <span
											class="laydate-end-span input-group-addon m-ui-addon">
											<span class="glyphicon glyphicon-calendar"
											aria-hidden="true"></span>
										</span>
								 </div>
							</div>
							<div class="col-xs-3 m-ui-btn-box">
								<button class="btn btn-primary btn-sm" id="query">查询</button>
								<button id="reset" class="btn btn-default btn-sm">重置</button>
							</div>
						</div>
						</div>
						</div>

				<div class="fixed-a">
						<div class="m-ui-title1">
							<h1>预警消息</h1>
						</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="alertMessageTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
							
						</table>
					</div>
				</div>
			
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/alertMessage.js"></script>
</html>
