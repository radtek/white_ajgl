<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="d" uri="/WEB-INF/tag/securityresource.tld"%>
<%-- <div id="c-center-left">
<div id="c-center-left-menuHeight"> --%>
    
<div class="user-box">
	<div class="user-icon" onclick ="openPersonInfo()">
	</div>
	<div class="user-name"></div>
</div>
<script type="text/javascript">
	function openPersonInfo(){
		window.top.$.layerAlert.dialog({
 			content :context+'/personInfoImage/personInfo.action',
 			pageLoading : true,
 			width : "700px",
 			height : "400px",
 			title:"个人信息查看",
 			shadeClose : false,
 			btn:["关闭"],
 			callBacks:{
				btn1:function(index, layero){
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				}
			},
 			end:function(){
    		}
 		});
	}
	</script>
	<script type="text/javascript">
	$(document).ready(function() {	
		setH3Display();
	});
	/**
	 * 隐藏无下级项的一级标签
	 * @returns
	 */
	function setH3Display () {
		$("#accordion h3").each(function(){
			if($.util.isBlank($(this).attr("myhref"))){
				//myhref为空说明是不可跳转的一级菜单项
				var liLst = $($(this).next()).find("ul li");
				//获取子菜单列表
				var aCount = 0;//跳转连接数
				for(var i = 0; i<liLst.length; i++){
					aCount += $(liLst[i]).find("a").length;
				}
				if(aCount == 0){
					$(this).css("display", "none");
					$($(this).next()).css("display", "none");
				}
			}
		});
	}
	</script>
<script type="text/javascript">
	$(".user-name").html(currentUserName + "<br>" + currentUnitName);
</script>
<h1 class="nav-father">办案区管理</h1> 
		<!-- 换肤  -->

		<div id="accordion" class="m-ui-accordion">
		<h3><span class="leftmenu-icon aj-ba-icon-1"></span><span class="moveSpan"><span class="leftH3Span">基本档案</span></span></h3>
             <div>
                 <ul>
                  <li>
                  	<d:ss resource="/askRoom/*" type="a" request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/askRoom/showAskRoomListPage.action"'
					value="办案区各房间情况查看" />
                  </li>
                  <%-- <li>
                  	<d:ss resource="/otherRoom/*" type="a" request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/otherRoom/showOtherRoomListPage.action"'
					value="其他房间维护" />
                  </li> --%>
                  <li>
                  	<d:ss resource="/locker/*" type="a" request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/locker/showLockerListPage.action"'
					value="储物柜管理" />
				  </li>
                  <%-- <li>
                  	<d:ss resource="/illegalType/*" type="a" request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/illegalType/toIllegalTypeList.action"'
					value="违规类型" />
				  </li>--%>
                </ul>
             </div>
        <h3><span class="leftmenu-icon aj-ba-icon-2"></span><span class="moveSpan"><span class="leftH3Span">办案区使用</span></span></h3>
          <div>
                 <ul>
                  <li><d:ss resource="/handlingAreaReceipt/*" type="a"
					request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/handlingAreaReceipt/showHandlingAreaReceiptListPage.action"'
					value="办案区使用单" /></li>
					<li><d:ss resource="/handlingAreaReceipt/*" type="a"
					request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/handlingAreaReceipt/showPoliceWhoHasCardListPage.action"'
					value="民警卡管理" /></li>
					<%-- <li><d:ss resource="/askRoomAllocation/*" type="a"
					request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/askRoomAllocation/showAskRoomAllocationSelectPage.action"'
					value="讯（询）问室分配" /></li>
                  <li><d:ss resource="/askRoomAllocation/*" type="a"
					request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/askRoomAllocation/showAskRoomAllocationRecord.action"'
					value="讯（询）问室分配记录" /></li>--%>
                </ul>
             </div>
        <%-- <h3><span class="leftmenu-icon aj-ba-icon-3"></span><span class="moveSpan"><span class="leftH3Span">办案区督查</span></span></h3>
		  <div>
                 <ul>
                 <li><d:ss resource="/handlingAreaSupervision/*" type="a"
					request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/handlingAreaSupervision/showHandlingAreaSupervision.action"'
					value="办案区督查" /></li>
                <li><d:ss resource="/handlingAreaSupervision/*" type="a"
					request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/handlingAreaSupervision/queryRoomUseRecord.action"'
					value="讯（询）问室使用记录列表" /></li>
                <li><d:ss resource="/handlingAreaSupervision/*" type="a"
					request="${pageContext.request}"
					otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/handlingAreaSupervision/toRoomUseAbnormalList.action"'
					value="异常记录查询" /></li>
                </ul>
             </div>
             --%>
             
             	<!-- 办案通 -->
             	<%-- <h3><span class="leftmenu-icon aj-ba-icon-3"></span><span class="moveSpan"><span class="leftH3Span">办案通入口</span></span></h3>
				 	 <div>
		                 <ul>
		                 <li><d:ss resource="/handlingAreaSupervision/*" type="a"
							request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/agtLogin/login.action?roomId=06fd74d7-d914-4f4c-b357-7edbacfacc7a"'
							value="登录进入" /></li>
		                <li><d:ss resource="/handlingAreaSupervision/*" type="a"
							request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/agtTaskbar/agtTaskbar.action?formId=b73c261f-c437-46e6-8bb5-29075d7c0691&formCode=SQ201700002"'
							value="任务栏图标【右键弹出部分】" /></li>
		                </ul>
		             </div> --%>
		        <!-- 影音刻录 -->
             	<%-- <h3><span class="leftmenu-icon aj-ba-icon-3"></span><span class="moveSpan"><span class="leftH3Span">影音刻录入口</span></span></h3>
				 	 <div>
		                 <ul>
		                 <li><d:ss resource="/handlingAreaSupervision/*" type="a"
							request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/videoRecording/login.action"'
							value="登录进入" /></li>
							    
		                </ul>
		             </div> --%>
		             
		        <d:ss resource="/handlingAreaSupervisionyykl/*" type="h3"
				request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/videoRecording/login.action"'
				value='<span class="leftmenu-icon"></span><span class="moveSpan"><a><span class="leftH3Span">影音刻录</span></a></span>' />    
		        <!-- 远程指挥 -->
             	<%--  <h3><span class="leftmenu-icon aj-ba-icon-3"></span><span class=""><span class="leftH3Span">远程指挥入口</span></span></h3>
				 	 <div>
		                 <ul>
		                 <li><d:ss resource="/handlingAreaSupervision/*" type="a"
							request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/remoteCommand/login.action"'
							value="登录进入" /></li>
		                </ul>
		             </div>  --%>
		         <d:ss resource="/handlingAreaSupervisionyczh/*" type="h3"
				request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/remoteCommand/login.action"'
				value='<span class="leftmenu-icon"></span><span class="moveSpan"><a><span class="leftH3Span">远程指挥</span></a></span>' />    
             
		</div>
<%-- 	</div>
</div> --%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/custom/ajgl/js/left.js"></script>

