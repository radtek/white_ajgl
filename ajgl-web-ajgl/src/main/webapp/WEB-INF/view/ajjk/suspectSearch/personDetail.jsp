<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 案件监控</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformId" class="validform m-ui-layer-body">
	<%-- <%@include file="/WEB-INF/base/skin/topPart.jsp"%>--%>
	<input type="hidden" id="dataId" 
		value=<%=request.getParameter("dataId")%>>
	<%-- <div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-xqck.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content"> --%>
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>案件监控管理</li>
					<li>查看人员基本信息</li>
				</ol>


				<div class="row">
					<button id="back" class="btn btn-primary">
						<span class="glyphicon glyphicon-menu-left mar-right"></span>返回
					</button>
				</div>
				<h2 class="font24 text-center" style="margin-bottom: 20px;">人员基本信息</h2>
				<div style=" border: 1px solid #999; margin: 15px auto">
					<table class="table table-sg personForXYR" cellspacing="0" width="100%">
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
				</div>
				<!--结束-->

		<!-- 	</div>
		</div>
	</div> -->
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajjk/suspectSearch/personDetail.js"></script>
</html>