<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>案件管理 – 系统管理</title>

<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@ page import="com.taiji.pubsec.ajqlc.xtgl.Constant"%>
<%
	String constantResourceType = Constant.RESOURCE_TYPE;
%>
<script>
var constantResourceType = '<%=constantResourceType%>
	';
</script>
<script>
	$(document).ready(function() {

	});
</script>
</head>

<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-xtgl.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<div id="c-center-right-content-block">
					<div id="c-center-right-content-con" class="alt">
						<ol class="breadcrumb m-ui-breadcrumb">
							<li>系统管理</li>
							<li>操作日志查询</li>
						</ol>
						<!--查询开始-->
						<div class="m-ui-searchbox">
							<div class="m-ui-searchbox-con">
								<div class="row row-mar">
									<div class="col-xs-3">
										<div class="col-xs-6">
											<label class="control-label">用户名：</label>
										</div>
										<div class="col-xs-6">
											<input type="text" id="userName" class="form-control input-sm">
										</div>
									</div>
									<div id="rkDate" class="col-xs-6 dateRange">
										<input type="hidden" id="dtfmt" class="dateFmt"
											value="info@yyyy-MM-dd HH:mm:ss" />
										<div class="col-xs-3">
											<label class="control-label">记录时间：</label>
										</div>
										<div class="col-xs-3 input-group">
											<input type="text" id="fixed_start" date-pos="bottom"
												class="laydate-start form-control input-sm"
												readonly="readonly"> <span
												class="laydate-start-span input-group-addon m-ui-addon">
												<span class="glyphicon glyphicon-calendar"
												aria-hidden="true"></span>
											</span>
										</div>
										<div class="col-xs-3 to">——</div>
										<div class="col-xs-3 input-group">
											<input type="text" id="fixed_end"
												class="laydate-end form-control input-sm"
												readonly="readonly"> <span
												class="laydate-end-span input-group-addon m-ui-addon">
												<span class="glyphicon glyphicon-calendar"
												aria-hidden="true"></span>
											</span>
										</div>
									</div>
									<div class="col-xs-3">
										<div class="col-xs-6">
											<label class="control-label">功能菜单名称：</label>
										</div>
										<div class="col-xs-6">
											<input type="text" id="menuContent"
												class="form-control input-sm">
										</div>
									</div>
								</div>

								<div class="row row-mar">
									<div class="col-xs-3">
										<div class="col-xs-6">
											<label class="control-label">客户端IP：</label>
										</div>
										<div class="col-xs-6">
											<input type="text" id="clientIp" class="form-control input-sm">
										</div>
									</div>
									<div class="col-xs-8"></div>
									<div class="col-xs-1">
										<button class="btn btn-primary btn-sm search">查询</button>
										<button class="btn btn-default btn-sm reset">重置</button>
									</div>
									
								</div>
							</div>
						</div>
						<!--查询结束-->

						<div class="m-ui-table" style="margin: auto 10px auto 10px">
							<table id="logTable"
								class="table table-bordered table-hover m-ui-table-no-paginate"
								cellspacing="0" width="100%">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/xtgl/log/log.js"></script>

</html>