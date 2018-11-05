package com.taiji.pubsec.ajqlc.baq.service;

import com.taiji.pubsec.ajqlc.baq.model.PersonCheckRecord;

/**
 * 人身检查记录服务接口
 * @author wangfx
 *
 */
public interface IPersonCheckRecordService {
	
	/**
	 * 保存人身检查记录
	 * @param personCheckRecord 人身检查记录
	 * 		  handlingAreaReceipt 需要办案区使用单信息
	 * @return 办案区使用单id
	 */
	public String savePersonCheckRecord(PersonCheckRecord personCheckRecord);
	
	/**
	 * 更新人身检查记录，同时更新办案区使用单最新修改人与最新修改时间
	 * @param personCheckRecord 人身检查记录
	 */
	public void updatePersonCheckRecord(PersonCheckRecord personCheckRecord);

}
