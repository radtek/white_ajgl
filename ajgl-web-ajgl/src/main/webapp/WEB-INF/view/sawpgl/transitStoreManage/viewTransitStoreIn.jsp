<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>涉案物品管理 – 暂存物品入库</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>

		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">涉案物品管理</a></li>
					<li><a href="#">暂存物品管理</a></li>
					<li class="active">暂存物品入库单详情</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>暂存物品入库单详情</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-default" id="refresh">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button id="print" class="btn btn-success">打印</button>
							<button id="cancel" class="btn btn-default">返回</button>
							<input type="hidden" id="formCode" value="${code}" />
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
	<div class="alert"
		style="width: 1000px; padding-top: 20px; margin-top: 20px; position: relative; background-color: #f8f8f8; border-color: #ddd;">
		<div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">办案区使用单：</label></div>
            <div class="col-xs-2 m-label-right">${tempStorageInInfoBean.harCode}</div>
            <div class="col-xs-4"> <label class="control-label">办案民警： </label></div>
            <div class="col-xs-3 m-label-right">${tempStorageInInfoBean.solvePolice}</div>
        </div>
        <div class="row row-mar">

            <div class="col-xs-2"> <label class="control-label">入库单编号：</label></div>
            <div id="code" class="col-xs-2 m-label-right">${tempStorageInInfoBean.storageInCode}</div>
            <div class="col-xs-4"> <label class="control-label">入库时间： </label></div>
            <div class="col-xs-3 m-label-right">${tempStorageInInfoBean.storageInDateTime}</div>
        </div>

        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">对应案件编号：</label></div>
            <div class="col-xs-2 m-label-right">${tempStorageInInfoBean.caseCode}</div>
            <div class="col-xs-4"> <label class="control-label">对应嫌疑人/物品持有人： </label></div>
            <div class="col-xs-3 m-label-right">${tempStorageInInfoBean.objectOwnerPerson}</div>
        </div>

        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">案件名称：</label></div>
            <div class="col-xs-4 m-label-right">${tempStorageInInfoBean.caseName}</div>
            <div class="col-xs-2"> <label class="control-label">嫌疑人身份证号：</label></div>
            <div class="col-xs-3 m-label-right">${tempStorageInInfoBean.idCard}</div>
        </div>

        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">备注：</label></div>
            <div class="col-xs-6 m-label-right">${tempStorageInInfoBean.remark}</div>
        </div>

			<div id="qrcode"
				style="position: absolute; right: 10px; bottom: 10px; width: 170px;">
			</div>
		</div>
				
		<div class="m-ui-title3" ><h2>暂存物品清单：</h2></div>
	    <div class="row">
	        <div class="col-xs-5">
	            <div class="col-xs-3"> <label class="control-label"> 所在保管区：</label></div>
	            <div class="col-xs-4 m-label-right">${tempStorageInInfoBean.inSaveSelfList[0].areaBean.address}</div>
	        </div>
	        <div class="col-xs-5">
	            <div class="col-xs-4"> <label class="control-label"> 储物箱：</label></div>
	            <div class="col-xs-4 m-label-right">${tempStorageInInfoBean.inSaveSelfList[0].address}</div>
	        </div>
	    </div>
		
		<div class="m-ui-table">
			<table id="zcwpInTable"
				class="table table-bordered table-hover m-ui-table-whole"
				cellspacing="0" style="width: 100%">

			</table>
		</div>

				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/transitStoreManage/viewTransitStoreIn.js"></script>
</html>