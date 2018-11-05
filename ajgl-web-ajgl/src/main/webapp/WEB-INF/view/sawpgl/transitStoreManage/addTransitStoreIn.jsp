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
	<div id="c-center" class="validform">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>

		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">涉案物品管理</a></li>
					<li><a href="#">暂存物品管理</a></li>
					<li class="active">新增暂存物品入库单</li>
				</ol>
				<!--悬浮操作层-->
			<div class="fixed-a">
				<div class="m-ui-title1"><h1>新增暂存物品入库</h1>
					<div class="m-ui-caozuobox">
						<button id="print" class="btn btn-success" style="display: none">打印</button>
						<button id="save" class="btn btn-primary">保存</button>
						<button id="cancel" class="btn btn-default">取消</button>
						<input type="hidden" id="formId" value="" /> 
					</div>
				</div>
			</div>
			<!--悬浮操作层-->
			<div class="alert" style="width:900px; padding-top:20px; margin-top:20px; position:relative; background-color:#f8f8f8; border-color:#ddd;">
					<div class="row row-mar">
						<div class="col-xs-2"> <label class="control-label">办案区使用单：<span class="red-star">*</span></label></div>
						  <div class="col-xs-2 input-group"><input id="makingCode" type="text" class="form-control input-sm" value="">
                				<span class="input-group-addon" id="selectCode"><span class="glyphicon glyphicon-search"></span></span>
                		  </div>
						<div class="col-xs-3"> <label class="control-label">办案民警：</label></div>
					    <div class="col-xs-3"><input type="text" id="polices" class="form-control input-sm" value="" readonly></div>
					</div>
					<div class="row row-mar">
					   <div class="col-xs-2"> <label class="control-label">入库单编号：</label></div>
					   <div class="col-xs-2">
					   		<input type="text" id="storageInCode" class="form-control input-sm" value="" readonly>
					   </div>
					   <div class="col-xs-5"> <label class="control-label">入库时间：<span class="red-star">*</span></label></div>
					   <div id="rkDate" class="input-group laydate col-xs-5"
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
					    <div class="col-xs-2">
					    	<input type="text" id="caseCode"  readonly style="width:100%;height:30px;" class="form-control input-sm valiform-keyup form-val" value="" >
						</div>
						<div class="col-xs-5"> <label class="control-label">对应嫌疑人/物品持有人：</label></div>
            				<div class="col-xs-3"><input id="objectOwnerPerson" type="text" class="form-control input-sm" value="" disabled>
            			</div>
					</div>
					<div class="row row-mar">
					    <div class="col-xs-2"> <label class="control-label">案件名称：</label></div>
					    <div class="col-xs-4"><input type="text" id="caseName" class="form-control input-sm" value="" readonly>
						</div>
						 <div class="col-xs-3"> <label class="control-label">嫌疑人身份证号：</label></div>
			           		 <div class="col-xs-3"><input type="text" id="idCard" class="form-control input-sm" value="" disabled>
			            </div>
					</div>
					<div class="row row-mar">
		                   <div class="col-xs-2"> <label class="control-label">备注：</label></div>
		                   <div class="col-xs-6"><textarea class="form-control input-sm valiform-keyup form-val" id="remark" rows="3" datatype="*0-85" errormsg="备注不可超过85个字符" style="resize:none;"></textarea></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2"> <label class="control-label">最新修改人：</label></div>
			            <div class="col-xs-2"><input type="text" id="modifyPerson" class="form-control input-sm" value="" disabled></div>
			            <div class="col-xs-2"> <label class="control-label">最新修改时间：</label></div>
			            <div class="col-xs-3 input-group"><input type="text"id="modifyTime" class="form-control input-sm" value="" disabled> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div>
			        </div>
			        <div id="qrcode" style="position:absolute; right:10px; bottom:10px; width:170px;">
				</div>
				</div>
			 
				
			<div class="m-ui-title3" ><h2>暂存物品清单：</h2></div>
			 <div class="row row-mar" style="margin-bottom: 5;">
        <div class="col-xs-5" style="display:none;">
            <div class="col-xs-3"> <label class="control-label">所在保管区：<span class="red-star">*</span></label></div>
            <div class="col-xs-4"><input type="text" id="transitStoreArea" class="form-control input-sm" value="" disabled></div>
        </div>
        <div class="col-xs-5" style="display:none;">
            <div class="col-xs-4"> <label class="control-label"> 储物箱：<span class="red-star">*</span></label></div>
            <div class="col-xs-4" id><input type="text" id="transitStoreLocker" class="form-control input-sm" value="" disabled></div>
        </div>
    </div>
			<div class="m-ui-table" >
		        <table id="makingTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" style="width:100%">
		            
		        </table>
		    </div>
			
			<!--结束-->
		</div>
	</div>
</div>
 
    <input type="hidden" id="policeNumber1" value="">
	<input type="hidden" id="policeNumber2" value="">

	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/util/qrcode.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/transitStoreManage/addTransitStoreIn.js"></script>
</html>