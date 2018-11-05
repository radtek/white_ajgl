<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>大屏</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<!--[if lte IE 6]>
<![endif]-->
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
				<h1>刑事案件今日到期提醒</h1>
				<h2>
					今日到期提醒涉及的刑事案件：<span id="caseCount"></span>
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

			<div class="today-list">
				<ul>
					<li><div class="sort-tt short-bg">
							<h1>刑拘</h1>
							<h2>
								到期提醒共<span class="c-red" id="xjCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="xj">
								
							</ul>
						</div></li>
					<li><div class="sort-tt">
							<h1>变更羁押</h1>
							<h2>
								到期提醒共<span class="c-red" id="bgjyCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="bgjy">
								
							</ul>
						</div></li>
					<li><div class="sort-tt">
							<h1>提请逮捕</h1>
							<h2>
								到期提醒共<span class="c-red" id="tqdbCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="tqdb">
								
							</ul>
						</div></li>
					<li><div class="sort-tt short-bg">
							<h1>批捕</h1>
							<h2>
								到期提醒共<span class="c-red" id="pbCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="pb">
								
							</ul>
						</div></li>
					<li><div class="sort-tt">
							<h1>移送起诉</h1>
							<h2>
								到期提醒共<span class="c-red" id="ysqsCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="ysqs">
								
							</ul>
						</div></li>
					<li><div class="sort-tt">
							<h1>取保候审</h1>
							<h2>
								到期提醒共<span class="c-red" id="qbhsCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="qbhs">
								
							</ul>
						</div></li>
					<li class="last-li"><div class="sort-tt">
							<h1>监视居住</h1>
							<h2>
								到期提醒共<span class="c-red" id="jsjzCount"></span>人次
							</h2>
						</div>
						<div class="aj-list">
							<ul id="jsjz">
								
							</ul>
						</div></li>
				</ul>
			</div>
		</div>
	</div>
	<div>
	   <a href="#" id="nextxj"></a>
	   <a href="#" id="nextbgjy"></a>
	   <a href="#" id="nexttqdb"></a>
	   <a href="#" id="nextpb"></a>
	   <a href="#" id="nextysqs"></a>
	   <a href="#" id="nextqbhs"></a>
	   <a href="#" id="nextjsjz"></a>
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/crownCase/crownCase.js"></script>
</html>
