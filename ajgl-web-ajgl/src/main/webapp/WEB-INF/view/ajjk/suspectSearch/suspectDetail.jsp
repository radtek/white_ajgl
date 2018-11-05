<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 案件监控</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformId" class="validform m-ui-layer-body">
	<%-- <%@include file="/WEB-INF/base/skin/topPart.jsp"%> --%>
	<input type="hidden" id="dataId"
		value=<%=request.getParameter("dataId")%>>
	<%-- <div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-xqck.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content"> --%>
			
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>案件监控管理</li>
					<li>查看人员涉案信息</li>
				</ol>
				<div class="row">
					<button id="back" class="btn btn-primary">
						<span class="glyphicon glyphicon-menu-left mar-right"></span>返回
					</button>
				</div>
			
				<h2 class="font24 text-center" style="margin-bottom: 20px;">人员涉案信息</h2>
				<div style="border: 1px solid #999; margin: 15px auto">
					<table class="table table-sg xyrTable" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td class="td-left" width="15%">嫌疑人类型</td>
								<td width="18%" valName="suspectType" class="valCell"></td>
								<td class="td-left" width="15%">案件角色</td>
								<td colspan="3" valName="crimeRole" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">嫌疑依据</td>
								<td valName="suspiciongist" class="valCell"></td>
								<td class="td-left">处理方式</td>
								<td width="18%" valName="approach" class="valCell"></td>
								<td class="td-left">人员状态</td>
								<td width="18%" valName="personState" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">抓获日期</td>
								<td valName="dateofCapture" class="valCell"></td>
								<td class="td-left">抓获经过</td>
								<td colspan="3" valName="captureProcess" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">案件处理种类</td>
								<td valName="caseTreatmentType" class="valCell"></td>
								<td class="td-left">特殊人群类型</td>
								<td colspan="3" valName="specialGroup" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left" width="15%">违法事实及依据</td>
								<td colspan="5" valName="criRecord" class="valCell"></td>
							</tr>
							<tr>
								<td colspan="6" class="alert-info row-mar0">刑拘拘留</td>
							</tr>
							<tr>
								<td class="td-left">是否批准刑拘</td>
								<td valName="isxjApproval" class="valCell"></td>
								<td class="td-left">刑拘批准人</td>
								<td valName="xjApprover" class="valCell"></td>
								<td class="td-left">刑拘批准时间</td>
								<td valName="xjApprovalTime" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">刑拘时间</td>
								<td valName="detentionTime" class="valCell"></td>
								<td class="td-left">拘留地点</td>
								<td valName="detentionAddress" class="valCell"></td>
								<td class="td-left">拘留时限</td>
								<td valName="detentionLength" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">刑拘审核人</td>
								<td valName="xjReviewed" class="valCell"></td>
								<td class="td-left">刑拘办理人</td>
								<td colspan="3" valName="xjTransactor" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">是否批准延长拘留期限</td>
								<td valName="isycjlApproval" class="valCell"></td>
								<td class="td-left">延长拘留期限批准人</td>
								<td valName="ycjlApprover" class="valCell"></td>
								<td class="td-left">延长拘留期限批准时间</td>
								<td valName="ycjlApprovalTime" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">延长拘留期限时间</td>
								<td valName="ycDetentiomTime" class="valCell"></td>
								<td class="td-left">延长拘留办理人</td>
								<td valName="ycjlTransactor" class="valCell"></td>
								<td class="td-left">延长拘留审核人</td>
								<td valName="ycjlReviewed" class="valCell"></td>
							</tr>
							<tr valTr="isqbhsApproval">
								<td colspan="6" class="alert-info row-mar0 valShow"
									valName="isqbhsApproval">取保候审</td>
							</tr>
							<tr valTr="isqbhsApproval">
								<td class="td-left">是否批准取保候审</td>
								<td valName="isqbhsApproval" class="valCell"></td>
								<td class="td-left">取保候审批准人</td>
								<td valName="qbhsApprover" class="valCell"></td>
								<td class="td-left">取保候审批准时间</td>
								<td valName="qbhsApprovalTime" class="valCell"></td>
							</tr>
							<tr valTr="isqbhsApproval">
								<td class="td-left">取保候审执行日期</td>
								<td valName="bailTime" class="valCell"></td>
								<td class="td-left">取保候审办理人</td>
								<td valName="qbhsTransactor" class="valCell"></td>
								<td class="td-left">取保候审审核人</td>
								<td valName="qbhsreviewed" class="valCell"></td>
							</tr>
							<tr valTr="isjsjzApproval">
								<td colspan="6" class="alert-info row-mar0 valShow"
									valName="isjsjzApproval">监视居住</td>
							</tr>
							<tr valTr="isjsjzApproval">
								<td class="td-left">是否批准监视居住</td>
								<td valName="isjsjzApproval" class="valCell"></td>
								<td class="td-left">监视居住批准人</td>
								<td valName="jsjzApprover" class="valCell"></td>
								<td class="td-left">监视居住批准时间</td>
								<td valName="jsjzApprovalTime" class="valCell"></td>
							</tr>
							<tr valTr="isjsjzApproval">
								<td class="td-left">监视居住执行日期</td>
								<td valName="surveillanceTime" class="valCell"></td>
								<td class="td-left">监视居住办理人</td>
								<td valName="jsjzTransactor" class="valCell"></td>
								<td class="td-left">监视居住审核人</td>
								<td valName="jsjzReviewed" class="valCell"></td>
							</tr>
							<tr>
								<td colspan="6" class="alert-info row-mar0">报捕</td>
							</tr>
							<tr>
								<td class="td-left">是否报捕</td>
								<td valName="isRequestApproval" class="valCell"></td>
								<td class="td-left">报捕时间</td>
								<td colspan="3" valName="requestarrestTime" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">报捕办理人</td>
								<td valName="requestTransactor" class="valCell"></td>
								<td class="td-left">报捕批准人</td>
								<td colspan="3" valName="requestApprover" class="valCell"></td>
							</tr>
							<tr>
								<td colspan="6" class="alert-info row-mar0">批准逮捕</td>
							</tr>
							<tr>
								<td class="td-left">是否批准逮捕</td>
								<td valName="isArrestApproval" class="valCell"></td>
								<td class="td-left">批准逮捕时间</td>
								<td colspan="3" valName="arrestApprovalTime" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">执行逮捕时间</td>
								<td valName="arrestTime" class="valCell"></td>
								<td class="td-left">不批捕原因</td>
								<td colspan="3" valName="arrestRefuse" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">是否批准提请复议逮捕</td>
								<td valName="isfyApproval" class="valCell"></td>
								<td class="td-left">提请逮捕复议批准人</td>
								<td colspan="3" valName="drewReviewer" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">提请复议逮捕批准时间</td>
								<td valName="drewReviewTime" class="valCell"></td>
								<td class="td-left">提请复议逮捕时间</td>
								<td valName="arrestviewerTime" class="valCell"></td>
								<td class="td-left">复议后是否批准逮捕</td>
								<td valName="isfypzApproval" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">是否批准提请复核逮捕</td>
								<td valName="isfhApproval" class="valCell"></td>
								<td class="td-left">提请逮捕复核批准人</td>
								<td colspan="3" valName="arrestReviewer" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">提请复核逮捕批准时间</td>
								<td valName="arrestreviewTime" class="valCell"></td>
								<td class="td-left">提请复核批捕时间</td>
								<td valName="toArrestTime" class="valCell"></td>
								<td class="td-left">复核后是否提请批捕</td>
								<td valName="isfhpzApproval" class="valCell"></td>
							</tr>
							<tr>
								<td colspan="6" class="alert-info row-mar0">移送起诉</td>
							</tr>
							<tr>
								<td class="td-left">是否移送起诉</td>
								<td valName="isysqsApproval" class="valCell"></td>
								<td class="td-left">移送起诉时间</td>
								<td valName="prosecutedTime" class="valCell"></td>
								<td class="td-left">移送起诉罪名</td>
								<td valName="prosecutedCharge" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">不起诉原因</td>
								<td valName="notprosecuteReason" class="valCell"></td>
								<td class="td-left">移送起诉审核人</td>
								<td valName="prosecutReviewed" class="valCell"></td>
								<td class="td-left">移送起诉办理人</td>
								<td valName="prosecutTransactor" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">移送起诉批准人</td>
								<td colspan="5" valName="prosecutApproval" class="valCell"></td>
							</tr>
							<tr>
								<td colspan="6" class="alert-info row-mar0">行政处罚</td>
							</tr>
							<tr>
								<td class="td-left">是否批准行政处罚</td>
								<td valName="isxzcfApproval" class="valCell"></td>
								<td class="td-left">行政处罚批准人</td>
								<td valName="adminPenaltyApper" class="valCell"></td>
								<td class="td-left">行政处罚批准时间</td>
								<td valName="adminPenaltyApptime" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">是否行政拘留</td>
								<td valName="isadminDetention" class="valCell"></td>
								<td class="td-left">是否行政拘留并罚款</td>
								<td valName="isFinesDetention" class="valCell"></td>
								<td class="td-left">行政拘留日期</td>
								<td valName="adminDetentionTime" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">是否罚款</td>
								<td valName="isFines" class="valCell"></td>
								<td class="td-left">罚款金额</td>
								<td valName="finesNo" class="valCell"></td>
								<td class="td-left">是否没收违法所得及违法财物</td>
								<td valName="isConfiscation" class="valCell"></td>

							</tr>
							<tr>
								<td class="td-left">是否责令停产停业</td>
								<td valName="isorderedcase" class="valCell"></td>
								<td class="td-left">是否暂扣吊销证照</td>
								<td valName="isWithheldLicenses" class="valCell"></td>
								<td class="td-left">是否警告(行政处罚)</td>
								<td valName="iswarningPunish" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">行政处罚办理人</td>
								<td valName="adminTransactor" class="valCell"></td>
								<td class="td-left">行政处罚审核人</td>
								<td valName="adminPenalReviewed" class="valCell"></td>
								<td class="td-left">行政处罚执行日期</td>
								<td valName="adminPenaltyTime" class="valCell"></td>
							</tr>
							<tr>
								<td class="td-left">其他</td>
								<td valName="other" colspan="5" class="valCell"></td>
							</tr>
							<tr valTr="isDetoxif">
								<td colspan="6" class="alert-info row-mar0 valShow"
									valName="isDetoxif">强制隔离戒毒</td>
							</tr>
							<tr valTr="isDetoxif">
								<td class="td-left">是否强制隔离戒毒</td>
								<td valName="iszyDetoxif" class="valCell"></td>
								<td class="td-left">是否自愿接受强制隔离戒毒</td>
								<td valName="iszyDetoxif" class="valCell"></td>
								<td class="td-left">强制隔离戒毒批准人</td>
								<td valName="detoxifApprover" class="valCell"></td>
							</tr>
							<tr valTr="isDetoxif">
								<td class="td-left">强制隔离戒毒审核人</td>
								<td valName="detoxifReviewed" class="valCell"></td>
								<td class="td-left">强制隔离戒毒办理人</td>
								<td colspan="3" valName="detoxifTransactor" class="valCell"></td>
							</tr>
							<tr valTr="ifsqjd">
								<td colspan="6" class="alert-info row-mar0 valShow"
									valName="ifsqjd">责令社区戒毒</td>
							</tr>
							<tr valTr="ifsqjd">
								<td class="td-left">是否社区戒毒</td>
								<td valName="ifsqjd" class="valCell"></td>
								<td class="td-left">社区戒毒批准人</td>
								<td valName="sqjdApproved" class="valCell"></td>
								<td class="td-left">社区戒毒批准时间</td>
								<td valName="sqjdApprovalTime" class="valCell"></td>
							</tr>
							<tr valTr="ifsqjd">
								<td class="td-left">社区戒毒办理人</td>
								<td valName="sqjdRansactor" class="valCell"></td>
								<td class="td-left">社区戒毒审核人</td>
								<td colspan="3" valName="sqjdReviewer" class="valCell"></td>
							</tr>
							<tr valTr="issqkf">
								<td colspan="6" class="alert-info row-mar0 valShow"
									valName="issqkf">社区康复</td>
							</tr>
							<tr valTr="issqkf">
								<td class="td-left">是否社区康复</td>
								<td colspan="3" valName="issqkf" class="valCell"></td>
								<td class="td-left">社区康复批准人</td>
								<td valName="sqkfApproval" class="valCell"></td>
								<td class="td-left">社区康复批准时间</td>
								<td valName="sqkfApprovalTime" class="valCell"></td>
							</tr>
							<tr valTr="issqkf">
								<td class="td-left">社区康复办理人</td>
								<td valName="sqkfActor" class="valCell"></td>
								<td class="td-left">社区康复审核人</td>
								<td colspan="3" valName="sqkfReviewer" class="valCell"></td>
							</tr>
						</tbody>
					</table>
				</div>
			
				<!--结束-->

		<!-- 	</div>
		</div>
	</div> -->
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajjk/suspectSearch/suspectDetail.js"></script>
</html>