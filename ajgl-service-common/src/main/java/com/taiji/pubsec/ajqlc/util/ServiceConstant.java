package com.taiji.pubsec.ajqlc.util;

/**
 * 服务器地址端口等
 * @author huangda
 *
 */
public final class ServiceConstant {
	
	/**
	 * 大华服务器地址
	 */
	public static String DHSERVERIP;
	/**
	 * 大华平台端口
	 */
	public static String DHSERVERPORT;
	
	/**
	 * 实战平台地址
	 */
	public static String SZPT_IP;
	/**
	 * 实战平台端口
	 */
	public static String SZPT_PORT;
	/**
	 * 安管通 地址
	 */
	public static String  AGT_HOSTIP;
	/**
	 * 预警模块-定时任务提前时间
	 */
	public static String YJ_DSTQSJ;
	
	/**
	 * 安管通模块-实战平台代理
	 */
	public static String AGT_SZPTDL;
	/**
	 * 安管通模块-实战平台ip
	 */
	public static String AGT_SZPTIP;
	/**
	 * 项目上线日期
	 */
	public static String STRAT_DATE;
	/**
	 * 大华平台的用户名
	 */
	public static String DHSERVERUSER;
	/**
	 * 大华平台的密码
	 */
	public static String DHSERVERPASSWORD;
	
	public ServiceConstant(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10){
		ServiceConstant.DHSERVERIP = arg0;
		ServiceConstant.DHSERVERPORT = arg1;
		ServiceConstant.SZPT_IP = arg2;
		ServiceConstant.SZPT_PORT = arg3;
		ServiceConstant.AGT_HOSTIP = arg4;
		ServiceConstant.YJ_DSTQSJ = arg5;
		ServiceConstant.AGT_SZPTDL = arg6;
		ServiceConstant.AGT_SZPTIP = arg7;
		ServiceConstant.STRAT_DATE = arg8;
		ServiceConstant.DHSERVERUSER = arg9;
		ServiceConstant.DHSERVERPASSWORD = arg10;
	}
}
