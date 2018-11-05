<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 讯（询）问室使用记录列表</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformId" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajgl.jsp"%>
		<div id="c-center-right">

<div id="c-center-right-content">

<ol class="breadcrumb m-ui-breadcrumb">
  <li>办案区管理</li>
   <li>办案区督察</li>
   <li>讯（询）问室使用记录列表</li>
</ol>
<!--整体查询板块--begin-->
<div class="basic-query-out">
<div class="basic-query"><input type="text" class="form-control input-sm" id="simpleCode" value="使用单编号模糊查询" onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"  onFocus="if(value==defaultValue){value='';this.style.color='#000'}" style="color:#aaa;"><button class="btn btn-primary btn-sm queryRecord">查询</button><button class="advanced-btn">展开高级查询</button></div>
</div>


<div class="m-ui-searchbox  advanced-query">
<div class="m-ui-searchbox-con">

<div class="row row-mar">
<div id="accessDate" class="col-xs-6 dateRange">
									<input type="hidden" id="dtfmt" class="dateFmt"
										value="yyyy-MM-dd" />
      <div class="col-xs-3"> <label class="control-label">开始使用日期：</label></div>
      <div class="col-xs-3 input-group">
		<input type="text" id="allocationDate" date-pos="bottom"
				class="laydate-start form-control input-sm"
				readonly="readonly"> <span
				class="laydate-start-span input-group-addon m-ui-addon">
				<span class="glyphicon glyphicon-calendar"
				aria-hidden="true"></span>
			</span>
	  </div>
      <div class="col-xs-3 to">——</div>
      <div class="col-xs-3 input-group">
		 <input type="text" id="allocationDate"
				class="laydate-end form-control input-sm"
				readonly="readonly"> <span
				class="laydate-end-span input-group-addon m-ui-addon">
				<span class="glyphicon glyphicon-calendar"
				aria-hidden="true"></span>
			</span>
	 </div>
</div>
<div class="col-xs-3">
   <div class="col-xs-6"> <label class="control-label">所属单位：</label></div>
   <div class="col-xs-6 input-group">
		<input id="unit" type="hidden" />
		<input id="unitName" type="text" readonly class="form-control input-sm selectTreeUnit valiform-keyup form-val" datatype="*0-25">
		<span class="input-group-addon selectTreeUnit">
			<span class="glyphicon glyphicon-search"></span>
		</span>
	</div>
</div>
<div class="col-xs-3">
   <div class="col-xs-6">
		<label class="control-label">讯（询）问室：</label>
	</div>
	<div class="col-xs-6">
		<select class="form-control select2-noSearch allowClear" id="roomLst"></select>
	</div>
</div>
</div>

<div class="row row-mar">
<div class="col-xs-3">
   <div class="col-xs-6"> <label class="control-label">被讯（询）问人：</label></div>
    <div class="col-xs-6"><input type="text" id="askPerson" class="form-control input-sm"></div>
</div>
<div class="col-xs-3">
   <div class="col-xs-6"> <label class="control-label">证件号码：</label></div>
    <div class="col-xs-6"><input type="text" id="idNum" class="form-control input-sm"></div>
</div>
<div class="col-xs-3">
   <div class="col-xs-6"> <label class="control-label">办案民警：</label></div>
    <div class="col-xs-6"><input type="text" id="handlingPolice" class="form-control input-sm"></div>
</div>
<div class="col-xs-3">
   <div class="col-xs-6"> <label class="control-label">使用单编码：</label></div>
    <div class="col-xs-6"><input type="text" id="code" class="form-control input-sm"></div>
</div>
</div>
<div class="row row-mar">
<div class="col-xs-3">
   <div class="col-xs-6"> <label class="control-label">所属案件：</label></div>
     <div class="col-xs-6 input-group"><input type="text" id="lawCase" class="form-control input-sm"> <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span></div>
</div>
<div class="col-xs-3 m-ui-btn-box">
<button class="btn btn-primary btn-sm queryRecord">查询</button>
<button class="btn btn-default btn-sm" id="reset">重置</button>
</div>
</div>


</div>
</div>
<!--查询结束-->


<div class="advanced-btn-box"><button class="advanced-btn-up">收起高级查询</button></div>

<!--整体查询板块--end-->




<!--悬浮操作层-->
<div class="fixed-a">
<div class="m-ui-title1"><h1>讯（询）问室使用记录</h1><div class="m-ui-caozuobox" style="top:3px">
</div></div>
</div>
<!--悬浮操作层-->

<div id="c-center-right-content-block">
	<div class="m-ui-table" style="width:100%; overflow:auto;">
        <table id="askRoomUseRecordTable" style="width:1800px" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" >
            
        </table>
    </div>
</div>
<!--结束-->
</div>
</div>

</div>
    <div id="menuContent" class="ztree-MenuContent">
		<input type="text" id="keySelect" style="margin-bottom:5px;" class="form-control input-sm" />
		<ul id="ztree-unitSelect" class="ztree" style="width:200px; height: 150px;"></ul>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaSupervision/askRoomUseRecord.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>