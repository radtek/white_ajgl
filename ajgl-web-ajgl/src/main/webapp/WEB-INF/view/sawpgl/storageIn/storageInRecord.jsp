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
					<li>涉案物品管理</li>
					<li>涉案物品入库</li>
				</ol>

				<!--整体查询板块--begin-->
				<div class="basic-query-out">
					<div class="basic-query">
						<input type="text" class="form-control input-sm simpleCode" value="入库单编号模糊查询"
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
							    <div class="col-xs-6"> <label class="control-label">入库单编号：</label></div>
							    <div class="col-xs-6"><input type="text" id="code" class="form-control input-sm"></div>
							</div>
							<div id="rkDate" class="col-xs-6 dateRange">
								<input type="hidden" id="dtfmt" class="dateFmt"
											value="info@yyyy-MM-dd HH:mm" />
						      	<div class="col-xs-3"> <label class="control-label">入库时间：</label></div>
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
						   <div class="col-xs-6"> <label class="control-label">对应案件编号：</label></div>
						    <div class="col-xs-6"><input type="text" id="caseCode" class="form-control input-sm"></div>
						</div>
					</div>

					<div class="row row-mar">
						<div class="col-xs-3">
						    <div class="col-xs-8"> <label class="control-label">对应犯罪嫌疑人/物品持有人：</label></div>
						    <div class="col-xs-4"><input type="text" id="suspectName" class="form-control input-sm"></div>
						</div>
						<div class="col-xs-3">
						    <div class="col-xs-6"> <label class="control-label">对应文书：</label></div>
						    <div class="col-xs-6"><input type="text" id="papers" class="form-control input-sm"></div>
						</div>
						<div class="col-xs-3">
						    <div class="col-xs-6"> <label class="control-label">备注：</label></div>
						    <div class="col-xs-6"><input type="text" id="remark" class="form-control input-sm"></div>
						</div>
						<div style="display:none;" class="col-xs-3 text-right">只显示我的入库单&nbsp;&nbsp;<input name="sf" type="checkbox" checked class="icheckbox sxcheckbox">
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-11">
						</div>
						<div class="col-xs-1">
							<button class="btn btn-primary btn-sm search">查询</button>
							<button class="btn btn-default btn-sm reset">重置</button>
						</div>
					</div>
				</div>
			</div>
			<!--查询结束-->
			
			<div class="advanced-btn-box"><button class="advanced-btn-up">收起高级查询</button></div>
			<!--整体查询板块--end-->

			<!--悬浮操作层-->
			<div class="fixed-a">
				<div class="m-ui-title1"><h1>涉案物品入库</h1>
					<div class="m-ui-caozuobox">
						<button class="btn btn-primary" id="add">新增</button>
						<button class="btn btn-danger" id="delete">删除</button>
						<button class="btn btn-primary" id="excel">导出EXCEL</button>
						<button class="btn btn-primary" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
					</div>
				</div>
			</div>
			<!--悬浮操作层-->
			<div id="c-center-right-content-block">
				<div class="m-ui-table" >
			        <table id="storageInTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" style="width:100%">
			            
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
	src="<%=context%>/scripts/sawpgl/storageIn/storageInRecord.js"></script>
</html>