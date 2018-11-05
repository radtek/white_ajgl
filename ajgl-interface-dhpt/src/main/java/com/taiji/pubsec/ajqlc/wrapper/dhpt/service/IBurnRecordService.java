package com.taiji.pubsec.ajqlc.wrapper.dhpt.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.BurnRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;

public interface IBurnRecordService {
	/**
	 * 查询刻录历史记录。刻录记录包括操作用户姓名、操作时间、刻录内容。
	 * @param caseFormCode 使用单编号
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @return 刻录记录列表
	 */
	public List<BurnRecordBean> queryBurnRecord(String caseFormCode, String startTime, String endTime) throws DahuaException;

}
