<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 案件监控</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
<script type="text/javascript" src="<%=context%>/common/library/zTree/custom/zTreeMenu.js"></script>
</head>
<body id="validformId" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajjk.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>案件监控管理</li>
					<li>涉案嫌疑人查询</li>
				</ol>
				<!--整体查询板块--begin-->
				<div class="basic-query-out">
					<div class="basic-query">
						<input id="simpleName" type="text" class="form-control input-sm" value="姓名模糊查询"
							onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
							onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
							style="color: #aaa;">
						<button class="btn btn-primary btn-sm search">查询</button>
						<button class="advanced-btn">展开高级查询</button>
					</div>
				</div>
					<div class="m-ui-searchbox  advanced-query">
						<div class="m-ui-searchbox-con">
							<div class="row row-mar">
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">姓名：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="name" class="form-control input-sm">
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">性别：</label>
									</div>
									<div class="col-xs-6">
										<select class="form-control select2-noSearch allowClear" id="sex"></select>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">身份证号：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="idNum" class="form-control input-sm">
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">现居住地：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="address" class="form-control input-sm">
									</div>
								</div>
							</div>
							<div class="row row-mar">
								<div id="birthday" class="col-xs-6 dateRange">
									<input type="hidden" id="dtfmt" class="dateFmt"
										value="yyyy-MM-dd" />
									<div class="col-xs-3">
										<label class="control-label">出生年月：</label>
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
									<div class="col-xs-3 to">――</div>
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
								<!-- <div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">籍贯地址：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="nativeAddress" class="form-control input-sm">
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">口音：</label>
									</div>
									<div id="accentDiv" class="col-xs-6 input-group">
										<input id="accent" type="text" class="form-control input-sm" readonly="readonly"> <span
											class="input-group-addon"><span
											class="glyphicon glyphicon-search"></span></span>
										<input id="accentId" type="hidden">
									</div>
								</div> -->
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">案件编号：</label>
								</div>
								<div class="col-xs-6">
									<input type="text" id="caseCode" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">案件名称：</label>
								</div>
								<div class="col-xs-6">
									<input type="text" id="caseName" class="form-control input-sm">
								</div>
							</div>
							</div>
							<div class="row row-mar">
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">相关案件：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" class="form-control input-sm" id="aboutCase">
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
								<div class="col-xs-5"></div>
								<div class="col-xs-1">
									<button class="btn btn-primary btn-sm search">查询</button>
									<button class="btn btn-default btn-sm reset">重置</button>
								</div>
							</div>
						</div>
					</div>
				<!--查询结束-->
				<div class="advanced-btn-box">
					<button class="advanced-btn-up">收起高级查询</button>
				</div>
				<!--整体查询板块--end-->
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>涉案嫌疑人查询</h1>
					</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="suspectTable"
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
	src="<%=context%>/scripts/ajjk/suspectSearch/suspectSearch.js"></script>
</html>