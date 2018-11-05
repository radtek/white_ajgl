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
						  <div style="display: none">
							<button class="btn btn-success finishReceipt finishBtnFlag" style="display: none;">完成本次问讯</button>
							<button class="btn btn-primary printBtnFlag" style="display: none;" id="printAll">打印使用单</button>
							<button class="btn btn-primary printBtnFlag" style="display: none;" id="unbound">解绑手环</button>
     					 </div>
							<div class="btn-group dropup">
							  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">打印二维码</button>
							  <ul class="dropdown-menu" style="top: 100%; bottom: auto;">
							    <li><a href="###" id="printLJFH"><span class="icon-qrcode micon-lg mar-right"></span>立即返还</a></li>
							    <li><a href="###" id="printYJKY"><span class="icon-qrcode micon-lg mar-right"></span>移交扣押</a></li>
							    <li><a href="###" id="printYJZC"><span class="icon-qrcode micon-lg mar-right"></span>移交暂存</a></li>
							  </ul>
							</div>
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
						<div class="row" style="padding: 10px; text-align: right;">
						<button id="printls" class="print btn btn-info btn-lg" ><span class="icon-print micon-lg mar-right"></span>打印本次物品临时取出</button>
							<button id="printyj" class="print btn btn-info btn-lg" ><span class="icon-print micon-lg mar-right"></span>打印本次物品移交取出</button>
							<button id="updateCarryGoodsReturnRecord"  class="btn btn-info modifyBtnFlag" style="display: none;height:30px;">
								<span class="glyphicon glyphicon-edit micon-lg mar-right"></span>维护随身物品返还情况
							</button>
						</div>
						<div class="m-ui-title3"><h2 class="m-bold">在箱物品</h2></div>
						<table id="goodsTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
						</table>
						<div class="m-ui-title3 mar-top"><h2 class="m-bold">临时取出物品</h2></div>
						<table id="temporaryTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
						</table>  
						<div class="m-ui-title3 mar-top"><h2 class="m-bold">移交扣押物品</h2></div>
						<table id="detainTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
						</table> 
						<div class="m-ui-title3 mar-top"><h2 class="m-bold">移交暂存物品</h2></div>   
						<table id="keepTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
						</table>
						<div class="m-ui-title3 mar-top"><h2 class="m-bold">立即返还物品</h2></div>
						<table id="returnObjTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
						</table>    
						<div class="color-gray text-right">
							最新修改人：<span id="modifyPeopleName"></span>
							<span style="margin-left: 50px;">最新修改时间：<span id="updateTime"></span></span>
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
	src="<%=context%>/scripts/ajgl/carryGoodsInfo/carryGoodsReturnRecord.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>