<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区督查</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
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
					<li>办案区督察</li>
					<li>办案区督查</li>
				</ol>
				<div id="c-center-right-content-block">
					<div id="c-center-right-content-bar">
						<div class="m-ui-title3"><h2>单位</h2></div>
						<ul class="list-group" id="unit">
						</ul>
						</div>
					<!--询问室左侧侧结束-->
				<div id="c-center-right-content-con">
					<div class="right-inner">
					<div class="m-ui-title2 "><h2 id="titles"></h2></div>
					<div style="border-top:1px solid #aaa; border-bottom:1px solid #aaa;  padding:20px 0;">
					<div class="m-ui-title-lg "><h2>讯（询）问室</h2></div>
					<div class="room-list room-only">
					<ul id="xws">
									
					</ul>
					<div class="clear"></div>
					</div>
					</div>
					
					<div style="border-bottom:1px solid #aaa;  padding:20px 0;">
					
					<div class="m-ui-title-lg"><h2>其他房间</h2></div>
					<div class="room-list room-only">
					<ul id="qt">
					
					
					</ul>
					<div class="clear"></div>
					</div>
					</div>
					</div>
					</div>
					<!--询问室右侧结束-->
					</div>

				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaSupervision/handlingAreaSupervision.js"></script>
</html>