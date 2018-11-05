<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
</head>
<body class="m-ui-layer-body">
<div class="m-ui-layer-box" style="width:600px;">
<div class="m-ui-layer-searchbox-sm">
<div class="row row-mar mar-top">
<div class="col-xs-9"><p class="row-mar" id = "loginName"></p>
</div>
</div>

<p class="m-line"></p>
<div class="row row-mar">
<div class="m-ui-title4 row"><h2 class="pull-left">个人信息</h2><p class="pull-right" style="color:#f77244">温馨提示：您想修改个人信息，请联系分局管理员，由管理员统一修改。</p></div>
          <div class="row">
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">姓名：</label></p><p class="col-xs-7 m-label-right" id="pa_lookPage_name"></p></div>
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">性别：</label></p><p class="col-xs-7" id="pa_lookPage_sex"></p></div>
             </div>
          <div class="row">   
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">职务：</label></p><p class="col-xs-7" id="pa_lookPage_job"></p></div>
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">警号：</label></p><p class="col-xs-7" id="pa_lookPage_policeNo"></p></div>
              </div>
          <div class="row">       
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">身份证号：</label></p><p class="col-xs-7" id="pa_lookPage_statusCardNo"></p></div>
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">民族：</label></p><p class="col-xs-7" id="pa_lookPage_nationality"></p></div>
              </div>
           <div class="row">       
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">政治面貌：</label></p><p class="col-xs-7" id="pa_lookPage_politicsType"></p></div>
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">学历：</label></p><p class="col-xs-7" id="pa_lookPage_diploma"></p></div>
              </div>    
          <div class="row"> 
          <p class="col-xs-2 color-gray"><label class="control-label">所属单位：</label></p><p class="col-xs-9" id="pa_lookPage_unit"></p></div>   
          <div class="row">       
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">移动电话：</label></p><p class="col-xs-7" id="pa_lookPage_unit"></p></div>
          <div class="col-xs-6"><p class="col-xs-4 color-gray"><label class="control-label">办公电话：</label></p><p class="col-xs-7" id="pa_lookPage_officePhone"></p></div>
              </div>       
</div>
</div>
</div>
<script type="text/javascript" src="<%=context %>/scripts/ajgl/personInfo/personInfo.js"></script>
</body></html>