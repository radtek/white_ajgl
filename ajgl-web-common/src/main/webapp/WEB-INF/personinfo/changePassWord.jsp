<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
</head>
<body class="m-ui-layer-body">
<div class="m-ui-layer-box" style="width:400px;">
<div class="m-ui-layer-searchbox-sm">
          <div class="row row-mar">
         <p class="col-xs-3"><label class="control-label">用户名：</label></p><p class="col-xs-7" style=" padding-top: 6px;" id = "userName"></p>
             </div>
          <div class="row row-mar">   
         <p class="col-xs-3"><label class="control-label">原始密码：</label></p><p class="col-xs-7"> <input type="password" class="form-control input-sm" id = "originPassword">
         <span class="color-gray2">若您忘记原始密码，请先联系系统管理员重置密码后再修改</span></p>
              </div>
          <div class="row row-mar">       
         <p class="col-xs-3"><label class="control-label">新密码：</label></p><p class="col-xs-7"> <input type="password" class="form-control input-sm" id = "newPassword"></p>
              </div>
           <div class="row row-mar">       
          <p class="col-xs-3"><label class="control-label">确认新密码：</label></p><p class="col-xs-7"> <input type="password" class="form-control input-sm" id = "confirmNewPassword"></p>
              </div>           
</div>
<!--内容end-->
</div>
<script type="text/javascript" src="<%=context %>/scripts/ajgl/personInfo/changePassWord.js"></script>
</body></html>