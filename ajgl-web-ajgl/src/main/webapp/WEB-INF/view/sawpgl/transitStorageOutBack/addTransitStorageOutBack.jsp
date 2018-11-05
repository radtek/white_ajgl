<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 暂存物品出库返还</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformTransitStorageOut" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">

				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>暂存物品管理</li>
					<li>新增暂存物品出库返还单</li>
				</ol>

				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>新增暂存物品出库返还单</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-primary" id="save">保存</button>
							<button class="btn btn-default" id="cancel">取消</button>
							<input type="hidden" id="formId" value="" /> 
						</div>
					</div>
				</div>
	<!--悬浮操作层-->
	<div class="alert"
		style="width: 900px; padding-top: 20px; margin-top: 20px; position: relative; background-color: #f8f8f8; border-color: #ddd;">

        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">办案区使用单：<span class="red-star">*</span></label></div>
            <div class="col-xs-3 input-group"><input type="text" id="makingCode" class="form-control input-sm" value="">
            <span class="input-group-addon" id ="selectCode"><span class="glyphicon glyphicon-search"></span></span></div>

            <div class="col-xs-1" style="display:none;"><button class="btn btn-primary btn-sm"></button></div>
            <div class="col-xs-2"> <label class="control-label">办案民警：</label></div>
            <div class="col-xs-3 m-label-right"><span id="polices"></span></div>
        </div>
        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">出库返还单编号：</label></div>
            <div class="col-xs-3 input-group"><input id="storageOutBackCode" type="text" class="form-control input-sm" value="" readonly></div>
            <div class="col-xs-3"> <label class="control-label">出库返还时间：<span class="red-star">*</span></label></div>
		   <div id="outBackDate" class="input-group laydate col-xs-5"
				style="width: 225px">
				<input type="hidden" class="laydate-fmt dateFmt" value="yyyy-MM-dd HH:mm" /> 
				<input	class="form-control laydate-value valiform-keyup form-val" id="fixed_date" readonly="readonly" datatype="*1-85" nullmsg="请填写入库时间">
				<span class="laydate-value-span input-group-addon m-ui-addon">
					<span class="glyphicon glyphicon-calendar" aria-hidden="true" style="font-size: 16px;"></span>
				</span>
			</div>
        </div>
        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">对应案件编号：</label></div>
            <div id="caseCode" class="col-xs-3 input-group m-label-right"></div>
            <div class="col-xs-3"> <label class="control-label">案件名称：</label></div>
            <div  id="caseName" class="col-xs-4 m-label-right"></div>
        </div>

        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">嫌疑人身份证号：</label></div>
            <div id="suspectId" class="col-xs-3 m-label-right"></div>
            <div class="col-xs-3"> <label class="control-label">对应嫌疑人/物品持有人：</label></div>
            <div id="suspectName" class="col-xs-3 m-label-right"></div>
        </div>

        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">物品领取人：<span class="red-star">*</span></label></div>
            <div class="col-xs-3"><input id="receivePerson" type="text" class="form-control input-sm"></div>
        </div>

        <div class="row row-mar">
            <div class="col-xs-2">
				<label class="control-label">附件：<span class="red-star" id="containerStar">*</span></label>
			</div>
			<div class="col-xs-8 upload-control" id="container"></div>
        </div>

        <div class="row row-mar">
            <div class="col-xs-2"> <label class="control-label">备注：</label></div>
            <div class="col-xs-6"><textarea id="remark" class="form-control input-sm" rows="3"></textarea></div>
        </div>
        <div id="qrcode" style="position:absolute; right:10px; bottom:10px; width:170px;">
		</div>
    </div>


	<div class="m-ui-title4" ><h2>暂存物品清单：</h2></div>
	    <div class="row row-mar">
	        <div class="col-xs-3"> 所在物证保管区：</div>
	        <div class="col-xs-3 m-label-right" id="inStorageArea"></div>
	        <div class="col-xs-3">储物箱:</div>
	        <div class="col-xs-3 m-label-right" id="storageRack" ></div>
	    </div>
	<div class="m-ui-table">
		<table id="outBackTable"
			class="table table-bordered table-hover m-ui-table-whole"
			cellspacing="0" width="100%">
		</table>
	</div>
	<!--结束-->
	</div>
	</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/transitStorageOutBack/addTransitStorageOutBack.js"></script>
</html>