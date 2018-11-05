<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.taiji.pubsec.ajqlc.util.Constant" %>
<%@ page import="com.taiji.pubsec.ajgl.util.OperationRecordConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<!-- 讯（询）问室管理模块常量 -->
<script type="text/javascript">
(function($){
	"use strict";
	jQuery.extend($.common, { 
		Constant : {
			
			CWGZT : function(){  //状态
				return "<%= Constant.CWGZT%>";
			},
			CWGZT_KX: function(){
				return "<%= Constant.CWGZT_KX%>";
			},
			CWGZT_TY: function(){
				return "<%= Constant.CWGZT_TY%>";
			},
			CWGZT_SYZ: function(){
				return "<%= Constant.CWGZT_SYZ%>";
			},
			
			WXSZT : function(){
				return "<%= Constant.WXSZT%>";
			},
			FJLX: function(){
				return "<%= Constant.FJLX%>";
			},
			FJLX_WXS: function(){
				return "<%= Constant.FJLX_WXS%>";
			},
			FJLX_QT: function(){
				return "<%= Constant.FJLX_QT%>";
			},
			SYZT_KX : function(){
				return "<%= Constant.SYZT_KX%>";
			},
			SYZT_BKY : function(){
				return "<%= Constant.SYZT_BKY%>";
			},
			SYZT_SYZ : function(){
				return "<%= Constant.SYZT_SYZ%>";
			},
			SYDZT : function(){
				return "<%= Constant.SYDZT%>";
			},
			SYDZT_QT : function(){
				return "<%= Constant.SYDZT_QT%>";
			},
			SYDZT_YWC : function(){
				return "<%= Constant.SYDZT_YWC%>";
			},
			SF : function(){
				return "<%= Constant.SF%>";
			},
			SF_S : function(){
				return "<%= Constant.SF_S%>";
			},
			SF_F : function(){
				return "<%= Constant.SF_F%>";
			},
			BAQYY : function(){
				return "<%= Constant.BAQYY%>";
			},
			BAQYY_QT : function(){
				return "<%= Constant.BAQYY_QT%>";
			},
			XB : function(){
				return "<%= Constant.XB%>";
			},
			XB_NAN : function(){
				return "<%= Constant.XB_NAN%>";
			},
			XB_NU : function(){
				return "<%= Constant.XB_NU%>";
			},
			XB_WEIZHI : function(){
				return "<%= Constant.XB_WEIZHI%>";
			},
			AY : function(){
				return "<%= Constant.AY%>";
			},
			AY_QT : function(){
				return "<%= Constant.AY_QT%>";
			},
			ZJLX : function(){
				return "<%= Constant.ZJLX%>";
			},
			ZJLX_SFZ : function(){
				return "<%= Constant.ZJLX_SFZ%>";
			},
			ZJLX_JGZ : function(){
				return "<%= Constant.ZJLX_JGZ%>";
			},
			ZJLX_HZ : function(){
				return "<%= Constant.ZJLX_HZ%>";
			},
			BGCS : function(){
				return "<%= Constant.BGCS%>";
			},
			WPZT : function(){
				return "<%= Constant.WPZT%>";
			},
			WPZT_ZK : function(){
				return "<%= Constant.WPZT_ZK%>";
			},
			WPZT_YFH : function(){
				return "<%= Constant.WPZT_YFH%>";
			},
			WPZT_LSQC : function(){
				return "<%= Constant.WPZT_LSQC%>";
			},
			WPZT_YJZC : function(){
				return "<%= Constant.WPZT_YJZC%>";
			},
			WPZT_YJKY : function(){
				return "<%= Constant.WPZT_YJKY%>";
			},
			HDLX : function(){
				return "<%= Constant.HDLX%>";
			},
			HDLX_X4W : function(){
				return "<%= Constant.HDLX_X4W%>";
			},
			HDLX_X2W : function(){
				return "<%= Constant.HDLX_X2W%>";
			},
			QTHDLX : function(){
				return "<%= Constant.QTHDLX%>";
			},
			YJFS_XTXX : function(){    //预警方式--系统消息
				return "<%= Constant.YJFS_XTXX%>";
			},
			YJFS_PDA : function(){    //预警方式--PDA
				return "<%= Constant.YJFS_PDA%>";
			},
			YJFS_DX : function(){    //预警方式--短信
				return "<%= Constant.YJFS_DX%>";
			},
			CFFS_DZ : function(){     //触发方式--动作触发
				return "<%= Constant.CFFS_DZ%>";
			},
			CFFS_XT : function(){  //触发方式--系统自动
				return "<%= Constant.CFFS_XT%>";
			},
			YJGZ_BCZC : function(){  //退侦后补充侦查类型的id
				return "<%= Constant.YJGZ_BCZC%>";
			},
			YJGZ_ZCWPCS : function(){  //暂存物品超时未返还预警规则的id
				return "<%= Constant.YJGZ_ZCWPCS%>";
			},
			YJGZ_KYWP : function(){  //预警规则-扣押物品
				return "<%= Constant.YJGZ_KYWP%>";
			},
			BAQMK : function(){  //所属模块--办案区
				return "<%= Constant.BAQMK%>";
			},
			AJJKMK : function(){  //所属模块--案件监控
				return "<%= Constant.AJJKMK%>";
			},
			SAWPMK : function(){  //所属模块--涉案物品
				return "<%= Constant.SAWPMK%>";
			},
			ZT : function(){  //状态
				return "<%= Constant.ZT%>";
			},
			QY : function(){  //启用
				return "<%= Constant.ZT_QY%>";
			},
			TY : function(){  //停用
				return "<%= Constant.ZT_TY%>";
			},
			WGLX_QT : function(){  //违规类型-其他情况
				return "<%= Constant.WGLX_QT%>";
			},
			SAWPXZ : function(){  //涉案物品性质-字典类型
				return "<%= Constant.SAWPXZ%>";
			},
			SAWPXZ_ZAGJ : function(){  //涉案物品性质-作案工具
				return "<%= Constant.SAWPXZ_ZAGJ%>";
			},
			CKLX : function(){  //出库类型
				return "<%= Constant.CKLX%>";
			},
			CKLX_SAYJ : function(){  //出库类型-随案移交
				return "<%= Constant.CKLX_SAYJ%>";
			},
			CKLX_ZLFH : function(){  //出库类型-责令发还
				return "<%= Constant.CKLX_ZLFH%>";
			},
			CKLX_JC : function(){  //出库类型-借出
				return "<%= Constant.CKLX_JC%>";
			},
			CKLX_QT : function(){  //出库类型-其它
				return "<%= Constant.CKLX_QT%>";
			},
			//涉案物品操作记录类型
			WP_CZJL_RK : function(){//入库
				return "<%= OperationRecordConstant.WP_CZJL_RK%>";
			},
			WP_CZJL_CK : function(){//出库
				return "<%= OperationRecordConstant.WP_CZJL_CK%>";
			},
			WP_CZJL_FH : function(){//返还
				return "<%= OperationRecordConstant.WP_CZJL_FH%>";
			},
			WP_CZJL_TZ : function(){//调整储存位置
				return "<%= OperationRecordConstant.WP_CZJL_TZ%>";
			}
		}
	});	
})(jQuery);
</script>
</body>
</html>