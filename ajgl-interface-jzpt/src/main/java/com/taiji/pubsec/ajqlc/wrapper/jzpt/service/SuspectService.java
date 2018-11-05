package com.taiji.pubsec.ajqlc.wrapper.jzpt.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.BasicPersonBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.CriminalRecordBean;

/**
 * 嫌疑人相关信息接口。
 * @author dixiaofeng
 *
 */
public interface SuspectService {

	/**
	 * 根据身份证号查询人员基本信息。
	 * @param idcard 身份证号
	 * @return 人员基本信息
	 */
	BasicPersonBean findBasicInfo(String idcard);
	
	/**
	 * 根据身份证号查询人员的前科记录。
	 * @param idcard 身份证号
	 * @return 前科基本信息列表
	 */
	List<CriminalRecordBean> findCriminalRecord(String idcard);
}
