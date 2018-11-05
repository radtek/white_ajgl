<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<script type="text/javascript">
var allowModify='<%=request.getParameter("allowModify")%>';
</script>
<body class="validform" id="validformId">
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
						<div class="m-ui-caozuobox">
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="m-ui-layer-box">
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include
							file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						<!-----随身物品记录----->
						<table class="table table-sg" cellspacing="0" width="100%">
							<tr>
								<td class="td-left" width="160">当前在用讯（询）问室：</td>
								<td><span id="useRoom"></span><input type="hidden" id="useRoomId"/><input type="hidden" id="useRoomStatus"/></td>
							</tr>
						</table>
						<div class="m-ui-title3 mar-top">
							<h2>临时离开</h2>
						</div>
						<div class="row text-right" style="margin-top: -20px;">
							<button class="btn btn-bordered btn-sm" id="addRow"><span class="icon-plus mar-right"></span>增行</button>
							<button id="offLine" class="btn  btn-sm"><span class="icon-circle-blank mar-right-sm"></span>手环挂起</button>
               				<button id="onLine" class="btn  btn-sm"><span class="icon-ban-circle mar-right-sm"></span>取消挂起</button>
						</div>
						<table id="temporaryLeave"
							class="table table-bordered table-hover m-ui-table-no-paginate"
							cellspacing="0" width="100%">

						</table>
						<div class="m-ui-title3 mar-top">
							<h2>
								最终离开
								<button id="updateLeaveInfo" style="margin-left: 30px"
									class="btn btn-info">
									<span class="glyphicon glyphicon-edit micon-lg mar-right"></span>维护最终离开情况
								</button>
							</h2>

						</div>
						<div id="leaveInfo" style="display: none">
							<table class="table table-sg" cellspacing="0" width="100%">
								<tr>
									<td class="td-left" width="100">最终离开时间：</td>
									<td width="150">
										<div id="finalLeaveTime" class="input-group laydate finalLeaveTime"
											style="width: 160px">
											<input type="hidden" class="laydate-fmt dateFmt"
												value="yyyy-MM-dd HH:mm" /> <input
												id="gatherTime_input" 
												class="form-control laydate-value"
												readonly="readonly"> <span
												class="input-group-addon m-ui-addon laydate-value-span"> <span 
												class="glyphicon glyphicon-calendar" aria-hidden="true"
												style="font-size: 16px;"></span>
											</span>
										</div>
									</td>
									<td class="td-left" width="70">离开原因：</td>
									<td><input id="leaveReason"  class="form-control input-sm d" datatype="*0-80"
										value="" style="width: 150px;"></td>
									<td class="td-left" width="190">离开前使用的讯（询）问室：</td>
									<td><span id="askRoomName"></span>
										<input id="askRoomId" type="hidden"></td>
								</tr>
							</table>
						</div>
						<div class="color-gray text-right">
							最新修改人：<span id="modifyPeopleName"></span> <span
								style="margin-left: 50px;">最新修改时间：<span id="updateTime"></span></span>
						</div>

						<!--操作历史-->
						<!--<div class="m-ui-title3 mar-top">
							<h2>操作历史</h2>
						</div>

						<div class="od-expand-btn" id="od-expand-btn-3"
							onClick="document.getElementById('od-expand-content-3').style.display='block';document.getElementById('od-expand-btn-3').style.display='none'">
							<a href="###"><span>显示操作历史</span></a>
						</div>
						<div id="od-expand-content-3" style="display: none;">
							<div class="m-ui-table m-ui-table-sm">
								<table id="history"
									class="table table-bordered table-hover m-ui-table-no-paginate"
									cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>序号</th>
											<th>操作内容</th>
											<th>操作人</th>
											<th>操作时间</th>
											<th>说明</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
							<div class="od-expand-btn od-fold-btn"
								onClick="document.getElementById('od-expand-content-3').style.display='none';document.getElementById('od-expand-btn-3').style.display='block'">
								<a href="###"><span>隐藏操作历史</span></a>
							</div>
						</div>-->
						<!--操作历史-->
						<div class="m-ui-commitbox">
							<button class="btn btn-primary btn-lg" id="confirm"
								style="width: 100px;">保存</button>
							<button class="btn btn-bordered btn-lg" id="cancel">取消</button>
							<button class="btn btn-bordered btn-lg" style="display: none"
								id="return">返回</button>
						</div>
					</div>
					<!-----随身物品检查记录end----->
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript">
	
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/leaveSituation/updateLeaveSituation.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>