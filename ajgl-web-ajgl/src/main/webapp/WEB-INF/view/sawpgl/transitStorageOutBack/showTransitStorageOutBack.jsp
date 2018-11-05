<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 暂存物品出库返还</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformTransitStorageOutBack" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<input type="hidden" id="outCode" value=<%=request.getParameter("code")%>>
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>暂存物品管理</li>
					<li>暂存物品出库返还</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>暂存物品出库返还单详情</h1>
						<div class="m-ui-caozuobox">
                            <button id="cancel" class="btn btn-default">返回</button>
							<button class="btn btn-default" id="refresh">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button class="btn btn-primary" id="print" disabled="disabled">打印</button>
							<button class="btn btn-primary" id="affirmStorageOut" disabled="disabled">确认出库</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="alert"
					style="width: 900px; padding-top: 20px; margin-top: 20px; position: relative; background-color: #f8f8f8; border-color: #ddd;">
					
					<div class="row row-mar">
						<div class="col-xs-3">
							<label class="control-label">办案区使用单：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="harCode"></span></div>
						<div class="col-xs-2">
							<label class="control-label">办案民警：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="polices"></span></div>
					</div>
					
					
					<div class="row row-mar">
						<div class="col-xs-3">
							<label class="control-label">出库返还单编号：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="code"></span></div>
						<div class="col-xs-2">
							<label class="control-label">出库时间：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="ckTime"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-3">
							<label class="control-label">对应案件编号：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="caseCode"></span></div>
						<div class="col-xs-2">
							<label class="control-label">案件名称：</label>
						</div>
						<div class="col-xs-5 m-label-right"><span id="caseName"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-3">
							<label class="control-label">对应嫌疑人/物品持有人：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="suspectName"></span></div>
						<div class="col-xs-2">
							<label class="control-label">嫌疑人身份证号：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="suspectId"></span></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-3">
							<label class="control-label">附件：</label>
						</div>
						<div class="col-xs-9 m-label-right">
							<ul class="m-list-group" id="papers">
								
							</ul>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-3">
							<label class="control-label">备注：</label>
						</div>
						<div class="col-xs-6 m-label-right"><span id="remark" style="word-wrap: break-word; word-break: break-all"></span></div>
					</div>
					<div id="qrDiv"
						style="position: absolute; right: 10px; bottom: 10px; width: 170px;">
					</div>
				</div>
				<div class="m-ui-title3">
					<h2>
						暂存物品清单
					</h2>
				</div>
				<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">所在物证保管区：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id=inStorageArea></span></div>
						<div class="col-xs-2">
							<label class="control-label">储物箱：</label>
						</div>
						<div class="col-xs-2 m-label-right"><span id="storageRack"></span></div>
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
	src="<%=context%>/scripts/sawpgl/transitStorageOutBack/showTransitStorageOutBack.js"></script>
</html>