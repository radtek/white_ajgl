/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年2月27日 下午7:13:28
 */
package com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service;

/**
 * 储物柜UDP数据包需要的常量
 * 
 * @author yucy
 *
 */
public final class CwgConstants {

	/**
	 * 命令标志：主机发送
	 */
	public static final byte[] CMD_FLAG_PC = new byte[]{'P', 'C'};

	/**
	 * 命令标志：储物柜响应
	 */
	public static final byte[] CMD_FLAG_TK = new byte[]{'T', 'K'};

	/**
	 * 命令码：储物柜状态查询
	 */
	public static final byte CMD_CODE_LOOKUPSTATUS = 0x01;

	/**
	 * 命令码：锁箱
	 */
	public static final byte CMD_CODE_LOCK = 0x02;

	/**
	 * 命令码：解锁
	 */
	public static final byte CMD_CODE_UNLOCK = 0x03;

	/**
	 * 命令码：清除单箱密码
	 */
	public static final byte CMD_CODE_CLEANSINGLEPASSWORD = 0x04;

	/**
	 * 命令码：清除全部密码
	 */
	public static final byte CMD_CODE_CLEANALLPASSWORD = 0x05;

	/**
	 * 命令码：开单门
	 */
	public static final byte CMD_CODE_OPENSINGLEDORE = 0x06;

	/**
	 * 命令码：开所有门
	 */
	public static final byte CMD_CODE_OPENALLDORE = 0x07;

	/**
	 * 命令码：绑定箱门
	 */
	public static final byte CMD_CODE_BINDDORE = 0x08;

	/**
	 * 命令码：取消绑定箱门
	 */
	public static final byte CMD_CODE_UNBINDDORE = 0x09;

	/**
	 * 命令码：同步系统时间
	 */
	public static final byte CMD_CODE_SYNCTIME = 0x0A;

	/**
	 * 命令码：存取事件储物柜实时发送
	 */
	public static final byte CMD_CODE_SAVEGETEVENT = (byte) 0x80;

	/**
	 * 命令码：箱门变化存物柜实时发送
	 */
	public static final byte CMD_CODE_DORECHANGEEVENT = (byte) 0x81;
	
	/**
	 * 储物柜状态：未占用，正常
	 */
	public static final Integer STATUS_UNUSED_NORMAL = 1;
	
	/**
	 * 储物柜状态：占用，正常
	 */
	public static final Integer STATUS_USED_NORMAL = 2;
	
	/**
	 * 储物柜状态：未占用，锁箱
	 */
	public static final Integer STATUS_UNUSED_LOCKED = 3;
	
	/**
	 * 储物柜状态：占用，锁箱
	 */
	public static final Integer STATUS_USED_LOCKED = 4;

}
