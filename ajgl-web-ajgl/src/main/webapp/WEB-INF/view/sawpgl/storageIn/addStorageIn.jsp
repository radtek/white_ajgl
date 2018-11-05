<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 涉案物品入库</title>
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
					<li class="active">新增涉案物品入库单</li>
				</ol>
				<!--悬浮操作层-->
			<div class="fixed-a">
				<div class="m-ui-title1"><h1>涉案物品入库</h1>
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
					   <div class="col-xs-2"> <label class="control-label">入库单编号：</label></div>
					   <div class="col-xs-2">
					   		<input type="text" id="code" class="form-control input-sm" value="" readonly unselectable="on">
					   </div>
					   <div class="col-xs-2"> <label class="control-label">入库时间：<span class="red-star">*</span></label></div>
					   <div id="rkDate" class="input-group laydate col-xs-3"
							style="width: 160px">
							<input type="hidden" class="laydate-fmt dateFmt" value="yyyy-MM-dd HH:mm" /> 
							<input	class="form-control laydate-value valiform-keyup form-val" id="fixed_date" readonly="readonly" datatype="*1-85" nullmsg="请填写入库时间">
							<span class="laydate-value-span input-group-addon m-ui-addon">
								<span class="glyphicon glyphicon-calendar" aria-hidden="true" style="font-size: 16px;"></span>
							</span>
						</div>
					   
					</div>

					<div class="row row-mar">
					    <div class="col-xs-2"> <label class="control-label">对应案件编号：<span class="red-star">*</span></label></div>
					    <div class="col-xs-5">
					    	<input type="text" id="caseCode"  readonly unselectable="on" style="width:90%;height:30px;" class="form-control input-sm valiform-keyup form-val" value="" >
						</div>
						<div class="col-xs-3">				    
							<button id="selectCaseCode" class="btn btn-primary btn-sm">查询案件编号</button>
							<button id="caseCodeConfirm" class="btn btn-primary btn-sm" style="display:none;">确定</button>
							<button id="caseCodeUpdate" class="btn btn-primary btn-sm" style="display:none;">修改</button>
						</div>
					</div>
					<div class="row row-mar">
					    <div class="col-xs-2"> <label class="control-label">案件名称：</label></div>
					    <div class="col-xs-6"><input type="text" id="caseName" class="form-control input-sm" value="" unselectable="on"  readonly unselectable="on">
						</div>
					</div>
					<div class="row row-mar">
					   <div class="col-xs-2"> <label class="control-label">办案民警：</label></div>
					    <div class="col-xs-6"><input type="text" id="polices" class="form-control input-sm" value="" readonly unselectable="on"></div>
					</div>
					<div class="row row-mar">
					   <div class="col-xs-5" style="width:370px;"> <label class="control-label">本次入库涉案物品对应的嫌疑人/物品持有人：<span class="red-star">*</span></label></div>
					   <div class="col-xs-5">
		                    <div class="row m-icheck-group" id="suspectRadio" style="margin-bottom:10px;">
		                    </div>
			           </div>
					</div>
					<div class="row row-mar">
					      <div class="col-xs-2"> <label class="control-label">对应文书：</label></div>
					      <div class="col-xs-10">
					      	 <ul class="m-list-group" id="papers">
                             </ul>
                          </div>
					</div>
					<div class="row row-mar">
		                   <div class="col-xs-2"> <label class="control-label">备注：</label></div>
		                   <div class="col-xs-6"><textarea class="form-control input-sm valiform-keyup form-val" id="remarkBig" rows="3" datatype="*0-85" errormsg="备注不可超过85个字符" style="resize:none;"></textarea></div>
					</div>
					
					<div id="qrcode" style="position:absolute; right:10px; bottom:10px; width:170px;">
					</div>
				</div>
			 
			<div class="m-ui-title3">
				<h2>涉案物品<button class="btn btn-primary btn-sm" style="display:none;">查看物品图片</button></h2>
			</div>
			
			<div class="m-ui-table" >
		        <table id="sawqTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" style="width:100%">
		            
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
	src="<%=context%>/scripts/sawpgl/storageIn/addStorageIn.js"></script>
</html>