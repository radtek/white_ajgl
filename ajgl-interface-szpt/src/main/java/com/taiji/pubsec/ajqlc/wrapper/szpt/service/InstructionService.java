package com.taiji.pubsec.ajqlc.wrapper.szpt.service;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;

/**
 * 实战平台指令接口。
 * @author dixiaofeng
 *
 */
public interface InstructionService {

	/**
	 * 创建指令。
	 * @param suspectIdCardNo 嫌疑人身份证号
	 * @param formCode 使用单编号
	 * @param content 指令内容
	 * @param type 指令类型，取实战中的字典项（code）
	 * @param receiveUnits 接收单位编码数组
	 * @param creatorCode 创建人编码
	 * @param creatorUnitCode 创建人单位编码
	 * @return 结果bean
	 */
	ResultBean create(String suspectIdCardNo, String formCode, String content, String type, String[] receiveUnits, String creatorCode, String creatorUnitCode);
	
	
}
