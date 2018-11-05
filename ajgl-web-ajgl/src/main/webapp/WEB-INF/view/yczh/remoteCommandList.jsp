<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>案件管理 – 讯（询）问室维护</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajgl.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<div class="fixed-a">
<div class="m-ui-title1"><h1>经开区公安分局远程指挥</h1></div>
</div>
<!--悬浮操作层-->
<div class="row">
    <div class="col-xs-6" style="border-right: 1px solid #ddd;width:100%;"><div class="m-ui-title-lg" style=" margin-top: 15px;"><h2>讯（询）问室管理</h2></div>
                                <div class="room-only">
                      <ul>
						<li id="template" style="display: none;" class="roomli fi-ceng-out">
							<h2 class="room-name"></h2> <span class="state-mark"></span>
							<div class="fi-ceng box-storage-ceng room-ceng"
								style="display: none;">
								<h4 id="roomName"></h4>
								<div class="alert">
									<p>
										<span class="color-gray">办案区使用单：</span><a id="harCode"
											href="###" class="showDetail"></a>
									</p>
									<p>
										<span class="color-gray">被讯询问人：</span><span
											id="byQuestioningPeopleName"></span>
									</p>
									<p>
										<span class="color-gray">主办民警：</span><span
											id="handlingPolice"></span>
									</p>
									<p>
										<span class="color-gray">分配讯（询）问室时间：</span><span
											id="allocateTime"></span>
									</p>
								</div>
							</div>
						</li>
					</ul>
                      <div class="clear"></div>
                      </div>
      
    </div>
    <div class="col-xs-6" style="margin-left: -1px;width:100%;">
      <div style="padding-left: 20px;border-left: 1px solid #ddd; ">
                    <div class="m-ui-title-lg" style=" margin-top: 15px;"><h2>其它房间</h2></div>
                              <div class="room-only">
                      <ul>
							<li id="template2" style="display: none;" class="fi-ceng-out">
								<h2 class="room-name"></h2> <span class="state-mark"></span>
								<div class="fi-ceng box-storage-ceng room-ceng"
									style="display: none;">
									<h4 id="roomName"></h4>
								</div>
							</li>
						</ul>
                      <div class="clear"></div>
                      </div>
      </div>
      
    </div>
</div>
			</div>
		</div>
	</div>
	<div id="menuContent" class="ztree-MenuContent">
		<input type="text" id="keySelect" style="margin-bottom:5px;display:none;" class="form-control input-sm" />
		<ul id="ztree-unitSelect" class="ztree" style="width:200px; height: 150px;"></ul>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/yczh/remoteCommandList.js"></script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/util/ajglUtil.js"></script>
<script type="text/javascript">
$(".fi-ceng-out").hover(function(){
	  $(this).find(".fi-ceng").show();
	   },function(){
		    $(this).find(".fi-ceng").hide();	 
     });
</script>
</html>