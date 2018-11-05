<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformHandlingAreaReceipt" class="validform m-ui-layer-body">
<div class="m-ui-layer-box" style="width: 98%;">
	<div class="m-ui-layer-content">
		<div class="m-ui-searchbox">
			<div class="m-ui-searchbox-con">
				<div class="row row-mar">
					<div class="col-xs-3">
						<div class="col-xs-6">
							<label class="control-label">案件编号：</label>
						</div>
						<div class="col-xs-6">
							<input type="text" class="form-control input-sm" id="caseCode">
						</div>
					</div>
					<div class="col-xs-3">
						<div class="col-xs-6">
							<label class="control-label">案件名称：</label>
						</div>
						<div class="col-xs-6">
							<input type="text" class="form-control input-sm" id="caseName">
						</div>
					</div>
					<div class="col-xs-3">
						<div class="col-xs-6">
							<label class="control-label">办案民警：</label>
						</div>
						<div class="col-xs-6">
							<input type="text" class="form-control input-sm" id="disposePerson">
						</div>
					</div>
					<div class="col-xs-3 m-ui-btn-box">
						<button class="btn btn-primary btn-sm search">查询</button>
						<button class="btn btn-default btn-sm reset">重置</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row" >
			<div class="col-xs-12">
				<div style="padding-right: 20px;">
					<table id="caseListTable" width="100%" height="100%"
						class="table table-bordered table-hover m-ui-table-whole"
						cellspacing="0" width="100%">
					</table>
				</div>
			</div>
			<div style="display: none;">
				<div style="min-height: 100px;">
					<div style="margin-right: 1px;">
						<table
							class="table table-bordered table-hover m-ui-table-no-paginate"
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<th colspan="3" style="height: 24px;">其他</th>
								</tr>
							</thead>
							<tbody id="otherPersonOne">
								<tr>
									<td>
										案件名称：<br/>
										<input type="text" style="width: 155px" maxlength="80" class="form-control input-sm" id="otherCaseName">
										<button style="display: inline;" class="btn btn-primary btn-sm save">确定</button>
									</td>
								</tr>
							</tbody>
							
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/agt/util/showAGTCaseList.js"></script>
</html>