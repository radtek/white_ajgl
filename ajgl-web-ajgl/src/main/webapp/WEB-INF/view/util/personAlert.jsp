<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>案件管理 – 系统管理</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
</head>
<body>
	<div id="c-center-right" style="background-color: ghostwhite">
			<div id="c-center-right-content-block">
				<!--树形菜单start-->
				<div id="c-center-right-content-bar" style="padding:0">
					<div class="left-tree">
						<div class="m-ztree-out"> 
							<input type="text" id="keyIn" style="margin-bottom:5px" class="form-control input-sm" />
							<ul id="ztree-inUnitTree" class="ztree" style="height:100%"></ul>
				    	</div>
					</div>
				</div>
				<!--树形菜单end-->
				<div id="c-center-right-content-con" class="alt">
					<div class="right-inner">
					<div class="m-ui-searchbox">
						<div class="m-ui-searchbox-con" style="min-width:800px">
							<div class="row row-mar mar-top">
								<div class="col-xs-3">
									<div class="form-group">
										<div class="col-xs-4"> <label class="control-label">姓名：</label></div>
										<div class="col-xs-8">
											<input id="pa_name" type="text" class="form-control input-sm" value="">
										</div>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="form-group">
										<div class="col-xs-4"> <label class="control-label">性别：</label></div>
								    	<div class="col-xs-8">
								   			<select id="pa_sex" class="form-control input-sm"></select>
								 		</div>
									</div>
								</div>
								<div class="col-xs-3">
							  		<div class="form-group">
							   			<div class="col-xs-4"> <label class="control-label">状态：</label></div>
							    		<div class="col-xs-8">
											<select id="pa_status" class="form-control input-sm"></select>
										</div>
									</div>
								</div>
								<div class="col-xs-3">
								<div class="m-ui-btn-box" style="text-align: center">
									<button id="pa_search" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true" style="margin-right:10px;"></span>查询</button>
									<button id="pa_resetting" class="btn btn-default m-ui-btn3" style="margin-left:5px;">重置</button>
								</div>
							</div>
							</div>
							<div class="row row-mar mar-top">
							<div class="col-xs-4">
								<div class="form-group" style="display:none">
									<div class="col-xs-4"> <label class="control-label">所属单位：</label></div>
							    	<div class="col-xs-8" style="padding-top:5px;">
							    		<input id="pa_unitCode" type="hidden" value="" >
							   			<input id="pa_unit" type="radio" name="pa_iCheck" style="display:none" class="icheckradio" checked value=""><span style="margin-left:5px; margin-right:20px; display:none">本单位</span>
							 			<input id="pa_isXJUnit" type="radio"  style="display:none" name="pa_iCheck" class="icheckradio" value="1" ><span style="margin-left:5px;display:none">含下级单位</span>
							    	</div>
							  	</div>
							</div>
						</div>
					</div>
				</div>
				<div class="m-ui-table">
					<table id="pa_dataTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%"></table>
				</div>
				<!--数据列表结束-->
			</div>
			<!--查询结束-->
</div>
</div>
<!--c-center-right-content-block结束-->


    <!--右侧内容结束-->
   </div>	

</body>
<script type="text/javascript" src="<%=context %>/scripts/util/personAlert.js"></script>


</html>