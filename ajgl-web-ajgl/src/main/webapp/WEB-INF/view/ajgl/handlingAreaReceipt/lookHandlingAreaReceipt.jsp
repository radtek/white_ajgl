<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 办案区使用单</title>
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
					<li>办案区使用</li>
					<li>办案区使用单</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>办案区使用单详情</h1>
						<div class="m-ui-caozuobox" style="display:none">

							<button class="btn btn-success finishReceipt finishBtnFlag" style="display: none;">完成本次问讯</button>
							<button class="btn btn-primary printBtnFlag" style="display: none;" id="printAll">打印使用单</button>
							<button class="btn btn-primary printBtnFlag" style="display: none;" id="unbound">解绑手环</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="m-ui-layer-box">
					<div id="tabs" class="m-ui-tabs">
						<!-- 办案区使用单tabs按钮页面 -->
						<%@include file="/WEB-INF/view/ajgl/util/handlingAreaReceiptMenu.jsp"%>
						<div class="row" style="padding: 10px; text-align: right;">
							<button id="updateHandlingAreaReceipt" class="btn btn-info modifyBtnFlag" style="display: none;">
								<span class="glyphicon glyphicon-edit micon-lg mar-right"></span>维护人员基本情况
							</button>
						</div>
						<div class="row">
<div class="col-xs-12">
<div style="padding-right:220px;">
 <table class="table table-sg" cellspacing="0" width="100%">
 <tbody>
           <tr>
              <td class="td-left" width="15%">使用单编号：</td>
              <td colspan="3" id="handlingAreaReceiptNum"></td>
              </tr>
      <tr>
              <td class="td-left" width="15%">被问讯人：</td>
              <td width="32%" id="byQuestioningPeopleName"></td>
              <td class="td-left" width="18%">手环号：</td>
              <td id="bangleCode"></td>
          </tr>
          <tr>
              <td class="td-left">性别：</td>
              <td id="sexStr"></td>
              <td class="td-left">身份证件号码：</td>
              <td id="byQuestioningPeopleIdentifyNum"></td>
          </tr>
          <tr>
            <td class="td-left"><font color="green">#</font>&nbsp;&nbsp;联系方式：</td>
              <td id="byQuestioningPeopleTelphoneNumber"></td>
              <td class="td-left"><font color="green">#</font>&nbsp;&nbsp;户籍地：</td>
              <td id="door"></td>
          </tr>
           <tr style="height:55px;">
              <td class="td-left"><font color="green">#</font>&nbsp;&nbsp;现住址：</td>
              <td id="byQuestioningPeopleAddress"></td>
              <td class="td-left">所属案件：</td>
			  <td><div id="lawCase"></div><div id="lawCaseId"></div>
	          <tr>
              <td  class="td-left">案由：</td>
              <td id="causeOfLawCaseStr"></td>
              <td  class="td-left">进入办案区原因：</td>
              <td id="enterAreaReasons"></td>
              </tr>
             <tr>
              <td class="td-left">对应文书：</td>
              <td colspan="3" id="attach"></td>
              </tr>
             <tr>
              <td class="td-left">办案民警：</td>
              <td><span id="handlingPolice"></span><a href="#" id="showPoliceList" style="font-size: 14px" class="btn btn-bordered btn-xs mar-left">查看办案民警信息</a></td>
              <td class="td-left">进入办案区时间：</td>
              <td id="enterIntoTimeStr"></td>
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
<div style="padding:10px 0;width:150px;overflow:hidden; margin:0 auto;"><img id="idCardPicture" class="zoomIn" src="../images/man/man-none.png" height="180"><p style="padding-top:5px;">身份证照片</p></div>
 </div> 
  </div>  
 </div> 
<div class="row-mar color-gray font12">
最新修改人：<span id="modifyPeopleName"></span>  <span style="margin-left:30px;">最新修改时间：<span id="modifyTime"></span></span>
</div>   

						<!--操作历史-->
						<!--<div class="m-ui-title3 mar-top">
							<h2>操作历史</h2>
						</div>

						<div class="od-expand-btn" id="od-expand-btn-1"
							onClick="document.getElementById('od-expand-content-1').style.display='block';document.getElementById('od-expand-btn-1').style.display='none'">
							<a href="###"><span>显示操作历史</span></a>
						</div>
						<div id="od-expand-content-1" style="display: none;">
							<div class="m-ui-table m-ui-table-sm">
								<table id="opertionRecord" 
									class="table table-bordered table-hover m-ui-table-no-paginate"
									cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>序号</th>
											<th>操作内容</th>
											<th>操作人</th>
											<th>操作时间</th>
											<th>说明</th>
										</tr>
									</thead>
									<tbody>										
									</tbody>
								</table>
							</div>
							<div class="od-expand-btn od-fold-btn"
								onClick="document.getElementById('od-expand-content-1').style.display='none';document.getElementById('od-expand-btn-1').style.display='block'">
								<a href="###"><span>隐藏操作历史</span></a>
							</div>
						</div>-->
						<!--操作历史-->
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaReceipt/lookHandlingAreaReceipt.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
</html>