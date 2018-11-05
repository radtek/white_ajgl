<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 涉案物品入库</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformStorageOut" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<input type="hidden" id="outId" value=<%=request.getParameter("id")%>>
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>涉案物品出库</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>涉案物品出库单详情</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-default" id="refresh">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button class="btn btn-primary" id="print">打印</button>
                            <button id="cancel" class="btn btn-default">返回</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="alert"
					style="width: 900px; padding-top: 20px; margin-top: 20px; position: relative; background-color: #f8f8f8; border-color: #ddd;">
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">出库单编号：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="code"></span></div>
						<div class="col-xs-2">
							<label class="control-label">出库时间：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="ckTime"></span></div>
						<div class="col-xs-2">
							<label class="control-label">出库类型：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="type"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应案件编号：</label>
						</div>
						<div class="col-xs-6 m-label-right"><span id="caseCode"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">案件名称：</label>
						</div>
						<div class="col-xs-6 m-label-right"><span id="caseName"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">办案民警：</label>
						</div>
						<div class="col-xs-6 m-label-right"><span id="polices"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应文书：</label>
						</div>
						<div class="col-xs-10 m-label-right">
							<ul class="m-list-group" id="papers">
								
							</ul>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">备注：</label>
						</div>
						<div class="col-xs-6 m-label-right"><span id="remark" style="word-wrap: break-word; word-break: break-all"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">是否再入库：</label>
						</div>
						<div class="col-xs-6 m-label-right"><span id="isReturned"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">物品领取人：</label>
						</div>
						<div class="col-xs-6 m-label-right"><span id="receiver"></span></div>
					</div>
					<div id="qrDiv"
						style="position: absolute; right: 10px; bottom: 10px; width: 170px;">
					</div>
				</div>
				<div class="m-ui-title3">
					<h2>
						涉案物品
					</h2>
				</div>
				<div class="m-ui-table">
					<table id="sawpTable"
						class="table table-bordered table-hover m-ui-table-whole"
						cellspacing="0" width="100%">
					</table>
				</div>
				<div class="color-gray text-right">
						最新修改人：<span id="modifyPeople"></span> <span style="margin-left: 50px;">最新修改时间：<span id="modifyTime"></span></span>
				</div>
				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/storageOut/showStorageOut.js"></script>
</html>