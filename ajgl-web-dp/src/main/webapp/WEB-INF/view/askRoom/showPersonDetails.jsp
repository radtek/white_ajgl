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
<link rel="stylesheet" href="<%=context%>/dp/common/library/bootstrap/dist/css/bootstrap.custom.css">
<link rel="stylesheet" href="<%=context%>/dp/common/library/font-awesome/css/font-awesome.css" /><!---------图标------------>
<!--[if lte IE 6]>
  <link rel="stylesheet" type="text/css" href="<%=context%>/dp/common/library/bootstrap/bsie/css/bootstrap-ie6.css">
  <link rel="stylesheet" type="text/css" href="<%=context%>/dp/common/library/bootstrap/bsie/css/ie.css">
<![endif]-->
<link rel="stylesheet" href="<%=context%>/dp/common/base/style/base.css">
<link rel="stylesheet" href="<%=context%>/custom/default/style/frame.css">
<link rel="stylesheet" href="<%=context%>/custom/default/style/screen.css">

</head>
<body class="screen-bg">

  <!-------------------大屏分辨率1472px:576px-------------------->
<div class="screen-content">
<div class="screen-content-inner">
<div class="screen-tt"><h1>嫌疑人详细信息</h1></div>


<div class="row">
<div class="col-xs-8">
<div class="row" style="height:70px; padding-top: 30px;">  
<h2 class="title2 pull-left" style="margin-top: 15px; margin-right: 10px;">被讯询问人：</h2><h1 class="xy-name pull-left" id="byQuestioningPeopleNameBig"></h1> 
<p class="pull-left" style="padding-left: 10px; padding-top: 10px"> <span id="statusSpan" class="event-state event-state-lg"></span></p>
</div>
<div class="row basic-info">
<div class="col-xs-6">
  <p class="col-xs-3 text-right c-gray">使用单编号：</p>
  <p class="col-xs-8" id="handlingAreaReceiptNum"></p>
  <p class="col-xs-3 text-right c-gray">被问询人：</p>
  <p class="col-xs-8" id="byQuestioningPeopleName"></p>
  <p class="col-xs-3 text-right c-gray">性别：</p>
  <p class="col-xs-8" id="sexStr"></p>
  <p class="col-xs-3 text-right c-gray">手环号：</p>
  <p class="col-xs-8" id="bangleCode"></p>
  <p class="col-xs-3 text-right c-gray">身份证号码：</p>
  <p class="col-xs-8" id="byQuestioningPeopleIdentifyNum"></p>
  <p class="col-xs-3 text-right c-gray">联系方式：</p>
  <p class="col-xs-8" id="byQuestioningPeopleTelphoneNumber"></p>
  <p class="col-xs-3 text-right c-gray">进入办案区时间：</p>
  <p class="col-xs-8" id="enterIntoTimeStr"></p>
</div>  
<div class="col-xs-6">
  <p class="col-xs-3 text-right c-gray">现住址：</p>
  <p class="col-xs-8" id="byQuestioningPeopleAddress"></p>
  <p class="col-xs-3 text-right c-gray">户籍地：</p>
  <p class="col-xs-8" id="door"></p>
  <p class="col-xs-3 text-right c-gray">所属案件：</p>
  <p class="col-xs-8" id="lawCase"></p>
  <p class="col-xs-3 text-right c-gray">案由：</p>
  <p class="col-xs-8" id="causeOfLawCaseStr"></p>
  <p class="col-xs-3 text-right c-gray">进入办案区原因：</p>
  <p class="col-xs-8" id="enterAreaReasons"></p>
  <p class="col-xs-3 text-right c-gray">对应文书：</p>
  <p class="col-xs-8" id="attach"></p>
  <p class="col-xs-3 text-right c-gray">办案民警：</p>
  <p class="col-xs-8" id="handlingPolice"></p>
</div>  
</div>

  

<!------------------询问室end-------------------->

<h2 class="title2 row-mar">前科情况 </h2>
<div class="row">
 <div class="col-xs-7"> 
    <table class="table table-bordered table-hover screen-table" cellspacing="0" width="100%">
                      <thead>
                          <tr>
                              <th width="10%">序号</th>
                              <th width="50%">涉及案件</th>
                              <th width="20%">办案民警</th>
                              <th>涉案时间</th>
                        </tr>
                      </thead>
    </table>
 <div id="tableScroll">
	<table id="qkTable" class="table table-bordered table-hover screen-table" cellspacing="0" width="100%">
		<tbody>
		</tbody>
	</table>
</div>
</div>
 <div class="col-xs-4">
 <div class="pull-left photo-box" style="margin-left: 20px;">
   <img id="cameraPicture" src="">
   <p class="c-gray">人像采集照片</p>
 </div> 
<div class="pull-left photo-box">
   <img id="idCardPicture" src="">
   <p class="c-gray">身份证照片</p>
 </div>    
 </div>       
</div>
<!------------------案件列表end-------------------->

</div>
<div class="col-xs-4">
<div style="position: relative">
 <div class="room-video-box">
    <div id="ocxModel" tabid="OCX" title="OCX" style="display: block;height: 100%">
    <object id="dsscocx" classid="CLSID:1D1A0ACA-3A53-4589-88B8-94A0DDC47EA6" 
    onreadystatechange="" style="display: none;"></object>
    </div> 
 </div>
 <h3 class="room-name" id="gridName"></h3> 
 </div> 
<div class="room-timeline">
  <ul id="timerLst">
  </ul>
</div>
 </div>
  <!------------------right end-------------------->
</div> 
</div>
</div>
<div>
<a href="#" id="next"></a>
</div>





</body>

<script type="text/javascript" src="<%=context%>/scripts/askRoom/showPersonDetails.js"></script>
</html>
