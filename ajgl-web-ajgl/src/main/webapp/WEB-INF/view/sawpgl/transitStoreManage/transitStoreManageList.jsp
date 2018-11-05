<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>涉案物品管理 – 暂存物品管理</title>
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
					<li>暂存物品存储情况查询</li>
				</ol>
				
					<div class="m-ui-searchbox-con">
					<div class="row row-mar">
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">保管区：</label>
							</div>
							<div class="col-xs-6">
								<select id="storageArea" class="select2-noSearch allowClear form-control input-sm"></select>
							</div>
							
						</div>
						<div class="col-xs-3">
							<div class="col-xs-6">
								<label class="control-label">储物箱：</label>
							</div>
							<div class="col-xs-6">
								<select id="storageLocker" class="form-control input-sm select2-multiple allowClear" multiple="multiple">
								</select>
							</div>
						</div>
						<div class="col-xs-3">
						    <div class="col-xs-6"> <label class="control-label">对应案件编号/名称：</label></div>
						    <div class="col-xs-6"><input type="text" id="caseNameOrCode" class="form-control input-sm"></div>
						</div>
						<div class="col-xs-3">
						    <div class="col-xs-6"> <label class="control-label">办案民警：</label></div>
						    <div class="col-xs-6"><input type="text" id="policeNameOrNum" class="form-control input-sm"></div>
						</div>
						
					</div>
					<div class="row row-mar">
					<div class="col-xs-6">
						    <div class="col-xs-6"> <label class="control-label">对应犯罪嫌疑人姓名/身份证号：</label></div>
						    <div class="col-xs-6"><input type="text" id="suspectNameOrIdCard" class="form-control input-sm"></div>
						</div>
					
					<div class="col-xs-3" >
							<div class="col-xs-6">
								<label class="control-label">是否空闲：</label>
							</div>
							<div class="col-xs-6">
								<select id="isFree" class="form-control input-sm select2-noSearch">
									<option value="全部">全部</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</div>
						</div>
						<div class="col-xs-1">
						</div>
						<div class="col-xs-2">
							<button class="btn btn-primary btn-sm search">查询</button>
							<button class="btn btn-default btn-sm reset">重置</button>
						</div>
					</div>
				</div>
			<!--查询结束-->
			
			<!--悬浮操作层-->
			<div class="fixed-a">
				<div class="m-ui-title1"><h1>暂存物品存储情况查询</h1>
					<div class="m-ui-caozuobox">
						<button class="btn btn-primary" id="excel">导出EXCEL</button>
						<button class="btn btn-primary" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
					</div>
				</div>
			</div>
			<!--悬浮操作层-->
			<div id="c-center-right-content-block">
				<div class="m-ui-table" >
			        <table id="transitStoreManageTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" style="width:100%">
			            
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
	src="<%=context%>/scripts/sawpgl/transitStoreManage/transitStoreManageList.js"></script>
</html>