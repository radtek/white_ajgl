<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformPersonCheckRecord" class="validform">
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
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="m-ui-layer-box">
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include
							file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						<!-----信息采集情况----->
						<div class="m-ui-title3 mar-top"><h2>维护信息采集情况</h2></div>
						<table id="dataTable" class="validform table table-sg" cellspacing="0" width="100%">
							<tbody id="infoTbody">
								<tr>
									<td class="td-left" width="100">信息采集：<span class="red-star">*</span></td>
									<td id="isCollectVerify" width="20%">
										<input id="isCollectY" type="radio" class="icheckradio" name="isCollect" value="<%= Constant.SF_S%>">&nbsp;&nbsp; 
										<span style="margin-right: 20px;">是</span>
										<input id="isCollectN" type="radio" class="icheckradio" name="isCollect" value="<%= Constant.SF_F%>">&nbsp;&nbsp;否
									</td>
									<td class="td-left" width="120">采集项目：
										<span class="red-star">*</span>
									</td>
									<td id="collectProjectVerify" colspan="4">
										<input id="fingerprintCheckbox" type="checkbox" class="icheckbox" name="collectProject" value="指纹">&nbsp;&nbsp;
										<span style="margin-right: 20px;">指纹</span>
										<input id="sanguisCheckbox" type="checkbox" class="icheckbox" name="collectProject" value="血液">&nbsp;&nbsp;
										<span style="margin-right: 20px;">血液</span> 
										<input id="urineCheckbox" type="checkbox" class="icheckbox" name="collectProject" value="尿液">&nbsp;&nbsp;
										<span style="margin-right: 20px;">尿液</span> 
										<input id="otherCheckbox" type="checkbox" class="icheckbox" name="collectProjectOther" value="其他">&nbsp;&nbsp;
										<span style="margin-right: 20px;">其他</span>							
										<input id="collectProjectOther" type="text" style="width: 100px;display:none;">
									</td>
								</tr>
								<tr>
									<td class="td-left" width="100">信息入库：
										<span class="red-star">*</span>
									</td>
									<td id="infoIntoStringVerify" width="20%">
										<input id="infoIntoStringY" type="radio" class="icheckradio" name="infoIntoString" value="<%= Constant.SF_S%>">&nbsp;&nbsp;
										<span style="margin-right: 20px;">是 </span> 
										<input id="infoIntoStringN" type="radio" class="icheckradio" name="infoIntoString" value="<%= Constant.SF_F%>">&nbsp;&nbsp;否
									</td>
									<td class="td-left" width="120">核查比对：
										<span class="red-star">*</span>
									</td>
									<td id="inspectComparisonVerify" colspan="4">
										<input type="radio" class="icheckradio" id="inspectComparisonY" name="inspectComparison" value="<%= Constant.SF_S%>">&nbsp;&nbsp;
										<span style="margin-right: 20px;">是 </span> 
										<input type="radio" class="icheckradio" id="inspectComparisonN" name="inspectComparison" value="<%= Constant.SF_F%>">&nbsp;&nbsp;否
									</td>
								</tr>
								<tr>
              <td class="td-left">QQ号：<span class="red-star">*</span></td>
              <td colspan="6" id="qqTd"><input id="qqNum" class="form-control input-sm valiform-keyup form-val" vf-position="1" datatype="*1-26" style="width:150px;"><button id="newQqNum" class="btn btn-default btn-sm"><span class="icon-plus"></span></button><span id="tempGroup" class="removeSpan" style="display: none; margin-left: 20px;"><input class="form-control input-sm" style="width:150px;"><button class="btn btn-default btn-sm trashInput"><span class="icon-trash"></span></button></span></td>
          </tr>
            <tr>
              <td class="td-left">微信：<span class="red-star">*</span></td>
              <td colspan="6" id="wxTd"><input id="weiXinNum" class="form-control input-sm valiform-keyup form-val" vf-position="1" datatype="*1-26" style="width:150px;"><button id="newWeixinNum" class="btn btn-default btn-sm"><span class="icon-plus"></span></button></td>
          </tr>
           <tr id="firstInfoTr" class="phoneInfo">
            <td class="td-left">手机号：<span class="red-star">*</span></td>
            <td><input class="form-control input-sm valiform-keyup form-val phoneNum " vf-position="1" datatype="*1-26" style="width:150px;"></td>
            <td  class="td-left">IMEI：</td>
            <td colspan="2"><input class="form-control input-sm valiform-keyup form-val phoneIMEI" vf-position="1" datatype="*1-26" style="width:150px;"></td>
            <td  class="td-left">MAC：</td>
            <td><input class="form-control input-sm valiform-keyup form-val phoneMAC" vf-position="1" datatype="*1-26" style="width:150px;"><button id="newPhoneInfo" class="btn btn-default btn-sm"><span class="icon-plus"></span></button></td>
          </tr>
          <tr id="tempTr" class="removeTr phoneInfo" style="display:none;">
            <td class="td-left">手机号：<span class="red-star">*</span></td>
            <td><input class="form-control input-sm" style="width:150px;"></td>
            <td  class="td-left">IMEI：</td>
            <td colspan="2"><input class="form-control input-sm" style="width:150px;"></td>
            <td  class="td-left">MAC：</td>
            <td><input class="form-control input-sm" style="width:150px;"><button class="btn btn-default btn-sm trashTr"><span class="icon-trash"></span></button></td>
          </tr>
          <tr>
          	<td class="td-left" colspan="7" style="color: red">*如果未采集到新信息，请填写“无”。</td>
          </tr>
							</tbody>
						</table>
						<div id="newUpdateRecord" class="color-gray text-right">
							最新修改人：<span id="modifyPeopleName"></span>
							<span style="margin-left: 50px;">最新修改时间：<span id="updateTime"></span></span>
						</div>


						<!--操作历史-->
						<!--<div class="m-ui-title3 mar-top">
							<h2>操作历史</h2>
						</div>

						<div class="od-expand-btn" id="od-expand-btn-2"
							onClick="document.getElementById('od-expand-content-2').style.display='block';document.getElementById('od-expand-btn-2').style.display='none'">
							<a href="###"><span>显示操作历史</span></a>
						</div>
						<div id="od-expand-content-2" style="display: none;">
							<div class="m-ui-table m-ui-table-sm">
								<table id="ncisOperateRecordTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>序号</th>
											<th>操作内容</th>
											<th>操作人</th>
											<th>操作时间</th>
											<th>说明</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
							<div class="od-expand-btn od-fold-btn"
								onClick="document.getElementById('od-expand-content-2').style.display='none';document.getElementById('od-expand-btn-2').style.display='block'">
								<a href="###"><span>隐藏操作历史</span></a>
							</div>
						</div>-->
						<!--操作历史-->
						<div class="m-ui-commitbox">
							<button id="cisConfirm" class="btn btn-primary btn-lg" style="width:100px;">保存</button>
							<button id="cisCancel" class="btn btn-bordered btn-lg cisBack">取消</button>
							<button id="cisPrint" class="btn btn-bordered btn-lg" style="display:none;">打印</button>
							<button id="cisBack" class="btn btn-bordered btn-lg cisBack" style="display:none;">返回</button>
						</div>   
						<!-----离开办案区情况end----->
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/collectInfoSituation/newOrUpdateCollectInfoSituation.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>