<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 涉案物品入库</title>
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
					<li><a href="#">首页</a></li>
					<li><a href="#">涉案物品管理</a></li>
					<li class="active">新增涉案物品入库单</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>涉案物品入库</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-default" id="refresh">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button id="print" class="btn btn-success">打印</button>
							<button id="cancel" class="btn btn-default">返回</button>
							<input type="hidden" id="formId" value="${inStorageFormId}" />
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="alert"
					style="width: 900px; padding-top: 20px; margin-top: 20px; position: relative; background-color: #f8f8f8; border-color: #ddd;">
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">入库单编号：</label>
						</div>
						<div class="col-xs-2">
							<div id="code" class="col-xs-2 m-label-right">${inStorageFormBean.code}</div>
						</div>
						<div class="col-xs-2">
							<label class="control-label">入库时间：</label>
						</div>
						<div class="col-xs-2">
							<div class="m-label-right">${inStorageFormBean.createdTimeStr}</div>
						</div>
					</div>

					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应案件编号：</label>
						</div>
						<div class="col-xs-2">
							<div id="caseCode" class="m-label-right">${inStorageFormBean.caseCode}</div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">案件名称：</label>
						</div>
						<div class="col-xs-4">
							<div class="m-label-right">${inStorageFormBean.caseName}</div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">办案民警：</label>
						</div>
						<div class="col-xs-2">
							<div class="m-label-right">${inStorageFormBean.caseHandler}</div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-5" style="width: 370px;">
							<label class="control-label">本次入库涉案物品对应的嫌疑人/物品持有人：</label>
						</div>
						<div class="col-xs-5">
							<div class="row m-icheck-group" style="margin-bottom: 10px;">
								<input id="suspectId" type="hidden"
									value="${inStorageFormBean.suspectId}">
								<p class="col-xs-4">${inStorageFormBean.suspectName}</p>
							</div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应文书：</label>
						</div>
						<div class="col-xs-10">
							<ul class="m-list-group" id="paperUl">
							</ul>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">备注：</label>
						</div>
						<div class="col-xs-6">
							<div class="m-label-right" style="word-wrap: break-word; word-break: break-all">${inStorageFormBean.remark}</div>
						</div>
					</div>

					<div id="qrcode"
						style="position: absolute; right: 10px; bottom: 10px; width: 170px;">
					</div>
				</div>
				<div class="m-ui-title3">
					<h2>
						涉案物品
						<button class="btn btn-primary btn-sm" style="display: none;">查看物品图片</button>
					</h2>
				</div>
				<div class="m-ui-table">
					<table id="sawqTable"
						class="table table-bordered table-hover m-ui-table-whole"
						cellspacing="0" style="width: 100%">

					</table>
				</div>

				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/storageIn/viewStorageIn.js"></script>
</html>