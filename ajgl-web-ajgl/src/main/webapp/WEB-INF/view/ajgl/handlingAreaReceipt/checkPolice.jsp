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
<div class="m-ui-layer-box" style="width: 600px;">
	<div class="m-ui-layer-content">
<table class="table table-sg" cellspacing="0" width="100%">
 <tbody>
           <tr>
              <td class="td-left" width="150px">姓名：<span class="red-star">*</span></td>
              <td><input id="policeName" class="form-control input-sm valiform-keyup form-val" vf-position="1" datatype="*1-26" style="width:150px" ><br>
                  <p class="mar-top"><button id="wristband" class="btn btn-bordered btn-xs" style=" margin-left:0">读取IC卡信息</button><span class="mar-left">IC卡号：<span id="icNum" style="margin-left: 10px;"></span></span></p></td>
          </tr>
          <!-- 
          <tr>
              <td style="text-align: right; color: red">*此行为临时使用，注意删除*</td>
          		<td>
          			<input style="width: 100px" onblur="$('#icNum').text($(this).val())">
          		</td>
          </tr>
           -->
      		<tr>
              <td class="td-left">警号（或辅警编号）：<span class="red-star">*</span></td>
              <td><input id="policeNum" class="form-control input-sm valiform-keyup form-val" vf-position="1" datatype="*1-26" style="width:150px"></td>
          </tr>
          <tr>
              <td class="td-left">是否主办民警：</td>
              <td><input type="checkbox" class="icheckbox" id="ifMainPolice"></td>
          </tr>
             <tr style="height:150px;">
              <td class="td-left">备注：</td>
              <td><textarea style="width: 400px;height:150px;resize: none;"  class="form-control valiform-keyup form-val" vf-position="1" datatype="*0-66"  id="remark" ></textarea></td>
          </tr>
  </tbody>
    </table>

</div>
</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaReceipt/checkPolice.js"></script>
</html>