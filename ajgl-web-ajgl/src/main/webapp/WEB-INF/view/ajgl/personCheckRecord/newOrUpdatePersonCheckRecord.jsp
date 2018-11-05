<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformPersonCheckRecord" class="validform">
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
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="m-ui-layer-box">
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include
							file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						<!-----人身检查记录----->
						<div class="m-ui-title3 mar-top"><h2>维护人身检查记录</h2></div>
						<table class="table table-sg" cellspacing="0" width="100%">
							<tbody>
								<tr>
									<td class="td-left" width="120">自述症状：（既往病史、是否饮酒、是否患有传染性等疾病）<span class="red-star">*</span></td>
									<td colspan="3"><textarea id="selfReportedSymptoms" class="form-control input-sm  valiform-keyup form-val"
											rows="5" style="width: 80%;" datatype="*1-65" errormsg="不可超过65个字符"></textarea></td>
								</tr>
								<tr>
									<td class="td-left">检查情况：（体表是否有伤痕、是否饮酒以及全身检查情况）<span class="red-star">*</span></td>
									<td colspan="3"><textarea id="checkCondition" class="form-control input-sm  valiform-keyup form-val"
											rows="5" style="width: 80%;" datatype="*1-65" errormsg="不可超过65个字符"></textarea></td>
								</tr>
								<tr>
									<td class="td-left">附件：</td>
									<td id="newAttach" colspan="3">
										<div id="pcrUpload" class="upload-control"></div>
										<div id="showAttach"></div>
									</td>
								</tr>
								<tr>
									<td class="td-left" style="width: 130px">被检查人/监护人：<span class="red-star">*</span></td>
									<td width="30%"><input id="isCheckedPerson" class="form-control input-sm valiform-keyup form-val"
										style="width: 150px" datatype="*1-20" errormsg="不可超过20个字符"></td>
									<td class="td-left" width="120">检查民警：<span class="red-star">*</span></td>
									<td><input id="checkPolice" class="form-control input-sm valiform-keyup form-val"
										style="width: 150px" datatype="*1-20" errormsg="不可超过20个字符"></td>
								</tr>
								<tr>
									<td class="td-left">见证人：<span class="red-star">*</span></td>
									<td colspan="3"><input id="eyewitness" class="form-control input-sm valiform-keyup form-val"
										style="width: 150px" datatype="*1-20" errormsg="不可超过20个字符"></td>
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
								<table id="nrcrOperateRecordTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
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
						<div class="m-ui-commitbox">
							<button id="pcrConfirm" class="btn btn-primary btn-lg" style="width:100px;">保存</button>
							<button id="pcrCancel" class="btn btn-bordered btn-lg pcrBack">取消</button>
							<button id="pcrPrint" class="btn btn-bordered btn-lg" style="display:none;">打印</button>
							<button id="pcrBack" class="btn btn-bordered btn-lg pcrBack" style="display:none;">返回</button>
						</div>   
						<!-----离开办案区情况end----->
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/personCheckRecord/newOrUpdatePersonCheckRecord.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>