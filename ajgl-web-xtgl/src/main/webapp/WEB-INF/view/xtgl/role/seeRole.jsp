<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<script>
</script>
</head>
<body class="m-ui-layer-body">
<div class="m-ui-layer-box" >
		<div class="m-ui-searchbox m-ui-layer-searchbox" style="padding-bottom: 0;">
		<div class="row row-mar mar-left mar-right">
           <div class="col-xs-12">
               <table class="table table-sg" cellspacing="0" width="100%" style="margin-bottom:0">
                <tbody>
              <tr>
                <td class="td-left"  width="20%">角色名称</td><td class="word-break" width="20%" id="roleName"></td>
                <td class="td-left" width="20%">状    态</td><td width="20%"  id="statusReadonly"></td>
              </tr>
              <tr>
                <td class="td-left">角色编码</td><td id="codeReadonly"></td>
              </tr>
               <tr>
                   <td rowspan="5"  class="td-left">权限信息</td>
                   <td colspan="3" ><ul id="ztree-inUnitTree" class="ztree" style="height:150px"></ul></td>
              </tr>
            </tbody>
          </table>
        </div>
     </div>
	</div>
</body>
<script type="text/javascript"
			src="<%=context%>/scripts/xtgl/role/seeRole.js"></script>

</html>