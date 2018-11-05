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
<h1 class="nav-father">我的消息</h1> 
		<!-- 换肤  -->

		<div id="accordion" class="m-ui-accordion">
			<d:ss resource="/alertMessage/*" type="h3"
				request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/alertMessage/showMessageSearchPage.action"'
				value='<span class="leftmenu-icon icon-search"></span><span class="moveSpan"><a><span class="leftH3Span">我的消息</span></a></span>' />
		</div>
	<%-- </div>
</div> --%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/custom/ajgl/js/left.js"></script>

