<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformId" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajgl.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>办案区管理</li>
					<li>办案区使用</li>
					<li>民警卡管理</li>
				</ol>
				<!--整体查询板块--begin-->
					<div class="m-ui-searchbox">
						<div class="m-ui-searchbox-con">
							<div class="row row-mar">
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">卡号：</label>
									</div>
									<div class="col-xs-4">
										<input type="text" class="form-control input-sm" id="icNum">
									</div>
									<div class="col-xs-2">
										<button class="btn btn-primary btn-sm read">读取</button>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">姓名：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" class="form-control input-sm" id="policeName">
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">警号：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" class="form-control input-sm" id="policeNum">
									</div>
								</div>
								<div class="col-xs-3 m-ui-btn-box">
									<button class="btn btn-primary btn-sm search">查询</button>
									<button class="btn btn-default btn-sm reset">重置</button>
								</div>
							</div>
						</div>
					</div>
				<!--查询结束-->
				<!--整体查询板块--end-->
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>民警卡管理</h1>
					</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="policeTable"
							class="table table-bordered table-hover m-ui-table-whole"
							cellspacing="0">
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
	src="<%=context%>/scripts/ajgl/handlingAreaReceipt/policeWhoHasCardListPage.js"></script>
</html>