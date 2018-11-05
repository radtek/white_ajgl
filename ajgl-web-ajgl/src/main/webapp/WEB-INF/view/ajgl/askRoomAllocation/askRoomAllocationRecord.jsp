<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 讯（询）问室分配记录</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
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
					<li>讯（询）问室分配记录</li>
				</ol>
				<!--整体查询板块--begin-->
				<div class="basic-query-out">
					<div class="basic-query">
						<input type="text" class="form-control input-sm simpleCode" value="使用单编号模糊查询"
							onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
							onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
							style="color: #aaa;">
						<button class="btn btn-primary btn-sm queryRecord">查询</button>
						<button class="advanced-btn">展开高级查询</button>
					</div>
				</div>

				<div class="m-ui-searchbox  advanced-query">
					<div class="m-ui-searchbox-con">

						<div class="row row-mar">
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">分配讯（询）问室：</label>
								</div>
								<div class="col-xs-6">
									<input type="text" id="roomName" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">使用单编号：</label>
								</div>
								<div class="col-xs-6">
									<input type="text" class="form-control input-sm code">
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">进入办案区原因：</label>
								</div>
								<div class="col-xs-6 input-group">
									<select class="form-control select2-noSearch"
											id="enterAreaReasons">

									</select>
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">被讯（询）问人：</label>
								</div>
								<div class="col-xs-6">
									<input type="text" class="form-control input-sm"
											id="askPerson">
								</div>
							</div>
						</div>

						<div class="row row-mar">
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">证件号码：</label>
								</div>
								<div class="col-xs-6">
									<input type="text" id="idNum" class="form-control input-sm">
								</div>
							</div>
							<div id="allocationDate" class="col-xs-6 dateRange">
									<input type="hidden" id="dtfmt" class="dateFmt"
										value="yyyy-MM-dd hh:mm" />
									<div class="col-xs-3">
										<label class="control-label">分配时间：</label>
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
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">排序依据：</label>
								</div>
								<div class="col-xs-6 input-group">
									<select class="form-control select2-noSearch" id="orderCondition">
										<option value="askRoomName">分配讯（询）问室</option>
										<option value="handlingAreaReceiptNum">使用单编号</option>
										<option value="byQuestioningPeopleName">被询问人姓名</option>
										<option value="allocationTime" selected="selected">分配时间</option>
									</select>
								</div>
							</div>
							
						</div>
						<div>
							<div class="col-xs-9"></div>
							<div class="col-xs-3 m-ui-btn-box">
								<button class="btn btn-primary btn-sm queryRecord">查询</button>
								<button class="btn btn-default btn-sm" id="reset">重置</button>
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
						<h1>讯（询）问室分配记录</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-primary" id="refresh">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->

				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="recordTable"
							class="table table-bordered table-hover m-ui-table-whole"
							cellspacing="0" width="100%">
						
						</table>
					</div>
				</div>


				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript">
	var askRoomId = "${param.askRoomId}";
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/askRoomAllocation/askRoomAllocationRecord.js"></script>
</html>