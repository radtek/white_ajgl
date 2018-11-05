<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>案件管理 – 储物柜管理</title>
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
					<li>首页</li>
					<li>办案区管理</li>
					<li>储物柜管理</li>
				</ol>
				<!--整体查询板块--begin-->
				<!-- <div class="basic-query-out">
					<div class="basic-query">
						<input id="positionText" type="text" class="form-control input-sm" value="具体位置模糊查询"
							onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
							onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
							style="color: #aaa;">
						<button class="btn btn-primary btn-sm searchLocker">查询</button>
						<button class="advanced-btn">展开高级查询</button>
					</div>
				</div>
				<div class="m-ui-searchbox  advanced-query">
					<div class="m-ui-searchbox-con">

						<div class="row row-mar">
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">储物架编号：</label>
								</div>
								<div class="col-xs-6">
									<input id="code" type="text" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">具体位置：</label>
								</div>
								<div class="col-xs-6">
									<input id="position" type="text" class="form-control input-sm">
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">状态：</label>
								</div>
								<div class="col-xs-6">
									<select id="status" class="form-control select2-noSearch input-sm allowClear"></select>
								</div>
							</div>
							<div class="col-xs-3">
								<div class="col-xs-6">
									<label class="control-label">所属单位：</label>
								</div>
								<div class="col-xs-6 input-group">
									<input id="unit" type="hidden" />
									<input id="unitName" readonly type="text" class="form-control input-sm selectTreeUnit">
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
								<button class="btn btn-primary btn-sm searchLocker">查询</button>
								<button id="resetLocker" class="btn btn-default btn-sm">重置</button>
							</div>
						</div>

					</div>
				</div>
				查询结束

				<div class="advanced-btn-box">
					<button class="advanced-btn-up">收起高级查询</button>
				</div>
 -->
 
				<div class="m-ui-searchbox">
				<div class="m-ui-searchbox-con">
				
				<div class="row row-mar" style="display:none;">
				<!-- <div class="col-xs-3">
				   <div class="col-xs-6"> <label class="control-label">所属单位：</label></div>
				    <div class="col-xs-6 input-group">
                    <input id="unit" type="hidden" />
					<input id="unitName" readonly type="text" class="form-control input-sm selectTreeUnit">
					<span class="input-group-addon selectTreeUnit">
						<span class="glyphicon glyphicon-search"></span>
					</span>
                    </div>
				</div> -->
				<div class="col-xs-2">
				   <div class="col-xs-6"> <label class="control-label">区号：</label></div>
				   <div class="col-xs-6">
						<select id="areaCode" class="form-control input-sm"></select>
				   </div>
				</div>
				<div class="col-xs-2">
				   <div class="col-xs-6"> <label class="control-label">柜号：</label></div>
				   <div class="col-xs-6">
						<select id="lockerCode" class="form-control input-sm"></select>
				   </div>
				</div>
				<div class="col-xs-3">
				   <div class="col-xs-6"> <label class="control-label">储物柜名称：</label></div>
				    <div class="col-xs-6">
                    <input id="name" type="text" class="form-control input-sm" value="模糊查询，多个可用逗号隔开"
						onBlur="if(!value){value=defaultValue;this.style.color='#aaa'}"
						onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
						style="color: #aaa;">
                    </div>
				</div>
				<div class="col-xs-5 m-ui-btn-box">
					<button id='checkBtn' class="btn btn-primary btn-sm">查询</button>
					<button id="resetLocker" class="btn btn-default btn-sm">重置</button>
				</div>
				</div>
				
				
				</div>
				</div>
			
 
				<!--整体查询板块--end-->

				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>储物柜管理</h1>
						<div class="m-ui-caozuobox">
							<!-- <button id="newLocker" class="btn btn-primary">新增</button>
							<button id="updateLocker" class="btn btn-success">修改</button>
							<button id="deleteLocker" class="btn btn-danger">删除</button> -->
							<button id="enabledLocker" class="btn btn-success">启用</button>
							<button id="disableLocker" class="btn btn-danger">停用</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
                    <div >
						<p class="alert-default" style="font-size:12px; color:#888; padding:10px;">
						<span style="display:inline-block;width:30px; height:5px; background-color:#ff4d4d;"></span> 表示“在用”
						<span style="display:inline-block;width:30px; height:5px; background-color:#7dd0ff;margin-left:20px"></span> 表示“空闲”
						<span style="display:inline-block;width:30px; height:5px; background-color:#999;margin-left:20px"></span> 表示“停用”
						</p>
						
					</div>	
					<div id="boxDiv"></div>
						<!--结束-->
				
				<!--结束-->
			</div>
		</div>
	</div>
	<div id="menuContent" class="ztree-MenuContent">
		<input type="text" id="keySelect" style="margin-bottom: 5px;display:none;" class="form-control input-sm" />
		<ul id="ztree-unitSelect" class="ztree" style="width: 200px; height: 150px;"></ul>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/locker/lockerList.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/locker/lockerUtil.js"></script>
</html>