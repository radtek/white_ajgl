<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 案件监控</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<input type="hidden" id="caseCode"
	value=<%=request.getParameter("caseCode")%>>
<body class="m-ui-layer-body">

<div style="display:none"><%@include file="/WEB-INF/base/skin/topPart.jsp"%></div>
   <%-- <div id="c-center">--%>
       <div style="display:none"><%@include file="/WEB-INF/base/skin/leftPart-xqck.jsp"%></div>
        <%--<div id="c-center-right-content">--%>
			<ol class="breadcrumb m-ui-breadcrumb">
				<li>案件监控管理</li>
				<li>案件全流程监控查看</li>
			</ol>

			<!--整体查询板块--end-->

			<div class="row">
				<button style="display:none" id="back" class="btn btn-primary">
					<span class="glyphicon glyphicon-menu-left mar-right"></span> 返回
				</button>
				<button style="display:none" id="refresh" class="btn btn-primary">
					<span class="glyphicon glyphicon-refresh mar-right"></span> 刷新
				</button>
			</div>

			<div id="c-center-right-content-block">

				<div id="c-center-right-content-bar" style="width:360px; margin-right:-381px;">
					<div id="caseTrace">
						<div class="m-ui-title3">
							<h2>案件追踪</h2>
						</div>
						<div class="history-content history-content2 history-content-lg2">
							<ul id="processUl" class="nav nav-tabs" role="tablist">
	
							</ul>
						</div>
					</div>
					<div id="susTrace" style="display:none;">
						<div class="m-ui-title3">
							<h2>人员追踪</h2>
						</div>
						<div class="history-content history-content2  history-content-lg" id="personTrace">
						
						</div>
					</div>
				</div>

				<div id="c-center-right-content-con">
					<div class="right-inner" style="margin-left:375px;">

						<h2 class="font24 text-center" style="margin-bottom: 20px;">
							<span id="caseTitleUp"></span> <span id="caseStateUp"
								class="state state-red2 mar-left"></span>
						</h2>
						<div class="alert alert-info" style="margin-bottom: 0">
							<table class="table-sg-sm" cellspacing="0" width="100%">
								<tbody>
									<tr>
										<td width="80">案件编号：</td>
										<td id="caseCodeUp"></td>
										<td width="80">发案时间：</td>
										<td id="caseTimeStartUp"></td>
									</tr>
									<tr>
										<td width="70">发案地点：</td>
										<td id="caseAddressUp"></td>
										<td>案由：</td>
										<td id="caseReasonUp"></td>
									</tr>
									<tr>
										<td>主办人：</td>
										<td id="disposePersonUp"></td>
										<td>立案时间：</td>
										<td id="filingTime"></td>
									</tr>
									<tr>
										<td>办理单位：</td>
										<td id="dqbldwUp"></td>
										<td>是否归档：</td>
										<td id="ifArchive"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="alert alert-info">
							<table class="table-sg-sm" cellspacing="0" width="100%">
								<tbody>
									<tr>
										<td width="70">简要案情：</td>
										<td id="detailsUp"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div id="tabs" class="m-ui-tabs" style="background: #fff;">
							<ul>
								<li class="tabsLi" id="detailLi"><a href="#tabs-1">基本信息</a></li>
								<li class="tabsLi" id="detailAlm"><a href="#tabs-8">接处警</a></li>
								<li class="tabsLi-xyr" id="detailXYR"><a href="#tabs-2">嫌疑人</a></li>
								<li class="tabsLi" id="detailObj"><a href="#tabs-3">涉案物品</a></li>
								<li class="tabsLi" id="detailKYWP"><a href="#tabs-7">扣押物品</a></li>
								<li class="tabsLi" id="detailBJR"><a href="#tabs-4">报案人、受害人</a></li>
								<li class="tabsLi" id="detailJZ"><a href="#tabs-5">卷宗</a></li>
								<li class="tabsLi" id="bcaLst"><a href="#tabs-6">并案从案</a></li>
								<li class="tabsLi" id="bcaLst"><a href="#tabs-9">证据笔录</a></li>
							</ul>

							<div id="tabs-1">
								<div class="m-ui-title3">
								</div>
								<table class="table table-sg caseTable" cellspacing="0"
									width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="15%">案件编号</td>
											<td width="30%" valName="caseCode" class="valCell"></td>
											<td class="td-left" width="15%">案件名称</td>
											<td width="30%" valName="caseName" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">案件文号</td>
											<td valName="caseWhid" class="valCell"></td>
											<td class="td-left">案件类别</td>
											<td valName="caseSort" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">案件性质</td>
											<td valName="caseKind" class="valCell"></td>
											<td class="td-left">案件状态</td>
											<td valName="caseState" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">案由</td>
											<td valName="caseReason" class="valCell"></td>
											<td class="td-left">发案地点 <span
												class="micon-lg glyphicon glyphicon-map-marker pull-right"></span>
											</td>
											<td valName="caseAddress" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">发现时间 <span
												class="micon-lg glyphicon glyphicon-time pull-right"></span>
											</td>
											<td>起：<span valName="discoverTimeStart" class="valCell"></span>
												<br>止：<span valName="discoverTimeEnd" class="valCell"></span>
											</td>
											<td class="td-left">发案时间 <span
												class="micon-lg glyphicon glyphicon-time pull-right"></span>
											</td>
											<td>起：<span valName="caseTimeStart" class="valCell"></span>
												<br>止：<span valName="caseTimeEnd" class="valCell"></span>
											</td>

										</tr>
										<tr>
											<td class="td-left">涉及国家地区</td>
											<td valName="countries" class="valCell"></td>
											<td class="td-left">发案地行政区划</td>
											<td valName="districtCode" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">发案社区</td>
											<td valName="community" class="valCell"></td>
											<td class="td-left">案情关键词</td>
											<td valName="caseKeyword" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">简要案情</td>
											<td colspan="3">
												<div class="alert alert-info">
													<span valName="details" class="valCell"></span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="td-left">是否涉密案件</td>
											<td valName="ifClassified" class="valCell"></td>
											<td class="td-left">当前办理单位</td>
											<td valName="dqbldw" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">涉案总价值经侦专用</td>
											<td valName="totalValue" class="valCell"></td>
											<td class="td-left">现勘编号</td>
											<td valName="kno" class="valCell"></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="tabs-8">
								<div class="m-ui-title3">
								</div>
								<table class="table alarmTable table-sg" cellspacing="0"
									width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="15%">报警电话 <span
												class="micon-lg glyphicon glyphicon-earphone pull-right"></span>
											</td>
											<td width="30%" valName="rvcallNo" class="valCell"></td>
											<td width="15%" class="td-left">警情类别</td>
											<td width="30%" valName="alarmType" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left" width="15%">接警单位</td>
											<td width="30%" valName="receiveUnit" class="valCell"></td>
											<td class="td-left" width="15%">接警人</td>
											<td valName="receivePerson" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">接警时间 <span
												class="micon-lg glyphicon glyphicon-time pull-right"></span>
											</td>
											<td valName="reportTime" class="valCell"></td>
											<td class="td-left">接警形式</td>
											<td valName="reportMode" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">接警内容</td>
											<td colspan="3">
												<div class="alert alert-info">
													<span valName="reportDetails" class="valCell"></span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="td-left">出动船支（次）</td>
											<td valName="boatNum" class="valCell"></td>
											<td class="td-left">出动机动车（次）</td>
											<td valName="voitureNum" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">出动警力数</td>
											<td valName="policeNum" class="valCell"></td>
											<td class="td-left">出动协勤数</td>
											<td valName="policeAssNum" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">处警单位</td>
											<td valName="disposeUnit" class="valCell"></td>
											<td class="td-left">处警结果</td>
											<td valName="disposeResult" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">处警简要情况</td>
											<td colspan="3">
												<div class="alert alert-info">
													<span valName="disposeDetails" class="valCell"></span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="td-left">处警人1</td>
											<td valName="disposePerson" class="valCell"></td>
											<td class="td-left">处警人2</td>
											<td valName="disposePerson2" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">处警意见</td>
											<td valName="disposeOpinion" class="valCell"></td>
											<td class="td-left">到达现场时间 <span
												class="micon-lg glyphicon glyphicon-time pull-right"></span>
											</td>
											<td valName="arriveTime" class="valCell"></td>

										</tr>
										<tr>
											<td class="td-left">解救被拐卖儿童数</td>
											<td valName="rescueyNum" class="valCell"></td>
											<td class="td-left">解救被拐卖妇女数</td>
											<td valName="rescuewNum" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">解救人质数</td>
											<td valName="rescurhNum" class="valCell"></td>
											<td class="td-left">救助群众</td>
											<td valName="salvationPeople" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">救助伤员</td>
											<td colspan="3" valName="salvationWou" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">留置审查人数</td>
											<td valName="censorNum" class="valCell"></td>
											<td class="td-left">是否现场处罚</td>
											<td valName="ifSpotfine" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">是否现场调解</td>
											<td valName="ifSpotReconcile" class="valCell"></td>
											<td class="td-left">辖区</td>
											<td valName="popedom" class="valCell"></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id="tabs-2">
								<div class="man-group">
									<a href="###" class="btn" style="display: none"></a>
								</div>

								<div class="m-ui-title3">
									<h2>人员基本信息</h2>
								</div>
								<table style="display: none;"
									class="table table-sg personForXYR" cellspacing="0"
									width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="15%">人员编号</td>
											<td width="30%" valName="personId" class="valCell"></td>
											<td class="td-left" width="15%">性别</td>
											<td width="30%" valName="sex" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">姓名</td>
											<td>
												<h2 valName="name" class="valCell font24"></h2>
											</td>
											<td class="td-left">别名</td>
											<td valName="aliasName" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">曾用名</td>
											<td valName="usedName" class="valCell"></td>
											<td class="td-left">绰号</td>
											<td valName="nickName" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">身份证号</td>
											<td valName="idcardNo" class="valCell"></td>
											<td class="td-left">婚否</td>
											<td valName="ifMarry" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">出生日期起</td>
											<td valName="birthdayStart" class="valCell"></td>
											<td class="td-left">出生日期止</td>
											<td valName="birthdayEnd" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">QQ号码</td>
											<td valName="qqNo" class="valCell"></td>
											<td class="td-left">电子邮箱 <span
												class="micon-lg glyphicon glyphicon-envelope pull-right"></span>
											</td>
											<td valName="email" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">联系电话<span
												class="micon-lg glyphicon glyphicon-phone pull-right"></span>
											</td>
											<td valName="telephone" class="valCell"></td>
											<td class="td-left">籍贯国籍</td>
											<td valName="nativePlace" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">工作单位</td>
											<td valName="employUnit" class="valCell"></td>
											<td class="td-left">工作单位地址</td>
											<td valName="employAdress" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">现住址</td>
											<td valName="address" class="valCell"></td>
											<td class="td-left">现住地详细地址</td>
											<td valName="addressDetail" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">出生地</td>
											<td valName="birthCode" class="valCell"></td>
											<td class="td-left">出生地详细住址</td>
											<td valName="birthDetail" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">户籍地</td>
											<td valName="door" class="valCell"></td>
											<td class="td-left">户籍地详细地址</td>
											<td valName="doorDetail" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">民族</td>
											<td valName="nation" class="valCell"></td>
											<td class="td-left">特殊身份</td>
											<td valName="specialIdentity" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">文化程度</td>
											<td valName="culture" class="valCell"></td>
											<td class="td-left">政治面貌</td>
											<td valName="politics" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">宗教信仰</td>
											<td valName="faith" class="valCell"></td>
											<td class="td-left">其他专长</td>
											<td valName="otherspecialty" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">情况说明</td>
											<td colspan="3">
												<div class="alert alert-info">
													<span valName="thingExplain" class="valCell"></span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="td-left">职业</td>
											<td valName="job" class="valCell"></td>
											<td class="td-left">职务</td>
											<td valName="headShip" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">脸型</td>
											<td valName="faceForm" class="valCell"></td>
											<td class="td-left">人员状态</td>
											<td valName="personState" class="valCell"></td>
										</tr>
										<tr>
											<td colspan="6" class="alert-info row-mar0">体貌特征</td>
										</tr>
										<tr>
											<td class="td-left">身高</td>
											<td valName="staturest" class="valCell"></td>
											<td class="td-left">体重</td>
											<td valName="avoirdupois" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">体型</td>
											<td valName="bodilyForm" class="valCell"></td>
											<td class="td-left">血型</td>
											<td valName="bloodType" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">鞋号</td>
											<td valName="shoesSize" class="valCell"></td>
											<td class="td-left">足长</td>
											<td valName="footSize" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">口音</td>
											<td valName="tone" class="valCell"></td>
											<td class="td-left">嗜好</td>
											<td valName="addiction" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">是否会驾驶</td>
											<td valName="ifDrive" class="valCell"></td>
											<td class="td-left">是否经常上网</td>
											<td valName="ifOftenNet" class="valCell"></td>
										</tr>
									</tbody>
								</table>

								<div class="m-ui-title3 mar-top" style="margin-top: 30px;">
									<h2>嫌疑人信息</h2>
								</div>
								<table style="display: none;" class="table table-sg xyrTable"
									cellspacing="0" width="100%">
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
										<tr valTr="isxjApproval">
											<td colspan="6" class="alert-info row-mar0 valShow" valName="isxjApproval">刑拘拘留</td>
										</tr>
										<tr valTr="isxjApproval">
											<td class="td-left">是否批准刑拘</td>
											<td valName="isxjApproval" class="valCell"></td>
											<td class="td-left">刑拘批准人</td>
											<td valName="xjApprover" class="valCell"></td>
											<td class="td-left">刑拘批准时间</td>
											<td valName="xjApprovalTime" class="valCell"></td>
										</tr>
										<tr valTr="isxjApproval">
											<td class="td-left">刑拘时间</td>
											<td valName="detentionTime" class="valCell"></td>
											<td class="td-left">拘留地点</td>
											<td valName="detentionAddress" class="valCell"></td>
											<td class="td-left">拘留时限</td>
											<td valName="detentionLength" class="valCell"></td>
										</tr>
										<tr valTr="isxjApproval">
											<td class="td-left">刑拘审核人</td>
											<td valName="xjReviewed" class="valCell"></td>
											<td class="td-left">刑拘办理人</td>
											<td colspan="3" valName="xjTransactor" class="valCell"></td>
										</tr>
										<tr valTr="isxjApproval">
											<td class="td-left">是否批准延长拘留期限</td>
											<td valName="isycjlApproval" class="valCell"></td>
											<td class="td-left">延长拘留期限批准人</td>
											<td valName="ycjlApprover" class="valCell"></td>
											<td class="td-left">延长拘留期限批准时间</td>
											<td valName="ycjlApprovalTime" class="valCell"></td>
										</tr>
										<tr valTr="isxjApproval">
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

							<div id="tabs-3">
								<div class="man-group">
									<a href="###" class="btn" style="display: none"></a>
								</div>
								<table style="display: none;"
									class="table table-sg criminalObjectTable" cellspacing="0"
									width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="15%">物品名称</td>
											<td width="30%" valName="objectName" class="valCell"></td>
											<td class="td-left" width="15%">物品编号</td>
											<td width="30%" valName="objid" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">物品数量</td>
											<td valName="amounts" class="valCell"></td>
											<td class="td-left">数量单位</td>
											<td valName="amountUnit" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">重量</td>
											<td valName="weight" class="valCell"></td>
											<td class="td-left">重量单位</td>
											<td valName="weightUnit" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">价值</td>
											<td valName="value" class="valCell"></td>
											<td class="td-left">型号</td>
											<td valName="model" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">物品状态</td>
											<td valName="itemStatus" class="valCell"></td>
											<td class="td-left">品牌</td>
											<td valName="tradeMark" class="valCell"></td>
										</tr>

										<tr>
										<tr>
											<td class="td-left">产地</td>
											<td valName="producingArea" class="valCell"></td>
											<td class="td-left">购买地址</td>
											<td valName="purchaseAdd" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">购买日期</td>
											<td valName="purchaseDate" class="valCell"></td>
											<td class="td-left">号码(车牌号)</td>
											<td valName="serialNumber" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">特征描述及备注</td>
											<td valName="annex" class="valCell"></td>
											<td class="td-left">其他特征</td>
											<td valName="otherFeature" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">物品类型</td>
											<td valName="objectType" class="valCell"></td>
											<td class="td-left">物品成色</td>
											<td valName="quality" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">颜色1</td>
											<td valName="color1" class="valCell"></td>
											<td class="td-left">颜色2</td>
											<td valName="color2" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">颜色3</td>
											<td colspan="3" valName="color3" class="valCell"></td>
										</tr>

									</tbody>
								</table>
							</div>
							<div id="tabs-7">
								<div class="man-group">
									<a href="###" class="btn" style="display: none"></a>
								</div>
								<div class="kywpInfoDiv" style="display: none;">
									<table
										class="kywpInfo table table-bordered table-hover m-ui-table-whole"
										cellspacing="0" width="100%">
									</table>
								</div>

							</div>
							<div id="tabs-4">
								<div class="man-group">
									<a href="###" class="btn" style="display: none"></a>
								</div>
								<div class="m-ui-title3">
									<h2>人员基本信息</h2>
								</div>
								<table style="display: none;" class="table table-sg personInfo"
									cellspacing="0" width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="15%">人员编号</td>
											<td width="30%" valName="personId" class="valCell"></td>
											<td class="td-left" width="15%">性别</td>
											<td width="30%" valName="sex" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">姓名</td>
											<td>
												<h2 valName="name" class="valCell font24"></h2>
											</td>
											<td class="td-left">别名</td>
											<td valName="aliasName" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">曾用名</td>
											<td valName="usedName" class="valCell"></td>
											<td class="td-left">绰号</td>
											<td valName="nickName" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">身份证号</td>
											<td valName="idcardNo" class="valCell"></td>
											<td class="td-left">婚否</td>
											<td valName="ifMarry" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">出生日期起</td>
											<td valName="birthdayStart" class="valCell"></td>
											<td class="td-left">出生日期止</td>
											<td valName="birthdayEnd" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">QQ号码</td>
											<td valName="qqNo" class="valCell"></td>
											<td class="td-left">电子邮箱 <span
												class="micon-lg glyphicon glyphicon-envelope pull-right"></span>
											</td>
											<td valName="email" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">联系电话<span
												class="micon-lg glyphicon glyphicon-phone pull-right"></span>
											</td>
											<td valName="telephone" class="valCell"></td>
											<td class="td-left">籍贯国籍</td>
											<td valName="nativePlace" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">工作单位</td>
											<td valName="employUnit" class="valCell"></td>
											<td class="td-left">工作单位地址</td>
											<td valName="employAdress" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">现住址</td>
											<td valName="address" class="valCell"></td>
											<td class="td-left">现住地详细地址</td>
											<td valName="addressDetail" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">出生地</td>
											<td valName="birthCode" class="valCell"></td>
											<td class="td-left">出生地详细住址</td>
											<td valName="birthDetail" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">户籍地</td>
											<td valName="door" class="valCell"></td>
											<td class="td-left">户籍地详细地址</td>
											<td valName="doorDetail" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">民族</td>
											<td valName="nation" class="valCell"></td>
											<td class="td-left">特殊身份</td>
											<td valName="specialIdentity" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">文化程度</td>
											<td valName="culture" class="valCell"></td>
											<td class="td-left">政治面貌</td>
											<td valName="politics" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">宗教信仰</td>
											<td valName="faith" class="valCell"></td>
											<td class="td-left">其他专长</td>
											<td valName="otherspecialty" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">情况说明</td>
											<td colspan="3">
												<div class="alert alert-info">
													<span valName="thingExplain" class="valCell"></span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="td-left">职业</td>
											<td valName="job" class="valCell"></td>
											<td class="td-left">职务</td>
											<td valName="headShip" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">脸型</td>
											<td valName="faceForm" class="valCell"></td>
											<td class="td-left">人员状态</td>
											<td valName="personState" class="valCell"></td>
										</tr>
										<tr>
											<td colspan="6" class="alert-info row-mar0">体貌特征</td>
										</tr>
										<tr>
											<td class="td-left">身高</td>
											<td valName="staturest" class="valCell"></td>
											<td class="td-left">体重</td>
											<td valName="avoirdupois" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">体型</td>
											<td valName="bodilyForm" class="valCell"></td>
											<td class="td-left">血型</td>
											<td valName="bloodType" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">鞋号</td>
											<td valName="shoesSize" class="valCell"></td>
											<td class="td-left">足长</td>
											<td valName="footSize" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">口音</td>
											<td valName="tone" class="valCell"></td>
											<td class="td-left">嗜好</td>
											<td valName="addiction" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">是否会驾驶</td>
											<td valName="ifDrive" class="valCell"></td>
											<td class="td-left">是否经常上网</td>
											<td valName="ifOftenNet" class="valCell"></td>
										</tr>
									</tbody>
								</table>
								<div class="m-ui-title3 mar-top" style="margin-top: 30px;">
									<h2>人员案件关联</h2>
								</div>
								<table style="display: none;" class="table table-sg bjrTable"
									cellspacing="0" width="100%">
									<tbody>
										<tr>
											<td class="td-left" width="15%">涉案类型</td>
											<td width="30%" valName="relation" class="valCell"></td>
											<td class="td-left" width="15%">受害程度</td>
											<td valName="receivePSN" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">受害形式</td>
											<td valName="reportMode" class="valCell"></td>
											<td class="td-left">受害形式2</td>
											<td valName="reportMode2" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">受侵害时间上限</td>
											<td valName="aggrievedTimeLimit" class="valCell"></td>
											<td class="td-left">受侵害时间下限</td>
											<td valName="aggrievedTimeLower" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">损伤及病理特征</td>
											<td valName="damageFeature" class="valCell"></td>
											<td class="td-left">与受害人关系</td>
											<td valName="repnexusdis" class="valCell"></td>
										</tr>
										<tr>
											<td class="td-left">致死伤工具</td>
											<td valName="injuryTools" class="valCell"></td>
											<td class="td-left">致死伤原因</td>
											<td valName="injuryCause" class="valCell"></td>
										</tr>

									</tbody>
								</table>
							</div>
							<div id="tabs-5">
								<div class="m-ui-title3">
								</div>
								<ul id="writLstUl" class="list-news" style="width: 500px;">
								</ul>
							</div>
							<div id="tabs-6">
								<div class="m-ui-title3">
								</div>
								<table id="caseTable"
									class="table table-bordered table-hover m-ui-table-whole"
									cellspacing="0" width="100%">
								</table>
							</div>
							<div id="tabs-9">
								<div class="m-ui-title3">
								</div>
								<ul id="zjblLstUl" class="list-news" style="width: 500px;">
								</ul>
							</div>
						</div>

					</div>
				</div>
			</div>

		<!-- </div>
    </div> -->
    <%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajjk/caseSearch/caseDetail.js"></script>
</html>