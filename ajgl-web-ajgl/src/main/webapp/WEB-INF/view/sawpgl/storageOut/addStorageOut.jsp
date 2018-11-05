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
			<div id="c-center-right-content">

				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>涉案物品出库</li>
				</ol>

				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>新增涉案物品出库单</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-primary" id="saveBtn">保存</button>
							<button class="btn btn-default" id="cancel">取消</button>
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
						<div class="col-xs-2">
							<input type="text" class="form-control input-sm" id="code"
								value="" readonly unselectable="on">
						</div>
						<div class="col-xs-2">
							<label class="control-label">出库时间：<span class="red-star">*</span></label>
						</div>
						<div class="col-xs-2 input-group">
							<div id="ckTime" class="input-group laydate" style="width: 150px">
								<input type="hidden" class="laydate-fmt dateFmt"
									value="yyyy-MM-dd HH:mm" /> <input id="gatherTime_input"
									class="form-control laydate-value valiform-keyup form-val" vf-position="1" datatype="*" readonly="readonly">
								<span class="laydate-value-span input-group-addon m-ui-addon">
									<span class="glyphicon glyphicon-calendar" aria-hidden="true"
									style="font-size: 16px;"></span>
								</span>
							</div>
						</div>
						<div class="col-xs-2">
							<label class="control-label">出库类型：<span class="red-star">*</span></label>
						</div>
						<div class="col-xs-2">
							<select id="type" class="form-control input-sm select2-noSearch valiform-keyup form-val" vf-position="1" datatype="*">
							</select>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应案件编号：<span
								class="red-star">*</span></label>
						</div>
						<div class="col-xs-5">
							<input type="text" class="form-control input-sm" value="" id="caseCode" unselectable="on" readonly>
						</div>
						<div class="col-xs-3">
							<button id="caseCodeConfirm" class="btn btn-primary btn-sm" style="display:none;">确定</button>
							<button id="caseCodeUpdate" class="btn btn-primary btn-sm" style="display:none;">修改</button>
							<button id="selectCaseCode" class="btn btn-primary btn-sm">查询案件编号</button>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">案件名称：</label>
						</div>
						<div class="col-xs-6">
							<input type="text" class="form-control input-sm" value=""
								id="caseName" readonly unselectable="on">
						</div>
					</div>

					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">办案民警：</label>
						</div>
						<div class="col-xs-6">
							<input type="text" class="form-control input-sm" value=""
								id="polices" readonly unselectable="on">
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应出库文书：<span class="red-star" id="containerStar">*</span></label>
						</div>
						<div class="col-xs-8 upload-control" id="container"></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">备注：</label>
						</div>
						<div class="col-xs-6">
							<textarea class="form-control input-sm valiform-keyup form-val" rows="3" id="remark" vf-position="1" datatype="*0-80"></textarea>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">物品领取人：<span class="red-star">*</span></label>
						</div>
						<div class="col-xs-2">
							<input type="text" class="form-control input-sm valiform-keyup form-val" id="receiver"
								value="" vf-position="1" datatype="*1-20">
						</div>
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
				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/storageOut/addStorageOut.js"></script>
</html>