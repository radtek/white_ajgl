<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
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
				<li>基本档案</li>
				<li>影音刻录</li>
			</ol>
		  <!--整体查询板块--begin-->
			<div class="m-ui-searchbox">
			<div class="m-ui-searchbox-con">
					<div class="row row-mar">
						<div class="col-xs-4">
							<div class="col-xs-6">
								<label class="control-label">办案区使用单编号：</label>
							</div>
							<div class="col-xs-6">
								<input id="code" type="text" class="form-control input-sm"
									value="模糊查询"
									onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
									onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
									style="color: #aaa;">
							</div>
						</div>
						<div class="col-xs-4">
							<div class="col-xs-6">
								<label class="control-label">使用房间：</label>
							</div>
							<div class="col-xs-6">
								<select class="form-control select2-noSearch allowClear"
									id="roomLst" width="100%"></select>
							</div>
						</div>
						<div class="col-xs-4">
							<div class="col-xs-6">
								<label class="control-label">办案民警：</label>
							</div>
							<div class="col-xs-6">
								<input id="police" type="text" class="form-control input-sm"
									value="模糊查询"
									onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
									onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
									style="color: #aaa;">
							</div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-4">
							<div class="col-xs-6">
								<label class="control-label">被问讯人姓名：</label>
							</div>
							<div class="col-xs-6">
								<input id="askPerson" type="text" class="form-control input-sm"
									value="模糊查询"
									onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
									onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
									style="color: #aaa;">
							</div>
						</div>
						<div class="col-xs-4">
							<div class="col-xs-6">
								<label class="control-label">身份证号：</label>
							</div>
							<div class="col-xs-6">
								<input id="idNum" type="text" class="form-control input-sm"
									value="模糊查询"
									onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
									onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
									style="color: #aaa;">
							</div>
						</div>
						<div class="col-xs-4">
							<div class="col-xs-6">
								<label class="control-label">所属案件：</label>
							</div>
							<div class="col-xs-6">
								<div class="input-group">
									<input class="form-control input-sm" id="aboutCaseLst"
										><span class="input-group-addon selectAboutCaseLst"><span
										class="icon-search"></span></span> <input type="hidden" id="lawCaseName">
								</div>
							</div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-6">
							<div class="col-xs-4">
								<label class="control-label">进入办案区时间：</label>
							</div>
							<div class="col-xs-8">
								<div id="dateRangeId_2" class="dateRange form-group"
									style="height: 30px;">
									<input type="hidden" id="dtfmt" class="dateFmt"
										value="info@yyyy-MM-dd" />
									<div class="col-xs-4">
										<div class="input-group" style="margin-right: 10px;">
											<input type="text" id="fixed_start" date-pos="top"
												class="laydate-start form-control input-sm"
												readonly="readonly"> <span
												class="laydate-start-span input-group-addon m-ui-addon">
												<span class="glyphicon glyphicon-calendar"
												aria-hidden="true"></span>
											</span>
										</div>
									</div>
									<div class="col-xs-4 to">
										——
									</div>
									<div class="col-xs-4">
										<div class="input-group" style="padding-left: 5px;">
											<input type="text" id="fixed_end"
												class="laydate-end form-control input-sm"
												readonly="readonly"> <span
												class="laydate-end-span input-group-addon m-ui-addon">
												<span class="glyphicon glyphicon-calendar"
												aria-hidden="true"></span>
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
	
						<div class="col-xs-5">
							<div class="col-xs-3">
								<label class="control-label">是否已刻录：</label>
							</div>
							<div class="col-xs-3">
								<div style="width: 100px; float: left;">
									<select id="recordstatus"
										class="form-control input-sm select2-noSearch allowClear"
										style="width: 100px"></select>
								</div>
								<div style="width: 100px; float: left; margin-left: 10px;"
									class="m-inline">
									<input id="statusBtn"
										class="form-control input-sm valiform-keyup form-val"
										style="display: none; width: 100px; height: 30px; vertical-align: bottom; border: none; border-bottom: 1px solid #aaa">
								</div>
							</div>
						</div>
						<div class="col-xs-1 m-ui-btn-box">
							<button id='checkBtn' class="btn btn-primary btn-sm">查询</button>
							<button id="resetLocker" class="btn btn-default btn-sm">重置</button>
						</div>
					</div>
	
	
				</div>
			</div>

		  <!--整体查询板块--end-->

			<!--悬浮操作层-->
			<div id="c-center-right-content-block">
				<div class="m-ui-table">
					<table id="askRoomTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
						
					</table>
				</div>
			</div>
		  <!--结束-->
		</div>
      </div>
	</div>
	<div id="menuContent" class="ztree-MenuContent">
		<input type="text" id="keySelect" style="margin-bottom:5px;display:none;" class="form-control input-sm" />
		<ul id="ztree-unitSelect" class="ztree" style="width:200px; height: 150px;"></ul>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/yykl/videoRecordingList.js"></script>
 <script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>