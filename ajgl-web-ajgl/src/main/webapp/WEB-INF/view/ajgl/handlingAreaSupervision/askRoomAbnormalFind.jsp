<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>案件管理 – 办案区督察</title>
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
					<li>办案区督察</li>
					<li>办案区督察</li>
				</ol>
				<div class="row">
				<div  class="col-xs-7" >				
					<button class="btn btn-primary" id="unusual"><span class="glyphicon glyphicon-play-circle micon-lg"></span>截取异常画面</button>
					<button class="btn btn-success" id="back"><span class="glyphicon glyphicon-download micon-lg"></span>返回</button>
				</div>
				<div  class="col-xs-5" id="roomDetail" >
				
				</div>
				</div>
				<input type="hidden" id="askRoomId"	value=<%=request.getParameter("askRoomId")%>>
				<input type="hidden" id="askRoomName"	value="${activityRoomBean.name}">
				<input type="hidden" id="askRoomStatusName"	value="${activityRoomBean.statusName}">
				<input type="hidden" id="tabHaveFlag"	value="${tabHaveFlag}">
				<input type="hidden" id="askRoomZt"	value="${activityRoomBean.status}">
				<input type="hidden" id="attachFlag"	value="${basicCaseBean.uploadNew}">
				<input type="hidden" id="attachId"	value="${basicCaseBean.attach.id}">
				<input type="hidden" id="attachName"	value="${basicCaseBean.attach.name}">
				<input type="hidden" id="base64Str"	value="${basicCaseBean.attach.base64Str}">
				<input type="hidden" id="askRoomAllocationId"	value="${askRoomAllocation.id}">
				<div class="m-ui-layer-content">
					<div class="row">
						<div class="col-xs-5"
							style="height: 545px; width: 600px; border: 1px solid #666; overflow: hidden;">
							<div id="imgDiv" style="height: 420px; background: #333;">
								<img src="..//images/photo/xunwenshi.png" width="600"
									height="420">
							</div>

						</div>


						<div id="tabs" class="m-ui-tabs" style="width: 505px; border: 1px solid #666;float:right; margin-left: 30px;">
							<ul>
								<li id="wgxx" style="width:49%;"><a style="width:90%" href="#tabs-1">违规信息</a></li>
								<li id="zyxx" style="width:49%;"><a style="width:90%" href="#tabs-2">当前在用信息</a></li>
							</ul>
							<div id="tabs-1" style="height:500px;overflow:auto;">
								<table class="table table-sg table-sg-sm" cellspacing="0" width="100%" height="450px;" >
								    <tbody id="radioBody">
									</tbody>
								</table>
								<div class="m-ui-commitbox">
									<button id="tj" class="btn btn-primary">提交</button> 
									<button id="back" class="btn btn-default">取消</button>
								</div>
							</div>
							<div id="tabs-2" style="height:500px;overflow:auto;">
								<table class="table table-sg table-sg-sm" cellspacing="0"	width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="120">使用单编号：</td>
											<td colspan="3">${basicCaseBean.handlingAreaReceiptNum }</td>
										</tr>
										<tr>
											<td class="td-left" width="100">被询（讯）问人：</td>
											<td colspan="3">${basicCaseBean.byQuestioningPeopleName}</td>
										</tr>
										<tr>
											<td class="td-left" width="100">性别：</td>
											<td colspan="3">${basicCaseBean.sexStr}</td>
										</tr>
										<tr>
											<td class="td-left" width="100">出生日期：</td>
											<td colspan="3">${birthDay}</td>
										</tr>
										<tr>
											<td class="td-left">联系方式：</td>
											<td colspan="3">${askRoomAllocation.handlingAreaReceipt.basicCase.byQuestioningPeopleTelphoneNumber}</td>
										</tr>
										<tr>
											<td class="td-left">证件种类：</td>
											<td colspan="3">${basicCaseBean.byQuestioningPeopleIdentifyTypeStr}</td>
										</tr>
										<tr>
											<td class="td-left">证件号码：</td>
											<td colspan="3">${basicCaseBean.byQuestioningPeopleIdentifyNum}</td>
										</tr>
										<tr>
											<td class="td-left">所属案件：</td>
											<td colspan="3">${basicCaseBean.lawCase}</td>
										</tr>
										<tr>
											<td class="td-left">进入办案区原因：</td>
											<td colspan="3">${basicCaseBean.enterAreaReasonsStr}</td>
										</tr>
										<tr>
											<td class="td-left">对应文书：</td>
											<td id="attach" colspan="3"></td>
										</tr>
										<tr>
											<td class="td-left">办案民警：</td>
											<td colspan="3">${basicCaseBean.handlingPolice}</td>
										</tr>
									</tbody>
								</table>
								<table class="table table-sg table-sg-sm" cellspacing="0" width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="160px">讯（询）问室分配人：</td>
											<td colspan="3">${askRoomAllocation.allocationPeople}</td>
										</tr>
										<tr>
											<td class="td-left" width="160px">讯（询）问室分配时间：</td>
											<td colspan="3" id="allocationTime">${time}</td>
										</tr>
									</tbody>
								</table>
								
								
							</div>
						</div>
					</div>
				</div>
				
				<!--内容end-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaSupervision/askRoomAbnormalFind.js"></script>
</html>