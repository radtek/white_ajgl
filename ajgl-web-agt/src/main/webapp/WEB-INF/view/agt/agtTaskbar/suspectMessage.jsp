<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>案管客户端</title>
<script>
var context='<%=request.getContextPath()%>';
var formId='<%=request.getParameter("formId")%>';
</script>
<!-- 换肤  -->
<link rel="icon" type="image/x-icon"
	href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon"
	href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="bookmark" type="image/x-icon"
	href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/agt/custom/default/style/frame.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/agt/custom/default/style/cs.css">
</head>

<body class="cs-bg validform" id="validformHandlingAreaReceipt">
	<div class="cs-content">
		<div class="cs-content-top" id="divMouseMove">
			<h2>嫌疑人信息</h2>
			<a id="btn-close-window" href="####" style="transform: rotate(0deg);"></a>
		</div>
		<div class="cs-content-main">

			<div class="row">
				<div class="col-xs-12">
					<div style="padding-right: 220px;">
						<table class="table table-sg" cellspacing="0" width="100%">
							<tbody>
								<tr>
									<td class="td-left" width="24%">被问讯人：<span
										class="red-star">*</span></td>
									<td><input class="form-control input-sm"
										style="width: 150px" vf-position="1" datatype="*1-26"
										id="byQuestioningPeopleName"></td>
									<td class="td-left" width="17%">性别：<span class="red-star">*</span></td>
									<td>
										<div style="width: 120px">
											<select
												class="form-control input-sm select2-noSearch valiform-keyup"
												id="sex" vf-position="1" datatype="*"></select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="td-left">身份证件号码：<span class="red-star">*</span></td>
									<td><input class="form-control input-sm"
										style="width: 150px" vf-position="1" datatype="*0-26"
										id="byQuestioningPeopleIdentifyNum">
										<button class="btn btn-primary btn-sm search" id="queryPersonId">查看信息</button></td>
									<td class="td-left"><font color="green">#</font>&nbsp;&nbsp;联系方式：</td>
									<td><input class="form-control input-sm"
										style="width: 120px" vf-position="1" datatype="*0-26"
										id="byQuestioningPeopleTelphoneNumber"></td>
								</tr>
								<tr>

									<td class="td-left"><font color="green">#</font>&nbsp;&nbsp;现住址：</td>
									<td colspan="3"><input class="form-control input-sm"
										style="width: 300px" vf-position="1" datatype="*0-33"
										id="byQuestioningPeopleAddress"></td>
								</tr>
								<tr>
									<td class="td-left"><font color="green">#</font>&nbsp;&nbsp;户籍地：</td>
									<td colspan="3"><input class="form-control input-sm"
										style="width: 300px" vf-position="1" datatype="*0-33"
										id="door"></td>

								</tr>
								<tr>
									<td class="td-left">所属案件：</td>
									<td colspan="3" class="selectAboutCaseLst"><div class="input-group"
											style="width: 300px">
											<input style="background-color: transparent"
												class="form-control input-sm" id="aboutCaseLst" readonly><span
												class="input-group-addon"><span class="icon-search"></span></span>
										</div>
										<input type="hidden" id="lawCaseName"></td>

								</tr>
								<tr>
									<td class="td-left">案由：</td>
									<td colspan="3">
										<div style="width: 100px; float: left;">
											<select id="causeOfLawCase"
												class="form-control input-sm select2-noSearch"
												style="width: 100px"></select>
										</div>
										<div style="width: 100px; float: left; margin-left: 10px;"
											class="m-inline">
											<input id="otherCauseOfLawCase" vf-position="1"
												datatype="*0-26"
												class="form-control input-sm valiform-keyup form-val"
												style="display: none; width: 100px; height: 30px;">
										</div>
									</td>
								</tr>
								
								<!-- -$ -->
								<tr>
									<td class="td-left">对应文书：</td>
									<td colspan="3">
										<div id="attach"></div>
										<div id="container" class="upload-control"
											style="display: none"></div>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="m-ui-commitbox">
							<button class="btn btn-primary btn-lg" id="harConfirm">确认</button>
							<button class="btn btn-default btn-lg" id="resBtn">重置</button>
						</div>

						<div class="m-ui-title3 mar-top">
							<h2>前科情况</h2>
						</div>
						<div class="m-ui-table m-ui-table-sm">
							<table id="criminalRecordTable" class="table m-ui-table-no-paginate table-bordered " cellspacing="0">
                            </table>
						</div>
					</div>


				</div>
				<div class="col-xs-2" style="width: 200px; margin-left: -200px;">
					<div class="alert-default font14 text-center">
						 <div
							style="padding: 10px 0; width: 150px; overflow: hidden; margin: 0 auto;">
							<img id="cameraPicture" class="zoomIn"
								src="<%=request.getContextPath()%>/agt/images/man/man-none.png"
								height="180">
							<p style="padding-top: 5px;">人像采集照片</p>
						</div> 
						<div
							style="padding: 10px 0; width: 150px; overflow: hidden; margin: 0 auto;">
							<img id="idCardPicture" class="zoomIn"
								<%-- src="<%=request.getContextPath()%>/agt/images/man/man-sf.png" --%>
								height="180">
							<p style="padding-top: 5px;">身份证照片</p>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<%-- <%@include file="/WEB-INF/base/skin/footPart.jsp"%> --%>
</body>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/agt/agtTaskbar/suspectMessage.js"></script>
<!--关闭按钮旋转-->
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/custom/default/js/jQueryRotate.2.2.js"></script>
		
</html>