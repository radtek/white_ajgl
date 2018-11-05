<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 系统管理</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>

<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>大屏</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<link rel="stylesheet" href="<%=context%>/dp/common/base/style/base.css">
<link rel="stylesheet"
	href="<%=context%>/custom/default/style/frame.css">
<link rel="stylesheet"
	href="<%=context%>/custom/default/style/screen.css">

</head>
<body class="screen-bg">
	<!-------------------大屏分辨率1472px:576px-------------------->
	<div class="screen-content">
		<div class="screen-content-inner">
			<div class="screen-tt screen-tt-blue">
				<h1>行政案件今日到期提醒</h1>
				<h2>
					今日到期提醒涉及的行政案件：<span id="caseCount"></span>
				</h2>
			</div>
			<div class="row" style="height: 95px;">
				<div class="col-xs-8">
					<div class="fang-box hang-box">
						<div class="inner">
							<p class="c-red">行次序说明：</p>
							<p>嫌疑人姓名 &nbsp; &nbsp; 案件编号 &nbsp; &nbsp; 案件名称 &nbsp; &nbsp;
								主办单位 &nbsp; &nbsp; 主办民警 &nbsp; &nbsp; 法制审核人</p>
						</div>
					</div>
				</div>
				<div class="col-xs-4">
					<div class="fang-box time-box" style="margin-top: 0;">
						<div class="inner">
							<p class="y-m-d" id="showDate"></p>
						</div>
					</div>
				</div>
			</div>

			<div class="today-list today-list-xingzheng">
				<ul>
					<li><div class="sort-tt">
							<h1>行政拘留</h1>
							<h2>
								到期提醒共<span class="c-red" id="xzjlCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="xzjl">

							</ul>
						</div></li>
					<li><div class="sort-tt">
							<h1>社区戒毒</h1>
							<h2>
								到期提醒共<span class="c-red" id="sqjdCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="sqjd">

							</ul>
						</div></li>

					<li class="last-li"><div class="sort-tt">
							<h1>强制戒毒</h1>
							<h2>
								到期提醒共<span class="c-red" id="qzjdCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="qzjd">

							</ul>
						</div></li>


				</ul>
			</div>
		</div>
	</div>

	<div>
		<a href="#" id="nextxzjl"></a>
		<a href="#" id="nextsqjd"></a>
		<a href="#" id="nextqzjd"></a>
	</div>




</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/administrativeCase/showAdministrativeCase.js"></script>
</html>
