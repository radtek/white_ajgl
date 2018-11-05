<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>涉案物品台账</li>
				</ol>
				<!--整体查询板块--begin-->
				<div class="basic-query-out">
					<div class="basic-query">
						<input id="speedinessSearchText" type="text" class="form-control input-sm" value="物品名称及特征模糊查询"
							onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
							onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
							style="color: #aaa;width:170px">
						<button class="btn btn-primary btn-sm search">查询</button>
						<button id="unfoldCondition" class="advanced-btn">展开高级查询</button>
					</div>
				</div>
				<div class="m-ui-searchbox  advanced-query">
					<div class="m-ui-searchbox-con">

						<div class="row row-mar">
							<div class="col-xs-4">
								<div class="col-xs-6">
									<label class="control-label">物品名称及特征：</label>
								</div>
								<div class="col-xs-6">
									<input id="nameOrFeature" type="text" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-4">
								<div class="col-xs-6">
									<label class="control-label">所在涉案物品保管区：</label>
								</div>
								<div class="col-xs-6">
									<select id="storageArea" class="form-control input-sm select2-noSearch allowClear">
										
									</select>
								</div>
							</div>
							<div class="col-xs-4">
								<div class="col-xs-6">
									<label class="control-label">所在储物箱：</label>
								</div>
								<div class="col-xs-6">
									<!-- <select id="articleLocker" class="form-control input-sm select2-multiple allowClear" multiple="multiple">
										
									</select> -->
									<input id="locker" arrId="" type="text" class="form-control input-sm" readonly="readonly">
								</div>
							</div>
						</div>

						<div class="row row-mar">
							<div class="col-xs-4">
								<div class="col-xs-6">
									<label class="control-label">对应案件编号：</label>
								</div>
								<div class="col-xs-6">
									<input id="code" type="text" class="form-control input-sm">
								</div>
							</div>
							<div id="sbDateRange" class="col-xs-8 dateRange">
								<input type="hidden" id="dtfmt" class="dateFmt" value="info@yyyy-MM-dd HH:mm" />
								<div class="col-xs-3">
									<label class="control-label">入库时间：</label>
								</div>
								<div class="col-xs-3 input-group">
									<div class="input-group">
										<input type="text" id="fixed_start" class="laydate-start form-control input-sm" readonly="readonly">
										<span class="laydate-start-span input-group-addon m-ui-addon">
											<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
										</span>
									</div>
								</div>
								<div class="col-xs-3 to">——</div>
								<div class="col-xs-3 input-group">
									<input type="text" id="fixed_end" class="laydate-end form-control input-sm" readonly="readonly">
									<span class="laydate-end-span input-group-addon m-ui-addon">
										<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
									</span>
								</div>
							</div>
						</div>
	
						<div class="row row-mar">
							
							<div class="col-xs-4">
								<div class="col-xs-8">
									<label class="control-label">对应犯罪嫌疑人姓名/身份证号：</label>
								</div>
								<div class="col-xs-4">
									<input id="nameOrCardId" type="text" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-4">
								<div class="col-xs-6">
									<label class="control-label">办案民警(姓名或警号)：</label>
								</div>
								<div class="col-xs-6">
									<input type="text" id="disposePerson" class="form-control input-sm">
								</div>
							</div>
							
							<div class="col-xs-3 text-right">
								是否显示在库数量为0的涉案物品&nbsp;&nbsp;<input id="goodsNum" type="checkbox" class="icheckbox" name="goodsNum">
							</div>
							<div class="col-xs-1 text-center">
								<button class="btn btn-primary btn-sm search">查询</button>
								<button id="reset" class="btn btn-default btn-sm">重置</button>
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
						<h1>涉案物品台账</h1>
						<div class="m-ui-caozuobox">
							<button id="excel" class="btn btn-primary">导出EXCEL</button>
							<button class="btn btn-primary" id="refBtn">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button id="adjustment" class="btn btn-primary">调整存储位置</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="sbTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
							
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
	src="<%=context%>/scripts/sawpgl/standingBook/standingBook.js"></script>
</html>