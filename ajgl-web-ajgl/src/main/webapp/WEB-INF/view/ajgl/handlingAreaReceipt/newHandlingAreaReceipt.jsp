<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformHandlingAreaReceipt" class="validform m-ui-layer-body">
	<div class="m-ui-layer-content" style="margin-left: 35px;">
<div class="row">
<div class="col-xs-12">
<div style="padding-right:220px;">
 <table class="table table-sg" cellspacing="0" width="100%">
 <tbody> 
           <tr>
              <td class="td-left" width="15%">使用单编号：</td>
              <td colspan="3"><span id="handlingAreaReceiptNum"></span></td>
              </tr>
      <tr>
              <td class="td-left" width="15%">被问讯人：<span class="red-star">*</span></td>
              <td width="35%"><input class="form-control input-sm valiform-keyup form-val" style="width:150px"
              		vf-position="1" datatype="*1-26" id="byQuestioningPeopleName"></td>
              <td class="td-left" width="18%">手环号：</td>
              <td><button id="wristband" class="btn btn-info btn-xs"><span class="icon-rss mar-right-sm"></span>读取手环信息</button><span id="bangleCode" style="margin-left: 10px;"></span></td>
          </tr>
          <tr>
          <!-- 
           <td colspan="2" style="text-align: right; color: red">*此行为临时使用，注意删除*</td>
          		<td>
          			<input style="width: 100px" onblur="$('#handlingAreaReceiptNum').text($(this).val())">
          		</td>
          		<td>
          			<input style="width: 100px" onblur="$('#bangleCode').text($(this).val())">
          		</td>
          </tr>
           -->
          <tr>
              <td class="td-left">性别：<span class="red-star">*</span></td>
              <td><div style="width: 150px"><select class="form-control input-sm select2-noSearch valiform-keyup"
						 id="sex" vf-position="1" datatype="*"></select></div></td>
              <td class="td-left">身份证件号码：<span class="red-star">*</span></td>
              <td><input class="form-control input-sm valiform-keyup form-val" style="width: 170px"
						vf-position="1" datatype="*0-26" id="byQuestioningPeopleIdentifyNum"><button class="btn btn-primary btn-sm search" id="queryPersonId">查看信息</button></td>
          </tr>
          <tr>
            <td class="td-left">联系方式：</td>
              <td><input class="form-control input-sm valiform-keyup form-val" style="width: 150px"
						vf-position="1" datatype="*0-26" id="byQuestioningPeopleTelphoneNumber"></td>
              <td class="td-left">户籍地：</td>
              <td><input class="form-control input-sm valiform-keyup form-val"
						vf-position="1" datatype="*0-50" id="door"></td>
              </tr>
           <tr style="height:37px;">
             <td class="td-left">现住址：</td>
              <td><textarea class="form-control input-sm valiform-keyup form-val" style="height:55px;resize: none;"
						vf-position="1" datatype="*0-50" id="byQuestioningPeopleAddress"></textarea></td>
              <td  class="td-left">所属案件：</td>
              <td><div class="input-group lawCaseDiv"><input class="form-control input-sm selectLawCaseName" id="lawCaseName" readonly/><span class="input-group-addon selectLawCaseName"><span class="icon-search"></span></span></div><input class="form-control input-sm" id="lawCase" readonly/></td>
          </tr>
		<tr>
            <td  class="td-left">案由：<span class="red-star">*</span></td>
              <td>
              		<div style="width: 100px; float: left;"><select id="causeOfLawCase" class="form-control input-sm select2-noSearch" style="width:100px"></select></div>
              		<div style="float: left; margin-left: 10px; width:150px" class="m-inline"><input id="otherCauseOfLawCase" style="display: none" vf-position="1" datatype="*0-26" class="form-control input-sm valiform-keyup form-val" style="height:30px; vertical-align:bottom; border:none; border-bottom:1px solid #aaa"></div>
              </td>
              <td  class="td-left">进入办案区原因：<span class="red-star">*</span></td>
              <td><input id="enterAreaReasons" vf-position="1" datatype="*1-26" class="form-control input-sm valiform-keyup form-val"></td>
              </tr>
               <tr>
               <td  class="td-left">对应文书：</td>
              <td colspan="3">
				<div id="container" class="upload-control"></div>
              </td>
              </tr>
             <tr>
              <td  class="td-left">办案民警：<span class="red-star">*</span></td>
              <td><span id="handlingPolice"></span>
              <p class="mar-top">  <button id="checkPolice" class="btn btn-bordered btn-xs" style=" margin-left:0">核对办案民警信息</button><a href="#" id="showPoliceList" style="font-size: 14px" class="btn btn-bordered btn-xs mar-left">查看办案民警信息</a></p></td>
              <td  class="td-left">进入办案区时间：</td>
              <td>
				<span id="enterIntoTime"></span>
			</td>
          </tr>
  </tbody>
    </table> 
<div class="m-ui-title3 mar-top"><h2 class="m-bold">前科情况</h2></div>  
<div class="m-ui-table m-ui-table-sm">
	<table id="criminalRecordTable" class="table m-ui-table-no-paginate table-bordered " cellspacing="0">
    </table>
</div>    
 </div> 
 </div>
<div class="col-xs-2" style="width:200px; margin-left:-200px;">
<div class="alert-default font14 text-center">
<div style="padding:10px 0; width:150px;overflow:hidden; margin:0 auto;"><img id="cameraPicture" class="zoomIn" src="../images/man/man-none.png" height="180"><p style="padding-top:5px;"> 
<button class="btn btn-primary btn-sm showCamera"><span class="icon-camera mar-right-sm"></span>人像采集</button>&nbsp;<span class="red-star">*</span></p></div>
<div style="padding:10px 0;width:150px;overflow:hidden; margin:0 auto;"><img id="idCardPicture" class="zoomIn" src="../images/man/man-none.png" height="180"><p style="padding-top:5px;">身份证照片</p></div>
 </div> 
  </div>  
 </div>

</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaReceipt/newHandlingAreaReceipt.js"></script>
</html>