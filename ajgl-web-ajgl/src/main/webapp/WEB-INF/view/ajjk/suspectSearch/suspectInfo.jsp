<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 案件监控</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformId" class="validform m-ui-layer-body">
	<div style="display: none">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%> 
	</div>
	<input type="hidden" id="dataId"
		value=<%=request.getParameter("dataId")%>>
	<!-- <div id="c-center"> -->
		<div style="display: none">
		 <%@include file="/WEB-INF/base/skin/leftPart-xqck.jsp"%>
	    </div>
		<!-- <div id="c-center-right"> -->
			<!-- <div id="c-center-right-content"> -->
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>案件监控管理</li>
					<li>涉案嫌疑人全流程监控查看</li>
				</ol>

				<!--整体查询板块--end-->

				<div class="row">
					<button id="back" class="btn btn-primary" style="display: none;">
						<span class="glyphicon glyphicon-menu-left mar-right"></span>返回
					</button>
					<button id="refresh" class="btn btn-primary">
						<span class="glyphicon glyphicon-refresh mar-right"></span>刷新
					</button>
				</div>
				<div class="row">
					<div class="col-xs-4" >
							<div style="padding-left: 20px;">
								<div class="m-ui-title3">
									<h2>人员追踪</h2>
								</div>
								<div class="history-content history-content2  history-content-lg" id="personTrace">
								</div>
							</div>
						</div>
					<div class="col-xs-8">
						<h2 class="font24 text-center" style="margin-bottom: 20px;">
							<span valName="name" class="valCell"></span><span class="state state-red2 mar-left" id="personState"></span><span
								class="state state-green1 mar-left" id="dispose"></span>
						</h2>
						<table class="table table-sg" cellspacing="0" width="100%">
							<tbody>
								<tr>
									<td width="15%" class="td-left">性别</td>
									<td width="30%" valName="sex" class="valCell"></td>
									<td width="15%" class="td-left">绰号</td>
									<td width="30%" valName="nickName" class="valCell"></td>
								</tr>
								<tr>
									<td class="td-left">出生日期</td>
									<td valName="birthdayStart" class="valCell"></td>
									<td class="td-left">身份证号</td>
									<td valName="idcardNo" class="valCell"></td>
								</tr>
								<tr>
									<td class="td-left">身高</td>
									<td valName="staturest" class="valCell"></td>
									<td class="td-left">体重</td>
									<td valName="avoirdupois" class="valCell"></td>
								</tr>
								<tr>
									<td class="td-left">联系电话</td>
									<td valName="telephone" class="valCell"></td>
									<td class="td-left">籍贯地址</td>
									<td valName="doorDetail" class="valCell"></td>
								</tr>
								<tr>
									<td class="td-left">口音</td>
									<td valName="tone" class="valCell"></td>
									<td class="td-left">现居住地址</td>
									<td valName="addressDetail" class="valCell"></td>
								</tr>
							</tbody>
						</table>
						<p class="text-right">
							<a href="#" class="personDetail">更多人员基本信息&gt;</a>
						</p>
						<div class="m-ui-title3">
							<h2>相关案件</h2>
						</div>
						<table id="suspectInfo" class="table table-bordered table-hover m-ui-table-whole"
							cellspacing="0" width="100%">
						</table>
					</div>
					
				</div>
				<!--结束-->

			<!-- </div> -->
		<!-- </div> -->
	<!-- </div> -->
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajjk/suspectSearch/suspectInfo.js"></script>
</html>