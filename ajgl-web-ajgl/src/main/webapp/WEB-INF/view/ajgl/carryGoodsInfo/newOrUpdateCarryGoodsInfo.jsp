<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
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
				<div class="m-ui-layer-box" >
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						<div class="m-ui-title3 mar-top"><h2>维护随身物品保管记录</h2></div>
<div class="row pull-right" style=" height:40px;margin-top:-25px">
<button id="hdCameraAdd" disabled class="btn btn-primary btn-sm"><span class="icon-camera mar-right"></span>启动高拍仪</button>
<button id="getBox" class="btn btn-bordered btn-sm">存入</button>
<button id="addRow" class="btn btn-bordered btn-sm"><span class="icon-plus mar-right"></span>增行</button>
</div>
<table id="goodsTable" class="validform table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
   </table>
   
<div class="row-mar mar-top color-gray">
最新修改人：<span id="modifyPeopleName"></span>   <span style="margin-left:50px;">最新修改时间：<span id="updateTime"></span></span>
</div>                 
<object id="iVideo" style="display:none" classid="clsid:8CAA584A-AC84-445B-89D6-D4BD455EAF96"></object>


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
							<button class="btn btn-primary btn-lg" id="confirm" style="width: 100px;">保存</button>
							<button class="btn btn-bordered btn-lg" id="cancel">取消</button>
							<button class="btn btn-bordered btn-lg" style="display: none" id="print">打印</button>
							<button class="btn btn-bordered btn-lg" style="display: none" id="return">返回</button>
						</div>
					</div>
					<!-----随身物品检查记录end----->
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" value="" id="goodsId">
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript">
	var askRoomId = "${param.askRoomId}";
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/carryGoodsInfo/newOrUpdateCarryGoodsInfo.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/carryGoodsInfo/hdDocumentCamera.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>