<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>案件管理 – 讯（询）问室维护</title>
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
					<li>房间维护</li>
				</ol>

				<!--整体查询板块--begin-->
				<div class="basic-query-out">
					<div class="basic-query">
						<input id="nameText" type="text" class="form-control input-sm" value="讯（询）问室名称模糊查询"
							onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
							onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
							style="color: #aaa;">
						<button class="btn btn-primary btn-sm searchAskRoom">查询</button>
						<button class="advanced-btn">展开高级查询</button>
					</div>
				</div>

				<div class="m-ui-searchbox  advanced-query">
					<div class="m-ui-searchbox-con">

						<div class="row row-mar">
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">讯（询）问室编号：</label>
								</div>
								<div class="col-xs-6">
									<input id="code" type="text" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">讯（询）问室名称：</label>
								</div>
								<div class="col-xs-6">
									<input id="name" type="text" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">状态：</label>
								</div>
								<div class="col-xs-6">
									<select id="status" class="form-control input-sm select2-noSearch allowClear askRoomStatusDictionaryItem"></select>
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">所属单位：</label>
								</div>
								<div class="col-xs-6 input-group">
									<input id="unit" type="hidden" />
									<input id="unitName" type="text" readonly class="form-control input-sm selectTreeUnit valiform-keyup form-val" datatype="*1-25" nullmsg="请选择单位">
									<span class="input-group-addon selectTreeUnit">
										<span class="glyphicon glyphicon-search"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="row row-mar">
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">备注：</label>
								</div>
								<div class="col-xs-6">
									<input id="note" type="text" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-3 m-ui-btn-box">
								<button class="btn btn-primary btn-sm searchAskRoom">查询</button>
								<button id="resetAskRoom" class="btn btn-default btn-sm">重置</button>
							</div>
						</div>

					</div>
				</div>
				<!--查询结束-->
				<div class="advanced-btn-box">
					<button class="advanced-btn-up">收起高级查询</button>
				</div>
				<!--整体查询板块--end-->

				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>房间维护</h1>
						<div class="m-ui-caozuobox">
							<button id="newAskRoom" class="btn btn-primary">新增</button>
							<button id="updateAskRoom" class="btn btn-success">修改</button>
							<button id="deleteAskRoom" class="btn btn-danger">删除</button>
							<button id="enabledAskRoom" class="btn btn-success">启用</button>
							<button id="disableAskRoom" class="btn btn-danger">停用</button>
						</div>
					</div>
				</div>
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
	src="<%=context%>/scripts/ajgl/askRoom/askRoomList.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>