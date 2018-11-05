<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 暂存物品出库返还</title>
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
					<li>暂存物品管理</li>
					<li>暂存物品出库返还</li>
				</ol>
				<!--整体查询板块--begin-->
				<div class="basic-query-out">
					<div class="basic-query">
						<input id="simpleCode" type="text" class="form-control input-sm" value="出库返还单编号模糊查询"
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
										<label class="control-label">出库返还单编号：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="code" class="form-control input-sm">
									</div>
								</div>
								<div id="ckDate" class="col-xs-6 dateRange">
								<input type="hidden" id="dtfmt" class="dateFmt"
											value="yyyy-MM-dd HH:mm" />
						      	<div class="col-xs-3"> <label class="control-label">出库返还日期：</label></div>
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
										<label class="control-label">对应案件编号：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="caseCode" class="form-control input-sm">
									</div>
								</div>
							</div>

							<div class="row row-mar">
								<div class="col-xs-3">
									<div class="col-xs-8">
										<label class="control-label">对应犯罪嫌疑人/物品持有人：</label>
									</div>
									<div class="col-xs-4">
										<input type="text" id="suspectName" class="form-control input-sm">
									</div>
								</div>
							<!-- 	<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">对应出库文书：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="paper" class="form-control input-sm">
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">备注：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" id="remark" class="form-control input-sm">
									</div>
								</div> -->
								<div class="col-xs-4 text-right">
										<div class="col-xs-6">
											<label class="control-label">显示我的出库返还单：</label>
										</div>
										<div class="col-xs-2">
										<input type="radio" id="checkedYes" class="redioBtn icheckradio" name="sf" value="">是
										</div>
										<div class="col-xs-2">
										<input type="radio" id="checkedNo" class="redioBtn icheckradio" name="sf" value="">否
									   </div>
								</div>
								<div class="col-xs-4">
								</div>
								<div class="col-xs-1">
									<button class="btn btn-primary btn-sm search">查询</button>
									<button class="btn btn-default btn-sm" id="reset">重置</button>
								</div>
							</div>
							<!-- <div class="row row-mar">
								<div class="col-xs-3">
									<div class="col-xs-8">
										<label class="control-label">借出或其他出库物品是否再入库：</label>
									</div>
									<div class="col-xs-4">
										<select id="restitution" class="select2-noSearch allowClear form-control input-sm"></select>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">出库类型：</label>
									</div>
									<div class="col-xs-6">
										<select id="type" class="select2-noSearch allowClear form-control input-sm"></select>
									</div>
								</div>
								<div class="col-xs-5">
								</div>
								
							</div> -->
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
						<h1>涉案物品出库</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-primary addOut">新增</button>
							<button class="btn btn-primary excel">导出EXCEL</button>
							<button class="btn btn-primary reloadBtn">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="transitStorageOutBackRecordTable"
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
	src="<%=context%>/scripts/sawpgl/transitStorageOutBack/transitStorageOutBackRecord.js"></script>
</html>