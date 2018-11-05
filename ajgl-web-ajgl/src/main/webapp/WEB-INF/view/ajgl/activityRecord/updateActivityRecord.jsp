<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
<style type="text/css">
.container-totalInfoDom {
	display: none;
}
</style>
</head>
<body class="validform" id="validformId">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajgl.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>办案区管理</li>
					<li>办案区使用</li>
					<li>办案区使用单</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>办案区使用单详情</h1>
						<div class="m-ui-caozuobox"></div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="m-ui-layer-box">
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include
							file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						<!-----随身物品记录----->
						<div style="position: absolute; right: 0;">
							<a href="#" class="right-vbtn btn-info">返回活动记录<span
								class="icon-arrow-left micon-lg"></span></a>
						</div>
						<div class="room-only" style="margin-right:45px;">
							<div class="m-ui-title3 mar-top" style="margin-bottom: 0">
								<h2 class="m-bold">分配询问室</h2>
							</div>
							<ul>
								<li id="template" style="display: none;" class="roomli fi-ceng-out">
									<h2 class="room-name"></h2> <span class="state-mark"></span>
									<p class="row text-center btn-box">
										<button class="btn btn-primary allocationBtn">分配</button>
									</p>
									<div class="fi-ceng box-storage-ceng room-ceng"
										style="display: none;">
										<h4 id="roomName"></h4>
										<div class="alert">
											<p>
												<span class="color-gray">办案区使用单：</span><a id="harCode"
													href="###" class="showDetail"></a>
											</p>
											<p>
												<span class="color-gray">被讯询问人：</span><span
													id="byQuestioningPeopleName"></span>
											</p>
											<p>
												<span class="color-gray">主办民警：</span><span
													id="handlingPolice"></span>
											</p>
											<p>
												<span class="color-gray">分配讯（询）问室时间：</span><span
													id="allocateTime"></span>
											</p>
										</div>
									</div>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
					</div>
					<!-----随身物品检查记录end----->
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript">
	$(".fi-ceng-out").hover(function() {
		$(this).find(".fi-ceng").show();
	}, function() {
		$(this).find(".fi-ceng").hide();
	});
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/activityRecord/updateActivityRecord.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>