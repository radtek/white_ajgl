<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<%@ page import="com.taiji.pubsec.ajqlc.util.Constant"%>
<html>
<head>
<title>案件管理 – 系统管理</title>
<script>
var dictionaryType = '<%=Constant.ZT%>';
</script>
<%@include file="/WEB-INF/base/basePart.jsp"%>
</head>

<body>
		<div id="c-center-right" style="background-color: ghostwhite">
					<div id="c-center-right-content-block">


            <div id="c-center-right-content-con" class="alt">
							<div class="m-ui-searchbox">
								<div class="m-ui-searchbox-con">
									<div class="row mar-top row-mar">
										<div class="col-xs-4">
											<div class="form-group">
												<div class="col-xs-4">
													<label class="control-label">角色名称：</label>
												</div>
												<div class="col-xs-8">
													<input type="text" id="roleName" class="form-control input-sm" maxlength="10">
												</div>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="form-group">
												<div class="col-xs-4">
													<label class="control-label">状态：</label>
												</div>
												<div class="col-xs-8">
													<select id="statu" class="select2-noSearch">
														<option value="all">全部</option>
														
													</select>
												</div>
											</div>
										</div>
										<div class="col-xs-4">
											<div class="m-ui-btn-box text-right" style = "padding-right:15px">
												<button class="btn btn-primary " id="queryRole">
													<span class="glyphicon glyphicon-search mar-right" aria-hidden="true"></span>查询
												</button>
												<button class="btn btn-default" id="resetRole">重置
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="m-ui-table " style="margin: auto 10px auto 10px">
								<table id="roleTabulation"
									class="table table-bordered table-hover m-ui-table-no-paginate"
									cellspacing="0" width="100%">
								</table>
							</div>
						</div>
					</div>
				</div>
</body>
<script type="text/javascript"
			src="<%=context%>/scripts/util/roleAlert.js"></script>

</html>