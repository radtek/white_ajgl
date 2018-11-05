<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="d" uri="/WEB-INF/tag/securityresource.tld"%> 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/custom/ajgl/js/left.js"></script>
<div class="user-box">
	<div class="user-icon" onclick ="openPersonInfo()" >
		
	</div>
	<div class="user-name"></div>
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
</div>

<script type="text/javascript">
	$(".user-name").html(currentUserName + "<br>" + currentUnitName);
</script>
<h1 class="nav-father">系统管理</h1>

<div id="accordion" class="m-ui-accordion" style="display: none">
	<h3><span class="leftmenu-icon aj-ba-icon-1"></span><span class="moveSpan"><a myHref="<%=request.getContextPath()%>/dictionaryType/toDictionaryType.action"><span class="leftH3Span">数据字典</span></a></span></h3><div></div>
	
	<h3><span class="leftmenu-icon aj-ba-icon-1"></span><span class="moveSpan"><span class="leftH3Span">组织机构管理</span></span></h3>
	<div>
		<ul>
			<li>
				<d:ss resource="/unit/*"
							type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/unit/toUnit.action"' value="单位管理" />	
			</li>
			<li>
				<d:ss resource="/department/*"
							type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/department/toDepartment.action"' value="部门管理" />	
			</li>
			<li>
				<d:ss resource="/personManage/*"
							type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/personManage/toPerson.action"' value="人员管理" />	
			</li>
		</ul>
	</div>
	<h3><span class="leftmenu-icon aj-ba-icon-1"></span><span class="moveSpan"><span class="leftH3Span">角色权限管理</span></span></h3>
	<div>
		<ul>
			<li>
				<d:ss resource="/resource/*"
							type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/resource/toResource.action"' value="资源管理" />	
			</li>
			<li>
				<d:ss resource="/authority/*"
							type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/authority/toAuthority.action"' value="权限管理" />	
			</li>
			<li>
				<d:ss resource="/role/*"
							type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/role/toRole.action"' value="角色管理" />	
			
			</li>
			<li>
				<d:ss resource="/userManage/*"
							type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/userManage/toUser.action"' value="用户管理" />	
			</li>
		</ul>
	</div>
	<h3><span class="leftmenu-icon aj-ba-icon-1"></span><span class="moveSpan"><span class="leftH3Span">预警规则设置</span></span></h3>
		<div>
			<ul>
				<li><d:ss resource="/alertRule/*" type="h3"
				request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/alertRule/askRoomAlert.action"'
				value="办案区预警" /></li>
				<li><d:ss resource="/alertRule/*" type="h3" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/alertRule/sawpyjAlert.action"'
				value="涉案物品预警" /></li>
				<li><d:ss resource="/alertRule/*" type="h3"
				request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/alertRule/anjkyjAlert.action"'
				value='案件监控预警' /></li>
			</ul>
		</div>
		
             <d:ss resource="/lockerStatistics/*" type="h3" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/operationLog/showLogPage.action"'
				value="<span class='leftmenu-icon icon-1'></span><span class='moveSpan'><a><span class='leftH3Span'>操作日志查询</span></a></span>" />
</div>
