<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="d" uri="/WEB-INF/tag/securityresource.tld"%>

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
		<h1 class="nav-father">涉案物品管理</h1> 
		<div id="accordion" class="m-ui-accordion">
			<h3>
				<span class="leftmenu-icon aj-ba-icon-1"></span>
				<span class="moveSpan">
					<span class="leftH3Span">基本档案</span>
				</span>
			</h3>
			<div>
				<ul>
					<li>
						<d:ss resource="/storageArea/*" type="a" request="${pageContext.request}"
						otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/storageArea/showStorageAreaListPage.action"'
						value="物证保管区维护" />
					</li>
					<li>
	                  	<d:ss resource="/locker/*" type="a" request="${pageContext.request}"
						otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/lockerMaintain/showLockerListPage.action"'
						value="储物箱维护" />
					</li>
					<!-- 
					<li>
	                  	<d:ss resource="/transitStoreArea/*" type="a" request="${pageContext.request}"
						otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/transitStoreArea/transitStoreAreaListPage.action"'
						value="暂存物品保管区维护" />
					</li>
					<li>
	                  	<d:ss resource="/transitStoreLocker/*" type="a" request="${pageContext.request}"
						otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/transitStoreLocker/transitStoreLockerListPage.action"'
						value="暂存物品储物箱维护" />
					</li>
					 -->
				</ul>
			</div>
			<!--  
			<h3>
				<span class="leftmenu-icon aj-ba-icon-1"></span><span class="moveSpan"><span class="leftH3Span">暂存物品管理</span></span>
			</h3>
			<div>
			<ul>
				<li>
					<d:ss resource="/transitStoreManage/*" type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/transitStoreManage/toTransitStoreManage.action"' 
							value="暂存物品存储情况查询" />	
				</li>
				<li>
					<d:ss resource="/transitStoreManage/*" type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/transitStoreManage/toTransitStoreIn.action"' 
							value="暂存物品入库" />
				</li>
				<li>
					<d:ss resource="/transitStoreBack/*" type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/transitStorageOutBack/toTransitStorageOutBackRecord.action"' 
							value="暂存物品出库返还" />
				</li>
				<li>
					<d:ss resource="/transitStorageReturnTimeOut/*" type="a" request="${pageContext.request}"
							otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/transitStorageReturnTimeOut/toTransitStorageReturnTimeOut.action"' 
							value="超时未返还物品清单" />
				</li>
			</ul>
			</div>
			-->
			<h3>
				<span class="leftmenu-icon aj-ba-icon-1"></span><span class="moveSpan"><span class="leftH3Span">扣押物品管理</span></span>
			</h3>
			<div>
			<ul>
				<li>
			<d:ss resource="/storageIn/*" type="a" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/storageIn/toStorageInRecord.action"'
				value="涉案物品入库" />
			</li>
				<li>
			<d:ss resource="/storageOut/*" type="a" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/storageOut/toStorageOutRecord.action"'
				value="涉案物品出库" />
			</li>
				<li>
			<d:ss resource="/backStorageForm/*" type="a" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/backStorageForm/showBackStorageFormListPage.action"'
				value="涉案物品再入库" />
			</li>
				<li>	
			<d:ss resource="/standingBook/*" type="a" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/standingBook/showStandingBookPage.action"'
				value="涉案物品台账" />
			</li>
				<li>	
			<d:ss resource="/adjustmentStorageForm/*" type="a" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/adjustmentStorageForm/showAdjustmentStorageFormListPage.action"'
				value="涉案物品存储位置调整" />
			</li>
				<li>
			<d:ss resource="/articleStatistics/*" type="a" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/articleStatistics/showArticleStatisticsPage.action"'
				value="涉案物品情况统计" />
			</li>
				<li>	
			<d:ss resource="/lockerStatistics/*" type="a" request="${pageContext.request}"
				otherAttribute='href="javascript:void(0);" myHref="${pageContext.request.contextPath}/lockerStatistics/showLockerStatisticsPage.action"'
				value="储物箱存储情况查询" />
		    </li>
		  </ul>
		</div>
		</div>	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/custom/ajgl/js/left.js"></script>

