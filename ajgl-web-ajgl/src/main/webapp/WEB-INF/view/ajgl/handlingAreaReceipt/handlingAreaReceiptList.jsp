<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformId" class="validform">
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
				<!--整体查询板块--begin-->
				<div class="basic-query-out">
					<div class="basic-query">
						<input type="text" class="form-control input-sm simpleCode" value="使用单编号模糊查询"
							onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
							onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
							style="color: #aaa;">
						<button class="btn btn-primary btn-sm search">查询</button>
						<button class="advanced-btn">展开高级查询</button>
					</div>
				</div>
					<div class="m-ui-searchbox  advanced-query">
						<div class="m-ui-searchbox-con">
							
							<input type="hidden" id="fullSearchInSession"
								value=<%=request.getAttribute("fullSearch")%>>
							
							<div class="row row-mar">
								<div id="accessDate" class="col-xs-6 dateRange">
									<input type="hidden" id="dtfmt" class="dateFmt"
										value="yyyy-MM-dd" />
									<div class="col-xs-3">
										<label class="control-label">进入办案区日期：</label>
									</div>
									<div class="col-xs-3 input-group">
										<input type="text" id="fixed_start" date-pos="bottom"
												class="laydate-start form-control input-sm"
												readonly="readonly"> <span
												class="laydate-start-span input-group-addon m-ui-addon">
												<span class="glyphicon glyphicon-calendar"
												aria-hidden="true"></span>
											</span>
									</div>
									<div class="col-xs-3 to">――</div>
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
								<div class="col-xs-6">
									<div class="col-xs-3">
										<label class="control-label">所属案件：</label>
									</div>
									<td>
									<div class="input-group"  class="col-xs-3">
						              <input type="text" class="form-control input-sm selectAboutCaseLst" id="aboutCaseLst" readonly>
						              <span class="input-group-addon selectAboutCaseLst" ><span class="icon-search"></span></span>
						              </div><input type="hidden" id="lawCaseName">
					              </td> 
								</div>
							</div>
							<div class="row row-mar">
											<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">使用单编号：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" class="form-control input-sm" id="code">
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">使用房间：</label>
									</div>
									<div class="col-xs-6">
										<select class="form-control select2-noSearch allowClear" id="roomLst"></select>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">办案民警：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" class="form-control input-sm" id="police">
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">身份证号码：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" class="form-control input-sm" id="idNum">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">使用单状态：</label>
									</div>
									<div class="col-xs-6">
										<select class="form-control select2-noSearch allowClear" id="stateLst"></select>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">案由：</label>
									</div>
									<div class="col-xs-6">
										<select class="form-control select2-noSearch allowClear" id="causeOfLawCaseLst"></select>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">被讯（询）问人：</label>
									</div>
									<div class="col-xs-6">
										<input type="text" class="form-control input-sm" id="askPerson">
									</div>
								</div>
								<!-- 
								<div class="col-xs-3">
									<div class="col-xs-6">
										<label class="control-label">是否上传问询笔录：</label>
									</div>
									<div class="col-xs-6">
										<select class="form-control select2-noSearch allowClear" id="recordLst"></select>
									</div>
								</div>
								 -->
								<div class="col-xs-3 m-ui-btn-box">
									<button class="btn btn-primary btn-sm search">查询</button>
									<button class="btn btn-default btn-sm reset">重置</button>
								</div>
							</div>
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
						<h1>办案区使用单</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-primary create">新增</button>
							<button class="btn btn-primary refresh">刷新</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="handlingAreaReceiptTable"
							class="table table-bordered table-hover m-ui-table-whole"
							cellspacing="0">
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
	src="<%=context%>/scripts/ajgl/handlingAreaReceipt/handlingAreaReceiptList.js"></script>
</html>