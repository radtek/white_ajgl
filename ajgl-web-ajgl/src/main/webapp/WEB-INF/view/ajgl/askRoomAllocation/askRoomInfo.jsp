<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 讯（询）问室分配</title>
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
					<li>讯（询）问室分配</li>
				</ol>
				<div class="row">
				<div  class="col-xs-7" >				
					<button class="btn btn-success" id="back"><span class="glyphicon glyphicon-download micon-lg"></span>返回</button>
					<button class="btn btn-info" id="refresh"><span class="glyphicon glyphicon-edit micon-lg"></span>刷新</button>
					
				</div>
				<div  class="col-xs-5" id="roomDetail" >
				
				</div>
				</div>
				<input type="hidden" id="askRoomId"	value=<%=request.getParameter("askRoomId")%>>
				<input type="hidden" id="askRoomName"	value="${activityRoomBean.name}">
				<input type="hidden" id="askRoomStatusName"	value="${activityRoomBean.statusName}">
				<input type="hidden" id="askRoomZt"	value="${activityRoomBean.status}">
				<input type="hidden" id="attachFlag"	value="${basicCaseBean.uploadNew}">
				<input type="hidden" id="attachId"	value="${basicCaseBean.attach.id}">
				<input type="hidden" id="attachName"	value="${basicCaseBean.attach.name}">
				<input type="hidden" id="base64Str"	value="${basicCaseBean.attach.base64Str}">
				<input type="hidden" id="tabHaveFlag"	value="${tabHaveFlag}">
				<div class="m-ui-layer-content">
					<div class="row">
						<div class="col-xs-5"
							style="height: 545px; width: 55%; border: 1px solid #666; overflow: hidden;">
							<div id="imgDiv" style="height: 420px; background: #333;">
								<img src="..//images/photo/xunwenshi.png" width="600"
									height="420">
							</div>

							<p class="color-gray mar-left mar-top">
								申请使用人：${askRoomAllocation.handlingAreaReceipt.modifyPeople.name} <br> 申请时间：${askRoomAllocation.handlingAreaReceipt.updatedTime} <br> 使用申请单编号：
								<a	href="#" id='${askRoomAllocation.handlingAreaReceipt.id }' class='a-link mar-right showDetail'>${askRoomAllocation.handlingAreaReceipt.basicCase.handlingAreaReceiptNum}</a>
							</p>
						</div>


						<div id="tabs" class="col-xs-5 m-ui-tabs3 ppt"	style="height: 542px; width: 42%; text-align:center;border: 1px solid #666; margin-left: 16px;">
                            <div class="row" style="width: 99%;">
								<div  class="col-xs-7" >				
									<label class="control-label" style="text-align:left;">当前在用信息</label>
								</div>
							</div>
							<table class="table table-sg table-sg-sm" cellspacing="0"	style="width: 99%;">
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
							<table class="table table-sg table-sg-sm" cellspacing="0" style="width: 99%;">
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
				
				<!--内容end-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/askRoomAllocation/askRoomInfo.js"></script>
</html>