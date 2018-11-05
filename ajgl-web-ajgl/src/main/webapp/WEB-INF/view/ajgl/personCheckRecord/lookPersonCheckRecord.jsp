<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajgl.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
				  	<li>办案区管理</li>
					<li>办案区使用</li>
					<li>办案区使用单</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
				<div class="m-ui-title1">
					<h1>办案区使用单详情</h1>
						<div class="m-ui-caozuobox" style="display: none"> 
							<button class="btn btn-success finishReceipt finishBtnFlag" style="display: none;" style="display: none;">完成本次问讯</button>
							<button class="btn btn-primary printBtnFlag" style="display: none;" id="printAll">打印使用单</button>
							<button class="btn btn-primary printBtnFlag" style="display: none;" id="unbound">解绑手环</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="m-ui-layer-box">
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						<!-----人身检查记录----->
						<div class="row" style="padding: 10px; text-align: right;">
							<button id="updatePersonCheckRecord" class="btn btn-info modifyBtnFlag" style="display: none;">
								<span class="glyphicon glyphicon-edit micon-lg mar-right"></span>维护人身检查记录
							</button>
						</div>
						<table class="table table-sg" cellspacing="0" width="100%">
							<tbody>
								<tr>
									<td class="td-left" width="120">自述症状：（既往病史、是否饮酒、是否患有传染性等疾病）：</td>
									<td id="selfReportedSymptoms" colspan="3"></td>
								</tr>
								<tr>
									<td class="td-left" width="120">检查情况：（体表是否有伤痕、是否饮酒以及全身检查情况）：</td>
									<td id="checkCondition" colspan="3"></td>
								</tr>
								<tr>
									<td class="td-left">附件：</td>
									<td id="pcrAttach" colspan="3"></td>
								</tr>
								<tr>
									<td class="td-left" width="120">被检查人/监护人：</td>
									<td id="isCheckedPerson" width="30%"></td>
									<td class="td-left" width="120">检查民警：</td>
									<td id="checkPolice"></td>
								</tr>
								<tr>
									<td class="td-left">见证人：</td>
									<td id="eyewitness" colspan="3"></td>
								</tr>
							</tbody>
						</table>
						<div class="color-gray text-right">
							最新修改人：<span id="modifyPeopleName"></span>
							<span style="margin-left: 50px;">最新修改时间：<span id="updateTime"></span></span>
						</div>


						<!--操作历史-->
						<!--<div class="m-ui-title3 mar-top">
							<h2>操作历史</h2>
						</div>

						<div class="od-expand-btn" id="od-expand-btn-2"
							onClick="document.getElementById('od-expand-content-2').style.display='block';document.getElementById('od-expand-btn-2').style.display='none'">
							<a href="###"><span>显示操作历史</span></a>
						</div>
						<div id="od-expand-content-2" style="display: none;">
							<div class="m-ui-table m-ui-table-sm">
								<table id="lrcrOperateRecordTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>序号</th>
											<th>操作内容</th>
											<th>操作人</th>
											<th>操作时间</th>
											<th>说明</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
							<div class="od-expand-btn od-fold-btn"
								onClick="document.getElementById('od-expand-content-2').style.display='none';document.getElementById('od-expand-btn-2').style.display='block'">
								<a href="###"><span>隐藏操作历史</span></a>
							</div>
						</div>-->
						<!--操作历史-->
						<!-----人身检查记录end----->
				</div>
			</div>
		</div>
	</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/personCheckRecord/lookPersonCheckRecord.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>