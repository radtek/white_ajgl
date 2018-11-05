<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>

</head>
<body >
	<input type="hidden" id="policeName" value="<%=request.getParameter("policeName")%>">
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
				<div class="m-ui-layer-box validform" id="validformId">
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include
							file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						

<div class="m-ui-title3 mar-top"><h2>维护随身物品日常管理</h2></div>
<div class="row">

</div>
<div class="alert-default font14">
<div class="row row-mar" style="min-height: 101px">
<div class="col-xs-7">
<h2 class="pull-left font20" style="width:60px; padding-top:30px;"></h2>
<div class="box-storage-list pull-left">
<ul id="boxUl"></ul>
</div></div>
<div class="col-xs-4 pull-right" style="width:340px;">
<p id="policeP" class="row-mar mar-top"><span class="red-star">*</span>办案民警：</p>
</div>
</div> 
</div>
<div class=" pull-right" style="margin-top:-40px;">
<button id="returnObjBtn" class="disabledBtn btn btn-primary btn-sm">立即返还</button>
<button id="keepBtn" class="disabledBtn btn btn-primary btn-sm">移交暂存</button>
<button id="detainBtn" class="disabledBtn btn btn-primary btn-sm">移交扣押</button>
<button id="temporaryBtn" class="disabledBtn btn btn-success btn-sm">临时取出</button></div>
<div class="m-ui-title3"><h2 class="m-bold">在箱物品</h2></div>
<table id="goodsTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
    </table>
<div class="m-ui-title3 mar-top"><h2 class="m-bold pull-left">临时取出物品</h2><div class="pull-right"><button id="temporaryBackBtn" class="disabledBtn btn btn-success btn-sm">物品归还</button></div></div>
<table id="temporaryTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
    </table>  
<div class="m-ui-title3 mar-top"><h2 class="m-bold pull-left">移交扣押物品</h2><div class="pull-right"><button id="detainBackBtn" class="disabledBtn btn btn-success btn-sm"><span class="icon-reply"></span></button></div></div>
<table id="detainTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
    </table> 
     <div class="m-ui-title3 mar-top"><h2 class="m-bold">移交暂存物品 </h2><div class="pull-right"><button id="keepBackBtn" class="disabledBtn btn btn-success btn-sm"><span class="icon-reply"></span></button></div></div>
<table id="keepTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
</table> 
    <div class="m-ui-title3 mar-top"><h2 class="m-bold pull-left">立即返还物品</h2><div class="pull-right"><button id="returnObjBackBtn" class="disabledBtn btn btn-success btn-sm"><span class="icon-reply"></span></button></div></div>
<table id="returnObjTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
</table>  


						
						
						<div class="color-gray text-right">
							最新修改人：<span id="modifyPeopleName"></span> <span
								style="margin-left: 50px;">最新修改时间：<span id="updateTime"></span></span>
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
						<div class="m-ui-commitbox">
							<button class="btn btn-primary btn-lg" id="confirm"
								style="width: 100px;">保存</button>
							<button class="btn btn-bordered btn-lg" id="cancel">取消</button>
							<button id="printls" class="print btn btn-info btn-lg" style="display: none"><span class="icon-print micon-lg mar-right"></span>打印本次物品临时取出</button>
							<button id="printyj" class="print btn btn-info btn-lg" style="display: none"><span class="icon-print micon-lg mar-right"></span>打印本次物品移交取出</button>
							<button class="btn btn-bordered btn-lg" style="display: none"
								id="return">返回</button>
						</div>
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
	src="<%=context%>/scripts/ajgl/carryGoodsInfo/updateCarryGoodsReturnRecord.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>