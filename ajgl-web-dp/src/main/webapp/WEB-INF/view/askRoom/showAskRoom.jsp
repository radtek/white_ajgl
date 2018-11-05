<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
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
			<div class="screen-tt">
				<h1>今日讯（询）问情况</h1>
			</div>


			<div class="row">
				<div class="col-xs-8">
					<div class="row" style="height: 250px; padding-top: 20px;">
						<div class="col-xs-5">
							<div class="row" style="margin-bottom: 21px">
								<h2 class="title1 pull-left">讯（询）问室管理</h2>
								<p class="pull-left c-gray" style="margin-left: 15px">
									<span class="c-state c-busy" style="margin-right: 5px"></span>在用,
									<span class="c-state c-free" style="margin-right: 5px"></span>空闲,
									<span class="c-state" style="margin-right: 5px;background-color:#999"></span>不可用
								</p>
							</div>
							<ul class="c-room-list" id="xwsList">
							</ul>
						</div>
						<div class="col-xs-1 txt-center" style="width: 12%">
							<div id="room-group-change">
								<p id="room-change_bar">
									<a href="#" id="previous"></a><a href="#" id="next"></a>
								</p>
							</div>
						</div>
						<div class="col-xs-6" style="padding-top: 85px; width: 44%;">
							<div class="today-total">
								<div style="padding: 0 30px">
									<p
										style="margin-bottom: 6px; padding-bottom: 6px; border-bottom: 1px dotted #516aa1">
										<span class="m-inline " style="width: 160px">待问人数：</span><span
											class="c-red" id="waitingCount"></span>
									</p>
									<p>
										<span class="m-inline " style="width: 160px">讯（询）问进行中人数：</span><span
											class="m-inline c-red" style="width: 150px;"
											id="runningCount"></span>
									</p>
									<p
										style="margin-bottom: 6px; padding-bottom: 6px; border-bottom: 1px dotted #516aa1">
										<span class="m-inline" style="width: 160px">共：</span><span
											class="m-inline c-red" style="width: 150px;"><span
											id="sumCount"></span>人</span>
									</p>
									<p>
										<span class="m-inline" style="width: 160px">已完成讯询问人数：</span><span
											class="m-inline c-red" style="width: 150px;" id="finishCount"></span>
									</p>
									<p>
										<span class="m-inline" style="width: 160px">其中：</span><span
											class="m-inline c-red" style="width: 150px;">男<span
											id="manCount"></span>人，女<span id="womenCount"></span>人
										</span>
									</p>
								</div>
							</div>
						</div>
					</div>


					<!------------------询问室end-------------------->
					<div class="row row-mar">
						<h2 class="title1 pull-left">
							今日讯（询）问嫌疑人列表 共<span class="c-red" id="countBtn"></span>人
						</h2>
						<p class="pull-left c-gray" style="margin-left: 15px">
							<span class="icon-overtime time-23" style="margin-right: 5px"></span>进入办案区达23小时,
							<span class="icon-overtime time-24" style="margin-right: 5px"></span>进入办案区达23.5小时以上
						</p>
					</div>
					<div class="row">
						<table class="table table-bordered table-hover screen-table"
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<th width="3%"></th>
									<th width="13%">嫌疑人姓名</th>
									<th width="20%">案件名称</th>
									<th width="4%">性别</th>
									<th width="13%">进入办案区时间</th>
									<th width="13%">预计达24小时时间</th>
									<th width="15%">主办单位</th>
									<th width="10%">主办民警</th>
									<th>案由</th>
								</tr>
							</thead>
						</table>

						<div id="tableScroll-box" style="height: 190px">
							<div id="tableScroll">
								<table class="table table-bordered table-hover screen-table"
									cellspacing="0" width="100%">
									<tbody id="suspectsTable">

									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!------------------案件列表end-------------------->

				</div>
				<div class="col-xs-4">
					<div class="fang-box time-box">
						<div class="inner">
							<p class="long">统计时间：</p>
							<p id="today"></p>
							<span class="to">至</span>
							<p id="tomorrow"></p>
						</div>
					</div>
					<h2 class="title1 text-center row-mar" style="margin-top: 25px;">本周各主办单位嫌疑人入区数</h2>
					<h3 class="title2 text-center" id="week"></h3>
					<div id="highchart-bar" style="height: 400px"></div>
				</div>
				<!------------------right end-------------------->
			</div>
		</div>
	</div>

</body>

<script type="text/javascript"
	src="<%=context%>/scripts/askRoom/showAskRoom.js"></script>
</html>
